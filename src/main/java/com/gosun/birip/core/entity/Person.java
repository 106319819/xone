package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Aug 14 15:19:48 CST 2018
 */
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="person")
public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long personId;


	/**
	 * 人员编码
	 */
	@Column(nullable=false,length=64)
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
	@Column(nullable=false,length=32)
	private String identityNo;

	/**
	 * 性别
	 * w女 m男 X未知
	 */
	@Column(nullable=false,length=4)
	private String sex = "x";
	

	/**
	 * 出生日期 yyyy-mm-dd
	 */
	@Column(nullable=false,length=10)
	private String birthday;

	/**
	 *国籍代码,默认中国 3个字母
	 */	
	@Column(nullable=false,length=3)
	private String nationalityCode="CHN"; 
	/**
	 *行政区划代码
	 */
	@Column(nullable=false,columnDefinition="varchar(64) comment '行政区划代码'  ")
	private String countyCode;

	@Column(length=64,columnDefinition="varchar(64) comment '手机号'  ")
	private String mobile;


	@Column(length=256,columnDefinition="varchar(256) comment '邮箱'  ")
	private String email;


	@Column(nullable=false,columnDefinition="int comment '创建日期'  ")
	private Long createTime;

	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PersonRelations",joinColumns = {@JoinColumn(name="personId")},inverseJoinColumns= {@JoinColumn(name="organizationId")})
	private List<Organization> organizations;
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account account;
	
	public Long getPersonId()
	{
		return personId;
	}


	public String getPersonCode()
	{
		return personCode;
	}


	public String getFullName()
	{
		return fullName;
	}


	public String getAliasName()
	{
		return aliasName;
	}


	public String getFirstName()
	{
		return firstName;
	}


	public String getLastName()
	{
		return lastName;
	}


	public String getIdentityNo()
	{
		return identityNo;
	}


	public String getSex()
	{
		return sex;
	}


	public String getBirthday()
	{
		return birthday;
	}


	public String getNationalityCode()
	{
		return nationalityCode;
	}


	public String getCountyCode()
	{
		return countyCode;
	}


	public String getMobile()
	{
		return mobile;
	}


	public String getEmail()
	{
		return email;
	}


	public Long getCreateTime()
	{
		return createTime;
	}


}
