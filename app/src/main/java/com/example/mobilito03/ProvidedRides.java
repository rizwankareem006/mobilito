package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class ProvidedRides extends AppCompatActivity {

    private static final String TAG = "ProvidedRides";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provided_rides);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Log.d(TAG, "Before getting ride!");
        Rides[] rides = Rides.getRides(getApplicationContext(), username);
        Log.d(TAG, "After getting ride!");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProvidedRidesAdapter adapter = new ProvidedRidesAdapter(getApplicationContext(), rides, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                // callback performed on click
                Intent detailsIntent = new Intent(getApplicationContext(), ProvideRideDetails.class);
                detailsIntent.putExtra("username", username);
                detailsIntent.putExtra("rideId", rides[position].getRideId());
                detailsIntent.putExtra("booked", rides[position].isBooked());
                startActivity(detailsIntent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}