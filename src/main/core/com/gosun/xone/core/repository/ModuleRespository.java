package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.SubSystem;

public interface ModuleRespository extends JpaRepository<Module, Long>
{

	@Query("select t1 from Module t1 where t1.subSystemId = :subSystemId"
			+ " order by t1.sortNo,t1.moduleId")
	public List<Module> findModulesBySubSystemId(Long subSystemId);
	
	@Query("select t2 from Module t1,SubSystem t2"
			+ " where t1.subSystemId = t2.subSystemId and t1.moduleId = :moduleId")
	public SubSystem findSubSystemByModuleId(Long moduleId);
	
	@Modifying
	@Query("delete from Module t1 where t1.moduleId in :moduleIds")
	public void deleteByIdList(List<Long> moduleIds);
	
	@Query("select t1 from Module t1,RolePerson t2,RoleModule t3"
			+ " where t1.moduleId = t3.moduleId and t2.roleId=t3.roleId "
			+ " and t2.personId = :personId")
	public List<Module> fetchTreeByPersonId(Long personId);
	@Query("select t1 from Module t1,RolePerson t2,RoleModule t3"
			+ " where t1.moduleId = t3.moduleId and t2.roleId=t3.roleId "
			+ " and t2.personId = :personId and t1.subSystemId =:subSystemId")
	public List<Module> fetchTree(Long personId,Long subSystemId);
}
