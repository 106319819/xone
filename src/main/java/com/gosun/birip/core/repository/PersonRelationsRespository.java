package com.gosun.birip.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.entity.Person;
import com.gosun.birip.core.entity.PersonRelations;

public interface PersonRelationsRespository extends JpaRepository<PersonRelations, Long>
{

	@Query("select t1 from Person t1,PersonRelations t2"
			+ " where t1.personId = t2.personId and t2.organizationId = :organizationId")
	public List<Person> findPersonsByOrganizationId(Long organizationId);
	
	@Query("select t1.organizations from Person t1,PersonRelations t2"
			+ " where t1.personId = t2.personId and t2.personId = :personId")
	public List<Organization> findOrganizationsByPersonId(Long personId);
	
	public PersonRelations findByPersonIdAndOrganizationId(Long personId,Long organizationId);
	
	public List<PersonRelations> findByPersonId(Long personId);
	
	@Query("update PersonRelations t1 set t1.active = :active"
			+ " where t1.personId = :personId and t1.organizationId = :organizationId")
	public void updateActive(Long personId,Long organizationId,Integer active);
}
