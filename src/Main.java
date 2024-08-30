import DAO.AuthorDAO;
import DAO.BookDAO;
import DAO.UserDAO;
import DAO.LibraryDAO;
import model.Author;
import model.Book;
import model.User;
import model.Borrowing;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        LibraryDAO libraryDAO = new LibraryDAO();

        boolean running = true;

        // Menu Principal do sistema.
        while (running) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Usuários");
            System.out.println("2. Autores");
            System.out.println("3. Livros");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (mainChoice) {
                case 1:
                    manageUsers(scanner, userDAO);
                    break;
                case 2:
                    manageAuthors(scanner, authorDAO);
                    break;
                case 3:
                    manageBooks(scanner, bookDAO, authorDAO, libraryDAO, userDAO);
                    break;
                case 4:
                    running = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
    /* Método para gerenciar as operações relacionadas aos usuários.
    *  exibe um menu para que o usuário possa escolher entre cadastrar, listar ou excluir usuários.
    */
    private static void manageUsers(Scanner scanner, UserDAO userDAO) {
        boolean userMenu = true;

        while (userMenu) {
            System.out.println("\n--- Menu Usuários ---");
            System.out.println("1. Cadastrar usuários");
            System.out.println("2. Listar usuários");
            System.out.println("3. Excluir usuários");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (userChoice) {
                case 1:
                    System.out.print("Nome do usuário: ");
                    String userName = scanner.nextLine();
                    System.out.print("Email do usuário: ");
                    String userEmail = scanner.nextLine();
                    User user = new User(userName, userEmail);
                    userDAO.saveUser(user);
                    System.out.println("Usuário cadastrado com sucesso.");
                    break;
                case 2:
                    System.out.println("Usuários cadastrados:");
                    userDAO.findAll().forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("ID do usuário a ser excluído: ");
                    int userId = scanner.nextInt();
                    userDAO.delete(userId);
                    System.out.println("Usuário excluído com sucesso.");
                    break;
                case 4:
                    userMenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /* Método para gerenciar as operações relacionadas aos autores.
    *  exibe um menu para que o usuário possa escolher entre cadastrar, listar ou excluir autores.
    */
    private static void manageAuthors(Scanner scanner, AuthorDAO authorDAO) {
        boolean authorMenu = true;

        while (authorMenu) {
            System.out.println("\n--- Menu Autores ---");
            System.out.println("1. Cadastrar autores");
            System.out.println("2. Listar autores");
            System.out.println("3. Excluir autores");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            int authorChoice = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (authorChoice) {
                case 1:
                    System.out.print("Nome do autor: ");
                    String authorName = scanner.nextLine();
                    System.out.print("Data de nascimento do autor (dd/MM/yyyy): ");
                    String authorBirthDate = scanner.nextLine();
                    Author author = new Author(authorName, authorBirthDate);
                    authorDAO.saveAuthor(author);
                    System.out.println("Autor cadastrado com sucesso.");
                    break;
                case 2:
                    System.out.println("Autores cadastrados:");
                    authorDAO.findAll().forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("ID do autor a ser excluído: ");
                    int authorId = scanner.nextInt();
                    authorDAO.delete(authorId);
                    System.out.println("Autor excluído com sucesso.");
                    break;
                case 4:
                    authorMenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /* Método para gerenciar as operações relacionadas aos livros
    *  exibe um menu para que o usuário possa escolher entre cadastrar, listar, excluir livros e realizar operações de empréstimo.
    */
    private static void manageBooks(Scanner scanner, BookDAO bookDAO, AuthorDAO authorDAO, LibraryDAO libraryDAO, UserDAO userDAO) {
        boolean bookMenu = true;

        while (bookMenu) {
            System.out.println("\n--- Menu Livros ---");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Listar livros disponíveis");
            System.out.println("3. Listar livros emprestados");
            System.out.println("4. Listar livros por autores");
            System.out.println("5. Emprestar livro");
            System.out.println("6. Consultar histórico de empréstimos");
            System.out.println("7. Excluir livros");
            System.out.println("8. Voltar");
            System.out.print("Escolha uma opção: ");
            int bookChoice = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (bookChoice) {
                case 1:
                    System.out.print("Título do livro: ");
                    String bookTitle = scanner.nextLine();
                    System.out.print("Nome do autor do livro: ");
                    String bookAuthorName = scanner.nextLine();

                    // Carrega todos os autores do arquivo
                    List<Author> authors = authorDAO.findAll();

                    // Busca o autor pelo nome
                    Author bookAuthor = authors.stream()
                            .filter(a -> a.getName().equalsIgnoreCase(bookAuthorName))
                            .findFirst()
                            .orElse(null);

                    if (bookAuthor == null) {
                        System.out.println("Autor não encontrado. Por favor, cadastre o autor antes de cadastrar o livro.");
                        break;
                    }

                    System.out.print("Data de adição do livro (dd/MM/yyyy): ");
                    String bookDateAdded = scanner.nextLine();
                    Book book = new Book(bookTitle, bookAuthor, true, bookDateAdded);
                    bookDAO.saveBook(book);
                    System.out.println("Livro cadastrado com sucesso.");
                    break;
                case 2:
                    System.out.println("Livros disponíveis:");
                    bookDAO.findAll().stream()
                            .filter(Book::isAvailable)
                            .forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Livros emprestados:");
                    bookDAO.findAll().stream()
                            .filter(b -> !b.isAvailable())
                            .forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Nome do autor: ");
                    String authorName = scanner.nextLine();
                    authorDAO.findAll().stream()
                            .filter(a -> a.getName().equalsIgnoreCase(authorName))
                            .forEach(a -> bookDAO.findAll().stream()
                                    .filter(b -> b.getAuthor().equals(a))
                                    .forEach(System.out::println));
                    break;
                case 5:
                    System.out.print("ID do usuário: ");
                    int borrowUserId = scanner.nextInt();
                    scanner.nextLine();
                    User borrowUser = userDAO.findById(borrowUserId);
                    if (borrowUser == null) {
                        System.out.println("Usuário não encontrado.");
                        break;
                    }
                    System.out.print("ID do livro: ");
                    int borrowBookId = scanner.nextInt();
                    scanner.nextLine();
                    Book borrowedBook = bookDAO.findById(borrowBookId);
                    if (borrowedBook == null) {
                        System.out.println("Livro não encontrado.");
                        break;
                    }
                    if (borrowedBook.isAvailable()) {
                        borrowedBook.borrow();  // Atualiza o status do livro para "não disponível"
                        libraryDAO.saveBorrowing(new Borrowing(borrowUser, borrowedBook));
                        bookDAO.saveBook(borrowedBook);  // Atualiza o estado do livro no arquivo
                        System.out.println("Livro emprestado com sucesso.");
                    } else {
                        System.out.println("Livro não disponível para empréstimo.");
                    }
                    break;
                case 6:
                    System.out.println("Histórico de empréstimos:");
                    libraryDAO.findAllBorrowings(userDAO, bookDAO).forEach(System.out::println);
                    break;
                case 7:
                    System.out.print("ID do livro a ser excluído: ");
                    int bookId = scanner.nextInt();
                    bookDAO.delete(bookId);
                    System.out.println("Livro excluído com sucesso.");
                    break;
                case 8:
                    bookMenu = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
