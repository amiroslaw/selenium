package ovh.miroslaw.selenium;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ovh.miroslaw.selenium.Utils.BLANK;

public record WebsiteInfo(String name, URL url, String login, String password) {

    public static Map<String, WebsiteInfo> getWebsitesInfo() {
        try (Stream<String> stream = Files.lines(Paths.get("credentials"))) {
            return stream
                    .filter(s -> !s.isBlank())
                    .map(WebsiteInfo::createWebsiteInfo)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toMap(WebsiteInfo::name, website -> website));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private static Optional<WebsiteInfo> createWebsiteInfo(String line) {
        final String[] split = line.split("\\|");
        String login = Utils.getStringIfExist(split, 2, BLANK);
        String password = Utils.getStringIfExist(split, 3, BLANK);
        try {
            return Optional.ofNullable(new WebsiteInfo(split[0], new URL(split[1]), login, password));
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

}

