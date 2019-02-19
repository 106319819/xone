package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.xone.core.entity.RegionProvince;

public interface RegionProvinceRespository extends JpaRepository<RegionProvince, String>
{
	public List<RegionProvince> findAll();
	public List<RegionProvince> findByRegionNationality(String nationalityId);
}
