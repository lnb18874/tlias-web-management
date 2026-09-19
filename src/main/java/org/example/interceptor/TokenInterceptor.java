package org.example.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.CurrentHolder;
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

        //5. 验证令牌，并把当前登录用户放入 ThreadLocal（供切面/业务层获取操作人）
        try {
            Claims claims = JwtUtils.parseJWT(jwt);
            Object idClaim = claims.get("id");
            if (idClaim == null) {
                log.info("令牌中缺少 id 声明，进行拦截");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            CurrentHolder.setCurrentId(Integer.valueOf(idClaim.toString()));
        } catch (Exception e) {
            log.info("令牌解析失败，进行拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //6. 放行
        return true;
    }

    /**
     * 请求结束后必须清理 ThreadLocal，线程池复用线程时会残留上一个请求的用户身份
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CurrentHolder.remove();
    }
}
