package com.team27.killddl.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="login";
    private static final int DB_VER = 1;
    public static final String DB_TABLE="Users";

    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private static final String SQL_CREATE_TABLE_USERS = String.format("CREATE TABLE %s"
                    +" (%s TEXT, %s TEXT, %s TEXT)",
            DB_TABLE,
            UserContract.TaskColumns.NAME,
            UserContract.TaskColumns.USERNAME,
            UserContract.TaskColumns.PASSWORD
    );

   // private static final String SQL_CREATE_TABLE_USERS = String.format("create table %s (%s varchar(50) primary key, %s text, %s text",
     //       DB_TABLE, UserContract.TaskColumns.USERNAME, UserContract.TaskColumns.PASSWORD, UserContract.TaskColumns.NAME );



    public LoginDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(SQL_CREATE_TABLE_USERS);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertNewUser(String name, String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, USERNAME, PASSWORD},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int usernameIndex = cursor.getColumnIndex(USERNAME);

            if(cursor.getString(usernameIndex).equals(username)) {
                return false;
            }
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(USERNAME, username);
        values.put(PASSWORD, password);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return true;
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,USERNAME + " = ?",new String[]{username});
        db.close();
    }

    public void deleteAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE);
        db.close();
    }

    public User getUser(String username) { //or parameter of type task
        User u = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, USERNAME, PASSWORD},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int usernameIndex = cursor.getColumnIndex(USERNAME);
            int passwordIndex = cursor.getColumnIndex(PASSWORD);

            if(cursor.getString(usernameIndex).equals(username)) {
                u = new User(cursor.getString(nameIndex),
                        cursor.getString(usernameIndex),
                        cursor.getString(passwordIndex));

            }
        }
        cursor.close();
        db.close();
        return u;
    }

    public String verifyUser(String username, String password) { //or parameter of type task
        User u = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{NAME, USERNAME, PASSWORD},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(NAME);
            int usernameIndex = cursor.getColumnIndex(USERNAME);
            int passwordIndex = cursor.getColumnIndex(PASSWORD);

            if(cursor.getString(usernameIndex).equals(username)) {
                if(cursor.getString(passwordIndex).equals(password)){
                    return "valid";
                }
                else{
                    return "invalid";
                }

            }
        }

        cursor.close();
        db.close();
        return "nonexistent";
    }


}
