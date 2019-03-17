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
import com.gosun.xone.core.entity.SubSystem;
import com.gosun.xone.core.service.SubSystemService;

@RestController
@RequestMapping("/admin/sub-system")
public class SubSystemController {

	@Autowired
	private SubSystemService subSystemService;

	@RequestMapping("/create")
	public Result<?> create(@RequestBody SubSystem subSystem){
		subSystemService.create(subSystem);
		
		return Result.success(subSystem);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<SubSystem> result = subSystemService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("/find-by-id/{subSystemId}")
	public Result<?> findById(@PathVariable("subSystemId") Long subSystemId){
		SubSystem result = subSystemService.findById(subSystemId);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{subSystemId}")
	public Result<?> delete(@PathVariable("subSystemId") Long subSystemId){
		subSystemService.deleteById(subSystemId);
		return Result.success(subSystemId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> subSystemIds){
		subSystemService.deleteByIdList(subSystemIds);
		return Result.success(subSystemIds);
	}
	@RequestMapping("/find-by-organization/{organizationId}")
	public Result<?> findByOrganization(@PathVariable("organizationId") Long organizationId){
		List<SubSystem> result = subSystemService.findByOrganizationId(organizationId);
		return Result.success(result);
	}
	@PostMapping("/update")
	public Result<?> update(@RequestBody SubSystem subSystem) throws XoneException{
		subSystemService.update(subSystem);
		return Result.success(subSystem);
	}
}
