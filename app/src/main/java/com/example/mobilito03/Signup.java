package com.example.mobilito03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class Signup extends AppCompatActivity {

    int year, month, day;
    DatePickerDialog.OnDateSetListener myDateListener;

    public static final String TAG = "Signup";
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText usernameText = findViewById(R.id.signupUsername);
        EditText fullNameText = findViewById(R.id.signupFullName);
        EditText dobText = findViewById(R.id.signupDob);
        Spinner genderSpinner = (Spinner) findViewById(R.id.signupGender);
        EditText emailText = findViewById(R.id.signupEmail);
        EditText mobileText = findViewById(R.id.signupMobile);
        EditText passwordText = findViewById(R.id.signupPassword);
        Button signupBtn = findViewById(R.id.signup);
        Button loginBtn = (Button) findViewById(R.id.loginbtn);

        genderSpinner.setSelection(0);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

         myDateListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if (month < 10 && day < 10) {
                    dobText.setText(String.format("%d-0%d-0%d", year, month+1, day));
                }
                else if (day < 10) {
                    dobText.setText(String.format("%d-%d-0%d", year, month+1, day));
                }
                else if (month < 10) {
                    dobText.setText(String.format("%d-0%d-%d", year, month+1, day));
                }
                else {
                    dobText.setText(String.format("%d-%d-%d", year, month+1, day));
                }
            }
        };

        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalender();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String fullName = fullNameText.getText().toString();
                LocalDate dobDate = LocalDate.parse(dobText.getText().toString());
                String dob = dobDate.toString();
                String gender = genderSpinner.getSelectedItem().toString();
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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backIntent);
        return true;
    }

    private void showCalender() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }
}