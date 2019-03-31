# xone
xone 基础信息资源整合平台
解决基础功能分散在各业务子系统，想通过此平台，整合基础资源，统一管理、统一授权、统一认证、统一登录、统一访问、统一注册、统一服务的问题
1、前言
	在做的很多项目，都存在如组织、人员、角色、权限、子系统、应用、模块、菜单、操作界面统一等的基础功能，如何有效的抽象这些公共的内容，而不是每个项目都有自己的一套，因此产生了在网上寻找开源项目来代替。然而，未如人意，因此想利用业务时间自己做一下，就有了XONE的想法。

2、功能设想
	组织管理
	人员管理
	子系统管理
	应用管理
	模块管理
	菜单管理
	资源管理
	角色、权限管理（子系统、模块、菜单、资源、数据）
	动态配置管理
	动态生成管理界面
	统一认证
3、技术路线
	SpringBoot2(cloud/jpa...)
	MySQL...
	vue/elementUI
4、应用集成示例
比如有CMS内容管理系统，是基于java的web应用，步骤如下：
	在应用中创建LoginFilter，并在过滤器中检查当前请求者是否已经登录，
	未登录则通过http在后台，发送token到XONE平台的/login-verify/进行登录验证，
	成功后，会返回当前登录者的JSON对象，应用做登录处理；

示例如下：
（1）、在应用中创建LoginFilter
package com.gesun.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.gesun.cms.exception.CMSException;
import com.gesun.cms.global.ErrConst;
import com.gesun.cms.util.CMSUtil;
import com.gesun.cms.util.HTTPUtil;
import com.gesun.cms.util.HttpStateCode;
import com.gesun.framework.exception.BaseException;
import com.gesun.framework.servlet.BaseServlet;
import com.gesun.framework.util.Util;


/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
	protected static Logger logger = Logger.getLogger(LoginFilter.class);
	String loginURL;
	String except;
    private String origin; //域名
    //方法
    private String methods;
    private String headers;
    private String maxAge;
    private String credentials;
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// pass the request along the filter chain
		HttpServletRequest hq = (HttpServletRequest) request;
		String method = hq.getMethod();
		if("OPTIONS".equalsIgnoreCase(method)) {
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.setHeader("Access-Control-Allow-Origin", this.origin);
			resp.setHeader("Access-Control-Allow-Methods", this.methods);
			resp.setHeader("Access-Control-Max-Age", this.maxAge);
			resp.setHeader("Access-Control-Allow-Headers", this.headers);
			resp.setHeader("Access-Control-Expose-Header", this.headers);
			if("true".equals(this.credentials)) {
				resp.setHeader("Access-Control-Allow-Credentials", this.credentials);
			}
			resp.setStatus(HttpStatus.SC_OK);
//			StringBuffer sb = new StringBuffer("ok");
//			BaseServlet.writeText(sb, hq, (HttpServletResponse)response);
//			.writeError(new DSException(ErrConst.ERR_SESSION_TIMEOUT), (HttpServletRequest)request, (HttpServletResponse)response);
			return;
		}
		
		
		
		String uri = hq.getRequestURI();
		logger.debug(String.format("uri -> %s",uri));
		if(uri.matches(except)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = null;
		Enumeration<String> names = hq.getHeaderNames();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			String value = hq.getHeader(name);
			logger.debug(String.format("%s : %s",name,value));
			if("token".equalsIgnoreCase(name) 
					|| "Authorization".equalsIgnoreCase(name)) {
				token = value;
			}
		}
		logger.debug("***params***");
		names = hq.getParameterNames();
		if(null != names) {
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			String value = hq.getParameter(name);
			logger.debug(String.format("%s : %s",name,value));
		}
		}
		logger.debug("***cookies***");
		Cookie[] cookies  = hq.getCookies();
		if(null != cookies) {
		for(Cookie cookie: cookies) {
			
			String name = cookie.getName();
			String value = cookie.getValue();
			logger.debug(String.format("%s : %s",name,value));
			if("token".equalsIgnoreCase(name) && null == token) {
				token = value;
			}
		}
		}
		
		logger.debug(String.format("token:%s",token));
		HttpSession session = hq.getSession(true);
		if(!Util.isNvl(session.getAttribute(token))) {
			chain.doFilter(request, response);
			return;
		}
		
		Header header = new Header("Authorization", token);
		List<Header> headers = new ArrayList<>();
		headers.add(header);
		StringBuffer out = new StringBuffer();
		try {
			int state = HTTPUtil.get(null, "http://localhost:9090/login-verify/", headers, out);
			if(HttpStatus.SC_OK != state){
				//http请求错误
				String message = HttpStateCode.getDescription(state);
				CMSException.throwing(com.gesun.cms.global.ErrConst.ERR_HTTP_INVOKE, message);
			}
			JSONObject jo = CMSUtil.parseJSONObject(out.toString());
			if(jo.getString("status").equals("success")) {
				jo.put("token", token);
				session.setAttribute(token, jo);
			}else {
				CMSException.throwing(ErrConst.ERR_GET_LOGINUSER, jo.toString());
			}
			
			chain.doFilter(request, response);
			
		} catch (BaseException e) {
			// TODO: handle exception
			logger.error(e);
			BaseServlet.writeError(e,(HttpServletResponse) response);
		}catch(JSONException e) {
			logger.error(e);
			BaseServlet.writeError(new CMSException(e),(HttpServletResponse) response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.loginURL = filterConfig.getInitParameter("loginURL");
		this.except = filterConfig.getInitParameter("except");
		this.origin = Util.nvl(filterConfig.getInitParameter("origin"),"*");
		this.methods = Util.nvl(filterConfig.getInitParameter("methods"),"*");
		this.headers = Util.nvl(filterConfig.getInitParameter("headers"),"*");
		this.maxAge = Util.nvl(filterConfig.getInitParameter("maxAge"),"3600");
		this.credentials = Util.nvl(filterConfig.getInitParameter("credentials"),"true");
	}

}


（2）并在web.xml增加如下配置：
<!-- 集成XONE平台支持开始 -->
	<filter>
    <description>
		</description>
    <display-name>LoginFilter</display-name>
    <filter-name>LoginFilter</filter-name>
    <filter-class>com.gesun.framework.filter.LoginFilter</filter-class>
    <init-param>
      <description>登录页面</description>
      <param-name>loginURL</param-name>
      <param-value>/admin/login/login.jsp</param-value>
    </init-param>
    <init-param>
      <description>要排除的URL,正则表达式</description>
      <param-name>except</param-name>
      <param-value>[\s\S]*/admin/LoginServlet[\s\S]*|[\s\S]*/admin/login/login.jsp[\s\S]*|[\s\S]*/js/[\s\S]*|[\s\S]*/css/[\s\S]*|[\s\S]*/kindeditor/[\s\S]*|[\s\S]*/mobile/[\s\S]*|[\s\S]*/notify/[\s\S]*|[\s\S]*/upload/[\s\S]*</param-value>
    </init-param>
    <init-param>
      <description>域名</description>
      <param-name>origin</param-name>
      <param-value>*</param-value>
    </init-param>
    <init-param>
      <description>方法</description>
      <param-name>methods</param-name>
      <param-value>GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH</param-value>
    </init-param>
    <init-param>
      <description>头</description>
      <param-name>headers</param-name>
      <param-value>Origin,X-Requested-With,Content-Type,Accept,Authorization</param-value>
    </init-param>
    <init-param>
      <description></description>
      <param-name>maxAge</param-name>
      <param-value>3600</param-value>
    </init-param>
    
    <init-param>
      <description></description>
      <param-name>credentials</param-name>
      <param-value>false</param-value>
    </init-param>   
  </filter>
  <filter-mapping>
    <filter-name>LoginFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!-- 集成XONE平台支持结束 -->