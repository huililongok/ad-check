package ad.home.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestTokenException extends ValidateException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RequestTokenException(int errorCode, String errorString) {
        super(errorString);
        this.errorCode = errorCode;

        log.error(String.format("RequestTokenException: errorCode=%d, errorString=%s", errorCode, errorString), this);
    }

    public RequestTokenException(int errorCode, String errorString, Throwable cause) {
        super(errorString, cause);
        this.errorCode = errorCode;
        log.error(String.format("RequestTokenException: errorCode=%d, errorString=%s", errorCode, errorString), this);
    }

    public RequestTokenException(String errorString,String path) {
        super(errorString);
        setErrorPath(path);
        log.error(String.format("RequestTokenException: errorString=%s", errorCode, errorString), this);
    }
    public RequestTokenException(String errorString) {
        super(errorString);
        log.error(String.format("RequestTokenException: errorString=%s", errorCode, errorString), this);
    }

}
