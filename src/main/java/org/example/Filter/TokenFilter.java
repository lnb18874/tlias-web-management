package org.example.Filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.CurrentHolder;
import org.example.utils.JwtUtils;

import java.io.IOException;


@Slf4j
//@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1. 获取请求的URI
        String url = request.getRequestURI().toString();

        //2. 判断请求的URI是否包含login
        if(url.contains("login")){
            log.info("登录请求，不进行拦截");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //3.获取请求头中的令牌
        String jwt = request.getHeader("token");

        //4. 判断令牌是否为空
        if(jwt == null||jwt.isEmpty()){
            log.info("令牌为空，进行拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //5.解析令牌
        try{
            Claims claims =JwtUtils.parseJWT(jwt);
            Integer empId=Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("令牌解析成功");
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            log.info("令牌解析失败，进行拦截");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //6. 放行
        filterChain.doFilter(servletRequest, servletResponse);

        //7. 移除当前线程的令牌信息
        CurrentHolder.remove();
    }
}
