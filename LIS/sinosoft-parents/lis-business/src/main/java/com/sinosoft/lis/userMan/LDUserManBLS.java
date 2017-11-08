/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Date;

import com.sinosoft.lis.db.LDMenuGrpDB;
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.encrypt.LisEncrypt;
import com.sinosoft.lis.menumang.MenuGrpUpdate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vdb.LDUserTOMenuGrpDBSet;
import com.sinosoft.lis.vschema.LDMenuGrpToMenuSet;
import com.sinosoft.lis.vschema.LDUserTOMenuGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
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
 * @author Dingzhong
 * @version 1.0
 */

public class LDUserManBLS {
private static Logger logger = Logger.getLogger(LDUserManBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public LDUserManBLS() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// logger.debug("Start LDMenuGrp BLS Submit...");

		// 信息保存
		if (this.mOperate.equals("insert")) {
			tReturn = save(cInputData);
		}

		// 信息删除
		if (this.mOperate.equals("delete")) {
			tReturn = remove(cInputData);
		}

		// 信息更新
		if (this.mOperate.equals("update")) {
			tReturn = update(cInputData);
		}

		// logger.debug("End LDUserMan BLS Submit...");
		return tReturn;
	}

	/**
	 * 删除操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean remove(VData mInputData) {
		boolean tReturn = true;
		// logger.debug("start remove...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserManBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			// logger.debug("Start 删除用户...");
			LDUserDB tLDUserDB = new LDUserDB(conn);
			LDUserSchema tUserSchema = (LDUserSchema) mInputData
					.getObjectByObjectName("LDUserSchema", 0);
			tLDUserDB.setSchema((LDUserSchema) mInputData
					.getObjectByObjectName("LDUserSchema", 0));
			if (!tLDUserDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDUserBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "用户删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			
			if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
				ExeSQL tExeSQL = new ExeSQL(conn);
    			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    			String tSql2 = "insert into lduser_trace (operatetype, operatetime, usercode, ip, id) values ('?operateType?','?opertateTime?','?userCode?','?ip?', '?id?') ";
    			sqlbv2.sql(tSql2);
    			sqlbv2.put("operateType", "DELETE");
    			sqlbv2.put("opertateTime", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
    			sqlbv2.put("userCode", tUserSchema.getUserCode());
    			String ipAndId = tExeSQL.getOneValue("select USER() from dual");
    			String[] ipId = ipAndId.split("@");
    			sqlbv2.put("ip", ipId[1]);
    			sqlbv2.put("id", ipId[0]);
    			tExeSQL.execUpdateSQL(sqlbv2);
    		}

			// logger.debug("start 转移用户关联菜单组及用户创建菜单组....");
			// logger.debug("start 转移用户关联菜单组及用户创建菜单组....");
			String delUserCode = tUserSchema.getUserCode();
			String deletor = (String) mInputData.getObjectByObjectName(
					"String", 0);
			// logger.debug("deletor : " + deletor);
			if (deletor == null) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 将被删用户创建的用户的operator改为当前操作员，相当于用户由操作员deletor接管
			String sqlStr = "update lduser set operator = '" + "?deletor?"
					+ "' where operator = '" + "?delUserCode?" + "'";
			// logger.debug(sqlStr);
			 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			 sqlbv3.sql(sqlStr);
			 sqlbv3.put("deletor", deletor);
			 sqlbv3.put("delUserCode", delUserCode);
			tLDUserDB.executeQuery(sqlbv3);

			// 将被删用户创建的菜单组由当前操作员deletor接管
			sqlStr = "update ldmenugrp set operator = '" + "?deletor?"
					+ "' where operator = '" + "?delUserCode?" + "'";
			// logger.debug(sqlStr);
			 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			 sqlbv4.sql(sqlStr);
			 sqlbv4.put("deletor", deletor);
			 sqlbv4.put("delUserCode", delUserCode);
			LDMenuGrpDB tMenuGrpDB = new LDMenuGrpDB(conn);
			tMenuGrpDB.executeQuery(sqlbv4);

			sqlStr = "update ldusertomenugrp set usercode = '" + "?deletor?"
					+ "' where usercode = '" + "?delUserCode?" + "'";
			// logger.debug(sqlStr);
			 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			 sqlbv5.sql(sqlStr);
			 sqlbv5.put("deletor", deletor);
			 sqlbv5.put("delUserCode", delUserCode);
			LDMenuGrpToMenuDB tMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
			tMenuGrpToMenuDB.executeQuery(sqlbv5);

			conn.commit();
			conn.close();
			// logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserBLS";
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

	/**
	 * 保存操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		// logger.debug("Start Save...");

		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// logger.debug("Start 保存用户...");
			LDUserDB tLDUserDB = new LDUserDB(conn);
			LDUserSchema tLDUserSchema = (LDUserSchema) mInputData
					.getObjectByObjectName("LDUserSchema", 0);
			if (tLDUserSchema == null) {
				return false;
			}

			// 开始进行密码加密
			String plainPwd = tLDUserSchema.getPassword();
			String encryptPwd = LisEncrypt.encrypt(plainPwd);
			tLDUserSchema.setPassword(encryptPwd);
			tLDUserDB.setSchema(tLDUserSchema);

			if (!tLDUserDB.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDUserBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "用户保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
				ExeSQL tExeSQL = new ExeSQL(conn);
    			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    			String tSql2 = "insert into lduser_trace (operatetype, operatetime, usercode, ip, id) values ('?operateType?','?opertateTime?','?userCode?','?ip?', '?id?') ";
    			sqlbv2.sql(tSql2);
    			sqlbv2.put("operateType", "INSERT");
    			sqlbv2.put("opertateTime", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
    			sqlbv2.put("userCode", tLDUserSchema.getUserCode());
    			String ipAndId = tExeSQL.getOneValue("select USER() from dual");
    			String[] ipId = ipAndId.split("@");
    			sqlbv2.put("ip", ipId[1]);
    			sqlbv2.put("id", ipId[0]);
    			tExeSQL.execUpdateSQL(sqlbv2);
    		}
			
			// logger.debug("Start 用户菜单组插入....");
			LDUserTOMenuGrpDBSet tLDUserToMenuGrpDBSet = new LDUserTOMenuGrpDBSet(
					conn);

			tLDUserToMenuGrpDBSet.set((LDUserTOMenuGrpSet) mInputData
					.getObjectByObjectName("LDUserTOMenuGrpSet", 0));

			int len = tLDUserToMenuGrpDBSet.size();
			// logger.debug("lens=" + len);

			if (!tLDUserToMenuGrpDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDUserToMenuGrpDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDUserBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "用户菜单组关联表保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
			// logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserBLS";
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

	/**
	 * 更新操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean update(VData mInputData) {
		boolean tReturn = true;
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		try {
			conn.setAutoCommit(false);

			// logger.debug("Start 更新用户...");

			LDUserDB tLDUserDB = new LDUserDB(conn);
			LDUserSchema tLDUserSchema = new LDUserSchema();

			tLDUserSchema = (LDUserSchema) mInputData.getObjectByObjectName(
					"LDUserSchema", 0);
			String usercode = tLDUserSchema.getUserCode();
			String operator = (String) mInputData.getObjectByObjectName(
					"String", 0);

			// logger.debug("go go go");
			// logger.debug("password is :" +
			// tLDUserSchema.getPassword());

			// 更新菜单组表,如果当前操作员和用户创建者一致
			String curOperator = (String) mInputData.getObjectByObjectName(
					"String", 0);
			String crtOperator = tLDUserSchema.getOperator();
			boolean updateUser = true;
			//用户管理现在只能001 看到
//			if (curOperator.compareTo(crtOperator) == 0) {
				// logger.debug("we start tLDusertDB.update!");
				// 开始进行密码加密
				String plainPwd = tLDUserSchema.getPassword();
				String encryptPwd = LisEncrypt.encrypt(plainPwd);
				tLDUserSchema.setPassword(encryptPwd);
				tLDUserDB.setSchema(tLDUserSchema);
				updateUser = tLDUserDB.update();

//			} else {
//				// logger.debug("we dont start tLDusertDB.update!");
//			}
			if (!updateUser) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDUserBLS";
				tError.functionName = "update";
				tError.errorMessage = "用户表更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			
			if(!"ORACLE".equalsIgnoreCase(SysConst.DBTYPE)){
				ExeSQL tExeSQL = new ExeSQL(conn);
    			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    			String tSql2 = "insert into lduser_trace (operatetype, operatetime, usercode, ip, id) values ('?operateType?','?opertateTime?','?userCode?','?ip?', '?id?') ";
    			sqlbv2.sql(tSql2);
    			sqlbv2.put("operateType", "UPDATE");
    			sqlbv2.put("opertateTime", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
    			sqlbv2.put("userCode", tLDUserSchema.getUserCode());
    			String ipAndId = tExeSQL.getOneValue("select USER() from dual");
    			String[] ipId = ipAndId.split("@");
    			sqlbv2.put("ip", ipId[1]);
    			sqlbv2.put("id", ipId[0]);
    			tExeSQL.execUpdateSQL(sqlbv2);
    		}

			// 在更新前，必须先得到实际此用户失去的菜单节点集合，以便进行菜单组的更新
			// logger.debug("在更新前，必须先得到实际此用户失去的菜单节点集合，以便进行菜单组的更新");
			String sqlStr = " select * from ldmenugrptomenu where menugrpcode in "
					+ " (select menugrpcode  from ldusertomenugrp where usercode = '"
					+ "?usercode?"
					+ "' and "
					+ " menugrpcode in (select menugrpcode from ldmenugrp where operator = '"
					+ "?operator?" + "'))";
			// logger.debug(sqlStr);
			LDMenuGrpToMenuDB tRemoveSetDB = new LDMenuGrpToMenuDB(conn);

			// tRemoveSet是原来所有菜单集合的总集，并不是真正的删除集合，但没关系,
			// 不过必须在插入新菜单组以后
			 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			 sqlbv1.sql(sqlStr);
			 sqlbv1.put("usercode", usercode);
			 sqlbv1.put("operator", operator);
			LDMenuGrpToMenuSet tRemoveSet = tRemoveSetDB.executeQuery(sqlbv1);

			// 更新首先删除关联表中的相关关联，然后再插入最新的数据
			sqlStr = "delete from ldusertomenugrp where usercode = '"
					+ "?usercode?"
					+ "' and "
					+ " menugrpcode in (select menugrpcode from ldmenugrp where operator = '"
					+ "?operator?" + "')";

			// logger.debug("****" + sqlStr + "***");
			 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			 sqlbv2.sql(sqlStr);
			 sqlbv2.put("usercode", usercode);
			 sqlbv2.put("operator", operator);
			ExeSQL exeSQL = new ExeSQL(conn);
			exeSQL.execUpdateSQL(sqlbv2);

			LDUserTOMenuGrpDBSet tLDUserToMenuGrpDBSet = new LDUserTOMenuGrpDBSet(
					conn);
			tLDUserToMenuGrpDBSet.set((LDUserTOMenuGrpSet) mInputData
					.getObjectByObjectName("LDUserTOMenuGrpSet", 0));
			// logger.debug("开始插入...");
			// logger.debug("BLS DBSet size:" +
			// tLDUserToMenuGrpDBSet.size());

			if (!tLDUserToMenuGrpDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDUserToMenuGrpDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDUserBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "用户菜单组关联表更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 开始更新此用户创建的菜单组
			MenuGrpUpdate tMenuGrpUpdate = new MenuGrpUpdate();
			boolean suc = tMenuGrpUpdate.userToMenuGrp(tRemoveSet, usercode,
					conn);
			if (!suc) {
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
			// logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserBLS";
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
