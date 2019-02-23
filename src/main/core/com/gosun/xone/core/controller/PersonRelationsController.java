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
import com.gosun.xone.core.entity.PersonRelations;
import com.gosun.xone.core.service.PersonRelationsService;

@RestController
@RequestMapping("/person-relations")
public class PersonRelationsController {

	@Autowired
	private PersonRelationsService personRelationsService;
	
	@RequestMapping("/create")
	public Result<?> create(@RequestBody PersonRelations personRelations){
		personRelationsService.create(personRelations);
		return Result.success(personRelations);
	}
	
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<PersonRelations> result = personRelationsService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("find-by-id/{relationsId}")
	public Result<?> findById(@PathVariable("relationsId") Long relationsId){
		PersonRelations result = personRelationsService.findById(relationsId);
		return Result.success(result);
	}
	
	@RequestMapping("delete-by-id/{relationsId}")
	public Result<?> delete(@PathVariable("relationsId") Long relationsId){
		personRelationsService.delete(relationsId);
		return Result.success(relationsId);
	}
}
