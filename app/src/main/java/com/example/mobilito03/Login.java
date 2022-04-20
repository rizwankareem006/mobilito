package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Button loginButton = findViewById(R.id.login);
        EditText usernameText = findViewById(R.id.loginUsername);
        EditText passwordText = findViewById(R.id.loginPassword);

        if (intent.hasExtra("username")) {
            usernameText.setText(intent.getStringExtra("username"));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                Users user = Users.getUser(getApplicationContext(), username);

                if (user == null) {
                    Toast.makeText(Login.this, "Invalid Username!", Toast.LENGTH_LONG).show();
                }
                else {
                    if (password.equals(user.getPassword())) {
                        Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                        homeIntent.putExtra("username", username);
                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                        startActivity(homeIntent);
                    }
                    else {
                        Toast.makeText(Login.this, "Invalid Password!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backIntent);
        return true;
    }
}