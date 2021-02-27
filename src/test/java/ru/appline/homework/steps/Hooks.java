package ru.appline.homework.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.appline.homework.managers.InitManager;
import ru.appline.homework.managers.PageManager;
import ru.appline.homework.utils.Cart;

public class Hooks {

    @Before
    public void beforeEach() {
        InitManager.initFramework();
    }

    @After
    public void afterEach() {
        InitManager.quitFramework();
        PageManager.clearPage();
        Cart.disable();
    }
}
