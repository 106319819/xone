package com.gosun.xone.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.xone.core.entity.SystemLog;

public interface SystemLogService
{
	public void create(SystemLog systemLog);
	public Page<SystemLog> findAll(Pageable pageable);
}
