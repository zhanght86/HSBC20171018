package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 费率调整
 * </p>
 * <p>
 * Description: 团单人工核保时对集体险种单进行费率调整
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class GrpFloatRateBL {
private static Logger logger = Logger.getLogger(GrpFloatRateBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 集体合同表 */
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	/** 集体险种表 */
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();

	/** 个人合同表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCContSet mLCContSet = new LCContSet();
	/** 个人险种表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	/** 责任表 */
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LCDutySet mLCDutySet = new LCDutySet();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mFloatRate;// 浮动费率
	private double newFloatRate;
	private double mOldPrem = 0;// 责任项旧标准保费
	private double mNewPrem = 0;// 责任项新保费

	private double mSumGrpPolPrem = 0;// 集体险种单下的新保费
	private String mAddPrem;// 责任项下加费金额

	private String FORMATMODOL = "0.0000"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	public GrpFloatRateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		// 提交数据

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCInsuredUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(mLCDutySet, "UPDATE");
		map.put(mLCPolSet, "UPDATE");
		map.put(mLCContSet, "UPDATE");
		map.put(mLCGrpPolSchema, "UPDATE");
		map.put(mLCGrpContSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCGrpPolSet = (LCGrpPolSet) cInputData.getObjectByObjectName(
				"LCGrpPolSet", 0);
		mLCGrpPolSchema = mLCGrpPolSet.get(1).getSchema();
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mFloatRate = mLCGrpPolSchema.getStandbyFlag1();
		if (mFloatRate == null || mFloatRate.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(mLCGrpPolSchema.getGrpPolNo());
		if (!tLCGrpPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LCGrpPol失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpPolSchema = tLCGrpPolDB.getSchema();
		// mLCGrpPolSchema.setStandbyFlag1(mFloatRate);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!preparePrem())
			return false;

		return true;
	}

	// 根据前台传输的GrpPolNo查询责任项
	private boolean preparePrem() {
		// 根据前台传输的GrpPolNo查询LCPol表
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setGrpPolNo(mLCGrpPolSchema.getGrpPolNo());
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LCPol表失败";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 根据PolNo查询LCDuty表

		for (int i = 1; i <= tLCPolSet.size(); i++) {

			mLCPolSchema = tLCPolSet.get(i).getSchema();
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
			LCDutySet tLCDutySet = new LCDutySet();
			tLCDutySet = tLCDutyDB.query();
			if (tLCDutySet.size() == 0) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询LCPol表失败";
				this.mErrors.addOneError(tError);
				return false;
			}
			double mSumPolPrem = 0;// 险种单下的新保费
			for (int j = 1; j <= tLCDutySet.size(); j++) {
				mLCDutySchema = tLCDutySet.get(j).getSchema();

				// 查询加费金额
				String tSql = "select sum(Prem) from LCPrem where PolNo='"
						+ mLCPolSchema.getPolNo()
						+ "' and PayPlanCode like '000000%'"
						+ " and DutyCode = '" + mLCDutySchema.getDutyCode()
						+ "'";
				SSRS tempSSRS = new SSRS();
				ExeSQL tempExeSQL = new ExeSQL();
				tempSSRS = tempExeSQL.execSQL(tSql);
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0")
							|| tempSSRS.GetText(1, 1).trim().equals("") || tempSSRS
							.GetText(1, 1).equals("null"))) {
						mAddPrem = tempSSRS.GetText(1, 1); // 累计加费

					} else {
						mAddPrem = "0";
					}
				}

				double tFloatRate = Double.parseDouble(mFloatRate);
				newFloatRate = mLCDutySchema.getFloatRate() * tFloatRate;
				newFloatRate = Double.parseDouble(mDecimalFormat
						.format(newFloatRate)); // 转换计算后的保费(规定的精度)
				double tAddPrem = Double.parseDouble(mAddPrem);
				mOldPrem = mLCDutySchema.getStandPrem();
				// 责任项下的新保费金额
				mNewPrem = mOldPrem * tFloatRate + tAddPrem;
				mLCDutySchema.setPrem(mNewPrem);

				mLCDutySchema.setFloatRate(newFloatRate);
				mSumPolPrem = mSumPolPrem + mNewPrem;
				mSumGrpPolPrem = mSumGrpPolPrem + mNewPrem;
				mLCDutySet.add(mLCDutySchema);
			}
			// 在LCPol层次上，计算旧保费与新保费的差额
			double tOldPolPrem = mLCPolSchema.getPrem();
			double tDiffPol = tOldPolPrem - mNewPrem;
			// 险种单下的新保费金额
			mLCPolSchema.setPrem(mSumPolPrem);
			mLCPolSchema.setFloatRate(newFloatRate);
			// 合同单LCCont下的新的保费金额
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLCPolSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询LCCont表失败";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCContSchema = tLCContDB.getSchema();
			mLCContSchema.setPrem(mLCContSchema.getPrem() - tDiffPol);
			mLCPolSet.add(mLCPolSchema);
			mLCContSet.add(mLCContSchema);
		}
		// 在LCGrpPol层次上，计算旧保费与新保费的差额
		double tOldGrpPol = mLCGrpPolSchema.getPrem();
		double tDiffGrpPol = tOldGrpPol - mSumGrpPolPrem;
		// 集体险种单下的新保费金额
		mLCGrpPolSchema.setPrem(mSumGrpPolPrem);
		mLCGrpPolSchema.setStandbyFlag1(Double.toString(newFloatRate));
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLCGrpPolSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LCCont表失败";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();
		mLCGrpContSchema.setPrem(mLCGrpContSchema.getPrem() - tDiffGrpPol);

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
