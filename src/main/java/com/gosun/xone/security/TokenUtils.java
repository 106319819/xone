package com.gosun.xone.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.gosun.common.DESUtil;
import com.gosun.common.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT工具类
 * 
 * @date Nov 20, 2018
 */
@Component
public class TokenUtils implements Serializable {

	private static String namePassword;

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名称
	 */
	private static final String USERNAME = Claims.SUBJECT;
	/**
	 * 创建时间
	 */
	private static final String CREATED = "created";
	/**
	 * 权限列表
	 */
	private static final String AUTHORITIES = "authorities";
	/**
	 * 密钥
	 */
	private static String SECRET;
	/**
	 * 有效期12小时
	 */
	private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

	@Value("${service.sercurity.name-password:pay*&%}")
	public void setNamePassword(String namePassword) {
		TokenUtils.namePassword = namePassword;
	}

	@Value("${service.sercurity.token-password:pay*&%}")
	public void setSECRET(String secret) {
		SECRET = secret;
	}

	/**
	 * 生成令牌
	 *
	 * @param userDetails 用户
	 * @return 令牌
	 */
	public static String generateToken(Authentication authentication) {
		Map<String, Object> claims = new HashMap<>(3);
		String username = DESUtil.encrypt(SecurityUtils.getUsername(authentication), namePassword);
		claims.put(USERNAME, username);
		claims.put(CREATED, new Date());
//	    claims.put(AUTHORITIES, authentication.getAuthorities());
		return generateToken(claims);
	}

	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	private static String generateToken(Map<String, Object> claims) {
		Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public static String getUsernameFromToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			if (!Util.isNvl(claims)) {
				String username = DESUtil.decrypt(claims.getSubject(), namePassword);
				return username;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public static String getUsername(Authentication authentication) {
		try {
			String username = DESUtil.decrypt(authentication.getName(), namePassword);
			return username;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据请求令牌获取登录认证信息
	 * 
	 * @param token 令牌
	 * @return 用户名
	 */
	public static Authentication getAuthenticationeFromToken(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = TokenUtils.getToken(request);
		return getAuthenticationeFromToken(token);
	}

	/**
	 * 根据请求令牌获取登录认证信息
	 * 
	 * @param token 令牌
	 * @return 用户名
	 */
	public static Authentication getAuthenticationeFromToken(String token) {
		Authentication authentication = SecurityUtils.getAuthentication();
		// 获取请求携带的令牌
		if (Util.isNvl(token)) {
			return authentication;
		}

		if (!Util.isNvl(authentication)) {
			if (validateToken(token, authentication.getName())) {
				return authentication;
			}
		}

		// 上下文中Authentication为空
		Claims claims = getClaimsFromToken(token);
		if (claims == null) {
			return null;
		}
		String username = claims.getSubject();
		if (username == null) {
			return null;
		}
		if (isTokenExpired(token)) {
			return null;
		}
		Object authors = claims.get(AUTHORITIES);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (authors != null && authors instanceof List) {
			for (Object object : (List) authors) {
//				authorities.add(new SimpleGrantedAuthority((String) ((Map) object).get("authority")));
				authorities.add(new SimpleGrantedAuthority((String) ((Map) object).get("role")));
			}
		}
		authentication = new AuthenticationToken(username, null, authorities, token);
		return authentication;
	}

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	private static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * 验证令牌
	 * 
	 * @param token
	 * @param username
	 * @return
	 */
	public static Boolean validateToken(String token, String username) {
		String name = getUsernameFromToken(token);
		return (username.equals(name) && !isTokenExpired(token));
	}

	/**
	 * 刷新令牌
	 * 
	 * @param token
	 * @return
	 */
	public static String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * 判断令牌是否过期
	 *
	 * @param token 令牌
	 * @return 是否过期
	 */
	public static Boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取请求token
	 * 
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String tokenHead = "Bearer ";
		if (token == null) {
			token = request.getHeader("token");
		} else if (token.contains(tokenHead)) {
			token = token.substring(tokenHead.length());
		}
		if ("".equals(token)) {
			token = null;
		}
		return token;
	}

}