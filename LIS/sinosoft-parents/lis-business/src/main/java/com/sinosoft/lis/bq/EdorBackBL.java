/*
 * @(#)EdorDetailBL.java 2005-06-28 Copyright 2005 Sinosoft Co. Ltd. All rights reserved. All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJFIGetDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全回退公用处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao pst
 * @version：1.0   2.0
 * @CreateDate：2005-09-17,2008-10-17
 */
public class EdorBackBL {
private static Logger logger = Logger.getLogger(EdorBackBL.class);
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
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet bLPEdorItemSet = new LPEdorItemSet();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet(); // 批改补退费记录
	private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet(); // 批改补退费对冲记录
	private LJAGetSet mLJAGetSet = new LJAGetSet(); // 付费总表对冲记录
	private LJAPaySet mLJAPaySet = new LJAPaySet(); // 付费总表对冲记录 
	private LJFIGetSet mLJFIGetSet = new LJFIGetSet();
	// 回退补退费金额
	private double dGetMoney = 0.0;

	// 财务冲账的实付号
	private String mActuGetNo = "";
	
	// 财务冲账的实付号
	private String mPayNo = "";

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public EdorBackBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
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
	 * @return boolean
	 */
	private boolean dealData() {
		if (mOperate.equals("Detail")) // 回退明细处理
		{
			for (int i = 1; i <= bLPEdorItemSet.size(); i++) {
				// 校验保全申请当前处理状态
				if (!checkEdorState(bLPEdorItemSet.get(i))) {
					return false;
				}

				// 调用各保全项目对应的回退明细处理类
				if (!calPEdorXXBackDetailBL(bLPEdorItemSet.get(i), "Detail")) {
					return false;
				}

				// 财务数据合计
				if (!createGetEndorse(bLPEdorItemSet.get(i))) {
					return false;
				}
			}
			if (mLJSGetEndorseSet != null && mLJSGetEndorseSet.size() > 0) {
				map.put(mLJSGetEndorseSet, "DELETE&INSERT");
			}
			if (mLJSGetDrawSet != null && mLJSGetDrawSet.size() > 0) {
				map.put(mLJSGetDrawSet, "DELETE&INSERT");
			}
			if (mLJSPayPersonSet != null && mLJSPayPersonSet.size() > 0) {
				map.put(mLJSPayPersonSet, "DELETE&INSERT");
			}
		} else if (mOperate.equals("Confirm")) // 回退确认生效处理
		{
			for (int i = 1; i <= bLPEdorItemSet.size(); i++) {
				// 调用各保全项目对应的回退确认生效处理类
				if (!calPEdorXXBackDetailBL(bLPEdorItemSet.get(i), "Confirm")) {
					return false;
				}

				// 批改补退费对冲 add by zhangtao 2007-05-11
				if (!createLJAGetEndorse(bLPEdorItemSet.get(i))) {
					return false;
				}
			}

			// 财务总表对冲 add by zhangtao 2007-05-11
			if (!createLJAGet()) {
				return false;
			}

			if (mLJAGetEndorseSet != null && mLJAGetEndorseSet.size() > 0) {
				map.put(mLJAGetEndorseSet, "DELETE&INSERT");
			}

			if (mLJAGetEndorseSet != null && mLJAGetEndorseSet.size() > 0) {
				map.put(mLJAGetEndorseSet, "DELETE&INSERT");
			}
			// 财务总表对冲 add by gaoht 2007-05-11
			if (mLJAGetSet != null && mLJAGetSet.size() > 0) {
				map.put(mLJAGetSet, "DELETE&INSERT");
			}

			if (mLJFIGetSet != null && mLJFIGetSet.size() > 0) {
				map.put(mLJFIGetSet, "DELETE&INSERT");
			}
		}
		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {

		// 只取出共通类用到的公用数据
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		bLPEdorItemSet = // 需要回退的保全项目
		(LPEdorItemSet) mInputData.getObjectByObjectName("LPEdorItemSet", 0);
		return true;
	}

	/**
	 * 校验需要回退的保全项目
	 * 
	 * @return: boolean
	 */
	private boolean checkEdorState(LPEdorItemSchema bLPEdorItemSchema) {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(bLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(bLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(bLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(bLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(bLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(bLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询保全批改项目信息失败!"));
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "没有保全批改项目信息!");
			return false;
		}
		bLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));

		String sEdorState = bLPEdorItemSchema.getEdorState();

		if (sEdorState == null || !sEdorState.equals("0")) {
			CError.buildErr(this, "保全尚未生效，不允许做回退!");
		}

		return true;
	}

	/**
	 * 调用保全项目对应的明细处理类
	 * 
	 * @param bLPEdorItemSchema
	 * @param sCalType
	 * @return: boolean
	 */
	private boolean calPEdorXXBackDetailBL(LPEdorItemSchema bLPEdorItemSchema,
			String sCalType) {
		// 调用各保全项目对应的明细处理类
		String sEdorType = bLPEdorItemSchema.getEdorType();
		try {
			Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
					+ sEdorType + "Back" + sCalType + "BL");
			EdorBack tEdorBack = (EdorBack) tClass.newInstance();

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(bLPEdorItemSchema);

			if (!tEdorBack.submitData(tVData, mOperate)) {
				mErrors.copyAllErrors(tEdorBack.mErrors);
				return false;
			} else {
				VData rVData = tEdorBack.getResult();
				MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "获取保全项目" + sEdorType
							+ "的保全回退明细结果时失败!");
					return false;
				} else {
					map.add(tMap);
				}
			}
		} catch (ClassNotFoundException BLEX) {
			if (sCalType.equals("Confirm")) {
				CError.buildErr(this, "找不到保全项目" + sEdorType + "对应的回退确认处理类!");
				return false;
			} else {
				logger.debug("=== 保全项目" + sEdorType + "没有对应的回退明细处理类 ===");
			}
		} catch (Exception ex) {
			CError.buildErr(this, "保全项目" + sEdorType + "回退处理失败!");
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 根据需要回退的原保全补退费创建该次补退费
	 * 
	 * @param bLPEdorItemSchema
	 * @return boolean
	 */
	private boolean createGetEndorse(LPEdorItemSchema bLPEdorItemSchema) {
		if (mLPEdorItemSchema.getStandbyFlag2() != null
				&& mLPEdorItemSchema.getStandbyFlag2().trim().equals("1")) { // 银保通对冲，不用创建应收 add by zhangtao 2007-05-11
			return true;
		}

		String sActuPayGetNo = "";
		
		if(!getLPEdorApp(bLPEdorItemSchema))
		{
			CError.buildErr(this, "保全补费查询失败!");
			return false;
		}		
		// 查询本次保全实收数据
		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
		tLJAPayDB.setOtherNoType("10");
		LJAPaySet tLJAPaySet = tLJAPayDB.query();
		if (tLJAPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全补费查询失败!");
			return false;
		}
		if (tLJAPaySet != null && tLJAPaySet.size() > 0) {
			sActuPayGetNo = tLJAPaySet.get(1).getPayNo();
			LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
			tLJAPayPersonDB.setPayNo(sActuPayGetNo);
			LJAPayPersonSet tLJAPayPersonSet = tLJAPayPersonDB.query();
			if (tLJAPayPersonDB.mErrors.needDealError()) {
				CError.buildErr(this, "保全个人交费查询失败!");
				return false;
			}
			if (tLJAPayPersonSet != null && tLJAPayPersonSet.size() > 0) {
				// 根据个人交费记录创建批改补退费记录
				for (int k = 1; k <= tLJAPayPersonSet.size(); k++) {
					createGetEndorseByPayPerson(tLJAPayPersonSet.get(k));
					dGetMoney -= tLJAPayPersonSet.get(k).getSumActuPayMoney();
				}
			}
		} else {
			// 查询本次保全实付数据
			LJAGetDB tLJAGetDB = new LJAGetDB();
			//用归档号去查
			tLJAGetDB.setOtherNo(mLPEdorAppSchema.getEdorConfNo());
			tLJAGetDB.setOtherNoType("10");
			LJAGetSet tLJAGetSet = tLJAGetDB.query();
			if (tLJAGetDB.mErrors.needDealError()) {
				CError.buildErr(this, "保全付费查询失败!");
				return false;
			}
			if (tLJAGetSet != null && tLJAGetSet.size() > 0) {
				//处理红利领取
				sActuPayGetNo = tLJAGetSet.get(1).getActuGetNo();
				LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
				tLJABonusGetDB.setActuGetNo(sActuPayGetNo);
				LJABonusGetSet tLJABonusGetSet = tLJABonusGetDB.query();
				if (tLJABonusGetDB.mErrors.needDealError()) {
					CError.buildErr(this, "保全个人领取查询失败!");
					return false;
				}
				if (tLJABonusGetSet != null && tLJABonusGetSet.size() > 0) {
					// 根据领取记录创建批改补退费记录
					for (int k = 1; k <= tLJABonusGetSet.size(); k++) {
						createGetEndorseByBonusGet(tLJABonusGetSet.get(k), k);
						dGetMoney += tLJABonusGetSet.get(k).getGetMoney();
					}
				}
				
				
				//处理生存金领取
				LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
				tLJAGetDrawDB.setActuGetNo(sActuPayGetNo);
				LJAGetDrawSet tLJAGetDrawSet = tLJAGetDrawDB.query();
				if (tLJAGetDrawDB.mErrors.needDealError()) {
					CError.buildErr(this, "保全个人领取查询失败!");
					return false;
				}
				if (tLJAGetDrawSet != null && tLJAGetDrawSet.size() > 0) {
					// 根据领取记录创建批改补退费记录
					for (int k = 1; k <= tLJAGetDrawSet.size(); k++) {
						createGetEndorseByGetDraw(tLJAGetDrawSet.get(k), k);
						dGetMoney += tLJAGetDrawSet.get(k).getGetMoney();
					}
				}
			} else {
				// 该次保全没有补退费
				logger.debug("=== 该次保全没有补退费 ===");
				return true;
			}
		}

		Reflections tRef = new Reflections();
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
//		tLJAGetEndorseDB.setEndorsementNo(bLPEdorItemSchema.getEdorNo());
//		tLJAGetEndorseDB.setFeeOperationType(bLPEdorItemSchema.getEdorType());
		//modify zbx 20110909
		String temp_sql = "select ENDORSEMENTNO actugetno,ENDORSEMENTNO,FEEOPERATIONTYPE,FEEFINATYPE,GRPCONTNO,CONTNO,GRPPOLNO,POLNO,OTHERNO,OTHERNOTYPE,DUTYCODE,PAYPLANCODE,APPNTNO,INSUREDNO,GETNOTICENO,GETDATE,ENTERACCDATE,GETCONFIRMDATE,sum(GETMONEY) getmoney,KINDCODE,RISKCODE,RISKVERSION,MANAGECOM,AGENTCOM,AGENTTYPE,AGENTCODE,AGENTGROUP,GRPNAME,HANDLER,APPROVEDATE,APPROVETIME,POLTYPE,APPROVECODE,OPERATOR,'' SERIALNO,MODIFYDATE,MAKEDATE,MAKETIME,GETFLAG,MODIFYTIME,SUBFEEOPERATIONTYPE,OPERSTATE,currency,PeriodPrem,ComCode,ModifyOperator,NetAmount,TaxAmount,Tax"
			+" from ljagetendorse where EndorsementNo='"+"?EndorsementNo?"+"' and FeeOperationType='"+"?FeeOperationType?"+"' "
			+" group by  ENDORSEMENTNO,FEEOPERATIONTYPE,FEEFINATYPE,GRPCONTNO,CONTNO,GRPPOLNO,POLNO,OTHERNO,OTHERNOTYPE,DUTYCODE,PAYPLANCODE,APPNTNO,INSUREDNO,GETNOTICENO,GETDATE,ENTERACCDATE,GETCONFIRMDATE,KINDCODE,RISKCODE,RISKVERSION,MANAGECOM,AGENTCOM,AGENTTYPE,AGENTCODE,AGENTGROUP,GRPNAME,HANDLER,APPROVEDATE,APPROVETIME,POLTYPE,APPROVECODE,OPERATOR,MODIFYDATE,MAKEDATE,MAKETIME,GETFLAG,MODIFYTIME,SUBFEEOPERATIONTYPE,OPERSTATE,currency,PeriodPrem,ComCode,ModifyOperator,NetAmount,TaxAmount,Tax"
			;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(temp_sql);
		sqlbv.put("EndorsementNo", bLPEdorItemSchema.getEdorNo());
		sqlbv.put("FeeOperationType", bLPEdorItemSchema.getEdorType());
		LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.executeQuery(sqlbv);
		if (tLJAGetEndorseDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全批改补退费查询失败!");
			return false;
		}
		if (tLJAGetEndorseSet != null && tLJAGetEndorseSet.size() > 0) {
			LJSGetEndorseSchema tLJSGetEndorseSchema;
			lable:for (int j = 1; j <= tLJAGetEndorseSet.size(); j++) {
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tRef
						.transFields(tLJSGetEndorseSchema, tLJAGetEndorseSet
								.get(j));
//				for(int k=1;k<=mLJSGetEndorseSet.size();k++)
//				{
////					mLJSGetEndorseSet.get(k).getFeeOperationType().equals(tLJSGetEndorseSchema.getFeeOperationType())
//					if(mLJSGetEndorseSet.get(k).getFeeFinaType().equals(tLJSGetEndorseSchema.getFeeFinaType())
//							&&mLJSGetEndorseSet.get(k).getPolNo().equals(tLJSGetEndorseSchema.getPolNo())
//							&&mLJSGetEndorseSet.get(k).getOtherNo().equals(tLJSGetEndorseSchema.getOtherNo())
//							&&mLJSGetEndorseSet.get(k).getDutyCode().equals(tLJSGetEndorseSchema.getDutyCode())
//							&&mLJSGetEndorseSet.get(k).getPayPlanCode().equals(tLJSGetEndorseSchema.getPayPlanCode())
//							&&mLJSGetEndorseSet.get(k).getSubFeeOperationType().equals(tLJSGetEndorseSchema.getSubFeeOperationType()))
//					{
//						mLJSGetEndorseSet.get(k).setGetMoney(mLJSGetEndorseSet.get(k).getGetMoney()+(-tLJSGetEndorseSchema.getGetMoney()));
//						dGetMoney += -tLJSGetEndorseSchema.getGetMoney();
//						continue lable;
//					}
//				}
				tLJSGetEndorseSchema.setGetMoney(-tLJSGetEndorseSchema
						.getGetMoney()); 

				tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
						.getEdorNo());
				tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema
						.getEdorNo());
				tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
						.getEdorType());
				tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema
						.getEdorValiDate());
				tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
				tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
				tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
				tLJSGetEndorseSchema.setMakeDate(mCurrentDate);
				tLJSGetEndorseSchema.setMakeTime(mCurrentTime);
				tLJSGetEndorseSchema.setModifyDate(mCurrentDate);
				tLJSGetEndorseSchema.setModifyTime(mCurrentTime);
				if(tLJSGetEndorseSchema.getGetFlag().equals("0"))
				{
					dGetMoney +=-tLJSGetEndorseSchema.getGetMoney();
				}
				else
				{
					dGetMoney +=tLJSGetEndorseSchema.getGetMoney();
				}
				//营改增 add zhangyingfeng 2016-07-11
				//价税分离 计算器
				TaxCalculator.calBySchema(tLJSGetEndorseSchema);
				//end zhangyingfeng 2016-07-11
				mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			}
		}

		return true;
	}

	/**
	 * 财务总表对冲（银保通回退） add by zhangtao 2007-05-11
	 * 
	 * @return boolean
	 */
	private boolean createLJAGet() {
		if (mLPEdorItemSchema.getStandbyFlag2() == null
				|| !mLPEdorItemSchema.getStandbyFlag2().trim().equals("1")) {
			return true;
		}

		// 银保通财务对冲 add by zhangtao 2007-05-11
		Reflections tRef = new Reflections();

		// 查询本次保全实收数据
		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setOtherNo(mLPEdorItemSchema.getStandbyFlag1().trim());
		tLJAPayDB.setOtherNoType("10");
		LJAPaySet tLJAPaySet = tLJAPayDB.query();
		if (tLJAPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全补费查询失败!");
			return false;
		}
		if (tLJAPaySet != null && tLJAPaySet.size() > 0) {
			// 生成付费总表的对冲记录
			LJAPaySchema tLJAPaySchema = new LJAPaySchema();
			tRef.transFields(tLJAPaySchema, tLJAPaySet.get(1));
			tLJAPaySchema.setGetNoticeNo(tLJAPaySet.get(1).getGetNoticeNo());
			tLJAPaySchema.setPayNo(mPayNo);
			tLJAPaySchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLJAPaySchema.setOtherNoType(BqCode.LJ_OtherNoType);
			tLJAPaySchema.setOperator(mGlobalInput.Operator);
			tLJAPaySchema.setMakeDate(mCurrentDate);
			tLJAPaySchema.setMakeTime(mCurrentTime);
			tLJAPaySchema.setModifyDate(mCurrentDate);
			tLJAPaySchema.setModifyTime(mCurrentTime);
			tLJAPaySchema.setManageCom(mLPEdorItemSchema.getManageCom());
			tLJAPaySchema.setEnterAccDate(mCurrentDate);
			tLJAPaySchema.setConfDate(mCurrentDate);
			tLJAPaySchema.setSumActuPayMoney(-tLJAPaySet.get(1).getSumActuPayMoney());

			LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();

			LJFIGetDB tLJFIGetDB = new LJFIGetDB();
			tLJFIGetDB.setActuGetNo(tLJAPaySet.get(1).getPayNo());
			tLJFIGetDB.setPayMode(tLJAPaySet.get(1).getPayTypeFlag());  //??
			if (!tLJFIGetDB.getInfo()) {
				CError.buildErr(this, "保全付费查询失败!");
				return false;
			}
			tLJFIGetSchema = tLJFIGetDB.getSchema();
			tLJFIGetSchema.setActuGetNo(mActuGetNo);
			tLJFIGetSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLJFIGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
			tLJFIGetSchema.setOperator(mGlobalInput.Operator);
			tLJFIGetSchema.setMakeDate(mCurrentDate);
			tLJFIGetSchema.setMakeTime(mCurrentTime);
			tLJFIGetSchema.setModifyDate(mCurrentDate);
			tLJFIGetSchema.setModifyTime(mCurrentTime);
			tLJFIGetSchema.setManageCom(mLPEdorItemSchema.getManageCom());
			tLJFIGetSchema.setShouldDate(mCurrentDate);
			tLJFIGetSchema.setEnterAccDate(mCurrentDate);
			tLJFIGetSchema.setConfDate(mCurrentDate);
			tLJFIGetSchema.setGetMoney(-tLJAPaySet.get(1).getSumActuPayMoney());
			mLJFIGetSet.add(tLJFIGetSchema);
			mLJAPaySet.add(tLJAPaySchema);
		}

		// 查询本次保全实付数据
		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetDB.setOtherNo(mLPEdorItemSchema.getStandbyFlag1().trim());
		tLJAGetDB.setOtherNoType("10");
		LJAGetSet tLJAGetSet = tLJAGetDB.query();
		if (tLJAGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全付费查询失败!");
			return false;
		}
		if (tLJAGetSet != null && tLJAGetSet.size() > 0) {
			// 生成付费总表的对冲记录
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tRef.transFields(tLJAGetSchema, tLJAGetSet.get(1));
			tLJAGetSchema.setGetNoticeNo(tLJAGetSet.get(1).getGetNoticeNo());
			tLJAGetSchema.setActuGetNo(mActuGetNo);
			tLJAGetSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLJAGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
			tLJAGetSchema.setOperator(mGlobalInput.Operator);
			tLJAGetSchema.setMakeDate(mCurrentDate);
			tLJAGetSchema.setMakeTime(mCurrentTime);
			tLJAGetSchema.setModifyDate(mCurrentDate);
			tLJAGetSchema.setModifyTime(mCurrentTime);
			tLJAGetSchema.setManageCom(mLPEdorItemSchema.getManageCom());
			tLJAGetSchema.setShouldDate(mCurrentDate);
			tLJAGetSchema.setEnterAccDate(mCurrentDate);
			tLJAGetSchema.setConfDate(mCurrentDate);
			tLJAGetSchema.setSumGetMoney(-tLJAGetSet.get(1).getSumGetMoney());
			//营改增 add zhangyingfeng 2016-07-13
			//除了总额， 还要 净额 总税额 税率
			tLJAGetSchema.setNetAmount(-tLJAGetSet.get(1).getNetAmount());
			tLJAGetSchema.setTaxAmount(-tLJAGetSet.get(1).getTaxAmount());
			tLJAGetSchema.setTax(-tLJAGetSet.get(1).getTax());
			//end zhangyingfeng 2016-07-13

			// 置财务到账日期 gaoht
			// mLJFIGetSet
			LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();

			LJFIGetDB tLJFIGetDB = new LJFIGetDB();
			tLJFIGetDB.setActuGetNo(tLJAGetSet.get(1).getActuGetNo());
			tLJFIGetDB.setPayMode(tLJAGetSet.get(1).getPayMode());
			if (!tLJFIGetDB.getInfo()) {
				CError.buildErr(this, "保全付费查询失败!");
				return false;
			}
			tLJFIGetSchema = tLJFIGetDB.getSchema();
			tLJFIGetSchema.setActuGetNo(mActuGetNo);
			tLJFIGetSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLJFIGetSchema.setOtherNoType(BqCode.LJ_OtherNoType);
			tLJFIGetSchema.setOperator(mGlobalInput.Operator);
			tLJFIGetSchema.setMakeDate(mCurrentDate);
			tLJFIGetSchema.setMakeTime(mCurrentTime);
			tLJFIGetSchema.setModifyDate(mCurrentDate);
			tLJFIGetSchema.setModifyTime(mCurrentTime);
			tLJFIGetSchema.setManageCom(mLPEdorItemSchema.getManageCom());
			tLJFIGetSchema.setShouldDate(mCurrentDate);
			tLJFIGetSchema.setEnterAccDate(mCurrentDate);
			tLJFIGetSchema.setConfDate(mCurrentDate);
			tLJFIGetSchema.setGetMoney(-tLJAGetSet.get(1).getSumGetMoney());
			mLJFIGetSet.add(tLJFIGetSchema);
			mLJAGetSet.add(tLJAGetSchema);
		}
		return true;
	}

	/**
	 * 财务批改补退费对冲（银保通回退） add by zhangtao 2007-05-11
	 * 
	 * @param bLPEdorItemSchema
	 * @return boolean
	 */
	private boolean createLJAGetEndorse(LPEdorItemSchema bLPEdorItemSchema) {
		if (mLPEdorItemSchema.getStandbyFlag2() == null
				|| !mLPEdorItemSchema.getStandbyFlag2().trim().equals("1")) {
			return true;
		}
		// 如果是银保通对冲，则创建财务对冲
		String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
		mActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		
		mPayNo=PubFun1.CreateMaxNo("PAYNO", tLimit);
		// 财务批改补退费对冲
		Reflections tRef = new Reflections();
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		tLJAGetEndorseDB.setEndorsementNo(bLPEdorItemSchema.getEdorNo());
		tLJAGetEndorseDB.setFeeOperationType(bLPEdorItemSchema.getEdorType());
		LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.query();
		if (tLJAGetEndorseDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全批改补退费查询失败!");
			return false;
		}
		if (tLJAGetEndorseSet != null && tLJAGetEndorseSet.size() > 0) {
			LJAGetEndorseSchema tLJAGetEndorseSchema;
			for (int j = 1; j <= tLJAGetEndorseSet.size(); j++) {
				tLJAGetEndorseSchema = new LJAGetEndorseSchema();
				tRef
						.transFields(tLJAGetEndorseSchema, tLJAGetEndorseSet
								.get(j));

				tLJAGetEndorseSchema.setGetMoney(tLJAGetEndorseSchema
						.getGetMoney());

				tLJAGetEndorseSchema.setGetNoticeNo(tLJAGetEndorseSet.get(j)
						.getGetNoticeNo());
				tLJAGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema
						.getEdorNo());
				tLJAGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
						.getEdorType());
				tLJAGetEndorseSchema.setGetDate(mLPEdorItemSchema
						.getEdorValiDate());
				tLJAGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
				tLJAGetEndorseSchema.setOtherNoType("3"); // 保全给付
				tLJAGetEndorseSchema.setOperator(mGlobalInput.Operator);
				tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
				tLJAGetEndorseSchema.setMakeTime(mCurrentTime);
				tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
				tLJAGetEndorseSchema.setModifyTime(mCurrentTime);
				// 置财务到账日期 gaoht
				tLJAGetEndorseSchema.setActuGetNo(mActuGetNo);// need
				// confirmed by
				// zhangtao
				tLJAGetEndorseSchema.setEnterAccDate(mCurrentDate);
				tLJAGetEndorseSchema.setGetDate(mCurrentDate);
				//营改增 add zhangyingfeng 2016-07-11
				//价税分离 计算器
				TaxCalculator.calBySchema(tLJAGetEndorseSchema);
				//end zhangyingfeng 2016-07-11
				mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
			}
		}

		return true;
	}

	/**
	 * 根据个人交费记录创建批改补退费记录
	 * 
	 * @param bLJAPayPersonSchema
	 * @return boolean
	 */
	private void createGetEndorseByPayPerson(
			LJAPayPersonSchema bLJAPayPersonSchema) {
		Reflections tRef = new Reflections();

		LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
		tRef.transFields(tLJSPayPersonSchema, bLJAPayPersonSchema);
		tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		tLJSPayPersonSchema.setSumDuePayMoney(-bLJAPayPersonSchema
				.getSumDuePayMoney());
		tLJSPayPersonSchema.setSumActuPayMoney(-bLJAPayPersonSchema
				.getSumActuPayMoney());
		tLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());
		tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
		tLJSPayPersonSchema.setMakeDate(mCurrentDate);
		tLJSPayPersonSchema.setMakeTime(mCurrentTime);
		tLJSPayPersonSchema.setModifyDate(mCurrentDate);
		tLJSPayPersonSchema.setModifyTime(mCurrentTime);
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSPayPersonSchema);
		//end zhangyingfeng 2016-07-11
		mLJSPayPersonSet.add(tLJSPayPersonSchema);

		LJSGetEndorseSchema rLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tRef.transFields(rLJSGetEndorseSchema, bLJAPayPersonSchema);
		setGetEndorseInfo(rLJSGetEndorseSchema);
		rLJSGetEndorseSchema.setGetMoney(bLJAPayPersonSchema
				.getSumActuPayMoney()); 
		rLJSGetEndorseSchema.setFeeFinaType("BF");
		// 对保全收费进行回退，置付费标志
		rLJSGetEndorseSchema.setGetFlag("1");
		rLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem
				);
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(rLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-11
		mLJSGetEndorseSet.add(rLJSGetEndorseSchema);
	}

	/**
	 * 根据领取记录创建批改补退费记录
	 * 
	 * @param bLJAGetDrawSchema
	 * @return
	 */
	private void createGetEndorseByBonusGet(LJABonusGetSchema bLJABonusGetSchema,
			int nSerialNo) {
		Reflections tRef = new Reflections();

		LJSGetEndorseSchema rLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tRef.transFields(rLJSGetEndorseSchema, bLJABonusGetSchema);
		setGetEndorseInfo(rLJSGetEndorseSchema);
		rLJSGetEndorseSchema.setGetFlag("1");
		rLJSGetEndorseSchema.setGetMoney(-bLJABonusGetSchema.getGetMoney());
		rLJSGetEndorseSchema.setFeeFinaType(bLJABonusGetSchema.getFeeFinaType());
		rLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_BonusCashValue
				);
		rLJSGetEndorseSchema.setOtherNo(bLJABonusGetSchema.getContNo());
		rLJSGetEndorseSchema.setOtherNoType("7");
		rLJSGetEndorseSchema.setPolNo(bLJABonusGetSchema.getPolNo());
		rLJSGetEndorseSchema.setDutyCode("000000");
		rLJSGetEndorseSchema.setPayPlanCode("00000000");
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(rLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-11
		mLJSGetEndorseSet.add(rLJSGetEndorseSchema);
	}

	
	/**
	 * 根据领取记录创建批改补退费记录
	 * 
	 * @param bLJAGetDrawSchema
	 * @return
	 */
	private void createGetEndorseByGetDraw(LJAGetDrawSchema bLJAGetDrawSchema,
			int nSerialNo) {
		Reflections tRef = new Reflections();

		LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
		tRef.transFields(tLJSGetDrawSchema, bLJAGetDrawSchema);
		tLJSGetDrawSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetDrawSchema.setGetMoney(-bLJAGetDrawSchema.getGetMoney());
		tLJSGetDrawSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		// XinYQ added on 2007-04-11 : 修改核销日期和财务到账日期
		tLJSGetDrawSchema.setConfDate("");
		tLJSGetDrawSchema.setEnterAccDate("");
		tLJSGetDrawSchema.setOperator(mGlobalInput.Operator);
		tLJSGetDrawSchema.setMakeDate(mCurrentDate);
		tLJSGetDrawSchema.setMakeTime(mCurrentTime);
		tLJSGetDrawSchema.setModifyDate(mCurrentDate);
		tLJSGetDrawSchema.setModifyTime(mCurrentTime);
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSGetDrawSchema);
		//end zhangyingfeng 2016-07-11
		mLJSGetDrawSet.add(tLJSGetDrawSchema);

		LJSGetEndorseSchema rLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tRef.transFields(rLJSGetEndorseSchema, bLJAGetDrawSchema);
		setGetEndorseInfo(rLJSGetEndorseSchema);
		//对客户领取进行回退，收付费标志置为“1”
		rLJSGetEndorseSchema.setGetFlag("1");
		rLJSGetEndorseSchema.setGetMoney(-bLJAGetDrawSchema.getGetMoney());
		rLJSGetEndorseSchema.setFeeFinaType(bLJAGetDrawSchema.getFeeFinaType());
		// XinYQ added on 2007-04-04 : 保证主键不重复
		rLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_GetDraw
				+ String.valueOf(nSerialNo));
		rLJSGetEndorseSchema.setOtherNo(tLJSGetDrawSchema.getContNo());
		rLJSGetEndorseSchema.setOtherNoType("7");
		rLJSGetEndorseSchema.setPolNo(tLJSGetDrawSchema.getPolNo());
		rLJSGetEndorseSchema.setDutyCode(tLJSGetDrawSchema.getDutyCode());
		rLJSGetEndorseSchema.setPayPlanCode("00000000");
		//营改增 add zhangyingfeng 2016-07-11
		//价税分离 计算器
		TaxCalculator.calBySchema(rLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-11
		mLJSGetEndorseSet.add(rLJSGetEndorseSchema);
	}
	
	private void setGetEndorseInfo(LJSGetEndorseSchema rLJSGetEndorseSchema) {
		rLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		rLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		rLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		rLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		rLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
		rLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		rLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		rLJSGetEndorseSchema.setMakeDate(mCurrentDate);
		rLJSGetEndorseSchema.setMakeTime(mCurrentTime);
		rLJSGetEndorseSchema.setModifyDate(mCurrentDate);
		rLJSGetEndorseSchema.setModifyTime(mCurrentTime);
	}

	/**
	 * 查询保全受理信息
	 * 
	 * @return boolean
	 */
	private boolean getLPEdorApp(LPEdorItemSchema bLPEdorItemSchema) {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(bLPEdorItemSchema.getEdorAcceptNo());
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
	/**
	 * 返回补退费金额
	 * 
	 * @return double
	 */
	public double getGetMoney() {
		return dGetMoney;
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
		} catch (Exception ex) {
			CError tError = new CError();
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
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

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

	}
}
