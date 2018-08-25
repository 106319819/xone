package com.gosun.birip.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gosun.birip.core.entity.Account;
import com.gosun.birip.core.repository.AccountRespository;
import com.gosun.birip.core.service.AccountService;
@Service
public class AccountServiceImpl implements AccountService
{

	@Autowired
	private AccountRespository accountRespository;
	public void create(Account account)
	{
		accountRespository.save(account);
	}

	public Account findById(Long accountId)
	{
		// TODO Auto-generated method stub
		return accountRespository.findByAccountId(accountId);
	}

	public Account findByPersonId(Long personId)
	{
		return accountRespository.findByPerson(personId);
	}

	public void update(Account account)
	{
		accountRespository.save(account);

	}

}
