package com.gosun.birip.core.service;


import java.util.List;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.entity.Person;

public interface PersonService
{
	public void create(Person person);
	public List<Person> findByOrganizationId(Long organizationId);
	public Person findById(Long personId);
	public List<Organization> findOrganizationsById(Long personId);
}
