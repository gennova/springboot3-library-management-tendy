package com.amazon.test.model;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "email_address", nullable = false)
	private String emailId;
	@Column(name = "created_at", nullable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "created_by", nullable = false)
	@CreatedBy
	private String createdBy;
	@Column(name = "updated_at", nullable = false)
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	@Column(name = "updated_by", nullable = false)
	private String updatedby;

}
