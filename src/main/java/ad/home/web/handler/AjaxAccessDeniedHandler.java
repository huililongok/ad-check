package ad.home.web.handler;

import ad.home.common.fixed.ErrorStatus;
import ad.home.common.util.web.WebUtils;
import ad.home.common.vo.ResultInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResultInfo<Object> resultInfo = ResultInfo.getFailResult(ErrorStatus.PERMISSIONS.getName());
        resultInfo.setStatusCode(ErrorStatus.PERMISSIONS.getValue());
        String content = JSON.toJSONString(resultInfo);
        WebUtils.writeData(httpServletResponse, content, WebUtils.SYS_CONTENT_TYPE);
    }

}
