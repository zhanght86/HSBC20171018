package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 扫描件申请
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class ScanApplyBLS {
private static Logger logger = Logger.getLogger(ScanApplyBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public ScanApplyBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 信息保存
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = save(cInputData);
		}

		if (tReturn)
			logger.debug("Save sucessful");
		else
			logger.debug("Save failed");

		return tReturn;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");

		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ScanApplyBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			// ES_DOC_MAIN表
			logger.debug("Start ES_DOC_MAIN表...");
			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);
			tES_DOC_MAINDB.setSchema((ES_DOC_MAINSchema) mInputData
					.getObjectByObjectName("ES_DOC_MAINSchema", 0));

			// 提交前确认该印刷号没有被其他操作员申请
			ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
			tES_DOC_MAINSchema.setDocID(tES_DOC_MAINDB.getDocID());
			String strOprator = tES_DOC_MAINSchema.getDB().query().get(1)
					.getOperator();
			logger.debug("ES_DOC_MAIN数据库存的操作员:" + strOprator + "提交申请的操作员:"
					+ tES_DOC_MAINDB.getOperator());
			// tongmeng 2008-01-21 modify
			// 修改判断逻辑
			if (!tES_DOC_MAINDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tES_DOC_MAINDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "ScanApplyBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "ES_DOC_MAIN表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			
			// LWMISSION表
			logger.debug("Start LWMISSION表...");
			LWMissionSchema mLWMissionSchema = (LWMissionSchema) mInputData.getObjectByObjectName("LWMissionSchema", 0);
			if(mLWMissionSchema!=null)
			{
				LWMissionDB tLWMissionDB = new LWMissionDB(conn);
				tLWMissionDB.setSchema(mLWMissionSchema);

				// 提交前确认该印刷号没有被其他操作员申请
				LWMissionSchema tLWMissionSchema = new LWMissionSchema();
				tLWMissionSchema.setMissionProp1(mLWMissionSchema.getMissionProp1());
				tLWMissionSchema.setActivityID(mLWMissionSchema.getActivityID());
				String sOprator = tLWMissionSchema.getDB().query().get(1).getDefaultOperator();
				logger.debug("LWMission数据库存的操作员:" + sOprator + "提交申请的操作员:"
						+ mLWMissionSchema.getDefaultOperator());
				// 修改判断逻辑
				if (!tLWMissionDB.update()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tES_DOC_MAINDB.mErrors);
					CError.buildErr(this, "ES_DOC_MAIN表数据保存失败!") ;
					conn.rollback();
					conn.close();
					return false;
				}
			}		

			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ScanApplyBLS";
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

	public static void main(String[] args) {
		ScanApplyBLS scanApplyBLS1 = new ScanApplyBLS();
	}
}
