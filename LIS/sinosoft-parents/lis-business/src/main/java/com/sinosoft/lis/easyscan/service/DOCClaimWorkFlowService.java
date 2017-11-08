package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.claim.ClaimWorkFlowUI;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.ActivityOperatorNode;

public class DOCClaimWorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DOCClaimWorkFlowService.class);

	public DOCClaimWorkFlowService(){}
	
	protected boolean deal() {
		String tApplyNo = "";//申请书号
		tApplyNo = mES_DOC_MAINSchema.getDocCode();
		MMap map = new MMap();
		TransferData mTransferData = new TransferData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
		VData tVData = new VData();
		String strlimit = PubFun.getNoLimit(mES_DOC_MAINSchema.getManageCom());
		String mRgtNo = "";
		mRgtNo = PubFun1.CreateMaxNo("RGTNO", strlimit);
		mTransferData.setNameAndValue("RgtNo", mRgtNo);
		mTransferData.setNameAndValue("RgtState", "21");
		mTransferData.setNameAndValue("ApplySubType","CA001");
		mTransferData.setNameAndValue("ApplyNo",tApplyNo);
		mTransferData.setNameAndValue("ScanDate",PubFun.getCurrentDate());
		mTransferData.setNameAndValue("ScanTime",PubFun.getCurrentTime());
		mTransferData.setNameAndValue("ScanOperator",mES_DOC_MAINSchema.getScanOperator());
		mTransferData.setNameAndValue("MngCom",mES_DOC_MAINSchema.getManageCom());
		mTransferData.setNameAndValue("ClaimType", "S");
		mTransferData.setNameAndValue("flag", "N");
		mTransferData.setNameAndValue("PreAuditConclusion", "Scan");
		mTransferData.setNameAndValue("PreAuditor", "Scan");
		mTransferData.setNameAndValue("RgtDate", "Scan");
		
		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		ES_DOC_RELATIONSet tES_DOC_RELATIONSet = new ES_DOC_RELATIONSet();
		mES_DOC_RELATIONSchema.setDocCode(mRgtNo);
		mES_DOC_RELATIONSchema.setBussNo(mRgtNo);
		mES_DOC_RELATIONSchema.setBussNoType("15");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		tES_DOC_RELATIONSet.add(mES_DOC_RELATIONSchema);
		
		mES_DOC_MAINSchema.setDocCode(mRgtNo);
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();
		//创建工作流
		String sql1 = "select busitype,activityid from lwactivity where functionid='10030002'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		if(tSSRS == null || tSSRS.MaxRow == 0){
			CError.buildErr(this, "查询工作流节点失败!");
			return false;
		}
		String BusiType = tSSRS.GetText(1, 1);
		String ActivityID = tSSRS.GetText(1, 2);
		
		String sql2 = "select processid from LWCorresponding where busitype='" + "?BusiType?" +"'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql2);
		sqlbv2.put("BusiType",BusiType);
		String ProcessID = tExeSQL.getOneValue(sqlbv2);
		
		String sql3 = "select version from LWCorresponding where busitype='"+ "?BusiType?" +"'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql3);
		sqlbv3.put("BusiType",BusiType);
		String Version = tExeSQL.getOneValue(sqlbv3);
		if(ProcessID == null || ProcessID.equals("")){
			CError.buildErr(this, "创建立案确认工作流节点信息失败!");
			return false;
		}
		mTransferData.setNameAndValue("Version", Version);
		String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
		String tSubMissionID = "1";
		tVData.add(tGlobalInput);
		tVData.add(mTransferData);
		try{
			ActivityOperatorNode tActivityOperatorNode = new ActivityOperatorNode(ActivityID,ProcessID,Version);
			if(tActivityOperatorNode.Create(tMissionID, tSubMissionID, tVData)){
				mResult = tActivityOperatorNode.getResult();
			}else{
				CError.buildErr(this, "立案确认工作流节点生成失败!");
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			CError.buildErr(this, "立案确认工作流节点生成失败!");
			return false;
		}
//		ActivityOperator tActivityOpertor = new ActivityOperator();
//		if (tActivityOpertor.CreateStartMission("0000000005",
//				"0000005010", tVData)) {
//			mResult = tActivityOpertor.getResult();
//		} else {
//			// @@错误处理
//			CError.buildErr(this, "扫描录入工作流节点生成失败!");
//			return false;
//		}
		
		mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(tApplyNo);
		mES_DOC_RELATIONSchema.setDocCode(tApplyNo);
		mES_DOC_RELATIONSchema.setBussNoType("11"); // by niuzj 20050809
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		tES_DOC_RELATIONSet.add(mES_DOC_RELATIONSchema);
		
		MMap tmap = new MMap();
		for (int i = 0; i < mResult.size(); i++) {
			MMap mmap = (MMap) mResult.getObjectByObjectName("MMap", 0);
			if (tES_DOC_RELATIONSet.size()!=0) {
				mmap.put(tES_DOC_RELATIONSet, "INSERT");
			}
			tmap.add(mmap);
		}
		mResult.add(tmap);
		return true;
	}

}
