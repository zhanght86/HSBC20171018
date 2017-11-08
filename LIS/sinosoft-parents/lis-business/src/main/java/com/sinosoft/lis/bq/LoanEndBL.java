package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 贷款终止批处理BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Howie
 * @version 1.0
 */
public class LoanEndBL {
private static Logger logger = Logger.getLogger(LoanEndBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 业务处理相关变量 */
	LCPolSet mLCPolSet = null;
	LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mCountDate = ""; // 传入的当前计算日期
	private LCContStateSet mLCContStateSet = new LCContStateSet();

	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();

	// 用于提交单条保单的信息
	private MMap sMap = new MMap();

	/** 保全保单结算计算类 */
	BqPolBalBL tBqPolBalBL = new BqPolBalBL();

	public LoanEndBL() {
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 * @return
	 */
	public boolean submitData(VData cInputData, String tOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		// 根据业务逻辑对数据进行处理
		if (!this.dealData()) {
			return false;
		}
		// logger.debug("\n==> 准备数据完成，开始提交后台\n");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			try {
				mCountDate = (String) mInputData.getObjectByObjectName(
						"String", 0);
				if (mCountDate == null || mCountDate.equals("")) {
					mCountDate = PubFun.getCurrentDate();
				}
			} catch (Exception ex) {
				mCountDate = PubFun.getCurrentDate();
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @return
	 */
	private boolean dealData() {
		// 获取前台传入的日期数据
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);

		// 现金价值净额(现金价值除掉贷款本息和、自垫本息和， 之后的净额)
		double tPV = 0.0;
		// 现金价值
		double tCashValue = 0.0;
		// 贷款、自垫 本息和
		double tLoan = 0.0;
		double tAutoPay = 0.0;
		double tPrem = 0.0; // 期交保费
		String aPolNo = "";
		String aContNo = "";
		String tLoanDate = ""; // 贷款开始日期
		String tLoanByDate = ""; // 六个月后的贷款对应日
		String tNDate = PubFun.calDate(mCountDate, 1, "D", null); // 当前计算日期的次日
		FDate tFDate = new FDate();
		// 查询用
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;
		String strsql = "";

		// *********************************************//
		// 逐日判断“次日保单的现金价值是否大于贷款、自垫本息和”,这里不用筛选时间
		strsql = "Select ContNo,PolNo from LCContState a"
				+ " where StateType='Loan' and State='1' and EndDate is null "
				+ " and not exists ( select 'X' from LCContState where "
				+ " PolNo=a.PolNo " + " and ContNo=a.ContNo "
				+ " and StateType='Terminate' "
				+ " and State='1' and EndDate is null)"; // 不终止
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(strsql);
		logger.debug("strsql" + strsql);
		try {
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this, "查询贷款的保单状态信息时产生错误!");
			return false;
		}
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				aPolNo = tSSRS.GetText(i, 1);
				aContNo = tSSRS.GetText(i, 2);
				// 查询贷款起期
				LOLoanDB tLOLoanDB = new LOLoanDB();
				LOLoanSet tLOLoanSet = new LOLoanSet();
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanDB.setContNo(aContNo);
				tLOLoanDB.setPolNo(aPolNo);
				tLOLoanDB.setLoanType("0");
				tLOLoanDB.setPayOffFlag("0");
				tLOLoanSet = tLOLoanDB.query();
				if (tLOLoanSet == null || tLOLoanSet.size() <= 0) {
					// @这里作错误处理，是因为没有相应的记录日志
					// CError.buildErr(this, "没有查到未清偿的贷款业务!");
					logger.debug("日志：没有查到未清偿的贷款业务");
					continue;
				}
				// 目前业务上只允许一笔贷款，所以这里不循环了
				tLOLoanSchema = tLOLoanSet.get(1);
				tLoanDate = tLOLoanSchema.getLoanDate();
				tLoanByDate = PubFun.calDate(tLoanDate, 6, "M", null);
				// 当前日期大于贷款六个月后的对应日
				if (tFDate.getDate(tLoanByDate).compareTo(
						tFDate.getDate(mCountDate)) <= 0) {
					LCPolSchema tLCPolSchema = new LCPolSchema();
					LCPolDB tLCPolDB = new LCPolDB();
					tLCPolDB.setContNo(aContNo);
					tLCPolDB.setPolNo(aPolNo);
					if (tLCPolDB.getInfo()) {
						tLCPolSchema.setSchema(tLCPolDB.getSchema());
						mLCPolSchema.setSchema(tLCPolSchema);
						// mLCPolSet.add(tLCPolSchema);
					}
					// 取得当前计算日期的次日的保单现金价值
					tCashValue = tEdorCalZT.getCashValue(aPolNo, null, tNDate);
					logger.debug("tCashValue:" + tCashValue);
					tBqPolBalBL = new BqPolBalBL();
					// 取得当前计算日期的次日的保单未清偿贷款和自垫的本息和
					if (tBqPolBalBL.calLoanCorpusAddInterest(aContNo, tNDate)) {
						tLoan = tBqPolBalBL.getCalResult();
						logger.debug("tLoan:" + tLoan);
					}
					//add by jiaqiangli 2009-04-10 需要重新初始化类
					tBqPolBalBL = new BqPolBalBL();
					if (tBqPolBalBL.calAutoPayPremAddInterest(aPolNo, tNDate)) {
						tAutoPay = tBqPolBalBL.getCalResult();
						logger.debug("tAutoPay:" + tAutoPay);
					}
					// 现金价值净额(现金价值除掉贷款本息和、自垫本息和， 之后的净额)
					tPV = tCashValue - tLoan - tAutoPay;
					if (tPV < 0) {
						// 生成贷款终止通知书
						LOPRTManagerSchema tLOPRTManagerSchema = setLOPRTManager(
								aContNo, "BQ39");
						// tLOPRTManagerSet.add(tLOPRTManagerSchema);
						// 改变保单状态
						// LCContStateSchema tLCContStateSchema =
						// this.setLoanEndState(tLCPolSchema);
						// tLCContStateSet.add(tLCContStateSchema);
						tLCContStateSet = new LCContStateSet();
						tLCContStateSet = setLoanEndState(tLCPolSchema);
						mLCContStateSet.add(tLCContStateSet);

						// 每次提交
						sMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
						sMap.put(mLCContStateSet, "DELETE&INSERT");
						mResult.clear();
						mResult.add(sMap);
						PubSubmit tSubmit = new PubSubmit();
						if (!tSubmit.submitData(mResult, "")) {
							// 这里作错误处理，只记录错误信息，不返回结果
							this.mErrors.copyAllErrors(tSubmit.mErrors);
							CError tError = new CError();
							tError.moduleName = "LoanEndBL";
							tError.functionName = "dealData";
							tError.errorMessage = "数据提交失败! 保单号为: " + aContNo;
							this.mErrors.addOneError(tError);
							continue;
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * 贷款的保单险种状态更新
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @return LCContStateSet
	 */
	private LCContStateSet setLoanEndState(LCPolSchema tLCPolSchema) {
		LCContStateSchema tLCContStateSchema = new LCContStateSchema(); // 要返回的结果
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		LCContStateSet rLCContStateSet = new LCContStateSet();
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		String tSql;
		// 查询需改变的状态
		tSql = "SELECT *" + " FROM LCContState b" + " WHERE EndDate is null"
				+ " and b.polNo = '" + "?polNo?" + "'"
				+ " and b.contNo = '" + "?contNo?" + "'"
				+ " and not exists (select 'X' from LCContState"
				+ " where ContNo=b.ContNo" + " and InsuredNo=b.InsuredNo"
				+ " and PolNo=b.PolNo" + " and StateType=b.StateType"
				+ " and StartDate=b.StartDate" + " and StateType='Terminate'"
				+ " and State='1'" + " and EndDate is null)";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql);
        sqlbv.put("polNo", tLCPolSchema.getPolNo());
        sqlbv.put("contNo", tLCPolSchema.getContNo());
        
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			for (int i = 1; i <= tLCContStateSet.size(); i++) {
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema = tLCContStateSet.get(i);
				tLCContStateSchema.setEndDate(PubFun.calDate(tCurrentDate, -1,
						"D", null));
				tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
				tLCContStateSchema.setModifyDate(tCurrentDate);
				tLCContStateSchema.setModifyTime(tCurrentTime);
				rLCContStateSet.add(tLCContStateSchema);
			}
		}
		// 生成新状态记录
		tLCContStateSchema.setContNo(tLCPolSchema.getContNo());
		// tLCContStateSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLCContStateSchema.setInsuredNo("000000");
		tLCContStateSchema.setPolNo(tLCPolSchema.getPolNo());
		tLCContStateSchema.setStateType("Terminate");
		tLCContStateSchema.setState("1");
		tLCContStateSchema.setStartDate(tCurrentDate);
		tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
		tLCContStateSchema.setMakeDate(tCurrentDate);
		tLCContStateSchema.setMakeTime(tCurrentTime);
		tLCContStateSchema.setModifyDate(tCurrentDate);
		tLCContStateSchema.setModifyTime(tCurrentTime);
		rLCContStateSet.add(tLCContStateSchema);

		return rLCContStateSet;
	}

	/**
	 * 生成打印管理表数据
	 * 
	 * @param tContNo
	 *            String 保单号
	 * @param type
	 *            String 打印通知书类型
	 * @return LOPRTManagerSchema 打印记录
	 */
	private LOPRTManagerSchema setLOPRTManager(String tContNo, String type) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		String mLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
		tLOPRTManagerSchema.setPrtSeq(serNo);
		tLOPRTManagerSchema.setOtherNo(tContNo);
		tLOPRTManagerSchema.setOtherNoType("00"); // 个人保单号
		tLOPRTManagerSchema.setCode(type); // 通知书类型
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		if (tLCContDB.getInfo()) {
			tLCContSchema.setSchema(tLCContDB.getSchema());
		}
		if (tLCContSchema != null) {
			tLOPRTManagerSchema.setManageCom(tLCContSchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(tLCContSchema.getAgentCode());
		}
		tLOPRTManagerSchema.setPrtType("1"); // 后台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		// tLOPRTManagerSchema.setStandbyFlag1(tEdorNo); //预留字段
		// tLOPRTManagerSchema.setStandbyFlag2();

		return tLOPRTManagerSchema;
	}

	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "000"; // 系统自动操作
		mGlobalInput.ManageCom = "86";
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		LoanEndBL tLoanEndBL = new LoanEndBL();
		tVData.add(PubFun.getCurrentDate());
		// tVData.add("2005-05-20");
		boolean flag = tLoanEndBL.submitData(tVData, "");
		if (flag) {
			logger.debug("OK");
		} else
			logger.debug("Fail");
	}
}
