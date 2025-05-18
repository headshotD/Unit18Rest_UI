package api;

import tests.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static specs.Specs.*;

public class BookStoreApiSteps extends TestBase {

    public static void deleteAllBooks(String token, String userId) {
        given(requestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(204));
    }

    public static void addBook(String token, String userId, String isbn){
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                userId, isbn);
            given(requestSpec)
                    .header("Authorization", "Bearer " + token)
                    .body(bookData)
                    .when()
                    .post("/BookStore/v1/Books")
                    .then()
                    .spec(responseSpec(201));
    }

    public static void deleteBook(String token, String userId, String isbn) {
        String bookData = format("{\"isbn\":\"%s\",\"userId\":\"%s\"}", isbn, userId);

        given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(responseSpec(204));
    }

}
