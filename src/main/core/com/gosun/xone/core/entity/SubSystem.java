package com.gosun.xone.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 子系统信息表
 * @version 1.0
 * @date 2018年8月26日 下午3:12:17
 * @history
 */
@Data
@EqualsAndHashCode( callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="SubSystem")
public class SubSystem extends BaseEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
//	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long subSystemId;
	/**
	 * 子系统编码
	 */
	@Column(nullable=false,length=64)
	private String code;
	/**
	 * 名称
	 */
	@Column(nullable=false,length=256)
	private String name;
	
	/**
	 * 状态 0未激活 1激活 2禁用 3删除
	 */
	@Column(nullable=false,length = 2)
	private Integer status = 0;

	//
//	@OneToMany(fetch=FetchType.LAZY)
//	//@JoinTable(name="Application" ,joinColumns= {@JoinColumn(name="subSystemId")},inverseJoinColumns= {@JoinColumn(name="applicationId")} )
//	@JoinColumn(name="subSystemId")
//	private List<Application> applications;

	/**
	 * 仅记录在创建子系统时，方便同时建立子系统关系时使用
	 */
	@Transient
	private Long organizationId;
}
