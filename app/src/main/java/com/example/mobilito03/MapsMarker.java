package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mobilito03.databinding.ActivityMapsMarkerBinding;

public class MapsMarker extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsMarkerBinding binding;

    public static final String TAG = "MapsMarker";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding = ActivityMapsMarkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d(TAG, "Start on map ready!");
        Intent intent = getIntent();

        int startId = intent.getIntExtra("startLocationId", 0);
        int endId = intent.getIntExtra("endLocationId", 0);
        Log.d(TAG, "start id: "+startId+"\t end id: "+endId);
        Locations startLocation = Locations.getLocation(getApplicationContext(), startId);
        Locations endLocation = Locations.getLocation(getApplicationContext(), endId);
        Log.d(TAG, "After fetching locations!");
        // Add a marker in Sydney and move the camera
        Log.d(TAG, endLocation.toString());
        Log.d(TAG, "Lat:"+endLocation.getLatitude()+"\t Lng: "+endLocation.getLongitude());
        LatLng end = new LatLng(endLocation.getLatitude(), endLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(end));
        Log.d(TAG, "After end marker!");
        LatLng start = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(start));
        Log.d(TAG, "After start marker!");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        Log.d(TAG, "After move camera!");
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f));


        Log.d(TAG, "End on map ready!");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}