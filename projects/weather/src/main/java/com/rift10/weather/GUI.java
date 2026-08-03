package com.rift10.weather;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import atlas.Atlas;
import atlas.City;

public class GUI {
    private Scanner scanner = new Scanner(System.in);
    private Atlas atlas = new Atlas();
    private List<City> allCities = new ArrayList<>();

    public static final String ANSI_RED = "\u001B[38;5;196m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[38;5;77m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[38;5;57m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static final int START_LAT = 26; // -54 for world
    public static final int END_LAT = 49; // 75 for world
    public static final int START_LONG = -125; // -175 for world
    public static final int END_LONG = -68; // 175 for world

    public GUI() {
        System.out.print("Loading");
        // skips for world
        // if (i > -4 && i < 22) continue;
        // if (i < 5 && j < -85) continue;

        // looping over the latitudes in the US
        for (double i = START_LAT; i < END_LAT; i += 0.25) {
            System.out.print(".");
            // looping over the longitudes
            for (double j = START_LONG; j < END_LONG; j += 0.25) {
                try {
                    allCities.addAll(atlas.findAll(i, j));
                } catch (Exception e) {}
            }
        }
        allCities = allCities.stream().distinct().toList();
        System.out.println();
        System.out.println(
                "Welcome to the command line weather app! Input a city name to get started or type 'random' to get a random city. Only cities in mainland USA are supported.");
    }

    public void prompt() {
        // City city = atlas.find(37.87, -122.27); // berkeley
        City city = null;
        while (city == null) {
            System.out.println("Which city? ");
            String input = scanner.nextLine();
            city = findCity(input);
        }
        update(Report.request(city));
    }

    private City findCity(String cityName) {
        if (clean(cityName).equals("random"))
            return allCities.get((int) (Math.random() * (allCities.size() - 1)));
        List<City> result = new ArrayList<>();
        for (City city : allCities) {
            if (clean(city.name).equals(clean(cityName))) result.add(city);
        }
        if (result.isEmpty()) return null;
        if (result.size() > 1) {
            System.out.println("Which state? ");
            System.out.print("Options: " + result.get(0).admin1);
            for (int i = 1; i < result.size() - 2; i++) {
                System.out.print(", " + result.get(i).admin1);
            }
            System.out.println(", or " + result.get(result.size() - 1).admin1);

            String input = scanner.nextLine();
            for (City city : result) {
                if (clean(city.admin1).equals(clean(input))) return city;
            }
        }
        return result.get(0);
    }

    public void update(Report report) {
        ArrayList<Coordinate> points = new ArrayList<>();
        String startStamp = report.getHourlyStringTimes().get(0);
        int startDay = Hourly.getDayFromString(report.getHourlyStringTimes().get(0));

        for (int i = 0; i < report.getHourlyStringTimes().size(); i++) {
            // we only want to display the data for a day
            if (Hourly.getDayFromString(report.getHourlyStringTimes().get(i)) == startDay) {
                points.add(new Coordinate(
                        report.getHourlyTimes().get(i).intValue(),
                        report.getHourlyTemps().get(i).intValue()));
            }
        }

        City city = atlas.find(report.getLatitude(), report.getLongitude());
        System.out.println();
        System.out.println("Showing temperature data in " +
                city.name + ", " + city.admin1 + " on " +
                OffsetDateTime.parse(startStamp + "+00:00")
                        .format(DateTimeFormatter.ofPattern("EEEE MM/dd/yy")));
        System.out.println("The current temperature is " + report.getCurrent().getTemp() + "°C at " +
                OffsetDateTime.parse(report.getCurrent().getTime() + "+00:00")
                        .format(DateTimeFormatter.ofPattern("hh:mm a")));

        int maxTime = points.stream().map(p -> p.x()).max(Comparator.naturalOrder()).get();
        int maxTemp = points.stream().map(p -> p.y()).max(Comparator.naturalOrder()).get();

        for (int i = maxTemp; i > 0; i--) {
            String color = setColor(i);
            int x = i;
            if (!points.stream().map(p -> p.y()).toList().contains(i)) continue;
            System.out.print(x + "   "); // print out y axis
            // points that have the same y coordinate (temperature), sorted by time
            List<Coordinate> yPoints = points.stream().filter(p -> p.y() == x).sorted().toList();
            System.out.print(color + "    ".repeat(yPoints.get(0).x()) + "o   ");
            for (int j = 1; j < yPoints.size(); j++) {
                System.out.print("    ".repeat(yPoints.get(j).x() - yPoints.get(j - 1).x() - 1) + "o   ");
            }
            System.out.println(ANSI_RESET); // reset text color back to normal
        }
        // print out x axis
        System.out.print("     ");
        for (int i = 0; i < maxTime + 1; i++) {
            System.out.print(i + (i < 10 ? "   " : "  "));
        }
        System.out.println();
    }

    private String setColor(int temp) {
        if (temp < 10) return ANSI_PURPLE;
        if (temp < 15) return ANSI_BLUE;
        if (temp < 20) return ANSI_GREEN;
        if (temp < 25) return ANSI_YELLOW;
        if (temp < 30) return ANSI_ORANGE;
        return ANSI_RED;
    }

    private String clean(String string) {
        return string.strip().toLowerCase();
    }
}
