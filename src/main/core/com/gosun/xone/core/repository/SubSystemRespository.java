package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.entity.SubSystem;

public interface SubSystemRespository extends JpaRepository<SubSystem, Long>
{

	@Query("select t1 from SubSystem t1,SubSystemRelations t2"
			+ " where t1.subSystemId = t2.subSystemId and t2.organizationId = :organizationId")
	public List<SubSystem> findSubSystemsByOrganizationId(Long organizationId);
	
	@Query("select t4 from RolePerson t1,RoleModule t2, Module t3, SubSystem t4"
			+ " where t1.roleId = t2.roleId and t2.moduleId=t3.moduleId and t3.subSystemId = t4.subSystemId"
			+ " and t1.personId = :personId"
			+ " group by t4.subSystemId,t4.name")
	public List<SubSystem> findByPersonId(Long personId);
	
	@Query("select t1 from Organization t1,SubSystemRelations t2"
			+ " where t1.organizationId = t2.organizationId and t2.subSystemId = :subSystemId")
	public List<Organization> findOrganizationsBySubSystemId(Long subSystemId);
	
	@Modifying
	@Query("delete from SubSystem t1 where t1.subSystemId in :subSystemIds")
	public void deleteByIdList(List<Long> subSystemIds);
}
