package com.gosun.birip.core.entity;

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

/**
 * 子系统信息表
 * <pre>
 * Copyright: Copyright gosun technology Inc. 2018, All rights reserved.
 * Company: 云南戈阳科技有限公司
 * </pre>
 * @author 张尧伟
 * @version 1.0
 * @date 2018年8月26日 下午3:12:17
 * @history
 */
@Entity
@Table(name="SubSystem")
public class SubSystem implements Serializable{

	private static final long serialVersionUID = 8131723396319234027L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long subSystemId;
	
	@Column(nullable=false,length=64, columnDefinition="varchar(64) comment '子系统编码'")
	private String code;
	
	@Column(nullable=false,length=256, columnDefinition="varchar(64) comment '名称'")
	private String name;
	
	@Column(nullable=false,columnDefinition="int(2) comment '状态 0未激活 1激活 2禁用 3删除'")
	private Integer status = 0;

	@Column(nullable=false,columnDefinition="int comment '创建时间' ")
	private Long createTime;

	@Column(length=256,columnDefinition="varchar(256) comment '备注' ")
	private String comment;
	//
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="Application" ,joinColumns= {@JoinColumn(name="subSystemId")},inverseJoinColumns= {@JoinColumn(name="applicationId")} )
	@JoinColumn(name="subSystemId")
	private List<Application> applications;

	
	
	
}
