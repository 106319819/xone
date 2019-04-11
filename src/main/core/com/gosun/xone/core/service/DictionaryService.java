package com.gosun.xone.core.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gosun.xone.core.entity.Dictionary;

public interface DictionaryService
{
	public void create(Dictionary dictionary);
	public void deleteById(Long dictionaryId);
	public void deleteByIdList(List<Long> dictionaryIds);
	public void deleteAll(Long typeId);
	public void delete(Long typeId,String code);
	
	
	public void update(Dictionary dictionary);
	public Page<Dictionary> findAll(Pageable pageable);
	public List<Dictionary> findAll();
	public List<Dictionary> findAllByTypeId(Long typeId);
	public Dictionary findByTypeIdAndCode(Long typeId,String code);
	public Dictionary findById(Long dictionaryId);
}
