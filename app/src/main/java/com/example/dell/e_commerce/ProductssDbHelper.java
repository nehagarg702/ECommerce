package com.example.dell.e_commerce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ProductssDbHelper extends SQLiteOpenHelper {

    public ProductssDbHelper(Context context) {
        super(context, "neha.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE ProductDetails( Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL, Description Text not null, Category Text not null, Price Number NOT NULL, Cart Integer not null, Image1 Blob, Image2 Blob, Quantity Integer, Seller Text);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ProductDetails");
        onCreate(db);
    }

}