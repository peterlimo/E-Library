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
import com.example.vlibrary.model.GetPpData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PastPAdapter extends RecyclerView.Adapter<PastPAdapter.ViewHolder> {
    List<GetPpData> UserList;
    Context context;
    OnItemClickListener listener;
    public PastPAdapter(List<GetPpData> UserList, Context context, OnItemClickListener listener){
        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public PastPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_one_book, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PastPAdapter.ViewHolder holder, int position) {
        String title=UserList.get(position).getTitle();
        String  url=UserList.get(position).getUrl();

        holder.setData(title);
        holder.setImage(url);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_title;
        ImageView imagebook;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.bookTitle);
            itemView.setOnClickListener(this);
        }

        private void setData( String title){
            tv_title.setText(title);
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