package com.myweatherforecastapp.accurate.Model.Today;

import com.google.gson.annotations.SerializedName;

public class Coord{

	@SerializedName("lon")
	private double lon;

	@SerializedName("lat")
	private double lat;

	public double getLon(){
		return lon;
	}

	public double getLat(){
		return lat;
	}
}