package com.rohit.flickerapp.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rohit.flickerapp.R;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity  {

    private TextView author,title,tags,link;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        initiateViews();

        Bundle bundle = getIntent().getExtras();

        author.setText("Author :"  + bundle.getString("author"));
        title.setText("Title : "+bundle.getString("title"));
        tags.setText("Tags : "+bundle.getString("tags"));
        link.setText("Link : "+bundle.getString("link"));
        String link  =  bundle.getString("link");


        GlideApp.with(PhotoDetailActivity.this)
                .load(link)
                .thumbnail(Glide.with(PhotoDetailActivity.this).load(R.drawable.ic_placeholder))
                .into(image);

    }

    public void initiateViews(){

        author = findViewById(R.id.photo_author);
        title = findViewById(R.id.photo_title);
        tags = findViewById(R.id.photo_tags);
        link = findViewById(R.id.photo_link);
        image = findViewById(R.id.photo_image);

    }

}
