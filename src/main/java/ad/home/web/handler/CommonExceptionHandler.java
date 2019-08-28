package ad.home.web.handler;


import ad.home.common.exception.ServiceException;
import ad.home.common.fixed.ErrorStatus;
import ad.home.common.vo.ResultInfo;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * utn.aic.controller.CommonExceptionHandler：<全局异常处理>
 *
 * @author Sugar
 * @version <版本信息>
 *
 * @see <参见的内容>
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Object serviceExceptionHandle(HttpServletRequest request, HttpServletResponse response, ServiceException ex){
        return jsonResult( request, response, ex, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Object serviceExceptionHandle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex){
        return jsonResult( request, response, ex, ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public Object sqlExceptionHandle(HttpServletRequest request, HttpServletResponse response, SQLException ex){
        return jsonResult( request,response,  ex, "数据库错误！");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object commonExceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception ex){
        return jsonResult( request, response, ex, "系统错误！");
    }

    public Object jsonResult(HttpServletRequest request, HttpServletResponse response, Exception ex, String message) {
        return jsonResult(request , response , ex , message, ErrorStatus.FEIGNSERVER_ERR.getValue());
    }

    public Object jsonResult(HttpServletRequest request, HttpServletResponse response, Exception ex, String message ,Integer statusCode) {
        StringBuffer requestURL = request.getRequestURL();
        Map<?, ?> parameterMap = request.getParameterMap();
        log.error("访问URl【" + requestURL.toString() + "】 的时候抛出异常 -> " + ex.getMessage(), ex);
        log.error("【参数】：" + JSONObject.toJSON(parameterMap));
        return ResultInfo.getFailResult(StringUtils.isNotBlank(message) ? message:"系统繁忙！");
    }

    @Deprecated// 暂时不使用
    public static boolean isAjaxRequest(HttpServletRequest req) {
        //判断是否为ajax请求，默认不是
        boolean isAjaxRequest = false;
        if (!StringUtils.isBlank(req.getHeader("x-requested-with")) && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            isAjaxRequest = true;
        }
        return isAjaxRequest;
    }

}
