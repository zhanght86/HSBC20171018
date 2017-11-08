package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Set;


import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.VData;

public class ExtPubSubmit {
private static Logger logger = Logger.getLogger(ExtPubSubmit.class);

	public ExtPubSubmit() {
		// TODO Auto-generated constructor stub
	}

	// 传输数据类
	private VData mInputData;
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 数据库连接 * */
	private Connection conn = null;
	/** 立即提交标志 * */
	private final boolean commitFlag = true;

	/**
	 * 传输数据的公共方法 传入数据
	 * 
	 * @param cInputData
	 *            VData 数据操作标示
	 *            为了解决同时提交时的数据重复插入的问题，传入一个(select)sql如果查出数据则表示已经有数据，则报错退出
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		if (!this.saveData()) {
			return false;
		}
		mInputData = null;
		return true;
	}

	public boolean submitData(VData cInputData) {
		return submitData(cInputData, null);
	}

	/**
	 * 数据库操作
	 * 
	 * @return: boolean
	 */
	private boolean saveData() {
		// logger.debug("---Start Save---");

		// 建立数据库连接
		try {
			if (conn == null || conn.isClosed())
				conn = DBConnPool.getConnection();

			if (conn == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败");
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "数据库连接失败");
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
			// 通过MMap来传递每个Schema或Set的数据库操作方式，约定使用
			MMap map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
			if (map != null && map.keySet().size() != 0) {
				Set set = map.keySet();
				// Iterator iterator = map.keySet().iterator();
				// while (iterator.hasNext()) {

				/**
				 * 添加支持在BLS中校验的公共提交程序
				 * 先对加锁或者需要在BLS中校验的条件运行,因为如果放在INSERT,UPDATE或者DELETE后面运行的话,
				 * 虽然还没有commit,前边的其他操作如UPDATE等的修改对select是有效的.
				 * 当action.equals("SELECT_EXIST"),则当查询返回null时需要退出,如加锁失败等情况
				 * select .....for update 当action.equals("SELECT_NOT_EXIST")
				 * 则当查询返回not null时需要退出
				 */

				for (int i = 0; i < set.size(); i++) {
					// 获取操作对象Schema或Set或SQL
					o = map.getOrder().get(String.valueOf(i + 1));
					// 获取操作方式
					action = (String) map.get(o);
					if (action == null)
						continue;
					logger.debug("\n" + o.getClass().getName()
							+ " Operate DB: " + action);

					// 构造相应的DB类名
					className = o.getClass().getName();
					logger.debug("className :" + className);
					logger.debug("action :" + action);

					if (className.endsWith("String")) {

					
						if (action.toUpperCase().equals("SELECT_EXIST")) {
							className = "com.sinosoft.lis.db."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							logger.debug("执行SQL语句:" + tSQL);
							SSRS tSSRS = null;
							tSSRS = tExeSQL.execSQL(tSQL);
							if (tSSRS == null || tSSRS.getMaxRow() < 1) {
								CError.buildErr(this,
										"SELECT_EXIST表加锁失败或者违反该操作的相关限制! SQL: "
												+ tSQL);
								logger.debug("SELECT_EXIST表加锁失败或者违反该操作的相关限制! \n*******SQL: "
												+ tSQL);
								conn.rollback();
								conn.close();
								return false;
							}
							continue;
						}
						if (action.toUpperCase().equals("SELECT_NOT_EXIST")) {
							className = "com.sinosoft.lis.db."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							logger.debug("执行SQL语句:" + tSQL);
							SSRS tSSRS = null;
							tSSRS = tExeSQL.execSQL(tSQL);
							if (tSSRS != null && tSSRS.getMaxRow() > 0) {
								CError.buildErr(this,
										"SELECT_NOT_EXIST表加锁失败或者违反该操作的相关限制! SQL: "
												+ tSQL);
								logger.debug("SELECT_NOT_EXIST表加锁失败或者违反该操作的相关限制! \n*******SQL: "
												+ tSQL);
								conn.rollback();
								conn.close();
								return false;
							}
							continue;
						}
					}
				}

				for (int j = 0; j < set.size(); j++) {
					// 获取操作对象Schema或Set或SQL
					// o = iterator.next();
					o = map.getOrder().get(String.valueOf(j + 1));
					// 获取操作方式
					action = (String) map.get(o);
					if (action == null) {
						continue;
					}
					// logger.debug("\n" + o.getClass().getName() +
					// " Operate DB: " + action);

					// 构造相应的DB类名
					className = o.getClass().getName();
					// logger.debug("className :" + className);
					// logger.debug("action :" + action);

					if (className.endsWith("String")) {
					
						if (action.equals("UPDATE")) {
							className = "com.sinosoft.lis.db."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tSQL)) {
								CError.buildErr(this, "执行更新语句失败");
								conn.rollback();
								conn.close();
								return false;
							}
							continue;
						}
						if (action.equals("DELETE")) {
							className = "com.sinosoft.lis.db."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tSQL)) {
								CError.buildErr(this, "执行删除语句失败");
								conn.rollback();
								conn.close();
								return false;
							}
							continue;
						}
						if (action.toUpperCase().equals("DROP")
								|| action.toUpperCase().equals("TRUNCATE")
								|| action.toUpperCase().equals("INSERT")||action.toUpperCase().equals("CREATE")) {
						
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							 logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tSQL)) {
								CError
										.buildErr(
												this,
												"执行插入语句失败原因："
														+ tExeSQL.mErrors
																.getError(0).errorMessage);
								conn.rollback();
								conn.close();
								return false;
							}
							continue;
						}
						if (action.equals("SELECT")) // 加入select方法，
						// 如果查出则表示有重复操作并报错退出
						{
							className = "com.sinosoft.lis.db."
									+ className.substring(className
											.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tSQL);

							if (tSSRS.getMaxRow() > 0) {
								CError.buildErr(this, "该操作已经执行，请检查数据！");
								conn.rollback();
								conn.close();
								return false;
							}
							continue;
						}
					} 
					else if (className.endsWith("VData")) {
							
							if (action.equals("UPDATE")) {
//								className = "com.sinosoft.lis.db."
//										+ className.substring(className
//												.lastIndexOf(".") + 1, className
//												.lastIndexOf("S")) + "DB";
								VData tBVVdata = (VData) o;
								ExeSQL tExeSQL = new ExeSQL(conn);
								// logger.debug("执行SQL语句:" + tSQL);
								if (!tExeSQL.execUpdateSQL(tBVVdata)) {
									CError.buildErr(this, "执行更新语句失败");
									conn.rollback();
									conn.close();
									return false;
								}
								continue;
							}
							if (action.equals("DELETE")) {
//								className = "com.sinosoft.lis.db."
//										+ className.substring(className
//												.lastIndexOf(".") + 1, className
//												.lastIndexOf("S")) + "DB";
								VData tBVVdata = (VData) o;
								ExeSQL tExeSQL = new ExeSQL(conn);
								// logger.debug("执行SQL语句:" + tSQL);
								if (!tExeSQL.execUpdateSQL(tBVVdata)) {
									CError.buildErr(this, "执行删除语句失败");
									conn.rollback();
									conn.close();
									return false;
								}
								continue;
							}
							if (action.toUpperCase().equals("DROP")
									|| action.toUpperCase().equals("TRUNCATE")
									|| action.toUpperCase().equals("INSERT")||action.toUpperCase().equals("CREATE")) {
							
								VData tBVVdata = (VData) o;
								ExeSQL tExeSQL = new ExeSQL(conn);
								// logger.debug("执行SQL语句:" + tSQL);
								if (!tExeSQL.execUpdateSQL(tBVVdata)) {
									CError
											.buildErr(
													this,
													"执行插入语句失败原因："
															+ tExeSQL.mErrors
																	.getError(0).errorMessage);
									conn.rollback();
									conn.close();
									return false;
								}
								continue;
							}
							if (action.equals("SELECT")) // 加入select方法，
							// 如果查出则表示有重复操作并报错退出
							{
//								className = "com.sinosoft.lis.db."
//										+ className.substring(className
//												.lastIndexOf(".") + 1, className
//												.lastIndexOf("S")) + "DB";
								VData tBVVdata = (VData) o;
								ExeSQL tExeSQL = new ExeSQL(conn);
								SSRS tSSRS = new SSRS();
								//logger.debug("执行SQL语句:" + tSQL);
								tSSRS = tExeSQL.execSQL(tBVVdata);

								if (tSSRS.getMaxRow() > 0) {
									CError.buildErr(this, "该操作已经执行，请检查数据！");
									conn.rollback();
									conn.close();
									return false;
								}
								continue;
							}
						}
					else if (className.endsWith("Schema")) {
						className = "com.sinosoft.lis.db."
								+ className.substring(className
										.lastIndexOf(".") + 1, className
										.lastIndexOf("S")) + "DB";
					} else if (className.endsWith("BLSet")) {
						className = "com.sinosoft.lis.vdb."
								+ className.substring(className
										.lastIndexOf(".") + 1, className
										.lastIndexOf("B")) + "DBSet";
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
						m = DBObject.getClass().getMethod("set", parameterC);
					}
					parameterO[0] = o;
					m.invoke(DBObject, parameterO);

					// 进行数据库操作
					if (action.equals("INSERT")) {
						m = DBObject.getClass().getMethod("insert", null);
						Boolean b = (Boolean) m.invoke(DBObject, null);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行插入语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}
					} else if (action.equals("UPDATE")) {
						m = DBObject.getClass().getMethod("update", null);
						Boolean b = (Boolean) m.invoke(DBObject, null);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行更新语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}
					} else if (action.equals("DELETE")) {
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
					} else if (action.equals("DELETE&INSERT")) {
						// DELETE
						m = DBObject.getClass().getMethod("delete", null);
						Boolean b = (Boolean) m.invoke(DBObject, null);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行删除，插入语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}

						// INSERT
						m = DBObject.getClass().getMethod("insert", null);
						b = (Boolean) m.invoke(DBObject, null);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行插入语句失败");
							try {
								conn.rollback();
							} catch (Exception e) {
							}
							conn.close();
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}
					} else if (action.equals("BLOBINSERT")) { // add by Alex
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

						// COracleBlob tCOracleBlob = new COracleBlob();
						// if
						// (!tCOracleBlob.InsertBlankBlobRecord(parmStrArr[0],
						// conn)) {
						// CError.buildErr(this, "执行插入blob语句失败");
						// try {
						// conn.rollback();
						// } catch (Exception e) {}
						// conn.close();
						// logger.debug(
						// "COracleBlob.InsertBlankBlobRecord" +
						// " " + action + " Failed");
						// return false;
						// }

						// second,using UpdateBlob method to update the
						// empty_blob with a InputStream object from the schema
						String aClassName = o.getClass().getName();
						Schema s = (Schema) o;

						m = o.getClass().getMethod("get" + parmStrArr[2], null);
						InputStream ins = (InputStream) m.invoke(o, null);
						// if (!tCOracleBlob.UpdateBlob(ins, parmStrArr[1],
						// parmStrArr[2] , parmStrArr[3], conn)) {
						// CError.buildErr(this, "执行更新blob语句失败");
						// try {
						// conn.rollback();
						// } catch (Exception e) {}
						// conn.close();
						// ins.close();
						// logger.debug("COracleBlob.UpdateBlob" +
						// " " + action + " Failed");
						// return false;
						// }
						// CBlob tCBlob = new CBlob();
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

						// second,using UpdateBlob method to update the
						// blobfield with a InputStream object from the schema
						// COracleBlob tCOracleBlob = new COracleBlob();
						String aClassName = o.getClass().getName();
						Schema s = (Schema) o;

						m = o.getClass().getMethod("get" + parmStrArr[2], null);
						InputStream ins = (InputStream) m.invoke(o, null);
						// if (!tCOracleBlob.UpdateBlob(ins, parmStrArr[1],
						// parmStrArr[2], parmStrArr[3], conn))
						// {
						// CError.buildErr(this, "执行更新blob语句失败");
						// try
						// {
						// conn.rollback();
						// }
						// catch (Exception e)
						// {}
						// conn.close();
						// ins.close();
						// logger.debug("COracleBlob.UpdateBlob" +
						// " " + action + " Failed");
						// return false;
						// }
						// CBlob tCBlob = new CBlob();
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
						// logger.debug(SysConst.DBTYPE);
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

				} // end of while
			}

			// 数据提交:为保正事务一致性所有数据准备完毕后一次性提交.
			if (commitFlag) {
				conn.commit();
				conn.close();
				conn = null;
				// logger.debug("---End Committed---");
			} else {
				logger.debug("---End Datebase Operation, but not Commit in AutoBLS---");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString());
			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			try {
				conn.close();
			} catch (Exception ex) {
			}

			conn = null;
			return false;
		}
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

			for (int i = 0; i < nFieldCount - 1; i++) { // 只循环（nFieldCount-1）次，
				// 使blob字段不加入循环
				String strFieldName = s.getFieldName(i);
				String strFieldValue = s.getV(i);
				for (int ii = 0; ii < pk.length; ii++) {
					if (strFieldName.equals(pk[ii])) {
						pWhereSQL += strFieldName + " = '" + strFieldValue
								+ "' and ";
					}
				}
				int nFieldType = s.getFieldType(i);
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
					ValPart += strFieldValue;
				}
			} // end of for
			// ColPart += " )";
			ValPart += ",";
			ValPart += "empty_blob()"; // 添加empty_blob
			ValPart += ")";
			tSQL = "insert into " + pTabName + " " + ValPart;

			if (jj == 0) {
				tSQL = "";
			}
			pUpdateField = s.getFieldName(nFieldCount - 1);
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

	public static void main(String[] args) {
		PubSubmit pubSubmit1 = new PubSubmit();
		// LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
		// tLPEdorPrintSchemaMain.setEdorNo("410110001000065");
		// tLPEdorPrintSchemaMain.setManageCom("86");
		// tLPEdorPrintSchemaMain.setPrtFlag("N");
		// tLPEdorPrintSchemaMain.setPrtTimes(0);
		// tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
		// tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
		// tLPEdorPrintSchemaMain.setOperator("001");
		// tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
		// tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
		// com.sinosoft.utility.XmlExport xmlexport = new com.sinosoft.utility.
		// XmlExport();
		// xmlexport.createDocument("", "");
		// InputStream ins = xmlexport.getInputStream();
		// tLPEdorPrintSchemaMain.setEdorInfo(ins);
		VData mResult = new VData();// BLOBUPDATE
		// MMap map = new MMap();//BLOBINSERT
		// map.put(tLPEdorPrintSchemaMain, "BLOBDELETE");
		// mResult.addElement(map);
		// pubSubmit1.submitData(mResult, "");

		MMap map = new MMap();// BLOBINSERT
		map.put("select 1 from dual WHERE 1=1", "SELECT");
		mResult.addElement(map);
		pubSubmit1.submitData(mResult, "");

	}
}
