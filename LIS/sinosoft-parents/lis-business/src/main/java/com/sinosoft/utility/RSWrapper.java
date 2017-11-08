package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sinosoft.lis.vschema.LMRiskSet;

/**
 * ResultSet包裹类 完成类似4GL的游标的FETCH功能,主要用于大数据量的问题.
 * 
 * @author Yang Yalin
 * @version 1.0
 */
public class RSWrapper {
private static Logger logger = Logger.getLogger(RSWrapper.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 */
	private boolean useOutterConnection = false;

	public CErrors mErrors = new CErrors(); // 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private PreparedStatement mStatement = null;

	private SchemaSet container = null;

	// @Constructor
	public RSWrapper(Connection tConnection) {
		con = tConnection;
		useOutterConnection = true;
	}

	public RSWrapper() {
		con = null;
		useOutterConnection = false;
	}

	/**
	 * 准备数据查询条件
	 * 
	 * @param strSQL
	 *            String
	 * @return boolean
	 */
	public boolean prepareData(SchemaSet container, String strSQL) {
		this.container = container;

		logger.debug("prepareData:" + strSQL);

		if (mResultSet != null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
			tError.functionName = "prepareData";
			tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (!useOutterConnection) {
			con = DBConnPool.getConnection();
		}
		try {
			mStatement = con.prepareStatement(StrTool.GBKToUnicode(strSQL),ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			mResultSet = mStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RSWrapper";
			tError.functionName = "prepareData";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try {
				mResultSet.close();
			} catch (Exception ex2) {
			}
			try {
				mStatement.close();
			} catch (Exception ex3) {
			}
			if (!useOutterConnection) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}

		logger.debug("success prepared...");
		return true;
	}

	/**
	 * 获取数据集
	 * 
	 * @return boolean
	 */
	private boolean next() {
		boolean flag = true;
		if (null == mResultSet) {
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
			tError.functionName = "hasMoreData";
			tError.errorMessage = "数据集为空，请先准备数据集！";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			flag = mResultSet.next();
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
			tError.functionName = "hasMoreData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				mResultSet.close();
				mResultSet = null;
			} catch (Exception ex2) {
			}
			try {
				mStatement.close();
				mStatement = null;
			} catch (Exception ex3) {
			}
			if (!useOutterConnection) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		return flag;
	}

	/**
	 * 获取定量数据
	 * 
	 * @return SchemaSet
	 */
	public SchemaSet getData() {
		container.clear();

		if (null == mResultSet) {
			CError tError = new CError();
			tError.moduleName = "RSWrapper";
			tError.functionName = "getData";
			tError.errorMessage = "数据集为空，请先准备数据集！";
			this.mErrors.addOneError(tError);
			return null;
		}
		try {
			int tCount = 0;
			String clzName = container.getClass().getName();
			clzName = clzName.substring(clzName.lastIndexOf(".") + 1, clzName
					.length() - 3)
					+ "Schema";

			// 注意mResultSet.next()的作用
			while (tCount++ < SysConst.FETCHCOUNT && mResultSet.next()) {
				Schema schema = (Schema) Class.forName(
						"com.sinosoft.lis.schema." + clzName).newInstance();
				schema.setSchema(mResultSet, 1);
				container.add(schema);
			}

			logger.debug("RSWrapper Actual Fetched Row:" + tCount);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "RSWrapper";
			tError.functionName = "getData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				mResultSet.close();
				mResultSet = null;
			} catch (Exception ex2) {
			}
			try {
				mStatement.close();
				mStatement = null;
			} catch (Exception ex3) {
			}
			if (!useOutterConnection) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return null;
		}
		return container;
	}

	/**
	 * 关闭数据集
	 * 
	 * @return boolean
	 */
	public boolean close() {
		boolean flag = true;
		try {
			if (null == mResultSet) {
				CError tError = new CError();
				tError.moduleName = "RSWrapper";
				tError.functionName = "closeData";
				tError.errorMessage = "数据集已经关闭了！";
				this.mErrors.addOneError(tError);
				flag = false;
			} else {
				mResultSet.close();
				mResultSet = null;
			}
		} catch (Exception ex2) {
			CError tError = new CError();
			tError.moduleName = "RSWrapper";
			tError.functionName = "closeData";
			tError.errorMessage = ex2.toString();
			this.mErrors.addOneError(tError);
			flag = false;
		}
		try {
			if (null == mStatement) {
				CError tError = new CError();
				tError.moduleName = "RSWrapper";
				tError.functionName = "closeData";
				tError.errorMessage = "语句已经关闭了！";
				this.mErrors.addOneError(tError);
				flag = false;
			} else {
				mStatement.close();
				mStatement = null;
			}
		} catch (Exception ex3) {
			CError tError = new CError();
			tError.moduleName = "RSWrapper";
			tError.functionName = "closeData";
			tError.errorMessage = ex3.toString();
			this.mErrors.addOneError(tError);
			flag = false;
		}

		logger.debug("use out connection:" + useOutterConnection);

		if (!useOutterConnection) {
			try {
				con.close();
				logger.debug("inner connection closed");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		RSWrapper rswrapper = new RSWrapper();
		LMRiskSet lmRiskSet = new LMRiskSet();
		rswrapper.prepareData(lmRiskSet, "select * from lmrisk");
		do {
			rswrapper.getData();
			logger.debug("get data count:" + lmRiskSet.size());
		} while (lmRiskSet.size() > 0);
		rswrapper.close();
		logger.debug("commplete");

	}
	
	public boolean prepareData(SchemaSet container, SQLwithBindVariables sqbvc) {
		this.container = container;

		logger.debug("prepareData:" + sqbvc.originalSql());

		if (mResultSet != null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
			tError.functionName = "prepareData";
			tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (!useOutterConnection) {
			con = DBConnPool.getConnection();
		}
		try {
			mStatement = con.prepareStatement(sqbvc.sql(),ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqbvc.setParameters(mStatement);
			mResultSet = mStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RSWrapper";
			tError.functionName = "prepareData";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try {
				mResultSet.close();
			} catch (Exception ex2) {
			}
			try {
				mStatement.close();
			} catch (Exception ex3) {
			}
			if (!useOutterConnection) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}

		logger.debug("success prepared...");
		return true;
	}
}
