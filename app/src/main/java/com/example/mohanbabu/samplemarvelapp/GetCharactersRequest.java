package com.example.mohanbabu.samplemarvelapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohan Babu on 3/15/2016.
 */

public class GetCharactersRequest extends JsonObjectRequest {

    Map<String ,String> params;

    public GetCharactersRequest(int method, String url,HashMap hashMap , JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);

        params = hashMap ;
    }

    public GetCharactersRequest(String url, JSONObject jsonRequest, HashMap hashMap , Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return params;
    }
}
