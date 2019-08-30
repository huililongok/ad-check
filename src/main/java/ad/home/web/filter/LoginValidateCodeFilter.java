package ad.home.web.filter;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginValidateCodeFilter extends UsernamePasswordAuthenticationFilter {
    public LoginValidateCodeFilter(String validataUrl) {
        AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(validataUrl, "POST");
        this.setRequiresAuthenticationRequestMatcher(requestMatcher);
        this.setAuthenticationManager(getAuthenticationManager());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestURI = request.getRequestURI();
        String expect = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        String kaptcha = request.getParameter("vcode");
        System.out.println("来自：" + requestURI + "，接收到验证码：" + (kaptcha == null ? "" : kaptcha));
        if (expect == null || StringUtils.isBlank(kaptcha)) {
            InsufficientAuthenticationException ext = new InsufficientAuthenticationException("验证码为空！");
            throw ext;
        } else if (expect != null && !expect.equalsIgnoreCase(kaptcha)) {
            InsufficientAuthenticationException ext = new InsufficientAuthenticationException("验证码错误！");
            throw ext;
        }
        // return new Authentication
        return super.attemptAuthentication(request, response);
        // return null;
    }

}
