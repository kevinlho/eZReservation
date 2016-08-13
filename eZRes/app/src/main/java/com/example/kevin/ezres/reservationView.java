package com.example.kevin.ezres;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class reservationView extends Fragment {
    Firebase root;
    ListView listReservation;
    reservationAdapter reservation_adapter;
    List dataList = new ArrayList();;

    public reservationView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_view, container, false);
        listReservation = (ListView)v.findViewById(R.id.listView_reservationView);

        reservation_adapter = new reservationAdapter(getActivity().getApplicationContext(),R.layout.listview_reservationview);
        listReservation.setAdapter(reservation_adapter);


        root = new Firebase("https://ezreservation.firebaseio.com/Reservation");

                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Reservation res = snapshot.getValue(Reservation.class);

                            if(Login.getUserUID().toLowerCase().contains("ezres")){
                                reservation_adapter.addKey(snapshot.getKey());
                                reservation_adapter.add(res);
                            }
                            else {
                                if(res.getUserID().toLowerCase().equals(Login.getUserUID().toLowerCase())){
                                    reservation_adapter.addKey(snapshot.getKey());
                                    reservation_adapter.add(res);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });

        listReservation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int selectedPosition = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final TextView warning = new TextView(getContext());
                warning.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                warning.setText("Delete Reservation?");
                builder.setView(warning);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Firebase itemRef = root.child(reservation_adapter.getKey(selectedPosition)).getRef();
                        itemRef.removeValue();
                        reservation_adapter.clearData();
                        reservation_adapter.notifyDataSetChanged();

                        Toast.makeText(getActivity().getBaseContext(), "Data Deleted!", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return false;
            }
        });

        return v;
    }

}
