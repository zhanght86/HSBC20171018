package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIAboriginalDataDB;
import com.sinosoft.lis.db.FIRuleDefDB;
import com.sinosoft.lis.fininterface.LogInfoDeal;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.schema.FIRuleDealErrLogSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.lis.vschema.FIRuleDealErrLogSet;
import com.sinosoft.lis.vschema.FIRuleDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/*******************************************************************************
 * 校验规则处理类 根据对应的规则来校验对应的批次数据 仅仅针对质量校验：是否为空、是否在业务数据中存在对应的值
 *
 *
 * @author lijs
 * @createTime 2008-08-26
 *
 */
public class FIRuleDealData {
private static Logger logger = Logger.getLogger(FIRuleDealData.class);


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** *RuleID,BatchNo** */
	String m_sRuleID = null;

	String m_sBatchNo = null;

	String ms_ = null;

	MMap m_oMMap = null;

	VData m_oData = null;

	private LogInfoDeal m_oInfoDeal = null;

	private String strMsg = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		

	}

	/*****
	 * 固定写文件并且告诉该文件日志是否结束
	 * @param strMsg
	 * @param bFlag
	 */
	private void WriteLog(String strMsg,boolean bFlag){
		
		m_oInfoDeal.WriteLogTxt(strMsg + "\n\r");
	}


	/****
	 * 通过查询得到不符合的数据情况，把对应的数据转为对应的错误日志信息
	 * @param oFIAboriginalDataSet
	 * @param strRuleID
	 * @param strVersionNo
	 * @return
	 */
	public boolean CHGFIATOLOG(FIAboriginalDataSet oFIAboriginalDataSet,String strRuleID,String strVersionNo){
		
	   /****
	    * 修改 
	    */
		
		return true;
	}
	
	
	/***************************************************************************
	 * 针对明细数据的校验---按照对应的规则进行校验
	 *
	 * @param strRuleID
	 *            规则代码
	 * @param strBatchNo
	 *            批次号码
	 * @param strVersionNo
	 *            版本号码
	 * @return
	 */
	public boolean DealData(String strRuleID, String strBatchNo,
			String strVersionNo, String strRulePlan, String strCheckBatchNo,String strCallPointID,LogInfoDeal oLogInfoDeal) {

		m_oInfoDeal = oLogInfoDeal;
		/** *FIRuleDef*** */
		/***********************************************************************
		 * 校验方式：1、类处理 2、SQL处理 3、文件校验规则处理 4、暂不处理
		 */
		boolean bFlag = false;
		try {
			FIRuleDefDB oDefDB = new FIRuleDefDB();
			oDefDB.setRuleID(strRuleID);
			oDefDB.setVersionNo(strVersionNo);
			FIRuleDefSet oFIRuleDefSet = oDefDB.query();

			if (oFIRuleDefSet.size() > 0) {
				for (int i = 1; i <= oFIRuleDefSet.size(); i++) {


					strMsg = "";
					strMsg = "规则：" + oFIRuleDefSet.get(i).getRuleName() + "处理中：\n\r";
					WriteLog(strMsg, false);
					if (oFIRuleDefSet.get(i).getRuleDealMode().equals("2")) {

						strMsg = "";
						strMsg = "该规则校验方式为：SQL处理方式，正在校验处理中：\n\r";
						/*******************************************************
						 * 固定得到如下信息: 批次号码、该类数据的标记类型说明 SQL查询范围是 针对的未校验的 符合条件
						 * 把校验类型分为如下几种情况 是否为空 是否存在对应的数据值 是否存在遗漏 是否存在重复
						 * 这些错误作为错误原因保存起来
						 *
						 * checktype 1 是否为空 2 是否存在 3 遗漏 4 重复 SQL编写规范：
						 * 1、查询校验标志为空的 2、查询不符合条件的数据 -- 查询的结构全是不通过的数据
						 * 3、主要校验范围：字段是否为空、在对应的业务数据中是否包含了该类数据
						 */
						String strSQL = oFIRuleDefSet.get(i).getRuleDealSQL();

						PubCalculator tPubCalculator = new PubCalculator();
						tPubCalculator.addBasicFactor("BatchNo", strBatchNo);
						tPubCalculator.addBasicFactor("RuleID", strRuleID);
						tPubCalculator.setCalSql(strSQL);
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							strSQL = tPubCalculator.calculateEx()
									+ " and rownum <= 500";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							strSQL = tPubCalculator.calculateEx()
									+ " limit 0,500";
						}
						SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
						sqlbvb.sql(strSQL);

						strMsg = strMsg + "该规则对应的校验SQL为：" + tPubCalculator.calculateEx() + "。\n\r";
						WriteLog(strMsg, false);
						/*******************************************************
						 * 事件点分为： 1、采集数据校验 对应的关联的表：FIAboriginalData 2、凭证数据校验
						 * 对应的关联的表：FIDataTransResult
						 */
						if (strCallPointID != null) {
							if (strCallPointID.equals("01")) {
								/*****
								 * 01、采集完毕
								 * 02、凭证转换
								 * 03、导出凭证
								 */
								FIAboriginalDataDB oFIAboriginalDataDB = null;
								FIAboriginalDataSet oAboriginalDataSet = null;
								FIAboriginalDataSet oAllUpdateAboriginalDataSet = new FIAboriginalDataSet();

								String strCountSQL = "select count(1) from (" + strSQL + ") g";
								SQLwithBindVariables sqlbv = new SQLwithBindVariables();
								sqlbv.sql(strCountSQL);
								while (havingData(sqlbv)) {

									strMsg = "";
									strMsg = "处理处于事件点：采集数据，获取到符合该规则的数据，正在处理中：\n\r";

									oFIAboriginalDataDB = new FIAboriginalDataDB();
									oAboriginalDataSet = new FIAboriginalDataSet();
									// 用于处理对应的凭证类型关联的数据
									FIAboriginalDataDB oUpdateFIAboriginalDataDB = new FIAboriginalDataDB();
									FIAboriginalDataSet oUpdateAboriginalDataSet = new FIAboriginalDataSet();

									// FIRuleDealLog 需要增加信息FIRuleDealErrLog
									oAboriginalDataSet = oFIAboriginalDataDB
											.executeQuery(sqlbvb);
									strMsg = strMsg + "此批处理的数据量为：" + oAboriginalDataSet.size() + "\n\r";
									WriteLog(strMsg, false);
									FIRuleDealErrLogSet oFIRuleDealErrLogSet = new FIRuleDealErrLogSet();

									String[] strErrSerialNo = FinCreateSerialNo
											.getSerialNo("ErrSerialNo",
													oAboriginalDataSet.size(),
													25);

									if (oAboriginalDataSet.size() > 0) {
										for (int j = 1; j <= oAboriginalDataSet
												.size(); j++) {
											/***********************************
											 * "RuleDealBatchNo" VARCHAR2(20)
											 * not null, "DataSourceBatchNo"
											 * VARCHAR2(20) not null,
											 * "ClassType" VARCHAR2(10) not
											 * null, "ErrInfo" VARCHAR2(1000),
											 * "KeyUnionValue" VARCHAR2(100) not
											 * null, "RulePlanID" VARCHAR2(10)
											 * not null, "RuleID" varchar2(10)
											 * not null, "LogFilePath"
											 * VARCHAR2(100), "LogFileName"
											 * VARCHAR2(100),
											 */
											FIAboriginalDataSchema oAboriginalDataSchema = oAboriginalDataSet
													.get(j);

											FIRuleDealErrLogSchema oFIRuleDealErrLogSchema = new FIRuleDealErrLogSchema();
											oFIRuleDealErrLogSchema
													.setErrSerialNo(strErrSerialNo[j - 1]);
											oFIRuleDealErrLogSchema
													.setRuleDealBatchNo(strCheckBatchNo);
											oFIRuleDealErrLogSchema
													.setDataSourceBatchNo(strBatchNo);
											oFIRuleDealErrLogSchema
													.setbusinessno(oAboriginalDataSchema
															.getBusinessNo());
											oFIRuleDealErrLogSchema
													.setCertificateID(oAboriginalDataSchema
															.getCertificateID());
											// 规则中什么不可为空 --- 保存该规则表中的一个描述
											oFIRuleDealErrLogSchema
													.setErrInfo("规则:"
															+ oFIRuleDefSet
																	.get(i)
																	.getRuleName()
															+ "["
															+ oFIRuleDefSet
																	.get(i)
																	.getRuleReturnMean()
															+ "]" + "校验不通过");
											// 为了方便唯一标志 采用流水号码关联 方便后续的错误数据处理
											oFIRuleDealErrLogSchema
													.setAserialno(oAboriginalDataSchema
															.getASerialNo());
											oFIRuleDealErrLogSchema
													.setRuleID(strRuleID);
											oFIRuleDealErrLogSchema
													.setRulePlanID(strRulePlan);

											// 得到对应的凭证和关联的业务号码对应的业务数据以便进行统一处理
											oUpdateFIAboriginalDataDB
													.setCertificateID(oAboriginalDataSchema
															.getCertificateID());
											oUpdateFIAboriginalDataDB
													.setBusinessNo(oAboriginalDataSchema
															.getBusinessNo());
											oUpdateFIAboriginalDataDB
													.setBatchNo(strBatchNo);
											oUpdateAboriginalDataSet = oUpdateFIAboriginalDataDB
													.query();
											oAllUpdateAboriginalDataSet
													.add(oUpdateAboriginalDataSet);

											// 根据对应的错误信息得到该类型
											oFIRuleDealErrLogSet
													.add(oFIRuleDealErrLogSchema);

											oAboriginalDataSchema
													.setCheckFlag("0"); // 未通过
											oAboriginalDataSet.set(j,
													oAboriginalDataSchema);

										}
										// 第一返回 对应的UPDATE数据  第二步返回对应的 日志错误信息
										
										// 第三步返回对应的凭证层级数据 这步处理需要同时处理对应的计划

										// 更新对应同一凭证下同一业务号码的校验状态,如果有一个费用类型校验失败全部保存为失败.
										
										if (oAllUpdateAboriginalDataSet.size() > 1) {

											strMsg = "";
											strMsg = "更新对应同一凭证下同一业务号码的校验状态,如果有一个费用类型校验失败全部保存为失败。关联的数据量为：" + oAllUpdateAboriginalDataSet.size() + "\n\r";
											WriteLog(strMsg, false);
											for (int k = 1; k <= oAllUpdateAboriginalDataSet.size(); k++) {
												FIAboriginalDataSchema oAboriginalDataSchema = oAllUpdateAboriginalDataSet.get(k);
												oAboriginalDataSchema.setCheckFlag("0");
												oAllUpdateAboriginalDataSet.set(k,oAboriginalDataSchema);
											}
										}

										m_oMMap = new MMap();
										m_oMMap.put(oAboriginalDataSet,
												"UPDATE");
										m_oMMap.put(
												oAllUpdateAboriginalDataSet,
												"UPDATE");
										m_oMMap.put(oFIRuleDealErrLogSet,
												"INSERT");

										m_oData = new VData();
										m_oData.clear();
										m_oData.add(m_oMMap);

										strMsg = "";
										strMsg = "该批处理的符合该规则中的数据量为：" + oAboriginalDataSet.size() +
										         "，这些数据相关的凭证对应的其他费用数据量为:" + oAllUpdateAboriginalDataSet.size() +
										         "，更新的错误日志信息数据量为：" + oFIRuleDealErrLogSet.size() + "。\n\r";

										WriteLog(strMsg, false);

										PubSubmit oPubSubmit = new PubSubmit();
										if (oPubSubmit.submitData(m_oData,
												"INSERT")) {
											// 更新成功
											bFlag = true;
											WriteLog("规则：" + oFIRuleDefSet.get(i).getRuleName()+ "["
													+ oFIRuleDefSet.get(i).getRuleID() + "]校验完毕，对应的错误数据状态和日志更新完毕。\n\r", false);
										} else {
											logger.debug(" ---  End Check Fail ------");
											bFlag = true;
											WriteLog("规则：" + oFIRuleDefSet.get(i).getRuleName()+ "["
													+ oFIRuleDefSet.get(i).getRuleID() + "]校验完毕，对应的错误数据状态和日志更新失败，失败原因" + oPubSubmit.mErrors.getFirstError() + "。\n\r", false);
										}
									}
								}
							} else if (strCallPointID.equals("02")) {
								// 凭证事件点
								bFlag = false;
								WriteLog("处理处于事件点：凭证分录数据质量校验。\n\r", false);

							} else if (strCallPointID.equals("03")) {

								// 凭证汇总
								bFlag = false;
								WriteLog("处理处于事件点：凭证汇总数据质量校验。\n\r", false);
							} else {

								// 不支持的事件点
								bFlag = true;
								WriteLog("处理处于事件点：目前不支持该事件点，需要确认该事件点定义是否准确。\n\r", false);
							}
						} else {

							// 事件点为空异常
							bFlag = true;
							WriteLog("处理处于事件出现异常，该事件点为空。\n\r", false);
						}

					} else {
						// 其他校验方式 暂不处理
						//
						bFlag = false;
//						WriteLog("处理处于事件出现异常，该事件点为空。\n\r", false);
					}
				}

			} else {
				logger.debug(strRuleID + "---无此规则");
				bFlag = false;
				WriteLog("规则：" + strRuleID + "不存在。\n\r", false);
			}
		} catch (Exception e) {
			buildError("DealData", "校验出现异常，异常信息：" + e.getMessage() + "。");
			WriteLog("校验出现异常，异常信息：" + e.getMessage() + "。\n\r", false);
			return true;
		}

		return bFlag;

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

	/**
	 * 通过一个sql得到对应的数据是否还有
	 *
	 * @param sql
	 * @return
	 */
	private boolean havingData(SQLwithBindVariables sql) {

		ExeSQL tExeSQL = new ExeSQL();
		int countNum = Integer.parseInt(tExeSQL.getOneValue(sql));
		if (countNum > 0) {
			return true;
		} else {
			return false;
		}
	}

}
