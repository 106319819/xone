package com.gosun.xone.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.common.XoneException;
import com.gosun.common.Error;
import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.entity.PersonRelations;
import com.gosun.xone.core.repository.AccountRespository;
import com.gosun.xone.core.repository.PersonRelationsRespository;
import com.gosun.xone.core.repository.PersonRespository;
import com.gosun.xone.core.service.AccountService;
import com.gosun.xone.core.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService
{

	@Autowired
	private PersonRespository personRespository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PersonRelationsRespository personRelationsRespository;
	public void create(Person person)
	{
		//创建账号信息
		this.accountService.create(person.getAccount());
		//创建人员基本信息
		this.personRespository.save(person);
		//创建人员关系信息
		PersonRelations relations = new PersonRelations();
		relations.setPersonId(person.getPersonId());
		relations.setOrganizationId(person.getOrganizationId());
		relations.setActive(1);
		this.personRelationsRespository.save(relations);
		
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

	public void deleteById(Long personId) {
		// TODO Auto-generated method stub
		personRespository.deleteById(personId);
	}

	@Override
	public void update(Person person) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.personRespository.existsById(person.getPersonId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.personRespository.save(person);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> personIds) {
		// TODO Auto-generated method stub
		this.personRespository.deleteByIdList(personIds);
	}

	@Override
	public Person findByAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return personRespository.findByAccountId(accountId);
	}
	
	

}
