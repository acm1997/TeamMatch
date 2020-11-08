package com.example.teammatch.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.objects.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mRePassword;
    private ArrayList<Evento> MyEvents = new ArrayList<Evento>();
    private ArrayList<Evento> MyEventsPart = new ArrayList<Evento>();

    public static final int GO_TO_LOGIN_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mRePassword = findViewById(R.id.et_repassword);

        final Button submitRegisterButton = findViewById(R.id.btn_register);
        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String repassword = mRePassword.getText().toString();

                if(password.equals(repassword)){
                    Intent i = new Intent();
                    User.packageIntent(i, email, password, MyEvents, MyEventsPart);

                    finish();
                } else {
                     Snackbar.make(view, "Las contraseñas no coinciden", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        final ImageView botonIrLogin = findViewById(R.id.imagebuttonLeft);
        botonIrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivityForResult(intent2, GO_TO_LOGIN_REQUEST);
            }
        });
    }
}