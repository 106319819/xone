package com.gosun.birip.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.birip.core.entity.RegionCity;

public interface RegionCityRespository extends JpaRepository<RegionCity, String>
{
	public List<RegionCity> findByRegionProvince(String provinceCode);
}
