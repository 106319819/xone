package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Wed Aug 15 11:15:12 CST 2018
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="RegionCounty")
public class RegionCounty{

	@Id
	@Column(columnDefinition="char(6) comment '县区代码' ")
	private String countyCode;

	@Column(columnDefinition="varchar(64) comment '县区名称' ")
	private String countyName;

	@Column(columnDefinition="varchar(32) comment'拼音码'  ")
	private String chineseCode;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cityCode")
	private RegionCity regionCity;
}
