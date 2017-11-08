package com.sinosoft.easyscan5.core.service.clientupload.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.f1j.ss.es;
import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.EsDocPagesPToEsQcPage;
import com.sinosoft.easyscan5.core.service.clientupload.UploadIndexIssueService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssueIndexVo;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssuePrepareVo;
import com.sinosoft.easyscan5.entity.EsIssueDoc;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.entity.EsQcPages;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.easyscan5.util.SysMaxNoBasic;
import com.sinosoft.lis.db.ES_COM_SERVERDB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.db.Es_IssueDocDB;
import com.sinosoft.lis.easyscan.CallService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_COM_SERVERSchema;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_PROPERTYSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.schema.Es_IssueDocSchema;
import com.sinosoft.lis.vschema.ES_COM_SERVERSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_PROPERTYSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.lis.vschema.Es_IssueDocSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class UploadIndexIssueServiceImpl extends BaseServiceImpl implements
		UploadIndexIssueService {
	private boolean isCoreIssue;
	private String returnStr = null;
	private UploadIssueIndexVo tempIndexVo;
	private SysMaxNoBasic sysMaxNoBasic = new SysMaxNoBasic();
	private EsIssueDoc esIssueDoc;
	private ES_SERVER_INFOSchema esServerInfo;
	private Es_IssueDocSchema issueSchema;
	private String issueNo;
	private MMap tMMap = new MMap();//所有待提交数据库的数据 都放到该mmap中
	ExeSQL nExeSQL= new ExeSQL();

	public String saveIssueIndex(UploadIssueIndexVo uploadIssueIndexVo)
			throws Exception {
		tempIndexVo = uploadIssueIndexVo;
		issueNo = tempIndexVo.getIssueNo();
		List esqcmainList = tempIndexVo.getEsQcMainList();
		Map esqcPagesMap = tempIndexVo.getEsQcPagesMap();
		
		for (int i = 0; i < esqcmainList.size(); i++) {
			EsQcMain inputesQcMain = (EsQcMain) esqcmainList.get(i);
			if (!issueNotHasDeal()) {
				returnStr = "该问题件已被其他人回销！";
				return returnStr;
			}
			String manageCom = inputesQcMain.getManageCom();
			if (i == 0) {
				getServer(manageCom);
			}
			if (!checkInputData(inputesQcMain, esqcPagesMap)) {
				return returnStr;
			}
		}
		for (int i = 0; i < esqcmainList.size(); i++) {
			EsQcMain inputesQcMain = (EsQcMain) esqcmainList.get(i);
			VData nInputData = new VData();
			if (!upIndex(inputesQcMain, esqcPagesMap,nInputData)) {
				return returnStr;
			}
			if(!bussCheck(nInputData)){
				return returnStr;
			}
		}
		
		VData tVData = new VData();
		tVData.add(tMMap);
		PubSubmit puSubmit = new PubSubmit();
		if (puSubmit.submitData(tVData, "")) {
			returnStr = null;
			return returnStr;
		} else {
			returnStr = "提交数据库失败";
			return returnStr;
		}
	}

	private boolean bussCheck(VData nInputData) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean upIndex(EsQcMain inputesQcMain, Map esqcPagesMap, VData nInputData)
			throws Exception {

		String docId = inputesQcMain.getDocId();

	
		CallService tCallService = new CallService();
		ES_DOC_MAINSet nES_DOC_MAINSet = new ES_DOC_MAINSet();
		ES_DOC_PAGESSet nES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		ES_DOC_PROPERTYSet nES_DOC_PROPERTYSet = new ES_DOC_PROPERTYSet();

		ES_DOC_PAGESSet oldDocPagePSet = queryOldPages(docId);
		ES_DOC_MAINSchema oldDocMainPSchema = queryOldDoc(docId);
		
		List newEsQcPagesList = (List) esqcPagesMap.get(docId);
		String oldScandate = "";
		String oldScanTime = "";
		for (int j = 1; j <= oldDocPagePSet.size(); j++) {
			boolean blnDelete = true;
			ES_DOC_PAGESSchema oldEsQcPages = oldDocPagePSet.get(j);
			oldScandate = oldEsQcPages.getMakeDate();
			oldScanTime = oldEsQcPages.getMakeTime();
			for (int m = 0; m < newEsQcPagesList.size(); m++) {
				EsQcPages esQcPages = (EsQcPages) newEsQcPagesList.get(m);
				String pageId = esQcPages.getPageId();
				if ((pageId != null && !"".equals(pageId))
						&& String.valueOf(oldEsQcPages.getPageID()).equals(
								esQcPages.getPageId())) {
					blnDelete = false;
					break;
				}
			}
			if (blnDelete) {
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("delete from es_doc_pages where pageid='?PageID?'");
				sqlbv.put("PageID", oldEsQcPages.getPageID());
				tMMap.put(sqlbv, "DELETE");
				//增加1张表记录被删除影像
//				savePagesLog(oldEsQcPages, "DELETE");
			}
		}
		Date currentDate = FDate.getCurrentDateAndTime();
		inputesQcMain.setUpdateDate(currentDate);
//		inputesQcMain.setQcStatus(Constants.STATUS_TYPE_WAITQC);
		oldDocMainPSchema.setNumPages(inputesQcMain.getNumPages().intValue());
		oldDocMainPSchema.setModifyDate(FDate.getCurrentDate());
		oldDocMainPSchema.setModifyTime(FDate.getCurrentTime());
		oldDocMainPSchema.setDocFlag("1");
		
		nES_DOC_PROPERTYSet.add(this.getDocPropertySchema(inputesQcMain));
		tMMap.put(nES_DOC_PROPERTYSet, "UPDATE");

		nES_DOC_MAINSet.add(oldDocMainPSchema);
		tMMap.put(oldDocMainPSchema, "UPDATE");

		for (int n = 0; n < newEsQcPagesList.size(); n++) {
			EsQcPages esQcPages = (EsQcPages) newEsQcPagesList.get(n);
			esQcPages.setUpdateDate(currentDate);
			String pageFlag = esQcPages.getPageFlag();
			if (Constants.PAGE_FLAG_CHANGED.equals(pageFlag)) {
				esQcPages.setCreateDate(currentDate);
				esQcPages.setDocId(docId);
				esQcPages.setPageId(sysMaxNoBasic.createPageId(inputesQcMain
						.getManageCom()));
				esQcPages.setPageFlag(Constants.PAGE_FLAG_CHANGED);
			
				String strPath = esQcPages.getPicPath();
				esQcPages.setPicPath(Constants.PIC_PATH + strPath);
				esQcPages.setServerNo(esServerInfo.getHostName());
				
				ES_DOC_PAGESSchema newpagesSchema = new ES_DOC_PAGESSchema();
				EsDocPagesPToEsQcPage.esQcPageToEsDocPagesP(esQcPages,
						newpagesSchema, inputesQcMain.getManageCom(),
						inputesQcMain.getScanNo());
				newpagesSchema.setMakeDate(oldScandate);
				newpagesSchema.setMakeTime(oldScanTime);
				if (".tif".equals(newpagesSchema.getPageSuffix())) {
					newpagesSchema.setPageSuffix(".gif");
				}
				tMMap.put(newpagesSchema, "INSERT");
			} else {

//				for (int mm = 1; mm <= oldDocPagePSet.size(); mm++) {
//					ES_DOC_PAGESSchema oldpage = oldDocPagePSet.get(mm);
//					if (String.valueOf(oldpage.getPageID()).equals(esQcPages.getPageId())) {
//						oldpage.setPageCode(esQcPages.getPageNo().intValue());
//						oldpage.setModifyDate(FDate.getCurrentDate());
//						oldpage.setModifyTime(FDate.getCurrentTime());
//						tMMap.put(oldpage, "UPDATE");
////						savePagesLog(oldpage, "UPDATE");
//					}
//				}
			}
		}
		replyIssue(inputesQcMain);
		for (int k = 0; k < newEsQcPagesList.size(); k++) {
			ES_DOC_PAGESSchema aSchema = new ES_DOC_PAGESSchema();
			EsQcPages esQcPages = (EsQcPages) newEsQcPagesList.get(k);
			EsDocPagesPToEsQcPage.esQcPageToEsDocPagesP(esQcPages, aSchema,
					oldDocMainPSchema.getManageCom(),
					oldDocMainPSchema.getScanNo());
			nES_DOC_PAGESSet.add(aSchema);
		}
		nInputData.add(nES_DOC_MAINSet);
		nInputData.add(nES_DOC_PAGESSet);
		nInputData.add(nES_DOC_PROPERTYSet);
		//调用驱动类
		return true;
		
		
	}

	private void replyIssue(EsQcMain esQcMain) {
		// TODO Auto-generated method stub
		Es_IssueDocDB es_IssueDocDB = new Es_IssueDocDB();
		es_IssueDocDB.setIssueDocID(tempIndexVo.getIssueNo());
		Es_IssueDocSet es_IssueDocSet = es_IssueDocDB.query();
		Es_IssueDocSchema es_IssueDocSchema = es_IssueDocSet.get(1);
		es_IssueDocSchema.setStatus("1");
		es_IssueDocSchema.setResult("1");
		es_IssueDocSchema.setReplyOperator(esQcMain.getScanOperator());
		es_IssueDocSchema.setModifyDate(FDate.getCurrentDate());
		es_IssueDocSchema.setModifyTime(FDate.getCurrentTime());
		tMMap.put(es_IssueDocSchema, "UPDATE");
	}

	/**
	 * ��ѯԭ����֤ҳ��Ϣ
	 * 
	 * @param docid
	 * @return
	 */
	private ES_DOC_PAGESSet queryOldPages(String docid) {
		ES_DOC_PAGESDB es_doc_pagesdb = new ES_DOC_PAGESDB();
		es_doc_pagesdb.setDocID(docid);
		ES_DOC_PAGESSet eSet = es_doc_pagesdb.query();
		return eSet;
	}

	/**
	 * ��ѯԭ����֤��Ϣ
	 * 
	 * @param docid
	 * @return
	 */
	private ES_DOC_MAINSchema queryOldDoc(String docid) {
		ES_DOC_MAINDB es_doc_maindb = new ES_DOC_MAINDB();
		es_doc_maindb.setDocID(docid);
		ES_DOC_MAINSet eSet = es_doc_maindb.query();
		ES_DOC_MAINSchema eSchema = eSet.get(1);
		return eSchema;
	}

	/**
	 * ҵ��У��
	 * 
	 * @return boolean
	 */
	private boolean checkInputData(EsQcMain inputesQcMain, Map esqcPagesMap)
			throws Exception {

		return true;
	}

	/**
	 * ��ȡ��������Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean getServer(String manageCom) throws Exception {
	

        ES_COM_SERVERDB tES_COM_SERVERDB = new ES_COM_SERVERDB();
        tES_COM_SERVERDB.setManageCom(manageCom);
        ES_COM_SERVERSet tES_COM_SERVERSet = tES_COM_SERVERDB.query();
        if (tES_COM_SERVERSet.size() == 0) {
        	returnStr = "管理机构" + manageCom + "没有设置对应的文件服务器";
			return false;
		}
		ES_COM_SERVERSchema escomSchema = tES_COM_SERVERSet.get(1);
		
        ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
        String strHostName = tES_COM_SERVERSet.get(1).getHostName();
        tES_SERVER_INFODB.setHostName(strHostName);

        ES_SERVER_INFOSet tES_SERVER_INFOSet = tES_SERVER_INFODB.query();
		if (tES_SERVER_INFOSet == null || tES_SERVER_INFOSet.size() == 0) {
			returnStr = "获取服务器信息失败，未配置服务器信息！";
			return false;
		}
		esServerInfo = tES_SERVER_INFOSet.get(1);
		return true;
	}

	/**
	 * ��ѯ������Ƿ����
	 * 
	 * @param esQcMain
	 * @return
	 */
	public boolean issueNotHasDeal(){
		Es_IssueDocDB esDocDB = new Es_IssueDocDB();
		esDocDB.setIssueDocID(issueNo);
		Es_IssueDocSet eSet = esDocDB.query();
		Es_IssueDocSchema eSchema = eSet.get(1);
		String qcStatus = eSchema.getStatus();
		if(Constants.STATUS_ISSUE_YES.equals(qcStatus)){
			return true;
		}
		return false;
	}

	private boolean checkPersonFileType(EsQcMain inputEsQcMain,
			EsQcPages esQcPages) {
		// TODO Auto-generated method stub
		ExeSQL nExeSQL = new ExeSQL();
		String subtype = inputEsQcMain.getSubType();
		String pageSuffix = esQcPages.getPageSuffix();
		if (".jpg".equals(pageSuffix)) {
			String querySql = "select 1 from ldcode where codeType = 'uploadFileTypeCheck' and codeName = '?subtype?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(querySql);
			sqlbv.put("subtype", subtype);
			String codeName = nExeSQL.getOneValue(sqlbv);
			if (codeName == null || "".equals(codeName)) {
				return false;
			} else {
				return true;
			}
		}

		return true;
	}
	
	private  ES_DOC_PROPERTYSchema  getDocPropertySchema(EsQcMain inputesQcMain){
		ES_DOC_PROPERTYSchema nES_DOC_PROPERTYSchema = new ES_DOC_PROPERTYSchema();
		nES_DOC_PROPERTYSchema.setP1(inputesQcMain.getP1());
		nES_DOC_PROPERTYSchema.setP2(inputesQcMain.getP2());
		nES_DOC_PROPERTYSchema.setP3(inputesQcMain.getP3());
		nES_DOC_PROPERTYSchema.setP4(inputesQcMain.getP4());
		nES_DOC_PROPERTYSchema.setP5(inputesQcMain.getP5());
		nES_DOC_PROPERTYSchema.setP6(inputesQcMain.getP6());
		nES_DOC_PROPERTYSchema.setP7(inputesQcMain.getP7());
		nES_DOC_PROPERTYSchema.setP8(inputesQcMain.getP8());
		nES_DOC_PROPERTYSchema.setP9(inputesQcMain.getP9());
		nES_DOC_PROPERTYSchema.setP10(inputesQcMain.getP10());
		nES_DOC_PROPERTYSchema.setP11(inputesQcMain.getP11());
		nES_DOC_PROPERTYSchema.setP12(inputesQcMain.getP12());
		nES_DOC_PROPERTYSchema.setP13(inputesQcMain.getP13());
		nES_DOC_PROPERTYSchema.setP14(inputesQcMain.getP14());
		nES_DOC_PROPERTYSchema.setP15(inputesQcMain.getP15());
		nES_DOC_PROPERTYSchema.setP16(inputesQcMain.getP16());
		nES_DOC_PROPERTYSchema.setP17(inputesQcMain.getP17());
		nES_DOC_PROPERTYSchema.setP18(inputesQcMain.getP18());
		nES_DOC_PROPERTYSchema.setP19(inputesQcMain.getP19());
		nES_DOC_PROPERTYSchema.setP20(inputesQcMain.getP20());
		nES_DOC_PROPERTYSchema.setDocID(inputesQcMain.getDocId());
		nES_DOC_PROPERTYSchema.setMakeDate(FDate.getCurrentDate());
		nES_DOC_PROPERTYSchema.setMakeTime(FDate.getCurrentTime());
		nES_DOC_PROPERTYSchema.setDefCode("00000001");
		nES_DOC_PROPERTYSchema.setOperator(inputesQcMain.getScanOperator());
		nES_DOC_PROPERTYSchema.setModifyDate(FDate.getCurrentDate());
		nES_DOC_PROPERTYSchema.setModifyTime(FDate.getCurrentTime());
		return nES_DOC_PROPERTYSchema;
	}
}
