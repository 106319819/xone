package com.gosun.birip.core.service;

import com.gosun.birip.core.entity.Account;

public interface AccountService
{
	public void create(Account account);
	public Account findById(Long accountId);
	public Account findByPersonId(Long personId);
	public void update(Account account);
}
