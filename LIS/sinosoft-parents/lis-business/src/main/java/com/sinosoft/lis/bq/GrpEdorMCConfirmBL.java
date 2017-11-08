package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author liangcong
 * @version 1.0
 */
public class GrpEdorMCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorMCConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	public GrpEdorMCConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------Grp MC Confirm----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			return false;
		}
		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
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
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
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
		// 取出现有C、P表数据
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LCGrpContSet curLCGrpContSet = tLCGrpContDB.query();
		if (tLCGrpContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体保单信息失败!");
			return false;
		}
		if (curLCGrpContSet == null || curLCGrpContSet.size() < 1) {
			CError.buildErr(this, "团体保单信息不存在!");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = curLCGrpContSet.get(1);

		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpContSet curLPGrpContSet = tLPGrpContDB.query();
		if (tLPGrpContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全团体保单信息失败!");
			return false;
		}
		if (curLPGrpContSet == null || curLPGrpContSet.size() < 1) {
			CError.buildErr(this, "保全团体保单信息不存在!");
			return false;
		}
		LPGrpContSchema tLPGrpContSchema = curLPGrpContSet.get(1);

		// 交换
		Reflections ref = new Reflections();

		LPGrpContSchema newLPGrpContSchema = new LPGrpContSchema();
		ref.transFields(newLPGrpContSchema, tLCGrpContSchema);
		newLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		newLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		newLPGrpContSchema.setModifyDate(CurrDate);
		newLPGrpContSchema.setModifyTime(CurrTime);
		newLPGrpContSchema.setRemark(tLCGrpContSchema.getRemark());
		mMap.put(newLPGrpContSchema, "UPDATE");

		LCGrpContSchema newLCGrpContSchema = new LCGrpContSchema();
		ref.transFields(newLCGrpContSchema, tLPGrpContSchema);
		newLCGrpContSchema.setModifyDate(CurrDate);
		newLCGrpContSchema.setModifyTime(CurrTime);
		mMap.put(newLCGrpContSchema, "UPDATE");

		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
