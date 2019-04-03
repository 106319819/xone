package com.gosun.xone.core.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="SystemLog")
public class SystemLog extends BaseEntity
{
	  	@Id
		@GeneratedValue(strategy = GenerationType.AUTO, generator = "xoneIdentityGenerator") 
		@GenericGenerator(name = "xoneIdentityGenerator", strategy = "com.gosun.xone.core.utils.XoneIdentityGenerator")
		private Long systemLogId;		
		@Column
		private String accountCode;		
		@Column
		private Long accountId;
		@Column
		private Long personId;
		@Column
		private String fullName;
		@Column
		private String packages;
		@Column
		private String clazz;
		@Column
		private String method;
		@Basic(fetch = FetchType.LAZY)
		@Column(length=8192)
		private String args;
		@Column
		private String host;
		@Column
		private Date startTime;
		@Column
		private Long times;
}
