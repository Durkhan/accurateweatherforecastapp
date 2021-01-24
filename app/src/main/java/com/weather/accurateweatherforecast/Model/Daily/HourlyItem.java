package com.weather.accurateweatherforecast.Model.Daily;

import java.util.List;

public class HourlyItem {
    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    private int dt;
    private double pop;

    public int getTemp() {
        return (int) temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    private double temp;
    private int windDeg;
    private int visibility;
    private double dewPoint;

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

   public List<WeatherItem> weather;
    private int humidity;
    private double windSpeed;

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    private int pressure;
    private int clouds;
    private double feelsLike;
}
