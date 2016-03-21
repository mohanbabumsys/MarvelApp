package com.example.mohanbabu.samplemarvelapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Mohan Babu on 3/16/2016.
 */
abstract public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    Context mContext;

    ImageLoader imageLoader;

    public RecycleViewAdapter(Context context) {

        mContext = context ;

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public NetworkImageView imageView;

        public TextView name;

        public ViewHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(this);

            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);

            name = (TextView) itemView.findViewById(R.id.name);

        }

        @Override
        public void onClick(View v) {


        }
    }
}
