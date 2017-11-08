package com.sinosoft.easyscan5.core.vo.easyscan;

public class GetScanNoVo {

	private String manageCom;
	private String channel;	
	private String groupNo;
	private String bussType;
	private String scanUser;
	private String subType;
	private String startDate;
	private String endDate;
	private String scanNo;
	public String getScanNo() {
		return scanNo;
	}
	public void setScanNo(String scanNo) {
		this.scanNo = scanNo;
	}
	public String getManageCom() {
		return manageCom;
	}
	public void setManageCom(String manageCom) {
		this.manageCom = manageCom;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getScanUser() {
		return scanUser;
	}
	public void setScanUser(String scanUser) {
		this.scanUser = scanUser;
	}
}
