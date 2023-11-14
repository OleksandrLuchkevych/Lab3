package exceptions;

public class InvalidProductDataException extends Exception {
    public InvalidProductDataException() {
        super("Invalid product data");
    }
}
