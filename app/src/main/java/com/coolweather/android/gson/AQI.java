package com.coolweather.android.gson;

/**
 * Created by nano on 2017/3/28.
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
