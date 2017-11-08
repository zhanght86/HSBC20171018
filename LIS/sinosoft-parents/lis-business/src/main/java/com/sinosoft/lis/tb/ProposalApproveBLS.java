package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统复核功能部分
 * </p>
 * <p>
 * Description: BL层业务逻辑保存类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft < /p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-17
 */
public class ProposalApproveBLS {
private static Logger logger = Logger.getLogger(ProposalApproveBLS.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public ProposalApproveBLS() {
	}

	// @Method
	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 保存数据
		if (this.saveData() == false)
			return false;

		// 释放数据容器
		mInputData = null;

		return true;
	}

	/**
	 * 保存数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean saveData() {
		// 分解传入数据容器中的数据
		LCPolSchema mLCPolSchema = (LCPolSchema) mInputData
				.getObjectByObjectName("LCPolSchema", 0);

		// 建立数据连接
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 修改个人投保单总表
			LCPolDB tLCPolDB = new LCPolDB(conn);
			tLCPolDB.setSchema(mLCPolSchema);
			if (tLCPolDB.update() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalApproveBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCPol修改失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
		} // end of try
		catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalApproveBLS";
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
}
