package com.sinosoft.easyscan5.core.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.easyscan5.entity.EsQcMain;





public class EsIssueDocVO {
	private String issueNo;
	private String issueDesc;
	private String issueDate;
	private String issueType;
	private String issueUser;
	private List<EsQcMain> esQcList = new ArrayList<EsQcMain>();
	private Map<String,ExpandPropertyVo> propMap = new HashMap<String, ExpandPropertyVo>();
	public Map<String, ExpandPropertyVo> getPropMap() {
		return propMap;
	}
	public void setPropMap(Map<String, ExpandPropertyVo> propMap) {
		this.propMap = propMap;
	}
	public String getIssueNo() {
		return issueNo;
	}
	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}
	public List<EsQcMain> getEsQcList() {
		return esQcList;
	}
	public void setEsQcList(List<EsQcMain> esQcList) {
		this.esQcList = esQcList;
	}
	public String getIssueDesc() {
		return issueDesc;
	}
	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	public String getIssueUser() {
		return issueUser;
	}
	public void setIssueUser(String issueUser) {
		this.issueUser = issueUser;
	}
}
