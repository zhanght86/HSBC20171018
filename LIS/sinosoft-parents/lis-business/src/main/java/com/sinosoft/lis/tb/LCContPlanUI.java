/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * 保障计划前台数据传入接收类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 接收前台传入的数据，转入BL类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class LCContPlanUI implements BusinessService{
private static Logger logger = Logger.getLogger(LCContPlanUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
	private LCContPlanSchema mLCContPlanSchema = new LCContPlanSchema();

	// private String mGrpContNo = "";
	// private String mContPlanCode = "";
	// private String mProposalGrpContNo = "";
	// private String mPlanSql = "";
	// private String mPlanType = "";
	// private String mPeoples3 = "";

	public LCContPlanUI() {
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
		LCContPlanBL tLCContPlanBL = new LCContPlanBL();
		logger.debug("Start LCContPlan UI Submit...");
		tLCContPlanBL.submitData(mInputData, mOperate);
		logger.debug("End LCContPlan UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCContPlanBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContPlanBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContPlanUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tLCContPlanBL.getResult();
		}
		mInputData = null;
		return true;
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
			mInputData.add(this.mLCContPlanDutyParamSet);
			mInputData.add(this.mLCContPlanSchema);
			// mInputData.add(this.mProposalGrpContNo);
			// mInputData.add(this.mGrpContNo);
			// mInputData.add(this.mContPlanCode);
			// mInputData.add(this.mPlanType);
			// mInputData.add(this.mPlanSql);
			// mInputData.add(this.mPeoples3);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanUI";
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
	private static boolean dealData() {
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
		this.mLCContPlanDutyParamSet.set((LCContPlanDutyParamSet) cInputData
				.getObjectByObjectName("LCContPlanDutyParamSet", 0));
		this.mLCContPlanSchema.setSchema((LCContPlanSchema) cInputData
				.getObjectByObjectName("LCContPlanSchema", 0));
		// this.mProposalGrpContNo = (String) cInputData.get(2);
		// this.mGrpContNo = (String) cInputData.get(3);
		// this.mContPlanCode = (String) cInputData.get(4);
		// this.mPlanType = (String) cInputData.get(5);
		// this.mPlanSql = (String) cInputData.get(6);
		// this.mPeoples3 = (String) cInputData.get(7);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanUI";
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

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// LCContPlanDutyParamSet tLCContPlanDutyParamSet = new
		// LCContPlanDutyParamSet();
		// LCContPlanDutyParamSchema tLCContPlanDutyParamSchema = new
		// LCContPlanDutyParamSchema();
		//
		// tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
		// tLCContPlanDutyParamSchema.setGrpContNo("120110000000236 ");
		// tLCContPlanDutyParamSchema.setRiskCode("221670");
		// tLCContPlanDutyParamSchema.setMainRiskCode("211672");
		// tLCContPlanDutyParamSchema.setProposalGrpContNo("123131");
		// tLCContPlanDutyParamSchema.setContPlanCode("中国");
		// tLCContPlanDutyParamSchema.setGrpPolNo("sdfaf");
		// tLCContPlanDutyParamSchema.setCalFactor("Deductible");
		// tLCContPlanDutyParamSchema.setDutyCode("679001");
		// tLCContPlanDutyParamSchema.setCalFactorValue("");
		// tLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
		//
		// tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
		// tLCContPlanDutyParamSchema.setGrpContNo("120110000000236 ");
		// tLCContPlanDutyParamSchema.setRiskCode("221670");
		// tLCContPlanDutyParamSchema.setMainRiskCode("211672");
		// tLCContPlanDutyParamSchema.setProposalGrpContNo("123131");
		// tLCContPlanDutyParamSchema.setContPlanCode("中国");
		// tLCContPlanDutyParamSchema.setGrpPolNo("sdfaf");
		// tLCContPlanDutyParamSchema.setCalFactor("1");
		// tLCContPlanDutyParamSchema.setDutyCode("679001");
		// tLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
		//
		// tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
		// tLCContPlanDutyParamSchema.setGrpContNo("120110000000236 ");
		// tLCContPlanDutyParamSchema.setRiskCode("221670");
		// tLCContPlanDutyParamSchema.setMainRiskCode("211672");
		// tLCContPlanDutyParamSchema.setProposalGrpContNo("123131");
		// tLCContPlanDutyParamSchema.setContPlanCode("中国");
		// tLCContPlanDutyParamSchema.setGrpPolNo("sdfaf");
		// tLCContPlanDutyParamSchema.setCalFactor("1");
		// tLCContPlanDutyParamSchema.setDutyCode("679001");
		// tLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
		// GlobalInput tG = new GlobalInput();
		// tG.ComCode = "8611";
		// tG.ManageCom = "8611";
		// tG.Operator = "actest";
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLCContPlanDutyParamSet);
		// tVData.add("120110000000236");
		// tVData.add("120110000000236");
		// tVData.add("A");
		// tVData.add("0");
		// tVData.add("0");
		// LCContPlanUI tLCContPlanUI = new LCContPlanUI();
		// tLCContPlanUI.submitData(tVData, "UPDATE||MAIN");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
