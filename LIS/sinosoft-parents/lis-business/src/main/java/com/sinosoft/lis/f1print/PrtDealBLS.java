package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LZSysCertifyDB;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: sinosoft (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author haopan function :print and send out syscert
 * @version 1.0
 * @date 2006-10-16
 */
public class PrtDealBLS {
private static Logger logger = Logger.getLogger(PrtDealBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private VData mResult = new VData();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();

	public PrtDealBLS() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;

	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		mLZSysCertifySchema.setSchema((LZSysCertifySchema) cInputData
				.getObjectByObjectName("LZSysCertifySchema", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

		LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
		tLZSysCertifyDB.setSchema(mLZSysCertifySchema);

		if (mOperate.equals("UPDATE&&INSERT")) {
			if (!tLZSysCertifyDB.insert()) {
				mErrors.copyAllErrors(tLZSysCertifyDB.mErrors);
				return false;
			}
			if (!tLOPRTManagerDB.update()) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				return false;
			}

		} else {
			buildError("dealData", "不支持的操作字符串");
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintManagerBLS";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
