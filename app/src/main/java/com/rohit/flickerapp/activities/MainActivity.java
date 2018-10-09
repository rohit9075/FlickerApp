package com.rohit.flickerapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rohit.flickerapp.Listner.RecyclerOnClickListner;
import com.rohit.flickerapp.Listner.RecyclerViewTouchListener;
import com.rohit.flickerapp.R;
import com.rohit.flickerapp.adapter.ImageRecyclerAdapter;
import com.rohit.flickerapp.model.ModelClass;
import com.rohit.flickerapp.utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    // reference variables
    RecyclerView mRecyclerView;

    List<ModelClass> mFlickerDataList;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ImageRecyclerAdapter mImageRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateViews(); // method call

        handlingRecyclerViewItemClick();  // method call

    }

    //********************** added in the version 3.0 ********************************

    private void handlingRecyclerViewItemClick() {
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(MainActivity.this, mRecyclerView, new RecyclerOnClickListner() {
            @Override
            public void onClick(View view, int position) {

                ModelClass modelClass = mFlickerDataList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("author" , modelClass.getAuthor());
                bundle.putString("title",modelClass.getTitle());
                bundle.putString("tags" , modelClass.getTags());
                bundle.putString("link", modelClass.getImage());

                Intent mIntentPhotoDetailActivity = new Intent(MainActivity.this, PhotoDetailActivity.class);
                mIntentPhotoDetailActivity.putExtras(bundle);
                startActivity(mIntentPhotoDetailActivity);

            }

            @Override
            public void onLongClick(View view, int position) {

                // handle event in long press
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetImages().execute(); // asyncTask class call

    }

    /**
     * method to instantiate the variables
     */
    public void initiateViews(){

        mRecyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mFlickerDataList = new ArrayList<>();

    }

    /**
     * method to handle swipe event
     */
    @Override
    public void onRefresh() {

        new GetImages().execute(); // execute async task class

    }


    /**
     * inner async task class
     */
    public class GetImages extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall("https://api.flickr.com/services/feeds/photos_public.gne?tags="+ "car" +"&tagmode=any&format=json&nojsoncallback=1");


            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray mFlickerJsonObject = jsonObject.getJSONArray("items");

                    mFlickerDataList.clear();

                    for (int i = 0; i < mFlickerJsonObject.length(); i++) {
                        JSONObject jsonObject1 = mFlickerJsonObject.optJSONObject(i);
                        ModelClass mFlickerObject=new ModelClass();

                        mFlickerObject.setTitle(jsonObject1.getString("title"));

                        mFlickerObject.setAuthor(jsonObject1.getString("author"));

                        mFlickerObject.setTags(jsonObject1.getString("tags"));

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("media");
                        String photoUrl = jsonObject2.getString("m");

                        String link = photoUrl.replaceFirst("_m.","_b.");

                        mFlickerObject.setImage(link);

                        mFlickerDataList.add(mFlickerObject);

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

            if(mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
            recyclerViewInitialisation(); // method call


        }
    }

    //********************** added in the version 3.0 ********************************

    private void recyclerViewInitialisation() {
        // adapter initialization
        mImageRecyclerAdapter = new ImageRecyclerAdapter(MainActivity.this, mFlickerDataList);

        //********************** added in the version 2.0 ********************************

        // creating the object of the SharedPreferences class
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        // getting the boolean value form the preferences as per user selection
        Boolean grid = settings.getBoolean(getString(R.string.pref_display_grid),false);


        if (grid){
            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        }
        else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }

        //********************** added in the version 2.0 ********************************

        mRecyclerView.setAdapter(mImageRecyclerAdapter);



    }


    //********************** added in the version 2.0 ********************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Show the settings screen
                Intent settingsIntent = new Intent(this, PrefsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //********************** added in the version 2.0 ********************************
}
