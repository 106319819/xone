package com.gosun.xone.core.log;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.SystemLog;
import com.gosun.xone.core.service.SystemLogService;
import com.gosun.xone.security.SecurityUtils;




/**
 * 系统日志，切面处理类，记录日志
 */
@Aspect
@Component
public class SystemLogAspect {
	
	@Autowired
	private SystemLogService systemLogService;
	
	@Pointcut("execution(* com.gosun.xone.core.service.*.*(..))")
	public void loggingPointcut() { 
		
	}

	@Around("loggingPointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long times = System.currentTimeMillis() - start;
		// 保存日志
		createSystemLog(point, start,times);
		return result;
	}

	private void createSystemLog(ProceedingJoinPoint joinPoint, Long start,Long times) {
		if(joinPoint.getTarget() instanceof SystemLogService) {
			return ;
		}
		SystemLog systemLog = new SystemLog();
		
		String username = SecurityUtils.getUsername();
		if(!Util.isNvl(username)) {
			JsonNode node = Util.parseJSONEx(username);
			systemLog.setAccountCode(node.get("accountCode").asText());
			systemLog.setAccountId(node.get("accountId").asLong());
			systemLog.setPersonId(node.get("personId").asLong());
			systemLog.setFullName(node.get("fullName").asText());
		}
		
				
		Class<?> clazz = joinPoint.getTarget().getClass();
		String packages =  clazz.getPackage().getName();
		String method = joinPoint.getSignature().getName();
				
		systemLog.setClazz(clazz.getName());
		systemLog.setMethod(method);
		systemLog.setPackages(packages);
		
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		if(!Util.isNvl(args) && args.length > 0) {
			systemLog.setArgs(Util.buildJSONEx(args[0]));
		}
		// 获取request,设置IP地址
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		systemLog.setHost(parseHost(request));
		systemLog.setStartTime(new Date(start));
		systemLog.setTimes(times); // 执行时长(毫秒)		
		this.systemLogService.create(systemLog);
	}

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	protected String parseHost(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	
        }
        
        
        return ip;
    }
}
