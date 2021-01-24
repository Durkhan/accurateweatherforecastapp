package com.weather.accurateweatherforecast.Model.Forecast;

public class Main{
	private double temp;
	private double tempMin;
	private double grndLevel;
	private int tempKf;
	private int humidity;
	private double pressure;
	private double seaLevel;
	private double tempMax;

	public void setTemp(long temp){
		this.temp = temp;
	}

	public long getTemp(){
		return (long) temp;
	}

	public void setTempMin(double tempMin){
		this.tempMin = tempMin;
	}

	public int getTempMin(){
		return (int) tempMin;
	}

	public void setGrndLevel(double grndLevel){
		this.grndLevel = grndLevel;
	}

	public double getGrndLevel(){
		return grndLevel;
	}

	public void setTempKf(int tempKf){
		this.tempKf = tempKf;
	}

	public int getTempKf(){
		return tempKf;
	}

	public void setHumidity(int humidity){
		this.humidity = humidity;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setPressure(double pressure){
		this.pressure = pressure;
	}

	public int getPressure(){
		return (int) pressure;
	}

	public void setSeaLevel(double seaLevel){
		this.seaLevel = seaLevel;
	}

	public double getSeaLevel(){
		return seaLevel;
	}

	public void setTempMax(double tempMax){
		this.tempMax = tempMax;
	}

	public int getTempMax(){
		return (int) tempMax;
	}
}
