package com.gosun.birip.core.repository;

import java.util.List;

import org.jboss.logging.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.birip.core.entity.Organization;

public interface OrganizationRespository extends JpaRepository<Organization, Long>
{
	public Organization findByOrganizationId(Long organizationId);
	public List<Organization>  findByParentId(Long parentId);
	@Query("select o from Organization o, PersonRelations r where o.organizationId = r.organizationId "
			+ " and r.personId=?1 ")
	public List<Organization> findByPersonId(Long personId);
	public Page<Organization> findAll(Pageable pageable);
	@Modifying
	@Query("delete from Organization o where o.organizationId = ?1")
	public void delete(Long organizationId);
	@Modifying
	@Query("delete from Organization o where o.parentId = ?1")
	public void deleteByParentId(Long parentId);
}
