package com.gosun.birip.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.repository.OrganizationRespository;
import com.gosun.birip.core.service.OrganizationService;
@Service
public class OrganizationServiceImpl implements OrganizationService
{
	@Autowired
	private OrganizationRespository organizationRespository;

	public void create(Organization organization)
	{
		organizationRespository.save(organization);
		//organization.setSortNo(organization.getOrganizationId());
	}
	
	public Organization findById(Long organizationId)
	{
		return organizationRespository.findByOrganizationId(organizationId);
	}

	public void update(Organization organization)
	{
		organizationRespository.save(organization);
	}

	public Page<Organization> findAll(Pageable pageable)
	{
		return organizationRespository.findAll(pageable);
	}

	public List<Organization> findByParentId(Long parentId)
	{
		// TODO Auto-generated method stub
		return organizationRespository.findByParentId(parentId);
	}

	public List<Organization> findByPersonId(Long personId)
	{
		return organizationRespository.findByPersonId(personId);
	}

	@Transactional
	public void delete(Long organizationId)
	{
		organizationRespository.deleteByParentId(organizationId);
		organizationRespository.deleteById(organizationId);
	}
	


}
