/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPGeneralToRiskSchema;
import com.sinosoft.lis.vschema.LPGeneralToRiskSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPGeneralToRiskDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPGeneralToRiskDB extends LPGeneralToRiskSchema
{
private static Logger logger = Logger.getLogger(LPGeneralToRiskDB.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public LPGeneralToRiskDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPGeneralToRisk" );
		mflag = true;
	}

	public LPGeneralToRiskDB()
	{
		con = null;
		db = new DBOper( "LPGeneralToRisk" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPGeneralToRiskSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPGeneralToRiskSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM LPGeneralToRisk WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ? AND GrpPolNo = ? AND ExecuteCom = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getExecuteCom());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPGeneralToRisk");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPGeneralToRisk");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPGeneralToRisk SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , GrpPolNo = ? , GrpProposalNo = ? , PrtNo = ? , ExecuteCom = ? , AgentCom = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , SaleChnl = ? , ManageCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , CustomerNo = ? , AddressNo = ? , GrpName = ? , GetFlag = ? , BankCode = ? , BankAccNo = ? , AccName = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , RegetDate = ? , LastEdorDate = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , BonusRate = ? , MaxMedFee = ? , OutPayFlag = ? , EmployeeRate = ? , FamilyRate = ? , SpecFlag = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , PayMode = ? , ManageFeeRate = ? , PayIntv = ? , CValiDate = ? , Peoples2 = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , State = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , Remark = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ? AND GrpPolNo = ? AND ExecuteCom = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGrpProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtNo());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getExecuteCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentCom());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRiskVersion());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getManageCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCode1());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCustomerNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAddressNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getGrpName());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getGetFlag());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAccName());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getSSFlag());
			}
			pstmt.setDouble(31, this.getPeakLine());
			pstmt.setDouble(32, this.getGetLimit());
			pstmt.setDouble(33, this.getGetRate());
			pstmt.setDouble(34, this.getBonusRate());
			pstmt.setDouble(35, this.getMaxMedFee());
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getOutPayFlag());
			}
			pstmt.setDouble(37, this.getEmployeeRate());
			pstmt.setDouble(38, this.getFamilyRate());
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getSpecFlag());
			}
			pstmt.setInt(40, this.getExpPeoples());
			pstmt.setDouble(41, this.getExpPremium());
			pstmt.setDouble(42, this.getExpAmnt());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getPayMode());
			}
			pstmt.setDouble(44, this.getManageFeeRate());
			pstmt.setInt(45, this.getPayIntv());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(47, this.getPeoples2());
			pstmt.setDouble(48, this.getMult());
			pstmt.setDouble(49, this.getPrem());
			pstmt.setDouble(50, this.getAmnt());
			pstmt.setDouble(51, this.getSumPrem());
			pstmt.setDouble(52, this.getSumPay());
			pstmt.setDouble(53, this.getDif());
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getState());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(59, 1);
			} else {
				pstmt.setString(59, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(61, 91);
			} else {
				pstmt.setDate(61, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getAppFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getRemark());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(69, 91);
			} else {
				pstmt.setDate(69, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(70, 1);
			} else {
				pstmt.setString(70, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(72, 1);
			} else {
				pstmt.setString(72, this.getModifyTime());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getGrpPolNo());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getExecuteCom());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPGeneralToRisk");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPGeneralToRisk(EdorNo ,EdorType ,GrpContNo ,GrpPolNo ,GrpProposalNo ,PrtNo ,ExecuteCom ,AgentCom ,KindCode ,RiskCode ,RiskVersion ,SaleChnl ,ManageCom ,AgentType ,AgentCode ,AgentGroup ,AgentCode1 ,CustomerNo ,AddressNo ,GrpName ,GetFlag ,BankCode ,BankAccNo ,AccName ,FirstPayDate ,PayEndDate ,PaytoDate ,RegetDate ,LastEdorDate ,SSFlag ,PeakLine ,GetLimit ,GetRate ,BonusRate ,MaxMedFee ,OutPayFlag ,EmployeeRate ,FamilyRate ,SpecFlag ,ExpPeoples ,ExpPremium ,ExpAmnt ,PayMode ,ManageFeeRate ,PayIntv ,CValiDate ,Peoples2 ,Mult ,Prem ,Amnt ,SumPrem ,SumPay ,Dif ,State ,ApproveFlag ,ApproveCode ,ApproveDate ,ApproveTime ,UWFlag ,UWOperator ,UWDate ,UWTime ,AppFlag ,Remark ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGrpProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtNo());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getExecuteCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentCom());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRiskVersion());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getManageCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCode1());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCustomerNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAddressNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getGrpName());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getGetFlag());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAccName());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getSSFlag());
			}
			pstmt.setDouble(31, this.getPeakLine());
			pstmt.setDouble(32, this.getGetLimit());
			pstmt.setDouble(33, this.getGetRate());
			pstmt.setDouble(34, this.getBonusRate());
			pstmt.setDouble(35, this.getMaxMedFee());
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getOutPayFlag());
			}
			pstmt.setDouble(37, this.getEmployeeRate());
			pstmt.setDouble(38, this.getFamilyRate());
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getSpecFlag());
			}
			pstmt.setInt(40, this.getExpPeoples());
			pstmt.setDouble(41, this.getExpPremium());
			pstmt.setDouble(42, this.getExpAmnt());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getPayMode());
			}
			pstmt.setDouble(44, this.getManageFeeRate());
			pstmt.setInt(45, this.getPayIntv());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(47, this.getPeoples2());
			pstmt.setDouble(48, this.getMult());
			pstmt.setDouble(49, this.getPrem());
			pstmt.setDouble(50, this.getAmnt());
			pstmt.setDouble(51, this.getSumPrem());
			pstmt.setDouble(52, this.getSumPay());
			pstmt.setDouble(53, this.getDif());
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getState());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(59, 1);
			} else {
				pstmt.setString(59, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(61, 91);
			} else {
				pstmt.setDate(61, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getAppFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getRemark());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(69, 91);
			} else {
				pstmt.setDate(69, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(70, 1);
			} else {
				pstmt.setString(70, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(72, 1);
			} else {
				pstmt.setString(72, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM LPGeneralToRisk WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ? AND GrpPolNo = ? AND ExecuteCom = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getExecuteCom());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGeneralToRiskDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public LPGeneralToRiskSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPGeneralToRiskSet aLPGeneralToRiskSet = new LPGeneralToRiskSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPGeneralToRisk");
			LPGeneralToRiskSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LPGeneralToRiskSchema s1 = new LPGeneralToRiskSchema();
				s1.setSchema(rs,i);
				aLPGeneralToRiskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLPGeneralToRiskSet;

	}

	public LPGeneralToRiskSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPGeneralToRiskSet aLPGeneralToRiskSet = new LPGeneralToRiskSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LPGeneralToRiskSchema s1 = new LPGeneralToRiskSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGeneralToRiskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGeneralToRiskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLPGeneralToRiskSet;
	}

	public LPGeneralToRiskSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPGeneralToRiskSet aLPGeneralToRiskSet = new LPGeneralToRiskSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPGeneralToRisk");
			LPGeneralToRiskSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LPGeneralToRiskSchema s1 = new LPGeneralToRiskSchema();
				s1.setSchema(rs,i);
				aLPGeneralToRiskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLPGeneralToRiskSet;

	}

	public LPGeneralToRiskSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPGeneralToRiskSet aLPGeneralToRiskSet = new LPGeneralToRiskSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LPGeneralToRiskSchema s1 = new LPGeneralToRiskSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGeneralToRiskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGeneralToRiskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLPGeneralToRiskSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LPGeneralToRisk");
			LPGeneralToRiskSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPGeneralToRisk " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPGeneralToRiskDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return LPGeneralToRiskSet
 */
public LPGeneralToRiskSet getData()
{
    int tCount = 0;
    LPGeneralToRiskSet tLPGeneralToRiskSet = new LPGeneralToRiskSet();
    LPGeneralToRiskSchema tLPGeneralToRiskSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPGeneralToRiskSchema = new LPGeneralToRiskSchema();
        tLPGeneralToRiskSchema.setSchema(mResultSet, 1);
        tLPGeneralToRiskSet.add(tLPGeneralToRiskSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPGeneralToRiskSchema = new LPGeneralToRiskSchema();
                tLPGeneralToRiskSchema.setSchema(mResultSet, 1);
                tLPGeneralToRiskSet.add(tLPGeneralToRiskSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tLPGeneralToRiskSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LPGeneralToRiskDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "LPGeneralToRiskDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "LPGeneralToRiskDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPGeneralToRiskSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPGeneralToRiskSet aLPGeneralToRiskSet = new LPGeneralToRiskSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LPGeneralToRiskSchema s1 = new LPGeneralToRiskSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGeneralToRiskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGeneralToRiskSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLPGeneralToRiskSet;
	}

	public LPGeneralToRiskSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPGeneralToRiskSet aLPGeneralToRiskSet = new LPGeneralToRiskSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LPGeneralToRiskSchema s1 = new LPGeneralToRiskSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGeneralToRiskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGeneralToRiskSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGeneralToRiskDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLPGeneralToRiskSet; 
	}

}
