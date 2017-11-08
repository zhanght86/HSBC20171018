/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.pubfun.PubFun;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:该类为错误对象的容器，一般在所有的其他处理类中都 需要包含该类，在发生错误的时候，需要在该类中设置错误信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author YT
 * @version 1.0
 * @date: 2002-06-18
 */
public class CErrors implements Cloneable, java.io.Serializable{
private static Logger logger = Logger.getLogger(CErrors.class);
	private Vector vErrors = new Vector();
	private int errorCount = 0;

	private String flag = "";
	private String content = "";
	private String result = "";

	/**
	 * 克隆CErrors对象 2005-04-15 朱向峰添加
	 * 
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
		CErrors cloned = (CErrors) super.clone();
		// clone the mutable fields of this class
		return cloned;
	}
	public String getFlag() {
		return flag;
	}

	public void setFlag(String f) {
		flag = f;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String c) {
		content = c;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String c) {
		result = c;
	}

	public CErrors() {
	}

	/**
	 * 向错误容器类中增加一个错误，错误个数加1
	 * 
	 * @param cError
	 *            CError
	 */
	public void addOneError(CError cError) {
		this.vErrors.add(cError);
		this.errorCount++;
	}

	/**
	 * 向错误容器类中增加一个错误，错误个数加1
	 * 
	 * @param cErrorString
	 *            String
	 */
	public void addOneError(String cErrorString) {
		CError tError = new CError(cErrorString.trim());
		this.vErrors.add(tError);
		this.errorCount++;
	}

	/**
	 * 移出最后的错误
	 */
	public void removeLastError() {
		if (errorCount > 0) {
			this.vErrors.removeElementAt(errorCount - 1);
			this.errorCount--;
		}
	}

	/**
	 * 移出指定的错误信息
	 * 
	 * @param pos
	 *            int
	 */
	public void removeError(int pos) {
		if ((errorCount > 0) && (pos < errorCount)) {
			this.vErrors.removeElementAt(pos);
			this.errorCount--;
		}
	}

	/**
	 * 将错误容器内的错误信息清空，计数重置为0
	 */
	public void clearErrors() {
		this.vErrors.clear();
		this.errorCount = 0;
	}

	/**
	 * 得到容器中的错误的个数
	 * 
	 * @return int
	 */
	public int getErrorCount() {
		return this.errorCount;
	}

	/**
	 * 得到容器中指定位置的错误对象
	 * 
	 * @param indexError
	 *            int
	 * @return CError
	 */
	public CError getError(int indexError) {
		CError tError;
		tError = (CError) vErrors.get(indexError);

		return tError;
	}

	/**
	 * 得到最早一个错误的错误信息,如果没有错误则返回空字符串""
	 * 
	 * @return String
	 */
	public String getFirstError() {
		try {
			CError tError = (CError) this.vErrors.get(0);

			/*
			 * kevin 2002-10-15 保证错误信息的最后一个字符不是回车。否则，这样的信息会造成javascript的错误。
			 * 替换一些特殊的字符。否则，会造成javascript的错误。
			 */
			String strMsg = tError.errorMessage;

			strMsg = strMsg.replace((char) (10), ' ');
			strMsg = strMsg.replace('"', ' ');
			strMsg = strMsg.replace('\'', ' ');

			return strMsg;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 得到最后一个错误的错误信息,如果没有错误则返回空字符串""
	 * 
	 * @return String
	 */
	public String getLastError() {
		if (errorCount < 1) {
			return "";
		}

		try {
			CError tError = (CError) this.vErrors.get(errorCount - 1);

			/*
			 * kevin 2002-10-15 保证错误信息的最后一个字符不是回车。否则，这样的信息会造成javascript的错误。
			 * 替换一些特殊的字符。否则，会造成javascript的错误。
			 */
			String strMsg = tError.errorMessage;

			strMsg = strMsg.replace((char) (10), ' ');
			strMsg = strMsg.replace('"', ' ');
			strMsg = strMsg.replace('\'', ' ');

			return strMsg;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断产生的错误是否严重到需要处理
	 * 
	 * @return boolean
	 */
	public boolean needDealError() {
		try {
			if (this.getErrorCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	/**
	 * 复制所有的错误信息到本类中
	 * 
	 * @param cSourceErrors
	 *            CErrors
	 */
	public void copyAllErrors(CErrors cSourceErrors) {
		int i = 0;
		int iMax = 0;
		iMax = cSourceErrors.getErrorCount();

		if (this.equals(cSourceErrors)) {
			logger.error("为同一个错误对象,不进行复制");
			return;
		}

		for (i = 0; i < iMax; i++) {
			this.addOneError(cSourceErrors.getError(i));
		}
	}

	/**
	 * 获取错误严重级别
	 * 
	 * @return String
	 */
	public String getErrType() {
		int forbitNum = 0;
		int needSelectNum = 0;
		int allowNum = 0;
		int unknowNum = 0;

		for (int i = 0; i < vErrors.size(); i++) {
			CError tError = (CError) this.vErrors.get(i);

			if (tError.errorType.equals(CError.TYPE_FORBID)) {
				forbitNum++;
			} else if (tError.errorType.equals(CError.TYPE_NEEDSELECT)) {
				needSelectNum++;
			} else if (tError.errorType.equals(CError.TYPE_ALLOW)) {
				allowNum++;
			} else {
				unknowNum++;
			}
		}

		if (forbitNum > 0) {
			return CError.TYPE_FORBID;
		} else if (needSelectNum > 0) {
			return CError.TYPE_NEEDSELECT;
		} else if (allowNum > 0) {
			return CError.TYPE_ALLOW;
		} else if (unknowNum > 0) {
			return CError.TYPE_UNKNOW;
		} else {
			return CError.TYPE_NONEERR;
		}
	}

	/**
	 * 获取错误内容,并分类,以提供界面显示
	 * 
	 * @return String
	 */
	public String getErrContent() {
		content = "（一共发生 " + vErrors.size() + " 个错误）\n";
		String forbitContent = "";
		String needSelectContent = "";
		String allowContent = "";
		String unknowContent = "";

		for (int i = 0; i < vErrors.size(); i++) {
			CError tError = (CError) this.vErrors.get(i);

			if (tError.errorType.equals(CError.TYPE_FORBID)) {
				forbitContent = forbitContent + "  " + "（错误编码："
						+ tError.errorNo + "）" + tError.errorMessage + "\n";
			} else if (tError.errorType.equals(CError.TYPE_NEEDSELECT)) {
				needSelectContent = needSelectContent + "  " + "（错误编码："
						+ tError.errorNo + "）" + tError.errorMessage + "\n";
			} else if (tError.errorType.equals(CError.TYPE_ALLOW)) {
				allowContent = allowContent + "  " + "（错误编码：" + tError.errorNo
						+ "）" + tError.errorMessage + "\n";
			} else {
				unknowContent = unknowContent + "  " + "（错误编码：意外错误）"
						+ tError.errorMessage + "\n";
			}
		}

		if (!forbitContent.equals("")) {
			content = content + "严重错误如下:\n" + forbitContent;
		}

		if (!needSelectContent.equals("")) {
			content = content + "需要选择的错误如下:\n" + needSelectContent;
		}

		if (!allowContent.equals("")) {
			content = content + "允许出现的错误如下:\n" + allowContent;
		}

		if (!unknowContent.equals("")) {
			content = content + "意外错误如下:\n" + unknowContent;
		}

		// content = content + forbitContent + needSelectContent + allowContent;
		// content = PubFun.changForJavaScript(content);
		// content = PubFun.changForHTML(content);
		return content;
	}

	/**
	 * 校验错误
	 * 
	 * @param cerrors
	 *            CErrors
	 */
	public void checkErrors(CErrors cerrors) {
		if (cerrors.getErrType().equals(CError.TYPE_NONE)) {
			content = "操作成功";
			flag = "Success";
		} else {
			String ErrorContent = cerrors.getErrContent();

			if (cerrors.getErrType().equals(CError.TYPE_ALLOW)) {
				content = "保存成功，但是：" + PubFun.changForHTML(ErrorContent);
				flag = "Success";
			} else {
				content = "保存失败，原因是：" + PubFun.changForHTML(ErrorContent);
				flag = "Fail";
			}
		}
	}

	/**
	 * 校验错误
	 * 
	 * @param v
	 *            VData
	 */
	public void checkWSErrors(VData v) {
		String type = (String) v.get(0);

		if (type.equals(CError.TYPE_NONE)) {
			content = "操作成功";
			flag = "Success";

			if (v.size() > 1) {
				result = (String) v.get(1);
			}
		} else {
			if (type.equals(CError.TYPE_ALLOW)) {
				content = "保存成功，但是：" + PubFun.changForHTML((String) v.get(1));
				flag = "Success";
			} else {
				content = "保存失败，原因是：" + PubFun.changForHTML((String) v.get(1));
				flag = "Fail";
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
		// CErrors tErrors = new CErrors();
		// CErrors te1 = new CErrors();
		// tErrors.addOneError("adsfad");
		// tErrors.addOneError("2");
		// tErrors.addOneError("3");
		// tErrors.removeError(0);
		// tErrors.removeLastError();
		// te1.copyAllErrors(tErrors);
		// logger.debug(te1.getErrorCount());
		// logger.debug(te1.getFirstError());
		// logger.debug(te1.needDealError());
		// if (te1.needDealError())
		// {
		// logger.debug("Need Error");
		// }
		// else
		// {
		// logger.debug("Not Need Error");
		// }

		ExeSQL es = new ExeSQL();

		for (int i = 0; i < 50; i++) {
			es.execSQL("error sql");
		}
		logger.error(es.mErrors.getErrContent());
	}
}
