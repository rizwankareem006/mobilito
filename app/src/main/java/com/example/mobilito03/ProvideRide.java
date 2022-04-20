package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProvideRide extends AppCompatActivity {

    public static final String TAG = "ProvideRide";
    public static final int START_REQUEST_CODE = 1;
    public static final int END_REQUEST_CODE = 2;

    protected long startLocationId = -1;
    protected long endLocationId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_ride);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        EditText noCopassengersText = findViewById(R.id.provideNoCopassengers);
        EditText vehicleNumberText = findViewById(R.id.provideVehicleNumber);
        EditText vehicleModelText = findViewById(R.id.provideVehicleModel);
        EditText noSeatsText = findViewById(R.id.provideNoSeats);
        EditText startTimeText = findViewById(R.id.provideStartTime);
        EditText expectedAmountText = findViewById(R.id.provideExpectedAmount);
//      EditText startAddressText = findViewById(R.id.provideStartAddress);
//      EditText endAddressText = findViewById(R.id.provideEndAddress);

        Button startLocation = findViewById(R.id.provideStartLocation);
        Button endLocation = findViewById(R.id.provideEndLocation);

        Button provideRide = findViewById(R.id.provideRide);

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

        provideRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users user = Users.getUser(getApplicationContext(), intent.getStringExtra("username"));
                int noCopassengers = Integer.parseInt(noCopassengersText.getText().toString());
                String vehicleNumber = vehicleNumberText.getText().toString();
                String vehicleModel = vehicleModelText.getText().toString();
                int noSeats = Integer.parseInt(noSeatsText.getText().toString());
                LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(startTimeText.getText().toString()));
                int expectedAmount = Integer.parseInt(expectedAmountText.getText().toString());

                long rideId = Rides.insertRide(getApplicationContext(), user, noCopassengers, vehicleNumber, vehicleModel, noSeats, startTime, expectedAmount, startLocationId, endLocationId, false);

                if (rideId == -1) {
                    Log.d(TAG, "Ride Registration Unsuccessful!");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Ride Registered Successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EditText startAddressText = findViewById(R.id.provideStartAddress);
        EditText endAddressText = findViewById(R.id.provideEndAddress);

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

    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return true;
    }


}