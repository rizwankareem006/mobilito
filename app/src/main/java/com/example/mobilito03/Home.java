package com.example.mobilito03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username = null;
        Intent intent = getIntent();
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username");
        }
        else {
            Toast.makeText(getApplicationContext(), "No username passed!", Toast.LENGTH_LONG).show();
        }
        String finalUsername = username;
        Button toProvideRide = findViewById(R.id.toProvideRide);
        Button toBookRide = findViewById(R.id.toBookRide);
        Button toProvidedRides = findViewById(R.id.toProvidedRides);
        Button toBookedRides = findViewById(R.id.toBookedRides);
        Button toProfile = findViewById(R.id.toProfile);
        Button signout = findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(welcomeIntent);
            }
        });


        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(getApplicationContext(), Profile.class);
                profileIntent.putExtra("username", finalUsername);
                startActivity(profileIntent);
            }
        });

        toProvideRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent provideRideIntent = new Intent(getApplicationContext(), ProvideRide.class);
                provideRideIntent.putExtra("username", finalUsername);
                startActivity(provideRideIntent);
            }
        });

        toProvidedRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent providedRidesIntent = new Intent(getApplicationContext(), ProvidedRides.class);
                providedRidesIntent.putExtra("username", finalUsername);
                startActivity(providedRidesIntent);
            }
        });

        toBookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookRideIntent = new Intent(getApplicationContext(), BookRide.class);
                bookRideIntent.putExtra("username", finalUsername);
                startActivity(bookRideIntent);
            }
        });

        toBookedRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookedRidesIntent = new Intent(getApplicationContext(), BookedRides.class);
                bookedRidesIntent.putExtra("username", finalUsername);
                startActivity(bookedRidesIntent);
            }
        });
    }
}