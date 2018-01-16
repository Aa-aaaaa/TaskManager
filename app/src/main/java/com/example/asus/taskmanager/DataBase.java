package com.example.asus.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Maxim on 14.01.2018.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final String db_name = "TASKSDATABASE";
    public static final String table_name = "TASKS";
    public static final String key_name = "NAME";
    public static final String key_date = "DATE";
    public static final String key_description = "DESCRIPTION";
    public static final String key_id = "MYID";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = "DataBase";

    private int size;
    private SQLiteDatabase db;

    public int getSize()
    {
        return this.size;
    }

    public void incSize()
    {
        this.size++;
    }

    public DataBase(Context context) {
        super(context, db_name, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
        this.size = 0;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db.execSQL("CREATE TABLE " + table_name +"(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + key_name + " TEXT, " + key_date + " TEXT, " + key_description + " TEXT, " + key_id + " INTEGER);");
        Log.d(TAG, "Table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(sqLiteDatabase);
    }
    public void takeAllNotesFomDataBase(TaskList taskList)
    {
        //Commemt to commit
        //Cursor cursor = sqLiteDatabase.query(table_name, null, null, null, null, null, null);
        taskList.clear();
        Cursor cursor = db.rawQuery("SELECT " + table_name + "." + key_name + " AS NAME, " + table_name + "." + key_date + " AS DATE, " + table_name + "." + key_description + " AS DESCRIPTION," + table_name + "." + key_id + " AS MYID FROM "  + table_name, null);
        boolean hasNextStep = cursor.moveToFirst();
        Log.d(TAG, "Taking notes from data base started");
        while (hasNextStep)
        {
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            MyDate date = new MyDate();
            date.setString((cursor.getString(cursor.getColumnIndex("DATE"))));
            String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
            int x = cursor.getInt(cursor.getColumnIndex("MYID"));
            Log.d(TAG, "Note" + name + " " + date + " " + description + " was made with id/t" + x);
            taskList.addTaskWithoutDB(new Task(name, date, description, x));
            if (this.size <= x)
                this.size = x + 1;
            this.incSize();
            hasNextStep = cursor.moveToNext();
        }
        cursor.close();
        Log.d(TAG, "Taking notes from data base finished");
    }

    public void addTask(Task task)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(key_name, task.getName());
        newValues.put(key_date, task.getTime().getString());
        newValues.put(key_description, task.getDescription());
        newValues.put(key_id, task.getId());
        db.insert(table_name, null, newValues);
        Log.d(TAG, "Note " + task.getName() + " " + task.getTime() + " " + task.getDescription() + " added");
    }
    /*public void updateTask(Task oldTask, Task newTask)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(key_name, newTask.getName());
        newValues.put(key_date, newTask.getTime().getString());
        newValues.put(key_description, newTask.getDescription());
        db.update(table_name, newValues, "_ID = ?", new String[] {String.valueOf(oldTask.getId())});
        Log.d(TAG, "Note" + oldTask.getName() + " " + oldTask.getTime() + " " + oldTask.getDescription() + " removed");
        Log.d(TAG, "Note" + newTask.getName() + " " + newTask.getTime() + " " + newTask.getDescription() + " added");
    }*/

    public void deleteTask(Task oldTask)
    {
        String[] ms = new String[] {Integer.toString(oldTask.getId())};
        //int a = db.delete(table_name, table_name + "._ID = ?", new String[]{"1"});
        int a = db.delete(table_name, table_name + "." + key_id +" = ?", ms);
        /*a += db.delete(table_name, table_name + "._ID = 2", null);
        a += db.delete(table_name, table_name + "._ID = 3", null);
        a += db.delete(table_name, table_name + "._ID = 4", null);
        a += db.delete(table_name, table_name + "._ID = ?", ms);*/
        Log.d(TAG, "Note " + oldTask.getName() + " " + oldTask.getTime() + " " + oldTask.getDescription() + " removed");
        Log.d(TAG, "" + a);
    }

    public void deleteAll()
    {
        onUpgrade(this.db, 0, 0);
    }
}
