package com.sinosoft.easyscan5.entity;

import java.util.Date;

/**
 * EsQcMainHis entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EsQcMainHis implements java.io.Serializable {

	// Fields

	private String keyId;
	private String docId;
	private String docCode;
	private String bussType;
	private String subType;
	private String qcStatus;
	private Long numPages;
	private String docFlag;
	private String docRemark;
	private String scanOperator;
	private Date scanDate;
	private String manageCom;
	private String scanNo;
	private String docPriority;
	private String tempFlag;
	private String docVersion;
	private String qcOperator;
	private Date qcStartDate;
	private Date qcUploadDate;
	private Date qcFinishDate;
	private String qcLockFlag;
	private String qcErrorinfo;
	private Date createDate;
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
	private String expropertyVersion;
	private String basepropertyVersion;
	private String p10;
	private String p11;
	private String p12;
	private String p13;
	private String p14;
	private String p15;
	private String p16;
	private String p17;
	private String p18;
	private String p19;
	private String p20;
	private String systemNo;
	private String groupNo;
	private String channel;
	private String printCode;
	private String batchNo;
	private String fu1;
	private Date fu2;	
	private String scanCom;
	// Constructors	

	
	public EsQcMainHis() {
	}

	/** minimal constructor */
	public EsQcMainHis(String keyId, String docId, String docCode,
			String bussType, String subType, String qcStatus, Long numPages,
			String docFlag, String scanOperator, Date scanDate,
			String manageCom, String scanNo, String tempFlag, Date createDate,
			Date updateDate, String systemNo) {
		this.keyId = keyId;
		this.docId = docId;
		this.docCode = docCode;
		this.bussType = bussType;
		this.subType = subType;
		this.qcStatus = qcStatus;
		this.numPages = numPages;
		this.docFlag = docFlag;
		this.scanOperator = scanOperator;
		this.scanDate = scanDate;
		this.manageCom = manageCom;
		this.scanNo = scanNo;
		this.tempFlag = tempFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.systemNo = systemNo;
	}

	/** full constructor */
	public EsQcMainHis(String keyId, String docId, String docCode,
			String bussType, String subType, String qcStatus, Long numPages,
			String docFlag, String docRemark, String scanOperator,
			Date scanDate, String manageCom, String scanNo,
			String docPriority, String tempFlag, String docVersion,
			String qcOperator, Date qcStartDate, Date qcUploadDate,
			Date qcFinishDate, String qcLockFlag, String qcErrorinfo,
			Date createDate, Date updateDate, String p1, String p2, String p3,
			String p4, String p5, String p6, String p7, String p8, String p9,
			String expropertyVersion, String basepropertyVersion, String p10,
			String p11, String p12, String p13, String p14, String p15,
			String p16, String p17, String p18, String p19, String p20, String systemNo,
			String scanCom,String groupNo,String channel,String printCode,String batchNo,
			String fu1,Date fu2) {
		this.keyId = keyId;
		this.docId = docId;
		this.docCode = docCode;
		this.bussType = bussType;
		this.subType = subType;
		this.qcStatus = qcStatus;
		this.numPages = numPages;
		this.docFlag = docFlag;
		this.docRemark = docRemark;
		this.scanOperator = scanOperator;
		this.scanDate = scanDate;
		this.manageCom = manageCom;
		this.scanNo = scanNo;
		this.docPriority = docPriority;
		this.tempFlag = tempFlag;
		this.docVersion = docVersion;
		this.qcOperator = qcOperator;
		this.qcStartDate = qcStartDate;
		this.qcUploadDate = qcUploadDate;
		this.qcFinishDate = qcFinishDate;
		this.qcLockFlag = qcLockFlag;
		this.qcErrorinfo = qcErrorinfo;
		this.createDate = createDate;
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
		this.expropertyVersion = expropertyVersion;
		this.basepropertyVersion = basepropertyVersion;
		this.p10 = p10;
		this.p11 = p11;
		this.p12 = p12;
		this.p13 = p13;
		this.p14 = p14;
		this.p15 = p15;
		this.p16 = p16;
		this.p17 = p17;
		this.p18 = p18;
		this.p19 = p19;
		this.p20 = p20;
		this.systemNo = systemNo;
		this.scanCom = scanCom;
		this.batchNo = batchNo;
		this.channel = channel;
		this.groupNo = groupNo;
		this.printCode = printCode;
		this.fu1 = fu1;
		this.fu2 = fu2;
	}

	public EsQcMainHis(EsDocMain esDocMain){
		this.docId = esDocMain.getDocid().toString();
		this.docCode = esDocMain.getDoccode();
		this.bussType = esDocMain.getBusstype();
		this.subType = esDocMain.getSubtype();
		this.qcStatus = esDocMain.getQcStatus();
		this.numPages = esDocMain.getNumpages();
		this.docFlag = esDocMain.getDocflag();
		this.docRemark = esDocMain.getDocremark();
		this.scanOperator = esDocMain.getScanoperator();
		this.scanDate = esDocMain.getScanDate();
		this.manageCom = esDocMain.getManagecom();
		this.scanNo = esDocMain.getScanno();
		this.docPriority = esDocMain.getDocPriority();
		this.tempFlag = esDocMain.getTempFlag();
		this.qcOperator = esDocMain.getQcOperator();
		this.qcStartDate = esDocMain.getQcStartDate();
		this.qcUploadDate = esDocMain.getQcUploadDate();
		this.qcFinishDate = esDocMain.getQcFinishDate();
		this.qcErrorinfo = esDocMain.getQcErrorinfo();
		this.createDate = esDocMain.getCreateDate();
		this.updateDate = esDocMain.getUpdateDate();
		this.p1 = esDocMain.getP1();
		this.p2 = esDocMain.getP2();
		this.p3 = esDocMain.getP3();
		this.p4 = esDocMain.getP4();
		this.p5 = esDocMain.getP5();
		this.p6 = esDocMain.getP6();
		this.p7 = esDocMain.getP7();
		this.p8 = esDocMain.getP8();
		this.p9 = esDocMain.getP9();
		this.expropertyVersion = esDocMain.getExpropertyVersion();
		this.basepropertyVersion = esDocMain.getBasepropertyVersion();
		this.p10 = esDocMain.getP10();
		this.p11 = esDocMain.getP11();
		this.p12 = esDocMain.getP12();
		this.p13 = esDocMain.getP13();
		this.p14 = esDocMain.getP14();
		this.p15 = esDocMain.getP15();
		this.p16 = esDocMain.getP16();
		this.p17 = esDocMain.getP17();
		this.p18 = esDocMain.getP18();
		this.p19 = esDocMain.getP19();
		this.p20 = esDocMain.getP20();
		this.systemNo = esDocMain.getSystemNo();
		this.scanCom = esDocMain.getScanCom();
		this.channel = esDocMain.getChannel();
		this.groupNo = esDocMain.getGroupNo();
		this.batchNo = esDocMain.getBatchNo();
		this.printCode = esDocMain.getPrintcode();
	}
	
	// Property accessors

	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocCode() {
		return this.docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getBussType() {
		return this.bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getSubType() {
		return this.subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getQcStatus() {
		return this.qcStatus;
	}

	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}

	public Long getNumPages() {
		return this.numPages;
	}

	public void setNumPages(Long numPages) {
		this.numPages = numPages;
	}

	public String getDocFlag() {
		return this.docFlag;
	}

	public void setDocFlag(String docFlag) {
		this.docFlag = docFlag;
	}

	public String getDocRemark() {
		return this.docRemark;
	}

	public void setDocRemark(String docRemark) {
		this.docRemark = docRemark;
	}

	public String getScanOperator() {
		return this.scanOperator;
	}

	public void setScanOperator(String scanOperator) {
		this.scanOperator = scanOperator;
	}

	public Date getScanDate() {
		return this.scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

	public String getManageCom() {
		return this.manageCom;
	}

	public void setManageCom(String manageCom) {
		this.manageCom = manageCom;
	}

	public String getScanNo() {
		return this.scanNo;
	}

	public void setScanNo(String scanNo) {
		this.scanNo = scanNo;
	}

	public String getDocPriority() {
		return this.docPriority;
	}

	public void setDocPriority(String docPriority) {
		this.docPriority = docPriority;
	}

	public String getTempFlag() {
		return this.tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public String getDocVersion() {
		return this.docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public String getQcOperator() {
		return this.qcOperator;
	}

	public void setQcOperator(String qcOperator) {
		this.qcOperator = qcOperator;
	}

	public Date getQcStartDate() {
		return this.qcStartDate;
	}

	public void setQcStartDate(Date qcStartDate) {
		this.qcStartDate = qcStartDate;
	}

	public Date getQcUploadDate() {
		return this.qcUploadDate;
	}

	public void setQcUploadDate(Date qcUploadDate) {
		this.qcUploadDate = qcUploadDate;
	}

	public Date getQcFinishDate() {
		return this.qcFinishDate;
	}

	public void setQcFinishDate(Date qcFinishDate) {
		this.qcFinishDate = qcFinishDate;
	}

	public String getQcLockFlag() {
		return this.qcLockFlag;
	}

	public void setQcLockFlag(String qcLockFlag) {
		this.qcLockFlag = qcLockFlag;
	}

	public String getQcErrorinfo() {
		return this.qcErrorinfo;
	}

	public void setQcErrorinfo(String qcErrorinfo) {
		this.qcErrorinfo = qcErrorinfo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getExpropertyVersion() {
		return this.expropertyVersion;
	}

	public void setExpropertyVersion(String expropertyVersion) {
		this.expropertyVersion = expropertyVersion;
	}

	public String getBasepropertyVersion() {
		return this.basepropertyVersion;
	}

	public void setBasepropertyVersion(String basepropertyVersion) {
		this.basepropertyVersion = basepropertyVersion;
	}

	public String getP10() {
		return this.p10;
	}

	public void setP10(String p10) {
		this.p10 = p10;
	}

	public String getP11() {
		return this.p11;
	}

	public void setP11(String p11) {
		this.p11 = p11;
	}

	public String getP12() {
		return this.p12;
	}

	public void setP12(String p12) {
		this.p12 = p12;
	}

	public String getP13() {
		return this.p13;
	}

	public void setP13(String p13) {
		this.p13 = p13;
	}

	public String getP14() {
		return this.p14;
	}

	public void setP14(String p14) {
		this.p14 = p14;
	}

	public String getP15() {
		return this.p15;
	}

	public void setP15(String p15) {
		this.p15 = p15;
	}

	public String getP16() {
		return this.p16;
	}

	public void setP16(String p16) {
		this.p16 = p16;
	}

	public String getP17() {
		return this.p17;
	}

	public void setP17(String p17) {
		this.p17 = p17;
	}

	public String getP18() {
		return this.p18;
	}

	public void setP18(String p18) {
		this.p18 = p18;
	}

	public String getP19() {
		return this.p19;
	}

	public void setP19(String p19) {
		this.p19 = p19;
	}

	public String getP20() {
		return this.p20;
	}

	public void setP20(String p20) {
		this.p20 = p20;
	}

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}
	public String getScanCom() {
		return scanCom;
	}

	public void setScanCom(String scanCom) {
		this.scanCom = scanCom;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPrintCode() {
		return printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getFu1() {
		return fu1;
	}

	public void setFu1(String fu1) {
		this.fu1 = fu1;
	}

	public Date getFu2() {
		return fu2;
	}

	public void setFu2(Date fu2) {
		this.fu2 = fu2;
	}
}