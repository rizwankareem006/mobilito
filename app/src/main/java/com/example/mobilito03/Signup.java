package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDate;


public class Signup extends AppCompatActivity {

    public static final String TAG = "Signup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText usernameText = findViewById(R.id.signupUsername);
        EditText fullNameText = findViewById(R.id.signupFullName);
        EditText dobText = findViewById(R.id.signupDob);
        RadioGroup genderGroup = findViewById(R.id.signupGender);
        EditText emailText = findViewById(R.id.signupEmail);
        EditText mobileText = findViewById(R.id.signupMobile);
        EditText passwordText = findViewById(R.id.signupPassword);
        Button signupBtn = findViewById(R.id.signup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String fullName = fullNameText.getText().toString();
                LocalDate dobDate = LocalDate.parse(dobText.getText().toString());
                String dob = dobDate.toString();
                RadioButton genderRadio = findViewById(genderGroup.getCheckedRadioButtonId());
                String gender = genderRadio.getText().toString();
                String email = emailText.getText().toString();
                String mobile = mobileText.getText().toString();
                String password = passwordText.getText().toString();

                long id = Users.insertUser(Signup.this,
                        username,
                        fullName,
                        dob,
                        gender,
                        email,
                        mobile,
                        password);

                if (id == -1) {
                    Toast.makeText(Signup.this, "Sign Up Failed!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.putExtra("username", username);
                    Toast.makeText(Signup.this, "Sign Up Successful!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
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