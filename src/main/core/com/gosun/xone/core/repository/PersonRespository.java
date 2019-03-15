package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.entity.Person;

public interface PersonRespository extends JpaRepository<Person, Long>
{

	@Query("select t1 from Person t1,PersonRelations t2"
			+ " where t1.personId = t2.personId and t2.organizationId = :organizationId")
	public List<Person> findPersonsByOrganizationId(Long organizationId);
	
	@Query("select t1 from Organization t1,PersonRelations t2"
			+ " where t1.organizationId = t2.organizationId and t2.personId = :personId")
	public List<Organization> findOrganizationsByPersonId(Long personId);
	
	@Modifying
	@Query("delete from Person t1 where t1.personId in :personIds")
	public void deleteByIdList(List<Long> personIds);
	
	@Query("select t1 from Person t1 where t1.accountId = :accountId")
	public Person findByAccountId(Long accountId);
}
