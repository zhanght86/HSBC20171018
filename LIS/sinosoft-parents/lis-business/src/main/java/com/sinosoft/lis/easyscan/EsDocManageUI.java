package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan单证索引管理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class EsDocManageUI implements BusinessService{
private static Logger logger = Logger.getLogger(EsDocManageUI.class);
	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public EsDocManageUI() {
	}

	// 传输数据的公共方法
	// 输入ES_DOC_MAINSchema,ES_DOC_PAGESSet
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		EsDocManageBL tEsDocManageBL = new EsDocManageBL();
		tReturn = tEsDocManageBL.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tEsDocManageBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tEsDocManageBL.mErrors);
			tReturn = false;
		}

		// 返回数据处理
		mResult.clear();
		mResult = tEsDocManageBL.getResult();

		mInputData = null;

		return tReturn;
	}

	// 返回数据的公共方法
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		EsDocManageUI tEsDocManageUI1 = new EsDocManageUI();
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
