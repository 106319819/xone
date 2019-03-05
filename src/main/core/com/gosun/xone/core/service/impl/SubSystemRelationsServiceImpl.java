package com.gosun.xone.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.xone.core.entity.SubSystemRelations;
import com.gosun.xone.core.repository.SubSystemRelationsRespository;
import com.gosun.xone.core.service.SubSystemRelationsService;
@Service
public class SubSystemRelationsServiceImpl implements SubSystemRelationsService
{

	@Autowired
	private SubSystemRelationsRespository subSystemRelationsRespository;
	public void create(SubSystemRelations relations)
	{
		subSystemRelationsRespository.save(relations);
	}

	public SubSystemRelations findById(Long subSystemId, Long organizationId)
	{
		// TODO Auto-generated method stub
		return subSystemRelationsRespository.findBySubSystemIdAndOrganizationId(subSystemId,organizationId);
	}

	public SubSystemRelations findById(Long relationsId)
	{
		// TODO Auto-generated method stub
		return subSystemRelationsRespository.findById(relationsId).get();
	}

	public List<SubSystemRelations> findBySubSystemId(Long subSystemId)
	{
		// TODO Auto-generated method stub
		return subSystemRelationsRespository.findBySubSystemId(subSystemId);
	}

	public Page<SubSystemRelations> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return subSystemRelationsRespository.findAll(pageable);
	}

	public void delete(Long relationsId) {
		// TODO Auto-generated method stub
		subSystemRelationsRespository.deleteById(relationsId);
	}

	public void updateActive(Long subSystemId, Long organizationId, Integer active) {
		// TODO Auto-generated method stub
		subSystemRelationsRespository.updateActive(subSystemId, organizationId, active);
	}
	
	

}
