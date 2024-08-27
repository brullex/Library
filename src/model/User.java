package model;

public class User {
    public static int idCounter = 1;
    private int id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id: " + id +
                ", Nome: '" + name + '\'' +
                ", E-mail: '" + email + '\'' +
                '}';
    }
}
