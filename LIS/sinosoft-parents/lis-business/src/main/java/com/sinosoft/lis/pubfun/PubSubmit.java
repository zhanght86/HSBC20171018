/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.SQLwithBindVariables;;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class PubSubmit {
private static Logger logger = Logger.getLogger(PubSubmit.class);
	// 传输数据类
	private VData mInputData;
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 数据库连接 * */
	private Connection conn = null;
	/** 立即提交标志 * */
	private final boolean commitFlag = true;
	private OptimisticLockDataCheck mOptimisticLockDataCheck = new OptimisticLockDataCheck();

	/**
	 * mflag = true: 传入Connection mflag = false: 不传入Connection
	 */
	private boolean mflag = false;
	
	public PubSubmit() {
	}
	
	public PubSubmit(Connection tConnection) {
		conn = tConnection;
		mflag = true;
	}
	


	

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
			//if (conn == null || conn.isClosed())
			if (!mflag) {
				conn = DBConnPool.getConnection();
			}
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
			//如果是从外部传入的连接,由外部自行管理事务 
			if (!mflag) {
				conn.setAutoCommit(false);
			}
			String action = ""; // 操作方式，INSERT\UPDATE\DELETE
			String className = ""; // 类名
			String tableName = "";
			Object o = null; // Schema或Set对象
			Object DBObject = null; // DB或DBSet对象
			Method m = null; // 方法
			Constructor constructor = null; // 构造函数
			Class[] parameterC = new Class[1]; // 调用方法的参数数组
			Object[] parameterO = new Object[1]; // 调用方法的对象数组
			// logger.debug("mInputData.size():" + mInputData.size());
			// logger.debug("mInputData :" + mInputData);
			// 通过MMap来传递每个Schema或Set的数据库操作方式，约定使用
			MMap map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
			if (map != null && map.keySet().size() != 0) {
				//增加数据校验
				mOptimisticLockDataCheck.setmOptimisticLockData(map.getmOptimisticLockData());
				mOptimisticLockDataCheck.setConn(conn);
			
				if(!mOptimisticLockDataCheck.checkLock()){
					try {
						if (!mflag) {
							conn.rollback();
						}
					} catch (Exception e) {
					}
					if (!mflag) {
						conn.close();
					}
					logger.info("数据校验出错，不允许提交");
					return false;
				}
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
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.toUpperCase().equals("SELECT_NOT_EXIST")) {
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
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
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
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tSQL)) {
								CError.buildErr(this, "执行更新语句失败");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("DELETE")) {
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tSQL)) {
								CError.buildErr(this, "执行删除语句失败");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("INSERT")
								||action.toUpperCase().equals("DROP")
								||action.toUpperCase().equals("TRUNCATE")
								||action.toUpperCase().equals("CREATE")		
						) {
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tSQL)) {
								CError
										.buildErr(
												this,
												"执行插入语句失败原因："
														+ tExeSQL.mErrors
																.getError(0).errorMessage);
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("SELECT")) // 加入select方法，如果查出则表示有重复操作并报错退出
						{
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tSQL);

							if (tSSRS.getMaxRow() > 0) {
								CError.buildErr(this, "该操作已经执行，请检查数据！");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("FORUPDATE")) 
						{
							String tSQL = (String) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tSQL);

							if (tSSRS.getMaxRow() <= 0) {
								CError.buildErr(this, "查询无数据，请检查数据！");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
					} 
					//增加绑定变量方式的执行
					else if (className.endsWith("VData")) {
						if (action.equals("UPDATE")) {
							VData tBVVdata = (VData) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tBVVdata)) {
								CError.buildErr(this, "执行更新语句失败");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("DELETE")) {
							VData tBVVdata = (VData) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tBVVdata)) {
								CError.buildErr(this, "执行删除语句失败");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("INSERT")
								||action.toUpperCase().equals("DROP")
								||action.toUpperCase().equals("TRUNCATE")
								||action.toUpperCase().equals("CREATE")		
						) {
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
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("SELECT")) // 加入select方法，如果查出则表示有重复操作并报错退出
						{
							VData tBVVdata = (VData) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							//logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tBVVdata);

							if (tSSRS.getMaxRow() > 0) {
								CError.buildErr(this, "该操作已经执行，请检查数据！");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("FORUPDATE")) 
						{
							VData tBVVdata = (VData) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							//logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tBVVdata);

							if (tSSRS.getMaxRow() <= 0) {
								CError.buildErr(this, "查询无数据，请检查数据！");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
					}
					
					else if (className.endsWith("SQLwithBindVariables")) {
						if (action.equals("UPDATE")) {
							SQLwithBindVariables tBVVdata = (SQLwithBindVariables) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tBVVdata)) {
								CError.buildErr(this, "执行更新语句失败");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("DELETE")) {
							SQLwithBindVariables tBVVdata = (SQLwithBindVariables) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tBVVdata)) {
								CError.buildErr(this, "执行删除语句失败");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("INSERT")
								||action.toUpperCase().equals("DROP")
								||action.toUpperCase().equals("TRUNCATE")
								||action.toUpperCase().equals("CREATE")		
						) {
							SQLwithBindVariables tBVVdata = (SQLwithBindVariables) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							// logger.debug("执行SQL语句:" + tSQL);
							if (!tExeSQL.execUpdateSQL(tBVVdata)) {
								CError
										.buildErr(
												this,
												"执行插入语句失败原因："
														+ tExeSQL.mErrors
																.getError(0).errorMessage);
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("SELECT")) // 加入select方法，如果查出则表示有重复操作并报错退出
						{
							SQLwithBindVariables tBVVdata = (SQLwithBindVariables) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							//logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tBVVdata);

							if (tSSRS.getMaxRow() > 0) {
								CError.buildErr(this, "该操作已经执行，请检查数据！");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
						if (action.equals("FORUPDATE")) 
						{
							SQLwithBindVariables tBVVdata = (SQLwithBindVariables) o;
							ExeSQL tExeSQL = new ExeSQL(conn);
							SSRS tSSRS = new SSRS();
							//logger.debug("执行SQL语句:" + tSQL);
							tSSRS = tExeSQL.execSQL(tBVVdata);

							if (tSSRS.getMaxRow() <= 0) {
								CError.buildErr(this, "查询无数据，请检查数据！");
								if (!mflag) {
									conn.rollback();
									conn.close();
								}
								return false;
							}
							continue;
						}
					}
					
					else if (className.endsWith("Schema")) {
						tableName = className.substring(className
								.lastIndexOf(".") + 1, className
								.lastIndexOf("S"));
						className = "com.sinosoft.lis.db."
								+ tableName + "DB";
					} else if (className.endsWith("BLSet")) {
						tableName = className.substring(className
								.lastIndexOf(".") + 1, className
								.lastIndexOf("B"));
						className = "com.sinosoft.lis.vdb."
								+ tableName + "DBSet";
					} else if (className.endsWith("DBSet")) {
						throw new IllegalArgumentException("Not Support DBSet as pojo.");
					} else if (className.endsWith("Set")) {
						tableName = className.substring(className
								.lastIndexOf(".") + 1, className
								.lastIndexOf("S"));
						className = "com.sinosoft.lis.vdb."
								+ tableName + "DBSet";
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
					} else if (o.getClass().getName().endsWith("DB")) {
						throw new IllegalArgumentException("Not Support DB as pojo.");
					}
					parameterO[0] = o;
					m.invoke(DBObject, parameterO);

					// 进行数据库操作
					if (action.equals("INSERT")) {
						m = DBObject.getClass().getMethod("insert", new Class[0]);
						Boolean b = (Boolean) m.invoke(DBObject, new Object[0]);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行" + tableName + "插入语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}
					} else if (action.equals("UPDATE")) {
						m = DBObject.getClass().getMethod("update", new Class[0]);
						Boolean b = (Boolean) m.invoke(DBObject, new Object[0]);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行" + tableName + "更新语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}
					} else if (action.equals("DELETE")) {
						m = DBObject.getClass().getMethod("delete", new Class[0]);
						Boolean b = (Boolean) m.invoke(DBObject, new Object[0]);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行" + tableName + "删除语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}
					} else if (action.equals("DELETE&INSERT")) {
						// DELETE
						m = DBObject.getClass().getMethod("delete", new Class[0]);
						Boolean b = (Boolean) m.invoke(DBObject, new Object[0]);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行" + tableName + "删除，插入语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}

						// INSERT
						m = DBObject.getClass().getMethod("insert", new Class[0]);
						b = (Boolean) m.invoke(DBObject, new Object[0]);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行插入语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
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

						// second,using UpdateBlob method to update the
						// empty_blob with a InputStream object from the schema

						m = o.getClass().getMethod("get" + parmStrArr[2], new Class[0]);
						InputStream ins = (InputStream) m.invoke(o, new Object[0]);
						if (!CBlob.BlobInsert(ins, parmStrArr, conn)) {
							CError.buildErr(this, "执行更新blob语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
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

						m = o.getClass().getMethod("get" + parmStrArr[2], new Class[0]);
						InputStream ins = (InputStream) m.invoke(o, new Object[0]);
						if (!CBlob.BlobUpdate(ins, parmStrArr, conn)) {
							CError.buildErr(this, "执行更新blob语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
							ins.close();
							logger.debug("tCBlob.BlobUpdate" + " "
									+ action + " Failed");
							return false;
						}
						ins.close();
					} else if (action.equals("BLOBDELETE")) { // add by Alex
																// at 2005.1.12
					// logger.debug(SysConst.DBTYPE);
						m = DBObject.getClass().getMethod("delete", new Class[0]);
						Boolean b = (Boolean) m.invoke(DBObject, new Object[0]);

						if (!b.booleanValue()) {
							CError.buildErr(this, "执行删除语句失败");
							try {
								if (!mflag) {
									conn.rollback();
								}
							} catch (Exception e) {
							}
							if (!mflag) {
								conn.close();
							}
							logger.debug(DBObject.getClass().getName()
									+ " " + action + " Failed");
							return false;
						}

					}

				} // end of while
			}

			// 数据提交:为保正事务一致性所有数据准备完毕后一次性提交.
			if (commitFlag) {
				if (!mflag) {
					conn.commit();
					conn.close();
					conn = null;
				}
				// logger.debug("---End Committed---");
			} else {
				logger.debug("---End Datebase Operation, but not Commit in AutoBLS---");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString());
			try {
				if (!mflag) {
					conn.rollback();
				}
			} catch (Exception ex) {
			}

			try {
				if (!mflag) {
					conn.close();
				}
			} catch (Exception ex) {
			}
			if (!mflag) {
				conn = null;
			}
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

		//	for (int i = 0; i < nFieldCount - 1; i++) { // 只循环（nFieldCount-1）次，使blob字段不加入循环
			for (int i = 0; i < nFieldCount ; i++) {
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
					else
					{
						strFieldValue = mark + "" + mark;
						bFlag = true;
					}
					break;
				case Schema.TYPE_DOUBLE:
				case Schema.TYPE_FLOAT:
				case Schema.TYPE_INT:
					bFlag = true;
					break;
				case Schema.TYPE_BLOB:
					pUpdateField = s.getFieldName(i);
					bFlag = true;
					break;
				default:
					bFlag = false;
					break;
				}

				if (bFlag) {
//					jj++;
//					if (jj != 1) {
//						// ColPart += ",";
//						ValPart += ",";
//					}
//					// ColPart += strFieldName;
//					ValPart += strFieldValue;
					jj++;
					if (jj != 1) {
						// ColPart += ",";
						ValPart += ",";
					}
					// ColPart += strFieldName;
					if(nFieldType==Schema.TYPE_BLOB)
					{
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							ValPart += "null";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							ValPart += "empty_blob()"; // 添加empty_blob
						}
					}
					else
					{
						ValPart += strFieldValue;
					}
				}
			} // end of for
			// ColPart += " )";
//			ValPart += ",";
//			ValPart += "empty_blob()"; // 添加empty_blob
			ValPart += ")";
			tSQL = "insert into " + pTabName + " " + ValPart;
			if (jj == 0) {
				tSQL = "";
			}
		//	pUpdateField = s.getFieldName(nFieldCount - 1);
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

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	//	PubSubmit pubSubmit1 = new PubSubmit();
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
//		VData mResult = new VData();// BLOBUPDATE
//		// MMap map = new MMap();//BLOBINSERT
//		// map.put(tLPEdorPrintSchemaMain, "BLOBDELETE");
//		// mResult.addElement(map);
//		// pubSubmit1.submitData(mResult, "");
//
//        MMap map = new MMap();//BLOBINSERT
//        String sql_lock = " select * from LCAirPol  for update";
//   	    String sql_insert = " insert into LCAirPolForCal select * from LCAirPol where State = 'B' and Password = '001'";
//   	    String sql_delete = " delete from LCAirPol where State = 'B' and Password ='001' ";
//   	    map.put(sql_lock, "SELECT");
//   	    map.put(sql_insert, "INSERT");
//        map.put(sql_delete, "DELETE");       ;
//        mResult.addElement(map);
//        pubSubmit1.submitData(mResult);
		try {
			//DBConn conn = DBConnPool.getConnection("Local");
			//if(conn!=null)
			{
				//conn.setAutoCommit(false);
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo("110110000000134");
				if(!tLCPolDB.getInfo())
				{
					
				}
				tLCPolDB.setOperator("111");
				VData mResult = new VData();
				 MMap map = new MMap();
				 map.put(tLCPolDB.getSchema(), "UPDATE");
				 Map checkMap = new HashMap();
				 
				 checkMap.put("operator", "001");
				 checkMap.put("polno", "110110000000134");
				 map.setDataCheck("lcpol", checkMap);
				 mResult.add(map);
				 PubSubmit pubSubmit1 = new PubSubmit();
				 if(!pubSubmit1.submitData(mResult))
				 {
					logger.debug("false");
				 }else{
					 logger.debug("true");
				 }
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
}
