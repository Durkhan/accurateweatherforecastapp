package com.myweatherforecastapp.accurate.Model.Daily;

public class MinutelyItem {
    private int dt;

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    private double precipitation;
}