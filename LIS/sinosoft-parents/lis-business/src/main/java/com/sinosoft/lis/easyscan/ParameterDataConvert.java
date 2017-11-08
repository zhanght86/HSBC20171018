package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis 5.0
 * </p>
 * <p>
 * Description: 参数数据格式转换类：实现索引XML与Schema和Set之间的转换
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class ParameterDataConvert {
private static Logger logger = Logger.getLogger(ParameterDataConvert.class);
	private static final String CON_XML_ROOT = "DATA";
	private static final String CON_XML_DOC_NAME = "DOC";
	private static final String CON_XML_PAGE_NAME = "PAGE";
	private static final String CON_XML_RET_NAME = "RETURN";

	public ParameterDataConvert() {
	}

	/**
	 * 索引XML数据转换为vData
	 * 
	 * @param strXML
	 * @param vData
	 * @return
	 */
	public boolean xMLToVData(String strXML, VData vData) {
		try {
			// Use SAXBuilder
			SAXBuilder builder = new SAXBuilder();

			// Document doc = builder.build(new
			// ByteArrayInputStream(strXML.getBytes()));
			Document doc = builder.build(new CharArrayReader(strXML
					.toCharArray()));
			Element root = doc.getRootElement();
			vData.clear();
			vData.setSize(10);

			// 设置Es_Doc_Main
			List listMain = root.getChildren(CON_XML_DOC_NAME);
			ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
			String[] strScanType = new String[listMain.size()];
			for (int j = 0; j < listMain.size(); j++) {
				Element docMain = (Element) listMain.get(j);
				ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
				tES_DOC_MAINSchema
						.setDocID(docMain.getChild("DocID").getText());
				tES_DOC_MAINSchema.setDocCode(docMain.getChild("DocCode")
						.getText());
				tES_DOC_MAINSchema.setBussType(docMain.getChild("BussType")
						.getText());
				tES_DOC_MAINSchema.setSubType(docMain.getChild("SubType")
						.getText());
				// tES_DOC_MAINSchema.setBussType("TB");
				// tES_DOC_MAINSchema.setSubType("TB17");
				tES_DOC_MAINSchema.setNumPages(docMain.getChild("NumPages")
						.getText());
				tES_DOC_MAINSchema.setDocFlag(docMain.getChild("DocFlag")
						.getText());
				tES_DOC_MAINSchema.setDocRemark(docMain.getChild("DocRemark")
						.getText());
				tES_DOC_MAINSchema.setScanOperator(docMain.getChild(
						"ScanOperator").getText());
				tES_DOC_MAINSchema.setManageCom(docMain.getChild("ManageCom")
						.getText());
				tES_DOC_MAINSchema.setMakeDate(docMain.getChild("MakeDate")
						.getText());
				tES_DOC_MAINSchema.setMakeTime(docMain.getChild("MakeTime")
						.getText());
				tES_DOC_MAINSchema.setModifyDate(docMain.getChild("ModifyDate")
						.getText());
				tES_DOC_MAINSchema.setModifyTime(docMain.getChild("ModifyTime")
						.getText());
				// Added by wellhi 2005.07.29
				tES_DOC_MAINSchema.setVersion(docMain.getChild("DocVersion")
						.getText());
				tES_DOC_MAINSchema.setPrintCode(docMain.getChild("PrintCode")
						.getText());
				tES_DOC_MAINSchema.setScanNo(docMain.getChild("ScanNo")
						.getText());
				// Added by wellhi 2005.10.08
				// 扫描方式:事前扫描,事后扫描
				strScanType[j] = docMain.getChild("ScanType").getText();
				tES_DOC_MAINSet.add(tES_DOC_MAINSchema);
			}

			// vData.add(tES_DOC_MAINSet);
			vData.setElementAt(tES_DOC_MAINSet, 0);
			// 设置Es_Doc_Pages
			List listPage = root.getChildren(CON_XML_PAGE_NAME);
			String[] strPage_URL = new String[listPage.size()];
			ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet();

			for (int i = 0; i < listPage.size(); i++) {
				Element docPages = (Element) listPage.get(i);
				ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
				tES_DOC_PAGESSchema.setPageID(docPages.getChild("PageID")
						.getText());
				tES_DOC_PAGESSchema.setDocID(docPages.getChild("DocID")
						.getText());
				tES_DOC_PAGESSchema.setHostName(docPages.getChild("HostName")
						.getText());
				tES_DOC_PAGESSchema.setPageName(docPages.getChild("PageName")
						.getText());
				tES_DOC_PAGESSchema.setPageCode(docPages.getChild("PageCode")
						.getText());
				tES_DOC_PAGESSchema.setPageSuffix(docPages.getChild(
						"PageSuffix").getText());
				tES_DOC_PAGESSchema.setPicPathFTP(docPages.getChild(
						"PicPathFTP").getText());
				tES_DOC_PAGESSchema.setPageFlag(docPages.getChild("PageFlag")
						.getText());
				tES_DOC_PAGESSchema.setPicPath(docPages.getChild("PicPath")
						.getText());
				tES_DOC_PAGESSchema.setPageType(docPages.getChild("PageType")
						.getText());
				tES_DOC_PAGESSchema.setManageCom(docPages.getChild("ManageCom")
						.getText());
				tES_DOC_PAGESSchema.setOperator(docPages.getChild("Operator")
						.getText());
				tES_DOC_PAGESSchema.setMakeDate(docPages.getChild("MakeDate")
						.getText());
				tES_DOC_PAGESSchema.setMakeTime(docPages.getChild("MakeTime")
						.getText());
				tES_DOC_PAGESSchema.setModifyDate(docPages.getChild(
						"ModifyDate").getText());
				tES_DOC_PAGESSchema.setModifyTime(docPages.getChild(
						"ModifyTime").getText());
				// Added by wellhi 2005.07.29
				tES_DOC_PAGESSchema.setScanNo(docPages.getChild("ScanNo")
						.getText());
				// Added by zxm 2006.02.20
				// tES_DOC_PAGESSchema.setFNDocKey(docPages.getChild("FNDocKey").getText());
				tES_DOC_PAGESSchema.setFNDocKey("0");
				tES_DOC_PAGESSet.add(tES_DOC_PAGESSchema);

				strPage_URL[i] = docPages.getChild("Page_URL").getText();
			}

			// vData.add(tES_DOC_PAGESSet);
			// vData.add(strPage_URL);
			vData.setElementAt(tES_DOC_PAGESSet, 1);
			vData.setElementAt(strPage_URL, 2);
			// 设置其它
			Element ret = root.getChild(CON_XML_RET_NAME);
			// vData.add(ret.getChild("NUMBER").getText());
			// vData.add(ret.getChild("MESSAGE").getText());
			vData.setElementAt(ret.getChild("NUMBER").getText(), 3);
			vData.setElementAt(ret.getChild("MESSAGE").getText(), 4);
			vData.setElementAt(strScanType, 7);
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * vData转换为索引XML数据
	 * 
	 * @param strXML
	 * @param vData
	 * @return
	 */
	public boolean vDataToXML(StringBuffer strXML, VData vData) {
		try {
			// 创建标准XML文档，"DATA"作为根节点
			Element root = new Element(CON_XML_ROOT);
			Document doc = new Document(root);
			Element column;

			// 处理ES_DOC_MAIN
			ES_DOC_MAINSet tES_DOC_MAINSet = (ES_DOC_MAINSet) vData.get(0);
			int intSize = tES_DOC_MAINSet.size();
			String[] strScanType = (String[]) vData.get(7);

			for (int j = 0; j < intSize; j++) {
				Element docMain = new Element(CON_XML_DOC_NAME);
				ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet
						.get(j + 1);
				column = new Element("DocID");
				column = column.setText(String.valueOf((int) tES_DOC_MAINSchema
						.getDocID()));
				docMain.addContent(column);
				column = new Element("DocCode");
				column = column.setText((tES_DOC_MAINSchema.getDocCode()));
				docMain.addContent(column);
				column = new Element("BussType");
				column = column.setText(tES_DOC_MAINSchema.getBussType());
				docMain.addContent(column);
				column = new Element("SubType");
				column = column.setText(tES_DOC_MAINSchema.getSubType());
				docMain.addContent(column);
				column = new Element("NumPages");
				column = column.setText(String.valueOf(tES_DOC_MAINSchema
						.getNumPages()));
				docMain.addContent(column);
				column = new Element("DocFlag");
				column = column.setText((tES_DOC_MAINSchema.getDocFlag()));
				docMain.addContent(column);
				column = new Element("DocRemark");
				column = column.setText((tES_DOC_MAINSchema.getDocRemark()));
				docMain.addContent(column);
				column = new Element("ScanOperator");
				column = column.setText((tES_DOC_MAINSchema.getScanOperator()));
				docMain.addContent(column);
				// column = new Element("ScanOperator");
				// column =
				// column.setText((tES_DOC_MAINSchema.getScanOperator()));
				// docMain.addContent(column);
				column = new Element("ManageCom");
				column = column.setText((tES_DOC_MAINSchema.getManageCom()));
				docMain.addContent(column);
				column = new Element("MakeDate");
				column = column.setText((tES_DOC_MAINSchema.getMakeDate()));
				docMain.addContent(column);
				column = new Element("MakeTime");
				column = column.setText((tES_DOC_MAINSchema.getMakeTime()));
				docMain.addContent(column);
				column = new Element("ModifyDate");
				column = column.setText((tES_DOC_MAINSchema.getModifyDate()));
				docMain.addContent(column);
				column = new Element("ModifyTime");
				column = column.setText((tES_DOC_MAINSchema.getModifyTime()));
				docMain.addContent(column);
				// Added by wellhi 2005.07.29
				column = new Element("DocVersion");
				column = column.setText((tES_DOC_MAINSchema.getVersion()));
				docMain.addContent(column);
				column = new Element("PrintCode");
				column = column.setText((tES_DOC_MAINSchema.getPrintCode()));
				docMain.addContent(column);
				column = new Element("ScanNo");
				column = column.setText(tES_DOC_MAINSchema.getScanNo());
				docMain.addContent(column);
				root.addContent(docMain);
				// Added by wellhi 2005.10.20
				column = new Element("ScanType");
				column = column.setText(strScanType[j]);
				docMain.addContent(column);

			}

			// 处理ES_DOC_PAGES
			ES_DOC_PAGESSet tES_DOC_PAGESSet = (ES_DOC_PAGESSet) vData.get(1);
			String[] strPage_URL = (String[]) vData.get(2);

			for (int i = 0; i < tES_DOC_PAGESSet.size(); i++) {
				Element docPage = new Element(CON_XML_PAGE_NAME);
				ES_DOC_PAGESSchema tES_DOC_PAGESSchema = tES_DOC_PAGESSet
						.get(i + 1);

				column = new Element("PageID");
				column = column.setText(String
						.valueOf((int) tES_DOC_PAGESSchema.getPageID()));
				docPage.addContent(column);
				column = new Element("DocID");
				column = column.setText(String
						.valueOf((int) tES_DOC_PAGESSchema.getDocID()));
				docPage.addContent(column);
				column = new Element("PageCode");
				column = column.setText(String.valueOf(tES_DOC_PAGESSchema
						.getPageCode()));
				docPage.addContent(column);
				column = new Element("HostName");
				column = column.setText((tES_DOC_PAGESSchema.getHostName()));
				docPage.addContent(column);
				column = new Element("PageName");
				column = column.setText((tES_DOC_PAGESSchema.getPageName()));
				docPage.addContent(column);
				column = new Element("PageSuffix");
				column = column.setText((tES_DOC_PAGESSchema.getPageSuffix()));
				docPage.addContent(column);
				column = new Element("PicPathFTP");
				column = column.setText((tES_DOC_PAGESSchema.getPicPathFTP()));
				docPage.addContent(column);

				column = new Element("PageFlag");
				column = column.setText((tES_DOC_PAGESSchema.getPageFlag()));
				docPage.addContent(column);
				column = new Element("PicPath");
				column = column.setText((tES_DOC_PAGESSchema.getPicPath()));
				docPage.addContent(column);
				column = new Element("PageType");
				column = column.setText((tES_DOC_PAGESSchema.getPageType()));
				docPage.addContent(column);
				column = new Element("ManageCom");
				column = column.setText((tES_DOC_PAGESSchema.getManageCom()));
				docPage.addContent(column);
				column = new Element("Operator");
				column = column.setText((tES_DOC_PAGESSchema.getOperator()));
				docPage.addContent(column);
				column = new Element("MakeDate");
				column = column.setText((tES_DOC_PAGESSchema.getMakeDate()));
				docPage.addContent(column);
				column = new Element("MakeTime");
				column = column.setText((tES_DOC_PAGESSchema.getMakeTime()));
				docPage.addContent(column);
				column = new Element("ModifyDate");
				column = column.setText((tES_DOC_PAGESSchema.getModifyDate()));
				docPage.addContent(column);
				column = new Element("ModifyTime");
				column = column.setText((tES_DOC_PAGESSchema.getModifyTime()));
				docPage.addContent(column);
				// Page_URL
				column = new Element("Page_URL");
				column = column.setText(strPage_URL[i]);
				docPage.addContent(column);
				// Added by wellhi 2005.07.29
				column = new Element("ScanNo");
				column = column.setText(tES_DOC_PAGESSchema.getScanNo() + "");
				docPage.addContent(column);
				// Added by zxm 2006.02.20
				column = new Element("FNDocKey");
				column = column.setText(tES_DOC_PAGESSchema.getFNDocKey());
				docPage.addContent(column);
				root.addContent(docPage);
			}

			// 处理RETURN
			Element docRet = new Element(CON_XML_RET_NAME);

			String strNumber = (String) vData.get(3);
			column = new Element("NUMBER");
			column = column.setText(strNumber);
			docRet.addContent(column);

			String strMessage = (String) vData.get(4);
			column = new Element("MESSAGE");
			column = column.setText(strMessage);
			docRet.addContent(column);

			// logger.debug("ParameterDataConvert->vDataToXML：MESSAGE=" +
			// column.getText());
			root.addContent(docRet);

			// 把doc转换为OutputStream
			XMLOutputter out = new XMLOutputter();

			// OutputStream outputStream = new ByteArrayOutputStream();
			// out.output(doc,outputStream);
			// outputStream.flush();
			strXML.append(out.outputString(doc));

			// logger.debug("ParameterDataConvert->vDataToXML Out=" +
			// out.outputString(doc));
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Added by wellhi 2005.08.28 中心单证修改时借用了ManageCom保存IssueDocID
	 * 
	 * @param strManageCom
	 */
	public String getIssueDocID(String strManageCom) {
		String strRIssueDocID;
		int intPos = strManageCom.indexOf("||");
		if (intPos > 0) {
			strRIssueDocID = strManageCom.substring(intPos + 2);
		} else {
			strRIssueDocID = "";
		}
		return strRIssueDocID;
	}

	/**
	 * Added by wellhi 2005.08.28 中心单证修改时借用了ManageCom保存IssueDocID，故要先去掉
	 * 
	 * @param strManageCom
	 */
	public String getManageCom(String strManageCom) {
		String strRManageCom;
		int intPos = strManageCom.indexOf("||");
		if (intPos > 0) {
			strRManageCom = strManageCom.substring(0, intPos);
		} else {
			strRManageCom = strManageCom;
		}
		return strRManageCom;
	}

	/**
	 * 测试 Main 方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ParameterDataConvert convert = new ParameterDataConvert();

		try {
			VData vData = new VData();
			StringBuffer strBuf = new StringBuffer(1024);

			// Use SAXBuilder
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new File("D:/EasyScan.xml"));
			Element root = doc.getRootElement();
			XMLOutputter out = new XMLOutputter();
			OutputStream outputStream = new ByteArrayOutputStream();
			out.output(doc, outputStream);
			outputStream.flush();

			String strXML = outputStream.toString();
			logger.debug(strXML);

			convert.xMLToVData(strXML, vData);

			// String strNumber = "123";
			// String strMessage = "Message";
			// vData.add(strNumber);
			// vData.add(strMessage);
			convert.vDataToXML(strBuf, vData);

			logger.debug(strBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
