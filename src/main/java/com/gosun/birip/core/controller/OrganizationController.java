package com.gosun.birip.core.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.birip.common.Result;
import com.gosun.birip.common.Util;
import com.gosun.birip.core.entity.Organization;
import com.gosun.birip.core.service.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController
{

	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Organization organization){
		organizationService.create(organization);
		return Result.success(organization);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException{
		Pageable pageable = Util.parse(content);
		Page<Organization> result = organizationService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("find-by-parent/{parentId}")
	public Result<?> findByParentId(@RequestParam("parentId") Long parentId){
		List<Organization> result = organizationService.findByParentId(parentId);
		return Result.success(result);
	}
	
	@RequestMapping("delete-by-id/{organizationId}")
	public Result<?> delete(@RequestParam("organizationId") Long organizationId){
		organizationService.delete(organizationId);
		return Result.success(organizationId);
		
	}
}
