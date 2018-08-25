package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Wed Aug 15 11:15:11 CST 2018
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

@Entity
@Table(name="RegionCity")
public class RegionCity implements Serializable{

	@Id
	@Column(columnDefinition="char(6) comment '城市编码' ")
	private String cityCode;

	@Column(columnDefinition="varchar(64) comment '城市名称' ")
	private String cityName;

	@Column(columnDefinition="varchar(32) comment'拼音码'  ")
	private String chineseCode;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proviceCode")
	private RegionProvince regionProvince;
	
	@OneToMany
	@JoinTable(name="RegionCounty",joinColumns= {@JoinColumn(name="cityCode")}, inverseJoinColumns = {@JoinColumn(name="countyCode")})
	private List<RegionCounty> regionCounties;

}
