package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.xone.core.entity.City;

public interface CityRespository extends JpaRepository<City, String>
{
	public List<City> findByRegionProvince(String provinceCode);
}
