package tests;

import helpers.WithLogin;
import models.BookModel;
import models.LoginResponseModel;
import models.UserBooksResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.api.AuthorizationApi;
import tests.api.Books;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.BOOK_ISBN;
@Tag("API")
public class BookStoreApplicationFullAPITest extends TestBase {


    @Test
    @WithLogin
    @DisplayName("Удаление книги через UI")
    void deleteBookUITest() {
        LoginResponseModel auth = step("Авторизация через API", AuthorizationApi::login);
        String token = auth.getToken();
        String userId = auth.getUserId();

        step("Удаление всех книг с корзины", () ->
                Books.deleteAllBooks(token, userId)
        );

        step("Добавление книги через API", () ->
                Books.addBook(token, userId, BOOK_ISBN)
        );

        step("Проверка, что книга добавлена через API", () -> {
            UserBooksResponseModel booksResp = Books.getUserBooks(token, userId);
            List<BookModel> userBooks = booksResp.getBooks();
            assertThat(userBooks).extracting(BookModel::getIsbn).contains(BOOK_ISBN);
        });


        step("Удаление конкретной книги через API", () -> {
            Books.deleteBook(token, userId, BOOK_ISBN);
        });

        step("Проверка через API, что книга удалена", () -> {
            UserBooksResponseModel delBook = Books.getUserBooks(token, userId);
            assertThat(delBook.getBooks()).isEmpty();
        });
    }
}