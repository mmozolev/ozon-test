package ru.appline.homework.pages;


import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.homework.utils.Item;
import ru.appline.homework.utils.Utils;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(xpath = "//div[contains(text(), 'Цена')]/..//input[@qa-id='range-from']")
    WebElement priceRangeFrom;

    @FindBy(xpath = "//div[contains(text(), 'Цена')]/..//input[@qa-id='range-to']")
    WebElement priceRangeTo;

    @FindBy(xpath = "//div[@value='Высокий рейтинг']//input")
    WebElement highRating;

    @FindBy(xpath = "//span[text()='NFC']/../..//input")
    WebElement NFC;

    @FindBy(xpath = "//div[@data-widget='searchResultsFiltersActive']//span")
    List<WebElement> activeFilters;

    @FindBy(xpath = "//div[contains(@style, 'grid-column-start')]")
    List<WebElement> products;

    @FindBy(xpath = "//a[@data-widget='cart']//span[contains(@class, 'f-caption--bold')]")
    WebElement numberOfProducts;

    @FindBy(xpath = "//a[@data-widget='cart']")
    WebElement cartButton;

    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//span[contains(text(), 'Посмотреть все')]")
    WebElement extendedFilterSearch;

    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//input")
    WebElement extendedFilterInput;

    @FindBy(xpath = "//div[contains(text(), 'Бренды')]")
    WebElement brandFilter;

    @FindBy(xpath = "//div[text()='Дальше']")
    WebElement toNextPage;


    /**
     * Метод устанавливающий фильтры
     *
     * @param name  - название фильтра
     * @param value - значение для фильтра
     * @return остаемся на странице
     */
    public SearchPage setFilter(String name, String value) {
        int number = activeFilters.size();
        switch (name.toLowerCase()) {
            case ("цена до"):
                setAmount(number, value, name.toLowerCase());
                break;
            case ("высокий рейтинг"):
                setSingleFilter(number, highRating);
                break;
            case ("nfc"):
                setSingleFilter(number, NFC);
                break;
            case ("бренд"):
                setBrand(number, value);
                break;
            default:
                Assertions.fail("Заданного фильтра для товаров этой категории не существует");
        }
        return app.getSearchPage();
    }

    /**
     * Метод добавляющий чётные товары в корзину
     *
     * @param size - число товаров для добавления
     * @return CartPage - переходим на страницу корзины
     */
    public CartPage addToCart(int size) {
        String tmp = numberOfProducts.getText();

        while (cart.itemList.size() < size) {
            for (int i = 1; i < products.size(); i += 2) {
                if (cart.itemList.size() == size) break;
                WebElement element = products.get(i);
                String addToCartButton = ".//div[text()='В корзину']";
                if (isElementExist(element, addToCartButton)) {

                    element.findElement(By.xpath(addToCartButton)).click();

                    String xpathToName = ".//div[contains(@class, 'item')]/..//a[@href][contains(@class, 'tile-hover-target')][text()]";
                    String name = element.findElement(By.xpath(xpathToName)).getText();
                    cart.itemList.add(new Item(name));

                    tmp = Utils.addOne(tmp);
                    Assertions.assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(numberOfProducts, tmp)));
                }

            }
            if (isElementExist(toNextPage)) {
                toNextPage.click();
            } else break;
        }
        elementIsClickable(cartButton).click();
        wait.until((ExpectedCondition<Boolean>) driver -> driver.getTitle().equals("OZON.ru - Моя корзина"));
        return app.getCartPage();
    }

    private void setBrand(int number, String value) {
        if (isElementExist(extendedFilterSearch)) {
            extendedFilterSearch.click();
        }
        elementIsClickable(extendedFilterInput).click();
        extendedFilterInput.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        extendedFilterInput.sendKeys(value);
        elementIsClickable(brandFilter.findElement(By.xpath(String.format("./..//span[text()='%s']", value)))).click();
        wait.until((ExpectedCondition<Boolean>) driver -> activeFilters.size() != number);
    }

    private void setAmount(int number, String value, String text) {
        if (text.equals("цена до")) {
            actions.moveToElement(priceRangeTo).click().build().perform();
            priceRangeTo.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
            priceRangeTo.sendKeys(value + Keys.ENTER);
            wait.until((ExpectedCondition<Boolean>) driver -> activeFilters.size() != number);
        } else {
            actions.moveToElement(priceRangeFrom).click().build().perform();
            priceRangeTo.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
            priceRangeTo.sendKeys(value + Keys.ENTER);
            wait.until((ExpectedCondition<Boolean>) driver -> activeFilters.size() != number);
        }
    }

    private void setSingleFilter(int number, WebElement element) {
        actions.moveToElement(element).click().build().perform();
        wait.until((ExpectedCondition<Boolean>) driver -> activeFilters.size() != number);
    }


}
