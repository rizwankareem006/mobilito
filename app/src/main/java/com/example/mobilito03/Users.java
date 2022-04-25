package com.example.mobilito03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;

public class Users {

    private static final String TAG = "Users";
    private String username;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String mobile;
    private String password;
    private int rating;

    public Users(String username, String fullName, LocalDate dateOfBirth, String gender, String email, String mobile, String password, int rating) {
        this.username = username;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.rating = rating;
    }

    @Nullable
    public static Users getUser(Context context, String username) {
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = db.query(MobilitoContract.Users.TABLE_NAME,
                null,
                MobilitoContract.Users.COLUMN_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null);

        Users user = null;
        if (cursor.moveToFirst()) {
            user = new Users(cursor.getString(0),
                    cursor.getString(1),
                    LocalDate.parse(cursor.getString(2)),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7));
        }

        db.close();

        return user;
    }

    public static long insertUser(Context context,
                                  String username,
                                  String fullName,
                                  String dob,
                                  String gender,
                                  String email,
                                  String mobile,
                                  String password)
    {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MobilitoContract.Users.COLUMN_USERNAME, username);
        values.put(MobilitoContract.Users.COLUMN_FULL_NAME, fullName);
        values.put(MobilitoContract.Users.COLUMN_DATE_OF_BIRTH, dob);
        values.put(MobilitoContract.Users.COLUMN_GENDER, gender);
        values.put(MobilitoContract.Users.COLUMN_EMAIL, email);
        values.put(MobilitoContract.Users.COLUMN_MOBILE, mobile);
        values.put(MobilitoContract.Users.COLUMN_PASSWORD, password);
        values.put(MobilitoContract.Users.COLUMN_RATING, 0);

        long newRowId = db.insert(MobilitoContract.Users.TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    public static int updateRating(Context context, String username, int value) {
        Log.d(TAG, "Inside Update");
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        Users user = Users.getUser(context, username);
        ContentValues values = new ContentValues();
        values.put(MobilitoContract.Users.COLUMN_RATING, user.getRating()+value);

        int affected = db.update(MobilitoContract.Users.TABLE_NAME, values, MobilitoContract.Users.COLUMN_USERNAME + "=?", new String[]{username});
        Log.d(TAG, String.valueOf(affected));
        db.close();

        return affected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                '}';
    }
}

