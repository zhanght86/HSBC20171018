/*
 * @(#)LJSGetEndorseTotalBL.java	2005-06-09
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Hashtable;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-批改补退费合计处理类(2.0) MS以财务类型来区分不退费，不以正负来区分
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao,PST
 * @version：1.0,2.0
 * @CreateDate：2005-06-09,2007-12-20
 */
public class LJSGetEndorseTotalBL {
private static Logger logger = Logger.getLogger(LJSGetEndorseTotalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	
	private ExeSQL mExeSql = new ExeSQL();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LJSGetEndorseSet mLJSGetEndorseSet;
	private LJSGetSchema mLJSGetSchema = new LJSGetSchema();
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();

	private String mEdorAcceptNo;
	// private String mAgentCode;
	// private String mAgentGroup;
	private String mActNoticeNo = "";
	private String mManageCom;

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	/* 转换精确位数的对象 */
	private DecimalFormat mDecimalFormat = new DecimalFormat("0.00"); // 数字转换对象

	public LJSGetEndorseTotalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作符
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 删除以前生成的财务数据
		/*
		 * if (!delLJFina()) { return false; }
		 */

		// 批改补退费合计
		if (!conLJFina()) {
			return false;
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		// 查询保全受理信息
		if(mLPEdorAppSchema==null)
		{
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

			mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
			if (mEdorAcceptNo == null || mEdorAcceptNo.equals("")) {
				CError.buildErr(this, "传输数据EdorAcceptNo失败!");
				return false;
			}
			if (!getLPEdorApp()) {
				return false;
			}
		}
		mManageCom=mLPEdorAppSchema.getManageCom();		
		mEdorAcceptNo =mLPEdorAppSchema.getEdorAcceptNo(); 

		// mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		// if (mAgentCode == null || mAgentCode.equals(""))
		// {
		// CError tError = new CError();
		// tError.errorMessage = "传输数据AgentCode失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mAgentGroup = (String) mTransferData.getValueByName("AgentGroup");
		// if (mAgentGroup == null || mAgentGroup.equals(""))
		// {
		// CError tError = new CError();
		// tError.errorMessage = "传输数据AgentGroup失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// mNoLimit = (String) mTransferData.getValueByName("NoLimit");
		// if (mNoLimit == null || mNoLimit.equals(""))
		// {
		// CError tError = new CError();
		// tError.errorMessage = "传输数据NoLimit失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
			mResult.add(mTransferData);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.errorMessage = "准备提交数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 准备转储数据
	 * 
	 * @return String
	 */
		/* 统计金额 */
	public boolean conLJFina() {

		String tFlag = "";

		
		//查询本次保全受理下产生所有批改补退费的币种
		String tSQL = "Select distinct Currency "
					+ "  from LJSGetEndorse l "
					+ " where l.EndorsementNo in "
					+ "       (select EdorNo "
					+ "          from LPEdorMain "
					+ "         where EdorAcceptNo = '"+"?mEdorAcceptNo?"+"' "
					+ "        union "
					+ "        select EdorNo from LPGrpEdorMain where EdorAcceptNo = '"+"?mEdorAcceptNo?"+"') "
					+ "   and OtherNo != '000000'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		SSRS curSSRS = mExeSql.execSQL(sqlbv);
		
		for(int c =1;c<=curSSRS.getMaxRow();c++){
			double tGetMoney = 0; // 收钱
			double tPayMoney = 0; // 付钱
			//营改增 add zhangyingfeng 2016-07-08
			//存储总净额 总税额 税率（税率后续因业务等因素变化不统一，汇总税率意义不大，暂取第一条） 
			double tGetNetAM=0;//应付总净额
			double tGetTaxAM=0;//应付总税额
			double tPayNetAM=0;//应收总净额
			double tPayTaxAM=0;//应收总税额
			double tTax=0;//税率  因业务不同税率不统一，取其中一条
			//end zhangyingfeng 2016-07-08
			String tCurrency = curSSRS.GetText(c, 1);
			
			if(tCurrency == null ||tCurrency.equals("")){
				CError.buildErr(this, "批改补退费的币种信息不完整！");
				return false;
			}
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" select * from LJSGetEndorse l  ")
			.append(" where l.endorsementno in ")
			.append(" (select edorno from lpEdormain where edoracceptno = '")
			.append("?mEdorAcceptNo?")
			.append("' union ")
			.append(" select edorno from lpGrpEdormain where edoracceptno = '")
			.append("?mEdorAcceptNo?").append("' ) ")
			.append(" and otherno!='000000'")
			.append(" and currency = '"+"?tCurrency?"+"'");
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(sbSQL.toString());
			sbv.put("mEdorAcceptNo", mEdorAcceptNo);
			sbv.put("tCurrency", tCurrency);
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			mLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(sbv);
			if (tLJSGetEndorseDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
				CError tError = new CError();
				tError.errorMessage = "批改补退费查询失败!";
				mErrors.addOneError(tError);
				return false;
			}
			/* 批改补退费核销转储 */
			if (mLJSGetEndorseSet != null && mLJSGetEndorseSet.size() > 0) {
				
				for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
					
					tFlag = GetOrPayMoney(mLJSGetEndorseSet.get(i));
					if ("".equals(tFlag)) {
						continue;
					}
					tTax=mLJSGetEndorseSet.get(i).getTax();//税率
					if ("Y".equals(tFlag)) // 补费
					{
						double dPayMoney = mLJSGetEndorseSet.get(i).getGetMoney();
//					tPayMoney += dPayMoney;
						tPayMoney = Arith.add(tPayMoney,dPayMoney);
						//营改增 add zhangyingfeng 2016-07-08
						//增加营改增信息存储总净额 总税额 税率    
						tPayNetAM=Arith.add(tPayNetAM,mLJSGetEndorseSet.get(i).getNetAmount());//应收总净额
						tPayTaxAM=Arith.add(tPayTaxAM,mLJSGetEndorseSet.get(i).getTaxAmount());//应收总税额
						//end zhangyingfeng 2016-07-08
					} else if ("N".equals(tFlag)) // 退费
					{
						double dGetMoney = mLJSGetEndorseSet.get(i).getGetMoney();
//					tGetMoney += dGetMoney;
						tGetMoney = Arith.add(tGetMoney,dGetMoney);
						//营改增 add zhangyingfeng 2016-07-08
						//增加营改增信息存储总净额 总税额 税率 
						tGetNetAM=Arith.add(tGetNetAM,mLJSGetEndorseSet.get(i).getNetAmount());//应付总净额
						tGetTaxAM=Arith.add(tGetTaxAM,mLJSGetEndorseSet.get(i).getTaxAmount());//应付总税额
						//end zhangyingfeng 2016-07-08
					}
					
				}
				String sNoLimit = PubFun
				.getNoLimit(mLPEdorAppSchema.getManageCom());
				tPayMoney = Double.parseDouble(mDecimalFormat.format(tPayMoney));
				
//			tPayMoney -= tGetMoney; // 求收付费之和
				tPayMoney = Arith.sub(tPayMoney, tGetMoney);
				//营改增 add zhangyingfeng 
				//计算对应 总净额、总税额之和，税率分开取
				tPayNetAM=Arith.sub(Double.parseDouble(mDecimalFormat.format(tPayNetAM)), tGetNetAM);
				tPayTaxAM=Arith.sub(Double.parseDouble(mDecimalFormat.format(tPayTaxAM)), tGetTaxAM);
				//end zhangyingfeng 2016-07-08
				if (tPayMoney >0) {
					tFlag = "Y";
					if("".equals(mActNoticeNo)){
						mActNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", sNoLimit);
					}
//					mLJSPaySchema = initLJSPaySchema(tPayMoney, mLJSGetEndorseSet
//							.get(1).getManageCom(),tCurrency);
					//营改增 add zhangyingfeng  2016-07-08
					//增加方法参数    总净额、总税额、税率
					mLJSPaySchema = initLJSPaySchema(tPayMoney, mLJSGetEndorseSet
							.get(1).getManageCom(),tCurrency,tPayNetAM,tPayTaxAM,tTax);
					//end zhangyingfeng  2016-07-08
					if (mLJSPaySchema == null) {
						return false;
					}
					map.put(mLJSPaySchema, "DELETE&INSERT");
					
					// 插入收费通知书
					if (!InsertPRT()) {
						return false;
					}
					
				} else if (tPayMoney <= 0) {
//				tGetMoney = 0 - tPayMoney;
					tFlag = "N";
					tGetMoney = Arith.sub(0, tPayMoney);
					//营改增 add zhangyingfeng  2016-07-08
					//对应 总净额、总税额，转正数   ＞0
					tGetNetAM= Arith.sub(0, tPayNetAM);
					tGetTaxAM= Arith.sub(0, tPayTaxAM);
					//end zhangyingfeng  2016-07-08
					if("".equals(mActNoticeNo)){
						
						mActNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", sNoLimit);
					}
//					mLJSGetSchema = initLJSGetSchema(tGetMoney, mLJSGetEndorseSet
//							.get(1).getManageCom(),tCurrency);
					//营改增 add zhangyingfeng  2016-07-08
					//增加方法参数    总净额、总税额
					mLJSGetSchema = initLJSGetSchema(tGetMoney, mLJSGetEndorseSet
							.get(1).getManageCom(),tCurrency,tGetNetAM,tGetTaxAM,tTax);
					//end zhangyingfeng  2016-07-08
					map.put(mLJSGetSchema, "DELETE&INSERT");
				}
				
				sbSQL.setLength(0);
				sbSQL.append(" update LJSGetEndorse set  ")
				.append(" GetNoticeNo = '")
				.append("?mActNoticeNo?")
				.append("' where endorsementno in ")
				.append(
				" (select edorno from lpEdormain where edoracceptno = '")
				.append("?mEdorAcceptNo?")
				.append("' union ")
				.append(
				" select edorno from lpGrpEdormain where edoracceptno = '")
				.append("?mEdorAcceptNo?").append("' ) ")
				.append(" and currency = '"+"?tCurrency?"+"'");
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(sbSQL.toString());
				sbv1.put("mActNoticeNo", mActNoticeNo);
				sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
				sbv1.put("tCurrency", tCurrency);
				map.put(sbv1, "UPDATE");
			}
			if ("Y".equals(tFlag)) {
				// 不需要交费
				mTransferData.setNameAndValue("NeedPayFlag", "Y");
			} else {
				// 需要交费
				mTransferData.setNameAndValue("NeedPayFlag", "N");
			}
			
		}


		return true;
	}

	/**
	 * 通过财务类型来判断是收费还是付费
	 * 
	 * @param LJSGetEndorseSchema
	 *            tLJSGetEndorseSChema
	 * @return Y 补费，N,退费
	 */
	private String GetOrPayMoney(LJSGetEndorseSchema tLJSGetEndorseSChema) {
		String tFlag = "";
		String tFinType = tLJSGetEndorseSChema.getGetFlag();
		if ("1".equals(tFinType)) {
			tFlag = "N";
			return tFlag;
		} else if ("0".equals(tFinType)) {
			tFlag = "Y";
			return tFlag;
		}
		return tFlag;
	}

	/*
	 * private boolean delLJFina() {
	 * if("5".equals(mLPEdorAppSchema.getEdorState())) { String
	 * tDelLJSPay="delete ljspay where otherno='"+mEdorAcceptNo+"' and
	 * othernotype='10'"; String tDelLJSGet="delete ljsget where
	 * otherno='"+mEdorAcceptNo+"' and othernotype='10'"; map.put(tDelLJSPay,
	 * "DELETE"); map.put(tDelLJSGet, "DELETE");
	 *  } return true; }
	 */
	/**
	 * 准备付费总表数据
	 * 
	 * @param aGetMoney
	 * @param sManageCom
	 * @return LJSGetSchema
	 */
	public LJSGetSchema initLJSGetSchema(double aGetMoney, String sManageCom,String sCurrency,double tPayNetAM,double tPayTaxAM,double tGetTax) {
		LJSGetSchema aLJSGetSchema = new LJSGetSchema();

		aLJSGetSchema.setGetNoticeNo(mActNoticeNo);
		aLJSGetSchema.setCurrency(sCurrency);
		aLJSGetSchema.setOtherNo(mEdorAcceptNo);
		aLJSGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
		aLJSGetSchema.setSumGetMoney(aGetMoney);
		aLJSGetSchema.setGetDate(mCurrentDate);
		aLJSGetSchema.setBankCode(mLPEdorAppSchema.getBankCode());
		aLJSGetSchema.setBankAccNo(mLPEdorAppSchema.getBankAccNo());
		aLJSGetSchema.setAccName(mLPEdorAppSchema.getAccName());
		aLJSGetSchema.setPayMode(mLPEdorAppSchema.getGetForm());
		aLJSGetSchema.setOperator(mGlobalInput.Operator);
		aLJSGetSchema.setMakeDate(mCurrentDate);
		aLJSGetSchema.setMakeTime(mCurrentTime);
		aLJSGetSchema.setModifyDate(mCurrentDate);
		aLJSGetSchema.setModifyTime(mCurrentTime);
		aLJSGetSchema.setManageCom(sManageCom);
		aLJSGetSchema.setDrawerID(mLPEdorAppSchema.getPersonID());
		aLJSGetSchema.setDrawerType("0");
		//营改增 add zhangyingfeng 2016-07-08
		//添加总净额 总税额 税率
		aLJSGetSchema.setNetAmount(tPayNetAM);
		aLJSGetSchema.setTaxAmount(tPayTaxAM);
		aLJSGetSchema.setTax(tGetTax);
		//end zhangyingfeng 2016-07-08
		String tsql="";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		tsql="select a.* from lccont a ,lpedormain b where a.contno=b.contno and b.edoracceptno='"
			+"?edoracceptno?"+"' and rownum=1";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE_MYSQL)){
			tsql="select a.* from lccont a ,lpedormain b where a.contno=b.contno and b.edoracceptno='"
					+"?edoracceptno?"+"' limit 0,1";
		}
		sqlbv.sql(tsql);
		sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContSet=tLCContDB.executeQuery(sqlbv);
		
		if(tLCContSet.size()>0)
		{
			aLJSGetSchema.setAgentCode(tLCContSet.get(1).getAgentCode());
			aLJSGetSchema.setAgentGroup(tLCContSet.get(1).getAgentGroup());
			aLJSGetSchema.setAgentCom(tLCContSet.get(1).getAgentCom());
			//增加一些必要值
			aLJSGetSchema.setAppntNo(tLCContSet.get(1).getAppntNo());
			aLJSGetSchema.setInsuredNo(tLCContSet.get(1).getInsuredNo());
			aLJSGetSchema.setSaleChnl(tLCContSet.get(1).getSaleChnl());
		}

		return aLJSGetSchema;
	}

	/**
	 * 准备收费总表数据
	 * 
	 * @param aGetMoney
	 * @param sManageCom
	 * @return LJSPaySchema
	 */
	public LJSPaySchema initLJSPaySchema(double aGetMoney, String sManageCom,String sCurrency,double tPayNetAM,double tPayTaxAM,double tPayTax) {
		LJSPaySchema aLJSPaySchema = new LJSPaySchema();
		aLJSPaySchema.setGetNoticeNo(mActNoticeNo);
		aLJSPaySchema.setCurrency(sCurrency);
		aLJSPaySchema.setOtherNo(mEdorAcceptNo); // 保全受理号
		aLJSPaySchema.setOtherNoType(BqCode.LJ_OtherNoType);
		aLJSPaySchema.setSumDuePayMoney(aGetMoney);

		// ====需求变动，AA和NS，只有主险交费后才可以复核通过，故最早交费日期为复核通过日期===BGN===
		// //如果如果保全项目是AA,NS，最早交费日期设为主险交费对应日
		// String sStartPayDate = getStartPayDate(mEdorAcceptNo);
		// if (sStartPayDate == null)
		// {
		// return null;
		// }
		// aLJSPaySchema.setStartPayDate(sStartPayDate); //最早交费日期
		// ====需求变动，AA和NS，只有主险交费后才可以复核通过，故最早交费日期为复核通过日期===END===

		// ******************疑问：zhangtao 2005-12-13**************************
		// 清偿类的保全项目交费止期是申请日当日还是复核通过当日？
		// 如果当天申请隔天再复核通过呢，允许交费吗？
		// 如果申请日期手工调整为系统日期之前，止期怎么控制？
		// 还是系统暂不考虑这些情况，假定就是当天申请当天复核并且申请日期就是系统日期？
		// ********************************************************************
		/*String sPayDate = getPayEndDate(mEdorAcceptNo, mCurrentDate); // 计算补费止期
*/		
		String sPayDate = mCurrentDate ;//paydate 不在使用，由于流程改变，逾期通过工作流控制。
		if (sPayDate == null) {
			return null;
		}
		aLJSPaySchema.setStartPayDate(mCurrentDate); // 最早交费日期为复核通过日期
		aLJSPaySchema.setPayDate(sPayDate); // 补费止期
		aLJSPaySchema.setBankCode(mLPEdorAppSchema.getBankCode());
		aLJSPaySchema.setBankAccNo(mLPEdorAppSchema.getBankAccNo());
		aLJSPaySchema.setAccName(mLPEdorAppSchema.getAccName());
		aLJSPaySchema.setOperator(mGlobalInput.Operator);
		aLJSPaySchema.setMakeDate(mCurrentDate);
		aLJSPaySchema.setMakeTime(mCurrentTime);
		aLJSPaySchema.setModifyDate(mCurrentDate);
		aLJSPaySchema.setModifyTime(mCurrentTime);
		aLJSPaySchema.setRiskCode("000000");
		aLJSPaySchema.setIDType("0");
		aLJSPaySchema.setIDNo(mLPEdorAppSchema.getPersonID());
		aLJSPaySchema.setManageCom(sManageCom);
		//营改增 add zhangyingfeng 2016-07-08
		//添加总净额 总税额 税率
		aLJSPaySchema.setNetAmount(tPayNetAM);
		aLJSPaySchema.setTaxAmount(tPayTaxAM);
		aLJSPaySchema.setTax(tPayTax);
		//end zhangyingfeng 2016-07-08
		String tsql="";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tsql="select a.* from lccont a ,lpedormain b where a.contno=b.contno and b.edoracceptno='"
					+"?edoracceptno?"+"' and rownum=1";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE_MYSQL)){
			tsql="select a.* from lccont a ,lpedormain b where a.contno=b.contno and b.edoracceptno='"
					+"?edoracceptno?"+"' limit 0,1";
		}
		sqlbv.sql(tsql);
		sqlbv.put("edoracceptno", mLPEdorAppSchema.getEdorAcceptNo());
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContSet=tLCContDB.executeQuery(sqlbv);
		
		if(tLCContSet.size()>0)
		{
			aLJSPaySchema.setAgentCode(tLCContSet.get(1).getAgentCode());
			aLJSPaySchema.setAgentGroup(tLCContSet.get(1).getAgentGroup());
			aLJSPaySchema.setAgentCom(tLCContSet.get(1).getAgentCom());
			//增加一些必要值
			aLJSPaySchema.setAppntNo(tLCContSet.get(1).getAppntNo());
		}
		
		return aLJSPaySchema;
	}

	/**
	 * 查询保全受理信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();
		return true;
	}

	// /**
	// * 确定最早交费日期
	// * @return: boolean
	// */
	// private String getStartPayDate(String sEdorAcceptNo)
	// {
	// //String sStartPayDate;
	// String sql = " select max(edorvalidate) from lpedoritem " +
	// " where trim(edortype) in ('NS', 'AA') " +
	// " and edoracceptno = '" + sEdorAcceptNo + "'";
	// ExeSQL tExeSQL = new ExeSQL();
	// String sEdorValiDate = tExeSQL.getOneValue(sql);
	// if (tExeSQL.mErrors.needDealError())
	// {
	// CError.buildErr(this, "保全项目查询失败!");
	// return null;
	// }
	// if (sEdorValiDate == null || sEdorValiDate.trim().equals(""))
	// {
	// return mCurrentDate; //没有这两个保全项目
	// }
	// else
	// {
	// if (PubFun.calInterval(sEdorValiDate, mCurrentDate, "D") > 0)
	// {
	// return mCurrentDate;
	// }
	// else
	// {
	// return sEdorValiDate;
	// }
	//
	// // //保全项目包含AA,NS，最早交费日期设为主险交费对应日
	// // sql = " select * from lcpol where polno = mainpolno " +
	// // " and contno = '" + mLPEdorAppSchema.getOtherNo() + "' and rownum =
	// 1";
	// // LCPolDB tLCPolDB = new LCPolDB();
	// // LCPolSet tLCPolSet = new LCPolSet();
	// // tLCPolSet = tLCPolDB.executeQuery(sql);
	// // if (tLCPolDB.mErrors.needDealError())
	// // {
	// // CError.buildErr(this, "保单主险信息查询失败!");
	// // return null;
	// // }
	// // if (tLCPolSet == null || tLCPolSet.size() < 1)
	// // {
	// // CError.buildErr(this, "没有查到保单主险信息!");
	// // return null;
	// // }
	// // if (tLCPolSet.get(1).getPayIntv() == 0) //主险趸交
	// // {
	// // return mCurrentDate;
	// // }
	// // else
	// // {
	// // sStartPayDate = tLCPolSet.get(1).getPaytoDate();
	// // }
	//
	// }
	// }

	/**
	 * 计算补费止期（走算法描述）
	 * 
	 * @param sEdorAcceptNo
	 *            保全受理号
	 * @param sApproveDate
	 *            复核通过日期
	 * @return String 补费止期
	 */
	private String getPayEndDate(String sEdorAcceptNo, String sApproveDate) {
		// 查询补费止期计算代码
		String sql = "select * from lmedorcal where caltype='OverDate'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv);
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "补费止期计算代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			CError.buildErr(this, "补费止期计算代码查询失败!");
			return null;
		}
		String sFinaCalCode = tLMEdorCalSet.get(1).getCalCode();

		// 计算补费止期
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sFinaCalCode);
		tCalculator.addBasicFactor("EdorAcceptNo", sEdorAcceptNo); // 保全受理号
		tCalculator.addBasicFactor("ApproveDate", sApproveDate); // 复核通过日期
		String sPayEndDate = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "补费止期计算失败!");
			return null;
		}

		if (sPayEndDate == null || sPayEndDate.trim().equals("")) {
			CError.buildErr(this, "补费止期计算失败!");
			return null;
		}

		return sPayEndDate;
	}

	/**
	 * 往打印管理表插入收费通知书记录
	 * 
	 * @return boolean
	 */
	private boolean InsertPRT() {
		LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSchema mmLOPRTManagerSchema;
		try {
			String Code = PrintManagerBL.CODE_PEdorPayInfo;// 个险保全收费通知书
			if ("2".equals(mLPEdorAppSchema.getOtherNoType())
					|| "4".equals(mLPEdorAppSchema.getOtherNoType())) {
				Code = PrintManagerBL.CODE_GEdorPayInfo;// 团体保全收费通知书
			}

			// =================插入保全补费通知书打印记录==========================
			mmLOPRTManagerSchema = new LOPRTManagerSchema();
			//String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", Code); // 生成打印流水号
			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setOtherNo(mEdorAcceptNo);
			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDORACCEPT); // 保全受理号
			mmLOPRTManagerSchema.setCode(Code); // 打印类型
			mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom()); // 管理机构
			mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			mmLOPRTManagerSchema.setMakeDate(mCurrentDate);
			mmLOPRTManagerSchema.setMakeTime(mCurrentTime);
			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			//保全二次核保核保结论通过后重新汇总财务需要删除
			String tLOPRTManager_Delete_sql = "delete from LOPRTManager where LOPRTManager.otherno='"
				+"?mEdorAcceptNo?"+"' and OtherNoType = '"+"?OtherNoType?"+"' and code='"+"?Code?"+"'"
				+" and StateFlag = '0' and PatchFlag='0' ";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tLOPRTManager_Delete_sql);
			sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
			sbv1.put("OtherNoType", PrintManagerBL.ONT_EDORACCEPT);
			sbv1.put("Code", Code);
			map.put(sbv1, "DELETE");

			// =================插入保全补费收据打印记录===========================
			// if ("1".equals(mLPEdorAppSchema.getOtherNoType()) ||
			// "3".equals(mLPEdorAppSchema.getOtherNoType()))
			// { //个险保全插入保全补费收据打印记录
			// LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			// tLPEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
			// LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
			// if (tLPEdorMainDB.mErrors.needDealError())
			// {
			// CError.buildErr(this, "保全申请批单查询失败!");
			// return false;
			// }
			// if (tLPEdorMainSet == null || tLPEdorMainSet.size() < 1)
			// {
			// CError.buildErr(this, "保全受理下没有申请批单!");
			// return false;
			// }
			// for (int i = 1; i <= tLPEdorMainSet.size(); i++)
			// {
			// LCContDB tLCContDB = new LCContDB();
			// tLCContDB.setContNo(tLPEdorMainSet.get(i).getContNo());
			// LCContSet tLCContSet = tLCContDB.query();
			// if (tLCContDB.mErrors.needDealError())
			// {
			// CError.buildErr(this, "保单查询失败!");
			// return false;
			// }
			// if (tLCContSet == null || tLCContSet.size() < 1)
			// {
			// CError.buildErr(this, "没有保单信息!");
			// return false;
			// }
			// LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			// tLPEdorItemDB.setEdorNo(tLPEdorMainSet.get(i).getEdorNo());
			// LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			// if (tLPEdorItemDB.mErrors.needDealError())
			// {
			// CError.buildErr(this, "保全项目查询失败!");
			// return false;
			// }
			// if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1)
			// {
			// CError.buildErr(this, "没有保全项目!");
			// return false;
			// }
			// mmLOPRTManagerSchema = new LOPRTManagerSchema();
			// strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			// sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); //生成打印流水号
			// mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			// mmLOPRTManagerSchema.setOtherNo(tLPEdorMainSet.get(i).getEdorNo());
			// mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDOR);
			// //批单号
			// mmLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorReceipt);
			// //打印类型
			// mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom());
			// //保全管理机构
			// mmLOPRTManagerSchema.setAgentCode(tLCContSet.get(1).getAgentCode());
			// //保单代理人编码
			// mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			// mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			//
			// mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); //前台打印
			// mmLOPRTManagerSchema.setStateFlag("0"); //打印状态
			// mmLOPRTManagerSchema.setPatchFlag("0"); //补打标志
			// mmLOPRTManagerSchema.setMakeDate(mCurrentDate);
			// mmLOPRTManagerSchema.setMakeTime(mCurrentTime);
			//
			// mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			// mmLOPRTManagerSchema.setStandbyFlag1(mEdorAcceptNo);
			// mmLOPRTManagerSchema.setStandbyFlag2(tLPEdorMainSet.get(i).getContNo());
			// mmLOPRTManagerSchema.setStandbyFlag3(tLPEdorItemSet.get(1).getEdorType());
			//
			// mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			// break; //默认一个保全申请只有一个保全项目
			// }
			// map.put(mLOPRTManagerSet, "INSERT");
			// }
			// else
			// {
			// //团险保全插入保全补费收据打印记录 暂时先不做，待刘荣晓收据打印那边确定后再做 2006-04-10 zhangtao
			// //团体保全发票数据产生，Add By QianLy on 2006-11-16
			// LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
			// tLPGrpEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
			// LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
			// if (tLPGrpEdorMainSet.mErrors.needDealError())
			// {
			// CError.buildErr(this, "保全申请批单查询失败!");
			// return false;
			// }
			// if (tLPGrpEdorMainSet == null || tLPGrpEdorMainSet.size() < 1)
			// {
			// CError.buildErr(this, "保全受理下没有申请批单!");
			// return false;
			// }
			// for (int i = 1; i <= tLPGrpEdorMainSet.size(); i++)
			// {
			// LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			// tLCGrpContDB.setGrpContNo(tLPGrpEdorMainSet.get(i).getGrpContNo());
			// LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();
			// if (tLCGrpContSet.mErrors.needDealError())
			// {
			// CError.buildErr(this, "团体保单查询失败!");
			// return false;
			// }
			// if (tLCGrpContSet == null || tLCGrpContSet.size() < 1)
			// {
			// CError.buildErr(this, "没有团体保单信息!");
			// return false;
			// }
			// LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			// tLPGrpEdorItemDB.setEdorNo(tLPGrpEdorMainSet.get(i).getEdorNo());
			// LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
			// if (tLPGrpEdorItemSet.mErrors.needDealError())
			// {
			// CError.buildErr(this, "团体保全项目查询失败!");
			// return false;
			// }
			// if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1)
			// {
			// CError.buildErr(this, "没有团体保全项目!");
			// return false;
			// }
			// mmLOPRTManagerSchema = new LOPRTManagerSchema();
			// strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			// sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); //生成打印流水号
			// mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			// mmLOPRTManagerSchema.setOtherNo(tLPGrpEdorMainSet.get(i).getEdorNo());
			// mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDOR);
			// //批单号
			// mmLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GEdorInvoice);
			// //打印类型BQ36
			// mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom());
			// //保全管理机构
			// mmLOPRTManagerSchema.setAgentCode(tLCGrpContSet.get(1).getAgentCode());
			// //保单代理人编码
			// mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			// mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			//
			// mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); //前台打印
			// mmLOPRTManagerSchema.setStateFlag("0"); //打印状态
			// mmLOPRTManagerSchema.setPatchFlag("0"); //补打标志
			// mmLOPRTManagerSchema.setMakeDate(mCurrentDate);
			// mmLOPRTManagerSchema.setMakeTime(mCurrentTime);
			//
			// mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			// mmLOPRTManagerSchema.setStandbyFlag1(mEdorAcceptNo);
			// mmLOPRTManagerSchema.setStandbyFlag2(tLPGrpEdorMainSet.get(i).getGrpContNo());
			// mmLOPRTManagerSchema.setStandbyFlag3(tLPGrpEdorItemSet.get(1).getEdorType());
			//
			// mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			// break; //默认一个保全申请只有一个保全项目
			// }
			map.put(mLOPRTManagerSet, "INSERT");
			// }
		} catch (Exception e) {
			CError.buildErr(this, "插入保全收费通知书失败!");
			return false;
		}

		return true;
	}
}
