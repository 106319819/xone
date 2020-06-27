package com.gosun.xone.core.entity;


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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name="RegionCity")
public class City {

	/**
	 * 城市编码
	 */
	@Id
	@Column(length=6)
	private String cityCode;

	/**
	 * 城市名称
	 */
	@Column(length=64)
	private String cityName;

	/**
	 * 拼音码
	 */
	@Column(length=32)
	private String chineseCode;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proviceCode")
	private Province regionProvince;
	
	@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="RegionCounty",joinColumns= {@JoinColumn(name="cityCode")}, inverseJoinColumns = {@JoinColumn(name="countyCode")})
	@JoinColumn(name="cityCode")
	private List<County> regionCounties;
	

}
