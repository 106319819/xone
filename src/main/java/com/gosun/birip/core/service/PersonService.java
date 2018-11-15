package com.gosun.birip.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.entity.Person;

public interface PersonService
{
	public void create(Person person);
	public List<Person> findByOrganizationId(Long organizationId);
	public Person findById(Long personId);
	public List<Organization> findOrganizationsById(Long personId);
	
	public Page<Person> findAll(Pageable pageable);
	public void delete(Long personId);
}
