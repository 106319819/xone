package com.gosun.birip.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gosun.birip.core.entity.Person;

public interface PersonRespository extends JpaRepository<Person, Long>
{

}
