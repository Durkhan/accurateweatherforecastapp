package com.myweatherforecastapp.accurate.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.R;
import com.myweatherforecastapp.accurate.WeatherForecastResult;

public class ThreeHourlyDetails extends RecyclerView.Adapter<ThreeHourlyDetails.MYViewHolder>{
        Context context;
        WeatherForecastResult weatherForecastResult;

public  ThreeHourlyDetails(Context context,WeatherForecastResult weatherForecastResult){
        this.context=context;
        this.weatherForecastResult=weatherForecastResult;


        }
@NonNull
@Override
public ThreeHourlyDetails.MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(context).inflate(R.layout.hourly_layout,parent,false);
        return new ThreeHourlyDetails.MYViewHolder(convertView);
        }

@Override
public void onBindViewHolder(@NonNull ThreeHourlyDetails.MYViewHolder holder, int position) {
        holder.txt_date_time.setText(new StringBuilder(Common.converttounixDayDetails(weatherForecastResult.list.get(position).dt)));
        holder.detailsdescription.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));
        holder.detailspressure.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getPressure())).append("hpa"));
        holder.detailshumidity.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getHumidity())).append("%"));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= prefs.edit();
    if (prefs.getString("key4","").equals("imperial")){
        holder.detailstemperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°F"));
    }
    else{
        holder.detailstemperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°C"));
    }


    holder.detailswind.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).getWind().getSpeed())).append("m/s"));
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("01d")){
        holder.detailsimage.setImageResource(R.drawable.dayclearsky);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("01n")){
        holder.detailsimage.setImageResource(R.drawable.nightclearsky);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("02n")){
        holder.detailsimage.setImageResource(R.drawable.dayfewclouds);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("02d")){
        holder.detailsimage.setImageResource(R.drawable.dayfewclouds);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("03n")){
        holder.detailsimage.setImageResource(R.drawable.scatteredclouds);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("03d")){
        holder.detailsimage.setImageResource(R.drawable.scatteredclouds);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("04n")){
        holder.detailsimage.setImageResource(R.drawable.brokenclouds);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("04d")){
        holder.detailsimage.setImageResource(R.drawable.brokenclouds);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("09n")){
        holder.detailsimage.setImageResource(R.drawable.showerrain);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("09d")){
        holder.detailsimage.setImageResource(R.drawable.showerrain);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("10d")){
        holder.detailsimage.setImageResource(R.drawable.dayrain);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("10n")){
        holder.detailsimage.setImageResource(R.drawable.nightrain);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("50d")){
        holder.detailsimage.setImageResource(R.drawable.mist);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("50n")){
        holder.detailsimage.setImageResource(R.drawable.mist);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("13d")){
        holder.detailsimage.setImageResource(R.drawable.snow);
    }
    if(weatherForecastResult.list.get(position).weather.get(0).getIcon().equals("13n")){
        holder.detailsimage.setImageResource(R.drawable.snow);
    }

}



@Override
public int getItemCount() {
        return weatherForecastResult.list.size();
        }

public class MYViewHolder extends RecyclerView.ViewHolder {
    TextView txt_date_time,detailswind,detailsdescription,detailshumidity,detailspressure,detailstemperature;
    ImageView detailsimage;


    public MYViewHolder(@NonNull View convertview) {
        super(convertview);
        detailsimage=(ImageView)convertview.findViewById(R.id.detailicon_);
        txt_date_time=(TextView)convertview.findViewById(R.id.hours_);
        detailsdescription=(TextView)convertview.findViewById(R.id.description_);
        detailshumidity=(TextView)convertview.findViewById(R.id.humidity_);
        detailspressure=(TextView)convertview.findViewById(R.id.pressure_);
        detailswind=(TextView)convertview.findViewById(R.id.wind_);
        detailstemperature=(TextView)convertview.findViewById(R.id.temperature_);

    }
}
    }
