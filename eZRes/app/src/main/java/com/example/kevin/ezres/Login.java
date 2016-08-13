package com.example.kevin.ezres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class Login extends AppCompatActivity {
    EditText text_email;
    EditText text_password;

    Button button_login;
    Button button_register;

    Firebase root;
    public static String userUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_email = (EditText)findViewById(R.id.login_email);
        text_password = (EditText)findViewById(R.id.login_password);
        button_login = (Button)findViewById(R.id.login_button);
        button_register = (Button)findViewById(R.id.login_register);
        button_login.setEnabled(true);
        button_register.setEnabled(true);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_login.setEnabled(false);
                button_register.setEnabled(false);
                root = new Firebase("https://ezreservation.firebaseio.com/");
                root.authWithPassword(text_email.getText().toString(), text_password.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        setUserUID(text_email.getText().toString());
                        Intent loginSuccess = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(loginSuccess);
                        button_login.setEnabled(false);
                        button_register.setEnabled(false);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getBaseContext(), "Please check your email and password!", Toast.LENGTH_LONG).show();
                        button_login.setEnabled(true);
                    }
                });
            }
        });

        registerRedirect(button_register);
    }


    public void registerRedirect(Button register){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectRegister = new Intent(getBaseContext(),Register.class);
                startActivity(redirectRegister);
            }
        });
    }

    public static String getUserUID() {
        return userUID;
    }

    public static void setUserUID(String userUID) {
        Login.userUID = userUID;
    }
}
