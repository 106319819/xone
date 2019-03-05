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

	@Id
	@Column(columnDefinition="char(6) comment '城市编码' ")
	private String cityCode;

	@Column(columnDefinition="varchar(64) comment '城市名称' ")
	private String cityName;

	@Column(columnDefinition="varchar(32) comment'拼音码'  ")
	private String chineseCode;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="proviceCode")
	private Province regionProvince;
	
	@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="RegionCounty",joinColumns= {@JoinColumn(name="cityCode")}, inverseJoinColumns = {@JoinColumn(name="countyCode")})
	@JoinColumn(name="cityCode")
	private List<County> regionCounties;
	

}
