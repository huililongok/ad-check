package ad.home.web.annotation;

import java.lang.annotation.*;

/**
 * Created by YON on 2017/11/6.
 * 系统日志注解
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String module()  default "";
    String methods()  default "";
}
