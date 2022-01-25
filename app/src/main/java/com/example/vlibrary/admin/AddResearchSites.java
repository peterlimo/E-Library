package com.example.vlibrary.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.vlibrary.R;
import com.example.vlibrary.model.Research;
import com.google.firebase.firestore.FirebaseFirestore;
public class AddResearchSites extends AppCompatActivity {
    FirebaseFirestore db;
    EditText edt_link,edt_title;
    Button send_link,paste_link;
    ClipboardManager clipboardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_research_sites);
        db=FirebaseFirestore.getInstance();
        edt_link=findViewById(R.id.edt_link);
        edt_title=findViewById(R.id.edt_title);
        send_link=findViewById(R.id.send_link);
        paste_link=findViewById(R.id.paste_link);
        clipboardManager =(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        paste_link.setOnClickListener(view->
        {
        ClipData pData = clipboardManager.getPrimaryClip();
        ClipData.Item item = pData.getItemAt(0);
        String txtpaste = item.getText().toString();
        edt_link.setText(txtpaste);
        });
        send_link.setOnClickListener(view->
        {
                    Research link=new Research(edt_title.getText().toString(),edt_link.getText().toString());
                    db.collection("researchsites")
                    .document().set(link)
                    .addOnSuccessListener(hhh->
                    {
                        Toast.makeText(this, "Research site added successfully!!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(cc->
                    {
                        Toast.makeText(this, "Failed add data,Please try again!!", Toast.LENGTH_SHORT).show();
                    });
        });
    }

}