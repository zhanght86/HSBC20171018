package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 改变生效日期
 * </p>
 * <p>
 * Description: 在人工核保时改变生效日期
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class ChangeCvalidateBL {
private static Logger logger = Logger.getLogger(ChangeCvalidateBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 合同表
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCContSchema mmLCContSchema = new LCContSchema();

	// 险种表
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mmLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	MMap map = new MMap();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	double SumPrem = 0; // 合计保费

	public ChangeCvalidateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

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

		map.put(mmLCContSchema, "UPDATE");
		// map.put(mmLCPolSet, "UPDATE");

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
		mLCContSchema = (LCContSchema) cInputData.getObjectByObjectName(
				"LCContSchema", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
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
			tError.moduleName = "ReCalCulationPrem";
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
			tError.moduleName = "ReCalCulationPrem";
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
		// 准备合同信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCContSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ReCalCulationPrem";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);

		}
		mmLCContSchema = tLCContDB.getSchema();
		mmLCContSchema.setCValiDate(mLCContSchema.getCValiDate());

		// 准备险种信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		mLCPolSet = tLCPolDB.query();
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = mLCPolSet.get(i).getSchema();
			tLCPolSchema.setCValiDate(mLCContSchema.getCValiDate());

			// 循环调用RecalculationPremBL类，重新计算保费

			RecalculationPremBL aRecalculationPremBL = new RecalculationPremBL();
			VData aVData = new VData();
			aVData.add(mGlobalInput);
			aVData.add(tLCPolSchema);
			logger.debug(tLCPolSchema.getPolNo());
			boolean tResult = aRecalculationPremBL.submitData(aVData, "");
			logger.debug("after!tResult===" + tResult);
			if (tResult) {
				mMap = (MMap) aRecalculationPremBL.getResult()
						.getObjectByObjectName("MMap", 0);

				mLCPolSchema = (LCPolSchema) aRecalculationPremBL.getResult()
						.getObjectByObjectName("LCPolSchema", 0);

			}
			double Prem = mLCPolSchema.getPrem();
			SumPrem += Prem;
			// tLCPolSchema.setSpecifyValiDate("Y");
			map.add(this.mMap);

		}
		mmLCContSchema.setPrem(SumPrem);

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
