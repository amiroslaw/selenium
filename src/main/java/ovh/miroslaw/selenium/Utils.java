package ovh.miroslaw.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BLANK = "";

    public static String getStringIfExist(String[] array, int index, String defaultValue) {
        String value = defaultValue;
        if (array.length > index) {
            value = array[index];
        }
        return value;
    }

    public static void log(String data) {
        Logger.getLogger("ovh.miroslaw.selenium").log(Level.INFO, data);
    }

    public static void log(String data, Level level) {
        Logger.getLogger("ovh.miroslaw.selenium").log(level, data);
    }

    public static ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/home/miro/Ext/selenium/chromedriver");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}
