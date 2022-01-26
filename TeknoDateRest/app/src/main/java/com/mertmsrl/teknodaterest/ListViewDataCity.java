package com.mertmsrl.teknodaterest;

public class ListViewDataCity {

    private String weatherStateName;
    private String maxTemp;
    private String minTemp;
    private String applicableDate;

    public ListViewDataCity(String weatherStateName, String maxTemp, String minTemp, String applicableDate) {
        this.weatherStateName = weatherStateName;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.applicableDate = applicableDate;
    }

    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }
}
