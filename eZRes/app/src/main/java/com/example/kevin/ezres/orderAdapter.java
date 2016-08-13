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
 * Created by Kevin on 5/22/2016.
 */
public class orderAdapter extends ArrayAdapter {
    public orderAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class dataHandler{
        TextView date;
        TextView price;
        TextView productname;
        TextView quantity;
        TextView userID;
        TextView type;
    }

    List orderList = new ArrayList();
    dataHandler handler;


    @Override
    public void add(Object object) {
        super.add(object);
        orderList.add(object);
    }

    @Override
    public int getCount() {
        return  this.orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.orderList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_order, parent, false);
            handler = new dataHandler();
            handler.date = (TextView)row.findViewById(R.id.orderview_date);
            handler.price = (TextView)row.findViewById(R.id.orderview_price);
            handler.productname = (TextView)row.findViewById(R.id.orderview_productname);
            handler.quantity = (TextView)row.findViewById(R.id.orderview_quantity);
            handler.userID = (TextView)row.findViewById(R.id.orderview_username);
            handler.type = (TextView)row.findViewById(R.id.orderview_type);
            row.setTag(handler);
        }
        else {
            handler = (dataHandler)row.getTag();
        }

        orderForPush pushData = (orderForPush)this.getItem(position);
        handler.date.setText(pushData.getDate());
        handler.price.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Float.parseFloat(pushData.getPrice())));
        handler.productname.setText(pushData.getProductName());
        handler.quantity.setText(pushData.getQuantity());
        handler.userID.setText(pushData.getUserID());
        handler.type.setText(pushData.getType());

        return row;
    }
}
