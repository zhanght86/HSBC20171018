



package com.sinosoft.productdef;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.PD_LACommissionRateSchema;
import com.sinosoft.lis.vschema.LACommissionRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PD_RiskCommissionUI {
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	PD_LACommissionRateSchema mPD_LACommissionRateSchema   = new PD_LACommissionRateSchema();
	LACommissionRateSet mLACommissionRateSet = new LACommissionRateSet();
	
	public PD_RiskCommissionUI(){
		
	}
	
	/**
	 * 传输数据的公共方法
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
		PD_RiskCommissionBL tLABKCommRateMaintainBL = new PD_RiskCommissionBL();
		System.out.println("Start LABKCommRateMaintain UI Submit...");
		tLABKCommRateMaintainBL.submitData(mInputData, mOperate);
		System.out.println("End LABKCommRateMaintain UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLABKCommRateMaintainBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLABKCommRateMaintainBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LABKCommRateMaintainUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		this.mPD_LACommissionRateSchema.setSchema((PD_LACommissionRateSchema) cInputData
				.getObjectByObjectName("PD_LACommissionRateSchema", 0));	
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABKCommRateMaintainUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
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
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mPD_LACommissionRateSchema);	
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABKCommRateMaintainUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	public VData getResult() {
		return this.mResult;
	}

}

