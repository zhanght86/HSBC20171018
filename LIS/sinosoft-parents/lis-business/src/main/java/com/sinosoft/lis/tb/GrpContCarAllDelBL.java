package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
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
 * @version 6.0
 */
public class GrpContCarAllDelBL {
private static Logger logger = Logger.getLogger(GrpContCarAllDelBL.class);
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

	public GrpContCarAllDelBL() {
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
		tSQL = "(select distinct contno from lcpolother where grpcontno='"
				+ mGrpContNo + "')";

		mMap.put("delete from lcinsured where grpcontno='" + mGrpContNo
				+ "' and contno in " + tSQL, "DELETE");
		mMap.put("delete from LCDuty where contno in " + tSQL, "DELETE");
		mMap.put("delete from LCPrem where contno in " + tSQL, "DELETE");
		mMap.put("delete from LCGet where contno in " + tSQL, "DELETE");

		mMap.put("delete from lcpol where grpcontno='" + mGrpContNo
				+ "' and contno in " + tSQL, "DELETE");
		mMap.put("delete from lccont where grpcontno='" + mGrpContNo
				+ "' and contno in " + tSQL, "DELETE");
		mMap.put("delete from lcpolother where grpcontno='" + mGrpContNo + "'",
				"DELETE");
		// 更新集体险种信息
		String fromPart = "from LCPol where GrpContNo='" + mGrpContNo
				+ "' and riskcode = LCGrpPol.riskcode)";
		mMap.put("update LCGrpPol set " + "Prem=(select nvl(SUM(Prem),0) "
				+ fromPart + ",Amnt=(select nvl(SUM(Amnt),0) " + fromPart
				+ ",SumPrem=(select nvl(SUM(SumPrem),0) " + fromPart
				+ ",Mult=(select nvl(SUM(Mult),0) " + fromPart
				+ ",Peoples2=(select nvl(SUM(InsuredPeoples),0) " + fromPart
				+ " where grpcontno='" + mGrpContNo + "'", "UPDATE");
		// 集体保单信息
		fromPart = "from LCPol where GrpContNo='" + mGrpContNo + "')";
		String updateLCGrpContStr = "update LCGrpCont set "
				+ "Prem=(select SUM(Prem) " + fromPart
				+ ",Amnt=(select SUM(Amnt) " + fromPart
				+ ",SumPrem=(select SUM(SumPrem) " + fromPart
				+ ",Mult=(select SUM(Mult) " + fromPart
				+ ",Peoples2=(select SUM(Peoples) "
				+ "from lccont where grpcontno='" + mGrpContNo + "')"
				+ " where grpcontno='" + mGrpContNo + "'";
		mMap.put(updateLCGrpContStr, "UPDATE");
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
		GrpContCarAllDelBL grpcontcaralldelbl = new GrpContCarAllDelBL();
	}
}
