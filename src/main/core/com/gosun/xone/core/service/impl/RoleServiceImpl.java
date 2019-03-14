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
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.repository.RoleRespository;
import com.gosun.xone.core.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService
{

	@Autowired
	private RoleRespository roleRespository;
	public void create(Role role)
	{
		//创建人员基本信息
		this.roleRespository.save(role);
		
	}

	public Role findById(Long roleId)
	{
		// TODO Auto-generated method stub
		Optional<Role> result = roleRespository.findById(roleId);
		return result.get();
	}

	public Page<Role> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return roleRespository.findAll(pageable);
	}

	public void deleteById(Long roleId) {
		// TODO Auto-generated method stub
		roleRespository.deleteById(roleId);
	}

	@Override
	public void update(Role role) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.roleRespository.existsById(role.getRoleId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.roleRespository.save(role);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> roleIds) {
		// TODO Auto-generated method stub
		this.roleRespository.deleteByIdList(roleIds);
	}

}
