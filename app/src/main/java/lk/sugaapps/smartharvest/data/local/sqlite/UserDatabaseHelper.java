package lk.sugaapps.smartharvest.data.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_harvest.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_USERS = "users";
    public static final String TABLE_LOCATIONS = "locations";

    // User Columns
    public static final String COL_UID = "uid";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_LAT = "latitude";
    public static final String COL_LNG = "longitude";

    // Location Columns
    public static final String COL_LOC_ID = "id";
    public static final String COL_LOC_NAME = "name";
    public static final String COL_LOC_REGION = "region";
    public static final String COL_LOC_COUNTRY = "country";
    public static final String COL_LOC_LAT = "lat";
    public static final String COL_LOC_LNG = "lon";
    public static final String COL_LOC_URL = "url";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COL_UID + " TEXT PRIMARY KEY, " +
                    COL_NAME + " TEXT, " +
                    COL_EMAIL + " TEXT, " +
                    COL_MOBILE + " TEXT, " +
                    COL_LAT + " REAL, " +
                    COL_LNG + " REAL)";

    private static final String CREATE_TABLE_LOCATIONS =
            "CREATE TABLE " + TABLE_LOCATIONS + " (" +
                    COL_LOC_ID + " INTEGER PRIMARY KEY, " +
                    COL_LOC_NAME + " TEXT, " +
                    COL_LOC_REGION + " TEXT, " +
                    COL_LOC_COUNTRY + " TEXT, " +
                    COL_LOC_LAT + " REAL, " +
                    COL_LOC_LNG + " REAL, " +
                    COL_LOC_URL + " TEXT)";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
