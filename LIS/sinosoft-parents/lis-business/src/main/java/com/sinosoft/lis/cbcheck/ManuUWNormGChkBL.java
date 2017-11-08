package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人人工核保部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class ManuUWNormGChkBL {
private static Logger logger = Logger.getLogger(ManuUWNormGChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator; // 操作员

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mPolNo = "";
	private String mUWFlag = ""; // 核保标志
	private Date mvalidate = null;
	private String mUWIdea = ""; // 核保结论
	private String mUPUWCode = "";
	private String mpostday; // 延长天数
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别
	private String mReason = ""; // 加费原因
	private String mSpecReason = ""; // 特约原因

	private GlobalInput mGlobalInput = new GlobalInput();

	public ManuUWNormGChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---ManuUWNormGChkBL getInputData Begin ---");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---ManuUWNormGChkBL getInputData End ---");

		// 校验核保级别
		logger.debug("---ManuUWNormGChkBL checkUWGrade Begin---");

		if (!checkUWGrade()) {
			return false;
		}

		// 校验主附险
		logger.debug("校验主附险" + mUWFlag);

		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理
		logger.debug("数据操作业务处理" + mUWFlag);

		if (!dealData()) {
			return false;
		}

		logger.debug("---ManuUWNormGChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();
		logger.debug("---ManuUWNormGChkBL prepareOutputData---");

		// 数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!cOperate.equals("submit")) {

			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "ManuUWNormGChkBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean checkData() {
		// 校验合同单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);

		LCContSet tLCContSet = tLCContDB.query();

		if ((tLCContSet == null) || (tLCContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWGrpManuAddChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 校验团单信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);

		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

		if ((tLCGrpContSet == null) || (tLCGrpContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWGrpManuAddChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("begin dealOnePol()");
		if (preparePol() == false) {
			return false;
		}
		logger.debug("---dealOnePol end---");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = mGlobalInput.Operator;

		// 取投保单
		mLCPolSet.set((LCPolSet) cInputData
				.getObjectByObjectName("LCPolSet", 0));

		int n = mLCPolSet.size();

		if (n == 1) {
			LCPolSchema tLCPolSchema = mLCPolSet.get(1);
			mUWFlag = tLCPolSchema.getUWFlag();
			mPolNo = tLCPolSchema.getPolNo();
			mUWIdea = tLCPolSchema.getRemark();

			// 校验是不是以下核保结论
			if ((mUWFlag == null) || mUWFlag.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ManuUWNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有选择核保结论";
				this.mErrors.addOneError(tError);

				return false;
			}

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mPolNo);
			logger.debug("--保单表中的保单号:  " + mPolNo);

			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);

				CError tError = new CError();
				tError.moduleName = "ManuUWNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mPolNo + "投保单查询失败!";
				this.mErrors.addOneError(tError);

				return false;
			} else {
				mLCPolSchema.setSchema(tLCPolDB);
				mContNo = mLCPolSchema.getContNo();
				mGrpContNo = mLCPolSchema.getGrpContNo();
			}

		} else {
			return false;
		}

		return true;
	}

	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "ManuUWNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tUWPopedom;
		mAppGrade = mUWPopedom;
		logger.debug("tUWPopedom" + tUWPopedom);

		return true;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		logger.debug("preparePol ----- Begin");
		// 拒保退费
		if (mUWFlag.equals("1")) {
			String tSQL = "update lcgrpcont set prem=(select sum(prem) from lcpol "
					+ "where grpcontno='"
					+ "?grpcontno?"
					+ "' and riskcode<>'"
					+ "?riskcode?"
					+ "' and uwflag<>'1')"
					+ ",operator='"
					+ "?operator?"
					+ "',modifydate='"
					+ "?modifydate?"
					+ "',modifytime='"
					+ "?modifytime?"
					+ "' where grpcontno='" + "?grpcontno?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("grpcontno", mLCPolSchema.getGrpContNo());
			sqlbv.put("riskcode", mLCPolSchema.getRiskCode());
			sqlbv.put("operator", mOperator);
			sqlbv.put("modifydate", PubFun.getCurrentDate());
			sqlbv.put("modifytime", PubFun.getCurrentTime());
			mMap.put(sqlbv, "UPDATE");
		}

		VData tVData = new VData();
		VData tResult = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", mLCPolSchema.getGrpContNo());
		tTransferData.setNameAndValue("ContNo", mLCPolSchema.getContNo());
		tTransferData.setNameAndValue("RiskCode", mLCPolSchema.getRiskCode());
		tTransferData.setNameAndValue("UWFlag", mUWFlag);
		tTransferData.setNameAndValue("UWIdea", mUWIdea);
		tTransferData.setNameAndValue("ContType", "2");
		tTransferData.setNameAndValue("PostDay", mpostday);
		tTransferData.setNameAndValue("ValiDate", mvalidate);
		tTransferData.setNameAndValue("Reason", mReason);
		tTransferData.setNameAndValue("SpecReason", mSpecReason);
		tTransferData.setNameAndValue("AppGrade", mAppGrade);
		tTransferData.setNameAndValue("UPUWCode", mUPUWCode);
		tVData.add(tTransferData);
		tVData.add(mGlobalInput);

		// 对险种保单下核保结论
		UWManuNormPolChkBL tUWManuNormPolChkBL = new UWManuNormPolChkBL();
		if (!tUWManuNormPolChkBL.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tUWManuNormPolChkBL.mErrors);
			return false;
		}
		tResult = tUWManuNormPolChkBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		logger.debug("tUWManuNormPolChkBL ----- End");

		// 对保单下核保结论
		UWManuNormContChkBL tUWManuNormContChkBL = new UWManuNormContChkBL();
		if (!tUWManuNormContChkBL.submitData(tVData, "")) {
			this.mErrors.copyAllErrors(tUWManuNormContChkBL.mErrors);
			return false;
		}
		tResult = tUWManuNormContChkBL.getResult();
		mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));

		logger.debug("mMap..Size=" + mMap.toString());

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
	}
}
