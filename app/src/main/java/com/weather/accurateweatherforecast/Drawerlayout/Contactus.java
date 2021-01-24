package com.weather.accurateweatherforecast.Drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.weather.accurateweatherforecast.MainActivity;
import com.weather.accurateweatherforecast.R;

public class Contactus extends AppCompatActivity {
    private static final String TAG =null;
    ImageButton backdrawer;
    TextView myemail,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateus);
        final Intent intent=getIntent();
        backdrawer=findViewById(R.id.backdrawer);
        backdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(Contactus.this, MainActivity.class);
                back .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });
        myemail=findViewById(R.id.openmyemail);
        email=findViewById(R.id.openemail);
        email.setText(R.string.your_string_email);
        myemail.setText(R.string.your_string);


    }
}