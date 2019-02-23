package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.common.BiripException;
import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.entity.Person;

public interface PersonService
{
	public void create(Person person);
	public List<Person> findByOrganizationId(Long organizationId);
	public Person findById(Long personId);
	public List<Organization> findOrganizationsById(Long personId);
	public Page<Person> findAll(Pageable pageable);
	public void deleteById(Long personId);
	
	public void deleteByIdList(List<Long> personIds);
	public void update(Person person) throws BiripException;
	
}
