package com.example.kevin.ezres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity{

    Firebase root;

    private String email;
    private String password;
    private String name;
    private String contact;

    EditText text_email;
    EditText text_password;
    EditText text_name;
    EditText text_contact;
    Button button_register;


    public Register(){

    }

    public Register(String email, String password, String name, String contact) {
        this.contact = contact;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        text_email = (EditText) findViewById(R.id.register_email);
        text_password = (EditText) findViewById(R.id.register_password);
        text_name = (EditText)findViewById(R.id.register_name);
        text_contact = (EditText)findViewById(R.id.register_contact);
        button_register = (Button) findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_register.setEnabled(false);
                root = new Firebase("https://ezreservation.firebaseio.com/");
                String email = text_email.getText().toString();
                if (text_email.getText() != null || text_password.getText() != null) {
                    if (text_email.getText().length() > 4 || text_password.getText().length() > 4 && email.contains("@") && email.contains(".com")) {
                        final Register newUser = new Register(text_email.getText().toString(), text_password.getText().toString(),text_name.getText().toString(),text_contact.getText().toString());
                        root.createUser(newUser.getEmail(), newUser.getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                Firebase dbpushCustomer = new Firebase("https://ezreservation.firebaseio.com/Customer");
                                Map<String, String> CustomerData = new HashMap<String, String>();
                                CustomerData.put("email", text_email.getText().toString());
                                CustomerData.put("name", text_name.getText().toString());
                                CustomerData.put("contact", text_contact.getText().toString());

//                                Map<String, Map<String, String>> pushCustomer = new HashMap<String, Map<String, String>>();
//                                pushCustomer.put(text_email.getText().toString(),CustomerData);
                                dbpushCustomer.push().setValue(CustomerData, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                        if (firebaseError != null) {
                                            System.out.println("ERROR : "+firebaseError);
                                            button_register.setEnabled(true);
                                        }
                                        else if (firebaseError == null){
                                            Toast.makeText(getBaseContext(), "Successfully created user account " + newUser.getEmail(), Toast.LENGTH_LONG).show();
                                            Intent redirectLogin = new Intent(getBaseContext(), Login.class);
                                            startActivity(redirectLogin);
                                            button_register.setEnabled(true);
                                        }
                                    }

                                });


                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getBaseContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                button_register.setEnabled(true);
                            }
                        });
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Email and Password must contain at least 5 character!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

//    private void registerProcessor(){
//
//        root = new Firebase("https://ezreservation.firebaseio.com/");
//
//        root.createUser("emailid", "password", new Firebase.ValueResultHandler<Map<String, Object>>() {
//            @Override
//            public void onSuccess(Map<String, Object> result) {
//                Toast.makeText(getBaseContext(), "Successfully created user account emailid", Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onError(FirebaseError firebaseError) {
//                // there was an error
//            }
//        });
//
//    }
}


