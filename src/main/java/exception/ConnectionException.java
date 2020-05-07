package exception;

public class ConnectionException extends Exception {

    private Integer code;
    private String msg;

    public ConnectionException() {
        super();
    }

    public ConnectionException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.code+" : "+this.msg;
    }
}
