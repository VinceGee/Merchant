package com.vhg.empire.merchant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.vhg.empire.merchant.Products.Product;

/**
 * Created by VinceGee on 10/19/2015.
 */
public class ProductObject {
    private static ProductDatabase dbHelper;
    private SQLiteDatabase db;

    public ProductObject(Context context) {
        dbHelper = new ProductDatabase(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDbConnection(){
        return this.db;
    }

    public void closeDbConnection(){
        if(this.db != null){
            this.db.close();
        }
    }
}
