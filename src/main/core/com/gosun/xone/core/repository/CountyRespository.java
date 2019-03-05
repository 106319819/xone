package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.xone.core.entity.County;


public interface CountyRespository extends JpaRepository<County, String>
{
	public List<County> findByRegionCity(String cityCode);
}
