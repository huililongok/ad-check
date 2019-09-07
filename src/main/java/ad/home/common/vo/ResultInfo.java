package ad.home.common.vo;

import ad.home.common.fixed.ErrorStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultInfo <T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9003955719801331996L;

    // 状态编码 1：成功；2：失败
    private int statusCode;
    // 成功后返回内容
    private T data;
    // 说明信息
    private String message;

    public ResultInfo() {

    }

    public boolean isSuccess() {
        if(ErrorStatus.SUCCESS.getValue().intValue() == this.statusCode) {
            return true;
        }
        return false;
    }

    public ResultInfo(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }

    public static<T> ResultInfo<T> getSuccessResult() {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatusCode(ErrorStatus.SUCCESS.getValue());
        return resultInfo;
    }

    public static<T> ResultInfo<T> getSuccessResult(String msg) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatusCode(ErrorStatus.SUCCESS.getValue());
        resultInfo.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.SUCCESS.getName());
        return resultInfo;
    }

    public static<T> ResultInfo<T> getSuccessResult(T t) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatusCode(ErrorStatus.SUCCESS.getValue());
        resultInfo.setData(t);
        return resultInfo;
    }

    public static<T> ResultInfo<T> getSuccessResult(String msg, T data) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatusCode(ErrorStatus.SUCCESS.getValue());
        resultInfo.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.SUCCESS.getName());
        resultInfo.setData(data);
        return resultInfo;
    }

    public static<T> ResultInfo<T> getFailResult() {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatusCode(ErrorStatus.FAIL.getValue());
        return resultInfo;
    }

    public static<T> ResultInfo<T> getFailResult(String msg) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setStatusCode(ErrorStatus.FAIL.getValue());
        resultInfo.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.FAIL.getName());
        return resultInfo;
    }

    public void getSelfSuccessResult(String msg, T data) {
        this.setStatusCode(ErrorStatus.SUCCESS.getValue());
        this.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.SUCCESS.getName());
        this.setData(data);
    }

    public void getSelfSuccessResult(String msg) {
        this.setStatusCode(ErrorStatus.SUCCESS.getValue());
        this.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.SUCCESS.getName());
    }

    public void getSelfSuccessResult() {
        this.setStatusCode(ErrorStatus.SUCCESS.getValue());
        this.setMessage(ErrorStatus.SUCCESS.getName());
    }

    public void getSelfFailResult(String msg, T data) {
        this.setStatusCode(ErrorStatus.FAIL.getValue());
        this.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.FAIL.getName());
        this.setData(data);
    }

    public void getSelfFailResult(String msg) {
        this.setStatusCode(ErrorStatus.FAIL.getValue());
        this.setMessage(StringUtils.isNotBlank(msg) ? msg : ErrorStatus.FAIL.getName());
    }

    public void getSelfFailResult() {
        this.setStatusCode(ErrorStatus.FAIL.getValue());
        this.setMessage(ErrorStatus.FAIL.getName());
    }

}
