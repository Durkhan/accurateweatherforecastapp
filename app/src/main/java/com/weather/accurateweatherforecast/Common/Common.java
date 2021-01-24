package com.weather.accurateweatherforecast.Common;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class  Common {
    public static String api_key="8c5b75cad5426c001358e2dba86f8222";
    public static Location current_location=null;


    public static String converttounix(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEEE, dd MMMM, HH:mm ");
        String formatted=sdf.format(date);
        return  formatted;

    }
    public static String converttoweatherunix(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEE,dd MMM");
        String formatted=sdf.format(date);
        return  formatted;

    }
    public static int converttoweatherunixhour(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("HH");
       String formatted=sdf.format(date);
        return Integer.parseInt(formatted);

    }

    public static String converttounixHour(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("HH:mm");
        String formatted=sdf.format(date);
        return  formatted;

    }
    public static String converttounixDay(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM");
        String formatted=sdf.format(date);
        return  formatted;

    }
    public static String converttounixDayDetails(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("dd MMMM HH:mm ");
        String formatted=sdf.format(date);
        return  formatted;

    }

    public static String converttounixdaily(int dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEE");
        String formatted=sdf.format(date);
        return  formatted;
    }
    public static String converttounixDayDaily(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEEE,dd MMM");
        String formatted=sdf.format(date);
        return  formatted;

    }
}
