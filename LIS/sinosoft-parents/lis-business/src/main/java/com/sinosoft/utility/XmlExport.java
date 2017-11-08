/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sinosoft.lis.f1print.FileNameQueue;
import com.sinosoft.lis.pubfun.GlobalInput;

public class XmlExport implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(XmlExport.class);

	private Document myDocument;

	private int col;

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

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

	// @Method
	// 初始化文件，参数为模板名，打印机名
	public Document createDocument(String templatename, String printername) {
		// Create the root element
		// TemplateName=templatename;
		logger.debug("TemplateName:" + templatename + ",PrinterName:"
				+ printername);
		myDocument = DocumentHelper.createDocument();// create the document
		Element DataSetElement = myDocument.addElement("DATASET");// add some
		// child
		// elements

		// Note that this is the first approach to adding an element and
		// textual content. The second approach is commented out.

		Element CONTROL = DataSetElement.addElement("CONTROL");

		Element TEMPLATE = CONTROL.addElement("TEMPLATE");
		Element PRINTER = CONTROL.addElement("PRINTER");
		PRINTER.setText(printername);
		TEMPLATE.setText(templatename);

		CONTROL.addElement("DISPLAY");
		return myDocument;
	}

	public Document createDocument(String rootName, String templatename,
			String printername) {
		// Create the root element
		// TemplateName=templatename;
		logger.debug("TemplateName:" + templatename + ",PrinterName:"
				+ printername + ",RootName:" + rootName);
		myDocument = DocumentHelper.createDocument();// create the document
		Element DataSetElement = myDocument.addElement("DATASET");// add some
		// child
		// elements

		// Note that this is the first approach to adding an element and
		// textual content. The second approach is commented out.

		Element CONTROL = DataSetElement.addElement("CONTROL");
		Element TEMPLATE = CONTROL.addElement("TEMPLATE");
		Element PRINTER = CONTROL.addElement("PRINTER");
		PRINTER.setText(printername);
		TEMPLATE.setText(templatename);

		CONTROL.addElement("DISPLAY");
		return myDocument;
	}

	// @Method
	// 初始化文件，参数为模板名，打印机名
	public Document createDocuments(String printername, GlobalInput mGlobalInput) {
		// Create the root element
		// TemplateName=templatename;
		logger.debug("PrinterName:" + printername + ",Operator:"
				+ mGlobalInput.Operator);
		myDocument = DocumentHelper.createDocument();// create the document
		Element DataSetElements = myDocument.addElement("DATASETS");// add some
		// child
		// elements

		// Note that this is the first approach to adding an element and
		// textual content. The second approach is commented out.

		Element CONTROL = DataSetElements.addElement("CONTROL");
		Element PRINTER = CONTROL.addElement("PRINTER");
		Element OPERATOR = CONTROL.addElement("OPERATOR");
		Element REQUESTCOM = CONTROL.addElement("REQUESTCOM");
		Element OPERATECOM = CONTROL.addElement("OPERATECOM");
		CONTROL.addElement("TEMPLATE");
		OPERATOR.setText(mGlobalInput.Operator);
		REQUESTCOM.setText(mGlobalInput.ComCode);
		OPERATECOM.setText(mGlobalInput.ManageCom);
		PRINTER.setText(printername);
		return myDocument;
	}

	public Document getDocument() {
		return this.myDocument;
	}

	/**
	 * 输出xml文件，参数为路径，文件名
	 * 
	 * @param pathname
	 * @param filename
	 */
	public void outputDocumentToFile(String pathname, String filename) {
		outputDocumentToFile(pathname, filename, true);
	}

	/**
	 * 输出xml文件，参数为路径，文件名,是否需要改名，文件名后缀
	 * 
	 * @param pathname
	 * @param filename
	 * @param isNeedRename
	 */
	public void outputDocumentToFile(String pathname, String filename,
			boolean isNeedRename) {
		XMLWriter outputter = null;
		FileWriter writer = null;
		// setup this like outputDocument
		try {
			outputter = new XMLWriter();
			// output to a file
			String str = pathname + filename;
			if (isNeedRename)
				str += FileNameQueue.XML_PRE;
			else
				str += FileNameQueue.XML;
			writer = new FileWriter(str);
			outputter.setWriter(writer);
			outputter.write(myDocument);
			writer.close();
			outputter.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			if (isNeedRename)
				FileNameQueue.rename(pathname, filename);
		}

	}

	/**
	 * 添加一个列表，参数为ListTag和动态列表的表头数组
	 * 
	 * @param EleName
	 * @param listtable
	 * @param colvalue
	 * @return
	 */
	public Document addListTableToEle(Element EleName, ListTable listtable,
			String[] colvalue) {
		this.col = colvalue.length;
		Element DataSetElement = EleName;
		Element table = DataSetElement.addElement(listtable.getName());
		table.addAttribute("RowCount", String.valueOf(listtable.size()));
		table.addAttribute("ColCount", String.valueOf(colvalue.length));
		Element head = table.addElement(this.TAG_NAME_HEAD);
		// 建立表头名
		for (int m = 0; m < colvalue.length; m++) {
			int n = m + 1;
			String colnum = this.TAG_NAME_COL + n;
			if (colvalue[m] == null)
				colvalue[m] = "";
			head.addElement(colnum).setText(colvalue[m]);
		}

		// 遍历整个table
		for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
			String[] temparray = new String[this.col];
			temparray = listtable.get(i);
			Element row = table.addElement(this.TAG_NAME_ROW);
			for (int m = 0; m < temparray.length; m++) {
				int n = m + 1;
				String colnum = this.TAG_NAME_COL + n;
				if (temparray[m] == null)
					temparray[m] = "";
				row.addElement(colnum).setText(temparray[m]);
			}

		}
		return myDocument;
	}

	// 添加一个列表，参数为ListTag和动态列表的表头数组
	public Document addListTable(ListTable listtable, String[] colvalue) {
		this.col = colvalue.length;
		Element DataSetElement = this.myDocument.getRootElement();
		Element table = DataSetElement.addElement(listtable.getName());
		table.addAttribute("RowCount", String.valueOf(listtable.size()));
		table.addAttribute("ColCount", String.valueOf(colvalue.length));
		Element head = table.addElement(this.TAG_NAME_HEAD);
		// 建立表头名
		for (int m = 0; m < colvalue.length; m++) {
			int n = m + 1;
			String colnum = this.TAG_NAME_COL + n;
			if (colvalue[m] == null)
				colvalue[m] = "";
			head.addElement(colnum).setText(colvalue[m]);
		}

		// 遍历整个table
		for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
			String[] temparray = new String[this.col];
			temparray = listtable.get(i);
			Element row = table.addElement(this.TAG_NAME_ROW);
			for (int m = 0; m < temparray.length; m++) {
				int n = m + 1;
				String colnum = this.TAG_NAME_COL + n;
				if (temparray[m] == null)
					temparray[m] = "";
				row.addElement(colnum).setText(temparray[m]);
			}

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
				if (colvalue[m] == null)
					colvalue[m] = "";
				head.addElement(colnum).setText(colvalue[m]);
			}

			// 遍历整个table
			for (int i = 0, tablesize = listtable.size() - 1; i <= tablesize; i++) {
				String[] temparray = new String[this.col];
				temparray = listtable.get(i);
				Element row = table.addElement(this.TAG_NAME_ROW);
				for (int m = 0; m < temparray.length; m++) {
					int n = m + 1;
					String colnum = this.TAG_NAME_COL + n;
					if (temparray[m] == null)
						temparray[m] = "";
					row.addElement(colnum).setText(temparray[m]);
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

	/*
	 * 直接从文档中产生一个输入流对象，而不是生成一个临时文件 输出： 一个输入流对象
	 */
	public InputStream getInputStream() {
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputFormat format = OutputFormat.createCompactFormat();
			format.setEncoding("GBK");
			format.setNewLineAfterDeclaration(false);
			XMLWriter outputter = new XMLWriter(format);
			outputter.setOutputStream(baos);
			outputter.write(myDocument);
			baos.close();
			outputter.close();
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

	/**
	 * 2002-11-11 kevin 在数据xml文件的某一项中加入一个新项
	 * 
	 * @param strRiskNo
	 *            String
	 * @return Document
	 */
	public Document addControlItem(String item, String name, String value) {
		Element elementControl = this.myDocument.getRootElement().element(item);
		if (value == null)
			value = "";
		elementControl.addElement(name).setText(value);

		return myDocument;
	}

	public static void main(String args[]) {
		XmlExport xe = new XmlExport();
		GlobalInput tg = new GlobalInput();
		tg.ComCode = "";
		tg.ManageCom = "";
		tg.Operator = "";

		xe.createDocuments("printer", tg);
		xe.outputDocumentToFile("c:\\nclprint\\", "xetest");
		xe.setPrinterName("hpe3005");
		xe.setTemplateName("abc");
		xe.outputDocumentToFile("c:\\nclprint\\", "xetest2");
	}
}
