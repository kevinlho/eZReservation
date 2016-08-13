package com.example.kevin.ezres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;

/**
 * Created by Kevin on 4/26/2016.
 */
public class productAdapter extends ArrayAdapter {

    static class dataHandler{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productDetail;
    }

    List productList = new ArrayList();
    dataHandler handler;

    public productAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        productList.add(object);
    }

    @Override
    public int getCount() {
        return  this.productList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_product, parent, false);
            handler = new dataHandler();
            handler.productImage = (ImageView)row.findViewById(R.id.productImage);
            handler.productName = (TextView)row.findViewById(R.id.productName);
            handler.productPrice = (TextView)row.findViewById(R.id.productPrice);
            handler.productDetail = (TextView)row.findViewById(R.id.productDetail);
            row.setTag(handler);
        }
        else {
            handler = (dataHandler)row.getTag();
        }

        Product productDataDetails = (Product)this.getItem(position);
        handler.productImage.setImageResource(productDataDetails.getProductImage());
        handler.productName.setText(productDataDetails.getProductName());
        handler.productPrice.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Float.parseFloat(productDataDetails.getProductPrice())));
        handler.productDetail.setText(productDataDetails.getProductDetail());

        return row;
    }
}
