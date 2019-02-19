package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.entity.SubSystem;
import com.gosun.xone.core.entity.SubSystemRelations;

public interface SubSystemRelationsRespository extends JpaRepository<SubSystemRelations, Long>
{

	@Query("select t1 from SubSystem t1,SubSystemRelations t2"
			+ " where t1.subSystemId = t2.subSystemId and t2.organizationId = :organizationId")
	public List<SubSystem> findSubSystemsByOrganizationId(Long organizationId);
	
	@Query("select t1 from Organization t1,SubSystemRelations t2"
			+ " where t1.organizationId = t2.organizationId and t2.subSystemId = :subSystemId")
	public List<Organization> findOrganizationsBySubSystemId(Long subSystemId);
	
	public SubSystemRelations findBySubSystemIdAndOrganizationId(Long subSystemId,Long organizationId);
	
	public List<SubSystemRelations> findBySubSystemId(Long subSystemId);
	
	@Query("update SubSystemRelations t1 set t1.active = :active"
			+ " where t1.subSystemId = :subSystemId and t1.organizationId = :organizationId")
	public void updateActive(Long subSystemId,Long organizationId,Integer active);
}
