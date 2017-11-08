package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class StopInsuredBL {
private static Logger logger = Logger.getLogger(StopInsuredBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData;
	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 业务操作类 */
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();

	/** 业务数据 */
	private String mInsuredStat = "";
	private String mContNo = "";
	private String mInsuredNo = "";

	public StopInsuredBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate==" + cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("After getinputdata");

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("After prepareOutputData");

		logger.debug("Start StopInsuredBL Submit...");

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("StopInsuredBL end");

		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLCInsuredSchema, "UPDATE");

		mResult.add(map);

		return true;
	}

	/**
	 * dealData 业务逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		mLCInsuredSchema.setInsuredStat(mInsuredStat);

		return true;
	}

	/**
	 * checkData 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "StopInsuredBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据Operator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCInsuredSchema = tLCInsuredDB.getSchema();

		if (mLCInsuredSchema.getInsuredStat() != null
				&& mLCInsuredSchema.getInsuredStat().length() != 0
				&& mLCInsuredSchema.getInsuredStat().equals("1")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "StopInsuredBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该被保人已被暂停!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputData
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 公用变量
		tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mLCInsuredSchema = (LCInsuredSchema) cInputData.getObjectByObjectName(
				"LCInsuredSchema", 0);

		mOperator = tGI.Operator;
		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "StopInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得暂停标志
		mContNo = mLCInsuredSchema.getContNo();
		if (mContNo == null || mContNo.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "StopInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据mContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得暂停标志
		mInsuredNo = mLCInsuredSchema.getInsuredNo();
		if (mInsuredNo == null || mInsuredNo.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "StopInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据InsuredNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得暂停标志
		mInsuredStat = mLCInsuredSchema.getInsuredStat();
		if (mInsuredStat == null || mInsuredStat.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "StopInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据InsuredStat失败!";
			this.mErrors.addOneError(tError);
			return false;
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

}
