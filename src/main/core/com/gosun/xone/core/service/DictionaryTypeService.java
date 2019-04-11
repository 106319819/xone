package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.xone.core.entity.DictionaryType;

public interface DictionaryTypeService
{
	public void create(DictionaryType dictionaryType);
	public void deleteByTypeId(Long typeId);
	public void deleteByIdList(List<Long> typeIds);
	public void update(DictionaryType dictionaryType);
	public Page<DictionaryType> findAll(Pageable pageable);
	public List<DictionaryType> findAll();
	public DictionaryType findByTypeId(Long typeId);
	public DictionaryType findByTypeCode(String typeCode);
	
}
