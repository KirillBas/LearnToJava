package ru.basharin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by drbah on 24.02.2017.
 */
public class ClientStorage {
    private final static String CLIENT_FILE_NAME = "User.bin";
    private final String fileName;

    public ClientStorage(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, Client> readAllClients() throws FileNotFoundException {
        Map<String, Client> result = new HashMap<>();
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split(" ");
                // TODO: 10.03.2017 проблемма. Integer.parseInt бросает NumberFormat нужно обработать внутри цикла
                Client client = new Client(words[0], words[1], words[2], Integer.parseInt(words[3]), Boolean.parseBoolean(words[4]));
                result.put(client.getLogin(), client);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeClientsToFile(Map<String, Client> clients) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (String login : clients.keySet()) {
                Client client = clients.get(login);
                bufferedWriter.append(client.getLogin())
                        .append(" ").append(client.getPassword())
                        .append(" ").append(client.getName())
                        .append(" ").append(Integer.toString(client.getAge()))
                        .append(" ").append(Boolean.toString(client.isAdmin()));
                bufferedWriter.newLine();
            }
        }
    }

    public void writeClientToFile(Client client) {
        File file = new File(CLIENT_FILE_NAME);
        try {
            file.createNewFile();
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file))) {
                os.writeObject(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client readClientFromFile() {
        File file = new File(CLIENT_FILE_NAME);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream os = new ObjectInputStream(new FileInputStream(file))) {
            return (Client) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteClientFile() {
        File file = new File(CLIENT_FILE_NAME);
        file.delete();
    }
}
