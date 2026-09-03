package com.etitc.crm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.etitc.crm.service..*(..))")
    public void registrarInicio(JoinPoint joinPoint) {

        System.out.println(
                "[AOP] INICIANDO MÉTODO: "
                        + joinPoint.getSignature().getName()
        );
    }

    @AfterReturning(
            pointcut = "execution(* com.etitc.crm.service..*(..))",
            returning = "resultado"
    )
    public void registrarFinalizacion(
            JoinPoint joinPoint,
            Object resultado
    ) {

        System.out.println(
                "[AOP] FINALIZANDO MÉTODO: "
                        + joinPoint.getSignature().getName()
        );
    }
}