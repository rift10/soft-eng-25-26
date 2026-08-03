package com.rift10.weather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import atlas.City;
import lombok.Getter;

public class Report {

    @Getter
    private double latitude;

    @Getter
    private double longitude;

    @Getter
    @SerializedName("generationtime_ms")
    private double generationTime;

    @Getter
    @SerializedName("utc_offset_seconds")
    private double offsetSeconds;

    @Getter
    private String timezone;

    @Getter
    @SerializedName("timezone_abbreviation")
    private String timezoneAbbr;

    @Getter
    private double elevation;

    @Getter
    private Hourly hourly;

    @Getter
    private Current current;

    private static Gson gson = new Gson();
    private static HttpClient client = HttpClient.newHttpClient();

    public Report() {}

    public static Report request(City city) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.open-meteo.com/v1/forecast?latitude=" + city.latitude
                        + "&longitude=" + city.longitude + "&hourly=temperature_2m&current=temperature_2m&timezone=auto"))
                .GET().build();
        try {
            String result = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            Report report = gson.fromJson(result, Report.class);

            return report;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error during API call: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getHourlyStringTimes() {
        return hourly.getTimestamps();
    }

    public ArrayList<Integer> getHourlyTimes() {
        return hourly.getIntTimes();
    }

    public ArrayList<Double> getHourlyTemps() {
        return hourly.getTemps();
    }
}