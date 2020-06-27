package com.gosun.xone.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Aug 14 15:19:47 CST 2018
 */

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
 * 组织信息表
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
//	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long organizationId;

	/**
	 * 组织中文名称
	 */
	@Column(nullable=false,length=256)
	private String organizationName;

	/**
	 * 组织编码64位长度 可以自定义默认为organizationId
	 */
	@Column(unique=true,length=256)
	private String organizationCode;

	/**
	 * 组织英文名称，默认与中文名称一致
	 */
	@Column(length=256)
	private String organizationNameEn;

	/**
	 * 组织层级，从0开始，由系统维护
	 */
	@Column(nullable=false,length = 4)
	private Integer level = 0;

	/**
	 * 排序索引，默认设置为organizationId
	 */
	@Column(nullable=false)
	private Long sortNo = 0L;
	
	/**
	 * 上级组织的organizationId
	 */
	@Column
	private Long parentId;

	/**
	 * 状态 0 正常 1禁用 2删除
	 */
	@Column(nullable=false,length = 2)
	private Integer state=0;
	/**
	 * 是否叶子节点 1是 0否
	 */
	@Column(nullable=false,length = 1)
	private String isLeaf="1";


//	@OneToMany(fetch=FetchType.LAZY)
//	@JoinTable(name="PersonRelations",joinColumns = {@JoinColumn(name="organizationId" ,unique=false)},inverseJoinColumns= {@JoinColumn(name="personId" , unique=false)})
//	private List<Person> persons;

}
