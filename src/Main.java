import model.Author;
import model.Book;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        Author author = new Author(1, "Bruno Teste", "19/05/1986");
        System.out.println(author);

        Book book = new Book(1, "Senhor dos Aneis", author, true, "20/05/1980");

        System.out.println(book);
        }
}