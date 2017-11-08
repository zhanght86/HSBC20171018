/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LDUserLogSchema;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 系统登出处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author dingzhong
 * @version 1.0
 */
public class logoutBL {
private static Logger logger = Logger.getLogger(logoutBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	// private String mOperate;
	// private VData mInputData = new VData();

	private LDUserLogSchema mLDUserLogSchema = new LDUserLogSchema();
	private LDSysTraceSchema mLDSysTraceSchema = new LDSysTraceSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	private VData mInputData = new VData();
	/** 返回的数据 */
	// private String mResultStr = "";
	public logoutBL() {
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
		// this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		// 进行业务处理
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			return false;
		}
		this.mLDUserLogSchema=(LDUserLogSchema) cInputData.getObjectByObjectName("LDUserLogSchema", 0);

//		mLDSysTraceSchema.setOperator(mGlobalInput.Operator);
//		mLDSysTraceSchema.setPolState(1001);

		return true;
	}

	/**
	 * 数据准备
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
//		String strSql = "delete from LDSYSTrace where Operator = '"
//				+ mLDSysTraceSchema.getOperator() + "' and PolState = 1001";
//		// logger.debug(strSql);
//		LDSysTraceDB tLDSYSTraceDB = new LDSysTraceDB();
//		tLDSYSTraceDB.executeQuery(strSql);
		
		String logno = PubFun1.CreateMaxNo("LOGNO", 10);

		mLDUserLogSchema.setLogNo(logno);
		map.put(mLDUserLogSchema, "INSERT");
		mInputData.clear();
		mInputData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, "INSERT"))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";		
			this.mErrors.addOneError(tError);
			return false;
		}
		
		return true;
	}
}
