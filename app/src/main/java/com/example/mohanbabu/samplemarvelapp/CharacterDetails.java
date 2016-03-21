package com.example.mohanbabu.samplemarvelapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.mohanbabu.samplemarvelapp.model.*;
import com.example.mohanbabu.samplemarvelapp.model.Character;

import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterDetails extends AppCompatActivity implements Response.ErrorListener{

    static String TAG = "CharacterDetails";

    ImageLoader imageLoader;

    NetworkImageView topBanner;

   // TextView titleTextView;

    TextView discriptionTextview;

    BaseRequest baseRequest;

    RequestQueue requestQueue;

    private RecyclerView comics_recycleView;

    private RecyclerView series_recyclerView;

    private RecyclerView stories_recyclerView;

    private RecyclerView events_recyclerView;

    private LinearLayoutManager comicsLayoutManager;

    private LinearLayoutManager seriesLayoutManager;

    private LinearLayoutManager storiesLayoutManager;

    private LinearLayoutManager eventsLayoutManager;

    private Character character;

    private SubListAdapter subListAdapter;

    private ArrayList<BaseData> subList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_character_details);

        topBanner = (NetworkImageView) findViewById(R.id.backdrop);

      //  titleTextView = (TextView) findViewById(R.id.title);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(topToolBar);

        discriptionTextview = (TextView) findViewById(R.id.character_description);

        imageLoader = CustomVolleyRequest.getInstance(this).getImageLoader();

        baseRequest = new BaseRequest(RequestSignature.create());

        requestQueue = Volley.newRequestQueue(this);

        comics_recycleView = (RecyclerView) findViewById(R.id.comics_recycleview);

        series_recyclerView = (RecyclerView) findViewById(R.id.series_recycleview);

        stories_recyclerView = (RecyclerView) findViewById(R.id.stories_recycleview);

        events_recyclerView = (RecyclerView) findViewById(R.id.events_recycleview);

        comics_recycleView.setHasFixedSize(true);

        series_recyclerView.setHasFixedSize(true);

        stories_recyclerView.setHasFixedSize(true);

        events_recyclerView.setHasFixedSize(true);

        comicsLayoutManager = new LinearLayoutManager(this);

        comicsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        seriesLayoutManager = new LinearLayoutManager(this);

        seriesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        storiesLayoutManager = new LinearLayoutManager(this);

        storiesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        eventsLayoutManager = new LinearLayoutManager(this);

        eventsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        comics_recycleView.setLayoutManager(comicsLayoutManager);

        series_recyclerView.setLayoutManager(seriesLayoutManager);

        stories_recyclerView.setLayoutManager(storiesLayoutManager);

        events_recyclerView.setLayoutManager(eventsLayoutManager);

        subList = new ArrayList<>();

        if(getIntent() != null) {

            character = (Character) getIntent().getSerializableExtra("details");

            topBanner.setImageUrl(character.getImageUrl(), imageLoader);

          //  titleTextView.setText(character.getName());

            discriptionTextview.setText(character.getDescription());

        }

        getSupportActionBar().setTitle(character.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subListAdapter = new SubListAdapter(getApplicationContext() , subList);

        comics_recycleView.setAdapter(subListAdapter);

        ListRequest(Constants.COMICS_URL, comics_recycleView);

        ListRequest(Constants.SERIES_URL, series_recyclerView);

        ListRequest(Constants.STORIES_URL, stories_recyclerView);

        ListRequest(Constants.EVENTS_URL, events_recyclerView);

    }

    private void ListRequest( String path , final RecyclerView recyclerView) {

         Log.d(TAG , ""+String.format(path, character.getId()).toString());

        String URL = Uri.parse(String.format(path ,character.getId())).buildUpon().

                appendQueryParameter("apikey", baseRequest.getApiKey())

                .appendQueryParameter("ts", "" + baseRequest.getTimestamp())

                .appendQueryParameter("hash", baseRequest.getHashSignature()).build().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                subList = Utility.comicListJsonParser(response);

                subListAdapter = new SubListAdapter( getApplicationContext() , subList );

                recyclerView.setAdapter(subListAdapter);

            }
        }, this);

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
