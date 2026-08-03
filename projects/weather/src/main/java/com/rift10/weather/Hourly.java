package com.rift10.weather;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class Hourly {

    @Getter
    @SerializedName("time")
    private ArrayList<String> timestamps;

    @Getter
    @SerializedName("temperature_2m")
    private ArrayList<Double> temps;

    public ArrayList<Integer> getIntTimes() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {
            result.add(getHourFromString(timestamps.get(i)));
        }
        return result;
    }

    public static int getDayFromString(String timestring) {
        return LocalDateTime.parse(timestring).atZone(ZoneId.of("UTC")).getDayOfYear();
    }

    public int getHourFromString(String timestring) {
        return LocalDateTime.parse(timestring).atZone(ZoneId.of("UTC")).getHour();
    }
}
