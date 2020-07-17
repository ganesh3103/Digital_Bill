package com.biller.bill;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dbhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "NAME";
    public static final String col_3 = "Total_item";
    public static final String col_4 = "total";
    public static final String col_5 = "Name_of_items";


    public Dbhelper(Context context) {
        super( context, DATABASE_NAME, null, 1 );

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,Total_item TEXT,total TEXT,Name_of_items TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( sqLiteDatabase );
    }

    public boolean insertData(String name, String total_item,String total_ts,String nameof_items) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(  );
        contentValues.put(col_2,name);
        contentValues.put(col_3,total_item);
        contentValues.put(col_4,total_ts);
        contentValues.put(col_5,nameof_items);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public ArrayList<Student> getAllData()
    {
        ArrayList<Student> arrayList = new ArrayList<>(  );
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        while (res.moveToNext())
        {


            int id = res.getInt( 0 );

            String name = res.getString( 1 );
            String surname = res.getString( 2 );
            Student student = new Student( id,name,surname );
            arrayList.add( student );
        }

        return arrayList;
    }
    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete( TABLE_NAME,"ID = ?",new String[] {id} );

    }
}

