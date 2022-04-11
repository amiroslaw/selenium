package ovh.miroslaw.selenium;

import java.util.function.BiFunction;

public enum WebsiteFactory {
    YAHOO(Yahoo::getData),
    INPOST(Inpost::getData);

    private final BiFunction<String[], WebsiteInfo, String> operator;

    WebsiteFactory(BiFunction<String[], WebsiteInfo, String> operator) {
        this.operator = operator;
    }

    public String apply(String[] args, WebsiteInfo websiteInfo) {
        return operator.apply(args, websiteInfo);
    }
}
