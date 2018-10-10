package com.rohit.flickerapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    //********************** added in the version 2.0 ********************************
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    public ImageRecyclerAdapter(Context context, List<ModelClass> imageList) {

        this.context = context;
        this.imageList = imageList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //********************** added in the version 2.0 ********************************
        // creating the object of the SharedPreferences class
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);


        // shared preference change listner
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d("prererences", "onSharedPreferenceChanged: changed");
            }
        };

        // register the sharedpreferences listner to get the changes
        settings.registerOnSharedPreferenceChangeListener(preferenceChangeListener);


        // getting the boolean value form the preferences as per user selection
        Boolean grid = settings.getBoolean(context.getString(R.string.pref_display_grid),false);

        // getting the id of the layouts to inflate xml layout
        int layoutId = grid ? R.layout.grid_layout_items : R.layout.layout_items;

        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(layoutId, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;

        //********************** added in the version 2.0 ********************************

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        holder.bind(imageList.get(position),listener);

        ModelClass photo = imageList.get(position);


        Picasso.with(context).load(photo.getImage()).placeholder(R.drawable.progress_animation).into(holder.image);

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
