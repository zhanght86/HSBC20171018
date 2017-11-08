package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-04-16
 * @function print notice bill User Interface Layer
 */

import java.util.Vector;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ZhixiaoUI {
private static Logger logger = Logger.getLogger(ZhixiaoUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strIssueDate;

	private String strPrintDate;
	private String strReturnDate;
	private String strAgentGroup;
	private String mListType;

	public ZhixiaoUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 只对打印操作进行支持
			if (!cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			if (!getInputData(cInputData)) {
				return false;
			}
			VData vData = new VData();

			if (!prepareOutputData(vData)) {
				return false;
			}

			ZhixiaoBL tZhixiaoBL = new ZhixiaoBL();
			logger.debug("Start Zhixiao UI Submit ...");
			if (!tZhixiaoBL.submitData(vData, cOperate)) {
				if (tZhixiaoBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tZhixiaoBL.mErrors);
					return false;
				} else {
					buildError("submitData", "ZhixiaoBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tZhixiaoBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "ZhixiaoUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strIssueDate);
		vData.add(strAgentGroup);
		vData.add(strPrintDate);
		vData.add(strReturnDate);
		vData.add(mListType);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strIssueDate = (String) cInputData.get(1);
		strAgentGroup = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
		strReturnDate = (String) cInputData.get(4);
		mListType = (String) cInputData.get(5);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ZhixiaoUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private Vector getVsearch(Vector v) {
		Vector temp = new Vector();
		Vector temp1 = new Vector();
		Vector bin = new Vector();
		temp = v;
		int length = 0;
		length = temp.size();
		for (int i = 0; i < length; i++) {
			temp1 = (Vector) temp.get(i);
			if (temp1.size() == 1) {
				bin.add(temp1);
			} else {
				for (int k = 0; k < temp1.size(); k++) {
					if (temp1.get(k) != null) {
						bin.add(temp1.get(k));

					}
				}
			}

		}
		return bin;
	}

	public static void main(String[] args) {
	}
}
