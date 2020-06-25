package com.yasser.roknaapp.Model;

import com.parse.ParseGeoPoint;

public class Event {
    String eventTitle,eventDates,eventImageURL;
    ParseGeoPoint eventLocation;
    String avaliable;



    public Event() {
    }

    public String isAvaliable() {
        return avaliable;
    }

    public Event(String eventTitle, String eventDates, String eventImageURL, ParseGeoPoint eventLocation, String avaliable) {
        this.eventTitle = eventTitle;
        this.eventDates = eventDates;
        this.eventImageURL = eventImageURL;
        this.eventLocation = eventLocation;
        this.avaliable = avaliable;
    }

    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDates() {
        return eventDates;
    }

    public void setEventDates(String eventDates) {
        this.eventDates = eventDates;
    }

    public String getEventImageURL() {
        return eventImageURL;
    }

    public void setEventImageURL(String eventImageURL) {
        this.eventImageURL = eventImageURL;
    }

    public ParseGeoPoint getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(ParseGeoPoint eventLocation) {
        this.eventLocation = eventLocation;
    }
}
