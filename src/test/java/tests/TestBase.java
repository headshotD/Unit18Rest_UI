package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.restassured.config.SSLConfig.sslConfig;

public class TestBase {
    @BeforeAll
    static void configuration() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.config = RestAssured.config()
                .sslConfig(sslConfig().with().relaxedHTTPSValidation());
        //Configuration.remote = System.getProperty("browserRemote", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
    }
    @AfterEach
    void shutDown(){
        closeWebDriver();
    }
}
