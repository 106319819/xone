package com.gosun.xone.core.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.xone.core.entity.Dictionary;
import com.gosun.xone.core.repository.DictionaryRespository;
import com.gosun.xone.core.service.DictionaryService;
@Service
public class DictionaryServiceImpl implements DictionaryService
{

	@Autowired
	private DictionaryRespository dictionaryRespository;

	public void create(Dictionary dictionary)
	{
		this.dictionaryRespository.save(dictionary);
	}

	public Page<Dictionary> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return dictionaryRespository.findAll(pageable);
	}

	@Override
	public void deleteAll(Long typeId) {
		// TODO Auto-generated method stub
		dictionaryRespository.deleteAll(typeId);
	}

	@Override
	public void update(Dictionary dictionary) {
		// TODO Auto-generated method stub
		dictionaryRespository.save(dictionary);
	}

	@Override
	public void deleteById(Long dictionaryId) {
		// TODO Auto-generated method stub
		dictionaryRespository.deleteById(dictionaryId);
	}

	@Override
	public void deleteByIdList(List<Long> dictionaryIds) {
		// TODO Auto-generated method stub
		dictionaryRespository.deleteByIdList(dictionaryIds);
	}

	@Override
	public void delete(Long typeId, String code) {
		// TODO Auto-generated method stub
		dictionaryRespository.delete(typeId,code);
	}

	@Override
	public List<Dictionary> findAllByTypeId(Long typeId) {
		// TODO Auto-generated method stub
		return dictionaryRespository.findAllByTypeId(typeId);
	}

	@Override
	public Dictionary findByTypeIdAndCode(Long typeId, String code) {
		// TODO Auto-generated method stub
		return dictionaryRespository.findByTypeIdAndCode(typeId, code);
	}

	@Override
	public Dictionary findById(Long dictionaryId) {
		// TODO Auto-generated method stub
		return dictionaryRespository.findById(dictionaryId).get();
	}

	
	

}
