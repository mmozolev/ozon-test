package ru.appline.homework.pages;

import org.openqa.selenium.Keys;

public class StartPage extends BasePage {

    /**
     * Метод для поиска товара на сайте
     *
     * @param text - название товара
     * @return SearchPage - переход на страницу поиска
     */
    public SearchPage search(String text) {
        actions.moveToElement(searchLine).click().sendKeys(text).sendKeys(Keys.ENTER).build().perform();
        return app.getSearchPage();
    }
}
