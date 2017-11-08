package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 投保单证上载工作流处理接口实现类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.easyscan.RelationConfig;
import com.sinosoft.lis.easyscan.outsourcing.DocTBImageMoveRelationService;
import com.sinosoft.lis.easyscan.outsourcing.EsDocMoveMS;
import com.sinosoft.lis.easyscan.service.util.PolStateCheck;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.tb.BPOChoose;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.service.BusinessDelegate;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.ActivityOperatorNode;
import com.sinosoft.workflowengine.WorkFlowUI;

public class DocTBWorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBWorkFlowService.class);

	protected boolean deal() {
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();

		tTransferData.setNameAndValue("PrtNo", mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue("InputDate", mES_DOC_MAINSchema
				.getMakeDate());
		tTransferData.setNameAndValue("ScanOperator", mES_DOC_MAINSchema
				.getScanOperator());
		tTransferData.setNameAndValue("ManageCom", mES_DOC_MAINSchema
				.getManageCom());
		tTransferData.setNameAndValue("UFlag", "0");
		tTransferData.setNameAndValue("ScanCom", mES_DOC_MAINSchema
				.getScanOperator());
		tTransferData.setNameAndValue("InputTime", "0");
		tTransferData.setNameAndValue("ScanDate", mES_DOC_MAINSchema
				.getMakeDate());
		tTransferData.setNameAndValue("ScanTime", mES_DOC_MAINSchema
				.getMakeTime());
		//tTransferData.setNameAndValue("DefaultOperator", mES_DOC_MAINSchema
		//		.getScanOperator());
		// 必须先经过转化 by wellhi 2005.06.19
		RelationConfig nRelationConfig = RelationConfig.getInstance();
		String strSubType = nRelationConfig.getBackRelation(mES_DOC_MAINSchema
				.getSubType());
		tTransferData.setNameAndValue("SubType", strSubType);

		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

//		tVData.add(tGlobalInput);
//		tVData.add(tTransferData);

		/**
		 * 扫描单证细类选择为“投保单类”时，上载时系统对印刷号进行校验，
		 * 如校验系统中该印刷号已有录入出单信息，系统自动将其扫描影像件与录入的保单信息进行关联，不进入录入外包流程；
		 */
		// 查询是否已经录单
		if(PolStateCheck.isPolInSystem(mES_DOC_MAINSchema.getDocCode())){
			if(PolStateCheck.isPolAvailable(mES_DOC_MAINSchema.getDocCode())){
				CError.buildErr(this, "保单已录入，禁止再扫描投保单");
				return false;
			}else{
				// reset the maketime
				String sql="select makedate,maketime,operator from lccont where prtno='"+"?prtno?"+"'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("prtno", mES_DOC_MAINSchema.getDocCode());
				SSRS tSSRS = new ExeSQL().execSQL(sqlbv1);
				Object objDate=tSSRS.GetText(1, 1);
				Object objTime=tSSRS.GetText(1, 2);
				mES_DOC_MAINSchema.setInputState("1");
				mES_DOC_MAINSchema.setOperator(tSSRS.GetText(1, 3));
				mES_DOC_MAINSchema.setInputStartDate(tSSRS.GetText(1, 1));
				mES_DOC_MAINSchema.setInputStartTime(tSSRS.GetText(1, 2));
				if(objDate!=null && !objDate.equals("null") && !objDate.equals(""))
					mES_DOC_MAINSchema.setMakeDate((String)objDate);
				if(objTime!=null && !objTime.equals("null") && !objTime.equals(""))
					mES_DOC_MAINSchema.setMakeTime((String)objTime);
				return true;
			}
		}
		if(PolStateCheck.isPolBpo(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "保单正在外包，禁止再扫描投保单");
			return false;
		}
		if(PolStateCheck.isPolHasWorkflow(mES_DOC_MAINSchema.getDocCode())){
			return true;
		}

		if (!doBPO(mInputData, mOperate)) {
//			ActivityOperator tActivityOpertor = new ActivityOperator();
//			if (tActivityOpertor.CreateStartMission("0000000003",
//					chooseProcessID(), tVData)) {
//				mResult = tActivityOpertor.getResult();
//			} else {
//				// @@错误处理
//				CError.buildErr(this, "扫描录入工作流节点生成失败!");
//				return false;
//			}
			//add by lizhifeng 2013-03-03			
			ExeSQL tExeSQL = new ExeSQL();
			String sql2 = "select busitype,activityflag,functionid from lwactivity where activityid='"+"?activityid?" +"'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql2);
			sqlbv2.put("activityid", chooseProcessID());
			SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
			if(tSSRS == null || tSSRS.MaxRow == 0){
				CError tCError = new CError();
				tCError.moduleName = "DocTBWorkFlowService";
				tCError.functionName = "dealData";
				tCError.errorMessage = "查询工作流节点失败!";
				this.mErrors.addOneError(tCError);
				return false;
			}
			String BusiType = tSSRS.GetText(1, 1);
			String ActivityFlag = tSSRS.GetText(1, 2);
			String FunctionID = tSSRS.GetText(1, 3);
			String sql3 = "select processid from LWCorresponding where busitype='"+ "?BusiType?" +"'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql3);
			sqlbv3.put("BusiType", BusiType);
			String ProcessID = tExeSQL.getOneValue(sqlbv3);
			
			String sql4 = "select version from LWCorresponding where busitype='"+ "?BusiType?" +"'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql4);
			sqlbv4.put("BusiType", BusiType);
			String Version = tExeSQL.getOneValue(sqlbv4);
			if(ProcessID == null || ProcessID.equals("")){
				CError tCError = new CError();
				tCError.moduleName = "WorkFlowBL";
				tCError.functionName = "dealData";
				tCError.errorMessage = "查询工作流流程失败!";
				this.mErrors.addOneError(tCError);
				return false;
			}
			tTransferData.setNameAndValue("Version", Version);
			String tMissionID = PubFun1.CreateMaxNo("MissionID", 20);
			String tSubMissionID = "1";
			tVData.add(tGlobalInput);
			tVData.add(tTransferData);
			MMap tMap;
			try{
				ActivityOperatorNode tActivityOperatorNode = new ActivityOperatorNode(chooseProcessID(),ProcessID,Version);
				if(tActivityOperatorNode.Create(tMissionID, tSubMissionID, tVData)){
					VData mVData = new VData();
					mVData = tActivityOperatorNode.getResult();
					tMap = (MMap)mVData.getObjectByObjectName("MMap", 0);
					if(tMap == null){
						CError.buildErr(this, "接受新契约工作流节点信息失败!");
						return false;
					}else{
						mResult.add(tMap);						
					}
				}else{
					CError.buildErr(this, "新契约扫描申请录入工作流节点生成失败!");
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
				CError.buildErr(this, "新契约扫描申请录入工作流节点生成失败!");
				return false;
			}			
			//end by lizhifeng 213-03-03			
		}
		return true;
	}

	private static String TBProcess = "0000001401";

	static {
		// 为了保留原来的流程便于可能的切换操作，特加以下处理
		TBProcess = "0000001401";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("TBProcess");
		if (tLDSysVarDB.getInfo()) {
			if ("1".equals(tLDSysVarDB.getSysVarValue())) {
				TBProcess = "0000001099";
			}
		}
	}

	private String chooseProcessID() {
		return TBProcess;
	}

	/**
	 * @param cInputData
	 * @param operate
	 * @return 需要外包返回true, 不需要外包返回false
	 */
	private boolean doBPO(VData cInputData, String operate) {
		logger.debug("#Start DocOutSourceService...");
		//tongmeng 2011-08-05 modify
		//暂时注释掉外包的接口调用 
		//DocTBImageMoveRelationService s = new DocTBImageMoveRelationService();
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
		String bpo = BPOChoose.chooseBPO(mES_DOC_MAINSchema);
		if (bpo.equals("")) {// 不需外包
			return false;
		}
		logger.debug("#DocOutSourceService:" + bpo);
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_BPO, bpo);
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("EasyScanShare");
		if (!tLDSysVarDB.getInfo()) {
			throw new RuntimeException("缺少EasyScanShare目录");
		}

		tTransferData.setNameAndValue(EsDocMoveMS.TEMP_PATH, tLDSysVarDB
				.getSysVarValue());
		tVData.add(tTransferData);
		tVData.add(EsDocMoveMS.class.getName());
		tVData.add("1");
		// BPOServerInfoDB tBPOServerInfoDB=new BPOServerInfoDB();
		// tBPOServerInfoDB.setBPOID(bpo);
		// if(!tBPOServerInfoDB.getInfo()){
		// CError.buildErr(this, "缺少BPO描述信息");
		// return false;
		// }
		// tVData.add(tBPOServerInfoDB.getSuffix());
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

		tVData.add(tGlobalInput);
		//if (!s.submitData(tVData, null)) {
		//	throw new RuntimeException(s.mErrors.getFirstError());
		//}
		ActivityOperator tActivityOpertor = new ActivityOperator();
		tTransferData.setNameAndValue("PrtNo", mES_DOC_MAINSchema.getDocCode());// needed by create mission
		if (tActivityOpertor.CreateStartMission("0000000003", "0000001089",
				tVData)) {
			mResult = tActivityOpertor.getResult();
		} else {
			// @@错误处理
			throw new RuntimeException("扫描录入外包工作流节点生成失败!");
		}
		return true;
	}

}
