package ru.basharin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by drbah on 07.02.2017.
 */
public class Client implements Serializable {
    private String login;
    private String password;
    private String name; // transient
    private int age; // transient
    private boolean admin; // по умолчанию false, потому что примитив

    public Client(String login, String password, String name, int age, boolean admin) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
        this.admin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age &&
                admin == client.admin &&
                Objects.equals(login, client.login) &&
                Objects.equals(password, client.password) &&
                Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, age, admin);
    }

    public boolean isAdmin() {
        if (admin != false) {
            return admin;
        }
        return false;
    }

    private void writeObject(OutputStream outputStream) throws IOException {
        System.out.println("Hi");
    }

}
