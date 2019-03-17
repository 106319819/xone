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
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.entity.Role;
import com.gosun.xone.core.entity.RolePerson;
import com.gosun.xone.core.service.RolePersonService;

@RestController
@RequestMapping("/admin/role-person")
public class RolePersonController {

	@Autowired
	private RolePersonService rolePersonService;
	@RequestMapping("/create/{personId}")
	public Result<?> create(@PathVariable("personId") Long personId,@RequestBody List<RolePerson> list){
		rolePersonService.create(personId,list);
		return Result.success(list);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<RolePerson> result = rolePersonService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("/find-by-id/{rolePersonId}")
	public Result<?> findById(@PathVariable("rolePersonId") Long rolePersonId){
		RolePerson result = rolePersonService.findById(rolePersonId);
		return Result.success(result);
	}
	@RequestMapping("/find-all-by-role-id/{roleId}")
	public Result<?> findListByRoleId(@PathVariable("roleId") Long roleId){
		List<RolePerson> result  = rolePersonService.findAllByRoleId(roleId);
		return Result.success(result);
	}
	
	@RequestMapping("/find-persons-by-role-id/{roleId}")
	public Result<?> findPersonsByRoleId(@PathVariable("roleId") Long roleId){
		List<Person> result = rolePersonService.findPersonsByRoleId(roleId);
		return Result.success(result);
	}
	
	@RequestMapping("/find-roles-by-person-id/{personId}")
	public Result<?> findRolesByPersonId(@PathVariable("personId") Long personId){
		List<Role> result = rolePersonService.findRolesByPersonId(personId);
		return Result.success(result);
	}
	
	
	@RequestMapping("/delete-by-id/{rolePersonId}")
	public Result<?> delete(@PathVariable("rolePersonId") Long rolePersonId){
		rolePersonService.deleteById(rolePersonId);
		return Result.success(rolePersonId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> rolePersonIds){
		rolePersonService.deleteByIdList(rolePersonIds);
		return Result.success(rolePersonIds);
	}
	
	@PostMapping("/update")
	public Result<?> update(@RequestBody RolePerson rolePerson) throws XoneException{
		rolePersonService.update(rolePerson);
		return Result.success(rolePerson);
	}
	
	
}
