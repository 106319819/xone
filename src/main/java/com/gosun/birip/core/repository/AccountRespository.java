package com.gosun.birip.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gosun.birip.core.entity.Account;

public interface AccountRespository extends JpaRepository<Account, Long>
{
	public Account findByAccountId(Long accountId);
	@Query("select t1 from Account t1 where t1.person.personId = :personId")
	public Account findByPersonId(Long personId);
	
	public Account findByAccountCode(String accountCode);
}
