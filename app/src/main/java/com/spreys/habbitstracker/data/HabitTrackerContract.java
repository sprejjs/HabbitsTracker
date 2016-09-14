package com.spreys.habbitstracker.data;

import android.provider.BaseColumns;

/**
 * Created by vspreys on 9/14/16.
 */
public final class HabitTrackerContract {
    /**
     * Private constructor to prevent the class from being initialised.
     */
    private HabitTrackerContract (){};

    public static final class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAYS_COUNT = "days_count";
    }
}
