package ru.basharin;

/**
 * Created by drbah on 10.02.2017.
 */
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String message){
        super(message);
    }
}
