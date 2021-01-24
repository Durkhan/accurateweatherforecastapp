package com.weather.accurateweatherforecast.Drawerlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Switch;

import com.weather.accurateweatherforecast.MainActivity;
import com.weather.accurateweatherforecast.NotificationService;
import com.weather.accurateweatherforecast.R;

public class  Settings extends AppCompatActivity {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private int notification_id;
    private RemoteViews remoteViews;
    Context contex;
    Switch notification_switch;
    PreferenceManager preferenceManager;
    ImageButton backdrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent3=getIntent();
        contex=this;
        notification_switch=findViewById(R.id.switchnoti);
        backdrawer=findViewById(R.id.backdrawer);
        SharedPreferences prefs = getSharedPreferences("save",MODE_PRIVATE);


        backdrawer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent back=new Intent(Settings.this, MainActivity.class);
                back .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });
    

        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                    if (isChecked){
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value", true);
                        editor.apply();
                        notification_switch.setChecked(true);
                        startServiceg();
                    }else{
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value", false);
                        editor.apply();
                        notification_switch.setChecked(false);
                        stopServiceg();
                    }

                }

            }
        });





    }
    public void startServiceg() {

            Intent serviceintentent = new Intent(contex, NotificationService.class);
            startService(serviceintentent);

    }
    public void stopServiceg() {
        Intent serviceintentent1=new Intent(contex,NotificationService.class);
        stopService(serviceintentent1);
    }

}