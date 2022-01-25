package com.example.vlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlibrary.admin.AdminLogin;
import com.example.vlibrary.student.ControlPage;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {
EditText login_password,login_email;
FirebaseFirestore db;
SharedPreferences pref;
SharedPreferences.Editor editor;
    TextView open_register,open_admin_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        open_admin_login=findViewById(R.id.open_admin_login);
        open_register=findViewById(R.id.open_reg_page);
        db=FirebaseFirestore.getInstance();

        open_register.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),RegisterActivity.class)));
        open_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));

            }
        });
    }
    public void onLogin(View view) {
        Validateuser();
    }
    private void Validateuser() {
      String  email=login_email.getText().toString();
       String password=login_password.getText().toString();
        Boolean isDataValid=true;
        if (email=="" &&email.equals(""))
        {
            login_email.setError("Please enter Email");
            isDataValid=false;
        }
        if (password=="" && password.equals(""))
        {
            login_password.setError("Please enter password");
            isDataValid=false;
        }


        if(isDataValid) {
LoginUser();
        }
    }

    private void LoginUser() {
        String email=login_email.getText().toString();
        String password=login_password.getText().toString();

        db.collection("users").document(email)
                .get().addOnSuccessListener(
                documentSnapshot -> {
                    if(documentSnapshot.exists())
                    {
                        String dpassword=documentSnapshot.get("password").toString();
                        String name=documentSnapshot.get("username").toString();
                        if (dpassword.equals(password))
                        {
                            pref=getSharedPreferences("user",MODE_PRIVATE);
                            editor=pref.edit();
                            editor.putString("email",email);
                            editor.putString("name",name);
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), ControlPage.class);
                            startActivity(intent);
                            finish();
                            finishAffinity();
                        }
                        else
                            {
                            Toast.makeText(LoginPage.this, "Incorrect email or password!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(LoginPage.this, "Incorrect email or password!!", Toast.LENGTH_SHORT).show();
                    }

                }
        );
    }
}