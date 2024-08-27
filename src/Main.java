import model.Author;
import model.Book;
import model.Library;
import model.User;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        User user1 = new User("Joselina Maranhão", "josymaranhas@gmail.com");
        User user2 = new User("Anna Sophia Pidona", "pidona@gmail.com");
        library.addUser(user1);
        library.addUser(user2);

        Author author1 = new Author("Bruno Teste", "19/05/1986");
        Author author2 = new Author("J.K Rowlling", "19/05/1959");
        library.addAuthor(author1);
        library.addAuthor(author2);

        Book book1 = new Book( "Senhor dos Aneis", author1, true, "20/05/1980");
        Book book2 = new Book( "Harry Potter", author2, true, "20/05/1982");
        library.addBook(book1);
        library.addBook(book2);

        System.out.println("\nLista de Usúarios");
        for (User user : library.listUsers()){
            System.out.println(user);
        }
        System.out.println("\nLista de Autores");
        for (Author author : library.listAuthors()){
            System.out.println(author);
        }
        System.out.println("\nLivros disponiveis");
        for (Book book : library.listAvailableBooks()){
            System.out.println(book);
        }

        if (library.listBorrowedBooks().isEmpty()){
            System.out.println("\nNão livros emprestados no momento...");
        }else{
            System.out.println("\nLivros emprestados");
            library.listBorrowedBooks().forEach(System.out::println);
        }
    }
}