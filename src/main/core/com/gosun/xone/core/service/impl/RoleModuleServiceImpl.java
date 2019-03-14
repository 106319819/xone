package com.gosun.xone.core.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.common.XoneException;
import com.gosun.common.Error;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RoleModule;
import com.gosun.xone.core.repository.RoleModuleRespository;
import com.gosun.xone.core.service.RoleModuleService;

@Service
public class RoleModuleServiceImpl implements RoleModuleService
{

	@Autowired
	private RoleModuleRespository roleModuleRespository;
	@Transactional
	public void create(Long roleId,List<RoleModule> list)
	{
		this.roleModuleRespository.deleteAllByRoleId(roleId);
		Iterator<RoleModule> it = list.iterator();
		while(it.hasNext()) {
			RoleModule tmp = it.next();
			this.roleModuleRespository.save(tmp);
		}
	}

	public RoleModule findById(Long roleModuleId)
	{
		// TODO Auto-generated method stub
		Optional<RoleModule> result = roleModuleRespository.findById(roleModuleId);
		return result.get();
	}

	public Page<RoleModule> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return roleModuleRespository.findAll(pageable);
	}

	public void deleteById(Long roleModuleId) {
		// TODO Auto-generated method stub
		roleModuleRespository.deleteById(roleModuleId);
	}

	@Override
	public void update(RoleModule roleModule) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.roleModuleRespository.existsById(roleModule.getRoleModuleId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.roleModuleRespository.save(roleModule);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> roleModuleIds) {
		// TODO Auto-generated method stub
		this.roleModuleRespository.deleteByIdList(roleModuleIds);
	}

	@Override
	public List<Module> findModulesByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return this.roleModuleRespository.findModulesByRoleId(roleId);
	}

	@Override
	public List<Role> findRolesByModuleId(Long moduleId) {
		// TODO Auto-generated method stub
		return this.roleModuleRespository.findRolesByModuleId(moduleId);
	}

	@Override
	public List<RoleModule> findAllByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return roleModuleRespository.findAllByRoleId(roleId);
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		this.roleModuleRespository.deleteAllByRoleId(roleId);
	}

	
}
