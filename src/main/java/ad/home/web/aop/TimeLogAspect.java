package ad.home.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeLogAspect implements MethodInterceptor {
    /**
     * 反射获取执行时间
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("时间方法-------------------------");
        long procTime = System.currentTimeMillis();

        if (log.isDebugEnabled()) {
            log.info("::- TimeHandler -::" + methodInvocation.getArguments()[0] + "开始执行:" + methodInvocation.getMethod() + "方法。");
        }

        try {
            Object result = methodInvocation.proceed();
            return result;
        } finally {
            procTime = System.currentTimeMillis() - procTime;
            if (log.isDebugEnabled()) {
                log.info("::- TimeHandler -::" + methodInvocation.getArguments()[0] + "执行：" + methodInvocation.getMethod() + "方法结束。");
            }
            if (log.isInfoEnabled()) {
                log.info("::- TimeHandler -::执行：" + methodInvocation.getMethod().getName() + " 方法共用" + procTime + "毫秒。");
            }
        }
    }

}
