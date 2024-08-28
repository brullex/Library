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

    //formata data no padrão BR
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Book(String title, Author author, boolean isAvailable, LocalDate dateAdded) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.dateAdded = LocalDate.now();
        this.dateUpdated = dateAdded;

    }

    public Book(int id, String title, Author author, boolean isAvailable, LocalDate dateAdded, LocalDate dateUpdated) {
        this.id = id;  // Usa o id fornecido em vez de incrementar automaticamente
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
    }

    //construtor com data no formato BR
    public Book(String title, Author author, boolean isAvailable, String dateAddedStr) {
        this.id = idCounter++;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.dateAdded = LocalDate.parse(dateAddedStr, dateFormatter);
        this.dateUpdated = dateAdded;
    }

    //Método para atualizar dateUpdated com uma string no formato BR
    public void dateUpdateted(String dateUpdatedStr) {
        this.dateUpdated = LocalDate.parse(dateUpdatedStr, dateFormatter);
    }

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

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getAuthorName(){
        return author.getName();
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id: " + id +
                ", Titulo: '" + title + '\'' +
                ", Autor: " + author +
                ", Disponivel: " + (isAvailable ? "Sim" : "Não")+
                ", Data de Entrada: " + dateAdded +
                ", Data Ultima Atualização: " + (dateUpdated == null ? "Sem Movimento" : dateAdded.format(dateFormatter)) +
                '}';
    }
}
