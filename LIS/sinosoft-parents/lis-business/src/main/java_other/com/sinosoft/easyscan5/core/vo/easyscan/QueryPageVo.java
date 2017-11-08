package com.sinosoft.easyscan5.core.vo.easyscan;

public class QueryPageVo {
	private String docId;
	private String startPage;
	private String endPage;
	private String haveMark;
	private String dataSource;
	private String type;
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getStartPage() {
		return startPage;
	}
	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}
	public String getEndPage() {
		return endPage;
	}
	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}
	public String getHaveMark() {
		return haveMark;
	}
	public void setHaveMark(String haveMark) {
		this.haveMark = haveMark;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
