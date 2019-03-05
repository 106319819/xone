package com.gosun.xone.core.entity;


/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Aug 14 15:19:48 CST 2018
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 人员信息表
 * @date 2018年8月26日 下午3:12:49
 * @history
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="Person")
public class Person extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long personId;


	/**
	 * 人员编码
	 */
	@Column(length=64)
	private String personCode;

	/**
	 * 全名
	 */
	@Column(nullable=false,length=128)
	private String fullName;

	/**
	 * 别名
	 */
	@Column(length=128)
	private String aliasName;
	/**
	 * 名
	 */
	@Column(nullable=false,length=128)
	private String firstName;

	/**
	 * 姓
	 */
	@Column(nullable=false,length=32)
	private String lastName;

	/**
	 * 证号
	 */
	@Column(length=32)
	private String identityNo;

	/**
	 * 性别
	 * W女 M男 X未知
	 */
	@Column(length=4)
	private String sex;
	

	/**
	 * 出生日期 yyyy-mm-dd
	 */
	@Column(length=10)
	private String birthday;

	/**
	 *国籍代码,默认中国 3个字母
	 */	
	@Column(length=3)
	private String nationalityCode="CHN"; 
	/**
	 *行政区划代码
	 */
	@Column(columnDefinition="varchar(64) comment '行政区划代码'  ")
	private String countyCode;

	@Column(length=64,columnDefinition="varchar(64) comment '手机号'  ")
	private String mobile;


	@Column(length=256,columnDefinition="varchar(256) comment '邮箱'  ")
	private String email;

	
//	@OneToMany(fetch=FetchType.LAZY)
//	@JoinTable(name="PersonRelations",joinColumns = {@JoinColumn(name="personId", unique=false)},inverseJoinColumns= {@JoinColumn(name="organizationId", unique=false)})
//	private List<Organization> organizations;
	
	@OneToOne
	@JoinColumn(name="accountId")
	private Account account;
	
	/**
	 * 仅记录在创建人员时，方便同时建立人员关系时使用
	 */
	@Transient
	private Long organizationId;
}
