package com.gosun.xone.core.entity;

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
public class County{

	/**
	 * 县区代码
	 */
	@Id
	@Column(length=6)
	private String countyCode;

	/**
	 * 县区名称
	 */
	@Column(length=64)
	private String countyName;

	/**
	 * 拼音码
	 */
	@Column(length=32)
	private String chineseCode;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cityCode")
	private City regionCity;
}
