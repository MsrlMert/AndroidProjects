package com.mertmsrl.gpsapplication.Activities.Models;

public class LocationModel {
    private double Lat, Lng;

    public LocationModel(double lat, double lng) {
        Lat = lat;
        Lng = lng;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}
