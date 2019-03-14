package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RoleModule;

public interface RoleModuleRespository extends JpaRepository<RoleModule, Long>
{
	
	@Modifying
	@Query("delete from RoleModule t1 where t1.roleModuleId in :roleModuleIds")
	public void deleteByIdList(List<Long> roleModuleIds);
	@Query("select t2 from RoleModule t1,Module t2 where t1.moduleId = t2.moduleId"
			+ " and t1.roleId= :roleId")
	public List<Module> findModulesByRoleId(Long roleId);
	@Query("select distinct t2 from RoleModule t1,Role t2 where t1.roleId = t2.roleId"
			+ " and t1.moduleId = :moduleIdId")
	public List<Role> findRolesByModuleId(Long moduleId);
	
	public List<RoleModule> findAllByRoleId(Long roleId);
	@Modifying
	@Query("delete from  RoleModule t1 where t1.roleId in :roleId")
	public void deleteAllByRoleId(Long roleId);
}
