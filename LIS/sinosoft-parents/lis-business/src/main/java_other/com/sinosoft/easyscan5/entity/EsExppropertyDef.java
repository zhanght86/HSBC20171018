package com.sinosoft.easyscan5.entity;

import java.util.Date;

/**
 * EsExppropertyDef entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EsExppropertyDef implements java.io.Serializable {

	// Fields

	private String proId;
	private String proType;
	private String proName;
	private String proDictId;
	private String proDefaultVaule;
	private String proVersion;
	private String proValid;
	private String remark;
	private String operUser;
	private Date operDate;

	// Constructors

	/** default constructor */
	public EsExppropertyDef() {
	}

	/** minimal constructor */
	public EsExppropertyDef(String proId, String proType, String proName,
			String operUser, Date operDate) {
		this.proId = proId;
		this.proType = proType;
		this.proName = proName;
		this.operUser = operUser;
		this.operDate = operDate;
	}

	/** full constructor */
	public EsExppropertyDef(String proId, String proType, String proName,
			String proDictId, String proDefaultVaule, String proVersion,
			String proValid, String remark, String operUser, Date operDate) {
		this.proId = proId;
		this.proType = proType;
		this.proName = proName;
		this.proDictId = proDictId;
		this.proDefaultVaule = proDefaultVaule;
		this.proVersion = proVersion;
		this.proValid = proValid;
		this.remark = remark;
		this.operUser = operUser;
		this.operDate = operDate;
	}

	// Property accessors

	public String getProId() {
		return this.proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProType() {
		return this.proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProDictId() {
		return this.proDictId;
	}

	public void setProDictId(String proDictId) {
		this.proDictId = proDictId;
	}

	public String getProDefaultVaule() {
		return this.proDefaultVaule;
	}

	public void setProDefaultVaule(String proDefaultVaule) {
		this.proDefaultVaule = proDefaultVaule;
	}

	public String getProVersion() {
		return this.proVersion;
	}

	public void setProVersion(String proVersion) {
		this.proVersion = proVersion;
	}

	public String getProValid() {
		return this.proValid;
	}

	public void setProValid(String proValid) {
		this.proValid = proValid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperUser() {
		return this.operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public Date getOperDate() {
		return this.operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

}