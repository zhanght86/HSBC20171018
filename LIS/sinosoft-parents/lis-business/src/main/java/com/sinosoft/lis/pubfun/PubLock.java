package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Vector;

import com.sinosoft.lis.db.LockAppDB;
import com.sinosoft.lis.schema.LockAppSchema;
import com.sinosoft.lis.vschema.LockAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;

/**
 * <p>
 * Title:公共加锁程序
 * </p>
 * <p>
 * Description: 防止不同的业务岗位对同一笔业务做互斥的操作，如对与一个投保单，签单和删除动作为互斥的，当该投保单签单的时候就不能做删除
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class PubLock {
private static Logger logger = Logger.getLogger(PubLock.class);
	public CErrors mErrors = new CErrors(); // 错误信息
	private Connection con;
	private Vector mLockedNos = new Vector(); // 同一PubLock对象已经锁定的号码
	/**
	 * mflag = true: 传入Connection mflag = false: 不传入Connection
	 */
	private boolean mflag = false;
	private String mReason = "锁定应用模块不明确！";

	public PubLock() {
	}

	public PubLock(Connection tConnection) {
		con = tConnection;
		mflag = true;
	}

	public boolean pareLockData(Connection conn, String tOperatedNo,
			String tReason) {
		try {
			LockAppSchema tLockAppSchema = new LockAppSchema();
			LockAppSet tLockAppSet = new LockAppSet();
			LockAppDB tLockAppDB = new LockAppDB(conn);
			tLockAppDB.setOperatedNo(tOperatedNo);
			// 如果不存在任何应用模块对该记录加锁，则直接插入加锁信息
			if (!tLockAppDB.getInfo()) {
				tLockAppSchema.setOperatedNo(tOperatedNo);
				tLockAppSchema.setState("1");
				tLockAppSchema
						.setReason(StrTool.cTrim(tReason).equals("") ? mReason
								: tReason); // 当输入的原因为空时，取默认原因
				tLockAppSchema.setMakeDate(PubFun.getCurrentDate());
				tLockAppSchema.setMakeTime(PubFun.getCurrentTime());
				tLockAppSchema.setModifyDate(PubFun.getCurrentDate());
				tLockAppSchema.setModifyTime(PubFun.getCurrentTime());
				tLockAppDB.setSchema(tLockAppSchema);
				if (!tLockAppDB.insert()) {
					this.mErrors.copyAllErrors(tLockAppDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "PubLock";
					tError.functionName = "pareLockData";
					tError.errorMessage = "表LockApp插库失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			} else {
				//mysql 不支持nowait 暂时取消nowait
//				String tLockSQL = "select * from LockApp where OperatedNo = '"
//						+ "?tOperatedNo?" + "' for update nowait";
				String tLockSQL = "select * from LockApp where OperatedNo = '"
						+ "?tOperatedNo?" + "' for update";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(tLockSQL);
				sqlbv1.put("tOperatedNo", tOperatedNo);
				logger.debug("LockSQL: " + tLockSQL);
				tLockAppDB = new LockAppDB(conn);
				tLockAppSet = tLockAppDB.executeQuery(sqlbv1);
				if (tLockAppSet == null || tLockAppSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "PubLock";
					tError.functionName = "pareLockData";
					tError.errorMessage = "操作号：" + tOperatedNo
							+ "对表LockApp中记录加锁失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLockAppSchema = tLockAppSet.get(1);
				if (!tLockAppSchema.getState().equals("0")) {
					CError tError = new CError();
					tError.moduleName = "PubLock";
					tError.functionName = "pareLockData";
					tError.errorMessage = "操作号：" + tOperatedNo + "已经被其他业务锁定："
							+ tLockAppSchema.getReason() + "!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLockAppSchema.setState("1");
				tLockAppSchema.setMakeDate(PubFun.getCurrentDate());
				tLockAppSchema.setMakeTime(PubFun.getCurrentTime());
				tLockAppSchema.setModifyDate(PubFun.getCurrentDate());
				tLockAppSchema.setModifyTime(PubFun.getCurrentTime());
				tLockAppSchema
						.setReason(StrTool.cTrim(tReason).equals("") ? mReason
								: tReason);
				tLockAppDB.setSchema(tLockAppSchema);
				if (!tLockAppDB.update()) {
					this.mErrors.copyAllErrors(tLockAppDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "PubLock";
					tError.functionName = "pareLockData";
					tError.errorMessage = "表LockApp更新失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PubLock";
			tError.functionName = "pareLockData";
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

	public boolean pareUnLockData(Connection conn, String tOperatedNo) {
		try {
			LockAppSchema tLockAppSchema = new LockAppSchema();
			LockAppSet tLockAppSet = new LockAppSet();
			LockAppDB tLockAppDB = new LockAppDB(conn);
			tLockAppDB.setOperatedNo(tOperatedNo);
			// 如果原来没有加锁信息，直接返回
			if (!tLockAppDB.getInfo()) {
				return true;
			} else {
				//mysql 不支持nowait 暂时取消 nowait
//				String tLockSQL = "select * from LockApp where OperatedNo = '"
//						+ "?tOperatedNo?" + "' for update nowait";
				String tLockSQL = "select * from LockApp where OperatedNo = '"
						+ "?tOperatedNo?" + "' for update ";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(tLockSQL);
				sqlbv2.put("tOperatedNo", tOperatedNo);
				tLockAppDB = new LockAppDB(conn);
				tLockAppSet = tLockAppDB.executeQuery(sqlbv2);
				if (tLockAppSet == null || tLockAppSet.size() == 0) {
					String aa = tLockAppDB.mErrors.getFirstError();
					CError tError = new CError();
					tError.moduleName = "PubLock";
					tError.functionName = "pareUnLockData";
					tError.errorMessage = "操作号：" + tOperatedNo
							+ "对表LockApp加锁失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				tLockAppSchema = tLockAppSet.get(1);
				if (tLockAppSchema.getState().equals("1")) {
					tLockAppSchema.setState("0");
					tLockAppSchema.setModifyDate(PubFun.getCurrentDate());
					tLockAppSchema.setModifyTime(PubFun.getCurrentTime());
					tLockAppDB.setSchema(tLockAppSchema);
					if (!tLockAppDB.update()) {
						this.mErrors.copyAllErrors(tLockAppDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "PubLock";
						tError.functionName = "pareUnLockData";
						tError.errorMessage = "表LockApp更新失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PubLock";
			tError.functionName = "pareLockData";
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
	 * 对号码为tOperatedNo（如投保单号）的业务锁定，在解锁之前，除锁定业务外，其他业务不能对该号码做操作
	 * 
	 * @parameter tOperatedNo 需要锁定的号码
	 * @parameter tReason 加锁理由
	 * @return 如果加锁成功，返回true;加锁失败，则返回false
	 */
	public boolean lock(String tOperatedNo, String tReason) {
		try {
			if (mLockedNos.contains(tOperatedNo)) {
				logger.debug("本批次已经对号码成功锁定:" + tOperatedNo);
				return true;
			}

			if (mflag == false) {
				con = DBConnPool.getConnection();
			}
			if (con == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PubLock";
				tError.functionName = "lock";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			if (!pareLockData(con, tOperatedNo, tReason))
				return false;
			if (mflag == false) {
				con.commit();
				con.close();
				mLockedNos.add(tOperatedNo); // 将锁定号码加入到集合中
			}

		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PubLock";
			tError.functionName = "lock";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				// 如果发生异常以后，关闭连接
				if (mflag == false && !con.isClosed()) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 对数组中tArrayNo包含的每一个tOperatedNo号码（如投保单号）的业务锁定，在解锁之前，除锁定业务外，其他业务不能对该号码组做操作
	 * 
	 * @parameter tOperatedNo 需要锁定的号码
	 * @parameter tReason 加锁理由
	 * @return 如果所有的号码都加锁成功，返回true;如果其中一个加锁失败，则返回false
	 */
	public boolean lock(String[] tArrayNo, String tReason) {
		try {
			if (mflag == false) {
				con = DBConnPool.getConnection();
			}
			if (con == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PubLock";
				tError.functionName = "lock";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			for (int i = 0; i < tArrayNo.length; i++) {
				if (mLockedNos.contains(tArrayNo[i])) {
					logger.debug("本批次已经对号码成功锁定:" + tArrayNo[i]);
					continue;
				}
				if (!pareLockData(con, tArrayNo[i], tReason))
					return false;

			}
			if (mflag == false) {
				con.commit();
				con.close();
				// 对非传如连接的,保存该批次传入需要锁定
				for (int j = 0; j < tArrayNo.length; j++) {
					if (!mLockedNos.contains(tArrayNo[j])) // 如果号码中不包含这个号,加入集合中
					{
						mLockedNos.add(tArrayNo[j]);
					}
				}
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PubLock";
			tError.functionName = "lock";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				// 如果发生异常以后，关闭连接
				if (mflag == false && !con.isClosed()) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 对号码为tOperatedNo（如投保单号）的业务解除锁定，解锁后，允许其他业务对该号码对应的记录做操作
	 * 
	 * @parameter tOperatedNo 需要解锁的号码
	 * @return 如果解锁成功，返回true;解锁失败，则返回false
	 */
	public boolean unLock(String tOperatedNo) {
		try {
			if (mflag == false) {
				con = DBConnPool.getConnection();
			}
			if (con == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PubLock";
				tError.functionName = "unLock";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			// 解锁数据准备
			if (!pareUnLockData(con, tOperatedNo))
				return false;
			if (mflag == false) {
				con.commit();
				con.close();
				// 当解锁成功,删除集合中的号码
				if (mLockedNos.contains(tOperatedNo)) {
					mLockedNos.remove(tOperatedNo);
				}
			}

		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PubLock";
			tError.functionName = "lock";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				// 如果发生异常以后，关闭连接
				if (mflag == false && !con.isClosed()) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	public boolean unLock(String[] tArrayNo) {

		try {
			if (mflag == false) {
				con = DBConnPool.getConnection();
			}
			if (con == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PubLock";
				tError.functionName = "unLock";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 设置为非自动提交
			con.setAutoCommit(false);
			// 解锁数据准备
			for (int i = 0; i < tArrayNo.length; i++) {
				if (!pareUnLockData(con, tArrayNo[i]))
					return false;
			}
			if (mflag == false) {
				con.commit();
				con.close();
				for (int j = 0; j < tArrayNo.length; j++) {
					if (mLockedNos.contains(tArrayNo[j])) {
						mLockedNos.remove(tArrayNo[j]);
					}
				}
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PubLock";
			tError.functionName = "lock";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				// 如果发生异常以后，关闭连接
				if (mflag == false && !con.isClosed()) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;

	}

	public static void main(String[] args) {
		try {
			// Connection con1 = DBConnPool.getConnection();
			// 设置为非自动提交
			// con1.setAutoCommit(false);
			// PubLock pubLock1 = new PubLock(con1);
			PubLock pubLock1 = new PubLock();
			String[] tArray = { "86330020030210004620", "86330020030210004621",
					"86330020030210004622" };

			if (!pubLock1.lock("86330020030210004621", "abc"))
				logger.debug(pubLock1.mErrors.getError(0).functionName
						+ " : " + pubLock1.mErrors.getError(0).errorMessage);
			// pubLock1 = new PubLock();
			if (!pubLock1.lock(tArray, "defghi"))
				logger.debug(pubLock1.mErrors.getError(0).functionName
						+ " : " + pubLock1.mErrors.getError(0).errorMessage);

			if (!pubLock1.unLock(tArray))
				logger.debug(pubLock1.mErrors.getError(0).functionName
						+ " : " + pubLock1.mErrors.getError(0).errorMessage);

			if (!pubLock1.unLock("86330020030210004621"))
				logger.debug(pubLock1.mErrors.getError(0).functionName
						+ " : " + pubLock1.mErrors.getError(0).errorMessage);
			// con1.commit();
			// con1.close();
		} catch (Exception ex) {

		}

		logger.debug("完毕");
	}
}
