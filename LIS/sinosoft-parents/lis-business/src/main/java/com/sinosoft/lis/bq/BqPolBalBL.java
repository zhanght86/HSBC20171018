package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * Title: 保全保单结算类
 * </p>
 * <p>
 * Description: 通过传入的保单信息和保费项目信息计算出：
 * </p>
 * <p>
 * 欠交保费、多交保费、未领领取、多领领取、未清偿贷款和利息、未清偿自垫保费和利息等信息
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wuhao
 * @version 1.0
 */
public class BqPolBalBL {
private static Logger logger = Logger.getLogger(BqPolBalBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	FDate aFDate = new FDate();

	private LCContStateSet mLCContStateSet = new LCContStateSet();
	private LOLoanSet mLOLoanSet = new LOLoanSet();
	private AccountManage mAccountManage = new AccountManage();

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCDutySet mLCDutySet = new LCDutySet();

	private LCPremSet mLCPremSet = new LCPremSet();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();

	// private String mPayToDate = ""; //交至日期
	private String mLapseDate = ""; // 宽限期止期
	private double mRate = 0.0; // 利率
	private double mInterest = 0.0; // 利息
	private double mGetMoney = 0.0; // 应付金额
	private double mTotalPrem = 0.0; // 总保费
	// private double mTotalInterest = 0.0; //总利息
	// private double mLoanMoney = 0.0; //贷款总额本金

	private String mPayEndYear;

	// 存储返回的计算结果
	private double mCountResult = 0.0;
	
	private double mOwePrem = 0.0; // 欠交保费
	private double mOweInterest = 0.0; // 欠交保费利息

	public BqPolBalBL() {
	}

	// 得到计算结果
	public double getCalResult() {
		return mCountResult;
	}

	// 得到四舍六入五靠偶数后的计算结果
	public double getCalResultRound() {
		return this.getRound(mCountResult);
	}

	// 四舍六入五靠偶数，保留两位
	private double getRound(double tValue) {
//		String t = "0.00";
//		DecimalFormat tDF = new DecimalFormat(t);
//		return Double.parseDouble(tDF.format(tValue));
		//add by jiaqiangli 2008-10-30 MS统一四舍五入
		return PubFun.round(tValue,2);
	}
	
	
	//add by jiaqiangli 2008-12-01 计算应交未交保费与利息
	//针对polno层
	public boolean calDuePremAddInterest(LCPolSchema tLCPolSchema,LPEdorItemSchema tLPEdorItemSchema) {
		FDate tFDate = new FDate(); // 日期格式化类
		Date lastPayToDate; // 最新交至日期
		Date lastLapseDate; // 最新宽限期止期
		
		LCPremSet tLCPremSet = null;
		String strSQL = null;
		
		lastPayToDate = tFDate.getDate(tLCPolSchema.getPaytoDate());
		lastLapseDate = tFDate.getDate(PubFun.calLapseDate(tLCPolSchema.getPaytoDate(), tLCPolSchema.getRiskCode()));
		
		double tPolOwePrem = 0.0;
		double tPolOweInterest = 0.0;
		
		//循环期数判断
		while (lastLapseDate.before(tFDate.getDate(tLPEdorItemSchema.getEdorValiDate()))
		// 保全复效日期已经超过宽限期
				&& lastPayToDate.before(tFDate.getDate(tLCPolSchema.getPayEndDate()))) {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			strSQL = "select * From lcprem where contno='" + "?contno?" + "' and polno='"
					+ "?polno?" + "' and urgepayflag='Y' and payintv>0 and paytodate='"
					+ "?paytodate?" + "' and paytodate<payenddate";
			sqlbv.sql(strSQL);
			sqlbv.put("contno", tLCPolSchema.getContNo());
			sqlbv.put("polno", tLCPolSchema.getPolNo());
			sqlbv.put("paytodate", tLCPolSchema.getPaytoDate());
			tLCPremSet = new LCPremDB().executeQuery(sqlbv);
			if (tLCPremSet == null || tLCPremSet.size() <= 0) {
				CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")应收保费记录失败!");
				return false;
			}
			for (int j = 1; j <= tLCPremSet.size(); j++) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLCPremSet.get(j).getPolNo());
				tLCDutyDB.setDutyCode(tLCPremSet.get(j).getDutyCode());
				if (!tLCDutyDB.getInfo()) {
					CError.buildErr(this, "获取保费记录对应责任信息失败!");
					return false;
				}
				double duePayPrem = tLCPremSet.get(j).getPrem();
				if ((tLCDutyDB.getFreeFlag() != null) && !tLCDutyDB.getFreeFlag().equals("1") && (tLCDutyDB.getFreeRate() <= 1)
						&& (tLCDutyDB.getFreeRate() > 0)) {
					// 有免交标记
					duePayPrem = tLCPremSet.get(j).getPrem() * (1 - tLCDutyDB.getFreeRate());
				}
				tPolOwePrem += duePayPrem;
			}
			//本次欠交保费的利息 宽末下一天计息
			tPolOweInterest = tPolOwePrem * AccountManage.calMultiRateMS(tFDate.getString(PubFun.calDate(lastLapseDate,1,"D",null)), tLPEdorItemSchema.getEdorValiDate(), tLCPolSchema.getRiskCode(),
					tLPEdorItemSchema.getEdorType(), "L", "C", "Y",tLCPolSchema.getCurrency());
			//下次循环的计数器处理
			//下一次paytodate
			lastPayToDate = FinFeePubFun.calOFDate(lastPayToDate, tLCPolSchema.getPayIntv(), "M", tFDate.getDate(tLCPolSchema.getCValiDate()));
			//下一次paytodate的宽末
			lastLapseDate = tFDate.getDate(PubFun.calLapseDate(tFDate.getString(lastPayToDate), tLCPolSchema.getRiskCode()));
		}
		this.mOwePrem += PubFun.round(tPolOwePrem, 2);
		this.mOweInterest += PubFun.round(tPolOweInterest, 2);
		return true;
	}
	//重载函数 针对contno层
	public boolean calDuePremAddInterest(LPEdorItemSchema tLPEdorItemSchema) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tAllPolno = "select * from lcpol where contno = '"+"?contno?"+"' ";
		sqlbv.sql(tAllPolno);
		sqlbv.put("contno", tLPEdorItemSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		int tSize = tLCPolSet.size();
		for (int i=1;i<=tSize;i++) {
			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			if (calDuePremAddInterest(tLCPolSchema,tLPEdorItemSchema) == false) {
				return false;
			}
		}
		return true;
	}
	public double getDuePremAddInterest() {
		return getRound(mOwePrem+mOweInterest);
	}
	//override
	public double getDuePrem() {
		return mOwePrem;
	}
	//override
	public double getDueAddInterest() {
		return mOweInterest;
	}
	//add by jiaqiangli 2008-12-01 计算应交未交保费与利息
	

	// ************************************************************************//

	/**
	 * 未清偿贷款本金 若产生错误 mCountResult返回-1
	 * 
	 * @param tContNo
	 *            String 保单号
	 * @param tCountDate
	 *            String 当前计算日期
	 * @return boolean false--失败 true--成功 返回: 贷款本金 mCountResult
	 */
	public boolean calLoanCorpus(String tContNo, String tCountDate) {
		// 贷款本金
		double tSumMoney = 0.0;
		// 准备从借款业务表中获取计算参数
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(tContNo);
		tLOLoanDB.setLoanType("0"); // 贷款类型
		tLOLoanDB.setPayOffFlag("0"); // 未还清
		mLOLoanSet.clear();
		mLOLoanSet = tLOLoanDB.query();
		if (mLOLoanSet != null && mLOLoanSet.size() > 0) {
			for (int i = 1; i <= mLOLoanSet.size(); i++) {
				tSumMoney += mLOLoanSet.get(i).getLeaveMoney();
			}
			mCountResult = tSumMoney;
			//mCountResult = this.getCalResultRound();
		} 
		return true;
	}

	/**
	 * 未清偿贷款本金 若产生错误 mCountResult返回-1
	 * 
	 * @param aLCPolSchema
	 *            LCPolSchema
	 * @param aContDate
	 *            String
	 * @return boolean
	 * @auther Lizhuo
	 */
	public boolean calLoanCorpus(LCPolSchema aLCPolSchema, String aCountDate) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aLCPolSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		if (!StrTool.compareString(aLCPolSchema.getMainPolNo(), aLCPolSchema
				.getPolNo())) {
			// 附加险不能进行贷款
			mCountResult = 0.0;
			return true;
		} else {
			this.calLoanCorpus(aLCPolSchema.getContNo(), aCountDate);
			return true;
		}
	}

	/**
	 * 未清偿贷款本息和的计算 若计算错误 mCountResult返回-1
	 * 
	 * @param tContNo
	 *            String 保单号
	 * @param tCountDate
	 *            String 当前计算日期
	 * @return boolean false--失败 true--成功 返回: 贷款本息和 mCountResult
	 */
	public boolean calLoanCorpusAddInterest(String tContNo, String tCountDate) {
		try {
			// 贷款本金
			double tSumMoney = 0.0;
			// 贷款利息
			double tInterest = 0.0;
			if (!calLoanInterest(tContNo, tCountDate)) {
				this.mCountResult = -1.0;
				return false;
			}
			tInterest = this.getCalResult();
			if (!calLoanCorpus(tContNo, tCountDate)) {
				this.mCountResult = -1.0;
				return false;
			}
			tSumMoney = this.getCalResult();
			mCountResult = tInterest + tSumMoney;
			//mCountResult = this.getCalResultRound();
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calLoanCorpusAddInterest";
			tError.errorMessage = "计算贷款本息和时产生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 未清偿贷款本息和的计算 若计算错误 mCountResult返回-1
	 * 
	 * @param aLCPolSchema
	 *            LCPolSchema
	 * @param aCountDate
	 *            String
	 * @return boolean
	 * 
	 */
	public boolean calLoanCorpusAddInterest(LCPolSchema aLCPolSchema,
			String aCountDate) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aLCPolSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		if (!StrTool.compareString(aLCPolSchema.getMainPolNo(), aLCPolSchema
				.getPolNo())) {
			// 附加险不能进行贷款
			mCountResult = 0.0;
			return true;
		} else {
			this.calLoanCorpusAddInterest(aLCPolSchema.getContNo(), aCountDate);
			return true;
		}
	}

	/**
	 * 未清偿贷款利息的计算 若计算错误 mCountResult返回-1
	 * 
	 * @param tContNo
	 *            String 保单号
	 * @param tCountDate
	 *            String 当前计算日期
	 * @return boolean false--失败 true--成功 返回: 贷款利息 mCountResult 现在允许多笔借款 add by
	 *         pst on 2007-11-28
	 */
	public boolean calLoanInterest(String tContNo, String tCountDate) {
		// 贷款本金
		double tSumMoney = 0.0;
		// 贷款起期
		String tLoanDate = ""; // 本金产生日期tCorpusDate YYYY-MM-DD
		String tCurrency = "";

		// 准备从借款业务表中获取计算参数
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(tContNo);
		tLOLoanDB.setLoanType("0"); // 贷款类型
		tLOLoanDB.setPayOffFlag("0"); // 未还清
		mLOLoanSet.clear();
		mLOLoanSet = tLOLoanDB.query();
		if (mLOLoanSet != null && mLOLoanSet.size() > 0) {
			for (int i = 1; i <= mLOLoanSet.size(); i++) {
				// 准备计算参数
				String tEdorType = "RF"; // 批改类型为贷款
				double tInterest = 0.0; // 每笔借款的利息
				// 现在业务有多笔借款，需要循环 MS
				tSumMoney = mLOLoanSet.get(i).getLeaveMoney(); // 余下金额，未还款则金额为初始金额
				tLoanDate = mLOLoanSet.get(i).getNewLoanDate();
				tCurrency =  mLOLoanSet.get(i).getCurrency();
				// 计算分段利率值，但不是利率，可以说一个参数，参数×本金=利息
				double tRate = AccountManage.calMultiRateMS(tLoanDate,  tCountDate, "000000",tEdorType,"L","C","Y",tCurrency);
				tInterest=tSumMoney*tRate;
				mInterest += tInterest;

			}
			mCountResult = mInterest;
			//mCountResult = this.getCalResultRound();
		}

		return true;
	}

	// /************贷款利率相关计算，实例计算*****Add by Nicholas****2005-08-10****\
	/**
	 * 获得某一天所在利率期贷款的“年利率”，如果产生错误则为-1
	 * 说明：如果没有找到相应险种编码对应的算法，则查找公共算法，即RiskCode为"000000"
	 * 注意：对于“贷款”，目前需求是，利率都按主险算，总先进价值为主险和长期附加险的和。（因为目前客户只有单一主险）
	 * 
	 * @param String
	 *            tContNo 保单号
	 * @param String
	 *            tLoanDate 要查询的日期（即贷款日期）
	 * @param String
	 *            tCalTypeFlag 计算类型（R或者O），用于区分贷款利率（Rate）和逾期期间利率（OverRate）
	 * @return boolean true-成功，false-失败。 结果通过getCalResult()获得
	 */
	public boolean calLoanRate(String tContNo, String tLoanDate,
			String tCalTypeFlag) {
		// 参数定义-->
		String tCalType = "";

		// 输入参数校验-->
		if (tContNo == null || tContNo.equals("")) {
			this.makeOneError("calLoanRate", "输入的保单号有误！");
			mCountResult = -1.0;
			return false;
		}
		if (tLoanDate == null || tLoanDate.equals("")) {
			this.makeOneError("calLoanRate", "输入的查询日期有误！");
			mCountResult = -1.0;
			return false;
		}
		if (tCalTypeFlag == null || tCalTypeFlag.equals("")) {
			this.makeOneError("calLoanRate", "输入的计算类型有误！");
			mCountResult = -1.0;
			return false;
		}
		if (tCalTypeFlag.equals("R") || tCalTypeFlag.equals("r")) {
			tCalType = "Rate";
		} else if (tCalTypeFlag.equals("O") || tCalTypeFlag.equals("o")) {
			tCalType = "OverRate";
		} else {
			this.makeOneError("calLoanRate", "输入的计算类型有误！");
			mCountResult = -1.0;
			return false;
		}

		// 查询主险的险种编码和终交年龄年期-->
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tSql = "SELECT RiskCode,PayEndYear FROM LCPol WHERE PolNo=MainPolNo and ContNo='"
				+ "?tContNo?" + "'";
		sqlbv.sql(tSql);
		sqlbv.put("tContNo", tContNo);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			this.makeOneError("calLoanRate", "查询主险信息时产生错误！");
			mCountResult = -1.0;
			return false;
		}

		// 查询算法编码-->
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(tSSRS.GetText(1, 1));
		tLMEdorCalDB.setEdorType("RF"); // 此函数仅适用与（报单滞压）贷款和贷款清偿
		tLMEdorCalDB.setCalType(tCalType);
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType("RF");
			tLMEdorCalDB.setCalType(tCalType);
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				// @@错误处理
				this.makeOneError("calLoanRate", "获得年利率查询算法时产生错误！" + " 保单号："
						+ tContNo);
				mCountResult = -1.0;
				return false;
			}
		}
		if (tLMEdorCalDB.mErrors.needDealError()) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType("RF");
			tLMEdorCalDB.setCalType(tCalType);
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				// @@错误处理
				this.makeOneError("calLoanRate", "获得年利率查询算法时产生错误！" + " 保单号："
						+ tContNo);
				mCountResult = -1.0;
				return false;
			}
		}

		// 组织要素-->
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setStartDate(tLoanDate);
		AccountManage tAccountManage = new AccountManage(tSSRS.GetText(1, 2));
		tBqCalBase.setBonusRate(tAccountManage.getBonusRate(tLoanDate, tSSRS
				.GetText(1, 1)));

		// 开始计算-->
		BqCalBL tBqCalBL = new BqCalBL();
		mCountResult = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).getCalCode(),
				tBqCalBase);
		if (tBqCalBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tBqCalBL.mErrors);
			mCountResult = -1.0;
			return false;
		}
		return true;
	}

	// 错误记录函数
	private void makeOneError(String tFunctionName, String tMessage) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "BqPolBalBL";
		tError.functionName = tFunctionName;
		tError.errorMessage = tMessage;
		this.mErrors.addOneError(tError);
		return;
	}

	// \************************************************************************/

	/**
	 * 未清偿贷款利息的计算 若计算错误 mCountResult返回-1
	 * 
	 * @param aLCPolSchema
	 *            LCPolSchema
	 * @param aCountDate
	 *            String
	 * @return boolean
	 * @auther Lizhuo
	 */
	public boolean calLoanInterest(LCPolSchema aLCPolSchema, String aCountDate) {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aLCPolSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		if (!StrTool.compareString(aLCPolSchema.getMainPolNo(), aLCPolSchema
				.getPolNo())) {
			// 附加险不能进行贷款
			mCountResult = 0;
			return true;
		} else {
			this.calLoanInterest(aLCPolSchema.getContNo(), aCountDate);
			return true;
		}
	}

	/**
	 * 计算指定贷款开始日期，六个月之后的贷款利息
	 * 
	 * @param tContNo
	 *            String 保单号
	 * @param tPolNo
	 *            String 保单险种号
	 * @param tSumMoney
	 *            double 贷款本金
	 * @param tLoanDate
	 *            String 贷款起期
	 * @return boolean
	 */
	public boolean calLoanSpecificInterest(String tContNo, double tSumMoney,
			String tLoanDate) {
		// 初始利率
		double tCommonRate = 0.0;
		// 初始利息
		double tCommonInterest = 0.0;
		// 贷款截止日期
		String tRequitalDate = "";
		// 逾期开始日期
		String tOverFistDate = "";

		// 查询用
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;

		// 输入参数校验-->
		if (tContNo == null || tContNo.equals("")) {
			this.makeOneError("calLoanSpecificInterest", "输入的保单号有误！");
			mCountResult = -1.0;
			return false;
		}
		// 准备计算参数
		String tEdorType = "RF"; // 批改类型为贷款
		String tRiskCode = "";

		String tPayEndYear = "";
		// 查询险种编码（RiskCode）
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tSql = "SELECT RiskCode,PayEndYear FROM LCPol WHERE ContNo='"
				+ "?tContNo?" + "' and PolNo = MainPolNo";
		sqlbv.sql(tSql);
		sqlbv.put("tContNo", tContNo);
		tExeSQL = new ExeSQL();
		tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			// @@错误处理
			CError.buildErr(this, "查询险种编码时产生错误!");
			return false;
		}
		tRiskCode = tSSRS.GetText(1, 1);
		tPayEndYear = tSSRS.GetText(1, 2);
		// 目前业务上只允许一笔贷款

		// 获得贷款截止日期，一般为贷款日6个月后
		tOverFistDate = PubFun.calDate(tLoanDate, 6, "M", null);
		tRequitalDate = PubFun.calDate(tOverFistDate, -1, "D", null);

		// 准备此要素，以便算红利率，从而算年利率
		mPayEndYear = tPayEndYear;
		mAccountManage.setPayEndYear(mPayEndYear);
		// add by zhangtao 2006-06-30 获取利息计算类型 M-按月计 D-按天
		String sIntervalType = EdorVerifyBL.getInterestCalType(tEdorType);
		// 初始利率
		if (!calRateByDate(tLoanDate, tRiskCode, "Rate")) {
			mCountResult = -1.0;
			return false;
		}
		tCommonRate = mRate;

		// 贷款期间的利息（贷款时间一般为6个月）
		mInterest = mAccountManage.getMultiAccInterest(tCommonRate, tSumMoney,
				tLoanDate, tRequitalDate, "C", sIntervalType); // modify by
																// zhangtao
																// 2006-06-30
																// 利息计算类型 M-按月计
																// D-按天
		// 贷款利息
		tCommonInterest = mInterest;
		mCountResult = tCommonInterest;
		mCountResult = this.getCalResultRound();
		return true;
	}

	/**
	 * 算年利率时有个要素是年度红利，算年度红利时要用到PayEndYear(终交年龄年期) 两个参数不同时为空，若同时不为空时，取根据险种层查出的结果。
	 * 
	 * @param tContNo
	 *            String 保单合同号
	 * @param tPolNo
	 *            String 保单险种号
	 * 
	 * @return String
	 */
	private String queryPayEndYear(String tContNo, String tPolNo) {
		String payEndYear = "";
		// 查询用
		ExeSQL tExeSQL = null;
		String strsql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (tContNo != null && !tContNo.equals(""))
			try {
				strsql = "Select PayEndYear From LCPol where ContNo ='"
						+ "?tContNo?" + "'" + " and PolNo = MainPolNo";
				sqlbv.sql(strsql);
				sqlbv.put("tContNo", tContNo);
				tExeSQL = new ExeSQL();
				payEndYear = tExeSQL.getOneValue(sqlbv);
			} catch (Exception e) {
				// @@错误处理
				CError.buildErr(this, "获得终交年龄年期(payEndYear)时产生错误!");
			}
		if (tPolNo != null && !tPolNo.equals("")) {
			try {
				strsql = "Select PayEndYear From LCPol where PolNo ='" + "?tPolNo?"
						+ "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(strsql);
				sqlbv.put("tPolNo", tPolNo);
				tExeSQL = new ExeSQL();
				payEndYear = tExeSQL.getOneValue(sqlbv);
			} catch (Exception e) {
				// @@错误处理
				CError.buildErr(this, "获得终交年龄年期(payEndYear)时产生错误!");
			}
		}
		return payEndYear;
	}

	/**
	 * 获得某一天所在利率期的“年利率”或“半年利率”以及“利率形式标志”，如果产生错误则为-1
	 * 说明：对于贷款，查到的利率可能是“年利率”或“半年利率”，但目前“利率形式标志”还不知道怎么得到，先按“年利率”处理
	 * 如果没有找到相应险种编码对应的算法，则查找公共算法，即RiskCode为"000000"
	 * 注意：对于“贷款”，目前需求是，利率都按主险算，总现金价值为主险和长期附加险的和。（因为目前客户只有单一主险）
	 * 
	 * @param String
	 *            tFindDate 要查询的日期
	 * @param String
	 *            tRiskCode 险种编码，传这个参数是为了（将来）把主险和长期附加险分开算的时候扩充用的
	 * @param String
	 *            tCalType 计算类型，用于区分贷款利率（Rate）和逾期期间利率（OverRate）
	 * @return boolean true-成功，false-失败。 结果存在mRate和mCountResult里
	 */
	public boolean calRateByDate(String tFindDate, String tRiskCode,
			String tCalType) {
		// 查询算法编码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(tRiskCode);
		tLMEdorCalDB.setEdorType("RF");
		tLMEdorCalDB.setCalType(tCalType);
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType("RF");
			tLMEdorCalDB.setCalType(tCalType);
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "BqPolBalBL";
				tError.functionName = "calRateByDate";
				tError.errorMessage = "获得年利率查询算法时产生错误！" + "保全项目：RF" + " 险种编码："
						+ tRiskCode;
				this.mErrors.addOneError(tError);
				mRate = -1.0;
				mCountResult = -1.0;
				return false;
			}
		}
		if (tLMEdorCalDB.mErrors.needDealError()) {
			// 查询公共的
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setEdorType("RF");
			tLMEdorCalDB.setCalType(tCalType);
			tLMEdorCalSet = tLMEdorCalDB.query();

			if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
				mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
				mRate = -1.0;
				mCountResult = -1.0;
				return false;
			}
		}
		// 组织要素
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setStartDate(tFindDate);

		AccountManage tAccountManage = new AccountManage(mPayEndYear);
		tBqCalBase.setBonusRate(tAccountManage.getBonusRate(tFindDate,
				tRiskCode));

		// 开始计算
		BqCalBL tBqCalBL = new BqCalBL();
		mRate = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).getCalCode(),
				tBqCalBase);
		mCountResult = mRate;
		if (tBqCalBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tBqCalBL.mErrors);
			mRate = -1.0;
			mCountResult = -1.0;
			return false;
		}
		return true;
	}

	// ************************************************************************//
	/**
	 * 未清偿自垫保费的计算，如果产生错误则为-1
	 * 
	 * @param aPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true-成功，返回：自垫保费 mCountResult
	 */
	public boolean calAutoPayPrem(String tPolNo, String tCountDate) {
		try {
			double tCorpusAddInterest = 0.0; // 本息和
			double tIntrerest = 0.0; // 利息
			if (!calAutoPayPremAddInterest(tPolNo, tCountDate)) {
				this.mCountResult = -1;
				return false;
			}
			tCorpusAddInterest = this.getCalResult();
			if (!calAutoPayInterest(tPolNo, tCountDate)) {
				this.mCountResult = -1;
				return false;
			}
			tIntrerest = this.getCalResult();
			mCountResult = tCorpusAddInterest - tIntrerest;
			mCountResult = this.getCalResult();
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calAutoPayPrem";
			tError.errorMessage = "计算自垫保费时产生错误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 保单下所有的贷款加自垫本息总和，如果产生错误则为-1
	 * 
	 * @param aPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true-成功，返回：贷款加自垫本息总和 mCountResult
	 */
	public boolean calContLoanAndAutoPay(String tContNo, String tCountDate) {
		// 保单下的所有贷款加自垫本息总和
		double tSum = 0.0;
		double tLoanMoney = 0.0;
		double tAutoPay = 0.0;
		if (tContNo != null && !tContNo.equals("")) {
			if (this.calLoanCorpusAddInterest(tContNo, tCountDate)) {
				tLoanMoney = this.getCalResult();
			}
			if (this.calContAutoPay(tContNo, tCountDate)) {
				tAutoPay = this.getCalResult();
			}
			tSum = tLoanMoney + tAutoPay;
			mCountResult = tSum;
		}
		return true;
	}

	/**
	 * 保单下所有险种的自垫本息总和，如果产生错误则为-1
	 * 
	 * @param aPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true-成功，返回：自垫本息总和 mCountResult
	 */
	public boolean calContAutoPay(String tContNo, String tCountDate) {
		// 保单下的所有险种的自垫本息总和
		double tSumAutoPay = 0.0;
		String tPolNo = "";
		// 查询用
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;
		String strsql = "";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		strsql = "SELECT DISTINCT PolNo FROM LcPol WHERE ContNo = '" + "?tContNo?"
				+ "' ";
		sqlbv.sql(strsql);
		sqlbv.put("tContNo", tContNo);
		try {
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			// CError.buildErr(this, "查询保单险种相关信息时产生错误!");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calContAutoPay";
			tError.errorMessage = "没有查到该保单下的险种信息!";
			this.mErrors.addOneError(tError);
			mCountResult = -1.0;
			return false;
		}
		if (tSSRS != null || tSSRS.MaxRow > 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				tPolNo = tSSRS.GetText(i, 1);
				if (this.calAutoPayPremAddInterest(tPolNo, tCountDate)) {
					tSumAutoPay += this.getCalResult();
				}
			}
			mCountResult = tSumAutoPay;
		}
		return true;
	}

	/**
	 * 未清偿自垫保费加利息 (本息和) 的计算，如果产生错误则为-1
	 * 
	 * @param aPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true-成功，返回：本息和 mCountResult
	 */
	public boolean calAutoPayPremAddInterest(String tPolNo, String tCountDate) {


		// 准备从借款业务表中获取计算参数
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setPolNo(tPolNo);
		tLOLoanDB.setLoanType("1"); // 垫交类型
		tLOLoanDB.setPayOffFlag("0"); // 未还清
		mLOLoanSet.clear();
		mLOLoanSet = tLOLoanDB.query();
		if (mLOLoanSet != null && mLOLoanSet.size() > 0) {
			
			// 准备计算参数
			String tEdorType = "TR"; // 批改类型为保费自垫
			String tLoanDate = ""; // 本金产生日期tCorpusDate 借款日期 YYYY-MM-DD
			
			double tSumMoney = 0.0; // 每期借款总金额，即为本金tCorpus
			double tDouble = 0.0;

			String tCurrency = "";
			for (int i = 1; i <= mLOLoanSet.size(); i++) {
				//tLoanDate = mLOLoanSet.get(i).getNewLoanDate();
				//modify by jiaqiangli 2009-04-09 垫缴取loandate 借款取newloandate
				tLoanDate = mLOLoanSet.get(i).getLoanDate();
				tSumMoney = mLOLoanSet.get(i).getLeaveMoney();
				// 计算分段利率值，但不是利率，可以说是一个参数，参数×本金=利息
				tCurrency = mLOLoanSet.get(i).getCurrency();
				double tRate = AccountManage.calMultiRateMS(tLoanDate,  tCountDate, "000000",tEdorType,"L","C","Y",tCurrency);
				if (tRate+1==0) {
					CError.buildErr(this, "本息和计算失败！");
					mCountResult = -1.0;
					return false;
				}
				//累计利息
				tDouble += tSumMoney*tRate;
				//累计本息
				tDouble+=tSumMoney;
			}
			mCountResult = tDouble;
			//mCountResult = this.getCalResultRound();
		}

		return true;
	}

	/**
	 * 未清偿自垫保费的利息的计算，如果产生错误则为-1
	 * 
	 * @param tPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true-成功，返回： 总利息 mCountResult
	 */
	public boolean calAutoPayInterest(String tPolNo, String tCountDate) {
		// 查询用
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;
		String strsql = "";

		// 准备从借款业务表中获取计算参数
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setPolNo(tPolNo);
		tLOLoanDB.setLoanType("1"); // 垫交类型
		tLOLoanDB.setPayOffFlag("0"); // 未还清
		mLOLoanSet.clear();
		mLOLoanSet = tLOLoanDB.query();
		if(mLOLoanSet != null && mLOLoanSet.size() > 0)
		{
			// 准备计算参数
			String tEdorType = "TR"; // 批改类型为保费自垫
			double tSumMoney = 0.0; // 每次自垫借款总金额，即为本金tCorpus
			String tLoanDate = ""; // 本金产生日期tCorpusDate 借款日期 YYYY-MM-DD
			String tRiskCode = mLCPolSchema.getRiskCode();

			// 计算临时结果变量
			double tDouble = 0.0;
			String tCurrency = "";
			for (int i = 1; i <= mLOLoanSet.size(); i++) {
				tLoanDate = mLOLoanSet.get(i).getLoanDate();
				tSumMoney = mLOLoanSet.get(i).getSumMoney();
				tCurrency = mLOLoanSet.get(i).getCurrency();
				// 计算每次自垫利息
				// 计算分段利率值，但不是利率，可以说是一个参数，参数×本金=利息
				double tRate = AccountManage.calMultiRateMS(tLoanDate,  tCountDate, tRiskCode,tEdorType,"L","C","Y",tCurrency);
				if (tRate+1==0) {
					CError.buildErr(this, "利息计算失败！");
					mCountResult = -1.0;
					return false;
				}
				//累计利息
				tDouble += tSumMoney*tRate;
			}
			mCountResult = tDouble;
			//mCountResult = this.getCalResultRound();
		}
		return true;
	}

	// ************************************************************************//

	private boolean prepareCalLackPay(String tPolNo) {
		// 取得保单险种信息
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select * from LCPol where PolNo = '" + "?tPolNo?" + "'"
				+ " and payintv <> 0 ";
		sqlbv.sql(i_sql);
		sqlbv.put("tPolNo", tPolNo);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolSet.size() < 1) {
			CError.buildErr(this, "查询险种信息时产生错误!");
			return false;
		}
		mLCPolSchema = new LCPolSchema();
		mLCPolSchema.setSchema(tLCPolSet.get(1).getSchema());
		if (mLCPolSchema.getPayIntv() != 1 || mLCPolSchema.getPayIntv() != 12) {
			// 目前只做 月缴 和 年缴 的欠交保费计算
			CError.buildErr(this, "该保单险种的交费间隔不能计算欠交保费,目前只针对月缴和年缴！");
			return false;
		}
		// 取得保单责任信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		mLCDutySet.clear();
		mLCDutySet = tLCDutyDB.query();
		if (mLCDutySet.size() < 1) {
			CError.buildErr(this, "没有保单责任信息!");
			return false;
		}
		// 取得应收总表,检查应收总表是否有催收信息
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mLCPolSchema.getPolNo());
		tLJSPayDB.setOtherNoType("2"); // 续期催收的个人保单号码
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() != 1) {
			CError.buildErr(this, "该保单首期催收数据错误!(LJSPay)");
			return false;
		}
		// 取得个人应收表信息
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		tLJSPayPersonDB.setPolNo(mLCPolSchema.getPolNo());
		mLJSPayPersonSet = new LJSPayPersonSet();
		mLJSPayPersonSet = tLJSPayPersonDB.query();
		if (mLJSPayPersonSet.size() < 1) {
			CError.buildErr(this, "该保单没有首期催收数据!(LJSPayPerson)");
			return false;
		}
		// 应交保费项信息（催收交费金额）mGetMoney
		if (!setReceivablePrem(mLCPolSchema.getPolNo())) {
			return false;
		}
		// 校验催收保费是否和应交保费相等
		double aGetMoney = 0;
		for (int k = 1; k <= mLJSPayPersonSet.size(); k++) {
			aGetMoney += mLJSPayPersonSet.get(k).getSumDuePayMoney();
		}
		if (mGetMoney != aGetMoney) {
			CError.buildErr(this, "催收数据与应收保费不符!");
			return false;
		}
		return true;
	}

	// 计算以时间间隔为计量单位的数量, 计算利息用
	private int calWholeUnitNum(String tCountDate) {
		int tWholeUnitNum = -1;
		String tPayFlag = "";
		// 缴费间隔为月缴
		if (mLCPolSchema.getPayIntv() == 1) {
			tPayFlag = "M";
		}
		// 缴费间隔为年缴
		else if (mLCPolSchema.getPayIntv() == 12) {
			tPayFlag = "Y";
		}

		// 当前计算日期小于交费止期（终交日期），计算从交至日期到当前计算日期，欠交的期数
		if (aFDate.getDate(tCountDate).before(
				aFDate.getDate(mLCPolSchema.getPayEndDate()))) { // 如果年缴,
																	// unit为"M"，返回整月数;
																	// 如果月缴，unit为"D"，返回整天数
			tWholeUnitNum = calUnitBetween(mLCPolSchema.getPaytoDate(),
					tCountDate, tPayFlag);
		} else { // 当前计算日期大于交费止期（终交日期），计算从交至日期到交费止期，计算欠交期数
			tWholeUnitNum = calUnitBetween(mLCPolSchema.getPaytoDate(),
					mLCPolSchema.getPayEndDate(), tPayFlag);
		}
		return tWholeUnitNum;
	}

	/**
	 * 计算以时间间隔为计量单位的数量
	 * 
	 * @param tStartDate
	 *            String 开始日期
	 * @param tEndDate
	 *            String 结束日期
	 * @param tIntvFlag
	 *            String 时间间隔
	 * @return int tWholeUnitNum ：M-月数 ，D-天数
	 */
	public static int calUnitBetween(String tStartDate, String tEndDate,
			String tIntvFlag) {
		int tWholeUnitNum = -1;
		// 根据一个日期算此日期到结束日期有多少个月，不足一个月按一个月算，注意：如果计算日是结束日期则也算一个月！
		if (tIntvFlag.equals("Y") || tIntvFlag.equals("y")) {
			tWholeUnitNum = PubFun.calInterval(tStartDate, tEndDate, "M") + 1;
		} else { // 根据一个日期算此日期到结束日期有多少天，注意：如果计算日是结束日期则也算一天！
			tWholeUnitNum = PubFun.calInterval(tStartDate, tEndDate, "D") + 1;
		}
		return tWholeUnitNum;
	}

	// 计算欠交保费的期数 以时间间隔为计量单位
	private int calPaySTime(String tCountDate) {
		int tPaySTime = -1;
		String tPayFlag = "";
		// 缴费间隔为月缴
		if (mLCPolSchema.getPayIntv() == 1) {
			tPayFlag = "M";
		}
		// 缴费间隔为年缴
		else if (mLCPolSchema.getPayIntv() == 12) {
			tPayFlag = "Y";
		}
		// 当前计算日期小于交费止期（终交日期），计算从交至日期到当前计算日期，欠交的期数
		if (aFDate.getDate(tCountDate).before(
				aFDate.getDate(mLCPolSchema.getPayEndDate()))) { // 如果年缴，返回整年数;
																	// 如果月缴，返回整月数
			tPaySTime = PubFun.calInterval(mLCPolSchema.getPaytoDate(),
					tCountDate, tPayFlag) + 1;
		} else { // 当前计算日期大于交费止期（终交日期），计算从交至日期到交费止期，计算欠交期数
			tPaySTime = PubFun.calInterval(mLCPolSchema.getPaytoDate(),
					mLCPolSchema.getPayEndDate(), tPayFlag) + 1;
		}
		return tPaySTime;
	}

	/**
	 * 计算欠交保费加利息
	 * 
	 * @param tPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true--成功 返回: 欠交总保费 mTotalPrem 总利息 mTotalInterest
	 *         本息和 mCountResult
	 */
	public boolean calLackPayPremAddInsterest(String tPolNo, String tCountDate) {
		double tPrem = 0.0; // 欠交保费
		double tIntrerest = 0.0; // 欠交保费利息
		if (!calLackPayPrem(tPolNo, tCountDate)) {
			this.mCountResult = -1;
			return false;
		}
		tPrem = this.getCalResultRound();
		if (!calLackPayInsterest(tPolNo, tCountDate)) {
			this.mCountResult = -1;
			return false;
		}
		tIntrerest = this.getCalResultRound();
		mCountResult = tPrem + tIntrerest;
		mCountResult = this.getCalResultRound();
		return true;
	}

	/**
	 * 计算欠交保费
	 * 
	 * @param tPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true--成功 返回: 欠交总保费 mCountResult
	 */
	public boolean calLackPayPrem(String tPolNo, String tCountDate) {
		if (!prepareCalLackPay(tPolNo)) {
			mCountResult = -1.0;
			return false;
		}
		// 欠交保费的期数,年缴对应整年数，月缴对应整月数
		// int tPaySTime = this.calPaySTime(tCountDate);
		// 计算宽限期止期(交至日期的)——只有第一期欠费才有宽限期
		String tLapseDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), 60,
				"D", null);
		mLapseDate = tLapseDate;

		// //////////到当前计算日期时该险种下所有的保费项////////////
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select * from LCPrem where PolNo = '"
				+ "?PolNo?" + "' and "
				+ "PayStartDate <= to_date('" + "?Date?"
				+ "','YYYY-MM-DD') and PayEndDate >= to_date('"
				+ "?Date?"
				+ "','YYYY-MM-DD') order by PaytoDate ";
		sqlbv.sql(i_sql);
		sqlbv.put("PolNo", mLCPolSchema.getPolNo());
		sqlbv.put("Date", mLCPolSchema.getPaytoDate());
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		if (tLCPremSet.size() < 1) {
			mErrors.addOneError("保费项表中没有对应保费信息!");
			mCountResult = -1.0;
			return false;
		}
		// 责任下的保费
		mLCPremSet = new LCPremSet();
		for (int i = 1; i <= tLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(tLCPremSchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCPremSchema.getDutyCode());
			if (!tLCDutyDB.getInfo()) {
				CError.buildErr(this, "查询责任表失败!");
				mCountResult = -1.0;
				return false;
			}
			if ((tLCDutyDB.getFreeFlag() != null)
					&& !tLCDutyDB.getFreeFlag().equals("1")
					&& (tLCDutyDB.getFreeRate() < 1)
					&& (tLCDutyDB.getFreeRate() > 0)) {
				if (aFDate.getDate(mLCPolSchema.getPaytoDate()).before(
						aFDate.getDate(tLCDutyDB.getFreeEndDate()))) {
					tLCPremSchema.setPrem(tLCPremSchema.getPrem()
							* (1 - tLCDutyDB.getFreeRate()));
				}
			}
			if (tLCPremSchema.getPrem() > 0) {
				// mGetMoney += tLCPremSchema.getPrem();
				mLCPremSet.add(tLCPremSchema);
			}
		}
		if (mLCPremSet.size() == 0) { // 如果过滤后的保费项表纪录数=0
			CError.buildErr(this, "查询保险责任表失败，原因是:都为免交!");
			mCountResult = -1.0;
			return false;
		}
		// 临时变量存储计算欠交保费
		double tTotalPrem = 0.0;
		// 当前计算日期在缴费止期之前
		if (aFDate.getDate(tCountDate).before(
				aFDate.getDate(mLCPolSchema.getPayEndDate()))) {
			for (int i = 1; i <= mLCPremSet.size(); i++) { // 累计在当前计算日期前欠交的保费项
				if (aFDate.getDate(mLCPremSet.get(i).getPaytoDate()).before(
						aFDate.getDate(tCountDate))) {
					tTotalPrem += mLCPremSet.get(i).getPrem();
				} else {
					continue;
				}
			}
		} else
			for (int i = 1; i <= mLCPremSet.size(); i++) {
				tTotalPrem += mLCPremSet.get(i).getPrem();
			}
		mTotalPrem = tTotalPrem;
		mCountResult = tTotalPrem;
		mCountResult = this.getCalResultRound();
		return true;
	}

	/**
	 * 计算欠交保费加利息
	 * 
	 * @param tPolNo
	 *            String 保单险种号
	 * @param tCountDate
	 *            String 计算日期 YYYY-MM-DD
	 * @return boolean false-失败 true--成功 返回: 欠交总保费 mTotalPrem 总利息 mTotalInterest
	 *         本息和 mCountResult
	 */
	public boolean calLackPayInsterest(String tPolNo, String tCountDate) {
		if (!prepareCalLackPay(tPolNo)) {
			mCountResult = -1.0;
			return false;
		}
		// 计算宽限期止期(交至日期的)——只有第一期欠费才有宽限期
		String tLapseDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), 60,
				"D", null);
		mLapseDate = tLapseDate;
		// ////////到当前计算日期时所有欠交保费的利息////////////
		// 计算利率时用到
		mAccountManage.setPayEndYear(this.queryPayEndYear("", tPolNo));

		// 临时变量存储欠交保费的利息
		double tTotaInterest = 0.0;
		// 宽限期小于当前计算日期,且当前计算日期小于缴费止期
		if (aFDate.getDate(tLapseDate).before(aFDate.getDate(tCountDate))
				&& aFDate.getDate(tLapseDate).before(
						aFDate.getDate(mLCPolSchema.getPayEndDate()))) { // 计算欠交的利息
			tTotaInterest = calLJSGetInterest(mLCPolSchema.getPayIntv(),
					tCountDate);
		}
		// 当前计算日期在宽限期内,且当前计算日期小于缴费止期
		else if (aFDate.getDate(tCountDate).before(aFDate.getDate(tLapseDate))
				&& aFDate.getDate(tLapseDate).before(
						aFDate.getDate(mLCPolSchema.getPayEndDate()))) {
			// 计算欠交的利息
			tTotaInterest = calInLGetInterest(mLCPolSchema.getPayIntv(),
					tCountDate);
		} else {
			CError.buildErr(this, "该保单可能未超过宽限期止期或达到终交日期");
			mCountResult = -1.0;
			return false;
		}
		mCountResult = tTotaInterest;
		mCountResult = this.getCalResultRound();
		return true;
	}

	/**
	 * 得到首期催收的应收保费项
	 * 
	 * @param aPolNo
	 * @return boolean
	 */
	private boolean setReceivablePrem(String aPolNo) {
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select * from LCPrem where PolNo = '" + "?aPolNo?"
				+ "' and " + "PayStartDate <= to_date('"
				+ "?Date?"
				+ "','YYYY-MM-DD') and PayEndDate >= to_date('"
				+ "?Date?"
				+ "','YYYY-MM-DD') and UrgePayFlag = 'Y'";
		sqlbv.sql(i_sql);
		sqlbv.put("aPolNo", aPolNo);
		sqlbv.put("Date", mLCPolSchema.getPaytoDate());
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		if (tLCPremSet.size() < 1) {
			mErrors.addOneError("保费项表中没有对应催交信息!");
			return false;
		}
		this.mGetMoney = 0.0;
		this.mLCPremSet.clear();
		// 责任下的保费
		for (int i = 1; i <= tLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(tLCPremSchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCPremSchema.getDutyCode());
			if (!tLCDutyDB.getInfo()) {
				CError.buildErr(this, "查询责任表失败!");
				return false;
			}
			// aLCPremSet.add(tLCPremSchema);
			if ((tLCDutyDB.getFreeFlag() != null)
					&& !tLCDutyDB.getFreeFlag().equals("1")
					&& (tLCDutyDB.getFreeRate() < 1)
					&& (tLCDutyDB.getFreeRate() > 0)) {
				if (aFDate.getDate(mLCPolSchema.getPaytoDate()).before(
						aFDate.getDate(tLCDutyDB.getFreeEndDate()))) {
					tLCPremSchema.setPrem(tLCPremSchema.getPrem()
							* (1 - tLCDutyDB.getFreeRate()));
				}
			}
			if (tLCPremSchema.getPrem() > 0) {
				mGetMoney += tLCPremSchema.getPrem();
				mLCPremSet.add(tLCPremSchema);
			}
		}
		if (mLCPremSet.size() == 0) { // 如果过滤后的保费项表纪录数=0
			this.mErrors.addOneError("查询保险责任表失败，原因是:都为免交");
			return false;
		}
		return true;
	}

	/**
	 * （宽限期小于当前计算日期）欠交保费利息计算 只针对年交和月交
	 * 
	 * @param tPayIntv
	 *            int 计算利息的类型
	 * @param tCountDate
	 *            String 计算日期
	 * @return double 利息
	 */
	private double calLJSGetInterest(int tPayIntv, String tCountDate) {
		// 计算利息 = 首期利息+续期利息
		double fInterest = 0.0;
		double oInterest = 0.0;
		String tIntvFlag = "";
		// 获得某一天所在利率期的“年利率”，如果产生错误则为-1
		if (mAccountManage.calYearRateByDate("RE", tCountDate, mLCPolSchema
				.getRiskCode()))
			mRate = mCountResult;
		// 宽限期
		// String tLapseDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), 60,
		// "D", null);
		// 首期记利开始日期,宽限期的次日
		String tDate = PubFun.calDate(mLapseDate, 1, "D", null);
		// 欠交保费的期数
		int tPaySTime = this.calPaySTime(tCountDate);
		if (tPayIntv == 1) {
			tIntvFlag = "D";
			// 首期利息
			fInterest = AccountManage.getMultiAccInterest(mRate,
					mLJSPayPersonSet.get(1).getSumDuePayMoney(), tDate,
					tCountDate, "C", tIntvFlag);
			// 续期利息
			for (int i = 2; i <= tPaySTime; i++) {
				oInterest += AccountManage.getMultiAccInterest(mRate,
						(mLCPremSet.get(i).getPrem()), PubFun.calDate(
								mLCPolSchema.getPaytoDate(), i, "M", null),
						tCountDate, "C", tIntvFlag);
			}
			mCountResult = fInterest + oInterest;
		}
		if (tPayIntv == 12) {
			tIntvFlag = "M";
			// 首期利息
			fInterest = AccountManage.getMultiAccInterest(mRate,
					mLJSPayPersonSet.get(1).getSumDuePayMoney(), tDate,
					tCountDate, "C", tIntvFlag);
			// 续期利息
			for (int i = 2; i <= tPaySTime; i++) {
				oInterest += AccountManage.getMultiAccInterest(mRate,
						(mLCPremSet.get(i).getPrem()), PubFun.calDate(
								mLCPolSchema.getPaytoDate(), i, "Y", null),
						tCountDate, "C", tIntvFlag);
			}
		} else {
			CError.buildErr(this, "交费间隔错误!");
			mCountResult = -1.0;
			return -1;
		}
		mCountResult = fInterest + oInterest;
		return mCountResult;
	}

	/**
	 * （当前计算日期小于宽限期）欠交保费利息计算 当前计算日期在宽限期内，只针对年交和月交
	 * 
	 * @param tPayIntv
	 *            int
	 * @param tCountDate
	 *            String
	 * @return double
	 */
	private double calInLGetInterest(int tPayIntv, String tCountDate) {
		double oInterest = 0.0;
		String tIntvFlag = "";
		// 获得某一天所在利率期的“年利率”，如果产生错误则为-1
		if (mAccountManage.calYearRateByDate("RE", tCountDate, mLCPolSchema
				.getRiskCode()))
			mRate = mCountResult;
		// 宽限期
		// String tLapseDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), 60,
		// "D", null);
		// 对于年缴，按月计息，记利开始日期为宽限期的次日
		String tDate = "";
		// 欠交保费的期数（只对月缴有意义）
		// int tPaySTime = this.calPaySTime(tCountDate);

		// 对于月缴，首期宽限期内无利息，可能到达续期缴费对应日，此时tPaySTime为1或2
		if (tPayIntv == 1) {
			tIntvFlag = "D";
			tDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), tPayIntv, "M",
					null);
			if (aFDate.getDate(tDate).before(aFDate.getDate(tCountDate))) {
				oInterest = AccountManage.getMultiAccInterest(mRate,
						(mTotalPrem - mLJSPayPersonSet.get(1)
								.getSumDuePayMoney()), tDate, tCountDate, "C",
						tIntvFlag);
			} else
				oInterest = 0.0;
		}
		// 对于年缴，由于当前计算日期还在宽限期（60天）内不存在利息，也没有到达续期缴费对应日
		else if (tPayIntv == 12)
			oInterest = 0.0;
		else {
			CError.buildErr(this, "交费间隔错误!");
			mCountResult = -1.0;
			return -1;
		}
		mCountResult = oInterest;
		return mCountResult;
	}

	// /********责任保费相关计算********Add by Nicholas********2005-07-20********\
	// 说明：可以计算某个责任当前的保费，分基本保费和包括加费的总保费的计算
	// 结果通过getCalResult()返回，true--成功，false--失败
	// --基本保费（不包括加费）计算-->
	public boolean calBasicPremPerDuty(String tPolNo, String tDutyCode) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode and PayPlanType='0')"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and substr(b.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode and PayPlanType='0')"
					+ ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tDutyCode", tDutyCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calBasicPremPerDuty";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --保费（包括加费）计算-->
	public boolean calSumPremPerDuty(String tPolNo, String tDutyCode) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?" + "'" + " and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?" + "',1,6)" + " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b" + " where b.PolNo='" + "?tPolNo?"
					+ "'" + " and substr(b.DutyCode,1,6)=substr('" + "?tDutyCode?"
					+ "',1,6)" + " and FeeFinaType in ('BF','TF')" + ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tDutyCode", tDutyCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calSumPremPerDuty";
			tError.errorMessage = "计算保费（包括加费）时产生错误！";
			logger.debug("计算保费（包括加费）时产生错误！PolNo:" + tPolNo + " DutyCode:"
					+ tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 说明：可以计算某个责任当前的保费，分基本保费和包括加费的总保费的计算 2005-09-14
	// 这两个方法包括一个截止日期，防止不同时间调用此方法返回的值不同。
	// 结果通过getCalResult()返回，true--成功，false--失败
	// --基本保费（不包括加费）计算-->
	public boolean calNewBasicPremPerDuty(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode and PayPlanType='0')"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end ) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and substr(b.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode and PayPlanType='0')"
					+ ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("tDutyCode", tDutyCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewBasicPremPerDuty";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --保费（包括加费）计算-->
	public boolean calNewSumPremPerDuty(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and substr(b.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)" + " and FeeFinaType in ('BF','TF')" + ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("tDutyCode", tDutyCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewSumPremPerDuty";
			tError.errorMessage = "计算保费（包括加费）时产生错误！";
			logger.debug("计算保费（包括加费）时产生错误！PolNo:" + tPolNo + " DutyCode:"
					+ tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 下面两个函数是获得险种的相关保费，利用上面的两个函数循环得到
	// --基本保费（不包括加费）计算-->
	public boolean calNewBasicPremPerPol(String tPolNo, String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT distinct substr(DutyCode,1,6)"
					+ " FROM LCDuty" + " WHERE PolNo='" + "?tPolNo?" + "'";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "BqPolBalBL";
				tError.functionName = "calNewBasicPremPerPol";
				tError.errorMessage = "计算险种保费时查询责任信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			double tR = 0.0;
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (!this.calNewBasicPremPerDuty(tPolNo, tSSRS.GetText(i, 1),
						tLineDate)) {
					return false;
				}
				tR += mCountResult;
			}
			mCountResult = tR;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewBasicPremPerPol";
			tError.errorMessage = "计算保费（不包括加费）时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --基本保费（不包括加费）计算-->
	public boolean calNewSumPremPerPol(String tPolNo, String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT distinct substr(DutyCode,1,6)"
					+ " FROM LCDuty" + " WHERE PolNo='" + "?tPolNo?" + "'";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "BqPolBalBL";
				tError.functionName = "calNewSumPremPerPol";
				tError.errorMessage = "计算险种保费时查询责任信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			double tR = 0.0;
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (!this.calNewSumPremPerDuty(tPolNo, tSSRS.GetText(i, 1),
						tLineDate)) {
					return false;
				}
				tR += mCountResult;
			}
			mCountResult = tR;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewSumPremPerPol";
			tError.errorMessage = "计算保费（包括加费）时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 说明：可以计算某个责任当前的保费，分基本保费和包括加费的总保费的计算 2005-09-14
	// 下面两个方法转为减保写。最后一次减保前的取Prem*期数（CValiDate-tLastEdorValiDate包括tLastEdorValiDate当天），后的取实交。
	// 结果通过getCalResult()返回，true--成功，false--失败
	// --基本保费（不包括加费）计算-->
	public boolean calJBBasicPremPerDuty(String tPolNo, String tDutyCode,
			String tLineDate, SSRS vSSRS) {
		// 循环处理责任下的每一个缴费责任（趸缴就不乘了，就交一次）
		try {
			mCountResult = 0.0;
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
					+ " a.PayIntv,"
					+ " a.Prem"
					+ " FROM LPPrem a"
					+ " WHERE exists("
					+ " select 'x' from"
					+ " (select EdorNo,EdorType"
					+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
					+ "?EdorAcceptNo?"
					+ "' and EdorNo='"
					+ "?EdorNo?"
					+ "' and EdorType='"
					+ "?EdorType?"
					+ "' and ContNo='"
					+ "?ContNo?"
					+ "' and InsuredNo='"
					+ "?InsuredNo?"
					+ "' and PolNo='"
					+ "?PolNo?"
					+ "')"
					+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
					+ "?tPolNo?"
					+ "')"
					+ " and EdorState='0' and rownum=1"
					+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime) "
					+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
					+ " and a.PolNo='"
					+ "?tPolNo?"
					+ "' and a.PayPlanType='0' and a.PayStartDate<=to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD') and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?" + "',1,6)";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
						+ " a.PayIntv,"
						+ " a.Prem"
						+ " FROM LPPrem a"
						+ " WHERE exists("
						+ " select 'x' from"
						+ " (select EdorNo,EdorType"
						+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
						+ "?EdorAcceptNo?"
						+ "' and EdorNo='"
						+ "?EdorNo?"
						+ "' and EdorType='"
						+ "?EdorType?"
						+ "' and ContNo='"
						+ "?ContNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "' and PolNo='"
						+ "?PolNo?"
						+ "')"
						+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorState='0'"
						+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime limit 0,1) g"
						+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
						+ " and a.PolNo='"
						+ "?tPolNo?"
						+ "' and a.PayPlanType='0' and a.PayStartDate<=to_date('"
						+ "?date?"
						+ "','YYYY-MM-DD') and substr(a.DutyCode,1,6)=substr('"
						+ "?tDutyCode?" + "',1,6)";
			}
			sqlbv.sql(tSql);
			sqlbv.put("EdorAcceptNo", vSSRS.GetText(1, 2));
			sqlbv.put("EdorNo", vSSRS.GetText(1, 3));
			sqlbv.put("EdorType", vSSRS.GetText(1, 4));
			sqlbv.put("ContNo", vSSRS.GetText(1, 5));
			sqlbv.put("InsuredNo", vSSRS.GetText(1, 6));
			sqlbv.put("PolNo", vSSRS.GetText(1, 7));
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 执行到这里说明P表里还没有新记录近来，再从C表里去查。
				tSql = "SELECT to_char(a.CValiDate,'YYYY-MM-DD'),"
						+ " b.PayIntv," + " b.Prem" + " FROM LCPol a,LCPrem b"
						+ " WHERE a.PolNo=b.PolNo" + " and b.PayPlanType='0'"
						+ " and b.PayStartDate<=to_date('"
						+ "?date?" + "','YYYY-MM-DD')"
						+ " and a.PolNo='" + "?tPolNo?" + "'"
						+ " and substr(b.DutyCode,1,6)=substr('" + "?tDutyCode?"
						+ "',1,6)";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("date", vSSRS.GetText(1, 1));
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tDutyCode", tDutyCode);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS == null || tSSRS.MaxRow <= 0) {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "calJBBasicPremPerDuty";
					tError.errorMessage = "查询保费责任信息时产生错误！PolNo:" + tPolNo
							+ "，DutyCode:" + tDutyCode;
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if ("0".equals(StrTool.cTrim(tSSRS.GetText(i, 2)))) {
					// 趸交
					mCountResult += Double.parseDouble(tSSRS.GetText(i, 3));
					continue;
				}
				int tMonthNum = PubFun.calInterval(tSSRS.GetText(i, 1), vSSRS
						.GetText(1, 1), "M");
				int tNum = (int) (tMonthNum / Integer.parseInt(tSSRS.GetText(i,
						2))) + 1;
				mCountResult += (Double.parseDouble(tSSRS.GetText(i, 3)) * tNum);
			}
			// 计算tLastEdorValiDate后的实交
			tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and LastPayToDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode and PayPlanType='0')"
					+ " union"
					+ " select (case when sum(GetMoney) is  not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and GetDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and substr(b.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode and PayPlanType='0')"
					+ ") g";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				mCountResult += Double.parseDouble(tSSRS.GetText(1, 1));
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBBasicPremPerDuty";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --保费（包括加费）计算-->
	public boolean calJBSumPremPerDuty(String tPolNo, String tDutyCode,
			String tLineDate, SSRS vSSRS) {
		// 循环处理责任下的每一个缴费责任（趸缴就不乘了，就交一次）
		try {
			mCountResult = 0.0;
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			String tSql="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
					+ " a.PayIntv,"
					+ " a.Prem"
					+ " FROM LPPrem a"
					+ " WHERE exists("
					+ " select 'x' from"
					+ " (select EdorNo,EdorType"
					+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
					+ "?EdorAcceptNo?"
					+ "' and EdorNo='"
					+ "?EdorNo?"
					+ "' and EdorType='"
					+ "?EdorType?"
					+ "' and ContNo='"
					+ "?ContNo?"
					+ "' and InsuredNo='"
					+ "?InsuredNo?"
					+ "' and PolNo='"
					+ "?PolNo?"
					+ "')"
					+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
					+ "?tPolNo?"
					+ "')"
					+ " and EdorState='0' and rownum=1"
					+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime)"
					+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
					+ " and a.PolNo='"
					+ "?tPolNo?"
					+ "' and a.PayStartDate<=to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD') and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?" + "',1,6)";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			    tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
						+ " a.PayIntv,"
						+ " a.Prem"
						+ " FROM LPPrem a"
						+ " WHERE exists("
						+ " select 'x' from"
						+ " (select EdorNo,EdorType"
						+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
						+ "?EdorAcceptNo?"
						+ "' and EdorNo='"
						+ "?EdorNo?"
						+ "' and EdorType='"
						+ "?EdorType?"
						+ "' and ContNo='"
						+ "?ContNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "' and PolNo='"
						+ "?PolNo?"
						+ "')"
						+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorState='0'"
						+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime limit 0,1) g"
						+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
						+ " and a.PolNo='"
						+ "?tPolNo?"
						+ "' and a.PayStartDate<=to_date('"
						+ "?date?"
						+ "','YYYY-MM-DD') and substr(a.DutyCode,1,6)=substr('"
						+ "?tDutyCode?" + "',1,6)";
			}
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("EdorAcceptNo", vSSRS.GetText(1, 2));
			sqlbv.put("EdorNo", vSSRS.GetText(1, 3));
			sqlbv.put("EdorType", vSSRS.GetText(1, 4));
			sqlbv.put("ContNo", vSSRS.GetText(1, 5));
			sqlbv.put("InsuredNo", vSSRS.GetText(1, 6));
			sqlbv.put("PolNo", vSSRS.GetText(1, 7));
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 执行到这里说明P表里还没有新记录近来，再从C表里去查。
				tSql = "SELECT to_char(a.CValiDate,'YYYY-MM-DD'),"
						+ " b.PayIntv," + " b.Prem" + " FROM LCPol a,LCPrem b"
						+ " WHERE a.PolNo=b.PolNo"
						+ " and b.PayStartDate<=to_date('"
						+ "?date?" + "','YYYY-MM-DD')"
						+ " and a.PolNo='" + "?tPolNo?" + "'"
						+ " and substr(b.DutyCode,1,6)=substr('" + "?tDutyCode?"
						+ "',1,6)";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("date", vSSRS.GetText(1, 1));
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tDutyCode", tDutyCode);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS == null || tSSRS.MaxRow <= 0) {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "calJBSumPremPerDuty";
					tError.errorMessage = "查询保费责任信息时产生错误！PolNo:" + tPolNo
							+ "，DutyCode:" + tDutyCode;
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if ("0".equals(StrTool.cTrim(tSSRS.GetText(i, 2)))) {
					// 趸交
					mCountResult += Double.parseDouble(tSSRS.GetText(i, 3));
					continue;
				}
				int tMonthNum = PubFun.calInterval(tSSRS.GetText(i, 1), vSSRS
						.GetText(1, 1), "M");
				int tNum = (int) (tMonthNum / Integer.parseInt(tSSRS.GetText(i,
						2))) + 1;
				mCountResult += (Double.parseDouble(tSSRS.GetText(i, 3)) * tNum);
			}
			// 计算tLastEdorValiDate后的实交
			tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and LastPayToDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and substr(a.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and GetDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and substr(b.DutyCode,1,6)=substr('"
					+ "?tDutyCode?"
					+ "',1,6)" + " and FeeFinaType in ('BF','TF')" + ") g";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				mCountResult += Double.parseDouble(tSSRS.GetText(1, 1));
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBSumPremPerDuty";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 下面两个函数是获得险种的相关保费，利用上面的两个函数循环得到
	// --基本保费（不包括加费）计算-->
	public boolean calJBBasicPremPerPol(String tPolNo, String tLineDate,
			SSRS vSSRS) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT distinct substr(DutyCode,1,6)"
					+ " FROM LCDuty" + " WHERE PolNo='" + "?tPolNo?" + "'";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "BqPolBalBL";
				tError.functionName = "calJBBasicPremPerPol";
				tError.errorMessage = "计算险种保费时查询责任信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			double tR = 0.0;
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (!this.calJBBasicPremPerDuty(tPolNo, tSSRS.GetText(i, 1),
						tLineDate, vSSRS)) {
					return false;
				}
				tR += mCountResult;
			}
			mCountResult = tR;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBBasicPremPerPol";
			tError.errorMessage = "计算保费（不包括加费）时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --基本保费（包括加费）计算-->
	public boolean calJBSumPremPerPol(String tPolNo, String tLineDate,
			SSRS vSSRS) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT distinct substr(DutyCode,1,6)"
					+ " FROM LCDuty" + " WHERE PolNo='" + "?tPolNo?" + "'";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError tError = new CError();
				tError.moduleName = "BqPolBalBL";
				tError.functionName = "calJBSumPremPerPol";
				tError.errorMessage = "计算险种保费时查询责任信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			double tR = 0.0;
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (!this.calJBSumPremPerDuty(tPolNo, tSSRS.GetText(i, 1),
						tLineDate, vSSRS)) {
					return false;
				}
				tR += mCountResult;
			}
			mCountResult = tR;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBSumPremPerPol";
			tError.errorMessage = "计算保费（包括加费）时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// \**********************************************************************/

	// /**************为外部提供公共接口，共四个，两个责任（前6位）的，两个险种的*******\
	// 计算保费相关
	// 1.查是否做过PU，如果做过，直接取LCPrem里的Prem。
	// 2.查是否做过减保，如果做过，调用减保专用计算保费函数。
	// 3.如果以上两个项目都没做过，则调用公共保费计算函数。
	// 结果通过getCalResult()返回，true--成功，false--失败

	// --外部--责任--基本保费（不包括加费）计算-->
	public boolean getBasicPremPerDuty(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE PayPlanType='0'"
						+ " and substr(DutyCode,1,6)=substr('" + "?tDutyCode?"
						+ "',1,6)" + " and PolNo='" + "?tPolNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tDutyCode", tDutyCode);
				sqlbv.put("tPolNo", tPolNo);
				String tPUBasicPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUBasicPrem != null && !"".equals(tPUBasicPrem)) {
					mCountResult = Double.parseDouble(tPUBasicPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getBasicPremPerDuty";
					tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
					logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
							+ " DutyCode:" + tDutyCode);
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType='PT' and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保
					if (!calJBBasicPremPerDuty(tPolNo, tDutyCode, tLineDate,
							tSSRS)) {
						return false;
					}
				} else {
					if (!calNewBasicPremPerDuty(tPolNo, tDutyCode, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getBasicPremPerDuty";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	// --外部--责任--合计保费（包括加费）计算-->
	public boolean getSumPremPerDuty(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE substr(DutyCode,1,6)=substr('" + "?tDutyCode?"
						+ "',1,6)" + " and PolNo='" + "?tPolNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tDutyCode", tDutyCode);
				sqlbv.put("tPolNo", tPolNo);
				String tPUSumPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUSumPrem != null && !"".equals(tPUSumPrem)) {
					mCountResult = Double.parseDouble(tPUSumPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getSumPremPerDuty";
					tError.errorMessage = "计算合计保费（包括加费）时产生错误！";
					logger.debug("计算合计保费（包括加费）时产生错误！PolNo:" + tPolNo
							+ " DutyCode:" + tDutyCode);
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType='PT' and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保
					if (!calJBSumPremPerDuty(tPolNo, tDutyCode, tLineDate,
							tSSRS)) {
						return false;
					}
				} else {
					if (!calNewSumPremPerDuty(tPolNo, tDutyCode, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getSumPremPerDuty";
			tError.errorMessage = "计算合计保费（包括加费）时产生错误！";
			logger.debug("计算合计保费（包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	// --外部--险种--基本保费（不包括加费）计算-->
	public boolean getBasicPremPerPol(String tPolNo, String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE PayPlanType='0'" + " and PolNo='" + "?tPolNo?"
						+ "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				String tPUBasicPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUBasicPrem != null && !"".equals(tPUBasicPrem)) {
					mCountResult = Double.parseDouble(tPUBasicPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getBasicPremPerPol";
					tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
					logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo);
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType='PT' and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保
					if (!calJBBasicPremPerPol(tPolNo, tLineDate, tSSRS)) {
						return false;
					}
				} else {
					if (!calNewBasicPremPerPol(tPolNo, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getBasicPremPerPol";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo);
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	// --外部--险种--合计保费（包括加费）计算-->
	public boolean getSumPremPerPol(String tPolNo, String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE PolNo='" + "?tPolNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				String tPUSumPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUSumPrem != null && !"".equals(tPUSumPrem)) {
					mCountResult = Double.parseDouble(tPUSumPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getSumPremPerPol";
					tError.errorMessage = "计算合计保费（包括加费）时产生错误！";
					logger.debug("计算合计保费（包括加费）时产生错误！PolNo:" + tPolNo);
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType='PT' and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保
					if (!calJBSumPremPerPol(tPolNo, tLineDate, tSSRS)) {
						return false;
					}
				} else {
					if (!calNewSumPremPerPol(tPolNo, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getSumPremPerPol";
			tError.errorMessage = "计算合计保费（包括加费）时产生错误！";
			logger.debug("计算合计保费（包括加费）时产生错误！PolNo:" + tPolNo);
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	// \************************************************************************/

	// /@@@@@@@@@@@@@@@@@下面这种算法不区分DutyCode的位数@@@@@@@@@@@@@@@@@@@@@@@@@@@@\
	// ----所有函数名后面有个“2”
	// 说明：可以计算某个责任当前的保费，分基本保费和包括加费的总保费的计算 2005-09-14
	// 这两个方法包括一个截止日期，防止不同时间调用此方法返回的值不同。
	// 结果通过getCalResult()返回，true--成功，false--失败
	// --基本保费（不包括加费）计算-->
	public boolean calNewBasicPremPerDuty2(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and a.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode and PayPlanType='0')"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and b.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode and PayPlanType='0')"
					+ ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("tDutyCode", tDutyCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewBasicPremPerDuty2";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --保费（包括加费）计算-->
	public boolean calNewSumPremPerDuty2(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and a.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and b.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " and FeeFinaType in ('BF','TF')" + ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("tDutyCode", tDutyCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewSumPremPerDuty2";
			tError.errorMessage = "计算保费（包括加费）时产生错误！";
			logger.debug("计算保费（包括加费）时产生错误！PolNo:" + tPolNo + " DutyCode:"
					+ tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 说明：可以计算某个责任当前的保费，分基本保费和包括加费的总保费的计算 2005-09-14
	// 下面两个方法转为减保写。最后一次减保前的取Prem*期数（CValiDate-tLastEdorValiDate包括tLastEdorValiDate当天），后的取实交。
	// 结果通过getCalResult()返回，true--成功，false--失败
	// --基本保费（不包括加费）计算-->
	public boolean calJBBasicPremPerDuty2(String tPolNo, String tDutyCode,
			String tLineDate, SSRS vSSRS) {
		// 循环处理责任下的每一个缴费责任（趸缴就不乘了，就交一次）
		try {
			mCountResult = 0.0;
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
					+ " a.PayIntv,"
					+ " a.Prem"
					+ " FROM LPPrem a"
					+ " WHERE exists("
					+ " select 'x' from"
					+ " (select EdorNo,EdorType"
					+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
					+ "?EdorAcceptNo?"
					+ "' and EdorNo='"
					+ "?EdorNo?"
					+ "' and EdorType='"
					+ "?EdorType?"
					+ "' and ContNo='"
					+ "?ContNo?"
					+ "' and InsuredNo='"
					+ "?InsuredNo?"
					+ "' and PolNo='"
					+ "?PolNo?"
					+ "')"
					+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
					+ "?tPolNo?"
					+ "')"
					+ " and EdorState='0' and rownum=1"
					+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime)"
					+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
					+ " and a.PolNo='"
					+ "?tPolNo?"
					+ "' and a.PayPlanType='0' and a.PayStartDate<=to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD') and a.DutyCode='"
					+ "?tDutyCode?" + "'";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
						+ " a.PayIntv,"
						+ " a.Prem"
						+ " FROM LPPrem a"
						+ " WHERE exists("
						+ " select 'x' from"
						+ " (select EdorNo,EdorType"
						+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
						+ "?EdorAcceptNo?"
						+ "' and EdorNo='"
						+ "?EdorNo?"
						+ "' and EdorType='"
						+ "?EdorType?"
						+ "' and ContNo='"
						+ "?ContNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "' and PolNo='"
						+ "?PolNo?"
						+ "')"
						+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorState='0'"
						+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime limit 0,1) g"
						+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
						+ " and a.PolNo='"
						+ "?tPolNo?"
						+ "' and a.PayPlanType='0' and a.PayStartDate<=to_date('"
						+ "?date?"
						+ "','YYYY-MM-DD') and a.DutyCode='"
						+ "?tDutyCode?" + "'";	
			}
			sqlbv.sql(tSql);
			sqlbv.put("EdorAcceptNo", vSSRS.GetText(1, 2));
			sqlbv.put("EdorNo", vSSRS.GetText(1, 3));
			sqlbv.put("EdorType", vSSRS.GetText(1, 4));
			sqlbv.put("ContNo", vSSRS.GetText(1, 5));
			sqlbv.put("InsuredNo", vSSRS.GetText(1, 6));
			sqlbv.put("PolNo", vSSRS.GetText(1, 7));
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 执行到这里说明P表里还没有新记录近来，再从C表里去查。
				tSql = "SELECT to_char(a.CValiDate,'YYYY-MM-DD'),"
						+ " b.PayIntv," + " b.Prem" + " FROM LCPol a,LCPrem b"
						+ " WHERE a.PolNo=b.PolNo" + " and b.PayPlanType='0'"
						+ " and b.PayStartDate<=to_date('"
						+ "?date?" + "','YYYY-MM-DD')"
						+ " and a.PolNo='" + "?tPolNo?" + "'" + " and b.DutyCode='"
						+ "?tDutyCode?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("date", vSSRS.GetText(1, 1));
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tDutyCode", tDutyCode);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS == null || tSSRS.MaxRow <= 0) {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "calJBBasicPremPerDuty2";
					tError.errorMessage = "查询保费责任信息时产生错误！PolNo:" + tPolNo
							+ "，DutyCode:" + tDutyCode;
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if ("0".equals(StrTool.cTrim(tSSRS.GetText(i, 2)))) {
					// 趸交
					mCountResult += Double.parseDouble(tSSRS.GetText(i, 3));
					continue;
				}
				int tMonthNum = PubFun.calInterval(tSSRS.GetText(i, 1), vSSRS
						.GetText(1, 1), "M");
				int tNum = (int) (tMonthNum / Integer.parseInt(tSSRS.GetText(i,
						2))) + 1;
				mCountResult += (Double.parseDouble(tSSRS.GetText(i, 3)) * tNum);
			}
			// 计算tLastEdorValiDate后的实交
			tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and LastPayToDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and a.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode and PayPlanType='0')"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and GetDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and b.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode and PayPlanType='0')"
					+ ") g";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				mCountResult += Double.parseDouble(tSSRS.GetText(1, 1));
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBBasicPremPerDuty2";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// --保费（包括加费）计算-->
	public boolean calJBSumPremPerDuty2(String tPolNo, String tDutyCode,
			String tLineDate, SSRS vSSRS) {
		// 循环处理责任下的每一个缴费责任（趸缴就不乘了，就交一次）
		try {
			mCountResult = 0.0;
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
					+ " a.PayIntv,"
					+ " a.Prem"
					+ " FROM LPPrem a"
					+ " WHERE exists("
					+ " select 'x' from"
					+ " (select EdorNo,EdorType"
					+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
					+ "?EdorAcceptNo?"
					+ "' and EdorNo='"
					+ "?EdorNo?"
					+ "' and EdorType='"
					+ "?EdorType?"
					+ "' and ContNo='"
					+ "?ContNo?"
					+ "' and InsuredNo='"
					+ "?InsuredNo?"
					+ "' and PolNo='"
					+ "?PolNo?"
					+ "')"
					+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
					+ "?tPolNo?"
					+ "')"
					+ " and EdorState='0' and rownum=1"
					+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime)"
					+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
					+ " and a.PolNo='"
					+ "?tPolNo?"
					+ "' and a.PayStartDate<=to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD') and a.DutyCode='" + "?tDutyCode?" + "'";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
						+ " a.PayIntv,"
						+ " a.Prem"
						+ " FROM LPPrem a"
						+ " WHERE exists("
						+ " select 'x' from"
						+ " (select EdorNo,EdorType"
						+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
						+ "?EdorAcceptNo?"
						+ "' and EdorNo='"
						+ "?EdorNo?"
						+ "' and EdorType='"
						+ "?EdorType?"
						+ "' and ContNo='"
						+ "?ContNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "' and PolNo='"
						+ "?PolNo?"
						+ "')"
						+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorState='0'"
						+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime limit 0,1) g"
						+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
						+ " and a.PolNo='"
						+ "?tPolNo?"
						+ "' and a.PayStartDate<=to_date('"
						+ "?date?"
						+ "','YYYY-MM-DD') and a.DutyCode='" + "?tDutyCode?" + "'";
			}
			sqlbv.sql(tSql);
			sqlbv.put("EdorAcceptNo", vSSRS.GetText(1, 2));
			sqlbv.put("EdorNo", vSSRS.GetText(1, 3));
			sqlbv.put("EdorType", vSSRS.GetText(1, 4));
			sqlbv.put("ContNo", vSSRS.GetText(1, 5));
			sqlbv.put("InsuredNo", vSSRS.GetText(1, 6));
			sqlbv.put("PolNo", vSSRS.GetText(1, 7));
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 执行到这里说明P表里还没有新记录近来，再从C表里去查。
				tSql = "SELECT to_char(a.CValiDate,'YYYY-MM-DD'),"
						+ " b.PayIntv," + " b.Prem" + " FROM LCPol a,LCPrem b"
						+ " WHERE a.PolNo=b.PolNo"
						+ " and b.PayStartDate<=to_date('"
						+ "?date?" + "','YYYY-MM-DD')"
						+ " and a.PolNo='" + "?tPolNo?" + "'" + " and b.DutyCode='"
						+ "?tDutyCode?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("date", vSSRS.GetText(1, 1));
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tDutyCode", tDutyCode);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS == null || tSSRS.MaxRow <= 0) {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "calJBSumPremPerDuty2";
					tError.errorMessage = "查询保费责任信息时产生错误！PolNo:" + tPolNo
							+ "，DutyCode:" + tDutyCode;
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if ("0".equals(StrTool.cTrim(tSSRS.GetText(i, 2)))) {
					// 趸交
					mCountResult += Double.parseDouble(tSSRS.GetText(i, 3));
					continue;
				}
				int tMonthNum = PubFun.calInterval(tSSRS.GetText(i, 1), vSSRS
						.GetText(1, 1), "M");
				int tNum = (int) (tMonthNum / Integer.parseInt(tSSRS.GetText(i,
						2))) + 1;
				mCountResult += (Double.parseDouble(tSSRS.GetText(i, 3)) * tNum);
			}
			// 计算tLastEdorValiDate后的实交
			tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and LastPayToDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and a.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and GetDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and b.DutyCode='"
					+ "?tDutyCode?"
					+ "'"
					+ " and FeeFinaType in ('BF','TF')" + ") g";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tDutyCode", tDutyCode);
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				mCountResult += Double.parseDouble(tSSRS.GetText(1, 1));
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBSumPremPerDuty2";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// /**********为外部提供公共接口，共两个，区分DutyCode的位数，不截取，两个险种的*******\
	// 计算保费相关
	// 1.查是否做过PU，如果做过，直接取LCPrem里的Prem。
	// 2.查是否做过减保，如果做过，调用减保专用计算保费函数。
	// 3.如果以上两个项目都没做过，则调用公共保费计算函数。
	// 结果通过getCalResult()返回，true--成功，false--失败

	// --外部--责任--基本保费（不包括加费）计算-->
	public boolean getBasicPremPerDuty2(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE PayPlanType='0'" + " and DutyCode='"
						+ "?tDutyCode?" + "'" + " and PolNo='" + "?tPolNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tDutyCode", tDutyCode);
				sqlbv.put("tPolNo", tPolNo);
				String tPUBasicPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUBasicPrem != null && !"".equals(tPUBasicPrem)) {
					mCountResult = Double.parseDouble(tPUBasicPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getBasicPremPerDuty2";
					tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
					logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
							+ " DutyCode:" + tDutyCode);
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType in ('PT', 'FM', 'YC') and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保 或 缴费期限变更
					if (!calJBBasicPremPerDuty2(tPolNo, tDutyCode, tLineDate,
							tSSRS)) {
						return false;
					}
				} else {
					if (!calNewBasicPremPerDuty2(tPolNo, tDutyCode, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getBasicPremPerDuty2";
			tError.errorMessage = "计算基本保费（不包括加费）时产生错误！";
			logger.debug("计算基本保费（不包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	// --外部--责任--合计保费（包括加费）计算-->
	public boolean getSumPremPerDuty2(String tPolNo, String tDutyCode,
			String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE DutyCode='" + "?tDutyCode?" + "'"
						+ " and PolNo='" + "?tPolNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tDutyCode", tDutyCode);
				sqlbv.put("tPolNo",tPolNo);
				String tPUSumPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUSumPrem != null && !"".equals(tPUSumPrem)) {
					mCountResult = Double.parseDouble(tPUSumPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getSumPremPerDuty2";
					tError.errorMessage = "计算合计保费（包括加费）时产生错误！";
					logger.debug("计算合计保费（包括加费）时产生错误！PolNo:" + tPolNo
							+ " DutyCode:" + tDutyCode);
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType='PT' and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保
					if (!calJBSumPremPerDuty2(tPolNo, tDutyCode, tLineDate,
							tSSRS)) {
						return false;
					}
				} else {
					if (!calNewSumPremPerDuty2(tPolNo, tDutyCode, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getSumPremPerDuty2";
			tError.errorMessage = "计算合计保费（包括加费）时产生错误！";
			logger.debug("计算合计保费（包括加费）时产生错误！PolNo:" + tPolNo
					+ " DutyCode:" + tDutyCode);
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	// \************************************************************************/
	// \@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/

	// ====ADD=====zhangtao========2005-10-16===================BGN==================
	/**
	 * 按保费项计算累计保费
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	public boolean getSumPremPerPrem(String tPolNo, String tPayplanCode,
			String tLineDate) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT 'x' FROM LPEdorItem WHERE EdorState='0'"
					+ " and EdorType='PU' and PolNo='" + "?tPolNo?" + "'"
					+ " and EdorValiDate<=to_date('" + "?tLineDate?"
					+ "','YYYY-MM-DD')";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			String tHaveDoPU = tExeSQL.getOneValue(sqlbv);
			if (tHaveDoPU != null && "x".equals(tHaveDoPU)) {
				// 做过PU
				tSql = "SELECT (case when sum(Prem) is not null then sum(Prem) else 0 end)" + " FROM LCPrem"
						+ " WHERE PayplanCode='" + "?tPayplanCode?" + "'"
						+ " and PolNo='" + "?tPolNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPayplanCode", tPayplanCode);
				sqlbv.put("tPolNo", tPolNo);
				String tPUSumPrem = tExeSQL.getOneValue(sqlbv);
				if (tPUSumPrem != null && !"".equals(tPUSumPrem)) {
					mCountResult = Double.parseDouble(tPUSumPrem);
				} else {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "getBasicPremPerDuty2";
					tError.errorMessage = "计算保费错误！";
					this.mErrors.addOneError(tError);
					mCountResult = 0.0;
					return false;
				}
			} else {
				tSql = "SELECT to_char(EdorValiDate,'YYYY-MM-DD'),EdorAcceptNo,EdorNo,EdorType,ContNo,InsuredNo,PolNo FROM LPEdorItem a WHERE EdorState='0'"
						+ " and EdorType='PT' and exists(select 'x' from LCPol where ContNo=a.ContNo and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorValiDate<=to_date('"
						+ "?tLineDate?"
						+ "','YYYY-MM-DD')"
						+ " and exists(select 'x' from LPPol where ContNo=a.ContNo and EdorNo=a.EdorNo and EdorType=a.EdorType"
						+ " and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " ORDER BY EdorValiDate desc,ModifyDate desc,ModifyTime desc";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tLineDate", tLineDate);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					// 做过减保
					if (!calJBPremPerPrem(tPolNo, tPayplanCode, tLineDate,
							tSSRS)) {
						return false;
					}
				} else {
					if (!calNewPremPerPrem(tPolNo, tPayplanCode, tLineDate)) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "getBasicPremPerDuty2";
			tError.errorMessage = "计算保费时产生错误！";
			this.mErrors.addOneError(tError);
			mCountResult = 0.0;
			return false;
		}
		return true;
	}

	public boolean calJBPremPerPrem(String tPolNo, String tPayplanCode,
			String tLineDate, SSRS vSSRS) {
		// 循环处理责任下的每一个缴费责任（趸缴就不乘了，就交一次）
		try {
			mCountResult = 0.0;
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
					+ " a.PayIntv,"
					+ " a.Prem"
					+ " FROM LPPrem a"
					+ " WHERE exists("
					+ " select 'x' from"
					+ " (select EdorNo,EdorType"
					+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
					+ "?EdorAcceptNo?"
					+ "' and EdorNo='"
					+ "?EdorNo?"
					+ "' and EdorType='"
					+ "?EdorType?"
					+ "' and ContNo='"
					+ "?ContNo?"
					+ "' and InsuredNo='"
					+ "?InsuredNo?"
					+ "' and PolNo='"
					+ "?PolNo?"
					+ "')"
					+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
					+ "?tPolNo?"
					+ "')"
					+ " and EdorState='0' and rownum=1"
					+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime)"
					+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
					+ " and a.PolNo='"
					+ "?tPolNo?"
					+ "' and a.PayStartDate<=to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD') and a.PayplanCode='"
					+ "?tPayplanCode?"
					+ "'";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "SELECT (select to_char(CValiDate,'YYYY-MM-DD') from LCPol where PolNo=a.PolNo),"
						+ " a.PayIntv,"
						+ " a.Prem"
						+ " FROM LPPrem a"
						+ " WHERE exists("
						+ " select 'x' from"
						+ " (select EdorNo,EdorType"
						+ " from LPEdorItem b where exists(select 'x' from LPEdorItem where EdorValiDate<=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')<to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='"
						+ "?EdorAcceptNo?"
						+ "' and EdorNo='"
						+ "?EdorNo?"
						+ "' and EdorType='"
						+ "?EdorType?"
						+ "' and ContNo='"
						+ "?ContNo?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?"
						+ "' and PolNo='"
						+ "?PolNo?"
						+ "')"
						+ " and exists(select 'x' from LPPrem where EdorNo=b.EdorNo and EdorType=b.EdorType and PolNo='"
						+ "?tPolNo?"
						+ "')"
						+ " and EdorState='0'"
						+ " order by b.EdorValiDate,b.ModifyDate,b.ModifyTime limit 0,1) g"
						+ " where EdorNo=a.EdorNo and EdorType=a.EdorType)"
						+ " and a.PolNo='"
						+ "?tPolNo?"
						+ "' and a.PayStartDate<=to_date('"
						+ "?date?"
						+ "','YYYY-MM-DD') and a.PayplanCode='"
						+ "?tPayplanCode?"
						+ "'";	
			}
			sqlbv.sql(tSql);
			sqlbv.put("EdorAcceptNo", vSSRS.GetText(1, 2));
			sqlbv.put("EdorNo", vSSRS.GetText(1, 3));
			sqlbv.put("EdorType", vSSRS.GetText(1, 4));
			sqlbv.put("ContNo", vSSRS.GetText(1, 5));
			sqlbv.put("InsuredNo", vSSRS.GetText(1, 6));
			sqlbv.put("PolNo", vSSRS.GetText(1, 7));
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tPayplanCode", tPayplanCode);
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				// 执行到这里说明P表里还没有新记录近来，再从C表里去查。
				tSql = "SELECT to_char(a.CValiDate,'YYYY-MM-DD'),"
						+ " b.PayIntv," + " b.Prem" + " FROM LCPol a,LCPrem b"
						+ " WHERE a.PolNo=b.PolNo"
						+ " and b.PayStartDate<=to_date('"
						+ "?date?" + "','YYYY-MM-DD')"
						+ " and a.PolNo='" + "?tPolNo?" + "'"
						+ " and b.PayplanCode='" + "?tPayplanCode?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("date", vSSRS.GetText(1, 1));
				sqlbv.put("tPolNo", tPolNo);
				sqlbv.put("tPayplanCode", tPayplanCode);
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv);
				if (tSSRS == null || tSSRS.MaxRow <= 0) {
					CError tError = new CError();
					tError.moduleName = "BqPolBalBL";
					tError.functionName = "calJBPremPerPrem";
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if ("0".equals(StrTool.cTrim(tSSRS.GetText(i, 2)))) {
					// 趸交
					mCountResult += Double.parseDouble(tSSRS.GetText(i, 3));
					continue;
				}
				int tMonthNum = PubFun.calInterval(tSSRS.GetText(i, 1), vSSRS
						.GetText(1, 1), "M");
				int tNum = (int) (tMonthNum / Integer.parseInt(tSSRS.GetText(i,
						2))) + 1;
				mCountResult += (Double.parseDouble(tSSRS.GetText(i, 3)) * tNum);
			}
			// 计算tLastEdorValiDate后的实交
			tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and LastPayToDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and a.PayplanCode='"
					+ "?tPayplanCode?"
					+ "'"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode)"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and GetDate>to_date('"
					+ "?date?"
					+ "','YYYY-MM-DD')"
					+ " and b.PayplanCode='"
					+ "?tPayplanCode?"
					+ "'"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode)"
					+ ") g";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("date", vSSRS.GetText(1, 1));
			sqlbv.put("tPayplanCode", tPayplanCode);
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				mCountResult += Double.parseDouble(tSSRS.GetText(1, 1));
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calJBPremPerPrem";
			tError.errorMessage = "计算保费错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public boolean calNewPremPerPrem(String tPolNo, String tPayplanCode,
			String tLineDate) {
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			String tSql = "SELECT sum(BasicPrem)"
					+ " FROM ("
					+ " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) as BasicPrem"
					+ " from LJAPayPerson a"
					+ " where a.paytype in ('ZC','RE','AA','NS','NI') and a.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and LastPayToDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and a.PayplanCode='"
					+ "?tPayplanCode?"
					+ "'"
					+ " and exists(select 'X' from LCPrem where PolNo=a.PolNo and DutyCode=a.DutyCode and PayPlanCode=a.PayPlanCode )"
					+ " union"
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) as BasicPrem"
					+ " from LJAGetEndorse b"
					+ " where b.PolNo='"
					+ "?tPolNo?"
					+ "'"
					+ " and GetDate<=to_date('"
					+ "?tLineDate?"
					+ "','YYYY-MM-DD')"
					+ " and b.PayplanCode='"
					+ "?tPayplanCode?"
					+ "'"
					+ " and FeeFinaType in ('BF','TF')"
					+ " and exists(select 'Y' from LCPrem where PolNo=b.PolNo and DutyCode=b.DutyCode and PayPlanCode=b.PayPlanCode )"
					+ ") g";
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tLineDate", tLineDate);
			sqlbv.put("tPayplanCode", tPayplanCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				mCountResult = 0.0;
				return true;
			}
			mCountResult = Double.parseDouble(tSSRS.GetText(1, 1));
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BqPolBalBL";
			tError.functionName = "calNewBasicPremPerDuty2";
			tError.errorMessage = "计算保费时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// ====ADD=====zhangtao========2005-10-16===================END==================

	public static void main(String[] args) {

		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		String tPolNo = "210110000001506";
		// String tContNo = "230110000002293";
		// String tDate = PubFun.getCurrentDate();
		String tDate = "2005-04-15";
		if (tBqPolBalBL.calAutoPayPremAddInterest(tPolNo, tDate))
		// if(tBqPolBalBL.calLoanCorpusAddInterest(tContNo,tDate))
		{
			logger.debug("OK!");
			logger.debug("mResult:" + tBqPolBalBL.getCalResult());
		} else
			logger.debug("Fail!");

	}
}
