/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLClaimDescUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimDescUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLClaimDescUI() {
	}

	public static void main(String[] args) {
		//
		// LLClaimDescSchema tLLClaimDescSchema = new LLClaimDescSchema();
		// //案件核赔表
		//
		// GlobalInput tGI = new GlobalInput();
		// LLClaimDescUI tLLClaimDescUI = new LLClaimDescUI();
		//
		// String transact = "INSERT";//获取操作insert||update
		// tGI.ManageCom="86";
		// tGI.Operator="001";
		//
		// //获取页面信息
		// tLLClaimDescSchema.setClmNo("90000000130");//赔案号
		// tLLClaimDescSchema.setInqNo("90000000130");//调查序号
		// tLLClaimDescSchema.setBatNo("90000000130");//调查批次号
		// tLLClaimDescSchema.setCustomerNo("1");//出险人客户号
		// tLLClaimDescSchema.setCustomerName("1");//出险人名称
		//
		// VData tVData = new VData();
		// tVData.add(tGI);
		// tVData.add(tLLClaimDescSchema);
		//
		// tLLClaimDescUI.submitData(tVData,transact);
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		LLClaimDescBL tLLClaimDescBL = new LLClaimDescBL();

		logger.debug("----------UI BEGIN----------");
		if (tLLClaimDescBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimDescBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimDescUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLClaimDescBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
