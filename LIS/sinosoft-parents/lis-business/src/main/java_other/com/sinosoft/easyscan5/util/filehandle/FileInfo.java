package com.sinosoft.easyscan5.util.filehandle;

import java.util.Date;

public class FileInfo {

	private String company;
	private String bussType;
	private String subType;
	private Date createDate;
	private String fileName;
	private String filePath;
	private String itemID;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public Date getCreateDate() {
		return createDate;
	}
	
	public String getItemID() {
		return itemID;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	
}
