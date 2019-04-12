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
import org.hibernate.annotations.GenericGenerator;

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
@Table(name="Dictionary"
, uniqueConstraints = {@UniqueConstraint(name="DICTIONARY_INDEX",columnNames= {"typeId","code"})}
)
public class Dictionary extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long dictionaryId;
	
	@Column(nullable=false)
	private String code;
	
	@Column(nullable=false)
	private String value;
	
//	@Column(nullable=false)
//	private String name;
	
	@Column
	private Integer sortNo;

	@Column(nullable=false)
	private Long typeId;


	
}
