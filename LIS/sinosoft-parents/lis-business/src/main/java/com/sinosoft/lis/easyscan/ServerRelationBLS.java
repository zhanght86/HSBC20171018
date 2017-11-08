package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.ES_COM_SERVERDB;
import com.sinosoft.lis.schema.ES_COM_SERVERSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class ServerRelationBLS {
private static Logger logger = Logger.getLogger(ServerRelationBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();

	// 输入参数私有变量
	private ES_COM_SERVERSchema tES_COM_SERVERSchema;

	public ServerRelationBLS() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		getInputData();

		// DELETE||MAIN UPDATE||MAIN UPDATE||PAGES DELETE||PAGES
		tReturn = saveModify(cOperate);
		if (tReturn) {
			logger.debug("ServerRelationBLS:Save Successful!");
		} else {
			logger.debug("ServerRelationBLS:Save Failed!");
		}

		mResult.clear();
		mResult = mInputData;
		mInputData = null;

		return tReturn;
	}

	// 获取返回数据
	public VData getResult() {
		return mResult;
	}

	// 入参处理
	private boolean getInputData() {
		// 获取入参
		tES_COM_SERVERSchema = (ES_COM_SERVERSchema) mInputData.get(0);

		return true;
	}

	// 保存单证修改操作
	private boolean saveModify(String cOperate) {
		boolean blnReturn = false;
		logger.debug("Start saveModify ...");
		Connection conn = DBConnPool.getConnection();
		try {
			conn.setAutoCommit(false);

			if (conn == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ServerRelationBLS";
				tError.functionName = "saveModify";
				tError.errorMessage = "数据库连接失败";
				this.mErrors.addOneError(tError);
				return false;
			}

			conn.setAutoCommit(false);

			// 处理单证主表
			logger.debug("Dealing ES_COM_SERVER ...");

			ES_COM_SERVERDB tES_COM_SERVERDB = new ES_COM_SERVERDB(conn);
			tES_COM_SERVERDB.setSchema(tES_COM_SERVERSchema);
			blnReturn = true;
			if (cOperate.equals("INSERT")) // 添加新对应关系
			{
				blnReturn = tES_COM_SERVERDB.insert();
			}
			if (cOperate.equals("DELETE")) // 删除对应关系
			{
				blnReturn = tES_COM_SERVERDB.delete();
			}
			if (cOperate.equals("UPDATE")) // 更新对应关系
			{
				blnReturn = tES_COM_SERVERDB.update();
			}
			if (blnReturn == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ServerRelationBLS";
				tError.functionName = "saveModify";
				tError.errorMessage = "保存数据库ES_COM_SERVER表的修改出现错误";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 提交事务
			// conn.rollback();//测试用
			conn.commit();

			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception in BLS");
			logger.debug("Exception:" + ex.toString());

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ServerRelationBLS";
			tError.functionName = "saveModify";
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

	public static void main(String[] args) {
		ServerRelationBLS tServerRelationBLS = new ServerRelationBLS();
	}
}
