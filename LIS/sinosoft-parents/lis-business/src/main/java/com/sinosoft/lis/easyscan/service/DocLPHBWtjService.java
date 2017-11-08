package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;
import com.sinosoft.workflowengine.WorkFlowUI;

public class DocLPHBWtjService extends BaseService {
private static Logger logger = Logger.getLogger(DocLPHBWtjService.class);

	protected boolean deal() {
		logger.debug("====DocLPHBWtjService start====");
		MMap mMap = new MMap();

		// 扫描回销
		String tPrtSeq = mES_DOC_MAINSchema.getDocCode();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(tPrtSeq);
		if (!tLOPRTManagerDB.getInfo()) {
			throw new IllegalArgumentException("查不到通知书！");
		}

		if (!"1".equals(tLOPRTManagerDB.getStateFlag())) {
			throw new IllegalArgumentException("通知书状态错误:"
					+ tLOPRTManagerDB.getStateFlag());
		}

		if (!checkWTJNO(tLOPRTManagerDB.getCode())) {
			throw new IllegalArgumentException("通知书类型错误:"
					+ tLOPRTManagerDB.getCode());
		}
	

		//tongmeng 2009-04-02 modify
		// "LP90"; // 理赔核保通知书
		// "LP03"; // 理赔体检通知书
		
		String tActivityid = "";
		String tCertifyCode = "";
		String tCode = tLOPRTManagerDB.getCode();
		//modify by lzf 
		LWMissionDB mLWMissionDB = new LWMissionDB();
		LWMissionSet mLWMissionSet = new LWMissionSet();

		
		if(tCode.equals("LP90"))
		{
			if(!"CA101".equals(mES_DOC_MAINSchema.getSubType())){
				CError.buildErr(this, "请使用CA101");
				return false;
			}
			String MissionSQL="select * from lwmission where missionprop3='"+"?missionprop3?"+"' AND MISSIONPROP15='LP90' and activityid in(select activityid from lwactivity where functionid in('10030031'))";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(MissionSQL);
			sqlbv1.put("missionprop3", mES_DOC_MAINSchema.getDocCode());
			mLWMissionSet = mLWMissionDB.executeQuery(sqlbv1);
			
			tActivityid = mLWMissionSet.get(1).getActivityID();
		
			tCertifyCode = "4490";
		}else if(tCode.equals("LP03"))
		{
			if(!"CA102".equals(mES_DOC_MAINSchema.getSubType())){
				CError.buildErr(this, "请使用CA102");
				return false;
			}
			String MissionSQL="select * from lwmission where missionprop3='"+"?missionprop3?"+"' AND  activityid in(select activityid from lwactivity where functionid in('10030023'))";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(MissionSQL);
			sqlbv2.put("missionprop3", mES_DOC_MAINSchema.getDocCode());
			mLWMissionSet = mLWMissionDB.executeQuery(sqlbv2);
			tActivityid = mLWMissionSet.get(1).getActivityID();
			tCertifyCode = "4403";
		}
		else{
			CError.buildErr(this, "通知书类型错误");
			return false;
		}
		
		if (mLWMissionSet == null || mLWMissionSet.size() != 1) {
			CError.buildErr(this, "无发放此单证!");
			return false;
		}
		//end by lzf	
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
	
		// 通过前台得到最后操作数据
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

		// 对输入数据进行验证
//		LWMissionDB mLWMissionDB = new LWMissionDB();
//		LWMissionSet mLWMissionSet = new LWMissionSet();
//		mLWMissionDB.setActivityID(tActivityid);
//		mLWMissionDB.setMissionProp3(tPrtSeq); // 流水号
//		mLWMissionSet = mLWMissionDB.query();
//		if (mLWMissionSet == null || mLWMissionSet.size() != 1) {
//			CError.buildErr(this, "无发放此单证!");
//			return false;
//		}
		
		
		// 获取保全批单号
		String tContNo = tLOPRTManagerDB.getOtherNo();

		// 准备传输数据 VData
		tTransferData.setNameAndValue("CertifyNo",mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue("CertifyCode",tCertifyCode) ;
		tTransferData.setNameAndValue("ContNo",tContNo) ;
		tTransferData.setNameAndValue("GrpContNo",tContNo) ;
		tTransferData.setNameAndValue("MissionID",mLWMissionSet.get(1).getMissionID());
		tTransferData.setNameAndValue("SubMissionID",mLWMissionSet.get(1).getSubMissionID());
		tTransferData.setNameAndValue("TakeBackOperator",mES_DOC_MAINSchema.getScanOperator());
		tTransferData.setNameAndValue("TakeBackMakeDate",mES_DOC_MAINSchema.getMakeDate());
		tTransferData.setNameAndValue("flag", "N");
		//add by lzf
		tTransferData.setNameAndValue("ActivityID", tActivityid);
		tTransferData.setNameAndValue("BusiType", "1003");
		tTransferData.setNameAndValue("CodeType", tCode);
		//end by lzf
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(tPrtSeq);
		mES_DOC_RELATIONSchema.setDocCode(tPrtSeq);
		mES_DOC_RELATIONSchema.setBussNoType("35");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");

		mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(mLWMissionSet.get(1).getMissionProp18());
		mES_DOC_RELATIONSchema.setDocCode(mLWMissionSet.get(1).getMissionProp18());
		mES_DOC_RELATIONSchema.setBussNoType("21");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");


		try {
//			TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
//			if (!tTbWorkFlowUI.submitData(tVData, tActivityid)) {
//				// @@错误处理
//				CError.buildErr(this, "回收通知书失败!", tTbWorkFlowUI.mErrors);
//				return false;
//			}
//			mResult = tTbWorkFlowUI.getResult();
			WorkFlowUI tWorkFlowUI = new WorkFlowUI();
			if (!tWorkFlowUI.submitData(tVData, "execute")) {
				// @@错误处理
				CError.buildErr(this, "回收通知书失败!", tWorkFlowUI.mErrors);
				return false;
			}
			mResult = tWorkFlowUI.getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError.buildErr(this, ex.toString());
			return false;
		}
//		MMap m=(MMap)mResult.getObjectByObjectName("MMap", 0);
//		m.add(mMap);
		mResult.add(mMap);
		return true;
	}

	private boolean checkWTJNO(String code) {
		String[] validnos = new String[] { "LP03", "LP90" };
		for (int i = 0; i < validnos.length; i++) {
			if (validnos[i].equals(code))
				return true;
		}
		return false;
	}

}
