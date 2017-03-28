package ru.basharin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by drbah on 09.02.2017.
 */
public class ClientRegistrant {
    private final Map<String, Client> clientsInfo = new HashMap<>();

    private Scanner scanner;

    private ClientStorage clientStorage;

    public ClientRegistrant(Scanner scanner) {
        this.scanner = scanner;
        this.clientStorage = new ClientStorage("test.txt");
        try {
            clientsInfo.putAll(clientStorage.readAllClients());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Client> getClientsInfo() {
        return clientsInfo;
    }

    public void signUp() {
        System.out.println("Регистрация нового пользователя");
        System.out.println("*******************************");
        String login;
        while (true) {
            try {
                System.out.println("Введите логин (# - выход):");
                login = scanner.nextLine();
                if (login.equals("#")) {
                    return;
                }
                validateLogin(login);
                break;
            } catch (AlreadyExistsException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        String password;
        while (true) {
            try {
                System.out.println("Введите пароль (# - выход):");
                password = scanner.nextLine();
                if (password.equals("#")) {
                    return;
                }
                validatePassword(password);
                break;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        String name;
        while (true) {
            try {
                System.out.println("Ведите имя (# - выход):");
                name = scanner.nextLine();
                if (name.equals("#")) {
                    return;
                }
                validateName(name);
                break;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }

        int age;
        while (true) {
            try {
                System.out.println("Введите ваш возраст (# - выход):");
                age = scanner.nextInt();
                if (Integer.toString(age).equals("#")) {
                    return;
                }
                validateAge(age);
                break;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        boolean admin = false;

        Client client = new Client(login, password, name, age, admin);
        clientsInfo.put(login, client);
        try {
            clientStorage.writeClientsToFile(clientsInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 4) {
            throw new NullPointerException("Пароль должен быть длиннее 4 символов");
        }
    }

    private void validateLogin(String login) throws AlreadyExistsException {
        if (login == null || login.isEmpty()) {
            throw new NullPointerException("Пустой логин");
        }
        if (clientsInfo.containsKey(login)) {
            throw new AlreadyExistsException("Данный логин занят");
        }
    }

    private void validateName(String name) throws NullPointerException {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Пустое имя");
        }
    }

    private void validateAge(int age) throws NullPointerException {
        if (age == 0) {
            throw new NullPointerException("Пустой возраст");
        }
    }

    public Client signIn() {
        System.out.println("Вход в систему");
        System.out.println("**************");
        String login;
        Client client = null;
        while (true) {
            try {
                System.out.println("Для входа введите логин (# - выход)");
                login = scanner.nextLine();
                if (login.equals("#")) {
                    return null;
                }
                client = getClientByLogin(login);
                break;
            } catch (AlreadyExistsException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        String password;
        while (true) {
            try {
                System.out.println("Введите пароль (# - выход)");
                password = scanner.nextLine();
                if (password.equals("#")) {
                    return null;
                }
                validateClientPassword(password, client);
                break;
            } catch (IllegalAccessException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        return client;
    }

    public Client getClientByLogin(String login) throws AlreadyExistsException {
        if (login == null || login.isEmpty()) {
            throw new NullPointerException("Пустой логин");
        }
        Client result = clientsInfo.get(login);
        if (result == null) {
            throw new NullPointerException("Не найден пользователь с таким логином");
        }
        return result;
    }

    public void validateClientPassword(String password, Client client) throws IllegalAccessException {
        if (password == null || password.isEmpty()) {
            throw new NullPointerException("Ввели пустой пароль");
        }
        if (!client.getPassword().equals(password)) {
            throw new IllegalAccessException("Неправильный пароль");
        }
    }

    public void makeClientAdmin(Client client) {
        clientsInfo.get(client.getLogin()).setAdmin(true);
        try {
            clientStorage.writeClientsToFile(clientsInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeClientName(Client client) {
        String newName = null;
        while (true){
            System.out.println("Для изменения имени введите новое имя:");
            newName = scanner.nextLine();
            clientsInfo.get(client.getLogin()).setName(newName);
            try {
                clientStorage.writeClientsToFile(clientsInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(newName.equals("#")) {
                return;
            }
            System.out.println("Спасибо за новое имя: " + newName);
        }
    }
    public void changeClientAge(Client client) {
        String newAge;
        while (true) {
            System.out.println("Для изменения возраста введите новый возраст:");
            newAge =scanner.nextLine();
            clientsInfo.get(client.getLogin()).setAge(Integer.parseInt(newAge));
            try {
                clientStorage.writeClientsToFile(clientsInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(newAge.equals("#")) {
                return;
            }
            System.out.println("Теперь я моложе? " + newAge);
        }

    }

    public void deleteClient(Client client) {
        clientsInfo.remove(client.getLogin());
        try {
            clientStorage.writeClientsToFile(clientsInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
