package com.gosun.xone.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号信息表
 * @date 2018年8月26日 下午3:12:17
 * @history
 */
@Data
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="account")
public class Account extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "xoneIdentityGenerator") 
//	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long accountId;
	/**
	 * 登录账号
	 */
	@Column(nullable=false,length=64, unique = true)
	private String accountCode;
	/**
	 * 密码
	 */
	@Column(nullable=false,length=256)
	private String password;

	/**
	 * 账号状态 0未激活 1激活 2禁用 3删除
	 */
	@Column(nullable=false)
	private Integer status = 1;

	/**
	 * 账号类型 9超级管理员  8子系统管理员 0一般账号
	 */
	@Column(nullable=false,length=1)
	private String accountType="0";


	
}
