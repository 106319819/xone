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
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RoleModule;
import com.gosun.xone.core.service.RoleModuleService;

@RestController
@RequestMapping("/role-module")
public class RoleModuleController {

	@Autowired
	private RoleModuleService roleModuleService;
	@RequestMapping("/create/{roleId}")
	public Result<?> create(@PathVariable("roleId") Long roleId,@RequestBody List<RoleModule> list){
		roleModuleService.create(roleId,list);
		return Result.success(list);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<RoleModule> result = roleModuleService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("/find-by-id/{roleModuleId}")
	public Result<?> findById(@PathVariable("roleModuleId") Long roleModuleId){
		RoleModule result = roleModuleService.findById(roleModuleId);
		return Result.success(result);
	}
	@RequestMapping("/find-all-by-role-id/{roleId}")
	public Result<?> findListByRoleId(@PathVariable("roleId") Long roleId){
		List<RoleModule> result  = roleModuleService.findAllByRoleId(roleId);
		return Result.success(result);
	}
	
	@RequestMapping("/find-modules-by-role-id/{roleId}")
	public Result<?> findModulesByRoleId(@PathVariable("roleId") Long roleId){
		List<Module> result = roleModuleService.findModulesByRoleId(roleId);
		return Result.success(result);
	}
	
	@RequestMapping("/find-roles-by-module-id/{moduleId}")
	public Result<?> findRolesByModuleId(@PathVariable("moduleId") Long moduleId){
		List<Role> result = roleModuleService.findRolesByModuleId(moduleId);
		return Result.success(result);
	}
	
	
	@RequestMapping("/delete-by-id/{roleModuleId}")
	public Result<?> delete(@PathVariable("roleModuleId") Long roleModuleId){
		roleModuleService.deleteById(roleModuleId);
		return Result.success(roleModuleId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> roleModuleIds){
		roleModuleService.deleteByIdList(roleModuleIds);
		return Result.success(roleModuleIds);
	}
	
	@PostMapping("/update")
	public Result<?> update(@RequestBody RoleModule roleModule) throws XoneException{
		roleModuleService.update(roleModule);
		return Result.success(roleModule);
	}
	
	
}
