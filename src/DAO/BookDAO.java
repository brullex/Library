package DAO;

import model.Author;
import model.Book;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BookDAO {
    private static final String FILE_PATH_BOOK = "data/book.txt";
    private static int idCounter = 1;

    public BookDAO() {
        // Inicializa idCounter com base no maior ID existente
        int lastId = getLastId();
        if (lastId > 0) {
            idCounter = lastId + 1;
        }
    }
    private int getLastId() {
        int lastId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] data = line.split(",");
                if (data.length > 0 && !data[0].trim().isEmpty()) {
                    int id = Integer.parseInt(data[0].trim());
                    if (id > lastId) {
                        lastId = id;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return lastId;
    }


    private void DataDirectoryExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório se não existir
        }
    }

    public void saveBook(Book book) {
        DataDirectoryExists();
        List<Book> books =  findAll();
        book.setId(idCounter++);
        books.add(book);
        saveAll(books);
    }

    private void saveAll(List<Book> books) {
        DataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_BOOK))) {
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthorName() + ","  + book.isAvailable() + "," + book.getDateAdded() + "," + book.getDateUpdated() + "\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] data = line.split(",");
                if (data.length >= 6) {
                    int id = Integer.parseInt(data[0].trim());
                    String title = data[1].trim();
                    String authorName = data[2].trim();
                    boolean isAvailable = Boolean.parseBoolean(data[3].trim());
                    LocalDate dateAdded = LocalDate.parse(data[4].trim());
                    LocalDate dateUpdated = LocalDate.parse(data[5].trim());

                    Author author = new Author(authorName);
                    books.add(new Book(id, title, author, isAvailable, dateAdded, dateUpdated));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return books;
    }


    // Método para encontrar um livro por ID
    public Book findById(int id) {
        return findAll().stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Método para remover um livro
    public void delete(int id) {
        List<Book> books = findAll();
        books.removeIf(book -> book.getId() == id);
        saveAll(books);
    }
}
