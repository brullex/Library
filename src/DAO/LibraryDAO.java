package DAO;

import model.Borrowing;
import model.User;
import model.Book;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
* Classe responsavel por persistir e manipular os dados no library.txt
* esse dados são um historico das transações dos livros.
*/
public class LibraryDAO {
    private static final String FILE_PATH_LIBRARY = "data/library.txt";


    // Método verifica se o diretorio data existe, se não existe ele cria.
    private void dataDirectoryExists() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /*
    * Método para salvar emprestimos de livros, persiste esses dados em um arquivo .txt
    * incluindo o usuário, o livro, a data de empréstimo e a data de devolução, se disponível.
    */
    public void saveBorrowing(Borrowing borrowing) {
        dataDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_LIBRARY, true))) {
            writer.write(borrowing.getUser().getId() + "," + borrowing.getBook().getId() + "," +
                    borrowing.getBorrowDate() + "," +
                    (borrowing.getReturnDate() != null ? borrowing.getReturnDate() : "Ainda não devolvido") + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o histórico de empréstimos: " + e.getMessage());
        }
    }

    /*
    * Método que recupera todos os no arquivo de historico library.txt
    * ao ler o arquivo recria os objetos Borrowing, fazendo associação aos
    * objetos User e Book
    */
    public List<Borrowing> findAllBorrowings(UserDAO userDAO, BookDAO bookDAO) {
        List<Borrowing> borrowings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_LIBRARY))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignora linhas vazias
                }
                String[] data = line.split(",");
                if (data.length >= 4) {
                    int userId = Integer.parseInt(data[0].trim());
                    int bookId = Integer.parseInt(data[1].trim());
                    LocalDate borrowDate = LocalDate.parse(data[2].trim());
                    String returnDateStr = data[3].trim();

                    User user = userDAO.findById(userId);
                    Book book = bookDAO.findById(bookId);

                    if (user != null && book != null) {
                        Borrowing borrowing = new Borrowing(user, book);
                        borrowing.setReturnDate(returnDateStr.equals("Ainda não devolvido") ? null : LocalDate.parse(returnDateStr));
                        borrowings.add(borrowing);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o histórico de empréstimos: " + e.getMessage());
        }
        return borrowings;
    }
}
