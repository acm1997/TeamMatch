package com.example.teammatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teammatch.R;
import com.example.teammatch.objects.User;
import com.example.teammatch.room_db.UserDAO;
import com.example.teammatch.room_db.UserDatabase;

public class EditUserActivity extends AppCompatActivity {

    private Button btn_deleteuser;
    private SharedPreferences preferences;
    public static final int GO_HOME_DELETE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_deleteuser = findViewById(R.id.btn_deleteuser);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        Long usuario_id = preferences.getLong("usuario_id", 0);
        String name = preferences.getString("username", null);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        btn_deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuario_id > 0 && name != null && email != null && password != null){
                    UserDatabase userdatabase = UserDatabase.getInstance(getApplicationContext());
                    UserDAO userdao = userdatabase.userDao();
                    User userdelete = new User(usuario_id, name, email, password);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            preferences.edit().clear().apply();
                            userdao.deleteUser(userdelete);
                            Intent intentdelete = new Intent(EditUserActivity.this, MainActivity.class);
                            startActivityForResult(intentdelete, GO_HOME_DELETE_REQUEST);
                        }
                    }).start();

                }

            }
        });
    }
}