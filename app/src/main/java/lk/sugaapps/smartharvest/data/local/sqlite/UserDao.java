package lk.sugaapps.smartharvest.data.local.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.remote.model.LocationResultResponse;

public class UserDao {

    private final UserDatabaseHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new UserDatabaseHelper(context);
    }

    public void insertOrUpdateUser(UserModel user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COL_UID, user.getUid());
        values.put(UserDatabaseHelper.COL_NAME, user.getName());
        values.put(UserDatabaseHelper.COL_EMAIL, user.getEmail());
        values.put(UserDatabaseHelper.COL_MOBILE, user.getMobile());
        values.put(UserDatabaseHelper.COL_LAT, user.getLocation().getLatitude());
        values.put(UserDatabaseHelper.COL_LNG, user.getLocation().getLongitude());

        db.insertWithOnConflict(UserDatabaseHelper.TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
      //  db.close();
    }
    public void insertLocation(LocationResultResponse location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COL_LOC_ID, 1);
        values.put(UserDatabaseHelper.COL_LOC_NAME, location.getName());
        values.put(UserDatabaseHelper.COL_LOC_REGION, location.getRegion());
        values.put(UserDatabaseHelper.COL_LOC_COUNTRY, location.getCountry());
        values.put(UserDatabaseHelper.COL_LOC_LAT, location.getLat());
        values.put(UserDatabaseHelper.COL_LOC_LNG, location.getLon());
        values.put(UserDatabaseHelper.COL_LOC_URL, location.getUrl());
        db.insert(UserDatabaseHelper.TABLE_LOCATIONS, null, values);
        //db.close();
    }
    public List<LocationResultResponse> getAllLocations() {
        List<LocationResultResponse> locations = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                UserDatabaseHelper.TABLE_LOCATIONS,
                null, // all columns
                null, // where clause
                null, // where args
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                LocationResultResponse location = new LocationResultResponse();
                location.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_ID)));
                location.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_NAME)));
                location.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_REGION)));
                location.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_COUNTRY)));
                location.setLat(cursor.getDouble(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_LAT)));
                location.setLon(cursor.getDouble(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_LNG)));
                location.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LOC_URL)));

                locations.add(location);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return locations;
    }

    public UserModel getUserById(String uid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                UserDatabaseHelper.TABLE_USERS,
                null,
                UserDatabaseHelper.COL_UID + "=?",
                new String[]{uid},
                null, null, null
        );

        UserModel user = null;
        if (cursor.moveToFirst()) {
            user = new UserModel(
                    cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_UID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_MOBILE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LAT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COL_LNG))
            );
        }
        cursor.close();
        db.close();
        return user;
    }
}

