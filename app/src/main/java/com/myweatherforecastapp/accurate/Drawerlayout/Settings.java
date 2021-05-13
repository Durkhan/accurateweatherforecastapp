package com.myweatherforecastapp.accurate.Drawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;

import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.MainActivity;
import com.myweatherforecastapp.accurate.NotificationService;
import com.myweatherforecastapp.accurate.R;

public class  Settings extends AppCompatActivity {
    Context contex;
    RadioButton fahrenheit,celsius;
    Switch notification_switch;
    ImageButton backdrawer;
    public static final String SHARED_PREFS="shared_prefs";
    private boolean farhenheiton,celsiuson;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    public static final String button="button";
    public static final String button1="button1";
    public static final String TEXT="text";
    public static final String TEXt="text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent3 = getIntent();
        contex = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        notification_switch = findViewById(R.id.switchnoti);
        backdrawer = findViewById(R.id.backdrawer);
        fahrenheit=findViewById(R.id.fahreit);
        celsius=findViewById(R.id.celsius);




        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    if (isChecked) {
                        SharedPreferences preferences=getSharedPreferences("save",MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor1.putBoolean("value", true);
                        editor1.apply();
                        notification_switch.setChecked(true);
                        startServiceg();
                    } else {
                        SharedPreferences preferences=getSharedPreferences("save",MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor1.putBoolean("value", false);
                        editor1.apply();
                        notification_switch.setChecked(false);
                        stopServiceg();
                    }

                }

            }
        });
        SharedPreferences preferences=getSharedPreferences("save",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = getSharedPreferences("save", MODE_PRIVATE).edit();
        notification_switch.setChecked(preferences.getBoolean("value",false));
        fahrenheit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                celsius.setChecked(false);
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor shared=sharedPreferences.edit();
                shared.putString(TEXT,"imperial");
                shared.putBoolean(button,fahrenheit.isChecked());
                shared.apply();
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor shared=sharedPreferences.edit();
        farhenheiton=sharedPreferences.getBoolean(button,false);
        fahrenheit.setChecked(farhenheiton);
        sharedPreferences.getString(TEXT,"");

        celsius.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (celsius.isPressed()){
                    fahrenheit.setChecked(false);
                    celsius.setChecked(true);
                }

            }
        });





        backdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fahrenheit.isChecked()==false){
                    editor.putString("key4","metric");
                    editor.apply();

                }
                else{

                    prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    editor = prefs.edit();
                    editor.putString("key4","imperial");
                    editor.apply();
                }
                Intent back = new Intent(Settings.this, MainActivity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });





    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fahrenheit.isChecked()==false){
            editor.putString("key4","metric");
            editor.apply();

        }
        else{
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = prefs.edit();
            editor.putString("key4","imperial");
            editor.apply();
        }
        Intent back = new Intent(Settings.this, MainActivity.class);
        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(back);
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