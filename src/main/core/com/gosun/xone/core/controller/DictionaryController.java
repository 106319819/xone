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
import com.gosun.xone.core.entity.Dictionary;
import com.gosun.xone.core.service.DictionaryService;

@RestController
@RequestMapping("/admin/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Dictionary dictionary){
		dictionaryService.create(dictionary);
		
		return Result.success(dictionary);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<Dictionary> result = dictionaryService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	@RequestMapping("/find-all-list")
	public Result<?> findAllList(@RequestBody String content) throws IOException, XoneException{
		List<Dictionary> result = dictionaryService.findAll();
		return Result.success(result);
	}
	@RequestMapping("/find-list-by/{typeId}")
	public Result<?> findListBy(@PathVariable("typeId") Long typeId) throws IOException, XoneException{
		List<Dictionary> result = dictionaryService.findAllByTypeId(typeId);
		return Result.success(result);
	}
	

	@RequestMapping("/find-by-code/{typeId}/{code}")
	public Result<?> findById(@PathVariable("typeId") Long typeId,@PathVariable("code") String code){
		Dictionary result = dictionaryService.findByTypeIdAndCode(typeId, code);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{dictionaryId}")
	public Result<?> delete(@PathVariable("dictionaryId") Long dictionaryId){
		dictionaryService.deleteById(dictionaryId);
		return Result.success(dictionaryId);
	}
	@RequestMapping("/delete-by-type-id/{typeId}")
	public Result<?> deleteByTypeId(@PathVariable("typeId") Long typeId){
		dictionaryService.deleteAll(typeId);
		return Result.success(typeId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> dictionaryIds){
		dictionaryService.deleteByIdList(dictionaryIds);
		return Result.success(dictionaryIds);
	}
	
	@PostMapping("/update")
	public Result<?> update(@RequestBody Dictionary dictionary) throws XoneException{
		dictionaryService.update(dictionary);
		return Result.success(dictionary);
	}
	
	
}
