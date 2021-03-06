/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAscriptionRuleFactorySchema;
import com.sinosoft.lis.vschema.LCAscriptionRuleFactorySet;
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
public class LCAscriptionRuleFactoryUI {
private static Logger logger = Logger.getLogger(LCAscriptionRuleFactoryUI.class);
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
	private LCAscriptionRuleFactorySet mLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
	private String mGrpContNo = "";

	public LCAscriptionRuleFactoryUI() {
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
		LCAscriptionRuleFactoryBL tLCAscriptionRuleFactoryBL = new LCAscriptionRuleFactoryBL();
		logger.debug("Start LCAscriptionRuleFactory UI Submit...");
		tLCAscriptionRuleFactoryBL.submitData(mInputData, mOperate);
		logger.debug("End LCAscriptionRuleFactory UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLCAscriptionRuleFactoryBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCAscriptionRuleFactoryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tLCAscriptionRuleFactoryBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
		LCAscriptionRuleFactorySchema tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();

		tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
		tLCAscriptionRuleFactorySchema.setGrpContNo("140110000000176");
		tLCAscriptionRuleFactorySchema.setGrpPolNo("120110000000403");
		tLCAscriptionRuleFactorySchema.setAscriptionRuleCode("A");
		tLCAscriptionRuleFactorySchema.setAscriptionRuleName("dsa");
		tLCAscriptionRuleFactorySchema.setRiskCode("212403");
		tLCAscriptionRuleFactorySchema.setFactoryType("000006");
		tLCAscriptionRuleFactorySchema.setOtherNo("692101");
		tLCAscriptionRuleFactorySchema.setFactoryCode("gs0001");
		tLCAscriptionRuleFactorySchema.setFactorySubCode("1");
		tLCAscriptionRuleFactorySchema.setFactoryName("ServiceYear");
		tLCAscriptionRuleFactorySchema.setCalRemark("当员工在公司工作满a年不满b年时，归属比例为c");
		tLCAscriptionRuleFactorySchema.setParams("0,3,0.05");
		tLCAscriptionRuleFactorySet.add(tLCAscriptionRuleFactorySchema);

		// tLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
		// tLCAscriptionRuleFactorySchema.setGrpContNo("86110020040120000104");
		// tLCAscriptionRuleFactorySchema.setGrpPolNo("111111");
		// tLCAscriptionRuleFactorySchema.setAscriptionRuleCode("A");
		// tLCAscriptionRuleFactorySchema.setAscriptionRuleName("dfsljfsajlkfjlk");
		// tLCAscriptionRuleFactorySchema.setRiskCode("211403");
		// tLCAscriptionRuleFactorySchema.setFactoryType("000005");
		// tLCAscriptionRuleFactorySchema.setOtherNo("672201");
		// tLCAscriptionRuleFactorySchema.setFactoryCode("000001");
		// tLCAscriptionRuleFactorySchema.setFactorySubCode("1");
		// tLCAscriptionRuleFactorySchema.setFactoryName("test");
		// tLCAscriptionRuleFactorySchema.setCalRemark(
		// "赔付比例：实际发生额大于等于 a 并且小于等于 b 时 赔付比例为 c");
		// tLCAscriptionRuleFactorySchema.setParams("100,200,200");
		// tLCAscriptionRuleFactorySet.add(tLCAscriptionRuleFactorySchema);

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "8611";
		tG.ManageCom = "8611";
		tG.Operator = "ac";

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLCAscriptionRuleFactorySet);
		tVData.add("140110000000176");
		// tVData.add("86110020040990000041");
		// tVData.add("A");
		// tVData.add("select * from dual");

		LCAscriptionRuleFactoryUI tLCAscriptionRuleFactoryUI = new LCAscriptionRuleFactoryUI();
		// tLCAscriptionRuleFactoryUI.submitData(tVData, "INSERT||MAIN");
		tLCAscriptionRuleFactoryUI.submitData(tVData, "DELETE||MAIN");
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mLCAscriptionRuleFactorySet);
			mInputData.add(this.mGrpContNo);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryUI";
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
		this.mLCAscriptionRuleFactorySet
				.set((LCAscriptionRuleFactorySet) cInputData
						.getObjectByObjectName("LCAscriptionRuleFactorySet", 0));
		this.mGrpContNo = (String) cInputData.get(2);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryUI";
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
