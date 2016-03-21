package com.example.mohanbabu.samplemarvelapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohanbabu.samplemarvelapp.model.Character;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    //Creating a List of superheroes
    private List<com.example.mohanbabu.samplemarvelapp.model.Character> characterList;

    //Creating Views
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private RecyclerView.Adapter adapter;

    private boolean isloading = false;

    private SearchView searchView;

    final static String TAG = "Marvel";

    public static String[] columns = new String[]{"_id", "name", "path" ,"doc_id" , "description"};

    BaseRequest baseRequest;

    RequestQueue requestQueue;

    searchSuggestListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        getSupportActionBar().setTitle(R.string.app_title);

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        baseRequest = new BaseRequest(RequestSignature.create());

        requestQueue = Volley.newRequestQueue(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isloading & totalItemCount <= (lastVisibleItem + 2)) {

                    CharacterListRequest();

                    isloading = true;
                }
            }
        });

        //Initializing our Character list
        characterList = new ArrayList<>();

        //Finally initializing our adapter
        adapter = new CardAdapter(characterList, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //Calling method to get data
        CharacterListRequest();
    }

    //This method will get data from the web api
    private void CharacterListRequest() {

        String URL = Uri.parse(Constants.DATA_URL).buildUpon().

                appendQueryParameter("apikey", baseRequest.getApiKey())

                .appendQueryParameter("ts", "" + baseRequest.getTimestamp())

                .appendQueryParameter("hash", baseRequest.getHashSignature())

                .appendQueryParameter("offset", "" + characterList.size()).toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, new JSONObject(), this, this);

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        isloading = false;
    }

    @Override
    public void onResponse(Object response) {

        characterList.addAll(Utility.chartacterListJsonParser(response));

        adapter.notifyDataSetChanged();

        isloading = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.activity_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(this);

        searchView.setOnSuggestionListener(this);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        listAdapter = new searchSuggestListAdapter(this , null);

        searchView.setSuggestionsAdapter(listAdapter);

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        searchSuggestionRequest(newText);

        return true;
    }

    @Override
    public boolean onSuggestionSelect(int position) {

        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {

        Cursor cursor = (Cursor) listAdapter.getItem(position);

        Character character = new Character();

        character.setName(cursor.getString(cursor.getColumnIndex("name")));

        character.setId(cursor.getString(cursor.getColumnIndex("doc_id")));

        character.setImageUrl(cursor.getString(cursor.getColumnIndex("path")));

        character.setDescription(cursor.getString(cursor.getColumnIndex("description")));

        Intent intent = new Intent( this , CharacterDetails.class );

        intent.putExtra("details", character);

        startActivity(intent);

        return true;
    }

    public void searchSuggestionRequest(String query) {

        String URL = Uri.parse(Constants.DATA_URL).buildUpon().
                appendQueryParameter("apikey", baseRequest.getApiKey())
                .appendQueryParameter("ts", "" + baseRequest.getTimestamp())
                .appendQueryParameter("hash", baseRequest.getHashSignature())
                .appendQueryParameter("nameStartsWith", query)
                .appendQueryParameter("limit", "10").toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ArrayList<Character> suggestList = Utility.chartacterListJsonParser(response);

                  listAdapter.swapCursor(convertToCursor(suggestList));

            }
        }, this);

        requestQueue.add(jsonObjectRequest);

    }

    private Cursor convertToCursor(ArrayList<Character> list) {

        MatrixCursor cursor = new MatrixCursor(columns);

        int i = 0;

        for (Character character : list) {

            String[] temp = new String[5];

            i = i + 1;

            temp[0] = Integer.toString(i);

            temp[1] = character.getName();

            temp[2] =character.getImageUrl();

            temp[3] = character.getId();

            temp[4] = character.getDescription();

            cursor.addRow(temp);
        }
        return cursor;

    }


}