package com.example.kevin.ezres;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class orderView extends Fragment {

    Firebase firebaseConnection;
    ListView orderList;
    orderAdapter orderAdapter;
    List dataList = new ArrayList();;

    public orderView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_view, container, false);
        orderList = (ListView)v.findViewById(R.id.listView_orderView);
        orderAdapter = new orderAdapter(getActivity().getApplicationContext(),R.layout.listview_order);

        orderList.setAdapter(orderAdapter);

        firebaseConnection = new Firebase("https://ezreservation.firebaseio.com/Order");

        firebaseConnection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    orderForPush res = snapshot.getValue(orderForPush.class);
                    if (Login.getUserUID().contains("ezres")) {
                        orderAdapter.add(res);
                    } else {
                        if (res.getUserID().toLowerCase().equals(Login.getUserUID().toLowerCase())) {
                            orderAdapter.add(res);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        return v;
    }

}
