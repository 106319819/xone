package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.xone.common.BiripException;
import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.entity.SubSystem;

public interface SubSystemService
{
	public void create(SubSystem subSystem);
	public List<SubSystem> findByOrganizationId(Long organizationId);
	public SubSystem findById(Long subSystemId);
	public List<Organization> findOrganizationsById(Long subSystemId);
	public Page<SubSystem> findAll(Pageable pageable);
	public void deleteById(Long subSystemId);
	
	public void deleteByIdList(List<Long> subSystemIds);
	public void update(SubSystem subSystem) throws BiripException;
	
}
