package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.jdom.Element;

import com.sinosoft.utility.DBConnPool;

/**
 * <p>
 * Title: DataMine
 * </p>
 * <p>
 * Description: Use template to get data that print job needed
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Kevin
 * @version 1.0
 */

public class DMDataSourceImpl implements DMDataSource {
private static Logger logger = Logger.getLogger(DMDataSourceImpl.class);

	/**
	 * _szSource 这个数据源对象所对应的SQL语句 _VData 结果集
	 */
	private String _szID = "";
	private String _szSource = "";
	private String[] _szParams = new String[0];

	private int nCurRow = -1;
	private Vector _VData = new Vector();
	private Vector _VHeader = new Vector();
	private Vector _VCol = new Vector();

	private void getData(String szSource, String[] szParams) throws Exception {
		Connection conn = DBConnPool.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(szSource);
		for (int nIndex = 0; nIndex < szParams.length; nIndex++) {
			pstmt.setString(nIndex + 1, szParams[nIndex]);
		}

		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int nMaxCol = rsmd.getColumnCount();

		_VHeader.clear();
		for (int nIndex = 0; nIndex < nMaxCol; nIndex++) {
			_VHeader.add(rsmd.getColumnName(nIndex + 1).toUpperCase());
		}

		_VData.clear();
		_VCol.clear();
		// int nCount=0;
		while (rs.next()) {

			for (int nIndex = 0; nIndex < nMaxCol; nIndex++) {
				String szData = rs.getString(nIndex + 1);
				szData = szData == null ? "" : szData.trim();
				_VCol.add(nIndex, szData);
			}
			// nCount++;
			// if(nCount%20 ==0)
			// {
			// logger.debug(nCount) ;
			// logger.debug(_VData.size()) ;
			// }
			_VData.add(_VCol.clone());
			_VCol.clear();
		}

		rs.close();
		pstmt.close();
		conn.close();

		nCurRow = -1;
	}

	public DMDataSourceImpl() {
		_szSource = "";
	}

	public DMDataSourceImpl(Element element) throws Exception {
		setID(element.getAttributeValue("id"));
		setSource(element.getAttributeValue("src"));
	}

	public DMDataSourceImpl(Hashtable hashData) throws Exception {
		int nIndex = 0;
		Enumeration tEnumeration = hashData.keys();

		_VCol.clear();
		_VHeader.clear();

		while (tEnumeration.hasMoreElements()) {
			String szKey = (String) tEnumeration.nextElement();

			_VHeader.add(nIndex, szKey);
			_VCol.add(nIndex, hashData.get(szKey));
			nIndex++;
		}
	}

	public String getID() throws Exception {
		return _szID;
	}

	public void setID(String szID) throws Exception {
		_szID = szID;
	}

	public String getSource() throws Exception {
		return _szSource;
	}

	public void setSource(String szSource) throws Exception {
		if (null == szSource) {
			throw new Exception("Invalid source");
		}

		_szSource = szSource;
	}

	public void setParams(String szParams[]) throws Exception {
		if (null == szParams) {
			throw new Exception("Invalid params");
		}

		_szParams = szParams;
	}

	public void fetch() throws Exception {
		if (null == _szSource || null == _szParams) {
			throw new Exception("Invalid source or params");
		}

		getData(_szSource, _szParams);
	}

	public int getFieldCount() throws Exception {
		return _VHeader.size();
	}

	public int getRowCount() throws Exception {
		return _VData.size();
	}

	public String getFieldName(int nIndex) throws Exception {
		return (String) _VHeader.get(nIndex);
	}

	public String getFieldValue(String szName) throws Exception {
		int nIndex = -1;

		try {
			nIndex = Integer.valueOf(szName).intValue();
		} catch (Exception ex) {
			nIndex = _VHeader.indexOf(szName.toUpperCase());
		}

		if (-1 == nIndex) {
			throw new Exception("Invalid col <" + szName + "> for datasource <"
					+ _szID + ">");
		}

		if (0 == _VCol.size()) {
			return "";
		} else {
			return (String) _VCol.get(nIndex);
		}
	}

	public boolean next() throws Exception {
		if (nCurRow + 1 >= _VData.size()) {
			return false;
		}

		_VCol = (Vector) _VData.get(++nCurRow);
		return true;
	}

	public void first() throws Exception {
		nCurRow = -1;
	}

	/**
	 * Attention please! This function is "DMDataSource Clone()", not
	 * 
	 * @return DMDataSource
	 */
	public DMDataSource Clone() {
		DMDataSourceImpl dmDataSourceImpl = new DMDataSourceImpl();

		dmDataSourceImpl._szID = this._szID;
		dmDataSourceImpl._szParams = (String[]) this._szParams.clone();
		dmDataSourceImpl._szSource = this._szSource;
		dmDataSourceImpl._VCol = (Vector) this._VCol.clone();
		dmDataSourceImpl._VData = (Vector) this._VData.clone();
		dmDataSourceImpl._VHeader = (Vector) this._VHeader.clone();

		return dmDataSourceImpl;
	}

	// public static DMDataSource defaultDataSource(String szPolNo)
	// throws Exception {
	//
	// DMDataSourceImpl dmDataSourceImpl = new DMDataSourceImpl();
	//
	// dmDataSourceImpl._VCol.add(0, szPolNo);
	// dmDataSourceImpl._VHeader.add(0, "_POLNO");
	// dmDataSourceImpl._VData.add(0, dmDataSourceImpl._VCol);
	//
	// return dmDataSourceImpl;
	// }
}
