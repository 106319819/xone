package com.gosun.xone.core.entity;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name="module")
public class Module extends BaseEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
	@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
	private Long moduleId;
	
	//名称
	@Column(nullable=false,length=64)
	private String name;
	//上级模块id
	@Column
	private Long parentId;

	//菜单URL
	@Column(length=256)
    private String url;

	//权限标识
	@Column(length=128)
    private String permission;

	//模块类型 0 分类 1菜单 2操作
	@Column(nullable=false)
    private Integer type;

	//图标资源
	@Column(length=64)
    private String icon;

	//排序码
	@Column
    private Integer sortNo = 0;

	//状态 0未激活 1激活 2禁用 3删除
	@Column(nullable=false)
	private Integer status=1;
	
    // 非数据库字段
	@Transient
    private String parentName;
    // 非数据库字段
	@Transient
    private Integer level;
    // 非数据库字段
	@Transient
    private List<Module> children;
	
	
	//模块所属子系统
	@Column
	private Long subSystemId;
	
}
