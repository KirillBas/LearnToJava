package ru.basharin.comparators;

import ru.basharin.Client;

import java.util.Comparator;

/**
 * Created by drbah on 24.03.2017.
 */
public class ClientAgeComparator implements Comparator<Client> {

    @Override
    public int compare(Client o1, Client o2) {
        if (o1.getAge() > o2.getAge()) {
            return -1;
        } else if (o1.getAge() < o2.getAge()) {
            return 1;
        } else {
            return 0;
        }
    }
}
