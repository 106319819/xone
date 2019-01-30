package com.gosun.birip.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * 账号信息表
 * <pre>
 * Copyright: Copyright gosun technology Inc. 2018, All rights reserved.
 * Company: 云南戈阳科技有限公司
 * </pre>
 * @author 张尧伟
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
