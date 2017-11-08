package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDiseaseResultDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LCDiseaseResultSet;
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

public class ImpartToICDBL {
private static Logger logger = Logger.getLogger(ImpartToICDBL.class);
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
	private LCDiseaseResultSet mLCDiseaseResultSet = new LCDiseaseResultSet();

	/** 业务数据 */
	private String mName = "";
	private String mDelSql = "";

	public ImpartToICDBL() {
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

		logger.debug("Start ImpartToICDBL Submit...");

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

		logger.debug("ImpartToICDBL end");

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

		map.put(mLCDiseaseResultSet, "INSERT");

		mResult.add(map);

		return true;
	}

	/**
	 * dealData 业务逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		int tDisSerialNo;
		LCDiseaseResultDB tLCDiseaseResultDB = new LCDiseaseResultDB();
		tLCDiseaseResultDB.setContNo(mLCDiseaseResultSet.get(1).getContNo());
		tLCDiseaseResultDB.setCustomerNo(mLCDiseaseResultSet.get(1)
				.getCustomerNo());
		LCDiseaseResultSet tLCDiseaseResultSet = tLCDiseaseResultDB.query();
		if (tLCDiseaseResultSet.size() == 0) {
			tDisSerialNo = 0;
		} else {
			tDisSerialNo = tLCDiseaseResultSet.size() + 1;
		}

		for (int i = 1; i <= mLCDiseaseResultSet.size(); i++) {
			mLCDiseaseResultSet.get(i).setName(mName);
			mLCDiseaseResultSet.get(i).setSerialNo("" + tDisSerialNo);
			mLCDiseaseResultSet.get(i).setOperator(mOperator);
			mLCDiseaseResultSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mLCDiseaseResultSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mLCDiseaseResultSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLCDiseaseResultSet.get(i).setModifyTime(PubFun.getCurrentTime());

			tDisSerialNo++;
		}

		return true;
	}

	/**
	 * checkData 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LDPersonDB tLDPersonDB = new LDPersonDB();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tLDPersonDB.setCustomerNo(mLCDiseaseResultSet.get(1).getCustomerNo());
		if (!tLDPersonDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ImpartToICDBL";
			tError.functionName = "checkData";
			tError.errorMessage = "客户信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLDPersonSchema = tLDPersonDB.getSchema();
		mName = tLDPersonSchema.getName();
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
		mLCDiseaseResultSet = (LCDiseaseResultSet) cInputData
				.getObjectByObjectName("LCDiseaseResultSet", 0);

		mOperator = tGI.Operator;
		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ImpartToICDBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得疾病信息
		if (mLCDiseaseResultSet == null || mLCDiseaseResultSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "ImpartToICDBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据LCDiseaseResultSet失败!";
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
