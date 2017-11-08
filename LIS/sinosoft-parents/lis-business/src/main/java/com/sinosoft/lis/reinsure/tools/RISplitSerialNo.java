

package com.sinosoft.lis.reinsure.tools;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public class RISplitSerialNo {
	public CErrors mErrors = new CErrors();

	public RISplitSerialNo() {
	}

	/**
	 * 将一大号段分割成若干小号段.
	 * 
	 * @param startNo
	 *            String 起始号
	 * @param endNo
	 *            String 终止号
	 * @param intv
	 *            int 号段长度
	 * @return String[][] 例: splitSerialNo("N01B000100","N01B000200",50) 返回结果:
	 *         N01B000100 N01B000149 N01B000150 N01B000199 N01B000200 N01B000200
	 */
	public String[][] splitSerialNo(String startNo, String endNo, int intv) {
		if (startNo.length() != endNo.length()) {
			buildError("RIPubFun", "序列号分段时出错，起始号与终止号长度不等。");
			return null;
		}
		if (!getHead(startNo).equals(getHead(endNo))) {
			buildError("RIPubFun", "序列号分段时出错，起始号与终止号字符串头不相同。");
			return null;
		}
		// 得到序列号字符部分
		String head = getHead(startNo);

		String startTail = getTail(startNo);
		long hL = Long.parseLong(startTail);
		// 得到序列号数据部分
		String endTail = getTail(endNo);
		long tL = Long.parseLong(endTail);
		if (tL < hL) {
			buildError("RIPubFun", "序列号分段时出错，起始号大于终止号。");
			return null;
		}
		int seg = (int) Math.ceil(((double) tL - (double) hL + 1)
				/ (double) intv);
		String[][] segArr = new String[seg][2];

		long startNum = hL;
		for (int i = 0; i < seg; i++) {
			segArr[i][0] = format(head, startNum + "", startNo.length());
			if (i < seg - 1) {
				segArr[i][1] = format(head, (startNum + intv - 1) + "",
						startNo.length());
			} else {
				segArr[i][1] = endNo;
			}
			startNum = startNum + intv;
		}
		return segArr;
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

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RIPubFun";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
		System.out.print(szErrMsg);
	}

	public static void main(String[] args) {
		RISplitSerialNo tRISplitSerialNo = new RISplitSerialNo();
		tRISplitSerialNo.splitSerialNo("N00001000000000100",
				"N00001000000000200", 20);
	}
}
