package com.example.vlibrary.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.vlibrary.R;
import com.example.vlibrary.model.Borrow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

public class ViewDialog {
FirebaseFirestore db;
    public void showDialog(Activity activity, String title, String author, String isbn, String category, String url,String id){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_borrow_book_dialog);
        ImageView imageView=dialog.findViewById(R.id.imageBook);
        TextView bookTitle =  dialog.findViewById(R.id.bookTitle);
        TextView bookAuthor =  dialog.findViewById(R.id.bookAuthor);
        TextView bookIsbn =  dialog.findViewById(R.id.bookIsbn);
        TextView bookCategory =  dialog.findViewById(R.id.bookCategory);
        TextView txt_info=dialog.findViewById(R.id.txt_info);
        ImageView cancel_btn=dialog.findViewById(R.id.cancel_button);
        Button borrow_btn = dialog.findViewById(R.id.borrow_btn);
        Picasso.get().load(url).into(imageView);
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookCategory.setText(category);
        bookIsbn.setText(isbn);


        SharedPreferences pref = activity.getSharedPreferences("user", MODE_PRIVATE);
        String username = pref.getString("email", "");
db=FirebaseFirestore.getInstance();
db.collection("users").document(username).collection("borrowed")
        .document(id).get().addOnCompleteListener(task -> {
    DocumentSnapshot document=task.getResult();
    if(document.exists()){
        borrow_btn.setVisibility(View.INVISIBLE);
        txt_info.setVisibility(View.VISIBLE);
    }
        });



        borrow_btn.setOnClickListener(v ->
                {
                    Borrow borrow=new Borrow(title,url,isbn,id);
                    db.collection("users")
                            .document(username)
                            .collection("borrowed")
                            .document(id)
                            .set(borrow)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(activity, "Book Successfully Borrowed", Toast.LENGTH_SHORT).show();
                                db.collection("users")
                                        .document(username).update("noOfBooks", FieldValue.increment(1));
                                borrow_btn.setVisibility(View.INVISIBLE);
                                txt_info.setVisibility(View.VISIBLE);
                            })
                            .addOnFailureListener(e -> {

                            });
                }
        );
        cancel_btn.setOnClickListener(v->dialog.dismiss());
        dialog.setCancelable(true);
        dialog.show();
    }
}
