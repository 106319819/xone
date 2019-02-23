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
import com.gosun.xone.core.entity.Organization;
import com.gosun.xone.core.service.OrganizationService;


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
	@RequestMapping("/update")
	public Result<?> update(@RequestBody Organization organization){
		organizationService.update(organization);
		return Result.success(organization);
	}
	
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<Organization> result = organizationService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	
	@RequestMapping("/find-by-id/{organizationId}")
	public Result<?> findByOrganizationId(@PathVariable("organizationId") Long organizationId){
		Organization result = organizationService.findById(organizationId);
		return Result.success(result);
	}
	
	@RequestMapping("/find-by-parent/{parentId}")
	public Result<?> findByParentId(@PathVariable("parentId") Long parentId){
		List<Organization> result = organizationService.findByParentId(parentId);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{organizationId}")
	public Result<?> delete(@PathVariable("organizationId") Long organizationId){
		organizationService.delete(organizationId);
		return Result.success(organizationId);
		
	}
	
	@RequestMapping("/delete-by-parentId/{parentId}")
	public Result<?> deleteByParentId(@PathVariable("parentId") Long parentId){
		organizationService.deleteByParentId(parentId);
		return Result.success(parentId);
		
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> organizationIdList){
		organizationService.deleteByOrganizationId(organizationIdList);
		return Result.success(organizationIdList);
		
	}
}
