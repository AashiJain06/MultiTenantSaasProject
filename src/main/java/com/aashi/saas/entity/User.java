package com.aashi.saas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false,unique=true)
	private String username;
	@Column(nullable=false,unique=true)
	private String email;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String role;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tenant_id",nullable=false)
	private Tenant tenant;

}
