package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.sinosoft.lis.db.ES_PROPERTY_DEFDB;
import com.sinosoft.lis.schema.ES_PROPERTY_DEFSchema;
import com.sinosoft.lis.vschema.ES_PROPERTY_DEFSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexIssueReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexIssueResXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareReqXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssueIndexVo;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.entity.EsQcPages;
import com.sinosoft.easyscan5.util.FDate;

public class UpIndexIssueDataConvertUtil extends NewDataConvertServiceImpl
		implements INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());

	public String stringToXml(String temp, StringBuffer bufXml) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			root.addElement(UpIndexIssueResXmlConstants.XML_BODY);
			addResult(root);
			super.getOutXml(bufXml);
		} catch (RuntimeException e) {
			logger.error("拼接返回报文出错", e);
			returnStr = "拼接返回报文出错" + e.getMessage();
		}
		return returnStr;
	}

	public String xmlToUpIssueIndexVo(String indexXML,
			UploadIssueIndexVo uploadIssueIndexVo){
		String returnStr = null;
		try {
			super.setDocument(indexXML);
			Document doc = super.getReqDocument();
			uploadIssueIndexVo.setChannel(getChannel(doc));
			List batchList = doc.selectNodes(UpIndexIssueReqXmlConstants.XML_ROOT
					+ "/" + UpIndexIssueReqXmlConstants.XML_BODY + "/"
					+ UpIndexIssueReqXmlConstants.XML_UPLOADBATCH);
			if (batchList == null || batchList.size() == 0) {
				logger.error("报文缺少uploadbatch节点");
				returnStr = "报文缺少uploadbatch节点";
				return returnStr;
			}
			Element uploadbatchEle = (Element) batchList.get(0);
			String batchKey = uploadbatchEle
						.attributeValue(UpIndexIssueReqXmlConstants.XML_UPLOADBATCH_BATCHKEY);
			uploadIssueIndexVo.setBatchKey(batchKey);
			List issueEleList = uploadbatchEle.selectNodes(UpIndexIssueReqXmlConstants.XML_ISSUE);
			if(issueEleList == null || issueEleList.size() == 0){
				logger.error("报文缺少ISSUE节点");
				returnStr = "报文缺少ISSUE节点";
				return returnStr;
			}
			Element issueEle = (Element) issueEleList.get(0);
			String issueNo = issueEle.attributeValue(UpIndexIssueReqXmlConstants.XML_ISSUE_ISSUENO);
			uploadIssueIndexVo.setIssueNo(issueNo);
			// ��ȡgroup�ڵ�,����EsQcMain
			List groupEleList = issueEle.selectNodes(UpIndexIssueReqXmlConstants.XML_GROUP);
			if (groupEleList == null || groupEleList.size() == 0) {
				logger.error("报文格式不正确,缺少GROUP节点");
				returnStr = "报文格式不正确,缺少GROUP节点";
				return returnStr;
			}
			this.parseXmlToEsQcMain(groupEleList, uploadIssueIndexVo,
					batchKey);
		}catch (Exception e) {
			logger.error("解析报文出错", e);
			returnStr = "解析报文出错：" + e.getMessage();
		}
		return null;
	}

	/**
	 * parseXmlToEsQcMain
	 * @param groupEleList
	 * @param uploadIssueIndexVo
	 * @param batchKey
	 * @param param
	 * @throws Exception
	 */
	private void parseXmlToEsQcMain(List groupEleList,UploadIssueIndexVo uploadIssueIndexVo,
			String batchKey) throws Exception {
		for (int i = 0; i < groupEleList.size(); i++) {
			Element groupEle = (Element) groupEleList.get(i);
			// ���
			String groupNo = groupEle
					.attributeValue(UpIndexIssueReqXmlConstants.XML_GROUP_CASENO);
			List docEleList = groupEle.selectNodes(UpIndexIssueReqXmlConstants.XML_DOC);
			if (docEleList != null && docEleList.size() > 0) {
				for (int j = 0; j < docEleList.size(); j++) {
					Element doc = (Element) docEleList.get(j);
					EsDocAndPageVO esDocAndPageVO = new EsDocAndPageVO();
					this.setEsQcMain(doc, uploadIssueIndexVo, groupNo, batchKey);
				}
			}
		}
	}
	/**
	 * setEsQcMain
	 * @param doc
	 * @param uploadIssueIndexVo
	 * @param groupNo
	 * @param batchKey
	 * @param param
	 * @throws Exception
	 */
	private void setEsQcMain(Element doc, UploadIssueIndexVo uploadIssueIndexVo,
			String groupNo, String batchKey) throws Exception {
		EsQcMain esQcMain = new EsQcMain();
		esQcMain.setDocId(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_DOCID));
		esQcMain.setDocCode(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_DOCCODE));
		esQcMain.setGroupNo(groupNo);
		esQcMain.setPrintCode(doc.attributeValue(UpIndexReqXmlConstants.XML_DOC_PRINTCODE));
		esQcMain.setBussType(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_BUSSTYPE));
		esQcMain.setSubType(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_SUBTYPE));
		esQcMain.setScanNo(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_BOXNO));
		String scanType = doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_SCANTYPE);
		String numPages = doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_PAGECOUNT);
		if (numPages != null && !"".equals(numPages)) {
			esQcMain.setNumPages(new Long(numPages));
		}
		// 扫描时间
		String scanTime = doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_SCANDATE);
		if (scanTime == null || "".equals(scanTime)) {
			esQcMain.setScanDate(FDate.getCurrentDateAndTime());
		} else {
			if(scanTime.length()>19){
				
				SimpleDateFormat sdf = new SimpleDateFormat(scanTime);
				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(sdf.format(new Date()));
//				SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_MS_FORMAT);
				esQcMain.setScanDate(startTime);
			}else{
				SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
				esQcMain.setScanDate(format.parse(scanTime));
			}
			
		}
		esQcMain.setScanOperator(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_SCANUSER));
		esQcMain.setScanCom(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_SCANCOM));
		esQcMain.setManageCom(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_DOC_MANAGECOM));
	
		esQcMain.setTempFlag("N");
		//单证问题件标记
		esQcMain.setDocFlag(Constants.DOC_QC_ISSUE);
		
		esQcMain.setChannel(uploadIssueIndexVo.getChannel());
		esQcMain.setBatchNo(batchKey);
		//扫描类型
		esQcMain.setFu1(doc.attributeValue(UpIndexIssueReqXmlConstants.XML_SCANTYPE));
		//扩展属性
		this.xmlToProp(esQcMain, doc);
		/** 单证页* */
		List pageList = doc.selectNodes(UpIndexReqXmlConstants.XML_PAGES);
		List<EsQcPages> qcPagesList = new ArrayList<EsQcPages>();
		if (pageList != null && pageList.size() > 0) {
			for (int i = 0; i < pageList.size(); i++) {
				Element pages = (Element) pageList.get(i);
				List pagesList = pages
						.selectNodes(UpIndexReqXmlConstants.XML_PAGE);
				for (int j = 0; j < pagesList.size(); j++) {
					Element page = (Element) pagesList.get(j);
					EsQcPages esQcPages = new EsQcPages();
					this.setEsQcPages(page, esQcPages, esQcMain);
					qcPagesList.add(esQcPages);
				}
			}
		}
		uploadIssueIndexVo.getEsQcMainList().add(esQcMain);
		uploadIssueIndexVo.getEsQcPagesMap().put(esQcMain.getDocId(), qcPagesList);
	}
	private void xmlToProp(EsQcMain esQcMain, Element eDocMain)
			throws Exception {
		List list = eDocMain.selectNodes(UpPrepareReqXmlConstants.XML_PROPS
				+ "/" + UpPrepareReqXmlConstants.XML_PROP);
		StringBuffer sql = new StringBuffer("select * from es_property_def where busstype = '?busstype?' and subtype='?subtype?'");
		ArrayList<String> columnNameArr=new ArrayList<String>();
		List propertyList= new ArrayList();
		if (list != null && list.size() > 0) {
			sql.append(" and propfield not in (?columnName?");
//			String expropertyVersion = expandPropertyUtil.queryExpPropVersion();
			for (int j = 0; j < list.size(); j++) {
				Element pro = (Element) list.get(j);
				String columnName = pro
						.attributeValue(UpPrepareReqXmlConstants.XML_PROP_CODE);
//				if(j==0){
//					sql.append("'"+columnName+"'");
//				}else{
//					sql.append(",'"+columnName+"'");
//				}
				columnNameArr.add(columnName);
				String fValue = pro
						.attributeValue(UpPrepareReqXmlConstants.XML_PROP_VALUE);
//				esQcMain.setExpropertyVersion(expropertyVersion);
				propertyList.add(columnName);
				esQcMain.setV(columnName, fValue);
			}
			sql.append(" )");
		}
		ES_PROPERTY_DEFDB es_PROPERTY_DEFDB = new ES_PROPERTY_DEFDB();
		es_PROPERTY_DEFDB.setBussType(esQcMain.getBussType());
		es_PROPERTY_DEFDB.setSubType(esQcMain.getSubType());
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql.toString());
		sqlbv.put("busstype", esQcMain.getBussType());
		sqlbv.put("subtype", esQcMain.getSubType());
		sqlbv.put("columnName", columnNameArr);
		ES_PROPERTY_DEFSet	es_PROPERTY_DEFSet=	es_PROPERTY_DEFDB.executeQuery(sqlbv);
		for(int i=1;i<=es_PROPERTY_DEFSet.size();i++){
			ES_PROPERTY_DEFSchema es_PROPERTY_DEFSchema = es_PROPERTY_DEFSet.get(i);

				esQcMain.setV(es_PROPERTY_DEFSchema.getPropField(), es_PROPERTY_DEFSchema.getCtrlDefaultValue());
			
		}
	}
	private void setEsQcPages(Element docPages, EsQcPages esQcPages,
			EsQcMain esQcMain) throws Exception {

		esQcPages.setDocId(esQcMain.getDocId());
		String pageNo = docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_PAGECODE);
		esQcPages.setPageId(docPages.attributeValue(UpIndexIssueReqXmlConstants.XML_PAGE_PAGEID));
		esQcPages.setPicPath(docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_FILEPATH));
		esQcPages.setPageName(docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_PAGENAME));
		esQcPages.setPageNo(new Long(pageNo));
		esQcPages.setPageSuffix(docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_PAGESUFFIX));
		esQcPages.setScanOperator(docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_OPERATOR));
		String scanDate = docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_SCANDATE);
		if (scanDate != null && !"".equals(scanDate)) {
			if(scanDate.length()>19){
				SimpleDateFormat sdf = new SimpleDateFormat(scanDate);
				Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(sdf.format(new Date()));
//				SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_MS_FORMAT);
				esQcPages.setScanDate(startTime);
			}else{
				SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
				esQcPages.setScanDate(format.parse(scanDate));
			}
			
		} else {
			esQcPages.setScanDate(new Date());
		}
		//修改标记
		if(docPages.attributeValue(UpIndexIssueReqXmlConstants.XML_UPLOADBATCH_BATCHKEY).length()>30){
			esQcPages.setPageFlag(Constants.PAGE_FLAG_CHANGED);
		}else{
			esQcPages.setPageFlag(Constants.PAGE_FLAG_NOCHANGED);
		}
		
//		List markList = docPages.selectNodes(UpIndexReqXmlConstants.XML_MARKLAYER);
//		if (markList != null && markList.size() > 0) {
//			esMarklayer.setFormat(UpIndexReqXmlConstants.XML_PAGE_FORMAT);
//			for (int i = 0; i < markList.size(); i++) {
//				Element mark = (Element) markList.get(i);
//				String marklayer = mark.getText();
//				Blob blob = new SerialBlob(marklayer.getBytes());  
//				esMarklayer.setMarklayer(blob);
//				logger.info("marklayer:" + marklayer);
//			}
//		}
		
		Node contentNode = docPages
				.selectSingleNode(UpIndexReqXmlConstants.XML_CONTENT);
		if (contentNode != null) {
			String content = contentNode.getText();
		}
		Node remarkNode = docPages
				.selectSingleNode(UpIndexReqXmlConstants.XML_REMARK);
		if (remarkNode != null) {
			String remark = remarkNode.getText();
		}
	}
	
}
