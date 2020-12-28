package com.ts.aspect;


import com.ts.entity.BaseResp;
import com.ts.entity.User;
import com.ts.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author thesky
 */
@Component
@Aspect
public class LoginAspect {

    @Autowired
    private RedisService redisService;

    @Value("${unLoginApi}")
    private String unLoginApi;

    @Around("execution(public * com.ts.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        for (String path : unLoginApi.split(",")) {
            if (request.getServletPath().equals(path)) {
                return joinPoint.proceed();
            }
        }

        String token = request.getHeader("x-auth-token");
        if (token == null) {
            return ResponseEntity.ok(BaseResp.unlogin());
        }
        User user = (User) redisService.existKey(token);
        if (user == null) {
            return ResponseEntity.ok(BaseResp.unlogin());
        }

//        String methodName = joinPoint.getSignature().getName();
//        Class clazz = joinPoint.getTarget().getClass();
//        Class<?>[] paramClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
//
//        Method method = clazz.getMethod(methodName, paramClass);
//        if (method.isAnnotationPresent(Access.class)) {
//            Access access = method.getAnnotation(Access.class);
//            if (access.exclude().length != 0) {
//                for (AccessPeople accessPeople : access.exclude()) {
//                    if (accessPeople.getType() == member.getType()) {
//                        return ResponseEntity.ok(BaseResp.forbidden());
//                    }
//                }
//                return joinPoint.proceed();
//            }
//
//            for (AccessPeople accessPeople : access.value()) {
//                if (accessPeople.getType() == member.getType()) {
//                    return joinPoint.proceed();
//                }
//            }
//            return ResponseEntity.ok(BaseResp.forbidden());
//        }
        return joinPoint.proceed();
    }

}
