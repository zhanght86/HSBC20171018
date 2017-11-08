package com.sinosoft.easyscan5.core.service.clientupload.impl;

import java.util.List;

import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.EsDocMainPToEsQcMain;
import com.sinosoft.easyscan5.core.service.clientupload.UploadPrepareIssueService;
import com.sinosoft.easyscan5.core.vo.EsDocAndPageVO;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssuePrepareVo;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.db.ES_COM_SERVERDB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.db.Es_IssueDocDB;
import com.sinosoft.lis.schema.ES_COM_SERVERSchema;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.schema.Es_IssueDocSchema;
import com.sinosoft.lis.vschema.ES_COM_SERVERSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.lis.vschema.Es_IssueDocSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * 问题件上载
 * 
 * @author 
 * @date 2013-04-01
 * @version 5.0
 */
public class UploadPrepareIssueServiceImpl extends BaseServiceImpl implements
		UploadPrepareIssueService {
	private String[]  strPage_URL;
    private boolean blnPageNumChanged = false;
	
	private ES_SERVER_INFOSchema esServerInfo;
	private String returnStr = null;
	
	public String uploadIssuePrepare(UploadIssuePrepareVo uploadIssuePrepareVo)
			throws Exception {
		if (!checkInputData(uploadIssuePrepareVo)) {
			return returnStr;
		}
		return returnStr;
	}

	/**
	 * 校验
	 * 
	 * @return boolean
	 */
	private boolean checkInputData(UploadIssuePrepareVo uploadIssuePrepareVo) throws Exception {
		List qcList = uploadIssuePrepareVo.getEsQcMainList();
		for(int i = 0;i < qcList.size();i++){
			EsQcMain esQcMain = (EsQcMain) qcList.get(i);
			
			ES_DOC_MAINSet eset = queryDoc(esQcMain);  
			if (eset.size() == 0) {
				returnStr = "单证" + esQcMain.getDocCode() + "," 
						+ esQcMain.getBussType() + "," + esQcMain.getSubType() + "不存在!";
				return false;
			}
			if(!issueNotHasDeal(uploadIssuePrepareVo)){
				returnStr = "该问题件已经回销!";
				return false;
			}
			
			//busschenck'
			
			if(i == 0){
				if (!getServer(esQcMain.getManageCom())) {
					return false;
				}
				strPage_URL = new String[]{"http://" + esServerInfo.getServerPort() + Constants.UPLOAD_FILE_JSP,
						esQcMain.getManageCom()+"/"+FDate.getCurrentDate().replaceAll("-", "/")+"/"};
			}
		}
		return true;
	}
	/**
	 * 问题件是否回销
	 * @param esQcMain
	 * @return
	 */
	public boolean issueNotHasDeal(UploadIssuePrepareVo uploadIssuePrepareVo){
		Es_IssueDocDB esDocDB = new Es_IssueDocDB();
		esDocDB.setIssueDocID(uploadIssuePrepareVo.getIssueNo());
		Es_IssueDocSet eSet = esDocDB.query();
		Es_IssueDocSchema eSchema = eSet.get(1);
		String qcStatus = eSchema.getStatus();
		if(Constants.STATUS_ISSUE_YES.equals(qcStatus)){
			return true;
		}
		return false;
	}
	/**
	 * ��ѯ��֤
	 * @param esQcMain
	 * @return
	 */
	public ES_DOC_MAINSet queryDoc(EsQcMain esQcMain){
        String  sql ="select * from ES_DOC_MAIN ";
        sql +=" where  DocCode='?DocCode?' ";
        sql += " and BussType='?BussType?' ";
        sql += " and SubType='?SubType?' ";
        sql +=" union ";
        sql +="select * from ES_DOC_MAIN ";
        sql +=" where  exists (select 1 from es_doc_relation c where c.bussno = ES_DOC_MAIN.Doccode  and c.busstype = ES_DOC_MAIN.Busstype"
          +" and c.subtype = ES_DOC_MAIN.Subtype and c.DocCode='?DocCode?'  and c.doccode is not null ";
        sql += " and c.BussType='?BussType?' ";
        sql += " and c.SubType='?SubType?' )";

        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("DocCode", esQcMain.getDocCode());
        sqlbv.put("BussType",esQcMain.getBussType());
        sqlbv.put("SubType", esQcMain.getSubType());
        ES_DOC_MAINDB tES_DOC_MAINDB=new ES_DOC_MAINDB();
        ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.executeQuery(sqlbv);
		return tES_DOC_MAINSet;
	}
	/**
	 * 
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
	public String[] getServerUrl(){
		return strPage_URL;
	}
	
	public static void main(String[] args) {
		
	}
}
