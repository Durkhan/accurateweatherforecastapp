package com.weather.accurateweatherforecast;

import java.util.List;

import com.weather.accurateweatherforecast.Model.Today.Clouds;
import com.weather.accurateweatherforecast.Model.Today.Coord;
import com.weather.accurateweatherforecast.Model.Today.Main;
import com.weather.accurateweatherforecast.Model.Today.Sys;
import com.weather.accurateweatherforecast.Model.Today.WeatherItem;
import com.weather.accurateweatherforecast.Model.Today.Wind;
import com.google.gson.annotations.SerializedName;

public class WeatherResult{

    @SerializedName("visibility")
    private double visibility;

    @SerializedName("timezone")
    private int timezone;

    @SerializedName("main")
    private Main main;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("dt")
    private int dt;

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("weather")
    private List<WeatherItem> weather;

    @SerializedName("name")
    private String name;

    @SerializedName("cod")
    private int cod;

    @SerializedName("id")
    private int id;

    @SerializedName("base")
    private String base;

    @SerializedName("wind")
    private Wind wind;

    public int getVisibility(){
        return (int) visibility;
    }

    public int getTimezone(){
        return timezone;
    }

    public Main getMain(){
        return main;
    }

    public Clouds getClouds(){
        return clouds;
    }

    public Sys getSys(){
        return sys;
    }

    public int getDt(){
        return dt;
    }

    public Coord getCoord(){
        return coord;
    }

    public List<WeatherItem> getWeather(){
        return weather;
    }

    public String getName(){
        return name;
    }

    public int getCod(){
        return cod;
    }

    public int getId(){
        return id;
    }

    public String getBase(){
        return base;
    }

    public Wind getWind(){
        return wind;
    }
}