package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.text.DecimalFormat;

import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.vdb.LJSGetEndorseDBSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * ClassName: EdorFianProduce
 * </p>
 * <p>
 * Description: 财务业务应收、应付生成转储
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: TJJ
 * @CreateDate：2002-10-15
 */
public class PEdorUWFinaProduce {
private static Logger logger = Logger.getLogger(PEdorUWFinaProduce.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/* 通知书号 */
	String mGetNoticeNo;
	/* 实际通知书号 */
	String mActNoticeNo;
	/* 给付类型标志 "I": 交费；"O":退费 */
	String mFlag;
	/* 给付类型 */
	String mType;
	/* 实付号码 */
	String mActuGetNo;
	/* 交费收据号码 */
	String mPayNo;
	/* 号码生成参数(set,get方法提供外部接口) */
	String mLimit;
	/* 操作员 */
	String mOperator;
	/* 保存标志 */
	String mOperate;

	/* 代理人编码 */
	String mAgentCode;
	/* 代理人组别 */
	String mAgentGroup;
	/* 保全人工核保处NS加费金额 */
	// private double mUWAddPayMoney = 0;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/* 退费系列表 */
	LJAGetSet mLJAGetSet = new LJAGetSet();
	LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();
	LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet();
	LJAGetDrawSet mLJAGetDrawSet = new LJAGetDrawSet();
	LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();
	LJAGetTempFeeSet mLJAGetTempFeeSet = new LJAGetTempFeeSet();
	/* 交费总表 */
	LJAPaySet mLJAPaySet = new LJAPaySet();
	LJSGetEndorseSet mLJSGetEndorseSet;

	LJSGetSchema mLJSGetSchema = new LJSGetSchema();
	LJSPaySchema mLJSPaySchema = new LJSPaySchema();

	private MMap map = new MMap();

	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	// @Constructor
	public PEdorUWFinaProduce(String aGetNoticeNo) {
		mGetNoticeNo = aGetNoticeNo;
		logger.debug("--getnoticeno:" + mGetNoticeNo);
	}

	// @Method

	/**
	 * 提交整体给付核销
	 * 
	 * @return
	 */

	public boolean submitData() {
		// 批改补退费合计
		if (!conLJFina()) {
			return false;
		}

		return true;
	}

	public void setLimit(String aLimit) {
		mLimit = aLimit;
	}

	public String getLimit() {
		return mLimit;
	}

	public void setOperator(String aOperator) {
		mOperator = aOperator;
	}

	public String getOperator() {
		return mOperator;
	}

	public void setActuGetNo(String aActuGetNo) {
		mActuGetNo = aActuGetNo;
	}

	public String getActuGetNo() {
		return mActuGetNo;
	}

	public void setAgentGroup(String aAgentGroup) {
		mAgentGroup = aAgentGroup;
	}

	public String getAgentGroup() {
		return mAgentGroup;
	}

	public void setAgentCode(String aAgentCode) {
		mAgentCode = aAgentCode;
	}

	public String getAgentCode() {
		return mAgentCode;
	}

	// 得到保全交退费计算符号
	public int getSign(String aFinaFeeType) {
		int aSign = 0;
		if (aFinaFeeType.equals("BF")) {
			aSign = 1;
		} else if (aFinaFeeType.equals("TF")) {
			aSign = -1;
		} else if (aFinaFeeType.equals("TB")) {
			aSign = -1;
		} else if (aFinaFeeType.equals("EY")) {
			aSign = -1;
		} else if (aFinaFeeType.equals("LX")) {
			aSign = 1;
		} else if (aFinaFeeType.equals("PF")) // 不计入保全交退费
		{
			aSign = 0;
		} else if (aFinaFeeType.equals("GB")) // 不计入保全交退费
		{
			aSign = 1;
		} else if (aFinaFeeType.equals("0")) {
			aSign = 1;
		} else if (aFinaFeeType.equals("1")) {
			aSign = -1;
		} else {
			logger.debug("-------目前没有此类型描述----------");
		}

		return aSign;
	}

	/**
	 * 准备转储数据
	 * 
	 * @return String
	 */
	public boolean conLJFina() {
		/* 统计金额 */
		double tGetMoney = 0;
        //营改增 add zhangyingfeng 2016-07-14
        //统计税额 净额 税率
		double tGetNetAm = 0;  //净额
		double tGetTaxAm = 0;  //税额
		double tGetTax = 0;  //税率  ，取一条
        //end zhangyingfeng 2016-07-14
		/* 批改补退费核销转储 */
		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetEndorseSet = tLJSGetEndorseDB.query();
		if (tLJSGetEndorseDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "批改补退费查询失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (mLJSGetEndorseSet != null && mLJSGetEndorseSet.size() > 0) {
			for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
				double dGetMoney = mLJSGetEndorseSet.get(i).getGetMoney();
				tGetMoney += dGetMoney;
		        //营改增 add zhangyingfeng 2016-07-14
		        //统计税额 净额 税率
				tGetNetAm += mLJSGetEndorseSet.get(i).getNetAmount();  //净额
				tGetTaxAm += mLJSGetEndorseSet.get(i).getTaxAmount();  //税额
				tGetTax = mLJSGetEndorseSet.get(i).getTax();  //税率  ，取一条
		        //end zhangyingfeng 2016-07-14
			}
			LJSGetEndorseSchema aLJSGetEndorseSchema = mLJSGetEndorseSet.get(1);

			tGetMoney = Double.parseDouble(mDecimalFormat.format(tGetMoney));
			if (tGetMoney >= 0) {
				mFlag = "I";
				String StrLimit = getLimit();
				mActNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", StrLimit);
				aLJSGetEndorseSchema.setGetNoticeNo(mActNoticeNo);
//				mLJSPaySchema = initLJSPaySchema(aLJSGetEndorseSchema,
//						tGetMoney);
		        //营改增 add zhangyingfeng 2016-07-14
		        //增加税额 净额 税率 
				mLJSPaySchema = initLJSPaySchema(aLJSGetEndorseSchema,
						tGetMoney,tGetNetAm,tGetTaxAm,tGetTax);
		        //end zhangyingfeng 2016-07-14
				map.put(mLJSPaySchema, "DELETE&INSERT");
			} else {
				mFlag = "O";
				String StrLimit = getLimit();
				mActNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", StrLimit);
				aLJSGetEndorseSchema.setGetNoticeNo(mActNoticeNo);
//				mLJSGetSchema = initLJSGetSchema(aLJSGetEndorseSchema,
//						-tGetMoney);
		        //营改增 add zhangyingfeng 2016-07-14
		        //增加税额 净额 税率 
				mLJSGetSchema = initLJSGetSchema(aLJSGetEndorseSchema,
						-tGetMoney,-tGetNetAm,-tGetTaxAm,tGetTax);
		        //end zhangyingfeng 2016-07-14
				map.put(mLJSGetSchema, "DELETE&INSERT");
			}

			String tSQL = " update LJSGetEndorse set " + " GetNoticeNo = '?mActNoticeNo?' " + " where GetnoticeNo='?mGetNoticeNo?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("mActNoticeNo", mActNoticeNo);
			sqlbv.put("mGetNoticeNo", mGetNoticeNo);
			map.put(sqlbv, "UPDATE");

		}

		return true;
	}

	// 准备给付主表
//	public LJSGetSchema initLJSGetSchema(
//			LJSGetEndorseSchema aLJSGetEndorseSchema, double aGetMoney) {
	public LJSGetSchema initLJSGetSchema(
			LJSGetEndorseSchema aLJSGetEndorseSchema, double aGetMoney,double tGetNetAm,double tGetTaxAm, double tGetTax) {
		LJSGetSchema aLJSGetSchema = new LJSGetSchema();

		aLJSGetSchema.setGetNoticeNo(mActNoticeNo);
		aLJSGetSchema.setOtherNo(aLJSGetEndorseSchema.getEndorsementNo());
		aLJSGetSchema.setOtherNoType("3");
		aLJSGetSchema.setAppntNo(aLJSGetEndorseSchema.getAppntNo());
		aLJSGetSchema.setApproveCode(aLJSGetEndorseSchema.getApproveCode());
		aLJSGetSchema.setApproveDate(aLJSGetEndorseSchema.getApproveDate());
		aLJSGetSchema.setSumGetMoney(aGetMoney);
        //营改增 add zhangyingfeng 2016-07-14
        //增加税额 净额 税率 
		aLJSGetSchema.setNetAmount(tGetNetAm);
		aLJSGetSchema.setTaxAmount(tGetTaxAm);
		aLJSGetSchema.setTax(tGetTax);
        //end zhangyingfeng 2016-07-14
		aLJSGetSchema.setGetDate(mCurrentDate);
		aLJSGetSchema.setOperator(mOperator);
		aLJSGetSchema.setMakeDate(mCurrentDate);
		aLJSGetSchema.setMakeTime(mCurrentTime);
		aLJSGetSchema.setModifyDate(mCurrentDate);
		aLJSGetSchema.setModifyTime(mCurrentTime);
		aLJSGetSchema.setManageCom(aLJSGetEndorseSchema.getManageCom());
		if (mAgentCode != null) {
			aLJSGetSchema.setAgentCode(mAgentCode);
		}
		if (mAgentGroup != null) {
			aLJSGetSchema.setAgentGroup(mAgentGroup);
		}

		return aLJSGetSchema;
	}
	
	// 准备给付主表
//	public LJSPaySchema initLJSPaySchema(
//			LJSGetEndorseSchema aLJSGetEndorseSchema, double aGetMoney) {
	public LJSPaySchema initLJSPaySchema(
			LJSGetEndorseSchema aLJSGetEndorseSchema, double aGetMoney,double tGetNetAm,double tGetTaxAm, double tGetTax) {
		LJSPaySchema aLJSPaySchema = new LJSPaySchema();

		aLJSPaySchema.setGetNoticeNo(mActNoticeNo);
		aLJSPaySchema.setOtherNo(aLJSGetEndorseSchema.getEndorsementNo()); // 批单号
		aLJSPaySchema.setOtherNoType("3");
		aLJSPaySchema.setAppntNo(aLJSGetEndorseSchema.getAppntNo());
		aLJSPaySchema.setApproveCode(aLJSGetEndorseSchema.getApproveCode()); // 何时置的？
		aLJSPaySchema.setApproveDate(aLJSGetEndorseSchema.getApproveDate()); // 何时置的？
		aLJSPaySchema.setSumDuePayMoney(aGetMoney);
        //营改增 add zhangyingfeng 2016-07-14
        //增加税额 净额 税率 
		aLJSPaySchema.setNetAmount(tGetNetAm);
		aLJSPaySchema.setTaxAmount(tGetTaxAm);
		aLJSPaySchema.setTax(tGetTax);
        //end zhangyingfeng 2016-07-14
		aLJSPaySchema.setPayDate(mCurrentDate);
		aLJSPaySchema.setOperator(mOperator);
		aLJSPaySchema.setMakeDate(mCurrentDate);
		aLJSPaySchema.setMakeTime(mCurrentTime);
		aLJSPaySchema.setModifyDate(mCurrentDate);
		aLJSPaySchema.setModifyTime(mCurrentTime);
		aLJSPaySchema.setRiskCode("000000");
		aLJSPaySchema.setManageCom(aLJSGetEndorseSchema.getManageCom());
		if (mAgentCode != null) {
			aLJSPaySchema.setAgentCode(mAgentCode);
		}
		if (mAgentGroup != null) {
			aLJSPaySchema.setAgentGroup(mAgentGroup);
		}

		return aLJSPaySchema;
	}

	/**
	 * 保存实付数据
	 * 
	 * @return
	 */
	public boolean saveLJFinaSerials() {
		/* 批改补退费表（实收/实付） */
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();

		/* save data */
		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorFinaProduce";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			if (mLJSGetEndorseSet.size() > 0) {
				LJSGetEndorseDBSet tLJSGetEndorseDBSet = new LJSGetEndorseDBSet(
						conn);
				tLJSGetEndorseDBSet.set(mLJSGetEndorseSet);
				if (!tLJSGetEndorseDBSet.update()) {
					CError tError = new CError();
					tError.moduleName = "EdorFinaProduce";
					tError.functionName = "saveLJFinaProduce";
					tError.errorMessage = "实付保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				logger.debug("--getnoticeno:" + mGetNoticeNo);
				ExeSQL aExeSQL = new ExeSQL(conn);
				String sql = "update LJSGetEndorse set GetNoticeNo = '?mActNoticeNo?' where GetnoticeNo='?mGetNoticeNo?'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("mActNoticeNo", mActNoticeNo);
				sqlbv1.put("mGetNoticeNo", mGetNoticeNo);
				logger.debug("----sql:" + sql);
				if (!aExeSQL.execUpdateSQL(sqlbv1)) {
					CError tError = new CError();
					tError.moduleName = "EdorFinaProduce";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "更新给付通知有误失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			if (mOperate.equals("INSERT")) {
				if (mFlag.equals("I")) {
					LJSPayDB tLJSPayDB = new LJSPayDB(conn);
					tLJSPayDB.setSchema(mLJSPaySchema);
					if (!tLJSPayDB.insert()) {
						CError tError = new CError();
						tError.moduleName = "EdorFinaProduce";
						tError.functionName = "saveLJFinaProduce";
						tError.errorMessage = "实收主表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				} else if (mFlag.equals("O")) {
					LJSGetDB tLJSGetDB = new LJSGetDB(conn);
					tLJSGetDB.setSchema(mLJSGetSchema);
					if (!tLJSGetDB.insert()) {
						CError tError = new CError();
						tError.moduleName = "EdorFinaProduce";
						tError.functionName = "saveLJAGetserials";
						tError.errorMessage = "实付主表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				} else {
					CError tError = new CError();
					tError.moduleName = "EdorFinaProduce";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "标志有误失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			} else {
				if (mFlag.equals("I")) {
					LJSPayDB tLJSPayDB = new LJSPayDB(conn);
					tLJSPayDB.setSchema(mLJSPaySchema);
					if (!tLJSPayDB.update()) {
						CError tError = new CError();
						tError.moduleName = "EdorFinaProduce";
						tError.functionName = "saveLJFinaProduce";
						tError.errorMessage = "实收主表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				} else if (mFlag.equals("O")) {
					LJSGetDB tLJSGetDB = new LJSGetDB(conn);
					tLJSGetDB.setSchema(mLJSGetSchema);
					if (!tLJSGetDB.update()) {
						CError tError = new CError();
						tError.moduleName = "EdorFinaProduce";
						tError.functionName = "saveLJAGetserials";
						tError.errorMessage = "实付主表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				} else {
					CError tError = new CError();
					tError.moduleName = "EdorFinaProduce";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "标志有误失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorFinaProduce";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}

}
