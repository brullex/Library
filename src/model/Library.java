package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Author> authors;
    private List<User> users;
    private List<Borrowing> borrowings;  // Adiciona uma lista para os empréstimos

    // Construtor da classe
    public Library() {
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.users = new ArrayList<>();
        this.borrowings = new ArrayList<>();  // Inicializa a lista de empréstimos
    }

    // Adiciona um livro
    public void addBook(Book book) {
        books.add(book);
    }

    // Adiciona um autor
    public void addAuthor(Author author) {
        authors.add(author);
    }

    // Adiciona um usuário
    public void addUser(User user) {
        users.add(user);
    }

    // Lista livros disponíveis
    public List<Book> listAvailableBooks() {
        List<Book> availableBooks  = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    // Lista livros emprestados
    public List<Book> listBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books) {
            if(!book.isAvailable()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    // Lista autores
    public List<Author> listAuthors() {
        return new ArrayList<>(authors);
    }

    // Lista usuários
    public List<User> listUsers() {
        return new ArrayList<>(users);
    }

    // Deleta um livro
    public void deleteBook(int bookId) {
        books.removeIf(book -> book.getId() == bookId);
    }

    // Deleta um usuário
    public void deleteUser(int userId) {
        users.removeIf(user -> user.getId() == userId);
    }

    // Deleta um autor
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

    // Alugar um livro
    public void rentBook(User user, Book book) {
        if (book.isAvailable()) {
            book.borrow();  // Atualiza o status do livro
            Borrowing borrowing = new Borrowing(user, book);
            borrowings.add(borrowing);  // Registra o empréstimo
            System.out.println("Livro alugado com sucesso!");
        } else {
            System.out.println("O livro não está disponível para aluguel.");
        }
    }

    // Devolver um livro
    public void returnBook(Book book) {
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getBook().equals(book) && borrowing.getReturnDate() == null) {
                borrowing.setReturnDate(LocalDate.now());  // Define a data de devolução
                book.returnBook();  // Atualiza o status do livro para disponível
                System.out.println("Livro devolvido com sucesso!");
                break;
            }
        }
    }

    // Lista de todos os empréstimos
    public List<Borrowing> listBorrowings() {
        return new ArrayList<>(borrowings);
    }
}
