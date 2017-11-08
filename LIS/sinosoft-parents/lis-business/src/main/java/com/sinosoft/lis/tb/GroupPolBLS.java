package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统签单功能部分
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
 * @date 2002-09-25
 */
public class GroupPolBLS {
private static Logger logger = Logger.getLogger(GroupPolBLS.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 业务处理相关变量 */
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();

	// @Constructor
	public GroupPolBLS() {
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

		// 数据保存
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
		mLCGrpPolSchema = (LCGrpPolSchema) mInputData.getObjectByObjectName(
				"LCGrpPolSchema", 0);
		mLDGrpSchema = (LDGrpSchema) mInputData.getObjectByObjectName(
				"LDGrpSchema", 0);
		// 建立数据连接
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalFeeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 集体投保单保存
			if (saveGrpPol(conn) == false) {
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
			tError.moduleName = "ProposalFeeBLS";
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

	/**
	 * 保存集体投保单信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean saveGrpPol(Connection conn) {
		if (mOperate.equals("INSERT||GROUPPOL")) {
			if (insertGrpPol(conn) == false)
				return false;
		}

		if (mOperate.equals("UPDATE||GROUPPOL")) {
			if (updateGrpPol(conn) == false)
				return false;
			if (updateLCPol(conn) == false)
				return false;
		}

		if (mOperate.equals("DELETE||GROUPPOL")) {
			if (deleteGrpPol(conn) == false)
				return false;
		}

		return true;
	}

	/**
	 * 保存集体投保单信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean insertGrpPol(Connection conn) {
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GroupPolBLS";
			tError.functionName = "insertGrpPol";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);
			tLCGrpPolDB.setSchema(mLCGrpPolSchema);
			if (tLCGrpPolDB.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GroupPolBLS";
				tError.functionName = "insertGrpPol";
				tError.errorMessage = "表LCGrpPol插库失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			LDGrpDB tLDGrpDB = new LDGrpDB(conn);
			if (mLDGrpSchema != null) {
				tLDGrpDB.setSchema(mLDGrpSchema);
				if (tLDGrpDB.insert() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLDGrpDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "GroupPolBLS";
					tError.functionName = "insertGrpPol";
					tError.errorMessage = "表LDGrp插库失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpPolBLS";
			tError.functionName = "insertGrpPol";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 修改集体投保单信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean updateGrpPol(Connection conn) {
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);
		tLCGrpPolDB.setSchema(mLCGrpPolSchema);
		if (tLCGrpPolDB.update() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GroupPolBLS";
			tError.functionName = "updateGrpPol";
			tError.errorMessage = "表LCGrpPol修改失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 删除集体投保单信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean deleteGrpPol(Connection conn) {
		// 先查询个单是否存在，若有不容许删除
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(*) from lcpol where grppolno='"
				+ mLCGrpPolSchema.getGrpPolNo() + "'";
		SSRS tSSRS = tExeSQL.execSQL(sql);
		String strCount = tSSRS.GetText(1, 1);
		int SumCount = Integer.parseInt(strCount);
		if (SumCount > 0) {
			CError tError = new CError();
			tError.moduleName = "GroupPolBLS";
			tError.functionName = "deleteGrpPol";
			tError.errorMessage = "表LCGrpPol删除失败--还有个单存在，请重新操作!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);
		tLCGrpPolDB.setGrpPolNo(mLCGrpPolSchema.getGrpPolNo());
		if (tLCGrpPolDB.delete() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GroupPolBLS";
			tError.functionName = "deleteGrpPol";
			tError.errorMessage = "表LCGrpPol删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 更新集体下的个人投保单和多投保人（集体）
	 * 
	 * @param conn
	 * @return
	 */
	private boolean updateLCPol(Connection conn) {
		try {
			// 个人投保单更新
			ExeSQL tExeSQL = new ExeSQL(conn);
			String sql = "update LCPol " + "set PrtNo = '"
					+ mLCGrpPolSchema.getPrtNo() + "', " + "ManageCom = '"
					+ mLCGrpPolSchema.getManageCom() + "', " + "SaleChnl = '"
					+ mLCGrpPolSchema.getSaleChnl() + "', " + "AgentCom = '"
					+ mLCGrpPolSchema.getAgentCom() + "', " + "AgentCode = '"
					+ mLCGrpPolSchema.getAgentCode() + "', " + "AgentGroup = '"
					+ mLCGrpPolSchema.getAgentGroup() + "', "
					+ "AgentCode1 = '" + mLCGrpPolSchema.getAgentCode1()
					+ "', " + "ManageFeeRate = "
					+ mLCGrpPolSchema.getManageFeeRate() + ", ";
			if (mLCGrpPolSchema.getPayMode() != null)
				sql = sql + "PayMode = '" + mLCGrpPolSchema.getPayMode()
						+ "', "

						/*
						 * Lis5.3 upgrade get sql=sql + "BankCode = '" +
						 * mLCGrpPolSchema.getBankCode() + "', " + "BankAccNo = '" +
						 * mLCGrpPolSchema.getBankAccNo() + "', " + "Currency = '" +
						 * mLCGrpPolSchema.getCurrency() + "' "
						 */

						+ "where GrpPolNo = '" + mLCGrpPolSchema.getGrpPolNo()
						+ "'";

			logger.debug("个人投保单更新:" + sql);

			if (tExeSQL.execUpdateSQL(sql) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpPolApproveBLS";
				tError.functionName = "updateLCPol";
				tError.errorMessage = "个人保单表修改失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			sql = "update lcappntgrp set "
					+ "GrpName = '"
					+ mLCGrpPolSchema.getGrpName()
					+ "', "
					/*
					 * Lis5.3 upgrade get + "GrpNo = '" +
					 * mLCGrpPolSchema.getGrpNo() + "', " + "GrpAddress = '" +
					 * mLCGrpPolSchema.getGrpAddress() + "', " + "GrpZipCode = '" +
					 * mLCGrpPolSchema.getGrpZipCode() + "', " + "GrpNature = '" +
					 * mLCGrpPolSchema.getGrpNature() + "', " + "BusinessType = '" +
					 * mLCGrpPolSchema.getBusinessType() + "', " + "Peoples = " +
					 * mLCGrpPolSchema.getPeoples() + ", " + "RgtMoney = '" +
					 * mLCGrpPolSchema.getRgtMoney() + "', " + "Asset = '" +
					 * mLCGrpPolSchema.getAsset() + "', " + "NetProfitRate = '" +
					 * mLCGrpPolSchema.getNetProfitRate() + "', " +
					 * "MainBussiness = '" + mLCGrpPolSchema.getMainBussiness() +
					 * "', " + "Corporation = '" +
					 * mLCGrpPolSchema.getCorporation() + "', " + "ComAera = '" +
					 * mLCGrpPolSchema.getComAera() + "', " + "LinkMan1 = '" +
					 * mLCGrpPolSchema.getLinkMan1() + "', " + "Department1 = '" +
					 * mLCGrpPolSchema.getDepartment1() + "', " + "HeadShip1 = '" +
					 * mLCGrpPolSchema.getHeadShip1() + "', " + "Phone1 = '" +
					 * mLCGrpPolSchema.getPhone1() + "', " + "E_Mail1 = '" +
					 * mLCGrpPolSchema.getE_Mail1() + "', " + "Fax1 = '" +
					 * mLCGrpPolSchema.getFax1() + "', " + "LinkMan2 = '" +
					 * mLCGrpPolSchema.getLinkMan2() + "', " + "Department2 = '" +
					 * mLCGrpPolSchema.getDepartment2() + "', " + "HeadShip2 = '" +
					 * mLCGrpPolSchema.getHeadShip2() + "', " + "Phone2 = '" +
					 * mLCGrpPolSchema.getPhone2() + "', " + "E_Mail2 = '" +
					 * mLCGrpPolSchema.getE_Mail2() + "', " + "Fax2 = '" +
					 * mLCGrpPolSchema.getFax2() + "', " + "BankCode = '" +
					 * mLCGrpPolSchema.getBankCode() + "', " + "BankAccNo = '" +
					 * mLCGrpPolSchema.getBankAccNo() + "' "
					 */
					+ "where PolNo in (select PolNo from LCPol where GrpPolNo= '"
					+ mLCGrpPolSchema.getGrpPolNo() + "')";

			logger.debug("多投保人（集体）修改:" + sql);
			/*
			 * Lis5.3 upgrade get
			 * logger.debug("多投保人（编码转换后LinkMan1）:"+StrTool.unicodeToGBK(mLCGrpPolSchema.getLinkMan1()));
			 */

			if (tExeSQL.execUpdateSQL(sql) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpPolApproveBLS";
				tError.functionName = "updateLCPol";
				tError.errorMessage = "多投保人（集体）修改失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("多投保人（集体）修改over:");
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "GrpPolApproveBLS";
			tError.functionName = "updateLCPol";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

}
