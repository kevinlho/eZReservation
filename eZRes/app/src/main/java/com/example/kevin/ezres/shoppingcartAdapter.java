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

/**
 * Created by Kevin on 5/1/2016.
 */
public class shoppingcartAdapter extends ArrayAdapter{

    public shoppingcartAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class dataHandler{
        ImageView orderImage;
        TextView orderName;
        TextView orderPrice;
        TextView orderQuantity;
    }

    List orderList = new ArrayList();
    dataHandler handler;
    List shoppingcartKey = new ArrayList();


    @Override
    public void add(Object object) {
        super.add(object);
        orderList.add(object);
    }

    public void addKey(String keyValue){
        shoppingcartKey.add(keyValue);
    }

    @Override
    public int getCount() {
        return this.orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.orderList.get(position);
    }

    public String getKey(int position){
        return (String)shoppingcartKey.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_shoppingcart, parent, false);
            handler = new dataHandler();
            handler.orderImage = (ImageView)row.findViewById(R.id.orderImage);
            handler.orderName = (TextView)row.findViewById(R.id.orderName);
            handler.orderPrice = (TextView)row.findViewById(R.id.orderPrice);
            handler.orderQuantity = (TextView)row.findViewById(R.id.orderQuantity);
            row.setTag(handler);
        }
        else {
            handler = (dataHandler)row.getTag();
        }

        order orderDataDetails = (order)this.getItem(position);
        handler.orderImage.setImageResource(orderDataDetails.getOrderImage());
        handler.orderName.setText(orderDataDetails.getOrderName());
        handler.orderPrice.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Float.parseFloat(orderDataDetails.getOrderPrice())));
        handler.orderQuantity.setText(orderDataDetails.getOrderQuantity());

        return row;
    }

    public void clearData(){
        orderList.clear();
    }
}
