package com.example.mobilito03;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Mobilito.db";

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    public void onCreate(@NonNull SQLiteDatabase db) {

        String users_create = "CREATE TABLE " + MobilitoContract.Users.TABLE_NAME + " ("
                + MobilitoContract.Users.COLUMN_USERNAME + " TEXT PRIMARY KEY,"
                + MobilitoContract.Users.COLUMN_FULL_NAME + " TEXT,"
                + MobilitoContract.Users.COLUMN_DATE_OF_BIRTH + " INTEGER,"
                + MobilitoContract.Users.COLUMN_GENDER + " TEXT,"
                + MobilitoContract.Users.COLUMN_EMAIL + " TEXT,"
                + MobilitoContract.Users.COLUMN_MOBILE + " TEXT,"
                + MobilitoContract.Users.COLUMN_PASSWORD + " TEXT,"
                + MobilitoContract.Users.COLUMN_RATING + " INTEGER DEFAULT 0)";

        String locations_create = "CREATE TABLE " + MobilitoContract.Locations.TABLE_NAME + " ("
                + MobilitoContract.Locations.COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MobilitoContract.Locations.COLUMN_ADDRESS + " TEXT,"
                + MobilitoContract.Locations.COLUMN_LATITUDE + " REAL,"
                + MobilitoContract.Locations.COLUMN_LONGITUDE + " REAL"
                + ")";

        String rides_create = "CREATE TABLE " + MobilitoContract.Rides.TABLE_NAME + " ("
                + MobilitoContract.Rides.COLUMN_RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME + " TEXT,"
                + MobilitoContract.Rides.COLUMN_TAKER_USERNAME + " TEXT,"
                + MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS + " INTEGER,"
                + MobilitoContract.Rides.COLUMN_NO_TAKER_COPASSENGERS + " INTEGER,"
                + MobilitoContract.Rides.COLUMN_VEHICLE_NUMBER + " TEXT,"
                + MobilitoContract.Rides.COLUMN_VEHICLE_MODEL + " TEXT,"
                + MobilitoContract.Rides.COLUMN_NO_SEATS + " INTEGER,"
                + MobilitoContract.Rides.COLUMN_START_TIME + " TEXT,"
                + MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT + " INTEGER,"
                + MobilitoContract.Rides.COLUMN_START_LOCATION + " INTEGER,"
                + MobilitoContract.Rides.COLUMN_END_LOCATION + " INTEGER,"
                + MobilitoContract.Rides.COLUMN_BOOKED + " BOOLEAN,"
                + "FOREIGN KEY (" + MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME + ") REFERENCES "
                + MobilitoContract.Users.TABLE_NAME + "(" + MobilitoContract.Users.COLUMN_USERNAME + "),"
                + "FOREIGN KEY (" + MobilitoContract.Rides.COLUMN_TAKER_USERNAME + ") REFERENCES "
                + MobilitoContract.Users.TABLE_NAME + "(" + MobilitoContract.Users.COLUMN_USERNAME + "),"
                + "FOREIGN KEY (" + MobilitoContract.Rides.COLUMN_START_LOCATION + ") REFERENCES "
                + MobilitoContract.Locations.TABLE_NAME + "(" + MobilitoContract.Locations.COLUMN_LOCATION_ID + "),"
                + "FOREIGN KEY (" + MobilitoContract.Rides.COLUMN_END_LOCATION + ") REFERENCES "
                + MobilitoContract.Locations.TABLE_NAME + "(" + MobilitoContract.Locations.COLUMN_LOCATION_ID + ")"
                + ")";

        db.execSQL(users_create);
        db.execSQL(locations_create);
        db.execSQL(rides_create);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String users_drop = "DROP TABLE " + MobilitoContract.Users.TABLE_NAME;
        String locations_drop = "DROP TABLE " + MobilitoContract.Locations.TABLE_NAME;
        String rides_drop = "DROP TABLE " + MobilitoContract.Rides.TABLE_NAME;

        db.execSQL(users_drop);
        db.execSQL(locations_drop);
        db.execSQL(rides_drop);


        onCreate(db);
    }

}

