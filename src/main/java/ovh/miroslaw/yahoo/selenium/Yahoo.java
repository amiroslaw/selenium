package ovh.miroslaw.yahoo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Yahoo {

    public static void main(String... args) throws InterruptedException {
        System.setProperty( "webdriver.chrome.driver", "/home/miro/Ext/selenium/chromedriver");
        final Map<String, Website> credentials = getCredentials();
        final Website yahooCredentials = credentials.get("yahoo");

        final String apiKey = getData(yahooCredentials);

        try {
            Files.write(Paths.get(yahooCredentials.name), Collections.singleton(apiKey));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getData(Website yahooCredentials) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(yahooCredentials.url.toString());
        driver.findElement(By.xpath("//a[@href='/dashboard']")).click();
        final WebElement emailInput = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        final WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        emailInput.sendKeys(yahooCredentials.login);
        passwordInput.sendKeys(yahooCredentials.password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(5000);
        final String dashboardText = driver.findElement(By.xpath("//div[@class='DashboardElements']/div[1]")).getText();
        final String[] api = dashboardText.split(":");
        final String apiKey = api[1].trim();
        System.out.println(apiKey);
        return apiKey;
    }

    private static Map<String, Website> getCredentials() {
        try (Stream<String> stream = Files.lines(Paths.get("credentials"))) {
            return stream
                    .map(Yahoo::createWebsite)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toMap(website -> website.name, website -> website));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private static Optional<Website> createWebsite(String line) {
        final String[] split = line.split("\\|");
        try {
            return Optional.ofNullable(new Website(split[0], new URL(split[1]), split[2], split[3]));
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

    record Website(String name, URL url, String login, String password) {

    }
}
