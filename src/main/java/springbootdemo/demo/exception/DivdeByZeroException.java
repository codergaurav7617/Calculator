package springbootdemo.demo.exception;
public class DivdeByZeroException extends RuntimeException {
    private static final long serialVersionUID = -470180507998010368L;

    public DivdeByZeroException(){
        super();
    }

    public  DivdeByZeroException(final String message) {
        super(message);
    }
}
