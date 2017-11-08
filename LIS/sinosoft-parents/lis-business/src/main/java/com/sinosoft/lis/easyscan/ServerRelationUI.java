package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ServerRelationUI {
private static Logger logger = Logger.getLogger(ServerRelationUI.class);
	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public ServerRelationUI() {
	}

	// 传输数据的公共方法
	// 输入ES_DOC_MAINSchema,ES_DOC_PAGESSet
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		ServerRelationBL tServerRelationBL = new ServerRelationBL();
		tReturn = tServerRelationBL.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tServerRelationBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tServerRelationBL.mErrors);
			tReturn = false;
		}

		// 返回数据处理
		mResult.clear();
		mResult = tServerRelationBL.getResult();

		mInputData = null;

		return tReturn;
	}

	// 返回数据的公共方法
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ServerRelationUI tServerRelationUI1 = new ServerRelationUI();
	}
}
