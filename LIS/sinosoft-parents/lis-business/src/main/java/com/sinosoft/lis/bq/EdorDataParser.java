/*
 * @(#)EdorDataParser.java	2006-05-18
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Hashtable;

import com.sinosoft.lis.db.LPParseGuideFieldMapDB;
import com.sinosoft.lis.db.LPParseGuideVDataMapDB;
import com.sinosoft.lis.vschema.LPParseGuideDataSet;
import com.sinosoft.lis.vschema.LPParseGuideFieldMapSet;
import com.sinosoft.lis.vschema.LPParseGuideVDataMapSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 磁盘导入临时数据重组
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @date 2006-05-18
 */
public class EdorDataParser {
private static Logger logger = Logger.getLogger(EdorDataParser.class);
	public CErrors mErrors = new CErrors();
	private VData mResult;

	// 不同业务类型才需要初始化的全局变量
	private String mEdorType = ""; // 业务类型
	private int mSheetCount = 0; // 该业务类型模版的Sheet数
	private LPParseGuideFieldMapSet mLPParseGuideFieldMapSet = null;
	private LPParseGuideVDataMapSet mLPParseGuideVDataMapSet = null;
	private SSRS mTableNamesSSRS = null; // 该业务类型设计的所有表名

	// 每条业务处理都需要初始化的全局变量
	private Hashtable mHashSchema = null;
	private Hashtable mHashSet = null;

	/**
	 * 构造函数
	 */
	public EdorDataParser(String sEdorType) {
		mEdorType = sEdorType;
	}

	/**
	 * 逐条业务处理解析临时数据
	 * 
	 * @return boolean
	 */
	public boolean transform(LPParseGuideDataSet curLPParseGuideDataSet) {
		// 初始化配置信息,准备业务数据结构
		if (!initFieldMap()) {
			return false;
		}

		String sTableName = null;
		String sFieldName = null;
		String sValue = null;

		Class[] parameterTypes;
		Object[] args;
		Method method;

		for (int iSheetNo = 0; iSheetNo < mSheetCount; iSheetNo++) {
			/*
			 * 取出每个sheet的起始行标和结束行标
			 */
			int iStartRowNo = getStartRowNo(curLPParseGuideDataSet, iSheetNo);
			int iEndRowNo = getEndRowNo(curLPParseGuideDataSet, iSheetNo);

			for (int iRowNo = iStartRowNo; iRowNo <= iEndRowNo; iRowNo++) {
				// 取得一个业务处理单位（可能为多行数据）
				LPParseGuideDataSet tLPParseGuideDataSet = getRowData(
						curLPParseGuideDataSet, iSheetNo, iRowNo);
				int iColNo;
				for (int i = 1; i <= tLPParseGuideDataSet.size(); i++) {
					iColNo = tLPParseGuideDataSet.get(i).getColNo();
					sValue = tLPParseGuideDataSet.get(i).getValue();
					String[] sTableNameField = getTableField(iSheetNo, iColNo,
							sTableName, sFieldName); // sTableName,
														// sFieldName可以不传
					sTableName = sTableNameField[0];
					sFieldName = sTableNameField[1];
					/*
					 * 如果为TransferData结构的数据，则反射调用其setNameAndValue方法
					 * 其他结构则反射成相应的setField方法
					 */
					try {
						if (sTableName.equals("TransferData")) {
							logger.debug("TransferData" + i); // add
																	// xiaosong
																	// for test
							Object tTransferObject = getSchemaInstance(sTableName);
							parameterTypes = new Class[2];
							Object obj = new Object();
							parameterTypes[0] = obj.getClass();
							parameterTypes[1] = obj.getClass();
							method = tTransferObject.getClass().getMethod(
									"setNameAndValue", parameterTypes);
							args = new Object[2];
							args[0] = sFieldName;
							args[1] = sValue;
							method.invoke(tTransferObject, args);
						} else {
							Object tSchemaObject = getSchemaInstance(sTableName);
							parameterTypes = new Class[1];
							parameterTypes[0] = "".getClass();
							method = tSchemaObject.getClass().getMethod(
									"set" + sFieldName, parameterTypes);
							args = new Object[1];
							args[0] = sValue;
							method.invoke(tSchemaObject, args);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						CError.buildErr(this, "数据重组失败!" + ex.toString());
						return false;
					}
				}

				// 在每行（ROW）数据处理后将schema 加入set中
				try {
					for (int i = 1; i <= mTableNamesSSRS.MaxRow; i++) {
						sTableName = mTableNamesSSRS.GetText(i, 1);
						if (sTableName.equals("TransferData")) {
							// Do Nothing
						} else {
							// 将schema 加入set中
							Object tSetObject = getSetInstance(sTableName);
							Object tSchemaObject = getSchemaInstance(sTableName);

							method = tSchemaObject.getClass().getMethod(
									"getSchema", null);
							Object tSchemaObject_New = method.invoke(
									tSchemaObject, null);

							parameterTypes = new Class[1];
							parameterTypes[0] = tSchemaObject.getClass();
							method = tSetObject.getClass().getMethod("add",
									parameterTypes);
							args = new Object[1];
							args[0] = tSchemaObject_New;
							method.invoke(tSetObject, args);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					CError tError = new CError();
					tError.moduleName = "EdorDataParser";
					tError.functionName = "initFileInfo";
					tError.errorMessage = "程序错误：生成业务数据结构时出错!";
					mErrors.addOneError(tError);
					return false;
				}
			}

		}

		mResult = new VData();
		for (int i = 1; i <= mLPParseGuideVDataMapSet.size(); i++) {
			sTableName = mLPParseGuideVDataMapSet.get(i).getTableName();
			String sDataType = mLPParseGuideVDataMapSet.get(i).getDataType();
			if (sDataType.equals("Set")) {
				Object tSetObject = getSetInstance(sTableName);
				mResult.add(tSetObject);
			}
			// 将schema或transferdata类型的数据加入结果容器中
			else if (sDataType.equals("Schema")) {
				Object tSetObject = getSchemaInstance(sTableName);
				mResult.add(tSetObject);
			}
		}

		Object tSetObject = getSchemaInstance("TransferData");
		mResult.add(tSetObject);

		return true;
	}

	/**
	 * 读取该业务类型的配置信息
	 */
	private boolean initFieldMap() {
		// 每种业务类型只需要初始化一次的全局变量
		if (mLPParseGuideFieldMapSet == null) {
			// 字段映射描述表
			LPParseGuideFieldMapDB tLPParseGuideFieldMapDB = new LPParseGuideFieldMapDB();
			tLPParseGuideFieldMapDB.setEdorType(mEdorType);
			mLPParseGuideFieldMapSet = tLPParseGuideFieldMapDB.query();
			if (tLPParseGuideFieldMapDB.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "查询字段映射描述表配置信息失败!";
				mErrors.addOneError(tError);

				return false;
			}
			if (mLPParseGuideFieldMapSet == null
					|| mLPParseGuideFieldMapSet.size() < 1) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "查询字段映射描述表配置信息失败!";
				mErrors.addOneError(tError);

				return false;
			}

			// VData结构描述表
			LPParseGuideVDataMapDB tLPParseGuideVDataMapDB = new LPParseGuideVDataMapDB();
			tLPParseGuideVDataMapDB.setEdorType(mEdorType);
			mLPParseGuideVDataMapSet = tLPParseGuideVDataMapDB.query();
			if (tLPParseGuideVDataMapDB.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "查询VData结构描述表配置信息失败!";
				mErrors.addOneError(tError);

				return false;
			}
			if (mLPParseGuideVDataMapSet == null
					|| mLPParseGuideVDataMapSet.size() < 1) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "查询VData结构描述表配置信息失败!";
				mErrors.addOneError(tError);

				return false;
			}

			// 取得所有要处理的业务数据结构类型
			String sql = " select distinct tablename from Lpparseguidefieldmap "
					+ " where edortype = '" + "?mEdorType?" + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("mEdorType", mEdorType);
			
			ExeSQL tExeSQL = new ExeSQL();
			mTableNamesSSRS = tExeSQL.execSQL(sqlbv);
			if (mTableNamesSSRS == null || mTableNamesSSRS.MaxRow < 1) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "查询配置信息失败：无法取得要处理的业务数据类型!";
				mErrors.addOneError(tError);

				return false;
			}

			// 从配置表中查出该批改类型磁盘文件的Sheet数
			sql = " select max(sheetno) + 1 from Lpparseguidefieldmap "
					+ " where edortype = '" + "?mEdorType?" + "' ";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("mEdorType", mEdorType);
			String sqlResult = tExeSQL.getOneValue(sqlbv);
			if (tExeSQL.mErrors.needDealError() || sqlResult == null
					|| sqlResult.equals("")) {
				CError tError = new CError();
				tError.moduleName = "EdorVTSParser";
				tError.functionName = "initFileInfo";
				tError.errorMessage = "查询临时数据表失败：无法获得excel文件页数!";
				mErrors.addOneError(tError);

				return false;
			}
			mSheetCount = Integer.parseInt(sqlResult);

		}

		// 每条业务处理都需要重新初始化的全局变量
		mHashSchema = new Hashtable();
		mHashSet = new Hashtable();
		String sTableName = null; // 业务数据结构名

		/*
		 * 根据业务数据结构名，反射生成相应的数据结构，如果是TransferData则生成TransferData对象，否则
		 * 各生成一个Schema和Set
		 */
		for (int i = 1; i <= mTableNamesSSRS.MaxRow; i++) {
			sTableName = mTableNamesSSRS.GetText(i, 1);
			try {
				if (sTableName.equals("TransferData")) {
					TransferData tTransferData = new TransferData();
					mHashSchema.put("TransferData", tTransferData);
					mHashSet.put("TransferData", tTransferData);
				} else {
					Class schema = Class.forName("com.sinosoft.lis.schema."
							+ sTableName + "Schema");
					Class set = Class.forName("com.sinosoft.lis.vschema."
							+ sTableName + "Set");
					mHashSchema.put(sTableName, schema.newInstance());
					mHashSet.put(sTableName, set.newInstance());
				}
			} catch (Exception ex) {
				CError.buildErr(this, "配置信息错误!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 取出当前Sheet的行数
	 * 
	 * @param curLPParseGuideDataSet
	 * @param iSheetNo
	 * @return int
	 */
	private int getRowCount(LPParseGuideDataSet curLPParseGuideDataSet,
			int iSheetNo) {
		int iRowCount = 0;
		for (int i = 1; i <= curLPParseGuideDataSet.size(); i++) {
			if (curLPParseGuideDataSet.get(1).getSheetNo() == iSheetNo) {
				iRowCount++;
			}
		}
		return iRowCount;
	}

	/**
	 * 取出当前Sheet的起始行标
	 * 
	 * @param curLPParseGuideDataSet
	 * @param iSheetNo
	 * @return int
	 */
	private int getStartRowNo(LPParseGuideDataSet curLPParseGuideDataSet,
			int iSheetNo) {
		int iStartRowNo = curLPParseGuideDataSet.get(1).getRowNo();
		int icurRowNo;
		int icursheetNo;
		for (int i = 1; i <= curLPParseGuideDataSet.size(); i++) {
			icursheetNo = curLPParseGuideDataSet.get(i).getSheetNo();
			if (icursheetNo == iSheetNo) {
				icurRowNo = curLPParseGuideDataSet.get(i).getRowNo();
				if (icurRowNo < iStartRowNo) {
					iStartRowNo = icurRowNo;
				}
			}
		}
		return iStartRowNo;
	}

	/**
	 * 取出当前Sheet的结束行标
	 * 
	 * @param curLPParseGuideDataSet
	 * @param iSheetNo
	 * @return int
	 */
	private int getEndRowNo(LPParseGuideDataSet curLPParseGuideDataSet,
			int iSheetNo) {
		int iEndRowNo = 0;
		int icurRowNo;
		int icursheetNo;
		for (int i = 1; i <= curLPParseGuideDataSet.size(); i++) {
			icursheetNo = curLPParseGuideDataSet.get(i).getSheetNo();
			if (icursheetNo == iSheetNo) {
				icurRowNo = curLPParseGuideDataSet.get(i).getRowNo();
				if (icurRowNo > iEndRowNo) {
					iEndRowNo = icurRowNo;
				}
			}
		}
		return iEndRowNo;
	}

	/**
	 * 取出当前行数据
	 * 
	 * @param allLPParseGuideDataSet
	 * @param iSheetNo
	 * @param iRowNo
	 * @return LPParseGuideDataSet
	 */
	private LPParseGuideDataSet getRowData(
			LPParseGuideDataSet curLPParseGuideDataSet, int iSheetNo, int iRowNo) {
		LPParseGuideDataSet rLPParseGuideDataSet = new LPParseGuideDataSet();
		for (int i = 1; i <= curLPParseGuideDataSet.size(); i++) {
			if (curLPParseGuideDataSet.get(i).getSheetNo() == iSheetNo
					&& curLPParseGuideDataSet.get(i).getRowNo() == iRowNo) {
				rLPParseGuideDataSet.add(curLPParseGuideDataSet.get(i));
			}
		}
		return rLPParseGuideDataSet;
	}

	/**
	 * 读取配置信息，返回相应Sheet相应列映射的表结构字段
	 * 
	 * @param pSheetNo
	 * @param pColNo
	 * @param sTableName
	 * @param sFieldName
	 */
	private String[] getTableField(int pSheetNo, int pColNo, String sTableName,
			String sFieldName) {
		int iSheetNo;
		int iColNo;
		for (int i = 1; i <= mLPParseGuideFieldMapSet.size(); i++) {
			iSheetNo = mLPParseGuideFieldMapSet.get(i).getSheetNo();
			iColNo = mLPParseGuideFieldMapSet.get(i).getColNo();
			if (iSheetNo == pSheetNo && iColNo == pColNo) {
				sTableName = mLPParseGuideFieldMapSet.get(i).getTableName();
				sFieldName = mLPParseGuideFieldMapSet.get(i).getFieldName();
				break;
			}
		}
		String[] rTableNameField = new String[2];
		rTableNameField[0] = sTableName;
		rTableNameField[1] = sFieldName;
		return rTableNameField;
	}

	/**
	 * 获取相应业务数据结构的实例，返回Schema或Set或TransferData对象
	 * 
	 * @param sTableName
	 */
	private Object getSchemaInstance(String sTableName) {
		return mHashSchema.get(sTableName);
	}

	/**
	 * 获取相应Set实例
	 * 
	 * @param sTableName
	 */
	private Object getSetInstance(String sTableName) {
		return mHashSet.get(sTableName);
	}

	/**
	 * 返回处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		EdorDataParser tEdorDataParser = new EdorDataParser("IC");
		// tEdorDataParser.transform("6120060518000003", 1, 1);
	}
}
