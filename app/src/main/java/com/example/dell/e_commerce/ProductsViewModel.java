package com.example.dell.e_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductsViewModel {
    Context context;
    public ProductssDbHelper mFavHelper;

    public ProductsViewModel(Context context) {
        this.context = context;
        mFavHelper = new ProductssDbHelper(context);
    }

    private List<Products> mFavs;


    public List<Products> loadFavs() {
        List<Products> newFavs = new ArrayList<>();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor = db.query("ProductDetails",
                new String[]{"Id", "Name", "Description", "Category", "Price", "Cart", "Image1", "Image2", "Quantity", "Seller"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            newFavs.add(new Products(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(5), cursor.getLong(4), cursor.getBlob(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9)));
        }

        cursor.close();
        db.close();
        return newFavs;
    }

    public int totalsum() {
        mFavHelper = new ProductssDbHelper(context);
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        String strSQL = "Select sum(Price * Quantity) as a from ProductDetails where Cart=1";
        Cursor cursor = db.rawQuery(strSQL, null);
        int data = 5;
        while (cursor.moveToNext())
            data = cursor.getInt(0);
        db.close();
        return data;
    }

    public List<Products> loadcart() {
        mFavs = new ArrayList<>();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor = db.query("ProductDetails",
                new String[]{"Id", "Name", "Description", "Category", "Price", "Cart", "Image1", "Image2", "Quantity", "Seller"},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(5) == 1)
                mFavs.add(new Products(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(5), cursor.getLong(4), cursor.getBlob(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9)));
        }

        cursor.close();
        db.close();
        return mFavs;
    }

    public void addFav(String name, String Description, String category, Integer cart, long price, byte[] Image1, byte[] Image2, Integer quantity, String Seller) {
        mFavHelper = new ProductssDbHelper(context);
        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Description", Description);
        values.put("Category", category);
        values.put("Cart", cart);
        values.put("Price", price);
        values.put("Image1", Image1);
        values.put("Image2", Image2);
        values.put("Quantity", quantity);
        values.put("Seller", Seller);
        long id = db.insertWithOnConflict("ProductDetails",
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateFav(long Id, int Cart, int Quantity) {
        mFavHelper = new ProductssDbHelper(context);
        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        String strSQL = "UPDATE ProductDetails SET Cart = " + Cart + " WHERE Id = " + Id;
        db.execSQL(strSQL);
        db.close();
    }

    public void updateQuantity(long Id, int Quantity) {
        mFavHelper = new ProductssDbHelper(context);
        SQLiteDatabase db = mFavHelper.getWritableDatabase();
        String strSQL = "UPDATE ProductDetails SET Quantity = " + Quantity + " WHERE Id = " + Id;
        db.execSQL(strSQL);
        db.close();
    }

    public int cartcount() {
        mFavHelper = new ProductssDbHelper(context);
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        String strSQL = "Select sum(Cart) as a from ProductDetails";
        Cursor cursor = db.rawQuery(strSQL, null);
        int data = 5;
        while (cursor.moveToNext())
            data = cursor.getInt(0);
        db.close();
        return data;
    }

    public void removeFav(List<Products> favs, long id) {
        for (int i = 0; i < favs.size(); i++) {
            Products favou = favs.get(i);
            if (favou.mId == id)
                updateFav(id, 0, 1);
        }
    }

    public List<String> loadcategory() {
        List<String> mFavs = new ArrayList<String>();
        mFavs.add("All");
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select Distinct(Category) from ProductDetails", null);
        while (cursor.moveToNext()) {
            mFavs.add(cursor.getString(0));
        }
            cursor.close();
            db.close();
            return mFavs;
        }

    public List<Long> loadprice() {
        List<Long> mFavs = new ArrayList<Long>();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select min(Price), max(Price) from ProductDetails", null);
        while (cursor.moveToNext()) {
            mFavs.add(cursor.getLong(0));
            mFavs.add(cursor.getLong(1));
        }
        cursor.close();
        db.close();
        return mFavs;
    }

    public List<Products> loadfiltereddata(long min, long max, String[] array) {
        List<Products> mFavs = new ArrayList<Products>();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor;
        if(array[0].equals("All"))
         cursor = db.rawQuery("Select * from ProductDetails where Price >="+min+" and Price <="+max, null);
        else {
            String str="";
            for (int i=0;i<array.length;i++)
            {
                str=str.concat("'"+array[i]+"',");
            }
            str=str.substring(0,str.length()-1);
            cursor = db.rawQuery("Select * from ProductDetails where Price >=" + min + " and Price <=" + max + " and Category in ("+str+")" , null);

        }while (cursor.moveToNext()) {
            mFavs.add(new Products(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(5), cursor.getLong(4), cursor.getBlob(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9)));
        }
        cursor.close();
        db.close();
        return mFavs;
    }

    public List<Products> loadsearchdata(String str) {
        List<Products> mFavs = new ArrayList<Products>();
        SQLiteDatabase db = mFavHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("Select * from ProductDetails where Name like '%"+str+"%'", null);
        while (cursor.moveToNext()) {
            mFavs.add(new Products(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(5), cursor.getLong(4), cursor.getBlob(6), cursor.getBlob(7), cursor.getInt(8), cursor.getString(9)));
        }
        cursor.close();
        db.close();
        return mFavs;
    }
}
