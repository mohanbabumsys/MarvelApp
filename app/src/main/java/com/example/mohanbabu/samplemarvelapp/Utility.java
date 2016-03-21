package com.example.mohanbabu.samplemarvelapp;

import android.util.Log;

import com.example.mohanbabu.samplemarvelapp.model.*;
import com.example.mohanbabu.samplemarvelapp.model.Character;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohan Babu on 3/16/2016.
 */
public class Utility {

    public static ArrayList <Character> chartacterListJsonParser(Object response) {

            ArrayList<Character> tempList = new ArrayList<Character>();

            try {

                JSONObject data = ((JSONObject) response).getJSONObject("data");

                JSONArray result = data.getJSONArray("results");

                int count = data.getInt("count");

                for (int i = 0; i < count; i++) {

                    Character character = new Character();

                    JSONObject resultObj = (JSONObject) result.get(i);

                    JSONObject thumbnail = resultObj.getJSONObject("thumbnail");

                    character.setName(resultObj.getString("name"));

                    character.setId(resultObj.getString("id"));

                    character.setDescription(resultObj.getString("description"));

                    character.setImageUrl(thumbnail.getString("path") + "." + thumbnail.getString("extension"));

                    tempList.add(character);
                }

            } catch (Exception e) {

                e.printStackTrace();
            }

            return tempList;

    }

    public static ArrayList<Character> suggestListJsonParser(Object response) {

        ArrayList<Character> tempList = new ArrayList<Character>();

        try {

            JSONObject data = ((JSONObject) response).getJSONObject("data");

            JSONArray result = data.getJSONArray("results");

            int count = data.getInt("count");

            for (int i = 0; i < count; i++) {

                Character character = new Character();

                JSONObject resultObj = (JSONObject) result.get(i);

                JSONObject thumbnail = resultObj.getJSONObject("thumbnail");

                character.setName(resultObj.getString("name"));

                character.setId(resultObj.getString("id"));

                character.setImageUrl(thumbnail.getString("path") + "." + thumbnail.getString("extension"));

                tempList.add(character);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return tempList;

    }

    public static ArrayList<BaseData> comicListJsonParser(Object response) {

        ArrayList<BaseData> tempList = new ArrayList<BaseData>();

        try {

            JSONObject data = ((JSONObject) response).getJSONObject("data");

            JSONArray result = data.getJSONArray("results");

            int count = data.getInt("count");

            for (int i = 0; i < count; i++) {

                Comics comics = new Comics();

                JSONObject resultObj = (JSONObject) result.get(i);

                JSONObject thumbnail = resultObj.getJSONObject("thumbnail");

                comics.setName(resultObj.getString("title"));

                comics.setId(resultObj.getString("id"));

                comics.setImageUrl(thumbnail.getString("path") + "." + thumbnail.getString("extension"));

                tempList.add(comics);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return tempList;

    }
}
