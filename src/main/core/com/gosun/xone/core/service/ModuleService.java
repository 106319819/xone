package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.common.XoneException;
import com.gosun.xone.core.entity.Module;

public interface ModuleService
{
	public void create(Module module);
	public List<Module> findBySubSystemId(Long subSystemId);
	public Module findById(Long moduleId);
	public Page<Module> findAll(Pageable pageable);
	public void deleteById(Long moduleId);
	
	public void deleteByIdList(List<Long> moduleIds);
	public void update(Module module) throws XoneException;
	
	public List<Module> fetchTreeBySubSystemId(Long subSystemId); 
	
}
