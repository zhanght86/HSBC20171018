package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
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
 * @author HZM
 * @version 1.0
 */

public class LDGrpBLS {
private static Logger logger = Logger.getLogger(LDGrpBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private LDGrpSet mLDGrpSet;

	public LDGrpBLS() {
	}

	public static void main(String[] args) {
		LDGrpBLS proposalBLS1 = new LDGrpBLS();
		// proposalBLS1.submitData() ;
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start LDGrp BLS Submit...");
		// 执行添加纪录活动
		if (cOperate.equals("INSERT")) {
			tReturn = save();
			if (tReturn) {
				logger.debug("Save sucessful");
			} else {
				logger.debug("Save failed");
			}
		}
		// 执行更新纪录活动
		if (cOperate.equals("UPDATE")) {
			tReturn = update();
			if (tReturn) {
				logger.debug("Update sucessful");
			} else {
				logger.debug("Update failed");
			}
		}
		// 执行删除纪录活动
		if (cOperate.equals("DELETE")) {
			tReturn = delete();
			if (tReturn) {
				logger.debug("Delete sucessful");
			} else {
				logger.debug("Delete failed");
			}
		}

		logger.debug("End LDGrp BLS Submit...");

		// 如果有需要处理的错误，则返回
		// if (tLDGrpBLS.mErrors .needDealError() )
		// this.mErrors .copyAllErrors(tLDGrpBLS.mErrors ) ;

		mInputData = null;
		return tReturn;
	}

	private boolean save() {
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalBLS";
			tError.functionName = "saveProposal";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			logger.debug("Start Save...");
			conn.setAutoCommit(false);

			// 投保单系列表插库动作
			if (this.insertGrp(conn) == false) {
				conn.rollback();
				conn.close();
				return false;
			}

			// 集体投保单部分
			// if (this.updateGrp(conn) == false)
			// {
			// conn.rollback();
			// conn.close();
			// return false;
			// }

			// 银行账户表
			// LCBankAccSchema tLCBankAccSchema = new LCBankAccSchema();
			// LCBankAccSet tLCBankAccSet = new LCBankAccSet();
			// tLCBankAccSchema=( LCBankAccSchema
			// )mInputData.getObjectByObjectName("LCBankAccSchema",0);
			// if( tLCBankAccSchema != null )
			// {
			// LCBankAccDB tLCBankAccDB=new LCBankAccDB( conn );
			// String sqlStr = "select * from LCBankAcc where
			// BankCode='"+tLCBankAccSchema.getBankCode()+"' "
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
			CError tError = new CError();
			tError.moduleName = "ProposalBLS";
			tError.functionName = "saveProposal";
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

	private boolean insertGrp(Connection conn) {
		boolean tReturn = true;
		logger.debug("Start Save...");

		/** 集体信息 */
		logger.debug("Start 集体信息...");

		LDGrpDB tLDGrpDB = new LDGrpDB(conn);
		tLDGrpDB.setSchema((LDGrpSchema) mInputData.getObjectByObjectName(
				"LDGrpSchema", 0));

		if (!tLDGrpDB.insert()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
			tError.functionName = "submitData";
			tError.errorMessage = "团体客户信息保存失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB(conn);
		tLCGrpAddressDB.setSchema((LCGrpAddressSchema) mInputData
				.getObjectByObjectName("LCGrpAddressSchema", 0));
		if (tLCGrpAddressDB.getSchema().getAddressNo() != null
				&& !tLCGrpAddressDB.getSchema().getAddressNo().equals("")) {
			if (!tLCGrpAddressDB.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDGrpBLS";
				tError.functionName = "submitData";
				tError.errorMessage = "团体客户地址信息保存失败！";
				this.mErrors.addOneError(tError);
				logger.debug("err:" + tError.errorMessage);
				return false;
			}
		}
		logger.debug("return true");
		return true;
	}

	private boolean update() {
		boolean tReturn = true;
		logger.debug("Start Update...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
			tError.functionName = "update";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			/** 集体信息 */
			logger.debug("Start 集体信息...");
			LDGrpDB tLDGrpDB = new LDGrpDB();
			tLDGrpDB.setSchema((LDGrpSchema) mInputData.getObjectByObjectName(
					"LDGrpSchema", 0));
			String tLDGrpSql = "update LDGrp set ";
			if (tLDGrpDB.getPassword() != null
					&& !tLDGrpDB.getPassword().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "Password='" + tLDGrpDB.getPassword()
						+ "',";
			}
			if (tLDGrpDB.getGrpName() != null
					&& !tLDGrpDB.getGrpName().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "GrpName = '" + tLDGrpDB.getGrpName()
						+ "',";
			}
			if (tLDGrpDB.getBusinessType() != null
					&& !tLDGrpDB.getBusinessType().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "BusinessType ='"
						+ tLDGrpDB.getBusinessType() + "',";
			}
			if (tLDGrpDB.getGrpNature() != null
					&& !tLDGrpDB.getGrpNature().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "GrpNature ='"
						+ tLDGrpDB.getGrpNature() + "',";
			}
			if (tLDGrpDB.getPeoples() != 0) {
				tLDGrpSql = tLDGrpSql + "Peoples =" + tLDGrpDB.getPeoples()
						+ ",";
			}
			if (tLDGrpDB.getRgtMoney() != 0) {
				tLDGrpSql = tLDGrpSql + "RgtMoney=" + tLDGrpDB.getRgtMoney()
						+ ",";
			}
			if (tLDGrpDB.getAsset() != 0) {
				tLDGrpSql = tLDGrpSql + "Asset = " + tLDGrpDB.getAsset() + ",";
			}
			if (tLDGrpDB.getNetProfitRate() != 0) {
				tLDGrpSql = tLDGrpSql + "NetProfitRate ="
						+ tLDGrpDB.getNetProfitRate() + ",";
			}
			if (tLDGrpDB.getMainBussiness() != null
					&& !tLDGrpDB.getMainBussiness().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "MainBussiness ='"
						+ tLDGrpDB.getMainBussiness() + "',";
			}
			if (tLDGrpDB.getCorporation() != null
					&& !tLDGrpDB.getCorporation().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "Corporation ='"
						+ tLDGrpDB.getCorporation() + "',";
			}
			if (tLDGrpDB.getComAera() != null
					&& !tLDGrpDB.getComAera().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "ComAera ='" + tLDGrpDB.getComAera()
						+ "',";
			}
			if (tLDGrpDB.getFax() != null
					&& !tLDGrpDB.getFax().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "Fax ='" + tLDGrpDB.getFax() + "',";
			}
			if (tLDGrpDB.getPhone() != null
					&& !tLDGrpDB.getPhone().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "Phone ='" + tLDGrpDB.getPhone() + "',";
			}
			if (tLDGrpDB.getGetFlag() != null
					&& !tLDGrpDB.getGetFlag().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "GetFlag =" + tLDGrpDB.getGetFlag()
						+ ",";
			}
			if (tLDGrpDB.getSatrap() != null
					&& !tLDGrpDB.getSatrap().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "Satrap ='" + tLDGrpDB.getSatrap()
						+ "',";
			}
			if (tLDGrpDB.getEMail() != null
					&& !tLDGrpDB.getEMail().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "EMail ='" + tLDGrpDB.getEMail() + "',";
			}
			if (tLDGrpDB.getFoundDate() != null
					&& !tLDGrpDB.getFoundDate().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "FoundDate =" + tLDGrpDB.getFoundDate()
						+ ",";
			}
			if (tLDGrpDB.getBankCode() != null
					&& !tLDGrpDB.getBankCode().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "BankCode ='" + tLDGrpDB.getBankCode()
						+ "',";
			}
			if (tLDGrpDB.getBankAccNo() != null
					&& !tLDGrpDB.getBankAccNo().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "BankAccNo ='"
						+ tLDGrpDB.getBankAccNo() + "',";
			}
			if (tLDGrpDB.getGrpGroupNo() != null
					&& !tLDGrpDB.getGrpGroupNo().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "GrpGroupNo ="
						+ tLDGrpDB.getGrpGroupNo() + ",";
			}
			if (tLDGrpDB.getBlacklistFlag() != null
					&& !tLDGrpDB.getBlacklistFlag().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "BlacklistFlag ='"
						+ tLDGrpDB.getBlacklistFlag() + "',";
			}
			if (tLDGrpDB.getState() != null
					&& !tLDGrpDB.getState().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "State ='" + tLDGrpDB.getState() + "',";
			}
			if (tLDGrpDB.getRemark() != null
					&& !tLDGrpDB.getRemark().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "Remark ='" + tLDGrpDB.getRemark()
						+ "',";
			}
			if (tLDGrpDB.getVIPValue() != null
					&& !tLDGrpDB.getVIPValue().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "VIPValue ='" + tLDGrpDB.getVIPValue()
						+ "',";
			}
			if (tLDGrpDB.getSubCompanyFlag() != null
					&& !tLDGrpDB.getSubCompanyFlag().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "SubCompanyFlag ='"
						+ tLDGrpDB.getSubCompanyFlag() + "',";
			}
			if (tLDGrpDB.getSupCustoemrNo() != null
					&& !tLDGrpDB.getSupCustoemrNo().trim().equals("")) {
				tLDGrpSql = tLDGrpSql + "SupCustoemrNo ='"
						+ tLDGrpDB.getSupCustoemrNo() + "',";
			}
			tLDGrpSql = tLDGrpSql + "ModifyDate='" + tLDGrpDB.getModifyDate()
					+ "'," + "ModifyTime = '" + tLDGrpDB.getModifyTime() + "'"
					+ " where customerno = '" + tLDGrpDB.getCustomerNo() + "'";
			logger.debug("update ldgrp sql:" + tLDGrpSql);

			ExeSQL cExeSQL = new ExeSQL(conn);
			if (!cExeSQL.execUpdateSQL(tLDGrpSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDGrpBLS";
				tError.functionName = "submitData";
				tError.errorMessage = "团体客户信息更新失败！";
				this.mErrors.addOneError(tError);
				try {
					conn.rollback();
					conn.close();
				} catch (Exception e) {
				}
				return false;
			}
			// tLDGrpDB.executeQuery(tLDGrpSql) ;
			LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
			tLCGrpAddressDB.setSchema((LCGrpAddressSchema) mInputData
					.getObjectByObjectName("LCGrpAddressSchema", 0));

			if (tLCGrpAddressDB.getSchema().getAddressNo() != null
					&& !tLCGrpAddressDB.getSchema().getAddressNo().equals("")) {
				ExeSQL tExeSQL = new ExeSQL(conn);
				String tSql = "select count(*) from lcgrpaddress where customerno='"
						+ tLDGrpDB.getCustomerNo()
						+ "' and addressno='"
						+ tLCGrpAddressDB.getAddressNo() + "'";
				logger.debug("---tSql:" + tSql);
				String tCount = tExeSQL.getOneValue(tSql);
				if (tCount == null || tCount.equals("")) {
					tCount = "0";
				}
				logger.debug("---tcount:" + tCount);
				if (tCount.equals("0")) { // 新增地址信息
					// logger.debug("mmmmmmmmmmmmmmmmmmmmmmmmmm");
					tLCGrpAddressDB.setOperator(tLDGrpDB.getOperator());
					tLCGrpAddressDB.setMakeDate(PubFun.getCurrentDate());
					tLCGrpAddressDB.setMakeTime(PubFun.getCurrentTime());
					if (!tLCGrpAddressDB.insert()) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LDGrpBLS";
						tError.functionName = "submitData";
						tError.errorMessage = "团体客户信息更新失败！";
						this.mErrors.addOneError(tError);
						try {
							conn.rollback();
							conn.close();
						} catch (Exception e) {
						}
						return false;
					}
				} else {
					String tAddressSql = "update LCGrpAddress set ";
					if (tLCGrpAddressDB.getGrpAddress() != null
							&& !tLCGrpAddressDB.getGrpAddress().trim().equals(
									"")) {
						tAddressSql = tAddressSql + "GrpAddress = '"
								+ tLCGrpAddressDB.getGrpAddress() + "',";
					}
					if (tLCGrpAddressDB.getGrpZipCode() != null
							&& !tLCGrpAddressDB.getGrpZipCode().trim().equals(
									"")) {
						tAddressSql = tAddressSql + "GrpZipCode = '"
								+ tLCGrpAddressDB.getGrpZipCode() + "',";
					}
					if (tLCGrpAddressDB.getLinkMan1() != null
							&& !tLCGrpAddressDB.getLinkMan1().trim().equals("")) {
						tAddressSql = tAddressSql + "LinkMan1 = '"
								+ tLCGrpAddressDB.getLinkMan1() + "',";
					}
					if (tLCGrpAddressDB.getDepartment1() != null
							&& !tLCGrpAddressDB.getDepartment1().trim().equals(
									"")) {
						tAddressSql = tAddressSql + "Department1 = '"
								+ tLCGrpAddressDB.getDepartment1() + "',";
					}
					if (tLCGrpAddressDB.getHeadShip1() != null
							&& !tLCGrpAddressDB.getHeadShip1().trim()
									.equals("")) {
						tAddressSql = tAddressSql + "HeadShip1 = '"
								+ tLCGrpAddressDB.getHeadShip1() + "',";
					}
					if (tLCGrpAddressDB.getPhone1() != null
							&& !tLCGrpAddressDB.getPhone1().trim().equals("")) {
						tAddressSql = tAddressSql + "Phone1 = '"
								+ tLCGrpAddressDB.getPhone1() + "',";
					}
					if (tLCGrpAddressDB.getE_Mail1() != null
							&& !tLCGrpAddressDB.getE_Mail1().trim().equals("")) {
						tAddressSql = tAddressSql + "E_Mail1 = '"
								+ tLCGrpAddressDB.getE_Mail1() + "',";
					}
					if (tLCGrpAddressDB.getFax1() != null
							&& !tLCGrpAddressDB.getFax1().trim().equals("")) {
						tAddressSql = tAddressSql + "Fax1 = '"
								+ tLCGrpAddressDB.getFax1() + "',";
					}
					if (tLCGrpAddressDB.getLinkMan2() != null
							&& !tLCGrpAddressDB.getLinkMan2().trim().equals("")) {
						tAddressSql = tAddressSql + "LinkMan2 = '"
								+ tLCGrpAddressDB.getLinkMan2() + "',";
					}
					if (tLCGrpAddressDB.getDepartment2() != null
							&& !tLCGrpAddressDB.getDepartment2().trim().equals(
									"")) {
						tAddressSql = tAddressSql + "Department2 = '"
								+ tLCGrpAddressDB.getDepartment2() + "',";
					}
					if (tLCGrpAddressDB.getHeadShip2() != null
							&& !tLCGrpAddressDB.getHeadShip2().trim()
									.equals("")) {
						tAddressSql = tAddressSql + "HeadShip2 = '"
								+ tLCGrpAddressDB.getHeadShip2() + "',";
					}
					if (tLCGrpAddressDB.getPhone2() != null
							&& !tLCGrpAddressDB.getPhone2().trim().equals("")) {
						tAddressSql = tAddressSql + "Phone2 = '"
								+ tLCGrpAddressDB.getPhone2() + "',";
					}
					if (tLCGrpAddressDB.getE_Mail2() != null
							&& !tLCGrpAddressDB.getE_Mail2().trim().equals("")) {
						tAddressSql = tAddressSql + "E_Mail2 = '"
								+ tLCGrpAddressDB.getE_Mail2() + "',";
					}
					if (tLCGrpAddressDB.getFax2() != null
							&& !tLCGrpAddressDB.getFax2().trim().equals("")) {
						tAddressSql = tAddressSql + "Fax2 = '"
								+ tLCGrpAddressDB.getFax2() + "',";
					}
					tAddressSql = tAddressSql + "ModifyDate = '"
							+ tLCGrpAddressDB.getModifyDate() + "',"
							+ "ModifyTime = '"
							+ tLCGrpAddressDB.getModifyTime() + "'"
							+ " where customerno = '"
							+ tLCGrpAddressDB.getCustomerNo() + "'"
							+ " and addressno = '"
							+ tLCGrpAddressDB.getAddressNo() + "'";
					logger.debug("----update address sql:" + tAddressSql);
					// tLCGrpAddressDB.ex(tAddressSql);
					if (!tExeSQL.execUpdateSQL(tAddressSql)) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LDGrpBLS";
						tError.functionName = "submitData";
						tError.errorMessage = "团体客户信息更新失败！";
						this.mErrors.addOneError(tError);
						try {
							conn.rollback();
							conn.close();
						} catch (Exception e) {
						}
						return false;
					}
				}

			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception in BLS");
			logger.debug("Exception:" + ex.toString());
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
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

	private boolean delete() {
		boolean tReturn = true;
		logger.debug("Start Delete...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
			tError.functionName = "delete";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			/** 集体信息 */
			logger.debug("Start 集体信息...");
			LDGrpDB tLDGrpDB = new LDGrpDB(conn);
			tLDGrpDB.setSchema((LDGrpSchema) mInputData.getObjectByObjectName(
					"LDGrpSchema", 0));

			LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB(conn);
			tLCGrpAddressDB.setSchema((LCGrpAddressSchema) mInputData
					.getObjectByObjectName("LCGrpAddressSchema", 0));
			// logger.debug("---------LCGrpAddressDB:"+tLCGrpAddressDB.encode());
			if (tLCGrpAddressDB.getSchema().getAddressNo() != null
					&& !tLCGrpAddressDB.getSchema().getAddressNo().equals("")) {
				tLCGrpAddressDB.setCustomerNo(tLDGrpDB.getCustomerNo());
				tLCGrpAddressDB.delete();
			}
			tLDGrpDB.delete();
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception in BLS");
			logger.debug("Exception:" + ex.toString());
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
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

	// 查询纪录的公共方法
	public LDGrpSet queryData(VData cInputData) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		logger.debug("Start LDGrp BLS Query...");
		// 执行查询纪录活动
		logger.debug("Start Query...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
			tError.functionName = "queryData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return null;
		}

		try {
			/** 集体信息 */
			logger.debug("Start 集体信息...");
			LDGrpDB tLDGrpDB = new LDGrpDB(conn);
			tLDGrpDB.setSchema((LDGrpSchema) mInputData.getObjectByObjectName(
					"LDGrpSchema", 0));
			mLDGrpSet = (LDGrpSet) tLDGrpDB.query();
			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception in BLS");
			logger.debug("Exception:" + ex.toString());
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBLS";
			tError.functionName = "QueryData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			mLDGrpSet = null;
			try {
				conn.close();
			} catch (Exception e) {
			}
			return null;
		}
		mInputData = null;
		return mLDGrpSet;
	}

}
