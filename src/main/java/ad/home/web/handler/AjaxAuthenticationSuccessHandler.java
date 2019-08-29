package ad.home.web.handler;

import ad.home.common.util.web.WebUtils;
import ad.home.common.vo.ResultInfo;
import ad.home.pojo.dbentity.UserEntity;
import ad.home.web.solve.SessionResolve;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功AJAX返回
 */
@Slf4j
@Component
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /**
     * 登录系统成功后返回，当前登录人信息JSON格式
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        UserEntity user = SessionResolve.getInstance().getSecuritySessionUser();
        user.setPassword(null);
        log.info("登录成功-用户为：" + user.getUserName());
        // 这里只返回了对象，还可以设置角色、权限等
        ResultInfo<Object> resultInfo = ResultInfo.getSuccessResult("登录成功", user);
        String content = JSON.toJSONString(resultInfo);
        WebUtils.writeData(httpServletResponse, content, WebUtils.SYS_CONTENT_TYPE);
    }


}
