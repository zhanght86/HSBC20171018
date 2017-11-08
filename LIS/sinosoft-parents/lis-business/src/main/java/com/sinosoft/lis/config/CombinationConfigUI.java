package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LDPlanDutyParamSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class CombinationConfigUI {
private static Logger logger = Logger.getLogger(CombinationConfigUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LDPlanDutyParamSet mLDPlanDutyParamSet = new LDPlanDutyParamSet();
	private String mContPlanCode = "";
	private String mPlanSql = "";
	private String mPlanType = "";
	private String mPeoples3 = "";
	private String mRemark = "";
	private String mPlanKind1 = "";
	private String mPlanKind2 = "";
	private String mPlanKind3 = "";
	private String mManageCom = "";
	private String mPortfolioflag = "";

	public CombinationConfigUI() {
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
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
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
		CombinationConfigBL tCombinationConfigBL = new CombinationConfigBL();
		logger.debug("Start CombinationConfigUI UI Submit...");
		tCombinationConfigBL.submitData(mInputData, mOperate);
		logger.debug("End CombinationConfigUI UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tCombinationConfigBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tCombinationConfigBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "CombinationConfigUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tCombinationConfigBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		// LDPlanDutyParamSet tLDPlanDutyParamSet = new
		// LDPlanDutyParamSet();
		// LDPlanDutyParamSchema tLDPlanDutyParamSchema = new
		// LDPlanDutyParamSchema();
		//
		// tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
		// tLDPlanDutyParamSchema.setGrpContNo("120110000000236     ");
		// tLDPlanDutyParamSchema.setRiskCode("221670");
		// tLDPlanDutyParamSchema.setMainRiskCode("211672");
		// tLDPlanDutyParamSchema.setProposalGrpContNo("123131");
		// tLDPlanDutyParamSchema.setContPlanCode("中国");
		// tLDPlanDutyParamSchema.setGrpPolNo("sdfaf");
		// tLDPlanDutyParamSchema.setCalFactor("Deductible");
		// tLDPlanDutyParamSchema.setDutyCode("679001");
		// tLDPlanDutyParamSchema.setCalFactorValue("");
		// tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
		//
		// tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
		// tLDPlanDutyParamSchema.setGrpContNo("120110000000236     ");
		// tLDPlanDutyParamSchema.setRiskCode("221670");
		// tLDPlanDutyParamSchema.setMainRiskCode("211672");
		// tLDPlanDutyParamSchema.setProposalGrpContNo("123131");
		// tLDPlanDutyParamSchema.setContPlanCode("中国");
		// tLDPlanDutyParamSchema.setGrpPolNo("sdfaf");
		// tLDPlanDutyParamSchema.setCalFactor("1");
		// tLDPlanDutyParamSchema.setDutyCode("679001");
		// tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
		//
		// tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
		// tLDPlanDutyParamSchema.setGrpContNo("120110000000236     ");
		// tLDPlanDutyParamSchema.setRiskCode("221670");
		// tLDPlanDutyParamSchema.setMainRiskCode("211672");
		// tLDPlanDutyParamSchema.setProposalGrpContNo("123131");
		// tLDPlanDutyParamSchema.setContPlanCode("中国");
		// tLDPlanDutyParamSchema.setGrpPolNo("sdfaf");
		// tLDPlanDutyParamSchema.setCalFactor("1");
		// tLDPlanDutyParamSchema.setDutyCode("679001");
		// tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
		// GlobalInput tG = new GlobalInput();
		// tG.ComCode = "8611";
		// tG.ManageCom = "8611";
		// tG.Operator = "actest";
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLDPlanDutyParamSet);
		// tVData.add("120110000000236");
		// tVData.add("120110000000236");
		// tVData.add("A");
		// tVData.add("0");
		// tVData.add("0");
		// LDPlanUI tLDPlanUI = new LDPlanUI();
		// tLDPlanUI.submitData(tVData, "UPDATE||MAIN");
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mLDPlanDutyParamSet);
			mInputData.add(this.mContPlanCode);
			mInputData.add(this.mPlanType);
			mInputData.add(this.mPlanSql);
			mInputData.add(this.mPeoples3);
			mInputData.add(this.mRemark);
			mInputData.add(this.mPlanKind1);
			mInputData.add(this.mPlanKind2);
			mInputData.add(this.mPlanKind3);
			mInputData.add(this.mManageCom);
			mInputData.add(this.mPortfolioflag);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CombinationConfigUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		boolean tReturn = false;
		// 此处增加一些校验代码
		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mLDPlanDutyParamSet.set((LDPlanDutyParamSet) cInputData
				.getObjectByObjectName("LDPlanDutyParamSet", 0));
		this.mContPlanCode = (String) cInputData.get(2);
		this.mPlanType = (String) cInputData.get(3);
		this.mPlanSql = (String) cInputData.get(4);
		this.mPeoples3 = (String) cInputData.get(5);
		this.mRemark = (String) cInputData.get(6);
		this.mPlanKind1 = (String) cInputData.get(7);
		this.mPlanKind2 = (String) cInputData.get(8);
		this.mPlanKind3 = (String) cInputData.get(9);
		this.mManageCom = (String) cInputData.get(10);
		this.mPortfolioflag = (String) cInputData.get(11);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 获取返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}
}
