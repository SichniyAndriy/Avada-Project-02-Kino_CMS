package avada.spacelab.kino_cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ServiceLogger {

    private final Logger logger = LoggerFactory.getLogger(ServiceLogger.class);


    @Pointcut("within(avada.spacelab.kino_cms.service.*)")
    public void allServiceMethods() {
    }

    @Before("allServiceMethods()")
    public void before(JoinPoint joinPoint) {
        logger.info("Method {}() called in class {}",
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getInterfaces()[0].getSimpleName()
        );
    }

    @After("allServiceMethods()")
    public void after(JoinPoint joinPoint) {
        logger.info("Method {}() finished", joinPoint.getSignature().getName());
    }

    @AfterThrowing("allServiceMethods()")
    public void afterThrowing(JoinPoint joinPoint) {
        logger.debug("Method {}() thrown", joinPoint.getSignature().getName());
    }

}
