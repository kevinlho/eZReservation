package com.example.kevin.ezres;

import android.widget.TextView;

/**
 * Created by Kevin on 4/26/2016.
 */
public class Product {
    private int productImage;
    private String productName;
    private String productPrice;
    private String productDetail;

    public Product(){

    }

    public Product(int productImage,String productName, String productPrice, String productDetail){
        this.setProductImage(productImage);
        this.setProductName(productName);
        this.setProductPrice(productPrice);
        this.setProductDetail(productDetail);
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }
}
