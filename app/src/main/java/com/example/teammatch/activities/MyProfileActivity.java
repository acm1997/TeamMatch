package com.example.teammatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.teammatch.R;

public class MyProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        String username = getIntent().getStringExtra("USERNAME");
        if(username==null) username = "";

        TextView tVUsername = findViewById(R.id.tVUsername);
        tVUsername.setText(""+username);
    }
}