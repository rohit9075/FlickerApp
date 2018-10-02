package com.rohit.flickerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreference {

    private Context mContext;

    public SharedPreference(Context context) {
        this.mContext = context;
    }

    public String getUrl() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageUrl", "");
    }

    public void saveImageDetails(String author,String url,String title,String tags) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ImageAuthor",author);
        editor.putString("ImageUrl", url);
        editor.putString("ImageTitle",title);
        editor.putString("ImageTags",tags);
        editor.apply();
    }

    public String getImageTitle() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageTitle", "");
    }

    public String getImageAuthor() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageAuthor", "");
    }

    public String getImageTags() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ImageTags", "");
    }

    public void saveSearchQuery(String query) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SearchQuery", query);
        editor.apply();
    }

    public String getSearchQuery() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("SearchQuery", "");
    }

    public void saveFilename(String filename) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Filename", filename);
        editor.apply();
    }

    public String getFilename() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ImageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Filename", "");
    }

}
