package com.team27.killddl.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.team27.killddl.R;

/**
 * Created by adithya on 10/18/18.
 */


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KillDDLTasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TASKS = String.format("CREATE TABLE %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER)",
            TaskContract.TABLE_TASKS,
            TaskContract.TaskColumns._ID,
            TaskContract.TaskColumns.DESCRIPTION,
            TaskContract.TaskColumns.IS_COMPLETE,
            TaskContract.TaskColumns.PRIORITY,
            TaskContract.TaskColumns.DUE_DATE
    );

    private final Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASKS);
        loadDemoTask(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE_TASKS);
        onCreate(db);
    }

    private void loadDemoTask(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskColumns.DESCRIPTION, mContext.getResources().getString(R.string.title_activity_create_task));
        values.put(TaskContract.TaskColumns.IS_COMPLETE, 0);
        values.put(TaskContract.TaskColumns.PRIORITY, 1);
        values.put(TaskContract.TaskColumns.DUE_DATE, Long.MAX_VALUE);

        db.insertOrThrow(TaskContract.TABLE_TASKS, null, values);
    }
}
