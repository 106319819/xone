package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.common.XoneException;
import com.gosun.xone.core.entity.Host;

public interface HostService
{
	public void create(Host host);
	public Host findById(Long hostId);
	public Page<Host> findAll(Pageable pageable);
	public void deleteById(Long hostId);
	
	public void deleteByIdList(List<Long> hostIds);
	public void update(Host host) throws XoneException;
	
	
}
