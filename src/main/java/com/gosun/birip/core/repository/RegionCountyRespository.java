package com.gosun.birip.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.birip.core.entity.RegionCounty;


public interface RegionCountyRespository extends JpaRepository<RegionCounty, String>
{
	public List<RegionCounty> findByRegionCity(String cityCode);
}
