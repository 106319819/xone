package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.common.XoneException;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RolePerson;

public interface RolePersonService
{
	public void create(Long roleId,List<RolePerson> list);
	public RolePerson findById(Long rolePersonId);
	public Page<RolePerson> findAll(Pageable pageable);
	public void deleteById(Long rolePersonId);
	
	public void deleteByRoleId(Long roleId);
	public void deleteByIdList(List<Long> rolePersonIds);
	public void update(RolePerson rolePerson) throws XoneException;
	public List<RolePerson> findAllByRoleId(Long roleId);
	
	public List<Person> findPersonsByRoleId(Long roleId);
	public List<Role> findRolesByPersonId(Long personId);
	
	public List<Module> findModulesByPersonId(Long personId);
	
}
