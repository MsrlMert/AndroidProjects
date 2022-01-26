package com.mertmsrl.intentactivity;

public class Flights {
    private String airportName, airportNameAbbr, flightStartPoint, flightEndPoint;
    private int airportPictureId;


    public Flights(String airportName, String airportNameAbbr, String flightStartPoint, String flightEndPoint, int airportPictureId) {
        this.airportName = airportName;
        this.airportNameAbbr = airportNameAbbr;
        this.flightStartPoint = flightStartPoint;
        this.flightEndPoint = flightEndPoint;
        this.airportPictureId = airportPictureId;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportNameAbbr() {
        return airportNameAbbr;
    }

    public void setAirportNameAbbr(String airportNameAbbr) {
        this.airportNameAbbr = airportNameAbbr;
    }

    public String getFlightStartPoint() {
        return flightStartPoint;
    }

    public void setFlightStartPoint(String flightStartPoint) {
        this.flightStartPoint = flightStartPoint;
    }

    public String getFlightEndPoint() {
        return flightEndPoint;
    }

    public void setFlightEndPoint(String flightEndPoint) {
        this.flightEndPoint = flightEndPoint;
    }

    public int getAirportPictureId() {
        return airportPictureId;
    }

    public void setAirportPictureId(int airportPictureId) {
        this.airportPictureId = airportPictureId;
    }
}


