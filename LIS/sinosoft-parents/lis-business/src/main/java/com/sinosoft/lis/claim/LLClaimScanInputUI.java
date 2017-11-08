/*
 * <p>ClassName: LLClaimScanInputUI</p>
 * <p>Description: LLClaimScanInputUI类文件 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证补扫
 * @CreateDate：2005-08-28
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.Es_IssueDocSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class LLClaimScanInputUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimScanInputUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 存放查询结果 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private Es_IssueDocSchema mEs_IssueDocSchema = new Es_IssueDocSchema();

	// private LATrainSet mLATrainSet=new LATrainSet();
	public LLClaimScanInputUI() {
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
		LLClaimScanInputBL tLLClaimScanInputBL = new LLClaimScanInputBL();
		logger.debug("Start LLClaimScanInput UI Submit...");
		tLLClaimScanInputBL.submitData(mInputData, mOperate);
		logger.debug("End LLClaimScanInput UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLLClaimScanInputBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimScanInputBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN")) {
			this.mResult.clear();
			this.mResult = tLLClaimScanInputBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		Es_IssueDocSchema tEs_IssueDocSchema = new Es_IssueDocSchema();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "Admin";
		LLClaimScanInputUI tLLClaimScanInputUI = new LLClaimScanInputUI();
		tEs_IssueDocSchema.setBussNo("90000001658");
		tEs_IssueDocSchema.setSubType("CLM200");
		tEs_IssueDocSchema.setIssueDesc("");
		tEs_IssueDocSchema.setBussNoType("23");
		tEs_IssueDocSchema.setBussType("LP");
		tEs_IssueDocSchema.setStatus("0");
		VData tVData = new VData();
		tVData.addElement(tEs_IssueDocSchema);
		tVData.addElement(tG);
		try {
			tLLClaimScanInputUI.submitData(tVData, "INSERT");
		} catch (Exception ex) {
			logger.debug("1");
		}

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mEs_IssueDocSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputUI";
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
		this.mEs_IssueDocSchema.setSchema((Es_IssueDocSchema) cInputData
				.getObjectByObjectName("Es_IssueDocSchema", 0));
		if (mEs_IssueDocSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputUI";
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

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
