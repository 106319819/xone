package com.gosun.birip.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="account")
public class Account implements Serializable{

	private static final long serialVersionUID = 8131723396319234027L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long accountId;
	
	@Column(nullable=false,length=64, columnDefinition="varchar(64) comment '登录账号'")
	private String accountCode;
	
	@Column(nullable=false,length=256, columnDefinition="varchar(64) comment '密码'")
	private String password;

	@Column(nullable=false,columnDefinition="int(2) comment '账号状态 0未激活 1激活 2禁用 3删除'")
	private Integer status = 0;

	@Column(nullable=false,columnDefinition="int comment '创建时间' ")
	private Long createTime;

	@Column(length=256,columnDefinition="varchar(256) comment '备注' ")
	private String comment;
	
	@OneToOne
	@JoinColumn(name="personId", columnDefinition="int comment '引用person表的persion_id' ")
	private Person person;

	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}


	public String getAccountCode()
	{
		return accountCode;
	}

	public void setAccountCode(String accountCode)
	{
		this.accountCode = accountCode;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Long createTime)
	{
		this.createTime = createTime;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	
}
