package com.example.kevin.ezres;

import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by Kevin on 4/18/2016.
 */
public class FirebaseConnection extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
