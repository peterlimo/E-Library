package com.example.vlibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.vlibrary.adapters.ResearchAdapter;
import com.example.vlibrary.model.Research;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ResearchSites extends AppCompatActivity implements  ResearchAdapter.OnItemClickListener{
FirebaseFirestore db;
ResearchAdapter adapter;
List<Research> list;
RecyclerView recyclerView;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_sites);
        db=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        dialog=new ProgressDialog(this);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ResearchAdapter(list,this,this);

db.collection("researchsites")
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot doc:value)
                {
                    String  title=doc.get("title").toString();
                    String url=doc.get("url").toString();
                    Research research=new Research(title,url);
                    list.add(research);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }

//    @Override
//    public void Onclick(int position) {

//
//    }

    @Override
    public void Onclick(int position, View v) {
        Research item=list.get(position);
        String url=item.getUrl();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}