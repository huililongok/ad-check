package ad.home.web.aop;

import ad.home.common.util.web.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 前端切面日志
 */
@Aspect
@Slf4j
@Component
public class SystemFrontLogAspect {
    ThreadLocal<Long> processTime = new ThreadLocal<>();

    @Pointcut("execution(public * ad.home.controller..*..*(..))")
    public void systemFrontLog() {

    }

    @Before("systemFrontLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        processTime.set(System.currentTimeMillis());

        // 记录请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String isZip = request.getHeader("isZip");
        String charachterEncoding = request.getCharacterEncoding();
        StringBuffer logstr = new StringBuffer("【接收到请求】");
        logstr.append("[URL=").append(request.getRequestURL()).append("]");
        logstr.append("[HTTP_METHOD=").append(request.getMethod()).append("]");
        logstr.append("[HTTP_HEAD Type=").append(request.getHeader("Content-Type")).append("]");
        logstr.append("[HTTP_HEAD isZip=").append(isZip).append("]");
        logstr.append("[HTTP CharacterEncoding=").append(charachterEncoding).append("]");
        logstr.append("[IP=").append(request.getRemoteAddr()).append("]");
        logstr.append("[CLASS_METHOD=").append(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()).append("]");
        String text;
        if (request instanceof MultipartHttpServletRequest) {
            boolean zip = false;
            try {
                if (StringUtils.isNotBlank(isZip)) {
                    zip = Boolean.parseBoolean(isZip);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("参数中的解压数据类型错误。", e);
            }
            // 直接从流获取参数内容
            byte[] content = WebUtils.analysisStream(request, zip);
            if (StringUtils.isBlank(charachterEncoding)) {
                charachterEncoding = "utf-8";
            }
            try {
                text = new String(content, charachterEncoding);
            } catch (Exception e) {
                text = new String(content, "utf-8");
                log.error("请求内容数据解析错误。", e);
            }
        } else {
            text = "文件上传";
        }
        logstr.append("[Content=").append(text).append("]");

        logstr.append("[RequestParameters").append(WebUtils.analysisRequestParameters(request)).append("]");
        if (log.isInfoEnabled()) {
            log.info(logstr.toString());
        }
    }

//    @After("systemFrontLog()")
//    public void doAfterAdvice(JoinPoint point) {
//
//    }
    /**
     * 请求后
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "systemFrontLog()")
    public void doAfterReturning(JoinPoint point, Object ret) {
        if (log.isInfoEnabled()) {
            long endTime = System.currentTimeMillis();
            long passTime = endTime - processTime.get();

            String typename = point.getSignature().getDeclaringTypeName();
            String name = point.getSignature().getName();
            log.info("【SystemFrontLogAspect】执行类型：{}。返回内容：{}，执行耗时：{}毫秒。", (typename + " | " + name), ret, passTime);
        }
    }

    /**
     * 后置异常
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(throwing = "ex", pointcut = "systemFrontLog()")
    public  void errorThrows(JoinPoint joinPoint, Exception ex) {
        if (log.isInfoEnabled()) {
            String typeName = joinPoint.getSignature().getDeclaringTypeName();
            String name = joinPoint.getSignature().getName();
            log.info("【SystemFrontLogAspect】Type-> {} 异常：{}", (typeName + "|" + name), ex.getMessage());
        }
    }

}
