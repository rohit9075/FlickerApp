package com.rohit.flickerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rohit.flickerapp.adapter.ImageRecyclerAdapter;
import com.rohit.flickerapp.model.ModelClass;
import com.rohit.flickerapp.utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerView;

    List<ModelClass> imageList = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout ;


    ImageRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateViews();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetImages().execute();

    }
    public void initiateViews(){

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {

        new GetImages().execute();

    }


    public class GetImages extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall("https://api.flickr.com/services/feeds/photos_public.gne?tags="+ "car" +"&tagmode=any&format=json&nojsoncallback=1");


            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray article = jsonObject.getJSONArray("items");

                    imageList.clear();

                    for (int i = 0; i < article.length(); i++) {
                        JSONObject jsonObject1 = article.optJSONObject(i);
                        ModelClass mobj=new ModelClass();

                        mobj.setTitle(jsonObject1.getString("title"));

                        mobj.setAuthor(jsonObject1.getString("author"));

                        mobj.setTags(jsonObject1.getString("tags"));

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("media");
                        String photoUrl = jsonObject2.getString("m");

                        String link = photoUrl.replaceFirst("_m.","_b.");

                        mobj.setImage(link);

                        imageList.add(mobj);

                    }
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "parser error", Toast.LENGTH_LONG).show();
                        }
                    });
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "can't get json file", Toast.LENGTH_LONG).show();
                    }
                });
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }


            mRecyclerAdapter = new ImageRecyclerAdapter(MainActivity.this,imageList);

            recyclerView.setAdapter(mRecyclerAdapter);
        }
    }
}
