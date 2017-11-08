package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class UWBatchManuNormGChkBL {
private static Logger logger = Logger.getLogger(UWBatchManuNormGChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator; // 操作员
	private String mManageCom;
	private String mpassflag; // 通过标记
	private String mContPassFlag = "";
	private String mBackFlag;
	private String mCalCode; // 计算编码
	private String mUser;
	private double mValue;
	private double mprem;

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();
	private LCContSet mAllLCContSet = new LCContSet();
	private LCContSchema mLCContSchema = new LCContSchema();

	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet mAllLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
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
	private String mQuesFlag = ""; // 是否有问题件标记
	private String mQuesOrgFlag = ""; // 机构问题件标记
	private String mQuesOpeFlag = ""; // 操作员问题件标记
	private String mPolType = ""; // 保单类型
	private String mAgentPrintFlag = ""; // 是否有返回业务员要打印的问题件标记
	private String mAgentQuesFlag = ""; // 是否有返回业务员问题件标记
	private boolean mChgContMaster = false; // 是否更新LCCUWMaster

	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();

	/** 核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();

	/** 计算公式表* */
	private LMUWSchema mLMUWSchema = new LMUWSchema();
	private LMUWSet mLMUWSet = new LMUWSet();
	private CalBase mCalBase = new CalBase();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mGetNoticeNo = "";

	public UWBatchManuNormGChkBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---UWContManuNormGChkBL getInputData Begin ---");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("---UWContManuNormGChkBL getInputData End ---");

		// 校验该投保单是否已复核过,是则可进行核保,否则不能进行
		if (!checkApprove()) {
			return false;
		}

		// 校验核保级别
		logger.debug("---UWContManuNormGChkBL checkUWGrade Begin---");

		if (!checkUWGrade()) {
			return false;
		}

		logger.debug("---UWContManuNormGChkBL checkUWGrade End---");

		// 数据操作业务处理
		logger.debug("数据操作业务处理" + mUWFlag);

		if (dealData() == false) {
			return false;
		}

		logger.debug("---UWContManuNormGChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData();
		logger.debug("---UWContManuNormGChkBL prepareOutputData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */

	private boolean dealData() {
		logger.debug("IN dealData()" + mUWFlag);
		if (prepareCont() == false) {
			return false;
		}

		if ((mLCContSet == null) || (mLCContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWBatchManuNormGChkBL";
			tError.functionName = "dealData";
			tError.errorMessage = "合同" + mGrpContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = mLCContSet.size();
		for (int i = 1; i <= n; i++) {
			mLCContSchema = mLCContSet.get(i);
			if (preparePol() == false) {
				if (mLCPolSet.size() == 0) {
					// 如果无单个险种记录，则调用UWContManuNormGChkBL.java
					if (callCont() == false) {
						return false;
					}
				} else {
					return false;
				}
			}

			// 调用核保程序 ---UWManuNormGChkBL.java
			if (callPol() == false) {
				return false;
			}
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		String getflag = "true"; // 判断承保条件是否接收

		GlobalInput tGlobalInput = new GlobalInput();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 取团保单号
		mLCGrpContSet.set((LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0));

		int n = mLCGrpContSet.size();
		if (n == 1) {
			LCGrpContSchema tLCGrpContSchema = mLCGrpContSet.get(1);
			mUWFlag = tLCGrpContSchema.getUWFlag();
			mGrpContNo = tLCGrpContSchema.getGrpContNo();
			mUWIdea = tLCGrpContSchema.getRemark();
			mUWFlag = tLCGrpContSchema.getUWFlag();
			// 校
			if ((mUWFlag == null) || mUWFlag.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWBatchManuNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "没有相应团单号";
				this.mErrors.addOneError(tError);

				return false;
			}

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mGrpContNo);
			logger.debug("--保单表中的合同单号:  " + mGrpContNo);

			if (tLCGrpContDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);

				CError tError = new CError();
				tError.moduleName = "UWBatchManuNormGChkBL";
				tError.functionName = "getInputData";
				tError.errorMessage = mGrpContNo + "团投保单查询失败!";
				this.mErrors.addOneError(tError);

				return false;
			} else {
				mLCGrpContSchema.setSchema(tLCGrpContDB);
				mGrpContNo = mLCGrpContSchema.getGrpContNo();
			}

			// 判断是团单下个单还是个人单
			if (!mLCGrpContSchema.getGrpContNo().equals("00000000000000000000")) {
				mPolType = "2";
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove() {
		if ((mLCGrpContSchema.getApproveCode() == null)
				|| mLCGrpContSchema.getApproveDate().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWBatchManuNormGChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + mContNo.trim()
					+ "）";
			this.mErrors.addOneError(tError);

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
			tError.moduleName = "UWContManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		mUWPopedom = tUWPopedom;
		mAppGrade = mUWPopedom;
		logger.debug("tUWPopedom" + tUWPopedom);

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "UWBatchManuNormGChkBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	private void prepareOutputData() {
		mMap.put(mAllLCPolSet, "UPDATE");
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 准备 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareCont() {
		String tsql = "select * from LCCont where  GrpContNo='" + "?GrpContNo?"
				+ "' and UWFlag not in ('0', '1', '4', '9')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("GrpContNo", mGrpContNo);
		LCContDB tLCContDB = new LCContDB();
		mLCContSet = tLCContDB.executeQuery(sqlbv);
		if ((mLCContSet == null) || (mLCContSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWBatchManuNormGChkBL";
			tError.functionName = "prepareCont";
			tError.errorMessage = "合同" + mGrpContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	private boolean preparePol() {
		String t1sql = "select * from LCPol where  GrpContNo='" + "?GrpContNo?"
				+ "' and UWFlag not in ('0', '1', '4', '9')" + " and ContNo ='"
				+ "?ContNo?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(t1sql);
		sqlbv1.put("GrpContNo", mGrpContNo);
		sqlbv1.put("ContNo", mLCContSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		mLCPolSet = tLCPolDB.executeQuery(sqlbv1);
		if ((mLCPolSet == null) || (mLCPolSet.size() <= 0)) {
			CError tError = new CError();
			tError.moduleName = "UWBatchManuNormGChkBL";
			tError.functionName = "preparePol";
			tError.errorMessage = "合同" + mLCContSchema.getContNo() + "信息查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 准备调用个人单自动核保程序－－－UWContManuNormGChkBL.java 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean callCont() {
		GlobalInput tG = new GlobalInput();
		tG.Operator = mOperator;
		tG.ManageCom = mManageCom;
		LCContSet tLCContSet = new LCContSet();
		mLCContSchema.setUWFlag(mUWFlag);
		tLCContSet.add(mLCContSchema);
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tG);
		UWContManuNormGChkBL tUWContManuNormGChkBL = new UWContManuNormGChkBL();
		if (tUWContManuNormGChkBL.submitData(tVData, "") == false) {
			CError tError = new CError();
			tError.moduleName = "UWBatchManuNormGChkBL";
			tError.functionName = "callCont";
			tError.errorMessage = "调用错误UWContManuNormGChkBL 操作员：" + mOperator
					+ "）";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备调用个保单（Pol）自动核保程序－－－UWManuNormGChkBL.java 输出：如果发生错误则返回false,否则返回true
	 */

	private boolean callPol() {
		int m = mLCPolSet.size();
		for (int j = 1; j <= m; j++) {

			GlobalInput tG = new GlobalInput();
			tG.Operator = mOperator;
			tG.ManageCom = mManageCom;

			LCPolSchema tLCPolSchema = mLCPolSet.get(j);
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSchema.setUWFlag(mUWFlag);
			logger.debug("*****************************  " + mUWFlag
					+ " PolNo:  " + tLCPolSchema.getPolNo());
			tLCPolSet.add(tLCPolSchema);

			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tLCPolSet);
			tVData.add(tG);
			UWManuNormGChkBL tUWManuNormGChkBL = new UWManuNormGChkBL();
			if (tUWManuNormGChkBL.submitData(tVData, "") == false) {
				CError tError = new CError();
				tError.moduleName = "UWBatchManuNormGChkBL";
				tError.functionName = "callPol";
				tError.errorMessage = "调用错误UWManuNormGChkBL 操作员：" + mOperator
						+ "）";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

}
