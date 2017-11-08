package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LJTempFeeBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 执行个人正常交费核销事务（续期非催收-无应收表）
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

public class IndiNormalPayVerifyBL {
private static Logger logger = Logger.getLogger(IndiNormalPayVerifyBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperate;

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private String serNo = ""; // 流水号

	private String tLimit = "";

	private String payNo = ""; // 交费收据号

	private double mprem = 0; // 本次核销的保费
	private double taxAmount = 0;  //税费
	private double netAmount = 0;   //净值费

	// 保存操作员和管理机构的类
	private GlobalInput mGI = new GlobalInput();

	// 暂收费表
	private LJTempFeeSchema pLJTempFeeSchema = new LJTempFeeSchema();

	private LJTempFeeBL mLJTempFeeBL = new LJTempFeeBL();

	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	// 暂收费分类表
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();

	// 实收个人交费表
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

	// 实收总表
	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();

	// 个人保单表
	private LCPolBL mLCPolBL = new LCPolBL();

	private LCPolSet mLCPolSet = new LCPolSet();

	private LCContSchema mLCContSchema = new LCContSchema();

	// 保费项表
	private LCPremSet mLCPremSet = new LCPremSet();

	// 保险责任表LCDuty
	private LCDutySet mLCDutySet = new LCDutySet();

	private Date mLastPaytoDate = new Date(); // 现在的缴费对应日

	private Date mCurPayToDate = new Date(); // 下一次的缴费对应日

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	// 业务处理相关变量
	public IndiNormalPayVerifyBL() {
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
		return true;
	}

	// 责任层级处理
	private boolean dealDuty(LCPolSchema tLCPolSchema) {
		String DutySql = "select * from lcduty where polno='?polno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DutySql);
		sqlbv.put("polno", tLCPolSchema.getPolNo());
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutySet = tLCDutyDB.executeQuery(sqlbv);
		if (tLCDutyDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutySet.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "责任表查询失败!";
			this.mErrors.addOneError(tError);
			mLCPremSet.clear();
			return false;
		}		

		if (tLCDutySet.size() == 0) {
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "责任表中没有符合不定期交费的纪录!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= tLCDutySet.size(); i++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLCDutySchema = tLCDutySet.get(i);
			dealPrem(tLCDutySchema);
			tLCDutySchema.setSumPrem(tLCDutySchema.getSumPrem()
					+ tLCDutySchema.getPrem());
			mLCDutySet.add(tLCDutySchema);
		}
		return true;
	}

	// 保费层级处理
	private boolean dealPrem(LCDutySchema tLCDutySchema) {
		String PremSql = "select * from lcprem where dutycode='?dutycode?' and polno='?polno?' and UrgePayFlag='N' and payintv>0";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(PremSql);
		sqlbv1.put("dutycode", tLCDutySchema.getDutyCode());
		sqlbv1.put("polno", tLCDutySchema.getPolNo());
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		mLCPremSet = tLCPremDB.executeQuery(sqlbv1);
		if (tLCPremDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保费项表查询失败!";
			this.mErrors.addOneError(tError);
			mLCPremSet.clear();
			return false;
		}
		if (mLCPremSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保费项表中没有符合不定期交费的纪录!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= mLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = mLCPremSet.get(i);
			tLCPremSchema.setPayTimes(tLCPremSchema.getPayTimes() + 1);
			tLCPremSchema.setSumPrem(tLCPremSchema.getSumPrem()
					+ tLCPremSchema.getPrem());
			tLCPremSchema.setPaytoDate(mCurPayToDate);
			tLCPremSchema.setModifyDate(CurrentDate);
			tLCPremSchema.setModifyTime(CurrentTime);
			mLCPremSet.set(i, tLCPremSchema);

			// 生成实收子表
			prepareLJAPayPerson(tLCPremSchema);
		}
		return true;
	}

	// 生成实收子表
	private boolean prepareLJAPayPerson(LCPremSchema tLCPremSchema) {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tLCPremSchema.getPolNo());
		tLCPolDB.getInfo();
		LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
		tLJAPayPersonSchema.setPayNo(payNo);
		tLJAPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
		tLJAPayPersonSchema.setContNo(tLCPremSchema.getContNo());
		tLJAPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
		tLJAPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
		tLJAPayPersonSchema.setGetNoticeNo(mLJTempFeeBL.getTempFeeNo());
		tLJAPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes());
		tLJAPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
		tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
		tLJAPayPersonSchema.setManageCom(mLCContSchema.getManageCom());
		tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
		tLJAPayPersonSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLJAPayPersonSchema.setGrpPolNo("00000000000000000000");
		tLJAPayPersonSchema.setAgentCom(mLCContSchema.getAgentCom());
		tLJAPayPersonSchema.setAgentGroup(mLCContSchema.getAgentGroup());
		tLJAPayPersonSchema.setAgentType(mLCContSchema.getAgentType());
		tLJAPayPersonSchema.setPayAimClass("1");
		tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
		tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
		tLJAPayPersonSchema.setLastPayToDate(mLastPaytoDate);
		tLJAPayPersonSchema.setCurPayToDate(mCurPayToDate);
		tLJAPayPersonSchema.setPayType("ZC");
		tLJAPayPersonSchema.setPayDate("");
		tLJAPayPersonSchema.setRiskCode(tLCPolDB.getRiskCode());
		tLJAPayPersonSchema.setOperator(mGI.Operator);
		tLJAPayPersonSchema.setMakeDate(CurrentDate);
		tLJAPayPersonSchema.setMakeTime(CurrentTime);
		tLJAPayPersonSchema.setConfDate(CurrentDate);
		tLJAPayPersonSchema.setModifyDate(CurrentDate);
		tLJAPayPersonSchema.setModifyTime(CurrentTime);
		tLJAPayPersonSchema.setGrpPolNo(tLCPolDB.getGrpPolNo());
		mLJAPayPersonSet.add(tLJAPayPersonSchema);

		if (tLCPremSchema.getFreeFlag() != null) {
			if (tLCPremSchema.getFreeFlag().equals("1")
					&& tLCPremSchema.getFreeStartDate().compareTo(CurrentDate) <= 0
					&& tLCPremSchema.getFreeEndDate().compareTo(CurrentDate) >= 0) {
				tLJAPayPersonSchema = new LJAPayPersonSchema();
				tLJAPayPersonSchema.setPayNo(payNo);
				tLJAPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
				tLJAPayPersonSchema.setContNo(tLCPremSchema.getContNo());
				tLJAPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
				tLJAPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
				tLJAPayPersonSchema.setGetNoticeNo(mLJTempFeeBL.getTempFeeNo());
				tLJAPayPersonSchema
						.setPayCount(tLCPremSchema.getPayTimes() + 1);
				tLJAPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
				tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema
						.getPayPlanCode());
				tLJAPayPersonSchema.setManageCom(mLCContSchema.getManageCom());
				tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
				tLJAPayPersonSchema.setAgentCode(mLCContSchema.getAgentCode());
				tLJAPayPersonSchema.setGrpPolNo("00000000000000000000");
				tLJAPayPersonSchema.setAgentCom(mLCContSchema.getAgentCom());
				tLJAPayPersonSchema
						.setAgentGroup(mLCContSchema.getAgentGroup());
				tLJAPayPersonSchema.setAgentType(mLCContSchema.getAgentType());
				tLJAPayPersonSchema.setPayAimClass("1");
				tLJAPayPersonSchema
						.setSumActuPayMoney(-tLCPremSchema.getPrem());
				tLJAPayPersonSchema.setSumDuePayMoney(-tLCPremSchema.getPrem());
				tLJAPayPersonSchema.setLastPayToDate(tLCPremSchema
						.getPaytoDate());
				tLJAPayPersonSchema.setCurPayToDate(CurrentDate);
				tLJAPayPersonSchema.setPayType("HM");
				tLJAPayPersonSchema.setPayDate("");
				tLJAPayPersonSchema.setRiskCode(tLCPolDB.getRiskCode());
				tLJAPayPersonSchema.setConfDate(CurrentDate);
				tLJAPayPersonSchema.setOperator(mGI.Operator);
				tLJAPayPersonSchema.setMakeDate(CurrentDate);
				tLJAPayPersonSchema.setMakeTime(CurrentTime);
				tLJAPayPersonSchema.setModifyDate(CurrentDate);
				tLJAPayPersonSchema.setModifyTime(CurrentTime);
				tLJAPayPersonSchema.setGrpPolNo(tLCPolDB.getGrpPolNo());
				mLJAPayPersonSet.add(tLJAPayPersonSchema);
			}
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		double tempFeeMoney = 0;
		double sumPrem = 0;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		String TempFeeSql = "select * from ljtempfee where 1=1 and (enteraccdate is not null and enteraccdate<>'3000-01-01') and confdate is null and tempfeeno='?tempfeeno?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(TempFeeSql);
		sqlbv2.put("tempfeeno", pLJTempFeeSchema.getTempFeeNo());
		logger.debug("查询暂交费信息:::" + TempFeeSql);
		mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv2);
		if (mLJTempFeeSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "暂交费查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mLJTempFeeBL.setSchema((LJTempFeeSchema) mLJTempFeeSet.get(1));
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		tLJTempFeeClassDB.setTempFeeNo(mLJTempFeeBL.getTempFeeNo());
		mLJTempFeeClassSet = tLJTempFeeClassDB.query();
		if (mLJTempFeeClassSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "交费方式查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tempFeeMoney = mLJTempFeeBL.getPayMoney();
		logger.debug("tempFeeMoney==" + tempFeeMoney);
		String tContNo = mLJTempFeeBL.getOtherNo();

		// 查询保单层级信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		tLCContDB.getInfo();
		mLCContSchema = tLCContDB.getSchema();
		if (mLCContSchema == null) {
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "个人保单表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 非不定期保单，不能核销
		if (mLCContSchema.getPayIntv() != -1) {
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "非不定期保单，不能核销";
			this.mErrors.addOneError(tError);
			return false;
		}

		boolean tReturn = false;
		logger.debug("查询保单表");
		// 1-查询保单表
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		String sqlStr = "select * from LCPol where ContNo='?tContNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("tContNo", tContNo);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv3);
		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "个人保单表查询失败!";
			this.mErrors.addOneError(tError);
			tLCPolSet.clear();
			return false;
		}
		if (tLCPolSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "个人保单表没有查到相关纪录!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			mprem = mprem + tLCPolSet.get(i).getPrem();

		}
		if (tempFeeMoney < mprem) {
			this.mErrors.addOneError("暂交费金额:" + tempFeeMoney + "元，不够支付保费:"
					+ mprem + "元");
			return false;
		}
		mLCPolBL.setSchema(tLCPolSet.get(1));

		tReturn = true;

		// 产生流水号
		tLimit = PubFun.getNoLimit(mLCPolBL.getManageCom());
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		// 产生交费收据号
		tLimit = PubFun.getNoLimit(mLCPolBL.getManageCom());
		payNo = PubFun1.CreateMaxNo("PayNo", tLimit);

		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			// 计算保单交费日期
			logger.debug("计算新的保单交费日期");
			logger.debug("原交费日期=" + tLCPolSchema.getPaytoDate());
			String LastPaytoDate = tLCPolSchema.getPaytoDate();
			int PayInterval = tLCPolSchema.getPayIntv();

			FDate tD = new FDate();
			mLastPaytoDate = tD.getDate(LastPaytoDate);
			mCurPayToDate = PubFun.calDate(mLastPaytoDate, PayInterval, "M",
					null);
			
			// 处理责任
			dealDuty(tLCPolSchema);

			tLCPolSchema.setPaytoDate(mCurPayToDate);
			tLCPolSchema.setSumPrem(tLCPolSchema.getSumPrem()
					+ tLCPolSchema.getPrem());
			tLCPolSchema.setModifyDate(CurrentDate);
			tLCPolSchema.setModifyTime(CurrentTime);
			mLCPolSet.add(tLCPolSchema);
		}

		for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
			mLJTempFeeSet.get(i).setConfDate(CurrentDate);
			mLJTempFeeSet.get(i).setConfFlag("1");
			mLJTempFeeSet.get(i).setModifyDate(CurrentDate);
			mLJTempFeeSet.get(i).setModifyTime(CurrentTime);
		}

		for (int t = 1; t <= mLJTempFeeClassSet.size(); t++) {
			mLJTempFeeClassSet.get(t).setConfDate(CurrentDate);
			mLJTempFeeClassSet.get(t).setConfFlag("1");
			mLJTempFeeClassSet.get(t).setModifyDate(CurrentDate);
			mLJTempFeeClassSet.get(t).setModifyTime(CurrentTime);
		}

		// 更新保单账户
		mLCContSchema.setDif(mLCContSchema.getDif() - mprem);
		mLCContSchema.setModifyDate(CurrentDate);
		mLCContSchema.setModifyTime(CurrentTime);
		mLCContSchema.setSumPrem(mLCContSchema.getSumPrem() + mprem);

		// 1-个人保单表和暂交费表数据填充实收总表
		mLJAPaySchema.setPayNo(payNo); // 交费收据号码
		mLJAPaySchema.setIncomeNo(mLCContSchema.getContNo()); // 应收/实收编号
		mLJAPaySchema.setIncomeType("2"); // 应收/实收编号类型
		mLJAPaySchema.setAppntNo(mLCPolBL.getAppntNo()); // 投保人客户号码
		mLJAPaySchema.setSumActuPayMoney(mprem); // 总实交金额
		mLJAPaySchema.setEnterAccDate(mLJTempFeeBL.getEnterAccDate()); // 到帐日期
		mLJAPaySchema.setPayDate(mLJTempFeeBL.getPayDate()); // 交费日期
		mLJAPaySchema.setConfDate(mLJTempFeeBL.getConfDate()); // 确认日期
		mLJAPaySchema.setApproveCode(mLCPolBL.getApproveCode()); // 复核人编码
		mLJAPaySchema.setApproveDate(mLCPolBL.getApproveDate()); // 复核日期
		mLJAPaySchema.setGetNoticeNo(mLJTempFeeBL.getTempFeeNo()); // 交费通知书号
		mLJAPaySchema.setSerialNo(serNo); // 流水号
		mLJAPaySchema.setOtherNoType("2");
		mLJAPaySchema.setOtherNo(mLCContSchema.getContNo());
		mLJAPaySchema.setOperator(mGI.Operator); // 操作员
		mLJAPaySchema.setMakeDate(CurrentDate); // 入机时间
		mLJAPaySchema.setMakeTime(CurrentTime); // 入机时间
		mLJAPaySchema.setModifyDate(CurrentDate); // 最后一次修改日期
		mLJAPaySchema.setModifyTime(CurrentTime); // 最后一次修改时间
		mLJAPaySchema.setManageCom(mLCPolBL.getManageCom());
		/*
		 * Lis5.3 upgrade get mLJAPayBL.setBankCode(mLCPolBL.getBankCode());
		 * //银行编码 mLJAPayBL.setBankAccNo(mLCPolBL.getBankAccNo()); //银行帐号
		 */
		mLJAPaySchema.setRiskCode(mLCPolBL.getRiskCode()); // 险种编码
		mLJAPaySchema.setAgentCode(mLCPolBL.getAgentCode());
		mLJAPaySchema.setAgentGroup(mLCPolBL.getAgentGroup());
		// 更新完毕
		/** @todo 生成打印管理表数据* */
		VData prtData = getPrintData(mLCContSchema, mLJAPaySchema, payNo);
		mLOPRTManagerSet = (LOPRTManagerSet) prtData.getObjectByObjectName(
				"LOPRTManagerSet", 0);
		if (mLOPRTManagerSet.size() == 0) {
			this.mErrors.addOneError("保单号:" + mLCContSchema.getContNo()
					+ "生成通知书&发票数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		// 暂交费表
		pLJTempFeeSchema = ((LJTempFeeSchema) mInputData.getObjectByObjectName(
				"LJTempFeeSchema", 0));
		mGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		// pLCPremSet=(LCPremSet)mInputData.getObjectByObjectName("LCPremSet",0);

		if (pLJTempFeeSchema == null || mGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiNormalPayVerifyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private VData getPrintData(LCContSchema tLCContchema,
			LJAPaySchema mLJAPaySchema, String prtSeqNo) {
		VData tVData = new VData();
		String tLimit = PubFun.getNoLimit(tLCContchema.getManageCom());
		String tNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		// 产生打印通知书号
		String prtSeqNo1 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		// 发票（个人、银代）
		LOPRTManagerSchema dLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			dLOPRTManagerSchema.setPrtSeq(prtSeqNo1);
			dLOPRTManagerSchema.setOtherNo(tLCContchema.getContNo());
			dLOPRTManagerSchema.setOtherNoType("00");
			dLOPRTManagerSchema.setCode("32");

			dLOPRTManagerSchema.setManageCom(tLCContchema.getManageCom());
			dLOPRTManagerSchema.setAgentCode(tLCContchema.getAgentCode());
			dLOPRTManagerSchema.setReqCom(tLCContchema.getManageCom());
			dLOPRTManagerSchema.setReqOperator(tLCContchema.getOperator());
			dLOPRTManagerSchema.setPrtType("0");
			dLOPRTManagerSchema.setStateFlag("0");
			dLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			dLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			dLOPRTManagerSchema.setOldPrtSeq(prtSeqNo1);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			String psql = "select * from lcpol where contno='?contno?' and mainpolno=polno ";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(psql);
			sqlbv4.put("contno", tLCContchema.getContNo());
			tLCPolSet = tLCPolDB.executeQuery(sqlbv4);
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1).getSchema(); // 目前一个合同只有一个主险
			String tpaytodate = tLCPolSchema.getPaytoDate();
			dLOPRTManagerSchema.setStandbyFlag3(tpaytodate);
			dLOPRTManagerSchema.setStandbyFlag4(String.valueOf(mLJAPaySchema
					.getSumActuPayMoney()));
			dLOPRTManagerSchema.setStandbyFlag1(mLJAPaySchema.getGetNoticeNo());
			dLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}

		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		tLOPRTManagerSet.add(dLOPRTManagerSchema);
		tVData.add(tLOPRTManagerSet);
		return tVData;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		map.put(mLJAPaySchema, "INSERT");
		map.put(mLJAPayPersonSet, "INSERT");
		map.put(mLCPolSet, "UPDATE");
		map.put(mLCPremSet, "UPDATE");
		map.put(mLCDutySet, "UPDATE");
		map.put(mLCContSchema, "UPDATE");
		map.put(mLJTempFeeSet, "UPDATE");
		map.put(mLJTempFeeClassSet, "UPDATE");
		map.put(mLOPRTManagerSet, "INSERT");
		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
	
	public static void main(String[] args) {
		VData tVData = new VData();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo("3602100035322");
		GlobalInput mGI = new GlobalInput();
		mGI.ComCode = "86";
		mGI.Operator = "001";
		mGI.ManageCom = "86";
		tVData.add(tLJTempFeeSchema);
		tVData.add(mGI);
		IndiNormalPayVerifyBL tIndiNormalPayVerifyBL = new IndiNormalPayVerifyBL();
		tIndiNormalPayVerifyBL.submitData(tVData, "VERIFY");
	}
}
