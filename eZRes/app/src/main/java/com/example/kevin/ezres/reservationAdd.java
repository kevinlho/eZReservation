package com.example.kevin.ezres;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class reservationAdd extends Fragment {

    Firebase root;

    TextView noOfPeople;
    TextView time;
    TextView date;
    Button searchTime;
    Button searchDate;
    Button reserve;

    public reservationAdd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_add, container, false);

        noOfPeople = (EditText)v.findViewById(R.id.reservationNoPeople);
        time = (TextView)v.findViewById(R.id.reservationTime);
        date = (TextView)v.findViewById(R.id.reservationDate);
        searchTime = (Button)v.findViewById(R.id.searchTime);
        searchDate = (Button)v.findViewById(R.id.searchDate);
        reserve = (Button)v.findViewById(R.id.reserveSubmit);


        searchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservationTimeDialog timeDialog = new reservationTimeDialog();
                timeDialog.show(getFragmentManager(), "displayTime");
            }
        });

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservationDateDialog dateDialog = new reservationDateDialog();
                dateDialog.show(getFragmentManager(),"displayDate");
            }
        });


        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve.setEnabled(false);
                root = new Firebase("https://ezreservation.firebaseio.com/Reservation");

                String peopleNo = noOfPeople.getText().toString();
                String selectedDate = date.getText().toString();
                String selectedTime = time.getText().toString();
                String userName = Login.getUserUID();

                Map<String, String> addReservevation = new HashMap<String, String>();
                addReservevation.put("userID",userName);
                addReservevation.put("peopleNo",peopleNo);
                addReservevation.put("date",selectedDate);
                addReservevation.put("time",selectedTime);
                root.push().setValue(addReservevation, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            reserve.setEnabled(true);
                            System.out.println("Reservation could not be saved. " + firebaseError.getMessage());
                        } else {
                            Toast.makeText(getActivity(), "Reservation Added!", Toast.LENGTH_SHORT).show();
                            Intent goMain = new Intent(getContext(),MainActivity.class);
                            reserve.setEnabled(true);
                            startActivity(goMain);
                        }
                    }
                });






            }
        });
        return v;
    }









    public static class reservationDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(this.getContext(),this,year,month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(this.getContext(), dayOfMonth + "/" + monthOfYear +"/"+ monthOfYear, Toast.LENGTH_LONG).show();

            monthOfYear++;
            ((TextView) getActivity().findViewById(R.id.reservationDate)).setText(year + "-" + monthOfYear +"-"+ dayOfMonth);
        }
    }

    public static class reservationTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(this.getContext(),this,hour,minute,true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Toast.makeText(this.getContext(), hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
            if (minute<10){
                ((TextView) getActivity().findViewById(R.id.reservationTime)).setText(hourOfDay + ":0" + minute);
            }
            else {
                ((TextView) getActivity().findViewById(R.id.reservationTime)).setText(hourOfDay + ":" + minute);}
        }
    }


}
