package com.example.asus.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maxim on 14.01.2018.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final String db_name = "TASKSDATABASE";
    public static final String table_name = "TASKS";
    public static final String key_name = "NAME";
    public static final String key_date = "DATE";
    public static final String key_description = "DESCRIPTION";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = "DataBase";

    private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, db_name, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + table_name +"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + key_name + " TEXT, " + key_date + " TEXT, " + key_description + " TEXT);");
        Log.d(TAG, "Table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(sqLiteDatabase);
    }
    public ArrayList<Task> getAllNotesFromDataBase()
    {
        ArrayList <Task> taskList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        boolean hasNextStep = cursor.moveToFirst();
        while (hasNextStep)
        {
            String name = cursor.getString(cursor.getColumnIndex(key_name));
            MyDate date = new MyDate();
            date.setTime(cursor.getString(cursor.getColumnIndex(key_date)));
            String description = cursor.getString(cursor.getColumnIndex(key_description));
            Long dataBaseId = cursor.getLong(cursor.getColumnIndex("_ID"));
            taskList.add(new Task(name, date, description, dataBaseId));
            hasNextStep = cursor.moveToNext();
        }
        cursor.close();
        return taskList;
    }

    public void addTask(Task task)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(key_name, task.getName());
        newValues.put(key_date, task.getTime().toString());
        newValues.put(key_description, task.getDescription());
        task.setDataBaseId(db.insert(table_name, null, newValues));

        Log.d(TAG, "ID " + task.getDataBaseId() + " name: " + task.getName() + " " + task.getTime() + " " + task.getDescription() + " added");
    }
    public void updateTask(Task task)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(key_name, task.getName());
        newValues.put(key_date, task.getTime().toString());
        newValues.put(key_description,task.getDescription());
        db.update(table_name, newValues, "_ID = ?", new String[] {Long.toString(task.getDataBaseId())});
    }

    public void deleteTask(long dataBaseId)
    {
        db.delete(table_name, "_ID = ?", new String[] {Long.toString(dataBaseId)});
    }

    public Task getTaskById(long dataBaseId)
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name +
                " WHERE " + table_name + "._ID = " + Long.toString(dataBaseId), null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        MyDate date = new MyDate();
        date.setTime(cursor.getString(cursor.getColumnIndex(key_date)));
        Task task = new Task(
                cursor.getString(cursor.getColumnIndex(key_name)),
                date,
                cursor.getString(cursor.getColumnIndex(key_description)),
                cursor.getLong(cursor.getColumnIndex("_ID")));
        cursor.close();
        return task;
    }
}
