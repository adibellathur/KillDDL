package com.team27.killddl.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class UserContract {

    public static final String TABLE_USERS = "users";

    //Unique authority string for the content provider
    public static final String CONTENT_AUTHORITY = "com.team27.killddl";


    //Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_USERS)
            .build();

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }



    public static final class TaskColumns implements BaseColumns {

        public static final String NAME = "name";

        public static final String USERNAME = "username";

        public static final String PASSWORD = "password";
    }

}
