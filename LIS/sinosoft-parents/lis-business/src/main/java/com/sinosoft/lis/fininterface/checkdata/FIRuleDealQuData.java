package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIRuleDealLogDB;
import com.sinosoft.lis.db.FIRulePlanDefDB;
import com.sinosoft.lis.db.FIRulePlanDefDetailDB;
import com.sinosoft.lis.fininterface.LogInfoDeal;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIRulePlanDefSchema;
import com.sinosoft.lis.vschema.FIRulePlanDefDetailSet;
import com.sinosoft.lis.vschema.FIRulePlanDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/*****
 * 针对数据质量校验处理
 *
 * @createDate 2008-09-16
 * @author lijs
 *
 */
public class FIRuleDealQuData {
private static Logger logger = Logger.getLogger(FIRuleDealQuData.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 业务数据 * */
	GlobalInput m_oGlobalInput = new GlobalInput();

	/** 校验计划的版本号码 * */
	private String ms_VersionNo = null;

	/***事件结点***/
	private String ms_CallPointID = null;

	private String ms_CheckBatchNo = null;

	/** 需要校验的批次号码 * */
	private String ms_BatchNo = null;

	private String strMsg = null;

	private MMap m_oMMap = new MMap();
	private VData m_oVData = null;
	private boolean checkBFlag = true;


	/**保存全局日志信息***/
	public LogInfoDeal m_oInfoDeal = null;
	
	
	private String m_ostrState = "1";
	/****
	 *
	 * @param oData
	 * @param strOperator
	 * @return
	 */
	public boolean submitData(VData oData,String strOperator){

		/**
		 * 获取参数
		 */
		if(!getInputData(oData)){
			return false;
		}

		/**
		 * 处理数据
		 */
		if(!DealData()){
			return false;
		}
		
		

		return true;
	}


	/****
	 * 获取参数 --校验相关
	 * @param oData
	 * @return
	 */
	private boolean getInputData(VData oData){

		StringBuffer m_oStrBuffer = new StringBuffer(1024);

		try {
			m_oInfoDeal = (LogInfoDeal) oData.getObjectByObjectName("LogInfoDeal", 0);
			m_oGlobalInput.setSchema((GlobalInput) oData.getObjectByObjectName(
					"GlobalInput", 0));
			m_oMMap.add((MMap) oData.getObjectByObjectName("MMap", 0));

			ms_BatchNo = (String) m_oMMap.get("BatchNo");
			ms_VersionNo = (String) m_oMMap.get("VersionNo");
			ms_CallPointID = (String) m_oMMap.get("CallPointID");
			ms_CheckBatchNo = (String) m_oMMap.get("CheckBatchNo");

			m_oStrBuffer.append("进入数据质量校验.\n\r");
			m_oStrBuffer.append("成功或者质量校验相关的参数.\n");
			m_oStrBuffer.append("参数列表:批次号码＝" + ms_BatchNo + "，版本号码＝" + ms_VersionNo);
			m_oStrBuffer.append("事件结点＝" + ms_CallPointID + ",校验批次号码:"  +  ms_CheckBatchNo  + "。\n\r");
			WriteLog(m_oStrBuffer.toString(), false);

		} catch (Exception e) {

			buildError("getInputData","获取参数出现异常，异常信息：" + e.getMessage() + "。");
			m_oStrBuffer.append("获取参数出现异常，异常信息：" + e.getMessage() + "。\n\r");
			WriteLog(m_oStrBuffer.toString(), false);
			return false;
		}

		return true;
	}


	/*****
	 *
	 * @param szFunc
	 * @param szErrMsg
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FIRuleDealErrDataBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		logger.debug(szErrMsg);
	}

	/*****
	 * 固定写文件并且告诉该文件日志是否结束
	 * @param strMsg
	 * @param bFlag
	 */
	private void WriteLog(String strMsg,boolean bFlag){
		m_oInfoDeal.WriteLogTxt(strMsg + "\n\r");
	}

	/*****
	 * 数据质量校验核心处理
	 * @return
	 */
	private boolean DealData(){


		try {
			/*******************************************************************
			 * 先取到对应的校验计划信息
			 *
			 */
			strMsg = "";
			strMsg = "质量校验逻辑开始执行，以下为校验明细信息：\n\r";
			// 数据质量校验处理逻辑
			FIRulePlanDefDB oRulePlanDefDB = new FIRulePlanDefDB();

			String strRulePlan = "select * from FIRulePlanDef "
					+ " where CallPointID = '?CallPointID?'"
					+ " and versionno = '?versionno?'"
					+ " and RulePlanState = '?RulePlanState?' order by Sequence";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strRulePlan);
			sqlbv.put("CallPointID", ms_CallPointID);
			sqlbv.put("versionno", ms_VersionNo);
			sqlbv.put("RulePlanState", m_ostrState);

			FIRulePlanDefSet oRulePlanDefSet = oRulePlanDefDB
					.executeQuery(sqlbv);
			strMsg = "这次执行计划的条数：" + oRulePlanDefSet.size() + "\n\r";
			WriteLog(strMsg, false);
			if (oRulePlanDefSet.size() > 0) {
				for (int k = 1; k <= oRulePlanDefSet.size(); k++) {

					strMsg = "";
					strMsg = "当前执行的计划代码为："
							+ oRulePlanDefSet.get(k).getRulesPlanID() + "\n\r";
					WriteLog(strMsg, false);

					FIRulePlanDefSchema oRulePlanDefSchema = oRulePlanDefSet
							.get(k);

					/***********************************************************
					 * 逐条计划处理
					 */
					if (oRulePlanDefSchema.getRulePlanState().equals("1")) {

						strMsg = "";
						/*******************************************************
						 * FIRulePlanDefDetail 启动明细校验规则信息d
						 */
						strMsg = "该计划处于启用中，获取对应的明细规则：\n\r";

						FIRulePlanDefDetailDB oRulePlanDefDetailDB = new FIRulePlanDefDetailDB();
						String strRulePlanDefDetailSQL = "select * from FIRulePlanDefDetail "
								+ " where RulePlanID = '?RulePlanID?'"
								+ " and RuleState = '?RuleState?' "
								+ " and VersionNo = '?VersionNo?' order by Sequence";
						SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
						sqlbv1.sql(strRulePlanDefDetailSQL);
						sqlbv1.put("RulePlanID", oRulePlanDefSchema.getRulesPlanID());
						sqlbv1.put("RuleState", m_ostrState);
						sqlbv1.put("VersionNo", ms_VersionNo.trim());

						FIRulePlanDefDetailSet oRulePlanDefDetailSet = oRulePlanDefDetailDB.executeQuery(sqlbv1);

						strMsg = "该计划对应的明细规则条数为："
								+ oRulePlanDefDetailSet.size() + "。\n\r";
						WriteLog(strMsg, false);

						if (oRulePlanDefDetailSet.size() > 0) {

							/** *逐条处理得到规则信息，并调用对应的规则类来进行处理* */

							for (int i = 1; i <= oRulePlanDefDetailSet.size(); i++) {

								/***********************************************
								 *
								 */
								strMsg = "";
								strMsg = "获取规则：" + oRulePlanDefDetailSet.get(i)
										+ "校验中:\n\r";
								FIRuleDealData oRuleDealData = new FIRuleDealData();
								WriteLog(strMsg, false);
								// 修改 -- 通过调用对应的明细规则处理类进行处理 参数来区分哪种类型的数据校验
								if (!oRuleDealData.DealData(
										oRulePlanDefDetailSet.get(i)
												.getRuleID(), ms_BatchNo,
										ms_VersionNo, oRulePlanDefSchema
												.getRulesPlanID(),
										ms_CheckBatchNo, ms_CallPointID,
										m_oInfoDeal)) {
									// 如果返回false 表示存在数据错误或者其他错误

									logger.debug("规则处理结束");
								} else {
									// 校验失败
									checkBFlag = false;
									continue;
								}
								strMsg = "";
								strMsg = "规则："
										+ oRulePlanDefDetailSet.get(i)
												.getRuleID() + "校验结束:\n\r";
								WriteLog(strMsg, false);
							}
						} else {
							// 没有符合规则参数
							// buildError("DealData", "未得到");
							strMsg = "计划："
									+ oRulePlanDefSet.get(k).getRulesPlanID()
									+ "未得到对应的启用状态的校验规则。\n\r";
							WriteLog(strMsg, false);
							continue;
						}

						// 该计划校验完毕
						FIRuleDealLogDB oFIRuleDealLogDB = new FIRuleDealLogDB();
						oFIRuleDealLogDB.setCheckSerialNo(FinCreateSerialNo
								.getSerialNo("CheckSerialNo", 1, 25)[0]);
						oFIRuleDealLogDB.setVersionNo(ms_VersionNo);
						oFIRuleDealLogDB.setDataSourceBatchNo(ms_BatchNo);
						oFIRuleDealLogDB.setRuleDealBatchNo(ms_CheckBatchNo);
						oFIRuleDealLogDB.setRulePlanID(oRulePlanDefSchema
								.getRulesPlanID());
						if (checkBFlag) {
							oFIRuleDealLogDB.setRuleDealResult("Succ");
						} else {
							oFIRuleDealLogDB.setRuleDealResult("Fail");
						}
						oFIRuleDealLogDB.setCallPointID(ms_CallPointID);
						oFIRuleDealLogDB
								.setDealOperator(m_oGlobalInput.Operator);
						oFIRuleDealLogDB.setRuleDealDate(PubFun
								.getCurrentDate());
						oFIRuleDealLogDB.setRuleDealTime(PubFun
								.getCurrentTime());
						String[] strFileInfo = new String[4];//m_oInfoDeal.getotherresult();
						String strFileName = strFileInfo[0];
						String strFilePath = strFileInfo[1];
						oFIRuleDealLogDB.setLogFileName(strFileName);
						oFIRuleDealLogDB.setLogFilePath(strFilePath);

						strMsg = "计划："
								+ oRulePlanDefSet.get(k).getRulesPlanID()
								+ "执行结束，对应的日志文件保存到" + strFilePath + strFileName
								+ "。\n\r";
						WriteLog(strMsg, false);

						if (!oFIRuleDealLogDB.insert()) {
							// 更新校验计划校验日志信息
							// 继续下个计划执行 把这个错误信息保存起来
							buildError("DealData", "更新执行计划校验日志失败。");
							strMsg = "计划："
									+ oRulePlanDefSet.get(k).getRulesPlanID()
									+ "更新执行计划校验日志失败。\n\r";
							WriteLog(strMsg, false);

						}
					} else {
						WriteLog("该校验计划未启用。\n\r", false);
					}
				}
				// 计划执行完毕

				/***************************************************************
				 * 清理校验完毕的数据 要做的动作 1、如果某个凭证类型存在问题，则该类型相关的费用类型凭证全部不可作为修改
				 * 校验标志加个标志为该类型的数据 --- 该功能点 取消 2、总校验日志表 --按照对应的每个计划校验日志保存
				 * 3、生成财务系统需要的凭证类型数据 -- 仅仅处理那些全部校验通过的数据 ---导入到对应的接口表中
				 * FIAboriginalDataTemp
				 * 仅仅把FIAboriginalData中对应的这个批次的数据校验通过的保存到上面的表中
				 * 4、把凭证业务数据表的数据导入到对外接口表中以便财务系统进行提数凭证处理
				 *
				 * 5、对于一个凭证中部分费用类型的数据错误则对应的其他正确的数据也把状态改为2。 ---
				 *
				 */
				PubSubmit oPubSubmit = new PubSubmit();
				String strBatchNoSQLDetail = "";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				if (ms_CallPointID != null) {
					if (ms_CallPointID.equals("01")) {
						strBatchNoSQLDetail = "update FIAboriginalData set checkflag = '1' "
								+ " where batchno = '?batchno?' "
								+ " and checkflag is null";
						sqlbv2.sql(strBatchNoSQLDetail);
						sqlbv2.put("batchno", ms_BatchNo);
					} else {

					}
				}
				MMap oMMap = new MMap();
				m_oVData = new VData();
				m_oVData.clear();
				oMMap.put(sqlbv2, "UPDATE");
				m_oVData.add(oMMap);
				if (oPubSubmit.submitData(m_oVData, "UPDATE")) {
					// 更新完毕 不用操作
				} else {
					// 更新失败 不用记录
					// //记录错误信息 ---
				}
			} else {
				// 写错误信息
				logger.debug("没有找到对应的校验计划信息，请核对");
				WriteLog("没有找到对应的校验计划信息。", false);
				buildError("DealData", "没有找到对应的校验计划信息。");
				return false;
			}
		} catch (Exception e) {
			WriteLog("处理过程中出现异常，异常信息为：" + e.getMessage(), false);
			buildError("DealData", "处理过程中出现异常，异常信息为：" + e.getMessage());
			return false;
		}

		return true;
	}


}
