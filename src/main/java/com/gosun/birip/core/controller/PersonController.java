package com.gosun.birip.core.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.birip.common.BiripException;
import com.gosun.birip.common.Result;
import com.gosun.birip.common.Util;
import com.gosun.birip.core.entity.Person;
import com.gosun.birip.core.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Person person){
		personService.create(person);
		return Result.success(person);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, BiripException{
		Pageable pageable = Util.parse(content);
		Page<Person> result = personService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("find-by-id/{personId}")
	public Result<?> findById(@PathVariable("personId") Long personId){
		Person result = personService.findById(personId);
		return Result.success(result);
	}
	
	@RequestMapping("delete-by-id/{personId}")
	public Result<?> delete(@PathVariable("personId") Long personId){
		personService.delete(personId);
		return Result.success(personId);
	}
}
