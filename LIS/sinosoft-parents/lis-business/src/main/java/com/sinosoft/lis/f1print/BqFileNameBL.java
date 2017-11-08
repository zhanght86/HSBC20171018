package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
//import com.sinosoft.lis.midplat.util.DBConnPool;

public class BqFileNameBL {
private static Logger logger = Logger.getLogger(BqFileNameBL.class);
	private VData mInputData = new VData();

	public CErrors mErrors = new CErrors();
	
	private GlobalInput mGI;

	private VData mResult = new VData();
	
	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}
	
	public boolean submitData(VData cInputData, String cOperate) {
		
		mInputData = (VData) cInputData.clone();
		
		if (!getInputData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		
		return true;
	}
	
	private boolean dealData() {
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSql = "Select SysvarValue from LDSysvar where Sysvar='VTSFilePath'";
		sqlbv.sql(tSql);
		ExeSQL tExeSQL = new ExeSQL();
		String vtsFilePath = tExeSQL.getOneValue(sqlbv);
		String vtsFileName = vtsFilePath + mGI.Operator + "_" + FileQueue.getFileName()+".vts";
		mResult.add(vtsFileName);
		return true;
	}
	private boolean getInputData() {

		mGI = ((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if(mGI == null || mGI.Operator == null || mGI.Operator.equals("")){
			CError.buildErr(this, "请先登陆或登陆时间超时，请重新登陆");
			return false;
		}
		return true;
	}
}
