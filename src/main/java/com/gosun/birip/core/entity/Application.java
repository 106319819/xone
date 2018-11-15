package com.gosun.birip.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用信息表
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
@Table(name="application")
public class Application extends BaseEntity{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long applicationId;
	
	@Column(nullable=false,length=64, columnDefinition="varchar(64) comment '应用编码'")
	private String code;
	
	@Column(nullable=false,length=256, columnDefinition="varchar(64) comment '名称'")
	private String name;
	
	@Column(nullable=false,columnDefinition="int(2) comment '状态 0未激活 1激活 2禁用 3删除'")
	private Integer status = 0;
	
	//应用所属子系统
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="subSystemId")
	private SubSystem subSystem;
	
}
