package tests;

import models.AddBook;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static specs.Specs.*;
import static specs.Specs.addBookResponseSpec;

public class CollectionTestsApiWithUI extends TestBase {
    @Test
    void addBookToCollectionTest() {
        LoginBodyModel authData = new LoginBodyModel("OLEG", "Oleg!234");

        LoginResponseModel authResponse = step("Auth", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseModel.class));
        step("Check response", () -> {
            assertNotNull(authResponse.getToken());
            assertFalse(authResponse.getToken().isEmpty());
            assertFalse(authResponse.getToken().trim().isEmpty());
        });

        step("Delete all books", () -> {
                given(requestSpec)
                        .header("Authorization", "Bearer " + authResponse.getToken())
                        .queryParam("UserId", authResponse.getUserId())
                        .when()
                        .delete("/BookStore/v1/Books")
                        .then()
                        .spec(DeleteAllBooksResponseSpec);
    });

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.getUserId() , isbn);

        AddBook addBook = step("Add book", () ->
                given(addBookRequestSpec)
                        .header("Authorization", "Bearer " + authResponse.getToken())
                        .body(bookData)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(addBookResponseSpec)
                        .statusCode(201)
                        .extract().as(AddBook.class));
        step("Check response", () -> {
            assertNotNull(addBook.getBooks());
            assertEquals(isbn, addBook.getBooks().get(0).getIsbn());
        });

        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("username", authResponse.getUsername()));
        open("/profile");
        $x("//div//span[@id='delete-record-undefined']").click();
        $x("//div//button[@id='closeSmallModal-ok']").click();

    }
}
