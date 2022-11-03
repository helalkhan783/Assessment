package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "User_Info";
    private static final int VERSION_NUMBER = 2;


    private static final String ID = "_id";
    private static final String USER_ID = "User_id";
    private static final String NAME = "Name";
    private static final String COUNTRY = "Country";
    private static final String CITY = "City";
    private static final String Language = "Language";
    private static final String RESUME = "Resume";
    private static final String DATE_OF_BIRTH = "Birthday";


    private static final String select_all = "SELECT * FROM " + TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Toast.makeText(context, "On Create is Called", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID + " VARCHAR(255) unique," + NAME + " VARCHAR(255),"
                    + COUNTRY + " VARCHAR(255)," + CITY + " VARCHAR(255)," + Language + " VARCHAR(255)," + RESUME + " VARCHAR(1000)," + DATE_OF_BIRTH + " VARCHAR(255));");
        } catch (Exception e) {
            //  Toast.makeText(context, "On Create" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // Toast.makeText(context, "On Upgrade is Called", Toast.LENGTH_SHORT).show();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            // Toast.makeText(context, "On Upgrade " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public long insertData(String userId, String name, String country, String city, String language, String resume, String dateOfBirth) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, userId);
        contentValues.put(NAME, name);
        contentValues.put(COUNTRY, country);
        contentValues.put(CITY, city);
        contentValues.put(Language, language);
        contentValues.put(RESUME, resume);
        contentValues.put(DATE_OF_BIRTH, dateOfBirth);

        long rowId = -1;
        try {
            rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
        return rowId;
    }

    public boolean updateData(String userId, String name, String country, String city, String language, String resume, String dateOfBirth ) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, userId);
        contentValues.put(NAME, name);
        contentValues.put(COUNTRY, country);
        contentValues.put(CITY, city);
        contentValues.put(Language, language);
        contentValues.put(RESUME, resume);
        contentValues.put(DATE_OF_BIRTH, dateOfBirth);

        sqLiteDatabase.update(TABLE_NAME, contentValues, USER_ID + " = ?", new String[]{userId});

        return true;
    }

    public int deleteData(String userId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, USER_ID + " = ?", new String[]{userId});
    }


    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME); //delete all rows in a table
        db.close();
    }

    public Cursor displayAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(select_all, null);
        return cursor;
    }

    public Cursor getSingleData(String userId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(select_all, new String[]{userId});
        return cursor;
    }


}
