package com.sinosoft.easyscan5.entity;

import java.util.Date;

public class EsBatchnoInfo implements java.io.Serializable {

	private String batchNo;
	private String manageCom;
	private String scanOperator;
	private Date createDate;
	
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}
	
	public String getBatchNo(){
		return this.batchNo;
	}
	
	public void setManageCom(String manageCom){
		this.manageCom = manageCom;
	}
	
	public String getManageCom(){
		return this.manageCom;
	}
	
	public void setScanOperator(String scanOperator){
		this.scanOperator = scanOperator;
	}
	
	public String getScanOperator(){
		return this.scanOperator;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	
	public Date getCreateDate(){
		return this.createDate;
	}
	
}