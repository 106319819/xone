package com.gosun.birip.core.entity;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Aug 14 15:19:47 CST 2018
 */
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="organization")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 组织id
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	private Long organizationId;

	@Column(nullable=false,length=256,columnDefinition="varchar(256) comment '组织中文名称' ")
	private String organizationName;

	@Column(nullable=false,unique=true,length=32,columnDefinition="varchar(32) comment '组织编码32位长度 可以自定义默认为organizationId' ")
	private String organizationCode;

	@Column(length=256,columnDefinition="varchar(256) comment '组织英文名称，默认与中文名称一致' ")
	private String organizationNameEn;

	@Column(nullable=false,columnDefinition="int(4) comment '组织层级，从0开始，由系统维护' ")
	private Integer level = 0;

	@Column(nullable=false,columnDefinition="int comment '排序索引，默认设置为organizationId' ")
	private Long sortNo;
	
	@Column(nullable=false,columnDefinition="int comment '上级组织的organizationId' ")
	private Long parentId;

	@Column(nullable=false,columnDefinition="int(2) comment '人员状态 0 正常 1禁用 2删除 ' ")
	private Integer state=0;

	@Column(length=256 ,columnDefinition="varchar(256) comment '备注' ")
	private String comment;

	
	@Column(length=20,nullable=false,columnDefinition="bigint(20) comment '创建时间' ")
	private Long createTime;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PersonRelations",joinColumns = {@JoinColumn(name="organizationId")},inverseJoinColumns= {@JoinColumn(name="personId")})
	private List<Person> persons;

	public Long getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}

	public String getOrganizationName()
	{
		return organizationName;
	}

	public void setOrganizationName(String organizationName)
	{
		this.organizationName = organizationName;
	}

	public String getOrganizationCode()
	{
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode)
	{
		this.organizationCode = organizationCode;
	}

	public String getOrganizationNameEn()
	{
		return organizationNameEn;
	}

	public void setOrganizationNameEn(String organizationNameEn)
	{
		this.organizationNameEn = organizationNameEn;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

	public Long getSortNo()
	{
		return sortNo;
	}

	public void setSortNo(Long sortNo)
	{
		this.sortNo = sortNo;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Long createTime)
	{
		this.createTime = createTime;
	}

	public List<Person> getPersons()
	{
		return persons;
	}

	public void setPersons(List<Person> persons)
	{
		this.persons = persons;
	}


}
