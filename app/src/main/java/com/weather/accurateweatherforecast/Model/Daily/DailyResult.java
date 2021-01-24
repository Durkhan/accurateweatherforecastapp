package com.weather.accurateweatherforecast.Model.Daily;

import java.util.List;

public class DailyResult {
    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Current current;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    private String timezone;
    private int timezoneOffset;

    public List<DailyItem> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyItem> daily) {
        this.daily = daily;
    }

    public List<DailyItem> daily;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    private double lon;

    public List<HourlyItem> getHourly() {
        return hourly;
    }

    public void setHourly(List<HourlyItem> hourly) {
        this.hourly = hourly;
    }

    public List<HourlyItem> hourly;
    public List<MinutelyItem> minutely;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double lat;
}
