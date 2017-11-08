package com.sinosoft.easyscan5.core.vo.easyscan;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.easyscan5.entity.EsQcMain;


public class UploadIssuePrepareVo {
	private String batchKey;
	private String issueNo;
	private String groupNo;
	private String channel;
	private List<EsQcMain> esQcMainList = new ArrayList<EsQcMain>();
	public String getBatchKey() {
		return batchKey;
	}
	public void setBatchKey(String batchKey) {
		this.batchKey = batchKey;
	}
	public String getIssueNo() {
		return issueNo;
	}
	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public List<EsQcMain> getEsQcMainList() {
		return esQcMainList;
	}
	public void setEsQcMainList(List<EsQcMain> esQcMainList) {
		this.esQcMainList = esQcMainList;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
