package com.weather.accurateweatherforecast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.weather.accurateweatherforecast.Common.Common;
import com.weather.accurateweatherforecast.R;
import com.weather.accurateweatherforecast.WeatherForecastResult;

public class WeatherForecastAdabter extends RecyclerView.Adapter<WeatherForecastAdabter.MyViewHolder> {

  Context context;
  WeatherForecastResult weatherForecastResult;


  public  WeatherForecastAdabter(Context context,WeatherForecastResult weatherForecastResult){
      this.context=context;
      this.weatherForecastResult=weatherForecastResult;


  }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView= LayoutInflater.from(context).inflate(R.layout.item_forecsast,parent,false);
              return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/").
          //      append(weatherForecastResult.list.get(position).weather.get(0).getIcon()).append(".png").toString()).into(holder.img_weather);
        holder.txt_date_time.setText(new StringBuilder(Common.converttounixHour(weatherForecastResult.list.get(position).dt)));
        holder.txt_date_timeday.setText(new StringBuilder(Common.converttounixDay(weatherForecastResult.list.get(position).dt)));
        //holder.txt_description.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));
        holder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°C"));
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("01d")){
            holder.img_weather.setImageResource(R.drawable.dayclearsky);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("01n")){
            holder.img_weather.setImageResource(R.drawable.nightclearsky);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("02n")){
            holder.img_weather.setImageResource(R.drawable.dayfewclouds);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("02d")){
            holder.img_weather.setImageResource(R.drawable.dayfewclouds);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("03n")){
            holder.img_weather.setImageResource(R.drawable.scatteredclouds);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("03d")){
            holder.img_weather.setImageResource(R.drawable.scatteredclouds);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("04n")){
            holder.img_weather.setImageResource(R.drawable.brokenclouds);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("04d")){
            holder.img_weather.setImageResource(R.drawable.brokenclouds);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("09n")){
            holder.img_weather.setImageResource(R.drawable.showerrain);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("09d")){
            holder.img_weather.setImageResource(R.drawable.showerrain);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("10d")){
            holder.img_weather.setImageResource(R.drawable.dayrain);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("10n")){
            holder.img_weather.setImageResource(R.drawable.nightrain);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("50d")){
            holder.img_weather.setImageResource(R.drawable.mist);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("50n")){
            holder.img_weather.setImageResource(R.drawable.mist);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("13d")){
            holder.img_weather.setImageResource(R.drawable.snow);
        }
        if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("13n")){
            holder.img_weather.setImageResource(R.drawable.snow);
        }

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
            TextView txt_date_time,txt_date_timeday,txt_description,txt_temperature;
            ImageView img_weather;
            LinearLayout linearLayout;
            CardView cardView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
                txt_description = (TextView) itemView.findViewById(R.id.txt_description);
                txt_date_time = (TextView) itemView.findViewById(R.id.txt_date);
                txt_date_timeday=(TextView) itemView.findViewById(R.id.txt_dateday);
                txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
                linearLayout=(LinearLayout)itemView.findViewById(R.id.lina_);
                cardView=(CardView)itemView.findViewById(R.id.card_);

            }
        }
    }

