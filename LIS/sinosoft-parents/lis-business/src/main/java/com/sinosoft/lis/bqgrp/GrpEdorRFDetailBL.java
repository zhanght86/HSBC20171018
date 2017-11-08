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
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2006-05-09, 2006-06-05, 2006-08-21
 * @direction: 团体保全保单质押贷款清偿明细
 * @comment  : 世界环境日,拒绝电脑辐射!
 ******************************************************************************/

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LMEdorCalSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPLoanSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorRFDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorRFDetailBL.class);
	// public GrpEdorRFDetailBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema rLPGrpEdorItemSchema;
	private TransferData rTransferData;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	private String mLoanEdorNo="";
	
	/**总的借款金额*/
	private double mTotalRefund = 0.0;
	/**本次还款的金额*/
	private double mRefundMoney = 0.0;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorRFDetailBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorRFDetailBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> GrpEdorRFDetailBL.submitData() 结束");
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
		// logger.debug("\t@> GrpEdorRFDetailBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorRFDetailBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		rLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		rLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (rLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorRFDetailBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> GrpEdorRFDetailBL.getInputData() : 无法获取 TransferData 数据！");
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

		// 还款额度
		String sRefundMoney = (String) rTransferData
				.getValueByName("RefundMoney");
		String sTotalRefundMoney = (String) rTransferData
		.getValueByName("TotalRefundMoney");
		if (sRefundMoney == null || sRefundMoney.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的还款金额信息！");
			return false;
		} else {
			double dRefundMoney = 0.00;
			try {
				dRefundMoney = Double.parseDouble(sRefundMoney);
			} catch (Exception ex) {
				CError.buildErr(this, "还款金额数据类型转换异常！");
				return false;
			}
			if (dRefundMoney <= 0) {
				CError.buildErr(this, "还款金额不能为零或负数！");
				return false;
			}
		}

		// logger.debug("\t@> GrpEdorRFDetailBL.getInputData() 结束");
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
		// logger.debug("\t@> GrpEdorRFDetailBL.checkData() 开始");

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

		// 检查还款金额是否等于贷款金额加利息
//		String sRefundMoney = (String) rTransferData
//				.getValueByName("RefundMoney");
//		double dRefundMoney = Double.parseDouble(sRefundMoney);
//		double dOriginalLoanMoney = getOriginalLoanMoney(rLPGrpEdorItemSchema
//				.getGrpContNo());
//		String sOriginalLoanDate = getOriginalLoanDate(rLPGrpEdorItemSchema
//				.getGrpContNo());
//		if (sOriginalLoanDate == null || sOriginalLoanDate.trim().equals("")) {
//			CError.buildErr(this, "查询获取保单的原贷款日期失败！");
//			return false;
//		}
//		if (sOriginalLoanDate != null && sOriginalLoanDate.length()>10) {
//			sOriginalLoanDate = sOriginalLoanDate.substring(0, 10);
//			logger.debug(sOriginalLoanDate);
//		}
//		double dNormalInterest = getNormalInterest(rLPGrpEdorItemSchema
//				.getGrpContNo(),mLoanEdorNo, sOriginalLoanDate, rLPGrpEdorItemSchema
//				.getEdorValiDate());
//
//		double dTotalRefund = dNormalInterest;
		
		//mTotalRefund = dTotalRefund;
		//mRefundMoney = dRefundMoney;
		/*
		if (dRefundMoney < dTotalRefund) {
			CError.buildErr(this, "还款金额不能小于原始贷款金额加利息的和！应还款 "
					+ BqNameFun.getRound(dTotalRefund) + " 元。");
			return false;
		}
		*/

		// logger.debug("\t@> GrpEdorRFDetailBL.checkData() 结束");
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
		// logger.debug("\t@> GrpEdorRFDetailBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 更新 LPGrpEdorItemSchema
		rLPGrpEdorItemSchema.setEdorState("3");
		rLPGrpEdorItemSchema.setModifyDate(sCurrentDate);
		rLPGrpEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPGrpEdorItemSchema, "UPDATE");

		// ----------------------------------------------------------------------

		// 四舍五入还款金额,保留两位小数
		String sRefundMoney = "";//还款额度
		String sTotalRefundMoney = "";//还款总计
		String sRefundInterest = "";//还款额度的利息
		String sLoanEdorNo = "";//对应的借款记录EdorNo
		double dRefundMoney = 0.00;
		double dRefundInterest = 0.0;
		double dTotalRefundMoney = 0.0;
		sRefundMoney = (String) rTransferData.getValueByName("RefundMoney");
		sTotalRefundMoney = (String) rTransferData.getValueByName("TotalRefundMoney");
		sRefundInterest = (String) rTransferData.getValueByName("RefundInterest");
		sLoanEdorNo = (String) rTransferData.getValueByName("LoanEdorNo");
		dRefundMoney = Double.parseDouble(sRefundMoney);
		dRefundInterest = Double.parseDouble(sRefundInterest);
		dTotalRefundMoney = Double.parseDouble(sTotalRefundMoney);
		sRefundMoney = BqNameFun.getRound(dRefundMoney); // LPReturnLoan、LJSGetEndorse
															// 使用



		// ----------------------------------------------------------------------

		// 拷贝 LOLoan 到 LPLoan 和 LPReturnLoan
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLOLoanDB.setEdorNo(sLoanEdorNo);
		tLOLoanDB.setLoanType("0");
		tLOLoanDB.setPayOffFlag("0");
		LOLoanSet tLOLoanSet = new LOLoanSet();
		try {
			tLOLoanSet = tLOLoanDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询贷款业务表出现异常！");
			return false;
		}
		if (tLOLoanSet == null || tLOLoanSet.size() <= 0) {
			CError.buildErr(this, "在贷款业务表中没有该保单的需清偿的纪录！");
			return false;
		} else {
			// 拷贝 LOLoan 到 LPLoan 和 LPReturnLoan
			// 实际只会有一条LOLoan数据
			LPLoanSet tLPLoanSetNew = new LPLoanSet();
			LPReturnLoanSet tLPReturnLoanSetNew = new LPReturnLoanSet();
			LOLoanSchema tLOLoanSchema = tLOLoanSet.get(1);


			// LPReturnLoan
			LPReturnLoanSchema tLPReturnLoanSchemaNew = new LPReturnLoanSchema();
			tReflections.transFields(tLPReturnLoanSchemaNew, tLOLoanSchema);
			tLPReturnLoanSchemaNew.setEdorNo(rLPGrpEdorItemSchema
					.getEdorNo());
			tLPReturnLoanSchemaNew.setEdorType(rLPGrpEdorItemSchema
					.getEdorType());
			tLPReturnLoanSchemaNew.setActuGetNo(rLPGrpEdorItemSchema
					.getEdorAcceptNo()); // 暂时未使用
			tLPReturnLoanSchemaNew.setSumMoney(dTotalRefundMoney);
			tLPReturnLoanSchemaNew.setPayOffDate(rLPGrpEdorItemSchema
					.getEdorValiDate());
			if(dRefundMoney >= tLOLoanSchema.getLeaveMoney()){
				//还清
				tLPReturnLoanSchemaNew.setPayOffFlag("1");
				tLPReturnLoanSchemaNew.setPayOffDate(rLPGrpEdorItemSchema
						.getEdorAppDate());
				tLPReturnLoanSchemaNew.setLeaveMoney(0);//个人理解应该存放的是还完款后欠的余额
			}else if (dRefundMoney < tLOLoanSchema.getLeaveMoney()){
				double tLeaveMoney = tLOLoanSchema.getLeaveMoney() - dRefundMoney;
				tLPReturnLoanSchemaNew.setPayOffFlag("0");
				tLPReturnLoanSchemaNew.setLeaveMoney(tLeaveMoney);
				//tLPReturnLoanSchemaNew.setCurLoanMoney(tLeaveMoney);

				//tLPReturnLoanSchemaNew.setLoanDate(rLPGrpEdorItemSchema.getEdorValiDate());
			}
			tLPReturnLoanSchemaNew.setReturnMoney(dRefundMoney);
			tLPReturnLoanSchemaNew.setReturnInterest(dRefundInterest);
			tLPReturnLoanSchemaNew.setLoanNo(tLOLoanSchema.getEdorNo());
			tLPReturnLoanSchemaNew.setOperator(rGlobalInput.Operator);
			tLPReturnLoanSchemaNew.setMakeDate(sCurrentDate);
			tLPReturnLoanSchemaNew.setMakeTime(sCurrentTime);
			tLPReturnLoanSchemaNew.setModifyDate(sCurrentDate);
			tLPReturnLoanSchemaNew.setModifyTime(sCurrentTime);
			tLPReturnLoanSetNew.add(tLPReturnLoanSchemaNew);



			rMap.put(tLPReturnLoanSetNew, "DELETE&INSERT");
		}
		tLOLoanDB = null;
		tLOLoanSet = null;

		// ----------------------------------------------------------------------

//		// 删除 LJSGetEndorse
//		DeleteSQL = "delete from LJSGetEndorse " + "where 1 = 1 "
//				+ "and GetNoticeNo = '"
//				+ rLPGrpEdorItemSchema.getEdorAcceptNo() + "' "
//				+ "and EndorsementNo = '" + rLPGrpEdorItemSchema.getEdorNo()
//				+ "' " + "and FeeOperationType = '"
//				+ rLPGrpEdorItemSchema.getEdorType() + "' "
//				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
//				+ "'";
//		// logger.debug(DeleteSQL);
//		rMap.put(DeleteSQL, "DELETE");
//
//		// ----------------------------------------------------------------------
//
//		// 插入 LJSGetEndorse
//		String sFeeFinaType = BqCalBL.getFinType(rLPGrpEdorItemSchema
//				.getEdorType(), "HK", "000000");
//		LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
//		tLJSGetEndorseSchemaNew.setGetNoticeNo(rLPGrpEdorItemSchema
//				.getEdorAcceptNo());
//		tLJSGetEndorseSchemaNew.setEndorsementNo(rLPGrpEdorItemSchema
//				.getEdorNo());
//		tLJSGetEndorseSchemaNew.setFeeOperationType(rLPGrpEdorItemSchema
//				.getEdorType());
//		tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeFinaType);
//		tLJSGetEndorseSchemaNew.setGrpContNo(rLPGrpEdorItemSchema
//				.getGrpContNo());
//		tLJSGetEndorseSchemaNew.setPolNo("000000");
//		tLJSGetEndorseSchemaNew.setOtherNo(rLPGrpEdorItemSchema.getEdorNo());
//		tLJSGetEndorseSchemaNew.setOtherNoType("3");
//		tLJSGetEndorseSchemaNew.setDutyCode("000000");
//		tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
//		tLJSGetEndorseSchemaNew.setGetDate(sCurrentDate);
//		tLJSGetEndorseSchemaNew.setGetMoney(sRefundMoney); // 正数表示交费
//		tLJSGetEndorseSchemaNew.setManageCom(rLPGrpEdorItemSchema
//				.getManageCom());
//		tLJSGetEndorseSchemaNew.setPolType("1");
//		tLJSGetEndorseSchemaNew.setGetFlag("0");
//		tLJSGetEndorseSchemaNew.setOperator(rGlobalInput.Operator);
//		tLJSGetEndorseSchemaNew.setMakeDate(sCurrentDate);
//		tLJSGetEndorseSchemaNew.setMakeTime(sCurrentTime);
//		tLJSGetEndorseSchemaNew.setModifyDate(sCurrentDate);
//		tLJSGetEndorseSchemaNew.setModifyTime(sCurrentTime);
//		tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_LoanCorpus);
//		rMap.put(tLJSGetEndorseSchemaNew, "INSERT");
//		tLJSGetEndorseSchemaNew = null;

		// ----------------------------------------------------------------------


		// ----------------------------------------------------------------------
//		if(mRefundMoney >= mTotalRefund){
//			//如果还清了所有借款,则结束借款状态
//			
//			// 删除 LPGrpContState
//			DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
//			+ "and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo() + "' "
//			+ "and EdorType = '" + rLPGrpEdorItemSchema.getEdorType()
//			+ "' " + "and GrpContNo = '"
//			+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
//			+ "and StateType = 'Loan'";
//			// logger.debug(DeleteSQL);
//			rMap.put(DeleteSQL, "DELETE");
//			
//			// 查询 LCGrpContState
//			LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
//			tLCGrpContStateDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
//			tLCGrpContStateDB.setStateType("Loan"); // 复用注意修改
//			LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
//			try {
//				tLCGrpContStateSet = tLCGrpContStateDB.query();
//			} catch (Exception ex) {
//				CError.buildErr(this, "查询新契约团体保单状态表出现异常！");
//				return false;
//			}
//			if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
//				// 拷贝 LCGrpContState 到 LPGrpContState
//				LPGrpContStateSet tLPGrpContStateSetNew = new LPGrpContStateSet();
//				for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
//					LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
//					tLCGrpContStateSchema = tLCGrpContStateSet.get(i);
//					LPGrpContStateSchema tLPGrpContStateSchemaNew = new LPGrpContStateSchema();
//					tReflections.transFields(tLPGrpContStateSchemaNew,
//							tLCGrpContStateSchema);
//					tLPGrpContStateSchemaNew.setEdorNo(rLPGrpEdorItemSchema
//							.getEdorNo());
//					tLPGrpContStateSchemaNew.setEdorType(rLPGrpEdorItemSchema
//							.getEdorType());
//					// 结束上一状态
//					String sEndDate = tLCGrpContStateSchema.getEndDate();
//					if (sEndDate == null || sEndDate.trim().equals("")) {
//						sEndDate = PubFun.calDate(rLPGrpEdorItemSchema
//								.getEdorValiDate(), -1, "D", null);
//						tLPGrpContStateSchemaNew.setEndDate(sEndDate);
//					}
//					tLPGrpContStateSchemaNew.setOperator(rGlobalInput.Operator);
//					tLPGrpContStateSchemaNew.setModifyDate(sCurrentDate);
//					tLPGrpContStateSchemaNew.setModifyTime(sCurrentTime);
//					tLPGrpContStateSetNew.add(tLPGrpContStateSchemaNew);
//					tLPGrpContStateSchemaNew = null;
//					tLCGrpContStateSchema = null;
//				}
//				rMap.put(tLPGrpContStateSetNew, "INSERT");
//				tLPGrpContStateSetNew = null;
//			}
//			tLCGrpContStateDB = null;
//			tLCGrpContStateSet = null;
//
//			// ----------------------------------------------------------------------
//
//			// 插入 LPGrpContState
//			LPGrpContStateSchema tLPGrpContStateSchemaNew = new LPGrpContStateSchema();
//			tLPGrpContStateSchemaNew.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
//			tLPGrpContStateSchemaNew
//			.setEdorType(rLPGrpEdorItemSchema.getEdorType());
//			tLPGrpContStateSchemaNew.setGrpContNo(rLPGrpEdorItemSchema
//					.getGrpContNo());
//			tLPGrpContStateSchemaNew.setGrpPolNo("000000");
//			tLPGrpContStateSchemaNew.setStateType("Loan"); // 复用注意修改
//			tLPGrpContStateSchemaNew.setState("0");
//			tLPGrpContStateSchemaNew.setStartDate(rLPGrpEdorItemSchema
//					.getEdorValiDate());
//			tLPGrpContStateSchemaNew.setEndDate("");
//			tLPGrpContStateSchemaNew.setOperator(rGlobalInput.Operator);
//			tLPGrpContStateSchemaNew.setMakeDate(sCurrentDate);
//			tLPGrpContStateSchemaNew.setMakeTime(sCurrentTime);
//			tLPGrpContStateSchemaNew.setModifyDate(sCurrentDate);
//			tLPGrpContStateSchemaNew.setModifyTime(sCurrentTime);
//			rMap.put(tLPGrpContStateSchemaNew, "INSERT");
//			tLPGrpContStateSchemaNew = null;
//		}

		// ----------------------------------------------------------------------

		// 垃圾处理
		//tReflections = null;

		// logger.debug("\t@> GrpEdorRFDetailBL.dealData() 结束");
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
		// logger.debug("\t@> GrpEdorRFDetailBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorRFDetailBL.outputData() 结束");
		return true;
	} // function outputData end

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
	 * 查询团体保单未清偿的贷款金额合计
	 * 
	 * @param String
	 * @return double
	 */
	public double getOriginalLoanMoney(String sGrpContNo) {
		double dReturnValue = 0.00;

		// ----------------------------------------------------------------------

		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "查询未清偿的贷款金额的团单合同号不能为空！");
			return -1.00;
		}

		// ----------------------------------------------------------------------

		String sTempResult = new String("");
		ExeSQL tExeSQL = new ExeSQL();

		String QuerySQL = new String("");
		QuerySQL = "select sum(SumMoney) " + "from LOLoan " + "where 1 = 1 "
				+ "and ContNo = '" + sGrpContNo + "' "
				+ "and PolNo = '000000' " + "and LoanType = '0' "
				+ "and PayOffFlag = '0'";
		// logger.debug(QuerySQL);
		try {
			sTempResult = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询未清偿的贷款金额出现异常！");
			return -1.00;
		}

		if (sTempResult != null && !sTempResult.trim().equals("")) {
			try {
				dReturnValue = Double.parseDouble(sTempResult);
			} catch (Exception ex) {
				CError.buildErr(this, "转换未清偿的贷款金额数据格式出现异常！");
				return -1.00;
			}
		}

		return dReturnValue;
	} // function getOriginalLoanMoney end

	// ==========================================================================

	/**
	 * 查询团体保单未清偿的贷款日期
	 * 
	 * @param String
	 * @return String
	 */
	public String getOriginalLoanDate(String sGrpContNo) {
		String sReturnValue = new String("");

		// ----------------------------------------------------------------------

		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "查询未清偿的贷款日期的团单合同号不能为空！");
			return null;
		}

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();

		String QuerySQL = new String("");
		QuerySQL = "select min(LoanDate) " + "from LOLoan " + "where 1 = 1 "
				+ "and ContNo = '" + sGrpContNo + "' "
				+ "and PolNo = '000000' " + "and LoanType = '0' "
				+ "and PayOffFlag = '0'";
		// logger.debug(QuerySQL);
		try {
			sReturnValue = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询未清偿的贷款日期出现异常！");
			return null;
		}

		return sReturnValue;
	} // function getOriginalLoanDate end

	// ==========================================================================

	/**
	 * 计算到期利息的利率
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 * @return double
	 */
	public double getInterest(String sReturnMoney ,String sStartDate,
			String sCalcDate,String tCurrency) {
		double dReturnValue = Double.parseDouble(sReturnMoney);
		
		// ----------------------------------------------------------------------

		

		if (sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "计算利息的起始日期不能为空！");
			return -1.00;
		}

		if (sCalcDate == null || sCalcDate.trim().equals("")) {
			CError.buildErr(this, "计算利息的结束日期不能为空！");
			return -1.00;
		}


		double tRate = AccountManage.calMultiRateMS(sStartDate,  sCalcDate, "000000","00","L","C","Y",tCurrency);

				//dReturnValue += tIntrest;
				//tLPLoanSet.add(tLPLoanSchema);
		if (tRate+1==0) {
			
			return -1;
		}



		// ----------------------------------------------------------------------

		return PubFun.round(dReturnValue*tRate, 2);
	} // function getNormalInterest end


	// ==========================================================================

	/**
	 * 计算到期产生的常规利息
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @return double
	 */
	public double getNormalInterest(String sGrpContNo, String sReturnMoney ,String sStartDate,
			String sCalcDate) {
		double dReturnValue = Double.parseDouble(sReturnMoney);

		// ----------------------------------------------------------------------

		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "计算利息的团单合同号不能为空！");
			return -1.00;
		}

		if (sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "计算利息的起始日期不能为空！");
			return -1.00;
		}

		if (sCalcDate == null || sCalcDate.trim().equals("")) {
			CError.buildErr(this, "计算利息的结束日期不能为空！");
			return -1.00;
		}


				// 进行分段计息借款始期到目前日期存在两种不同利率则需要分段计息,但是用的半年利率，需要转换成年利率，需要乘以2
				String tSql = "SELECT rate,declaredate,enddate  from ldbankrate where period='6' and periodflag='M' and declaredate<='"
						+ sStartDate
						+ "'"
						+ "and enddate>='"
						+ sStartDate
						+ "'"
						+ " union "
						+ "SELECT rate,declaredate,enddate  from ldbankrate where period='6' and periodflag='M' and declaredate>='"
						+ sStartDate
						+ "'"
						+ "and enddate<='"
						+ sCalcDate
						+ "'"
						+ " union "
						+ "SELECT rate,declaredate,enddate  from ldbankrate where period='6' and periodflag='M' and declaredate<='"
						+ sCalcDate
						+ "'"
						+ "and enddate>='"
						+ sCalcDate
						+ "' order by declaredate,enddate ";
				SSRS rSSRS = new SSRS();
				ExeSQL rExeSQL = new ExeSQL();
				rSSRS = rExeSQL.execSQL(tSql);
				if (rSSRS == null || rSSRS.MaxRow == 0) {
					CError tError = new CError();
					tError.moduleName = "GrPEdorLNDetailBL";
					tError.errorMessage = "没有查询到险种利率信息！";
					this.mErrors.addOneError(tError);
					return 0;
				}
				int tArrLen = rSSRS.MaxRow;
                // 为了后续的方便计算，将查出来的结果进行必要的简单处理，即将查出来的时间区间头尾用贷款的起始日期替换！
				if (tArrLen == 1) {
					rSSRS.SetText(tArrLen, 2, sStartDate);
					rSSRS.SetText(tArrLen, 3, sCalcDate);
				} else {

					for (int k = 1; k <= tArrLen; k++) {
						if (k == 1) {
							rSSRS.SetText(k, 2, sStartDate);
						} else if (k == tArrLen) {
							rSSRS.SetText(k, 3, sCalcDate);
						}
					}
				}
				// 分段计息
				double tIntrest = 0.0;
				for (int k = 1; k <= tArrLen; k++) {
					tIntrest += AccountManage.getMultiAccInterest(Double
							.parseDouble(rSSRS.GetText(k, 1))*2, dReturnValue,
							rSSRS.GetText(k, 2), rSSRS.GetText(k, 3), "C","D");
				}

				dReturnValue += tIntrest;
				//tLPLoanSet.add(tLPLoanSchema);
			



		// ----------------------------------------------------------------------

		return dReturnValue;
	} // function getNormalInterest end

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
	}
	// function collectGarbage end
	public boolean confirmRF(LPGrpEdorItemSchema iLPGrpEdorItemSchema)
	{
		MMap tMap = new MMap();
		Reflections tReflections = new Reflections();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = null;
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(iLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(iLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(iLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(iLPGrpEdorItemSchema.getGrpContNo());
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
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
		}


		String tSQL = "select nvl(sum(ReturnMoney),0),nvl(sum(ReturnInterest),0),nvl(sum(SumMoney),0) from LPReturnLoan where "
			+"edorno ='"+iLPGrpEdorItemSchema.getEdorNo()+"' and contno = '"+iLPGrpEdorItemSchema.getGrpContNo()+"'";
		SSRS rSSRS = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(tSQL);
		if (rSSRS == null || rSSRS.MaxRow == 0) {
			CError tError = new CError();
			tError.moduleName = "GrPEdorRFDetailBL";
			tError.errorMessage = "查询本次保全还款记录失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		
		tLCGrpContDB.setGrpContNo(iLPGrpEdorItemSchema.getGrpContNo());
		if(!tLCGrpContDB.getInfo())
		{
			CError.buildErr(this, "查询团体合同信息失败!");
			return false;
		}
		
		LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.getSchema();
		
//		 ----------------------------------------------------------------------
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(iLPGrpEdorItemSchema.getGrpContNo());
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1){
			CError.buildErr(this, "查询团体保单险种信息失败！");
			return false;
		}
		LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolSet.get(1);
		String totleReturnMoney = rSSRS.GetText(1, 1);//总还款额度
		String totleReturnInterest =  rSSRS.GetText(1, 2);//总还款利息
		String totleReturnSum =  rSSRS.GetText(1, 3);//总还款金额
		double dReturnMoney = Double.parseDouble(totleReturnMoney);
		double dReturnInterest = Double.parseDouble(totleReturnInterest);

		// 插入 LJSGetEndorse 一条是还款额度,一条是利息
		//1 还款额度
		if(dReturnMoney >0){
			String sFeeFinaType = BqCalBL.getFinType(tLPGrpEdorItemSchema
					.getEdorType(), "HK", "000000");
			LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
			tLJSGetEndorseSchemaNew.setGetNoticeNo(tLPGrpEdorItemSchema
					.getEdorAcceptNo());
			tLJSGetEndorseSchemaNew.setEndorsementNo(tLPGrpEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchemaNew.setFeeOperationType(tLPGrpEdorItemSchema
					.getEdorType());
			tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeFinaType);
			tLJSGetEndorseSchemaNew.setGrpContNo(tLPGrpEdorItemSchema
					.getGrpContNo());
			tLJSGetEndorseSchemaNew.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLJSGetEndorseSchemaNew.setContNo("00000000000000000000");
			tLJSGetEndorseSchemaNew.setPolNo("00000000000000000000");
			tLJSGetEndorseSchemaNew.setOtherNo(tLPGrpEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchemaNew.setOtherNoType("3");
			tLJSGetEndorseSchemaNew.setDutyCode("000000");
			tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
			tLJSGetEndorseSchemaNew.setGetDate(sCurrentDate);
			tLJSGetEndorseSchemaNew.setGetMoney(dReturnMoney); // 正数表示交费
			tLJSGetEndorseSchemaNew.setManageCom(tLCGrpContSchema.getManageCom());
			tLJSGetEndorseSchemaNew.setPolType("1");
			tLJSGetEndorseSchemaNew.setGetFlag("0");
			tLJSGetEndorseSchemaNew.setOperator(tLPGrpEdorItemSchema.getOperator());
			tLJSGetEndorseSchemaNew.setMakeDate(sCurrentDate);
			tLJSGetEndorseSchemaNew.setMakeTime(sCurrentTime);
			tLJSGetEndorseSchemaNew.setModifyDate(sCurrentDate);
			tLJSGetEndorseSchemaNew.setModifyTime(sCurrentTime);
			tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Pay_LoanCorpus);
			tMap.put(tLJSGetEndorseSchemaNew, "DELETE&INSERT");
		}
		//2 生成利息的补退费记录
		if(dReturnInterest>0)
		{
			String sFeeFinaType = BqCalBL.getFinType(tLPGrpEdorItemSchema
					.getEdorType(), "LX", "000000");
			LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
			tLJSGetEndorseSchemaNew.setGetNoticeNo(tLPGrpEdorItemSchema
					.getEdorAcceptNo());
			tLJSGetEndorseSchemaNew.setEndorsementNo(tLPGrpEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchemaNew.setFeeOperationType(tLPGrpEdorItemSchema
					.getEdorType());
			tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeFinaType);
			tLJSGetEndorseSchemaNew.setGrpContNo(tLPGrpEdorItemSchema
					.getGrpContNo());
			tLJSGetEndorseSchemaNew.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLJSGetEndorseSchemaNew.setContNo("00000000000000000000");
			tLJSGetEndorseSchemaNew.setPolNo("00000000000000000000");
			tLJSGetEndorseSchemaNew.setOtherNo(tLPGrpEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchemaNew.setOtherNoType("3");
			tLJSGetEndorseSchemaNew.setDutyCode("000000");
			tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
			tLJSGetEndorseSchemaNew.setGetDate(sCurrentDate);
			tLJSGetEndorseSchemaNew.setGetMoney(dReturnInterest); // 正数表示交费
			tLJSGetEndorseSchemaNew.setManageCom(tLCGrpContSchema.getManageCom());
			tLJSGetEndorseSchemaNew.setPolType("1");
			tLJSGetEndorseSchemaNew.setGetFlag("0");
			tLJSGetEndorseSchemaNew.setOperator(tLPGrpEdorItemSchema.getOperator());
			tLJSGetEndorseSchemaNew.setMakeDate(sCurrentDate);
			tLJSGetEndorseSchemaNew.setMakeTime(sCurrentTime);
			tLJSGetEndorseSchemaNew.setModifyDate(sCurrentDate);
			tLJSGetEndorseSchemaNew.setModifyTime(sCurrentTime);
			tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Pay_LoanCorpusInterest);
			tMap.put(tLJSGetEndorseSchemaNew, "DELETE&INSERT");
		}

		//处理状态

		tSQL = "select nvl(sum(LeaveMoney),0) from LOLoan where "
			+" contno = '"+tLPGrpEdorItemSchema.getGrpContNo()+"' and loantype='0' and PayOffFlag='0'";
		rSSRS = new SSRS();
		rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(tSQL);
		if (rSSRS == null || rSSRS.MaxRow == 0) {
			CError tError = new CError();
			tError.moduleName = "GrPEdorRFDetailBL";
			tError.errorMessage = "查询还款记录失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tSumLoanMoney = rSSRS.GetText(1, 1);
		double dSumLoanMoney = Double.parseDouble(tSumLoanMoney);//
		// 删除 LPGrpContState
		String DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
		+ "and EdorNo = '" + tLPGrpEdorItemSchema.getEdorNo() + "' "
		+ "and EdorType = '" + tLPGrpEdorItemSchema.getEdorType()
		+ "' " + "and GrpContNo = '"
		+ tLPGrpEdorItemSchema.getGrpContNo() + "' "
		+ "and StateType = 'Loan'";
		// logger.debug(DeleteSQL);
		tMap.put(DeleteSQL, "DELETE");
		if(dSumLoanMoney<=dReturnMoney)
		{

			// 查询 LCGrpContState
			LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
			tLCGrpContStateDB.setGrpContNo(tLPGrpEdorItemSchema.getGrpContNo());
			tLCGrpContStateDB.setStateType("Loan"); // 复用注意修改
			LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
			try {
				tLCGrpContStateSet = tLCGrpContStateDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询新契约团体保单状态表出现异常！");
				return false;
			}
			if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
				// 拷贝 LCGrpContState 到 LPGrpContState
				LPGrpContStateSet tLPGrpContStateSetNew = new LPGrpContStateSet();
				for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
					LCGrpContStateSchema tLCGrpContStateSchema = tLCGrpContStateSet.get(i);
					LPGrpContStateSchema tLPGrpContStateSchemaNew = new LPGrpContStateSchema();
					tReflections.transFields(tLPGrpContStateSchemaNew,tLCGrpContStateSchema);
					tLPGrpContStateSchemaNew.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
					tLPGrpContStateSchemaNew.setEdorType(tLPGrpEdorItemSchema.getEdorType());
					// 结束上一状态
					String sEndDate = tLCGrpContStateSchema.getEndDate();
					if (sEndDate == null || sEndDate.trim().equals("")) {
						sEndDate = PubFun.calDate(tLPGrpEdorItemSchema.getEdorValiDate(), -1, "D", null);
						tLPGrpContStateSchemaNew.setEndDate(sEndDate);
					}
					tLPGrpContStateSchemaNew.setOperator(tLPGrpEdorItemSchema.getOperator());
					tLPGrpContStateSchemaNew.setModifyDate(sCurrentDate);
					tLPGrpContStateSchemaNew.setModifyTime(sCurrentTime);
					tLPGrpContStateSetNew.add(tLPGrpContStateSchemaNew);
					tLPGrpContStateSchemaNew = null;
					tLCGrpContStateSchema = null;
				}
				
				tMap.put(tLPGrpContStateSetNew, "DELETE&INSERT");
				
				// 插入 LPGrpContState
				LPGrpContStateSchema tLPGrpContStateSchemaNew = new LPGrpContStateSchema();
				tLPGrpContStateSchemaNew.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
				tLPGrpContStateSchemaNew
				.setEdorType(tLPGrpEdorItemSchema.getEdorType());
				tLPGrpContStateSchemaNew.setGrpContNo(tLPGrpEdorItemSchema
						.getGrpContNo());
				tLPGrpContStateSchemaNew.setGrpPolNo("000000");
				tLPGrpContStateSchemaNew.setStateType("Loan"); // 复用注意修改
				tLPGrpContStateSchemaNew.setState("0");
				tLPGrpContStateSchemaNew.setStartDate(tLPGrpEdorItemSchema
						.getEdorValiDate());
				tLPGrpContStateSchemaNew.setEndDate("");
				tLPGrpContStateSchemaNew.setOperator(tLPGrpEdorItemSchema.getOperator());
				tLPGrpContStateSchemaNew.setMakeDate(sCurrentDate);
				tLPGrpContStateSchemaNew.setMakeTime(sCurrentTime);
				tLPGrpContStateSchemaNew.setModifyDate(sCurrentDate);
				tLPGrpContStateSchemaNew.setModifyTime(sCurrentTime);
				tMap.put(tLPGrpContStateSchemaNew, "DELETE&INSERT");
			}
		}
		tLPGrpEdorItemSchema.setGetMoney(totleReturnSum);
		tLPGrpEdorItemSchema.setEdorState("1");
		tLPGrpEdorItemSchema.setModifyDate(sCurrentDate);
		tLPGrpEdorItemSchema.setModifyTime(sCurrentTime);
		tMap.put(tLPGrpEdorItemSchema, "UPDATE");
		
		rResultData = new VData();
		rResultData.add(tMap);
    	PubSubmit tSubmit = new PubSubmit();
    	if (!tSubmit.submitData(rResultData, ""))
    	{
    		CError.buildErr(this, "数据提交失败!");
			return false;
    		
    	}

		return true;
	}
	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// GrpEdorRFDetailBL tGrpEdorRFDetailBL = new GrpEdorRFDetailBL();
	// } //function main end
	// ==========================================================================

} // class GrpEdorRFDetailBL end
