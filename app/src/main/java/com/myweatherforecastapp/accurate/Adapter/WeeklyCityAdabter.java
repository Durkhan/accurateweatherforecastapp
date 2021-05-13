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

public class WeeklyCityAdabter extends RecyclerView.Adapter<WeeklyCityAdabter.MyViewHolder> {
    Context context;
    DailyResult dailyResult;
    public  WeeklyCityAdabter(Context context,DailyResult dailyResult ){
        this.context=context;
        this.dailyResult=dailyResult;
    }
    @NonNull
    @Override
    public WeeklyCityAdabter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertview= LayoutInflater.from(context).inflate(R.layout.sevendaycity_item,parent,false);
        return new WeeklyCityAdabter.MyViewHolder(convertview);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyCityAdabter.MyViewHolder holder, int position) {
        holder.weeklydate.setText(new StringBuilder(Common.converttounixDayDaily(dailyResult.daily.get(position+2).getDt()).substring(0,1).toUpperCase()+Common.converttounixDayDaily(dailyResult.daily.get(position+2).getDt()).substring(1).toLowerCase()));
        holder.dailydaytemperature.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(position+2).getTemp().getMorn())).append("°"));
        holder.dailynighttmpurature.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(position+2).getTemp().getEve())).append("°"));

        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("01d")){
            holder.imagedailydetails.setImageResource(R.drawable.dayclearsky);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("01n")){
            holder.imagedailydetails.setImageResource(R.drawable.nightclearsky);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("02n")){
            holder.imagedailydetails.setImageResource(R.drawable.nightfewclouds);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("02d")){
            holder.imagedailydetails.setImageResource(R.drawable.dayfewclouds);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("03n")){
            holder.imagedailydetails.setImageResource(R.drawable.scatteredclouds);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("03d")){
            holder.imagedailydetails.setImageResource(R.drawable.scatteredclouds);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("04n")){
            holder.imagedailydetails.setImageResource(R.drawable.brokenclouds);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("04d")){
            holder.imagedailydetails.setImageResource(R.drawable.brokenclouds);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("09n")){
            holder.imagedailydetails.setImageResource(R.drawable.showerrain);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("09d")){
            holder.imagedailydetails.setImageResource(R.drawable.showerrain);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("10d")){
            holder.imagedailydetails.setImageResource(R.drawable.dayrain);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("10n")){
            holder.imagedailydetails.setImageResource(R.drawable.nightrain);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("50d")){
            holder.imagedailydetails.setImageResource(R.drawable.mist);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("50n")){
            holder.imagedailydetails.setImageResource(R.drawable.mist);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("13d")){
            holder.imagedailydetails.setImageResource(R.drawable.snow);
        }
        if(dailyResult.daily.get(position+2).weather.get(0).getIcon().equals("13n")){
            holder.imagedailydetails.setImageResource(R.drawable.snow);
        }


    }

    @Override
    public int getItemCount() {
        return dailyResult.daily.size()-2 ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dailydaytemperature,dailynighttmpurature,weeklydate;
        ImageView imagedailydetails;
        public MyViewHolder(@NonNull View convertview) {
            super(convertview);
            imagedailydetails=(ImageView)convertview.findViewById(R.id.dailynextimage_);
            dailydaytemperature=(TextView)convertview.findViewById(R.id.daynexttemperature);
            dailynighttmpurature=(TextView)convertview.findViewById(R.id.nightnexttemperature);
            weeklydate=(TextView)convertview.findViewById(R.id.detsilsnextdate_);
        }
    }
}
