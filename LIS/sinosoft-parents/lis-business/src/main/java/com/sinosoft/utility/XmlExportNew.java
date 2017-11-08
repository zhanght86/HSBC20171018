/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sinosoft.lis.f1print.FileNameQueue;

/**打印数据存放类
 * @author sinosoft
 *
 */
public class XmlExportNew {
	private static Logger logger = Logger.getLogger(XmlExportNew.class);

	private Document myDocument = DocumentHelper.createDocument();

	private int col;

	/**
	 * 表头名称
	 */
	private final String TAG_NAME_HEAD = "HEAD";

	/**
	 * 行名称
	 */
	private final String TAG_NAME_ROW = "R";

	/**
	 * 列名称
	 */
	private final String TAG_NAME_COL = "C";

	/**
	 * 创建方式，与xmlexport区别
	 */
	public static final String TAG_CREATE_TYPE ="XmlExportNew";
	
	public XmlExportNew() {
	}

	/**初始化文件，设置打印名称
	 * @param tPrintName
	 * @return
	 */
	public Document createDocument(String tPrintName){
		return createDocument(tPrintName,"","","");
	}
	
	// 初始化文件，参数为模板名，打印机名
	public Document createDocument(String cPrintName, String cTemplateName,
			String cPrintType, String cOutputType) {
		logger.debug("PrintName:" + cPrintName + ",TemplateName:"
				+ cTemplateName + ",PrintType:" + cPrintType + ",OutputType:"
				+ cOutputType);
		// Create the root element
		// TemplateName=templatename;
		Element DataSetElement = myDocument.addElement("DATASET");
		// add some child elements

		// Note that this is the first approach to adding an element and
		// textual content. The second approach is commented out.

		Element tControl = DataSetElement.addElement("CONTROL");
		Element TEMPLATE = tControl.addElement("TEMPLATE");
		TEMPLATE.setText(cTemplateName);
		Element tPrintType = tControl.addElement("PrintType");
		tPrintType.setText(cPrintName);
		Element tPrintName = tControl.addElement("PrintName");
		tPrintName.setText(cPrintName);
		Element tDisplay = tControl.addElement("DISPLAY");

		Element tObejct = tDisplay.addElement("Obejct");
		tObejct.setText("0");
		Element tUserLanguage = tDisplay.addElement("UserLanguage");
		tUserLanguage.setText("zh");
		Element tSysLanguage = tDisplay.addElement("SysLanguage");
		tSysLanguage.setText("");
		Element tOutputType = tDisplay.addElement("OutputType");
		tOutputType.setText(cOutputType);
		Element tCreateType = tDisplay.addElement("CreateType");
		tCreateType.setText(TAG_CREATE_TYPE);//创建的类名，为了与原有方式区分开
		return myDocument;
	}

	public Document getDocument() {
		return this.myDocument;
	}

	/**
	 * 生成文件到指定目录 2008.09.11
	 * 
	 * @param filename
	 * @author nicole
	 */
	public void outputDocumentToFile() {
		ExeSQL tExeSQL = new ExeSQL();
		String SQL = " select a.Sysvarvalue from Ldsysvar a where a.Sysvar='PrintXmlFilePath' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(SQL);
		String filePath = tExeSQL.getOneValue(sqlbv);
		if (filePath == null) {
			return;
		}
		// String filename = PubFun.getCurrentDate().replaceAll("-", "") +
		// PubFun.getCurrentTime2().replaceAll(":", "");
		// outputDocumentToFile(filePath + "//", filename);
	}

	// 输出xml文件，参数为路径，文件名
	public void outputDocumentToFile(String pathname, String filename) {
		outputDocumentToFile(pathname, filename, true);
	}

	// 输出xml文件，参数为路径，文件名,是否需要改名，文件名后缀
	public void outputDocumentToFile(String pathname, String filename,
			boolean isNeedRename) {
		XMLWriter xmlWriter = new XMLWriter();
		;
		FileOutputStream out = null;
		// setup this like outputDocument
		try {
			// output to a file
			String str = pathname + filename;
			if (isNeedRename)
				str += FileNameQueue.XML_PRE;
			else
				str += FileNameQueue.XML;
			out = new FileOutputStream(str);
			xmlWriter.setOutputStream(out);
			xmlWriter.write(myDocument);
			out.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			if (isNeedRename)
				FileNameQueue.rename(pathname, filename);
		}

	}

	// 添加一个列表，参数为ListTag和动态列表的表头数组
	public Document addListTableToEle(Element EleName, ListTable listtable,
			String[] colvalue) {
		this.col = colvalue.length;
		Element DataSetElement = EleName;
		Element table = DataSetElement.addElement(listtable.getName())
				.addAttribute("RowCount", String.valueOf(listtable.size()))
				.addAttribute("ColCount", String.valueOf(colvalue.length));
		Element head = table.addElement(this.TAG_NAME_HEAD);
		// 建立表头名
		for (int m = 0; m < colvalue.length; m++) {
			int n = m + 1;
			String colnum = this.TAG_NAME_COL + n;
			addElementWithValue(head, colnum, colvalue[m]);
		}

		// 遍历整个table
		for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
			String[] temparray = new String[this.col];
			temparray = listtable.get(i);
			Element row = table.addElement(this.TAG_NAME_ROW);
			for (int m = 0; m < temparray.length; m++) {
				int n = m + 1;
				String colnum = this.TAG_NAME_COL + n;
				addElementWithValue(row, colnum, temparray[m]);
			}
		}
		return myDocument;
	}

	// 添加一个列表，参数为ListTag和动态列表的表头数组
	public Document addListTable(ListTable listtable, String[] colvalue) {
		this.col = colvalue.length;
		Element DataSetElement = this.myDocument.getRootElement();
		Element table = DataSetElement.addElement(listtable.getName())
				.addAttribute("RowCount", String.valueOf(listtable.size()))
				.addAttribute("ColCount", String.valueOf(colvalue.length));
		Element head = table.addElement(this.TAG_NAME_HEAD);
		// 建立表头名
		for (int m = 0; m < colvalue.length; m++) {
			int n = m + 1;
			String colnum = this.TAG_NAME_COL + n;
			addElementWithValue(head, colnum, colvalue[m]);
		}

		// 遍历整个table
		String[] temparray;
		for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
			temparray = new String[this.col];
			temparray = listtable.get(i);
			Element row = table.addElement(this.TAG_NAME_ROW);
			for (int m = 0; m < temparray.length; m++) {
				int n = m + 1;
				String colnum = this.TAG_NAME_COL + n;
				addElementWithValue(row, colnum, temparray[m]);
			}
		}
		return myDocument;
	}

	// 添加一个列表，参数为ListTag和动态列表的表头数组
	public Document addListTable(ListTable listtable, String[] colvalue,
			String cRowName) {
		this.col = colvalue.length;
		Element DataSetElement = this.myDocument.getRootElement();
		Element table = DataSetElement.addElement(listtable.getName())
				.addAttribute("RowCount", String.valueOf(listtable.size()))
				.addAttribute("ColCount", String.valueOf(colvalue.length));
		// 遍历整个table

		for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
			String[] temparray = new String[this.col];
			temparray = listtable.get(i);
			addListTableToElement(table, temparray, colvalue, cRowName);
		}
		return myDocument;
	}

	/**
	 * 添加一个节点rowname，再将colTitle以及对应值加到rowname节点下
	 * 
	 * @param element
	 *            在此节点下添加一个节点
	 * @param colValue
	 *            节点的各个值
	 * @param colTitle
	 *            节点的各个名称
	 * @param rowName
	 *            节点名
	 * @return
	 */
	public Document addListTableToElement(Element element, String[] colValue,
			String[] colTitle, String rowName) {
		Element DataSetElement = element;
		Element table = DataSetElement.addElement(rowName).addAttribute(
				"RowCount", "1").addAttribute("ColCount",
				String.valueOf(colTitle.length));
		for (int m = 0; m < colValue.length; m++) {
			String colnum = colTitle[m];
			addElementWithValue(table, colnum, colValue[m]);
		}
		return myDocument;
	}

	// 添加一个列表，参数为ListTag和动态列表的表头数组
	public Document addListTables(ListTable listtable, String[] colvalue,
			TextTag texttag) {
		Element DataSetElements = this.myDocument.getRootElement();
		Element DataSetElement = DataSetElements.addElement("DATASET");
		// 添加动态文本标签的数组，参数为一个TextTag
		int tagsize = texttag.size();
		for (int i = 0; i <= tagsize - 1; i++) {
			String[] temparray = new String[2];
			temparray = (String[]) texttag.get(i);
			if (temparray[1].length() > 0) {
				DataSetElement.addElement(temparray[0]).setText(temparray[1]);
			} else {
				DataSetElement.addElement(temparray[0]).setText(" ");
			}
		}

		if (listtable != null && listtable.size() > 0) {
			this.col = colvalue.length;
			Element table = DataSetElement.addElement(listtable.getName());
			Element head = table.addElement(this.TAG_NAME_HEAD);
			// 建立表头名
			for (int m = 0; m < colvalue.length; m++) {
				int n = m + 1;
				String colnum = this.TAG_NAME_COL + n;
				addElementWithValue(head, colnum, colvalue[m]);
			}
			// 遍历整个table
			for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
				String[] temparray = new String[this.col];
				temparray = listtable.get(i);
				Element row = table.addElement(this.TAG_NAME_ROW);
				for (int m = 0; m < temparray.length; m++) {
					int n = m + 1;
					String colnum = this.TAG_NAME_COL + n;
					addElementWithValue(row, colnum, temparray[m]);
				}
			}
		}
		return myDocument;
	}

	// 添加动态文本标签的数组，参数为一个TextTag
	public Document addTextTag(TextTag texttag) {
		Element DataSetElement = this.myDocument.getRootElement();
		int tagsize = texttag.size();
		for (int i = 0; i <= tagsize - 1; i++) {
			String[] temparray = new String[2];
			temparray = (String[]) texttag.get(i);
			if (temparray[1].length() > 0) {
				DataSetElement.addElement(temparray[0]).setText(temparray[1]);
			} else {
				DataSetElement.addElement(temparray[0]).setText(" ");
			}
		}
		return myDocument;
	}

	// 添加Map的内容，参数为一个Map
	public Document addMap(Map map) {
		Element DataSetElement = this.myDocument.getRootElement();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Entry en = (Entry) it.next();
			DataSetElement.addElement((String) en.getKey()).setText(
					(String) en.getValue());
		}
		return myDocument;
	}

	/*
	 * 直接从文档中产生一个输入流对象，而不是生成一个临时文件 输出： 一个输入流对象
	 */
	public InputStream getInputStream() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputFormat format = OutputFormat.createCompactFormat();
			format.setEncoding("GBK");
			format.setNewLineAfterDeclaration(false);
			@SuppressWarnings("unused")
			XMLWriter output = new XMLWriter(baos, format);
			output.write(myDocument);
			baos.close();
			output.close();
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 2005-4-28 往文档理添加文档，即：把DataSet添加到DataSets中.
	 * 
	 * @param parentElement
	 *            Element
	 * @param dataSet
	 *            Element
	 */
	public void addDataSet(Element parentElement, Element dataSet) {
		parentElement.add((Element) dataSet.clone());
	}

	public void addDataSet(Element dataSet) {
		this.getDocument().getRootElement().add((Element) dataSet.clone());
	}

	public void setTemplateName(Element parentElement, Element dataSet) {
		myDocument.getRootElement().element("CONTROL").element("TEMPLATE")
				.setText(
						dataSet.element("CONTROL").element("TEMPLATE")
								.getText());
	}

	// 设置模板名称
	public void setTemplateName(String templateName) {
		myDocument.getRootElement().element("CONTROL").element("TEMPLATE")
				.setText(templateName);
	}

	// 设置打印机名
	public void setPrinterName(String printerName) {
		myDocument.getRootElement().element("CONTROL").element("PRINTER")
				.setText(printerName);
	}

	/**
	 * 设置模板打印语言（按用户语言）
	 */
	public void setUserLanguage(String pLanguage) {
		myDocument.getRootElement().element("CONTROL").element("DISPLAY")
				.element("UserLanguage").setText(pLanguage);
	}

	/**
	 * 设置模板打印语言（按界面语言）
	 */
	public void setSysLanguage(String pLanguage) {
		myDocument.getRootElement().element("CONTROL").element("DISPLAY")
				.element("SysLanguage").setText(pLanguage);
	}

	/**
	 * 2002-11-11 kevin 在数据xml文件中加入一个显示控制数据
	 * 
	 * @param strName
	 *            String
	 * @return Document
	 */
	public Document addDisplayControl(String strName) {
		Element elementControl = this.myDocument.getRootElement().element(
				"CONTROL").element("DISPLAY");
		elementControl.addElement(strName).setText("1");
		return myDocument;
	}

	public Document createTitle(String cTitle) {
		Element tElement = this.myDocument.getRootElement();
		tElement.addElement(cTitle);
		return myDocument;
	}

	public Document addElement(String cTitle, String cElement,
			String cElementValue) {
		Element tElement = this.myDocument.getRootElement().element(cTitle);
		return addElementWithValue(tElement, cElement, cElementValue);
	}

	public Document addElement(String cElement, String cElementValue) {
		return addElementWithValue(this.myDocument.getRootElement(), cElement,
				cElementValue);
	}

	public Document addAttribute(String cElementName, String cAttributeName,
			String cAttributeValue) {
		Element tElement = this.myDocument.getRootElement().element(
				cElementName);
		tElement.addAttribute(cAttributeName, cAttributeValue);
		return myDocument;
	}

	/**
	 * 2002-11-11 kevin 在数据xml文件的某一项中加入一个新项
	 * 
	 * @param strRiskNo
	 *            String
	 * @return Document
	 */
	public Document addControlItem(String item, String name, String value) {
		Element elementControl = this.myDocument.getRootElement().element(item);
		return addElementWithValue(elementControl, name, value);
	}

	private Document addElementWithValue(Element element, String name,
			String value) {
		if (value == null)
			value = "";
		element.addElement(name).setText(value);
		return myDocument;
	}

	public static void main(String args[]) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("RequestIP", "100.105.190.141");
		tTransferData.setNameAndValue("PrintBranchCode", "86210000");
		XmlExportNew xmlexport = new XmlExportNew();
		xmlexport.createDocument("体检通知书", "PrtAppEndorsement.vts", "printer",
				"1");
		xmlexport.createTitle("Company");
		xmlexport.addAttribute("Company", "ele", "33");
		xmlexport.addElement("Company", "ManageCom", "86510101");
		xmlexport.addElement("Company", "ManageName", "安邦人寿成都新都支公司营销服务一部");
		xmlexport.addElement("Company", "Address", "");

		xmlexport.createTitle("Medical");
		xmlexport.addElement("Medical", "CheckItem", "物理检查  尿常规  心电图    ");
		xmlexport.addElement("Medical", "CheckReason", "达标体检");
		xmlexport.addElement("Medical", "NeedLimosis", "");

		xmlexport.addElement("PrintDate", "2011年01月20日");
		xmlexport.addAttribute("PrintDate", "type", "date");
		xmlexport.addAttribute("PrintDate", "format", "yyyy-mm-dd");

		ListTable tListTable = new ListTable();
		String[] tTitle = new String[3];
		tTitle[0] = "Name";
		tTitle[1] = "Sex";
		tTitle[2] = "Phone";
		tListTable.setName("AppntInfo");

		String[] StrCont;
		for (int j = 1; j <= 2; j++) {
			StrCont = new String[3];
			StrCont[0] = "0" + j;
			StrCont[1] = "1" + j;
			StrCont[2] = "2" + j;
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "Appnt");

		xmlexport.outputDocumentToFile("E:/", "test", false);
	}
}
