package com.chu.common.utils;

import com.chu.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ContextHolderEx {
    /***
     * 获取当前登录用户信息
     * @return
     */
    public static User getUserInfo() {
        User user = new User();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
            Authentication authentication = securityContextImpl.getAuthentication();
            user = (User) authentication.getPrincipal();
        } catch (Exception e) {
            log.error("获取当前登录用户信息异常！\n", e);
        }
        return user;
    }
}
