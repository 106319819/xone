package com.gosun.birip.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.birip.core.entity.Organization;

public interface OrganizationService
{
	
	public void create(Organization organization);
	public Organization findById(Long organizationId);
	public void update(Organization organization);
	public void delete(Long organizationId);
	public void deleteByParentId(Long parentId);
	public void deleteByOrganizationId(List<Long> list);
	public Page<Organization> findAll(Pageable page);
	public List<Organization> findByParentId(Long parentId);
	public List<Organization> findByPersonId(Long personId);
	
	
	
}
