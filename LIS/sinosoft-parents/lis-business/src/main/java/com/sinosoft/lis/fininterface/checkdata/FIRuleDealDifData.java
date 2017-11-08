package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIBusinessRuleDefDB;
import com.sinosoft.lis.db.FIDataBaseLinkDB;
import com.sinosoft.lis.fininterface.LogInfoDeal;
import com.sinosoft.lis.fininterface.utility.DBConnPool;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIBusinessRuleDealErrLogSchema;
import com.sinosoft.lis.schema.FIBusinessRuleDealLogSchema;
import com.sinosoft.lis.schema.FIBusinessRuleDefSchema;
import com.sinosoft.lis.vschema.FIBusinessRuleDealErrLogSet;
import com.sinosoft.lis.vschema.FIBusinessRuleDealLogSet;
import com.sinosoft.lis.vschema.FIBusinessRuleDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;


/*****
 * 完成差异校验处理
 * 按照结点顺序逐条得到规则，如果匹配成功就进行下个头结点的规则处理，否则直到叶子结点
 * @author dell-pc
 * 存在问题，不能使用    20110525  周渊
 */
public class FIRuleDealDifData {
private static Logger logger = Logger.getLogger(FIRuleDealDifData.class);

	/** 登陆操作参数 **/
	private GlobalInput m_oGlobalInput = new GlobalInput();

	/** 参数容器 **/
	private MMap m_oMap = new MMap();

	/** 错误处理类 **/
	public CErrors mErrors = new CErrors();
    
	/** 凭证类型 * */
	private String ms_CertificateID = null;

	/** 起始日期 * */
	private String ms_StartDate = null;

	/** 结束日期 * */
	private String ms_EndDate = null;

	/** 校验计划的版本号码 * */
	private String ms_VersionNo = null;

	/** 校验节点代码**/
	private String ms_CallPointID = null;

	/** 校验批次号**/
	private String ms_CheckBatchNo = null;
	
	/** 差异规则校验日志表**/
	private FIBusinessRuleDealLogSet oInsertBusinessRuleDealLogSet = new FIBusinessRuleDealLogSet();

	/** 差异规则校验错误日志表**/
	private FIBusinessRuleDealErrLogSet oInsertFIBusinessRuleDealErrLogSet = new FIBusinessRuleDealErrLogSet();

	/**保存全局日志信息***/
    public LogInfoDeal tLogInfoDeal = null;
    
    /**回车换行**/
    private final String enter = "\r\n"; 

    
	public FIRuleDealDifData(){

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		FIRuleDealDifData oDealDifData = new FIRuleDealDifData();

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		VData oData = new VData();
		MMap oMap = new MMap();
		oMap.put("CertificateID", "S-N-01");
		oMap.put("StartDate", "2007-01-01");
		oMap.put("EndDate", "2007-01-01");
		oMap.put("VersionNo", "00000000000000000001");

		// oData.clear();
		oData.addElement(tG);
		oData.addElement(oMap);

		if (!oDealDifData.submitData(oData, "difference")) {

			// return false;

		}

		logger.debug("End -- ljsi");

	}

	/***************************************************************************
	 * 获取外部参数，差异校验处理，差异数据存储
	 * @param oData
	 *            包含参数列表：
	 * @param strOperator
	 * @return
	 */
	public boolean submitData(VData oData, String strOperator) {

		/** 取得基本的参数**/
		if (!getInputData(oData)) {
			return false;
		}
		
		/** 差异校验处理**/
		if (!CheckDifData()) {
			return false;
		}
		
        /** 差异数据存储**/
		if(!prepareOutputData()){
			return false;
		}

		/**记录正常校验完毕日志，关闭日志读写**/
		tLogInfoDeal.WriteLogTxt("校验正常结束。"+ enter);
		tLogInfoDeal.Complete(true);
		return true;
	}


	/******
	 * 保存校验相关的日志文件信息
	 * @return
	 */
	private boolean prepareOutputData(){

		MMap oMap = new MMap();
		VData oData = new VData();
		oMap.put(oInsertBusinessRuleDealLogSet, "INSERT");
		oMap.put(oInsertFIBusinessRuleDealErrLogSet, "INSERT");

		oData.clear();
		oData.add(oMap);
		PubSubmit oPubSubmit = new PubSubmit();
		if(!oPubSubmit.submitData(oData, "INSERT")){
			buildError("FIRuleDealDifData","prepareOutputData", "出现异常,异常信息：" + oPubSubmit.mErrors.getFirstError());
			tLogInfoDeal.WriteLogTxt("出现异常,异常信息：" + oPubSubmit.mErrors.getFirstError() + "\n\r");
			return false;
		}

		return true;
	}

	/***************************************************************************
	 * 或者相关校验需要的参数
	 * @param oData
	 * @return
	 */
	private boolean getInputData(VData oData) {

		StringBuffer strStringBuffer = new StringBuffer(1024);
		try {
			strStringBuffer.append("开始获取该差异校验相关的参数信息：");
			tLogInfoDeal = (LogInfoDeal) oData.getObjectByObjectName("LogInfoDeal", 0);

			m_oGlobalInput.setSchema((GlobalInput) oData.getObjectByObjectName(
					"GlobalInput", 0));
			m_oMap.add((MMap) oData.getObjectByObjectName("MMap", 0));

			ms_StartDate = (String) m_oMap.get("StartDate");
			ms_EndDate = (String) m_oMap.get("EndDate");
			ms_VersionNo = (String) m_oMap.get("VersionNo");
			ms_CallPointID = (String) m_oMap.get("CallPointID");
			ms_CheckBatchNo = (String) m_oMap.get("CheckBatchNo");
            
			strStringBuffer.append("开始获取该差异校验相关的参数信息："+enter);
			strStringBuffer.append("操作者：" + m_oGlobalInput.Operator
					            + ",事件点[CallPointID]：" +  ms_CallPointID
					            + ",起始日期[StartDate]：" + ms_StartDate
					            + ",结束日期[StartDate]：" + ms_EndDate
					            + ",版本号码[VersionNo]：" + ms_VersionNo
					            + ",校验批次号码[CheckBatchNo]：" + ms_CheckBatchNo
					            + "。"+enter);
			tLogInfoDeal.WriteLogTxt(strStringBuffer.toString());

		} catch (Exception e) {
			tLogInfoDeal.WriteLogTxt("获取校验参数时出现异常,异常信息：" + e.getMessage() + "。" + enter);
			buildError("FIRuleDealDifData","getInputData", "出现异常,异常信息：" + e.getMessage());
			return false;
		}

		return true;
	}


	/*****
	 * 报错处理类
	 * @param szModuleName 模块名称
	 * @param szFunc 函数名称
	 * @param szErrMsg 报错信息
	 */
    private void buildError(String szModuleName, String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

	/***************************************************************************
	 * 第一步得到对应的校验范围
	 *
	 * @return
	 */
	private boolean CheckDifData() {

		/******
		 * 概要流程说明：
		 *    1、通过事件点得到需要处理的规则
		 *    2、根据每条规则处理逻辑
		 */
		try{
		// 先取到对应的头结点
		FIBusinessRuleDefDB oFIBusinessRuleDefDB = new FIBusinessRuleDefDB();
		oFIBusinessRuleDefDB.setVersionNo(ms_VersionNo);
		/**仅仅取到已经启用的规则**/
		oFIBusinessRuleDefDB.setRuleState("1"); 
		/**头结点**/
		oFIBusinessRuleDefDB.setLastNode("root"); 
		/**事件结点**/
		oFIBusinessRuleDefDB.setCallPointID(ms_CallPointID);
		FIBusinessRuleDefSet oFIBusinessRuleDefSet = oFIBusinessRuleDefDB.query();

		tLogInfoDeal.WriteLogTxt("进入规则校验中,得到规则大类数量：" + oFIBusinessRuleDefSet.size() + "。"+enter);

		if (oFIBusinessRuleDefSet.size() > 0) {
            /**	
             * 取到对应的校验规则头结点 进行下步处理
             * 只要中间任何一个规则出现了成功或者失败就终止该头结点的处理
			 */
			for (int i = 1; i <= oFIBusinessRuleDefSet.size(); i++) {
				FIBusinessRuleDefSchema oFIBusinessRuleDefSchema = oFIBusinessRuleDefSet.get(i);
				tLogInfoDeal.WriteLogTxt("当前处理规则大类：" + oFIBusinessRuleDefSchema.getRuleName() + "[" + oFIBusinessRuleDefSchema.getRuleID() + "]" + "。\n\r");
				if (dealDifData(oFIBusinessRuleDefSchema)) {
					
					/**
					 *该条Root结点相关的规则组别处理完毕 对应结果为成功
					 *保存成功日志信息
					 **/

					FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
					oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
					oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
//					oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//					oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
					oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
					oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
					oFIBusinessRuleDealLogSchema.setRuleDealResult("Fail");
					oFIBusinessRuleDealLogSchema.setRuleID(oFIBusinessRuleDefSchema.getRuleID());
					oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
					oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);
					continue;
				} else {
					/**该条处理结束  对应的结果为失败**/
					continue;
				}
			}
		}
		}catch(Exception e){
			tLogInfoDeal.WriteLogTxt("处理规则时出现异常，异常信息为：" +  e.getMessage() + "。\n\r");
			buildError("FIRuleDealDifData","CheckDifData", "处理规则时出现异常，异常信息为：" +  e.getMessage() + "。\n\r");
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 需要得到规则代码、上一个结点、下一个结点、以及处理方式 需要一个Schema就可以 同时需要对应的上一个结点相关参数的值
	 *
	 * 当当前结点不为Root执行该方法
	 *
	 * @param FIBusinessRuleDefSchema
	 *            当前需要处理的规则
	 * @param MMap
	 *            保存上个规则查询符合规则的数据组合
	 * @param strReturnDataDef
	 *            保存对应的查询列含义
	 * @param strErrInfo
	 *            保存对应的错误信息描述
	 */
	private boolean dealDifData(FIBusinessRuleDefSchema oDefSchema, MMap oMap,
			String strReturnDataDef, String strErrInfo) {

		/***********************************************************************
		 * 每次得到对应的参数值 并给SQL赋值 同时通过逻辑判断该层是否存在符合的数据，如果没有结束 1、这个地方仅仅核对是否存在数据
		 * 2、并把参数进行传递给该规则对应的下个规则处理 如果有继续 走这个方法进来 如果下个结点就是叶子结点，则不管失败与否终止执行下个逻辑处理
		 */
		try {
			if (oDefSchema.getNextNode().equalsIgnoreCase("leaf")) {
				
				/** 表示叶子结点 -- 最后一个校验 需要保存日志信息**/
				tLogInfoDeal.WriteLogTxt("进入规则: " + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() + "]" + "校验中：");
				
				if (dealDifDataByMode(oDefSchema, oMap, strReturnDataDef,strErrInfo)) {
					/**
					* 通过该规则对应的Schmea以及上个Schema
					* 校验成功 保存成功日志信息 跳出循环处理
					* 表示没有错误信息 不用关联错误日志表得到数据
					*/
					FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
					oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
					oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
//					oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//					oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
					oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
					oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
					oFIBusinessRuleDealLogSchema.setRuleDealResult("Fail");
					oFIBusinessRuleDealLogSchema.setRuleID(oDefSchema.getRuleID());
					oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
					oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);

					tLogInfoDeal.WriteLogTxt("规则: " + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() + "]" + "校验结束。");
					return true;
				} else {
					
					/** 校验失败 保存失败日志 跳出循环处理**/
					FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
					oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
					oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
//					oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//					oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
					oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
					oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
					oFIBusinessRuleDealLogSchema.setRuleDealResult("Succ");
					oFIBusinessRuleDealLogSchema.setRuleID(oDefSchema.getRuleID());
					oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
					oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);
					tLogInfoDeal.WriteLogTxt("规则: " + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() + "]" + "校验结束。");
					/** 存在错误数据 **/
					return false;
				}
			} else {
				if (dealDifDataByMode(oDefSchema, oMap, strReturnDataDef,strErrInfo)) {
					/**
					 * 校验成功 保存成功日志信息 跳出循环处理
					 * 表示没有找到错误的数据
					 */
					FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
					oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
					oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
//					oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//					oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
					oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
					oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
					oFIBusinessRuleDealLogSchema.setRuleDealResult("Fail");
					oFIBusinessRuleDealLogSchema.setRuleID(oDefSchema.getRuleID());
					oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
					oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);
					tLogInfoDeal.WriteLogTxt("规则: " + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() + "]" + "校验结束。");
					return true;
				} else {
					/** 校验失败 保存失败日志**/
//					getRuleIDFromNode(oDefSchema,oMap,strReturnDataDef,strErrInfo);
				}
			}

		} catch (Exception e) {
			tLogInfoDeal.WriteLogTxt("规则: " + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() + "]" + "校验时出现异常，异常信息为: " + e.getMessage() + "。");
			buildError("","dealDifData", "校验时出现异常，异常信息为: " + e.getMessage() + "。");
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 需要得到规则代码、上一个结点、下一个结点、以及处理方式 需要一个Schema就可以
	 * 只有当是Root结点才走这个分支
	 *
	 */
	private boolean dealDifData(FIBusinessRuleDefSchema oDefSchema) {

		try{
		if (oDefSchema != null) {
			
			/** 表示是根结点**/
			if (oDefSchema.getLastNode().equalsIgnoreCase("root")) {
				
				/** 如果同时还是叶子结点 -- 还需要保存对应的日志信息 **/
				if (oDefSchema.getNextNode().equalsIgnoreCase("leaf")) {
					
					tLogInfoDeal.WriteLogTxt("当前规则为叶子结点,进入叶子结点处理逻辑：\n\r");
					
                    /** 
					 * 校验成功 跳出循环处理
					 * 保存了错误日志信息
					 */
					if (dealDifDataByMode(oDefSchema)) {
						tLogInfoDeal.WriteLogTxt("当前规则:" + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() +"]处理结束，符合该规则的数据保存到错误日志表中。\n\r");
						return true;
					} else {
						/**
						 * 校验失败 跳出循环处理
						 * 没有错误日志信息
						 */
						FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
						oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
						oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
//						oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//						oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
						oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
						oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
						oFIBusinessRuleDealLogSchema.setRuleDealResult("Succ");
						oFIBusinessRuleDealLogSchema.setRuleID(oDefSchema.getRuleID());
						oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
						oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);
						tLogInfoDeal.WriteLogTxt("当前规则:" + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() +"]处理结束，符合该规则的数据保存到错误日志表中。\n\r");
						return false;
					}
				} else {
					/** 进行下一个结点的处理**/
					
					/***********************************************************
					 * 第一步校验 第二步返回信息 第三步错误处理
					 */
					if (dealDifDataByMode(oDefSchema)) {
						// 不进行下步比较了; 保存日志信息 如果相等就不处理了 跳出循环处理
						FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
						oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
						oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
						oFIBusinessRuleDealLogSchema.setRuleID(oDefSchema.getRuleID());
//						oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//						oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
						oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
						oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
						oFIBusinessRuleDealLogSchema.setRuleDealResult("Fail");
						oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
						oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);
						tLogInfoDeal.WriteLogTxt("当前规则:" + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() +"]处理结束，符合该规则的数据保存到错误日志表中。\n\r");
						return true;
					} else {
						// 进行下个结点的比较 不相等的情况下
						FIBusinessRuleDealLogSchema oFIBusinessRuleDealLogSchema = new FIBusinessRuleDealLogSchema();
						oFIBusinessRuleDealLogSchema.setCheckSerialNo(FinCreateSerialNo.getSerialNo("CheckSerialNo", 1, 25)[0]);
						oFIBusinessRuleDealLogSchema.setDealOperator(m_oGlobalInput.Operator);
						oFIBusinessRuleDealLogSchema.setRuleID(oDefSchema.getRuleID());
//						oFIBusinessRuleDealLogSchema.setLogFileName(m_oInfoDeal.getotherresult()[0]);
//						oFIBusinessRuleDealLogSchema.setLogFilePath(m_oInfoDeal.getotherresult()[1]);
						oFIBusinessRuleDealLogSchema.setRuleDealDate(PubFun.getCurrentDate());
						oFIBusinessRuleDealLogSchema.setRuleDealTime(PubFun.getCurrentTime());
						oFIBusinessRuleDealLogSchema.setRuleDealResult("Succ");
						oFIBusinessRuleDealLogSchema.setRuleDealBatchNo(ms_CheckBatchNo);
						oInsertBusinessRuleDealLogSet.add(oFIBusinessRuleDealLogSchema);
						tLogInfoDeal.WriteLogTxt("当前规则:" + oDefSchema.getRuleName() + "[" + oDefSchema.getRuleID() +"]处理结束，符合该规则的数据保存到错误日志表中。\n\r");
						return false;
					}
				}
			}
		}
		}catch(Exception e){
			tLogInfoDeal.WriteLogTxt("当前规则处理出现异常，异常信息为：" + e.getMessage() + "。\n\r");
			buildError("","dealDifData", "当前规则处理出现异常，异常信息为：" + e.getMessage() + "。");
			return false;
		}

		return true;

	}

	/***************************************************************************
	 * 根据结点信息得到相关的规则 然后进行每条规则处理
	 *
	 * @param oBusinessRuleDefSchema
	 *           上个结点记录
	 *  @param oMap
	 *            保存上个结点数据组合的值
	 *  @param strReturnDataDef
	 *            保存对应的返回参数值
	 *  @param strErrInfo
	 *            错误信息的相关数据
	 *
	 */
	private void getRuleIDFromNode(FIBusinessRuleDefSchema oBusinessRuleDefSchema,MMap oMap,String strReturnDataDef,String strErrInfo) {

		String strLogInfo = "进入规则：" + oBusinessRuleDefSchema.getRuleName()
		                    + "[" + oBusinessRuleDefSchema.getRuleID() + "]的下个结点处理。\n\r";
		String strNodeSQL = "select * from FIBusinessRuleDef where "
				+ " lastnode = '?lastnode?'"
				+ " and VersionNo = '?VersionNo?'"
				+ " and RuleState = '1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strNodeSQL);
		sqlbv.put("lastnode", oBusinessRuleDefSchema.getNextNode().trim());
		sqlbv.put("VersionNo", ms_VersionNo);
		FIBusinessRuleDefDB oBusinessRuleDefDB = new FIBusinessRuleDefDB();

		FIBusinessRuleDefSet oRuleDefSet = oBusinessRuleDefDB.executeQuery(sqlbv);

		if (oRuleDefSet.size() > 0) {
			strLogInfo = strLogInfo + "得到当前规则有" + oRuleDefSet.size() + "条下个结点规则，以下是逐条该规则的下个结点明细过程：";
			tLogInfoDeal.WriteLogTxt(strLogInfo);
			for (int i = 1; i <= oRuleDefSet.size(); i++) {

				if (dealDifData(oRuleDefSet.get(i),oMap,strReturnDataDef,strErrInfo)) {
					// ;成功校验通过
					break;
				}
			}
		}

	}


	/***************************************************************************
	 * 校验的数据 根据校验的方式、以及对应具体处理方式来处理数据
	 *
	 * 该方法针对的是非Root结点处理
	 *
	 * @param oDefSchema
	 * @param MMap 保存上个规则查询出来的数据组合
	 * @param strReturnDataDef 保存查询的列信息
	 *
	 * @return 如果如果该规则有符合的数据 则返回Ture 否则返回False
	 */
	private boolean dealDifDataByMode(FIBusinessRuleDefSchema oFIBusinessRuleDefSchema,MMap oMap,String strReturnDataDef,String strErrInfo) {

		try {


			if(oFIBusinessRuleDefSchema.getNextNode().equalsIgnoreCase("leaf")){

				//如果是叶子结点就直接保存该信息 错误信息
				//需要直接生成该信息的流水号码 以及规则  还有对应的关联流水号码？
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals("2")) {

					/****
					 * 固定第一列是比较的值 第二列是该值对应的具体数据
					 */
					   StringBuffer strLogInfo = new StringBuffer(1024);
					   strLogInfo.append("进入规则：" + oFIBusinessRuleDefSchema.getRuleName()
					                + "[" + oFIBusinessRuleDefSchema.getRuleID() +  "]"
					                + "逻辑处理，核对符合该规则的数据：\n\r");


					// 需要关注的
					//如果是数组处理 -- 先得到条数
					int nCount = 0;
					nCount = Integer.parseInt(getResultValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oMap,strReturnDataDef,oFIBusinessRuleDefSchema.getDataBaseID()));

					if(nCount > 0){

						strLogInfo.append("处理完毕后，符合该规则的数据条数为：" + nCount + "。\n\r");

						String []strErrSerialNo = FinCreateSerialNo.getSerialNo("ErrSerialNo", nCount, 25);
						SSRS oSsrs1 = getSQLSSRSValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oMap,strReturnDataDef,oFIBusinessRuleDefSchema.getDataBaseID());
						String []strReturnDef = oFIBusinessRuleDefSchema.getRuleReturnDataDef().split(",");
						for(int i=1;i<=oSsrs1.MaxRow;i++){
							//每条记录逐条处理以下相关的规则
							String strInfoTest = "";
							MMap oNewMap = new MMap();
							String []strInfo = new String[strReturnDef.length];
							for(int j=0;j<strReturnDef.length;j++){
								strInfo[j] = oSsrs1.GetText(i, j+1);
								oNewMap.put(strReturnDef[j], strInfo[j]);
								strInfoTest = strInfoTest + strReturnDef[j] + " = " + strInfo[j] + ",";
							}
							strLogInfo.append("第" + i +"条符合的数据信息为：" + strInfoTest + "。\n\r");
							/******
							 * 把该规则对应的校验出的所有错误信息逐一的保存到日志表中
							 */
							FIBusinessRuleDealErrLogSchema oFIBusinessRuleDealErrLogSchema = new FIBusinessRuleDealErrLogSchema();
							oFIBusinessRuleDealErrLogSchema.setErrSerialNo(strErrSerialNo[i-1]);
							oFIBusinessRuleDealErrLogSchema.setRuleID(oFIBusinessRuleDefSchema.getRuleID());
							if((strErrInfo + strInfoTest).length() < 999){
								oFIBusinessRuleDealErrLogSchema.setErrInfo(strErrInfo + strInfoTest);
							}else{
								oFIBusinessRuleDealErrLogSchema.setErrInfo((strErrInfo + strInfoTest).substring(1, 800) + "。由于错误信息太长，详细错误信息请查看日志文件。");
							}
							oFIBusinessRuleDealErrLogSchema.setAserialno(ms_CheckBatchNo);

							oInsertFIBusinessRuleDealErrLogSet.add(oFIBusinessRuleDealErrLogSchema);
							strLogInfo.append("当前流水号码：" + oFIBusinessRuleDealErrLogSchema.getErrSerialNo() + "的错误相关数据信息为:\n\r");
							strLogInfo.append(strErrInfo + strInfoTest + "\n\r");
							strLogInfo.append("该条流水号码记录错误信息描述完毕。" + "\n\r");
						}

						strLogInfo.append("当前规则: + " + oFIBusinessRuleDefSchema.getRuleName()
					             + "[" +  oFIBusinessRuleDefSchema.getRuleID() + "]核对数据结束" + "。\n\r");

						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
					}else{
						strLogInfo.append("没有找到符合该规则的数据。\n\r");
						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
						return false;
					}
				}
			}else{
			/*******************************************************************
			 * 1、类处理 2、文件校验规则处理 3、SQL处理 4、暂不处理 直接取值 返回是字符串
			 */
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"1")) {

					// 类初始化处理
					//单条记录比较
					VData oData = new VData();

					oData.clear();
					oData.addElement(m_oGlobalInput);
					oData.addElement(m_oMap);

					Class tClass = Class.forName(oFIBusinessRuleDefSchema.getRuleDealClass());
					FIBusinessRuleService mFIBusinessRuleService = (FIBusinessRuleService) tClass.newInstance();
					if(!mFIBusinessRuleService.dealData(oData)){
						return false;
					}

				}
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"3")) {
					// 通过外部框架来实现
				}
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"2")) {

					   StringBuffer strLogInfo = new StringBuffer(1024);
					   strLogInfo.append("进入规则：" + oFIBusinessRuleDefSchema.getRuleName()
					                + "[" + oFIBusinessRuleDefSchema.getRuleID() +  "]"
					                + "逻辑处理，核对符合该规则的数据：\n\r");
					/****
					 * 固定第一列是比较的值 第二列是该值对应的具体数据
					 */
					// 需要关注的
					//如果是数组处理 -- 先得到条数
					int nCount = 0;
					nCount = Integer.parseInt(getResultValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oMap,strReturnDataDef,oFIBusinessRuleDefSchema.getDataBaseID()));

					if(nCount > 0){
						strLogInfo.append("处理完毕后，符合该规则的数据条数为：" + nCount + "。\n\r");

						SSRS oSsrs1 = getSQLSSRSValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oMap,strReturnDataDef,oFIBusinessRuleDefSchema.getDataBaseID());

						String []strReturnDef = oFIBusinessRuleDefSchema.getRuleReturnDataDef().split(",");

						String []strInfo = new String[strReturnDef.length];
						for(int i=1;i<=oSsrs1.MaxRow;i++){
							String strInfoTest = "";
							//每条记录逐条处理以下相关的规则
							MMap oNewMap = new MMap();
							for(int j=0;j<strReturnDef.length;j++){
								strInfo[j] = oSsrs1.GetText(i, j+1);
								oNewMap.put(strReturnDef[j], strInfo[j]);
								strInfoTest = strInfoTest + strReturnDef[j] + " = " + strInfo[j] + ",";
							}
							strLogInfo.append("第" + i +"条符合的数据信息为：" + strInfoTest + "。\n\r");
							getRuleIDFromNode(oFIBusinessRuleDefSchema, oNewMap, oFIBusinessRuleDefSchema.getRuleReturnDataDef(), strErrInfo + strInfoTest);
							//每行执行完毕后 执行下条规则的校验
							//提供参数、校验规则的下个Node 仅仅针对的是当前不是LEAF结点
						}
						strLogInfo.append("当前规则: + " + oFIBusinessRuleDefSchema.getRuleName()
					             + "[" +  oFIBusinessRuleDefSchema.getRuleID() + "]核对数据结束" + "。\n\r");

						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
					}else{
						strLogInfo.append("没有找到符合该规则的数据。\n\r");
						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
						return false;
					}
				}
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"4")) {

				}
			}
		} catch (Exception e) {
			tLogInfoDeal.WriteLogTxt("处理规则：" + oFIBusinessRuleDefSchema.getRuleName() + "[" + oFIBusinessRuleDefSchema.getRuleID() +"]出现异常，异常信息为：" + e.getMessage() + "。\n\r");
			buildError("","dealDifDataByMode", "出现异常，异常信息为：" + e.getMessage() + "。");
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 校验的数据 根据校验的方式、以及对应具体处理方式来处理数据
	 * 该方法仅仅在Root时调用
	 *
	 * @param oDefSchema
	 * @return 如果校验的和预先定义的返回数据相等则返回Ture 否则返回False
	 */
	private boolean dealDifDataByMode(
			FIBusinessRuleDefSchema oFIBusinessRuleDefSchema) {

		try {
			String strErrInfo = "";
			/*******************************************************************
			 * 1-类处理  2-SQL处理 3-文件校验规则处理 4、暂不处理 直接取值 返回是字符串
			 */
			if(oFIBusinessRuleDefSchema.getNextNode().equalsIgnoreCase("leaf")){

				//如果是叶子结点就直接保存该信息 错误信息
				//需要直接生成该信息的流水号码 以及规则  还有对应的关联流水号码？
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals("2")) {

					/****
					 * 固定第一列是比较的值 第二列是该值对应的具体数据
					 */
				   StringBuffer strLogInfo = new StringBuffer(1024);
				   strLogInfo.append("进入规则：" + oFIBusinessRuleDefSchema.getRuleName()
				                + "[" + oFIBusinessRuleDefSchema.getRuleID() +  "]"
				                + "逻辑处理，核对符合该规则的数据："+enter);
					// 需要关注的
					//如果是数组处理 -- 先得到条数
					int nCount = 0;
					nCount = Integer.parseInt(getResultValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oFIBusinessRuleDefSchema.getDataBaseID()));

					//
					if(nCount > 0){
						strLogInfo.append("处理完毕后，符合该规则的数据条数为：" + nCount + "。"+enter);

						String []strErrSerialNo = FinCreateSerialNo.getSerialNo("ErrSerialNo", nCount, 25);

						SSRS oSsrs1 = getSQLSSRSValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oFIBusinessRuleDefSchema.getDataBaseID());
						String []strReturnDataDef = oFIBusinessRuleDefSchema.getRuleReturnDataDef().split(",");

						for(int i=1;i<=oSsrs1.MaxRow;i++){
							//每条记录逐条处理以下相关的规则
							String strInfoTest = "";
							MMap oMap = new MMap();
							String []strInfo = new String[strReturnDataDef.length];
							for(int j=0;j<strReturnDataDef.length;j++){

								strInfo[j] = oSsrs1.GetText(i, j+1);
								oMap.put(strReturnDataDef[j], strInfo[j]);
								strInfoTest = strInfoTest + strReturnDataDef[j] + " = " + strInfo[j] + ",";
							}
							strLogInfo.append("第" + i +"条符合的数据信息为：" + strInfoTest + "。"+enter);

							/******
							 * 把该规则对应的校验出的所有错误信息逐一的保存到日志表中
							 */
							FIBusinessRuleDealErrLogSchema oFIBusinessRuleDealErrLogSchema = new FIBusinessRuleDealErrLogSchema();
							oFIBusinessRuleDealErrLogSchema.setErrSerialNo(strErrSerialNo[i-1]);
							oFIBusinessRuleDealErrLogSchema.setRuleID(oFIBusinessRuleDefSchema.getRuleID());
							oFIBusinessRuleDealErrLogSchema.setErrInfo(strErrInfo + strInfoTest);
							oFIBusinessRuleDealErrLogSchema.setAserialno(ms_CheckBatchNo);
							/**数据主键号码**/
							/**LJTempFee-TempFeeNo,FIAboriginData-BusinessNo**/
							oFIBusinessRuleDealErrLogSchema.setbusinessno(strInfo[0]);

							oInsertFIBusinessRuleDealErrLogSet.add(oFIBusinessRuleDealErrLogSchema);
							strLogInfo.append("当前流水号码：" + oFIBusinessRuleDealErrLogSchema.getErrSerialNo() + "的错误相关数据信息为:\n\r");
							strLogInfo.append(strErrInfo + strInfoTest + "\n\r");
							strLogInfo.append("该条流水号码记录错误信息描述完毕。" + "\n\r");
						}
						strLogInfo.append("当前规则: + " + oFIBusinessRuleDefSchema.getRuleName()
						             + "[" +  oFIBusinessRuleDefSchema.getRuleID() + "]核对数据结束" + "。\n\r");

						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
					}else{
						//没有数据
						strLogInfo.append("没有找到符合该规则的数据。\n\r");
						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
						return false;
					}
				}else{
					//其他方式暂不处理
				}
			}else{
			if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"1")) {
					// 类初始化处理
					//单条记录比较
					VData oData = new VData();

					oData.clear();
					oData.addElement(m_oGlobalInput);
					oData.addElement(m_oMap);

					Class tClass = Class.forName(oFIBusinessRuleDefSchema.getRuleDealClass());
					FIBusinessRuleService mFIBusinessRuleService = (FIBusinessRuleService) tClass.newInstance();
					if(!mFIBusinessRuleService.dealData(oData)){
						//;
					}

				}
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"3")) {
					// 通过外部框架来实现

				}
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals("2")) {

					/****
					 * 固定第一列是比较的值 第二列是该值对应的具体数据
					 */
					// 需要关注的
					//如果是数组处理 -- 先得到条数
					int nCount = 0;
					   StringBuffer strLogInfo = new StringBuffer(1024);
					   strLogInfo.append("进入规则：" + oFIBusinessRuleDefSchema.getRuleName()
					                + "[" + oFIBusinessRuleDefSchema.getRuleID() +  "]"
					                + "逻辑处理，核对符合该规则的数据："+enter);

					nCount = Integer.parseInt(getResultValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oFIBusinessRuleDefSchema.getDataBaseID()));

					//
					if(nCount > 0){
						strLogInfo.append("处理完毕后，符合该规则的数据条数为：" + nCount + "。"+enter);
						SSRS oSsrs1 = getSQLSSRSValue(oFIBusinessRuleDefSchema.getRuleDealSQL(),oFIBusinessRuleDefSchema.getDataBaseID());
						String []strReturnDataDef = oFIBusinessRuleDefSchema.getRuleReturnDataDef().split(",");

						for(int i=1;i<=oSsrs1.MaxRow;i++){
							//每条记录逐条处理以下相关的规则

							String strInfoTest = "";
							MMap oMap = new MMap();
							String []strInfo = new String[strReturnDataDef.length];
							for(int j=0;j<strReturnDataDef.length;j++){

								strInfo[j] = oSsrs1.GetText(i, j+1);
								oMap.put(strReturnDataDef[j], strInfo[j]);
								strInfoTest = strInfoTest + strReturnDataDef[j] + " = " + strInfo[j] + ",";
							}
							strLogInfo.append("第" + i +"条符合的数据信息为：" + strInfoTest + "。\n\r");

							getRuleIDFromNode(oFIBusinessRuleDefSchema, oMap, oFIBusinessRuleDefSchema.getRuleReturnDataDef(), strErrInfo + strInfoTest);
							//每行执行完毕后 执行下条规则的校验
							//提供参数、校验规则的下个Node 仅仅针对的是当前不是LEAF结点
							strLogInfo.append("当前规则: + " + oFIBusinessRuleDefSchema.getRuleName()
						             + "[" +  oFIBusinessRuleDefSchema.getRuleID() + "]核对数据结束" + "。\n\r");

							tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
						}
					}else{
						//没有数据
						strLogInfo.append("没有找到符合该规则的数据。\n\r");
						tLogInfoDeal.WriteLogTxt(strLogInfo.toString());
						return false;
					}
				}
				if (oFIBusinessRuleDefSchema.getRuleDealMode() != null
						&& oFIBusinessRuleDefSchema.getRuleDealMode().equals(
								"4")) {

				}
			}
		} catch (Exception e) {
			tLogInfoDeal.WriteLogTxt("处理规则：" + oFIBusinessRuleDefSchema.getRuleName() + "[" + oFIBusinessRuleDefSchema.getRuleID() +"]出现异常，异常信息为：" + e.getMessage() + "。\n\r");
			buildError("FIRuleDealDifData","dealDifDataByMode", "出现异常，异常信息为：" + e.getMessage() + "。");
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 给一个SQL参数，以及对应的参数值 参数固定三个： 凭证类型、开始日期、结束日期 返回这个SQL查询的值
	 * 返回数组结果集
	 */
	private SSRS getSQLSSRSValue(String strSQL,String strDataBaseID) {

		SSRS oSSRS = new SSRS();

		PubCalculator tPubCalculator = new PubCalculator();
		tPubCalculator.addBasicFactor("CertificateID", ms_CertificateID);
		tPubCalculator.addBasicFactor("StartDate", ms_StartDate);
		tPubCalculator.addBasicFactor("EndDate", ms_EndDate);
		tPubCalculator.setCalSql(strSQL);
		strSQL = tPubCalculator.calculateEx();
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(strSQL);
		tLogInfoDeal.WriteLogTxt("当前执行的SQL为：" + strSQL + "。\n\r");
//		ExeSQL oExeSQL = new ExeSQL();

		ExeSQL oExeSQL = null;
		if(strDataBaseID != null){
		 oExeSQL = new ExeSQL(getConnection(strDataBaseID));
		}
		else{
			oExeSQL = new ExeSQL();
		}
		
		
		/****
		 * 支持单个的和数组的方式
		 */
		oSSRS = oExeSQL.execSQL(sqlbva);

		return oSSRS;
	}

	/***************************************************************************
	 * 给一个SQL参数，以及对应的参数值 参数固定三个： 凭证类型、开始日期、结束日期 返回这个SQL查询的值
	 * 针对是给下个结点传递相关的特别参数
	 *
	 * @param oMap
	 *        保存上个结点相关的参数组合值
	 * @param strReturnDataDef
	 *        保存上个结点对应的返回参数定义组合
	 * 返回数组结果集
	 */
	private SSRS getSQLSSRSValue(String strSQL,MMap oMap,String strReturnDataDef,String strDataBaseID) {

		SSRS oSSRS = new SSRS();

		PubCalculator tPubCalculator = new PubCalculator();
		tPubCalculator.addBasicFactor("CertificateID", ms_CertificateID);
		tPubCalculator.addBasicFactor("StartDate", ms_StartDate);
		tPubCalculator.addBasicFactor("EndDate", ms_EndDate);

		String []strDef = strReturnDataDef.split(",");
		for(int i=0;i<strDef.length;i++){
			tPubCalculator.addBasicFactor(strDef[i].trim(), oMap.get(strDef[i]).toString().trim());
		}
		tPubCalculator.setCalSql(strSQL);
		strSQL = tPubCalculator.calculateEx();
        SQLwithBindVariables sqlbvaa = new SQLwithBindVariables();
        sqlbvaa.sql(strSQL);
		tLogInfoDeal.WriteLogTxt("当前执行的SQL为：" + strSQL + "。\n\r");
//		ExeSQL oExeSQL = new ExeSQL();

		ExeSQL oExeSQL = null;
		if(strDataBaseID != null){
		 oExeSQL = new ExeSQL(getConnection(strDataBaseID));
		}
		else{
			oExeSQL = new ExeSQL();
		}
		
		/****
		 * 支持单个的和数组的方式
		 */
		oSSRS = oExeSQL.execSQL(sqlbvaa);

		return oSSRS;
	}

	/***************************************************************************
	 * 给一个SQL参数，以及对应的参数值 参数固定三个： 凭证类型、开始日期、结束日期 返回这个SQL查询的值
	 * 返回条数---记录总数
	 */
	private String getResultValue(String strSQL,String strDataBaseID) {

		String strValue = null;

		PubCalculator tPubCalculator = new PubCalculator();
		tPubCalculator.addBasicFactor("StartDate", ms_StartDate);
		tPubCalculator.addBasicFactor("EndDate", ms_EndDate);
		tPubCalculator.setCalSql(strSQL);
		strSQL = "select count(1) from (" + tPubCalculator.calculateEx() + ") g";
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(strSQL);
		tLogInfoDeal.WriteLogTxt("当前执行的SQL为：" + strSQL + "。\n\r");
//		ExeSQL oExeSQL = new ExeSQL();
		ExeSQL oExeSQL = null;
		if(strDataBaseID != null){
		 oExeSQL = new ExeSQL(getConnection(strDataBaseID));
		}
		else{
			oExeSQL = new ExeSQL();
		}
		/****
		 * 支持单个的和数组的方式
		 */
//		strValue = oExeSQL.getOneValue(strSQL);
		
		
		/****
		 * 支持单个的和数组的方式
		 */
		strValue = oExeSQL.getOneValue(sqlbva);

		return strValue;
	}

	/***************************************************************************
	 * 给一个SQL参数，以及对应的参数值 参数固定三个： 凭证类型、开始日期、结束日期 返回这个SQL查询的值
	 * 针对是给下个结点传递相关的特别参数
	 *
	 * @param oMap
	 *        保存上个结点相关的参数组合值
	 * @param strReturnDataDef
	 *        保存上个结点对应的返回参数定义组合
	 * 返回条数---记录总数
	 */
	private String getResultValue(String strSQL,MMap oMap,String strReturnDataDef,String strDataBaseID) {

		String strValue = null;

		PubCalculator tPubCalculator = new PubCalculator();
		tPubCalculator.addBasicFactor("CertificateID", ms_CertificateID);
		tPubCalculator.addBasicFactor("StartDate", ms_StartDate);
		tPubCalculator.addBasicFactor("EndDate", ms_EndDate);

		String []strDef = strReturnDataDef.split(",");
		for(int i=0;i<strDef.length;i++){
			tPubCalculator.addBasicFactor(strDef[i].trim(), oMap.get(strDef[i]).toString().trim());
		}

		tPubCalculator.setCalSql(strSQL);
		strSQL = "select count(1) from (" + tPubCalculator.calculateEx() + ") g";
        SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
        sqlbvb.sql(strSQL);
		tLogInfoDeal.WriteLogTxt("当前执行的SQL为：" + strSQL + "。\n\r");
//		ExeSQL oExeSQL = new ExeSQL();

		ExeSQL oExeSQL = null;
		if(strDataBaseID != null){
		 oExeSQL = new ExeSQL(getConnection(strDataBaseID));
		}
		else{
			oExeSQL = new ExeSQL();
		}
		/****
		 * 支持单个的和数组的方式
		 */
//		strValue = oExeSQL.getOneValue(strSQL);
		
		/****
		 * 支持单个的和数组的方式
		 */
		strValue = oExeSQL.getOneValue(sqlbvb);

		return strValue;
	}
	
	/******
	 * 跨库连接整合处理
	 * @param tFIDataBaseLinkSchema
	 * @return
	 */
    public Connection getConnection(String strDataBaseID) {
       
    	try{
    	FIDataBaseLinkDB tFIDataBaseLinkDB = new FIDataBaseLinkDB();
        tFIDataBaseLinkDB.setInterfaceCode(strDataBaseID);
        Connection con = null;
        if (!tFIDataBaseLinkDB.getInfo()) {
        	//没有找到配置的库信息        	
        	
        	tLogInfoDeal.WriteLogTxt("获取连接库信息失败，失败原因：" + tFIDataBaseLinkDB.mErrors.getFirstError() + "。\n\r");
//        	return null;
        }else{
        	con = DBConnPool.getConnection(tFIDataBaseLinkDB.getSchema());
        }
        if (con == null) {
            logger.debug("getConnection的连接没有获得");
            tLogInfoDeal.WriteLogTxt("getConnection的连接没有获得。\n\r");
        }
        return con;
    	}catch(Exception e){    		
    		tLogInfoDeal.WriteLogTxt("获取连接失败，失败原因：" + e.getMessage() + "。\n\r");
    		return null;
    	}
    }


}
