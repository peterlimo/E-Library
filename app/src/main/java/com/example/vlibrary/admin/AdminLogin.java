package com.example.vlibrary.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.vlibrary.R;

public class AdminLogin extends AppCompatActivity {
Button admin_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        admin_login=findViewById(R.id.admin_login_button);
        admin_login.setOnClickListener(view->startActivity(new Intent(this,AdminDashActivity.class)));
    }

}