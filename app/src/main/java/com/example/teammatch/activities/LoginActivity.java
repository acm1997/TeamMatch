package com.example.teammatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.objects.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private ArrayList<Evento> mMyEvents = new ArrayList<Evento>();
    private ArrayList<Evento> mMyEventsPart = new ArrayList<Evento>();

    public static final int GO_TO_REGISTER_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);

        final Button submitLoginButton = findViewById(R.id.btn_login);
        submitLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                Intent i = new Intent();
                User.packageIntent(i, email, password, mMyEvents, mMyEventsPart);

                finish();
            }
        });

        final ImageView botonIrRegistro = findViewById(R.id.imagebuttonRight);
        botonIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent2, GO_TO_REGISTER_REQUEST);
            }
        });
    }
}