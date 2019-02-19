package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Application;
import com.gosun.xone.core.entity.SubSystem;

public interface ApplicationRespository extends JpaRepository<Application, Long>
{

	@Query("select t1 from Application t1 where t1.subSystem.subSystemId = :subSystemId")
	public List<Application> findApplicationsBySubSystemId(Long subSystemId);
	
	@Query("select t1.subSystem from Application t1"
			+ " where t1.applicationId = :applicationId")
	public SubSystem findSubSystemsByApplicationId(Long applicationId);
	
	@Modifying
	@Query("delete from Application t1 where t1.applicationId in :applicationIds")
	public void deleteByIdList(List<Long> applicationIds);
}
