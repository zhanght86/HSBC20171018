package com.sinosoft.easyscan5.core.vo.easyscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.easyscan5.entity.EsQcMain;



public class UploadIssueIndexVo {
	private String batchKey;
	private String issueNo;
	private String groupNo;
	private String channel;
	private List<EsQcMain> esQcMainList = new ArrayList<EsQcMain>();
	private Map<String,List> esQcPagesMap = new HashMap<String, List>();
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
	public Map<String, List> getEsQcPagesMap() {
		return esQcPagesMap;
	}
	public void setEsQcPagesMap(Map<String, List> esQcPagesMap) {
		this.esQcPagesMap = esQcPagesMap;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
