package ru.appline.homework.utils;

import java.util.ArrayList;

public class Cart {

    private static Cart cart;
    public ArrayList<Item> itemList = new ArrayList<>();

    private Cart(){}

    public static Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    public static void disable() {
        cart = null;
    }
}
