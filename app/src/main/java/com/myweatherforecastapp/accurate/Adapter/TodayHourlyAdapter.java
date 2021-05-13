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

public class TodayHourlyAdapter extends RecyclerView.Adapter<TodayHourlyAdapter.MyViewHolder> {
    Context context;
    DailyResult dailyResult;


    public  TodayHourlyAdapter(Context context,DailyResult dailyResult ){
        this.context=context;
        this.dailyResult=dailyResult;
    }
    @NonNull
    @Override
    public TodayHourlyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todayhourly= LayoutInflater.from(context).inflate(R.layout.todayhourly_item,parent,false);
        return new MyViewHolder(todayhourly);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayHourlyAdapter.MyViewHolder holder, int position) {
        holder.date_timecity.setText(new StringBuilder(Common.converttounixHour( dailyResult.hourly.get(position+1).getDt())));
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
        return 23-Common.converttoweatherunixhour(dailyResult.hourly.get(0).getDt());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_timecity,hourlytemperaturecity;
        ImageView img_weathercity;
        public MyViewHolder(@NonNull View todayHourly) {
            super(todayHourly);
            date_timecity=todayHourly.findViewById(R.id.hourlydatecity);
            hourlytemperaturecity=todayHourly.findViewById(R.id.hourlytemperaturecity);
            img_weathercity=todayHourly.findViewById(R.id.img_weathercity);
        }
    }
}
