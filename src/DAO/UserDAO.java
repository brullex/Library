package DAO;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String FILE_PATH_USER = "data/user.txt";
    private static int idCounter = 1;

    // Este construtor é responsável por inicializar o contador de IDs (idCounter) com base no maior ID
    public UserDAO() {
        int lastId = getLastId();
        if (lastId > 0) {
            idCounter = lastId + 1;
        }
    }

    // Verifica se o diretorio data existe, se não, cria o mesmo.
    private void DataDirectoryExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Método para procurar um autor pelo ID
    public User findById(int id) {
        return findAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /* Este método garante que os ids gerados para autores sejam unicos e sequenciais
    *  mesmo que o programa seja finalizado ou reiniciado.
    */
    private int getLastId() {
        int lastId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_USER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
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
            e.printStackTrace();
        }
        return lastId;
    }

    // Salva um autor
    public void saveUser(User user) {
        DataDirectoryExists();
        user.setId(idCounter++);
        List<User> users = findAll();
        users.add(0, user);
        saveAll(users);
    }

    /*
     * Salva a lista de user no arquivo .txt
     * sobrescreve o arquivo existente com os dados da lista de usuarios recuperados
     */
    private void saveAll(List<User> users) {
        DataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_USER))) {
            for (User user : users) {
                writer.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Método para carregar todos os usuários do arquivo user.txt linha a linha
    * e reconstroi o objeto User, retornando uma lista de todos usuários.
    */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_USER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 3) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        String email = data[2].trim();

                        users.add(new User(id, name, email));
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao processar a linha: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    // Deleta um usuário ID
    public void delete(int id) {
        List<User> users = findAll();
        users.removeIf(user -> user.getId() == id);
        saveAll(users);
    }
}
