package com.vhg.empire.merchant.database;

import android.content.Context;
import android.database.Cursor;

import com.vhg.empire.merchant.Account.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VinceGee on 10/19/2015.
 */
public class ProductBackend extends ProductObject {
    public ProductBackend(Context context) {
        super(context);
    }

    /*public ArrayList<ProductDetailObject> dictionaryWords(){*/
    public List<ExpandableListAdapter.Item> dictionaryWords(){
        String query_products = "SELECT * FROM product ORDER BY p_name";
        String query_catergories = "SELECT * FROM categories ORDER BY c_id";

        Cursor cursor_products = this.getDbConnection().rawQuery(query_products, null);
        Cursor cursor_catergories = this.getDbConnection().rawQuery(query_catergories, null);

        //ArrayList<ProductDetailObject> wordTerms = new ArrayList<>();

        List<ExpandableListAdapter.Item> data = new ArrayList<>();


        //data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Apple"));

        if(cursor_catergories.moveToFirst()){
            do{
                String category = cursor_catergories.getString(cursor_catergories.getColumnIndexOrThrow("cat_name"));
                int cat_id = cursor_catergories.getInt(cursor_catergories.getColumnIndexOrThrow("c_id"));
                data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, category,
                        new ProductDetailObject(category,null,null,null)));

                if(cursor_products.moveToFirst()){
                    do{
                        String product = cursor_products.getString(cursor_products.getColumnIndexOrThrow("p_name"));
                        int cat_id_in_prod = cursor_products.getInt(cursor_products.getColumnIndexOrThrow("c_id"));
                        if(cat_id_in_prod == cat_id) {
                            data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, product,
                                    new ProductDetailObject(category,product,null,null)));
                        }
                    }while(cursor_products.moveToNext());
                }
            }while(cursor_catergories.moveToNext());
        }
        cursor_catergories.close();
        cursor_products.close();



        return data;
    }
}
