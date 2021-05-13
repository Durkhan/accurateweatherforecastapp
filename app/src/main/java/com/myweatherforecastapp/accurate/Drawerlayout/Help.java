package com.myweatherforecastapp.accurate.Drawerlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myweatherforecastapp.accurate.MainActivity;
import com.myweatherforecastapp.accurate.R;

public class Help extends AppCompatActivity {
ImageButton backdrawer;
TextView email,openweb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Intent intent=getIntent();
        backdrawer=findViewById(R.id.backdrawer);
        email=findViewById(R.id.openemail);
       openweb=findViewById(R.id.openweb);
        email.setText(R.string.your_string_email);
       openweb.setText(R.string.your_string_web);
        backdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(Help.this, MainActivity.class);
                back .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });
    }
}