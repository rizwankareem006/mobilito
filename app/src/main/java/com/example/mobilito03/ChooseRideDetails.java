package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseRideDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ride_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int rideId = intent.getIntExtra("rideId", 0);
        int copassengers = intent.getIntExtra("copassengers", 0);


        Rides ride = Rides.getRideChooseDetails(getApplicationContext(), rideId);
        TextView provider = findViewById(R.id.chooseDetProvider);
        TextView passengers = findViewById(R.id.chooseDetPassengers);
        TextView vehicleNumber = findViewById(R.id.chooseDetVehicleNumber);
        TextView vehicleModel = findViewById(R.id.chooseDetVehicleModel);
        TextView noSeats = findViewById(R.id.chooseDetNoSeats);
        TextView startTime = findViewById(R.id.chooseDetStartTime);
        TextView expectedAmount = findViewById(R.id.chooseDetExpectedAmount);
        Button location = findViewById(R.id.chooseDetLocation);
        Button bookRide = findViewById(R.id.chooseDetBookRide);

        provider.setText(ride.getProvider().getFullName());
        passengers.setText(String.valueOf(ride.getNoProviderCopassengers()+copassengers));
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

        bookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int no_updated = Rides.updateRide(getApplicationContext(), rideId, username, copassengers);

                if (no_updated == 1) {
                    Toast.makeText(getApplicationContext(), "Booked Successfully!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Booking Failed!", Toast.LENGTH_LONG).show();
                }

                Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                homeIntent.putExtra("username", username);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}