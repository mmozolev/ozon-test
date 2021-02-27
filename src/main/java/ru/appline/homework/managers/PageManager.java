package ru.appline.homework.managers;

import ru.appline.homework.pages.CartPage;
import ru.appline.homework.pages.SearchPage;
import ru.appline.homework.pages.StartPage;

public class PageManager {

    private static PageManager pageManager;

    private static StartPage startPage;

    private static SearchPage searchPage;

    private static CartPage cartPage;

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    public static void clearPage() {
        startPage = null;
        searchPage = null;
        cartPage = null;
    }



}
