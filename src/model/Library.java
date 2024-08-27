package model;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Author> authors;
    private List<User> users;

    //starts the constructor of a class
    public Library() {
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    //add an book
    public void addBook(Book book) {
        books.add(book);
    }

    //add an author
    public void addAuthor(Author author) {
        authors.add(author);
    }

    //Add an user
    public void addUser(User user) {
        users.add(user);
    }

    //Create a list of available books
    public List<Book> listAvailableBooks() {
        List<Book> availableBooks  = new ArrayList<Book>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    //Create a list of borrowed books
    public List<Book> listBorrowedBooks(){
        List<Book> borrowedBooks = new ArrayList<Book>();
        for (Book book : books) {
            if(!book.isAvailable()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    //create list of authors
    public List<Author> listAuthors() {
        return new ArrayList<>(authors);
    }

    //list of users
    public List<User> listUsers() {
        return new ArrayList<>(users);
    }

    //delete an book
    public void deleteBook(int bookId) {
        books.removeIf(book -> book.getId() == bookId);
    }

    //delete an user
    public void deleteUser(int userId) {
        users.removeIf(user -> user.getId() == userId);
    }

    //delete an author
//    public void deleteAuthor(int authorId) {
//        authors.removeIf(author -> author.getId() == authorId);
//        books.removeIf(book -> book.getAuthor().getId() == authorId);
//    }
    public void deleteAuthor(int authorId) {
        boolean hasBooks = books.stream()
                .anyMatch(book -> book.getAuthor().getId() == authorId);

        if (hasBooks) {
            System.out.println("Não é possível remover o autor. Existem livros associados a este autor.");
        } else {
            authors.removeIf(author -> author.getId() == authorId);
            System.out.println("Autor removido com sucesso.");
        }
    }


}
