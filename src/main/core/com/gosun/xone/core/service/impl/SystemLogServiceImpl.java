package com.gosun.xone.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.xone.core.entity.SystemLog;
import com.gosun.xone.core.repository.SystemLogRespository;
import com.gosun.xone.core.service.SystemLogService;
@Service
public class SystemLogServiceImpl implements SystemLogService
{

	@Autowired
	private SystemLogRespository systemLogRespository;

	public void create(SystemLog systemLog)
	{
		this.systemLogRespository.save(systemLog);
	}

	public Page<SystemLog> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return systemLogRespository.findAll(pageable);
	}
	

}
