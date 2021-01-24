package com.weather.accurateweatherforecast.Drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.weather.accurateweatherforecast.MainActivity;
import com.weather.accurateweatherforecast.R;

public class About extends AppCompatActivity {
     ImageButton backdrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent=getIntent();
        backdrawer=findViewById(R.id.backdrawer);
        backdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(About.this, MainActivity.class);
                back .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });


    }
}