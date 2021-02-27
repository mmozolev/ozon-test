package ru.appline.homework;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"ru.appline.homework.utils.Listener",
                "progress",
                "summary"},
        glue = {"ru.appline.homework.steps"},
        features = {"src/test/resources/"}
)
public class CucumberRunner {

}