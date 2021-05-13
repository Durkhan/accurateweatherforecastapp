package com.myweatherforecastapp.accurate;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;


public class WeatheRDailyWidget  extends AppWidgetProvider {

    static RemoteViews remoteViews;

    private void updateAppWidget(final Context context,
                                 AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        String units;
        if(prefs.getString("key4","").equals("imperial")){
            units=("imperial");
        }
        else{
            units=("metric");
        }
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.dailywidget);
        remoteViews.setOnClickPendingIntent(R.id.dailywidget, pendingIntent);
        String cityname = prefs.getString("city", "").toUpperCase();
        String temp = prefs.getString("temp", "");
        String day1 = prefs.getString("day1", "");
        String day2 = prefs.getString("day2", "");
        String day3 = prefs.getString("day3", "");
        String day4 = prefs.getString("day4", "");
        String day5 = prefs.getString("day5", "");
        String hour = prefs.getString("hour", "");
        String feelslike = prefs.getString("feelslike", "");
        String day_month = prefs.getString("day_month", "");
        remoteViews.setTextViewText(R.id.widgetday_month, day_month.toUpperCase());
        remoteViews.setTextViewText(R.id.widget_feelslike, feelslike);
        remoteViews.setTextViewText(R.id.hour_, hour);

        remoteViews.setTextViewText(R.id.datewidget1, day1);
        remoteViews.setTextViewText(R.id.datewidget2, day2);
        remoteViews.setTextViewText(R.id.datewidget3, day3);
        remoteViews.setTextViewText(R.id.datewidget4, day4);
        remoteViews.setTextViewText(R.id.datewidget5, day5);
        remoteViews.setTextViewText(R.id.widgetcity, " |" + cityname);
        remoteViews.setTextViewText(R.id.widgettemp, temp + "°");
        remoteViews.setTextViewText(R.id.maxmindatetemp, prefs.getString("mintemp0", "") + "°/" + prefs.getString("maxtemp0", "") + "°");
        remoteViews.setTextViewText(R.id.datetemp1, prefs.getString("mintemp1", "") + "°/" + prefs.getString("maxtemp1", "") + "°");
        remoteViews.setTextViewText(R.id.datetemp2, prefs.getString("mintemp2", "") + "°/" + prefs.getString("maxtemp2", "") + "°");
        remoteViews.setTextViewText(R.id.datetemp3, prefs.getString("mintemp3", "") + "°/" + prefs.getString("maxtemp3", "") + "°");
        remoteViews.setTextViewText(R.id.datetemp4, prefs.getString("mintemp4", "") + "°/" + prefs.getString("maxtemp4", "") + "°");
        remoteViews.setTextViewText(R.id.datetemp5, prefs.getString("mintemp5", "") + "°/" + prefs.getString("maxtemp5", "") + "°");


        //position0
        if (prefs.getString("day_icon0", "").equals("01d")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetsunny);
        }
        if (prefs.getString("day_icon0", "").equals("01n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetnightclearsky);
        }
        if (prefs.getString("day_icon0", "").equals("02d")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon0", "").equals("02n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetfewcloudsnight);
        }
        if (prefs.getString("day_icon0", "").equals("03d") || prefs.getString("day_icon0", "").equals("03n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetscattered_clouds);
        }
        if (prefs.getString("day_icon0", "").equals("04d") || prefs.getString("day_icon0", "").equals("04n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetbrokenclouds);
        }
        if (prefs.getString("day_icon0", "").equals("09d") || prefs.getString("day_icon0", "").equals("09n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetshowerrain);
        }
        if (prefs.getString("day_icon0", "").equals("10d")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetrainyday);
        }
        if (prefs.getString("day_icon0", "").equals("10n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetrainynight);
        }
        if (prefs.getString("day_icon0", "").equals("11d") || prefs.getString("day_icon0", "").equals("11n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetthunderstorm);
        }
        if (prefs.getString("day_icon0", "").equals("13d") || prefs.getString("day_icon0", "").equals("13n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetsnow);
        }
        if (prefs.getString("day_icon0", "").equals("50d") || prefs.getString("day_icon0", "").equals("50n")) {
            remoteViews.setImageViewResource(R.id.widgeticon, R.drawable.widgetmist);
        }

        //position1
        if (prefs.getString("day_icon1", "").equals("01d")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetsunny);
        }
        if (prefs.getString("day_icon1", "").equals("01n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetnightclearsky);
        }
        if (prefs.getString("day_icon1", "").equals("02d")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon1", "").equals("02n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon1", "").equals("03d") || prefs.getString("day_icon1", "").equals("03n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetscattered_clouds);
        }
        if (prefs.getString("day_icon1", "").equals("04d") || prefs.getString("day_icon1", "").equals("04n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetbrokenclouds);
        }
        if (prefs.getString("day_icon1", "").equals("09d") || prefs.getString("day_icon1", "").equals("09n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetshowerrain);
        }
        if (prefs.getString("day_icon1", "").equals("10d")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetrainyday);
        }
        if (prefs.getString("day_icon1", "").equals("10n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetrainynight);
        }
        if (prefs.getString("day_icon1", "").equals("11d") || prefs.getString("day_icon1", "").equals("11n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetthunderstorm);
        }
        if (prefs.getString("day_icon1", "").equals("13d") || prefs.getString("day_icon1", "").equals("13n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetsnow);
        }
        if (prefs.getString("day_icon1", "").equals("50d") || prefs.getString("day_icon1", "").equals("50n")) {
            remoteViews.setImageViewResource(R.id.widgeticon1, R.drawable.widgetmist);
        }
        //position2
        if (prefs.getString("day_icon2", "").equals("01d")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetsunny);
        }
        if (prefs.getString("day_icon2", "").equals("01n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetnightclearsky);
        }
        if (prefs.getString("day_icon2", "").equals("02d")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon2", "").equals("02n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon2", "").equals("03d") || prefs.getString("day_icon2", "").equals("03n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetscattered_clouds);
        }
        if (prefs.getString("day_icon2", "").equals("04d") || prefs.getString("day_icon2", "").equals("04n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetbrokenclouds);
        }
        if (prefs.getString("day_icon2", "").equals("09d") || prefs.getString("day_icon2", "").equals("09n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetshowerrain);
        }
        if (prefs.getString("day_icon2", "").equals("10d")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetrainyday);
        }
        if (prefs.getString("day_icon2", "").equals("10n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetrainynight);
        }
        if (prefs.getString("day_icon2", "").equals("11d") || prefs.getString("day_icon2", "").equals("11n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetthunderstorm);
        }
        if (prefs.getString("day_icon2", "").equals("13d") || prefs.getString("day_icon2", "").equals("13n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetsnow);
        }
        if (prefs.getString("day_icon2", "").equals("50d") || prefs.getString("day_icon2", "").equals("50n")) {
            remoteViews.setImageViewResource(R.id.widgeticon2, R.drawable.widgetmist);
        }
        //position3
        if (prefs.getString("day_icon3", "").equals("01d")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetsunny);
        }
        if (prefs.getString("day_icon3", "").equals("01n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetnightclearsky);
        }
        if (prefs.getString("day_icon3", "").equals("02d")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon3", "").equals("02n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon3", "").equals("03d") || prefs.getString("day_icon3", "").equals("03n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetscattered_clouds);
        }
        if (prefs.getString("day_icon3", "").equals("04d") || prefs.getString("day_icon3", "").equals("04n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetbrokenclouds);
        }
        if (prefs.getString("day_icon3", "").equals("09d") || prefs.getString("day_icon3", "").equals("09n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetshowerrain);
        }
        if (prefs.getString("day_icon3", "").equals("10d")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetrainyday);
        }
        if (prefs.getString("day_icon3", "").equals("10n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetrainynight);
        }
        if (prefs.getString("day_icon3", "").equals("11d") || prefs.getString("day_icon3", "").equals("11n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetthunderstorm);
        }
        if (prefs.getString("day_icon3", "").equals("13d") || prefs.getString("day_icon3", "").equals("13n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetsnow);
        }
        if (prefs.getString("day_icon3", "").equals("50d") || prefs.getString("day_icon3", "").equals("50n")) {
            remoteViews.setImageViewResource(R.id.widgeticon3, R.drawable.widgetmist);
        }
        //position4
        if (prefs.getString("day_icon4", "").equals("01d")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetsunny);
        }
        if (prefs.getString("day_icon4", "").equals("01n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetnightclearsky);
        }
        if (prefs.getString("day_icon4", "").equals("02d")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon4", "").equals("02n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon4", "").equals("03d") || prefs.getString("day_icon4", "").equals("03n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetscattered_clouds);
        }
        if (prefs.getString("day_icon4", "").equals("04d") || prefs.getString("day_icon4", "").equals("04n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetbrokenclouds);
        }
        if (prefs.getString("day_icon4", "").equals("09d") || prefs.getString("day_icon4", "").equals("09n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetshowerrain);
        }
        if (prefs.getString("day_icon4", "").equals("10d")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetrainyday);
        }
        if (prefs.getString("day_icon4", "").equals("10n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetrainynight);
        }
        if (prefs.getString("day_icon4", "").equals("11d") || prefs.getString("day_icon4", "").equals("11n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetthunderstorm);
        }
        if (prefs.getString("day_icon4", "").equals("13d") || prefs.getString("day_icon4", "").equals("13n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetsnow);
        }
        if (prefs.getString("day_icon4", "").equals("50d") || prefs.getString("day_icon4", "").equals("50n")) {
            remoteViews.setImageViewResource(R.id.widgeticon4, R.drawable.widgetmist);
        }

        //position5
        if (prefs.getString("day_icon5", "").equals("01d")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetsunny);
        }
        if (prefs.getString("day_icon5", "").equals("01n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetnightclearsky);
        }
        if (prefs.getString("day_icon5", "").equals("02d")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon5", "").equals("02n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetfewcloudsday);
        }
        if (prefs.getString("day_icon5", "").equals("03d") || prefs.getString("day_icon5", "").equals("03n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetscattered_clouds);
        }
        if (prefs.getString("day_icon5", "").equals("04d") || prefs.getString("day_icon5", "").equals("04n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetbrokenclouds);
        }
        if (prefs.getString("day_icon5", "").equals("09d") || prefs.getString("day_icon5", "").equals("09n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetshowerrain);
        }
        if (prefs.getString("day_icon5", "").equals("10d")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetrainyday);
        }
        if (prefs.getString("day_icon5", "").equals("10n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetrainynight);
        }
        if (prefs.getString("day_icon5", "").equals("11d") || prefs.getString("day_icon5", "").equals("11n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetthunderstorm);
        }
        if (prefs.getString("day_icon5", "").equals("13d") || prefs.getString("day_icon5", "").equals("13n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetsnow);
        }
        if (prefs.getString("day_icon5", "").equals("50d") || prefs.getString("day_icon5", "").equals("50n")) {
            remoteViews.setImageViewResource(R.id.widgeticon5, R.drawable.widgetmist);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }


}