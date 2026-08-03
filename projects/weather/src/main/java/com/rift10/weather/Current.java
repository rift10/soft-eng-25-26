package com.rift10.weather;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class Current {

    @Getter
    @SerializedName("time")
    private String time;

    @Getter
    private int interval;

    @Getter
    @SerializedName("temperature_2m")
    private double temp;
}
