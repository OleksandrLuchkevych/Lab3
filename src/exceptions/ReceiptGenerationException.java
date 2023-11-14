package exceptions;

public class ReceiptGenerationException extends Exception {
    public ReceiptGenerationException() {
        super("Can`t generate receipt file, pay receipt first");
    }
}
