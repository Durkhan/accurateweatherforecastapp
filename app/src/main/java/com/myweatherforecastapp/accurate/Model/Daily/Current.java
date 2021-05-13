package com.myweatherforecastapp.accurate.Model.Daily;

import java.util.List;
public class Current {
    private int sunrise;

    public void setTemp(double temp) {
        this.temp = temp;
    }

    private double temp;

    public int getVisibility() {
        return visibility;
    }

    private int visibility;
    private double uvi;

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    private int pressure;
    private int clouds;
    private double feelsLike;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    private int dt;
    private int windDeg;

    public int getUvi() {
        return (int) uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    private double dewPoint;
    private int sunset;

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    private List<WeatherItem> weather;
    private int humidity;

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    private double windSpeed;

    public int getSunrise() {
        return sunrise;
    }
    public int getTemp() {
        return (int) temp;
    }
}