package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RolePerson;

public interface RolePersonRespository extends JpaRepository<RolePerson, Long>
{
	
	@Modifying
	@Query("delete from RolePerson t1 where t1.rolePersonId in :rolePersonIds")
	public void deleteByIdList(List<Long> rolePersonIds);
	@Query("select t2 from RolePerson t1,Person t2 where t1.personId = t2.personId"
			+ " and t1.roleId= :roleId")
	public List<Person> findPersonsByRoleId(Long roleId);
	@Query("select distinct t2 from RolePerson t1,Role t2 where t1.roleId = t2.roleId"
			+ " and t1.personId = :personId")
	public List<Role> findRolesByPersonId(Long personId);
	
	public List<RolePerson> findAllByRoleId(Long roleId);
	@Modifying
	@Query("delete from  RolePerson t1 where t1.roleId = :roleId")
	public void deleteAllByRoleId(Long roleId);
	
	@Modifying
	@Query("delete from  RolePerson t1 where t1.personId = :personId")
	public void deleteAllByPersonId(Long personId);
	
	@Query("select t3 from RolePerson t1,RoleModule t2,Module t3"
			+ " where t1.roleId = t2.roleId"
			+ " and t2.moduleId = t3.moduleId"
			+ " and t1.personId = :personId")
	public List<Module> findModulesByPersonId(Long personId);
}
