package com.example.mobilito03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Locations {

    private int locationId;
    private String address;
    private double latitude;
    private double longitude;

    public Locations(int locationId, String address, double latitude, double longitude) {
        this.locationId = locationId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Locations getLocation(Context context, int locationId) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.query(
                MobilitoContract.Locations.TABLE_NAME,
                null,
                MobilitoContract.Locations.COLUMN_LOCATION_ID + "=?",
                new String[]{Integer.toString(locationId)},
                null,
                null,
                null
        );

        Locations location = null;
        if (cursor.moveToFirst()) {
            location = new Locations(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3));
        }

        db.close();
        return location;
    }

    public static long insertLocation(Context context,
                                      String address,
                                      double latitude,
                                      double longitude)
    {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MobilitoContract.Locations.COLUMN_ADDRESS, address);
        values.put(MobilitoContract.Locations.COLUMN_LATITUDE, latitude);
        values.put(MobilitoContract.Locations.COLUMN_LONGITUDE, longitude);

        long newRowId = db.insert(MobilitoContract.Locations.TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "locationId=" + locationId +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
