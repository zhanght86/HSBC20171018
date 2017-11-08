package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.easyscan.outsourcing.DocTBImageMoveRelationService;
import com.sinosoft.lis.easyscan.outsourcing.EsDocMoveMS;
import com.sinosoft.lis.pubfun.AbstractBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class BPOReSendBL extends AbstractBL implements BusinessService {
private static Logger logger = Logger.getLogger(BPOReSendBL.class);
	private MMap mMMap = new MMap();

	public boolean dealData() {
		TransferData tTransferData1 = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		String doccode = (String) tTransferData1.getValueByName("DocCode");

		if (doccode == null || doccode.length() != 14) {
			CError.buildErr(this, "prtno error!");
			return false;
		}

		logger.debug("BPOReSendBL is dealing with " + doccode);
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(doccode);
		tES_DOC_MAINDB.setSubType("UA001");
		tES_DOC_MAINDB.setInputState("1");
		ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();
		if (tES_DOC_MAINSet.size() == 0) {
			CError.buildErr(this, "No Such Scan");
			return false;
		}
		if (tES_DOC_MAINSet.size() > 1) {
			CError.buildErr(this, "Scan is too much");
			return false;
		}
		ES_DOC_MAINSchema mES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		tES_DOC_PAGESDB.setDocID(mES_DOC_MAINSchema.getDocID());
		ES_DOC_PAGESSet mES_DOC_PAGESSet = tES_DOC_PAGESDB.query();

		logger.debug("#Start DocOutSourceService...");
		DocTBImageMoveRelationService s = new DocTBImageMoveRelationService();
		VData tVData = new VData();

		ES_DOC_MAINSet tES_DOC_MAINSET = new ES_DOC_MAINSet();
		tES_DOC_MAINSET.add(mES_DOC_MAINSchema);
		tVData.add(tES_DOC_MAINSET);
		tVData.add(mES_DOC_PAGESSet);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_CONTNO,
				mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_MANAGECOM,
				mES_DOC_MAINSchema.getManageCom());
		mES_DOC_MAINSchema.setInputState(null);
		String bpo = BPOChoose.chooseBPO(mES_DOC_MAINSchema);
		if (bpo.equals("")) {// 不需外包
			CError.buildErr(this, "Needn't bpo");
			return false;
		}
		logger.debug("#DocOutSourceService:" + bpo);
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_BPO, bpo);
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("EasyScanShare");
		if (!tLDSysVarDB.getInfo()) {
			CError.buildErr(this, "缺少EasyScanShare目录");
			return false;
		}

		logger.debug(new File(".").getAbsolutePath());
		tTransferData.setNameAndValue(EsDocMoveMS.TEMP_PATH, new File(".")
				.getAbsolutePath());
		tVData.add(tTransferData);
		tVData.add(EsDocMoveMS.class.getName());
		tVData.add("1");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

		tVData.add(tGlobalInput);
		if (!s.submitData(tVData, null)) {
			CError.buildErr(this, s.mErrors.getFirstError());
			return false;
		}
		ActivityOperator tActivityOpertor = new ActivityOperator();
		tTransferData.setNameAndValue("PrtNo", mES_DOC_MAINSchema.getDocCode());// needed by create mission
		if (tActivityOpertor.CreateStartMission("0000000003", "0000001089",
				tVData)) {
			mMMap.add((MMap) ((VData) tActivityOpertor.getResult())
					.getObjectByObjectName("MMap", 0));
		} else {
			// @@错误处理
			CError.buildErr(this, "扫描录入外包工作流节点生成失败!");
			return false;
		}
		String sql = "delete from bpopoldata where bussno='" + "?bussno?"
				+ "'  and bussnotype='TB'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("bussno", doccode);
		mMMap.put(sqlbv, "DELETE");
		
		sql = "delete from bpomissionstate where bussno='" + "?bussno?"
				+ "'  and bussnotype='TB'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("bussno", doccode);
		mMMap.put(sqlbv1, "DELETE");
		
		sql = "delete from bpoerrlog where bussno='" + "?bussno?"
				+ "'  and bussnotype='TB'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("bussno", doccode);
		mMMap.put(sqlbv2, "DELETE");
		
		sql = "delete from bpomissiondetailerror where bussno='" + "?bussno?"
				+ "'  and bussnotype='TB'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("bussno", doccode);
		mMMap.put(sqlbv3, "DELETE");
		
		sql = "delete from bpomissiondetailstate where bussno='" + "?bussno?"
				+ "'  and bussnotype='TB'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("bussno", doccode);
		mMMap.put(sqlbv4, "DELETE");
		
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("update es_doc_main set inputstate=null,managecom=substr(managecom,1,6),modifydate='"
				+ "?modifydate?"
				+ "',modifytime='"
				+ "?modifytime?"
				+ "' where subtype='UA001' and doccode='"
				+ "?doccode?" + "'");
		sqlbv5.put("modifydate", PubFun.getCurrentDate());
		sqlbv5.put("modifytime", PubFun.getCurrentTime());
		sqlbv5.put("doccode", doccode);
		mMMap.put(sqlbv5, "UPDATE");
		
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("insert into DT0319 (COLUMN0, RESULT, UWLEVEL, RULEID) " +
				"values ('"+"?doccode?"+"', '未完结业务', 'F', '0002')");
		sqlbv6.put("doccode", doccode);
		mMMap.put(sqlbv6, "INSERT");
		
		VData ttVData = new VData();
		ttVData.add(mMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(ttVData)) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
		return true;
	}

	@Override
	public CErrors getErrors() {
		return mErrors;
	}
}
