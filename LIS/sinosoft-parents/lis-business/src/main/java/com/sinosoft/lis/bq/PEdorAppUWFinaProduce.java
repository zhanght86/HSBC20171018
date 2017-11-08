package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * @Author: ZhangRong
 */
public class PEdorAppUWFinaProduce {
private static Logger logger = Logger.getLogger(PEdorAppUWFinaProduce.class);
	// @Field
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
	/* 险种编码 */
	String mRiskCode;
	/* 代理人编码 */
	String mAgentCode;
	/* 代理人组别 */
	String mAgentGroup;
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
	LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();

	LJSGetSchema mLJSGetSchema = new LJSGetSchema();
	LJSPaySchema mLJSPaySchema = new LJSPaySchema();

	private String mEdorAccepNo = "";
	private MMap mUpdatemap = new MMap();

	// @Constructor
	public PEdorAppUWFinaProduce(String aEdorAccNo) {
		mEdorAccepNo = aEdorAccNo;
	}

	// @Method

	/**
	 * 提交整体给付核销
	 * 
	 * @return
	 */

	public boolean submitData() {

		if (this.conLJFina().equals("Y")) {
			// 数据准备操作（preparedata())
			if (!prepareOutputData()) {
				return false;
			}

		}
		return true;
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
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		/* 批改补退费表（实收/实付） */
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		mResult.clear();
		MMap map = new MMap();
		try {

			if (mLJSGetEndorseSet.size() > 0) {
				// 添加核保通知书打印管理表数据
				map.put(mLJSGetEndorseSet, "UPDATE");
				map.add(mUpdatemap);
			}

			if (mOperate.equals("INSERT")) {
				if (mFlag.equals("I")) {
					// 添加核保通知书打印管理表数据
					map.put(mLJSPaySchema, "INSERT");
				} else if (mFlag.equals("O")) {
					// 添加核保通知书打印管理表数据
					map.put(mLJSGetSchema, "INSERT");
				} else {
					CError tError = new CError();
					tError.moduleName = "PEdorAppUWFinaProduce";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "标志有误失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
			} else {
				if (mFlag.equals("I")) {
					// 添加核保通知书打印管理表数据
					map.put(mLJSPaySchema, "UPDATE");
				} else if (mFlag.equals("O")) {
					// 添加核保通知书打印管理表数据
					map.put(mLJSGetSchema, "UPDATE");
				}
			}

			if (map.keySet().size() > 0) {
				mResult.add(map);

			}

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppUWFinaProduce";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备转储数据
	 * 
	 * @return true or false
	 */
	public String conLJFina() {
		String aFlag;

		aFlag = "Y";

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAccepNo);
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "查询保全申请主表失败！");
			return "N";
		}
		LPEdorAppSchema tLPEdorAppSchema = tLPEdorAppDB.getSchema();
		Vector tEdorNoList = new Vector();
		if (tLPEdorAppSchema.getOtherNoType().equals("1")
				|| tLPEdorAppSchema.getOtherNoType().equals("3")) {
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorAcceptNo(mEdorAccepNo);
			LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询保全批改主表失败！");
				return "N";
			}
			for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
				tEdorNoList.add(tLPEdorMainSet.get(i).getEdorNo());
			}
		} else if (tLPEdorAppSchema.getOtherNoType().equals("2")
				|| tLPEdorAppSchema.getOtherNoType().equals("4")) {
			LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
			tLPGrpEdorMainDB.setEdorAcceptNo(mEdorAccepNo);
			LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
			if (tLPGrpEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询保全批改主表失败！");
				return "N";
			}
			for (int i = 1; i <= tLPGrpEdorMainSet.size(); i++) {
				tEdorNoList.add(tLPGrpEdorMainSet.get(i).getEdorNo());
			}
		} else {
			CError.buildErr(this, "保全申请类型有误！");
			return "N";
		}

		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		String StrLimit = this.getLimit();
		mActNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", StrLimit);
		double tGetMoney = 0;
        //营改增 add zhangyingfeng 2016-07-14
        //增加净额 税额汇总   税率
		double tGetNetAm = 0;   //净额
		double tGetTaxAm = 0;  //税额
		double tGetTax = 0;  //税率  业务不同，税率不同 ，暂取一条
        //end zhangyingfeng 2016-07-14
		aFlag = "N";
		for (int j = 0; j < tEdorNoList.size(); j++) {

			/* 批改补退费表（应收/应付） */
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

			/* 批改补退费核销转储 */
			mGetNoticeNo = (String) tEdorNoList.get(j);
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);

			tLJSGetEndorseSet = tLJSGetEndorseDB.query();

			if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
				aFlag = "Y";

				mLJSGetEndorseSet.clear();
				for (int i = 1; i <= tLJSGetEndorseSet.size(); i++) {
					tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					tLJSGetEndorseSchema = tLJSGetEndorseSet.get(i);
					mRiskCode = tLJSGetEndorseSchema.getRiskCode();
					tGetMoney = tGetMoney + tLJSGetEndorseSchema.getGetMoney(); // *
																				// this.getSign(tLJSGetEndorseSchema.getGetFlag());
			        //营改增 add zhangyingfeng 2016-07-14
			        //增加净额 税额汇总   税率
					tGetNetAm +=tLJSGetEndorseSchema.getNetAmount();   //净额
					tGetTaxAm += tLJSGetEndorseSchema.getTaxAmount();  //税额
					tGetTax = tLJSGetEndorseSchema.getTax();  //税率  业务不同，税率不同 ，暂取一条
			        //end zhangyingfeng 2016-07-14
					mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
				}

				LJSGetEndorseSchema aLJSGetEndorseSchema = new LJSGetEndorseSchema();
				aLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
				aLJSGetEndorseSchema.setGetNoticeNo(mActNoticeNo);
			}
		}
		if (tGetMoney > 0) {
			mFlag = "I";
//			mLJSPaySchema = initLJSPaySchema(tLPEdorAppSchema, tGetMoney);
		    //营改增 add zhangyingfeng 2016-07-14
			//增加净额 税额汇总   税率
			mLJSPaySchema = initLJSPaySchema(tLPEdorAppSchema, tGetMoney,tGetNetAm,tGetTaxAm,tGetTax);
	        //end zhangyingfeng 2016-07-14
		} else if (tGetMoney < 0) {
			mFlag = "O";
//			mLJSGetSchema = initLJSGetSchema(tLPEdorAppSchema, -tGetMoney);
		    //营改增 add zhangyingfeng 2016-07-14
			//增加净额 税额汇总   税率
			mLJSGetSchema = initLJSGetSchema(tLPEdorAppSchema, -tGetMoney,-tGetNetAm,-tGetTaxAm,tGetTax);
	        //end zhangyingfeng 2016-07-14
		} else {
			mFlag = "";
			mOperate = "";
		}
		String tSQL = "update LJSGetEndorse set GetNoticeNo = '" + "?mActNoticeNo?"
				+ "' where GetnoticeNo='" + "?mGetNoticeNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("mActNoticeNo", mActNoticeNo);
		sqlbv.put("mGetNoticeNo", mGetNoticeNo);
		mUpdatemap.put(sqlbv, "UPDATE");
		return aFlag;
	}

	// 准备给付主表
//	public LJSGetSchema initLJSGetSchema(LPEdorAppSchema aLPEdorAppSchema,
//			double aGetMoney) {
    //营改增 add zhangyingfeng 2016-07-14
	//增加净额 税额汇总   税率
	public LJSGetSchema initLJSGetSchema(LPEdorAppSchema aLPEdorAppSchema,
		double aGetMoney,double tGetNetAm,double tGetTaxAm,double tGetTax) {
    //end zhangyingfeng 2016-07-14
		LJSGetSchema aLJSGetSchema = new LJSGetSchema();

		aLJSGetSchema.setGetNoticeNo(mActNoticeNo);
		aLJSGetSchema.setOtherNo(aLPEdorAppSchema.getEdorAcceptNo());
		aLJSGetSchema.setOtherNoType("10");
		aLJSGetSchema.setAppntNo(aLPEdorAppSchema.getOtherNo());
		aLJSGetSchema.setApproveCode("");
		aLJSGetSchema.setApproveDate("");
		aLJSGetSchema.setSumGetMoney(aGetMoney);
	    //营改增 add zhangyingfeng 2016-07-14
		//增加净额 税额汇总   税率
		aLJSGetSchema.setNetAmount(tGetNetAm);
		aLJSGetSchema.setTaxAmount(tGetTaxAm);
		aLJSGetSchema.setTax(tGetTax);
        //end zhangyingfeng 2016-07-14
		aLJSGetSchema.setGetDate(PubFun.getCurrentDate());
		aLJSGetSchema.setOperator(mOperator);
		aLJSGetSchema.setMakeDate(PubFun.getCurrentDate());
		aLJSGetSchema.setMakeTime(PubFun.getCurrentTime());
		aLJSGetSchema.setModifyDate(PubFun.getCurrentDate());
		aLJSGetSchema.setModifyTime(PubFun.getCurrentTime());
		aLJSGetSchema.setManageCom(aLPEdorAppSchema.getManageCom());
		if (mAgentCode != null) {
			aLJSGetSchema.setAgentCode(mAgentCode);
		}
		if (mAgentGroup != null) {
			aLJSGetSchema.setAgentGroup(mAgentGroup);
		}
		LJSGetSet tLJSGetSet = new LJSGetSet();
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNo(aLJSGetSchema.getOtherNo());
		tLJSGetDB.setOtherNoType("10");
		tLJSGetSet = tLJSGetDB.query();

		if (tLJSGetSet.size() > 0) {
			mOperate = "UPDATE";
		} else {
			mOperate = "INSERT";
		}
		return aLJSGetSchema;
	}

	// 准备给付主表
//	public LJSPaySchema initLJSPaySchema(LPEdorAppSchema aLPEdorAppSchema,
//			double aGetMoney) {
	    //营改增 add zhangyingfeng 2016-07-14
		//增加净额 税额汇总   税率
	public LJSPaySchema initLJSPaySchema(LPEdorAppSchema aLPEdorAppSchema,
			double aGetMoney,double tGetNetAm,double tGetTaxAm,double tGetTax) {
        //end zhangyingfeng 2016-07-14
		LJSPaySchema aLJSPaySchema = new LJSPaySchema();

		aLJSPaySchema.setGetNoticeNo(mActNoticeNo);
		aLJSPaySchema.setOtherNo(aLPEdorAppSchema.getEdorAcceptNo());
		aLJSPaySchema.setOtherNoType("10");
		aLJSPaySchema.setAppntNo(aLPEdorAppSchema.getOtherNo());
		aLJSPaySchema.setApproveCode("");
		aLJSPaySchema.setApproveDate("");
		aLJSPaySchema.setSumDuePayMoney(aGetMoney);
	    //营改增 add zhangyingfeng 2016-07-14
		//增加净额 税额汇总   税率
		aLJSPaySchema.setNetAmount(tGetNetAm);
		aLJSPaySchema.setTaxAmount(tGetTaxAm);
		aLJSPaySchema.setTax(tGetTax);
        //end zhangyingfeng 2016-07-14
		aLJSPaySchema.setPayDate(PubFun.getCurrentDate());
		aLJSPaySchema.setOperator(mOperator);
		aLJSPaySchema.setMakeDate(PubFun.getCurrentDate());
		aLJSPaySchema.setMakeTime(PubFun.getCurrentTime());
		aLJSPaySchema.setModifyDate(PubFun.getCurrentDate());
		aLJSPaySchema.setModifyTime(PubFun.getCurrentTime());
		aLJSPaySchema.setRiskCode(mRiskCode);
		aLJSPaySchema.setManageCom(aLPEdorAppSchema.getManageCom());
		if (mAgentCode != null) {
			aLJSPaySchema.setAgentCode(mAgentCode);
		}
		if (mAgentGroup != null) {
			aLJSPaySchema.setAgentGroup(mAgentGroup);
		}

		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(aLJSPaySchema.getOtherNo());
		tLJSPayDB.setOtherNoType("10");
		tLJSPaySet = tLJSPayDB.query();

		if (tLJSPaySet.size() > 0) {
			mOperate = "UPDATE";
		} else {
			mOperate = "INSERT";
		}

		return aLJSPaySchema;
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

	public String getActNoticeNo() {
		return mActNoticeNo;
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

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		PEdorAppUWFinaProduce tEdorFinaProduce = new PEdorAppUWFinaProduce(
				"86110020040410000021");
		tEdorFinaProduce.setLimit(PubFun.getNoLimit("86"));
		tEdorFinaProduce.setOperator("004");
		tEdorFinaProduce.submitData();
	}
}
