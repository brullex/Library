package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book {
    private static int idCounter = 1;
    private int id;
    private String title;
    private Author author;
    private boolean isAvailable;
    private LocalDate dateAdded;
    private LocalDate dateUpdated;

    // Formata a data no padrão BR
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor completo para recuperação de dados do arquivo
    public Book(int id, String title, Author author, boolean isAvailable, LocalDate dateAdded, LocalDate dateUpdated) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
    }

    // Construtor simplificado para criar novos livros.
    public Book(String title, Author author, boolean isAvailable, String dateAddedStr) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.dateAdded = LocalDate.parse(dateAddedStr, dateFormatter);
        this.dateUpdated = this.dateAdded;
    }

    // Métodos de aluguel e devolução
    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            dateUpdated = LocalDate.now();
        } else {
            System.out.println("O livro não está disponível para aluguel.");
        }
    }

    public void returnBook() {
        isAvailable = true;
        dateUpdated = LocalDate.now();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author.getName() +
                ", isAvailable=" + (isAvailable ? "Sim" : "Não") +
                ", dateAdded=" + dateAdded.format(dateFormatter) +
                ", dateUpdated=" + dateUpdated.format(dateFormatter) +
                '}';
    }
}
