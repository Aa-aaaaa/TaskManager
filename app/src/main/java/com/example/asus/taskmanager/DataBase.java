package com.example.asus.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Maxim on 14.01.2018.
 */

public class DataBase extends SQLiteOpenHelper {
    private static final String db_name = "TASKSDATABASE";
    private static final String table_name = "TASKS";
    private static final String key_name = "NAME";
    private static final String key_date = "DATE";
    private static final String key_description = "DESCRIPTION";
    private static final String key_global_id = "GLOBALID";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DataBase";
    private Context context;

    private SQLiteDatabase db;

    public DataBase(Context context) {
        super(context, db_name, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + table_name +"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + key_name + " TEXT, " + key_date + " TEXT, " + key_description + " TEXT, "
                + key_global_id + " INTEGER);");
        Log.d(TAG, "Table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }
    public ArrayList<Task> getAllNotesFromDataBase()
    {
        ArrayList <Task> taskList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        boolean hasNextStep = cursor.moveToFirst();
        Log.d(TAG, "Taking notes from data base started");
        while (hasNextStep)
        {
            String name = cursor.getString(cursor.getColumnIndex(key_name));
            MyDate date = new MyDate();
            date.setTime(cursor.getString(cursor.getColumnIndex(key_date)));
            String description = cursor.getString(cursor.getColumnIndex(key_description));
            Long dataBaseId = cursor.getLong(cursor.getColumnIndex("_ID"));
            Long globalDataBaseId = cursor.getLong(cursor.getColumnIndex(key_global_id));
            Task task = new Task(name, date, description, dataBaseId);
            task.setGlobalDataBaseId(globalDataBaseId);
            taskList.add(task);
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
        newValues.put(key_global_id, task.getGlobalDataBaseId());
        task.setDataBaseId(db.insert(table_name, null, newValues));

        Log.d(TAG, "ID " + task.getDataBaseId() + " name: " + task.getName() + " " + task.getTime() + " " + task.getDescription() + " added");
    }
    public void updateTask(Task task)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(key_name, task.getName());
        newValues.put(key_date, task.getTime().toString());
        newValues.put(key_description,task.getDescription());
        newValues.put(key_global_id, task.getGlobalDataBaseId());
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
        task.setGlobalDataBaseId(cursor.getLong(cursor.getColumnIndex(key_global_id)));
        cursor.close();
        return task;
    }

    public void addAllToGlobalDB()
    {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name +
                " WHERE " + table_name + ".GLOBALID = -1", null);
        boolean hasNextStep = cursor.moveToFirst();
        while (hasNextStep)
        {
            String name = cursor.getString(cursor.getColumnIndex(key_name));
            MyDate date = new MyDate();
            date.setString(cursor.getString(cursor.getColumnIndex(key_date)));
            String description = cursor.getString(cursor.getColumnIndex(key_description));
            Long dataBaseId = cursor.getLong(cursor.getColumnIndex("_ID"));
            Long globalDataBaseId = cursor.getLong(cursor.getColumnIndex(key_global_id));
            Task task = new Task(name, date, description, dataBaseId);
            task.setGlobalDataBaseId(globalDataBaseId);
            FoneService.addTask(task, context);
            hasNextStep = cursor.moveToNext();
        }
    }
}
