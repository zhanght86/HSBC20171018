package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: 打印转账不成功通知书 --新单银行转账收费失败3次</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010-05-07</p>
 * <p>Company: </p>
 * @author HanBin
 * @version 1.0
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BankF1PUI implements PrintService {
private static Logger logger = Logger.getLogger(BankF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();

	public BankF1PUI() {
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



		BankF1PBL tBankF1PBL = new BankF1PBL();
		logger.debug("Start tBankF1P BL Submit ...");

		if (!tBankF1PBL.submitData(cInputData, cOperate)) {
			if (tBankF1PBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tBankF1PBL.mErrors);
				return false;
			} else {
				buildError("submitData", "BankF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tBankF1PBL.getResult();
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
			vData.add(mLCContSchema);
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
//		if(mLOPRTManagerSchema != null && (mLOPRTManagerSchema.getPrtSeq() == null || "".equals(mLOPRTManagerSchema.getPrtSeq()))){
//			buildError("getInputData", "没有得到足够的信息！");
//			return false;
//		}
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));

//		if (mLCContSchema != null && (mLCContSchema.getContNo() == null || "".equals(mLCContSchema.getContNo()))) {
//			buildError("getInputData", "没有得到足够的信息！");
//			return false;
//		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "BankF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
//		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
//
//		tLOPRTManagerSchema.setPrtSeq("86110020030210000151");
//
//		GlobalInput tG = new GlobalInput();
//		tG.Operator = "001";
//		tG.ComCode = "86";
//
//		VData tVData = new VData();
//
//		tVData.addElement(tG);
//		tVData.addElement(tLOPRTManagerSchema);
//
//		RANF1PUI UI = new RANF1PUI();
//		if (!UI.submitData(tVData, "CONFIRM")) {
//			if (UI.mErrors.needDealError()) {
//				logger.debug(UI.mErrors.getFirstError());
//			} else {
//				logger.debug("RANF1PUI发生错误，但是没有提供详细的出错信息");
//			}
//		} else {
//			VData vData = UI.getResult();
//			XmlExport xe = (XmlExport) vData.get(0);
//
//			try {
//				InputStream ins = xe.getInputStream();
//				FileOutputStream fos = new FileOutputStream("LCPolData.xml");
//				int n = 0;
//
//				while ((n = ins.read()) != -1) {
//					fos.write(n);
//				}
//
//				fos.close();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
	}
}
