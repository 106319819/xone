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
import com.gosun.xone.core.entity.SubSystemRelations;
import com.gosun.xone.core.entity.SubSystem;
import com.gosun.xone.core.repository.SubSystemRelationsRespository;
import com.gosun.xone.core.repository.SubSystemRespository;
import com.gosun.xone.core.service.SubSystemService;
@Service
public class SubSystemServiceImpl implements SubSystemService
{

	@Autowired
	private SubSystemRespository subSystemRespository;
	@Autowired
	private SubSystemRelationsRespository subSystemRelationsRespository;
	public void create(SubSystem subSystem)
	{
		this.subSystemRespository.save(subSystem);
		//创建子系统关系信息
		SubSystemRelations relations = new SubSystemRelations();
		relations.setSubSystemId(subSystem.getSubSystemId());
		relations.setOrganizationId(subSystem.getOrganizationId());
		relations.setActive(1);
		this.subSystemRelationsRespository.save(relations);
	}

	public List<SubSystem> findByOrganizationId(Long organizationId)
	{
		return subSystemRespository.findSubSystemsByOrganizationId(organizationId);
	}

	@Override
	public List<SubSystem> findByPersonId(Long personId) {
		// TODO Auto-generated method stub
		return subSystemRespository.findByPersonId(personId);
	}

	public SubSystem findById(Long subSystemId)
	{
		// TODO Auto-generated method stub
		Optional<SubSystem> result = subSystemRespository.findById(subSystemId);
		return result.get();
	}

	public List<Organization> findOrganizationsById(Long subSystemId)
	{
		// TODO Auto-generated method stub
		return subSystemRespository.findOrganizationsBySubSystemId(subSystemId);
	}

	public Page<SubSystem> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return subSystemRespository.findAll(pageable);
	}

	public void deleteById(Long subSystemId) {
		// TODO Auto-generated method stub
		subSystemRespository.deleteById(subSystemId);
	}

	@Override
	public void update(SubSystem subSystem) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.subSystemRespository.existsById(subSystem.getSubSystemId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.subSystemRespository.save(subSystem);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> subSystemIds) {
		// TODO Auto-generated method stub
		this.subSystemRespository.deleteByIdList(subSystemIds);
	}
	
	

}
