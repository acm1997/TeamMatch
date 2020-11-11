package com.example.teammatch.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teammatch.R;
import com.example.teammatch.objects.User;
import com.example.teammatch.room_db.UserDAO;
import com.example.teammatch.room_db.UserDatabase;

public class EditUserActivity extends AppCompatActivity {

    private EditText eUsername;
    private EditText eEmail;
    private EditText ePassword;
    private EditText eRePassword;
    private Button btn_save;
    private Button btn_deleteuser;
    private SharedPreferences preferences;
    public static final int GO_HOME_DELETE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eUsername = findViewById(R.id.editTextTextPersonName);
        eEmail = findViewById(R.id.editTextTextEmailAddress);
        ePassword = findViewById(R.id.editTextTextPassword);
        eRePassword = findViewById(R.id.editTextTextPassword2);
        btn_save = findViewById(R.id.btn_save);
        btn_deleteuser = findViewById(R.id.btn_deleteuser);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        Long usuario_id = preferences.getLong("usuario_id", 0);
        String name = preferences.getString("username", null);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        //Mostrar datos actuales en los campos del usuario
        showDataUser(usuario_id, name, email, password);

        UserDatabase.getInstance(this);

        //Modificar los datos de un usuario
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String editusername = eUsername.getText().toString();
                final String editemail = eEmail.getText().toString();
                final String editpassword = ePassword.getText().toString();
                final String editRepassword = eRePassword.getText().toString();

                if(editpassword.equals(editRepassword)){
                    boolean validacion_editar = validarNuevosCampos(editusername, editemail, editpassword);
                    if(validacion_editar){
                        UserDatabase userdatabase = UserDatabase.getInstance(getApplicationContext());
                        UserDAO userdao = userdatabase.userDao();
                        User userupdate = new User(usuario_id, editusername, editemail, editpassword);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putLong("usuario_id", userupdate.getId());
                                editor.putString("username", userupdate.getUsername());
                                editor.putString("email", userupdate.getEmail());
                                editor.putString("password", userupdate.getPassword());
                                editor.commit();
                                userdao.update(userupdate);
                                String username = userupdate.getUsername();
                                startActivity(new Intent(EditUserActivity.this, MyProfileActivity.class).putExtra("username", username));
                            }
                        }).start();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Borrar un usuario
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
    public void showDataUser(Long usuario_id, String username, String email, String password){
        if(usuario_id > 0 && username != null && email != null && password != null){
            eUsername.setText(username);
            eEmail.setText(email);
            ePassword.setText(password);
        }
    }

    public boolean validarNuevosCampos(String username, String email, String password){
        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Rellena los campos vacíos", Toast.LENGTH_LONG).show();
            return false;
        } else if(username.length()<8 || password.length() < 8) {
            Toast.makeText(this, "Ingrese al menos un nombre de usuario y contraseña de 8 carácteres", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}