package com.example.kevin.ezres;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
public class productView extends Fragment {

    public static List<order> addProductOrder = new ArrayList<>();
    ListView listProduct;
    productAdapter adapter;
    Firebase root;

    int price;
    int quantity;
    int totalprice;

//    int[] productImage = {R.drawable.ayam,R.drawable.ayam};
//    String[] productName = {"AyamGoreng","Abc"};
//    String[] productPrice = {"12.000","Abc"};
//    String[] productDetail = {"Crispy","Abc"};

    public productView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_product_view, container, false);

        listProduct = (ListView) viewRoot.findViewById(R.id.listView_productView);
        adapter = new productAdapter(getActivity().getApplicationContext(), R.layout.listview_product);
        listProduct.setAdapter(adapter);
//
//        for(int i=0;i<=productName.length-1;i++){
//            Product productitem = new Product(productImage[i],productName[i],productPrice[i],productDetail[i]);
//            adapter.add(productitem);
//
//        }
        root = new Firebase("https://ezreservation.firebaseio.com/Product");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Reservation Count" + dataSnapshot.getChildrenCount());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Product addNewProduct = snapshot.getValue(Product.class);
                    addNewProduct.setProductImage(R.drawable.ayam);
                    adapter.add(addNewProduct);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        productClick();

        return viewRoot;
    }

    private void productClick() {
        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Product Quantity");
                builder.setView(input);
                builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Product productDataDetails = (Product) adapter.getItem(position);
                        order newOrder = new order(productDataDetails.getProductImage(), productDataDetails.getProductName(), productDataDetails.getProductPrice(), input.getText().toString());
                        price = Integer.parseInt(newOrder.getOrderPrice());
                        quantity = Integer.parseInt(newOrder.getOrderQuantity());
                        totalprice = price * quantity;
                        newOrder.setOrderPrice(String.valueOf(totalprice));
                        shoppingcartView.addOrder.add(newOrder);
                        Toast.makeText(getActivity().getBaseContext(), "Product Added!", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }


}
