package com.gosun.birip.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Account> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return accountRespository.findAll(pageable);
	}

	public void delete(Long accountId) {
		// TODO Auto-generated method stub
		accountRespository.deleteById(accountId);
	}

}
