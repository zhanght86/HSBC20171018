/*
 * @(#)PEdorRBDetailBL.java	2005-09-20
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPBonusPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOBonusPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LPBonusPolSet;
import com.sinosoft.lis.vschema.LPContSet;
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
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 红利领取回退确认生效处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author： pst
 * @version：1.0 
 * @CreateDate：2008-12-09
 */
public class PEdorDBBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorDBBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();

	private MMap map = new MMap();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();


	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorDBBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
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
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 退保回退确认生效 ===");
		
		if (!backInsurAcc(mLPEdorItemSchema.getEdorNo(),
				mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo())) {
			return false;
		}
		return true;
	}

	/**
	 * 红利领取退保回退
	 * 
	 * @param sPolNo
	 */
	private boolean backInsurAcc(String sEdorNo, String sEdorType, String sContNo) {

		Reflections tRef = new Reflections();
		
		LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		tLPInsureAccDB.setContNo(sContNo);
		tLPInsureAccDB.setEdorNo(sEdorNo);
		tLPInsureAccDB.setEdorType(sEdorType);
		LPInsureAccSet updLPInsureAccSet = tLPInsureAccDB.query();
		if (tLPInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户信息失败!");
			return false;
		}
		if (updLPInsureAccSet != null && updLPInsureAccSet.size() > 0) {

			for (int m = 1; m <= updLPInsureAccSet.size(); m++) {
				LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
				tRef.transFields(tLCInsureAccSchema, updLPInsureAccSet.get(m));
				updLCInsureAccSet.add(tLCInsureAccSchema);
			}
		}

		LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		tLPInsureAccClassDB.setContNo(sContNo);
		tLPInsureAccClassDB.setEdorNo(sEdorNo);
		tLPInsureAccClassDB.setEdorType(sEdorType);
		LPInsureAccClassSet updLPInsureAccClassSet = tLPInsureAccClassDB
				.query();
		if (tLPInsureAccClassDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户分类信息失败!");
			return false;
		}
		if (updLPInsureAccClassSet != null && updLPInsureAccClassSet.size() > 0) {
			for (int m = 1; m <= updLPInsureAccClassSet.size(); m++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
				tRef.transFields(tLCInsureAccClassSchema,
						updLPInsureAccClassSet.get(m));
				updLCInsureAccClassSet.add(tLCInsureAccClassSchema);
			}
		}
		map.put(updLCInsureAccSet, "UPDATE");
		map.put(updLCInsureAccClassSet, "UPDATE");
		
		// 查出P表中的结算履历(退保时的结算)
		LCInsureAccTraceSet delLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
		tLPInsureAccTraceDB.setEdorNo(sEdorNo);
		tLPInsureAccTraceDB.setEdorType(sEdorType);
		tLPInsureAccTraceDB.setContNo(sContNo);
		LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
		if (tLPInsureAccTraceDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
			return false;
		}
		if (tLPInsureAccTraceSet != null && tLPInsureAccTraceSet.size() > 0) {
			LCInsureAccTraceSchema tLCInsureAccTraceSchema;
			for (int k = 1; k <= tLPInsureAccTraceSet.size(); k++) {
				// 删除退保时做的结算记录
				tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				tRef.transFields(tLCInsureAccTraceSchema, tLPInsureAccTraceSet
						.get(k));
				delLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
		}
		map.put(delLCInsureAccTraceSet, "DELETE");

		return true;
	}


	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return null;
	}

}
