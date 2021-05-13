package com.myweatherforecastapp.accurate.Model.Daily;


import java.util.List;

public class DailyItem {
    public int getRain() {
        return (int) rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double rain;

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    private int sunrise;

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    private Temp temp;
    private double uvi;

    public int getPressure() {
        return pressure;
    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int pressure;
    private int clouds;

    public FeelsLike getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(FeelsLike feelsLike) {
        this.feelsLike = feelsLike;
    }

    public FeelsLike feelsLike;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    private int dt;

    public int getPop() {
        return (int) pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    private double pop;
    private int windDeg;
    private double dewPoint;

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    private int sunset;

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public List<WeatherItem> weather;

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int humidity;

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    private double windSpeed;
}
