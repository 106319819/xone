package com.gosun.xone.core.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Host;

public interface HostRespository extends JpaRepository<Host, Long>
{
	@Modifying
	@Query("delete from Host t1 where t1.hostId in :ids")
	public void deleteByIds(List<Long> ids);
}
