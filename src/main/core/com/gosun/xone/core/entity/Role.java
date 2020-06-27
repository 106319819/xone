package com.gosun.xone.core.entity;



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
 * 模块（菜单）信息表
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
@Table(name="Role")
public class Role extends BaseEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
//	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long roleId;
	
	//名称
	@Column(nullable=false,length=64)
	private String name;
	//排序码
	@Column
    private Integer sortNo;

	//状态 0未激活 1激活 2禁用 3删除
	@Column
	private Integer status;
	
	
}
