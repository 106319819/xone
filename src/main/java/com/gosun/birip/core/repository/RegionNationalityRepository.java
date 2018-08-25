package com.gosun.birip.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.birip.core.entity.RegionNationality;

public interface RegionNationalityRepository extends JpaRepository<RegionNationality, String>
{
	public List<RegionNationality> findAll();
	public RegionNationality findByNationalityId(String nationalityId);
}
