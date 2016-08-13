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


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class fragment_home extends Fragment {

    ListView hotproduct;
    ListView existingReservation;
    productAdapter product_adapter;
    reservationAdapter reservation_adapter;
    public fragment_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        hotproduct = (ListView) v.findViewById(R.id.home_listview_hotitem);
        existingReservation = (ListView) v.findViewById(R.id.home_listview_existingreservation);


        product_adapter = new productAdapter(getActivity().getApplicationContext(), R.layout.listview_product);
        hotproduct.setAdapter(product_adapter);
        Firebase productCatcher = new Firebase("https://ezreservation.firebaseio.com/Product");
        productCatcher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Product addNewProduct = snapshot.getValue(Product.class);
                    addNewProduct.setProductImage(R.drawable.ayam);
                    product_adapter.add(addNewProduct);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });




        reservation_adapter = new reservationAdapter(getActivity().getApplicationContext(),R.layout.listview_reservationview);
        existingReservation.setAdapter(reservation_adapter);
        Firebase reservationCatcher = new Firebase("https://ezreservation.firebaseio.com/Reservation");

        reservationCatcher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation res = snapshot.getValue(Reservation.class);

                    if (Login.getUserUID().toLowerCase().contains("ezres")) {
                        reservation_adapter.addKey(snapshot.getKey());
                        reservation_adapter.add(res);
                    } else {
                        if (res.getUserID().toLowerCase().equals(Login.getUserUID().toLowerCase())) {
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

        return v;
    }

}
