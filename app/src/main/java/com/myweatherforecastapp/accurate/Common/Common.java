package com.myweatherforecastapp.accurate.Common;

import android.location.Location;

import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class  Common {
    public  static String []api_keys={"b8648e771a2a0e6b1464cca6ac571d20","124e7b4afa8f81c14015d4d085121496","b3e7813844759c634c07355e32897607","e58181c46f9ecf4771f7f7c012bc1066","32d6d6a4ed95be43305fa5d3152e36f6"};
    public static String api_key=api_keys[new Random().nextInt(api_keys.length)];
    public static Location current_location=null;
    public static Location currentlocation=null;
    public static String  unit;


    public static String converttounix(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEEE, dd MMMM, HH:mm ");
        String formatted=sdf.format(date);
        return  formatted;

    }
    public static String converttounixDaymonth(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("EEE,MMM");
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
    public static int converttoweatherunixminute(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf =new SimpleDateFormat("mm");
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
