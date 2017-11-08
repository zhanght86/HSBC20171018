package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import java.io.FileOutputStream;
import java.io.InputStream;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class FirstPayF1PUI_MS implements PrintService {
private static Logger logger = Logger.getLogger(FirstPayF1PUI_MS.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public FirstPayF1PUI_MS() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug(cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备传往后台的数据
		VData vData = new VData();

		if (!prepareOutputData(vData)) {
			return false;
		}

		FirstPayF1PBL_MS tFirstPayF1PBL = new FirstPayF1PBL_MS();
		logger.debug("Start FirstPayF1PUI Submit ...");

		if (!tFirstPayF1PBL.submitData(vData, cOperate)) {
			if (tFirstPayF1PBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tFirstPayF1PBL.mErrors);
				return false;
			} else {
				buildError("submitData", "FirstPayF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tFirstPayF1PBL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "FirstPayF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setPrtSeq("86000020030810000026");
		// tLOPRTManagerSchema.setOtherNo("86110020030210000549");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);

		FirstPayF1PUI FirstPayF1PUI = new FirstPayF1PUI();
		if (!FirstPayF1PUI.submitData(tVData, "CONFIRM")) {
			if (FirstPayF1PUI.mErrors.needDealError()) {
				logger.debug(FirstPayF1PUI.mErrors.getFirstError());
			} else {
				logger.debug("FirstPayF1PUI发生错误，但是没有提供详细的出错信息");
			}
		}

		// else if( !FirstPayF1PUI.submitData(tVData,"PRINT") ) {
		// if( FirstPayF1PUI.mErrors.needDealError() ) {
		// logger.debug(FirstPayF1PUI.mErrors.getFirstError());
		// } else {
		// logger.debug("FirstPayF1PUI发生错误，但是没有提供详细的出错信息");
		// }
		// }

		else {
			VData vData = FirstPayF1PUI.getResult();
			XmlExport xe = (XmlExport) vData.get(0);

			try {
				InputStream ins = xe.getInputStream();
				FileOutputStream fos = new FileOutputStream("LCPolData.xml");
				int n = 0;

				while ((n = ins.read()) != -1) {
					fos.write(n);
				}

				fos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
