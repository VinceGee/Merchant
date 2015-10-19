package com.vhg.empire.merchant.Products;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vhg.empire.merchant.R;

import java.io.Serializable;

/**
 * Created by VinceGee on 9/11/2015.
 */
public class Product extends Fragment{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.product_tab,container,false);
        return v;
    }

    public Product(){}

    public String title;
    public Drawable productImage;
    public String description;
    public double price;
    public boolean selected;

   /* public static Product newInstance(String title, Drawable productImage, String description, double price) {
        Product product = new Product();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("productImage", (Serializable) productImage);
        args.putString("description", description);
        args.putDouble("price", price);
        product.setArguments(args);

        return product;

    }*/


    public Product(String title, Drawable productImage, String description,double price) {
        this.title = title;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
    }


}
