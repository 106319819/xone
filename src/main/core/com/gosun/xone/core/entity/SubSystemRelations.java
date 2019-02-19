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
 * 子系统关系表
 * <pre>
 * Copyright: Copyright gosun technology Inc. 2018, All rights reserved.
 * Company: 云南戈阳科技有限公司
 * </pre>
 * @author 张尧伟
 * @version 1.0
 * @date 2018年8月26日 下午3:13:05
 * @history
 */
@Data
@EqualsAndHashCode( callSuper = false)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="SubSystemRelations"
, uniqueConstraints = {@UniqueConstraint(name="SUB_SYSTEM_RELATIONS_INDEX",columnNames= {"organizationId","subSystemId"})}
)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class SubSystemRelations extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "biripIdentityGenerator") 
	@GenericGenerator(name = "biripIdentityGenerator", strategy = "com.gosun.birip.core.utils.BiripIdentityGenerator")
	private Long relationsId;

	/**
	 * 启用状态
	 * 0不启用 1启用 
	 */
	@Column(nullable=false , columnDefinition="int(2) comment '状态 0不启用 1启用 '")
	private Integer active;
	
	@Column(columnDefinition="bigint")
	private Long subSystemId;
	
	@Column(columnDefinition="bigint comment '组织id，引用organization表主键'")
	private Long organizationId;
	
	
}
