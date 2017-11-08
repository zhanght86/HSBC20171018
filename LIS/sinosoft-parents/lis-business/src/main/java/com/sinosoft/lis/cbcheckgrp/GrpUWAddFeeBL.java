package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 集体保单加费录入
 * </p>
 * <p>
 * Description: 在团单那人工核保时录入加费信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class GrpUWAddFeeBL {
private static Logger logger = Logger.getLogger(GrpUWAddFeeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LCPremSet mLCPremSet = new LCPremSet();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private MMap mMap;

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mGrpPol = "";
	private double tRiskScoce = 0.0;
	private double tAddPrem = 0.0;

	public GrpUWAddFeeBL() {
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
		map.add(this.mMap);

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

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (prepareAdd() == false) {
			return false;
		}

		return true;
	}

	private boolean prepareAdd() {

		for (int t = 1; t <= mLCGrpPolSet.size(); t++) {
			mLCGrpPolSchema = mLCGrpPolSet.get(t).getSchema();

			mGrpPol = mLCGrpPolSchema.getGrpPolNo();
			tRiskScoce = mLCGrpPolSchema.getDif();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setGrpPolNo(mGrpPol);
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet.size() == 0) {
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询LCPol表失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 循环查询LCPol表
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
					tError.errorMessage = "查询LCDuty表失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 循环查询LCDuty表
				for (int j = 1; j <= tLCDutySet.size(); j++) {
					mLCDutySchema = tLCDutySet.get(j).getSchema();
					LCPremSchema tLCPremSchema = new LCPremSchema();
					tLCPremSchema.setPolNo(mLCPolSchema.getPolNo());
					tLCPremSchema.setDutyCode(mLCDutySchema.getDutyCode());
					tLCPremSchema.setPayStartDate(mLCDutySchema
							.getFirstPayDate());
					tLCPremSchema.setPayPlanType("04"); // "04"团单加费
					tLCPremSchema.setPayEndDate(mLCDutySchema.getPayEndDate());
					tAddPrem = mLCDutySchema.getStandPrem() * tRiskScoce / 100;
					tLCPremSchema.setPrem(tAddPrem);
					tLCPremSchema.setSuppRiskScore(tRiskScoce);
					mLCPremSet.add(tLCPremSchema);

				}
				UWGrpManuAddChkBL tUWGrpManuAddChkBL = new UWGrpManuAddChkBL();
				VData aVData = new VData();
				aVData.add(this.mGlobalInput);
				aVData.add(this.mLCPolSchema);
				aVData.add(mLCPremSet);
				boolean tResult = tUWGrpManuAddChkBL.submitData(aVData,
						"GrpAdd");

				if (tResult) {
					mMap = (MMap) tUWGrpManuAddChkBL.getResult()
							.getObjectByObjectName("MMap", 0);
				}

				else {
					this.mErrors.copyAllErrors(tUWGrpManuAddChkBL.mErrors);
				}

			}
		}
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
