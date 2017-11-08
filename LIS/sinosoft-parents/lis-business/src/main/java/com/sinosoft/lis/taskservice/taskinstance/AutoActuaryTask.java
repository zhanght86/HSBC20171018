package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.taskservice.TaskThread;
import java.util.Date;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * 准备金自动运行入口
 * @author Administrator
 * 每个月23日自动提取准备金全部数据
 */
public class AutoActuaryTask extends TaskThread  {
private static Logger logger = Logger.getLogger(AutoActuaryTask.class);

	public CErrors mErrors = new CErrors();

	  public AutoActuaryTask() {
	  }

	  public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("start AutoActuaryTask.... "+PubFun.getCurrentDate() +" "+PubFun.getCurrentTime());
		try {

			// TODO Auto-generated method stub
			Connection conn = DBConnPool.getConnection();
			if (conn == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ReadFromFileBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据库连接失败!";
				// this.mErrors.addOneError(tError);
				// return false;
			}
			CallableStatement proc = null;
			try {
				// 开始事务，锁表
				conn.setAutoCommit(false);

				proc = conn.prepareCall("{ call generalloreserve(?) }");

				// 获取当前时间 默认为当月23号计算全部准备金
				FDate fDate = new FDate();
				Date tUCurrentDate = fDate.getDate(PubFun.getCurrentDate()); // java.util.date
				java.sql.Date tSCurrDate = new java.sql.Date(tUCurrentDate
						.getTime()); // java.sql.date

				proc.setDate(1, tSCurrDate);
				// proc.setString(1, "1");
				proc.execute();
				conn.commit();
			} catch (Exception ex) {
				conn.rollback();
				ex.printStackTrace();
			} finally {
				try {
					proc.close();
					conn.close();
				} catch (SQLException e) {
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		logger.debug("End AutoActuaryTask .... "+PubFun.getCurrentDate() +" "+PubFun.getCurrentTime());
		return true;
	}

	  public static void main(String[] args) {
		  AutoActuaryTask tAutoActuaryTask= new AutoActuaryTask();
		  tAutoActuaryTask.dealMain();
	  }
}
