package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.sinosoft.lis.db.ES_PROPERTY_DEFDB;
import com.sinosoft.lis.schema.ES_PROPERTY_DEFSchema;
import com.sinosoft.lis.vschema.ES_PROPERTY_DEFSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.ParentXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpIndexResXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareReqXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.entity.EsQcPages;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.easyscan5.util.SysMaxNoBasic;

public class UpIndexDataConvertUtil extends NewDataConvertServiceImpl implements
		INewDataConvertService {

	private SysMaxNoBasic sysMaxNoBasic = new SysMaxNoBasic();
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 生成返回结果
	 */
	public String vdataToXml(VData data, StringBuffer bufXml) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element result = root.addElement(UpIndexResXmlConstants.XML_RESULT);
			List<EsDocAndPageVO> esDocAndPageVOList = (List<EsDocAndPageVO>) data
					.get(0);
			EsDocAndPageVO vo = esDocAndPageVOList.get(0);
			result.addAttribute(UpIndexResXmlConstants.XML_RESULT_CODE,
					vo.getReturn_Number());
			Element result_message = result
					.addElement(UpIndexResXmlConstants.XML_RESULT_MESSAGE);
			result_message.setText(vo.getReturn_Message());
			Element body = root.addElement(UpIndexResXmlConstants.XML_BODY);
			super.getOutXml(bufXml);
		} catch (RuntimeException e) {
			logger.error("生成返回结果失败", e);
			returnStr = "生成返回结果失败" + e.getMessage();
		}
		return returnStr;
	}

	/**
	 * 解析请求
	 */
	public String xmlToVData(String xml, VData data) {
		String returnStr = null;
		data.clear();
		List<EsDocAndPageVO> esDocAndPageVOList = new ArrayList<EsDocAndPageVO>();
		String channel = "";
		try {
			super.setDocument(xml);
			Document doc = super.getReqDocument();
			List headList = doc.selectNodes(ParentXmlConstants.XML_ROOT + "/"
					+ ParentXmlConstants.XML_HEAD);
			String param = "";
			List channelList = doc.selectNodes(ParentXmlConstants.XML_ROOT
					+ "/" + ParentXmlConstants.XML_HEAD + "/"
					+ UpIndexReqXmlConstants.XML_UPLOADBATCH_CHANNEL);
			Element channelEl = (Element) channelList.get(0);
			channel = channelEl.getText();
			List batchList = doc.selectNodes(UpIndexReqXmlConstants.XML_ROOT
					+ "/" + UpIndexReqXmlConstants.XML_BODY + "/"
					+ UpIndexReqXmlConstants.XML_UPLOADBATCH);
			Element batchEL = (Element) batchList.get(0);
			String batchKey = batchEL
					.attributeValue(UpIndexReqXmlConstants.XML_UPLOADBATCH_BATCHKEY);

			List listGroup = doc.selectNodes(UpIndexReqXmlConstants.XML_ROOT
					+ "/" + UpIndexReqXmlConstants.XML_BODY + "/"
					+ UpIndexReqXmlConstants.XML_UPLOADBATCH + "/"
					+ UpIndexReqXmlConstants.XML_GROUP);
			if (listGroup != null && listGroup.size() > 0) {
				this.parseXmlToEsQcMain(listGroup, esDocAndPageVOList,
						batchKey, channel, param);
			} else {
				logger.error("缺少UPLOADBATCH或GROUP节点");
				returnStr = "缺少UPLOADBATCH或GROUP节点";
			}
		} catch (DocumentException e) {
			logger.error("解析上载请求异常", e);
			returnStr = "解析上载请求出现异常" + e.getMessage();
		} catch (Exception e) {
			logger.error("解析上载请求出现异常", e);
			returnStr = "解析上载请求异常" + e.getMessage();
		}

		data.add(esDocAndPageVOList);
		data.add(channel);
		return returnStr;
	}

	private void parseXmlToEsQcMain(List listGroup, List<EsDocAndPageVO> list,
			String batchKey, String channel, String param) throws Exception {
		for (int i = 0; i < listGroup.size(); i++) {
			Element group = (Element) listGroup.get(i);
			String groupNo = group
					.attributeValue(UpIndexReqXmlConstants.XML_GROUP_CASENO);
			List docs = group.selectNodes(UpIndexReqXmlConstants.XML_DOC);
			if (docs != null && docs.size() > 0) {
				for (int j = 0; j < docs.size(); j++) {
					Element doc = (Element) docs.get(j);
					EsDocAndPageVO esDocAndPageVO = new EsDocAndPageVO();
					this.setEsQcMain(doc, esDocAndPageVO, groupNo, batchKey,
							channel, param);
					list.add(esDocAndPageVO);
				}
			}
		}
	}

	private void setEsQcMain(Element doc, EsDocAndPageVO esDocAndPageVo,
			String groupNo, String batchKey, String channel, String param)
			throws Exception {
		EsQcMain esQcMain = esDocAndPageVo.getEsQcMain();

		esQcMain.setDocId(sysMaxNoBasic.createDocId(esQcMain.getManageCom()));
		esQcMain.setDocCode(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_DOCCODE));
		esQcMain.setPrintCode(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_PRINTCODE));
		esQcMain.setBussType(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_BUSSTYPE));
		esQcMain.setSubType(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_SUBTYPE));
		esQcMain.setScanNo(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_BOXNO));
		String numPages = doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_PAGECOUNT);
		if (numPages != null && !"".equals(numPages)) {
			esQcMain.setNumPages(new Long(numPages));
		}
		String scanTime = doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_SCANDATE);
		if (scanTime == null || "".equals(scanTime)) {
			esQcMain.setScanDate(FDate.getCurrentDateAndTime());
		} else {
			SimpleDateFormat format = new SimpleDateFormat(
					Constants.DATE_TIME_FORMAT);
			esQcMain.setScanDate(format.parse(scanTime));
		}
		esQcMain.setScanOperator(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_SCANUSER));
		esQcMain.setScanCom(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_SCANCOM));
		esQcMain.setManageCom(doc
				.attributeValue(UpIndexReqXmlConstants.XML_DOC_MANAGECOM));
		esQcMain.setTempFlag("N");
		esQcMain.setFu1(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_SCANTYPE));
		esQcMain.setDocRemark(doc
				.attributeValue(UpPrepareReqXmlConstants.XML_PROP2));
		esQcMain.setDocFlag(Constants.DOC_NEW);

		esQcMain.setGroupNo(groupNo);
		esQcMain.setChannel(channel);
		esQcMain.setBatchNo(batchKey);

		this.xmlToProp(esQcMain, doc);
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
		esDocAndPageVo.setEsQcPagesList(qcPagesList);
	}

	private void xmlToProp(EsQcMain esQcMain, Element eDocMain)
			throws Exception {
		List list = eDocMain.selectNodes(UpPrepareReqXmlConstants.XML_PROPS
				+ "/" + UpPrepareReqXmlConstants.XML_PROP);
		StringBuffer sql = new StringBuffer(
				"select * from es_property_def where busstype = '?busstype?' and subtype='?subtype?'");

		ArrayList<String> columnNameArr=new ArrayList<String>();
		List propertyList = new ArrayList();
		if (list != null && list.size() > 0) {
			sql.append(" and propfield not in (?columnName?");
			// String expropertyVersion =
			// expandPropertyUtil.queryExpPropVersion();
			for (int j = 0; j < list.size(); j++) {
				Element pro = (Element) list.get(j);
				String columnName = pro
						.attributeValue(UpPrepareReqXmlConstants.XML_PROP_CODE);
//				if (j == 0) {
//					sql.append("'" + columnName + "'");
//				} else {
//					sql.append(",'" + columnName + "'");
//				}
				columnNameArr.add(columnName);
				String fValue = pro
						.attributeValue(UpPrepareReqXmlConstants.XML_PROP_VALUE);
				// esQcMain.setExpropertyVersion(expropertyVersion);
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
		ES_PROPERTY_DEFSet es_PROPERTY_DEFSet = es_PROPERTY_DEFDB
				.executeQuery(sqlbv);
		for (int i = 1; i <= es_PROPERTY_DEFSet.size(); i++) {
			ES_PROPERTY_DEFSchema es_PROPERTY_DEFSchema = es_PROPERTY_DEFSet
					.get(i);
			if (es_PROPERTY_DEFSchema.getListCodeType().contains("priority")) {
				esQcMain.setV(es_PROPERTY_DEFSchema.getPropField(),
						es_PROPERTY_DEFSchema.getListCodeType().split("_")[1]);
			} else {
				esQcMain.setV(es_PROPERTY_DEFSchema.getPropField(),
						es_PROPERTY_DEFSchema.getCtrlDefaultValue());
			}
		}
	}

	private void setEsQcPages(Element docPages, EsQcPages esQcPages,
			EsQcMain esQcMain) throws Exception {

		esQcPages
				.setPageId(sysMaxNoBasic.createPageId(esQcMain.getManageCom()));
		esQcPages.setDocId(esQcMain.getDocId());
		String pageNo = docPages
				.attributeValue(UpIndexReqXmlConstants.XML_PAGE_PAGECODE);
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
			esQcPages.setScanDate(FDate.formatYMDHMSToDate(scanDate));
		} else {
			esQcPages.setScanDate(new Date());
		}
		esQcPages.setPageFlag("0");
		Node contentNode = docPages
				.selectSingleNode(UpIndexReqXmlConstants.XML_CONTENT);
		if (contentNode != null) {
			String content = contentNode.getText();
			logger.info("content:" + content);
		}
		Node remarkNode = docPages
				.selectSingleNode(UpIndexReqXmlConstants.XML_REMARK);
		if (remarkNode != null) {
			String remark = remarkNode.getText();
			logger.info("remark:" + remark);
		}
	}
}
