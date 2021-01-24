package com.weather.accurateweatherforecast.Retrofit;

import com.weather.accurateweatherforecast.Model.Daily.DailyResult;
import com.weather.accurateweatherforecast.WeatherForecastResult;
import com.weather.accurateweatherforecast.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMapApi {
    @GET("weather")
    Observable<WeatherResult>getweatherresultbylatlon(@Query("lat") String lat,
                                                      @Query("lon") String lon,
                                                      @Query("appid") String appid,
                                                      @Query("units") String  unit);
    @GET("forecast")
    Observable<WeatherForecastResult>getforecastweatherresultbylatlon(@Query("lat") String lat,
                                                                      @Query("lon") String lon,
                                                                      @Query("appid") String appid,
                                                                      @Query("units") String  unit);
    @GET("onecall")
    Observable<DailyResult>getforecastweatherresultdailycity(@Query("lat") String lat,
                                                             @Query("lon") String lon,
                                                            @Query("%20exclude") String exclude,
                                                            @Query("appid") String appid,
                                                            @Query("units") String  unit);
    @GET("weather")
    Observable<WeatherResult>getweatherresultbycity(@Query("q") String cityName,
                                                      @Query("appid") String appid,
                                                      @Query("units") String  unit);
}
