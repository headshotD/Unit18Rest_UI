package tests.api;

import models.AddBook;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static specs.Specs.*;

public class CollectionTestsApi extends TestBase {
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
                authResponse.getUserId(), isbn);

        AddBook addBook = step("Add book", () -> // Обращаемся вначале к модель-классу, которая содержит название ключа из ответа, потом название нашего теста
                given(addBookRequestSpec)// в гивен спеку кладем, всякий мусор хедер и тд
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

String deleteBookData = "{ \"userId\": \"" + authResponse.getUserId() + "\", \"isbn\": \"" + isbn + "\" }";
        step("Delete book", () -> {
            given(addBookRequestSpec)
                    .header("Authorization", "Bearer " + authResponse.getToken())
                    .body(deleteBookData)
                    .when()
                    .delete("/BookStore/v1/Book")
                    .then()
                    .spec(DeleteSoloBooksResponseSpec);

        });


    }
}