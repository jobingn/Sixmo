package com.sixmogroup.app.sixmo.model;

/**
 * Created by Jobin on Oct 14.
 */
public class EventModel {
    String eventid;
    String name;
    String time;
    String venue;
    String eventDate;
    String description;
    String organizerId;
    String imagePath;
    String requestedDate;


    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String rquestedDate) {
        this.requestedDate = rquestedDate;
    }
    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getDescription() {
        return description;
    }


}
