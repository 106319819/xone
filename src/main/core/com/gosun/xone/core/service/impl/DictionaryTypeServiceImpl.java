package com.gosun.xone.core.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.xone.core.entity.DictionaryType;
import com.gosun.xone.core.repository.DictionaryTypeRespository;
import com.gosun.xone.core.service.DictionaryTypeService;
@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService
{

	@Autowired
	private DictionaryTypeRespository dictionaryTypeRespository;

	public void create(DictionaryType dictionaryType)
	{
		this.dictionaryTypeRespository.save(dictionaryType);
	}

	public Page<DictionaryType> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return dictionaryTypeRespository.findAll(pageable);
	}

	@Override
	public void deleteByTypeId(Long typeId) {
		// TODO Auto-generated method stub
		dictionaryTypeRespository.deleteById(typeId);
	}
	
	

	@Override
	public void deleteByIdList(List<Long> typeIds) {
		// TODO Auto-generated method stub
		dictionaryTypeRespository.deleteByIdList(typeIds);
	}

	@Override
	public void update(DictionaryType dictionaryType) {
		// TODO Auto-generated method stub
		dictionaryTypeRespository.save(dictionaryType);
	}

	@Override
	public List<DictionaryType> findAll() {
		// TODO Auto-generated method stub
		return dictionaryTypeRespository.findAll();
	}

	@Override
	public DictionaryType findByTypeId(Long typeId) {
		// TODO Auto-generated method stub
		return dictionaryTypeRespository.findById(typeId).get();
	}

	@Override
	public DictionaryType findByTypeCode(String typeCode) {
		// TODO Auto-generated method stub
		return dictionaryTypeRespository.findByTypeCode(typeCode);
	}
	

}
