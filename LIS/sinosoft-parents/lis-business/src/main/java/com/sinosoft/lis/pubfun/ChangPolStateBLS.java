/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class ChangPolStateBLS {
private static Logger logger = Logger.getLogger(ChangPolStateBLS.class);
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	public ChangPolStateBLS() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("开始执行changepolstate的submit");
		mInputData = (VData) cInputData.clone();
		String mOperate;
		mOperate = cOperate;
		mLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0);

		if (mOperate.equals("UPDATA")) {
			Connection conn = DBConnPool.getConnection();
			if (conn == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ChangPolStateBLS";
				tError.functionName = "submitData";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			try {
				conn.setAutoCommit(false);

				// 修改个人保单状态
				ExeSQL tExeSQL = new ExeSQL(conn);
				String sql = "update LCPol " + "set polstate = '"
						+ "?s1?" + "'" + "where polno = '"
						+ "?s2?" + "' ";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("s1", mLCPolSchema.getPolState());
				sqlbv1.put("s2", mLCPolSchema.getPolNo());
				logger.debug("执行的sql语句是" + sql);

				if (tExeSQL.execUpdateSQL(sqlbv1) == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tExeSQL.mErrors);
					CError tError = new CError();
					tError.moduleName = "ChangPolStateBLS";
					tError.functionName = "submitData";
					tError.errorMessage = "LCPolDBSet修改失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				conn.commit();
				conn.close();
				logger.debug("执行成功");
			} // end of try
			catch (Exception ex) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ChangPolStateBLS";
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

		}
		return true;
	}
}
