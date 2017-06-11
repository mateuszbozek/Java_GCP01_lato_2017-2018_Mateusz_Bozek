package bozek.mateusz.crawler.exception;


public class CrolwerException extends Exception {

    public CrolwerException() {
        super();
    }


    public CrolwerException(String message) {
        super(message);
    }

    public CrolwerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrolwerException(Throwable cause) {
        super(cause);
    }
}
