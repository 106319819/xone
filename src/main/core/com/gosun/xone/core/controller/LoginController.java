package com.gosun.xone.core.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Error;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Account;
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.entity.SubSystem;
import com.gosun.xone.core.service.AccountService;
import com.gosun.xone.core.service.PersonService;
import com.gosun.xone.core.service.SubSystemService;
import com.gosun.xone.security.AuthenticationToken;
import com.gosun.xone.security.SecurityUtils;
import com.gosun.xone.security.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class LoginController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PersonService personService;
	@Autowired
	private SubSystemService subSystemService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping("admin/login")
	public Result<?> login(@RequestBody Account loginAccount,HttpServletRequest request){
//		if("webmaster".equals(loginAccount.getAccountCode())){
//			//如果是管理员，暂时先不认证
//			return Result.success(loginAccount);
//		}
		Account account = accountService.findByAccountCode(loginAccount.getAccountCode());
		if(Util.isNvl(account)) {
			return Result.error(XoneException.instance(Error.NO_FOUND_USER));
		}
		
		String password = Util.MD5(loginAccount.getPassword());
		if(!password.equals(account.getPassword()))
		{
			return Result.error(XoneException.instance(Error.ERROR_PASSWORD));
		}
		
		AuthenticationToken token = SecurityUtils.login(request,account.getAccountCode(), password, authenticationManager);
		Person person = personService.findByAccountId(account.getAccountId());
		return Result.success(this.buildAuthentication(account,person, token.getToken()) );
		
	}
	/**
	 * 登录校验
	 * @param loginAccount
	 * @param request
	 * @return
	 */
	@RequestMapping("login-verify/")
	public Result<?> loginVerify(HttpServletRequest request){
		log.debug("session id: {}",request.getRequestedSessionId());
				
		Cookie[] cookies = request.getCookies();
		if(!Util.isNvl(cookies)) {
			for(Cookie cookie : cookies) {
				log.debug("cookie - {} : {}",cookie.getName(),cookie.getValue());
			}
		}
		
		Authentication authentication = TokenUtils.getAuthenticationeFromToken(request);
		if(Util.isNvl(authentication)) {
			return Result.error(XoneException.instance(Error.NO_PRIVILEGE));
		}
		Account account = accountService.findByAccountCode(authentication.getName());
		Person person = personService.findByAccountId(account.getAccountId());
		return Result.success(this.buildAuthentication(account,person, null) );
	}
	
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<Account> result = accountService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("find-by-id/{accountId}")
	public Result<?> findById(@PathVariable("accountId") Long accountId){
		Account result = accountService.findById(accountId);
		return Result.success(result);
	}
	
	@RequestMapping("delete-by-id/{accountId}")
	public Result<?> delete(@PathVariable("accountId") Long accountId){
		accountService.delete(accountId);
		return Result.success(accountId);
		
	}
	
	protected Map<String, Object> buildAuthentication(Account account,Person person,String token){
		Map<String, Object> map = new HashMap<>();
		map.put("accountId", account.getAccountId());
		map.put("accountCode", account.getAccountCode());
		map.put("personId", String.valueOf(person.getPersonId()));
		map.put("fullName", person.getFullName());
		if(!Util.isNvl(token)) {
			map.put("token", token);
		}
		
		List<SubSystem> subSystems = subSystemService.findByPersonId(person.getPersonId());
		map.put("subSystems", subSystems);
		return map;
	}
}
