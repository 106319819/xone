package com.gosun.xone.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.common.XoneException;
import com.gosun.common.Error;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.repository.ModuleRespository;
import com.gosun.xone.core.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService
{

	@Autowired
	private ModuleRespository moduleRespository;
	public void create(Module module)
	{
		//创建人员基本信息
		this.moduleRespository.save(module);
		
	}

	public List<Module> findBySubSystemId(Long subSystemId)
	{
		return moduleRespository.findModulesBySubSystemId(subSystemId);
	}

	public Module findById(Long moduleId)
	{
		// TODO Auto-generated method stub
		Optional<Module> result = moduleRespository.findById(moduleId);
		return result.get();
	}

	public Page<Module> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return moduleRespository.findAll(pageable);
	}

	public void deleteById(Long moduleId) {
		// TODO Auto-generated method stub
		moduleRespository.deleteById(moduleId);
	}

	@Override
	public void update(Module module) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.moduleRespository.existsById(module.getModuleId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.moduleRespository.save(module);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> moduleIds) {
		// TODO Auto-generated method stub
		this.moduleRespository.deleteByIdList(moduleIds);
	}

	@Override
	public List<Module> fetchTreeBySubSystemId(Long subSystemId) {
		List<Module> modules = this.moduleRespository.findModulesBySubSystemId(subSystemId);
		return buildTree(modules);
	}
	
	protected List<Module> buildTree(List<Module> modules){
		List<Module> parents = new ArrayList<>();
		for(Module item: modules) {
			if(parents.contains(item)) {
				continue;
			}
			
			Long parentId = item.getParentId();
			if(Util.isNvl(parentId) || 0L == parentId) {
				item.setLevel(0);
				parents.add(item);
				//构建孩子
				buildChildren(item,modules);
			}
		}
		parents.sort((c1,c2)-> c1.getSortNo().compareTo(c2.getSortNo())) ;
		return parents;
	}
	
	protected void buildChildren(Module parent,List<Module> modules) {

		List<Module> children = new ArrayList<>();
		parent.setChildren(children);		
		for(Module item: modules) {
			if(children.contains(item)) {
				continue;
			}
			Long parentId = item.getParentId();
			if(!Util.isNvl(parentId) && parentId.longValue() == parent.getModuleId().longValue()) {
				children.add(item);
				item.setParentName(parent.getName());
				item.setLevel(parent.getLevel() + 1);
				//构建孩子
				buildChildren(item,modules);
			}
		}
		children.sort((c1,c2) -> c1.getSortNo().compareTo(c2.getSortNo()));
	}

}
