package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLJSPayPersonSchema;
import com.sinosoft.lis.schema.LLJSPaySchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLJSPayPersonSet;
import com.sinosoft.lis.vschema.LLJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单结算,补交保费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLBalRecruitFeeBL {
private static Logger logger = Logger.getLogger(LLBalRecruitFeeBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mClmNo = ""; // 赔案号
	private String mAccDate = ""; // 意外事故发生日期
	private String mRgtDate = ""; // 立案日期

	private String mFeeOperationType = ""; // 业务类型
	private String mSubFeeOperationType = ""; // 子业务类型
	private String mFeeFinaType = ""; // 财务类型

	public LLBalRecruitFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("--------保单结算,补交保费-------开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		// if (!pubSubmit())
		// {
		// return false;
		// }
		logger.debug("----------保单结算,补交保费-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mAccDate = (String) mTransferData.getValueByName("AccDate"); // 意外事故发生日期

		this.mFeeOperationType = (String) mTransferData
				.getValueByName("FeeOperationType"); // 业务类型
		this.mSubFeeOperationType = (String) mTransferData
				.getValueByName("SubFeeOperationType"); // 子业务类型
		this.mFeeFinaType = (String) mTransferData
				.getValueByName("FeeFinaType"); // 财务类型

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/**
		 * ---------------------------------------------------------------------BEG
		 * No.0 判断是否存在暂交费
		 * ----------------------------------------------------------------------
		 */
		if (!getLJTempFee(mLCPolSchema, mAccDate)) {
			return false;
		}

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.1 判断如果为附加险,M为主险,S为附加险,主险才作补费处理
		 * ----------------------------------------------------------------------
		 */
		String tSql = "select SubRiskFlag from LMRiskApp where 1=1 "
				+ " and riskcode = '" + "?riskcode?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("riskcode", mLCPolSchema.getRiskCode());
		ExeSQL tExeSQL = new ExeSQL();
		String tSubRiskFlag = tExeSQL.getOneValue(sqlbv);
		if (tSubRiskFlag == null || tSubRiskFlag.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLBalRecruitFeeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询险种定义失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.2 计算缴费宽限期
		 * ----------------------------------------------------------------------
		 */
		String tApseDate = PubFun.calLapseDate(mLCPolSchema.getPaytoDate(),
				mLCPolSchema.getRiskCode());

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.3 计算缴费宽限期
		 * ----------------------------------------------------------------------
		 */
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterDB.setRgtNo(mClmNo);
		if (tLLRegisterDB.getInfo()) {
			tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
		}
		mRgtDate = tLLRegisterSchema.getRgtDate(); // 立案日期

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.4 判断出险日期(意外事故发生日期)、立案时间是否在宽限期内
		 * ----------------------------------------------------------------------
		 */
		String tBegin = PubFun.getLaterDate(mLCPolSchema.getPaytoDate(),
				mAccDate);
		String tEnd = PubFun.getBeforeDate(tApseDate, mAccDate);
		if (tBegin.equals(mAccDate) && tEnd.equals(mAccDate)) {
			// 判断立案日期是否在宽限期内
			// String tSeSql = "select substr(a.rgtdate,1,10) from llregister a
			// where "
			// + "a.rgtno = '" + mClmNo + "'";
			// ExeSQL tExeSQL2 = new ExeSQL();
			// String tRgtDate = tExeSQL2.getOneValue(tSeSql);
//			String tBDate = PubFun.getLaterDate(mLCPolSchema.getPaytoDate(),
//					mRgtDate);
//			String tEDate = PubFun.getBeforeDate(tApseDate, mRgtDate);
//			if (tBDate.equals(mRgtDate) && tEDate.equals(mRgtDate)) {
//				// 在宽限期内,则查找该主险种（保单）所在合同下所有险种进行补费操作
//				String tFsql1 = "";
//				tFsql1 = "select * from lcpol a where a.contno in ("
//						+ " select b.contno from lcpol b where b.polno = '"
//						+ mLCPolSchema.getPolNo() + "')";
//				LCPolDB tLCPolDB = new LCPolDB();
//				LCPolSet tLCPolSet = tLCPolDB.executeQuery(tFsql1);
//				if (tLCPolSet == null || tLCPolSet.size() == 0) {
//					// @@错误处理
//					this.mErrors.copyAllErrors(tLCPolDB.mErrors);
//					CError tError = new CError();
//					tError.moduleName = "LLBalRecruitFeeBL";
//					tError.functionName = "dealData";
//					tError.errorMessage = "没有查询合同下所有险种信息!";
//					this.mErrors.addOneError(tError);
//					return false;
//				}
//				for (int i = 1; i <= tLCPolSet.size(); i++) {
//					LCPolSchema tLCPolSchema = new LCPolSchema();
//					tLCPolSchema = tLCPolSet.get(i);
//					// 险种进行补费操作
//					if (!calSumMoney(tLCPolSchema)) {
//						return false;
//					}
//				}
//			} else {
//				// 不在宽限期内，对主险种进行补费操作
//				if (!calSumMoney(mLCPolSchema)) {
//					return false;
//				}
//			}
			String tFsql1 = "";
			tFsql1 = "select b.riskperiod from lmriskapp b where "
				+" riskcode='"+"?riskcode?"+"'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tFsql1);
			sqlbv1.put("riskcode", mLCPolSchema.getRiskCode());
			if("L".equals(tExeSQL.getOneValue(sqlbv1)))
			{
				FDate tFDate = new FDate();
				Date lastPayToDate = tFDate.getDate(mLCPolSchema.getPaytoDate());
				Date lastLapseDate = tFDate.getDate(tApseDate);
				double sumprem =0.0;
				while (lastPayToDate.before(tFDate.getDate(mAccDate)))
				{
					String strSQL = "select * From lcprem where  polno='"
					+ "?polno?" + "' and urgepayflag='Y' and payintv>0 and paytodate='"
					+ "?paytodate?" + "' and paytodate<payenddate ";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(strSQL);
					sqlbv2.put("polno", mLCPolSchema.getPolNo());
					sqlbv2.put("paytodate", mLCPolSchema.getPaytoDate());
					LCPremSet tLCPremSet = new LCPremDB().executeQuery(sqlbv2);
					if (tLCPremSet == null || tLCPremSet.size() <= 0) {
						CError.buildErr(this, "查找保单(" + mLCPolSchema.getPolNo() + ")应收保费记录失败!");
						return false;
					}
					for(int i=1;i<=tLCPremSet.size();i++)
					{
						strSQL="select 1 from lcprem where paytodate between freestartdate and freeenddate and freeflag = '1' "
							+" and polno='"+"?polno?"
							+"' and dutycode ='"+"?dutycode?"
							+"' and payplancode='"+"?payplancode?"+"'"
							;
						
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(strSQL);
						sqlbv3.put("polno", tLCPremSet.get(i).getPolNo());
						sqlbv3.put("dutycode", tLCPremSet.get(i).getDutyCode());
						sqlbv3.put("payplancode", tLCPremSet.get(i).getPayPlanCode());
						//豁免的保费排除
						if("1".equals(tExeSQL.getOneValue(sqlbv3)))
						{
							continue;
						}
						sumprem = sumprem+tLCPremSet.get(i).getPrem();
					}
					
					lastPayToDate = FinFeePubFun.calOFDate(lastPayToDate, mLCPolSchema.getPayIntv(), "M", tFDate.getDate(mLCPolSchema.getCValiDate()));
					logger.debug("[new PaytoDate] " + tFDate.getString(lastPayToDate));
				}
				
				if (!updateLLBalance(mLCPolSchema, -sumprem)) {
					return false;
				}
			}
			
			
		}

		return true;
	}

	/**
	 * 进行暂交费的判断
	 * 
	 * @param tLLExemptSchema
	 * @return
	 */
	private boolean getLJTempFee(LCPolSchema pLCPolSchema, String pDate) {

		String tSql = "";
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 判断LJSPayPerson应收个人交费表是否有数据,根据检索的条件
		 * 在LJTempFee暂交费表判断是否有数据,如果有,给出提示,要求先退费.
		 * 
		 * 根据保单险种号,出险日期 <= LastPayToDate原交至日期为条件进行判断 2005.05.01 <= 2005.10.01
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LJTempFee where 1=1 "
				+ " and TempFeeNo = (select GetNoticeNo from LJSPayPerson where 1=1 "
				+ " and LastPayToDate >= '" + "?mAccDate?" + "' "
				+ " and PolNo = '" + "?PolNo?" + "')"
				+ " and confflag not in ('1')";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("mAccDate", this.mAccDate);
		sqlbv4.put("PolNo", this.mLCPolSchema.getPolNo());
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv4);

		if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {

		} else {
			this.mErrors.addOneError("合同号:[" + mLCPolSchema.getContNo()
					+ "],保单险种号:[" + mLCPolSchema.getPolNo()
					+ "],存在暂交费记录,请先进行退费操作,然后再进行此操作!");
			return false;
		}
		return true;
	}

	/**
	 * 公用:对险种进行补费操作
	 * 
	 * @return boolean
	 */
	private boolean calSumMoney(LCPolSchema tLCPolSchema) {
		/**
		 * ---------------------------------------------------------------------
		 * No.1 判断是否银行在途,如否则添加到暂存表 2005-12-29 14:27 周磊
		 * ----------------------------------------------------------------------
		 */
		String tSqlStr = "";
		tSqlStr = " select * from LJSPay where GetNoticeNo in "
				+ "( select distinct GetNoticeNo from LJSPayPerson where 1=1 "
				+ " and PolNo = '" + "?PolNo?" + "'"
				+ " and LastPayToDate = '" + "?LastPayToDate?" + "'"
				+ ")";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSqlStr);
		sqlbv5.put("PolNo", tLCPolSchema.getPolNo());
		sqlbv5.put("LastPayToDate", tLCPolSchema.getPaytoDate());
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv5);

		LLJSPaySet tLLJSPaySaveSet = new LLJSPaySet();
		LLJSPayPersonSet tLLJSPayPersonSaveSet = new LLJSPayPersonSet();

		for (int i = 1; i <= tLJSPaySet.size(); i++) {
			String tGetNoticeNo = tLJSPaySet.get(i).getGetNoticeNo();
			String tBankOnTheWayFlag = StrTool.cTrim(tLJSPaySet.get(i)
					.getBankOnTheWayFlag()); // 1--银行在途标志，不允许删除
			if (tBankOnTheWayFlag.equals("1")) {
				mErrors.addOneError("通知书号码[" + tGetNoticeNo + "],合同号["
						+ tLCPolSchema.getContNo()
						+ "]的交费信息银行在途,不能进行保单结算,等回销后在进行此操作!!!");
				return false;
			}

			// 总应收转入理赔临时表
			LLJSPaySchema tLLJSPaySchema = new LLJSPaySchema();
			mReflections.transFields(tLLJSPaySchema, tLJSPaySet.get(i));
			tLLJSPaySchema.setClmNo(this.mClmNo);
			tLLJSPaySaveSet.add(tLLJSPaySchema);

			// 明细表转入理赔临时表
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setGetNoticeNo(tGetNoticeNo);
			LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();
			for (int j = 1; j <= tLJSPayPersonSet.size(); j++) {
				LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet
						.get(j);

				LLJSPayPersonSchema tLLJSPayPersonSchema = new LLJSPayPersonSchema();
				mReflections.transFields(tLLJSPayPersonSchema,
						tLJSPayPersonSchema);
				tLLJSPayPersonSchema.setClmNo(this.mClmNo);
				tLLJSPayPersonSaveSet.add(tLLJSPayPersonSchema);
			}
		}
		mMMap.put(tLLJSPaySaveSet, "DELETE&INSERT");
		mMMap.put(tLLJSPayPersonSaveSet, "DELETE&INSERT");

		/**
		 * ---------------------------------------------------------------------
		 * No.2 计算应收总额 计算补费本金
		 * ----------------------------------------------------------------------
		 */
		this.mFeeOperationType = "C02";
		this.mSubFeeOperationType = "C0201";
		this.mFeeFinaType = "BF";
		String tFsql2 = "";
		tFsql2 = "select sum(a.SumDuePayMoney) " + " from LJSPayPerson a "
				+ " where a.polno = '" + "?polno?" + "'"
				+ " and a.LastPayToDate = '" + "?LastPayToDate?"
				+ "'" + " group by polno";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tFsql2);
		sqlbv6.put("polno", tLCPolSchema.getPolNo());
		sqlbv6.put("LastPayToDate", tLCPolSchema.getPaytoDate());
		ExeSQL tExeSQL4 = new ExeSQL();
		String tSum = tExeSQL4.getOneValue(sqlbv6);

		if (tSum == null || tSum.equals("")) {
			tSum = "0";
		}
		double tSumM = Double.parseDouble(tSum);
		tSumM = Double.parseDouble(new DecimalFormat("0.00").format(tSumM));

		// 存负值
		tSumM = 0 - tSumM;

		if (tSumM != 0) {
			// 插入数据到理赔结算表
			if (!updateLLBalance(tLCPolSchema, tSumM)) {
				return false;
			}

			// 添加补交保费通知书打印,首先删除
			// String tDSql = "delete from LOPRTManager where 1=1 "
			// + " and OtherNo = '" + mClmNo + "'"
			// + " and Code = 'PCT008'";
			// mMMap.put(tDSql, "DELETE");
			//
			// if (!insertLOPRTManager("PCT008")) //插入补交保费通知书
			// {
			// return false;
			// }//补交保费通知书还原到LLClaimPolDealBLF类中处理
		}

		/*---------------------------------------------------------------------
		 * No.3 计算补交保费利息(取当前5年贷款利率)
		 * 计算利息
		 * 2005-8-29 17:37 周磊
		 * 2006-5-29 10:11 修改贷款利率为存款利率 周磊
		 * 2006-5-31 10:21 修改补费利率算法和保全一致,按描述取利率 周磊
		 *----------------------------------------------------------------------*/
		// 只有主险才算利息
		String tMainPolno = tLCPolSchema.getMainPolNo();
		String tPolno = tLCPolSchema.getPolNo();
		if (tMainPolno.equals(tPolno)) {
			this.mFeeOperationType = "C02";
			this.mSubFeeOperationType = "C0202";
			this.mFeeFinaType = "LX";

			// //取当前5年期定期存款年利率
			// String tSql = "select rate from ldbankrate where "
			// + " period = '5' and depst_loan = 'D' "
			// + " and type = 'F' and periodflag = 'Y' "
			// + " and '" + CurrentDate + "' >= declaredate "
			// + " and '" + CurrentDate + "' < nvl(enddate, '9999-12-31')";
			// ExeSQL tExeSQL = new ExeSQL();
			// String tRate = tExeSQL.getOneValue(tSql);
			// double aRate = Double.parseDouble(tRate);
			// tSumM = Math.abs(tSumM); //本金
			// double tRateFee = AccountManage.getMultiAccInterest(aRate,tSumM,
			// tLCPolSchema.getPaytoDate(),
			// mRgtDate,"C", "D");

			// 和保全的利息计算保持一致,首先准备参数
			AccountManage tAccountManage = new AccountManage();
			String tRiskcode = tLCPolSchema.getRiskCode();
			String tIntervalType = "Y";
			if (tLCPolSchema.getPayIntv() == 1) {
				tIntervalType = "M";
			}

			// 计算利息(取保全复效补费利率),如果失败返回
			if (tRiskcode != null
					&& !tAccountManage.calInterest("RE", Math.abs(tSumM),
							tLCPolSchema.getPaytoDate(), mRgtDate, tRiskcode,
							tIntervalType)) {
				mErrors.copyAllErrors(tAccountManage.mErrors);
				return false;
			}

			double tRateFee = tAccountManage.getCalResult(); // 获得计算后的利息结果

			// 存负值
			tRateFee = 0 - tRateFee;

			// 插入数据到理赔结算表
			if (tRateFee != 0) {
				if (!updateLLBalance(tLCPolSchema, tRateFee)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 公用:插入数据到理赔结算表
	 * 
	 * @return boolean
	 */
	private boolean updateLLBalance(LCPolSchema tLCPolSchema, double tCal) {
		if (mClmNo.equals("") || mFeeOperationType.equals("")
				|| mSubFeeOperationType.equals("") || mFeeFinaType.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LLBalRecruitFeeBL";
			tError.functionName = "updateLLBalance";
			tError.errorMessage = "传输到理赔结算表数据不齐全!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(mClmNo);
		tLLBalanceSchema.setFeeOperationType(mFeeOperationType); // 业务类型
		tLLBalanceSchema.setSubFeeOperationType(mSubFeeOperationType); // 子业务类型
		tLLBalanceSchema.setFeeFinaType(mFeeFinaType); // 财务类型

		tLLBalanceSchema.setBatNo("0"); // 批次号
		tLLBalanceSchema.setOtherNo("0"); // 其它号码
		tLLBalanceSchema.setOtherNoType("0"); // 其它号码类型
		tLLBalanceSchema.setSaleChnl("0"); // 销售渠道

		tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo()); // 集体合同号码
		tLLBalanceSchema.setContNo(tLCPolSchema.getContNo()); // 合同号码
		tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo()); // 保单号码

		tLLBalanceSchema.setDutyCode("0"); // 责任编码
		tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
		tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码

		tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode()); // 险类编码
		tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码
		tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion()); // 险种版本

		tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode()); // 代理人编码
		tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup()); // 代理人组别

		tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
		tLLBalanceSchema.setPay(tCal); // 赔付金额

		tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
		tLLBalanceSchema.setState("0"); // 状态,0有效
		tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

		tLLBalanceSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		tLLBalanceSchema.setAgentCom(""); // 代理机构
		tLLBalanceSchema.setAgentType(""); // 代理机构内部分类

		tLLBalanceSchema.setOperator(mGlobalInput.Operator); // 操作员
		tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setCustomerNo(tLCPolSchema.getInsuredNo());

		TaxCalculator.calBySchema(tLLBalanceSchema);   //计算净值费和税费
		mMMap.put(tLLBalanceSchema, "DELETE&INSERT");

		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 插入新值,生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mClmNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);

		// 查询立案信息
		LLCaseDB tLLCaseDB = new LLCaseDB();
		String tSSql = "select * from llcase where 1=1 " + " and caseno = '"
				+ "?caseno?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSSql);
		sqlbv7.put("caseno", mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(sqlbv7);
		String tCusNo = tLLCaseSet.get(1).getCustomerNo();

		tLOPRTManagerSchema.setStandbyFlag4(tCusNo); // 客户号码
		tLOPRTManagerSchema.setStandbyFlag5(mRgtDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("40"); // 赔案状态

		mMMap.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);

		mResult.clear();
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		String tSql = "";
		tSql = "select LCPol.* from LCPol where 1=1 "
				+ " and LCPol.polno = 'TY010327011000750000'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv8);

		LCPolSchema tLCPolSchema = tLCPolSet.get(1);

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", "90000027373");
		tTransferData.setNameAndValue("AccDate", "2006-4-21");
		tTransferData.setNameAndValue("FeeOperationType", "C02");
		tTransferData.setNameAndValue("SubFeeOperationType", "C0201");
		tTransferData.setNameAndValue("FeeFinaType", "BF");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		tVData.addElement(tLCPolSchema);

		LLBalRecruitFeeBL tLLBalRecruitFeeBL = new LLBalRecruitFeeBL();
		tLLBalRecruitFeeBL.submitData(tVData, "");
	}

}
