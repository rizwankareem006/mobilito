package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class BookedRides extends AppCompatActivity {

    public static final String TAG = "BookedRides";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_rides);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Log.d(TAG, "Before getting ride!");
        Rides[] rides = Rides.getBookedRides(getApplicationContext(), username);
        Log.d(TAG, "After getting ride!");
        RecyclerView recyclerView = findViewById(R.id.bookedRecyclerView);
        BookedRidesAdapter adapter = new BookedRidesAdapter(getApplicationContext(), rides, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                // callback performed on click
                Intent detailsIntent = new Intent(getApplicationContext(), BookedRideDetails.class);
                detailsIntent.putExtra("username", username);
                detailsIntent.putExtra("rideId", rides[position].getRideId());
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