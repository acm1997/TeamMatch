package com.example.teammatch.objects;

import android.content.Intent;

import java.util.ArrayList;

public class User {

    public final static String USERNAME = "username";
    public final static String PASSWORD = "password";
    //Lista de Eventos del usuario
    private final static ArrayList<Evento> myEvents = new ArrayList<Evento>(); //Lista de Eventos creados por el user
    private final static ArrayList<Evento> myEventsPart = new ArrayList<Evento>(); //Lista de eventos en los que participa el user

 //   private String mUsername = new String();
 //   private String mPassword = new String();
    //De momento vamos a crear un usuario estático
    private String mUsername = "Jose Luis";
    private String mPassword = "mongolito";
    private ArrayList<Evento> mMyEvents = new ArrayList<Evento>();
    private ArrayList<Evento> mMyEventsPart = new ArrayList<Evento>();

    User(String mUsername, String mPassword){
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mMyEvents = null;
        this.mMyEventsPart = null;
    }

    User(String mUsername, String mPassword, ArrayList<Evento> mMyEvents) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mMyEvents = mMyEvents;
        this.mMyEventsPart = mMyEventsPart;
    }

    User(Intent intent){
        mUsername = intent.getStringExtra(User.USERNAME);
        mPassword = intent.getStringExtra(User.PASSWORD);
        mMyEvents = (ArrayList<Evento>) intent.getSerializableExtra("myEvents");
        mMyEventsPart = (ArrayList<Evento>) intent.getSerializableExtra("myEventsPart");
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

    public ArrayList<Evento> getmMyEventsPart() {
        return mMyEventsPart;
    }

    public void setmMyEventsPart(ArrayList<Evento> mMyEventsPart) {
        this.mMyEventsPart = mMyEventsPart;
    }

    public static void packageIntent(Intent intent, String mUsername, String mPassword, ArrayList<Evento> mMyEvents, ArrayList<Evento> mMyEventsPart) {
        intent.putExtra(User.USERNAME, mUsername);
        intent.putExtra(User.PASSWORD, mPassword);
        intent.putExtra("MyEvents", mMyEvents);
        intent.putExtra("MyEventsPart", mMyEventsPart);
    }
}
