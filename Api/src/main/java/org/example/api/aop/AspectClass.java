package org.example.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.user_application.services.IUserService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectClass {

    private final IUserService userService;

    public AspectClass(IUserService userService) {
        this.userService = userService;
    }

    @Before("@within(UpdateActivity) || @annotation(UpdateActivity)")
    public void updateLastActivity(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Long) {
                userService.updateUsersLastActivity((Long) arg);
                break;
            }
        }

    }
}
