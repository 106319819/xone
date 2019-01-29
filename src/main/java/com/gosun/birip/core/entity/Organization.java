package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Aug 14 15:19:47 CST 2018
 */
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
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织信息表
 * <pre>
 * Copyright: Copyright gosun technology Inc. 2018, All rights reserved.
 * Company: 云南戈阳科技有限公司
 * </pre>
 * @author 张尧伟
 * @version 1.0
 * @date 2018年8月26日 下午3:12:38
 * @history
 */
@Data
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="organization")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization extends BaseEntity{

	/**
	 * 组织id
	 */
	@Id
	//@GeneratedValue(strategy= GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "biripIdentityGenerator") 
	@GenericGenerator(name = "biripIdentityGenerator", strategy = "com.gosun.birip.core.utils.BiripIdentityGenerator")
	private Long organizationId;

	@Column(nullable=false,length=256,columnDefinition="varchar(256) comment '组织中文名称' ")
	private String organizationName;

	@Column(unique=true,length=64,columnDefinition="varchar(64) comment '组织编码64位长度 可以自定义默认为organizationId' ")
	private String organizationCode;

	@Column(length=256,columnDefinition="varchar(256) comment '组织英文名称，默认与中文名称一致' ")
	private String organizationNameEn;

	@Column(nullable=false,columnDefinition="int(4) comment '组织层级，从0开始，由系统维护' ")
	private Integer level = 0;

	@Column(nullable=false,columnDefinition="int comment '排序索引，默认设置为organizationId' ")
	private Long sortNo = 0L;
	
	@Column(columnDefinition="int comment '上级组织的organizationId' ")
	private Long parentId;

	@Column(nullable=false,columnDefinition="int(2) comment '状态 0 正常 1禁用 2删除 ' ")
	private Integer state=0;
	@Column(nullable=false,columnDefinition="char(1) comment '是否叶子节点 1是 0否' ")
	private String isLeaf="1";


	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PersonRelations",joinColumns = {@JoinColumn(name="organizationId")},inverseJoinColumns= {@JoinColumn(name="personId")})
	private List<Person> persons;

}
