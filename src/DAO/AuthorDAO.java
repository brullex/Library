package DAO;

import model.Author;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    private static final String FILE_PATH_AUTHOR = "data/authors.txt";
    private static int idCounter = 1;

    public AuthorDAO() {
        // Inicializa idCounter com base no maior ID existente
        int lastId = getLastId();
        if (lastId > 0) {
            idCounter = lastId + 1;
        }
    }

    private void DataDirectoryExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório se não existir
        }
    }

    private int getLastId() {
        int lastId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_AUTHOR))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] data = line.split(",");
                if (data.length > 0 && !data[0].trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        if (id > lastId) {
                            lastId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido na linha: " + line);
                        // Continue a leitura sem interromper
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastId;
    }


    public void saveAuthor(Author author) {
        DataDirectoryExists();
        author.setId(idCounter++); // Define o ID do autor com o valor atualizado
        List<Author> authors = findAll(); // Carrega a lista de autores existentes
        authors.add(author); // Adiciona o novo autor à lista
        saveAll(authors); // Salva toda a lista no arquivo
    }

    private void saveAll(List<Author> authors) {
        DataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_AUTHOR))) {
            for (Author author : authors) {
                writer.write(author.getId() + "," + author.getName() + "," + author.getBirthDate() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_AUTHOR))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] data = line.split(",");
                if (data.length >= 3) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        LocalDate birthDate = LocalDate.parse(data[2].trim());

                        authors.add(new Author(id, name, birthDate));
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Erro ao processar a linha: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
