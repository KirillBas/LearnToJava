package ru.basharin;

import ru.basharin.comparators.ClientAgeComparator;
import ru.basharin.comparators.ClientLoginComparator;
import ru.basharin.comparators.ClientNameComparator;

import java.util.*;

/**
 * Created by drbah on 10.03.2017.
 */
public class View {

    private Scanner scanner = new Scanner(System.in);
    private ClientRegistrant clientRegistrant = new ClientRegistrant(scanner);
    private ClientStorage clientStorage = new ClientStorage("test.txt");
    private Client client = null;

    private void printGreetingMenu() {
        System.out.println("Приветствуем вас в Google.");
        System.out.println("**************************");
        System.out.println("Нажмите 1 для регистрации в системе");
        System.out.println("Нажмите 2 для входа в систему");
        System.out.println("Нажмите # для завершения");
    }

    private void printPersonalMenu() {
        System.out.println("Приветствуем вас " + client.getName() + " в личном кабинете.");
        System.out.println("*****************************");
        System.out.println("На данный момент меню находится в разработке");
        if (client.isAdmin()) {
            System.out.println("Нажмите 1 для вывода всех зарегестрированных пользователей");
        }
        System.out.println("Нажмите # для выхода");
    }

    private void doWorkWithClient() {
        String input;
        while (true) {
            printPersonalMenu();
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    doManageClients();
                    break;
                case "#":
                    return;
            }
        }
    }

    private void printManageClientsMenu() {
        System.out.println("Список зарегестрированных пользователей");
        System.out.println("Выберите пользователя введя логин");
        System.out.println("Нажмите # для выхода");
        System.out.println("Нажмите 1 для сортировки по Возрасту");
        System.out.println("Нажмите 2 для сортировки по Имени");
        System.out.println("Нажмите 3 для сортировки по Логину");
    }

    private void printManageClientMenu(Client client) {
        System.out.println("Выбранный клиент: " + client.getLogin());
        System.out.println("Имя: " + client.getName() + ";");
        System.out.println("Возраст: " + client.getAge() + ";");
        if (client.isAdmin()) {
            System.out.println("Тип привилегий: Администратор");
        }
        System.out.println("Нажмите 1 для обновления данных клиента");
        System.out.println("Нажмите 2 для удаления клиента");
        System.out.println("Нажмите 3 для назначения клиента администратором");
        System.out.println("Нажмите # для выхода");
    }

    public void doManageClients() {
        String login;
        Map<String, Client> clientsInfo;
        Comparator<Client> currentComparator = new ClientAgeComparator();
        while (true) {
            clientsInfo = clientRegistrant.getClientsInfo();
            printManageClientsMenu();
            prettyPrintClientsInfo(clientsInfo, currentComparator);
            login = scanner.nextLine();
            switch (login) {
                case "#":
                    return;
                case "1":
                    currentComparator=new ClientAgeComparator();
                    break;
                case "2":
                    currentComparator = new ClientNameComparator();
                    break;
                case "3":
                    currentComparator = new ClientLoginComparator();
                    break;
                default:
                    Client client = clientsInfo.get(login);
                    if (client != null) {
                        doManageClient(client);
                    } else {
                        System.out.println("Клиент с логином " + login + " не найден");
                    }
                    break;
            }
        }
    }

    private void prettyPrintClientsInfo(Map<String, Client> clientsInfo, Comparator<Client> comparator) {
        List<Client> clients = new ArrayList<>(clientsInfo.values());
        clients.sort(comparator);
        for (Client client : clients) {
            System.out.println(client.getLogin() + " (" + client.getName() + ") "+client.getAge());
        }
    }

    public void changeClientData(Client client) {
        String input;
        while (true) {
            input = scanner.nextLine();
            printChangeClientMenu();
            if (input.equals("#")) {
                return;
            }
            switch (input) {
                case "1":
                    clientRegistrant.changeClientName(client);
                    break;
                case "2":
                    clientRegistrant.changeClientAge(client);
                    break;
            }
        }
    }

    private void printChangeClientMenu() {
        System.out.println("Нажмите 1 для изменения Имени");
        System.out.println("Нажмите 2 для изменения возраста");
    }

    //todo Дописать метод
    public void doManageClient(Client client) {
        String input;
        while (true) {
            printManageClientMenu(client);
            input = scanner.nextLine();
            if (input.equals("#")) {
                return;
            }
            switch (input) {
                case "1":
//                    todo дописать метод
                    changeClientData(client);
                    break;
                case "2":
                    clientRegistrant.deleteClient(client);
                    return;
                case "3":
                    clientRegistrant.makeClientAdmin(client);
                    break;
            }
        }

    }


    /*
    1. Завести понятие админ
    2. Для админа добавить пункты меню
    2.1. Вывести всех зарегестрированных пользователей
    2.2. Вывести количество зарегестрированных пользователей
    3. Сделать пользователя админом
    4. Удалять пользователей
     */
    public void run() {
        String input;
        while (true) {
            client = clientStorage.readClientFromFile();
            if (client != null) {
                doWorkWithClient();
                clientStorage.deleteClientFile();
            }
            printGreetingMenu();
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    clientRegistrant.signUp();
                    break;
                case "2":
                    client = clientRegistrant.signIn();
                    if (client != null) {
                        clientStorage.writeClientToFile(client);
                        doWorkWithClient();
                        clientStorage.deleteClientFile();
                    } else {
                        break;
                    }
                case "#":
                    return;
            }
        }
    }
}
