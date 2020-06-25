package com.yasser.roknaapp.Model;

import com.parse.ParseGeoPoint;

public class Workshop {

    String imageURL,title,description,price,phone;
    ParseGeoPoint location;
    String avaliable;


    public Workshop(String imageURL, String title, String description, String price, String phone, ParseGeoPoint location, String avaliable) {
        this.imageURL = imageURL;
        this.title = title;
        this.description = description;
        this.price = price;
        this.phone = phone;
        this.location = location;
        this.avaliable = avaliable;
    }

    public String isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }


    public Workshop() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ParseGeoPoint getLocation() {
        return location;
    }

    public void setLocation(ParseGeoPoint location) {
        this.location = location;
    }



    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
