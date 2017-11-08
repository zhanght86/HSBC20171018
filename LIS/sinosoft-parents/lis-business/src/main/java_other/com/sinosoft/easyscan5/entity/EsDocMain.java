package com.sinosoft.easyscan5.entity;

import java.util.Date;

/**
 * EsDocMain entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EsDocMain implements java.io.Serializable {

	// Fields

	private Long docid;
	private String doccode;
	private String busstype;
	private String subtype;
	private Long numpages;
	private String docflag;
	private String docremark;
	private String scanoperator;
	private String managecom;
	private Date inputstate;
	private String operator;
	private Date inputstartdate;
	private String inputstarttime;
	private Date inputenddate;
	private String inputendtime;
	private Date makedate;
	private String maketime;
	private Date modifydate;
	private String modifytime;
	private String version;
	private String scanno;
	private String printcode;
	private Date scanDate;
	private String qcStatus;
	private String docPriority;
	private String tempFlag;
	private String qcOperator;
	private Date qcStartDate;
	private Date qcUploadDate;
	private Date qcFinishDate;
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
	private String batchNo;
	private String fu1;
	private Date fu2;	
	private String scanCom;
	// Constructors

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

	public String getSystemNo() {
		return systemNo;
	}

	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

	/** default constructor */
	public EsDocMain() {
	}

	/** minimal constructor */
	public EsDocMain(Long docid, String doccode, String busstype,
			String subtype, Long numpages, String scanoperator,
			String managecom, String scanno, Date scanDate, String qcStatus,
			String tempFlag, Date createDate, Date updateDate,String systemNo) {
		this.docid = docid;
		this.doccode = doccode;
		this.busstype = busstype;
		this.subtype = subtype;
		this.numpages = numpages;
		this.scanoperator = scanoperator;
		this.managecom = managecom;
		this.scanno = scanno;
		this.scanDate = scanDate;
		this.qcStatus = qcStatus;
		this.tempFlag = tempFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.systemNo = systemNo;
	}

	/** full constructor */
	public EsDocMain(Long docid, String doccode, String busstype,
			String subtype, Long numpages, String docflag, String docremark,
			String scanoperator, String managecom, Date inputstate,
			String operator, Date inputstartdate, String inputstarttime,
			Date inputenddate, String inputendtime, Date makedate,
			String maketime, Date modifydate, String modifytime,
			String version, String scanno, String printcode, Date scanDate,
			String qcStatus, String docPriority, String tempFlag,
			String qcOperator, Date qcStartDate, Date qcUploadDate,
			Date qcFinishDate, String qcErrorinfo, Date createDate,
			Date updateDate, String p1, String p2, String p3, String p4,
			String p5, String p6, String p7, String p8, String p9,
			String expropertyVersion, String basepropertyVersion, String p10,
			String p11, String p12, String p13, String p14, String p15,
			String p16, String p17, String p18, String p19, String p20,String systemNo,
			String scanCom) {
		this.docid = docid;
		this.doccode = doccode;
		this.busstype = busstype;
		this.subtype = subtype;
		this.numpages = numpages;
		this.docflag = docflag;
		this.docremark = docremark;
		this.scanoperator = scanoperator;
		this.managecom = managecom;
		this.inputstate = inputstate;
		this.operator = operator;
		this.inputstartdate = inputstartdate;
		this.inputstarttime = inputstarttime;
		this.inputenddate = inputenddate;
		this.inputendtime = inputendtime;
		this.makedate = makedate;
		this.maketime = maketime;
		this.modifydate = modifydate;
		this.modifytime = modifytime;
		this.version = version;
		this.scanno = scanno;
		this.printcode = printcode;
		this.scanDate = scanDate;
		this.qcStatus = qcStatus;
		this.docPriority = docPriority;
		this.tempFlag = tempFlag;
		this.qcOperator = qcOperator;
		this.qcStartDate = qcStartDate;
		this.qcUploadDate = qcUploadDate;
		this.qcFinishDate = qcFinishDate;
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
	}

	// Property accessors

	public Long getDocid() {
		return this.docid;
	}

	public void setDocid(Long docid) {
		this.docid = docid;
	}

	public String getDoccode() {
		return this.doccode;
	}

	public void setDoccode(String doccode) {
		this.doccode = doccode;
	}

	public String getBusstype() {
		return this.busstype;
	}

	public void setBusstype(String busstype) {
		this.busstype = busstype;
	}

	public String getSubtype() {
		return this.subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public Long getNumpages() {
		return this.numpages;
	}

	public void setNumpages(Long numpages) {
		this.numpages = numpages;
	}

	public String getDocflag() {
		return this.docflag;
	}

	public void setDocflag(String docflag) {
		this.docflag = docflag;
	}

	public String getDocremark() {
		return this.docremark;
	}

	public void setDocremark(String docremark) {
		this.docremark = docremark;
	}

	public String getScanoperator() {
		return this.scanoperator;
	}

	public void setScanoperator(String scanoperator) {
		this.scanoperator = scanoperator;
	}

	public String getManagecom() {
		return this.managecom;
	}

	public void setManagecom(String managecom) {
		this.managecom = managecom;
	}

	public Date getInputstate() {
		return this.inputstate;
	}

	public void setInputstate(Date inputstate) {
		this.inputstate = inputstate;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getInputstartdate() {
		return this.inputstartdate;
	}

	public void setInputstartdate(Date inputstartdate) {
		this.inputstartdate = inputstartdate;
	}

	public String getInputstarttime() {
		return this.inputstarttime;
	}

	public void setInputstarttime(String inputstarttime) {
		this.inputstarttime = inputstarttime;
	}

	public Date getInputenddate() {
		return this.inputenddate;
	}

	public void setInputenddate(Date inputenddate) {
		this.inputenddate = inputenddate;
	}

	public String getInputendtime() {
		return this.inputendtime;
	}

	public void setInputendtime(String inputendtime) {
		this.inputendtime = inputendtime;
	}

	public Date getMakedate() {
		return this.makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String getMaketime() {
		return this.maketime;
	}

	public void setMaketime(String maketime) {
		this.maketime = maketime;
	}

	public Date getModifydate() {
		return this.modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getScanno() {
		return this.scanno;
	}

	public void setScanno(String scanno) {
		this.scanno = scanno;
	}


	public Date getScanDate() {
		return this.scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

	public String getQcStatus() {
		return this.qcStatus;
	}

	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
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

	public String getPrintcode() {
		return printcode;
	}

	public void setPrintcode(String printcode) {
		this.printcode = printcode;
	}

}