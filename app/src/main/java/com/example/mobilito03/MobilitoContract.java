package com.example.mobilito03;

import android.provider.BaseColumns;

public final class MobilitoContract {

    private MobilitoContract() {}

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_USERNAME = "Username";
        public static final String COLUMN_FULL_NAME = "Full_Name";
        public static final String COLUMN_DATE_OF_BIRTH = "Date_Of_Birth";
        public static final String COLUMN_GENDER = "Gender";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_MOBILE = "Mobile";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_RATING = "Rating";
    }

    public static class Rides implements BaseColumns {
        public static final String TABLE_NAME = "Rides";
        public static final String COLUMN_RIDE_ID = "Ride_Id";
        public static final String COLUMN_PROVIDER_USERNAME = "Provider_Username";
        public static final String COLUMN_TAKER_USERNAME = "Taker_Username";
        public static final String COLUMN_NO_PROVIDER_COPASSENGERS = "No_Provider_Copassengers";
        public static final String COLUMN_NO_TAKER_COPASSENGERS = "No_Taker_Copassengers";
        public static final String COLUMN_VEHICLE_NUMBER = "Vehicle_Number";
        public static final String COLUMN_VEHICLE_MODEL = "Vehicle_Model";
        public static final String COLUMN_NO_SEATS = "No_Seats";
        public static final String COLUMN_START_TIME = "Start_Time";
        public static final String COLUMN_EXPECTED_AMOUNT = "Expected_Amount";
        public static final String COLUMN_START_LOCATION = "Start_Location";
        public static final String COLUMN_END_LOCATION = "End_Location";
        public static final String COLUMN_BOOKED = "Booked";
    }

    public static class Locations implements BaseColumns {
        public static final String TABLE_NAME = "Locations";
        public static final String COLUMN_LOCATION_ID = "Location_Id";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_LATITUDE = "Latitude";
        public static final String COLUMN_LONGITUDE = "Longitude";
    }

}
