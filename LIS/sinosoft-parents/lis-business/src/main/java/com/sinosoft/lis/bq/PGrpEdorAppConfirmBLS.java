package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vdb.LCPolDBSet;
import com.sinosoft.lis.vdb.LJSGetEndorseDBSet;
import com.sinosoft.lis.vdb.LPEdorMainDBSet;
import com.sinosoft.lis.vdb.LPGrpEdorMainDBSet;
import com.sinosoft.lis.vdb.LPReturnLoanDBSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统保全申请确认变动功能部分
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author Tjj
 * @version 1.0
 */

public class PGrpEdorAppConfirmBLS {
private static Logger logger = Logger.getLogger(PGrpEdorAppConfirmBLS.class);
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	// 操作符
	public String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();

	public PGrpEdorAppConfirmBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start GrpAppEdorConfirm BLS Submit...");

		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mLPGrpEdorMainSet = (LPGrpEdorMainSet) mInputData
				.getObjectByObjectName("LPGrpEdorMainSet", 0);

		if (cOperate.equals("APPCONFIRM||LR")) {
			if (!grpAppConfrimLR())
				return false;
		} else if (cOperate.equals("APPCONFIRM||NS")) {
			if (!grpAppConfrimNS())
				return false;
		} else if (cOperate.equals("APPCONFIRM||RF")) {
			if (!grpAppConfrimRF())
				return false;
		} else if (cOperate.equals("APPCONFIRM||LF")) {
			if (!grpAppConfrimLF())
				return false;
		} else if (cOperate.equals("APPCONFIRM||LT")) {
			if (!grpAppConfrimLT())
				return false;
		} else {
			if (!saveGrpAppConfirmNo())
				return false;
		}
		return true;
	}

	/**
	 * 保全借款
	 * 
	 * @return
	 */
	private boolean grpAppConfrimLF() {
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		LJSGetEndorseSet tLJSGetEndorseSet = (LJSGetEndorseSet) mInputData
				.getObjectByObjectName("LJSGetEndorseSet", 0);

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// 更新主表信息
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(tLPGrpEdorItemSchema);
			if (!tLPGrpEdorItemDB.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "更新主表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 新增批改补退费表（应收/应付）数据
			LJSGetEndorseDBSet tLJSGetEndorseDBSet = new LJSGetEndorseDBSet(
					conn);
			tLJSGetEndorseDBSet.set(tLJSGetEndorseSet);
			if (!tLJSGetEndorseDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "新增批改补退费表（应收/应付）数据失败!";
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
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 团体退保
	 * 
	 * @return
	 */
	private boolean grpAppConfrimLT() {
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		LJSGetEndorseSet tLJSGetEndorseSet = (LJSGetEndorseSet) mInputData
				.getObjectByObjectName("LJSGetEndorseSet", 0);
		LPEdorMainSet tLPEdorMainSet = (LPEdorMainSet) mInputData
				.getObjectByObjectName("LPEdorMainSet", 0);

		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 更新主表信息
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(tLPGrpEdorItemSchema);
			if (!tLPGrpEdorItemDB.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "更新主表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 新增批改补退费表（应收/应付）数据
			LJSGetEndorseDBSet tLJSGetEndorseDBSet = new LJSGetEndorseDBSet(
					conn);
			tLJSGetEndorseDBSet.set(tLJSGetEndorseSet);
			if (!tLJSGetEndorseDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "新增批改补退费表（应收/应付）数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 新增公共帐户个人保全主表数据
			LPEdorMainDBSet tLPEdorMainDBSet = new LPEdorMainDBSet(conn);
			tLPEdorMainDBSet.set(tLPEdorMainSet);
			if (!tLPEdorMainDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "新增公共帐户个人保全主表数据失败!";
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
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}

		return true;
	}

	/**
	 * 团体新增附加险
	 * 
	 * @return
	 */
	private boolean grpAppConfrimNS() {
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		LJSGetEndorseSet tLJSGetEndorseSet = (LJSGetEndorseSet) mInputData
				.getObjectByObjectName("LJSGetEndorseSet", 0);
		LCPolSet tLCPolSet = (LCPolSet) mInputData.getObjectByObjectName(
				"LCPolSet", 0);

		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 更新主表信息
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(tLPGrpEdorItemSchema);
			if (!tLPGrpEdorItemDB.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "更新主表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 新增批改补退费表（应收/应付）数据
			LJSGetEndorseDBSet tLJSGetEndorseDBSet = new LJSGetEndorseDBSet(
					conn);
			tLJSGetEndorseDBSet.set(tLJSGetEndorseSet);
			if (!tLJSGetEndorseDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "新增批改补退费表（应收/应付）数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 更新保单表数据，设置集体下个人数据到复核、核保通过状态
			LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
			tLCPolDBSet.set(tLCPolSet);
			if (!tLCPolDBSet.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "设置集体下个人数据到复核、核保通过状态失败!";
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
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}

		return true;
	}

	/**
	 * 还款
	 * 
	 * @return
	 */
	private boolean grpAppConfrimRF() {
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();

		tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		tLJSGetEndorseSet = (LJSGetEndorseSet) mInputData
				.getObjectByObjectName("LJSGetEndorseSet", 0);
		tLPReturnLoanSet = (LPReturnLoanSet) mInputData.getObjectByObjectName(
				"LPReturnLoanSet", 0);

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 更新主表信息
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(tLPGrpEdorItemSchema);
			if (!tLPGrpEdorItemDB.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "更新主表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 新增批改补退费表（应收/应付）数据
			LJSGetEndorseDBSet tLJSGetEndorseDBSet = new LJSGetEndorseDBSet(
					conn);
			tLJSGetEndorseDBSet.set(tLJSGetEndorseSet);
			if (!tLJSGetEndorseDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "新增批改补退费表（应收/应付）数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 修改保全还款业务表
			LPReturnLoanDBSet tLPReturnLoanDBSet = new LPReturnLoanDBSet(conn);
			tLPReturnLoanDBSet.set(tLPReturnLoanSet);
			if (!tLPReturnLoanDBSet.update()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "修改保全还款业务表失败!";
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
			tError.moduleName = "PEdorAppConfirmBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}

		return true;
	}

	private boolean grpAppConfrimLR() {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		tLJSGetEndorseSchema = (LJSGetEndorseSchema) mInputData
				.getObjectByObjectName("LJSGetEndorseSchema", 0);

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorConfirmWTBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			// 保单信息备份
			if (tLJSGetEndorseSchema != null) {
				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB(conn);
				tLJSGetEndorseDB.setSchema(tLJSGetEndorseSchema);
				if (!tLJSGetEndorseDB.insert()) {
					CError tError = new CError();
					tError.moduleName = "PGrpEdorAppConfirmBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "集体交退费保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);

			if (!tLPGrpEdorItemDB.update()) {
				CError tError = new CError();
				tError.moduleName = "PGrpEdorAppConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "集体保单保全主表更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * 申请确认默认操作(含事务)
	 * 
	 * @return
	 */
	private boolean saveGrpAppConfirmNo() {
		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorConfirmWTBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);

			if (!tLPGrpEdorItemDB.update()) {
				CError tError = new CError();
				tError.moduleName = "PGrpEdorConfirmBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "保单保全项目表信息更新失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLPGrpEdorMainSet.size() > 0) {
				LPGrpEdorMainDBSet tLPGrpEdorMainDBSet = new LPGrpEdorMainDBSet();
				tLPGrpEdorMainDBSet.set(mLPGrpEdorMainSet);
				if (!tLPGrpEdorMainDBSet.update()) {
					CError tError = new CError();
					tError.moduleName = "PGrpEdorConfirmBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "保单保全主表信息更新失败!";
					this.mErrors.addOneError(tError);
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * 申请确认默认操作(无事务)
	 * 
	 * @return
	 */
	// private boolean saveGrpAppConfirmNo(Connection conn)
	// {
	//
	// if (conn==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "PGrpEdorConfirmWTBLS";
	// tError.functionName = "saveData";
	// tError.errorMessage = "数据库连接失败!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	//
	// try
	// {
	// conn.setAutoCommit(false);
	//
	// String tSql = "update LCGrpPol set EndorsementDate";
	// ExeSQL tExeSQL = new ExeSQL(conn);
	// if (!tExeSQL.execUpdateSQL(tSql))
	// {
	// CError tError = new CError();
	// tError.moduleName = "PGrpEdorConfirmBLS";
	// tError.functionName = "saveData";
	// tError.errorMessage = "集体保单更新失败!";
	// this.mErrors .addOneError(tError) ;
	// conn.rollback();
	// conn.close();
	// return false;
	// }
	// LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
	// tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
	//
	// if (!tLPGrpEdorItemDB.update())
	// {
	// CError tError = new CError();
	// tError.moduleName = "PGrpEdorConfirmBLS";
	// tError.functionName = "saveData";
	// tError.errorMessage = "保单保全主表信息更新失败!";
	// this.mErrors .addOneError(tError) ;
	// conn.rollback();
	// conn.close();
	// return false;
	// }
	//
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// }
	// return true;
	// }
}
