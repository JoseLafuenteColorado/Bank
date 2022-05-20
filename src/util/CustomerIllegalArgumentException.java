package util;


public class CustomerIllegalArgumentException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public CustomerIllegalArgumentException(String message) {
      super(message);
    }

}
