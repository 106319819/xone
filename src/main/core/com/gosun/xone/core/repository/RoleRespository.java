package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Role;

public interface RoleRespository extends JpaRepository<Role, Long>
{
	
	@Modifying
	@Query("delete from Role t1 where t1.roleId in :roleIds")
	public void deleteByIdList(List<Long> roleIds);
}
