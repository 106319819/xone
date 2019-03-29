package com.gosun.xone.core.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.service.ModuleService;

@RestController
@RequestMapping("/admin/module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Module module){
		moduleService.create(module);
		
		return Result.success(module);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<Module> result = moduleService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("/find-by-id/{moduleId}")
	public Result<?> findById(@PathVariable("moduleId") Long moduleId){
		Module result = moduleService.findById(moduleId);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{moduleId}")
	public Result<?> delete(@PathVariable("moduleId") Long moduleId){
		moduleService.deleteById(moduleId);
		return Result.success(moduleId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> moduleIds){
		moduleService.deleteByIdList(moduleIds);
		return Result.success(moduleIds);
	}
	@RequestMapping("/find-by-sub-system/{subSystemId}")
	public Result<?> findBySubSystem(@PathVariable("subSystemId") Long subSystemId){
		List<Module> result = moduleService.findBySubSystemId(subSystemId);
		return Result.success(result);
	}
	@PostMapping("/update")
	public Result<?> update(@RequestBody Module module) throws XoneException{
		moduleService.update(module);
		return Result.success(module);
	}
	
	@RequestMapping("/fetch-tree-by-sub-system/{subSystemId}")
	public Result<?> fetchTree(@PathVariable("subSystemId") Long subSystemId) throws XoneException{
		List<Module> modules = moduleService.fetchTreeBySubSystemId(subSystemId);
		return Result.success(modules);
	}
	
	@RequestMapping("/fetch-tree-by-person-id/{personId}")
	public Result<?> fetchTreeByPersonId(@PathVariable("personId") Long personId) throws XoneException{
		List<Module> modules = moduleService.fetchTreeByPersonId(personId);
		return Result.success(modules);
	}
	
	@RequestMapping("/fetch-tree")
	public Result<?> fetchTree(@RequestParam Long personId,@RequestParam Long subSystemId) throws XoneException{
		List<Module> modules = moduleService.fetchTree(personId, subSystemId);
		return Result.success(modules);
	}
}
