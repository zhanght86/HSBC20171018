package com.sinosoft.service;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.CodeQueryBL;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Sql解析
 * </p>
 * 
 * <p>
 * Description: 转发设计
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author litao
 * @version 1.0
 */

public class SqlParse {
	private static Logger logger = Logger.getLogger(SqlParse.class);

	/** SQLINFO分割符 */
	public static final String SQLINFO = ";";

	/** SQL PARA分割符 */
	public static final String SQLPAEA = ":";

	/** 子SQL 标志符 */
	public static final String SQLSUBPAEA = "*";

	public static final String SQLSHARP = "#";

	/**
	 * 解析SQL
	 */
	public String parseSQL(String mSQL) {
		if ((mSQL == null) || mSQL.trim().equals("")) {
			throw new ServiceException("解析SQL信息出错");
		}
		String[] tSql = mSQL.split(SQLINFO);
		if (tSql.length < 2) {
			throw new ServiceException("解析SQL信息出错");
		}
		String[] tPara = tSql[2].split(SQLPAEA);
		for (int i = 0; i < tPara.length; i++) {
			tPara[i] = dealSubSql(tPara[i], tSql[0], tSql[1] + "_" + i);
			logger.debug("tPara[" + i + "]:" + tPara[i]);
		}
		logger.debug("tSql[0]" + tSql[0]);
		logger.debug("tSql[1]" + tSql[1]);
		mSQL = SqlMessage.getSqlMessage().getSql(tSql[0], tSql[1], tPara);
		mSQL = getTranslatedSQL(mSQL);
		return mSQL;
	}

	/**
	 * 解析SQL
	 */
	public VData parseSQLBV(String mSQL) {
		VData tParseResult = new VData();
		if ((mSQL == null) || mSQL.trim().equals("")) {
			throw new ServiceException("解析SQL信息出错");
		}
		String[] tSql = mSQL.split(SQLINFO);
		if (tSql.length < 2) {
			throw new ServiceException("解析SQL信息出错");
		}
		String[] tPara = null;
		if(tSql.length > 2){
			 tPara = tSql[2].split(SQLPAEA);
		}
		// app.QueryMenuPageSql;QueryMenuPageSql1;*001:*86
		// logger.debug("mSQL:"+mSQL);
		// logger.debug("tSql[0]:"+tSql[0]);
		// logger.debug("tSql[1]:"+tSql[1]);
		// 使用正则表达式替换
		// Pattern pattern = Pattern.compile("(\\{.+?\\})");
		String tResourceName = tSql[0];
		String tSourceSQL = SqlMessage.getSqlMessage().getSourceSql(tSql[0],
				tSql[1]);
		tSourceSQL = tSourceSQL.replaceAll("''", "'");//提前替换，如果参数中出现两个单引号的时候不被错误替换
		logger.debug("tResourceName:" + tResourceName + ";tSQL:" + tSql[1]);
		logger.debug("tSourceSQL:" + tSourceSQL);
		
		mSQL = tSourceSQL.trim();
		// zyx 2016-05-25 修改对存储过程的调用支持
		boolean isCallFlag = false;
		if(mSQL.charAt(0)=='{'&&mSQL.charAt(mSQL.length()-1)=='}'){
			isCallFlag = true;
			mSQL = mSQL.substring(1, mSQL.length()-1);
		}
		Pattern pattern = Pattern.compile("(\\'*\\{.+?\\}\\'*)");
		Matcher matcher = pattern.matcher(mSQL);
		int i = 0;
		TransferData tParams = new TransferData();
		try {
			while (matcher.find()) {

				String tCurrentGroup = matcher.group();
				String tKeyCode = " ? ";

				String tKeyIdx = tCurrentGroup.replaceAll("[^0-9]", "");
				// logger.debug("group:"+matcher.group()+":tKeyIdx:"+tKeyIdx);
				String tSQL_i = tSql[1] + "_" + tKeyIdx;
				String tSQL_i_Value = SqlMessage.getSqlMessage().getSourceSql(
						tResourceName, tSQL_i);
				// logger.debug("tSQL_i_Value:"+tSQL_i_Value+":"+tSQL_i_Value.replaceAll("(\\'*\\{.+?\\}\\'*)",
				// ""));
				// logger.debug("@@@tSQL_i_Value:"+tPara[Integer.parseInt(tKeyIdx)].substring(1));
				String tReplaceStr = "";
				if (tPara[Integer.parseInt(tKeyIdx)].substring(1).trim().equals("")) {
					tReplaceStr = matcher.replaceFirst("");
					matcher = pattern.matcher(tReplaceStr);
					mSQL = tReplaceStr;
				} else {

					String tReplaceCode = tSQL_i_Value.replaceAll(
							"(\\'*\\{.+?\\}\\'*)", tKeyCode);

					String tParamType = "";
					String tParamValue = "";
					tParamValue = tPara[Integer.parseInt(tKeyIdx)].substring(1);
					if (tReplaceCode.trim().equals(tKeyCode.trim())) {

						if (tReplaceCode.trim().equals("?")
								&& tParamValue.replaceAll("(\\'*\\{.+?\\}\\'*)",
										tKeyCode).indexOf("?") == -1) {
							if (tCurrentGroup.indexOf("'") != -1)
							{
								tReplaceCode = "'"+tParamValue+"'";
							}
							else
							{
								tReplaceCode = tParamValue;
							}
						} else {
							
								tReplaceCode = tKeyCode;
							
							if (tCurrentGroup.indexOf("'") != -1
									|| tSQL_i_Value.replaceAll(
											"(\\'*\\{.+?\\}\\'*)", "").trim()
											.equals("")) {
								tParamType = "string";
							} else {
								// tParamType = "double";
								// tParamType = "int";
								try
								{
									Integer.parseInt(tParamValue);
									tParamType = "int";
								}
								catch(Exception e)
								{
									tParamType = "double";
								}
							}
						}
					} else {
						tReplaceCode = tReplaceCode.replaceAll("''", "'");
						if (tSQL_i_Value.indexOf("'") != -1
								|| tSQL_i_Value.trim().equals("")) {
							tParamType = "string";
						} else {
							// tParamType = "double";
							// tParamType = "int";
							try
							{
								Integer.parseInt(tParamValue);
								tParamType = "int";
							}
							catch(Exception e)
							{
								tParamType = "double";
							}
						}
					}
					// if(tParamValue!=null&&!tParamValue.equals(""))
					// {
					// tParams.setNameAndValue(String.valueOf(i),tParamType+":"+tParamValue);
					// }

					tReplaceStr = matcher.replaceFirst(tReplaceCode);
					if (tReplaceCode.indexOf("?") != -1) {
						tParams.setNameAndValue(String.valueOf(i), tParamType + ":"
								+ tParamValue);
					}
					// String test = matcher.replaceFirst("test");
					// logger.debug("tReplaceStr:" + tReplaceStr);
					matcher = pattern.matcher(tReplaceStr);
					mSQL = tReplaceStr;
					i++;
				}
			}
		} catch (Exception e) {
			throw new ServiceException("解析SQL信息出错,请检查您的语句中的参数数量。");
		}

		// logger.debug("tSql[0]"+tSql[0]);
		// logger.debug("tSql[1]"+tSql[1]);
		mSQL = mSQL.replaceAll("''''", "''");//特殊处理
		// mSQL = SqlMessage.getSqlMessage().getSql(tSql[0], tSql[1], tPara);
		// mSQL = getTranslatedSQL(mSQL);

		VData tempVData = dealTableName(mSQL, tParams);
		if (tempVData != null) {
			mSQL = (String) tempVData.getObject(0);
			tParams = (TransferData) tempVData.getObject(1);
		}
		if(isCallFlag==true){
			mSQL = "{"+mSQL+"}";
		}
		logger.debug("mSQL:" + mSQL);
		for (int m = 0; m < tParams.getValueNames().size(); m++) {
			logger.debug(tParams.getValueByName((String) tParams
					.getValueNames().get(m)));
		}
		tParseResult.add(mSQL);
		tParseResult.add(tParams);
		return tParseResult;
	}

	/**
	 * 增加一个对sql和参数的处理，因为表名不可作为参数，所以要在此将表名赋给sql
	 * 
	 * @param tSQL
	 * @param tParams
	 * @return
	 */
	private VData dealTableName(String tSQL, TransferData tParams) {
		VData tVData = new VData();
		// 判断是否有表名参数，使用match，匹配"from ?"并替换
		String tRegex = "(from +[?]{1})";
		Pattern tPattern = Pattern.compile(tRegex);
		Matcher tMatcher = tPattern.matcher(tSQL);
		if (tMatcher.find()) {
			Vector tNames = tParams.getValueNames();
			int i = 0;
			String tName = new String();
			String[] tStringArr = new String[2];
			String[] tTableNames = new String[2];
			{
				tStringArr = tSQL.split(tRegex, 2);
				if (tStringArr.length == 2) {
					i = tStringArr[0].split("[?]+").length - 1;
				} else {
					return null;
				}
				tName = (String) tNames.get(i);
				tTableNames = ((String) tParams.getValueByName(tName)).split(
						":", 2);
				if (tTableNames.length != 2
						|| !tTableNames[0].equalsIgnoreCase("string")) {
					return null;
				}
				String aTableName = tTableNames[1];
				tSQL = tStringArr[0] + " from " + aTableName + tStringArr[1];
				tParams.removeByName(tName);
				tMatcher = tPattern.matcher(tSQL);
			}
			while (tMatcher.find())
				;
			tVData.add(tSQL);
			tVData.add(tParams);
			return tVData;
		} else {
			return null;
		}
	}

	/**
	 * notes added by zhouwh@sinosoft.com.cn 20101029 翻译SQL
	 * 
	 * @param mSQL
	 * @return
	 */
	private String getTranslatedSQL(String mSQL) {
		String tSQL = "";
		int sharpindex = mSQL.indexOf(SQLSHARP);
		while (sharpindex != -1) {
			tSQL = tSQL + mSQL.substring(0, sharpindex);
			mSQL = mSQL.substring(sharpindex + 1, mSQL.length());
			int tempindex = mSQL.indexOf(SQLSHARP);
			if (tempindex != -1) {
				CodeQueryBL codequerybl = new CodeQueryBL();
				// 获取SQL中的资源文件id，并根据codequerybl进行翻译处理
				String tResourceId = codequerybl.transI18NMenu(mSQL.substring(
						0, tempindex));
				tSQL = tSQL + tResourceId;
				mSQL = mSQL.substring(tempindex + 1, mSQL.length());
			}
			sharpindex = mSQL.indexOf(SQLSHARP);
		}
		return tSQL + mSQL;
	}

	/** 获取参数信息,处理子SQL参数 */
	private String dealSubSql(String tStr, String resource, String sqlID) {
		if (tStr == null || tStr.equals("")) {
			return "";
		}
		if (tStr.startsWith(SQLSUBPAEA)) {
			String ts = tStr.substring(1);
			if (ts == null || ts.equals("")) {
				return "";
			} else {
				return SqlMessage.getSqlMessage().getSql(resource, sqlID,
						new String[] { ts });
			}
		} else {
			return tStr;
		}
	}
	
}
