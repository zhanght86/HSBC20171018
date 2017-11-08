

package com.sinosoft.lis.reinsure.tools;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSerialNo;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class RIGetSerialNo {
	public CErrors mErrors = new CErrors();

	public RIGetSerialNo() {
	}

	public String getSerialNo(String serialNoType) {
		if (serialNoType.equals("01")) {
			return getCessRecordSerialNo();
		} else {
			return null;
		}
	}

	private String getCessRecordSerialNo() {
		try {
			RIInitSerialNo tRIInitSerialNo = RIInitSerialNo.getObject();
			String serialNo = tRIInitSerialNo.getCessRecordSerialNo();
			long tL = Long.parseLong(getTail(serialNo));
			tRIInitSerialNo.setCessRecordSerialNo(format(getHead(serialNo),
					(tL + 1) + "", serialNo.length()));
			return tRIInitSerialNo.getCessRecordSerialNo();

		} catch (Exception ex) {
			buildError("RIPubFun", "得到分保记录序列号出错: " + ex.getMessage());
			return "";
		}
	}

	private String getHead(String str) {
		char[] c = str.toCharArray();
		int lastCharNum = c.length;
		for (int i = c.length - 1; i >= 0; i--) {
			lastCharNum--;
			if (c[i] < '0' || c[i] > '9') {
				lastCharNum++;
				break;
			}
		}
		return str.substring(0, lastCharNum);
	}

	private String getTail(String str) {
		char[] c = str.toCharArray();
		int lastCharNum = c.length;
		for (int i = c.length - 1; i >= 0; i--) {
			lastCharNum--;
			if (c[i] < '0' || c[i] > '9') {
				lastCharNum++;
				break;
			}
		}
		return str.substring(lastCharNum);
	}

	private String format(String head, String strNum, int len) {
		int cyc = len - head.length() - strNum.length();
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(head);
		for (int i = 0; i < cyc; i++) {
			strBuffer.append("0");
		}
		strBuffer.append(strNum);
		return strBuffer.toString();
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "InitInfo";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

}
