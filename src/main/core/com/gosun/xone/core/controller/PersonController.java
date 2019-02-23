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

import com.gosun.common.BiripException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	/**
	 * 在组织下创建人员，新增人员的同时，会新增默认的人员关系
	 * 人员关系可以在人员关系管理中进行维护
	 * @param person	人员对象信息
	 * @return Result json
	 */
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
	
	@RequestMapping("/find-by-id/{personId}")
	public Result<?> findById(@PathVariable("personId") Long personId){
		Person result = personService.findById(personId);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{personId}")
	public Result<?> delete(@PathVariable("personId") Long personId){
		personService.deleteById(personId);
		return Result.success(personId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> personIds){
		personService.deleteByIdList(personIds);
		return Result.success(personIds);
	}
	@RequestMapping("/find-by-organization/{organizationId}")
	public Result<?> findByOrganization(@PathVariable("organizationId") Long organizationId){
		List<Person> result = personService.findByOrganizationId(organizationId);
		return Result.success(result);
	}
	@PostMapping("/update")
	public Result<?> update(@RequestBody Person person) throws BiripException{
		personService.update(person);
		return Result.success(person);
	}
}
