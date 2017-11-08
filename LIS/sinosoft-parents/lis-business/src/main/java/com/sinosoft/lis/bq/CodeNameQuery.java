package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title:获取code名称
 * </p>
 * 
 * <p>
 * Description:获得code相对应的name
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wangyan
 * 
 * @version 1.0
 */

public class CodeNameQuery {
private static Logger logger = Logger.getLogger(CodeNameQuery.class);
	// 全局变量
	private String mCodeType;
	private String mCode;
	private String mCodeName;

	public void CodeNameQuery() {
	}

	public String getName(String aCodeType, String aCode) {
		mCodeType = aCodeType;
		mCode = aCode;
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select codename from ldcode where codetype = '"
				+ "?mCodeType?" + "' and code = '" + "?mCode?" + "'";
		sqlbv.sql(i_sql);
		sqlbv.put("mCodeType", mCodeType);
		sqlbv.put("mCode", mCode);
		mCodeName = tExeSQL.getOneValue(sqlbv);
		return mCodeName;
	}

	/**
	 * 省
	 * 
	 * @param aCode
	 *            String
	 * @return String
	 */
	public String getProvince(String aCode) {
		mCode = aCode;
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select placename from LDAddress where placetype='01' and placecode = '"
				+ "?mCode?" + "'";
		sqlbv.sql(i_sql);
		sqlbv.put("mCode", mCode);
		mCodeName = tExeSQL.getOneValue(sqlbv);
		return mCodeName;
	}

	/**
	 * 市
	 * 
	 * @param aCode
	 *            String
	 * @return String
	 */
	public String getCity(String aCode) {
		mCode = aCode;
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select placename from LDAddress where placetype='02' and placecode = '"
				+ "?mCode?" + "'";
		sqlbv.sql(i_sql);
		sqlbv.put("mCode", mCode);
		mCodeName = tExeSQL.getOneValue(sqlbv);
		return mCodeName;
	}

	/**
	 * 区
	 * 
	 * @param aCode
	 *            String
	 * @return String
	 */
	public String getDistrict(String aCode) {
		mCode = aCode;
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select placename from LDAddress where placetype='03' and placecode = '"
				+ "?mCode?" + "'";
		sqlbv.sql(i_sql);
		sqlbv.put("mCode", mCode);
		mCodeName = tExeSQL.getOneValue(sqlbv);
		return mCodeName;
	}

	/**
	 * 职业类别
	 * 
	 * @param aCode
	 *            String
	 * @return String
	 */
	public String getOccupationName(String aCode) {
		mCode = aCode;
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select OccupationName from LDOccupation where OccupationCode = '"
				+ "?mCode?" + "'";
		sqlbv.sql(i_sql);
		sqlbv.put("mCode", mCode);
		mCodeName = tExeSQL.getOneValue(sqlbv);
		return mCodeName;
	}

	public static String getOperator(String aCode) {
		// mCode = aCode;
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String i_sql = "select username from lduser where (usercode) = '?aCode?'";
		sqlbv.sql(i_sql);
		sqlbv.put("aCode", aCode);
		String aCodeName = "";
		aCodeName = tExeSQL.getOneValue(sqlbv);
		if (aCodeName == null || aCodeName.equals("")) {
			aCodeName = aCode;
		}
		return aCodeName;
	}

}
