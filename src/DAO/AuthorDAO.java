package DAO;

import model.Author;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/*
 * Classe responsavel por persistir e manipular os dados no author.txt
 */
public class AuthorDAO {
    private static final String FILE_PATH_AUTHOR = "data/author.txt";
    private static int idCounter = 1;

    // Este construtor é responsável por inicializar o contador de IDs (idCounter) com base no maior ID
    public AuthorDAO() {
        // Inicializa idCounter com base no maior ID existente
        int lastId = getLastId();
        if (lastId > 0) {
            idCounter = lastId + 1;
        }
    }
    // Verifica se o diretorio data existe, se não, cria o mesmo.
    private void DataDirectoryExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs(); // Cria o diretório se não existir
        }
    }

    // Método para procurar um autor pelo ID
    public Author findById(int id) {
        return findAll().stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /* Este método garante que os ids gerados para autores sejam unicos e sequenciais
    *  mesmo que o programa seja finalizado ou reiniciado.
    */
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
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de autores: " + e.getMessage());
        }
        return lastId;
    }

    // Salva um autor
    public void saveAuthor(Author author) {
        DataDirectoryExists();
        author.setId(idCounter++);
        List<Author> authors = findAll();
        authors.add(0, author);
        saveAll(authors);
    }

    /*
     * Salva a lista de autores no arquivo .txt
     * sobrescreve o arquivo existente com os dados da lista de autores recuperados
     */
    private void saveAll(List<Author> authors) {
        DataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_AUTHOR))) {
            for (Author author : authors) {
                writer.write(author.getId() + "," + author.getName() + "," + author.getBirthDate() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de autores: " + e.getMessage());
        }
    }


    /*
     * Método para carregar todos os autores do arquivo author.txt linha a linha
     * e reconstroi o objeto Author, retornando uma lista de todos autores.
     */
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
                        System.err.println("Erro ao processar a linha: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de autores: " + e.getMessage());
        }
        return authors;
    }

    // Deleta autor pelo ID
    public void delete(int id) {
        List<Author> authors = findAll();
        authors.removeIf(author -> author.getId() == id);
        saveAll(authors);
    }

}
