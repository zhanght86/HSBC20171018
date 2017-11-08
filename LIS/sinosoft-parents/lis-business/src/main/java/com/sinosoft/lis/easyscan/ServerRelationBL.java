package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ServerRelationBL {
private static Logger logger = Logger.getLogger(ServerRelationBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();
	String mRtnCode = "0";
	String mRtnDesc = "";

	public ServerRelationBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		ServerRelationBLS tServerRelationBLS = new ServerRelationBLS();
		tServerRelationBLS.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tServerRelationBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tServerRelationBLS.mErrors);
			tReturn = false;
		}

		mResult.clear();
		mResult = tServerRelationBLS.getResult();

		mInputData = null;

		return tReturn;
	}

	public VData getResult() {
		return mResult;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		return true;
	}

	public static void main(String[] args) {
		ServerRelationBL tServerRelationBL = new ServerRelationBL();
	}
}
