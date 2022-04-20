package com.example.mobilito03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Rides {

    private static final String TAG = "Rides";
    private int rideId;
    private Users provider;
    private Users taker;
    private int noProviderCopassengers;
    private int noTakerCopassengers;
    private String vehicleNumber;
    private String vehicleModel;
    private int noSeats;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int waitingTime;
    private int expectedAmount;
    private int amountPaid;
    private Locations startLocation;
    private Locations endLocation;
    private boolean booked;

    public Rides(int rideId, Users provider, int noProviderCopassengers, String vehicleNumber, String vehicleModel, int noSeats, LocalDateTime startTime, int expectedAmount, Locations startLocation, Locations endLocation, boolean booked) {
        this.rideId = rideId;
        this.provider = provider;
        this.noProviderCopassengers = noProviderCopassengers;
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.noSeats = noSeats;
        this.startTime = startTime;
        this.expectedAmount = expectedAmount;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.booked = booked;
    }

    public Rides(int rideId, Users taker, int noProviderCopassengers, int noTakerCopassengers, String vehicleNumber, String vehicleModel, int noSeats, LocalDateTime startTime, int expectedAmount, Locations startLocation, Locations endLocation, boolean booked) {
        this.rideId = rideId;
        this.taker = taker;
        this.noProviderCopassengers = noProviderCopassengers;
        this.noTakerCopassengers = noTakerCopassengers;
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.noSeats = noSeats;
        this.startTime = startTime;
        this.expectedAmount = expectedAmount;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.booked = booked;
    }

    public Rides(int rideId, int noProviderCopassengers, String vehicleNumber, String vehicleModel, int noSeats, LocalDateTime startTime, int expectedAmount, Locations startLocation, Locations endLocation, boolean booked) {
        this.rideId = rideId;
        this.noProviderCopassengers = noProviderCopassengers;
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.noSeats = noSeats;
        this.startTime = startTime;
        this.expectedAmount = expectedAmount;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.booked = booked;
    }

    public Rides(int rideId, Users provider, LocalDateTime startTime, int expectedAmount, boolean booked) {
        this.rideId = rideId;
        this.provider = provider;
        this.startTime = startTime;
        this.expectedAmount = expectedAmount;
        this.booked = booked;
    }

    public Rides(int rideId, Users provider, Users taker, int noProviderCopassengers, int noTakerCopassengers, String vehicleNumber, String vehicleModel, int noSeats, LocalDateTime startTime, LocalDateTime endTime, int waitingTime, int expectedAmount, int amountPaid, Locations startLocation, Locations endLocation, boolean booked) {
        this.rideId = rideId;
        this.provider = provider;
        this.taker = taker;
        this.noProviderCopassengers = noProviderCopassengers;
        this.noTakerCopassengers = noTakerCopassengers;
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.noSeats = noSeats;
        this.startTime = startTime;
        this.endTime = endTime;
        this.waitingTime = waitingTime;
        this.expectedAmount = expectedAmount;
        this.amountPaid = amountPaid;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.booked = booked;
    }

    public static Rides getRide(Context context, int rideId) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.query(
                MobilitoContract.Rides.TABLE_NAME,
                null,
                MobilitoContract.Rides.COLUMN_RIDE_ID + "=?",
                new String[]{Integer.toString(rideId)},
                null,
                null,
                null
        );

        Rides ride = null;
        if (cursor.moveToFirst()) {
            ride = new Rides(cursor.getInt(0),
                    Users.getUser(context, cursor.getString(1)),
                    Users.getUser(context, cursor.getString(2)),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    LocalDateTime.parse(cursor.getString(8)),
                    LocalDateTime.parse(cursor.getString(9)),
                    cursor.getInt(10),
                    cursor.getInt(11),
                    cursor.getInt(12),
                    Locations.getLocation(context, cursor.getInt(13)),
                    Locations.getLocation(context, cursor.getInt(14)),
                    cursor.getInt(15) != 0
            );
        }
        db.close();

        return ride;
    }


    public static Rides getRideNB(Context context, int rideId) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.query(
                MobilitoContract.Rides.TABLE_NAME,
                new String[]{MobilitoContract.Rides.COLUMN_RIDE_ID, MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS,
                        MobilitoContract.Rides.COLUMN_VEHICLE_NUMBER, MobilitoContract.Rides.COLUMN_VEHICLE_MODEL, MobilitoContract.Rides.COLUMN_NO_SEATS,
                        MobilitoContract.Rides.COLUMN_START_TIME, MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT, MobilitoContract.Rides.COLUMN_START_LOCATION,
                        MobilitoContract.Rides.COLUMN_END_LOCATION, MobilitoContract.Rides.COLUMN_BOOKED},
                MobilitoContract.Rides.COLUMN_RIDE_ID + "= ? ",
                new String[]{String.valueOf(rideId)},
                null,
                null,
                null
        );

        Rides ride = null;
        if (cursor.moveToFirst()) {
            ride = new Rides(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    LocalDateTime.parse(cursor.getString(5)),
                    cursor.getInt(6),
                    Locations.getLocation(context, cursor.getInt(7)),
                    Locations.getLocation(context, cursor.getInt(8)),
                    cursor.getInt(9) != 0
            );
        }
        db.close();

        return ride;
    }

    public static Rides getRideB(Context context, int rideId) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.query(
                MobilitoContract.Rides.TABLE_NAME,
                new String[]{MobilitoContract.Rides.COLUMN_RIDE_ID, MobilitoContract.Rides.COLUMN_TAKER_USERNAME,
                        MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS, MobilitoContract.Rides.COLUMN_NO_TAKER_COPASSENGERS,
                        MobilitoContract.Rides.COLUMN_VEHICLE_NUMBER, MobilitoContract.Rides.COLUMN_VEHICLE_MODEL, MobilitoContract.Rides.COLUMN_NO_SEATS,
                        MobilitoContract.Rides.COLUMN_START_TIME, MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT, MobilitoContract.Rides.COLUMN_START_LOCATION,
                        MobilitoContract.Rides.COLUMN_END_LOCATION},
                MobilitoContract.Rides.COLUMN_RIDE_ID + "= ? ",
                new String[]{String.valueOf(rideId)},
                null,
                null,
                null
        );

        Rides ride = null;
        if (cursor.moveToFirst()) {
            ride = new Rides(cursor.getInt(0),
                    Users.getUser(context, cursor.getString(1)),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    LocalDateTime.parse(cursor.getString(7)),
                    cursor.getInt(8),
                    Locations.getLocation(context, cursor.getInt(9)),
                    Locations.getLocation(context, cursor.getInt(10)),
                    cursor.getInt(11) != 0
            );
        }
        db.close();

        return ride;
    }

    public static Rides[] getRides(Context context, String providerUsername) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.query(
                MobilitoContract.Rides.TABLE_NAME,
                new String[]{MobilitoContract.Rides.COLUMN_RIDE_ID, MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME,
                    MobilitoContract.Rides.COLUMN_START_TIME, MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT,
                    MobilitoContract.Rides.COLUMN_BOOKED},
                MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME + "= ? ",
                new String[]{providerUsername},
                null,
                null,
                null
        );

        Rides[] rides = new Rides[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            rides[i++] = new Rides(cursor.getInt(0),
                    Users.getUser(context, cursor.getString(1)),
                    LocalDateTime.parse(cursor.getString(2)),
                    cursor.getInt(3),
                    cursor.getInt(4) != 0
            );
        }
        db.close();

        return rides;
    }

    public static Rides[] getRides(Context context, int copassengers, LocalDateTime startTime, int waitingTime, int amount) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        /*String select_sql = "SELECT *"
                + " FROM "
                + MobilitoContract.Rides.TABLE_NAME
                + " INNER JOIN " + MobilitoContract.Locations.TABLE_NAME + " AS LOC1 ON " + MobilitoContract.Rides.TABLE_NAME + "." + MobilitoContract.Rides.COLUMN_START_LOCATION + " = LOC1." + MobilitoContract.Locations.COLUMN_LOCATION_ID
                + " INNER JOIN " + MobilitoContract.Locations.TABLE_NAME + " AS LOC2 ON " + MobilitoContract.Rides.TABLE_NAME + "." + MobilitoContract.Rides.COLUMN_END_LOCATION + " = LOC2." + MobilitoContract.Locations.COLUMN_LOCATION_ID
                + " WHERE "
                + MobilitoContract.Rides.COLUMN_NO_SEATS + "-" + MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS + " >= " + copassengers
                + " AND " + "datetime(" + MobilitoContract.Rides.COLUMN_START_TIME + ") BETWEEN datetime(\"" + startTime + "\", \"-" + waitingTime + " minutes\") AND datetime(\"" + startTime + "\", \"+" + waitingTime + " minutes\")"
                + " AND " + MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT + " < " + amount;*/

        String select_sql = "SELECT "
                + MobilitoContract.Rides.COLUMN_RIDE_ID + ", " + MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME + ", "
                + MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS + ", " + MobilitoContract.Rides.COLUMN_VEHICLE_NUMBER + ", "
                + MobilitoContract.Rides.COLUMN_VEHICLE_MODEL + ", " + MobilitoContract.Rides.COLUMN_NO_SEATS + ", "
                + MobilitoContract.Rides.COLUMN_START_TIME + ", " + MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT + ", "
                + MobilitoContract.Rides.COLUMN_START_LOCATION + ", " + MobilitoContract.Rides.COLUMN_END_LOCATION + ", "
                + MobilitoContract.Rides.COLUMN_BOOKED
                + " FROM " + MobilitoContract.Rides.TABLE_NAME
                + " WHERE "
                + MobilitoContract.Rides.COLUMN_NO_SEATS + "-" + MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS + " >= " + copassengers
                + " AND " + "datetime(" + MobilitoContract.Rides.COLUMN_START_TIME + ") BETWEEN datetime(\"" + startTime + "\", \"-" + waitingTime + " minutes\") AND datetime(\"" + startTime + "\", \"+" + waitingTime + " minutes\")"
                + " AND " + MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT + " < " + amount
                + " AND " + MobilitoContract.Rides.COLUMN_BOOKED + " = 0";

        Cursor cursor = db.rawQuery(select_sql, null);

        Rides[] rides = new Rides[cursor.getCount()];

        int i = 0;
        while (cursor.moveToNext()) {
            rides[i++] = new Rides(
                    cursor.getInt(0),
                    Users.getUser(context, cursor.getString(1)),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    LocalDateTime.parse(cursor.getString(6)),
                    cursor.getInt(7),
                    Locations.getLocation(context, cursor.getInt(8)),
                    Locations.getLocation(context, cursor.getInt(9)),
                    cursor.getInt(10) != 0
            );
        }
        db.close();
        return rides;
    }

    public static long insertRide(Context context,
                                  Users provider,
                                  Users taker,
                                  int noProviderCopassengers,
                                  int noTakerCopassengers,
                                  String vehicleNumber,
                                  String vehicleModel,
                                  int noSeats,
                                  LocalDateTime startTime,
                                  LocalDateTime endTime,
                                  int waitingTime,
                                  int expectedAmount,
                                  int amountPaid,
                                  Locations startLocation,
                                  Locations endLocation,
                                  boolean booked)
    {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME, provider.getUsername());
        contentValues.put(MobilitoContract.Rides.COLUMN_TAKER_USERNAME, taker.getUsername());
        contentValues.put(MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS, noProviderCopassengers);
        contentValues.put(MobilitoContract.Rides.COLUMN_NO_TAKER_COPASSENGERS, noTakerCopassengers);
        contentValues.put(MobilitoContract.Rides.COLUMN_VEHICLE_NUMBER, vehicleNumber);
        contentValues.put(MobilitoContract.Rides.COLUMN_VEHICLE_MODEL, vehicleModel);
        contentValues.put(MobilitoContract.Rides.COLUMN_NO_SEATS, noSeats);
        contentValues.put(MobilitoContract.Rides.COLUMN_START_TIME, startTime.toString());
        contentValues.put(MobilitoContract.Rides.COLUMN_END_TIME, endTime.toString());
        contentValues.put(MobilitoContract.Rides.COLUMN_WAITING_TIME, waitingTime);
        contentValues.put(MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT, expectedAmount);
        contentValues.put(MobilitoContract.Rides.COLUMN_AMOUNT_PAID, amountPaid);
        contentValues.put(MobilitoContract.Rides.COLUMN_START_LOCATION, startLocation.getLocationId());
        contentValues.put(MobilitoContract.Rides.COLUMN_END_LOCATION, endLocation.getLocationId());
        contentValues.put(MobilitoContract.Rides.COLUMN_BOOKED, booked);

        long newRowId = db.insert(MobilitoContract.Rides.TABLE_NAME, null, contentValues);

        db.close();

        return newRowId;
    }

    public static long insertRide(Context context,
                                  Users provider,
                                  int noProviderCopassengers,
                                  String vehicleNumber,
                                  String vehicleModel,
                                  int noSeats,
                                  LocalDateTime startTime,
                                  int expectedAmount,
                                  long startLocationId,
                                  long endLocationId,
                                  boolean booked)
    {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MobilitoContract.Rides.COLUMN_PROVIDER_USERNAME, provider.getUsername());
        contentValues.put(MobilitoContract.Rides.COLUMN_NO_PROVIDER_COPASSENGERS, noProviderCopassengers);
        contentValues.put(MobilitoContract.Rides.COLUMN_VEHICLE_NUMBER, vehicleNumber);
        contentValues.put(MobilitoContract.Rides.COLUMN_VEHICLE_MODEL, vehicleModel);
        contentValues.put(MobilitoContract.Rides.COLUMN_NO_SEATS, noSeats);
        contentValues.put(MobilitoContract.Rides.COLUMN_START_TIME, startTime.toString());
        contentValues.put(MobilitoContract.Rides.COLUMN_EXPECTED_AMOUNT, expectedAmount);
        contentValues.put(MobilitoContract.Rides.COLUMN_START_LOCATION, startLocationId);
        contentValues.put(MobilitoContract.Rides.COLUMN_END_LOCATION, endLocationId);
        contentValues.put(MobilitoContract.Rides.COLUMN_BOOKED, booked);

        long newRowId = db.insert(MobilitoContract.Rides.TABLE_NAME, null, contentValues);

        db.close();

        return newRowId;
    }


    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public Users getProvider() {
        return provider;
    }

    public void setProvider(Users provider) {
        this.provider = provider;
    }

    public Users getTaker() {
        return taker;
    }

    public void setTaker(Users taker) {
        this.taker = taker;
    }

    public int getNoProviderCopassengers() {
        return noProviderCopassengers;
    }

    public void setNoProviderCopassengers(int noProviderCopassengers) {
        this.noProviderCopassengers = noProviderCopassengers;
    }

    public int getNoTakerCopassengers() {
        return noTakerCopassengers;
    }

    public void setNoTakerCopassengers(int noTakerCopassengers) {
        this.noTakerCopassengers = noTakerCopassengers;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public int getNoSeats() {
        return noSeats;
    }

    public void setNoSeats(int noSeats) {
        this.noSeats = noSeats;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(int expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Locations getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Locations startLocation) {
        this.startLocation = startLocation;
    }

    public Locations getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Locations endLocation) {
        this.endLocation = endLocation;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "Rides{" +
                "rideId=" + rideId +
                ", provider=" + provider +
                ", taker=" + taker +
                ", noProviderCopassengers=" + noProviderCopassengers +
                ", noTakerCopassengers=" + noTakerCopassengers +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", noSeats=" + noSeats +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", waitingTime=" + waitingTime +
                ", expectedAmount=" + expectedAmount +
                ", amountPaid=" + amountPaid +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", booked=" + booked +
                '}';
    }
}
