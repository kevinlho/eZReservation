package com.example.kevin.ezres;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class shoppingcartView extends Fragment {
    int finalprice;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    ListView listOrder;
    shoppingcartAdapter orderAdapter;
    public static List<order> addOrder = new ArrayList<order>();


    TextView itemCount;
    TextView totalPrice;
    RadioGroup radioGroup;
    Button checkout;

    public shoppingcartView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viewRoot = inflater.inflate(R.layout.fragment_shoppingcart_view, container, false);
        itemCount = (TextView)viewRoot.findViewById(R.id.order_totalItem);
        totalPrice = (TextView)viewRoot.findViewById(R.id.order_totalPrice);
        checkout = (Button)viewRoot.findViewById(R.id.order_checkoutButton);
        listOrder = (ListView)viewRoot.findViewById(R.id.listView_shoppingcartView);
        radioGroup = (RadioGroup)viewRoot.findViewById(R.id.orderType);

        orderAdapter = new shoppingcartAdapter(getActivity().getApplicationContext(),R.layout.listview_shoppingcart);
        listOrder.setAdapter(orderAdapter);
        for (int i = 0; i < addOrder.size(); i++) {
            orderAdapter.add(addOrder.get(i));
            finalprice += Float.parseFloat(addOrder.get(i).getOrderPrice().replaceAll("[^0-9]",""));
        }

        itemCount.setText("Total Item : "+addOrder.size());
        totalPrice.setText("Total Price : "+String.valueOf(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(finalprice)));

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout.setEnabled(false);
                Firebase pushOrder = new Firebase("https://ezreservation.firebaseio.com/Order");
                String currentDate = df.format(c.getTime());

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioSelection = (RadioButton)viewRoot.findViewById(selectedId);
                for (int i = 0; i < addOrder.size(); i++) {
                    order addnewOrder = addOrder.get(i);

                    Map<String, String> pushnewOrder = new HashMap<String, String>();
                    pushnewOrder.put("userID", Login.getUserUID());
                    pushnewOrder.put("date", currentDate);
                    pushnewOrder.put("productName", addnewOrder.getOrderName());
                    pushnewOrder.put("quantity", addnewOrder.getOrderQuantity());
                    pushnewOrder.put("price", addnewOrder.getOrderPrice());
                    pushnewOrder.put("type", radioSelection.getText().toString());

                    pushOrder.push().setValue(pushnewOrder, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                checkout.setEnabled(true);
                            } else {
                                Toast.makeText(getActivity(), "Order Added!", Toast.LENGTH_SHORT).show();
                                Intent goMain = new Intent(getContext(), MainActivity.class);
                                checkout.setEnabled(true);
                                startActivity(goMain);
                            }
                        }
                    });
                }
            }
        });
        listOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                        Firebase firebaseOrder = new Firebase("https://ezreservation.firebaseio.com/Order");
                        Firebase itemRef = firebaseOrder.child(orderAdapter.getKey(selectedPosition)).getRef();
                        itemRef.removeValue();
                        orderAdapter.clearData();
                        orderAdapter.notifyDataSetChanged();

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
        return viewRoot;
    }

}
