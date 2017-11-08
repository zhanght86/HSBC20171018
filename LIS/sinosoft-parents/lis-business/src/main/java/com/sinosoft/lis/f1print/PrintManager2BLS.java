package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.db.LOPRTManager2DB;
import com.sinosoft.lis.schema.LOPRTManager2Schema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PrintManager2BLS {
private static Logger logger = Logger.getLogger(PrintManager2BLS.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private VData mResult = new VData();
	private LOPRTManager2Schema mLOPRTManager2Schema = new LOPRTManager2Schema();

	public PrintManager2BLS() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("INSERT") && !cOperate.equals("UPDATE")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

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
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LOPRTManager2DB tLOPRTManager2DB = new LOPRTManager2DB();

		tLOPRTManager2DB.setSchema(mLOPRTManager2Schema);

		if (mOperate.equals("INSERT")) {
			if (!tLOPRTManager2DB.insert()) {
				mErrors.copyAllErrors(tLOPRTManager2DB.mErrors);
				return false;
			}
		} else if (mOperate.equals("UPDATE")) {
			if (!tLOPRTManager2DB.update()) {
				mErrors.copyAllErrors(tLOPRTManager2DB.mErrors);
				return false;
			}
		} else {
			buildError("dealData", "不支持的操作字符串");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mLOPRTManager2Schema.setSchema((LOPRTManager2Schema) cInputData
				.getObjectByObjectName("LOPRTManager2Schema", 0));

		if (mLOPRTManager2Schema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintManager2BLS";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		PrintManager2BLS printManager2BLS1 = new PrintManager2BLS();
	}
}
