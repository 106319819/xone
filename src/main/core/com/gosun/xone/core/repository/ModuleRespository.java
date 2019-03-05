package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.SubSystem;

public interface ModuleRespository extends JpaRepository<Module, Long>
{

	@Query("select t1 from Module t1 where t1.subSystem.subSystemId = :subSystemId"
			+ " order by t1.sortNo,t1.moduleId")
	public List<Module> findModulesBySubSystemId(Long subSystemId);
	
	@Query("select t1.subSystem from Module t1"
			+ " where t1.moduleId = :moduleId")
	public SubSystem findSubSystemByModuleId(Long moduleId);
	
	@Modifying
	@Query("delete from Module t1 where t1.moduleId in :moduleIds")
	public void deleteByIdList(List<Long> moduleIds);
}
