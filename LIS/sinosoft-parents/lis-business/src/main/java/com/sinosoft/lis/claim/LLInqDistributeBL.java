package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLInqApplyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLInqApplySchema;
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
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */
public class LLInqDistributeBL {
private static Logger logger = Logger.getLogger(LLInqDistributeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 调查申请表 */
	private LLInqApplySchema mLLInqApplySchema = new LLInqApplySchema();
	private LLInqApplySchema mmLLInqApplySchema = new LLInqApplySchema();
	MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	public LLInqDistributeBL() {
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

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLSuveryDistributeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (prepareDistribute() == false) {
			return false;
		}
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
		mLLInqApplySchema = (LLInqApplySchema) cInputData
				.getObjectByObjectName("LLInqApplySchema", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSuveryDistributeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLLInqApplySchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLSuveryDistributeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareDistribute() {
		// 准备续保核保主表信息
		LLInqApplyDB tLLInqApplyDB = new LLInqApplyDB();
		tLLInqApplyDB.setClmNo(mLLInqApplySchema.getClmNo());
		tLLInqApplyDB.setInqNo(mLLInqApplySchema.getInqNo());
		if (tLLInqApplyDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSuveryDistributeBL";
			tError.functionName = "prepareDistribute";
			tError.errorMessage = "无调查申请信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tInqPer = mLLInqApplySchema.getInqPer();
		mmLLInqApplySchema.setSchema(tLLInqApplyDB);
		mmLLInqApplySchema.setInqPer(tInqPer);
		mmLLInqApplySchema.setInqStartDate(CurrentDate);
		mmLLInqApplySchema.setModifyDate(CurrentDate);
		mmLLInqApplySchema.setModifyTime(CurrentTime);
		map.put(mmLLInqApplySchema, "UPDATE");
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
