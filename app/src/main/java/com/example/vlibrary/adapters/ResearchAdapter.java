package com.example.vlibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vlibrary.R;
import com.example.vlibrary.model.Research;

import java.util.List;

public class ResearchAdapter extends RecyclerView.Adapter<ResearchAdapter.ViewHolder> {
    List<Research> UserList;
    OnItemClickListener listener;
    Context context;
        public ResearchAdapter(List<Research> UserList,OnItemClickListener listener,Context context){
            this.UserList=UserList;
            this.listener=listener;
            this.context=context;
        }

        @NonNull
        @Override
        public ResearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.research_list_items, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ResearchAdapter.ViewHolder holder, int position) {
            String title=UserList.get(position).getTitle();
            holder.setData(title);
        }

        @Override
        public int getItemCount() {
            return UserList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView  tv_title;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_title=itemView.findViewById(R.id.research_title);
                itemView.setOnClickListener(this);
            }

            private void setData( String title){
                tv_title.setText(title);
            }

            @Override
            public void onClick(View v) {
                    listener.Onclick(getAdapterPosition(),v);
                }

        }

      public  interface  OnItemClickListener{
            void Onclick(int position,View v);
        }
    }