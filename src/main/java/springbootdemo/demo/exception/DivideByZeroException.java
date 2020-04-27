package springbootdemo.demo.exception;

public class DivideByZeroException extends RuntimeException {
    private static final long serialVersionUID = -470180507998010368L;

    public DivideByZeroException(){
        super();
    }

    public DivideByZeroException(final String message) {
        super(message);
    }
}
