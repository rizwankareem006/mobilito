package com.example.mobilito03;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    public static final String TAG = "MapsFragment";

    private String addressString = null;
    private double[] coords = null;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.d(TAG, "OnMapReady!");
            init(googleMap);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void init(GoogleMap googleMap) {
        EditText locationText = getActivity().findViewById(R.id.locationText);
        locationText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addressString = textView.getText().toString().trim();
                    coords = geoLocate(addressString);
                    if (coords != null)
                        addMarker(googleMap, coords);
                    else
                        Toast.makeText(getContext(), "Location Not Found!", Toast.LENGTH_LONG).show();
                    //return true;
                }
                return false;
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                addressString = reverseGeoLocate(latLng);
                coords = new double[]{latLng.latitude, latLng.longitude};
                /*Toast.makeText(getContext(), addressStr, Toast.LENGTH_LONG).show();
                Log.d(TAG, "Address: "+addressStr);*/

                if (addressString != null) {
                    locationText.setText(addressString);
                    addMarker(googleMap, latLng);
                }
            }
        });

        Button setLocation = getActivity().findViewById(R.id.setLocation);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent provideRideIntent = new Intent(getContext(), ProvideRide.class);
                provideRideIntent.putExtra("address", addressString);
                provideRideIntent.putExtra("coords", coords);
                getActivity().setResult(Activity.RESULT_OK, provideRideIntent);
                getActivity().finish();
            }
        });
        Log.d(TAG, "End of init!");
    }

    public double[] geoLocate(String searchString) {
        if (searchString.isEmpty())
            return null;

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addressList = new ArrayList<>();
        try{
            addressList = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(addressList.size() > 0) {
            Address address = addressList.get(0);
            double latitude = 0, longitude=0;
            latitude = address.getLatitude();
            longitude = address.getLongitude();
            return new double[]{latitude, longitude};
        }
        return null;
    }

    public String reverseGeoLocate(LatLng latlng) {
        if (latlng == null)
            return null;

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addressList = new ArrayList<>();
        try{
            addressList = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
        } catch (IOException e) {
            Log.e(TAG, "reverseGeoLocate: IOException: " + e.getMessage() );
        }

        if (addressList.size() > 0) {
            Address address = addressList.get(0);
            String addressStr = "";
            addressStr += address.getAddressLine(0);
            return addressStr;
        }
        return null;
    }

    public void addMarker(GoogleMap googleMap, double[] coords) {
        googleMap.clear();
        LatLng locPosition = new LatLng(coords[0], coords[1]);
        googleMap.addMarker(new MarkerOptions().position(locPosition));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locPosition));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
    }

    public void addMarker(GoogleMap googleMap, LatLng latLng) {
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
    }
}