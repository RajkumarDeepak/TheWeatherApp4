package com.example.android.theweatherapp4;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Data.Chanel;
import Data.Item;
import Service.YahooWeatherService;
import Service.weatherServiceCallBack;

public class MainActivity extends AppCompatActivity implements weatherServiceCallBack {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dailog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        weatherIconImageView =(ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView =(TextView)findViewById(R.id.temperatureTextView);
        conditionTextView =(TextView)findViewById(R.id.conditionTextView);
        locationTextView =(TextView) findViewById(R.id.locationTextView);


        service =new YahooWeatherService(this);
        dailog =new ProgressDialog(this);
        dailog.setMessage("Loading...");
        dailog.show();


        service.refreshWeather("Fremont, CA");


    }

    @Override
    public void serviceSuccess(Chanel chanel) {
        dailog.hide();

        final Item item = chanel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon"+ item.getCondition().getCode(),null,getPackageName());

        Drawable weatherIconDrawable =getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        temperatureTextView.setText(item.getCondition().getTemperature()+"\u0080"+ chanel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());

        locationTextView.setText(service.getLocation());

    }

    @Override
    public void serviceFailure(Exception exception) {
        dailog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();

    }
}
