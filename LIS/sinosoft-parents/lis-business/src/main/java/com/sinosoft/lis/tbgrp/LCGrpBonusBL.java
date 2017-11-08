package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpBonusDB;
import com.sinosoft.lis.db.LCGrpBonusSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpBonusSchema;
import com.sinosoft.lis.schema.LCGrpBonusSubSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * @version 1.0
 */
public class LCGrpBonusBL {
private static Logger logger = Logger.getLogger(LCGrpBonusBL.class);
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpBonusSchema mLCGrpBonusSchema = new LCGrpBonusSchema();
	private LCGrpBonusSchema mmLCGrpBonusSchema = new LCGrpBonusSchema();
	private LCGrpBonusSubSchema mLCGrpBonusSubSchema = new LCGrpBonusSubSchema();

	public LCGrpBonusBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!checkData()) {
			this.buildError("", "提交的数据有误，请核对后再操作");
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		this.prepareOutputData();
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCNewCardChcekBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "服务器接收数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpBonusSchema = (LCGrpBonusSchema) mInputData
				.getObjectByObjectName("LCGrpBonusSchema", 0);
		mLCGrpBonusSubSchema = (LCGrpBonusSubSchema) mInputData
				.getObjectByObjectName("LCGrpBonusSubSchema", 0);
		if (mOperate.equals("UPDATE")) {
			mmLCGrpBonusSchema = (LCGrpBonusSchema) mInputData
					.getObjectByObjectName("LCGrpBonusSchema", 2);
			// logger.debug(mmLMInsuAccRateSchema.encode());
		}
		return true;
	}

	private boolean checkData() {

		LCGrpBonusSubDB tLCGrpBonusSubDB = new LCGrpBonusSubDB();
		LCGrpBonusSubSchema tLCGrpBonusSubSchema = new LCGrpBonusSubSchema();
		tLCGrpBonusSubSchema.setGrpPolNo(mLCGrpBonusSubSchema.getGrpPolNo());
		tLCGrpBonusSubSchema.setAccountPassYears(mLCGrpBonusSubSchema
				.getAccountPassYears());
		tLCGrpBonusSubDB.setSchema(tLCGrpBonusSubSchema);

		if (mOperate.equals("ADD")) {
			if (tLCGrpBonusSubDB.query().size() > 0) {
				CError tError = new CError();
				tError.moduleName = "LCGrpBonusBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "该险种已经做过了个别分红退保!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		if (mOperate.equals("CancleMod")) {
			if (tLCGrpBonusSubDB.query().size() < 1) {
				CError tError = new CError();
				tError.moduleName = "LCGrpBonusBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有查询到该险种此种退保，您不能做修改!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	private boolean dealData() {
		mLCGrpBonusSchema.setStandbyFlag1("0");
		mLCGrpBonusSchema.setStandbyFlag2("");
		mLCGrpBonusSchema.setStandbyFlag3("");
		mLCGrpBonusSchema.setOperator(mGlobalInput.Operator);
		mLCGrpBonusSchema.setMakeDate(theCurrentDate);
		mLCGrpBonusSchema.setMakeTime(theCurrentTime);
		mLCGrpBonusSchema.setModifyDate(theCurrentDate);
		mLCGrpBonusSchema.setModifyTime(theCurrentTime);

		mLCGrpBonusSubSchema.setStandbyFlag1("0");
		mLCGrpBonusSubSchema.setStandbyFlag2("");
		mLCGrpBonusSubSchema.setStandbyFlag3("");
		mLCGrpBonusSubSchema.setOperator(mGlobalInput.Operator);
		mLCGrpBonusSubSchema.setMakeDate(theCurrentDate);
		mLCGrpBonusSubSchema.setMakeTime(theCurrentTime);
		mLCGrpBonusSubSchema.setModifyDate(theCurrentDate);
		mLCGrpBonusSubSchema.setModifyTime(theCurrentTime);

		if (mOperate.equals("INSERT")) {
			LCGrpBonusDB tLCGrpBonusDB = new LCGrpBonusDB();
			tLCGrpBonusDB.setGrpPolNo(mLCGrpBonusSchema.getGrpPolNo());
			if (tLCGrpBonusDB.query().size() > 0) {
				CError tError = new CError();
				tError.moduleName = "TkBdDealBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "该险种已经做过了个别分红!";
				this.mErrors.addOneError(tError);
				return false;
			}
			map.put(mLCGrpBonusSchema, "INSERT");
			return true;
		}
		if (mOperate.equals("UPDATE")) {
			map.put("delete from LCGrpBonus where grppolno='"
					+ mmLCGrpBonusSchema.getGrpPolNo() + "'", "DELETE");
			map.put(mLCGrpBonusSchema, "INSERT");
			return true;
		}
		if (mOperate.equals("ADD")) {
			map.put(mLCGrpBonusSubSchema, "INSERT");
			return true;
		}
		if (mOperate.equals("CancleMod")) {
			map.put("delete from LCGrpBonusSub where grppolno='"
					+ mLCGrpBonusSubSchema.getGrpPolNo()
					+ "' and AccountPassYears='"
					+ mLCGrpBonusSubSchema.getAccountPassYears() + "'",
					"DELETE");
			map.put(mLCGrpBonusSubSchema, "DELETE&INSERT");
			return true;
		}
		if (mOperate.equals("CancleDEL")) {
			map.put("delete from LCGrpBonusSub where grppolno='"
					+ mLCGrpBonusSubSchema.getGrpPolNo()
					+ "' and AccountPassYears='"
					+ mLCGrpBonusSubSchema.getAccountPassYears() + "'",
					"DELETE");
			return true;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "TkBdDealBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
	}

}
