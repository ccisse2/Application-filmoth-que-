package fr.eni.tp.filmotheque.bll;

public class BLLException extends Exception{
    public BLLException(String message) {
        super(message);
    }
    public BLLException(String message, Throwable cause) {
        super(message, cause);
    }
}
