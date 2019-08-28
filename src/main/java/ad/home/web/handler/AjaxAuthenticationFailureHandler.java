package ad.home.web.handler;

import ad.home.common.util.web.WebUtils;
import ad.home.common.vo.ResultInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String msg = "登录失败";
        if (e instanceof UsernameNotFoundException) {
            // 用户名不存在
            msg = "用户名不存在";
        } else if (e instanceof BadCredentialsException) {
            // 密码错误
            msg = "密码错误";
        } else if (e instanceof InsufficientAuthenticationException) {
            msg = "验证码错误";
        }
        ResultInfo<Object> resultInfo = ResultInfo.getFailResult(msg);
        String content = JSON.toJSONString(resultInfo);
        WebUtils.writeData(httpServletResponse, content, WebUtils.SYS_CONTENT_TYPE);
    }
}
