package com.sinosoft.workflow.proddef;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title: 产品定义工作流
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */
public class ProdDefWorkFlowBL implements BusinessService{
private static Logger logger = Logger.getLogger(ProdDefWorkFlowBL.class);
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

	public ProdDefWorkFlowBL() {
	}

	/**
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		logger.debug("---ProdDefWorkFlowBL getInputData---");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---ProdDefWorkFlowBL dealData---");

		// 准备给后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---ProdDefWorkFlowBL prepareOutputData---");

		if (mFlag) { // 如果置相应的标志位，不提交
			// 数据提交
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProdDefWorkFlowBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
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

		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mOperate.trim().equals("Start")) {
			if (!CreateMission()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} else { // 执行具体工作流任务
			if (!ExecuteMission()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		}

		return true;
	}

	/**
	 * 执行工作流任务，生成下一个节点任务，并备份当前工作流任务
	 * 
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean ExecuteMission() {
		mResult.clear();
		VData tVData = new VData();
		ActivityOperator tActivityOperator = new ActivityOperator();

		// 获得当前工作任务的任务ID
		String tMissionID = (String) mTransferData.getValueByName("MissionID");
		String tSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		String tActivityID = (String) mTransferData
				.getValueByName("ActivityID");
		if (tMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LWMissionDB tLWmissionDB = new LWMissionDB();
		tLWmissionDB.setMissionID(tMissionID);
		tLWmissionDB.setSubMissionID(tSubMissionID);
		tLWmissionDB.setActivityID(tActivityID);
		tLWmissionDB.getInfo();
		String tSTR = "select 1 from PD_Issue where IssueState in ('0','1') and BackPost like concat('"
				+ "?BackPost?"
				+ "','%') and riskcode = '"
				+ "?riskcode?"
				+ "' and not exists (select 1 from pd_issue where IssueState in ('0','1') and BackPost < '"
				+ "?BackPost?"
				+ "' and riskcode = '" + "?riskcode?" + "')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSTR);
		sqlbv1.put("BackPost", tLWmissionDB.getActivityID().substring(9, 10)+"0");
		sqlbv1.put("riskcode", tLWmissionDB.getMissionProp2());
		SSRS tSSRS = new ExeSQL().execSQL(sqlbv1);
		if (tSSRS.getMaxRow() > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "存在当前岗位未回复的问题件，请先处理!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
					tActivityID, mInputData)) {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
			// 获得执行工作流任务的结果
			tVData = mActivityOperator.getResult();
			if (tVData != null) {
				for (int i = 0; i < tVData.size(); i++) {
					VData tempVData = new VData();
					tempVData = (VData) tVData.get(i);
					mResult.add(tempVData);
				}
			}

			// 产生执行工作流任务后的任务节点
			if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
					tActivityID, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}

			tActivityOperator = new ActivityOperator();
			if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
					tActivityID, mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				if ((tempVData != null) && (tempVData.size() > 0)) {
					mResult.add(tempVData);
					tempVData = null;
				}
			} else {
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);

				return false;
			}

			if (!dealWith02Flag()) {
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);

			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "ExecuteMission";
			tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	private boolean dealWith02Flag() {
		String riskCode = (String) this.mTransferData
				.getValueByName("RiskCode");

		ExeSQL exec = new ExeSQL();

		// 校验是否有返回pd00000001节点的问题件
		String backPostTo01 = "select backpost from Pd_Issue where riskcode = '"
				+ "?riskCode?"
				+ "' and issuestate = '1' and backpost in (select distinct othersign from ldcode where codetype = 'pdEnabledNode')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(backPostTo01);
		sqlbv2.put("riskCode", riskCode);
		SSRS backPostTo01Arr = exec.execSQL(sqlbv2);
		if (backPostTo01Arr != null && backPostTo01Arr.getMaxRow() > 0) {
			// 查找契约确认标识对应lwmission中的字段
			String fieldNameStr = "select destfieldname from Lwfieldmap where activityid = 'pd00000002' and fieldorder = 2";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(fieldNameStr);
			String fieldName = exec.getOneValue(sqlbv3);

			String contDoneFlagStr = "select "
					+ "?fieldName?"
					+ " from lbmission where missionprop2 = '"
					+ "?riskCode?"
					+ "' and activityid = 'pd00000001' order by makedate desc,maketime desc";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(contDoneFlagStr);
			sqlbv4.put("fieldName", fieldName);
			sqlbv4.put("riskCode", riskCode);
			String contInfoDoneFlag = exec.getOneValue(sqlbv4);
			ArrayList<String> list = new ArrayList<String>();
			if (contInfoDoneFlag.equals("1")) // 不是简易产品
			{
				// 查找不需要再次确认的岗位
//				String backPostTo01Constraint = "";
				for (int i = 0; i < backPostTo01Arr.getMaxRow(); i++) {
					list.add(backPostTo01Arr.GetText(i + 1, 1));
//					backPostTo01Constraint += "'"
//							+ backPostTo01Arr.GetText(i + 1, 1) + "',";
				}
//				backPostTo01Constraint = backPostTo01Constraint.substring(0,
//						backPostTo01Constraint.length() - 1);

				String notNeedConfirmStr = "select distinct othersign from ldcode where codetype = 'pdEnabledNode' and othersign not in ('"
						+ "?list?" + "')";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(notNeedConfirmStr);
				sqlbv5.put("list", list);
				SSRS notNeedConfirmPostArr = exec.execSQL(sqlbv5);
				if (notNeedConfirmPostArr != null
						&& notNeedConfirmPostArr.getMaxRow() > 0) {
//					String notNeedConfirmPostConstraint = "";
					ArrayList<String> list1 = new ArrayList<String>();
					for (int i = 0; i < notNeedConfirmPostArr.getMaxRow(); i++) {
						list1.add(notNeedConfirmPostArr.GetText(i + 1, 1));
//						notNeedConfirmPostConstraint += "'"
//								+ notNeedConfirmPostArr.GetText(i + 1, 1)
//								+ "',";
					}
//					notNeedConfirmPostConstraint = notNeedConfirmPostConstraint
//							.substring(0,
//									notNeedConfirmPostConstraint.length() - 1);

					String updatePostFlagStr = "select distinct concat(codealias , '=''1''') from ldcode where codetype = 'pdEnabledNode' and othersign in ('"
							+ "?list1?" + "')";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(updatePostFlagStr);
					sqlbv6.put("list1", list1);
					SSRS updatePostFlagArr = exec.execSQL(sqlbv6);

					if (updatePostFlagArr != null
							&& updatePostFlagArr.getMaxRow() > 0) {
						String updatePostFlagConstraint = "";
//						ArrayList<String> list2 = new ArrayList<String>();
						for (int i = 0; i < updatePostFlagArr.getMaxRow(); i++) {
//							list2.add(updatePostFlagArr.GetText(i + 1, 1));
							updatePostFlagConstraint += updatePostFlagArr.GetText(i + 1, 1)
									+ ",";
						}

						updatePostFlagConstraint = updatePostFlagConstraint
								.substring(0,
										updatePostFlagConstraint.length() - 1);

						String update = "update lwmission set "+updatePostFlagConstraint+" where missionprop2 = '" + "?riskCode?" + "'";
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						sqlbv7.sql(update);
						sqlbv7.put("riskCode", riskCode);
//						sqlbv7.put("list2", list2);
						MMap map = new MMap();
						map.put(sqlbv7, "UPDATE");
						VData tVData = new VData();
						tVData.add(map);
						this.mResult.add(tVData);
					}
				}
			}
		}

		return true;
	}

	/**
	 * 创建起始任务节点
	 * 
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean CreateMission() {
		mResult.clear();
		logger.debug("Create Execute！");
		ActivityOperator tActivityOperator = new ActivityOperator();
		// 生成产品定义工作流起始节点
		try {
			logger.debug("ActivityOperator name:"
					+ mActivityOperator.getClass());
			if (tActivityOperator.CreateStartMission("pd00000011",
					"pd00000000", mInputData)) {
				VData tempVData = new VData();
				tempVData = tActivityOperator.getResult();
				mResult.add(tempVData);
				tempVData = null;
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(mActivityOperator.mErrors);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(mActivityOperator.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProdDefWorkFlowBL";
			tError.functionName = "CreateMission";
			tError.errorMessage = "工作流引擎工作出现异常!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			MMap tmap = new MMap();
			for (int i = 0; i < mResult.size(); i++) {
				VData tData = new VData();
				tData = (VData) mResult.get(i);
				MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
				tmap.add(map);
			}
			tResult.add(tmap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public VData getResult() {
		return tResult;
		}
	
	 public CErrors getErrors() {
			// TODO Auto-generated method stub
			return mErrors;
		}

	
	public static void main(String[] args) {
		// String t = "00000000000000000123";
		// logger.debug(t.substring(19,20));
		// TransferData transferData = new TransferData();
		// transferData.setNameAndValue("RiskNo", "20090323");
		// transferData.setNameAndValue("RequDate", "2009-03-23");
		// transferData.setNameAndValue("Operator", "temp");
		// transferData.setNameAndValue("MissionID", "00000000000000007224");
		// //transferData.setNameAndValue("SubMissionID","1");
		// //transferData.setNameAndValue("ActivityID","pd00000000");
		// transferData.setNameAndValue("SubMissionID", "2");
		// transferData.setNameAndValue("ActivityID", "pd00000001");
		// transferData.setNameAndValue("IsBaseInfoDone", "1");
		//
		// VData tVData = new VData();
		//
		// GlobalInput tG = new GlobalInput();
		// tG.Operator = "001";
		// tG.ManageCom = "86";
		// tVData.add(tG);
		// tVData.add(transferData);
		//
		// ProdDefWorkFlowBL tProdDefWorkFlowBL = new ProdDefWorkFlowBL();
		// tProdDefWorkFlowBL.submitData(tVData, "createNext");

		
	}
}
