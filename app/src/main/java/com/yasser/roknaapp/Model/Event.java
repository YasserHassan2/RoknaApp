package com.yasser.roknaapp.Model;

import com.parse.ParseGeoPoint;

public class Event {

    String image,description;
    ParseGeoPoint location;

    public Event(String image, String description, ParseGeoPoint location) {
        this.image = image;
        this.description = description;
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParseGeoPoint getLocation() {
        return location;
    }

    public void setLocation(ParseGeoPoint location) {
        this.location = location;
    }
}
