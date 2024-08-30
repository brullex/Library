package model;

import java.time.LocalDate;

public class Borrowing {
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    // Construtor que inicia um novo empréstimo
    public Borrowing(User user, Book book) {
        this.user = user;
        this.book = book;
        this.borrowDate = LocalDate.now();  // Define a data atual como data de empréstimo
        this.returnDate = null;  // A data de devolução começa como null, pois o livro ainda não foi devolvido.
    }

    // Getters e Setters
    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "user=" + user +
                ", book=" + book +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + (returnDate != null ? returnDate : "Ainda não devolvido") +
                '}';
    }
}
