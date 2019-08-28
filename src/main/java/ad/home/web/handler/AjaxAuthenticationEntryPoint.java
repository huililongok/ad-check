package ad.home.web.handler;

import ad.home.common.fixed.ErrorStatus;
import ad.home.common.util.web.WebUtils;
import ad.home.common.vo.ResultInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ResultInfo<Object> resultInfo = ResultInfo.getFailResult(ErrorStatus.UN_LOGIN.getName());
        resultInfo.setStatusCode(ErrorStatus.UN_LOGIN.getValue());
        String content = JSON.toJSONString(resultInfo);
        WebUtils.writeData(httpServletResponse, content, WebUtils.SYS_CONTENT_TYPE);
    }

}
