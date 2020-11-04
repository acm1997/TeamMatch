package com.example.teammatch;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    //Lista de Eventos del usuario
    private final static ArrayList<Evento> myEvents = new ArrayList<Evento>();

 //   private String mUsername = new String();
 //   private String mPassword = new String();
    //De momento vamos a crear un usuario estático
    private String mUsername = "Jose Luis";
    private String mPassword = "mongolito";
    private ArrayList<Evento> mMyEvents = new ArrayList<Evento>();

    User(String mUsername, String mPassword, ArrayList<Evento> mMyEvents) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mMyEvents = mMyEvents;
    }

    User(Intent intent){
        mUsername = intent.getStringExtra(User.USERNAME);
        mPassword = intent.getStringExtra(User.PASSWORD);
        mMyEvents = (ArrayList<Evento>) intent.getSerializableExtra("myEvents");
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public ArrayList<Evento> getmMyEvents() {
        return mMyEvents;
    }

    public void setmMyEvents(ArrayList<Evento> mMyEvents) {
        this.mMyEvents = mMyEvents;
    }

    public static void packageIntent(Intent intent, String mUsername, String mPassword, ArrayList<Evento> mMyEvents) {
        intent.putExtra(User.USERNAME, mUsername);
        intent.putExtra(User.PASSWORD, mPassword);
        intent.putExtra("MyEvents", mMyEvents);
    }
}
