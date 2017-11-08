

/**
 * <p>Title: PDProdAudiDetail</p>
 * <p>Description: 产品审核明细</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-18
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.workflow.proddef.ProdDefWorkFlowBL;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.lis.pubfun.*;

public class PDProdAudiDetailBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往前台传输数据的容器 */
	private VData tResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();

	/** 数据操作字符串 */
	private String mOperater;

	private String mManageCom;

	private String mOperate;
	
	private String mRiskCode;
	/** 是否提交标志* */
	private String flag;

	private boolean mFlag = true;

	public PDProdAudiDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		
		try
		{
		
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}	
			
		if (!check(cInputData, cOperate)) {
			return false;
		}

		ProdDefWorkFlowBL tProdDefWorkFlowBL = new ProdDefWorkFlowBL();
		if(!tProdDefWorkFlowBL.submitData(cInputData, cOperate))
		{
			this.mErrors.addOneError(tProdDefWorkFlowBL.mErrors.getFirstError());
			return false;
		}
		}
		catch(Exception ex)
		{
			this.mErrors.addOneError("提交错误:" + ex.getMessage());
			return false;
		}
		
		this.importDataToTestTrack();
		return true;
	}

	private boolean check(VData cInputData, String cOperate) {
		return true;
	}
	
	/**
	 * 向测试要素轨迹表中插入数据
	 * @return
	 */
	public boolean importDataToTestTrack(){
		//通过riskCode查询出产品属于哪个种类
		String tRiskKind = "";
		String sqlKind = "select kindcode from pd_lmriskapp where riskcode = '" + mRiskCode +"'";//判断是否为：1--医疗保险  2--普通疾病保险 3--重大疾病保险 4--失能保险 5--护理保险

		ExeSQL tExeSQL1 = new ExeSQL();
		SSRS tSSRS1 = tExeSQL1.execSQL(sqlKind);
		String tempRiskKind = tSSRS1.GetText(1, 1);
		System.out.println("tempRiskKind:" + tempRiskKind);
		
		if(tempRiskKind != null && !tempRiskKind.trim().equals("L")){//如果是年金保险 R 意外险 A 医疗保险 H 疾病保险（包括重疾） S 直接赋值 如果不是 需要继续查询
			
			sqlKind = "select RiskTypeDetail from pd_lmriskapp where riskcode = '" + mRiskCode + "'";
			tExeSQL1 = new ExeSQL();
			
			tSSRS1 = tExeSQL1.execSQL(sqlKind);
			String tempRiskKind1 = tSSRS1.GetText(1, 1);
			
			if(tempRiskKind1.trim().equals("M")){
				tRiskKind = "H";
			}else{
				tRiskKind = tempRiskKind;
			}
		}else if(tempRiskKind.trim().equals("L")){
			sqlKind = "select RiskType3 from pd_lmriskapp where riskcode = '" + mRiskCode +"'";//判断寿险分类
			tSSRS1 = tExeSQL1.execSQL(sqlKind);
			tempRiskKind = tSSRS1.GetText(1, 1);
			System.out.println("tempRiskKind:" + tempRiskKind);
			tRiskKind = tempRiskKind;
		}
					
		//判断轨迹表中是否有数据，有数据删掉，没数据插入
		String countSQL = "select count(*) from pd_testtrack where testplankind = '" + mRiskCode + "'";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(countSQL);
		String countNumber = tSSRS.GetText(1, 1);
		System.out.println("countNumber:" + countNumber);
		
		if(countNumber == null || countNumber.trim().equals("")){
			CError tError = new CError();
			tError.moduleName = "PDProdAudiDetailBL.java";
			tError.functionName = "importDataToTestTrack";
			tError.errorMessage = "查询轨迹表失败!";
			this.mErrors.addOneError(tError);
			
			return false;
		}
		
		//countNumber不等于0，说明有数据，删掉原来数据
		if(countNumber != null && !countNumber.trim().equals("0")){
			String deleteSQL = "delete pd_testtrack where testplankind = '" + mRiskCode + "'";
			if(!tExeSQL.execUpdateSQL(deleteSQL)){
				CError tError = new CError();
				tError.moduleName = "PDProdAudiDetailBL.java";
				tError.functionName = "importDataToTestTrack";
				tError.errorMessage = "执行删除轨迹表数据失败!";
				this.mErrors.addOneError(tError);
			}
		}
		//插入公共要素
		PD_TestPlanClew_LibDB tPD_TestPlanClew_LibDB = new PD_TestPlanClew_LibDB(); 
		String commonSQL = "select * from PD_TESTPLANCLEW_LIB t where t.testplankind = '1'";
		PD_TestPlanClew_LibSet tPD_TestPlanClew_LibSetCommon = tPD_TestPlanClew_LibDB.executeQuery(commonSQL);
		for(int i = 1; i <= tPD_TestPlanClew_LibSetCommon.size(); i ++){
			PD_TestPlanClew_LibSchema tPD_TestPlanClew_LibSchema = (PD_TestPlanClew_LibSchema)tPD_TestPlanClew_LibSetCommon.getObj(i);
			PD_TestTrackSchema tPD_TestTrackSchema = new PD_TestTrackSchema();
			tPD_TestTrackSchema.setCLEWCONTENTCODE(tPD_TestPlanClew_LibSchema.getClewContentCode());
			tPD_TestTrackSchema.setCLEWCONTENTNAME(tPD_TestPlanClew_LibSchema.getClewContent());
			tPD_TestTrackSchema.setBATCHNUM("BatchNum");
			tPD_TestTrackSchema.setTESTPLANKIND(mRiskCode);
			tPD_TestTrackSchema.setTESTSTATE("0");
			tPD_TestTrackSchema.setMAKEDATE(PubFun.getCurrentDate());
			tPD_TestTrackSchema.setMAKETIME(PubFun.getCurrentTime());
			tPD_TestTrackSchema.setMODIFYDATE(PubFun.getCurrentDate());
			tPD_TestTrackSchema.setMODIFYTIME(PubFun.getCurrentTime());
			
			tPD_TestTrackSchema.setOPERATOR(mOperater);
			
			PD_TestTrackDB tPD_TestTrackDB = new PD_TestTrackDB();
			tPD_TestTrackDB.setSchema(tPD_TestTrackSchema);
			tPD_TestTrackDB.insert();
		}
			
		//根据种类查出需要插入的测试要素
		String sql = "Select * from PD_TESTPLANCLEW_LIB t where t.testplanriskkind = '" + tRiskKind + "'";
		PD_TestPlanClew_LibSet tPD_TestPlanClew_LibSchemaSet = tPD_TestPlanClew_LibDB.executeQuery(sql);
		
		//将查出的测试要素插入到测试轨迹表中
		for(int i = 1; i <= tPD_TestPlanClew_LibSchemaSet.size(); i ++){
			PD_TestPlanClew_LibSchema tPD_TestPlanClew_LibSchema = (PD_TestPlanClew_LibSchema)tPD_TestPlanClew_LibSchemaSet.getObj(i);
			PD_TestTrackSchema tPD_TestTrackSchema = new PD_TestTrackSchema();
			tPD_TestTrackSchema.setCLEWCONTENTCODE(tPD_TestPlanClew_LibSchema.getClewContentCode());
			tPD_TestTrackSchema.setCLEWCONTENTNAME(tPD_TestPlanClew_LibSchema.getClewContent());
			tPD_TestTrackSchema.setBATCHNUM("BatchNum");
			System.out.println(mRiskCode);
			tPD_TestTrackSchema.setTESTPLANKIND(mRiskCode);
			tPD_TestTrackSchema.setTESTSTATE("0");
			tPD_TestTrackSchema.setMAKEDATE(PubFun.getCurrentDate());
			tPD_TestTrackSchema.setMAKETIME(PubFun.getCurrentTime());
			tPD_TestTrackSchema.setMODIFYDATE(PubFun.getCurrentDate());
			tPD_TestTrackSchema.setMODIFYTIME(PubFun.getCurrentTime());
			
			tPD_TestTrackSchema.setOPERATOR(mOperater);
			
			PD_TestTrackDB tPD_TestTrackDB = new PD_TestTrackDB();
			tPD_TestTrackDB.setSchema(tPD_TestTrackSchema);
			tPD_TestTrackDB.insert();
		}
		return true;
	}
	
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		System.out.println("+++" + mOperater);
		if ((mOperater == null) || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if ((mManageCom == null) || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得操作类型
		mOperate = cOperate;
		if ((mOperate == null) || mOperate.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate任务节点编码失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		
		flag = (String) mTransferData.getValueByName("flag");
		if (flag != null) {
			if (flag.equals("N")) {
				mFlag = false;
			}
		}
		
		new RiskState().setState(mRiskCode, "产品审核->产品审核","1");

		return true;
	}

	public static void main(String[] args) {
		 PDProdAudiDetailBL tPDProdAudiDetailBL = new PDProdAudiDetailBL();
		 TransferData transferData = new TransferData();
		 transferData.setNameAndValue("RiskNo", "20090323");
		 transferData.setNameAndValue("RequDate", "2009-03-23");
		 transferData.setNameAndValue("Operator", "temp");
		 transferData.setNameAndValue("MissionID", "00000000000000007224");
		 transferData.setNameAndValue("SubMissionID","1");
		 transferData.setNameAndValue("ActivityID","pd00000000");
		 transferData.setNameAndValue("SubMissionID", "2");
		 transferData.setNameAndValue("ActivityID", "pd00000001");
		 transferData.setNameAndValue("IsBaseInfoDone", "1");
		 transferData.setNameAndValue("RiskCode", "1056");
		 VData tVData = new VData();
		
		 GlobalInput tG = new GlobalInput();
		 tG.Operator = "001";
		 tG.ManageCom = "86";
		 tVData.add(tG);
		 tVData.add(transferData);
		 tPDProdAudiDetailBL.submitData(tVData, "save");
	}
}
