package org.corporateforce.server.model;

// Generated 25.12.2014 2:00:33 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Tickets generated by hbm2java
 */
@Entity
@Table(name = "tickets", catalog = "corporateforce")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  

public class Tickets implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Projects projects;
	private Users usersByUser;
	private Users usersByCreator;
	private Date created;
	private Date updated;
	private String type;
	private String priority;
	private String status;
	private BigDecimal estimate;
	private Date duedate;
	private String name;
	private String description;

	public Tickets() {
	}

	public Tickets(Projects projects, Date created, Date updated, String type,
			String priority, String status, Date duedate, String name) {
		this.projects = projects;
		this.created = created;
		this.updated = updated;
		this.type = type;
		this.priority = priority;
		this.status = status;
		this.duedate = duedate;
		this.name = name;
	}

	public Tickets(Projects projects, Users usersByUser, Users usersByCreator,
			Date created, Date updated, String type, String priority,
			String status, BigDecimal estimate, Date duedate, String name,
			String description) {
		this.projects = projects;
		this.usersByUser = usersByUser;
		this.usersByCreator = usersByCreator;
		this.created = created;
		this.updated = updated;
		this.type = type;
		this.priority = priority;
		this.status = status;
		this.estimate = estimate;
		this.duedate = duedate;
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT", nullable = false)
	public Projects getProjects() {
		return this.projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER")
	public Users getUsersByUser() {
		return this.usersByUser;
	}

	public void setUsersByUser(Users usersByUser) {
		this.usersByUser = usersByUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR")
	public Users getUsersByCreator() {
		return this.usersByCreator;
	}

	public void setUsersByCreator(Users usersByCreator) {
		this.usersByCreator = usersByCreator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, length = 19)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED", nullable = false, length = 19)
	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Column(name = "TYPE", nullable = false, length = 11)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PRIORITY", nullable = false, length = 7)
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(name = "STATUS", nullable = false, length = 11)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ESTIMATE", precision = 3, scale = 1)
	public BigDecimal getEstimate() {
		return this.estimate;
	}

	public void setEstimate(BigDecimal estimate) {
		this.estimate = estimate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DUEDATE", nullable = false, length = 10)
	public Date getDuedate() {
		return this.duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
