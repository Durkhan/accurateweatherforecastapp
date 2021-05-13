package com.myweatherforecastapp.accurate.Model.Daily;
public class Temp {
    public int getMin() {
        return (int) min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    private double min;

    public int getMax() {
        return (int) max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    private double max;

    public int getEve() {
        return (int) eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    private double eve;
    private double night;

    public int getDay() {
        return (int)day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    private double day;
    private double morn;

    public int getMorn() {
        return (int) morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }
}