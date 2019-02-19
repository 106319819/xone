package com.gosun.xone.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.xone.core.entity.PersonRelations;

public interface PersonRelationsService
{
	public void create(PersonRelations relations);
	public PersonRelations findById(Long personId,Long organizationId);
	public PersonRelations findById(Long relationsId);
	public List<PersonRelations> findByPersonId(Long personId);
	public Page<PersonRelations> findAll(Pageable pageable);
	public void delete(Long relationsId);
	
	public void updateActive(Long personId,Long organizationId,Integer active);
}
