package com.myweatherforecastapp.accurate.Model.Forecast;

import java.util.List;

public class MyList {

	public int dt;
	private Rain rain;
	private String dtTxt;
	public List<Weather> weather;
	public Main main;
	private Clouds clouds;
	private Sys sys;
	private Wind wind;

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setRain(Rain rain){
		this.rain = rain;
	}

	public Rain getRain(){
		return rain;
	}

	public void setDtTxt(String dtTxt){
		this.dtTxt = dtTxt;
	}

	public String getDtTxt(){
		return dtTxt;
	}

	public void setWeather(List<Weather> weather){
		this.weather = weather;
	}

	public List<Weather> getWeather(){
		return weather;
	}

	public void setMain(Main main){
		this.main = main;
	}

	public Main getMain(){
		return main;
	}

	public void setClouds(Clouds clouds){
		this.clouds = clouds;
	}

	public Clouds getClouds(){
		return clouds;
	}

	public void setSys(Sys sys){
		this.sys = sys;
	}

	public Sys getSys(){
		return sys;
	}

	public void setWind(Wind wind){
		this.wind = wind;
	}

	public Wind getWind(){
		return wind;
	}


}