package com.gosun.xone.core.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="sessions")
public class Sessions {

	@Id
	private String primaryId;
	
	@Column
	private String sessionId;

	@Column
	private long creationTime;

	@Column
	private long lastAccessTime;

	@Column
	private int maxInactiveInterval;

	@Column
	private long expiryTime;

	@Column
	private String principalName;

}
