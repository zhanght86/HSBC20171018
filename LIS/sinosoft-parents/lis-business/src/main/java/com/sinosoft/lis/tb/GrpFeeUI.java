/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 管理费处理
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 接收前台传入的管理费数据
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
public class GrpFeeUI {
private static Logger logger = Logger.getLogger(GrpFeeUI.class);
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
	private LCGrpFeeSet mLCGrpFeeSet = new LCGrpFeeSet();
	private LCGrpFeeParamSet mLCGrpFeeParamSet = new LCGrpFeeParamSet();

	public GrpFeeUI() {
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
		GrpFeeBL tGrpFeeBL = new GrpFeeBL();
		logger.debug("Start GrpFee UI Submit...");
		tGrpFeeBL.submitData(mInputData, mOperate);
		logger.debug("End GrpFee UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tGrpFeeBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpFeeUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tGrpFeeBL.getResult();
		}
		mInputData = null;
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mLCGrpFeeSet);
			mInputData.add(this.mLCGrpFeeParamSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpFeeUI";
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
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mLCGrpFeeSet.set((LCGrpFeeSet) cInputData.getObjectByObjectName(
				"LCGrpFeeSet", 0));
		this.mLCGrpFeeParamSet.set((LCGrpFeeParamSet) cInputData
				.getObjectByObjectName("LCGrpFeeParamSet", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpFeeUI";
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

	public static void main(String[] args) {
		// LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		// LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
		//
		// tLCGrpFeeSchema.setGrpPolNo("0001");
		// tLCGrpFeeSchema.setGrpContNo("86110020040120000104");
		// tLCGrpFeeSchema.setRiskCode("001");
		// tLCGrpFeeSchema.setPayPlanCode("00000000");
		// tLCGrpFeeSchema.setInsuAccNo("1");
		// tLCGrpFeeSchema.setFeeCalMode("01");
		// tLCGrpFeeSet.add(tLCGrpFeeSchema);
		//
		// GlobalInput tG = new GlobalInput();
		// tG.ComCode = "8611";
		// tG.ManageCom = "8611";
		// tG.Operator = "actest";
		//
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLCGrpFeeSet);
		//
		// GrpFeeUI tGrpFeeUI = new GrpFeeUI();
		// tGrpFeeUI.submitData(tVData, "INSERT||MAIN");
	}
}
