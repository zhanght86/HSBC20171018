/*
 * @(#)PEdorCTDetailBLF.java	2005-07-07
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LMDutyPayRelaDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPBonusPolDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOBonusPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LOPolAfterDealSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPBonusPolSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LMDutyPayRelaSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPBonusPolSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保单犹豫期退保变更保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：PST
 * @version：1.0
 * @CreateDate：2008-12-07
 */
public class PEdorCTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorCTConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务对象 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LCContStateSet mInstLCContStateSet = new LCContStateSet();

	private LCContStateSet mUpdLCContStateSet = new LCContStateSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	private Reflections ref = new Reflections();

	public PEdorCTConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("after getInputData....");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("after dealData...");

		if(!dealacc())//为投连增加的一个函数
       {
           return false;
       }
        logger.debug("after dealacc...");
	        
		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("after prepareOutputData....");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 */
	private boolean getInputData() {
		try {

			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			// 全局变量
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面准备的数据进行逻辑处理
	 */
	private boolean dealData() {
		Reflections tRef = new Reflections();



		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单信息失败!");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() < 1) {
			CError.buildErr(this, "没有查到退保的保单!");
			return false;
		}
		LCContSchema tLCContSchema = tLCContSet.get(1);

		LPContSchema tLPContSchema = new LPContSchema();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		LPContSet tLPContSet = tLPContDB.query();
		if (tLPContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单信息失败!");
			return false;
		}
		if (tLPContSet != null && tLPContSet.size() > 0) // 合同退保
		{
			tLPContSchema = tLPContSet.get(1);
			ref.transFields(tLCContSchema, tLPContSchema);
			// tLCContSchema.setOperator(mGlobalInput.Operator);
			tLCContSchema.setModifyDate(mCurrentDate);
			tLCContSchema.setModifyTime(mCurrentTime);

			//整单退保
			if("4".equals(tLPContSchema.getAppFlag()))
			{
				if (!updLCContState(mLPEdorItemSchema.getContNo(), "000000",
				"000000")) {
			          return false;
		               }
			}

			map.put(tLCContSchema, "DELETE&INSERT");

			tLPContSchema.setAppFlag("1");
			tLPContSchema.setModifyDate(mCurrentDate);
			tLPContSchema.setModifyTime(mCurrentTime);
			map.put(tLPContSchema, "DELETE&INSERT");

		}

		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		LPPolSet tLPolSet = tLPPolDB.query();
		if (tLPPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单险种信息失败!");
			return false;
		}
		if (tLPolSet != null && tLPolSet.size() > 0) // 险种退保
		{

			LPPolSet updLPPolSet = new LPPolSet();
			LCPolSet updLCPolSet = new LCPolSet();
			LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
			LPInsureAccSet updLPInsureAccSet = new LPInsureAccSet();
			LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
			LPInsureAccClassSet updLPInsureAccClassSet = new LPInsureAccClassSet();
			LCInsureAccTraceSet insLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LPInsureAccTraceSet insLPInsureAccTraceSet = new LPInsureAccTraceSet();

			LCInsureAccFeeSet updLCInsureAccFeeSet = new LCInsureAccFeeSet();
			LPInsureAccFeeSet updLPInsureAccFeeSet = new LPInsureAccFeeSet();
			LCInsureAccClassFeeSet updLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
			LPInsureAccClassFeeSet updLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
			LCInsureAccFeeTraceSet insLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
			LPInsureAccFeeTraceSet insLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
			
			LPBonusPolSet updLPBonusPolSet = new LPBonusPolSet();
			LOBonusPolSet updLOBonusPolSet = new LOBonusPolSet();

			LCPolDB tLCPolDB;
			LCPolSet tLCPolSet;
			for (int i = 1; i <= tLPolSet.size(); i++) {

				LPPolSchema tLPPolSchema = tLPolSet.get(i);
				LCPolSchema tLCPolSchema = new LCPolSchema();
				ref.transFields(tLCPolSchema, tLPolSet.get(i));
				// tLCPolSchema.setOperator(mGlobalInput.Operator);
				tLCPolSchema.setModifyDate(mCurrentDate);
				tLCPolSchema.setModifyTime(mCurrentTime);
				tLCPolSchema.setAppFlag("4"); 
				updLCPolSet.add(tLCPolSchema);

				tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
				tLCPolSet = tLCPolDB.query();
				if (tLPPolDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询保全个人保单险种信息失败!");
					return false;
				}
				if (tLCPolSet == null || tLCPolSet.size() < 1) {
					CError.buildErr(this, "没有查到退保的保单险种!");
					return false;
				}
				ref.transFields(tLPPolSchema, tLCPolSet.get(1));
				tLPPolSchema.setModifyDate(mCurrentDate);
				tLPPolSchema.setModifyTime(mCurrentTime);
				updLPPolSet.add(tLPolSet.get(i));

//				if (!updLCContState(tLCPolSchema.getContNo(), tLCPolSchema
//						.getInsuredNo(), tLCPolSchema.getPolNo())) {
//					return false;
//				}

				//处理红利表	
//				LPBonusPolDB tLPBonusPolDB = new LPBonusPolDB();
//				tLPBonusPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				tLPBonusPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
//				tLPBonusPolDB.setPolNo(tLCPolSchema.getPolNo());
//				LPBonusPolSet tLPBonusPolSet = tLPBonusPolDB.query();
//
//				if (tLPBonusPolDB.mErrors.needDealError()) {
//					CError.buildErr(this, "查询保全保险帐户信息失败!");
//					return false;
//				}
//				if (tLPBonusPolSet != null && tLPBonusPolSet.size() > 0) {
//
//					for (int m = 1; m <= tLPBonusPolSet.size(); m++) {
//						LOBonusPolSchema tLOBonusPolSchema = new LOBonusPolSchema();
//						tRef.transFields(tLOBonusPolSchema, tLPBonusPolSet
//								.get(m));
//						tLOBonusPolSchema.setModifyDate(mCurrentDate);
//						tLOBonusPolSchema.setModifyTime(mCurrentTime);
//						updLOBonusPolSet.add(tLOBonusPolSchema);
//
//						LOBonusPolDB tLOBonusPolDB = new LOBonusPolDB();
//						tLOBonusPolDB.setPolNo(tLOBonusPolSchema.getPolNo());
//						tLOBonusPolDB.setFiscalYear(tLOBonusPolSchema.getFiscalYear());
//						LOBonusPolSet tLOBonusPolSet = tLOBonusPolDB.query();
//						if (tLOBonusPolDB.mErrors.needDealError()) {
//							CError.buildErr(this, "查询保全保险帐户信息失败!");
//							return false;
//						}
//						if (tLOBonusPolSet == null
//								|| tLOBonusPolSet.size() < 1) {
//							CError.buildErr(this, "没有查到保险帐户信息!");
//							return false;
//						}
//						LPBonusPolSchema tLPBonusPolSchema = new LPBonusPolSchema();
//						tRef.transFields(tLPBonusPolSchema, tLOBonusPolSet
//								.get(1));
//						tLPBonusPolSchema.setEdorNo(mLPEdorItemSchema
//								.getEdorNo());
//						tLPBonusPolSchema.setEdorType(mLPEdorItemSchema
//								.getEdorType());
//						tLPBonusPolSchema.setModifyDate(mCurrentDate);
//						tLPBonusPolSchema.setModifyTime(mCurrentTime);
//						updLPBonusPolSet.add(tLPBonusPolSchema);
//					}
//				}
				
				if(IsTLRisk(tLPolSet.get(i).getRiskCode())){
	                   // 在此不要做任何处理
	            }else{
					LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
					tLPInsureAccDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsureAccDB.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
					LPInsureAccSet tLPInsureAccSet = tLPInsureAccDB.query();
	
					if (tLPInsureAccDB.mErrors.needDealError()) {
						CError.buildErr(this, "查询保全保险帐户信息失败!");
						return false;
					}
					if (tLPInsureAccSet != null && tLPInsureAccSet.size() > 0) {
	
						for (int m = 1; m <= tLPInsureAccSet.size(); m++) {
							LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
							tRef.transFields(tLCInsureAccSchema, tLPInsureAccSet.get(m));
							tLCInsureAccSchema.setModifyDate(mCurrentDate);
							tLCInsureAccSchema.setModifyTime(mCurrentTime);
							updLCInsureAccSet.add(tLCInsureAccSchema);
	
							LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
							tLCInsureAccDB.setPolNo(tLCInsureAccSchema.getPolNo());
							tLCInsureAccDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
							LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
							if (tLCInsureAccDB.mErrors.needDealError()) {
								CError.buildErr(this, "查询保全保险帐户信息失败!");
								return false;
							}
							if (tLCInsureAccSet == null|| tLCInsureAccSet.size() < 1) {
								CError.buildErr(this, "没有查到保险帐户信息!");
								return false;
							}
							LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
							tRef.transFields(tLPInsureAccSchema, tLCInsureAccSet.get(1));
							tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
							tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
							tLPInsureAccSchema.setModifyDate(mCurrentDate);
							tLPInsureAccSchema.setModifyTime(mCurrentTime);
							updLPInsureAccSet.add(tLPInsureAccSchema);
						}
					}
					LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
					tLPInsureAccClassDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsureAccClassDB.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
					LPInsureAccClassSet tLPInsureAccClassSet = tLPInsureAccClassDB.query();
					if (tLPInsureAccClassDB.mErrors.needDealError()) {
						CError.buildErr(this, "查询保全保险帐户分类信息失败!");
						return false;
					} else if (tLPInsureAccClassSet != null&& tLPInsureAccClassSet.size() > 0) {
	
						LCInsureAccClassSchema tLCInsureAccClassSchema;
						LPInsureAccClassSchema tLPInsureAccClassSchema;
						for (int n = 1; n <= tLPInsureAccClassSet.size(); n++) {
							tLCInsureAccClassSchema = new LCInsureAccClassSchema();
							tRef.transFields(tLCInsureAccClassSchema,tLPInsureAccClassSet.get(n));
							updLCInsureAccClassSet.add(tLCInsureAccClassSchema);
							LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
							tLCInsureAccClassDB.setPolNo(tLCInsureAccClassSchema.getPolNo());
							tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccClassSchema.getInsuAccNo());
							tLCInsureAccClassDB.setPayPlanCode(tLCInsureAccClassSchema.getPayPlanCode());
							tLCInsureAccClassDB.setOtherNo(tLCInsureAccClassSchema.getOtherNo());
							tLCInsureAccClassDB.setAccAscription(tLCInsureAccClassSchema.getAccAscription());
							LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
							if (tLCInsureAccClassDB.mErrors.needDealError()) {
								CError.buildErr(this, "查询保全保险帐户分类信息失败!");
								return false;
							}
							if (tLCInsureAccClassSet == null|| tLCInsureAccClassSet.size() < 1) {
								CError.buildErr(this, "没有查到保险帐户分类信息!");
								return false;
							}
							tLPInsureAccClassSchema = new LPInsureAccClassSchema();
							tRef.transFields(tLPInsureAccClassSchema,tLCInsureAccClassSet.get(1));
							tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
							tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
							tLPInsureAccClassSchema.setModifyDate(mCurrentDate);
							tLPInsureAccClassSchema.setModifyTime(mCurrentTime);
	
							updLPInsureAccClassSet.add(tLPInsureAccClassSchema);
						}
					}
	
					LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
					tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPInsureAccTraceDB.setPolNo(tLCPolSchema.getPolNo());
					LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
					if (tLPInsureAccTraceDB.mErrors.needDealError()) {
						CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
						return false;
					}
					if (tLPInsureAccTraceSet != null
							&& tLPInsureAccTraceSet.size() > 0) {
						LCInsureAccTraceSchema tLCInsureAccTraceSchema;
	//					LPInsureAccTraceSchema tLPInsureAccTraceSchema;
						for (int k = 1; k <= tLPInsureAccTraceSet.size(); k++) {
							tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
	//						tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
							tRef.transFields(tLCInsureAccTraceSchema,tLPInsureAccTraceSet.get(k));
							tLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
							tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
							//add 添加此处时由于财务提数需要，财务提取红利应付用makedate
							tLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
							insLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
	
	//						LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
	//
	//						tLCInsureAccTraceDB.setSerialNo(tLCInsureAccTraceSchema
	//								.getSerialNo());
	//						LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB
	//								.query();
	//						if (tLCInsureAccTraceDB.mErrors.needDealError()) {
	//							CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
	//							return false;
	//						}
	//						tRef.transFields(tLPInsureAccTraceSchema,tLCInsureAccTraceSet.get(1));
	//						tLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
	//						tLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
	//						insLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
						}
					}
				}

				//帐户费用信息
				LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
				tLPInsureAccFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPInsureAccFeeDB.setPolNo(tLCPolSchema.getPolNo());
				LPInsureAccFeeSet tLPInsureAccFeeSet = tLPInsureAccFeeDB
						.query();
				if (tLPInsureAccFeeDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询保全保险帐户信息失败!");
					return false;
				}
				if (tLPInsureAccFeeSet != null && tLPInsureAccFeeSet.size() > 0) {
					LCInsureAccFeeSchema tLCInsureAccFeeSchema;
					LPInsureAccFeeSchema tLPInsureAccFeeSchema;
					for (int m = 1; m <= tLPInsureAccFeeSet.size(); m++) {
						tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
						tRef.transFields(tLCInsureAccFeeSchema,
								tLPInsureAccFeeSet.get(m));
						updLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);

						LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
						tLCInsureAccFeeDB.setPolNo(tLCInsureAccFeeSchema
								.getPolNo());
						tLCInsureAccFeeDB.setInsuAccNo(tLCInsureAccFeeSchema
								.getInsuAccNo());
						LCInsureAccFeeSet tLCInsureAccFeeSet = tLCInsureAccFeeDB
								.query();
						if (tLCInsureAccFeeDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询保全保险帐户信息失败!");
							return false;
						}
						if (tLCInsureAccFeeSet == null
								|| tLCInsureAccFeeSet.size() < 1) {
							CError.buildErr(this, "没有查到保险帐户信息!");
							return false;
						}
						tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
						tRef.transFields(tLPInsureAccFeeSchema,
								tLCInsureAccFeeSet.get(1));
						tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						updLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);

						LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
						tLPInsureAccClassFeeDB.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						tLPInsureAccClassFeeDB.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPInsureAccClassFeeDB
								.setPolNo(tLCPolSchema.getPolNo());
						tLPInsureAccClassFeeDB
								.setInsuAccNo(tLCInsureAccFeeSchema
										.getInsuAccNo());
						LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB
								.query();
						if (tLPInsureAccClassFeeDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询保全保险帐户分类信息失败!");
							return false;
						} else if (tLPInsureAccClassFeeSet != null
								&& tLPInsureAccClassFeeSet.size() > 0) {

							LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema;
							LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema;
							for (int n = 1; n <= tLPInsureAccClassFeeSet.size(); n++) {
								tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
								tRef.transFields(tLCInsureAccClassFeeSchema,
										tLPInsureAccClassFeeSet.get(n));
								tLCInsureAccClassFeeSchema.setModifyDate(mCurrentDate);
								tLCInsureAccClassFeeSchema.setModifyTime(mCurrentTime);
								updLCInsureAccClassFeeSet
										.add(tLCInsureAccClassFeeSchema);

								LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
								tLCInsureAccClassFeeDB
										.setPolNo(tLCInsureAccClassFeeSchema
												.getPolNo());
								tLCInsureAccClassFeeDB
										.setInsuAccNo(tLCInsureAccClassFeeSchema
												.getInsuAccNo());
								tLCInsureAccClassFeeDB
										.setPayPlanCode(tLCInsureAccClassFeeSchema
												.getPayPlanCode());
								tLCInsureAccClassFeeDB
										.setOtherNo(tLCInsureAccClassFeeSchema
												.getOtherNo());
								tLCInsureAccClassFeeDB
										.setAccAscription(tLCInsureAccClassFeeSchema
												.getAccAscription());
								tLCInsureAccClassFeeDB.setFeeCode(tLCInsureAccClassFeeSchema.getFeeCode());
								LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB
										.query();
								if (tLCInsureAccClassFeeDB.mErrors
										.needDealError()) {
									CError.buildErr(this, "查询保全保险帐户分类信息失败!");
									return false;
								}
								if (tLCInsureAccClassFeeSet == null
										|| tLCInsureAccClassFeeSet.size() < 1) {
									CError.buildErr(this, "没有查到保险帐户分类信息!");
									return false;
								}
								tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
								tRef.transFields(tLPInsureAccClassFeeSchema,
										tLCInsureAccClassFeeSet.get(1));
								tLPInsureAccClassFeeSchema
										.setEdorNo(mLPEdorItemSchema
												.getEdorNo());
								tLPInsureAccClassFeeSchema
										.setEdorType(mLPEdorItemSchema
												.getEdorType());
								updLPInsureAccClassFeeSet
										.add(tLPInsureAccClassFeeSchema);
							}
						}
						
						
						LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
						tLPInsureAccFeeTraceDB.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						tLPInsureAccFeeTraceDB.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPInsureAccFeeTraceDB
								.setPolNo(tLCPolSchema.getPolNo());
						tLPInsureAccFeeTraceDB
								.setInsuAccNo(tLCInsureAccFeeSchema
										.getInsuAccNo());
						LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB
								.query();
						if (tLPInsureAccFeeTraceDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
							return false;
						} else if (tLPInsureAccFeeTraceSet != null
								&& tLPInsureAccFeeTraceSet.size() > 0) {
							LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema;
//							LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema;
							for (int k = 1; k <= tLPInsureAccFeeTraceSet.size(); k++) {
								tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
//								tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
								tRef.transFields(tLCInsureAccFeeTraceSchema,
										tLPInsureAccFeeTraceSet.get(k));

								tLCInsureAccFeeTraceSchema
										.setModifyDate(mCurrentDate);
								tLCInsureAccFeeTraceSchema
										.setModifyTime(mCurrentTime);
								insLCInsureAccFeeTraceSet
										.add(tLCInsureAccFeeTraceSchema);

//								LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB = new LCInsureAccFeeTraceDB();
//
//								tLCInsureAccFeeTraceDB
//										.setSerialNo(tLCInsureAccFeeTraceSchema
//												.getSerialNo());
//								LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = tLCInsureAccFeeTraceDB
//										.query();
//								if (tLCInsureAccFeeTraceDB.mErrors
//										.needDealError()) {
//									CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
//									return false;
//								}
//								if (tLCInsureAccFeeTraceSet == null
//										|| tLCInsureAccFeeTraceSet.size() < 1) {
//									CError.buildErr(this, "没有查到保险帐户计价履历信息!");
//									return false;
//								}
//								tRef.transFields(tLPInsureAccFeeTraceSchema,
//										tLCInsureAccFeeTraceSet.get(1));
//								tLPInsureAccFeeTraceSchema
//										.setModifyDate(mCurrentDate);
//								tLPInsureAccFeeTraceSchema
//										.setModifyTime(mCurrentTime);
//								insLPInsureAccFeeTraceSet
//										.add(tLPInsureAccFeeTraceSchema);
							}
						}
					}
				}

			}
			LPContStateDB tLPContStateDB = new LPContStateDB();
			tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
			LPContStateSet tLPContStateSet = tLPContStateDB.query();
			if (tLPContStateSet != null && tLPContStateSet.size() > 0) {
				map.put(tLPContStateSet, "DELETE");
				LCContStateSet tLCContStateSet = new LCContStateSet();
				for (int i = 1; i <= tLPContStateSet.size(); i++) {
					LCContStateSchema tLCContStateSchema = new LCContStateSchema();
					tRef.transFields(tLCContStateSchema,
							tLPContStateSet.get(i));
					tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(mCurrentDate);
					tLCContStateSchema.setModifyTime(mCurrentTime);
					tLCContStateSet.add(tLCContStateSchema);
				}
				map.put(tLCContStateSet, "DELETE&INSERT");
			}
			map.put(updLCPolSet, "DELETE&INSERT");
			map.put(updLPPolSet, "DELETE&INSERT");

			map.put(updLCInsureAccSet, "DELETE&INSERT");
			map.put(updLPInsureAccSet, "DELETE&INSERT");
			
//			map.put(updLOBonusPolSet, "DELETE&INSERT");
//			map.put(updLPBonusPolSet, "DELETE&INSERT");

			map.put(updLCInsureAccClassSet, "DELETE&INSERT");
			map.put(updLPInsureAccClassSet, "DELETE&INSERT");

			map.put(insLCInsureAccTraceSet, "DELETE&INSERT");
			
			//轨迹之类的表只需要插入，无需备份，如果回退直接删除新插入的表即可
//			map.put(insLPInsureAccTraceSet, "DELETE&INSERT");

			map.put(updLCInsureAccFeeSet, "DELETE&INSERT");
			map.put(updLPInsureAccFeeSet, "DELETE&INSERT");

			map.put(updLCInsureAccClassFeeSet, "DELETE&INSERT");
			map.put(updLPInsureAccClassFeeSet, "DELETE&INSERT");

			map.put(insLCInsureAccFeeTraceSet, "DELETE&INSERT");
//			轨迹之类的表只需要插入，无需备份，如果回退直接删除新插入的表即可
//			map.put(insLPInsureAccFeeTraceSet, "DELETE&INSERT");
				
	}
		return true;
	}

	/**
	 * 更新保单状态表
	 */
	private boolean updLCContState(String sContNo, String sInsuredNo,
			String sPolNo) {

		String sql = " select * from lccontstate where trim(statetype) = 'Terminate' "
				+ " and enddate is null  and contno = '?sContNo?" + 
				// "' and insuredno = '" + sInsuredNo +
				"' and polno = '?sPolNo?'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("sContNo", sContNo);
        sqlbv.put("sPolNo", sPolNo);
		LCContStateSchema tLCContStateSchema = new LCContStateSchema();

		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		if (tLCContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单状态查询失败!");
			return false;
		}
		if (tLCContStateSet == null || tLCContStateSet.size() < 1) {
			// 没有记录
			tLCContStateSchema.setContNo(sContNo);
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo(sPolNo);
			tLCContStateSchema.setStateType("Terminate");
			tLCContStateSchema.setStateReason("02");
			tLCContStateSchema.setState("1");
			tLCContStateSchema
					.setStartDate(mLPEdorItemSchema.getEdorValiDate());
			// tLCContStateSchema.setEndDate();
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(mCurrentDate);
			tLCContStateSchema.setMakeTime(mCurrentTime);
			tLCContStateSchema.setModifyDate(mCurrentDate);
			tLCContStateSchema.setModifyTime(mCurrentTime);
			mInstLCContStateSet.add(tLCContStateSchema);
		} else {
			String sEndDate = PubFun.calDate(mLPEdorItemSchema
					.getEdorValiDate(), -1, "D", null);
			tLCContStateSchema = tLCContStateSet.get(1);
			tLCContStateSchema.setEndDate(sEndDate);
			tLCContStateSchema.setModifyDate(mCurrentDate);
			tLCContStateSchema.setModifyTime(mCurrentTime);
			mUpdLCContStateSet.add(tLCContStateSchema);

			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setContNo(sContNo);
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo(sPolNo);
			tLCContStateSchema.setStateType("Terminate");
			tLCContStateSchema.setStateReason("02");
			tLCContStateSchema.setState("1");
			tLCContStateSchema
					.setStartDate(mLPEdorItemSchema.getEdorValiDate());
			// tLCContStateSchema.setEndDate();
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(mCurrentDate);
			tLCContStateSchema.setMakeTime(mCurrentTime);
			tLCContStateSchema.setModifyDate(mCurrentDate);
			tLCContStateSchema.setModifyTime(mCurrentTime);
			mInstLCContStateSet.add(tLCContStateSchema);
		}

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
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}

		return true;
	}
	
    private boolean  dealacc()
    {
             String tContNo = mLPEdorItemSchema.getContNo();
             String tEdorNo = mLPEdorItemSchema.getEdorNo();
             String tEdorType = mLPEdorItemSchema.getEdorType();
             String CurrentDate = PubFun.getCurrentDate();
             String CurrentTime = PubFun.getCurrentTime();
             String SQL="select risktype3 from Lmriskapp where riskcode in ( select riskcode from LPPol where contno='?tContNo?' and EdorNo='?tEdorNo?' and EdorType='?tEdorType?' ) and risktype3='3'";
             logger.debug(SQL);
             SQLwithBindVariables sqlbv=new SQLwithBindVariables();
             sqlbv.sql(SQL);
             sqlbv.put("tContNo", tContNo);
             sqlbv.put("tEdorNo", tEdorNo);
             sqlbv.put("tEdorType", tEdorType);
           ExeSQL tExeSQL=new ExeSQL();
           SSRS tSSRS=new SSRS();
           tSSRS=tExeSQL.execSQL(sqlbv);
          if(tSSRS!=null&&tSSRS.MaxRow>0){//退保险种中有投连险
              //处理后续处理接口表
                LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
                  LCInsureAccTraceDB tLCInsureAccTraceDB=new LCInsureAccTraceDB();
             SQL="select * from  LCInsureAccTrace where polno= (select polno from Lcpol where contno='?tContNo?' and riskcode in (select riskcode from Lmriskapp where  risktype3='3'))  and moneytype='BF' and state='1'";
             sqlbv=new SQLwithBindVariables();
             sqlbv.sql(SQL);
             sqlbv.put("tContNo", tContNo);
             tLCInsureAccTraceSet=tLCInsureAccTraceDB.executeQuery(sqlbv);
                  logger.debug(SQL);
             if(  tLCInsureAccTraceSet.size()>0){//如果资金已经进入帐户，则实付表在批处理的时候生成。
                 LOPolAfterDealSchema mLOPolAfterDealSchema = new LOPolAfterDealSchema();
                 mLOPolAfterDealSchema.setAccAlterNo(tEdorNo);
                 mLOPolAfterDealSchema.setAccAlterType("3");
                 mLOPolAfterDealSchema.setBusyType(tEdorType);
                 mLOPolAfterDealSchema.setManageCom(mLPEdorItemSchema.getManageCom());
                 mLOPolAfterDealSchema.setState("0");
                 mLOPolAfterDealSchema.setContNo(mLPEdorItemSchema.getContNo());
                 mLOPolAfterDealSchema.setConfDate(mLPEdorItemSchema.getEdorValiDate());
                 mLOPolAfterDealSchema.setMakeDate(CurrentDate);
                 mLOPolAfterDealSchema.setMakeTime(CurrentTime);
                 mLOPolAfterDealSchema.setModifyDate(CurrentDate);
                 mLOPolAfterDealSchema.setModifyTime(CurrentTime);
                 map.put(mLOPolAfterDealSchema, "INSERT");
                 String Data=mLPEdorItemSchema.getEdorValiDate();
                 logger.debug(Data);
                 if(!dealAcc(tLCInsureAccTraceSet.get(1).getPolNo() ,mLPEdorItemSchema.getEdorValiDate()))
                 {
                      return false;
                 }
                 //删除LJAGET和ljagetendorse的纪录，等到批处理的后续处理 时才生成准确的财务数据
               String JaSQL="select * from ljagetendorse where ContNo='?ContNo?' and Endorsementno='?Endorsementno?' and FeeOperationType='?FeeOperationType?' and riskcode=(select riskcode from Lcpol where polno='?polno?')";
               LJAGetEndorseSet tLJAGetEndorseSet=new LJAGetEndorseSet();
               LJAGetEndorseDB tLJAGetEndorseDB=new LJAGetEndorseDB();
               logger.debug(JaSQL);
               SQLwithBindVariables sbv=new SQLwithBindVariables();
               sbv.sql(JaSQL);
               sbv.put("ContNo", mLPEdorItemSchema.getContNo());
               sbv.put("Endorsementno", mLPEdorItemSchema.getEdorNo());
               sbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
               sbv.put("polno", tLCInsureAccTraceSet.get(1).getPolNo());
               tLJAGetEndorseSet=tLJAGetEndorseDB.executeQuery(sbv);
                 if(tLJAGetEndorseSet.size()>0)
               {
                    JaSQL = "select * from LJAGET where ActuGetNo='?ActuGetNo?'";
                    LJAGetSet tLJAGetSet=new LJAGetSet();
                    LJAGetDB tLJAGetDB=new LJAGetDB();
                    sbv=new SQLwithBindVariables();
                    sbv.sql(JaSQL);
                    sbv.put("ActuGetNo", tLJAGetEndorseSet.get(1).getActuGetNo());
                    tLJAGetSet=tLJAGetDB.executeQuery(sbv);
                    map.put(tLJAGetSet, "DELETE");
                    map.put(tLJAGetEndorseSet, "DELETE");
               }
             }else{//如果资金没有进入帐户，则应该保留实付表,更新Acc和Class表

                     //为提数的方便把payplancode字段在ljagetendorse的纪录中写入。
                String JaSQL="select * from ljagetendorse where ContNo='?ContNo?' and Endorsementno='?Endorsementno?'and FeeOperationType='?FeeOperationType?'and riskcode in (select riskcode from Lmriskapp where  risktype3='3')";
               // LJAGetEndorseSet toldLJAGetEndorseSet=new   LJAGetEndorseSet();
                 LJAGetEndorseSet tnewLJAGetEndorseSet=new LJAGetEndorseSet();
              //  LJAGetEndorseDB tLJAGetEndorseDB=new LJAGetEndorseDB();
                logger.debug(JaSQL);
              //  toldLJAGetEndorseSet=tLJAGetEndorseDB.executeQuery(JaSQL);
                LJAGetEndorseDB  ttLJAGetEndorseDB=new LJAGetEndorseDB();
                SQLwithBindVariables sbv=new SQLwithBindVariables();
                sbv.sql(JaSQL);
                sbv.put("ContNo", mLPEdorItemSchema.getContNo());
                sbv.put("Endorsementno", mLPEdorItemSchema.getEdorNo());
                sbv.put("", mLPEdorItemSchema.getEdorType());
                tnewLJAGetEndorseSet=ttLJAGetEndorseDB.executeQuery(sbv);
                  if(tnewLJAGetEndorseSet.size()>0)
                {
                   for(int i=1;i<=tnewLJAGetEndorseSet.size();i++){
                    LMDutyPayRelaSet tLMDutyPayRelaSet = new LMDutyPayRelaSet();
                   LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
                   String dutytoPaySQL = "select * from LMDutyPayRela where dutycode='?dutycode?'";
                   SQLwithBindVariables sbv1=new SQLwithBindVariables();
                   sbv1.sql(dutytoPaySQL);
                   sbv1.put("dutycode", tnewLJAGetEndorseSet.get(i).getDutyCode());
                     tLMDutyPayRelaSet = tLMDutyPayRelaDB.executeQuery(sbv1);
                     if(tLMDutyPayRelaSet.size()>0){
                        tnewLJAGetEndorseSet.get(i).setPayPlanCode(tLMDutyPayRelaSet.get(1).getPayPlanCode());
                     }
                   }
                }
                  SQLwithBindVariables sbv2=new SQLwithBindVariables();
                  sbv2.sql("delete from ljagetendorse where ContNo='?ContNo?' and Endorsementno='?Endorsementno?'and FeeOperationType='?FeeOperationType?' and PayPlanCode='0' and riskcode in (select riskcode from Lmriskapp where  risktype3='3')");
                  sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
                  sbv2.put("Endorsementno", mLPEdorItemSchema.getEdorNo());
                  sbv2.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
                map.put(sbv2, "DELETE");
                    map.put(tnewLJAGetEndorseSet, "DELETE&INSERT");
                 SQL="select * from Lcinsureacc where polno=(select polno from Lcpol where contno='?tContNo?' and riskcode in (select riskcode from Lmriskapp where  risktype3='3'))";
                 sqlbv=new SQLwithBindVariables();
                 sqlbv.sql(SQL);
                 sqlbv.put("tContNo", tContNo);
                 LCInsureAccDB tLCInsureAccDB=new LCInsureAccDB();
                LCInsureAccSet  tLCInsureAccSet=new LCInsureAccSet();
                tLCInsureAccSet=tLCInsureAccDB.executeQuery(sqlbv);
                double value=0;
                for(int i=1;i<=tLCInsureAccSet.size();i++)
                {
                    tLCInsureAccSet.get(i).setInsuAccBala(value);
                }
                SQL="select * from LCInsureAccClass where polno=(select polno from Lcpol where contno='?tContNo?' and riskcode in (select riskcode from Lmriskapp where  risktype3='3'))";
                sqlbv=new SQLwithBindVariables();
                sqlbv.sql(SQL);
                sqlbv.put("tContNo", tContNo);
                LCInsureAccClassDB  tLCInsureAccClassDB=new LCInsureAccClassDB();
                LCInsureAccClassSet  tLCInsureAccClassSet=new LCInsureAccClassSet();
                tLCInsureAccClassSet=tLCInsureAccClassDB.executeQuery(sqlbv);
                for(int i=1;i<=tLCInsureAccClassSet.size();i++)
                {
                    tLCInsureAccClassSet.get(i).setInsuAccBala(value);
                }
                //也可以把数据删除
                map.put(tLCInsureAccSet, "UPDATE");
                map.put(tLCInsureAccClassSet, "UPDATE");
             }
          }
        return true;
    }
    /**
   * 投连险退保账户操作
   * @param sPolNo
   * @param sBalaDate
   * @return boolean
   */
  private boolean dealAcc(String sPolNo, String sBalaDate)
  {
      LCInsureAccSet instLCInsureAccSet = new LCInsureAccSet();
      LCInsureAccClassSet instLCInsureAccClassSet = new LCInsureAccClassSet();
      LCInsureAccTraceSet instLCInsureAccTraceSet = new LCInsureAccTraceSet();

      LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
      String strSql = "select * from LCInsureAcc where PolNo ='?sPolNo?'";
      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
      sqlbv.sql(strSql);
      sqlbv.put("sPolNo", sPolNo);
      LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
      if (tLCInsureAccDB.mErrors.needDealError())
      {
          CError.buildErr(this, "保单帐户查询失败!");
          return false;
      }
      if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1)
      {
          CError.buildErr(this, "保单没有帐户数据!");
          return false;
      }

      for (int i = 1; i <= tLCInsureAccSet.size(); i++)
      {

          instLCInsureAccSet.add(tLCInsureAccSet.get(i));

          strSql = " select * from LCInsureAccclass where PolNo = '?sPolNo?'" +
                   " and InsuAccNo = '?InsuAccNo?' ";
          LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
          sqlbv=new SQLwithBindVariables();
          sqlbv.sql(strSql);
          sqlbv.put("sPolNo", sPolNo);
          sqlbv.put("InsuAccNo", tLCInsureAccSet.get(i).getInsuAccNo());
          LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(sqlbv);
          if (tLCInsureAccClassDB.mErrors.needDealError())
          {
              CError.buildErr(this, "保单子帐户查询失败!");
              return false;
          }
          if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() < 1)
          {
              CError.buildErr(this, "保单没有子帐户数据!");
              return false;
          }

          for (int j = 1; j <= tLCInsureAccClassSet.size(); j++)
          {
              instLCInsureAccClassSet.add(tLCInsureAccClassSet.get(j));
              //分别针对每个账户分类表增加一条退保轨迹记录
              Reflections ref = new Reflections();
              LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
              ref.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSet.get(j));
              String tLimit = PubFun.getNoLimit(tLCInsureAccClassSet.get(j).getManageCom());
              String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
              tLCInsureAccTraceSchema.setSerialNo(serNo);
              tLCInsureAccTraceSchema.setMoneyType("TB");  //金额类型为 TB-退保金
              tLCInsureAccTraceSchema.setState("0");
              tLCInsureAccTraceSchema.setFeeCode("000000");
              tLCInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
              tLCInsureAccTraceSchema.setPayDate(sBalaDate);
              tLCInsureAccTraceSchema.setMakeDate(sBalaDate);
              tLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
              tLCInsureAccTraceSchema.setModifyDate(sBalaDate);
              tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
              tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
              tLCInsureAccTraceSchema.setAccAlterType("3");
              tLCInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo());
              tLCInsureAccTraceSchema.setBusyType(mLPEdorItemSchema.getEdorType());
              tLCInsureAccTraceSchema.setUnitCount(-tLCInsureAccClassSet.get(j).getUnitCount());
              instLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
          }
      }
      map.put(instLCInsureAccTraceSet, "DELETE&INSERT");
      return true;
  }
  //判断是不是投连险种
  public boolean IsTLRisk(String RiskCode){
      String IsSql="select * from Lmriskapp where riskcode ='?RiskCode?' and risktype3='3' ";
      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
      sqlbv.sql(IsSql);
      sqlbv.put("RiskCode", RiskCode);
      ExeSQL isTLExeSQL = new ExeSQL();
      SSRS isTLSSRS=new SSRS();
     isTLSSRS=isTLExeSQL.execSQL(sqlbv);
     if(isTLSSRS!=null&&isTLSSRS.MaxRow>0){
         return true;
     }else{
         return false;
     }
  }
	
	// public static void main(String[] args){
	// String updtsql= "update loprtmanager"
	// + " set stateflag = '1'"
	// + " where stateflag = '0'"
	// + " and code in('" + PrintManagerBL.CODE_BONUSPAY//分红业绩报告书
	// + "','" + PrintManagerBL.CODE_PEdorAPPRE//保费自垫通知书
	// + "','" + PrintManagerBL.CODE_PEdorContInvalid//保单失效通知书
	// + "','" + PrintManagerBL.CODE_PEdorLoanPay//保单质押贷款还款通知书
	// + "','" + PrintManagerBL.CODE_PEdorAutoPayAG//领取通知书(代生存调查通知书)
	// + "','" + PrintManagerBL.CODE_PEdorAPPREEND//自垫预终止通知书
	// + "','" + PrintManagerBL.CODE_PEdorPreInvali//失效预终止通知书
	// + "','" + PrintManagerBL.CODE_PEdorExpireTerminate//满期终止通知书
	// + "','" + PrintManagerBL.CODE_PEdorLOANPREEND//贷款自垫预终止通知书
	// + "','" + PrintManagerBL.CODE_PEdorBankFine//保全收费银行划款成功通知书
	// + "','" + PrintManagerBL.CODE_EdorINSUACC//万能险结算状态报告书
	// + "')"
	// + " and otherno in (select polno from lcpol where appflag = '4' and
	// contno = '1001')";
	// logger.debug(updtsql);
	// }

}
