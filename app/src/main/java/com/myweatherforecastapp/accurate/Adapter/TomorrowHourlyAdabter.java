package com.myweatherforecastapp.accurate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Model.Daily.DailyResult;
import com.myweatherforecastapp.accurate.R;

public class TomorrowHourlyAdabter extends RecyclerView.Adapter<TomorrowHourlyAdabter.MyViewHolder>{
    Context context;
    DailyResult dailyResult;

    public  TomorrowHourlyAdabter (Context context,DailyResult dailyResult ){
        this.context=context;
        this.dailyResult=dailyResult;
    }
    @NonNull
    @Override
    public TomorrowHourlyAdabter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tomorrow= LayoutInflater.from(context).inflate(R.layout.hourly_tomorrow_item,parent,false);
        return new TomorrowHourlyAdabter.MyViewHolder(tomorrow);

    }

    @Override
    public void onBindViewHolder(@NonNull TomorrowHourlyAdabter.MyViewHolder holder, int position) {
        holder.date_timecity.setText(new StringBuilder(Common.converttounixHour(dailyResult.hourly.get(position+24-Common.converttoweatherunixhour( dailyResult.hourly.get(0).getDt())).getDt())));
        holder.hourlytemperaturecity.setText(new StringBuilder(String.valueOf(dailyResult.hourly.get(position).getTemp())).append("°"));
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("01d")){
            holder.img_weathercity.setImageResource(R.drawable.dayclearsky);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("01n")){
            holder.img_weathercity.setImageResource(R.drawable.nightclearsky);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("02n")){
            holder.img_weathercity.setImageResource(R.drawable.nightfewclouds);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("02d")){
            holder.img_weathercity.setImageResource(R.drawable.dayfewclouds);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("03n")){
            holder.img_weathercity.setImageResource(R.drawable.scatteredclouds);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("03d")){
            holder.img_weathercity.setImageResource(R.drawable.scatteredclouds);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("04n")){
            holder.img_weathercity.setImageResource(R.drawable.brokenclouds);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("04d")){
            holder.img_weathercity.setImageResource(R.drawable.brokenclouds);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("09n")){
            holder.img_weathercity.setImageResource(R.drawable.showerrain);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("09d")){
            holder.img_weathercity.setImageResource(R.drawable.showerrain);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("10d")){
            holder.img_weathercity.setImageResource(R.drawable.dayrain);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("10n")){
            holder.img_weathercity.setImageResource(R.drawable.nightrain);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("50d")){
            holder.img_weathercity.setImageResource(R.drawable.mist);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("50n")){
            holder.img_weathercity.setImageResource(R.drawable.mist);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("13d")){
            holder.img_weathercity.setImageResource(R.drawable.snow);
        }
        if(dailyResult.hourly.get(position).weather.get(0).getIcon().equals("13n")){
            holder.img_weathercity.setImageResource(R.drawable.snow);
        }


    }

    @Override
    public int getItemCount() {
        return Common.converttoweatherunixhour( dailyResult.hourly.get(0).getDt());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_timecity,hourlytemperaturecity;
        ImageView img_weathercity;
        public MyViewHolder(@NonNull View tomorrow) {
            super(tomorrow);
            date_timecity=tomorrow.findViewById(R.id.hourlydatecity);
            hourlytemperaturecity=tomorrow.findViewById(R.id.hourlytemperaturecity);
            img_weathercity=tomorrow.findViewById(R.id.img_weathercity);
        }
    }
}
