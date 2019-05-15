package com.itlx.vp.vpdroid.models;

/**
 * Created by OakSoe on 10/29/2016.
 */

public class SidemenuItem {

    String ItemName;
    int imgResID;

    public SidemenuItem(String itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

}
