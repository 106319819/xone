package com.gosun.xone.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Wed Aug 15 11:15:13 CST 2018
 */
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="RegionProvince")
public class Province{

	/**
	 * 省份编码
	 */
	@Id
	@Column(length = 6)
	private String provinceCode;

	/**
	 * 省份名称
	 */
	@Column(length = 64)
	private String provinceName;

	/**
	 * 省份简称
	 */
	@Column(length = 32)
	private String shortName;

	/**
	 * 省份拼音码
	 */
	@Column(length = 32)
	private String chineseCode;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nationalityId")
	private Nationality regionNationality;
	
	@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="RegionCity" ,joinColumns= {@JoinColumn(name="proviceCode")},inverseJoinColumns= {@JoinColumn(name="cityCode")} )
	@JoinColumn(name="proviceCode")
	private List<City> regionCities;

}
