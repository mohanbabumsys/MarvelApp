package com.example.mohanbabu.samplemarvelapp.model;

import java.io.Serializable;

/**
 * Created by Mohan Babu on 3/17/2016.
 */
public class BaseData implements Serializable {

    //Data Variables
    private String id;

    private String name;

    private String imageUrl;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Getters and Setters
    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        this.imageUrl = imageUrl;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }



}
