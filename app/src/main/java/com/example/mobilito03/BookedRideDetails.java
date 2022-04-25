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

public class BookedRideDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_ride_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int rideId = intent.getIntExtra("rideId", 0);


        Rides ride = Rides.getBookedRideDetails(getApplicationContext(), rideId);
        TextView provider = findViewById(R.id.bookedDetProvider);
        TextView passengers = findViewById(R.id.bookedDetPassengers);
        TextView vehicleNumber = findViewById(R.id.bookedDetVehicleNumber);
        TextView vehicleModel = findViewById(R.id.bookedDetVehicleModel);
        TextView noSeats = findViewById(R.id.bookedDetNoSeats);
        TextView startTime = findViewById(R.id.bookedDetStartTime);
        TextView expectedAmount = findViewById(R.id.bookedDetExpectedAmount);
        Button location = findViewById(R.id.bookedDetLocation);
        Button finishRide = findViewById(R.id.bookedDetFinishRide);

        provider.setText(ride.getProvider().getFullName());
        passengers.setText(String.valueOf(ride.getNoProviderCopassengers()+ride.getNoTakerCopassengers()));
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

        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(getApplicationContext(), Profile.class);
                profileIntent.putExtra("username", ride.getProvider().getUsername());
                startActivity(profileIntent);
            }
        });

        finishRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String providerUsername = ride.getProvider().getUsername();
                Rides.deleteRide(getApplicationContext(), ride.getRideId());
                FinishRideDialogFragment frdf = new FinishRideDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("oppUsername", providerUsername);
                frdf.setArguments(bundle);
                frdf.show(getFragmentManager(), "dialog");
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}