package com.sinosoft.easyscan5.core.controller.adapter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.easyscan5.common.XmlConstants;
import com.sinosoft.easyscan5.core.controller.autoupdate.AutoUpdateInfoAction;
import com.sinosoft.easyscan5.core.controller.centerinfo.GetCenterInfoAction;
import com.sinosoft.easyscan5.core.controller.clientupload.ClientUploadCommonFun;
import com.sinosoft.easyscan5.core.controller.clientupload.NewUploadIndexAction;
import com.sinosoft.easyscan5.core.controller.clientupload.NewUploadPrepareAction;
import com.sinosoft.easyscan5.core.controller.getscanno.GetNewScanNoAction;
import com.sinosoft.easyscan5.core.controller.getscanno.QueryHisScanNoAction;
import com.sinosoft.easyscan5.core.controller.license.GetLicenseAction;
import com.sinosoft.easyscan5.core.controller.queryissue.QueryIssueAction;
import com.sinosoft.easyscan5.core.controller.querypages.QueryPagesAction;
import com.sinosoft.easyscan5.core.controller.uploadissue.UploadIndexIssueAction;
import com.sinosoft.easyscan5.core.controller.uploadissue.UploadPrepareIssueAction;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.ClientHeadDataConvertUtil;

public class EasyScanAdapterBL {

	private ClientHeadDataConvertUtil clientHeadDataConvertUtil = new ClientHeadDataConvertUtil();
	private String IndexXML;
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 
	 * 
	 * @param request
	 * @param indexXML
	 * @return
	 * @throws Exception
	 */
	public String execute(HttpServletRequest request, String indexXML) throws Exception {
		IndexXML = indexXML;
		Map<String, String> head = new HashMap<String, String>();
		clientHeadDataConvertUtil.xmlToHeadMap(IndexXML, head);
		logger.info("IndexXML=" + IndexXML);
		if (head == null) {
			logger.info("EasyScanAdapterBL--execute()--报文头不能为空");
			return headError();
		}
		String name = head.get(XmlConstants.CON_XML_NAME);
		String version = head.get(XmlConstants.CON_XML_VERSION);
		String actionCode = head.get(XmlConstants.CON_XML_ACTIONCODE);
		String str = name + version + "$" + actionCode;
		logger.info("easyscan forward path:" + str);
		if (head.keySet().size() == 0 || name == null || version == null || actionCode == null || "".equals(name)
				|| "".equals(version) || "".equals(actionCode)) {
			return headError();
		}

		if ("EASYSCAN3.3.0$1009".endsWith(str)) {
			NewUploadPrepareAction newUploadPrepareAction = new NewUploadPrepareAction();
			newUploadPrepareAction.setIndexXML(indexXML);
			return newUploadPrepareAction.uploadPrepare();
		} else if ("EASYSCAN3.3.0$1010".endsWith(str)) {
			NewUploadIndexAction newUploadIndexAction = new NewUploadIndexAction();
			newUploadIndexAction.setIndexXML(indexXML);
			return newUploadIndexAction.uploadIndex();
		} else if ("EASYSCAN3.3.0$0000".endsWith(str)) {
			GetLicenseAction getLicenseAction = new GetLicenseAction();
			getLicenseAction.setIndexXML(indexXML);
			return getLicenseAction.getLicense(request);
		} else if ("EASYSCAN3.3.0$0001".endsWith(str)) {
			AutoUpdateInfoAction autoUpdateInfoAction = new AutoUpdateInfoAction();
			autoUpdateInfoAction.setIndexXML(indexXML);
			return autoUpdateInfoAction.getAutoUpdateInfo(request);
		} else if ("EASYSCAN3.3.0$1001".endsWith(str)) {
			GetCenterInfoAction centerInfoAction = new GetCenterInfoAction();
			centerInfoAction.setIndexXML(indexXML);
			return centerInfoAction.getCenterSetting(request);
		} else if ("EASYSCAN3.3.0$1004".endsWith(str)) {
			GetNewScanNoAction getNewScanNoAction= new GetNewScanNoAction();
			getNewScanNoAction.setIndexXML(indexXML);
			return getNewScanNoAction.getNewScanNo();
		} else if ("EASYSCAN3.3.0$1005".endsWith(str)) {
			QueryHisScanNoAction queryHisScanNoAction= new QueryHisScanNoAction();
			queryHisScanNoAction.setIndexXML(indexXML);
			return queryHisScanNoAction.queryHisScanNo();
		} else if ("EASYSCAN3.3.0$1006".endsWith(str)) {
			QueryIssueAction nQueryIssueAction= new QueryIssueAction();
			nQueryIssueAction.setIndexXML(indexXML);
			return nQueryIssueAction.queryIssue();
		} else  if ("EASYSCAN3.3.0$1003".endsWith(str)) {
			QueryPagesAction nQueryPagesAction= new QueryPagesAction();
			nQueryPagesAction.setIndexXML(indexXML);
			return nQueryPagesAction.queryPages();
		} else if ("EASYSCAN3.3.0$1011".endsWith(str)) {
			UploadPrepareIssueAction nUploadPrepareIssueAction= new UploadPrepareIssueAction();
			nUploadPrepareIssueAction.setIndexXML(indexXML);
			return nUploadPrepareIssueAction.uploadPrepare();
		} else if ("EASYSCAN3.3.0$1012".endsWith(str)) {
			UploadIndexIssueAction nUploadIndexIssueAction= new UploadIndexIssueAction();
			nUploadIndexIssueAction.setIndexXML(indexXML);
			return nUploadIndexIssueAction.uploadIndex();
		} else{
			return headError();
		}

	}

	/**
	 * 拼装报文头错误信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String headError() throws Exception {
		ClientUploadCommonFun clientUploadCommonFun = new ClientUploadCommonFun();
		String strErr = "EasyScanAdapterAction--影像跳转失败！\n:报文格式有误或头部信息不完整";
		String returnXml = clientUploadCommonFun.getReturnMessage(strErr);
		return returnXml;
	}

	public String getIndexXML() {
		return IndexXML;
	}

	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}

}
