package com.hari.myphotoalbum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.MyImagesHolder>{

    List<MyImages>imagesList = new ArrayList<>();

    public void setImagesList(List<MyImages> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card,parent,false);
        return new MyImagesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyImagesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class MyImagesHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewTitle, textViewDescription;

        public MyImagesHolder(@NonNull View itemView) {
            super(itemView);

            imageView  = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
