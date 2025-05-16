package tests.api;

import io.restassured.response.Response;
import models.UserBooksResponseModel;
import tests.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static specs.Specs.*;

public class Books extends TestBase {

    public static void deleteAllBooks(String token, String userId) {
        given(requestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(DeleteBooksResponseSpec(204));
    }

    public static void addBook(String token, String userId, String isbn){
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                userId, isbn);
            given(addBookRequestSpec)
                    .header("Authorization", "Bearer " + token)
                    .body(bookData)
                    .when()
                    .post("/BookStore/v1/Books")
                    .then()
                    .spec(addBookResponseSpec(201));
    }

    public static void deleteBook(String token, String userId, String isbn) {
        String bookData = format("{\"isbn\":\"%s\",\"userId\":\"%s\"}", isbn, userId);

        given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(DeleteBooksResponseSpec(204));
    }

    public static UserBooksResponseModel getUserBooks(String token, String userId) {
        Response response = given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(loginResponseSpec(200))
                .extract().response();

        return response.as(UserBooksResponseModel.class);
    }

}
