package DAO;

import model.Author;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    private static final String FILE_PATH_AUTHOR = "data/authors.txt";

    //Método para garantir que o diretório existe
    private void ensureDataDirectoryExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório se não existir
        }
    }

    public void saveAuthor(Author author) {
        ensureDataDirectoryExists();
        List<Author> authors = findAll();
        authors.add(author);
        saveAll(authors);
    }

    private void saveAll(List<Author> authors) {
        ensureDataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_AUTHOR))) {
            for (Author author : authors) {
                writer.write(author.getId() + ","  + author.getName() + "," + author.getBirthDate() + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<Author>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_AUTHOR))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                LocalDate birthDate = LocalDate.parse(data[2]);
                authors.add(new Author(id, name, birthDate));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return authors;
    }

    public Author findById(int id) {
        return findAll().stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void delete(int id) {
        List<Author> authors = findAll();
        authors.removeIf(author -> author.getId() == id);
        saveAll(authors);
    }


}
