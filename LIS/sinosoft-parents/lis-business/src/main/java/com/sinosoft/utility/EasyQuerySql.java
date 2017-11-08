package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.pubfun.PubFun;

/**
 * <p>
 * Title: EasySql解析类
 * </p>
 * <p>
 * Description: 用在EasyQueryVer4Window.jsp中，解析js页面传来的jsp名称，sql的id,及参数 Edited by
 * mojiao
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author mojiao
 * @version 1.0
 */
public class EasyQuerySql {
private static Logger logger = Logger.getLogger(EasyQuerySql.class);
	private String JspName;
	private String SqlId;
	private Vector ParaName = new Vector();
	private Vector ParaValue = new Vector();
	private int ParaCount = 0;

	public EasyQuerySql() {
	}

	// 解析传入的串，格式jspname;sqlid;paraname1=paravalue1;paraname2=paravalue2;....
	public boolean parsePara(String strPara) {
		// 解析jspname
		String tStr = PubFun.getStr(strPara, 1, ";");
		if (tStr.equals(""))
			return false;
		this.JspName = tStr;
		strPara = strPara.substring(tStr.length() + 1);
		// 解析sqlid
		tStr = PubFun.getStr(strPara, 1, ";");
		if (tStr.equals(""))
			return false;
		this.SqlId = tStr;
		strPara = strPara.substring(tStr.length() + 1);
		// 解析参数
		String strOnePara, strName, strValue;
		try {
			while (true) {
				tStr = PubFun.getStr(strPara, 1, ";");
				if (tStr.equals(""))
					break;
				strOnePara = tStr;
				strName = PubFun.getStr(strOnePara, 1, "=");
				if (strName.equals(""))
					break;
				strValue = strOnePara.substring(strName.length() + 1);
				logger.debug("strValue=" + strValue);
				if (strValue != null && !strValue.equals("")) {
					if (!checkValidation(strName, strValue)) {
						return false;
					}
					this.ParaName.add(strName);
					this.ParaValue.add(strValue);
					this.ParaCount++;
				}
				strPara = strPara.substring(tStr.length() + 1);
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "interpretFactorInSQL";
			tError.errorMessage = "解释" + strPara + "的变量:" + tStr + "时出错。";
			return false;
		}
		return true;
	}

	private boolean checkValidation(String aName, String aPara) {
		String tPara = aPara.toUpperCase();
		boolean vFlag = true;
		if (aName != null && aName.toUpperCase().equals("GET")) {
			return true;
		}
		if (tPara.indexOf("SELECT") > -1 && tPara.indexOf("FROM") > -1) {
			logger.debug("96");
			vFlag = false;
		}
		if (tPara.indexOf('<') != -1) {
			vFlag = false;
		} else if (tPara.indexOf('=') != -1) {
			vFlag = false;
		} else if (tPara.indexOf('>') != -1) {
			vFlag = false;
		} else if (tPara.indexOf('%') != -1) {
			logger.debug("%");
			vFlag = false;
			// } else if (tPara.indexOf('\'') != -1) {
			// logger.debug("");
			// vFlag = false;
		} else if (tPara.indexOf('"') != -1) {
			logger.debug("");
			vFlag = false;
		} else if (tPara.indexOf(';') != -1) {
			logger.debug("");
			vFlag = false;
			// } else if (tPara.indexOf('(') != -1) {
			// vFlag = false;
			// } else if (tPara.indexOf(')') != -1) {
			// vFlag = false;
		} else if (tPara.indexOf('+') != -1) {
			logger.debug("");
			vFlag = false;
		}
		// else if (tPara.indexOf('\'') != -1) {
		// vFlag = false;
		// }

		// logger.debug("aPara:"+aPara+" is "+vFlag) ;
		if (!vFlag) {
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "interpretFactorInSQL";
			tError.errorMessage = "变量值:" + aPara + "不合法。";
			return vFlag;
		}
		return vFlag;
	}

	// 转换sql中的?paraname?为传入的值
	public String convertToValue(String strSql) {
		String strValue = "";
		String tSql, tStr = "", tStr1 = "";
		tSql = strSql;
		while (true) {
			tStr = PubFun.getStr(tSql, 2, "?");
			logger.debug("tSql=" + tStr);
			if (tStr.equals("")) {
				break;
			}
			tStr1 = "?" + tStr.trim() + "?";
			logger.debug(tStr1);
			strValue = getValueByName(tStr);
			if (strValue.equals(""))// 如果有一个参数未找到对应的值，则整个串都为空
			{
				break;
			}
			// 替换变量
			tSql = StrTool.replaceEx(tSql, tStr1, strValue);
		}
		return tSql;
	}

	// 根据名称检索传入的参数值
	private String getValueByName(String cVarName) {
		String strName = "";
		String strValue = "";
		for (int i = 0; i < this.ParaName.size(); i++) {
			strName = (String) ParaName.get(i);
			if (strName.toUpperCase().equals(cVarName.toUpperCase())) {
				strValue = (String) ParaValue.get(i);
				break;
			}
		}
		return strValue;
	}

	public String getJspName() {
		return JspName;
	}

	public String getParaName(int index) {
		return (String) this.ParaName.get(index);
	}

	public String getParaValue(int index) {
		return (String) this.ParaValue.get(index);
	}

	public String getSqlId() {
		return SqlId;
	}

	public static void main(String[] args) {
		EasyQuerySql t = new EasyQuerySql();
		String sql = t
				.convertToValue("select CustomerNo, Name, Sex, Birthday, IDType, IDNo from LDPerson where 1=1 and name? like '?Name?'");
		logger.debug(sql);
		// t.parsePara("a.jsp;sql1;a=1;b=2;c=3;d=4;e=5;f=6;");
		// t.generateSql("selec * from dual where 1=1{and polno=?b?{and
		// contno=?c?{and grpconno=?d?}}"
		// + "and 2=2{and rskno=?f?}");
	}

	public int getParaCount() {
		return ParaCount;
	}
}
