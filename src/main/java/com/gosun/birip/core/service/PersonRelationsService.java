package com.gosun.birip.core.service;

import java.util.List;

import com.gosun.birip.core.entity.PersonRelations;

public interface PersonRelationsService
{
	public void create(PersonRelations relations);
	public PersonRelations findById(Long personId,Long organizationId);
	public PersonRelations findById(Long relationsId);
	public List<PersonRelations> findByPersonId(Long personId);
}
