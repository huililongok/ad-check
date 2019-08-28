package ad.home.common.exception;

public class ValidateException extends Exception {
    private static final long serialVersionUID = 1L;

    protected int errorCode = 0;
    private String errorPath;

    public ValidateException(int errorCode, String errorString) {
        super(errorString);
        this.errorCode = errorCode;
    }

    public ValidateException(int errorCode, String errorString, Throwable cause) {
        super(errorString, cause);
        this.errorCode = errorCode;
    }

    public ValidateException(String errorString, String errorPath, Throwable cause) {
        super(errorString, cause);
        this.errorPath = errorPath;
    }

    public ValidateException(String errorString,String path) {
        super(errorString);
        this.errorPath = path;
    }

    public ValidateException(String errorString) {
        super(errorString);
    }

    public ValidateException(String errorString, Throwable cause) {
        super(errorString, cause);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorString() {
        return this.getMessage();
    }

    public String getErrorPath() {
        return errorPath;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = errorPath;
    }

}
