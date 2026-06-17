package geeziel.order_service.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ToolLoggingAspect {

    @Around("@annotation(org.springframework.ai.tool.annotation.Tool)")
    public Object logToolExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("AI tool started: {} with args={}", methodName, args);

        try {
            Object result = joinPoint.proceed();

            log.info("AI tool finished: {} result={}", methodName, result);

            return result;
        } catch (Exception e) {
            log.error("AI tool failed: {} args={}", methodName, args, e);
            throw e;
        }
    }
}
