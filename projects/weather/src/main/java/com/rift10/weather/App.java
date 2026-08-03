package com.rift10.weather;

public class App {

    private static GUI gui;

    public static void main(String[] args) {
        gui = new GUI();
        while (true) {
            gui.prompt();
        }
    }
}
