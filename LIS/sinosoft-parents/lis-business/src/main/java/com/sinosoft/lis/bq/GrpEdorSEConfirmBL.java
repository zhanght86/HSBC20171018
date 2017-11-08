package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LPGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpEdorSEConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorSEConfirmBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	public GrpEdorSEConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("--==  Grp SE ConfirmBL  ==--");

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
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
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
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		tLCGrpContStateDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContStateDB.setStateType("Available");
		LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.query();
		if (tLCGrpContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体保单状态信息失败!");
			return false;
		}
		if (tLCGrpContStateSet == null || tLCGrpContStateSet.size() < 1) {
			CError.buildErr(this, "团体保单状态信息不存在!");
		}

		LPGrpContStateDB tLPGrpContStateDB = new LPGrpContStateDB();
		tLPGrpContStateDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContStateDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContStateDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContStateDB.setStateType("Available");
		LPGrpContStateSet tLPGrpContStateSet = tLPGrpContStateDB.query();
		if (tLPGrpContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全团体保单状态信息失败!");
			return false;
		}
		if (tLPGrpContStateSet == null || tLPGrpContStateSet.size() < 1) {
			CError.buildErr(this, "保全团体保单状态信息不存在!");
			return false;
		}

		String delSql;
		// 清除旧P表
		delSql = " delete from lpgrpcontstate where " + " edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'" + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + " and statetype = 'Available'";
		mMap.put(delSql, "DELETE");

		// 清除旧C表
		delSql = " delete from lcgrpcontstate where " + " grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ " and statetype = 'Available'";
		mMap.put(delSql, "DELETE");

		Reflections ref = new Reflections();
		String CurrDate = PubFun.getCurrentDate();
		String CurrTime = PubFun.getCurrentTime();
		int i;
		// 插入P表
		LPGrpContStateSchema tLPGrpContStateSchema;
		for (i = 1; i <= tLCGrpContStateSet.size(); i++) {
			tLPGrpContStateSchema = new LPGrpContStateSchema();
			ref.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(i));
			tLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema
					.getEdorType());
			tLPGrpContStateSchema.setModifyDate(CurrDate);
			tLPGrpContStateSchema.setModifyTime(CurrTime);
			mMap.put(tLPGrpContStateSchema, "INSERT");
		}

		// 插入C表
		LCGrpContStateSchema tLCGrpContStateSchema;
		for (i = 1; i <= tLPGrpContStateSet.size(); i++) {
			tLCGrpContStateSchema = new LCGrpContStateSchema();
			ref.transFields(tLCGrpContStateSchema, tLPGrpContStateSet.get(i));
			tLCGrpContStateSchema.setModifyDate(CurrDate);
			tLCGrpContStateSchema.setModifyTime(CurrTime);
			mMap.put(tLCGrpContStateSchema, "INSERT");
		}

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
