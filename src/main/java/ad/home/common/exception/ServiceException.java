package ad.home.common.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int errorCode = 0;

    private String errorString;

    private Object[] parameters;

    public ServiceException(String errorString) {
        super(errorString);
    }

    public ServiceException(int errorCode, String errorString, Object[] parameters) {
        super(errorString);
        this.errorCode = errorCode;
        this.errorString = errorString;
        this.parameters = parameters;
    }

    public ServiceException(int errorCode, String errorString, Object[] parameters, Throwable cause) {
        super(errorString, cause);
        this.errorCode = errorCode;
        this.errorString = errorString;
        this.parameters = parameters;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
