package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LWMissionReassignBL;
import com.sinosoft.lis.claim.LWMissionReassignUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class AddRelationUI implements BusinessService{
private static Logger logger = Logger.getLogger(AddRelationUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */
	public AddRelationUI() {
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
		// 首先将传入的数据和操作符号在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		AddRelationBL tAddRelationBL = new AddRelationBL();
		logger.debug("----------LWMissionReassignUI Begin----------");
		if (tAddRelationBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tAddRelationBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LWMissionReassignBLUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tAddRelationBL.getResult();
		}
		logger.debug("----------LWMissionReassignUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;

	}

}
