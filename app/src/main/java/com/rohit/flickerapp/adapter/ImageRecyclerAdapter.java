package com.rohit.flickerapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.rohit.flickerapp.R;
import com.rohit.flickerapp.model.ModelClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.MyViewHolder> {

    List<ModelClass> imageList;
    Context context;



    public ImageRecyclerAdapter(Context context, List<ModelClass> imageList) {

        this.context = context;
        this.imageList = imageList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.layout_items,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        holder.bind(imageList.get(position),listener);

        ModelClass photo = imageList.get(position);


        Picasso.with(context).load(photo.getImage()).into(holder.image);

        holder.title.setText(photo.getTitle());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title);
            image = (ImageView)itemView.findViewById(R.id.image);

        }

    }

}
