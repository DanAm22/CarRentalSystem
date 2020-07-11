package exceptions;

public class DriverNameException extends RuntimeException{

    public DriverNameException(){
        super();
    }

    public DriverNameException(String message){
        super(message);
    }
}
