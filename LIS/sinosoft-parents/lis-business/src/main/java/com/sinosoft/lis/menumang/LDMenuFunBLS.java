/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 * 修改人：周磊
 * 修改日期：2005-5-4
 * 修改内容1：增加根据菜单标志（NodeSign）进行相应菜单添加删除的逻辑判断
 */
package com.sinosoft.lis.menumang;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;
import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Fanym
 * @version 1.0
 */

public class LDMenuFunBLS {
private static Logger logger = Logger.getLogger(LDMenuFunBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate = null;
	private VData mInputData = new VData();

	public LDMenuFunBLS() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();
		// logger.debug("Start LDMenu BLS Submit...");
		// 菜单插入
		if (this.mOperate.equals("insert")) {
			tReturn = insertCode(this.mInputData);
		}

		// 菜单删除
		if (this.mOperate.equals("delete")) {
			tReturn = deleteCode(this.mInputData);
		}

		// logger.debug("End LDMenu BLS Submit...");

		return tReturn;
	}

	// 删除操作
	private boolean deleteCode(VData mInputData) {
		boolean tReturn = true;
		// logger.debug("start delete...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			LDMenuSchema tLDMenuSchema = (LDMenuSchema) mInputData
					.getObjectByObjectName("LDMenuSchema", 0);
			String ChildFlag = tLDMenuSchema.getChildFlag();
			String NodeCode = tLDMenuSchema.getNodeCode();
			String ParentNodeCode = tLDMenuSchema.getParentNodeCode();
			String NodeSign = tLDMenuSchema.getNodeSign();
			if (!ChildFlag.equals("0")) {
				CError tError = new CError();
				tError.moduleName = "LDMenuFunBLS";
				tError.functionName = "deleteCode";
				tError.errorMessage = "有子菜单的菜单不能删除!";
				this.mErrors.addOneError(tError);
				conn.close();
				return false;
			} else {
				// logger.debug("Start 删除菜单...");
				// 处理父菜单
				// logger.debug("BLS insert inputdata...childflag:" +
				// ChildFlag);
				// logger.debug("BLS insert inputdata...NodeCode" +
				// NodeCode);
				// logger.debug("BLS insert inputdata...ParentNodeCode" +
				// ParentNodeCode);
				LDMenuDB tLDMenuDB = new LDMenuDB(conn);
				tLDMenuDB.setSchema(tLDMenuSchema);
				if (!tLDMenuDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDMenuFunBLS";
					tError.functionName = "deleteCode";
					tError.errorMessage = "菜单删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				if (ParentNodeCode.equals("0") || NodeSign.equals("2")) {
				} else {
					// logger.debug("Start 处理父菜单...");
					ExeSQL tExeSQL = new ExeSQL(conn);
					String sql = "update LDMenu set ChildFlag = ChildFlag - 1 "
							+ "Where NodeCode = '?ParentNodeCode?'";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(sql);
					sqlbv.put("ParentNodeCode", ParentNodeCode);
					if (!tExeSQL.execUpdateSQL(sqlbv)) {
						CError tError = new CError();
						tError.moduleName = "LDMenuFunBLS";
						tError.functionName = "deleteCode";
						tError.errorMessage = "父菜单更新失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
				// 处理菜单分组和关联表
				logger.debug("Start 处理菜单分组和菜单关联表...");
				ExeSQL tExeSQL = new ExeSQL(conn);
				String sql = "delete from LDMenuGrpToMenu where NodeCode = '?NodeCode?'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("NodeCode", NodeCode);
				// 删除末级菜单的页面权限菜单
				String sql2 = "delete from LDMenu where NodeCode = '?NodeCode?'" + " or (ParentNodeCode = '?NodeCode?' and NodeSign = 2)";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(sql2);
				sqlbv2.put("NodeCode", NodeCode);
				if (!tExeSQL.execUpdateSQL(sqlbv1) || !tExeSQL.execUpdateSQL(sqlbv2)) {
					CError tError = new CError();
					tError.moduleName = "LDMenuFunBLS";
					tError.functionName = "deleteCode";
					tError.errorMessage = "菜单分组和关联表删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
			// logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			tReturn = false;
		}
		return tReturn;
	}

	private boolean insertCode(VData mInputData) {
		boolean tReturn = true;
		// logger.debug("start insert...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			// logger.debug("#####Conn error");
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			LDMenuSchema tLDMenuSchema = (LDMenuSchema) mInputData
					.getObjectByObjectName("LDMenuSchema", 0);
			TransferData t = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			String ischild = (String) t.getValueByName("ischild");
			String ischild2 = (String) t.getValueByName("ischild2");
			String Node = (String) t.getValueByName("NodeCode");
			String RunScript = (String) t.getValueByName("RunScript");

			// 作为页面权限菜单插入时,RunScript必须唯一
			if (ischild2.equalsIgnoreCase("true")) {
				ExeSQL tExeSQL = new ExeSQL(conn);
				String sql = "select RunScript from LDMenu ";
				sql = sql + "where NodeSign = 2 and RunScript='?RunScript?'";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(sql);
				sqlbv3.put("RunScript", RunScript);
				String isgetnull = tExeSQL.getOneValue(sqlbv3);
				if (isgetnull != null && !"".equals(isgetnull)) {
					logger.debug("作为页面权限菜单插入时,RunScript(映射文件)必须唯一!");
					return false;
				}
			}

			// 取NodeCode，NodeOrder
			ExeSQL tExeSQL = new ExeSQL(conn);
			String sql = "select max(to_number(NodeCode))+1 from LDMenu";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(sql);
			String MaxCode = tExeSQL.getOneValue(sqlbv4);
			// logger.debug("Maxcode::" + MaxCode);
			sql = "select NodeOrder from LDMenu where NodeCode = '?Node?'";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sql);
			sqlbv5.put("Node", Node);
			int NodeOrder = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
			// logger.debug("NodeOrder::" + NodeOrder);
			// tLDMenuSchema.setChildFlag("0");
			tLDMenuSchema.setNodeCode(MaxCode);
			// 更新菜单表的NodeOrder
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			if (ischild.equalsIgnoreCase("true")) {
				sql = "update LDMenu set NodeOrder = NodeOrder+1 "
						+ "Where NodeOrder > ?NodeOrder?";
				sqlbv6.sql(sql);
				sqlbv6.put("NodeOrder", NodeOrder);
				tLDMenuSchema.setNodeOrder(NodeOrder + 1);
			} else {
				sql = "update LDMenu set NodeOrder = NodeOrder+1 "
						+ "Where NodeOrder >= ?NodeOrder?";
				sqlbv6.sql(sql);
				sqlbv6.put("NodeOrder", NodeOrder);
				tLDMenuSchema.setNodeOrder(NodeOrder);
			}
			if (!tExeSQL.execUpdateSQL(sqlbv6)) {
				CError tError = new CError();
				tError.moduleName = "LDMenuFunBLS";
				tError.functionName = "insertCode";
				tError.errorMessage = "更新NodeOrder失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			LDMenuDB tLDMenuDB = new LDMenuDB(conn);
			tLDMenuDB.setSchema(tLDMenuSchema);
			if (ischild.equalsIgnoreCase("true")) {
				// 按照子菜单插入
				// tLDMenuSchema.setParentNodeCode(Node);
				if (!tLDMenuDB.insert()) {
					CError tError = new CError();
					tError.moduleName = "LDMenuFunBLS";
					tError.functionName = "insertCode";
					tError.errorMessage = "插入菜单失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				// 是否为页面功能菜单2005
				if (ischild2.equalsIgnoreCase("false")) {
					sql = "update LDMenu set ChildFlag = ChildFlag + 1 "
							+ "Where NodeCode = '?Node?'";
					SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
					sqlbv7.sql(sql);
					sqlbv7.put("Node", Node);
					if (!tExeSQL.execUpdateSQL(sqlbv7)) {
						CError tError = new CError();
						tError.moduleName = "LDMenuFunBLS";
						tError.functionName = "insertCode";
						tError.errorMessage = "更新父菜单孩子数失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				} else {
					sql = "update LDMenu set NodeSign = '1' "
							+ "Where NodeCode = '?Node?'";
					SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
					sqlbv8.sql(sql);
					sqlbv8.put("Node", Node);
					if (!tExeSQL.execUpdateSQL(sqlbv8)) {
						CError tError = new CError();
						tError.moduleName = "LDMenuFunBLS";
						tError.functionName = "insertCode";
						tError.errorMessage = "更新父菜单标志位失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
			} else {
				// 按照同级菜单插入
				// tLDMenuSchema.setParentNodeCode("0");
				// tLDMenuDB.setSchema(tLDMenuSchema);
				if (!tLDMenuDB.insert()) {
					CError tError = new CError();
					tError.moduleName = "LDMenuFunBLS";
					tError.functionName = "insertCode";
					tError.errorMessage = "插入菜单失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				// 是否为页面功能菜单2005
				if (ischild2.equalsIgnoreCase("false")) {
					sql = "update LDMenu set ChildFlag = trim(cast((to_number(ChildFlag) + 1) as char(20)))"
							+ "Where NodeCode = '?NodeCode?'";
					SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
					sqlbv9.sql(sql);
					sqlbv9.put("NodeCode", tLDMenuSchema.getParentNodeCode());
					if (!tExeSQL.execUpdateSQL(sqlbv9)) {
						CError tError = new CError();
						tError.moduleName = "LDMenuFunBLS";
						tError.functionName = "insertCode";
						tError.errorMessage = "更新父菜单孩子数失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
				// else
				// {
				// sql =
				// "update LDMenu set NodeSign = '1' "
				// + "Where NodeCode = '" + Node + "'";
				// if (!tExeSQL.execUpdateSQL(sql))
				// {
				// CError tError = new CError();
				// tError.moduleName = "LDMenuFunBLS";
				// tError.functionName = "insertCode";
				// tError.errorMessage = "更新父菜单标志位失败!";
				// this.mErrors.addOneError(tError);
				// conn.rollback();
				// conn.close();
				// return false;
				// }
				// }
			}
			// 插入菜单分组关联表：新菜单默认仅仅分配给8080菜单组
			LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new LDMenuGrpToMenuSchema();
			tLDMenuGrpToMenuSchema.setMenuGrpCode("8080");
			tLDMenuGrpToMenuSchema.setNodeCode(MaxCode);
			LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
			tLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);
			if (!tLDMenuGrpToMenuDB.insert()) {
				CError tError = new CError();
				tError.moduleName = "LDMenuFunBLS";
				tError.functionName = "insertCode";
				tError.errorMessage = "插入菜单分组关联表失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuFunBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			tReturn = false;
		}
		return tReturn;
	}
}
