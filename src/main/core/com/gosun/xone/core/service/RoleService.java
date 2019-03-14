package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.common.XoneException;
import com.gosun.xone.core.entity.Role;

public interface RoleService
{
	public void create(Role role);
	public Role findById(Long roleId);
	public Page<Role> findAll(Pageable pageable);
	public void deleteById(Long roleId);
	
	public void deleteByIdList(List<Long> roleIds);
	public void update(Role role) throws XoneException;
	
	
}
