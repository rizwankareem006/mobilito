package com.example.mobilito03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookRide extends AppCompatActivity {

    public static final String TAG = "BookRide";
    public static final int START_REQUEST_CODE = 1;
    public static final int END_REQUEST_CODE = 2;

    protected long startLocationId = -1;
    protected long endLocationId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        EditText copassengerText = findViewById(R.id.bookCopassengers);
        EditText startTimeText = findViewById(R.id.bookStartTime);
        EditText waitingTimeText = findViewById(R.id.bookWaitingTime);
        EditText amountText = findViewById(R.id.bookAmount);
        EditText distanceText = findViewById(R.id.bookDistance);

        Button startLocation = findViewById(R.id.bookStartLocation);
        Button endLocation = findViewById(R.id.bookEndLocation);

        Button chooseRide = findViewById(R.id.bookChooseRide);

        startLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(getApplicationContext(), Maps.class);
                startActivityForResult(mapIntent, START_REQUEST_CODE);
            }
        });

        endLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(getApplicationContext(), Maps.class);
                startActivityForResult(mapIntent, END_REQUEST_CODE);
            }
        });

        chooseRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int copassengers = Integer.parseInt(copassengerText.getText().toString());
                LocalTime startTime = LocalTime.parse(startTimeText.getText().toString());
                int waitingTime = Integer.parseInt(waitingTimeText.getText().toString());
                int amount = Integer.parseInt(amountText.getText().toString());
                double distance = Double.parseDouble(distanceText.getText().toString());
                Locations startLocation = Locations.getLocation(getApplicationContext(), (int)startLocationId);
                Locations endLocation = Locations.getLocation(getApplicationContext(), (int)endLocationId);
                LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime);
                Rides[] rides = Rides.getRides(getApplicationContext(), copassengers, startDateTime, waitingTime, amount);
                ArrayList<Integer> validRides = new ArrayList<>();

                for (Rides ride :
                        rides) {
                    double[] distances = calculateDistances(startLocation, endLocation, ride.getStartLocation(), ride.getEndLocation());
                    if (distances[0] <= distance && distances[1] <= distance) {
                        validRides.add(ride.getRideId());
                    }
                }

                Intent chooseRideIntent = new Intent(getApplicationContext(), ChooseRide.class);
                chooseRideIntent.putExtra("username", username);
                chooseRideIntent.putExtra("rideIds", validRides);
                chooseRideIntent.putExtra("copassengers", copassengers);
                startActivity(chooseRideIntent);
            }
        });
    }

    public double[] calculateDistances(Locations start1, Locations end1, Locations start2, Locations end2) {
        double startDistance, endDistance;
        LatLng srt1 = new LatLng(start1.getLatitude(), start1.getLongitude());
        LatLng srt2 = new LatLng(start2.getLatitude(), start2.getLongitude());
        startDistance = SphericalUtil.computeDistanceBetween(srt1, srt2);
        startDistance /= 1000;

        LatLng en1 = new LatLng(end1.getLatitude(), end1.getLongitude());
        LatLng en2 = new LatLng(end2.getLatitude(), end2.getLongitude());
        endDistance = SphericalUtil.computeDistanceBetween(en1, en2);
        endDistance /= 1000;

        return new double[]{startDistance, endDistance};
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditText startAddressText = findViewById(R.id.bookStartAddress);
        EditText endAddressText = findViewById(R.id.bookEndAddress);

        if (requestCode == START_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "After result ok!");
                String address = null;
                double[] coords = null;
                if (data.hasExtra("address") && data.hasExtra("coords")) {
                    Log.d(TAG, "Has data!");
                    address = data.getStringExtra("address");
                    coords = data.getDoubleArrayExtra("coords");
                    startLocationId = Locations.insertLocation(getApplicationContext(), address, coords[0], coords[1]);
                    startAddressText.setText(address);
                }
                else {
                    Log.d(TAG, "Start values not in intent!");
                }
            }
            else {
                Log.d(TAG, "Start Location result not OK!");
                //Toast.makeText(getApplicationContext(), "Start Location result not OK!", Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == END_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String address = null;
                double[] coords = null;
                if (data.hasExtra("address") && data.hasExtra("coords")) {
                    address = data.getStringExtra("address");
                    coords = data.getDoubleArrayExtra("coords");
                    endLocationId = Locations.insertLocation(getApplicationContext(), address, coords[0], coords[1]);
                    endAddressText.setText(address);
                }
                else {
                    Log.d(TAG, "End values not in intent!");
                }
            }
            else {
                Log.d(TAG, "End Location result not OK!");
                //Toast.makeText(getApplicationContext(), "End Location result not OK!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Log.d(TAG, "Unknown request code!");
            //Toast.makeText(getApplicationContext(), "Unknown request code!", Toast.LENGTH_LONG).show();
        }
    }
}