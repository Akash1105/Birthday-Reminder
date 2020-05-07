package com.rishi.family.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rishi.family.model.BirthdayBeans;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    /*STATIC VARIABLES*/
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String sDatabaseName = "BirthdaysManager";

    // Contacts table name
    private static final String sTableBirthdays = "Birthdays";

    // Contacts Table Columns names
    private static final String sEntryId = "Id";
    private static final String sImage = "Image";
    private static final String sEntryName = "Name";
    private static final String sEntryYear = "Year";
    private static final String sEntryMonth = "Month";
    private static final String sEntryDayOfMonth = "DayOfMonth";


    public DatabaseHandler(Context context) {
        super(context, sDatabaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BIRTHDAYS_TABLE = "CREATE TABLE " + sTableBirthdays + "("
                + sEntryId + " INTEGER PRIMARY KEY," + sImage + " BLOB NOT NULL," + sEntryName + " TEXT,"
                + sEntryYear + " INTEGER," + sEntryMonth + " INTEGER," + sEntryDayOfMonth + " INTEGER " + ")";
        db.execSQL(CREATE_BIRTHDAYS_TABLE);

    }

    // Method for upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + sTableBirthdays);

        // Create tables again
        onCreate(db);
    }

    /*CRUD Operations*/

    // Adding new Birthday
    public void addBirthday(BirthdayBeans objectBirthdayBeans) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.e("Called", "Addbirthday");
        ContentValues values = new ContentValues();
        values.put(sImage, objectBirthdayBeans.getmByteArrayImage());
        values.put(sEntryName, objectBirthdayBeans.getmName()); // Entry Name
        values.put(sEntryYear, objectBirthdayBeans.getmYear()); // Entry Year
        values.put(sEntryMonth, objectBirthdayBeans.getmMonth()); // Entry Month
        values.put(sEntryDayOfMonth, objectBirthdayBeans.getmDayOfMonth()); // Entry DayOfMonth


        // Inserting Row
        db.insert(sTableBirthdays, null, values);
        db.close(); // Closing database connection
    }

    // Getting single birthday
    public BirthdayBeans getBirthday(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(sTableBirthdays, new String[]{sEntryId, sImage,
                        sEntryName, sEntryYear, sEntryMonth, sEntryDayOfMonth}, sEntryDayOfMonth + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BirthdayBeans objectBirthdayBeans = new BirthdayBeans(Integer.parseInt(cursor.getString(0)), cursor.getBlob(1),
                cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)));

        cursor.close();
        // return birthday
        return objectBirthdayBeans;
    }

    // Getting all birthdays
    public List<BirthdayBeans> getAllBirthdays() {
        List<BirthdayBeans> listBirthdays = new ArrayList<BirthdayBeans>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + sTableBirthdays;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(selectQuery, null);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BirthdayBeans objectBirthdayBeans = new BirthdayBeans();
                    objectBirthdayBeans.setmId(Integer.parseInt(cursor.getString(0)));
                    objectBirthdayBeans.setmByteArrayImage(cursor.getBlob(1));
                    objectBirthdayBeans.setmName(cursor.getString(2));
                    objectBirthdayBeans.setmYear(Integer.parseInt(cursor.getString(3)));
                    objectBirthdayBeans.setmMonth(Integer.parseInt(cursor.getString(4)));
                    objectBirthdayBeans.setmDayOfMonth(Integer.parseInt(cursor.getString(5)));


                    // Adding Entry to list
                    listBirthdays.add(objectBirthdayBeans);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();
        // return birthdays list
        return listBirthdays;
    }

    // Getting birthdays Count
    public int getBirthdaysCount() {
        String countQuery = "SELECT  * FROM " + sTableBirthdays;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Updating single birthday
    public int updateBirthday(BirthdayBeans objectBirthdayBeans) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(sImage, objectBirthdayBeans.getmByteArrayImage());
        values.put(sEntryName, objectBirthdayBeans.getmName());
        values.put(sEntryYear, objectBirthdayBeans.getmYear());
        values.put(sEntryMonth, objectBirthdayBeans.getmMonth());
        values.put(sEntryDayOfMonth, objectBirthdayBeans.getmDayOfMonth());

        int effectedRows;

        effectedRows = db.update(sTableBirthdays, values, sEntryId + " = ?",
                new String[]{String.valueOf(objectBirthdayBeans.getmId())});

        db.close();

        // updating row
        return effectedRows;


    }

    // Deleting single birthday
    public void deleteBirthday(BirthdayBeans objectBirthdayBeans) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(sTableBirthdays, sEntryId + " = ?",
                new String[]{String.valueOf(objectBirthdayBeans.getmId())});
        db.close();
    }

    /*public Cursor getUser() {
// Select All Query
        String selectQuery = "SELECT  * FROM " + sTableBirthdays;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }*/


}
