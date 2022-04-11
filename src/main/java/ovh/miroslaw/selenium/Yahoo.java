package ovh.miroslaw.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Yahoo {

    private Yahoo() {
    }

    public static String getData(String[] args, WebsiteInfo yahooCredentials) {
        String data;
        try {
            final ChromeDriver driver = Utils.getChromeDriver();
            driver.get(yahooCredentials.url().toString());
            driver.findElement(By.xpath("//a[@href='/dashboard']")).click();
            final WebElement emailInput = driver.findElement(By.xpath("//input[@placeholder='Email']"));
            final WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Password']"));
            emailInput.sendKeys(yahooCredentials.login());
            passwordInput.sendKeys(yahooCredentials.password());
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(8000);
            final String dashboardText = driver.findElement(By.xpath("//div[@class='DashboardElements']/div[1]"))
                    .getText();

            final String[] api = dashboardText.split(":");
            data = api[1].trim();

        } catch (Exception e) {
            data = "Couldn't fetch data";
        }

        saveDataToFile(args[0], data);
        return data;
    }

    private static void saveDataToFile(String name, String data) {
        try {
            Files.write(Paths.get(name), Collections.singleton(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
