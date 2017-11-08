



package com.sinosoft.productdef;

import java.util.LinkedList;
import java.util.List;

//import com.sinosoft.service.pdfmaker.style.pdf.TableHeadStyle;

public class XmlTable<k> {
	private List<k> headers;
//	private TableHeadStyle tableHeadStyle;
	
	public XmlTable() {
		super();
		this.headers = new LinkedList<k>();
//		this.tableHeadStyle = new TableHeadStyle();
	}
//	public XmlTable(List<k> headers, TableHeadStyle tableHeadStyle) {
//		super();
//		this.headers = headers;
////		this.tableHeadStyle = tableHeadStyle;
//	}
	public List<k> getHeaders() {
		return headers;
	}
	public void setHeaders(List<k> headers) {
		this.headers = headers;
	}
//	public TableHeadStyle getTableHeadStyle() {
//		return tableHeadStyle;
//	}
//	public void setTableHeadStyle(TableHeadStyle tableHeadStyle) {
//		this.tableHeadStyle = tableHeadStyle;
//	}
}


