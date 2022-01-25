package com.example.vlibrary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlibrary.adapters.BooksAdapter;
import com.example.vlibrary.dialog.ViewDialog;
import com.example.vlibrary.model.Fetch;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Search_result extends AppCompatActivity implements BooksAdapter.OnItemClickListener {
EditText edit_search;
//Button go_btn;
FirebaseFirestore db;
List<Fetch> list;
RecyclerView recyclerView;
BooksAdapter adapter;
String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        edit_search=findViewById(R.id.edit_search);
//        go_btn=findViewById(R.id.go_btn);
        edit_search.requestFocus();
        db=FirebaseFirestore.getInstance();
        list=new ArrayList<>();

        recyclerView=findViewById(R.id.recycler_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new BooksAdapter(list,this,this);
        getRecommended();
//        go_btn.setOnClickListener(view->{
//             query=edit_search.getText().toString();
//            getSearchResults(query);
//        });
        TextWatcher textWatcherAdapter=new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // query=edit_search.getText().toString();
              //  getSearchResults(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
                query=edit_search.getText().toString();
                getSearchResults(query);
            }
        };
        edit_search.addTextChangedListener(textWatcherAdapter);
        edit_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query=edit_search.getText().toString();
                getSearchResults(query);
                return true;
            }
            return false;
        });
    }

  public   void getSearchResults(String query) {
        list.clear();
db.collection("books")
        .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                   String title=document.get("title").toString();
                   if(title.startsWith(query) || title.endsWith(query) || title.equals(query) || title.contains(query) )
                   {
                       String id=document.getId();
                       String author=document.get("author").toString();
                       String url=document.get("url").toString();
                       String category=document.get("category").toString();
                       String isbn=document.get("isbN").toString();
                       Fetch book=new Fetch(title,author,isbn,category,url,id);
                       list.add(book);
                       recyclerView.setAdapter(adapter);
                   }

                }
            }
        });
    }
    private void getRecommended() {
        db.collection("books")
                .limit(10)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title=document.get("title").toString();
String id=document.getId();
                        String author=document.get("author").toString();
                        String url=document.get("url").toString();
                    String category=document.get("category").toString();
                    String isbn=document.get("isbN").toString();

                       Fetch book=new Fetch(title,author,isbn,category,url,id);
                        list.add(book);
                        recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void Onclick(int position, View v) {
        Fetch book=list.get(position);
        String title=book.getTitle();
        String autor=book.getAuthor();
        String isbn=book.getIsbN();
        String category=book.getCategory();
        String url=book.getUrl();
        String id=book.getId();
        ViewDialog alert = new ViewDialog();
        alert.showDialog(Search_result.this, title,autor,isbn,category,url,id);
    }
}