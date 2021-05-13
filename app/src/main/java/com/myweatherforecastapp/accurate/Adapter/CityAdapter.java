package com.myweatherforecastapp.accurate.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Model.Daily.DailyResult;
import com.myweatherforecastapp.accurate.R;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    Context context;
    DailyResult dailyResult;

   public  CityAdapter(Context context,DailyResult dailyResult ){
       this.context=context;
       this.dailyResult=dailyResult;
   }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemViewcity= LayoutInflater.from(context).inflate(R.layout.item_city,parent,false);
        return new MyViewHolder(itemViewcity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_date_timecity.setText(new StringBuilder(Common.converttounixdaily( dailyResult.daily.get(position).getDt())));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= prefs.edit();

        if (prefs.getString("key4","").equals("imperial")){
            holder.txt_temperaturecity.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(position).getTemp().getDay())).append("°F"));
        }
         else {
            holder.txt_temperaturecity.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(position).getTemp().getDay())).append("°C"));
        }




        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("01d")){
            holder.img_weathercity.setImageResource(R.drawable.dayclearsky);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("01n")){
            holder.img_weathercity.setImageResource(R.drawable.nightclearsky);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("02n")){
            holder.img_weathercity.setImageResource(R.drawable.nightfewclouds);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("02d")){
            holder.img_weathercity.setImageResource(R.drawable.dayfewclouds);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("03n")){
            holder.img_weathercity.setImageResource(R.drawable.scatteredclouds);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("03d")){
            holder.img_weathercity.setImageResource(R.drawable.scatteredclouds);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("04n")){
            holder.img_weathercity.setImageResource(R.drawable.brokenclouds);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("04d")){
            holder.img_weathercity.setImageResource(R.drawable.brokenclouds);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("09n")){
            holder.img_weathercity.setImageResource(R.drawable.showerrain);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("09d")){
            holder.img_weathercity.setImageResource(R.drawable.showerrain);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("10d")){
            holder.img_weathercity.setImageResource(R.drawable.dayrain);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("10n")){
            holder.img_weathercity.setImageResource(R.drawable.nightrain);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("50d")){
            holder.img_weathercity.setImageResource(R.drawable.mist);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("50n")){
           holder.img_weathercity.setImageResource(R.drawable.mist);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("13d")){
            holder.img_weathercity.setImageResource(R.drawable.snow);
        }
        if(dailyResult.daily.get(position).weather.get(0).getIcon().equals("13n")){
            holder.img_weathercity.setImageResource(R.drawable.snow);
        }


    }

    @Override
    public int getItemCount() {
        return dailyResult.daily.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date_timecity,txt_temperaturecity;
        ImageView img_weathercity;
        LinearLayout linearLayoutcity;
        CardView cardViewcity;

        public MyViewHolder(View itemViewcity) {
            super(itemViewcity);
            img_weathercity = (ImageView) itemViewcity.findViewById(R.id.img_weathercity);
            txt_date_timecity = (TextView) itemViewcity.findViewById(R.id.txt_datecity);
            txt_temperaturecity = (TextView) itemViewcity.findViewById(R.id.txt_temperaturecity);
            linearLayoutcity=(LinearLayout)itemViewcity.findViewById(R.id.linacity_);
            cardViewcity=(CardView)itemViewcity.findViewById(R.id.cardcity_);

        }
    }
}
