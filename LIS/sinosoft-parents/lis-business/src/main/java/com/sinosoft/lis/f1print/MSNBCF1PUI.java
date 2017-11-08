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

public class MSNBCF1PUI implements PrintService {
private static Logger logger = Logger.getLogger(MSNBCF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public MSNBCF1PUI() {
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

		MSNBCF1PBL tNBCF1PBL = new MSNBCF1PBL();
		logger.debug("Start RefuseAppF1P UI Submit ...");

		if (!tNBCF1PBL.submitData(vData, cOperate)) {
			if (tNBCF1PBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tNBCF1PBL.mErrors);
				return false;
			} else {
				buildError("submitData", "NBCF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tNBCF1PBL.getResult();
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

		cError.moduleName = "NBCF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setPrtSeq("86000020030990000007");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		VData tVData = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);

		NBCF1PUI UI = new NBCF1PUI();
		if (!UI.submitData(tVData, "CONFIRM")) {
			if (UI.mErrors.needDealError()) {
				logger.debug(UI.mErrors.getFirstError());
			} else {
				logger.debug("NBCF1PUI发生错误，但是没有提供详细的出错信息");
			}
		} else {
			VData vData = UI.getResult();
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
