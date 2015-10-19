package com.vhg.empire.merchant.Cart;

import android.content.res.Resources;

import com.vhg.empire.merchant.Products.Product;
import com.vhg.empire.merchant.R;

import java.util.List;
import java.util.Vector;

/**
 * Created by VinceGee on 9/13/2015.
 */
public class ShoppingCartHelper {
    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static List<Product> cart;

    public static List<Product> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Product>();
            catalog.add(new Product("Dead or Alive", res.getDrawable(R.drawable.image), "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
            catalog.add(new Product("Switch", res.getDrawable(R.drawable.image),"Switch by Chip Heath and Dan Heath", 24.99));
            catalog.add(new Product("Watchmen", res.getDrawable(R.drawable.image),"Watchmen by Alan Moore and Dave Gibbons", 14.99));
        }

        return catalog;
    }

    public static List<Product> getCart() {
        if(cart == null) {
            cart = new Vector<Product>();
        }

        return cart;
    }
}
