package com.sinosoft.easyscan5.entity;

import java.util.Date;


/**
 * EsDocDef entity. @author MyEclipse Persistence Tools
 */

public class EsDocDef implements java.io.Serializable {

	// Fields

	private EsDocDefId id;
	private String bussTypeName;
	private String subtypeName;
	private String validFlag;
	private String codeFlag;
	private String passFlag;
	private String docType;
	private String codeLen;
	private String upFileType;
	private String saveColorMode;
	private String scanSettingCode;
	private String jpgQuality;
	private String fileSaveType;
	private String docPriority;
	private String numPages;
	private String isSign;
	private String docVersion;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private String p6;
	private String p7;
	private String p8;
	private String p9;
	private String reupload;
	private String isUploadDirectly;
	private String keyword;

	// Constructors

	
	public String getIsUploadDirectly() {
		return isUploadDirectly;
	}

	public void setIsUploadDirectly(String isUploadDirectly) {
		this.isUploadDirectly = isUploadDirectly;
	}

	/** default constructor */
	public EsDocDef() {
	}

	/** minimal constructor */
	public EsDocDef(EsDocDefId id, String validFlag, String docType,
			String createUser, Date createDate, String updateUser,
			Date updateDate) {
		this.id = id;
		this.validFlag = validFlag;
		this.docType = docType;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public EsDocDef(EsDocDefId id, String bussTypeName, String subTypeName,
			String validFlag, String codeFlag, String passFlag, String docType,
			String codeLen, String upFileType, String saveColorMode,
			String scanSettingCode, String jpgQuality, String fileSaveType,
			String docPriority, String numPages, String isSign,
			String docVersion, String createUser, Date createDate,
			String updateUser, Date updateDate, String p1, String p2,
			String p3, String p4, String p5, String p6, String p7, String p8,
			String p9, String reupload,String isUploadDirectly) {
		this.id = id;
		this.bussTypeName = bussTypeName;
		this.subtypeName = subTypeName;
		this.validFlag = validFlag;
		this.codeFlag = codeFlag;
		this.passFlag = passFlag;
		this.docType = docType;
		this.codeLen = codeLen;
		this.upFileType = upFileType;
		this.saveColorMode = saveColorMode;
		this.scanSettingCode = scanSettingCode;
		this.jpgQuality = jpgQuality;
		this.fileSaveType = fileSaveType;
		this.docPriority = docPriority;
		this.numPages = numPages;
		this.isSign = isSign;
		this.docVersion = docVersion;
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
		this.reupload = reupload;
		this.isUploadDirectly = isUploadDirectly;
	}

	// Property accessors

	public EsDocDefId getId() {
		return this.id;
	}

	public void setId(EsDocDefId id) {
		this.id = id;
	}

	public String getBussTypeName() {
		return this.bussTypeName;
	}

	public void setBussTypeName(String bussTypeName) {
		this.bussTypeName = bussTypeName;
	}
	
	public String getSubtypeName() {
		return subtypeName;
	}

	public void setSubtypeName(String subtypeName) {
		this.subtypeName = subtypeName;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getCodeFlag() {
		return this.codeFlag;
	}

	public void setCodeFlag(String codeFlag) {
		this.codeFlag = codeFlag;
	}

	public String getPassFlag() {
		return this.passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getCodeLen() {
		return this.codeLen;
	}

	public void setCodeLen(String codeLen) {
		this.codeLen = codeLen;
	}

	public String getUpFileType() {
		return this.upFileType;
	}

	public void setUpFileType(String upFileType) {
		this.upFileType = upFileType;
	}

	public String getSaveColorMode() {
		return this.saveColorMode;
	}

	public void setSaveColorMode(String saveColorMode) {
		this.saveColorMode = saveColorMode;
	}

	public String getScanSettingCode() {
		return this.scanSettingCode;
	}

	public void setScanSettingCode(String scanSettingCode) {
		this.scanSettingCode = scanSettingCode;
	}

	public String getJpgQuality() {
		return this.jpgQuality;
	}

	public void setJpgQuality(String jpgQuality) {
		this.jpgQuality = jpgQuality;
	}

	public String getFileSaveType() {
		return this.fileSaveType;
	}

	public void setFileSaveType(String fileSaveType) {
		this.fileSaveType = fileSaveType;
	}

	public String getDocPriority() {
		return this.docPriority;
	}

	public void setDocPriority(String docPriority) {
		this.docPriority = docPriority;
	}

	public String getNumPages() {
		return this.numPages;
	}

	public void setNumPages(String numPages) {
		this.numPages = numPages;
	}

	public String getIsSign() {
		return this.isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getDocVersion() {
		return this.docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
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

	public String getP6() {
		return this.p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return this.p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	public String getP8() {
		return this.p8;
	}

	public void setP8(String p8) {
		this.p8 = p8;
	}

	public String getP9() {
		return this.p9;
	}

	public void setP9(String p9) {
		this.p9 = p9;
	}

	public String getReupload() {
		return this.reupload;
	}

	public void setReupload(String reupload) {
		this.reupload = reupload;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}