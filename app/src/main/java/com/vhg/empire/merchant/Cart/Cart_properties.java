package com.vhg.empire.merchant.Cart;

/**
 * Created by maditsha on 10/23/2015.
 */

import java.io.Serializable;

public class Cart_properties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private String desc;

    private boolean isSelected;


    private int qnty;

    public Cart_properties() {

    }

    public Cart_properties(String name, String desc) {

        this.name = name;
        this.desc = desc;

    }

    public Cart_properties(String name, String desc, int qnty, boolean isSelected) {

        this.name = name;
        this.desc = desc;
        this.isSelected = isSelected;
        this.qnty = qnty;

    }

    public int getQnty() {
        return qnty;
    }

    public void setQnty(int qnty) {
        this.qnty = qnty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
