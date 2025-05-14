package models;

import lombok.Data;
import java.util.List;

@Data
public class AddBook {
    private List<Book> books;

    @Data
    public static class Book {
        private String isbn;
    }
}