package com.vhg.empire.merchant.product;

/**
 * Created by maditsha on 3/14/2016.
 */
import android.graphics.drawable.Drawable;

public class Product {

    public String id;
    public String title;
    public String productImage;
    public String description;
    public double price;
    public boolean selected;
    private int pos;
    private int quantity;

    public Product(String id,String title, String productImage/*Drawable productImage*/, String description,
                   double price) {
        this.title = title;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
        this.id=id;
    }
    public Product(String id,String title, String productImage/*Drawable productImage*/, String description,
                   double price,int pos,int quantity) {
        this.title = title;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
        this.id=id;
        this.pos = pos;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public int getPos() {
        return pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
