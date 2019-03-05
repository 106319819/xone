package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.xone.core.entity.Province;

public interface ProvinceRespository extends JpaRepository<Province, String>
{
	public List<Province> findAll();
	public List<Province> findByRegionNationality(String nationalityId);
}
