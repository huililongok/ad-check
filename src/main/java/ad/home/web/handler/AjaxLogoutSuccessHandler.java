package ad.home.web.handler;

import ad.home.common.util.web.WebUtils;
import ad.home.common.vo.ResultInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 退出登录
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ResultInfo<Object> resultInfo = ResultInfo.getFailResult("退出成功");
        String content = JSON.toJSONString(resultInfo);
        WebUtils.writeData(httpServletResponse, content, WebUtils.SYS_CONTENT_TYPE);
    }

}
