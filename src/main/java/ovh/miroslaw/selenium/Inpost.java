package ovh.miroslaw.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Inpost {

    private Inpost() {
    }

    public static String getData(String[] args, WebsiteInfo info) {
        String data;
        try {
            ChromeDriver driver = Utils.getChromeDriver();
            driver.get(info.url().toString());

            final WebElement input = driver.findElement(By.cssSelector("form.tracking-form input"));
            input.sendKeys(args[1]);
            input.sendKeys(Keys.ENTER);

            data = driver.findElement(By.cssSelector("div.singleParcelStatusesList")).getText();

        } catch (Exception e) {
            data = "Couldn't fetch data";
        }

        saveDataToFile(args, data);
        return data;
    }

    private static void saveDataToFile(String[] args, String data) {
        try {
            Files.write(Paths.get("/tmp/" + args[0] + args[1]), Collections.singleton(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
