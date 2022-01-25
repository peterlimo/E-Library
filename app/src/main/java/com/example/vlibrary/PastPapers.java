package com.example.vlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlibrary.adapters.PastPAdapter;
import com.example.vlibrary.model.GetPpData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PastPapers extends AppCompatActivity implements PastPAdapter.OnItemClickListener{
RecyclerView recyclerView;
List<GetPpData> list;
PastPAdapter adapter;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_papers);
        recyclerView=findViewById(R.id.ppRecycler);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new PastPAdapter(list,this,this);
        db=FirebaseFirestore.getInstance();

        db.collection("pastpapers").addSnapshotListener((value, error) -> {
            if (error!=null)
            {
                return;
            }
            for (QueryDocumentSnapshot doc:value)
            {
                String title=doc.getId();
                String type=doc.getString("type");
                populateData(title,type);
            }
        });
    }

    private void populateData(String title,String type) {
        db.collection("pastpapers").document(title).collection("urls").limit(1)
                .addSnapshotListener((value, error) -> {
                    if (error!=null){
                        return;
                    }
                    for (DocumentSnapshot doc: value)
                    {
                        if (doc.exists())
                        {
                            String url=doc.getString("url");
                            GetPpData data=new GetPpData(title,type,url);
                            list.add(data);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    @Override
    public void Onclick(int position, View v) {
        GetPpData data=list.get(position);
        Toast.makeText(this, data.getTitle(), Toast.LENGTH_SHORT).show();
    }
}