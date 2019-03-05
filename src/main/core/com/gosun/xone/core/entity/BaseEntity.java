package com.gosun.xone.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * @version 1.0
 * @date 2018年8月26日 下午3:12:17
 * @history
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract  class BaseEntity {

	@CreatedDate
	@Column(columnDefinition="datetime comment '创建时间' ")
	private Date createTime;

	@LastModifiedDate
	private Date updateTime;


	@Column(length=256,columnDefinition="varchar(256) comment '备注' ")
	private String comment;

	
}
