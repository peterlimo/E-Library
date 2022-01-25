package com.example.vlibrary.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlibrary.R;
import com.example.vlibrary.adapters.ViewBooksAdapter;
import com.example.vlibrary.model.Fetch;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewBooksActivity extends AppCompatActivity implements ViewBooksAdapter.OnItemClickListener{
RecyclerView recyclerView;
    SharedPreferences pref;
    String username;
    FirebaseFirestore db;
    List<Fetch> list;
    ViewBooksAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);
        recyclerView=findViewById(R.id.view_books_recycler);
        pref = getSharedPreferences("user", MODE_PRIVATE);
         username = pref.getString("email", "");
         db=FirebaseFirestore.getInstance();
         list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ViewBooksAdapter(list,this,this);
        getRecommended();
    }
    private void getRecommended() {
        db.collection("users")
                .document(username)
                .collection("borrowed")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title=document.get("title").toString();
                    String url=document.get("url").toString();
                    String isbn=document.get("isBn").toString();

                   Fetch book=new Fetch(title,"",isbn,"",url,"");
                    list.add(book);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void Onclick(int position, View v) {
        Fetch borrow=list.get(position);
        Toast.makeText(this, "Book:"+borrow.getTitle()+borrow.getIsbN(), Toast.LENGTH_SHORT).show();
    }
}