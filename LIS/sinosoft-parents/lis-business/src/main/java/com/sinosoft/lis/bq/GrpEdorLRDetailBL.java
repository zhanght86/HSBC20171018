package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: web业务系统
 * </p>
 * 
 * <p>
 * Description: 团体保全
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosfot
 * </p>
 * 
 * @author 梁聪
 * @version 1.0
 */
public class GrpEdorLRDetailBL implements EdorDetail {
private static Logger logger = Logger.getLogger(GrpEdorLRDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public GrpEdorLRDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------Grp LR Detail----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			return false;
		}
		// 处理数据
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null
				|| mLPEdorItemSchema == null) {
			CError.buildErr(this, "输入数据不完整!");
			return false;
		}

		return true;
	}

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 获取LPGrpEdorItem
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "团险保全项目信息不存在!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

		return true;
	}

	private boolean dealData() {
		// 调用个单处理个人补发凭证
		PEdorLRDetailBL tPEdorLRDetailBL = new PEdorLRDetailBL();
		if (!tPEdorLRDetailBL.submitData(mInputData, mOperate)) {
			mErrors.copyAllErrors(tPEdorLRDetailBL.mErrors);
			CError.buildErr(this, "处理提交数据失败！");
			return false;
		}
		mResult = tPEdorLRDetailBL.getResult();
		mMap = (MMap) mResult.getObjectByObjectName("MMap", 0);
		BqCalBase mBqCalBase = (BqCalBase) mResult.getObjectByObjectName(
				"BqCalBase", 0);

		// 更新团单保全项目LPGrpEdorItem
		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mLPGrpEdorItemSchema.setGetMoney(0);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		String delSql = "delete from LJSGetEndorse where GetNoticeNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EndorsementNo='" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' " + " and FeeOperationType='LR'" + " and OtherNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and OtherNoType='3'";
		mMap.put(delSql, "DELETE");

		delSql = "update lpedoritem set getmoney = 0 where contno='"
				+ mLPEdorItemSchema.getContNo() + "'" + " and EdorType='"
				+ mLPEdorItemSchema.getEdorType() + "'" + " and EdorAcceptNo='"
				+ mLPEdorItemSchema.getEdorAcceptNo() + "'" + " and EdorNo='"
				+ mLPEdorItemSchema.getEdorNo() + "'";
		mMap.put(delSql, "DELETE");

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);

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
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
