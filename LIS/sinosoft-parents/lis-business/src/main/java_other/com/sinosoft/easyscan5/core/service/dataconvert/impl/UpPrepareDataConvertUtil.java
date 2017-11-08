package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.sinosoft.lis.db.ES_PROPERTY_DEFDB;
import com.sinosoft.lis.schema.ES_PROPERTY_DEFSchema;
import com.sinosoft.lis.vdb.ES_PROPERTY_DEFDBSet;
import com.sinosoft.lis.vschema.ES_PROCESS_DEFSet;
import com.sinosoft.lis.vschema.ES_PROPERTY_DEFSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.util.FDate;

public class UpPrepareDataConvertUtil extends NewDataConvertServiceImpl implements INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());
	public String channel = "";

	/**
	 * 生成结果报文
	 */
	public String vdataToXml(VData data, StringBuffer bufXml) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element result = root.addElement(UpPrepareResXmlConstants.XML_RESULT);
			List<EsDocAndPageVO> esDocAndPageVOList = (List<EsDocAndPageVO>) data.get(0);
			EsDocAndPageVO vo = esDocAndPageVOList.get(0);
			result.addAttribute(UpPrepareResXmlConstants.XML_RESULT_CODE, vo.getReturn_Number());
			Element result_message = result.addElement(UpPrepareResXmlConstants.XML_RESULT_MESSAGE);
			result_message.setText(vo.getReturn_Message());
			Element body = root.addElement(UpPrepareResXmlConstants.XML_BODY);
			if ("0".equals(vo.getReturn_Number())) {
				Element fileurl = body.addElement(UpPrepareResXmlConstants.XML_FILEURL);
				String[] path = vo.getPage_URL();
				fileurl.addAttribute(UpPrepareResXmlConstants.XML_FILEURL_PAGEURL, (path != null) ? path[0] : "");
				fileurl.addAttribute(UpPrepareResXmlConstants.XML_FILEURL_FILEPATH, (path != null) ? path[1] : "");
			}
			super.getOutXml(bufXml);
		} catch (RuntimeException e) {
			logger.error("生成返回结果异常", e);
			returnStr = "生成返回结果异常" + e.getMessage();
		}
		return returnStr;
	}

	/**
	 * 解析请求报文
	 */
	public String xmlToVData(String xml, VData data) {
		String returnStr = null;
		data.clear();
		List<EsDocAndPageVO> esDocAndPageVOList = new ArrayList<EsDocAndPageVO>();
		String param = "";
		try {
			super.setDocument(xml);
			Document doc = super.getReqDocument();
			channel = getChannel(doc);
			List batchList = doc.selectNodes(UpPrepareReqXmlConstants.XML_ROOT + "/" + UpPrepareReqXmlConstants.XML_BODY
					+ "/" + UpPrepareReqXmlConstants.XML_UPLOADBATCH);
			String batchKey = "";
			// if (batchList != null && batchList.size() > 0) {
			// Element batchNode = (Element) batchList.get(0);
			// batchKey = batchNode
			// .attributeValue(UpPrepareReqXmlConstants.XML_RELATION);
			// } else {
			// return returnStr;
			// }
			List listGroup = doc.selectNodes(UpPrepareReqXmlConstants.XML_ROOT + "/" + UpPrepareReqXmlConstants.XML_BODY
					+ "/" + UpPrepareReqXmlConstants.XML_UPLOADBATCH + "/" + UpPrepareReqXmlConstants.XML_GROUP);
			if (listGroup != null && listGroup.size() > 0) {
				this.parseXmlToEsQcMain(listGroup, esDocAndPageVOList, batchKey, channel, param);
			} else {
				logger.error("缺少UPLOADBATCH或GROUP节点");
				returnStr = "缺少UPLOADBATCH或GROUP节点";
			}
		} catch (DocumentException e) {
			logger.error("解析请求异常", e);
			returnStr = "解析请求异常" + e.getMessage();
		} catch (Exception e) {
			logger.error("解析请求异常", e);
			returnStr = "解析请求异常" + e.getMessage();
		}

		data.add(esDocAndPageVOList);
		data.add(param);
		return returnStr;
	}

	/*
	 * ����EsQcMain
	 */
	private void parseXmlToEsQcMain(List listGroup, List<EsDocAndPageVO> list, String batchKey, String channel,
			String param) throws Exception {
		for (int i = 0; i < listGroup.size(); i++) {
			Element group = (Element) listGroup.get(i);
			String groupNo = group.attributeValue(UpPrepareReqXmlConstants.XML_GROUP_CASENO);
			List docs = group.selectNodes(UpPrepareReqXmlConstants.XML_DOC);
			if (docs != null && docs.size() > 0) {
				for (int j = 0; j < docs.size(); j++) {
					Element doc = (Element) docs.get(j);
					EsDocAndPageVO esDocAndPageVO = new EsDocAndPageVO();
					this.setEsQcMain(doc, esDocAndPageVO, groupNo, batchKey, channel, param);
					list.add(esDocAndPageVO);
				}
			}
		}
	}

	private void setEsQcMain(Element doc, EsDocAndPageVO esDocAndPageVo, String groupNo, String batchKey,
			String channel, String param) throws Exception {
		EsQcMain esQcMain = esDocAndPageVo.getEsQcMain();
		batchKey = doc.attributeValue(UpPrepareReqXmlConstants.XML_RELATION);
		esQcMain.setDocCode(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_DOCCODE));
		esQcMain.setGroupNo(groupNo);
		esQcMain.setPrintCode(doc.attributeValue(UpIndexReqXmlConstants.XML_DOC_PRINTCODE));
		esQcMain.setBussType(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_BUSSTYPE));
		esQcMain.setSubType(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SUBTYPE));
		esQcMain.setScanNo(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_BOXNO));
		String scanType = doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANTYPE);
		String numPages = doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_PAGECOUNT);
		if (numPages != null && !"".equals(numPages)) {
			esQcMain.setNumPages(new Long(numPages));
		}
		// ɨ��ʱ��
		String scanTime = doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANDATE);
		if (scanTime == null || "".equals(scanTime)) {
			esQcMain.setScanDate(FDate.getCurrentDateAndTime());
		} else {
			esQcMain.setScanDate(FDate.formatYMDHMSToDate(scanTime));
		}

		esQcMain.setScanOperator(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANUSER));
		esQcMain.setScanCom(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_SCANCOM));
		esQcMain.setManageCom(doc.attributeValue(UpPrepareReqXmlConstants.XML_DOC_MANAGECOM));
		esQcMain.setFu1(doc.attributeValue(UpPrepareReqXmlConstants.XML_SCANTYPE));
		esQcMain.setDocRemark(doc.attributeValue(UpPrepareReqXmlConstants.XML_PROP2));
		esQcMain.setTempFlag("N");
		esQcMain.setDocFlag(Constants.DOC_NEW);

		esQcMain.setChannel(channel);
		esQcMain.setBatchNo(batchKey);
		// esQcMain.setFu1(param);
		esDocAndPageVo.setScanType(scanType);
		this.xmlToProp(esQcMain, doc);
	}

	private void xmlToProp(EsQcMain esQcMain, Element eDocMain) throws Exception {
		List list = eDocMain.selectNodes(UpPrepareReqXmlConstants.XML_PROPS + "/" + UpPrepareReqXmlConstants.XML_PROP);
		StringBuffer sql = new StringBuffer("select * from es_property_def where busstype = '?busstype?' and subtype='?subtype?'");
		ArrayList<String> columnNameArr=new ArrayList<String>();
		List propertyList = new ArrayList();
		if (list != null && list.size() > 0) {
			sql.append(" and propfield not in (?columnName?");
			for (int j = 0; j < list.size(); j++) {
				Element pro = (Element) list.get(j);
				String columnName = pro.attributeValue(UpPrepareReqXmlConstants.XML_PROP_CODE);
//				if (j == 0) {
//					sql.append("'" + columnName + "'");
//				} else {
//					sql.append(",'" + columnName + "'");
//				}
				columnNameArr.add(columnName);
				String fValue = pro.attributeValue(UpPrepareReqXmlConstants.XML_PROP_VALUE);
				// esQcMain.setExpropertyVersion("1");
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
		ES_PROPERTY_DEFSet es_PROPERTY_DEFSet = es_PROPERTY_DEFDB.executeQuery(sqlbv);
		for (int i = 1; i <= es_PROPERTY_DEFSet.size(); i++) {
			ES_PROPERTY_DEFSchema es_PROPERTY_DEFSchema = es_PROPERTY_DEFSet.get(i);
			esQcMain.setV(es_PROPERTY_DEFSchema.getPropField(), es_PROPERTY_DEFSchema.getCtrlDefaultValue());
		}
	}

}
