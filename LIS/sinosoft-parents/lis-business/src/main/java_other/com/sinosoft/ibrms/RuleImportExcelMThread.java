package com.sinosoft.ibrms;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.sinosoft.lis.pubfun.ExtPubSubmit;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:多线程测试
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */

public class RuleImportExcelMThread extends CovBase {
	private static Logger logger = Logger
			.getLogger(RuleImportExcelMThread.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputDataNew;

	/** 数据操作字符串 */
	private String mOperate;

	/** SQL是否使用绑定变量 */
	Boolean paramFlag = true;

	public RuleImportExcelMThread() {
	}

	public static void main(String[] args) {

	}

	public void setObject(Object tObject) {
		// 多线程的外部参数条件
		mInputDataNew = (VData) tObject;
	}

	public void run() {

		TransferData mTransferData = new TransferData();
		mTransferData = mInputDataNew.getObjectByObjectName("TransferData", 0) == null ? null
				: (TransferData) mInputDataNew.getObjectByObjectName(
						"TransferData", 0);
		mOperate = String.valueOf(mTransferData.getValueByName("tOperate"));
		if ("prepare".equalsIgnoreCase(mOperate)) {
			String maxRow = String.valueOf(mTransferData
					.getValueByName("maxRow"));
			Element node = (Element) mTransferData.getValueByName("node");
			String tRuleName = (String) mTransferData
					.getValueByName("RuleName");
			Hashtable mKeyHashtable = (Hashtable) mTransferData
					.getValueByName("mKeyHashtable");
			String ColumnNames = (String) mTransferData
					.getValueByName("ColumnNames");
			String ColumnTypes = (String) mTransferData
					.getValueByName("ColumnTypes");

			Hashtable tKeyHashtable = (Hashtable) mTransferData
					.getValueByName("tKeyHashtable");
			ArrayList tInsertArray = (ArrayList) mTransferData
					.getValueByName("tInsertArray");
			ArrayList tDeleteArray = (ArrayList) mTransferData
					.getValueByName("tDeleteArray");
			StringBuffer xml = (StringBuffer) mTransferData
					.getValueByName("xml");
			CErrors tErrors = (CErrors) mTransferData.getValueByName("mErrors");

			StringBuilder colNames = new StringBuilder();
			StringBuilder colValues = new StringBuilder();
			StringBuilder tDeleteSQL = new StringBuilder();
			StringBuilder tKeyValue = new StringBuilder();

			StringBuilder rowString = new StringBuilder("<rows>");

			// 绑定变量型sql需要变量
			VData tDeleteData = new VData();
			VData tInsertData = new VData();
			TransferData tDeleteParams = new TransferData();
			TransferData tInsertParams = new TransferData();
			int aDeleteCount = 0;
			int aInsertCount = 0;

			if (paramFlag) {
				String[] tColumns = ColumnNames.split(";");
				String[] tTypes = ColumnTypes.split(";");
				for (Iterator index = node.elementIterator(); index.hasNext();) {
					Element element = (Element) index.next();

					String colName = element.getName();
					String colValue = element.getTextTrim();
					if (colValue.equals("")) {
						colValue = "''";
					}
					// tongmeng 2011-06-15 modify
					if (colName.toUpperCase().equals("RULEID")) {
						//colValue = PubFun1.CreateMaxNo("ibrms" + tRuleName, 4);
						colValue = maxRow;
					}

					colNames.append(colName).append(",");
					colValues.append("?").append(",");

					String bindValue = setBindType(colName, colValue, tColumns,
							tTypes);// 变量的类型和值
					tInsertParams.setNameAndValue(String.valueOf(aInsertCount),
							bindValue);
					aInsertCount++;
					if (mKeyHashtable.containsKey(colName)) {
						tKeyValue.append(colValue).append("_");
						tDeleteSQL.append(" and ").append(colName)
								.append(" = ").append("?");
						tDeleteParams.setNameAndValue(String
								.valueOf(aDeleteCount), bindValue);
						aDeleteCount++;
					}
					rowString.append("<").append(colName).append(">").append(
							colValue).append("</").append(colName).append(">");
				}
				rowString.append("</rows>");
				int end = colNames.length();
				colNames.replace(0, end, colNames.substring(0, end - 1));
				end = colValues.length();
				colValues.replace(0, end, colValues.substring(0, end - 1));

				// 校验是否有重复值
				String tKeyValueStr = tKeyValue.toString();
				if (tKeyHashtable.containsKey(tKeyValueStr)) {
					int tempRow = Integer.parseInt((String) tKeyHashtable
							.get(tKeyValueStr));
					String tErrorStr = "第" + maxRow + "行与第" + tempRow
							+ "行数据重复!";
					tErrors.addOneError(tErrorStr);
					logger.debug("$$$$$$$$$$$$$$$$$$" + tErrorStr);
				} else {
					tKeyHashtable.put(tKeyValueStr, maxRow);
					logger.debug("$$$$$$$$$$$$$$$$$$:tKeyValue:maxRow:"
							+ tKeyValueStr + ":" + maxRow);

					tDeleteData.add(" where 1=1 " + tDeleteSQL.toString());
					tInsertData.add("(" + colNames.toString() + ") values("
							+ colValues.toString() + ")");
					tDeleteData.add(tDeleteParams);
					tInsertData.add(tInsertParams);

					tInsertArray.add(tInsertData);
					tDeleteArray.add(tDeleteData);
					xml.append(rowString);
				}
			} 
		} else if ("add".equalsIgnoreCase(mOperate)) {
			String maxRow = String.valueOf(mTransferData
					.getValueByName("maxRow"));
			String[][] temp = (String[][]) mTransferData.getValueByName("temp");
			Hashtable mKeyHashtable = (Hashtable) mTransferData
					.getValueByName("mKeyHashtable");
			String ColumnNames = (String) mTransferData
					.getValueByName("ColumnNames");
			String ColumnTypes = (String) mTransferData
					.getValueByName("ColumnTypes");

			Hashtable tKeyHashtable = (Hashtable) mTransferData
					.getValueByName("tKeyHashtable");
			ArrayList tInsertArray = (ArrayList) mTransferData
					.getValueByName("tInsertArray");
			ArrayList tDeleteArray = (ArrayList) mTransferData
					.getValueByName("tDeleteArray");
			StringBuffer xml = (StringBuffer) mTransferData
					.getValueByName("xml");
			CErrors tErrors = (CErrors) mTransferData.getValueByName("mErrors");

			if (this.paramFlag) {
				StringBuilder colNames = new StringBuilder();
				StringBuilder colValues = new StringBuilder();
				StringBuilder tDeleteSQL = new StringBuilder();
				StringBuilder tKeyValue = new StringBuilder();
				StringBuilder rowString = new StringBuilder("<rows>");

				// 绑定变量型sql需要变量
				VData tDeleteData = new VData();
				VData tInsertData = new VData();
				TransferData tDeleteParams = new TransferData();
				TransferData tInsertParams = new TransferData();
				int aDeleteCount = 0;
				int aInsertCount = 0;

				int aSize = temp.length;
				String[] tColumns = ColumnNames.split(";");
				String[] tTypes = ColumnTypes.split(";");
				for (int n = 0; n < aSize; n++) {
					String tColName = temp[n][0];
					String tColValue = temp[n][1];

					colNames.append(tColName).append(",");
					colValues.append(tColValue).append(",");

					String bindValue = setBindType(tColName, tColValue,
							tColumns, tTypes);// 变量的类型和值
					tInsertParams.setNameAndValue(String.valueOf(aInsertCount),
							bindValue);
					aInsertCount++;
					if (mKeyHashtable.containsKey(tColName)) {
						tKeyValue.append(tColValue).append("_");

						tDeleteSQL.append(" and ").append(tColName).append(
								" = ").append("?");
						tDeleteParams.setNameAndValue(String
								.valueOf(aDeleteCount), bindValue);
						aDeleteCount++;
					}
					rowString.append("<").append(tColName).append(">").append(
							tColValue).append("</").append(tColName)
							.append(">");
				}
				rowString.append("</rows>");
				int end = colNames.length();
				colNames.replace(0, end, colNames.substring(0, end - 1));
				end = colValues.length();
				colValues.replace(0, end, colValues.substring(0, end - 1));

				// 校验是否有重复值
				String tKeyValueStr = tKeyValue.toString();
				if (tKeyHashtable.containsKey(tKeyValueStr)) {
					//由于增量导入是用新上传信息的覆盖原有信息，所以不报错
//					int tempRow = Integer.parseInt((String) tKeyHashtable
//							.get(tKeyValueStr));
//					String tErrorStr = "第" + maxRow + "行与第" + tempRow
//							+ "行数据重复!";
//					tErrors.addOneError(tErrorStr);
//					logger.debug("$$$$$$$$$$$$$$$$$$" + tErrorStr);
				} else {
					tKeyHashtable.put(tKeyValueStr, maxRow);
					logger.debug("$$$$$$$$$$$$$$$$$$:tKeyValue:maxRow:"
							+ tKeyValueStr + ":" + maxRow);

					tDeleteData.add(" where 1=1 " + tDeleteSQL.toString());
					tInsertData.add("(" + colNames.toString() + ") values("
							+ colValues.toString() + ")");
					tDeleteData.add(tDeleteParams);
					tInsertData.add(tInsertParams);

					tInsertArray.add(tInsertData);
					tDeleteArray.add(tDeleteData);
					xml.append(rowString);
				}
			}
		} else if ("submit".equalsIgnoreCase(mOperate)) {
			if (paramFlag) {
				CErrors tErrors = (CErrors) mTransferData.getValueByName("mErrors");
				mInputDataNew.remove(mTransferData);//移除操作符
				MMap map = new MMap();
				Iterator aIterator = mInputDataNew.iterator();
				while(aIterator.hasNext()){
					VData aInputData = (VData)aIterator.next();
					TransferData aTransferData = new TransferData();
					aTransferData = aInputData.getObjectByObjectName("TransferData", 0) == null ? null
							: (TransferData) aInputData.getObjectByObjectName(
									"TransferData", 0);
					String aTableName = String.valueOf(aTransferData
							.getValueByName("TableName"));
					VData aInsert = (VData) aTransferData.getValueByName("insert");
					VData aDelete = (VData) aTransferData.getValueByName("delete");

					String tDeleteSQL = "delete from " + aTableName
							+ (String) aDelete.getObject(0);
					String tInsertSQL = "insert into " + aTableName
							+ (String) aInsert.getObject(0);
					aDelete.set(0, tDeleteSQL);
					aInsert.set(0, tInsertSQL);
					map.put(aDelete, "DELETE");
					map.put(aInsert, "INSERT");
				}
				this.mInputData.add(map);
				ExtPubSubmit tPubSubmit = new ExtPubSubmit();
				if (!tPubSubmit.submitData(mInputData, "")) {
					tErrors.addOneError("数据提交失败!");
				}
			} 
		}
	}

	/**
	 * 设置绑定变量的具体内容，格式为“类型:值”
	 * @param colName
	 * @param colValue
	 * @param colNames
	 * @param colTypes
	 * @return
	 */
	private String setBindType(String colName, String colValue,
			String[] colNames, String[] colTypes) {
		int aSize = colNames.length;
		String colType = "";
		for (int i = 0; i < aSize; i++) {
			if (colName.equalsIgnoreCase(colNames[i])) {
				colType = colTypes[i].toUpperCase();
				break;
			}
		}
		if ("VARCHAR2".equals(colType) || "STRING".equals(colType)) {
			colType = "string";
		} else if ("NUMBER".equals(colType)) {
			colType = "double";
		} else if ("DATE".equals(colType)) {
			colType = "string";// colValue="to_date('"+colValue+"','yyyy-mm-dd
			// hh24:mi:ss')";
		} else {
			colType = "string";
		}
		return colType + ":" + colValue;
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}