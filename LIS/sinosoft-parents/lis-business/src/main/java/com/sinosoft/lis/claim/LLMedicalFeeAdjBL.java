package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLCaseInfoDB;
import com.sinosoft.lis.db.LLCaseReceiptDB;
import com.sinosoft.lis.db.LLClaimDutyFeeDB;
import com.sinosoft.lis.db.LLOperationDB;
import com.sinosoft.lis.db.LLOtherFactorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLClaimUWDutyFeeSchema;
import com.sinosoft.lis.vschema.LLCaseInfoSet;
import com.sinosoft.lis.vschema.LLCaseReceiptSet;
import com.sinosoft.lis.vschema.LLClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LLClaimUWDutyFeeSet;
import com.sinosoft.lis.vschema.LLOperationSet;
import com.sinosoft.lis.vschema.LLOtherFactorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 医疗费用调整显示信息
 * </p>
 * <p>
 * Description: 医疗费用调整显示信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author quyang
 * @version 1.0
 */

public class LLMedicalFeeAdjBL {
private static Logger logger = Logger.getLogger(LLMedicalFeeAdjBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private MMap mMap = new MMap();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();

	private LLClaimDutyFeeSchema mLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema(); // 责任费用统计
	private LLClaimDutyFeeSet mLLClaimDutyFeeSet = new LLClaimDutyFeeSet(); // 责任费用统计集合
	private LLClaimDutyFeeDB mLLClaimDutyFeeDB = new LLClaimDutyFeeDB(); // 责任费用统计数据
	private LLClaimUWDutyFeeSet mLLClaimUWDutyFeeSet = new LLClaimUWDutyFeeSet(); // 核赔责任费用统计集合

	private LLCaseReceiptSet mLLCaseReceiptSet = new LLCaseReceiptSet();
	private LLCaseInfoSet mLLCaseInfoSet = new LLCaseInfoSet();
	private LLCaseInfoSet mLLOperationSet = new LLCaseInfoSet();
	private LLOtherFactorSet mLLOtherFactorSet = new LLOtherFactorSet();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;

	public LLMedicalFeeAdjBL() {
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
			tError.moduleName = "LLMedicalFeeAdjBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		logger.debug("--start getInputData()");
		// 获取页面信息
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLLClaimDutyFeeSchema = (LLClaimDutyFeeSchema) cInputData
				.getObjectByObjectName("LLClaimDutyFeeSchema", 0);

		if (mLLClaimDutyFeeSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMedicalFeeAdjBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLMedicalFeeAdjBL";
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
			tError.moduleName = "LLMedicalFeeAdjBL";
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
			tError.moduleName = "LLMedicalFeeAdjBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
			this.mErrors.addOneError(tError);
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
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!prepareMedicalFeeAdj()) {
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean prepareMedicalFeeAdj() {
		// 准备数据
		String dutyFeeType = "";
		String clmNo = "";
		String dutyFeeCode = "";
		String dutyFeeStaNo = "";
		double adjSum = 0;
		String adjReason = "";
		String adjRemark = "";
		double realRate = 0;
		String adjReason1 = "";
		String adjRemark1 = "";
		double adjSum1 = 0;
		String adjReason2 = "";
		String adjRemark2 = "";

		// 需要的参数
		clmNo = mLLClaimDutyFeeSchema.getClmNo();
		dutyFeeType = mLLClaimDutyFeeSchema.getDutyFeeType();
		dutyFeeCode = mLLClaimDutyFeeSchema.getDutyFeeCode();
		dutyFeeStaNo = mLLClaimDutyFeeSchema.getDutyFeeStaNo();

		if ("A".equalsIgnoreCase(dutyFeeType)
				|| "B".equalsIgnoreCase(dutyFeeType)) {
			adjSum = mLLClaimDutyFeeSchema.getAdjSum();
			adjReason = mLLClaimDutyFeeSchema.getAdjReason();
			adjRemark = mLLClaimDutyFeeSchema.getAdjRemark();
		} else if ("C".equalsIgnoreCase(dutyFeeType)) {
			realRate = mLLClaimDutyFeeSchema.getRealRate();
			adjReason1 = mLLClaimDutyFeeSchema.getAdjReason();
			adjRemark1 = mLLClaimDutyFeeSchema.getAdjRemark();
		} else if ("F".equalsIgnoreCase(dutyFeeType)
				|| "D".equalsIgnoreCase(dutyFeeType)
				|| "E".equalsIgnoreCase(dutyFeeType)) {
			adjSum1 = mLLClaimDutyFeeSchema.getAdjSum();
			adjReason2 = mLLClaimDutyFeeSchema.getAdjReason();
			adjRemark2 = mLLClaimDutyFeeSchema.getAdjRemark();
		}

		// ----------------------------------------------------------------------BEG
		// 复制责任费用统计表到核赔责任费用统计
		// ----------------------------------------------------------------------
		String sql = "select * from LLClaimDutyFee where 1=1 "
				+ " and ClmNo = '" + "?clmNo?" + "'" + " and DutyFeeType = '"
				+ "?dutyFeeType?" + "'" + " and DutyFeeCode = '" + "?dutyFeeCode?"
				+ "'" + " and DutyFeeStaNo = '" + "?dutyFeeStaNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("clmNo", clmNo);
		sqlbv.put("dutyFeeType", dutyFeeType);
		sqlbv.put("dutyFeeCode", dutyFeeCode);
		sqlbv.put("dutyFeeStaNo", dutyFeeStaNo);
		mLLClaimDutyFeeSet = mLLClaimDutyFeeDB.executeQuery(sqlbv);

		LLClaimUWDutyFeeSchema tLLClaimUWDutyFeeSchema = new LLClaimUWDutyFeeSchema();
		mLLClaimUWDutyFeeSet.add(tLLClaimUWDutyFeeSchema);
		mReflections.transFields(mLLClaimUWDutyFeeSet, mLLClaimDutyFeeSet);

		String clmUWNo = "";
		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		clmUWNo = PubFun1.CreateMaxNo("EditNo", tLimit); // 生成核赔次数

		for (int i = 1; i <= mLLClaimUWDutyFeeSet.size(); i++) {
			mLLClaimUWDutyFeeSet.get(i).setClmUWNo(clmUWNo);
		}
		mMap.put(mLLClaimUWDutyFeeSet, "INSERT");
		// ----------------------------------------------------------------------END

		// ----------------------------------------------------------------------BEG
		// 插入调整后数据,更新责任费用统计表
		// ----------------------------------------------------------------------
		for (int i = 1; i <= mLLClaimDutyFeeSet.size(); i++) {
			if ("A".equalsIgnoreCase(dutyFeeType)
					|| "B".equalsIgnoreCase(dutyFeeType)) {
				mLLClaimDutyFeeSet.get(i).setAdjSum(adjSum);
				mLLClaimDutyFeeSet.get(i).setAdjReason(adjReason);
				mLLClaimDutyFeeSet.get(i).setAdjRemark(adjRemark);
			} else if ("C".equalsIgnoreCase(dutyFeeType)) {
				mLLClaimDutyFeeSet.get(i).setRealRate(realRate);
				mLLClaimDutyFeeSet.get(i).setAdjReason(adjReason1);
				mLLClaimDutyFeeSet.get(i).setAdjRemark(adjRemark1);
			} else if ("D".equalsIgnoreCase(dutyFeeType)
					|| "E".equalsIgnoreCase(dutyFeeType)) {
				mLLClaimDutyFeeSet.get(i).setAdjSum(adjSum1);
				mLLClaimDutyFeeSet.get(i).setAdjReason(adjReason2);
				mLLClaimDutyFeeSet.get(i).setAdjRemark(adjRemark2);
			} else if ("F".equalsIgnoreCase(dutyFeeType)) {
				mLLClaimDutyFeeSet.get(i).setAdjSum(adjSum1);
				mLLClaimDutyFeeSet.get(i).setAdjReason(adjReason2);
				mLLClaimDutyFeeSet.get(i).setAdjRemark(adjRemark2);
			}

			mLLClaimDutyFeeSet.get(i).setMakeDate(CurrentDate);
			mLLClaimDutyFeeSet.get(i).setMakeTime(CurrentTime);
			mLLClaimDutyFeeSet.get(i).setMngCom(mGlobalInput.ManageCom);
			mLLClaimDutyFeeSet.get(i).setModifyDate(CurrentDate);
			mLLClaimDutyFeeSet.get(i).setModifyTime(CurrentTime);
			mLLClaimDutyFeeSet.get(i).setOperator(mGlobalInput.Operator);
		}
		// ----------------------------------------------------------------------BEG

		// ----------------------------------------------------------------------BEG
		// 插入调整后数据,更新费用录入表
		// ----------------------------------------------------------------------
		// 门诊住院
		if ("A".equalsIgnoreCase(dutyFeeType)
				|| "B".equalsIgnoreCase(dutyFeeType)) {
			LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
			LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
			String tSelectSql = "";
			tSelectSql = "select * from LLCaseReceipt where 1=1 "
					+ " and ClmNo = '" + "?ClmNo?" + "'" + " and FeeItemCode = '"
					+ "?FeeItemCode?" + "'" + " and FeeDetailNo = '" + "?FeeDetailNo?"
					+ "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSelectSql);
			sqlbv1.put("ClmNo", clmNo);
			sqlbv1.put("FeeItemCode", dutyFeeCode);
			sqlbv1.put("FeeDetailNo", dutyFeeStaNo);
			tLLCaseReceiptSet = tLLCaseReceiptDB.executeQuery(sqlbv1);
			for (int j = 1; j <= tLLCaseReceiptSet.size(); j++) {
				tLLCaseReceiptSet.get(j).setAdjReason(adjReason);
				tLLCaseReceiptSet.get(j).setAdjRemark(adjRemark);
				tLLCaseReceiptSet.get(j).setAdjSum(adjSum);
				tLLCaseReceiptSet.get(j).setModifyDate(CurrentDate);
				tLLCaseReceiptSet.get(j).setModifyTime(CurrentTime);
			}
			mLLCaseReceiptSet.add(tLLCaseReceiptSet);
		}
		// 伤残
		else if ("C".equalsIgnoreCase(dutyFeeType)) {
			LLCaseInfoDB tLLCaseInfoDB = new LLCaseInfoDB();
			LLCaseInfoSet tLLCaseInfoSet = new LLCaseInfoSet();
			String tSelectSql = "";
			tSelectSql = "select * from LLCaseInfo where 1=1 "
					+ " and ClmNo = '" + "?ClmNo?" + "'" + " and DefoCode = '"
					+ "?DefoCode?" + "'" + " and SerialNo = '" + "?SerialNo?"
					+ "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSelectSql);
			sqlbv2.put("ClmNo", clmNo);
			sqlbv2.put("DefoCode", dutyFeeCode);
			sqlbv2.put("SerialNo", dutyFeeStaNo);
			tLLCaseInfoSet = tLLCaseInfoDB.executeQuery(sqlbv2);
			for (int j = 1; j <= tLLCaseInfoSet.size(); j++) {
				tLLCaseInfoSet.get(j).setAdjReason(adjReason);
				tLLCaseInfoSet.get(j).setAdjRemark(adjRemark);
				tLLCaseInfoSet.get(j).setRealRate(realRate);
				tLLCaseInfoSet.get(j).setModifyDate(CurrentDate);
				tLLCaseInfoSet.get(j).setModifyTime(CurrentTime);
			}
			mLLCaseInfoSet.add(tLLCaseInfoSet);
		}
		// 手术疾病
		else if ("D".equalsIgnoreCase(dutyFeeType)
				|| "E".equalsIgnoreCase(dutyFeeType)) {
			LLOperationDB tLLOperationDB = new LLOperationDB();
			LLOperationSet tLLOperationSet = new LLOperationSet();
			String tSelectSql = "";
			tSelectSql = "select * from LLOperation where 1=1 "
					+ " and ClmNo = '" + "?ClmNo?" + "'" + " and OperationCode = '"
					+ "?dutyFeeCode?" + "'" + " and SerialNo = '" + "?SerialNo?"
					+ "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSelectSql);
			sqlbv3.put("ClmNo", clmNo);
			sqlbv3.put("dutyFeeCode", dutyFeeCode);
			sqlbv3.put("SerialNo", dutyFeeStaNo);
			tLLOperationSet = tLLOperationDB.executeQuery(sqlbv3);
			for (int j = 1; j <= tLLOperationSet.size(); j++) {
				tLLOperationSet.get(j).setAdjReason(adjReason2);
				tLLOperationSet.get(j).setAdjRemark(adjRemark2);
				tLLOperationSet.get(j).setAdjSum(adjSum1);
				tLLOperationSet.get(j).setModifyDate(CurrentDate);
				tLLOperationSet.get(j).setModifyTime(CurrentTime);
			}
			mLLOperationSet.add(tLLOperationSet);
		}
		// 其他
		else if ("F".equalsIgnoreCase(dutyFeeType)) {
			LLOtherFactorDB tLLOtherFactorDB = new LLOtherFactorDB();
			LLOtherFactorSet tLLOtherFactorSet = new LLOtherFactorSet();
			String tSelectSql = "";
			tSelectSql = "select * from LLOtherFactor where 1=1 "
					+ " and ClmNo = '" + "?ClmNo?" + "'" + " and FactorCode = '"
					+ "?dutyFeeCode?" + "'" + " and SerialNo = '" + "?SerialNo?"
					+ "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSelectSql);
			sqlbv4.put("ClmNo", clmNo);
			sqlbv4.put("dutyFeeCode", dutyFeeCode);
			sqlbv4.put("SerialNo", dutyFeeStaNo);
			tLLOtherFactorSet = tLLOtherFactorDB.executeQuery(sqlbv4);
			for (int j = 1; j <= tLLOtherFactorSet.size(); j++) {
				tLLOtherFactorSet.get(j).setAdjReason(adjReason2);
				tLLOtherFactorSet.get(j).setAdjRemark(adjRemark2);
				tLLOtherFactorSet.get(j).setAdjSum(adjSum1);
				tLLOtherFactorSet.get(j).setModifyDate(CurrentDate);
				tLLOtherFactorSet.get(j).setModifyTime(CurrentTime);
			}
			mLLOtherFactorSet.add(tLLOtherFactorSet);
		}

		mMap.put(mLLCaseReceiptSet, "DELETE&INSERT");
		mMap.put(mLLCaseInfoSet, "DELETE&INSERT");
		mMap.put(mLLOperationSet, "DELETE&INSERT");
		mMap.put(mLLOtherFactorSet, "DELETE&INSERT");
		mMap.put(mLLClaimDutyFeeSet, "DELETE&INSERT");
		// ----------------------------------------------------------------------BEG
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
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
