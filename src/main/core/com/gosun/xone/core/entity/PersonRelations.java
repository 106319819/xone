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
 * 人员关系表
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
@Table(name="PersonRelations"
, uniqueConstraints = {@UniqueConstraint(name="PERSON_RELATIONS_FK_INDEX",columnNames= {"organizationId","personId"})}
//,indexes = {@Index(name="PERSON_RELATIONS_IDX_PERSON_ID",columnList="personId",unique=false),
//		@Index(name="PERSON_RELATIONS_IDX_ORGANIZATION_ID",columnList="organizationId",unique=false)}
)
//@org.hibernate.annotations.Table(appliesTo="PERSON_RELATIONS" ,comment="人员关系表")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class PersonRelations extends BaseEntity {


	/**
	 * 组织人员id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "biripIdentityGenerator") 
	@GenericGenerator(name = "biripIdentityGenerator", strategy = "com.gosun.birip.core.utils.BiripIdentityGenerator")
	private Long relationsId;

	/**
	 * 在编状态
	 * 0不在编 1在编 2移除本组织
	 */
	@Column(nullable=false , columnDefinition="int(2) comment '在编状态 0不在编 1在编 2移除本组织'")
	private Integer active;
	
	@Column(columnDefinition="bigint comment '人员id，引用person表主键'")
	private Long personId;
	
	@Column(columnDefinition="bigint comment '组织id，引用organization表主键'")
	private Long organizationId;
	
	
}
