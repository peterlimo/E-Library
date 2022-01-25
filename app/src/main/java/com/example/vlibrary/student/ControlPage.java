package com.example.vlibrary.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vlibrary.GoogleSearch;
import com.example.vlibrary.LoginPage;
import com.example.vlibrary.PastPapers;
import com.example.vlibrary.R;
import com.example.vlibrary.ResearchSites;
import com.example.vlibrary.Search_result;
import com.google.android.material.card.MaterialCardView;

public class ControlPage extends AppCompatActivity {
    SharedPreferences pref;
    EditText open_search;
    ImageView logout_btn;
    String username;
    TextView txt_user;
    AlertDialog.Builder builder;
    MaterialCardView search_online,past_papers,research_sites,open_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_page);
        open_search = findViewById(R.id.open_search);
        open_profile = findViewById(R.id.open_profile);
        search_online=findViewById(R.id.search_online);
        past_papers=findViewById(R.id.past_papers);
        research_sites=findViewById(R.id.research_sites);
        txt_user=findViewById(R.id.txt_user);
        logout_btn=findViewById(R.id.logout_btn);
        builder = new AlertDialog.Builder(this);
        CheckLogin();
        logout_btn.setOnClickListener(v -> logOutUser());
//opening activities
        open_search.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Search_result.class)));
        open_profile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AccountProfile.class)));
        search_online.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ViewBooksActivity.class)));
        past_papers.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PastPapers.class)));
        research_sites.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ResearchSites.class)));
    }

    private void logOutUser() {
        ControlPage.this.builder.setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        (dialog, which) -> {
                            pref = getSharedPreferences("user", MODE_PRIVATE);
                            pref.edit().clear().apply();
                            Intent i=new Intent(ControlPage.this, LoginPage.class);
                            startActivity(i);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                        }).setNegativeButton("No",
                (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = ControlPage.this.builder.create();
        alertDialog.setTitle("Confirm");
        alertDialog.show();

    }

    public void CheckLogin() {
            pref = getSharedPreferences("user", MODE_PRIVATE);
             username = pref.getString("email", "");
             txt_user.setText(username);
    }
}