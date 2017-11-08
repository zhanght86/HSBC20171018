package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBankAuthDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCCustomerImpartDB;
import com.sinosoft.lis.db.LCEdorReasonDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBankAuthSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCEdorReasonSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.vdb.LCAddressDBSet;
import com.sinosoft.lis.vdb.LCBnfDBSet;
import com.sinosoft.lis.vdb.LCCustomerImpartDBSet;
import com.sinosoft.lis.vdb.LCPolDBSet;
import com.sinosoft.lis.vdb.LPContDBSet;
import com.sinosoft.lis.vdb.LPPolDBSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统问题件功能部分
 * </p>
 * <p>
 * Description: 数据库功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author WHN
 * @version 1.0
 */
public class PolModifyBLS {
private static Logger logger = Logger.getLogger(PolModifyBLS.class);
	// 是否存在需要人工核保保单
	int merrno = 0;

	// 传输数据类
	private VData mInputData;

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private LCContSchema tLCContSchema = new LCContSchema();
	private LPContSet tLPContSet = new LPContSet();
	private LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	private LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	private LCBankAuthSchema tLCBankAuthSchema = new LCBankAuthSchema();
	private LCBnfSet tLCBnfSet = new LCBnfSet();
	private LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCEdorReasonSchema tLCEdorReasonSchema = new LCEdorReasonSchema();
	private LCAddressSet tLCAddressSet = new LCAddressSet();
	private LCPolSet tLCPolSet = new LCPolSet();
	private LPPolSet tLPPolSet = new LPPolSet();
	private String ContNo;

	public PolModifyBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start PolModifyBLS Submit...");

		if (!this.saveData()) {
			return false;
		}

		logger.debug("End PolModifyBLS Submit...");

		mInputData = null;

		return true;
	}

	private boolean saveData() {
		tLCContSchema = (LCContSchema) mInputData.getObjectByObjectName(
				"LCContSchema", 0);
		tLPContSet = (LPContSet) mInputData.getObjectByObjectName("LPContSet",
				0);

		tLCInsuredSchema = (LCInsuredSchema) mInputData.getObjectByObjectName(
				"LCInsuredSchema", 0);
		tLCAppntSchema = (LCAppntSchema) mInputData.getObjectByObjectName(
				"LCAppntSchema", 0);
		tLCBankAuthSchema = (LCBankAuthSchema) mInputData
				.getObjectByObjectName("LCBankAuthSchema", 0);
		tLCBnfSet = (LCBnfSet) mInputData.getObjectByObjectName("LCBnfSet", 0);
		tLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
				.getObjectByObjectName("LCCustomerImpartSet", 0);
		tLCEdorReasonSchema = (LCEdorReasonSchema) mInputData
				.getObjectByObjectName("LCEdorReasonSchema", 0);
		this.tLCAddressSet = (LCAddressSet) mInputData.getObjectByObjectName(
				"LCAddressSet", 0);
		this.tLCPolSet = (LCPolSet) mInputData.getObjectByObjectName(
				"LCPolSet", 0);
		ContNo = (String) mInputData.getObjectByObjectName("String", 0);

		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!") ;

			return false;
		}

		try {
			conn.setAutoCommit(false);

			if (tLCContSchema != null) {
				LCContDB tLCContDB = new LCContDB(conn);
				tLCContDB.setSchema(tLCContSchema);

				if (!tLCContDB.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCContDB.mErrors);

					CError.buildErr(this, "个人保单表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLPContSet != null) {
				LPContDBSet tLPContDBSet = new LPContDBSet(conn);
				tLPContDBSet.set(tLPContSet);

				if (!tLPContDBSet.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLPContDBSet.mErrors);

					CError.buildErr(this, "个人保单表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLCBankAuthSchema != null) {
				LCBankAuthDB tLCBankAuthDB = new LCBankAuthDB(conn);
				tLCBankAuthDB.setSchema(tLCBankAuthSchema);

				if (tLCBankAuthDB.getInfo()) {
					if (!tLCBankAuthDB.delete()) {
						// @@错误处理
						//this.mErrors.copyAllErrors(tLCBankAuthDB.mErrors);

						CError.buildErr(this, "银行授权书表更新失败!");
						conn.rollback();
						conn.close();

						return false;
					}
				}

				if (tLCContSchema.getPayLocation().equals("0")
						|| tLCContSchema.getPayLocation().equals("8")) {
					tLCBankAuthDB.setSchema(tLCBankAuthSchema);

					if (!tLCBankAuthDB.insert()) {
						// @@错误处理
						//this.mErrors.copyAllErrors(tLCBankAuthDB.mErrors);

						CError.buildErr(this, "银行授权书表更新失败!") ;
						conn.rollback();
						conn.close();

						return false;
					}
				}
			}

			if (tLCInsuredSchema != null) {
				LCInsuredDB tLCInsuredDB = new LCInsuredDB(conn);
				tLCInsuredDB.setSchema(tLCInsuredSchema);

				if (!tLCInsuredDB.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCInsuredDB.mErrors);

					CError.buildErr(this, "被保人信息表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLCAppntSchema != null) {
				LCAppntDB tLCAppntDB = new LCAppntDB(conn);
				tLCAppntDB.setSchema(tLCAppntSchema);

				if (!tLCAppntDB.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCAppntDB.mErrors);

					CError.buildErr(this, "投保人信息表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLCAddressSet != null && tLCAddressSet.size() > 0) {
				LCAddressDBSet tLCAddressDBSet = new LCAddressDBSet(conn);
				tLCAddressDBSet.set(tLCAddressSet);

				if (!tLCAddressDBSet.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCAddressDBSet.mErrors);

					CError.buildErr(this, "投被保人地址信息表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLCPolSet != null && tLCPolSet.size() > 0) {
				LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
				tLCPolDBSet.set(tLCPolSet);

				if (!tLCPolDBSet.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);

					CError.buildErr(this, "保单信息表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLPPolSet != null && tLPPolSet.size() > 0) {
				LPPolDBSet tLPPolDBSet = new LPPolDBSet(conn);
				tLPPolDBSet.set(tLPPolSet);

				if (!tLPPolDBSet.update()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLPPolDBSet.mErrors);

					CError.buildErr(this, "保单保全信息表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}
			
			if ((tLCBnfSet != null) && (tLCBnfSet.size() > 0)) {
			LCBnfDB tLCBnfDB = new LCBnfDB(conn);
			tLCBnfDB.setContNo(ContNo);

			LCBnfDBSet tLCBnfDBSet = new LCBnfDBSet(conn);
			tLCBnfDBSet.set(tLCBnfDB.query());

			if (tLCBnfDBSet.size() > 0) {
				if (!tLCBnfDBSet.delete()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCBnfDBSet.mErrors);

					CError.buildErr(this, "受益人表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}
			
				tLCBnfDBSet.clear();
				tLCBnfDBSet.set(tLCBnfSet);

				if (!tLCBnfDBSet.insert()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCBnfDBSet.mErrors);

					CError.buildErr(this, "受益人表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if ((tLCCustomerImpartSet != null)
					&& (tLCCustomerImpartSet.size() > 0)) {
			LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB(
					conn);
			tLCCustomerImpartDB.setContNo(ContNo);

			LCCustomerImpartDBSet tLCCustomerImpartDBSet = new LCCustomerImpartDBSet(
					conn);
			tLCCustomerImpartDBSet.set(tLCCustomerImpartDB.query());

			if (tLCCustomerImpartDBSet.size() > 0) {
				if (!tLCCustomerImpartDBSet.delete()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCCustomerImpartDBSet.mErrors);

					CError.buildErr(this, "客户告知表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}
			
				tLCCustomerImpartDBSet.clear();
				tLCCustomerImpartDBSet.set(tLCCustomerImpartSet);

				if (!tLCCustomerImpartDBSet.insert()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCCustomerImpartDBSet.mErrors);

					CError.buildErr(this, "客户告知表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLCEdorReasonSchema != null) {
				LCEdorReasonDB tLCEdorReasonDB = new LCEdorReasonDB(conn);
				tLCEdorReasonDB.setSchema(tLCEdorReasonSchema);

				if (tLCEdorReasonDB.getInfo()) {
					if (!tLCEdorReasonDB.delete()) {
						// @@错误处理
						//this.mErrors.copyAllErrors(tLCEdorReasonDB.mErrors);

						CError.buildErr(this, "保全批改原因表更新失败!") ;
						conn.rollback();
						conn.close();

						return false;
					}
				}

				tLCEdorReasonDB.setSchema(tLCEdorReasonSchema);

				if (!tLCEdorReasonDB.insert()) {
					// @@错误处理
					//this.mErrors.copyAllErrors(tLCEdorReasonDB.mErrors);

					CError.buildErr(this, "保全批改原因表更新失败!") ;
					conn.rollback();
					conn.close();

					return false;
				}
			}

			conn.commit();
			conn.close();
		}

		// end of try
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, ex.toString()) ;

			try {
				conn.rollback();
			} catch (Exception e) {
			}

			return false;
		}

		return true;
	}
}
