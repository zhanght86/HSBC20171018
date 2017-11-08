package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

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
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class CodeQueryApplet extends JApplet implements Runnable {
private static Logger logger = Logger.getLogger(CodeQueryApplet.class);
	private Hashtable m_hashCodeControl = null;
	private Hashtable m_hashNameControl = null;
	private boolean m_bDebug = false;

	public CodeQueryApplet() {
	}

	public void init() {
		reset();

		String strDebug = this.getParameter("DEBUG");
		if (strDebug != null && strDebug.equals("TRUE")) {
			m_bDebug = true;
		}
	}

	public void debug(String strLogInfo) {
		if (m_bDebug) {
			logger.debug(strLogInfo);
		}
	}
	public void reset() {
		m_hashCodeControl = new Hashtable();
		m_hashNameControl = new Hashtable();
	}

	/**
	 * 2005-08-13 zhouping 增加一个要查询的代码信息。
	 * 
	 * @param strCodeType
	 *            String 代码的类型
	 * @param strCodeControlName
	 *            String 显示编码的控件的名字
	 * @param strNameControlName
	 *            String 显示编码内容的控件的名字
	 */
	public void addCodeToQuery(String strCodeType, String strCodeControlName,
			String strNameControlName) {

		CodeInfo codeInfo = new CodeInfo(strCodeType, strCodeControlName,
				strNameControlName, "");
		m_hashCodeControl.put(codeInfo.getCodeControlName(), codeInfo);
		m_hashNameControl.put(codeInfo.getNameControlName(), codeInfo);

		debug("addCodeToQuery");
	}

	public void codeQuery() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		getPositionInfo();

		Enumeration enumer = null;
		for (enumer = m_hashCodeControl.elements(); enumer.hasMoreElements();) {
			CodeInfo codeInfo = (CodeInfo) enumer.nextElement();
			String strCHNValue = queryCode(codeInfo.getCodeType(), codeInfo
					.getCodeValue());
			debug(codeInfo.getCodeControlName());
			JSObject.getWindow(this).eval(
					"javascript:window.document.forms["
							+ codeInfo.getFormIndexOfName() + "].elements["
							+ codeInfo.getElementIndexOfName() + "].value = '"
							+ strCHNValue + "'");
		}
	}

	private void getPositionInfo() {
		CodeInfo codeInfo = null;

		Integer intForms = null;
		int nForms = 0;
		int nFormIndex = 0;

		Integer intElements = null;
		int nElements = 0;
		int nElementIndex = 0;

		String strControlName = null;
		String strControlValue = null;
		String strNameControlName = null;

		// locate control
		intForms = (Integer) JSObject.getWindow(this).eval(
				"javascript:window.document.forms.length");
		nForms = intForms.intValue();
		nFormIndex = 0;

		// for each forms
		for (nFormIndex = 0; nFormIndex < nForms; nFormIndex++) {
			intElements = (Integer) JSObject.getWindow(this).eval(
					"javascript:window.document.forms[" + nFormIndex
							+ "].elements.length");
			nElements = intElements.intValue();
			nElementIndex = 0;

			// for each elements in each forms
			for (nElementIndex = 0; nElementIndex < nElements; nElementIndex++) {
				strControlName = (String) JSObject.getWindow(this).eval(
						"javascript:window.document.forms[" + nFormIndex
								+ "].elements[" + nElementIndex + "].name");

				// test code control
				if (m_hashCodeControl.containsKey(strControlName)) {
					codeInfo = (CodeInfo) m_hashCodeControl.get(strControlName);
					strControlValue = (String) JSObject.getWindow(this)
							.eval(
									"javascript:window.document.forms["
											+ nFormIndex + "].elements["
											+ nElementIndex + "].value");

					debug(codeInfo.getCodeControlName());

					// if the value of control is empty, remove the entry
					if (strControlValue == null || strControlValue.equals("")) {
						m_hashCodeControl.remove(codeInfo.getCodeControlName());
						m_hashNameControl.remove(codeInfo.getNameControlName());
					} else {
						codeInfo.setCodeValue(strControlValue);
					}
				}

				// test name control
				if (m_hashNameControl.containsKey(strControlName)) {
					codeInfo = (CodeInfo) m_hashNameControl.get(strControlName);
					codeInfo.setFormIndexOfName(nFormIndex);
					codeInfo.setElementIndexOfName(nElementIndex);
				}
			}
		}
	}

	private String queryCode(String strCodeType, String strCodeValue) {
		try {
			URL url = new URL(getCodeBase(),
					"../common/jsp/CodeQueryWindowApplet.jsp?codeType="
							+ strCodeType);
			URLConnection con = url.openConnection();
			con.setUseCaches(false);

			InputStream in = con.getInputStream();
			ByteArrayOutputStream baous = new ByteArrayOutputStream();
			int nChar = 0;

			while ((nChar = in.read()) != -1) {
				baous.write(nChar);
			}

			in.close();
			baous.flush();

			int nBeginIndex = 0;
			int nEndIndex = 0;
			byte[] caData = baous.toByteArray();
			// debug(new String(caData));
			// byte[] caData =
			// "0|7^0|身份证|||^1|护照|||^2|军官证|||^3|驾照|||^4|出生证明|||^5|户口簿|||^8|其他|||".getBytes();
			String strValue = "";

			if (caData[0] == '1') {
				return "";
			}

			// skip header
			while (caData[nEndIndex] != '^' && nEndIndex < caData.length) {
				nEndIndex++;
			}

			if (nEndIndex >= caData.length) {
				return "";
			}

			do {
				// get one code
				nBeginIndex = nEndIndex + 1;
				nEndIndex = nEndIndex + 1;
				while (caData[nEndIndex] != '|' && nEndIndex < caData.length) {
					nEndIndex++;
				}

				if (nEndIndex >= caData.length) {
					return "";
				}

				strValue = new String(caData, nBeginIndex, nEndIndex
						- nBeginIndex);

				// match code found
				if (strValue.equals(strCodeValue)) {
					nBeginIndex = nEndIndex + 1;
					nEndIndex = nEndIndex + 1;
					while (caData[nEndIndex] != '|' && caData[nEndIndex] != '^'
							&& nEndIndex < caData.length) {
						nEndIndex++;
					}

					if (nEndIndex >= caData.length) {
						return "";
					}

					strValue = new String(caData, nBeginIndex, nEndIndex
							- nBeginIndex);
					return strValue;
				} else {
					// skip rest content
					nBeginIndex = nEndIndex + 1;
					while (nEndIndex < caData.length
							&& caData[nEndIndex] != '^') {
						nEndIndex++;
					}
				}
			} while (nEndIndex < caData.length);

			baous.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

		return "";
	}

	public static void main(String[] argv) {
		// CodeQueryApplet cqa = new CodeQueryApplet();
		// logger.debug(cqa.queryCode("IDType", "8"));
	}
}

/**
 * 2005-08-13 zhouping Innfer bean class to store code information
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
class CodeInfo {
	private String m_strCodeType;
	private String m_strCodeControlName;
	private String m_strNameControlName;
	private int m_nFormIndexOfName = 0;
	private int m_nElementIndexOfName = 0;
	private String m_strCodeValue;

	public CodeInfo() {
		m_strCodeType = "";
		m_strCodeControlName = "";
		m_strNameControlName = "";
		m_strCodeValue = "";
	}

	public CodeInfo(String strCodeType, String strCodeControlName,
			String strNameControlName, String strCodeValue) {
		m_strCodeType = strCodeType;
		m_strCodeControlName = strCodeControlName;
		m_strNameControlName = strNameControlName;
		m_strCodeValue = strCodeValue;
	}

	public void setCodeType(String strCodeType) {
		m_strCodeType = strCodeType;
	}

	public String getCodeType() {
		return m_strCodeType;
	}

	public void setCodeControlName(String strCodeControlName) {
		m_strCodeControlName = strCodeControlName;
	}

	public String getCodeControlName() {
		return m_strCodeControlName;
	}

	public void setNameControlName(String strNameControlName) {
		m_strNameControlName = strNameControlName;
	}

	public String getNameControlName() {
		return m_strNameControlName;
	}

	public void setCodeValue(String strCodeValue) {
		m_strCodeValue = strCodeValue;
	}

	public String getCodeValue() {
		return m_strCodeValue;
	}

	public void setFormIndexOfName(int nFormIndex) {
		m_nFormIndexOfName = nFormIndex;
	}

	public int getFormIndexOfName() {
		return m_nFormIndexOfName;
	}

	public void setElementIndexOfName(int nElementIndex) {
		m_nElementIndexOfName = nElementIndex;
	}

	public int getElementIndexOfName() {
		return m_nElementIndexOfName;
	}
}
