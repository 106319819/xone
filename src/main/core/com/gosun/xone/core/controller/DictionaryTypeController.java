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
import com.gosun.xone.core.entity.DictionaryType;
import com.gosun.xone.core.service.DictionaryTypeService;

@RestController
@RequestMapping("/admin/dictionary-type")
public class DictionaryTypeController {

	@Autowired
	private DictionaryTypeService dictionaryTypeService;
	@RequestMapping("/create")
	public Result<?> create(@RequestBody DictionaryType dictionaryType){
		dictionaryTypeService.create(dictionaryType);
		
		return Result.success(dictionaryType);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<DictionaryType> result = dictionaryTypeService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	@RequestMapping("/find-list")
	public Result<?> findList() throws IOException, XoneException{
		List<DictionaryType> result = dictionaryTypeService.findAll();
		return Result.success(result);
	}
	
	@RequestMapping("/find-by-id/{typeId}")
	public Result<?> findById(@PathVariable("typeId") Long typeId){
		DictionaryType result = dictionaryTypeService.findByTypeId(typeId);
		return Result.success(result);
	}
	@RequestMapping("/find-by-code/{typeCode}")
	public Result<?> findById(@PathVariable("typeCode") String typeCode){
		DictionaryType result = dictionaryTypeService.findByTypeCode(typeCode);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{typeId}")
	public Result<?> delete(@PathVariable("typeId") Long typeId){
		dictionaryTypeService.deleteByTypeId(typeId);
		return Result.success(typeId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> typeIds){
		dictionaryTypeService.deleteByIdList(typeIds);
		return Result.success(typeIds);
	}
	
	@PostMapping("/update")
	public Result<?> update(@RequestBody DictionaryType dictionaryType) throws XoneException{
		dictionaryTypeService.update(dictionaryType);
		return Result.success(dictionaryType);
	}
	
	
}
