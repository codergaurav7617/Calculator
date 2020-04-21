package springbootdemo.demo.exception;

public class NumberFormatException extends Exception {
    private static final long serialVersionUID = -9079454849611061074L;
    public NumberFormatException(){
        super();
    }

    public NumberFormatException(final String message){
        super(message);
    }
}
