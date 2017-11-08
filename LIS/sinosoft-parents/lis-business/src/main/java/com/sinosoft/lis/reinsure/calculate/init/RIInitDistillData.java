

package com.sinosoft.lis.reinsure.calculate.init;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @ zhangbin
 * @version 1.0
 */

import com.sinosoft.lis.db.RIAccumulateDefDB;
import com.sinosoft.lis.db.RICalDefDB;
import com.sinosoft.lis.db.RIItemCalDB;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RICalDefSchema;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

public class RIInitDistillData {
	public CErrors mErrors = new CErrors();
	/** 累计风险编码 */
	private String mAccumulateDefNo = "";
	/** 累计风险类别 */
	private RIAccumulateDefSchema mRIAccumulateDefSchema;
	/** 提数算法 */
	private RIItemCalSet riDistillCalSet = new RIItemCalSet();

	public RIInitDistillData(String accumulateDefNo) throws Exception {
		mAccumulateDefNo = accumulateDefNo;
		if (!init()) {
			Exception r = new Exception(mErrors.getFirstError());
			throw r;
		}
	}

	private boolean init() {
		// 初始化累计风险
		if (!initRIAccumulateDef()) {
			return false;
		}
		// 初始化业务数据提取类算法
		if (!initDistillCalSet()) {
			return false;
		}
		return true;
	}

	// 初始化累计风险
	private boolean initRIAccumulateDef() {
		RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
		tRIAccumulateDefDB.setAccumulateDefNO(mAccumulateDefNo);
		System.out.println(" mAccumulateDefNo: " + mAccumulateDefNo);
		if (!tRIAccumulateDefDB.getInfo()) {
			buildError("InitInfo", "初始化累计风险编码为：" + mAccumulateDefNo
					+ " 的累计风险时为空");
			return false;
		}
		if (tRIAccumulateDefDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo + " 的累计风险时出错");
			return false;
		}
		mRIAccumulateDefSchema = tRIAccumulateDefDB.getSchema();
		return true;
	}

	// 初始化数据提取算法
	private boolean initDistillCalSet() {
		RICalDefDB tRICalDefDB = new RICalDefDB();
		tRICalDefDB
				.setArithmeticDefID(mRIAccumulateDefSchema.getArithmeticID());
		if (!tRICalDefDB.getInfo()) {
			buildError("InitInfo", "初始化累计风险为：" + mAccumulateDefNo
					+ " 的业务数据提取算法为空。");
			return false;
		}
		RICalDefSchema tRICalDefSchema = tRICalDefDB.getSchema();
		RIItemCalDB tRIItemCalDB = new RIItemCalDB();
		String tSQL = " select * from RIItemCal where ArithmeticType='01' and ArithmeticDefID='"
				+ tRICalDefSchema.getArithmeticDefID()
				+ "' order by CalItemOrder ";
		System.out.println("数据提取算法: " + tSQL);
		riDistillCalSet = tRIItemCalDB.executeQuery(tSQL);
		System.out.println(" aa:　" + riDistillCalSet.size());
		if (tRIItemCalDB.mErrors.needDealError()) {
			buildError("InitInfo",
					"初始化累计风险编号为：" + mRIAccumulateDefSchema.getAccumulateDefNO()
							+ " 的数据提取类算法出错");
			return false;
		}
		return true;
	}

	/**
	 * 返回累积方案类别编码
	 * 
	 * @return String
	 */
	public String getRIAccumulateDefNo() {
		return mAccumulateDefNo;
	}

	/**
	 * 返回累积风险定义
	 * 
	 * @return String
	 */
	public RIAccumulateDefSchema getRIAccumulateDefSchema() {
		return mRIAccumulateDefSchema;
	}

	/**
	 * 返回累积风险下的业务数据提取算法
	 * 
	 * @return TransferData。其中：Name为累积风险编号，Value为该方案下数据检验算法
	 */
	public RIItemCalSet getRIDistillCalSet() {
		System.out.println(" bb: " + riDistillCalSet.size());
		return riDistillCalSet;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "InitInfo";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}
}
