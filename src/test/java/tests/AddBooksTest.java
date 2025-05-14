package tests;

import models.AddBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.Specs.addBookRequestSpec;
import static specs.Specs.addBookResponseSpec;

public class AddBooksTest extends TestBase{
    @Test
    @DisplayName("Добавляем книгу юзеру")
    void addBooksTest() {
        String userId = "8d141a51-d5cc-43fd-b2b6-f021c09fa098";
        String isbn = "9781449325862";

        String addBook = """
            {
              "userId": "%s",
              "collectionOfIsbns": [
                {
                  "isbn": "%s"
                }
              ]
            }
            """.formatted(userId, isbn);
        AddBook response = step("Add book", () ->
                given(addBookRequestSpec)
                        .body(addBook)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(addBookResponseSpec)
                        .statusCode(201)
                        .extract().as(AddBook.class));
        step("Check response", () -> {
            assertNotNull(response.getBooks());
            assertEquals(isbn, response.getBooks().get(0).getIsbn());
        });
    }
}
