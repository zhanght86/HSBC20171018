package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Set;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 新契约工作流
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class TbWorkFlowBLS {
private static Logger logger = Logger.getLogger(TbWorkFlowBLS.class);
	// 传输数据类
	private VData mInputData;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据库连接 * */
	private Connection conn = null;

	/** 立即提交标志 * */
	private boolean commitFlag = true;

	public TbWorkFlowBLS() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start TbWorkFlowBLS Submit...");
		if (!this.saveData()) {
			return false;
		}
		logger.debug("End TbWorkFlowBLS Submit...");

		mInputData = null;
		return true;
	}
	
	/**
	 * 获取Blob操作所需信息公共方法
	 * 
	 * @param o
	 *            Object
	 * @param tSQL
	 *            String
	 * @param pTabName
	 *            String
	 * @param pUpdateField
	 *            String
	 * @param pWhereSQL
	 *            String
	 * @return String[]
	 */
	private static String[] getBlobInsertStr(Object o, String tSQL,
			String pTabName, String pUpdateField, String pWhereSQL) {
		String aClassName = o.getClass().getName();

		if (aClassName.endsWith("Schema")) {
			Schema s = (Schema) o;
			String[] pk = s.getPK();
			pTabName = aClassName.substring(aClassName.lastIndexOf(".") + 1,
					aClassName.lastIndexOf("S"));

			// String ColPart = "( ";
			String ValPart = "values(";
			String mark = "'";

			int nFieldCount = s.getFieldCount();
			int jj = 0;

			for (int i = 0; i < nFieldCount ; i++) { // 只循环（nFieldCount-1）次，使blob字段不加入循环
				String strFieldName = s.getFieldName(i);
				String strFieldValue = s.getV(i);
				for (int ii = 0; ii < pk.length; ii++) {
					if (strFieldName.equals(pk[ii])) {
						pWhereSQL += strFieldName + " = '" + strFieldValue
								+ "' and ";
					}
				}
				int nFieldType = s.getFieldType(i);
				logger.debug(strFieldName+":"+nFieldType);
				boolean bFlag = false;

				switch (nFieldType) {
				case Schema.TYPE_STRING:
				case Schema.TYPE_DATE:
					if (!strFieldValue.equals("null")) {
						strFieldValue = mark + strFieldValue + mark;
						bFlag = true;
					}
					break;
				case Schema.TYPE_DOUBLE:
				case Schema.TYPE_FLOAT:
				case Schema.TYPE_INT:
					bFlag = true;
					break;
				case Schema.TYPE_BLOB:
					bFlag = true;
					pUpdateField = s.getFieldName(i);
					break;
				case Schema.TYPE_NOFOUND:
					pUpdateField = s.getFieldName(i);
					bFlag = true;
					break;
				default:
					bFlag = false;
					break;
				}

				if (bFlag) {
					jj++;
					if (jj != 1) {
						// ColPart += ",";
						ValPart += ",";
					}
					// ColPart += strFieldName;
					if(nFieldType==Schema.TYPE_BLOB)
					{
						ValPart += "empty_blob()"; // 添加empty_blob
					}
					else
					{
						ValPart += strFieldValue;
					}
				}
			} // end of for
			// ColPart += " )";
			//ValPart += ",";
			//ValPart += "empty_blob()"; // 添加empty_blob
			ValPart += ")";
			tSQL = "insert into " + pTabName + " " + ValPart;
			if (jj == 0) {
				tSQL = "";
			}
			//pUpdateField = s.getFieldName(nFieldCount - 1);
			if (pWhereSQL.lastIndexOf("and") != -1) {
				pWhereSQL = "and "
						+ pWhereSQL.substring(0, pWhereSQL.lastIndexOf("and"));

			}
		} else {
			return null;
		}
		String[] returnStr = new String[4];
		returnStr[0] = tSQL;
		returnStr[1] = pTabName;
		returnStr[2] = pUpdateField;
		returnStr[3] = pWhereSQL;
		return returnStr;
	}

	

	/**
	 * 数据库操作
	 * 
	 * @return: boolean
	 */
	private boolean saveData() {
		logger.debug("---Start Save---");

		// 建立数据库连接
		if (conn == null) {
			conn = DBConnPool.getConnection();
		}

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TbWorkFlowBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			String action = ""; // 操作方式，INSERT\UPDATE\DELETE
			String className = ""; // 类名
			Object o = null; // Schema或Set对象
			Object DBObject = null; // DB或DBSet对象
			Method m = null; // 方法
			Constructor constructor = null; // 构造函数
			Class[] parameterC = new Class[1]; // 调用方法的参数数组
			Object[] parameterO = new Object[1]; // 调用方法的对象数组
			logger.debug("mInputData.size():" + mInputData.size());
			logger.debug("mInputData :" + mInputData);
			// 通过MMap来传递每个Schema或Set的数据库操作方式，约定使用
			for (int i = 0; i < mInputData.size(); i++) {
				VData tData = new VData();
				tData = (VData) mInputData.get(i);
				MMap map = (MMap) tData.getObjectByObjectName("MMap", 0);
				if (map != null && map.keySet().size() != 0) {
					logger.debug("map.keySet().size():"
							+ map.keySet().size());
					Set set = map.keySet();
					// Iterator iterator = map.keySet().iterator();
					// while (iterator.hasNext()) {
					for (int j = 0; j < set.size(); j++) {
						// 获取操作对象Schema或Set或SQL
						// o = iterator.next();
						o = map.getOrder().get(String.valueOf(j + 1));
						// 获取操作方式
						action = (String) map.get(o);
						if (action == null) {
							continue;
						}
						logger.debug("\n" + o.getClass().getName()
								+ " Operate DB: " + action);

						// 构造相应的DB类名
						className = o.getClass().getName();
						logger.debug("className :" + className);
						logger.debug("action :" + action);

						if (className.endsWith("String")) {
							// 加入对于select语句的支持，hl
							if (action.equals("SELECT")) {
								className = "com.sinosoft.lis.db."
										+ className.substring(className
												.lastIndexOf(".") + 1,
												className.lastIndexOf("S"))
										+ "DB";
								SQLwithBindVariables sqlbva = new SQLwithBindVariables();
								if(o instanceof SQLwithBindVariables){
									sqlbva = (SQLwithBindVariables)o;
								}else{
									String tSQL = (String) o;
									sqlbva.sql(tSQL);
								}
								
								ExeSQL tExeSQL = new ExeSQL(conn);
								// logger.debug("执行SQL语句:" + tSQL);
								SSRS tSSRS = new SSRS();
								tSSRS = tExeSQL.execSQL(sqlbva);
								if (tSSRS.getMaxRow() > 0) {
									CError.buildErr(this, "操作数据重复，请确认！");
									conn.rollback();
									conn.close();
									return false;
								}
								continue;
							}

							else {
								className = "com.sinosoft.lis.db."
										+ className.substring(className
												.lastIndexOf(".") + 1,
												className.lastIndexOf("S"))
										+ "DB";
								SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
								if(o instanceof SQLwithBindVariables){
									sqlbvb = (SQLwithBindVariables)o;
								}else{
									String tSQL = (String) o;
									sqlbvb.sql(tSQL);
								}
								ExeSQL tExeSQL = new ExeSQL(conn);
//								logger.debug("执行SQL语句:" + tSQL);

								if (!tExeSQL.execUpdateSQL(sqlbvb)) {
									this.mErrors.copyAllErrors(tExeSQL.mErrors);
									CError tError = new CError();
									tError.moduleName = "TbWorkFlowBLS";
									tError.functionName = "saveLJAGetserials";
									tError.errorMessage = "执行DML语句失败!";
									this.mErrors.addOneError(tError);
									conn.rollback();
									conn.close();
									return false;
								}
								continue;
							}
						} else if (className.endsWith("Schema")) {
							className = "com.sinosoft.lis.db."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
						} else if (className.endsWith("Set")) {
							className = "com.sinosoft.lis.vdb."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DBSet";
						}
						Class DBClass = Class.forName(className);

						// 选择构造函数，构造相同事务的DB或DBSet对象
						parameterC[0] = Connection.class;
						constructor = DBClass.getConstructor(parameterC);
						parameterO[0] = conn;
						DBObject = constructor.newInstance(parameterO);

						// 给DB对象付值，将传入的Schema或Set对象的内容复制到DB中
						parameterC[0] = o.getClass();
						if (o.getClass().getName().endsWith("Schema")) {
							m = DBObject.getClass().getMethod("setSchema",
									parameterC);
						} else if (o.getClass().getName().endsWith("Set")) {
							m = DBObject.getClass()
									.getMethod("set", parameterC);
						}
						parameterO[0] = o;
						m.invoke(DBObject, parameterO);

						// 进行数据库操作
						if (action.equals("INSERT")) {
							m = DBObject.getClass().getMethod("insert", null);
							Boolean b = (Boolean) m.invoke(DBObject, null);

							if (!b.booleanValue()) {
								try {
									conn.rollback();
								} catch (Exception e) {
								}
								conn.close();
								logger.debug(DBObject.getClass()
										.getName()
										+ " " + action + " Failed");
								return false;
							}
						} else if (action.equals("UPDATE")) {
							m = DBObject.getClass().getMethod("update", null);
							Boolean b = (Boolean) m.invoke(DBObject, null);

							if (!b.booleanValue()) {
								try {
									conn.rollback();
								} catch (Exception e) {
								}
								conn.close();
								logger.debug(DBObject.getClass()
										.getName()
										+ " " + action + " Failed");
								return false;
							}
						} else if (action.equals("DELETE")) {
							m = DBObject.getClass().getMethod("delete", null);
							Boolean b = (Boolean) m.invoke(DBObject, null);

							if (!b.booleanValue()) {
								try {
									conn.rollback();
								} catch (Exception e) {
								}
								conn.close();
								logger.debug(DBObject.getClass()
										.getName()
										+ " " + action + " Failed");
								return false;
							}
						} else if (action.equals("DELETE&INSERT")) {
							// DELETE
							m = DBObject.getClass().getMethod("delete", null);
							Boolean b = (Boolean) m.invoke(DBObject, null);

							if (!b.booleanValue()) {
								try {
									conn.rollback();
								} catch (Exception e) {
								}
								conn.close();
								logger.debug(DBObject.getClass()
										.getName()
										+ " " + action + " Failed");
								return false;
							}

							// INSERT
							m = DBObject.getClass().getMethod("insert", null);
							b = (Boolean) m.invoke(DBObject, null);

							if (!b.booleanValue()) {
								try {
									conn.rollback();
								} catch (Exception e) {
								}
								conn.close();
								logger.debug(DBObject.getClass()
										.getName()
										+ " " + action + " Failed");
								return false;
					} 
						}else if (action.equals("BLOBINSERT")) { // add by Alex
																// at 2005.1.12
						// first,insert a record with a empty_blob , at the same
						// time prepare the param for the second step
						String tSQL = "";
						// 用于第二步update时的参数
						String pWhereSQL = " and ";
						String pTabName = "";
						String pUpdateField = "";

						String[] parmStrArr = getBlobInsertStr(o, tSQL,
								pWhereSQL, pTabName, pUpdateField);

						String aClassName = o.getClass().getName();
						Schema s = (Schema) o;

						m = o.getClass().getMethod("get" + parmStrArr[2], null);
						InputStream ins = (InputStream) m.invoke(o, null);
						if (!CBlob.BlobInsert(ins, parmStrArr, conn)) {
							CError.buildErr(this, "执行更新blob语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							ins.close();
							logger.debug("tCBlob.BlobInsert" + " "
									+ action + " Failed");
							return false;
						}
						ins.close();
					} else if (action.equals("BLOBUPDATE")) { // add by Alex
																// at 2005.1.12
						// first,prepare the param for the UpdateBlob
						String tSQL = "";
						// 用于第二步update时的参数
						String pWhereSQL = " and ";
						String pTabName = "";
						String pUpdateField = "";

						String[] parmStrArr = getBlobInsertStr(o, tSQL,
								pWhereSQL, pTabName, pUpdateField);

						String aClassName = o.getClass().getName();
						Schema s = (Schema) o;

						m = o.getClass().getMethod("get" + parmStrArr[2], null);
						InputStream ins = (InputStream) m.invoke(o, null);

						if (!CBlob.BlobUpdate(ins, parmStrArr, conn)) {
							CError.buildErr(this, "执行更新blob语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							ins.close();
							logger.debug("tCBlob.BlobUpdate" + " "
									+ action + " Failed");
							return false;
						}
						ins.close();
					} else if (action.equals("BLOBDELETE")) { // add by Alex
																// at 2005.1.12
						m = DBObject.getClass().getMethod("delete", null);
						Boolean b = (Boolean) m.invoke(DBObject, null);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行删除语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}

					}
			}
				}
			}
			// 数据提交:为保正事务一致性所有数据准备完毕后一次性提交.
			if (commitFlag) {
				conn.commit();
				conn.close();
				logger.debug("---End Committed---");
			} else {
				logger.debug("---End Datebase Operation, but not Commit in AutoBLS---");
			}
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "TbWorkFlowBLS";
			tError.functionName = "savaData";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
			} catch (Exception ex) {
			}
			return false;
		}
		return true;
	}
}
