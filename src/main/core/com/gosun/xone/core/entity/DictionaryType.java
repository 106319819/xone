package com.gosun.xone.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典信息表
 * @date 2018年8月26日 下午3:12:17
 * @history
 */
@Data
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="DictionaryType"
, uniqueConstraints = {@UniqueConstraint(name="DICTIONARY_TYPE_INDEX",columnNames= {"typeCode"})}
)
public class DictionaryType extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
//	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long typeId;
	
	@Column(nullable=false)
	private String typeCode;
	
	@Column(nullable=false)
	private String typeName;

	/**
	 * 账号状态 0未激活 1激活 2禁用 3删除
	 */
	@Column(nullable=false,length=2)
	private Integer status = 1;


	
}
