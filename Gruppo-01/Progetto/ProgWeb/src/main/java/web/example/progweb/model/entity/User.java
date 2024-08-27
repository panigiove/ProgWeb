package web.example.progweb.model.entity;

/*
CREATE TABLE UTENTI (
    id_utente INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono CHAR(10) NOT NULL,
    n_acquisti INT DEFAULT 0,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
*/

public class User {
    private final int id;
    private final String name;
    private final String surname;
    private final String birthDate;
    private final String email;
    private final String phone;
    private final int nPurchases;
    private final String username;

    public User (int id, String name, String surname, String birthDate, String email, String phone, int nPurchases, String username) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.nPurchases = nPurchases;
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername(){ return this.username; }

    public int getId() {
        return this.id;
    }

    public int getnPurchases() {
        return this.nPurchases;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSurname() {
        return this.surname;
    }

    @Override
    public String toString(){
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nPurchases=" + nPurchases +
                ", username='" + username + '\'' +
                '}';
    }
}