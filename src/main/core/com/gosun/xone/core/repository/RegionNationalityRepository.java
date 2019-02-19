package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.xone.core.entity.RegionNationality;

public interface RegionNationalityRepository extends JpaRepository<RegionNationality, String>
{
	public List<RegionNationality> findAll();
	public RegionNationality findByNationalityId(String nationalityId);
}
