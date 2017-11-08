/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
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
public class LCPayRuleFactoryUI {
private static Logger logger = Logger.getLogger(LCPayRuleFactoryUI.class);
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
	private LCPayRuleFactorySet mLCPayRuleFactorySet = new LCPayRuleFactorySet();
	private String mGrpContNo = "";

	public LCPayRuleFactoryUI() {
	}

	/**
	 * 传输数据的公共方法
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
		LCPayRuleFactoryBL tLCPayRuleFactoryBL = new LCPayRuleFactoryBL();
		logger.debug("Start LCPayRuleFactory UI Submit...");
		tLCPayRuleFactoryBL.submitData(mInputData, mOperate);
		logger.debug("End LCPayRuleFactory UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCPayRuleFactoryBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPayRuleFactoryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tLCPayRuleFactoryBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();

		tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactorySchema.setGrpContNo("86110020040120000104");
		tLCPayRuleFactorySchema.setGrpPolNo("111111");
		tLCPayRuleFactorySchema.setPayRuleCode("A");
		tLCPayRuleFactorySchema.setPayRuleName("dfsljfsajlkfjlk");
		tLCPayRuleFactorySchema.setRiskCode("211403");
		tLCPayRuleFactorySchema.setFactoryType("000005");
		tLCPayRuleFactorySchema.setOtherNo("672201");
		tLCPayRuleFactorySchema.setFactoryCode("000001");
		tLCPayRuleFactorySchema.setFactorySubCode("2");
		tLCPayRuleFactorySchema.setFactoryName("test");
		tLCPayRuleFactorySchema
				.setCalRemark("赔付比例：实际发生额大于等于 a  并且小于等于 b  时 赔付比例为 c");
		tLCPayRuleFactorySchema.setParams("100,200,200");
		tLCPayRuleFactorySet.add(tLCPayRuleFactorySchema);

		tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactorySchema.setGrpContNo("86110020040120000104");
		tLCPayRuleFactorySchema.setGrpPolNo("111111");
		tLCPayRuleFactorySchema.setPayRuleCode("A");
		tLCPayRuleFactorySchema.setPayRuleName("dfsljfsajlkfjlk");
		tLCPayRuleFactorySchema.setRiskCode("211403");
		tLCPayRuleFactorySchema.setFactoryType("000005");
		tLCPayRuleFactorySchema.setOtherNo("672201");
		tLCPayRuleFactorySchema.setFactoryCode("000001");
		tLCPayRuleFactorySchema.setFactorySubCode("1");
		tLCPayRuleFactorySchema.setFactoryName("test");
		tLCPayRuleFactorySchema
				.setCalRemark("赔付比例：实际发生额大于等于 a  并且小于等于 b  时 赔付比例为 c");
		tLCPayRuleFactorySchema.setParams("100,200,200");
		tLCPayRuleFactorySet.add(tLCPayRuleFactorySchema);

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "8611";
		tG.ManageCom = "8611";
		tG.Operator = "actest";

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLCPayRuleFactorySet);
		tVData.add("86110020040120000105");
		tVData.add("86110020040990000041");
		// tVData.add("A");
		// tVData.add("select * from dual");

		LCPayRuleFactoryUI tLCPayRuleFactoryUI = new LCPayRuleFactoryUI();
		tLCPayRuleFactoryUI.submitData(tVData, "INSERT||MAIN");

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mLCPayRuleFactorySet);
			mInputData.add(this.mGrpContNo);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		boolean tReturn = false;
		// 此处增加一些校验代码
		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mLCPayRuleFactorySet.set((LCPayRuleFactorySet) cInputData
				.getObjectByObjectName("LCPayRuleFactorySet", 0));
		this.mGrpContNo = (String) cInputData.get(2);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryUI";
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
}
