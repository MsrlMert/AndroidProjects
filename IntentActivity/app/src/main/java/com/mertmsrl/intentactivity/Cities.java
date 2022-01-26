package com.mertmsrl.intentactivity;

import android.media.Image;

public class Cities {
    private String cityName;
    private int imageId;
    private int population;

    public Cities(String cityName, int imageId, int population) {
        this.cityName = cityName;
        this.imageId = imageId;
        this.population = population;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
