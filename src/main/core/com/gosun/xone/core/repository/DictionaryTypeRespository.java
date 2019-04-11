package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.DictionaryType;

public interface DictionaryTypeRespository extends JpaRepository<DictionaryType, Long>
{
	public DictionaryType findByTypeCode(String typeCode);

	@Modifying
	@Query("delete from DictionaryType where typeId in :typeIds")
	public void deleteByIdList(List<Long> typeIds);
}
