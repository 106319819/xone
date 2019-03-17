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
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.service.RoleService;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Role role){
		roleService.create(role);
		
		return Result.success(role);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<Role> result = roleService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("/find-by-id/{roleId}")
	public Result<?> findById(@PathVariable("roleId") Long roleId){
		Role result = roleService.findById(roleId);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{roleId}")
	public Result<?> delete(@PathVariable("roleId") Long roleId){
		roleService.deleteById(roleId);
		return Result.success(roleId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> roleIds){
		roleService.deleteByIdList(roleIds);
		return Result.success(roleIds);
	}
	
	@PostMapping("/update")
	public Result<?> update(@RequestBody Role role) throws XoneException{
		roleService.update(role);
		return Result.success(role);
	}
	
	
}
