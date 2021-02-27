package ru.appline.homework.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Listener extends AllureCucumber6Jvm {

    @Attachment
    public static void addInfo() {
        Path content = Paths.get("src/test/resources/reportData/data.txt");
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment("My attachment", is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
