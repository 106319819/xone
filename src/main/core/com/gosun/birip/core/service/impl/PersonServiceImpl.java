package com.gosun.birip.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.entity.Person;
import com.gosun.birip.core.repository.PersonRespository;
import com.gosun.birip.core.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService
{

	@Autowired
	private PersonRespository personRespository;
	public void create(Person person)
	{
		// TODO Auto-generated method stub
		personRespository.save(person);
	}

	public List<Person> findByOrganizationId(Long organizationId)
	{
		return personRespository.findPersonsByOrganizationId(organizationId);
	}

	public Person findById(Long personId)
	{
		// TODO Auto-generated method stub
		Optional<Person> result = personRespository.findById(personId);
		return result.get();
	}

	public List<Organization> findOrganizationsById(Long personId)
	{
		// TODO Auto-generated method stub
		return personRespository.findOrganizationsByPersonId(personId);
	}

	public Page<Person> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return personRespository.findAll(pageable);
	}

	public void delete(Long personId) {
		// TODO Auto-generated method stub
		personRespository.deleteById(personId);
	}

}
