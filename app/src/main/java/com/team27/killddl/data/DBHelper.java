package com.team27.killddl.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.team27.killddl.TaskViewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by adithya on 10/18/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="killddl";
    private static final int DB_VER = 1;
    public static final String DB_TABLE="Tasks";

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String IS_COMPLETE = "is_complete";
    public static final String PRIORITY = "priority";
    public static final String DUE_DATE = "due_date";

    private static final String SQL_CREATE_TABLE_TASKS = String.format("CREATE TABLE %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s TEXT)",
            DB_TABLE,
            TaskContract.TaskColumns._ID,
            TaskContract.TaskColumns.NAME,
            TaskContract.TaskColumns.DESCRIPTION,
            TaskContract.TaskColumns.IS_COMPLETE,
            TaskContract.TaskColumns.PRIORITY,
            TaskContract.TaskColumns.DUE_DATE
    );

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(SQL_CREATE_TABLE_TASKS);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertNewTask(String name, String description, int priority, String date){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(DESCRIPTION, description);
        values.put(IS_COMPLETE, 0);
        values.put(PRIORITY, priority);
        values.put(DUE_DATE, date);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void insertNewTask(Task t){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,t.getName());
        values.put(DESCRIPTION, t.getDescription());
        values.put(IS_COMPLETE, t.isComplete());
        values.put(PRIORITY, t.getPriority());
        values.put(DUE_DATE, t.getDate());

        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,NAME + " = ?",new String[]{task});
        db.close();
    }

    public void deleteAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE);
        db.close();
    }

    public ArrayList<Task> getTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, DESCRIPTION, PRIORITY, DUE_DATE, IS_COMPLETE},
                null, null, null, null, TaskContract.DATE_SORT);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
            int priorityIndex = cursor.getColumnIndex(PRIORITY);
            int dateIndex = cursor.getColumnIndex(DUE_DATE);
            int complete = cursor.getColumnIndex(IS_COMPLETE);
            taskList.add(
                    new Task(cursor.getString(nameIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getInt(priorityIndex),
                            cursor.getString(dateIndex),
                            cursor.getInt(complete)
                    )
            );
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public ArrayList<Task> getTaskListByDate(String date) {
        String where = "due_date=date('"+date+"')";
        ArrayList<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, DESCRIPTION, PRIORITY, DUE_DATE, IS_COMPLETE},
                where, null, null, null, TaskContract.DATE_SORT);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
            int priorityIndex = cursor.getColumnIndex(PRIORITY);
            int dateIndex = cursor.getColumnIndex(DUE_DATE);
            int complete = cursor.getColumnIndex(IS_COMPLETE);
            taskList.add(
                    new Task(cursor.getString(nameIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getInt(priorityIndex),
                            cursor.getString(dateIndex),
                            cursor.getInt(complete))
            );
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public ArrayList<Task> getTaskListByPriority() {
        ArrayList<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, DESCRIPTION, PRIORITY, DUE_DATE, IS_COMPLETE},
                null, null, null, null, TaskContract.PRIORITY_SORT);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
            int priorityIndex = cursor.getColumnIndex(PRIORITY);
            int dateIndex = cursor.getColumnIndex(DUE_DATE);
            int complete = cursor.getColumnIndex(IS_COMPLETE);
            taskList.add(
                    new Task(cursor.getString(nameIndex),
                            cursor.getString(descriptionIndex),
                            cursor.getInt(priorityIndex),
                            cursor.getString(dateIndex),
                            cursor.getInt(complete))
            );
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public static String getDateString(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(calendar.getTime());
        return formattedDate;
    }

    //NOTE: CAN RETURN NULL
    public Task getTask(String name) { //or parameter of type task
        Task t = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, DESCRIPTION, PRIORITY, DUE_DATE, IS_COMPLETE},
                null, null, null, null, TaskContract.DATE_SORT);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
            int priorityIndex = cursor.getColumnIndex(PRIORITY);
            int dateIndex = cursor.getColumnIndex(DUE_DATE);
            int complete = cursor.getColumnIndex(IS_COMPLETE);
            if(cursor.getString(nameIndex).equals(name)) {
                t = new Task(cursor.getString(nameIndex),
                                cursor.getString(descriptionIndex),
                                cursor.getInt(priorityIndex),
                                cursor.getString(dateIndex),
                                cursor.getInt(complete));

            }
        }
        cursor.close();
        db.close();
        return t;
    }



    public int markComplete(String name){
        Task t = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, DESCRIPTION, PRIORITY, DUE_DATE},
                null, null, null, null, TaskContract.DATE_SORT);
        int success = 0;
        boolean occur = false;
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
            int priorityIndex = cursor.getColumnIndex(PRIORITY);
            int dateIndex = cursor.getColumnIndex(DUE_DATE);
            if(cursor.getString(nameIndex).equals(name)) {
                t = new Task(cursor.getString(nameIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getInt(priorityIndex),
                        cursor.getString(dateIndex),
                        1);

               // String s = "sfg";
                ContentValues cv  = new ContentValues();
                cv.put(IS_COMPLETE, 1);
                String[] args = {name};
                db.update(DB_TABLE, cv, NAME + " = ?", args);
                //cursor.close();
                //db.close();
                //return success;
            }
        }
        cursor.close();
        db.close();
        return success;
    }


}
