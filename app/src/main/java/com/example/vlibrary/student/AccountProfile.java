package com.example.vlibrary.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vlibrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountProfile extends AppCompatActivity {
    SharedPreferences pref;
    String username;
    FirebaseFirestore db;
    TextView tname,temail,tnumber,treg_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        pref = getSharedPreferences("user", MODE_PRIVATE);
        username = pref.getString("email", "");
        tname=findViewById(R.id.tv_name);
        temail=findViewById(R.id.tv_email);
        tnumber=findViewById(R.id.tv_no_of_books);
        treg_no=findViewById(R.id.tv_reg);
        db= FirebaseFirestore.getInstance();
        db.collection("users")
                .document(username)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        String name=documentSnapshot.get("username").toString();
                        String reg=documentSnapshot.get("regNo").toString();
                        String no_of_books=documentSnapshot.get("noOfBooks").toString();
                        String email=documentSnapshot.get("email").toString();
                        tname.setText(name);
                        temail.setText(email);
                        tnumber.setText(no_of_books);
                        treg_no.setText(reg);
                    }
                });
    }
}