package ru.appline.homework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.homework.utils.Listener;
import ru.appline.homework.utils.Utils;


import java.io.*;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = "//input[@type='checkbox']/parent::div/parent::label/parent::div/..")
    List<WebElement> itemsInCart;

    String itemName = ".//a//span";

    @FindBy(xpath = "//span[contains(text(), 'Ваша корзина')]//following-sibling::span")
    WebElement numberOfItem;

    @FindBy(xpath = "//span[contains(text(), 'Удалить выбранные')]")
    WebElement deleteButton;

    @FindBy(xpath = "//div[text()='Удалить']")
    WebElement acceptButton;

    @FindBy(xpath = "//h1")
    WebElement header;


    /**
     * Метод проверяющий добавленные товары в корзине
     *
     * @return остаемся на странице
     */
    public CartPage checkCart() {

        Assertions.assertTrue(numberOfItem.getText().contains(cart.itemList.size() + " товар"), "Не верно отображается число товаров в корзине");
        Assertions.assertEquals(itemsInCart.size(), cart.itemList.size(), "Корзины не равны");

        for (WebElement element : itemsInCart) {
            for (int i = 0; i < cart.itemList.size(); i++) {
                if (element.findElement(By.xpath(itemName)).getText().equals(cart.itemList.get(i).getName())) break;
                else if (i == cart.itemList.size() - 1)
                    Assertions.assertEquals(element.findElement(By.xpath(itemName)).getText(), cart.itemList.get(i).getName(), "Имена не совпадают");
            }
        }
        return app.getCartPage();
    }

    /**
     * Метод удаляющий товары из корзины
     *
     * @return остаемся на странице
     */
    public CartPage deleteFromCart() {
        actions.moveToElement(deleteButton).click().build().perform();
        actions.moveToElement(acceptButton).click().build().perform();
        return app.getCartPage();
    }

    /**
     * Метод проверяющий корзину на отсутствие в ней товаров
     *
     * @return отсаемся на странице
     */
    public CartPage checkEmpty() {
        Assertions.assertTrue(header.getText().contains("Корзина пуста"), "Корзина не пуста");
        return app.getCartPage();
    }

    /**
     * Метод добавляющий в шаг отчета информацию о товарах, находящихся в корзине
     *
     * @return остаемся на странице
     */
    public CartPage addInfo() {
        createAttachmentFile();
        Listener.addInfo();
        return app.getCartPage();
    }

    /**
     * Метод сохраняет информацию о товарах корзины и записывает их в файл "src/test/resources/name.txt"
     */
    private void createAttachmentFile() {
        File file = new File("src/test/resources/reportData/data.txt");
        int maxAmount = 0;
        String pathToName = ".//span[@style]";
        String pathToAmount = ".//div[contains(@style, 'bold')]//span";

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file, true));
             BufferedWriter clear = new BufferedWriter(new FileWriter(file))) {
            clear.write("");

            for (WebElement element : itemsInCart) {
                String name = element.findElement(By.xpath(pathToName)).getText();
                String amount = element.findElement(By.xpath(pathToAmount)).getText();
                bf.write(name + "\n" + "Цена: " + amount + "\n");

                if (Utils.parseAmount(amount) > maxAmount) maxAmount = Utils.parseAmount(amount);
            }

            bf.write("Максимальная цена: " + maxAmount + " ₽");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
