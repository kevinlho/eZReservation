package com.example.kevin.ezres;

/**
 * Created by Kevin on 5/13/2016.
 */
public class Reservation {
    private String date;
    private String peopleNo;
    private String time;
    private String userID;

    public Reservation(){

    }

    public  Reservation(String userID, String peopleNo, String date, String time){
        this.setPeopleNo(peopleNo);
        this.setUserID(userID);
        this.setDate(date);
        this.setTime(time);
    }

//    public Reservation(String peopleNo, String selectedDate, String selectedTime, String userName){
//        this.setPeopleNo(peopleNo);
//        this.setSelectedDate(selectedDate);
//        this.setSelectedTime(selectedTime);
//        this.setUserName(userName);
//    }


    public String getPeopleNo() {
        return peopleNo;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserID() {
        return userID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPeopleNo(String peopleNo) {
        this.peopleNo = peopleNo;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
