package com.example.mohanbabu.samplemarvelapp;

import android.content.Context;

import com.example.mohanbabu.samplemarvelapp.model.BaseData;
import com.example.mohanbabu.samplemarvelapp.model.Comics;
import java.util.ArrayList;

/**
 * Created by Mohan Babu on 3/16/2016.
 */
public class SubListAdapter extends RecycleViewAdapter {

    ArrayList<BaseData> list ;


    public SubListAdapter(Context context, ArrayList<BaseData> comicList) {

        super(context);

        list = comicList;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BaseData baseData = list.get(position);

        holder.imageView.setImageUrl(baseData.getImageUrl(), imageLoader);

        holder.name.setText(baseData.getName());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}
