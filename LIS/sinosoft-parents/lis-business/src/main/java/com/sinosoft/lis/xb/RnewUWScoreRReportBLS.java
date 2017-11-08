package com.sinosoft.lis.xb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.RnewScoreRReportSet;
import com.sinosoft.lis.vschema.RnewScoreRReportSubSet;
import java.io.*;
import oracle.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.OracleResultSet;

/**
 * <p>
 * Title: Web业务系统生调评审录入部分
 * </p>
 * <p>
 * Description: 数据库功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ln
 * @version 1.0
 */

public class RnewUWScoreRReportBLS {
private static Logger logger = Logger.getLogger(RnewUWScoreRReportBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 传输数据类
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	public RnewUWScoreRReportBLS() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		logger.debug("Start UWScoreRReportBLS Submit...");

		if (!save()) {
			logger.debug("Save failed");
			logger.debug("End UWScoreRReportBLS Submit...");
			return false;
		}

		logger.debug("Save succ");
		return true;
	}

	/**
	 * 保存函数
	 */

	private boolean save() {
		RnewScoreRReportSchema tRnewScoreRReportSchema = new RnewScoreRReportSchema();// 定义局部变量，之后赋值
		RnewScoreRReportSubSet tRnewScoreRReportSubSet = new RnewScoreRReportSubSet();
		tRnewScoreRReportSchema.setSchema((RnewScoreRReportSchema) mInputData
				.getObjectByObjectName("RnewScoreRReportSchema", 0));
		tRnewScoreRReportSubSet = (RnewScoreRReportSubSet) mInputData
				.getObjectByObjectName("RnewScoreRReportSubSet", 0);

		// logger.debug("Start Save...");
		Connection conn; // 建立数据库的连接
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!");
			return false;
		}
		// 如果已经存在记录则删除，然后新增；同一核保员对同一投保单的核保分析报告记录只有一条
		try {
			conn.setAutoCommit(false);
			logger.debug("query...");
			RnewScoreRReportDB tRnewScoreRReportDB = new RnewScoreRReportDB();
			RnewScoreRReportSubDB tRnewScoreRReportSubDB;
			RnewScoreRReportSet tRnewScoreRReportSet = new RnewScoreRReportSet();
			tRnewScoreRReportDB.setContNo(tRnewScoreRReportSchema.getContNo());
			tRnewScoreRReportDB.setCustomerNo(tRnewScoreRReportSchema
					.getCustomerNo());
			tRnewScoreRReportSet = tRnewScoreRReportDB.query();
			if (tRnewScoreRReportSet != null && tRnewScoreRReportSet.size() > 0) {
				logger.debug("update...");
				tRnewScoreRReportDB = new RnewScoreRReportDB(conn);
				tRnewScoreRReportSubDB = new RnewScoreRReportSubDB(conn);
				tRnewScoreRReportDB.setSchema(tRnewScoreRReportSchema);
				if (!tRnewScoreRReportDB.update()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tRnewScoreRReportDB.mErrors);
					CError.buildErr(this, "数据修改失败!");
					conn.rollback();
					conn.close();
					return false;
				}
				// 修改子表
				for (int i = 1; i <= tRnewScoreRReportSubSet.size(); i++) {
					tRnewScoreRReportSubDB
							.setSchema(tRnewScoreRReportSubSet.get(i));
					if (!tRnewScoreRReportSubDB.update()) {
						// @@错误处理
						this.mErrors.copyAllErrors(tRnewScoreRReportDB.mErrors);
						CError.buildErr(this, "数据修改失败!");
						conn.rollback();
						conn.close();
						return false;
					}
				}
			} else {
				logger.debug("Start 保存...");
				tRnewScoreRReportDB = new RnewScoreRReportDB(conn);
				tRnewScoreRReportSubDB = new RnewScoreRReportSubDB(conn);
				tRnewScoreRReportDB.setSchema(tRnewScoreRReportSchema);
				if (!tRnewScoreRReportDB.insert()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tRnewScoreRReportDB.mErrors);
					CError.buildErr(this, "数据新增失败!");
					conn.rollback();
					conn.close();
					return false;
				}
				// 修改子表
				for (int i = 1; i <= tRnewScoreRReportSubSet.size(); i++) {
					tRnewScoreRReportSubDB
							.setSchema(tRnewScoreRReportSubSet.get(i));
					if (!tRnewScoreRReportSubDB.insert()) {
						// @@错误处理
						this.mErrors.copyAllErrors(tRnewScoreRReportDB.mErrors);
						CError.buildErr(this, "数据新增失败!");
						conn.rollback();
						conn.close();
						return false;
					}
				}

			}

			conn.commit();// 数据库将数值全部传入
			conn.close();
			logger.debug("写入核保生调评审表完成！");
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, ex.toString());
			try {
				conn.rollback();// 数据库不传入任何数值
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

}
