package com.vhg.empire.merchant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.vhg.empire.merchant.Products.Product;

/**
 * Created by VinceGee on 10/19/2015.
 */
public class ProductDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAMES = "dealer";
    private static final int DATABASE_VERSION = 1;
    //private final Context myContext;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
       // this.myContext = context;
    }

   /* @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion){
            myContext.deleteDatabase(DATABASE_NAMES);
        }
    }*/
}
