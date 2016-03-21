package com.example.mohanbabu.samplemarvelapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Mohan Babu on 3/15/2016.
 */
public class searchSuggestListAdapter extends CursorAdapter {

    ImageLoader imageLoader ;

    public searchSuggestListAdapter(Context context, Cursor c) {
        super(context, c);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggest_list_item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.icon_feed);

        TextView textView = (TextView) view.findViewById(R.id.feed_url_text);

        imageLoader.get(cursor.getString(cursor.getColumnIndex("path")), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        imageView.setImageUrl(cursor.getString(cursor.getColumnIndex("path")) , imageLoader);

        textView.setText(cursor.getString(cursor.getColumnIndex("name")));

    }


}
