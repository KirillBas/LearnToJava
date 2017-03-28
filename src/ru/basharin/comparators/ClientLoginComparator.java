package ru.basharin.comparators;

import ru.basharin.Client;

import java.util.Comparator;

/**
 * Created by drbah on 24.03.2017.
 */
public class ClientLoginComparator implements Comparator<Client> {
    public int compare(Client o1, Client o2) {
        return o1.getLogin().compareTo(o2.getLogin());
    }
}
