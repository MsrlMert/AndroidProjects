package com.mertmsrl.teknodaterest;

public class Cities {

    private String cityName;
    private String cityId;

    public Cities() {
    }

    public Cities(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
