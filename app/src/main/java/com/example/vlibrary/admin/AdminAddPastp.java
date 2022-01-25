package com.example.vlibrary.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vlibrary.R;
import com.example.vlibrary.model.PastP;
import com.example.vlibrary.model.ppUrl;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AdminAddPastp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button chose_file,submit_pp;
TextView txt_no;
EditText pp_title;
Uri uri;
ProgressDialog dialog;
    ArrayList<Uri> list;
    FirebaseFirestore db;
    Spinner spinner;
    String[]  type = { "pdf", "image"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_pastp);
         list = new ArrayList<>();
        chose_file=findViewById(R.id.chose_file);
        submit_pp=findViewById(R.id.submit_pp);
        txt_no=findViewById(R.id.txt_no);
        pp_title=findViewById(R.id.pp_title);
        db=FirebaseFirestore.getInstance();
        spinner=findViewById(R.id.spinner);
        dialog=new ProgressDialog(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        chose_file.setOnClickListener(v->chooseFile());
        submit_pp.setOnClickListener(v->submitFiles());
    }

    private void submitFiles() {

    int upload_count;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        for (upload_count=0;upload_count<list.size();upload_count++)
        {
            Uri individualImage=list.get(upload_count);
            StorageReference  name=storageRef.child(individualImage.getLastPathSegment());

            name.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dialog.dismiss();
                            String url=uri.toString();
                            storeLink(url);
                        }
                    });
                }
            }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot task) {
                            double progress=(100.0 * task.getBytesTransferred())/task.getTotalByteCount();
                            int prog=(int)progress;
                            dialog.setMessage("Uploading" +prog);
                        }
                    }
            );
    }
    }

    private void storeLink(String url) {
        PastP p=new PastP(pp_title.getText().toString(),spinner.getSelectedItem().toString());
        ppUrl  pp=new ppUrl(url);
        db.collection("pastpapers").document(pp_title.getText().toString())
                .set(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.collection("pastpapers")
                        .document(pp_title.getText().toString())
                        .collection("urls")
                        .document()
                        .set(pp).addOnSuccessListener(adVoid -> Toast.makeText(AdminAddPastp.this, "past paper added successfully", Toast.LENGTH_SHORT).show());
            }
        });
            }

    private void chooseFile() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1&& resultCode==RESULT_OK&&data!=null &&data.getClipData()!=null){
            int countClipdata=data.getClipData().getItemCount();
            int currentImageselect=0;

            while(currentImageselect<countClipdata)
            {
                uri=data.getClipData().getItemAt(currentImageselect).getUri();
                list.add(uri);
                currentImageselect=currentImageselect+1;
            }
            txt_no.setText("Selected"+" "+list.size()+" "+"Files");
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "selected:"+spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please select item!", Toast.LENGTH_SHORT).show();
    }
}