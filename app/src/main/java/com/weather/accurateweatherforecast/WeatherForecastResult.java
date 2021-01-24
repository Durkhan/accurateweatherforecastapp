package com.weather.accurateweatherforecast;

import com.weather.accurateweatherforecast.Model.Forecast.City;
import com.weather.accurateweatherforecast.Model.Forecast.MyList;
import com.weather.accurateweatherforecast.Model.Today.WeatherItem;

import java.util.List;

public class WeatherForecastResult {

    public City city;
	private int cnt;
	private String cod;
	private double message;
	public List<MyList> list;
    public  List<WeatherItem>weather;
	public void setCity(City city){
		this.city = city;
	}

	public City getCity(){
		return city;
	}

	public void setCnt(int cnt){
		this.cnt = cnt;
	}

	public int getCnt(){
		return cnt;
	}

	public void setCod(String cod){
		this.cod = cod;
	}

	public String getCod(){
		return cod;
	}
	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setMessage(double message){
		this.message = message;
	}

	public double getMessage(){
		return message;
	}

	public void setList(List<MyList> list){
		this.list = list;
	}

	public List<MyList> getList(){
		return list;
	}
}