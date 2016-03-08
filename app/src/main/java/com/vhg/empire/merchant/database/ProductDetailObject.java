package com.vhg.empire.merchant.database;

/**
 * Created by VinceGee on 10/19/2015.
 */
public class ProductDetailObject {

    public String category;
    private String p_name;
    private String p_desc;
    private String p_size;


    public ProductDetailObject(String cat,String p_name, String p_desc, String p_size) {
        this.category=cat;
        this.p_name = p_name;
        this.p_desc = p_desc;
        this.p_size = p_size;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_desc() {
        return p_desc;
    }

    public void setP_desc(String p_desc) {
        this.p_desc = p_desc;
    }

    public String getP_size() {
        return p_size;
    }

    public void setP_size(String p_size) {
        this.p_size = p_size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
