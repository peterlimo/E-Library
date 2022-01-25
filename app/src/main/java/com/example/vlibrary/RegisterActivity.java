package com.example.vlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlibrary.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    TextView open_login_page;
    Button reg_button;
    EditText username,email,reg,pas1,pas2;
    String usernameText,emailText,regText,pas1Text,pas2Text;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email_address);
        username=findViewById(R.id.username);
        reg=findViewById(R.id.reg_number);
        pas1=findViewById(R.id.password1);
        pas2=findViewById(R.id.password2);

        open_login_page=findViewById(R.id.open_login_page);
        reg_button=findViewById(R.id.register_btn);
        open_login_page.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(),LoginPage.class)));
        reg_button.setOnClickListener(view -> Validateuser());
    }

    private void Validateuser() {
        emailText=email.getText().toString().trim();
        regText=reg.getText().toString();
        pas1Text=pas1.getText().toString();
        pas2Text=pas2.getText().toString();
        usernameText=username.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Boolean isDataValid=true;
        if (!emailText.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(usernameText))
        {
            username.setError("please enter name");
            isDataValid=false;
        }
        else  if (TextUtils.isEmpty(emailText))
        {
            username.setError("please enter name");
            isDataValid=false;
        }
        else if (TextUtils.isEmpty(pas1Text))
        {
            username.setError("please enter name");
            isDataValid=false;
        }
        else if (TextUtils.isEmpty(pas2Text))
        {
            username.setError("please enter name");
            isDataValid=false;
        }
        else if (!pas1Text.equals(pas2Text))
        {
            Toast.makeText(getApplicationContext(), "Passwords Do not match", Toast.LENGTH_SHORT).show();
            isDataValid=false;
        }
        else if (isDataValid){
            RegisterUser();
        }
    }

    private void RegisterUser() {
        emailText=email.getText().toString();
        regText=reg.getText().toString();
        pas1Text=pas1.getText().toString();
        pas2Text=pas2.getText().toString();
        usernameText=username.getText().toString();
        User user=new User(usernameText,emailText,regText,pas1Text,0);
        db.collection("users").document(emailText)
                .set(user).addOnSuccessListener(
                aVoid -> Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show()
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}