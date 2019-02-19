package com.gosun.xone.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.xone.core.entity.SubSystemRelations;

public interface SubSystemRelationsService
{
	public void create(SubSystemRelations relations);
	public SubSystemRelations findById(Long subSystemId,Long organizationId);
	public SubSystemRelations findById(Long relationsId);
	public List<SubSystemRelations> findBySubSystemId(Long subSystemId);
	public Page<SubSystemRelations> findAll(Pageable pageable);
	public void delete(Long relationsId);
	
	public void updateActive(Long subSystemId,Long organizationId,Integer active);
}
