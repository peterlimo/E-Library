package com.example.vlibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlibrary.R;
import com.example.vlibrary.model.Book;
import com.example.vlibrary.model.Fetch;
import com.example.vlibrary.model.Research;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    List<Fetch> UserList;
    Context context;
    OnItemClickListener listener;
    public BooksAdapter(List<Fetch> UserList, Context context, OnItemClickListener listener){
        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_one_book, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        String title=UserList.get(position).getTitle();
        String author=UserList.get(position).getAuthor();
        String  url=UserList.get(position).getUrl();

        holder.setData(title,author);
        holder.setImage(url);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_title,tv_author;
        ImageView imagebook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.bookTitle);
            tv_author=itemView.findViewById(R.id.bookMediam);
            itemView.setOnClickListener(this);
        }

        private void setData( String title,String author){
            tv_title.setText(title);
            tv_author.setText(author);
        }
        private void setImage(String url){
            imagebook=itemView.findViewById(R.id.imageBook);
            Picasso.get().load(url).into(imagebook);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            listener.Onclick(position,v);
        }
    }

    public  interface  OnItemClickListener{
        void Onclick(int position,View v);
    }
}