package com.example.kevin.ezres;

/**
 * Created by Kevin on 5/23/2016.
 */
public class orderForPush {
    String date;
    String price;
    String productName;
    String quantity;
    String userID;
    String type;

    public orderForPush(String productName, String userID, String date, String price, String quantity, String type) {
        this.productName = productName;
        this.userID = userID;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public orderForPush(){

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
