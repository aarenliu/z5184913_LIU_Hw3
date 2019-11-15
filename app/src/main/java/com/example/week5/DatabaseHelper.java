package com.example.week5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import java.util.ArrayList;

    public class DatabaseHelper extends SQLiteOpenHelper {

        private String TABLE_NAME = "Cat";

        private String C2 = "tv_name";


        public DatabaseHelper(Context context){
            super(context,"Cat", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            String createOrderTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY NOT NULL , " + C2 + " REAL)";
            sqLiteDatabase.execSQL( createOrderTable );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void addData(String tv_name){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(C2, tv_name);


            // Inserting Row
            db.insert(TABLE_NAME, null, values);
            db.close();
        }

        public ArrayList<catpage_detail> getdata() {
            ArrayList<catpage_detail> orderRecord = new ArrayList<catpage_detail>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                //    catpage_detail r = new catpage_detail(cursor.(1)); //cant debug here
                    // Adding order to list
                 //   orderRecord.add(r); //cant debug here
                } while (cursor.moveToNext());
            }

            // return order list
            return orderRecord;
        }


//fav btn
        public void addfav (String cat_name){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = String.format("DELETE FROM CatList");
            db.execSQL(query);

        }





}
