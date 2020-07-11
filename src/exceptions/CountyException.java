package exceptions;

public class CountyException extends RuntimeException {

    public CountyException() {
        super();
    }

    public CountyException(String message) {
        super(message);
    }
}
