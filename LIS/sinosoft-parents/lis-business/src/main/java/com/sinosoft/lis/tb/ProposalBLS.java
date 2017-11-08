package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCBankAccDB;
import com.sinosoft.lis.db.LCBankAuthDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDelPolLogDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPolOtherDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LockTableDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCBankAccSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vdb.LCBnfDBSet;
import com.sinosoft.lis.vdb.LCCustomerImpartDBSet;
import com.sinosoft.lis.vdb.LCDiscountDBSet;
import com.sinosoft.lis.vdb.LCDutyDBSet;
import com.sinosoft.lis.vdb.LCGetDBSet;
import com.sinosoft.lis.vdb.LCInsuredRelatedDBSet;
import com.sinosoft.lis.vdb.LCPremDBSet;
import com.sinosoft.lis.vdb.LCSpecDBSet;
import com.sinosoft.lis.vdb.LJSPayDBSet;
import com.sinosoft.lis.vdb.LJSPayPersonDBSet;
import com.sinosoft.lis.vschema.LCBankAccSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author YT
 * @version 1.0
 */
public class ProposalBLS {
private static Logger logger = Logger.getLogger(ProposalBLS.class);
	/** 传入的数据 */
	private VData mInputData;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	/** 集体投保单号 */
	private String mGrpPolNo = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public ProposalBLS() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		// 个人投保信息保存
		if (this.mOperate.equals("INSERT||PROPOSAL")) {
			if (saveProposal() == false) {
				return false;
			}
		}
		// 个人投保信息修改
		if (this.mOperate.equals("UPDATE||PROPOSAL")) {
			if (updateProposal() == false) {
				return false;
			}
		}
		// 个人投保信息删除
		if (this.mOperate.equals("DELETE||PROPOSAL")) {
			if (deleteProposal() == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 个人投保业务数据的保存函数
	 */
	private boolean saveProposal() {
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!");
			return false;
		}
		try {
			logger.debug("Start Save...");
			conn.setAutoCommit(false);

			// 投保单系列表插库动作
			if (this.insertPol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 集体投保单部分
			if (this.updateGrpPol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 银行账户表
			// LCBankAccSchema tLCBankAccSchema = new LCBankAccSchema();
			// LCBankAccSet tLCBankAccSet = new LCBankAccSet();
			// tLCBankAccSchema=( LCBankAccSchema
			// )mInputData.getObjectByObjectName("LCBankAccSchema",0);
			// if( tLCBankAccSchema != null )
			// {
			// LCBankAccDB tLCBankAccDB=new LCBankAccDB( conn );
			// String sqlStr =
			// "select * from LCBankAcc where BankCode='"+tLCBankAccSchema
			// .getBankCode()+"' "
			// + "and BankAccNo='"+tLCBankAccSchema.getBankAccNo()+"'";
			// tLCBankAccSet=tLCBankAccDB.executeQuery(sqlStr);
			// if(tLCBankAccSet.size()==0)//如果没有该项纪录，则添加
			// {
			// tLCBankAccDB.setSchema(tLCBankAccSchema);
			// if (!tLCBankAccDB.insert())
			// {
			// conn.rollback();
			// conn.close();
			// return false;
			// }
			// }
			// }

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, ex.toString());
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
	 * 个人投保业务数据的保存函数
	 */
	private boolean updateProposal() {
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!");
			return false;
		}
		try {
			logger.debug("Start Update...");
			conn.setAutoCommit(false);

			// 投保单系列表删除动作
			if (this.deletePol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 投保单系列表插库动作
			if (this.insertPol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 集体投保单部分
			if (this.updateGrpPol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 银行账户表更新（特殊处理）
			// logger.debug("银行账户表更新");
			LCBankAccSchema tLCBankAccSchema = new LCBankAccSchema();
			LCBankAccSet tLCBankAccSet = new LCBankAccSet();
			tLCBankAccSchema = (LCBankAccSchema) mInputData
					.getObjectByObjectName("LCBankAccSchema", 0);
			if (tLCBankAccSchema != null) { // 如果数据准备好
				LCBankAccDB tLCBankAccDB = new LCBankAccDB(conn);
				String sqlStr = "select * from LCBankAcc where BankCode='"
						+ "?BankCode?" + "' "
						+ "and BankAccNo='?BankAccNo?'";
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				sqlbv8.sql(sqlStr);
				sqlbv8.put("BankCode",tLCBankAccSchema.getBankCode());
				sqlbv8.put("BankAccNo",tLCBankAccSchema.getBankAccNo());
				tLCBankAccSet = tLCBankAccDB.executeQuery(sqlbv8);
				if (tLCBankAccSet.size() == 0) { // 如果没有该项纪录，则添加
					tLCBankAccDB.setSchema(tLCBankAccSchema);
					// logger.debug("ProposalBLS : " +
					// tLCBankAccDB.getBankAccNo());
					if (!tLCBankAccDB.insert()) {
						conn.rollback();
						conn.close();
						return false;
					}
				} else { // 如果有数据更新
					tLCBankAccDB.setSchema(tLCBankAccSchema);
					if (!tLCBankAccDB.update()) {
						conn.rollback();
						conn.close();
						return false;
					}
				}
			}
			// logger.debug("银行账户表更新结束");

			// 删除应收总表
			// logger.debug("Start 删除应收总表...");
			/*LJSPayDBSet tLJSPayDBSet = new LJSPayDBSet(conn);
			tLJSPayDBSet.set((LJSPaySet) mInputData.getObjectByObjectName(
					"LJSPaySet", 0));

			if (!tLJSPayDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJSPayDBSet.mErrors);
				CError.buildErr(this, "LJSPay表删除失败!");
				return false;
			}*/

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, ex.toString());
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
	 * 个人投保业务数据的保存函数
	 */
	private boolean deleteProposal() {
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!");
			return false;
		}
		try {
			logger.debug("Start Delete...");
			conn.setAutoCommit(false);

			// 投保单系列表删除动作
			if (this.deletePol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 集体投保单部分
			if (this.updateGrpPol(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			/*
			 * 银行账户表不删除，在插入时先查询，如果没有再插入 LCBankAccDB tLCBankAccDB=new
			 * LCBankAccDB( conn ); tLCBankAccDB.setSchema(( LCBankAccSchema
			 * )mInputData.getObjectByObjectName("LCBankAccSchema",0)); if
			 * (!tLCBankAccDB.delete()) { conn.rollback(); conn.close(); return
			 * false; }
			 */
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalBLS";
			tError.functionName = "updateProposal";
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
	 * 更新集体投保单的部分
	 **/
	private boolean updateGrpPol(Connection conn) {
		String tPolType = "";
		tPolType = (String) mInputData.getObject(1);
		// logger.debug("更新集体投保单的部分:"+tPolType);
		if (tPolType.equals("2")) {
			// // 集体险种表
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);
			tLCGrpPolDB.setSchema((LCGrpPolSchema) mInputData
					.getObjectByObjectName("LCGrpPolSchema", 0));
			if (!tLCGrpPolDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError.buildErr(this, "集体险种数据修改失败!");
				return false;
			}
			// 集体合同
			LCGrpContDB tLCGrpContDB = new LCGrpContDB(conn);
			tLCGrpContDB.setSchema((LCGrpContSchema) mInputData
					.getObjectByObjectName("LCGrpContSchema", 0));
			if (!tLCGrpContDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				CError.buildErr(this, "集体险种数据修改失败!");
				return false;
			}

		}
		return true;
	}

	/**
	 * 个人投保单系列表插库动作
	 **/
	private boolean insertPol(Connection conn) {
		// 合同表更新
		LCContDB tLCContDB = new LCContDB(conn);
		tLCContDB.setSchema((LCContSchema) mInputData.getObjectByObjectName(
				"LCContSchema", 0));
		if (!tLCContDB.update()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			CError.buildErr(this, "合同保单数据保存失败!");
			return false;
		}

		// 保单主表
		logger.debug("Start 保单...");
		LCPolDB tLCPolDB = new LCPolDB(conn);
		tLCPolDB.setSchema((LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0));
		if (!tLCPolDB.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError.buildErr(this, "保单数据保存失败!");
			return false;
		}
		mGrpPolNo = tLCPolDB.getGrpPolNo();
		// logger.debug("1111");

		// 投保人
		// logger.debug("Start 投保人...");
		String appntType = "";
		appntType = (String) mInputData.getObject(0);
		// if( appntType.equals( "1" )) // 个人投保人
		// {
		// LCAppntIndDB tLCAppntIndDB = new LCAppntIndDB(conn);
		// tLCAppntIndDB.setSchema(( LCAppntIndSchema
		// )mInputData.getObjectByObjectName("LCAppntIndSchema",0));
		// if (!tLCAppntIndDB.insert())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLCAppntIndDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "insertPol";
		// tError.errorMessage = "投保人数据保存失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// if( appntType.equals( "2" )) // 集体投保人
		// {
		// LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB(conn);
		// tLCAppntGrpDB.setSchema(( LCAppntGrpSchema
		// )mInputData.getObjectByObjectName("LCAppntGrpSchema",0));
		// if (!tLCAppntGrpDB.insert())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLCAppntGrpDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "insertPol";
		// tError.errorMessage = "投保人数据保存失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// logger.debug("2222");

		// 被保人
		// logger.debug("Start 被保人...");
		// LCInsuredDBSet tLCInsuredDBSet=new LCInsuredDBSet(conn);
		// tLCInsuredDBSet.set((LCInsuredSet)mInputData.getObjectByObjectName(
		// "LCInsuredBLSet",0));
		// if (!tLCInsuredDBSet.insert())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLCInsuredDBSet.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "insertPol";
		// tError.errorMessage = "被保人数据保存失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// logger.debug("3333");
		// 连带连生被保人
		// logger.debug("Start 连带连生被保人...");mLCInsuredRelatedSet
		LCInsuredRelatedDBSet tLCInsuredRelatedDBSet = new LCInsuredRelatedDBSet(
				conn);
		tLCInsuredRelatedDBSet.set((LCInsuredRelatedSet) mInputData
				.getObjectByObjectName("LCInsuredRelatedSet", 0));
		if (!tLCInsuredRelatedDBSet.delete()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCInsuredRelatedDBSet.mErrors);
			CError.buildErr(this, "被保人数据保存失败!");
			return false;
		}

		if (!tLCInsuredRelatedDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCInsuredRelatedDBSet.mErrors);
			CError.buildErr(this, "被保人数据保存失败!");
			return false;
		}
		// logger.debug("3333");

		// 受益人
		// logger.debug("Start 受益人...");
		LCBnfDBSet tLCBnfDBSet = new LCBnfDBSet(conn);
		tLCBnfDBSet.set((LCBnfSet) mInputData.getObjectByObjectName(
				"LCBnfBLSet", 0));
		if (!tLCBnfDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBnfDBSet.mErrors);
			CError.buildErr(this, "受益人数据保存失败!");
			return false;
		}
		// logger.debug("4444");

		// 告知信息
		// logger.debug("Start 告知...");
		LCCustomerImpartDBSet tLCCustomerImpartDBSet = new LCCustomerImpartDBSet(
				conn);
		tLCCustomerImpartDBSet.set((LCCustomerImpartSet) mInputData
				.getObjectByObjectName("LCCustomerImpartBLSet", 0));
		if (!tLCCustomerImpartDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCustomerImpartDBSet.mErrors);
			CError.buildErr(this, "告知信息数据保存失败!");
			return false;
		}
		// logger.debug("5555");

		// 特别约定
		logger.debug("Start 特约...");
		LCSpecDBSet tLCSpecDBSet = new LCSpecDBSet(conn);
		tLCSpecDBSet.set((LCSpecSet) mInputData.getObjectByObjectName(
				"LCSpecBLSet", 0));
		logger.debug("特约调数:" + tLCSpecDBSet.size());
		if (!tLCSpecDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecDBSet.mErrors);
			CError.buildErr(this, "特别约定数据保存失败!");
			return false;
		}
		// logger.debug("6666");

		// 保费项
		// logger.debug("Start 保费 ...");
		LCPremDBSet tLCPremDBSet = new LCPremDBSet(conn);
		tLCPremDBSet.set((LCPremSet) mInputData.getObjectByObjectName(
				"LCPremBLSet", 0));
		if (!tLCPremDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecDBSet.mErrors);
			CError.buildErr(this, "保费项数据保存失败!");
			return false;
		}
		// logger.debug("7777");

		// 给付项
		// logger.debug("Start 给付...");
		LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
		tLCGetDBSet.set((LCGetSet) mInputData.getObjectByObjectName(
				"LCGetBLSet", 0));
		if (!tLCGetDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDBSet.mErrors);
			CError.buildErr(this, "给付数据保存失败!");
			return false;
		}

		// 责任表
		// logger.debug("Start 责任...");
		LCDutyDBSet tLCDutyDBSet = new LCDutyDBSet(conn);
		tLCDutyDBSet.set((LCDutySet) mInputData.getObjectByObjectName(
				"LCDutyBLSet", 0));
		if (!tLCDutyDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
			CError.buildErr(this, "责任表数据保存失败!");
			return false;
		}
		// 特殊要素表
		LCPolOtherSchema tLCPolOtherSchema = (LCPolOtherSchema) mInputData
				.getObjectByObjectName("LCPolOtherSchema", 0);

		if (tLCPolOtherSchema != null) {
			LCPolOtherDB tLCPolOtherDB = new LCPolOtherDB(conn);
			tLCPolOtherDB.setSchema(tLCPolOtherSchema);
			if (!tLCPolOtherDB.insert()) {
				logger.debug("1");
				this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
				CError.buildErr(this, "特殊信息保存失败!");
				return false;
			}
		}
		// 银行授权书表
		// LCBankAuthDB tLCBankAuthDB=new LCBankAuthDB( conn );
		// LCBankAuthSchema tLCBankAuthSchema = ( LCBankAuthSchema
		// )mInputData.getObjectByObjectName("LCBankAuthSchema",0);
		// if( tLCBankAuthSchema != null )
		// {
		// tLCBankAuthDB.setSchema( tLCBankAuthSchema );
		// if (!tLCBankAuthDB.insert())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLCBankAuthDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "insertPol";
		// tError.errorMessage = "保单数据保存失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }

		// 个人客户表
		// LDPersonDBSet tLDPersonDBSet=new LDPersonDBSet(conn);
		// tLDPersonDBSet.set((LDPersonSet)mInputData.getObjectByObjectName(
		// "LDPersonSet",0));
		// if( tLDPersonDBSet.size() > 0 )
		// {
		// if (!tLDPersonDBSet.insert())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLDPersonDBSet.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "insertPol";
		// tError.errorMessage = "个人客户表数据保存失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }

		// logger.debug("8888");
		
		//保存折扣信息和应收信息 2011-1-11
		LCDiscountDBSet tLCDiscountDBSet = new LCDiscountDBSet(conn);
		tLCDiscountDBSet.set((LCDiscountSet) mInputData.getObjectByObjectName(
				"LCDiscountSet", 0));
		if (!tLCDiscountDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDiscountDBSet.mErrors);
			CError.buildErr(this, "折扣数据保存失败!");
			return false;
		}
		LJSPayPersonDBSet tLJSPayPersonDBSet = new LJSPayPersonDBSet(conn);
		tLJSPayPersonDBSet.set((LJSPayPersonSet) mInputData.getObjectByObjectName(
				"LJSPayPersonSet", 0));
		if (!tLJSPayPersonDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayPersonDBSet.mErrors);
			CError.buildErr(this, "应收子表数据保存失败!");
			return false;
		}
		LJSPayDBSet tLJSPayDBSet = new LJSPayDBSet(conn);
		tLJSPayDBSet.set((LJSPaySet) mInputData.getObjectByObjectName(
				"LJSPaySet", 0));
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(tLJSPayDBSet.get(1).getOtherNo());
		tLJSPayDB.setOtherNoType(tLJSPayDBSet.get(1).getOtherNoType());
		if (tLJSPayDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
			CError.buildErr(this, "LJSPay表删除失败!");
			return false;
		}
		if (!tLJSPayDBSet.insert()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayDBSet.mErrors);
			CError.buildErr(this, "应收总表数据保存失败!");
			return false;
		}
		
		return true;
	}

	/**
	 * 个人投保单系列表删除动作
	 **/
	private boolean deletePol(Connection conn) {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		LCPolSchema tLCPolSchema = (LCPolSchema) mInputData
				.getObjectByObjectName("LCPolSchema", 0);
		String tProposalNo = tLCPolSchema.getProposalNo();
		mGrpPolNo = tLCPolSchema.getGrpPolNo();

		LCContSchema tLCContSchema = (LCContSchema) mInputData
				.getObjectByObjectName("LCContSchema", 0);
		// logger.debug("PolNO:" + tProposalNo);
		// 删除时作锁表，修改时不用做，因为可能多次修改
		if (this.mOperate.equals("DELETE||PROPOSAL")) {
			if (LockTable(tProposalNo, conn, "TD") == false) // 即TB_Delete
			{
				return false;
			}
			// /////////////////////////开始填写日志和备份数据//////////////////////
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tProposalNo); // 不能保证其正确性
			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				CError.buildErr(this, "没有查到险种信息!");
				return false;
			}

			// /////////////////////////开始备份险种日志///////////////////////////
			LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();
			LCDelPolLogDB mLCDelPolLogDB = new LCDelPolLogDB(conn);
			mLCDelPolLog.setOtherNo(tLCPolDB.getPolNo());
			mLCDelPolLog.setOtherNoType("1");
			mLCDelPolLog.setPrtNo(tLCPolDB.getPrtNo());
			if (tLCPolDB.getAppFlag().equals("1")) {
				mLCDelPolLog.setIsPolFlag("1");
			} else {
				mLCDelPolLog.setIsPolFlag("0");
			}
			mLCDelPolLog.setOperator(mGlobalInput.Operator);
			mLCDelPolLog.setManageCom(mGlobalInput.ManageCom);
			mLCDelPolLog.setMakeDate(PubFun.getCurrentDate());
			mLCDelPolLog.setMakeTime(PubFun.getCurrentTime());
			mLCDelPolLog.setModifyDate(PubFun.getCurrentDate());
			mLCDelPolLog.setModifyTime(PubFun.getCurrentTime());
			mLCDelPolLogDB.setSchema(mLCDelPolLog);
			if (!mLCDelPolLogDB.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "险种日志填写失败!");
				return false;
			}

			// ////////////////////////备份险种日志结束////////////////////////////
			// ///////////////////////开始备份数据////////////////////////////////
			String sql = "insert into LOBPol (select * from LCPol where PolNo='"
					+ "?PolNo?" + "')";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("PolNo",tLCPolDB.getPolNo());
			ExeSQL tExeSQL = new ExeSQL(conn);
			if (!tExeSQL.execUpdateSQL(sqlbv1)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份险种失败!");
				return false;

			}
			sql = "insert into LOBInsuredRelated (select * from LCInsuredRelated where PolNo='"
					+ "?PolNo1?" + "')";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("PolNo1",tLCPolDB.getPolNo());
			if (!tExeSQL.execUpdateSQL(sqlbv2)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份连带被保人失败!");
				return false;

			}
			sql = "insert into LOBBnf (select * from LCBnf where PolNo='"
					+ "?PolNo2?" + "')";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("PolNo2",tLCPolDB.getPolNo());
			if (!tExeSQL.execUpdateSQL(sqlbv3)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份受益人失败!");
				return false;

			}
			sql = "insert into LOBDuty (select * from LCDuty where PolNo='"
					+ "?PolNo3?" + "')";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("PolNo3",tLCPolDB.getPolNo());
			if (!tExeSQL.execUpdateSQL(sqlbv4)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份责任失败!");
				return false;

			}
			sql = "insert into LOBPrem (select * from LCPrem where PolNo='"
					+ "?PolNo4?" + "')";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sql);
			sqlbv5.put("PolNo4",tLCPolDB.getPolNo());
			if (!tExeSQL.execUpdateSQL(sqlbv5)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份保费失败!");
				return false;

			}
			sql = "insert into LOBGet (select * from LCGet where PolNo='"
					+ "?PolNo5?" + "')";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(sql);
			sqlbv6.put("PolNo5",tLCPolDB.getPolNo());
			if (!tExeSQL.execUpdateSQL(sqlbv6)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份领取项失败!");
				return false;

			}
			sql = "insert into LOBSpec (select * from LCSpec where PolNo='"
					+ "?PolNo6?" + "')";
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(sql);
			sqlbv7.put("PolNo6",tLCPolDB.getPolNo());
			if (!tExeSQL.execUpdateSQL(sqlbv7)) {
				this.mErrors.copyAllErrors(mLCDelPolLogDB.mErrors);
				CError.buildErr(this, "备份特别约定失败!");
				return false;

			}

			// ///////////////////////备份数据完毕////////////////////////////////

		}
		// 更新个人合同表
		LCContDB tLCContDB = new LCContDB(conn);
		tLCContDB.setSchema(tLCContSchema);
		if (!tLCContDB.update()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			CError.buildErr(this, "个人合同数据修改失败!");
			return false;
		}

		// 保费项
		LCPremDB tLCPremDB = new LCPremDB(conn);
		tLCPremDB.setPolNo(tProposalNo);
		if (tLCPremDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			CError.buildErr(this, "LCPrem表删除失败!");
			return false;
		}

		// 领取项
		LCGetDB tLCGetDB = new LCGetDB(conn);
		tLCGetDB.setPolNo(tProposalNo);
		if (tLCGetDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);
			CError.buildErr(this, "LCGet表删除失败!");
			return false;
		}

		// 责任
		LCDutyDB tLCDutyDB = new LCDutyDB(conn);
		tLCDutyDB.setPolNo(tProposalNo);
		if (tLCDutyDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			CError.buildErr(this, "LCDuty表删除失败!");
			return false;
		}

		// 特别约定
		LCSpecDB tLCSpecDB = new LCSpecDB(conn);
		tLCSpecDB.setPolNo(tProposalNo);
		if (tLCSpecDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecDB.mErrors);
			CError.buildErr(this, "LCSpec表删除失败!");
			return false;
		}

		// // 告知
		// LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB( conn
		// );
		// /*Lis5.3 upgrade set
		// tLCCustomerImpartDB.setPolNo( tProposalNo );
		// */
		// if (tLCCustomerImpartDB.deleteSQL() == false)
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors( tLCCustomerImpartDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "deletePol";
		// tError.errorMessage = "LCCustomerImpart表删除失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		// // 投保人
		// String appntType = "";
		// appntType = ( String )mInputData.getObject( 0 );
		// if( appntType.equals( "1" )) // 个人投保人
		// {
		// LCAppntIndDB tLCAppntIndDB = new LCAppntIndDB( conn );
		// tLCAppntIndDB.setPolNo( tProposalNo );
		// if (tLCAppntIndDB.deleteSQL() == false)
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors( tLCAppntIndDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "deletePol";
		// tError.errorMessage = "LCAppntInd表删除失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// if( appntType.equals( "2" )) // 集体投保人
		// {
		// LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB( conn );
		// tLCAppntGrpDB.setPolNo( tProposalNo );
		// if (tLCAppntGrpDB.deleteSQL() == false)
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors( tLCAppntGrpDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "deletePol";
		// tError.errorMessage = "LCAppntGrp表删除失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }

		// // 被保人
		// LCInsuredDB tLCInsuredDB = new LCInsuredDB( conn );
		// /*Lis5.3 upgrade set
		// tLCInsuredDB.setPolNo( tProposalNo );
		// */
		// if (tLCInsuredDB.deleteSQL() == false)
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLCInsuredDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ProposalBLS";
		// tError.functionName = "deletePol";
		// tError.errorMessage = "LCInsured表删除失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		// 受益人
		LCBnfDB tLCBnfDB = new LCBnfDB(conn);
		tLCBnfDB.setPolNo(tProposalNo);
		if (tLCBnfDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBnfDB.mErrors);
			CError.buildErr(this, "LCBnf表删除失败!");
			return false;
		}

		// 银行授权书表
		LCBankAuthDB tLCBankAuthDB = new LCBankAuthDB(conn);
		tLCBankAuthDB.setPolNo(tProposalNo);
		if (tLCBankAuthDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBankAuthDB.mErrors);
			CError.buildErr(this, "LCBankAuth表删除失败!");
			return false;
		}

		// 投保单
		LCPolDB tLCPolDB = new LCPolDB(conn);
		tLCPolDB.setPolNo(tProposalNo);
		if (tLCPolDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError.buildErr(this, "LCPol表删除失败!");
			return false;
		}
		
		//折扣表
		LCDiscountDB tLCDiscountDB = new LCDiscountDB(conn);
		tLCDiscountDB.setPolNo(tProposalNo);
		if (tLCDiscountDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDiscountDB.mErrors);
			CError.buildErr(this, "LCDiscount表删除失败!");
			return false;
		}
		
		//应收子表
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		tLJSPayPersonDB.setPolNo(tProposalNo);
		if (tLJSPayPersonDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);
			CError.buildErr(this, "LJSPayPerson表删除失败!");
			return false;
		}
		
		return true;
	}

	/**
	 * 添加一条纪录，如果添加失败，认为已经有记录
	 * 
	 * @param PolNo
	 *            --投保单号
	 * @param conn
	 * @param strOper
	 *            --号码类型
	 * @return
	 */
	public boolean LockTable(String PolNo, Connection conn, String strOper) {
		LockTableDB tLockTableDB = new LockTableDB(conn);
		tLockTableDB.setNoLimit(PolNo);
		tLockTableDB.setNoType(strOper);
		tLockTableDB.setMakeDate(PubFun.getCurrentDate());
		tLockTableDB.setMakeTime(PubFun.getCurrentTime());
		if (tLockTableDB.insert() == false) {
			logger.debug("Lock failed :return false;");
			CError.buildErr(this, "该投保单（PolNo）正在操作中(" + strOper + "),您的操作无效！");
			return false;
		}
		return true;
	}

}
