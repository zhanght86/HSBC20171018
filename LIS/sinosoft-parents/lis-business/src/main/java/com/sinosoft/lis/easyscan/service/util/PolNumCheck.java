package com.sinosoft.lis.easyscan.service.util;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;

public class PolNumCheck {
private static Logger logger = Logger.getLogger(PolNumCheck.class);

	/**
	 * 8611：个人投保单
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingleOrg(String prtno) {
		return prtno.startsWith("8611");
	}

	/**
	 * 8616：简易投保单
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingleSimple(String prtno) {
		return prtno.startsWith("8616");
	}

	/**
	 * 8651：家庭单
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingleHome(String prtno) {
		return prtno.startsWith("8651");
	}

	/**
	 * 8661：多主险投保单
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingleMulMain(String prtno) {
		return prtno.startsWith("8661");
	}

	/**
	 * 8621：中介
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingleMiddle(String prtno) {
		return prtno.startsWith("8621");
	}

	/**
	 * 8635、8615：银代
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingleBank(String prtno) {
		return prtno.startsWith("8615") || prtno.startsWith("8625")
				|| prtno.startsWith("8635")|| prtno.startsWith("3110");
	}

	/**
	 * ：团险
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolGrp(String prtno) {
		return !isPolSingle(prtno);
	}

	/**
	 * 个险保单
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean isPolSingle(String prtno) {
		return (isPolSingleSimple(prtno) || isPolSingleHome(prtno)
				|| isPolSingleMulMain(prtno) || isPolSingleBank(prtno)
				|| isPolSingleMiddle(prtno) || isPolSingleOrg(prtno));
	}

	/**
	 * 检测投保单长度和数字性
	 * 
	 * @param prtno
	 * @return
	 */
	public static boolean checkPolLenNum(String prtno) {
		if (!checkPolNum(prtno))
			return false;
		return checkPolLen(prtno);
	}

	public static boolean checkPolLen(String prtno) {
		return (14 == prtno.length());
	}

	public static boolean checkPolNum(String prtno) {
		return prtno.matches("\\d+");
	}

	public static boolean hasSaveDoc(String doccode, String subtype) {
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(doccode);
		tES_DOC_MAINDB.setSubType(subtype);
		return tES_DOC_MAINDB.getCount() == 0;
	}
}
