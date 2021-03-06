package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 交费形式变更
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lihs
 * @version 1.0
 */
public class PEdorPCDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorPCDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPContSchema mLPContSchema = new LPContSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPAccountSchema mLPAccountSchema = new LPAccountSchema();

	public PEdorPCDetailBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
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
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行查询投保人信息功能 查询该次保全变更后投保人信息,如果保全明细尚未录入,查询本次保全之前项目修改过的投保人信息,
	 * 如果没有如上信息,查询正式表中投保人信息
	 */
	/*
	 * private boolean queryData(){ if (mOperate.equals("QUERY||MAIN")){
	 * LPAppntSchema tLPAppntSchema = new LPAppntSchema(); LPAppntBL tLPAppntBL =
	 * new LPAppntBL(); tLPAppntBL.setSchema(mLPAppntSchema); if
	 * (!tLPAppntBL.queryLPAppnt(mLPAppntSchema)){ return false; }
	 * tLPAppntSchema = tLPAppntBL.getSchema(); String tReturn =
	 * tLPAppntSchema.encode(); mResult.clear(); mResult.add(tReturn); } return
	 * true; }
	 */

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPContSchema = (LPContSchema) mInputData.getObjectByObjectName(
					"LPContSchema", 0);
			mLPAccountSchema = (LPAccountSchema) mInputData
					.getObjectByObjectName("LPAccountSchema", 0);

			if (mLPContSchema == null || mLPEdorItemSchema == null
					|| mGlobalInput == null || mLPAccountSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用交费形式变更业务逻辑处理类PEdorPCDetailBL进行处理
	 */
	private boolean dealData() {
		UpdateEdorState tUpdateEdorState = new UpdateEdorState();
		MMap mMap1 = new MMap();
		mMap1.put(tUpdateEdorState.getUpdateEdorStateSql(mLPEdorItemSchema),
				"UPDATE");
		mMap.add(mMap1);
		PEdorPCDetailBL tPEdorPCDetailBL = new PEdorPCDetailBL();
		if (!tPEdorPCDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorPCDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPCDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		VData tResult = tPEdorPCDetailBL.getResult();
		UpdateEdorState tUpdateEdorState1 = new UpdateEdorState();
		MMap mMap2 = new MMap();
		mMap2.put(tUpdateEdorState.UpdateEdorStateOne(mLPEdorItemSchema),
				"UPDATE");
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
		mMap.add(mMap2);
		mResult.add(mMap);

		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
