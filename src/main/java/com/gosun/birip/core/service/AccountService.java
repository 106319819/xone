package com.gosun.birip.core.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.birip.core.entity.Account;

public interface AccountService
{
	public void create(Account account);
	public Account findById(Long accountId);
	public Account findByPersonId(Long personId);
	public Account findByAccountCode(String accountCode);
	
	public void update(Account account);
	public Page<Account> findAll(Pageable pageable);
	
	public void delete(Long accountId);
	
	
}
