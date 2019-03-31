package com.gosun.xone.core.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Account;
import com.gosun.xone.core.service.AccountService;

@RestController
@RequestMapping("/admin/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Account account){
		accountService.create(account);
		return Result.success(account);
	}
	@RequestMapping("/update-account-code/{accountId}")
	public Result<?> create(@PathVariable("accountId") Long accountId,@RequestParam String accountCode){
		accountService.updateAccountCode(accountId,accountCode);
		return Result.success(accountId);
	}
	@RequestMapping("/update-account-password/{accountId}")
	public Result<?> create(@PathVariable("accountId") Long accountId,@RequestParam String srcpwd,@RequestParam String password) throws XoneException{
		accountService.updatePassword(accountId, srcpwd, password);
		return Result.success(accountId);
	}
	@RequestMapping("/reset-account-password")
	public Result<?> resetPassword(@RequestBody Account account) throws XoneException{
		Account result = accountService.resetPassword(account);
		return Result.success(result);
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
}
