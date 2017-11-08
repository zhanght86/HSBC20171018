package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全项目基本信息变更备注项明细
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */
public class PEdorMCDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorMCDetailBLF.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPContSchema mLPContSchema = new LPContSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	// private LCCustomerImpartSet mLCCustomerImpartSet = new
	// LCCustomerImpartSet();
	private MMap mMap = new MMap();

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据查询业务处理
		if (!cOperate.equals("UPDATE||MAIN")) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorMCDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPContSchema = (LPContSchema) mInputData.getObjectByObjectName(
					"LPContSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mLPContSchema == null || mLPEdorItemSchema == null
					|| mGlobalInput == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 数据检测
	 * 
	 * @return boolean
	 * 
	 * private boolean checkData() { LPEdorItemDB tLPEdorItemDB = new
	 * LPEdorItemDB(); tLPEdorItemDB.setSchema(mLPEdorItemSchema); if
	 * (!tLPEdorItemDB.getInfo()) { //
	 * @@错误处理 CError tError = new CError(); tError.moduleName =
	 *        "PEdorMCDetailBLF"; tError.functionName = "checkData";
	 *        tError.errorMessage = "无保全申请数据!"; logger.debug("------" +
	 *        tError); this.mErrors.addOneError(tError); return false; } if
	 *        (!tLPEdorItemDB.getEdorState().trim().equals("1") &&
	 *        !tLPEdorItemDB.getEdorState().trim().equals("3")) { //
	 * @@错误处理 CError tError = new CError(); tError.moduleName =
	 *        "PEdorMCDetailBLF"; tError.functionName = "checkData";
	 *        tError.errorMessage = "该保全已经申请确认不能修改!";
	 *        logger.debug("------" + tError);
	 *        this.mErrors.addOneError(tError); return false; } return true; }
	 */
	/**
	 * 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		UpdateEdorState tUpdateEdorState = new UpdateEdorState();
		MMap mMap1 = new MMap();
		mMap1.put(tUpdateEdorState.getUpdateEdorStateSql(mLPEdorItemSchema),
				"UPDATE");
		mMap.add(mMap1);
		PEdorMCDetailBL tPEdorMCDetailBL = new PEdorMCDetailBL();
		if (!tPEdorMCDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorMCDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorMCDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		VData tResult = tPEdorMCDetailBL.getResult();
		UpdateEdorState tUpdateEdorState1 = new UpdateEdorState();
		MMap mMap2 = new MMap();
		mMap2.put(tUpdateEdorState.UpdateEdorStateOne(mLPEdorItemSchema),
				"UPDATE");
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
		mMap.add(mMap2);
		mResult.add(mMap);
		// mResult = tPEdorMCDetailBL.getResult();
		// VData tResult = new VData();
		// mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
		// mResult.add(mMap);
		return true;
	}

	public PEdorMCDetailBLF() {
	}

}
