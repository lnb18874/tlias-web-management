package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.JwtUtils;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取请求的URI
        String url = request.getRequestURI().toString();

        //2. 判断请求的URI是否包含login
        if(url.contains("login")){
            log.info("登录请求，不进行拦截");
            return true;
        }

        //3. 获取请求头中的令牌
        String jwt = request.getHeader("token");

        //4. 判断令牌是否为空
        if(!StringUtils.hasText(jwt)) {
            log.info("令牌为空，进行拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //5. 验证令牌
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("令牌解析失败，进行拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //6. 放行
        return true;
    }
}
