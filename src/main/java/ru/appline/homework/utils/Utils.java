package ru.appline.homework.utils;

public class Utils {

    public static String addOne(String text) {
        return String.valueOf(Integer.parseInt(text) + 1);
    }

    public static int parseAmount(String text) {
        return Integer.parseInt(text.replaceAll("[^\\d]", ""));
    }
}
