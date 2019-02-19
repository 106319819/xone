package com.gosun.xone.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.xone.core.entity.PersonRelations;
import com.gosun.xone.core.repository.PersonRelationsRespository;
import com.gosun.xone.core.service.PersonRelationsService;
@Service
public class PersonRelationsServiceImpl implements PersonRelationsService
{

	@Autowired
	private PersonRelationsRespository personRelationsRespository;
	public void create(PersonRelations relations)
	{
		personRelationsRespository.save(relations);
	}

	public PersonRelations findById(Long personId, Long organizationId)
	{
		// TODO Auto-generated method stub
		return personRelationsRespository.findByPersonIdAndOrganizationId(personId,organizationId);
	}

	public PersonRelations findById(Long relationsId)
	{
		// TODO Auto-generated method stub
		return personRelationsRespository.findById(relationsId).get();
	}

	public List<PersonRelations> findByPersonId(Long personId)
	{
		// TODO Auto-generated method stub
		return personRelationsRespository.findByPersonId(personId);
	}

	public Page<PersonRelations> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return personRelationsRespository.findAll(pageable);
	}

	public void delete(Long relationsId) {
		// TODO Auto-generated method stub
		personRelationsRespository.deleteById(relationsId);
	}

	public void updateActive(Long personId, Long organizationId, Integer active) {
		// TODO Auto-generated method stub
		personRelationsRespository.updateActive(personId, organizationId, active);
	}
	
	

}
