/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LCContPlanFactorySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 保障计划要素前台数据接收类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 接收前台数据，传入BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 * 
 * @author ZHUXF
 * @version 1.0
 */
public class LCContPlanFactoryUI {
private static Logger logger = Logger.getLogger(LCContPlanFactoryUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContPlanFactorySet mLCContPlanFactorySet = new LCContPlanFactorySet();
	private String mGrpContNo = "";
	private String mProposalGrpContNo = "";

	public LCContPlanFactoryUI() {
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
		if (!getInputData(cInputData))
			return false;
		// 进行业务处理
		if (!dealData())
			return false;
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		LCContPlanFactoryBL tLCContPlanFactoryBL = new LCContPlanFactoryBL();
		logger.debug("Start LCContPlanFactory UI Submit...");
		tLCContPlanFactoryBL.submitData(mInputData, mOperate);
		logger.debug("End LCContPlanFactory UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCContPlanFactoryBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContPlanFactoryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tLCContPlanFactoryBL.getResult();
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
			mInputData.add(this.mLCContPlanFactorySet);
			mInputData.add(this.mProposalGrpContNo);
			mInputData.add(this.mGrpContNo);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryUI";
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
		this.mLCContPlanFactorySet.set((LCContPlanFactorySet) cInputData
				.getObjectByObjectName("LCContPlanFactorySet", 0));
		this.mProposalGrpContNo = (String) cInputData.get(2);
		this.mGrpContNo = (String) cInputData.get(3);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

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
		// LCContPlanFactorySet tLCContPlanFactorySet = new
		// LCContPlanFactorySet();
		// LCContPlanFactorySchema tLCContPlanFactorySchema = new
		// LCContPlanFactorySchema();
		// tLCContPlanFactorySchema = new LCContPlanFactorySchema();
		// tLCContPlanFactorySchema.setGrpContNo("86110020040120000104");
		// tLCContPlanFactorySchema.setRiskCode("211672");
		// tLCContPlanFactorySchema.setRiskVersion("2002");
		// tLCContPlanFactorySchema.setProposalGrpContNo("123131");
		// tLCContPlanFactorySchema.setContPlanCode("A");
		// tLCContPlanFactorySchema.setContPlanName("A级保障计划");
		// tLCContPlanFactorySchema.setFactoryType("000004");
		// tLCContPlanFactorySchema.setOtherNo("672201");
		// tLCContPlanFactorySchema.setFactoryCode("000001");
		// tLCContPlanFactorySchema.setFactorySubCode("2");
		// tLCContPlanFactorySchema.setFactoryName("test");
		// tLCContPlanFactorySchema.setCalRemark(
		// "止付线（最高限额）：本年首次的止付线为 a ，本年二次及以后的止付线为 b");
		// tLCContPlanFactorySchema.setParams("100,200");
		// tLCContPlanFactorySet.add(tLCContPlanFactorySchema);
		// GlobalInput tG = new GlobalInput();
		// tG.ComCode = "8611";
		// tG.ManageCom = "8611";
		// tG.Operator = "actest";
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLCContPlanFactorySet);
		// tVData.add("86110020040120000105");
		// tVData.add("86110020040990000041");
		// LCContPlanFactoryUI tLCContPlanFactoryUI = new LCContPlanFactoryUI();
		// tLCContPlanFactoryUI.submitData(tVData, "INSERT||MAIN");
	}
}
