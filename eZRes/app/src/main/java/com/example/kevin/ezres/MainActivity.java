package com.example.kevin.ezres;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolBar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();
        setDrawerLayout();
        setStartingPage();
        setPageNavigator();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Toolbar Setting
    private void setToolBar(){
        toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
    }

    //Navigation Drawer Setting
    private void setDrawerLayout(){
        drawerLayout = (DrawerLayout)findViewById(R.id.activitymain_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void setStartingPage(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainLayout,new fragment_home());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home Page");
    }

    private void setPageNavigator(){
        navigationView = (NavigationView)findViewById(R.id.navBar);
        View headerView = LayoutInflater.from(this).inflate(R.layout.navbar_header_layout, null);
        TextView LoginAs = (TextView) headerView.findViewById(R.id.navbarHeader_SignedName);
        LoginAs.setText("Login as : " + Login.getUserUID());

        navigationView.addHeaderView(headerView);
        navigationView.setCheckedItem(R.id.menu_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout,new fragment_home());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Main Menu");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_order_add:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout,new productView());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Product");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_order_view:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout,new shoppingcartView());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Shoppingcart");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.order_view:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout,new orderView());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Order History");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.addreserve_table:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout,new reservationAdd());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Reservation");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.reserve_table:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout,new reservationView());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Reservation History");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }
}
