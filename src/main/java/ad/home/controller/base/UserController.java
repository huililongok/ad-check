package ad.home.controller.base;

import ad.home.common.vo.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户信息相关
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    /**
     * 用户退出后跳转地址
     * @return
     */
    @RequestMapping(value = "/sign/out-succ", method = {RequestMethod.GET})
    public ResultInfo userLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        ResultInfo<Object> resultInfo = ResultInfo.getFailResult("退出成功");
        return resultInfo;
    }


}
