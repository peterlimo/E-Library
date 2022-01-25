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
import com.example.vlibrary.model.Fetch;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewBooksAdapter extends RecyclerView.Adapter<ViewBooksAdapter.ViewHolder> {
    List<Fetch> UserList;
    Context context;
    private OnItemClickListener listener;
    public ViewBooksAdapter(List<Fetch> UserList, Context context, OnItemClickListener listener){
        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewBooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_borrowed_book_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBooksAdapter.ViewHolder holder, int position) {
        String title=UserList.get(position).getTitle();
        String isbn=UserList.get(position).getIsbN();
        String  url=UserList.get(position).getUrl();

        holder.setData(title,isbn);
        holder.setImage(url);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_title,tv_isbn;
        ImageView imagebook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title=itemView.findViewById(R.id.b_bookTitle);
            tv_isbn=itemView.findViewById(R.id.b_bookisBn);
            itemView.setOnClickListener(this);
        }

        private void setData( String title,String isbn){
            tv_title.setText(title);
            tv_isbn.setText(isbn);
        }
        private void setImage(String url){
            imagebook=itemView.findViewById(R.id.b_imageBook);
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