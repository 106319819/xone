package com.gosun.xone.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;


/**
 * 登录认证过滤器
 * @date Nov 20, 2018
 */
@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter {

	
	@Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	// 获取token, 并检查登录状态
    	log.debug(request.getRequestURI());
        SecurityUtils.checkAuthentication(request);
        chain.doFilter(request, response);
    }
    
}