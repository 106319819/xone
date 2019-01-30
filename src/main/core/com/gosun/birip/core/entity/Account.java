package com.gosun.birip.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账号信息表
 * <pre>
 * Copyright: Copyright gosun technology Inc. 2018, All rights reserved.
 * Company: 云南戈阳科技有限公司
 * </pre>
 * @author 张尧伟
 * @version 1.0
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long accountId;
	
	@Column(nullable=false,length=64, unique = true, columnDefinition="varchar(64) comment '登录账号'")
	private String accountCode;
	
	@Column(nullable=false,length=256, columnDefinition="varchar(64) comment '密码'")
	private String password;

	@Column(nullable=false,columnDefinition="int(2) comment '账号状态 0未激活 1激活 2禁用 3删除'")
	private Integer status = 1;

	@Column(nullable=false,columnDefinition="char(1) comment '账号类型 9超级管理员  8子系统管理员 0一般账号 ' ")
	private String accountType="0";
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="personId", columnDefinition="bigint comment '引用person表的persion_id' ")
	private Person person;

	
}
