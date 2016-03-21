package com.example.mohanbabu.samplemarvelapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mohanbabu.samplemarvelapp.model.Character;

import java.util.List;

/**
 * Created by Mohanbabu on 3/15/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    //List of Characters
    List<Character> characters;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public CardAdapter(List<Character> characters, Context context){

        super();

        this.characters = characters;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_FOOTER) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress, parent, false);

            ViewHolder viewHolder = new ViewHolder(v);

            return viewHolder;
        }

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.characters_list, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == characters.size()) {

            return;
        }

        Character character =  characters.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();

        imageLoader.get(character.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(character.getImageUrl(), imageLoader);

        holder.name.setText(character.getName());


    }

    @Override
    public int getItemCount() {

        return characters.size()+1;
    }



    @Override
    public int getItemViewType(int position) {

      if(position == characters.size()) {

          return TYPE_FOOTER;
      }

        return TYPE_ITEM;
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

            Intent intent = new Intent(context , CharacterDetails.class);

            intent.putExtra("details" , characters.get(getPosition()));

            context.startActivity(intent);

        }
    }
}