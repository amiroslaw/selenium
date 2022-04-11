package ovh.miroslaw.selenium;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ovh.miroslaw.selenium.Utils.getStringIfExist;
import static ovh.miroslaw.selenium.Utils.log;
import static ovh.miroslaw.selenium.WebsiteInfo.getWebsitesInfo;

public class Application {

    public static void main(String... args) {
        final String appInfo = """
                Tool for fetching information from a website. It uses Selenium. Available options:
                -h → show help
                inpost parcel_number → get information form inpost about your shipment
                yahoo → get api key for yahoo finance
                                
                In order to execute the application you have to create file "credentials" with following structure:
                website_name|website_url|login|password
                Login and password are optional in some websites.
                """;

        if (Objects.equals(args[0], "-h")) {
            log(appInfo);
            System.exit(0);
        }

        final Map<String, WebsiteInfo> websiteInfoMap = getWebsitesInfo();
        String websiteName = getStringIfExist(args, 0, "yahoo");
        final Optional<WebsiteInfo> websiteInfo = Optional.ofNullable(websiteInfoMap.get(websiteName));
        final String data = websiteInfo
                .map(info -> WebsiteFactory.valueOf(info.name().toUpperCase()).apply(args, info))
                .orElseThrow(IllegalArgumentException::new);

        log(data);
    }

}
