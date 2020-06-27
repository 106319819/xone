package com.gosun.xone.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Wed Aug 15 11:15:12 CST 2018
 */
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="RegionNationality")
public class Nationality{

	/**
	 * 国家代码 ISO标准
	 */
	@Id
	@Column(length=3)
	private String nationalityId;

	/**
	 * 国家2位英文代码
	 */
	@Column(length=2)
	private String nationality_2code;

	/**
	 * '国家3位英文代码
	 */
	@Column(length=3)
	private String nationality_3code;


	/**
	 * ISO代码
	 */
	@Column(length = 128)
	private String iso_code;

	/**
	 * 英文名称
	 */
	@Column
	private String name_en;

	/**
	 * 中文名称
	 */
	@Column
	private String name_cn;

	@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="RegionProvince", joinColumns= {@JoinColumn(name="nationalityId")},inverseJoinColumns= {@JoinColumn(name="provinceCode")})
	@JoinColumn(name="nationalityId")
	private List<Province> regionProvinces;
}
