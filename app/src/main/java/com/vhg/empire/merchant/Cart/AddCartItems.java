package com.vhg.empire.merchant.Cart;

import java.util.ArrayList;

/**
 * Created by maditsha on 10/26/2015.
 */
public class AddCartItems {
    public static ArrayList<Cart_properties> studentList = new ArrayList<>();

    public ArrayList<Cart_properties> setCartItems(String name, String desc,int qnty, Boolean checked) {
        Cart_properties st = new Cart_properties(name, desc,qnty, checked);
        studentList.add(st);
        return studentList;

    }

    public ArrayList<Cart_properties> getCartItems() {

        return studentList;

    }
}
