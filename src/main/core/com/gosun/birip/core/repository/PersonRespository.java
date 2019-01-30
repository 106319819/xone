package com.gosun.birip.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.entity.Person;

public interface PersonRespository extends JpaRepository<Person, Long>
{

	@Query("select t1 from Person t1,PersonRelations t2"
			+ " where t1.personId = t2.personId and t2.organizationId = :organizationId")
	public List<Person> findPersonsByOrganizationId(Long organizationId);
	
	@Query("select t1.organizations from Person t1,PersonRelations t2"
			+ " where t1.personId = t2.personId and t2.personId = :personId")
	public List<Organization> findOrganizationsByPersonId(Long personId);
}
