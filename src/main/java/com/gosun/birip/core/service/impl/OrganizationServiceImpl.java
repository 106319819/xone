package com.gosun.birip.core.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.repository.OrganizationRespository;
import com.gosun.birip.core.service.OrganizationService;


@Service
public class OrganizationServiceImpl  implements OrganizationService
{
	private static final String CODE_FORMAT = "%04d";
	private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	@Autowired
	private OrganizationRespository organizationRespository;

	@Transactional
	public void create(Organization organization)
	{
		if(null == organization.getParentId()) {
			organization.setParentId(0L);
		}
		organizationRespository.save(organization);
		String organizationCode = String.format(CODE_FORMAT, organization.getOrganizationId());		
		if(null != organization.getParentId() && organizationRespository.existsById(organization.getParentId())) {
			//更新当前节点的父节点的叶子属性
			organizationRespository.updateIsLeaf(organization.getParentId(), "0");
			Organization parent = organizationRespository.findByOrganizationId(organization.getParentId());
			organizationCode = parent.getOrganizationCode() + organizationCode;
		}
		//更新编码
		organizationRespository.updateOrganizationCode(organization.getOrganizationId(), organizationCode);
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

	@Transactional
	public void deleteByParentId(Long parentId) {
		// TODO Auto-generated method stub
		List<Organization> organizations = organizationRespository.findByParentId(parentId);
		Iterator<Organization> it = organizations.iterator();
		while(it.hasNext()) {
			Organization tmp = it.next();
			if("0".equals(tmp.getIsLeaf())) {
				//有孩子，需要先删除孩子
				this.deleteByParentId(tmp.getOrganizationId());
			}else {
				//删除节点
				organizationRespository.delete(tmp);
			}
		}
		
		//删除下级数据后，需要更新当前节点为叶子节点
		organizationRespository.updateIsLeaf(parentId, "1");
		
		
	}


}
