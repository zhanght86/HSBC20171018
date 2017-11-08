package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.ContCancel;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单遗失补发保全确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class GrpEdorWTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorWTConfirmBL.class);
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
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorWTConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		// 数据操作业务处理
		// PEdorConfirmBLS tPEdorConfirmBLS = new PEdorConfirmBLS();
		// if (!tPEdorConfirmBLS.submitData(mInputData, mOperate))
		// {
		// CError.buildErr(this, "数据提交失败", tPEdorConfirmBLS.mErrors);
		// return false;
		// }

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorNIAppConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() != 1) {
			mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询保全项目信息失败！"));
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		if (!tLPGrpEdorMainDB.getInfo()) {
			mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
			mErrors.addOneError(new CError("查询保全信息失败！"));
			return false;
		}
		mLPGrpEdorMainSchema = tLPGrpEdorMainDB.getSchema();

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mResult.clear();
		MMap map = new MMap();

		// String tSql =
		// "update LCGrpCont set lostTimes = lostTimes + 1 where GrpContNo='" +
		// mLPGrpEdorItemSchema.getGrpContNo() + "'";
		// String strSql =
		// "update LCGrpCont set printcount = 0 where GrpContNo = '" +
		// mLPGrpEdorItemSchema.getGrpContNo() + "'";

		mLPGrpEdorMainSchema.setEdorState("0");
		mLPGrpEdorMainSchema.setConfDate(PubFun.getCurrentDate());
		mLPGrpEdorMainSchema.setConfOperator(mGlobalInput.Operator);
		// 当前日期为保全生效日期
		mLPGrpEdorMainSchema.setEdorValiDate(PubFun.getCurrentDate());

		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);

		ContCancel tContCancel = new ContCancel();
		MMap map1 = tContCancel.prepareGrpContData(mLPGrpEdorItemSchema
				.getGrpContNo(), mLPGrpEdorItemSchema.getEdorNo());

		map.put(mLPGrpEdorItemSchema, "UPDATE");
		map.put(mLPGrpEdorMainSchema, "UPDATE");
		map.add(map1);
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		LPContSet tLPContSet = tLPContDB.query();
		for (int i = 0; i < tLPContSet.size(); i++) {
			map.add(tContCancel.prepareContData(tLPContSet.get(i + 1)
					.getContNo(), mLPGrpEdorItemSchema.getEdorNo()));
		}

		mResult.add(map);

		return true;
	}
}
