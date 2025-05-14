package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor

public class DeleteBookModel {
    String userId, isbn;
}
