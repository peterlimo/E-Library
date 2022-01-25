package com.example.vlibrary.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.vlibrary.R;

public class AdminDashActivity extends AppCompatActivity {
LinearLayout open_add_book,ope_add_research,open_add_past;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        ope_add_research=findViewById(R.id.open_add_research);
        open_add_book=findViewById(R.id.open_add_book);
        open_add_past=findViewById(R.id.open_add_pastp);
        open_add_past.setOnClickListener(v->startActivity(new Intent(this,AdminAddPastp.class)));
        open_add_book.setOnClickListener(v->startActivity(new Intent(this,AdminAddBooks.class)));
        ope_add_research.setOnClickListener(v->startActivity(new Intent(this,AddResearchSites.class)));
    }
}