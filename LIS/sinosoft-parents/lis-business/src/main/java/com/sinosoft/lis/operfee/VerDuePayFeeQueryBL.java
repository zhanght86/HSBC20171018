package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.customer.FICustomer;
import com.sinosoft.lis.customer.FICustomerRN;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayGrpDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.pubfun.ContHangUpBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LJSPayGrpSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
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
 * Description: 应收信息查询逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class VerDuePayFeeQueryBL {
private static Logger logger = Logger.getLogger(VerDuePayFeeQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput tGI = new GlobalInput();
	private String CurrentDate = PubFun.getCurrentDate();
//	private String CurrentTime = PubFun.getCurrentTime();

	/** 业务处理相关变量 */
	/** 应收总费表 */
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LJSPayGrpSet mLJSPayGrpSet = new LJSPayGrpSet();

	public VerDuePayFeeQueryBL() {
	}

	public static void main(String[] args) {

		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		tLJSPaySchema.setOtherNoType("2");
		tLJSPaySchema.setOtherNo("JNK20227011000401");
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.Operator = "001";
		VData tVData = new VData();
		tVData.add(tLJSPaySchema);
		tVData.add(tGI);
		VerDuePayFeeQueryBL tVerDuePayFeeQueryBL = new VerDuePayFeeQueryBL();

		if (!tVerDuePayFeeQueryBL.submitData(tVData, "QUERYDETAIL")) {
			// return false;
			logger.debug("出错了!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---getInputData---");

		// 进行业务处理
		if (cOperate.equals("QUERYDETAIL")) {
			if (!queryLJVerDuePayFeeDetail())
				return false;
		} else {
			if (!queryLJVerDuePayFee())
				return false;
			logger.debug("---queryLJVerDuePayFee---");
		}

		return true;
	}

	public boolean submitDataForMult(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---getInputData---");

		// 进行业务处理
		if (!queryMultLJVerDuePayFee())
			return false;
		logger.debug("---queryLJVerDuePayFee---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		mLJSPaySchema.setSchema((LJSPaySchema) cInputData
				.getObjectByObjectName("LJSPaySchema", 0));
		tGI = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (mLJSPaySchema == null) {
			// @@错误处理
			CError.buildErr(this, "请输入查询条件!");
			return false;
		}
		return true;
	}

	/**
	 * 查询应收总表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLJVerDuePayFee() {
		// 应收总表
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setSchema(mLJSPaySchema);

		logger.debug("==================开始查询：业务　１－保全　２－理赔回退====================");
		logger.debug(mLJSPaySchema.getOtherNo());
		mLJSPaySet = tLJSPayDB.query();
		if (tLJSPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
			CError.buildErr(this, "应收总表查询失败!");
			mLJSPaySet.clear();
			return false;
		}
		if (mLJSPaySet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据!");
			mLJSPaySet.clear();
			return false;
		}
		for(int i=1;i<=mLJSPaySet.size();i++)
		{
			if("1".equals(mLJSPaySet.get(i).getBankOnTheWayFlag()))
			{
				CError.buildErr(this, "银行在途中不能收费");
				return false;
			}
			//针对续期,使用客户账户冲减余额后,再进行显示
			String tCurrency = mLJSPaySet.get(i).getCurrency();
			String tContNo = mLJSPaySet.get(i).getOtherNo();
			//获取客户账户的余额.
			FICustomer tFICustomer = new FICustomerRN();
			double mDif = tFICustomer.queryAccount(tContNo, "2",tCurrency);
			mLJSPaySet.get(i).setSumDuePayMoney((mLJSPaySet.get(i).getSumDuePayMoney()-mDif)<0?0:(mLJSPaySet.get(i).getSumDuePayMoney()-mDif));

		}
		
		LJSPaySet tempLJSPaySet = new LJSPaySet();
		tempLJSPaySet.add(mLJSPaySet);
		for(int i=1;i<=tempLJSPaySet.size();i++)
		{
			if(tempLJSPaySet.get(i).getSumDuePayMoney()==0)
			{
				mLJSPaySet.remove(tempLJSPaySet.get(i));
			}
		}
//			else {
//
//			LJSPaySchema tLJSPaySchema = new LJSPaySchema();
//			tLJSPaySchema = mLJSPaySet.get(1);
//			if (tLJSPaySchema.getBankOnTheWayFlag() != null
//					&& tLJSPaySchema.getBankOnTheWayFlag().equals("1")) {
//				CError tError = new CError();
//				tError.moduleName = "VerDuePayFeeQueryBL";
//				tError.functionName = "queryData";
//				tError.errorMessage = "银行在途中不能收费";
//				this.mErrors.addOneError(tError);
//				return false;
//			}

			logger.debug("开始具体业务收费的校验");
			// 张荣20050912让改的
			// if (mLJSPaySet.get(1).getOtherNo().equals("10")) {
			// //保全校验
			// String BQsql =
			// "select * from LJSGetEndorse where GetNoticeNo = '" +
			// mLJSPaySchema.getGetNoticeNo() +
			// "' and FeeOperationType in ('NS','AA')";
			// LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			// LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
			// tLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(BQsql);
			// if (tLJSGetEndorseSet.size() > 0) {
			// for (int i = 1; i <= tLJSGetEndorseSet.size(); i++) {
			// LJSGetEndorseSchema tLJSGetEndorseSchema = new
			// LJSGetEndorseSchema();
			// tLJSGetEndorseSchema = tLJSGetEndorseSet.get(i).
			// getSchema();
			// String ZxSql =
			// "select * from lcpol where mainpolno=polno and contno='" +
			// tLJSGetEndorseSchema.getContNo() + "'";
			// LCPolSet tLCPolSet = new LCPolSet();
			// LCPolDB tLCPolDB = new LCPolDB();
			// tLCPolSet = tLCPolDB.executeQuery(ZxSql);
			// for (int t = 1; t <= tLCPolSet.size(); t++) {
			// LCPolSchema tLCPolSchema = new LCPolSchema();
			// tLCPolSchema = tLCPolSet.get(t).getSchema();
			// if (tLCPolSchema.getPaytoDate().compareTo(
			// mLJSPaySchema.getStartPayDate()) == 0) {
			// this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "VerDuePayFeeQueryBL";
			// tError.functionName = "queryData";
			// tError.errorMessage = "主险未交续期保费，不能办理此业务交费!";
			// this.mErrors.addOneError(tError);
			// mLJSPaySet.clear();
			// return false;
			// }
			// }
			// }
			// }
			// }

//		}


		// 如果应收总表中的交费日期（失效日期）小于系统日期:报错
		// if
		// (tFDate.getDate(mLJSPaySet.get(1).getPayDate()).compareTo(tFDate.getDate(CurrentDate))<0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "VerDuePayFeeQueryBL";
		// tError.functionName = "queryData";
		// tError.errorMessage = "已经过了失效日期!";
		// this.mErrors.addOneError(tError);
		// mLJSPaySet.clear();
		// return false;
		// }
		mResult.clear();
		mResult.add(mLJSPaySet);
		return true;
	}

	private boolean queryLJVerDuePayFeeDetail() {
		// 应收总表
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setSchema(mLJSPaySchema);
//		String Asql = "select * from LJSPay where othernotype in ('1','2','3','8') and otherno = '"
//				+ mLJSPaySchema.getOtherNo()
//				+ "' and not exists (select otherno from ljtempfee where tempfeetype='2' and confdate is null and tempfeeno=ljspay.getnoticeno)";
//		logger.debug("1111111111=========" + Asql);
//		mLJSPaySet = tLJSPayDB.executeQuery(Asql);
		mLJSPaySet = tLJSPayDB.query();
		if (tLJSPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
			CError.buildErr(this, "应收总表查询失败!");
			mLJSPaySet.clear();
			return false;
		}
		if (mLJSPaySet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "无应收纪录或者存在未核销交费!!");
			mLJSPaySet.clear();
			return false;
		}

		for(int i=1;i<=mLJSPaySet.size();i++)
		{
			if("1".equals(mLJSPaySet.get(i).getBankOnTheWayFlag()))
			{
				CError.buildErr(this, "已经存在银行划帐数据，不能柜台交费 !");
				return false;
			}
				
		}
	    String tSql="";
	    ExeSQL tExeSQL = new ExeSQL();
	    SSRS tSSRS = new SSRS();
	    if (mLJSPaySet.get(1).getOtherNoType().equals("1")) //集体保单续期收费
	    {
	      tSql="select riskcode,sum(SumDuePayMoney),grppolno from LJSPayGrp where GetNoticeNo='?GetNoticeNo?' group by riskcode,grppolno";
	      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	      sqlbv.sql(tSql);
	      sqlbv.put("GetNoticeNo",mLJSPaySet.get(1).getGetNoticeNo());
	      tSSRS = tExeSQL.execSQL(sqlbv);
	    }
	    else if(mLJSPaySet.get(1).getOtherNoType().equals("2")) //个人保单续期收费
	    {
	      tSql="select riskcode,sum(SumDuePayMoney),polno from LJSPayPerson where GetNoticeNo='?GetNoticeNo?' group by riskcode,polno";
	      SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	      sqlbv1.sql(tSql);
	      sqlbv1.put("GetNoticeNo",mLJSPaySet.get(1).getGetNoticeNo());
	      tSSRS = tExeSQL.execSQL(sqlbv1);

	    }
	    else if("5".equals((mLJSPaySet.get(1).getOtherNoType()))) //理赔加费收费
	    {
		   tSql="select riskcode,sum(SumDuePayMoney),polno from LJSPayPerson where GetNoticeNo='?GetNoticeNo?' group by riskcode,polno";
		   SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		   sqlbv2.sql(tSql);
		   sqlbv2.put("GetNoticeNo",mLJSPaySet.get(1).getGetNoticeNo());
		   tSSRS = tExeSQL.execSQL(sqlbv2);
	    }
	    else
	    {
	      CError.buildErr(this, "非集体、个人续期收费或理赔加费收费!");
	      mLJSPaySet.clear();
	      return false;
	    }
	    if(tSSRS.MaxRow<=0)
	    {
	    	CError.buildErr(this, "没有应收明细信息");
	    	return false;
	    }

		/*-------------个人或集体应收查询完毕----------------*/
		// 检查保单是否被挂起
		/** @todo 5----检验个人保单是否被挂起* */
		RNHangUp tRNHangUp = new RNHangUp(tGI);
		if (!tRNHangUp.checkHangUP(mLJSPaySchema.getOtherNo())) {
			this.mErrors.copyAllErrors(tRNHangUp.mErrors);
			return false;
		}
	    
//		if (mLJSPaySet.get(1).getOtherNoType() != null
//				&& !mLJSPaySet.get(1).getOtherNoType().equals("1")) {
//			/*---------------查询应收个人表-----------------*/
//			for (int n = 1; n <= mLJSPaySet.size(); n++) {
//				LJSPaySchema tLJSPaySchema = new LJSPaySchema();
//				tLJSPaySchema = mLJSPaySet.get(n);
//				String Sql = "select * from ljspayperson where getnoticeno='"
//						+ tLJSPaySchema.getGetNoticeNo() + "' and contno = '"
//						+ tLJSPaySchema.getOtherNo() + "'";
//				logger.debug("22222222222" + Sql);
//				LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
//				mLJSPayPersonSet = tLJSPayPersonDB.executeQuery(Sql);
//
//				if (mLJSPayPersonSet.size() == 0) {
//					CError tError = new CError();
//					tError.moduleName = "VerDuePayFeeQueryBL";
//					tError.functionName = "queryData";
//					tError.errorMessage = "应收子表无数据!!";
//					this.mErrors.addOneError(tError);
//					mLJSPaySet.clear();
//					return false;
//				}
//
//				if (tLJSPaySchema.getBankOnTheWayFlag() != null) {
//					if (tLJSPaySchema.getBankOnTheWayFlag().equals("1")) {
//						CError tError = new CError();
//						tError.moduleName = "VerDuePayFeeQueryBL";
//						tError.functionName = "queryData";
//						tError.errorMessage = "该保单银行划款处理中";
//						this.mErrors.addOneError(tError);
//						mLJSPaySet.clear();
//						return false;
//					}
//				}
//
//				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
//				tLJSPayPersonSchema = mLJSPayPersonSet.get(1);
//				LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
//				tLJSPayPersonSet.add(mLJSPayPersonSet);
//				for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
//					LJSPayPersonSchema nLJSPayPersonSchema = new LJSPayPersonSchema();
//					nLJSPayPersonSchema = tLJSPayPersonSet.get(i);
//					LCPolDB tLCPolDB = new LCPolDB();
//					tLCPolDB.setPolNo(nLJSPayPersonSchema.getPolNo());
//					tLCPolDB.getInfo();
//					if (!tLCPolDB.getPolNo().equals(tLCPolDB.getMainPolNo())) {
//						logger.debug("第" + i + "次去除"
//								+ nLJSPayPersonSchema.getPayPlanCode());
//						mLJSPayPersonSet.remove(nLJSPayPersonSchema);
//						if (mLJSPayPersonSet.size() == 0) {
//							mLJSPayPersonSet.add(nLJSPayPersonSchema);
//						}
//					}
//				}
//			}
//		}
//		if (mLJSPaySet.get(1).getOtherNoType() != null
//				&& mLJSPaySet.get(1).getOtherNoType().equals("1")) {
//			/*---------------查询应收集体表-----------------*/
//			for (int n = 1; n <= mLJSPaySet.size(); n++) {
//				LJSPaySchema tLJSPaySchema = new LJSPaySchema();
//				tLJSPaySchema = mLJSPaySet.get(n);
//				String Sql = "select * from ljspaygrp where getnoticeno='"
//						+ tLJSPaySchema.getGetNoticeNo()
//						+ "' and grpcontno = '" + tLJSPaySchema.getOtherNo()
//						+ "'";
//				logger.debug("22222222222" + Sql);
//				LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
//				mLJSPayGrpSet = tLJSPayGrpDB.executeQuery(Sql);
//
//				if (mLJSPayGrpSet.size() == 0) {
//					CError tError = new CError();
//					tError.moduleName = "VerDuePayFeeQueryBL";
//					tError.functionName = "queryData";
//					tError.errorMessage = "应收子表无数据!!";
//					this.mErrors.addOneError(tError);
//					mLJSPaySet.clear();
//					return false;
//				}
//
//				if (tLJSPaySchema.getBankOnTheWayFlag() != null) {
//					if (tLJSPaySchema.getBankOnTheWayFlag().equals("1")) {
//						CError tError = new CError();
//						tError.moduleName = "VerDuePayFeeQueryBL";
//						tError.functionName = "queryData";
//						tError.errorMessage = "该保单银行划款处理中";
//						this.mErrors.addOneError(tError);
//						mLJSPaySet.clear();
//						return false;
//					}
//				}
//
//				LJSPayGrpSchema tLJSPayGrpSchema = new LJSPayGrpSchema();
//				tLJSPayGrpSchema = mLJSPayGrpSet.get(1);
//				LJSPayGrpSet tLJSPayGrpSet = new LJSPayGrpSet();
//				tLJSPayGrpSet.add(mLJSPayGrpSet);
//				for (int i = 1; i <= tLJSPayGrpSet.size(); i++) {
//					LJSPayGrpSchema nLJSPayGrpSchema = new LJSPayGrpSchema();
//					nLJSPayGrpSchema = tLJSPayGrpSet.get(i);
//					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
//					tLCGrpPolDB.setGrpPolNo(nLJSPayGrpSchema.getGrpPolNo());
//					tLCGrpPolDB.getInfo();
//					// if
//					// (!tLCGrpPolDB.getGrpPolNo().equals(tLCGrpPolDB.getMainPolNo()))
//					// {
//					// logger.debug("第" + i + "次去除" +
//					// nLJSPayGrpSchema.getPayPlanCode());
//					// mLJSPayPersonSet.remove(nLJSPayGrpSchema);
//					// if (mLJSPayPersonSet.size() == 0) {
//					// mLJSPayPersonSet.add(nLJSPayGrpSchema);
//					// }
//					// }
//				}
//			}
//		}

		/*-------------个人或集体应收查询完毕----------------*/
		// 检查保单是否被挂起
		/** @todo 5----检验个人保单是否被挂起* */
//		RNHangUp tRNHangUp = new RNHangUp(tGI);
//		if (!tRNHangUp.checkHangUP(mLJSPaySchema.getOtherNo())) {
//			this.mErrors.copyAllErrors(tRNHangUp.mErrors);
//			return false;
//		}
		/*----检验团体保单是否被挂起**/
//		ContHangUpBL tContHangUpBL = new ContHangUpBL(tGI);
//		if (!tContHangUpBL.checkGrpHangUpState(mLJSPaySchema.getOtherNo(), "3")) {
//			this.mErrors.copyAllErrors(tContHangUpBL.mErrors);
//			return false;
//		}

//		if (!mLJSPaySet.get(1).getOtherNoType().equals("1")) {
//			String hsql = "select * from lccontstate where contno='"
//					+ mLJSPaySchema.getOtherNo()
//					+ "' and polno in (select polno from lcpol where polno=mainpolno and contno='"
//					+ mLJSPaySchema.getOtherNo()
//					+ "') and state = '1' and StateType in ('Available','Terminate','Lost')"
//					+ " and startdate<='" + CurrentDate
//					+ "' and enddate is null";
//			logger.debug("查询保单是否失效===========" + hsql);
//			LCContStateDB tLCContStateDB = new LCContStateDB();
//			LCContStateSet tLCContStateSet = new LCContStateSet();
//			tLCContStateSet = tLCContStateDB.executeQuery(hsql);
//			if (tLCContStateSet.size() > 0) {
//				for (int i = 1; i <= tLCContStateSet.size(); i++) {
//					LCContStateSchema tLCContStateSchema = new LCContStateSchema();
//					tLCContStateSchema = tLCContStateSet.get(i);
//					if (tLCContStateSchema.getEndDate() == null
//							|| tLCContStateSchema.getEndDate().equals("")
//							|| tLCContStateSchema.getEndDate().equals(null)) {
//						if (PubFun.calInterval(tLCContStateSchema
//								.getStartDate(), CurrentDate, "D") >= 0) {
//							CError tError = new CError();
//							tError.moduleName = "VerDuePayFeeQueryBL";
//							tError.functionName = "queryData";
//							tError.errorMessage = "该保单已经失效";
//							this.mErrors.addOneError(tError);
//							mLJSPaySet.clear();
//							return false;
//						}
//					}
//
//				}
//			}
//		}
//		if (mLJSPaySet.get(1).getOtherNoType().equals("1")) {
//			String hsql = "select * from lcgrpcontstate where grpcontno='"
//					+ mLJSPaySchema.getOtherNo()
//					+ "' and grppolno in (select grppolno from grplcpol where grpcontno='"
//					+ mLJSPaySchema.getOtherNo()
//					+ "') and state = '1' and StateType in ('Available','Terminate','Lost')"
//					+ " and startdate<='" + CurrentDate
//					+ "' and enddate is null";
//			logger.debug("查询保单是否失效===========" + hsql);
//			LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
//			LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
//			tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(hsql);
//			if (tLCGrpContStateSet.size() > 0) {
//				for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
//					LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
//					tLCGrpContStateSchema = tLCGrpContStateSet.get(i);
//					if (tLCGrpContStateSchema.getEndDate() == null
//							|| tLCGrpContStateSchema.getEndDate().equals("")
//							|| tLCGrpContStateSchema.getEndDate().equals(null)) {
//						if (PubFun.calInterval(tLCGrpContStateSchema
//								.getStartDate(), CurrentDate, "D") >= 0) {
//							CError tError = new CError();
//							tError.moduleName = "VerDuePayFeeQueryBL";
//							tError.functionName = "queryData";
//							tError.errorMessage = "该保单已经失效";
//							this.mErrors.addOneError(tError);
//							mLJSPaySet.clear();
//							return false;
//						}
//					}
//					if (tLCGrpContStateSchema.getEndDate() != null
//							|| !tLCGrpContStateSchema.getEndDate().equals(null)
//							|| !tLCGrpContStateSchema.getEndDate().equals("")) {
//						if (PubFun.calInterval(tLCGrpContStateSchema
//								.getStartDate(), CurrentDate, "D") >= 0
//								&& PubFun.calInterval(tLCGrpContStateSchema
//										.getStartDate(), CurrentDate, "D") <= 0) {
//							CError tError = new CError();
//							tError.moduleName = "VerDuePayFeeQueryBL";
//							tError.functionName = "queryData";
//							tError.errorMessage = "该保单已经失效";
//							this.mErrors.addOneError(tError);
//							mLJSPaySet.clear();
//							return false;
//						}
//					}
//				}
//
//			}
//
//		}
//		String tSql = "";
//		ExeSQL tExeSQL = new ExeSQL();
//
//		if (mLJSPaySet.get(1).getOtherNoType().equals("1")) { // 集体保单续期收费
//			LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
//			tSql = "select '',sum(SumDuePayMoney),grppolno from LJSPayGrp where GetNoticeNo='"
//					+ mLJSPaySet.get(1).getGetNoticeNo()
//					+ "' group by riskcode,grppolno";
//			tSSRS = tExeSQL.execSQL(tSql);
//		} else if (!mLJSPaySet.get(1).getOtherNoType().equals("1")) { // 个人保单续期收费
//			tSql = "select riskcode,sum(SumDuePayMoney),polno from LJSPayPerson where GetNoticeNo='"
//					+ mLJSPaySet.get(1).getGetNoticeNo()
//					+ "' group by riskcode,polno";
//			tSSRS = tExeSQL.execSQL(tSql);
//
//		} else {
//			CError tError = new CError();
//			tError.moduleName = "VerDuePayFeeQueryBL";
//			tError.functionName = "queryData";
//			tError.errorMessage = "非集体或个人续期收费!";
//			this.mErrors.addOneError(tError);
//			mLJSPaySet.clear();
//			return false;
//		}		

		mResult.clear();
		mResult.add(mLJSPaySet);
		mResult.add(mLJSPayPersonSet);
		mResult.add(mLJSPayGrpSet);
		mResult.add(tSSRS);
		return true;
	}

	/**
	 * 查询应收总表信息-批量 输出：如果发生错误则返回false,否则返回true
	 */
	public boolean queryMultLJVerDuePayFee() {
		LJSPayDB tLJSPayDB = new LJSPayDB();
		String StartDate = mLJSPaySchema.getPayDate();
		String EndDate = mLJSPaySchema.getMakeDate();
		String sqlStr = "select * from LJSPay where 1=1 ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		if (StartDate != null){
			sqlStr = sqlStr + " and PayDate>='?StartDate?'";
			sqlbv3.put("StartDate", StartDate);
		}
		if (EndDate != null){
			sqlStr = sqlStr + " and PayDate<='?EndDate?'";
			sqlbv3.put("EndDate",EndDate);
		}
		sqlbv3.sql(sqlStr);
		logger.debug("query ljspay:" + sqlStr);

		mLJSPaySet = tLJSPayDB.executeQuery(sqlbv3);
		if (tLJSPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "queryMultLJVerDuePayFee";
			tError.errorMessage = "应收总表查询失败!";
			this.mErrors.addOneError(tError);
			mLJSPaySet.clear();
			return false;
		}
		if (mLJSPaySet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "queryMultLJVerDuePayFee";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLJSPaySet.clear();
			return false;
		}
		mResult.clear();
		mResult.add(mLJSPaySet);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		try {
			mResult.add(mLJSPaySet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "VerDuePayFeeQueryBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
