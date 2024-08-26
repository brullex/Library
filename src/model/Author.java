package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Author {
    private int id;
    private String name;
    private LocalDate birthDate;

    //formata a data para o padrão BR
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //construtor padrao
    public Author(int id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    //construtor que recebe data no formato BR
    public Author(int id, String name, String birthDateString){
        this.id = id;
        this.name = name;
        this.birthDate = LocalDate.parse(birthDateString, dateFormat);
    }

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
        return "Autor{" +
                "id: " + id +
                ", Nome: '" + name + '\'' +
                ", Data de Nascimento: " + birthDate.format(dateFormat) +
                '}';
    }
}
