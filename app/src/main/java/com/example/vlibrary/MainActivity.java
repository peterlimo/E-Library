package com.example.vlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.vlibrary.student.ControlPage;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        }, 3000);
    }
    private void checkLogin() {
        if (pref==null)
        {
            pref=getSharedPreferences("user",MODE_PRIVATE);

            String username=pref.getString("email","");
            if (username!=null && !username.equals(""))
            {
                Intent intent=new Intent(getApplicationContext(), ControlPage.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent i=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(i);
                finish();
            }
        }
    }


}