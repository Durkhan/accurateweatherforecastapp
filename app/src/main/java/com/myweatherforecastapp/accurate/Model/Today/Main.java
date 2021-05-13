package com.myweatherforecastapp.accurate.Model.Today;

import com.google.gson.annotations.SerializedName;

public class Main{

	@SerializedName("temp")
	private double temp;

	@SerializedName("temp_min")
	private double tempMin;

	@SerializedName("humidity")
	private int humidity;

	@SerializedName("pressure")
	private double pressure;

	@SerializedName("feels_like")
	private double feelsLike;

	@SerializedName("temp_max")
	private double tempMax;

	public int getTemp(){
		return (int) temp;
	}

	public int getTempMin(){
		return (int) tempMin;
	}

	public int getHumidity(){
		return humidity;
	}

	public int getPressure(){
		return (int) pressure;
	}

	public int getFeelsLike(){
		return (int) feelsLike;
	}

	public int getTempMax(){
		return (int) tempMax;
	}
}