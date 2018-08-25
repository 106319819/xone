package com.gosun.birip.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="PersonRelations")
//@org.hibernate.annotations.Table(appliesTo="PERSON_RELATIONS" ,comment="人员关系表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonRelations implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 组织人员id
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	private Long relationsId;

	/**
	 * 在编状态
	 * 0不在编 1在编 2移除本组织
	 */
	@Column(nullable=false , columnDefinition="int(2) comment '在编状态 0不在编 1在编 2移除本组织'")
	private Integer active;
	
	@Column(nullable=false , columnDefinition="int comment '人员id，引用person表主键'")
	private Long personId;
	
	@Column(nullable=false , columnDefinition="int comment '组织id，引用organization表主键'")
	private Long organizationId;
	
	
}
