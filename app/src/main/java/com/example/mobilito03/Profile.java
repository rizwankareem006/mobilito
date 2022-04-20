package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        EditText usernameText = findViewById(R.id.profileUsername);
        EditText fullNameText = findViewById(R.id.profileFullName);
        EditText dobText = findViewById(R.id.profileDob);
        EditText genderText = findViewById(R.id.profileGender);
        EditText emailText = findViewById(R.id.profileEmail);
        EditText mobileText = findViewById(R.id.profileMobile);

        if (intent.hasExtra("username")) {
            Users user = Users.getUser(getApplicationContext(), intent.getStringExtra("username"));
            if (user != null) {
                usernameText.setText(user.getUsername());
                fullNameText.setText(user.getFullName());
                dobText.setText(user.getDateOfBirth().toString());
                genderText.setText(user.getGender());
                emailText.setText(user.getEmail());
                mobileText.setText(user.getMobile());
            }
            else {
                Toast.makeText(getApplicationContext(), "User not found!", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "No username passed!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}