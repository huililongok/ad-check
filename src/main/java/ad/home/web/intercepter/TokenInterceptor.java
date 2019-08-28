package ad.home.web.intercepter;

import ad.home.common.exception.RequestTokenException;
import ad.home.common.validator.TokenProcessor;
import ad.home.web.annotation.SubmitToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by YON on 2017/11/6.
 */
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    public TokenInterceptor(){

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SubmitToken token = method.getAnnotation(SubmitToken.class);
            if (token != null) {
                if (!isRepeatSubmit(request, token)) {
                    log.error("无效的token！");
                    throw new RequestTokenException(token.message(),token.path());
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request,SubmitToken token) {
        return TokenProcessor.getInstance().isTokenValid(request, true, token.name());
    }


}
