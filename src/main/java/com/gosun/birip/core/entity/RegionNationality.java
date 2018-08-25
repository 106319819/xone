package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Wed Aug 15 11:15:12 CST 2018
 */
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RegionNationality")
public class RegionNationality implements Serializable{

	@Id
	@Column(columnDefinition="char(3) comment '国家代码 ISO标准'")
	private String nationalityId;

	@Column(columnDefinition="char(2) comment '国家2位英文代码' ")
	private String nationality_2code;

	@Column(columnDefinition="char(3) comment '国家3位英文代码' ")
	private String nationality_3code;


	@Column(columnDefinition="varchar(128) comment 'ISO代码' ")
	private String iso_code;

	@Column(columnDefinition="varchar(256) comment '英文名称' ")
	private String name_en;

	@Column(columnDefinition="varchar(256) comment '中文名称' ")
	private String name_cn;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="RegionProvince", joinColumns= {@JoinColumn(name="nationalityId")},inverseJoinColumns= {@JoinColumn(name="provinceCode")})
	private List<RegionProvince> regionProvinces;
}
