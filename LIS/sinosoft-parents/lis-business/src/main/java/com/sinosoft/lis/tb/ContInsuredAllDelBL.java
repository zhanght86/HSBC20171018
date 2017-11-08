package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团险删除全部被保人
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
 * Company: sinosoft
 * </p>
 * 
 * @author chenrong
 * @version 6.0
 */
public class ContInsuredAllDelBL {
private static Logger logger = Logger.getLogger(ContInsuredAllDelBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private String mGrpContNo = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();
	LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	public ContInsuredAllDelBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	public boolean submitData(VData cInputData) {
		this.mInputData = (VData) cInputData.clone();

		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			this.buildError("数据提交失败!", "SubmitData");
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 取得传输过来的数据
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (this.mGlobalInput == null) {
			this.buildError("未获得全局信息！", "getInputData");
			return false;
		}

		mLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));
		if (this.mLCGrpContSchema == null) {
			this.buildError("没有传集体保单信息！", "getInputData");
			return false;
		}
		mGrpContNo = mLCGrpContSchema.getGrpContNo();
		if (mGrpContNo == null || "".equals(mGrpContNo)) {
			this.buildError("没有传入集体保单的保单号码！", "getInputData");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		try {
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mGrpContNo);
			if (tLCGrpContDB.getInfo()) {
				mLCGrpContSchema.setSchema(tLCGrpContDB.getSchema());
				return true;
			} else {
				this.buildError("查询集体保单失败！", "checkData");
				return false;
			}
		} catch (Exception ex) {
			this.buildError("数据库查询失败！", "checkData");
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tSQL = "";
		tSQL = "(select insuredno from lccont where poltype='0' and conttype='2' "
				+ "and grpcontno='" + "?mGrpContNo?" + "')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("delete from lcinsured where grpcontno='" + "?mGrpContNo?"
				+ "' and insuredno in " + tSQL);
		sqlbv.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv, "DELETE");
		// 删除告知参数表
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("delete from LCCustomerImpartParams where GrpContNo='"
				+ "?mGrpContNo?" + "' and CustomerNo in " + tSQL
				+ " and CustomerNoType = '1'");
		sqlbv1.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv1, "DELETE");
		// 删除告知表
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("delete from LCCustomerImpart where GrpContNo='" + "?mGrpContNo?"
				+ "' and CustomerNo in " + tSQL + " and CustomerNoType = '1'");
		sqlbv2.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv2, "DELETE");
		// 删除告知明细表
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("delete from LCCustomerImpartDetail where GrpContNO='"
				+ "?mGrpContNo?" + "' and CustomerNo in " + tSQL
				+ " and CustomerNoType" + " = '1'");
		sqlbv3.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv3, "DELETE");

		tSQL = "(select polno from lcpol where poltypeflag='0' and"
				+ " conttype='2' and grpcontno='" + "?mGrpContNo?" + "')";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("delete from LCDuty where polno in " + tSQL);
		sqlbv4.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv4, "DELETE");
		
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("delete from LCPrem where polno in " + tSQL);
		sqlbv5.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv5, "DELETE");
		
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("delete from LCGet where polno in " + tSQL);
		sqlbv6.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv6, "DELETE");

		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("delete from lcpol where poltypeflag='0' and"
				+ " conttype='2' and grpcontno='" + "?mGrpContNo?" + "'");
		sqlbv7.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv7, "DELETE");
		
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("delete from lccont where poltype='0' and"
				+ " conttype='2' and grpcontno='" + "?mGrpContNo?" + "'");
		sqlbv8.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv8, "DELETE");
		
		// 更新集体险种信息
		String fromPart = "from LCPol where GrpContNo='" + "?mGrpContNo?"
				+ "' and riskcode = LCGrpPol.riskcode)";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("update LCGrpPol set " + "Prem=(select (case when SUM(Prem) is null then 0 else SUM(Prem) end) "
				+ fromPart + ",Amnt=(select (case when SUM(Amnt) is null then 0 else SUM(Amnt) end) " + fromPart
				+ ",SumPrem=(select (case when SUM(SumPrem) is null then 0 else SUM(SumPrem) end) " + fromPart
				+ ",Mult=(select (case when SUM(Mult) is null then 0 else SUM(Mult) end) " + fromPart
				+ ",Peoples2=(select (case when SUM(InsuredPeoples) is null then 0 else SUM(InsuredPeoples) end) " + fromPart
				+ " where grpcontno='" + "?mGrpContNo?" + "'");
		sqlbv9.put("mGrpContNo", mGrpContNo);
		mMap.put(sqlbv9, "UPDATE");
		// 集体保单信息
		fromPart = "from LCPol where GrpContNo='" + "?mGrpContNo?" + "')";
		String updateLCGrpContStr = "update LCGrpCont set "
				+ "Prem=(select SUM(Prem) " + fromPart
				+ ",Amnt=(select SUM(Amnt) " + fromPart
				+ ",SumPrem=(select SUM(SumPrem) " + fromPart
				+ ",Mult=(select SUM(Mult) " + fromPart
				+ ",Peoples2=(select SUM(Peoples) "
				+ "from lccont where grpcontno='" + "?mGrpContNo?" + "')"
				+ " where grpcontno='" + "?mGrpContNo?" + "'";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(updateLCGrpContStr);
		
		mMap.put(sqlbv10, "UPDATE");
		return true;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mMap);
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			this.buildError("在准备往后层处理所需要的数据时出错。", "prepareOutputData");
			return false;
		}
		return true;
	}

	private void buildError(String mMsg, String mFunctionName) {
		CError tError = new CError();
		tError.moduleName = "ContInsuredAllDelBL";
		tError.functionName = mFunctionName;
		tError.errorMessage = mMsg;
		this.mErrors.addOneError(tError);
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		ContInsuredAllDelBL continsuredalldelbl = new ContInsuredAllDelBL();
	}
}
