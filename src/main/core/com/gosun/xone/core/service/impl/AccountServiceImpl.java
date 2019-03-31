package com.gosun.xone.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.common.Error;
import com.gosun.common.Util;
import com.gosun.common.XoneException;
import com.gosun.xone.core.entity.Account;
import com.gosun.xone.core.repository.AccountRespository;
import com.gosun.xone.core.service.AccountService;
@Service
public class AccountServiceImpl implements AccountService
{

	@Autowired
	private AccountRespository accountRespository;
	public void create(Account account)
	{
		String password = Util.MD5(account.getPassword());
		account.setPassword(password);
		accountRespository.save(account);
	}

	public Account findById(Long accountId)
	{
		// TODO Auto-generated method stub
		return accountRespository.findByAccountId(accountId);
	}

	public Account findByPersonId(Long personId)
	{
		return accountRespository.findByPersonId(personId);
	}

	public Account findByAccountCode(String accountCode) {
		// TODO Auto-generated method stub
		return accountRespository.findByAccountCode(accountCode);
	}

	public void update(Account account)
	{

		accountRespository.save(account);

	}

	@Override
	public Account resetPassword(Account account) {
		// TODO Auto-generated method stub
		Account entity = accountRespository.findByAccountCode(account.getAccountCode());
		String paswd = Util.MD5(account.getPassword());
		entity.setPassword(paswd);
		accountRespository.save(entity);
		return entity;
	}

	@Override
	public Account updateAccountCode(Long accountId, String accountCode) {
		Account entity = accountRespository.findByAccountId(accountId);
		entity.setAccountCode(accountCode);
		return accountRespository.save(entity);
	}

	@Override
	public void updatePassword(Long accountId,String srcpwd, String password) throws XoneException {
		Account entity = accountRespository.findByAccountId(accountId);
		String old = Util.MD5(srcpwd);
		if(!old.equals(entity.getPassword())) {
			XoneException.throwing(Error.VALIDATE_ERROR);
		}
		
		entity.setPassword(Util.MD5(password));
		accountRespository.save(entity);
	}

	public Page<Account> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return accountRespository.findAll(pageable);
	}

	public void delete(Long accountId) {
		// TODO Auto-generated method stub
		accountRespository.deleteById(accountId);
	}

}
