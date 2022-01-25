package com.example.vlibrary.admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.vlibrary.R;
import com.example.vlibrary.model.Book;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.IOException;
public class AdminAddBooks extends AppCompatActivity {
EditText isbNumber,bookTitle,bookAuthor,bookCategory;
Button add_book;
TextView select_image;
FirebaseFirestore db;
Uri uri;
ImageView book_image;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_books);
        isbNumber=findViewById(R.id.isbNumber);
        bookTitle=findViewById(R.id.bookTitle);
        bookAuthor=findViewById(R.id.bookAuthor);
        bookCategory=findViewById(R.id.bookCategory);
        add_book=findViewById(R.id.button_add_book);
        db=FirebaseFirestore.getInstance();
        select_image=findViewById(R.id.select_image);
        book_image=findViewById(R.id.book_image);
        dialog=new ProgressDialog(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            // Permission is not granted
        }
        add_book.setOnClickListener(v -> UploadImage());

        select_image.setOnClickListener(v -> selectImage());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
        }
    }
        private void selectImage () {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }
        private void UploadImage () {
            dialog.show();
            dialog.setCancelable(false);
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference name = storageRef.child(uri.getLastPathSegment());
            name.putFile(uri).addOnSuccessListener(taskSnapshot ->
                    name.getDownloadUrl().addOnSuccessListener(uri -> {
                        dialog.dismiss();
                        String url = uri.toString();
                        addBook(url);
                    })).addOnProgressListener(
                    task -> {
                        double progress = (100.0 * task.getBytesTransferred()) / task.getTotalByteCount();
                        int prog = (int) progress;
                        dialog.setMessage("Uploading" + prog);
                    });
        }
        void addBook (String url){
            Book book = new Book(bookTitle.getText().toString(), bookAuthor.getText().toString(), isbNumber.getText().toString(), bookCategory.getText().toString(), url);
            db.collection("books")
                    .document()
                    .set(book)
                    .addOnSuccessListener(aVoid -> Toast.makeText(AdminAddBooks.this, "Book Successfully Added!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(AdminAddBooks.this, "Failed To Add Book!", Toast.LENGTH_SHORT).show());
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
                uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    book_image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }