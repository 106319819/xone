package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Wed Aug 15 11:15:13 CST 2018
 */
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RegionProvince")
public class RegionProvince implements Serializable{

	@Id
	@Column(columnDefinition="char(6) comment'省份编码'  ")
	private String provinceCode;

	@Column(columnDefinition="varchar(64) comment'省份名称'  ")
	private String provinceName;

	@Column(columnDefinition="varchar(64) comment'省份简称'  ")
	private String shortName;

	@Column(columnDefinition="varchar(32) comment'省份拼音码'  ")
	private String chineseCode;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="nationalityId")
	private RegionNationality regionNationality;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="RegionCity" ,joinColumns= {@JoinColumn(name="proviceCode")},inverseJoinColumns= {@JoinColumn(name="cityCode")} )
	@JoinColumn(name="proviceCode")
	private List<RegionCity> regionCities;

}
