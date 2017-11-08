package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LCPremBL;
import com.sinosoft.lis.bl.LJAPayBL;
import com.sinosoft.lis.bl.LJAPayPersonBL;
import com.sinosoft.lis.bl.LJSPayBL;
import com.sinosoft.lis.bl.LJSPayPersonBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.finfee.TempFeeQueryUI;
import com.sinosoft.lis.pubfun.DealAccount;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class AutoDueFeeMultiBL {
private static Logger logger = Logger.getLogger(AutoDueFeeMultiBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String serNo = "";// 流水号
	private String tLimit = "";
	private String payNo = "";// 交费收据号

	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LJSPayPersonSet mLJSPayPersonNewSet = new LJSPayPersonSet();
	// 应收总表
	private LJSPayBL mLJSPayBL = new LJSPayBL();
	// 实收个人交费表
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	// 实收总表
	private LJAPayBL mLJAPayBL = new LJAPayBL();
	// 个人保单表
	private LCPolBL mLCPolBL = new LCPolBL();
	// 保费项表
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet mLCPremNewSet = new LCPremSet();
	// 保险责任表LCDuty
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySet mLCDutyNewSet = new LCDutySet();
	// 保险帐户表
	private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
	// 保险帐户表记价履历表
	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

	// 业务处理相关变量
	public AutoDueFeeMultiBL() {
	}

	public static void main(String[] args) {

	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("After prepareOutputData");

		logger.debug("Start IndiFinUrgeVerify BL Submit...");

		AutoDueFeeMultiBLS tAutoDueFeeMultiBLS = new AutoDueFeeMultiBLS();
		tAutoDueFeeMultiBLS.submitData(mInputData, cOperate);

		logger.debug("End LJIndiFinUrgeVerify BL Submit...");

		// 如果有需要处理的错误，则返回
		if (tAutoDueFeeMultiBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tAutoDueFeeMultiBLS.mErrors);
		}

		mInputData = null;
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		boolean tReturn = false;

		// step one-查询数据
		String sqlStr = "";
		String PolNo = mLJSPayBL.getOtherNo();
		String GetNoticeNo = mLJSPayBL.getGetNoticeNo();
		double actuMoney = 0;
		double leaveMoney = 0;
		double newLeavingMoney = 0;
		DealAccount tDealAccount = new DealAccount();
		VData tempVData = new VData();
		boolean actuVerifyFlag = false;

		// 保险帐户表
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		// 保险帐户表记价履历表
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		// 1-查询保单表
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		sqlStr = "select * from LCPol where PolNo='?PolNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlStr);
		sqlbv.put("PolNo", PolNo);
		logger.debug("查询保单表:" + sqlStr);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "dealData";
			tError.errorMessage = "个人保单表查询失败!";
			this.mErrors.addOneError(tError);
			tLCPolSet.clear();
			return false;
		}
		if (tLCPolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有查询到保单号为" + PolNo + "的个人保单!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCPolBL.setSchema(tLCPolSet.get(1));
		leaveMoney = mLCPolBL.getLeavingMoney();
		tReturn = true;

		// 产生流水号
		tLimit = PubFun.getNoLimit(mLCPolBL.getManageCom());
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		// 产生交费收据号
		tLimit = PubFun.getNoLimit(mLCPolBL.getManageCom());
		payNo = PubFun1.CreateMaxNo("PayNo", tLimit);
		// 2-查询应收个人表
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		sqlStr = "select * from LJSPayPerson where PolNo='?PolNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlStr);
		sqlbv1.put("PolNo", PolNo);
		logger.debug("查询应收个人表:" + sqlStr);
		mLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv1);
		if (tLJSPayPersonDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "dealData";
			tError.errorMessage = "应收个人表查询失败!";
			this.mErrors.addOneError(tError);
			mLJSPayPersonSet.clear();
			return false;
		}
		if (mLJSPayPersonSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "dealData";
			tError.errorMessage = "应收个人表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tReturn = true;

		// 3-查询保费项表//根据应收个人交费表查询该表项
		logger.debug("查询保费项表:");
		LJSPayPersonBL tLJSPayPersonBL;
		LCPremSet tLCPremSet;
		LCPremBL tLCPremBL;
		LCPremDB tLCPremDB = new LCPremDB();
		for (int num = 1; num <= mLJSPayPersonSet.size(); num++) {
			tLJSPayPersonBL = new LJSPayPersonBL();
			tLJSPayPersonBL.setSchema(mLJSPayPersonSet.get(num));
			// 得到个人实际交款总和:交费方式为ZC的纪录
			if (tLJSPayPersonBL.getPayType().equals("ZC"))
				actuMoney = actuMoney + tLJSPayPersonBL.getSumDuePayMoney();
			if (tLJSPayPersonBL.getPayType().equals("YET"))// 存放新余额的应收纪录
				newLeavingMoney = tLJSPayPersonBL.getSumActuPayMoney();
			logger.debug("getPayType():" + tLJSPayPersonBL.getPayType());
			logger.debug("actuMoney:" + actuMoney);
			logger.debug("newLeavingMoney:" + newLeavingMoney);

			sqlStr = "select * from LCPrem where PolNo='?PolNo?'";

			sqlStr = sqlStr + " and DutyCode='?DutyCode?'";
			sqlStr = sqlStr + " and PayPlanCode='?PayPlanCode?'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sqlStr);
			sqlbv2.put("PolNo", tLJSPayPersonBL.getPolNo());
			sqlbv2.put("DutyCode", tLJSPayPersonBL.getDutyCode());
			sqlbv2.put("PayPlanCode", tLJSPayPersonBL.getPayPlanCode());
			tLCPremSet = new LCPremSet();
			tLCPremDB = new LCPremDB();
			tLCPremSet = tLCPremDB.executeQuery(sqlbv2);
			logger.debug("sql:" + sqlStr);
			if (tLCPremDB.mErrors.needDealError() == true) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPremDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBL";
				tError.functionName = "dealData";
				tError.errorMessage = "保费项表查询失败!";
				this.mErrors.addOneError(tError);
				tLCPremSet.clear();
				mLCPremSet.clear();
				return false;
			}
			tLCPremBL = new LCPremBL();
			tLCPremBL.setSchema(tLCPremSet.get(1).getSchema());
			mLCPremSet.add(tLCPremBL);

			// 处理帐户
			tempVData = tDealAccount.addPrem(tLCPremSet.get(1).getSchema(),
					"2", GetNoticeNo, "2", "BF", null);
			if (tempVData != null) {
				tempVData = tDealAccount.updateLCInsureAccTraceDate(mLJSPayBL
						.getPayDate(), tempVData);
				if (tempVData == null) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "AutoDueFeeMultiBL";
					tError.functionName = "dealData";
					tError.errorMessage = "修改保险帐户表记价履历表纪录的交费日期时出错!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCInsureAccSet = (LCInsureAccSet) tempVData
						.getObjectByObjectName("LCInsureAccSet", 0);
				tLCInsureAccTraceSet = (LCInsureAccTraceSet) tempVData
						.getObjectByObjectName("LCInsureAccTraceSet", 0);
				mLCInsureAccSet.add(tLCInsureAccSet);
				mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
			}
		}
		tReturn = true;

		// 5-查询保险责任表
		LCDutyDB tLCDutyDB = new LCDutyDB();
		sqlStr = "select * from LCDuty where PolNo='?PolNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("PolNo", PolNo);
		logger.debug("查询保险责任表:" + sqlStr);
		mLCDutySet = tLCDutyDB.executeQuery(sqlbv3);
		if (tLCDutyDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保险责任表!";
			this.mErrors.addOneError(tError);
			mLCDutySet.clear();
			return false;
		}
		tReturn = true;

		// step two-处理数据
		// 实收款=actuMoney
		// 1 ZC=100 YEL=0 YET=0 actuMoney=100
		// 2 ZC=100 YEL=-50 YET=0 actuMoney=50
		// 3 ZC=100 YEL=-200 YET=100 actuMoney=0

		int i, iMax;
		// 添加纪录
		if (this.mOperate.equals("VERIFY")) {
			// 1-应收总表和暂交费表数据填充实收总表
			mLJAPayBL.setPayNo(payNo); // 交费收据号码
			mLJAPayBL.setIncomeNo(mLJSPayBL.getOtherNo()); // 应收/实收编号
			mLJAPayBL.setIncomeType(mLJSPayBL.getOtherNoType()); // 应收/实收编号类型
			mLJAPayBL.setAppntNo(mLJSPayBL.getAppntNo()); // 投保人客户号
			mLJAPayBL.setSumActuPayMoney(0); // 总实交金额
			mLJAPayBL.setEnterAccDate(CurrentDate); // 到帐日期
			mLJAPayBL.setConfDate(CurrentDate); // 确认日期
			mLJAPayBL.setGetNoticeNo(mLJSPayBL.getGetNoticeNo()); // 交费通知书号码
			mLJAPayBL.setPayDate(mLJSPayBL.getPayDate()); // 交费日期
			mLJAPayBL.setApproveCode(mLJSPayBL.getApproveCode()); // 复核人编码
			mLJAPayBL.setApproveDate(mLJSPayBL.getApproveDate()); // 复核日期
			mLJAPayBL.setSerialNo(serNo); // 流水号
			mLJAPayBL.setOperator(mLJSPayBL.getOperator()); // 操作员
			mLJAPayBL.setMakeDate(CurrentDate); // 入机时间
			mLJAPayBL.setMakeTime(CurrentTime); // 入机时间
			mLJAPayBL.setModifyDate(CurrentDate); // 最后一次修改日期
			mLJAPayBL.setModifyTime(CurrentTime); // 最后一次修改时间

			mLJAPayBL.setBankCode(mLJSPayBL.getBankCode()); // 银行编码
			mLJAPayBL.setBankAccNo(mLJSPayBL.getBankAccNo()); // 银行帐号
			mLJAPayBL.setRiskCode(mLJSPayBL.getRiskCode()); // 险种编码
			mLJAPayBL.setManageCom(mLJSPayBL.getManageCom());
			mLJAPayBL.setAgentCode(mLJSPayBL.getAgentCode());
			mLJAPayBL.setAgentGroup(mLJSPayBL.getAgentGroup());

			// 4-应收个人表填充实收个人表

			LJAPayPersonBL tLJAPayPersonBL;
			iMax = mLJSPayPersonSet.size();
			for (i = 1; i <= iMax; i++) {
				tLJSPayPersonBL = new LJSPayPersonBL();
				tLJAPayPersonBL = new LJAPayPersonBL();
				tLJSPayPersonBL.setSchema(mLJSPayPersonSet.get(i).getSchema());
				tLJAPayPersonBL.setPolNo(tLJSPayPersonBL.getPolNo()); // 保单号码
				tLJAPayPersonBL.setPayCount(tLJSPayPersonBL.getPayCount()); // 第几次交费
				tLJAPayPersonBL.setGrpPolNo(tLJSPayPersonBL.getGrpPolNo()); // 集体保单号码
				tLJAPayPersonBL.setContNo(tLJSPayPersonBL.getContNo()); // 总单/合同号码
				tLJAPayPersonBL.setAppntNo(tLJSPayPersonBL.getAppntNo()); // 投保人客户号码
				tLJAPayPersonBL.setPayNo(payNo); // 交费收据号码
				tLJAPayPersonBL
						.setPayAimClass(tLJSPayPersonBL.getPayAimClass());// 交费目的分类
				tLJAPayPersonBL.setDutyCode(tLJSPayPersonBL.getDutyCode()); // 责任编码
				tLJAPayPersonBL
						.setPayPlanCode(tLJSPayPersonBL.getPayPlanCode());// 交费计划编码
				tLJAPayPersonBL.setSumDuePayMoney(tLJSPayPersonBL
						.getSumDuePayMoney());// 总应交金额
				tLJAPayPersonBL.setSumActuPayMoney(tLJSPayPersonBL
						.getSumActuPayMoney());// 总实交金额
				tLJAPayPersonBL.setPayIntv(tLJSPayPersonBL.getPayIntv()); // 交费间隔
				tLJAPayPersonBL.setPayDate(tLJSPayPersonBL.getPayDate()); // 交费日期
				tLJAPayPersonBL.setPayType(tLJSPayPersonBL.getPayType()); // 交费类型
				tLJAPayPersonBL.setEnterAccDate(CurrentDate); // 到帐日期
				tLJAPayPersonBL.setConfDate(CurrentDate); // 确认日期
				tLJAPayPersonBL.setLastPayToDate(tLJSPayPersonBL
						.getLastPayToDate()); // 原交至日期
				tLJAPayPersonBL.setCurPayToDate(tLJSPayPersonBL
						.getCurPayToDate()); // 现交至日期
				tLJAPayPersonBL.setInInsuAccState(tLJSPayPersonBL
						.getInInsuAccState());// 转入保险帐户状态
				tLJAPayPersonBL
						.setApproveCode(tLJSPayPersonBL.getApproveCode()); // 复核人编码
				tLJAPayPersonBL
						.setApproveDate(tLJSPayPersonBL.getApproveDate()); // 复核日期
				tLJAPayPersonBL.setSerialNo(serNo); // 流水号
				tLJAPayPersonBL.setOperator(tLJSPayPersonBL.getOperator()); // 操作员
				tLJAPayPersonBL.setMakeDate(CurrentDate); // 入机日期
				tLJAPayPersonBL.setMakeTime(CurrentTime); // 入机时间
				tLJAPayPersonBL
						.setGetNoticeNo(tLJSPayPersonBL.getGetNoticeNo());// 通知书号码
				tLJAPayPersonBL.setModifyDate(CurrentDate); // 最后一次修改日期
				tLJAPayPersonBL.setModifyTime(CurrentTime); // 最后一次修改时间

				tLJAPayPersonBL.setManageCom(tLJSPayPersonBL.getManageCom()); // 管理机构
				tLJAPayPersonBL.setAgentCom(tLJSPayPersonBL.getAgentCom());// 代理机构
				tLJAPayPersonBL.setAgentType(tLJSPayPersonBL.getAgentType()); // 代理机构内部分类
				tLJAPayPersonBL.setRiskCode(tLJSPayPersonBL.getRiskCode()); // 险种编码
				tLJAPayPersonBL.setAgentCode(tLJSPayPersonBL.getAgentCode());
				tLJAPayPersonBL.setAgentGroup(tLJSPayPersonBL.getAgentGroup());
				mLJAPayPersonSet.add(tLJAPayPersonBL);
				tReturn = true;
			}
			// 5-更新保单表字段，取第一个应收个人交费纪录
			tLJSPayPersonBL = new LJSPayPersonBL();
			tLJSPayPersonBL.setSchema(mLJSPayPersonSet.get(1).getSchema());
			mLCPolBL.setPaytoDate(tLJSPayPersonBL.getCurPayToDate()); // 交至日期
			mLCPolBL.setSumPrem(mLCPolBL.getSumPrem() + actuMoney);// 总累计保费
			// 求总余额:如果应收总表应收款=0，表明有余额，且这次的余额值存放在责任编码为
			// "yet"的应收个人交费纪录中（见个人催收流程图）,取出放在个人保单表的余额字段中
			// 否则，个人保单表余额纪录置为0
			mLCPolBL.setLeavingMoney(newLeavingMoney);// 取应收纪录中交费方式为“YET”的金额

			mLCPolBL.setModifyDate(CurrentDate);// 最后一次修改日期
			mLCPolBL.setModifyTime(CurrentTime);// 最后一次修改时间
			tReturn = true;
			// 6-更新保费项表字段
			for (int num = 1; num <= mLCPremSet.size(); num++) {
				tLJSPayPersonBL = new LJSPayPersonBL();
				tLJSPayPersonBL.setSchema(mLJSPayPersonSet.get(num));
				tLCPremBL = new LCPremBL();
				tLCPremBL.setSchema(mLCPremSet.get(num));
				tLCPremBL.setPayTimes(tLCPremBL.getPayTimes() + 1); // 已交费次数
				// tLCPremBL.setPrem(tLJSPayPersonBL.getSumActuPayMoney());//实际保费
				tLCPremBL.setSumPrem(tLCPremBL.getSumPrem()
						+ tLCPremBL.getPrem());// 累计保费
				tLCPremBL.setPaytoDate(tLJSPayPersonBL.getCurPayToDate());// 交至日期
				tLCPremBL.setModifyDate(CurrentDate); // 最后一次修改日期
				tLCPremBL.setModifyTime(CurrentTime); // 最后一次修改时间
				mLCPremNewSet.add(tLCPremBL);
				logger.debug("更新保险责任表:");
				// 6-2更新保险责任表
				LCDutyBL tLCDutyBL;
				iMax = mLCDutySet.size();
				for (i = 1; i <= iMax; i++)
					;
				{
					tLCDutyBL = new LCDutyBL();
					tLCDutyBL.setSchema(mLCDutySet.get(1)); // repair:???
					if (tLCPremBL.getPolNo().equals(tLCDutyBL.getPolNo())
							&& tLCPremBL.getDutyCode().equals(
									tLCDutyBL.getDutyCode())) {
						tLCDutyBL.setPrem(tLCPremBL.getPrem());// 实际保费
						tLCDutyBL.setSumPrem(tLCDutyBL.getSumPrem()
								+ tLCPremBL.getPrem());// 累计保费
						tLCDutyBL.setPaytoDate(tLCPremBL.getPaytoDate());// 交至日期
						tLCDutyBL.setModifyDate(CurrentDate);// 最后一次修改日期
						tLCDutyBL.setModifyTime(CurrentTime);// 最后一次修改时间

						mLCDutyNewSet.add(tLCDutyBL);
						break;
					}// end if
				} // end for
			} // end for
			tReturn = true;
			// 更新完毕
		}
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		// 应收总表
		mLJSPayBL.setSchema((LJSPaySchema) mInputData.getObjectByObjectName(
				"LJSPaySchema", 0));
		if (mLJSPayBL == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到应收总表，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLJSPayBL.getSumDuePayMoney() > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "应收总表金额不为0，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			mInputData.add(mLJAPayBL); // 实收总表（插入）
			mInputData.add(mLJSPayBL); // 应收总表（删除）
			mInputData.add(mLJAPayPersonSet); // 实收个人表（插入）
			mInputData.add(mLJSPayPersonSet); // 应收个人交费表（删除）
			mInputData.add(mLCPolBL); // 个人保单表（更新）
			mInputData.add(mLCPremNewSet); // 保费项表（更新）
			mInputData.add(mLCDutyNewSet); // 保险责任表（更新）
			mInputData.add(mLCInsureAccSet); // 保险帐户表(更新或插入：处理时先删除再插入)
			mInputData.add(mLCInsureAccTraceSet); // 保险帐户表记价履历表（插入）
			logger.debug("prepareOutputData:");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 公共核销程序
	 * 
	 * @param TempFeeNo
	 *            暂交费号
	 * @return 包含纪录的集合(纪录如何处理具体见prepareOutputData函数)
	 */
	public VData ReturnData(String TempFeeNo) {
		if (TempFeeNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "ReturnData";
			tError.errorMessage = "传入暂交费号不能为空";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 1-查询暂交费表，将TempFeeNo输入Schema中传入，查询得到Set集
		VData tVData = new VData();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();
		tLJTempFeeSchema.setTempFeeNo(TempFeeNo);
		tLJTempFeeSchema.setTempFeeType("2");// 交费类型为2：续期催收交费
		tVData.add(tLJTempFeeSchema);
		if (!tTempFeeQueryUI.submitData(tVData, "QUERY")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tTempFeeQueryUI.mErrors);
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "ReturnData";
			tError.errorMessage = "暂交费查询失败";
			this.mErrors.addOneError(tError);
			return null;
		}
		tVData.clear();
		tVData = tTempFeeQueryUI.getResult();
		tLJTempFeeSet.set((LJTempFeeSet) tVData.getObjectByObjectName(
				"LJTempFeeSet", 0));
		tLJTempFeeSchema = tLJTempFeeSet.get(1);
		double tempMoney = tLJTempFeeSchema.getPayMoney();
		// 2-查询实收总表
		tVData.clear();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		LJSPaySet tLJSPaySet = new LJSPaySet();
		VerDuePayFeeQueryUI tVerDuePayFeeQueryUI = new VerDuePayFeeQueryUI();
		tLJSPaySchema.setGetNoticeNo(TempFeeNo);
		tVData.add(tLJSPaySchema);
		if (!tVerDuePayFeeQueryUI.submitData(tVData, "QUERY")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tVerDuePayFeeQueryUI.mErrors);
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "ReturnData";
			tError.errorMessage = "实收总查询失败";
			this.mErrors.addOneError(tError);
			return null;
		}
		tVData.clear();
		tVData = tVerDuePayFeeQueryUI.getResult();
		tLJSPaySet
				.set((LJSPaySet) tVData.getObjectByObjectName("LJSPaySet", 0));
		tLJSPaySchema = tLJSPaySet.get(1);
		double sumDueMoney = tLJSPaySchema.getSumDuePayMoney();
		if (sumDueMoney != tempMoney) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBL";
			tError.functionName = "ReturnData";
			tError.errorMessage = "实收总表纪录中的金额和暂交费纪录中的金额不相等！";
			this.mErrors.addOneError(tError);
			return null;
		}
		tVData.clear();
		tVData.add(tLJTempFeeSchema);
		tVData.add(tLJSPaySchema);
		// 3-调用核销程序
		// 将操作类型字符串
		this.mOperate = "VERIFY";
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tVData))
			return null;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealData())
			return null;
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData())
			return null;

		return mInputData;
	}

}
