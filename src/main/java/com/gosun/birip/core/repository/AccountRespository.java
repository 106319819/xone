package com.gosun.birip.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.birip.core.entity.Account;

public interface AccountRespository extends JpaRepository<Account, Long>
{
	public Account findByAccountId(Long accountId);
	public Account findByPerson(Long personId);
}
