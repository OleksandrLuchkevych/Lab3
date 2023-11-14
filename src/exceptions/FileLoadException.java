package exceptions;

public class FileLoadException extends Exception {
    public FileLoadException() {
        super("Can`t load file");
    }
}
