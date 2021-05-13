package com.myweatherforecastapp.accurate;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.tabs.TabLayout;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Model.Daily.DailyResult;
import com.myweatherforecastapp.accurate.Retrofit.OpenMapApi;
import com.myweatherforecastapp.accurate.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Weatherhourlywidget extends AppWidgetProvider {
    static RemoteViews remoteViews;
     String units;
    private void updateAppWidget( final Context context,
                                 AppWidgetManager appWidgetManager,
                                 int appWidgetId){
        Intent intent=new Intent(context,MainActivity.class);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if(prefs.getString("key4","").equals("imperial")){
            units=("imperial");

        }
        else{
            units=("metric");
        }
        remoteViews=new RemoteViews(context.getPackageName(),R.layout.hourlywidget);
        remoteViews.setOnClickPendingIntent(R.id.widget,pendingIntent);
        String cityname=prefs.getString("city","");
        String countryname=prefs.getString("country","");
        String descr=prefs.getString("descr","");
        String temp=prefs.getString("temp","");
        String windspeed=prefs.getString("windspeed","");
        String humidity=prefs.getString("humidity","");
        String timezone=prefs.getString("timezone","");
        String mintemp=prefs.getString("mintemp","");
        String maxtemp=prefs.getString("maxtemp","");
        String uvi=prefs.getString("uvi","");
        String datex1= prefs.getString("date1","");
        String datex2= prefs.getString("date2","");
        String datex3= prefs.getString("date3","");
        String datex4= prefs.getString("date4","");
        String datex5= prefs.getString("date5","");
        String datex6= prefs.getString("date6","");
        String temp1= prefs.getString("temp1","");
        String temp2= prefs.getString("temp2","");
        String temp3= prefs.getString("temp3","");
        String temp4= prefs.getString("temp4","");
        String temp5= prefs.getString("temp5","");
        String temp6= prefs.getString("temp6","");

        remoteViews.setTextViewText(R.id.datetemp1,temp1);
        remoteViews.setTextViewText(R.id.datetemp2,temp2);
        remoteViews.setTextViewText(R.id.datetemp3,temp3);
        remoteViews.setTextViewText(R.id.datetemp4,temp4);
        remoteViews.setTextViewText(R.id.datetemp5,temp5);
        remoteViews.setTextViewText(R.id.datetemp,temp6);
        remoteViews.setTextViewText(R.id.datewidget1,datex1);
        remoteViews.setTextViewText(R.id.datewidget2,datex2);
        remoteViews.setTextViewText(R.id.datewidget3,datex3);
        remoteViews.setTextViewText(R.id.datewidget4,datex4);
        remoteViews.setTextViewText(R.id.datewidget5,datex5);
        remoteViews.setTextViewText(R.id.datewidget,datex6);
        remoteViews.setTextViewText(R.id.timezone,timezone);
        remoteViews.setTextViewText(R.id.mintemp,mintemp);
        remoteViews.setTextViewText(R.id.undex,uvi);
        remoteViews.setTextViewText(R.id.maxtemp,maxtemp);
        remoteViews.setTextViewText(R.id.descss,descr);
        remoteViews.setTextViewText(R.id.humidity,humidity);
        remoteViews.setTextViewText(R.id.wind_,windspeed);
        remoteViews.setTextViewText(R.id.citytempurature,temp);
        remoteViews.setTextViewText(R.id.cityName,cityname+",");
        remoteViews.setTextViewText(R.id.country,countryname);
        if(prefs.getString("key4","").equals("imperial")){
            remoteViews.setTextViewText(R.id.farheitwidget,"°F");
        }
        else{
            units=("metric");
        }

        //position1
        if(prefs.getString("first_hour","").equals("01d")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetsunny);
        }
        if(prefs.getString("first_hour","").equals("01n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetnightclearsky);
        }
        if(prefs.getString("first_hour","").equals("02d")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("first_hour","").equals("02n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("first_hour","").equals("03d") || prefs.getString("first_hour","").equals("03n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetscattered_clouds);
        }
        if(prefs.getString("first_hour","").equals("04d") || prefs.getString("first_hour","").equals("04n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetbrokenclouds);
        }
        if(prefs.getString("first_hour","").equals("09d") || prefs.getString("first_hour","").equals("09n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetshowerrain);
        }
        if(prefs.getString("first_hour","").equals("10d")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetrainyday);
        }
        if(prefs.getString("first_hour","").equals("10n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetrainynight);
        }
        if(prefs.getString("first_hour","").equals("11d") || prefs.getString("first_hour","").equals("11n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetthunderstorm);
        }
        if(prefs.getString("first_hour","").equals("13d") || prefs.getString("first_hour","").equals("13n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetsnow);
        }
        if(prefs.getString("first_hour","").equals("50d") || prefs.getString("first_hour","").equals("50n")){
            remoteViews.setImageViewResource(R.id.widgeticon1,R.drawable.widgetmist);
        }

        //postion2
        if(prefs.getString("second_hour","").equals("01d")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetsunny);
        }
        if(prefs.getString("second_hour","").equals("01n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetnightclearsky);
        }
        if(prefs.getString("second_hour","").equals("02d")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("second_hour","").equals("02n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("second_hour","").equals("03d") || prefs.getString("second_hour","").equals("03n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetscattered_clouds);
        }
        if(prefs.getString("second_hour","").equals("04d") || prefs.getString("second_hour","").equals("04n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetbrokenclouds);
        }
        if(prefs.getString("second_hour","").equals("09d") || prefs.getString("second_hour","").equals("09n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetshowerrain);
        }
        if(prefs.getString("second_hour","").equals("10d")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetrainyday);
        }
        if(prefs.getString("second_hour","").equals("10n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetrainynight);
        }
        if(prefs.getString("second_hour","").equals("11d") || prefs.getString("second_hour","").equals("11n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetthunderstorm);
        }
        if(prefs.getString("second_hour","").equals("13d") || prefs.getString("second_hour","").equals("13n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetsnow);
        }
        if(prefs.getString("second_hour","").equals("50d") || prefs.getString("second_hour","").equals("50n")){
            remoteViews.setImageViewResource(R.id.widgeticon2,R.drawable.widgetmist);
        }

        //postion3
        if(prefs.getString("third_hour","").equals("01d")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetsunny);
        }
        if(prefs.getString("third_hour","").equals("01n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetnightclearsky);
        }
        if(prefs.getString("third_hour","").equals("02d")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("third_hour","").equals("02n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("third_hour","").equals("03d") || prefs.getString("third_hour","").equals("03n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetscattered_clouds);
        }
        if(prefs.getString("third_hour","").equals("04d") || prefs.getString("third_hour","").equals("04n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetbrokenclouds);
        }
        if(prefs.getString("third_hour","").equals("09d") || prefs.getString("third_hour","").equals("09n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetshowerrain);
        }
        if(prefs.getString("third_hour","").equals("10d")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetrainyday);
        }
        if(prefs.getString("third_hour","").equals("10n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetrainynight);
        }
        if(prefs.getString("third_hour","").equals("11d") || prefs.getString("third_hour","").equals("11n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetthunderstorm);
        }
        if(prefs.getString("third_hour","").equals("13d") || prefs.getString("third_hour","").equals("13n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetsnow);
        }
        if(prefs.getString("third_hour","").equals("50d") || prefs.getString("third_hour","").equals("50n")){
            remoteViews.setImageViewResource(R.id.widgeticon3,R.drawable.widgetmist);
        }

        //postion4
        if(prefs.getString("fourth_hour","").equals("01d")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetsunny);
        }
        if(prefs.getString("fourth_hour","").equals("01n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetnightclearsky);
        }
        if(prefs.getString("fourth_hour","").equals("02d")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("fourth_hour","").equals("02n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("fourth_hour","").equals("03d") || prefs.getString("fourth_hour","").equals("03n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetscattered_clouds);
        }
        if(prefs.getString("fourth_hour","").equals("04d") || prefs.getString("fourth_hour","").equals("04n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetbrokenclouds);
        }
        if(prefs.getString("fourth_hour","").equals("09d") || prefs.getString("fourth_hour","").equals("09n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetshowerrain);
        }
        if(prefs.getString("fourth_hour","").equals("10d")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetrainyday);
        }
        if(prefs.getString("fourth_hour","").equals("10n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetrainynight);
        }
        if(prefs.getString("fourth_hour","").equals("11d") || prefs.getString("fourth_hour","").equals("11n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetthunderstorm);
        }
        if(prefs.getString("fourth_hour","").equals("13d") || prefs.getString("fourth_hour","").equals("13n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetsnow);
        }
        if(prefs.getString("fourth_hour","").equals("50d") || prefs.getString("fourth_hour","").equals("50n")){
            remoteViews.setImageViewResource(R.id.widgeticon4,R.drawable.widgetmist);
        }

        //postion5
        if(prefs.getString("fifth_hour","").equals("01d")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetsunny);
        }
        if(prefs.getString("fifth_hour","").equals("01n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetnightclearsky);
        }
        if(prefs.getString("fifth_hour","").equals("02d")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("fifth_hour","").equals("02n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("fifth_hour","").equals("03d") || prefs.getString("fifth_hour","").equals("03n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetscattered_clouds);
        }
        if(prefs.getString("fifth_hour","").equals("04d") || prefs.getString("fifth_hour","").equals("04n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetbrokenclouds);
        }
        if(prefs.getString("fifth_hour","").equals("09d") || prefs.getString("fifth_hour","").equals("09n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetshowerrain);
        }
        if(prefs.getString("fifth_hour","").equals("10d")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetrainyday);
        }
        if(prefs.getString("fifth_hour","").equals("10n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetrainynight);
        }
        if(prefs.getString("fifth_hour","").equals("11d") || prefs.getString("fifth_hour","").equals("11n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetthunderstorm);
        }
        if(prefs.getString("fifth_hour","").equals("13d") || prefs.getString("fifth_hour","").equals("13n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetsnow);
        }
        if(prefs.getString("fifth_hour","").equals("50d") || prefs.getString("fifth_hour","").equals("50n")){
            remoteViews.setImageViewResource(R.id.widgeticon5,R.drawable.widgetmist);
        }

        //postion6
        if(prefs.getString("sixth_hour","").equals("01d")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetsunny);
        }
        if(prefs.getString("sixth_hour","").equals("01n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetnightclearsky);
        }
        if(prefs.getString("sixth_hour","").equals("02d")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("sixth_hour","").equals("02n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetfewcloudsday);
        }
        if(prefs.getString("sixth_hour","").equals("03d") || prefs.getString("sixth_hour","").equals("03n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetscattered_clouds);
        }
        if(prefs.getString("sixth_hour","").equals("04d") || prefs.getString("sixth_hour","").equals("04n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetbrokenclouds);
        }
        if(prefs.getString("sixth_hour","").equals("09d") || prefs.getString("sixth_hour","").equals("09n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetshowerrain);
        }
        if(prefs.getString("sixth_hour","").equals("10d")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetrainyday);
        }
        if(prefs.getString("sixth_hour","").equals("10n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetrainynight);
        }
        if(prefs.getString("sixth_hour","").equals("11d") || prefs.getString("sixth_hour","").equals("11n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetthunderstorm);
        }
        if(prefs.getString("sixth_hour","").equals("13d") || prefs.getString("sixth_hour","").equals("13n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetsnow);
        }
        if(prefs.getString("sixth_hour","").equals("50d") || prefs.getString("sixth_hour","").equals("50n")){
            remoteViews.setImageViewResource(R.id.widgeticon,R.drawable.widgetmist);
        }
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);





    }
    @Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
