package com.gosun.xone.core.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.SubSystemRelations;
import com.gosun.xone.core.service.SubSystemRelationsService;

@RestController
@RequestMapping("/sub-system-relations")
public class SubSystemRelationsController {

	@Autowired
	private SubSystemRelationsService subSystemRelationsService;
	
	@RequestMapping("/create")
	public Result<?> create(@RequestBody SubSystemRelations subSystemRelations){
		subSystemRelationsService.create(subSystemRelations);
		return Result.success(subSystemRelations);
	}
	
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<SubSystemRelations> result = subSystemRelationsService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("find-by-id/{relationsId}")
	public Result<?> findById(@PathVariable("relationsId") Long relationsId){
		SubSystemRelations result = subSystemRelationsService.findById(relationsId);
		return Result.success(result);
	}
	
	@RequestMapping("delete-by-id/{relationsId}")
	public Result<?> delete(@PathVariable("relationsId") Long relationsId){
		subSystemRelationsService.delete(relationsId);
		return Result.success(relationsId);
	}
}
