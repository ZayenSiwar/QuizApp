package com.example.projetquizapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class    LoginActivity extends AppCompatActivity {


    EditText userNameEditText;
    EditText pass;
    MyDbHelper dbHelper;
    String log="admin";
    String psw="admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MyDbHelper(this);
        userNameEditText = findViewById(R.id.loginUsername);

        pass=(EditText) findViewById(R.id.loginPassword);

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(userNameEditText.getText().toString().trim(),
                        pass.getText().toString().trim());
            }
        });

        findViewById(R.id.loginCreateAccountLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });
    }


    public void loginUser(String username,String pass){
        if(username.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Veuillez remplir les deux champs !", Toast.LENGTH_SHORT).show();
        }else if(username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")){
            startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
            LoginActivity.this.finish();
        }else{
            int result = dbHelper.loginUser(username, pass);
            if(result == -1){
                Toast.makeText(this, " connexion échoué!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Bienvenue!", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("ProjetQuizApp",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER",username);
                editor.apply();
                startActivity(new Intent(LoginActivity.this,TopicActivity.class));
                LoginActivity.this.finish();
            }
        }
    }
}

