package tests;

import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.Specs.loginResponseSpec;
import static specs.Specs.requestSpec;

@Tag("AllApi")
public class LoginDemoQa extends TestBase {

    @Test
    @DisplayName("Successful authentication test")
    void successAuthTest() {
        LoginBodyModel authData = new LoginBodyModel("OLEG", "Oleg!234");

        LoginResponseModel response = step("Auth", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseModel.class));
        step("Check response", () -> {
            assertNotNull(response.getToken());
            assertFalse(response.getToken().isEmpty());
            assertFalse(response.getToken().trim().isEmpty());
        });

        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", response.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", response.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", response.getToken()));
        getWebDriver().manage().addCookie(new Cookie("username", response.getUsername()));
        open("/profile");
        $x("//div//span[@id='delete-record-undefined']").click();
        $x("//div//button[@id='closeSmallModal-ok']").click();
    }
}