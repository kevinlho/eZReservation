package com.example.kevin.ezres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 5/17/2016.
 */
public class reservationAdapter extends ArrayAdapter {

    public reservationAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class dataHandler{
        TextView peopleno;
        TextView user;
        TextView date;
        TextView time;
    }

    List reservationKey = new ArrayList();
    List reservationDisplay = new ArrayList();
    dataHandler handler;

    @Override
    public void add(Object object) {
        super.add(object);
        reservationDisplay.add(object);
    }

    public void addKey(String keyValue){
        reservationKey.add(keyValue);
    }

    @Override
    public int getCount() {
        return  this.reservationDisplay.size();
    }

    @Override
    public Object getItem(int position) {
        return this.reservationDisplay.get(position);
    }

    public String getKey(int position){
        return (String)reservationKey.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_reservationview, parent, false);
            handler = new dataHandler();
            handler.peopleno = (TextView)row.findViewById(R.id.displayreservation_peopleno);
            handler.user = (TextView)row.findViewById(R.id.displayreservation_user);
            handler.date = (TextView)row.findViewById(R.id.displayreservation_date);
            handler.time = (TextView)row.findViewById(R.id.displayreservation_time);
            row.setTag(handler);
        }
        else {
            handler = (dataHandler)row.getTag();
        }

        Reservation reservationDetail = (Reservation)this.getItem(position);
        handler.peopleno.setText(reservationDetail.getPeopleNo());
        handler.user.setText(reservationDetail.getUserID());
        handler.date.setText(reservationDetail.getDate());
        handler.time.setText(reservationDetail.getTime());

        return row;

    }

    public void clearData(){
        reservationDisplay.clear();
    }
}
