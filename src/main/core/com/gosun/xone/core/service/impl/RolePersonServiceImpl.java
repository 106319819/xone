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
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RolePerson;
import com.gosun.xone.core.repository.RolePersonRespository;
import com.gosun.xone.core.service.RolePersonService;

@Service
public class RolePersonServiceImpl implements RolePersonService
{

	@Autowired
	private RolePersonRespository rolePersonRespository;
	@Transactional
	public void create(Long personId,List<RolePerson> list)
	{
		this.rolePersonRespository.deleteAllByPersonId(personId);
		Iterator<RolePerson> it = list.iterator();
		while(it.hasNext()) {
			RolePerson tmp = it.next();
			this.rolePersonRespository.save(tmp);
		}
	}

	public RolePerson findById(Long rolePersonId)
	{
		// TODO Auto-generated method stub
		Optional<RolePerson> result = rolePersonRespository.findById(rolePersonId);
		return result.get();
	}

	public Page<RolePerson> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return rolePersonRespository.findAll(pageable);
	}

	public void deleteById(Long rolePersonId) {
		// TODO Auto-generated method stub
		rolePersonRespository.deleteById(rolePersonId);
	}

	@Override
	public void update(RolePerson rolePerson) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.rolePersonRespository.existsById(rolePerson.getRolePersonId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.rolePersonRespository.save(rolePerson);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> rolePersonIds) {
		// TODO Auto-generated method stub
		this.rolePersonRespository.deleteByIdList(rolePersonIds);
	}

	@Override
	public List<Person> findPersonsByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return this.rolePersonRespository.findPersonsByRoleId(roleId);
	}

	@Override
	public List<Role> findRolesByPersonId(Long personId) {
		// TODO Auto-generated method stub
		return this.rolePersonRespository.findRolesByPersonId(personId);
	}

	@Override
	public List<RolePerson> findAllByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		return rolePersonRespository.findAllByRoleId(roleId);
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		this.rolePersonRespository.deleteAllByRoleId(roleId);
	}

	@Override
	public List<Module> findModulesByPersonId(Long personId) {
		// TODO Auto-generated method stub
		return rolePersonRespository.findModulesByPersonId(personId);
	}

	
}
