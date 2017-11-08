/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author liurx
 * @version 1.0 Modified By QianLy on 2006-11-20 for GrpCheque
 */
public class EdorCheckPrintUI {
private static Logger logger = Logger.getLogger(EdorCheckPrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private TransferData mTransferData = new TransferData();
	private String chequeType;// 发票类型，根据不同的类型调用不同的处理BL类

	public EdorCheckPrintUI() {
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
		if (!cOperate.equals("PRINT")) {
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
		logger.debug("chequeType___" + chequeType);
		if (chequeType.equals(PrintManagerBL.CODE_PEdorReceipt)) {
			EdorCheckPrintBL tEdorCheckPrintBL = new EdorCheckPrintBL();
			if (!tEdorCheckPrintBL.submitData(vData, cOperate)) {
				if (tEdorCheckPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tEdorCheckPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"EdorCheckPrintBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tEdorCheckPrintBL.getResult();
				return true;
			}
		} else if (chequeType.equals(PrintManagerBL.CODE_GEdorInvoice)) {
			GEdorCheckPrintBL tGEdorCheckPrintBL = new GEdorCheckPrintBL();
			if (!tGEdorCheckPrintBL.submitData(vData, cOperate)) {
				if (tGEdorCheckPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGEdorCheckPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"GEdorCheckPrintBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGEdorCheckPrintBL.getResult();
				XmlExport txmlExport = (XmlExport) mResult
						.getObjectByObjectName("XmlExport", 0);
				if (txmlExport == null) {
					logger.debug("xe = null");
				}
				return true;
			}
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
			vData.add(mTransferData);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		chequeType = (String) mTransferData.getValueByName("ChequeType");
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {

		XmlExport txmlExport = (XmlExport) mResult.getObjectByObjectName(
				"XmlExport", 0);
		if (txmlExport == null) {
			logger.debug("__________getResult__xe = null"
					+ mResult.size());
		}

		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "EdorCheckPrintUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		VData tVData = new VData();
		EdorCheckPrintUI tEdorCheckPrintUI = new EdorCheckPrintUI();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100071200026");
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ChequeType", "BQ36");
		tTransferData.setNameAndValue("OtherSign", "0");
		tVData.addElement(tTransferData);
		tVData.addElement(tLOPRTManagerSchema);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "862100";
		tG.Operator = "001";
		tVData.addElement(tG);
		tEdorCheckPrintUI.submitData(tVData, "PRINT");
		logger.debug("______" + tLOPRTManagerSchema.encode());
		VData mrt = new VData();
		mrt = tEdorCheckPrintUI.getResult();
		XmlExport txmlExport = (XmlExport) mrt.getObjectByObjectName(
				"XmlExport", 0);
		if (txmlExport == null || txmlExport.equals("")) {
			logger.debug("xe = null");
		}
	}
}
