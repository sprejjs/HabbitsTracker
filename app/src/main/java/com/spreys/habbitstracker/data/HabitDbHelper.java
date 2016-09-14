package com.spreys.habbitstracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vspreys on 9/14/16.
 */
public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE = "INTEGER";
    private static final String DATABASE_NAME = "Habits.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitTrackerContract.HabitEntry.TABLE_NAME + " (" +
                    HabitTrackerContract.HabitEntry._ID + " INTEGER PRIMARY KEY," +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    HabitTrackerContract.HabitEntry.COLUMN_DAYS_COUNT + INTEGER_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitTrackerContract.HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //This method has been intentionally left empty. There is the first version of the database,
        //so no upgrade is required
    }

    public long createNewHabit(String habitName) {
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HabitTrackerContract.HabitEntry.COLUMN_NAME, habitName);
        values.put(HabitTrackerContract.HabitEntry.COLUMN_DAYS_COUNT, 0);

        // Insert the new row, returning the primary key value of the new row
        return db.insert(HabitTrackerContract.HabitEntry.TABLE_NAME, null, values);
    }

    public Cursor readAllHabits() {
        SQLiteDatabase db = getReadableDatabase();


        return db.query(
                HabitTrackerContract.HabitEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    public void removeAllHabits() {
        getWritableDatabase().execSQL(SQL_DELETE_ENTRIES);
    }

    public long updateHabit(String name, int daysCount) {
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(HabitTrackerContract.HabitEntry.COLUMN_DAYS_COUNT, daysCount);

        // Which row to update, based on the title
        String selection = HabitTrackerContract.HabitEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { name };

        return db.update(
                HabitTrackerContract.HabitEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
