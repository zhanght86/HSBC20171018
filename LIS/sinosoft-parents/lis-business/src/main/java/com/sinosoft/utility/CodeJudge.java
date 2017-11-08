/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

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
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */
public class CodeJudge {
private static Logger logger = Logger.getLogger(CodeJudge.class);

	public CodeJudge() {
	}

	/**
	 * 代码判断函数，用于判断号码类型
	 * <p>
	 * <b>应用于收付费业务处理中</b>
	 * </p>
	 * 
	 * @param strCode
	 *            号码
	 * @return 号码中包含的类型，失败返回“00”
	 */
	public static String getCodeType(String strCode) {
		if ((strCode == null) || (strCode.equals(""))) {
			return "00";
		} else {
			// 在“项目规范_约定”的“新旧号码对照.xls”中规定“号码的[1,2]位”为代码类型标志
			try {
				return strCode.substring(0, 2);
			} catch (Exception e) {
				return "00";
			}
		}
	}

	/**
	 * 代码判断函数，用于判断号码类型，判断是否是给定的类型
	 * 
	 * @param strCode
	 *            号码
	 * @param strType
	 *            给定类型
	 * @return Boolean值，true-类型相同，false-不同
	 */
	public static boolean judgeCodeType(String strCode, String strType) {
		if ((strCode == null) || (strCode.equals("")) || (strType == null)
				|| (strType.equals(""))) {
			return false;
		} else {
			try {
				return (getCodeType(strCode).compareTo(strType) == 0);
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * 根据开始位和长度，截取子串，用于获取标志位
	 * 
	 * @param strCode
	 *            号码
	 * @param strStart
	 *            开始位
	 * @param strLength
	 *            长度
	 * @return 号码中包含的类型，失败返回“00”
	 */
	public static String getCodeType(String strCode, String strStart,
			String strLength) {
		if ((strCode == null) || (strCode.equals(""))) {
			return "00";
		} else {
			try {
				return strCode.substring(Integer.parseInt(strStart), Integer
						.parseInt(strStart)
						+ Integer.parseInt(strLength));
			} catch (Exception e) {
				return "00";
			}
		}
	}

	/**
	 * 调试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// CodeJudge codeJudge1 = new CodeJudge();
		// logger.debug(getCodeType("abcdefg"));
		// logger.debug(judgeCodeType("abcdef", "11"));
		// logger.debug(getCodeType("abcdefghijk11asdfasdf"));
		// logger.debug(judgeCodeType("abcdefghijk11asdfasdf", "11"));
		// logger.debug(getCodeType("ab12cdefghijk11asdfasdf", "2", "2"));
	}
}
