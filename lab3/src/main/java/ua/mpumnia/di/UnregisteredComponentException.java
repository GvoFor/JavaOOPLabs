package ua.mpumnia.di;

public class UnregisteredComponentException extends RuntimeException {
    public UnregisteredComponentException(String message) {
        super(message);
    }
}
