package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gosun.xone.core.entity.Dictionary;

public interface DictionaryRespository extends JpaRepository<Dictionary, Long>
{
	public List<Dictionary> findAllByTypeId(Long typeId);
	
	@Query("select t1 from Dictionary t1,DictionaryType t2"
			+ " where t1.typeId = t2.typeId and t2.typeCode = :typeCode")
	public List<Dictionary> findAllByTypeCode(String typeCode);
	
	public Dictionary findByTypeIdAndCode(Long typeId,String code);
	@Modifying
	@Query("delete from Dictionary where typeId = :typeId")
	public void deleteAll(Long typeId);
	
	@Modifying
	@Query("delete from Dictionary where typeId = :typeId and code = :code")
	public void delete(Long typeId, String code);
	
	@Modifying
	@Query("delete from Dictionary where dictionaryId in :ids")
	public void deleteByIdList(List<Long> ids);
}
