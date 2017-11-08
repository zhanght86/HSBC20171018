package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04
 * @date     : 2006-05-09, 2006-06-01, 2006-08-21, 2006-08-31, 2006-11-17
 * @direction: 团体保全保单质押贷款明细
 * @comment  : 祝所有超龄儿童们节日快乐 :)
 ******************************************************************************/

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.bq.EdorVerifyBL;
import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMRevenueRateDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LMEdorCalSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMRevenueRateSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorLNDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorLNDetailBL.class);
	// public GrpEdorLNDetailBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema rLPGrpEdorItemSchema;
	private TransferData rTransferData;
	/** 计息类型 */
	private String mIntervalType;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	
	/** 代扣印花税率 */
	private double mRevenueRate = 0.0;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorLNDetailBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorLNDetailBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GrpEdorLNDetailBL.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> GrpEdorLNDetailBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorLNDetailBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		rLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		rLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (rLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorLNDetailBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> GrpEdorLNDetailBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = rLPGrpEdorItemSchema.getEdorAcceptNo();
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}

		// 批单号码
		String sEdorNo = rLPGrpEdorItemSchema.getEdorNo();
		if (sEdorNo == null || sEdorNo.equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}

		// 批改类型
		String sEdorType = rLPGrpEdorItemSchema.getEdorType();
		if (sEdorType == null || sEdorType.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}

		// 合同号码
		String sGrpContNo = rLPGrpEdorItemSchema.getGrpContNo();
		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		// 贷款金额
		String sLoanMoney = (String) rTransferData.getValueByName("LoanMoney");
		if (sLoanMoney == null || sLoanMoney.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的贷款金额信息！");
			return false;
		} else {
			double dLoanMoney = 0.00;
			try {
				dLoanMoney = Double.parseDouble(sLoanMoney);
			} catch (Exception ex) {
				CError.buildErr(this, "贷款金额数据类型转换异常！");
				return false;
			}
			if (dLoanMoney <= 0) {
				CError.buildErr(this, "贷款金额不能为零或负数！");
				return false;
			}
		}

		// logger.debug("\t@> GrpEdorLNDetailBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> GrpEdorLNDetailBL.checkData() 开始");

		// 查询 LPGrpEdorItem
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(rLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		try {
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单保全项目明细表出现异常！");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在团单保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			rLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
		}
		tLPGrpEdorItemDB = null;
		tLPGrpEdorItemSet = null;

		// ----------------------------------------------------------------------

		// 检查本次贷款额度
		String sLoanMoney = (String) rTransferData.getValueByName("LoanMoney");
		/*
		double dLoanMoney = 0.00;
		double dCashValue = 0.00;
		dLoanMoney = Double.parseDouble(sLoanMoney);
		EdorCalZT tEdorCalZT = new EdorCalZT();
		dCashValue = tEdorCalZT.getGrpCashValue(rLPGrpEdorItemSchema
				.getGrpContNo(), rLPGrpEdorItemSchema.getEdorValiDate());
		tEdorCalZT = null;
		if (dCashValue == -1) {
			CError.buildErr(this, "计算该团体保单的现金价值失败！");
			return false;
		} else if (dCashValue <= 0) {
			CError.buildErr(this, "该团体保单的现金价值不为正，不允许贷款！");
			return false;
		} else {
			if (dLoanMoney > (dCashValue * 7.00 / 10.00)) {
				CError
						.buildErr(this, "贷款金额过大！该保单最高贷款额度为 "
								+ BqNameFun.getRound(dCashValue * 7.00 / 10.00)
								+ " 元。");
				return false;
			}
		} // dCashValue > 0
		*/
		mIntervalType = EdorVerifyBL.getInterestCalType(rLPGrpEdorItemSchema
				.getEdorType());

		// logger.debug("\t@> GrpEdorLNDetailBL.checkData() 结束");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> GrpEdorLNDetailBL.dealData() 开始");

		rMap = new MMap();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------


		// ----------------------------------------------------------------------

		// 四舍五入贷款金额,保留两位小数
		String sLoanMoney = new String("");
		double dLoanMoney = 0.00;
		sLoanMoney = (String) rTransferData.getValueByName("LoanMoney");
		dLoanMoney = Double.parseDouble(sLoanMoney);
		sLoanMoney = BqNameFun.getRound(dLoanMoney); // LPLoan 使用
		dLoanMoney = Double.parseDouble(sLoanMoney); // LJSGetEndorse 使用

		// ----------------------------------------------------------------------

		// 查询 LOLoan  获得本次贷款是第几次
		String tSql = "SELECT to_number(max(OrderNo))+1 FROM LOLoan WHERE ContNo='"
			+ rLPGrpEdorItemSchema.getGrpContNo() + "' and LoanType='0'";

		int tOrderNo = 1;//默认是第一次
		try {
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tSql);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				tOrderNo = Integer.parseInt(tSSRS.GetText(1, 1));
			}
		} catch (Exception e) {
			tOrderNo = 1;
		}


		// ----------------------------------------------------------------------

		// 计算预计到期产生的利息
		double dPreInterest = getPreInterest(rLPGrpEdorItemSchema
				.getGrpContNo(), rLPGrpEdorItemSchema.getEdorValiDate(),
				dLoanMoney);
		if (dPreInterest == -1) {
			CError.buildErr(this, "计算团体保单预计到期产生的利息失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 删除 LPLoan
		DeleteSQL = "delete from LPLoan " + "where 1 = 1 " + "and EdorNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' " + "and EdorType = '"
				+ rLPGrpEdorItemSchema.getEdorType() + "' " + "and ContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and LoanType = '0'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 计算贷款截止日期, 一般为贷款日6个月后
		String sRequitalDate = new String("");
		sRequitalDate = PubFun.calDate(rLPGrpEdorItemSchema.getEdorValiDate(),
				6, "M", null);
		sRequitalDate = PubFun.calDate(sRequitalDate, -1, "D", null);

		// ----------------------------------------------------------------------
		// 插入 LPLoan

		LPLoanSchema tLPLoanSchemaNew = new LPLoanSchema();
		tLPLoanSchemaNew.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPLoanSchemaNew.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPLoanSchemaNew.setContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLPLoanSchemaNew.setPolNo("000000");
		tLPLoanSchemaNew.setActuGetNo(rLPGrpEdorItemSchema.getEdorAcceptNo()); // 暂时未使用
		tLPLoanSchemaNew.setLoanType("0"); // 贷款
		tLPLoanSchemaNew.setOrderNo(String.valueOf(tOrderNo));
		tLPLoanSchemaNew.setLoanDate(rLPGrpEdorItemSchema.getEdorValiDate());
		tLPLoanSchemaNew.setPayOffDate(sRequitalDate);
		tLPLoanSchemaNew.setInputFlag("1"); // 按描述进行利息计算
		tLPLoanSchemaNew.setSpecifyRate("1");
		tLPLoanSchemaNew.setPayOffFlag("0");
//		double aLastSumLoanMoney = getLastSumLoanMoney(rLPGrpEdorItemSchema
//				.getGrpContNo(),rLPGrpEdorItemSchema.getEdorValiDate());
		tLPLoanSchemaNew.setLastSumLoanMoney(0);
		tLPLoanSchemaNew.setCurLoanMoney(dLoanMoney);
		tLPLoanSchemaNew.setSumMoney(dLoanMoney);
		tLPLoanSchemaNew.setLeaveMoney(dLoanMoney);
		tLPLoanSchemaNew.setPreInterest(BqNameFun.getRound(dPreInterest));
		tLPLoanSchemaNew.setOperator(rGlobalInput.Operator);
		tLPLoanSchemaNew.setMakeDate(sCurrentDate);
		tLPLoanSchemaNew.setMakeTime(sCurrentTime);
		tLPLoanSchemaNew.setModifyDate(sCurrentDate);
		tLPLoanSchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLPLoanSchemaNew, "INSERT");
		tLPLoanSchemaNew = null;

		// ----------------------------------------------------------------------

		// 删除 LJSGetEndorse
		DeleteSQL = "delete from LJSGetEndorse " + "where 1 = 1 "
				+ "and GetNoticeNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EndorsementNo = '" + rLPGrpEdorItemSchema.getEdorNo()
				+ "' " + "and FeeOperationType = '"
				+ rLPGrpEdorItemSchema.getEdorType() + "' "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1){
			CError.buildErr(this, "查询团体保单险种信息失败！");
			return false;
		}
		LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolSet.get(1);
		// 插入 LJSGetEndorse
		String sFeeFinaType = BqCalBL.getFinType(rLPGrpEdorItemSchema
				.getEdorType(), "DK", "000000");
		LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
		tLJSGetEndorseSchemaNew.setGetNoticeNo(rLPGrpEdorItemSchema
				.getEdorNo());
		tLJSGetEndorseSchemaNew.setEndorsementNo(rLPGrpEdorItemSchema
				.getEdorNo());
		tLJSGetEndorseSchemaNew.setFeeOperationType(rLPGrpEdorItemSchema
				.getEdorType());
		tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeFinaType);
		tLJSGetEndorseSchemaNew.setGrpContNo(rLPGrpEdorItemSchema
				.getGrpContNo());
		tLJSGetEndorseSchemaNew.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		tLJSGetEndorseSchemaNew.setRiskCode(tLCGrpPolSchema.getRiskCode());
		tLJSGetEndorseSchemaNew.setContNo("00000000000000000000");
		tLJSGetEndorseSchemaNew.setInsuredNo("000000");
		tLJSGetEndorseSchemaNew.setPolNo("00000000000000000000");
		tLJSGetEndorseSchemaNew.setOtherNo(rLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchemaNew.setOtherNoType("3");
		tLJSGetEndorseSchemaNew.setDutyCode("000000");
		tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
		tLJSGetEndorseSchemaNew.setGetDate(sCurrentDate);
		tLJSGetEndorseSchemaNew.setGetMoney(dLoanMoney); 
		tLJSGetEndorseSchemaNew.setManageCom(rLPGrpEdorItemSchema
				.getManageCom());
		tLJSGetEndorseSchemaNew.setAppntNo(tLCGrpPolSchema.getCustomerNo());
		tLJSGetEndorseSchemaNew.setPolType("1");
		tLJSGetEndorseSchemaNew.setGetFlag("1");
		tLJSGetEndorseSchemaNew.setOperator(rGlobalInput.Operator);
		tLJSGetEndorseSchemaNew.setMakeDate(sCurrentDate);
		tLJSGetEndorseSchemaNew.setMakeTime(sCurrentTime);
		tLJSGetEndorseSchemaNew.setModifyDate(sCurrentDate);
		tLJSGetEndorseSchemaNew.setModifyTime(sCurrentTime);
		tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_LoanCorpus);
		rMap.put(tLJSGetEndorseSchemaNew, "DELETE&INSERT");
		//tLJSGetEndorseSchemaNew = null;
		
		double dRevenueAmount = getRevenueAmount(dLoanMoney); // 代扣印花税金额
		if (dRevenueAmount == -1) {
			return false;
		}
		double GetMoney = dLoanMoney - dRevenueAmount;
		// 创建印花税财务记录
		if(dRevenueAmount > 0){
			String sFeeType = BqCalBL.getFinType(rLPGrpEdorItemSchema
					.getEdorType(), "RV", "000000");
			LJSGetEndorseSchema tLJSGetEndorseSchema2 = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema2.setSchema(tLJSGetEndorseSchemaNew);
			tLJSGetEndorseSchema2.setFeeFinaType(sFeeType);
			tLJSGetEndorseSchema2.setGetMoney(BqNameFun
					.getRoundMoney(dRevenueAmount));
			tLJSGetEndorseSchema2.setSubFeeOperationType(BqCode.Pay_Revenue);
			tLJSGetEndorseSchema2.setGetFlag("0");
			rMap.put(tLJSGetEndorseSchema2, "DELETE&INSERT");
			// ===ADD===sunsx===2008-10-28=====扣除印花税=================END==============
		}
		// ----------------------------------------------------------------------

		// 删除 LPGrpContState
		DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
				+ "and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EdorType = '" + rLPGrpEdorItemSchema.getEdorType()
				+ "' " + "and GrpContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and StateType = 'Loan'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCGrpContState
		//查询是否存在借款状态记录,存在则延用,不生成新的
		String tSQL = "select * from LCGrpContState where "
					+ "grpcontno = '"+rLPGrpEdorItemSchema.getGrpContNo()+"' and StateType = 'Loan'  and state = '1' and enddate is null";
		
		
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
//		tLCGrpContStateDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
//		tLCGrpContStateDB.setStateType("Loan"); // 复用注意修改
		LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(tSQL);
		
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() == 0) {
			// 拷贝 LCGrpContState 到 LPGrpContState			
//			第一次借钱 生成新的,以后就延用第一次的状态
			LPGrpContStateSchema tLPGrpContStateSchemaNew = new LPGrpContStateSchema();
			tLPGrpContStateSchemaNew.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
			tLPGrpContStateSchemaNew
					.setEdorType(rLPGrpEdorItemSchema.getEdorType());
			tLPGrpContStateSchemaNew.setGrpContNo(rLPGrpEdorItemSchema
					.getGrpContNo());
			tLPGrpContStateSchemaNew.setGrpPolNo("000000");
			tLPGrpContStateSchemaNew.setStateType("Loan"); // 复用注意修改
			tLPGrpContStateSchemaNew.setState("1");
			tLPGrpContStateSchemaNew.setStartDate(rLPGrpEdorItemSchema
					.getEdorValiDate());
			tLPGrpContStateSchemaNew.setEndDate("");
			tLPGrpContStateSchemaNew.setOperator(rGlobalInput.Operator);
			tLPGrpContStateSchemaNew.setMakeDate(sCurrentDate);
			tLPGrpContStateSchemaNew.setMakeTime(sCurrentTime);
			tLPGrpContStateSchemaNew.setModifyDate(sCurrentDate);
			tLPGrpContStateSchemaNew.setModifyTime(sCurrentTime);
			rMap.put(tLPGrpContStateSchemaNew, "INSERT");
		}
		tLCGrpContStateDB = null;
		tLCGrpContStateSet = null;

		// ----------------------------------------------------------------------

		// 插入 LPGrpContState
		
		
		
		// 更新 LPGrpEdorItemSchema
		rLPGrpEdorItemSchema.setEdorState("1");
		rLPGrpEdorItemSchema.setGetMoney(GetMoney);
		rLPGrpEdorItemSchema.setModifyDate(sCurrentDate);
		rLPGrpEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPGrpEdorItemSchema, "UPDATE");


		// logger.debug("\t@> GrpEdorLNDetailBL.dealData() 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> GrpEdorLNDetailBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorLNDetailBL.outputData() 结束");
		return true;
	} // function outputData end

	// ==========================================================================
    /**
     * 计算团体保单现金价值公用接口 - 按保单计算
     * 公共帐户现金价值*比率+个人帐户单位现金价值*比率
     * Added by sunsx on 2008-10-25
     * @param iGrpContNo 保单号
     * @param iCalDate 保单结算时点
     * @return double 返回-1表示错误
     */
	public double getGrpCashValue(String iGrpContNo,String iCalDate){

		/*
		 * 合同现金价值的净额计算方式:
		 * 合同的现金价值是指
		 * 当时合同各个人帐户中单位交费部分现金价值
		 * 与公共帐户的余额之和
		 * 扣除保险单借款及利息后的余额。
		 */

		double dGrpCashValue = 0.0;

		if (iGrpContNo == null || iGrpContNo.trim().equals("") || iCalDate == null || iCalDate.trim().equals("")){
			CError.buildErr(this, "团体合同号或保单生效日期不能为空！");
			return -1;
		}
		//Step_1:开始计算团单下所有保单的现价
		logger.debug("Step_1:开始计算团单下所有保单的现价");

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setGrpContNo(iGrpContNo);
		tLCPolDB.setAppFlag("1");
		LCPolSet tLCPolSet = null;
		try{
			tLCPolSet = tLCPolDB.query();
		}
		catch (Exception ex){
			CError.buildErr(this, "查询团单下险种表以获取险种号码异常！");
			return -1;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0){
			CError.buildErr(this, "查询团单下险种表以获取险种号码失败！");
			return -1;
		}

		LCPolSchema tLCPolSchema = null;
		for (int i = 1; i <= tLCPolSet.size(); i++){
			tLCPolSchema = tLCPolSet.get(i);
			double dTempCash = 0.0;
			dTempCash = getPolCashValue(tLCPolSchema, iCalDate);
			if (dTempCash == -1) {
				CError.buildErr(this, "计算分单险种 " + tLCPolSchema.getPolNo() + " 现金价值失败！");
				return -1;
			} else {

				dGrpCashValue += dTempCash;

			}
		}



		tLCPolSchema = null;
		tLCPolSet = null;
		tLCPolDB = null;

		return dGrpCashValue;
	}
	
    /**
     * 计算团体下个单现金价值 - 按保单计算
     * 公共帐户现金价值or个人帐户单位现金价值*比率
     * Added by sunsx on 2008-10-25
     * @param LCPolSchema 保单险种
     * @param iCalDate 保单结算时点
     * @return double 返回-1表示错误
     */
	private double getPolCashValue(LCPolSchema tLCPolSchema, String iCalDate) {

		double tPolCashValue = 0.0;

		String tPolNo = tLCPolSchema.getPolNo();
		String tRiskCode = tLCPolSchema.getRiskCode();
		//保单类型标记 0-个人单;1-无名单;2-(团单的公共帐号)
		String tPolTypeFlag = tLCPolSchema.getPolTypeFlag();

		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tRiskCode);
		if(!tLMRiskDB.getInfo()){
			CError.buildErr(this, "查询团单下个单险种定义失败！");
			return -1;
		}
		String isAccRisk = tLMRiskDB.getInsuAccFlag();
		if(isAccRisk.equals("Y")){

			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(tPolNo);
			LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
			LCInsureAccSchema tLCInsureAccSchema = null;
			for(int i = 1;i <= tLCInsureAccSet.size();i++){
				tLCInsureAccSchema = tLCInsureAccSet.get(i);
				String tState = tLCInsureAccSchema.getState();
				if("1".equals(tState)||"4".equals(tState))
				{
					logger.debug("己转养老金或销户");
					continue;
				}
				String tInsuAccNo = tLCInsureAccSchema.getInsuAccNo();
				LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
				tLCInsureAccClassDB.setPolNo(tPolNo);
				tLCInsureAccClassDB.setInsuAccNo(tInsuAccNo);
				LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
				LCInsureAccClassSchema tLCInsureAccClassSchema = null;
				for(int j = 1; j <= tLCInsureAccClassSet.size();j++){
					tLCInsureAccClassSchema = tLCInsureAccClassSet.get(j);
					if("2".equals(tPolTypeFlag)){
						//处理公共帐户的金额
						//TODO 公共帐户扣除管理费

						/*帐户类别
						 *001-集体公共帐户;
						 *002-个人缴费帐户;
						 *003-个人累积生息帐户;
						 *004-个人红利帐户 
						 */
						AccountManage tAccountManage = new AccountManage();
						TransferData tTransferData = tAccountManage.getAccClassInterestNewMS(tLCInsureAccClassSchema,iCalDate,"Y","D");
						//本息和
						String tSumMoney = String.valueOf(tTransferData.getValueByName("aAccClassSumPay"));

						int intvYears = PubFun.calInterval(tLCInsureAccClassSchema.getMakeDate(), iCalDate, "Y");

						if(intvYears < 0){
							CError.buildErr(this, "帐户信息出错！");
							return -1;
						}
						double manageRate =  GrpZTChargeRate.getZTChargeRate(tRiskCode,intvYears);
						tPolCashValue += ((Double.parseDouble(tSumMoney))*manageRate);

					}else{
						String tPayPlanCode = tLCInsureAccClassSchema.getPayPlanCode();
						LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
						tLMDutyPayDB.setPayPlanCode(tPayPlanCode);
						if (!tLMDutyPayDB.getInfo()) {
							CError.buildErr(this, "查询责任缴费信息失败！");
							return -1;
						}						
						String tPayAimClass = tLMDutyPayDB.getPayAimClass();
						if("2".equals(tPayAimClass)){
							//1 为个人交费,2为集体交费
							//个人帐户单位交费的部分累计到保单的现价当中
							AccountManage tAccountManage = new AccountManage();
							TransferData tTransferData = tAccountManage.getAccClassInterestNewMS(tLCInsureAccClassSchema,iCalDate,"Y","D");
							//本息和
							String tSumMoney = String.valueOf(tTransferData.getValueByName("aAccClassSumPay"));

							int intvYears = PubFun.calInterval(tLCInsureAccClassSchema.getMakeDate(), iCalDate, "Y");

							if(intvYears < 0){
								CError.buildErr(this, "帐户信息出错！");
								return -1;
							}
							double manageRate =  GrpZTChargeRate.getZTChargeRate(tRiskCode,intvYears);
							tPolCashValue += ((Double.parseDouble(tSumMoney))*manageRate);
						}
					}
					
				}
				tLCInsureAccClassSchema = null;
				tLCInsureAccClassSet = null;
				tLCInsureAccClassDB = null;
			}
			tLCInsureAccSchema = null;
			tLCInsureAccSet = null;
			tLCInsureAccDB = null;
		}else {
			//TODO 非帐户型险的处理
		}
		return tPolCashValue;
	}

	/**
	 * 查询保单最大的贷款比例
	 * 
	 * @param String
	 * @return double
	 */
	public static double getMaxLoanRate(String sGrpContNo) {
		double dMaxLoanRate = 0.00;

		// ----------------------------------------------------------------------

		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			logger.debug("\t@> GrpEdorLNDetailBL.getMaxLoanRate() : 查询保单最大贷款比例的团体合同号不能为空！");
			return -1;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select nvl(max(CanRate), 0) " + "from LMLoan "
				+ "where 1 = 1 " + "and RiskCode in " + "('000000', "
				+ "(select RiskCode " + "from LCGrpPol " + "where 1 = 1 "
				+ "and AppFlag = '1' " + "and GrpContNo = '" + sGrpContNo
				+ "'))";
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();
		String sMaxLoanRate = new String("");
		try {
			sMaxLoanRate = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			logger.debug("\t@> GrpEdorLNDetailBL.getMaxLoanRate() : 查询保单最大贷款比例信息出现异常！");
			return -1;
		}
		if (sMaxLoanRate != null && !sMaxLoanRate.trim().equals("")) {
			try {
				dMaxLoanRate = Double.parseDouble(sMaxLoanRate);
			} catch (Exception ex) {
				logger.debug("\t@> GrpEdorLNDetailBL.getMaxLoanRate() : 转换保单最大贷款比例数据类型出现异常！");
				return -1;
			}
		}
		tExeSQL = null;

		return dMaxLoanRate;
	} // function getMaxLoanRate end

	// ==========================================================================

	/**
	 * 计算预计到期利息的利率
	 * 
	 * @param String
	 * @param String
	 * @return double
	 */
	public double getPreInterestRate(String sStartDate) {
		double dReturnValue = 0.00;

		// ----------------------------------------------------------------------
		/*

		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "计算预计利息的团单合同号不能为空！");
			return -1.00;
		}

		if (sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "计算预计利息的起始日期不能为空！");
			return -1.00;
		}

		// ----------------------------------------------------------------------

		// 获取贷款截止日期, 一般为贷款日6个月后
		String sRequitalDate = new String("");
		sRequitalDate = PubFun.calDate(sStartDate, 6, "M", null);
		sRequitalDate = PubFun.calDate(sRequitalDate, -1, "D", null);

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();

		// 获取终交年龄年期
		String sPayEndYear = new String("");
		QuerySQL = "select distinct max(PayEndYear) " + "from LCPol "
				+ "where 1 = 1 " + "and GrpContNo = '" + sGrpContNo + "' "
				+ "and PolNo = MainPolNo";
		// logger.debug(QuerySQL);
		try {
			sPayEndYear = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单主险终交年龄年期出现异常！");
			return -1.00;
		}
		if (sPayEndYear == null || sPayEndYear.trim().equals("")) {
			CError.buildErr(this, "查询团体保单主险的终交年龄年期失败！");
			return -1.00;
		}

		// 获取团单主险的险种代码
		String sRiskCode = new String("");
		QuerySQL = "select distinct RiskCode " + "from LCPol " + "where 1 = 1 "
				+ "and GrpContNo = '" + sGrpContNo + "' "
				+ "and PolNo = MainPolNo " + "and PayEndYear = '" + sPayEndYear
				+ "'";
		// logger.debug(QuerySQL);
		try {
			sRiskCode = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单主险险种代码出现异常！");
			return -1.00;
		}
		if (sRiskCode == null || sRiskCode.trim().equals("")) {
			CError.buildErr(this, "查询团体保单主险的险种代码失败！");
			return -1.00;
		}

		tExeSQL = null;

		// ----------------------------------------------------------------------

		// 查询年度红利率
		double dBonusRate = 0.00;
		AccountManage tAccountManage = new AccountManage(sPayEndYear);
		dBonusRate = tAccountManage.getBonusRate(sRequitalDate, sRiskCode);
		tAccountManage = null;

		// ----------------------------------------------------------------------

		// 查询险种利率计算代码
		String sCalCode = new String("");
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setCalType("Rate");
		tLMEdorCalDB.setRiskCode(sRiskCode);
		tLMEdorCalDB.setEdorType("RF"); // 总是使用 RF
		LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
		LMEdorCalSchema tLMEdorCalSchema = new LMEdorCalSchema();
		try {
			tLMEdorCalSet = tLMEdorCalDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询险种利率计算代码出现异常！");
			return -1.00;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			// 去掉 RiskCode 重新查询
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalSet = tLMEdorCalDB.query();
			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				CError.buildErr(this, "查询险种利率计算代码失败！");
				return -1.00;
			}
		}
		if (tLMEdorCalSet != null && tLMEdorCalSet.size() > 0) {
			tLMEdorCalSchema.setSchema(tLMEdorCalSet.get(1));
			sCalCode = tLMEdorCalSchema.getCalCode();
		}
		tLMEdorCalDB = null;
		tLMEdorCalSet = null;
		tLMEdorCalSchema = null;

		// ----------------------------------------------------------------------

		// 计算预计到期利息的利率
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setStartDate(sStartDate);
		tBqCalBase.setBonusRate(dBonusRate);
		BqCalBL tBqCalBL = new BqCalBL();
		dReturnValue = tBqCalBL.calChgMoney(sCalCode, tBqCalBase);
		tBqCalBase = null;
		tBqCalBL = null;

		// ----------------------------------------------------------------------

		*/
		
		String tSql = "SELECT rate FROM ldpubrate WHERE rltype='L' and sctype='C' and ymdinterest='Y' and riskcode='000000' and caltype='00'"
			+" and startdate<='"+sStartDate+"' and enddate>='"+sStartDate+"'";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSql);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			CError.buildErr(this, "查询利率失败！");

			return -1;
		}
		dReturnValue=Double.parseDouble(tSSRS.GetText(1, 1));
		return dReturnValue;
	} // function getPreInterestRate end

	// ==========================================================================

	/**
	 * 计算预计到期产生的利息
	 * 
	 * @param String
	 * @param String
	 * @param double
	 * @return double
	 */
	public double getPreInterest(String sGrpContNo, String sStartDate,
			double dLoanMoney) {
		double dReturnValue = 0.00;

		// ----------------------------------------------------------------------

		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "计算预计利息的团单合同号不能为空！");
			return -1.00;
		}

		if (sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "计算预计利息的起始日期不能为空！");
			return -1.00;
		}

		if (dLoanMoney <= 0) {
			CError.buildErr(this, "计算预计利息的贷款金额必须为正！");
			return -1.00;
		}

		// ----------------------------------------------------------------------

		// 获取贷款截止日期, 一般为贷款日6个月后
		String sRequitalDate = new String("");
		sRequitalDate = PubFun.calDate(sStartDate, 6, "M", null);
		sRequitalDate = PubFun.calDate(sRequitalDate, -1, "D", null);

		// ----------------------------------------------------------------------

		// 获取贷款利率
		double dPreInterestRate = getPreInterestRate(sStartDate);
		if (dPreInterestRate == -1) {
			CError.buildErr(this, "计算团单预计贷款利息利率失败！");
			return -1.00;
		} else {
			// 计算贷款利息
			dReturnValue = AccountManage.getMultiAccInterest(dPreInterestRate,
					dLoanMoney, sStartDate, sRequitalDate, "C", "D");
		}

		// ----------------------------------------------------------------------

		return dReturnValue;
	} // function getPreInterest end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rLPGrpEdorItemSchema != null)
			rLPGrpEdorItemSchema = null;
		if (rTransferData != null)
			rTransferData = null;
	} // function collectGarbage end

	// ==========================================================================
	/** 获取上次借款的本息和* */
	public double getLastSumLoanMoney(String tGrpContNo,String iAppDate) {
		double tSumAccMoney = 0;

		LOLoanSet tLOLoanSet = new LOLoanSet();
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(tGrpContNo);
		tLOLoanDB.setPayOffFlag("0");
		tLOLoanDB.setLoanType("0"); // 说明是借款，与自垫区分开来
		tLOLoanSet = tLOLoanDB.query();
		if (tLOLoanSet.size() < 1) // 说明以前没有借款记录，此是第一次借款
		{
			logger.debug("此前有" + tLOLoanSet.size() + "笔借款");
		} else {
            
			for (int i = 1; i <= tLOLoanSet.size(); i++) {
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanSchema = tLOLoanSet.get(i);
				tSumAccMoney += tLOLoanSchema.getLeaveMoney();
				String tLoanDate = tLOLoanSchema.getLoanDate();
				double tRate = AccountManage.calMultiRateMS(tLoanDate,  iAppDate, "000000","00","L","C","Y",tLOLoanSchema.getCurrency());
				if (tRate+1==0) {
					
					return -1;
				}
				double tIntrest=PubFun.round(tLOLoanSchema.getLeaveMoney()*tRate, 2);//本次利息
				
				tSumAccMoney += tIntrest;
				//tLPLoanSet.add(tLPLoanSchema);
			}
		}
		return tSumAccMoney;
	}
	
	private double getRevenueAmount(double dLoanMoney) {
		double dRevenueAmount = 0.0;
		String sql = " select * from LMRevenueRate where ratetype = 'YH' "
				+ " and ratestartdate < '"
				+ rLPGrpEdorItemSchema.getEdorValiDate()
				+ "' and (rateenddate is null or rateenddate > '"
				+ rLPGrpEdorItemSchema.getEdorValiDate() + "')";
		logger.debug("-0000000000000000000000000000000000000000-" + sql);
		LMRevenueRateDB tLMRevenueRateDB = new LMRevenueRateDB();
		LMRevenueRateSet tLMRevenueRateSet = tLMRevenueRateDB.executeQuery(sql);
		if (tLMRevenueRateDB.mErrors.needDealError()) {
			CError.buildErr(this, "税率查询失败");
			return -1;
		}
		if (tLMRevenueRateSet == null || tLMRevenueRateSet.size() < 1) {
			CError.buildErr(this, "税率查询失败");
			return -1;
		}
		mRevenueRate = tLMRevenueRateSet.get(1).getRate();

		dRevenueAmount = dLoanMoney * mRevenueRate;
		// ////////////////// add by pst on 2007-11-22///////////////
		if (dRevenueAmount < 0.1) // 若印花税不足0.1则不扣除
		{
			dRevenueAmount = 0.0;
		} else {
			dRevenueAmount = (int) (dRevenueAmount * 10 + 0.5) / 10.0;
		}
		// ////////////////// end add ////////////////
		return dRevenueAmount;
	}
	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	 public static void main(String[] args)
	 {
		 logger.debug("孙".length());
	 } //function main end

} // class GrpEdorLNDetailBL end
