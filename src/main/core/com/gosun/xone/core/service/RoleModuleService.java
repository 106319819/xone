package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.common.XoneException;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RoleModule;

public interface RoleModuleService
{
	public void create(Long roleId,List<RoleModule> list);
	public RoleModule findById(Long roleModuleId);
	public Page<RoleModule> findAll(Pageable pageable);
	public void deleteById(Long roleModuleId);
	
	public void deleteByRoleId(Long roleId);
	public void deleteByIdList(List<Long> roleModuleIds);
	public void update(RoleModule roleModule) throws XoneException;
	public List<RoleModule> findAllByRoleId(Long roleId);
	
	public List<Module> findModulesByRoleId(Long roleId);
	public List<Role> findRolesByModuleId(Long moduleId);
	
	
}
