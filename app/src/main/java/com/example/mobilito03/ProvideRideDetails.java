package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProvideRideDetails extends AppCompatActivity {

    private static final String TAG = "ProvideRideDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_ride_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int rideId = intent.getIntExtra("rideId", 0);
        boolean booked = intent.getBooleanExtra("booked", false);


        Rides ride = null;
        if (!booked)
            ride = Rides.getRideNB(getApplicationContext(), rideId);
        else {
            Log.d(TAG, "Before fetching rides!");
            ride = Rides.getRideB(getApplicationContext(), rideId);
            Log.d(TAG, "After fetching rides!");
        }
        TextView taker = findViewById(R.id.provDetTaker);
        TextView noCopassengers = findViewById(R.id.provDetCopassengers);
        TextView vehicleNumber = findViewById(R.id.provDetVehicleNumber);
        TextView vehicleModel = findViewById(R.id.provDetVehicleModel);
        TextView noSeats = findViewById(R.id.provDetNoSeats);
        TextView startTime = findViewById(R.id.provDetStartTime);
        TextView expectedAmount = findViewById(R.id.provDetExpectedAmount);
        Button location = findViewById(R.id.provDetLocation);
        Button finishRide = findViewById(R.id.provDetFinishRide);

        if (booked)
            taker.setText(ride.getTaker().getFullName());
        else
            taker.setText("Not Booked!");

        int takerCoPass = (booked)? ride.getNoTakerCopassengers() : 0;
        noCopassengers.setText(String.valueOf(ride.getNoProviderCopassengers()+takerCoPass));
        vehicleNumber.setText(ride.getVehicleNumber());
        vehicleModel.setText(ride.getVehicleModel());
        noSeats.setText(String.valueOf(ride.getNoSeats()));
        startTime.setText(ride.getStartTime().toString());
        expectedAmount.setText(String.valueOf(ride.getExpectedAmount()));

        Rides finalRide = ride;
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationIntent = new Intent(getApplicationContext(), MapsMarker.class);
                locationIntent.putExtra("startLocationId", finalRide.getStartLocation().getLocationId());
                locationIntent.putExtra("endLocationId", finalRide.getEndLocation().getLocationId());
                startActivity(locationIntent);
            }
        });

        if (booked) {
            finishRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else {
            finishRide.setEnabled(false);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}