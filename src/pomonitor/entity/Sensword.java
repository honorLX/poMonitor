package pomonitor.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Sensword entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sensword", catalog = "pomonitor")
/*
 * @SecondaryTables(
 * 
 * @SecondaryTable(name = "user") )
 */
public class Sensword implements java.io.Serializable {

	// Fields

	private Integer sensid;
	@ManyToOne(cascade = (CascadeType.ALL))
	@JoinColumn(name = "userid")
	private User user;
	private String senslevel;
	private String sensvalue;

	// Constructors

	/** default constructor */
	public Sensword() {
	}

	/** full constructor */
	public Sensword(User user, String senslevel, String sensvalue) {
		this.user = user;
		this.senslevel = senslevel;
		this.sensvalue = sensvalue;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sensid", unique = true, nullable = false)
	public Integer getSensid() {
		return this.sensid;
	}

	public void setSensid(Integer sensid) {
		this.sensid = sensid;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "senslevel")
	public String getSenslevel() {
		return this.senslevel;
	}

	public void setSenslevel(String senslevel) {
		this.senslevel = senslevel;
	}

	@Column(name = "sensvalue")
	public String getSensvalue() {
		return this.sensvalue;
	}

	public void setSensvalue(String sensvalue) {
		this.sensvalue = sensvalue;
	}

}