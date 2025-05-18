package tests;

import helpers.WithLogin;
import models.BookModel;
import models.LoginResponseModel;
import models.UserBooksResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import api.AccountApiSteps;
import api.BookStoreApiSteps;

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
        LoginResponseModel auth = step("Авторизация через API", AccountApiSteps::login);
        String token = auth.getToken();
        String userId = auth.getUserId();

        step("Удаление всех книг с корзины", () ->
                BookStoreApiSteps.deleteAllBooks(token, userId)
        );

        step("Добавление книги через API", () ->
                BookStoreApiSteps.addBook(token, userId, BOOK_ISBN)
        );

        step("Проверка, что книга добавлена через API", () -> {
            UserBooksResponseModel booksResp = AccountApiSteps.getUserBooks(token, userId);
            List<BookModel> userBooks = booksResp.getBooks();
            assertThat(userBooks).extracting(BookModel::getIsbn).contains(BOOK_ISBN);
        });


        step("Удаление конкретной книги через API", () -> {
            BookStoreApiSteps.deleteBook(token, userId, BOOK_ISBN);
        });

        step("Проверка через API, что книга удалена", () -> {
            UserBooksResponseModel delBook = AccountApiSteps.getUserBooks(token, userId);
            assertThat(delBook.getBooks()).isEmpty();
        });
    }
}