package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareIssueReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareIssueResXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareReqXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssuePrepareVo;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.db.ES_PROPERTY_DEFDB;
import com.sinosoft.lis.schema.ES_PROPERTY_DEFSchema;
import com.sinosoft.lis.vschema.ES_PROPERTY_DEFSet;
import com.sinosoft.utility.SQLwithBindVariables;
public class UpPrepareIssueDataConvertUtil extends NewDataConvertServiceImpl
		implements INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());

	public String stringToXml(String[] serverUrl, StringBuffer bufXml) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element body = root.addElement(UpPrepareIssueResXmlConstants.XML_BODY);
			Element fileurl = body.addElement(UpPrepareIssueResXmlConstants.XML_FILEURL);
			fileurl.addAttribute(UpPrepareIssueResXmlConstants.XML_FILEURL_PAGEURL,
							(serverUrl != null)?serverUrl[0]:"");
			fileurl.addAttribute(UpPrepareIssueResXmlConstants.XML_FILEURL_FILEPATH,
						(serverUrl != null)?serverUrl[1]:"");
			addResult(root);
			super.getOutXml(bufXml);
		} catch (RuntimeException e) {
			logger.error("拼接返回报文出错", e);
			returnStr = "拼接返回报文出错" + e.getMessage();
		}
		return returnStr;
	}

	public String xmlToUpIssuePrepareVo(String indexXML,
			UploadIssuePrepareVo uploadIssuePrepareVo){
		String returnStr = null;
		//��ʱ����
		String param = "";
		try {
			super.setDocument(indexXML);
			Document doc = super.getReqDocument();
			uploadIssuePrepareVo.setChannel(getChannel(doc));
			List batchList = doc.selectNodes(UpPrepareIssueReqXmlConstants.XML_ROOT
					+ "/" + UpPrepareIssueReqXmlConstants.XML_BODY + "/"
					+ UpPrepareIssueReqXmlConstants.XML_UPLOADBATCH);
			if (batchList == null || batchList.size() == 0) {
				logger.error("报文缺少批次节点");
				returnStr = "报文缺少批次节点";
				return returnStr;
			}
			Element uploadbatchEle = (Element) batchList.get(0);
			String batchKey = uploadbatchEle
						.attributeValue(UpPrepareIssueReqXmlConstants.XML_UPLOADBATCH_BATCHKEY);
			uploadIssuePrepareVo.setBatchKey(batchKey);
			List issueEleList = uploadbatchEle.selectNodes(UpPrepareIssueReqXmlConstants.XML_ISSUE);
			if(issueEleList == null || issueEleList.size() == 0){
				logger.error("报文缺少ISSUE节点");
				returnStr = "报文缺少ISSUE节点";
				return returnStr;
			}
			Element issueEle = (Element) issueEleList.get(0);
			String issueNo = issueEle.attributeValue(UpPrepareIssueReqXmlConstants.XML_ISSUE_ISSUENO);
			uploadIssuePrepareVo.setIssueNo(issueNo);
			// ��ȡgroup�ڵ�,����EsQcMain
			List groupEleList = issueEle.selectNodes(UpPrepareReqXmlConstants.XML_GROUP);
			if (groupEleList == null || groupEleList.size() == 0) {
				logger.error("报文格式不正确,缺少GROUP节点");
				returnStr = "报文格式不正确,缺少GROUP节点";
				return returnStr;
			}
			this.parseXmlToEsQcMain(groupEleList, uploadIssuePrepareVo,
					batchKey,param);
		}catch (Exception e) {
			logger.error("解析报文出错", e);
			returnStr = "解析报文出错：" + e.getMessage();
		}
		return null;
	}

	/*
	 * ����EsQcMain
	 */
	private void parseXmlToEsQcMain(List groupEleList,UploadIssuePrepareVo uploadIssuePrepareVo,
			String batchKey, String param) throws Exception {
		for (int i = 0; i < groupEleList.size(); i++) {
			Element groupEle = (Element) groupEleList.get(i);
			// ���
			String groupNo = groupEle
					.attributeValue(UpPrepareIssueReqXmlConstants.XML_GROUP_CASENO);
			List docEleList = groupEle.selectNodes(UpPrepareIssueReqXmlConstants.XML_DOC);
			if (docEleList != null && docEleList.size() > 0) {
				for (int j = 0; j < docEleList.size(); j++) {
					Element doc = (Element) docEleList.get(j);
					EsDocAndPageVO esDocAndPageVO = new EsDocAndPageVO();
					this.setEsQcMain(doc, uploadIssuePrepareVo, groupNo, batchKey,
							param);
				}
			}
		}
	}

	private void setEsQcMain(Element doc, UploadIssuePrepareVo uploadIssuePrepareVo,
			String groupNo, String batchKey, String param) throws Exception {
		EsQcMain esQcMain = new EsQcMain();
		esQcMain.setDocId(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_DOCID));
		esQcMain.setDocCode(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_DOCCODE));
		esQcMain.setGroupNo(groupNo);
		esQcMain.setPrintCode(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_PRINTCODE));
		esQcMain.setBussType(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_BUSSTYPE));
		esQcMain.setSubType(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SUBTYPE));
		esQcMain.setScanNo(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_BOXNO));
		String scanType = doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANTYPE);
		String numPages = doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_PAGECOUNT);
		if (numPages != null && !"".equals(numPages)) {
			esQcMain.setNumPages(new Long(numPages));
		}
		// ɨ��ʱ��
		String scanTime = doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANDATE);
		if (scanTime == null || "".equals(scanTime)) {
			esQcMain.setScanDate(FDate.getCurrentDateAndTime());
		} else {
			esQcMain.setScanDate(FDate.formatYMDHMSToDate(scanTime));
		}
		
		esQcMain.setScanOperator(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANUSER));
		esQcMain.setScanCom(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANCOM));
		esQcMain.setManageCom(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_DOC_MANAGECOM));
		//�Ƿ�ģ��-�����ֶ�
		esQcMain.setTempFlag("N");
		//�µ�-Ĭ��ֵ
		esQcMain.setDocFlag(Constants.DOC_QC_ISSUE);
		
		esQcMain.setChannel(uploadIssuePrepareVo.getChannel());
		esQcMain.setBatchNo(batchKey);
		esQcMain.setFu1(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_SCANTYPE));
		//ɨ�跽ʽ-��ݿ�û�и��ֶ�
		/** ��չ����* */
		this.xmlToProp(esQcMain, doc);
		uploadIssuePrepareVo.getEsQcMainList().add(esQcMain);
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
		if("1".equals(esQcMain.getFu1())&&"BQ".equals(esQcMain.getBussType())){
			sql.append("   and propcode  <>'ishurry'");
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
			if(es_PROPERTY_DEFSchema.getListCodeType().contains("priority")){
				esQcMain.setV(es_PROPERTY_DEFSchema.getPropField(), es_PROPERTY_DEFSchema.getListCodeType().split("_")[1]);
			}else{
				esQcMain.setV(es_PROPERTY_DEFSchema.getPropField(), es_PROPERTY_DEFSchema.getCtrlDefaultValue());
			}
		}		
	}

}
