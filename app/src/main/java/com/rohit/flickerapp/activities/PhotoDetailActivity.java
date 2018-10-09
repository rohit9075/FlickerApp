package com.rohit.flickerapp.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohit.flickerapp.R;
import com.rohit.flickerapp.utils.SharedPreference;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity  {

    private TextView author,title,tags,link;
    private ImageView image;
    private SharedPreference sharedPreference;


    private String[] listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        initiateViews();

        listItems = new String[]{"Download Image", "Share Image","Share Image Link"};

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());

        sharedPreference = new SharedPreference(this);

        author.setText("Author : " + sharedPreference.getImageAuthor());
        title.setText("Title : "+sharedPreference.getImageTitle());
        tags.setText("Tags : "+sharedPreference.getImageTags());
        link.setText("Link : "+sharedPreference.getUrl());
        Picasso.with(PhotoDetailActivity.this).load(sharedPreference.getUrl()).into(image);

    }

    public void initiateViews(){

        author = (TextView)findViewById(R.id.photo_author);
        title = (TextView)findViewById(R.id.photo_title);
        tags = (TextView)findViewById(R.id.photo_tags);
        link = (TextView)findViewById(R.id.photo_link);

        image = (ImageView)findViewById(R.id.photo_image);

    }




}
