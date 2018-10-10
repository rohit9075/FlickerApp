package com.rohit.flickerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rohit.flickerapp.R;

public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        Toast.makeText(this, "Welcome you to photo Detail Activity", Toast.LENGTH_SHORT).show();
    }
}
