package com.sinosoft.easyscan5.entity;

// default package

import java.util.Date;

/**
 * ESServerInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EsServerInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String serverType;
	private String serverNo;
	private String serverName;
	private String serverIp;
	private String visitUserName;
	private String visitPwd;
	private String visitPort;
	private String serverBrand;
	private String serverModel;
	private String serverSystem;
	private String serverPosition;
	private String maintainPerson;
	private String maintainTel;
	private Date useDate;
	private String picPath;
	private String serverBasePath;
	private String url;
	private String delFlag;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private Date p6;
	private Date p7;
	private Long p8;
	private Long p9;
	private String serverDataloadType;

	// Constructors

	/** default constructor */
	public EsServerInfo() {
	}

	/** minimal constructor */
	public EsServerInfo(String id, String serverType, String serverNo,
			String serverName, String serverIp, String delFlag,
			String createUser, Date createDate, String updateUser,
			Date updateDate) {
		this.id = id;
		this.serverType = serverType;
		this.serverNo = serverNo;
		this.serverName = serverName;
		this.serverIp = serverIp;
		this.delFlag = delFlag;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public EsServerInfo(String id, String serverType, String serverNo,
			String serverName, String serverIp, String visitUserName,
			String visitPwd, String visitPort, String serverBrand,
			String serverModel, String serverSystem, String serverPosition,
			String maintainPerson, String maintainTel, Date useDate,
			String picPath, String serverBasePath, String url, String delFlag,
			String createUser, Date createDate, String updateUser,
			Date updateDate, String p1, String p2, String p3, String p4,
			String p5, Date p6, Date p7, Long p8, Long p9,
			String serverDataloadType) {
		this.id = id;
		this.serverType = serverType;
		this.serverNo = serverNo;
		this.serverName = serverName;
		this.serverIp = serverIp;
		this.visitUserName = visitUserName;
		this.visitPwd = visitPwd;
		this.visitPort = visitPort;
		this.serverBrand = serverBrand;
		this.serverModel = serverModel;
		this.serverSystem = serverSystem;
		this.serverPosition = serverPosition;
		this.maintainPerson = maintainPerson;
		this.maintainTel = maintainTel;
		this.useDate = useDate;
		this.picPath = picPath;
		this.serverBasePath = serverBasePath;
		this.url = url;
		this.delFlag = delFlag;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
		this.p6 = p6;
		this.p7 = p7;
		this.p8 = p8;
		this.p9 = p9;
		this.serverDataloadType = serverDataloadType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerType() {
		return this.serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getServerNo() {
		return this.serverNo;
	}

	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return this.serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getVisitUserName() {
		return this.visitUserName;
	}

	public void setVisitUserName(String visitUserName) {
		this.visitUserName = visitUserName;
	}

	public String getVisitPwd() {
		return this.visitPwd;
	}

	public void setVisitPwd(String visitPwd) {
		this.visitPwd = visitPwd;
	}

	public String getVisitPort() {
		return this.visitPort;
	}

	public void setVisitPort(String visitPort) {
		this.visitPort = visitPort;
	}

	public String getServerBrand() {
		return this.serverBrand;
	}

	public void setServerBrand(String serverBrand) {
		this.serverBrand = serverBrand;
	}

	public String getServerModel() {
		return this.serverModel;
	}

	public void setServerModel(String serverModel) {
		this.serverModel = serverModel;
	}

	public String getServerSystem() {
		return this.serverSystem;
	}

	public void setServerSystem(String serverSystem) {
		this.serverSystem = serverSystem;
	}

	public String getServerPosition() {
		return this.serverPosition;
	}

	public void setServerPosition(String serverPosition) {
		this.serverPosition = serverPosition;
	}

	public String getMaintainPerson() {
		return this.maintainPerson;
	}

	public void setMaintainPerson(String maintainPerson) {
		this.maintainPerson = maintainPerson;
	}

	public String getMaintainTel() {
		return this.maintainTel;
	}

	public void setMaintainTel(String maintainTel) {
		this.maintainTel = maintainTel;
	}

	public Date getUseDate() {
		return this.useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getServerBasePath() {
		return this.serverBasePath;
	}

	public void setServerBasePath(String serverBasePath) {
		this.serverBasePath = serverBasePath;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getP1() {
		return this.p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return this.p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return this.p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getP4() {
		return this.p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	public String getP5() {
		return this.p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	public Date getP6() {
		return this.p6;
	}

	public void setP6(Date p6) {
		this.p6 = p6;
	}

	public Date getP7() {
		return this.p7;
	}

	public void setP7(Date p7) {
		this.p7 = p7;
	}

	public Long getP8() {
		return this.p8;
	}

	public void setP8(Long p8) {
		this.p8 = p8;
	}

	public Long getP9() {
		return this.p9;
	}

	public void setP9(Long p9) {
		this.p9 = p9;
	}

	public String getServerDataloadType() {
		return this.serverDataloadType;
	}

	public void setServerDataloadType(String serverDataloadType) {
		this.serverDataloadType = serverDataloadType;
	}

}