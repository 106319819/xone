package com.gosun.xone.core.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.gosun.xone.core.service.AccountService;
import com.gosun.xone.core.service.PersonService;
import com.gosun.xone.security.AuthenticationToken;
import com.gosun.xone.security.SecurityUtils;

@RestController
@RequestMapping("/admin/login")
public class LoginController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PersonService personService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@RequestMapping("/")
	public Result<?> login(@RequestBody Account loginAccount,HttpServletRequest request){
		if("webmaster".equals(loginAccount.getAccountCode())){
			//如果是管理员，暂时先不认证
			return Result.success(loginAccount);
		}
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
		return Result.success(this.buildAuthentication(account.getAccountCode(),person.getPersonId(), token.getToken()) );
		
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
	
	protected Map<String, String> buildAuthentication(String accountCode,Long personId,String token){
		Map<String, String> map = new HashMap<>();
		map.put("accountCode", accountCode);
		map.put("personId", String.valueOf(personId));
		map.put("token", token);
		return map;
	}
}
