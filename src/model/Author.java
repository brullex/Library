package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Author {
    private int id;  // O id será controlado pela classe DAO
    private String name;
    private LocalDate birthDate;

    // Formata a data para o padrão BR
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor padrão
    public Author(int id, String name, LocalDate birthDate) {
        this.id = id;  // O id é atribuído diretamente, sem incrementar.
        this.name = name;
        this.birthDate = birthDate;
    }

    // Construtor que recebe data no formato BR
    public Author(String name, String birthDateString) {
        this.name = name;
        this.birthDate = LocalDate.parse(birthDateString, dateFormat);
    }

    // Construtor para uso interno (ex. criação sem data)
    public Author(String authorName) {
        this.name = authorName;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        String birthDateStr = (birthDate != null) ? birthDate.format(dateFormat) : "Data de nascimento não informada";
        return "Autor{" +
                "id: " + id +
                ", Nome: '" + name + '\'' +
                ", Data de Nascimento: " + birthDateStr +
                '}';
    }
}
