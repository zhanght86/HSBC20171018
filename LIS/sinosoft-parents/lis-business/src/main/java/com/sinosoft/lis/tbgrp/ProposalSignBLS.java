package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCAppntIndDB;
import com.sinosoft.lis.db.LCBankAuthDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCustomerImpartDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LCSpecNoteDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LJAPayGrpDB;
import com.sinosoft.lis.db.LockTableDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vdb.LCAppntGrpDBSet;
import com.sinosoft.lis.vdb.LCAppntIndDBSet;
import com.sinosoft.lis.vdb.LCBankAuthDBSet;
import com.sinosoft.lis.vdb.LCBnfDBSet;
import com.sinosoft.lis.vdb.LCCustomerImpartDBSet;
import com.sinosoft.lis.vdb.LCDutyDBSet;
import com.sinosoft.lis.vdb.LCGetDBSet;
import com.sinosoft.lis.vdb.LCGetToAccDBSet;
import com.sinosoft.lis.vdb.LCInsureAccDBSet;
import com.sinosoft.lis.vdb.LCInsureAccTraceDBSet;
import com.sinosoft.lis.vdb.LCInsuredDBSet;
import com.sinosoft.lis.vdb.LCPolDBSet;
import com.sinosoft.lis.vdb.LCPremDBSet;
import com.sinosoft.lis.vdb.LCPremToAccDBSet;
import com.sinosoft.lis.vdb.LCPrem_1DBSet;
import com.sinosoft.lis.vdb.LCSpecDBSet;
import com.sinosoft.lis.vdb.LCSpecNoteDBSet;
import com.sinosoft.lis.vdb.LCUWErrorDBSet;
import com.sinosoft.lis.vdb.LCUWMasterDBSet;
import com.sinosoft.lis.vdb.LCUWSubDBSet;
import com.sinosoft.lis.vdb.LJABonusGetDBSet;
import com.sinosoft.lis.vdb.LJAGetDrawDBSet;
import com.sinosoft.lis.vdb.LJAGetEndorseDBSet;
import com.sinosoft.lis.vdb.LJAGetOtherDBSet;
import com.sinosoft.lis.vdb.LJAPayDBSet;
import com.sinosoft.lis.vdb.LJAPayGrpDBSet;
import com.sinosoft.lis.vdb.LJAPayPersonDBSet;
import com.sinosoft.lis.vdb.LJTempFeeClassDBSet;
import com.sinosoft.lis.vdb.LJTempFeeDBSet;
import com.sinosoft.lis.vschema.LCAppntGrpSet;
import com.sinosoft.lis.vschema.LCAppntIndSet;
import com.sinosoft.lis.vschema.LCBankAuthSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LCPrem_1Set;
import com.sinosoft.lis.vschema.LCSpecNoteSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统签单功能部分
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author YT
 * @version 1.0
 */

public class ProposalSignBLS {
private static Logger logger = Logger.getLogger(ProposalSignBLS.class);
	/** 传输数据类 */
	private VData mInputData;
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public ProposalSignBLS() {
	}

	/**
	 * 传输数据的公共方法 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		if (this.saveData() == false)
			return false;

		mInputData = null;
		return true;
	}

	/**
	 * 保存数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean saveData() {
		int n = mInputData.size();
		for (int i = 0; i < n; i++) {
			VData tPol = (VData) mInputData.getObject(i);
			if (this.saveOnePol(tPol) == false)
				return false;
		}
		return true;
	}

	/**
	 * 签发一张投保单（包含事务） 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	public boolean saveOnePol(VData tPol) {
		LCPolSchema tLCPolSchema = (LCPolSchema) tPol.getObjectByObjectName(
				"LCPolSchema", 0);

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
			// 签发一张投保单
			if (this.signOnePol(tPol, conn) == false) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "此投保单签发失败！（投保单号为："
						+ tLCPolSchema.getProposalNo() + "）";
				this.mErrors.addOneError(tError);
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
			tError.moduleName = "ProposalSignBLS";
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
	 * 签发一张投保单（没有事务） 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	public boolean signOnePol(VData tPol, Connection conn) {

		LCPolSchema tLCPolSchema = (LCPolSchema) tPol.getObjectByObjectName(
				"LCPolSchema", 0);
		// 锁表
		if (LockTable(tLCPolSchema.getProposalNo(), conn, "TS") == false) {
			return false;
		}
		// 删除数据
		if (this.deletePol(tPol, conn) == false)
			return false;

		// 插入数据
		if (this.insertPol(tPol, conn) == false)
			return false;

		TransferData tBQData = (TransferData) tPol.getObjectByObjectName(
				"TransferData", 0);
		if (tBQData != null) {
			String inEdorType = (String) tBQData.getValueByName("EdorType");
			// 插入数据
			if (this.insertPolBQ(tPol, conn, inEdorType, tBQData) == false)
				return false;
		} else {
			if (insertFinance(tPol, conn) == false)
				return false;
		}

		return true;
	}

	/**
	 * 保存与一张保单相关的数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean insertPol(VData tPol, Connection conn) {
		// 投保单主表
		LCPolSchema tLCPolSchema = (LCPolSchema) tPol.getObjectByObjectName(
				"LCPolSchema", 0);
		LCPolDB tLCPolDB = new LCPolDB(conn);
		tLCPolDB.setSchema(tLCPolSchema);
		if (tLCPolDB.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCPol表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 投保单主表（关联的附加险部分）
		LCPolSet tLCPolSet = (LCPolSet) tPol.getObjectByObjectName("LCPolSet",
				0);
		LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
		tLCPolDBSet.set(tLCPolSet);
		if (tLCPolDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCPol表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("责任");
		// 责任
		LCDutySet tLCDutySet = (LCDutySet) tPol.getObjectByObjectName(
				"LCDutySet", 0);
		LCDutyDBSet tLCDutyDBSet = new LCDutyDBSet(conn);
		tLCDutyDBSet.set(tLCDutySet);
		if (tLCDutyDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCDuty表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 保费项
		LCPremSet tLCPremSet = (LCPremSet) tPol.getObjectByObjectName(
				"LCPremSet", 0);
		LCPremDBSet tLCPremDBSet = new LCPremDBSet(conn);
		tLCPremDBSet.set(tLCPremSet);
		if (tLCPremDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCPrem表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 领取项
		LCGetSet tLCGetSet = (LCGetSet) tPol.getObjectByObjectName("LCGetSet",
				0);
		LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
		tLCGetDBSet.set(tLCGetSet);
		if (tLCGetDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCGet表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保主表
		LCUWMasterSet tLCUWMasterSet = (LCUWMasterSet) tPol
				.getObjectByObjectName("LCUWMasterSet", 0);
		LCUWMasterDBSet tLCUWMasterDBSet = new LCUWMasterDBSet(conn);
		tLCUWMasterDBSet.set(tLCUWMasterSet);
		if (tLCUWMasterDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "LCUWMaster表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保子表
		LCUWSubSet tLCUWSubSet = (LCUWSubSet) tPol.getObjectByObjectName(
				"LCUWSubSet", 0);
		LCUWSubDBSet tLCUWSubDBSet = new LCUWSubDBSet(conn);
		tLCUWSubDBSet.set(tLCUWSubSet);
		if (tLCUWSubDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCUWSub表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保错误信息表
		LCUWErrorSet tLCUWErrorSet = (LCUWErrorSet) tPol.getObjectByObjectName(
				"LCUWErrorSet", 0);
		LCUWErrorDBSet tLCUWErrorDBSet = new LCUWErrorDBSet(conn);
		tLCUWErrorDBSet.set(tLCUWErrorSet);
		if (tLCUWErrorDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWErrorDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCUWError表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 特别约定
		LCSpecSet tLCSpecSet = (LCSpecSet) tPol.getObjectByObjectName(
				"LCSpecSet", 0);
		LCSpecDBSet tLCSpecDBSet = new LCSpecDBSet(conn);
		tLCSpecDBSet.set(tLCSpecSet);
		if (tLCSpecDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCSpec表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 特别约定注释
		LCSpecNoteSet tLCSpecNoteSet = (LCSpecNoteSet) tPol
				.getObjectByObjectName("LCSpecNoteSet", 0);
		LCSpecNoteDBSet tLCSpecNoteDBSet = new LCSpecNoteDBSet(conn);
		tLCSpecNoteDBSet.set(tLCSpecNoteSet);
		if (tLCSpecNoteDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecNoteDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCSpecNote表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 告知信息
		LCCustomerImpartSet tLCCustomerImpartSet = (LCCustomerImpartSet) tPol
				.getObjectByObjectName("LCCustomerImpartSet", 0);
		LCCustomerImpartDBSet tLCCustomerImpartDBSet = new LCCustomerImpartDBSet(
				conn);
		tLCCustomerImpartDBSet.set(tLCCustomerImpartSet);
		if (tLCCustomerImpartDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCustomerImpartDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCCustomerImpart表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 个人投保人
		LCAppntIndSet tLCAppntIndSet = (LCAppntIndSet) tPol
				.getObjectByObjectName("LCAppntIndSet", 0);
		LCAppntIndDBSet tLCAppntIndDBSet = new LCAppntIndDBSet(conn);
		tLCAppntIndDBSet.set(tLCAppntIndSet);
		if (tLCAppntIndDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCAppntIndDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCAppntInd表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 集体投保人
		LCAppntGrpSet tLCAppntGrpSet = (LCAppntGrpSet) tPol
				.getObjectByObjectName("LCAppntGrpSet", 0);
		LCAppntGrpDBSet tLCAppntGrpDBSet = new LCAppntGrpDBSet(conn);
		tLCAppntGrpDBSet.set(tLCAppntGrpSet);
		if (tLCAppntGrpDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCAppntGrpDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCAppntGrp表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 被保人
		LCInsuredSet tLCInsuredSet = (LCInsuredSet) tPol.getObjectByObjectName(
				"LCInsuredSet", 0);
		LCInsuredDBSet tLCInsuredDBSet = new LCInsuredDBSet(conn);
		tLCInsuredDBSet.set(tLCInsuredSet);
		if (tLCInsuredDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCInsuredDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCInsured表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 受益人
		LCBnfSet tLCBnfSet = (LCBnfSet) tPol.getObjectByObjectName("LCBnfSet",
				0);
		LCBnfDBSet tLCBnfDBSet = new LCBnfDBSet(conn);
		tLCBnfDBSet.set(tLCBnfSet);
		if (tLCBnfDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBnfDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCBnf表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 银行授权书表
		LCBankAuthSet tLCBankAuthSet = (LCBankAuthSet) tPol
				.getObjectByObjectName("LCBankAuthSet", 0);
		LCBankAuthDBSet tLCBankAuthDBSet = new LCBankAuthDBSet(conn);
		tLCBankAuthDBSet.set(tLCBankAuthSet);
		if (tLCBankAuthDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBankAuthDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LCBankAuth表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCInsureAccSet tLCInsureAccSet = ((LCInsureAccSet) (tPol
				.getObjectByObjectName("LCInsureAccSet", 0)));
		if (tLCInsureAccSet != null) {
			LCInsureAccDBSet tLCInsureAccDBSet = new LCInsureAccDBSet(conn);
			tLCInsureAccDBSet.set(tLCInsureAccSet);
			if (tLCInsureAccDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCInsureAcc表保存失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		LCPremToAccSet tLCPremToAccSet = ((LCPremToAccSet) (tPol
				.getObjectByObjectName("LCPremToAccSet", 0)));
		if (tLCPremToAccSet != null) {
			LCPremToAccDBSet tLCPremToAccDBSet = new LCPremToAccDBSet(conn);
			tLCPremToAccDBSet.set(tLCPremToAccSet);
			if (tLCPremToAccDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPremToAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCPremToAcc表保存失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		LCGetToAccSet tLCGetToAccSet = ((LCGetToAccSet) (tPol
				.getObjectByObjectName("LCGetToAccSet", 0)));
		if (tLCGetToAccSet != null) {
			LCGetToAccDBSet tLCGetToAccDBSet = new LCGetToAccDBSet(conn);
			tLCGetToAccDBSet.set(tLCGetToAccSet);
			if (tLCGetToAccDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGetToAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCGetToAcc表保存失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		LCInsureAccTraceSet tLCInsureAccTraceSet = ((LCInsureAccTraceSet) (tPol
				.getObjectByObjectName("LCInsureAccTraceSet", 0)));
		if (tLCInsureAccTraceSet != null) {
			LCInsureAccTraceDBSet tLCInsureAccTraceDBSet = new LCInsureAccTraceDBSet(
					conn);
			tLCInsureAccTraceDBSet.set(tLCInsureAccTraceSet);
			if (tLCInsureAccTraceDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCInsureAccTraceDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCInsureAccTrace表保存失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 处理财务收费的部分，因为正常流程和保全有冲突，所以要单独提取出来
	 * 
	 * @param tPol
	 * @param conn
	 * @return
	 */
	private boolean insertFinance(VData tPol, Connection conn) {
		// 插入暂交费和子表时，要先删除，再插入，因为在deletePol()函数中，只是更新暂交费，所以可能该暂交费还存在
		// 暂交费
		LJTempFeeSet tLJTempFeeSet = (LJTempFeeSet) tPol.getObjectByObjectName(
				"LJTempFeeSet", 0);
		LJTempFeeDBSet tLJTempFeeDBSet = new LJTempFeeDBSet(conn);
		tLJTempFeeDBSet.set(tLJTempFeeSet);
		if (tLJTempFeeDBSet.delete() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LJTempFee表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLJTempFeeDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LJTempFee表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 暂交费子表
		LJTempFeeClassSet tLJTempFeeClassSet = (LJTempFeeClassSet) tPol
				.getObjectByObjectName("LJTempFeeClassSet", 0);
		LJTempFeeClassDBSet tLJTempFeeClassDBSet = new LJTempFeeClassDBSet(conn);
		tLJTempFeeClassDBSet.set(tLJTempFeeClassSet);
		if (tLJTempFeeClassDBSet.delete() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeClassDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LJTempFeeClass表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLJTempFeeClassDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeClassDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LJTempFeeClass表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		} // end of if

		// 实收总表
		VData tAPay = (VData) tPol.getObjectByObjectName("VData", 0);
		LJAPaySet tLJAPaySet = (LJAPaySet) tAPay.getObjectByObjectName(
				"LJAPaySet", 0);
		String tAction = (String) tAPay.get(0);
		LJAPayDBSet tLJAPayDBSet = new LJAPayDBSet(conn);
		tLJAPayDBSet.set(tLJAPaySet);
		if (tAction.equals("INSERT")) {
			if (tLJAPayDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAPay表保存失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		if (tAction.equals("UPDATE")) {
			if (tLJAPayDBSet.update() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAPay表保存失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		// 实收个人表
		LJAPayPersonSet tLJAPayPersonSet = (LJAPayPersonSet) tPol
				.getObjectByObjectName("LJAPayPersonSet", 0);
		LJAPayPersonDBSet tLJAPayPersonDBSet = new LJAPayPersonDBSet(conn);
		tLJAPayPersonDBSet.set(tLJAPayPersonSet);
		if (tLJAPayPersonDBSet.insert() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "savePol";
			tError.errorMessage = "LJAPayPerson表保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 保存与一张保单相关的数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean insertPolBQ(VData tPol, Connection conn, String EType,
			TransferData BQData) {
		// 如果是保全的集体下新单加人
		if (EType.equals("NI")) {
			// 批改补退实收表
			LJAGetEndorseSet tLJAGetEndorseSet = (LJAGetEndorseSet) tPol
					.getObjectByObjectName("LJAGetEndorseSet", 0);
			LJAGetEndorseDBSet tLJAGetEndorseDBSet = new LJAGetEndorseDBSet(
					conn);
			tLJAGetEndorseDBSet.set(tLJAGetEndorseSet);
			if (tLJAGetEndorseDBSet.delete() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAGetEndorseDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAGetEndorse表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 实收个人表--在正常流程中已经添加
			LJAPayPersonSet tLJAPayPersonSet = (LJAPayPersonSet) tPol
					.getObjectByObjectName("LJAPayPersonSet", 0);
			LJAPayPersonDBSet tLJAPayPersonDBSet = new LJAPayPersonDBSet(conn);
			tLJAPayPersonDBSet.set(tLJAPayPersonSet);
			if (tLJAPayPersonDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAPayPerson表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 实收集体表
			LJAPayGrpSet tLJAPayGrpSet = (LJAPayGrpSet) tPol
					.getObjectByObjectName("LJAPayGrpSet", 0);
			LJAPayGrpDBSet tLJAPayGrpDBSet = new LJAPayGrpDBSet(conn);
			LJAPayGrpSet tempLJAPayGrpSet = new LJAPayGrpSet();
			// 察看是否数据库中已经有该数据，跳过
			for (int i = 1; i <= tLJAPayGrpSet.size(); i++) {
				LJAPayGrpDB tLJAPayGrpDB = new LJAPayGrpDB();
				tLJAPayGrpDB.setGrpPolNo(tLJAPayGrpSet.get(i).getGrpPolNo());
				tLJAPayGrpDB.setPayNo(tLJAPayGrpSet.get(i).getPayNo());
				tLJAPayGrpDB.setPayType(tLJAPayGrpSet.get(i).getPayType());
				if (tLJAPayGrpDB.getInfo() == false) {
					tempLJAPayGrpSet.add(tLJAPayGrpSet.get(i));
				}

			}

			tLJAPayGrpDBSet.set(tempLJAPayGrpSet);
			if (tLJAPayGrpDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayGrpDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAPayGrp表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			double sumPay = 0;
			for (int i = 1; i <= tLJAPayPersonSet.size(); i++) {
				sumPay = sumPay + tLJAPayPersonSet.get(i).getSumActuPayMoney();
			}

			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolDB.setGrpPolNo(tLJAPayGrpSet.get(1).getGrpPolNo());
			if (tLCGrpPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "集体保单表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCGrpPolSchema = tLCGrpPolDB.getSchema();
			tLCGrpPolSchema.setSumPrem(sumPay + tLCGrpPolSchema.getSumPrem());
			tLCGrpPolSchema.setSumPay(sumPay + tLCGrpPolSchema.getSumPay());
			tLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setSchema(tLCGrpPolSchema);
			if (tLCGrpPolDB.update() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "集体保单表更新失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			return true;
		}

		// 如果是保全的新增附加险
		if (EType.equals("NS")) {
			// 批改补退实收表
			LJAGetEndorseSet tLJAGetEndorseSet = (LJAGetEndorseSet) tPol
					.getObjectByObjectName("LJAGetEndorseSet", 0);
			LJAGetEndorseDBSet tLJAGetEndorseDBSet = new LJAGetEndorseDBSet(
					conn);
			tLJAGetEndorseDBSet.set(tLJAGetEndorseSet);
			if (tLJAGetEndorseDBSet.delete() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAGetEndorseDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAGetEndorse表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 实收个人表--在正常流程中已经添加
			LJAPayPersonSet tLJAPayPersonSet = (LJAPayPersonSet) tPol
					.getObjectByObjectName("LJAPayPersonSet", 0);
			LJAPayPersonDBSet tLJAPayPersonDBSet = new LJAPayPersonDBSet(conn);
			tLJAPayPersonDBSet.set(tLJAPayPersonSet);
			if (tLJAPayPersonDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAPayPerson表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCPolSet tLCPolSet = (LCPolSet) BQData.getValueByName("UPLCPol");
			if (tLCPolSet != null) {
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					tLCPolSet.get(i).setRnewFlag("-2");
					tLCPolSet.get(i).setModifyDate(PubFun.getCurrentDate());
					tLCPolSet.get(i).setModifyTime(PubFun.getCurrentTime());
				}
				LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
				tLCPolDBSet.set(tLCPolSet);
				tLCPolDBSet.update();
			}
			return true;
		}

		// 如果是集体下无名单加人
		if (EType.equals("GANAME")) {
			TransferData tBQData = (TransferData) tPol.getObjectByObjectName(
					"TransferData", 0);
			VData tVData = (VData) tBQData.getValueByName("GANAME");
			// 更新
			LCPrem_1Set tLCPrem_1Set = (LCPrem_1Set) tVData
					.getObjectByObjectName("LCPrem_1Set", 0);
			LCPrem_1DBSet tLCPrem_1DBSet = new LCPrem_1DBSet(conn);
			tLCPrem_1DBSet.set(tLCPrem_1Set);
			if (tLCPrem_1DBSet.update() == false) {
				this.mErrors.copyAllErrors(tLCPrem_1DBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCPrem_1表更新失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCGetSet tLCGetSet = (LCGetSet) tVData.getObjectByObjectName(
					"LCGetSet", 0);
			LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
			tLCGetDBSet.set(tLCGetSet);
			if (tLCGetDBSet.update() == false) {
				this.mErrors.copyAllErrors(tLCGetDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCGet表更新失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 先删除再插入
			LCPolSet tLCPolSet = (LCPolSet) tVData.getObjectByObjectName(
					"LCPolSet", 0);
			LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
			tLCPolDBSet.set(tLCPolSet);
			if (tLCPolDBSet.delete() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCPol表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLCPolDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCPol表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCDutySet tLCDutySet = (LCDutySet) tVData.getObjectByObjectName(
					"LCDutySet", 0);
			LCDutyDBSet tLCDutyDBSet = new LCDutyDBSet(conn);
			tLCDutyDBSet.set(tLCDutySet);
			if (tLCDutyDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCDuty表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLCDutyDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCDuty表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCPremSet tLCPremSet = (LCPremSet) tVData.getObjectByObjectName(
					"LCPremSet", 0);
			LCPremDBSet tLCPremDBSet = new LCPremDBSet(conn);
			tLCPremDBSet.set(tLCPremSet);
			if (tLCPremDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLCPremDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCDuty表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLCPremDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLCPremDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCDuty表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) tVData
					.getObjectByObjectName("LCInsureAccSet", 0);
			LCInsureAccDBSet tLCInsureAccDBSet = new LCInsureAccDBSet(conn);
			tLCInsureAccDBSet.set(tLCInsureAccSet);
			if (tLCInsureAccDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCInsureAcc表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLCInsureAccDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCInsureAcc表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LCInsureAccTraceSet tLCInsureAccTraceSet = (LCInsureAccTraceSet) tVData
					.getObjectByObjectName("LCInsureAccTraceSet", 0);
			LCInsureAccTraceDBSet tLCInsureAccTraceDBSet = new LCInsureAccTraceDBSet(
					conn);
			tLCInsureAccTraceDBSet.set(tLCInsureAccTraceSet);
			if (tLCInsureAccTraceDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLCInsureAccTraceDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCInsureAccTrace表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLCInsureAccTraceDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLCInsureAccTraceDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LCInsureAccTrace表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LJAPayPersonSet tLJAPayPersonSet = (LJAPayPersonSet) tVData
					.getObjectByObjectName("LJAPayPersonSet", 0);
			LJAPayPersonDBSet tLJAPayPersonDBSet = new LJAPayPersonDBSet(conn);
			tLJAPayPersonDBSet.set(tLJAPayPersonSet);
			if (tLJAPayPersonDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LLJAPayPerson表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLJAPayPersonDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAPayPerson表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LJAGetDrawSet tLJAGetDrawSet = (LJAGetDrawSet) tVData
					.getObjectByObjectName("LJAGetDrawSet", 0);
			LJAGetDrawDBSet tLJAGetDrawDBSet = new LJAGetDrawDBSet(conn);
			tLJAGetDrawDBSet.set(tLJAGetDrawSet);
			if (tLJAGetDrawDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLJAGetDrawDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LLJAGetDraw表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLJAGetDrawDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLJAGetDrawDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAGetDraw表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LJAGetOtherSet tLJAGetOtherSet = (LJAGetOtherSet) tVData
					.getObjectByObjectName("LJAGetOtherSet", 0);
			LJAGetOtherDBSet tLJAGetOtherDBSet = new LJAGetOtherDBSet(conn);
			tLJAGetOtherDBSet.set(tLJAGetOtherSet);
			if (tLJAGetOtherDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLJAGetOtherDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LLJAGetOther表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLJAGetOtherDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLJAGetOtherDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJAGetOther表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LJABonusGetSet tLJABonusGetSet = (LJABonusGetSet) tVData
					.getObjectByObjectName("LJABonusGetSet", 0);
			LJABonusGetDBSet tLJABonusGetDBSet = new LJABonusGetDBSet(conn);
			tLJABonusGetDBSet.set(tLJABonusGetSet);
			if (tLJABonusGetDBSet.delete() == false) {
				this.mErrors.copyAllErrors(tLJABonusGetDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LLJABonusGet表删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tLJABonusGetDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLJABonusGetDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalSignBLS";
				tError.functionName = "savePol";
				tError.errorMessage = "LJABonusGet表插入失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 删除与一张保单相关的数据 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean deletePol(VData tPol, Connection conn) {
		LCPolSchema tLCPolSchema = (LCPolSchema) tPol.getObjectByObjectName(
				"LCPolSchema", 0);
		String tOldPolNo = tLCPolSchema.getProposalNo();

		// 保费项
		LCPremDB tLCPremDB = new LCPremDB(conn);
		tLCPremDB.setPolNo(tOldPolNo);
		if (tLCPremDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCPrem表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 领取项
		LCGetDB tLCGetDB = new LCGetDB(conn);
		tLCGetDB.setPolNo(tOldPolNo);
		if (tLCGetDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCGet表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 责任
		LCDutyDB tLCDutyDB = new LCDutyDB(conn);
		tLCDutyDB.setPolNo(tOldPolNo);
		if (tLCDutyDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCDuty表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保错误信息
		LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB(conn);
		tLCUWErrorDB.setPolNo(tOldPolNo);
		if (tLCUWErrorDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWErrorDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCUWError表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保子表
		LCUWSubDB tLCUWSubDB = new LCUWSubDB(conn);
		tLCUWSubDB.setPolNo(tOldPolNo);
		if (tLCUWSubDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCUWSub表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保主表
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB(conn);
		tLCUWMasterDB.setPolNo(tOldPolNo);
		if (tLCUWMasterDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCUWMaster表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 特别约定注释
		LCSpecNoteDB tLCSpecNoteDB = new LCSpecNoteDB(conn);
		tLCSpecNoteDB.setPolNo(tOldPolNo);
		if (tLCSpecNoteDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecNoteDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCSpecNote表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 特别约定
		LCSpecDB tLCSpecDB = new LCSpecDB(conn);
		tLCSpecDB.setPolNo(tOldPolNo);
		if (tLCSpecDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCSpecDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCSpec表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 告知
		LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB(conn);
		/*
		 * Lis5.3 upgrade set tLCCustomerImpartDB.setPolNo( tOldPolNo );
		 */
		if (tLCCustomerImpartDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCustomerImpartDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCCustomerImpart表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 投保人
		LCAppntIndDB tLCAppntIndDB = new LCAppntIndDB(conn);
		tLCAppntIndDB.setPolNo(tOldPolNo);
		if (tLCAppntIndDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCAppntIndDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCAppntInd表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 被保人
		LCInsuredDB tLCInsuredDB = new LCInsuredDB(conn);
		/*
		 * Lis5.3 upgrade set tLCInsuredDB.setPolNo( tOldPolNo );
		 */
		if (tLCInsuredDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCInsuredDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCInsured表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 受益人
		LCBnfDB tLCBnfDB = new LCBnfDB(conn);
		tLCBnfDB.setPolNo(tOldPolNo);
		if (tLCBnfDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBnfDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCBnf表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 银行授权书
		LCBankAuthDB tLCBankAuthDB = new LCBankAuthDB(conn);
		tLCBankAuthDB.setPolNo(tOldPolNo);
		if (tLCBankAuthDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBankAuthDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCBankAuth表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 投保单
		LCPolDB tLCPolDB = new LCPolDB(conn);
		tLCPolDB.setPolNo(tOldPolNo);
		if (tLCPolDB.deleteSQL() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCPol表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		/*
		 * // 更新暂交费表信息--即先删除后插入 LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB( conn );
		 * tLJTempFeeDB.setOtherNo( tOldPolNo ); //tLJTempFeeDB.setOtherNoType(
		 * "0" );//不止一种情况： //tLJTempFeeDB.setTempFeeType( "1" );//不止一种情况：
		 * tLJTempFeeDB.setConfFlag( "0" );
		 * 
		 * LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query(); int tempFeeCount =
		 * tLJTempFeeSet.size(); for( int iTempFee = 1; iTempFee <=
		 * tempFeeCount; iTempFee++ ) { LJTempFeeSchema tLJTempFeeSchema =
		 * tLJTempFeeSet.get( iTempFee ); LJTempFeeDB tLJTempFeeDB1 = new
		 * LJTempFeeDB( conn ); tLJTempFeeDB1.setSchema(tLJTempFeeSchema); ; if
		 * (tLJTempFeeDB1.deleteSQL() == false) { // @@错误处理
		 * this.mErrors.copyAllErrors(tLJTempFeeDB1.mErrors); CError tError =
		 * new CError(); tError.moduleName = "ProposalSignBLS";
		 * tError.functionName = "deletePol"; tError.errorMessage =
		 * "LJTempFee表删除失败!"; this.mErrors .addOneError(tError) ; return false; }
		 * tLJTempFeeSchema.setOtherNo(""); tLJTempFeeSchema.setOtherNoType("");
		 * tLJTempFeeDB1.setSchema(tLJTempFeeSchema); ; if
		 * (tLJTempFeeDB1.insert() == false) { // @@错误处理
		 * this.mErrors.copyAllErrors(tLJTempFeeDB1.mErrors); CError tError =
		 * new CError(); tError.moduleName = "ProposalSignBLS";
		 * tError.functionName = "deletePol"; tError.errorMessage =
		 * "LJTempFee表插入失败!"; this.mErrors .addOneError(tError) ; return false; } } //
		 * end of for
		 */
		// 关联的附险的投保单
		LCPolSet tLCPolSet = (LCPolSet) tPol.getObjectByObjectName("LCPolSet",
				0);
		LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
		tLCPolDBSet.set(tLCPolSet);
		if (tLCPolDBSet.delete() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "deletePol";
			tError.errorMessage = "LCPol表删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 添加一条纪录，如果添加失败，认为已经有记录
	 * 
	 * @param PolNo
	 * @param Operator
	 * @param PolState
	 * @param Operator2
	 * @return
	 */
	public boolean LockTable(String PolNo, Connection conn, String strOper) {
		try {
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "delete from locktable where sysdate-makedate>7";
			tExeSQL.execUpdateSQL(sql);
		} catch (Exception ex) {

		}

		LockTableDB tLockTableDB = new LockTableDB(conn);
		tLockTableDB.setNoLimit(PolNo);
		tLockTableDB.setNoType(strOper); // 即TB_Sign
		tLockTableDB.setMakeDate(PubFun.getCurrentDate());
		tLockTableDB.setMakeTime(PubFun.getCurrentTime());
		if (tLockTableDB.insert() == false) {
			logger.debug("Lock failed :return false;");
			CError tError = new CError();
			tError.moduleName = "ProposalSignBLS";
			tError.functionName = "LockTable";
			tError.errorMessage = "其他操作员正在签发该投保单（PolNo）,您的签单动作无效！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
