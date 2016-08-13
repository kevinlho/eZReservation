package com.example.kevin.ezres;

/**
 * Created by Kevin on 5/3/2016.
 */
public class order {
    int orderImage;
    String orderName;
    String orderPrice;
    String orderQuantity;

    public order(){

    }

    public order(int orderImage, String orderName, String orderPrice, String orderQuantity){
        this.setOrderImage(orderImage);
        this.setOrderName(orderName);
        this.setOrderPrice(orderPrice);
        this.setOrderQuantity(orderQuantity);
    }

    public int getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(int orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
