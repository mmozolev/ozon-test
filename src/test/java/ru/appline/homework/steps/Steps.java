package ru.appline.homework.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.appline.homework.managers.PageManager;

public class Steps {

    private PageManager app = PageManager.getPageManager();

    @Когда("^Загружена стартовая страница$")
    public void initPage() {
        app.getStartPage();
    }

    @Когда("^Найти '(.*)'$")
    public void search(String name) {
        app.getStartPage().search(name);
    }

    @Когда("^Установить фильтр$")
    public void setFilter(DataTable dataTable) {
        dataTable.cells().forEach(raw -> {
            app.getSearchPage().setFilter((raw.get(0)), (raw.get(1)));
        });
    }

    @Когда("^Добавить в корзину (\\d+) элементов$")
    public void addToCart(int number) {
        app.getSearchPage().addToCart(number);
    }

//    @Когда("^Перейти на страницу Корзина$")
//    public void goToCartPage() {
//        app.getSearchPage().goToCartPage();
//    }

    @Когда("^Добавить информацию о товарах$")
    public void addInfo() {
        app.getCartPage().addInfo();
    }

    @Когда("^Проверить наличие товаров в корзине")
    public void checkCart() {
        app.getCartPage().checkCart();
    }

    @Когда("^Удалить все товары из корзины")
    public void deleteFromCart() {
        app.getCartPage().deleteFromCart();
    }

    @Когда("^Проверить корзину на отсутствие товаров")
    public void checkCartEmpty() {
        app.getCartPage().checkEmpty();
    }




}
