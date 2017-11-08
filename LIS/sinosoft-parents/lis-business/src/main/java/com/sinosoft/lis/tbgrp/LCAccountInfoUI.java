/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

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
public class LCAccountInfoUI {
private static Logger logger = Logger.getLogger(LCAccountInfoUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	// private String mGrpContNo = "";
	// private String mContPlanCode = "";
	// private String mProposalGrpContNo = "";
	// private String mPlanSql = "";
	// private String mPlanType = "";
	// private String mPeoples3 = "";

	public LCAccountInfoUI() {
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
		LCAccountInfoBL tLCAccountInfoBL = new LCAccountInfoBL();
		logger.debug("Start LCAccountInfo UI Submit...");
		if (tLCAccountInfoBL.submitData(mInputData, mOperate) ){
			logger.debug("End LCAccountInfo UI Submit...");			
		}

		// 如果有需要处理的错误，则返回
		if (tLCAccountInfoBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCAccountInfoBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAccountInfoUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
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
			mInputData.add(this.mTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAccountInfoUI";
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
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAccountInfoUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		this.mInputData = cInputData;
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
		// LCAccountInfoDutyParamSet tLCAccountInfoDutyParamSet = new
		// LCAccountInfoDutyParamSet();
		// LCAccountInfoDutyParamSchema tLCAccountInfoDutyParamSchema = new
		// LCAccountInfoDutyParamSchema();
		//
		// tLCAccountInfoDutyParamSchema = new LCAccountInfoDutyParamSchema();
		// tLCAccountInfoDutyParamSchema.setGrpContNo("120110000000236 ");
		// tLCAccountInfoDutyParamSchema.setRiskCode("221670");
		// tLCAccountInfoDutyParamSchema.setMainRiskCode("211672");
		// tLCAccountInfoDutyParamSchema.setProposalGrpContNo("123131");
		// tLCAccountInfoDutyParamSchema.setContPlanCode("中国");
		// tLCAccountInfoDutyParamSchema.setGrpPolNo("sdfaf");
		// tLCAccountInfoDutyParamSchema.setCalFactor("Deductible");
		// tLCAccountInfoDutyParamSchema.setDutyCode("679001");
		// tLCAccountInfoDutyParamSchema.setCalFactorValue("");
		// tLCAccountInfoDutyParamSet.add(tLCAccountInfoDutyParamSchema);
		//
		// tLCAccountInfoDutyParamSchema = new LCAccountInfoDutyParamSchema();
		// tLCAccountInfoDutyParamSchema.setGrpContNo("120110000000236 ");
		// tLCAccountInfoDutyParamSchema.setRiskCode("221670");
		// tLCAccountInfoDutyParamSchema.setMainRiskCode("211672");
		// tLCAccountInfoDutyParamSchema.setProposalGrpContNo("123131");
		// tLCAccountInfoDutyParamSchema.setContPlanCode("中国");
		// tLCAccountInfoDutyParamSchema.setGrpPolNo("sdfaf");
		// tLCAccountInfoDutyParamSchema.setCalFactor("1");
		// tLCAccountInfoDutyParamSchema.setDutyCode("679001");
		// tLCAccountInfoDutyParamSet.add(tLCAccountInfoDutyParamSchema);
		//
		// tLCAccountInfoDutyParamSchema = new LCAccountInfoDutyParamSchema();
		// tLCAccountInfoDutyParamSchema.setGrpContNo("120110000000236 ");
		// tLCAccountInfoDutyParamSchema.setRiskCode("221670");
		// tLCAccountInfoDutyParamSchema.setMainRiskCode("211672");
		// tLCAccountInfoDutyParamSchema.setProposalGrpContNo("123131");
		// tLCAccountInfoDutyParamSchema.setContPlanCode("中国");
		// tLCAccountInfoDutyParamSchema.setGrpPolNo("sdfaf");
		// tLCAccountInfoDutyParamSchema.setCalFactor("1");
		// tLCAccountInfoDutyParamSchema.setDutyCode("679001");
		// tLCAccountInfoDutyParamSet.add(tLCAccountInfoDutyParamSchema);
		// GlobalInput tG = new GlobalInput();
		// tG.ComCode = "8611";
		// tG.ManageCom = "8611";
		// tG.Operator = "actest";
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLCAccountInfoDutyParamSet);
		// tVData.add("120110000000236");
		// tVData.add("120110000000236");
		// tVData.add("A");
		// tVData.add("0");
		// tVData.add("0");
		// LCAccountInfoUI tLCAccountInfoUI = new LCAccountInfoUI();
		// tLCAccountInfoUI.submitData(tVData, "UPDATE||MAIN");
	}
}
