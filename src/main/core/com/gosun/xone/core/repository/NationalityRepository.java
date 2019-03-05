package com.gosun.xone.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.xone.core.entity.Nationality;

public interface NationalityRepository extends JpaRepository<Nationality, String>
{
	public List<Nationality> findAll();
	public Nationality findByNationalityId(String nationalityId);
}
