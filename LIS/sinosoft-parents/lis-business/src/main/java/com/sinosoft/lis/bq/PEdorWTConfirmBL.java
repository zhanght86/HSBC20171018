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
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPBonusPolDB;
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
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPBonusPolSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
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
public class PEdorWTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorWTConfirmBL.class);
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

	public PEdorWTConfirmBL() {
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
	      //直接调用CT生效处理
	      PEdorCTConfirmBL tPEdorCTConfirmBL = new PEdorCTConfirmBL();
	      if (!tPEdorCTConfirmBL.submitData(mInputData, mOperate))
	      {
	          mErrors.copyAllErrors(tPEdorCTConfirmBL.mErrors);
	          return false;
	      }
	      mResult = tPEdorCTConfirmBL.getResult();
		return true;
	}


	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		return true;
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
