package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGeneralDB;
import com.sinosoft.lis.db.LCGeneralToRiskDB;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LPGeneralDB;
import com.sinosoft.lis.db.LPGeneralToRiskDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpAppntDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGeneralSchema;
import com.sinosoft.lis.schema.LCGeneralToRiskSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LPGeneralSchema;
import com.sinosoft.lis.schema.LPGeneralToRiskSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPMoveSchema;
import com.sinosoft.lis.vschema.LPGeneralSet;
import com.sinosoft.lis.vschema.LPGeneralToRiskSet;
import com.sinosoft.lis.vschema.LPGrpAddressSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis v6
 * </p>
 * <p>
 * Description: 保单迁移
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lanjun
 * @version 1.0
 */
public class GrpEdorPRConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorPRConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	boolean newaddrFlag = false;

	public GrpEdorPRConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
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
		logger.debug("---End getInputData---");

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");
		if (!prepareOutputData()) {
			return false;
		}

		// // 数据操作业务处理
		// PEdorConfirmBLS tPEdorConfirmBLS = new PEdorConfirmBLS();
		// logger.debug("Start Confirm BB BL Submit...");
		// if (!tPEdorConfirmBLS.submitData(mInputData, mOperate))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPEdorConfirmBLS.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorConfirmBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {

		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		VData tVData = new VData();
		LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		Reflections tRef = new Reflections();

		String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		String tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		String tEdorType = mLPGrpEdorItemSchema.getEdorType();
		String tManageCom = mLPGrpEdorItemSchema.getManageCom();

		// p表
		LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
		tLPGrpAppntDB.setGrpContNo(tGrpContNo);
		tLPGrpAppntDB.setEdorNo(tEdorNo);
		tLPGrpAppntDB.setEdorType(tEdorType);
		if (!tLPGrpAppntDB.getInfo()) {
			this.mErrors.copyAllErrors(tLPGrpAppntDB.mErrors);
			return false;
		}

		// General表
		LPGeneralDB tLPGeneralDB = new LPGeneralDB();
		tLPGeneralDB.setGrpContNo(tGrpContNo);
		tLPGeneralDB.setEdorNo(tEdorNo);
		tLPGeneralDB.setEdorType(tEdorType);
		LPGeneralSet tLPGeneralSet = new LPGeneralSet();
		tLPGeneralSet = tLPGeneralDB.query();
		if (tLPGeneralSet.size() > 0 && tLPGeneralSet != null) {
			for (int i = 1; i <= tLPGeneralSet.size(); i++) {
				LCGeneralSchema afterLCGeneralSchema = new LCGeneralSchema();
				LPGeneralSchema afterLPGeneralSchema = new LPGeneralSchema();

				tRef.transFields(afterLCGeneralSchema, tLPGeneralSet.get(i));
				map.put(afterLCGeneralSchema, "UPDATE");

				LCGeneralDB tLCGeneralDB = new LCGeneralDB();
				tLCGeneralDB.setGrpContNo(tGrpContNo);
				tLCGeneralDB
						.setExecuteCom(afterLCGeneralSchema.getExecuteCom());
				tLCGeneralDB.getInfo();
				afterLPGeneralSchema.setSchema(tLPGeneralDB.getSchema());
				tRef
						.transFields(afterLPGeneralSchema, tLCGeneralDB
								.getSchema());
				map.put(afterLPGeneralSchema, "UPDATE");
			}
		}

		// GrpPol表
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setGrpContNo(tGrpContNo);
		tLPGrpPolDB.setEdorNo(tEdorNo);
		tLPGrpPolDB.setEdorType(tEdorType);
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolSet.size() > 0 && tLPGrpPolSet != null) {
			for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
				LCGrpPolSchema afterLCGrpPolSchema = new LCGrpPolSchema();
				LPGrpPolSchema afterLPGrpPolSchema = new LPGrpPolSchema();

				tRef.transFields(afterLCGrpPolSchema, tLPGrpPolSet.get(i));
				map.put(afterLCGrpPolSchema, "UPDATE");

				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				tLCGrpPolDB.setGrpContNo(tGrpContNo);
				tLCGrpPolDB.setGrpPolNo(afterLCGrpPolSchema.getGrpPolNo());
				tLCGrpPolDB.getInfo();
				afterLPGrpPolSchema.setSchema(tLPGrpPolDB.getSchema());
				tRef.transFields(afterLPGrpPolSchema, tLCGrpPolDB.getSchema());
				map.put(afterLPGrpPolSchema, "UPDATE");
			}
		}

		// GeneralToRisk表
		LPGeneralToRiskDB tLPGeneralToRiskDB = new LPGeneralToRiskDB();
		tLPGeneralToRiskDB.setGrpContNo(tGrpContNo);
		tLPGeneralToRiskDB.setEdorNo(tEdorNo);
		tLPGeneralToRiskDB.setEdorType(tEdorType);
		LPGeneralToRiskSet tLPGeneralToRiskSet = new LPGeneralToRiskSet();
		tLPGeneralToRiskSet = tLPGeneralToRiskDB.query();
		if (tLPGeneralToRiskSet.size() > 0 && tLPGeneralToRiskSet != null) {
			for (int i = 1; i <= tLPGeneralToRiskSet.size(); i++) {
				LCGeneralToRiskSchema afterLCGeneralToRiskSchema = new LCGeneralToRiskSchema();
				LPGeneralToRiskSchema afterLPGeneralToRiskSchema = new LPGeneralToRiskSchema();

				tRef.transFields(afterLCGeneralToRiskSchema,
						tLPGeneralToRiskSet.get(i));
				map.put(afterLCGeneralToRiskSchema, "UPDATE");

				LCGeneralToRiskDB tLCGeneralToRiskDB = new LCGeneralToRiskDB();
				tLCGeneralToRiskDB.setGrpContNo(tGrpContNo);
				tLCGeneralToRiskDB.setExecuteCom(afterLCGeneralToRiskSchema
						.getExecuteCom());
				tLCGeneralToRiskDB.setGrpPolNo(afterLCGeneralToRiskSchema
						.getGrpPolNo());
				tLCGeneralToRiskDB.getInfo();
				afterLPGeneralToRiskSchema.setSchema(tLPGeneralToRiskDB
						.getSchema());
				tRef.transFields(afterLPGeneralToRiskSchema, tLCGeneralToRiskDB
						.getSchema());
				map.put(afterLPGeneralToRiskSchema, "UPDATE");
			}
		}

		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setGrpContNo(tGrpContNo);
		tLPGrpContDB.setEdorNo(tEdorNo);
		tLPGrpContDB.setEdorType(tEdorType);
		if (!tLPGrpContDB.getInfo()) {
			this.mErrors.copyAllErrors(tLPGrpContDB.mErrors);
		}

		// 转移表 轨迹
		LPMoveSchema tLPMoveSchema = new LPMoveSchema();
		tLPMoveSchema.setEdorNo(tEdorNo);
		tLPMoveSchema.setEdorType(tEdorType);
		tLPMoveSchema.setManageComNew(tManageCom);
		tLPMoveSchema.setContNoNew(tGrpContNo);
		tLPMoveSchema.setContNoOld(tGrpContNo);
		// tLPMoveSchema.setGrpContNoNew(tGrpContNo);
		tLPMoveSchema.setContNoOld(tGrpContNo);
		tLPMoveSchema.setManageComOld(tLPGrpContDB.getSchema().getManageCom());
		tLPMoveSchema.setModifyDate(PubFun.getCurrentDate());
		tLPMoveSchema.setModifyTime(PubFun.getCurrentTime());
		// tLPMoveSchema.setPolNoNew(tLPGrpPolSet.get(1).getpol);

		map.put(tLPMoveSchema, "INSERT");

		/*
		 * LPGrpDB tLPGrpDB = new LPGrpDB(); tLPGrpDB.setEdorNo(tEdorNo);
		 * tLPGrpDB.setEdorType(tEdorType);
		 * tLPGrpDB.setCustomerNo(tLPGrpContDB.getAppntNo()); if
		 * (!tLPGrpDB.getInfo()) { this.mErrors.copyAllErrors(tLPGrpDB.mErrors);
		 * 
		 * return false; }
		 */

		LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();
		LPGrpAddressSet tLPGrpAddressSet = new LPGrpAddressSet();
		tLPGrpAddressDB.setEdorNo(tEdorNo);
		tLPGrpAddressDB.setEdorType(tEdorType);

		tLPGrpAddressDB.setCustomerNo(tLPGrpContDB.getAppntNo());
		tLPGrpAddressDB.setAddressNo(tLPGrpContDB.getAddressNo());

		tLPGrpAddressSet = tLPGrpAddressDB.query();
		if (tLPGrpAddressDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPGrpAddressDB.mErrors);
			return false;
		}
		if (tLPGrpAddressSet.size() > 0) {
			LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
			tLCGrpAddressDB.setCustomerNo(tLPGrpContDB.getAppntNo());
			tLCGrpAddressDB.setAddressNo(tLPGrpContDB.getAddressNo());
			if (!tLCGrpAddressDB.getInfo()) {
				newaddrFlag = true;
			}
			tLPGrpAddressDB.setSchema(tLPGrpAddressSet.get(1));
		}

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();

		tLCGrpAppntDB.setGrpContNo(tGrpContNo);

		if (!tLCGrpAppntDB.getInfo()) {
			this.mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

			return false;
		}

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);

			return false;
		}

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(tGrpContNo);
		tLCGrpPolDB.getInfo();

		LCGrpAppntSchema afterLCGrpAppntSchema = new LCGrpAppntSchema();
		LCGrpContSchema afterLCGrpContSchema = new LCGrpContSchema();
		// LDGrpSchema afterLDGrpSchema = new LDGrpSchema();
		LCGrpAddressSchema afterLCGrpAddressSchema = new LCGrpAddressSchema();

		LPGrpAppntSchema afterLPGrpAppntSchema = new LPGrpAppntSchema();
		LPGrpContSchema afterLPGrpContSchema = new LPGrpContSchema();
		// LPGrpSchema afterLPGrpSchema = new LPGrpSchema();
		LPGrpAddressSchema afterLPGrpAddressSchema = new LPGrpAddressSchema();

		tRef.transFields(afterLCGrpAppntSchema, tLPGrpAppntDB.getSchema());
		map.put(afterLCGrpAppntSchema, "UPDATE");

		tRef.transFields(afterLCGrpContSchema, tLPGrpContDB.getSchema());
		map.put(afterLCGrpContSchema, "UPDATE");

		// tRef.transFields(afterLDGrpSchema, tLPGrpDB.getSchema());
		// map.put(afterLDGrpSchema, "UPDATE");
		if (newaddrFlag) {
			tRef.transFields(afterLCGrpAddressSchema, tLPGrpAddressDB
					.getSchema());
			map.put(afterLCGrpAddressSchema, "INSERT");
		}

		// /////插入p表
		afterLPGrpAppntSchema.setSchema(tLPGrpAppntDB.getSchema());
		tRef.transFields(afterLPGrpAppntSchema, tLCGrpAppntDB.getSchema());
		map.put(afterLPGrpAppntSchema, "UPDATE");

		afterLPGrpContSchema.setSchema(tLPGrpContDB.getSchema());
		tRef.transFields(afterLPGrpContSchema, tLCGrpContDB.getSchema());
		map.put(afterLPGrpContSchema, "UPDATE");

		return true;
	}

	public TransferData getReturnTransferData() {
		return null;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
