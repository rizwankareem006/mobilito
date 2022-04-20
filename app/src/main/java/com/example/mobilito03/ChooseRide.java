package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class ChooseRide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ride);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        int copassengers = intent.getIntExtra("copassengers", 0);

        ArrayList<Integer> rideIds = intent.getIntegerArrayListExtra("rideIds");
        Rides[] rides = new Rides[rideIds.size()];
        int i = 0;
        for (int rideId :
                rideIds) {
            rides[i++] = Rides.getRide(getApplicationContext(), rideId);
        }

        RecyclerView recyclerView = findViewById(R.id.chooseRecyclerView);
        ChooseRideAdapter adapter = new ChooseRideAdapter(getApplicationContext(), rides, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                // callback performed on click
                Intent detailsIntent = new Intent(getApplicationContext(), ChooseRideDetails.class);
                detailsIntent.putExtra("username", username);
                detailsIntent.putExtra("rideId", rides[position].getRideId());
                detailsIntent.putExtra("copassengers", copassengers);
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