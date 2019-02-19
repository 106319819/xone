package com.gosun.xone.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Account;

public interface AccountRespository extends JpaRepository<Account, Long>
{
	public Account findByAccountId(Long accountId);
	@Query("select t1 from Account t1,Person t2 where t1.accountId = t2.account.accountId and t2.personId = :personId")
	public Account findByPersonId(Long personId);
	
	public Account findByAccountCode(String accountCode);
}
