package com.gosun.xone.core.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.BiripException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Account;
import com.gosun.xone.core.service.AccountService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/")
	public Result<?> login(@RequestBody Account account){
//		Account account = accountService.findByAccountCode(accountCode);
//		if(Util.isNvl(account)) {
//			return Result.error(BiripException.instance(Error.NO_FOUND_USER));
//		}
//		String paswd = Util.MD5(password);
//		if(!paswd.equals(account.getPassword()))
//		{
//			return Result.error(BiripException.instance(Error.ERROR_PASSWORD));
//		}
//		
//		Account account = new Account();
//		account.setAccountCode(accountCode);
//		account.setPassword(password);
		account.setAccountId(1L);
		return Result.success(account);
		
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, BiripException{
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
}
