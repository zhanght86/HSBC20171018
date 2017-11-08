/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCGrpPolBakSchema;
import com.sinosoft.lis.vschema.LCGrpPolBakSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpPolBakDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCGrpPolBakDB extends LCGrpPolBakSchema
{
private static Logger logger = Logger.getLogger(LCGrpPolBakDB.class);

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
	public LCGrpPolBakDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCGrpPolBak" );
		mflag = true;
	}

	public LCGrpPolBakDB()
	{
		con = null;
		db = new DBOper( "LCGrpPolBak" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCGrpPolBakSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCGrpPolBakSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCGrpPolBak WHERE  GrpPolNo = ?");
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(1, 1);
			} else {
				pstmt.setString(1, StrTool.space1(this.getGrpPolNo(), 20));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPolBak");
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
		SQLString sqlObj = new SQLString("LCGrpPolBak");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCGrpPolBak SET  GrpPolNo = ? , ContNo = ? , GrpProposalNo = ? , PrtNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , SignCom = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , SaleChnl = ? , Password = ? , GrpNo = ? , Password2 = ? , GrpName = ? , GrpAddressCode = ? , GrpAddress = ? , GrpZipCode = ? , BusinessType = ? , GrpNature = ? , Peoples2 = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , LinkMan1 = ? , Department1 = ? , HeadShip1 = ? , Phone1 = ? , E_Mail1 = ? , Fax1 = ? , LinkMan2 = ? , Department2 = ? , HeadShip2 = ? , Phone2 = ? , E_Mail2 = ? , Fax2 = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , BankAccNo = ? , BankCode = ? , GrpGroupNo = ? , PayIntv = ? , PayMode = ? , CValiDate = ? , GetPolDate = ? , SignDate = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , RegetDate = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , MaxMedFee = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , DisputedFlag = ? , BonusRate = ? , Lang = ? , Currency = ? , State = ? , LostTimes = ? , AppFlag = ? , ApproveCode = ? , ApproveDate = ? , UWOperator = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , Remark = ? , UWFlag = ? , OutPayFlag = ? , ApproveFlag = ? , EmployeeRate = ? , FamilyRate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , AccName = ? , PrintCount = ? , LastEdorDate = ? , ManageFeeRate = ? , GrpSpec = ? , GetPolMode = ? , PolApplyDate = ? , CustomGetPolDate = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , SpecFlag = ? WHERE  GrpPolNo = ?");
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(1, 1);
			} else {
				pstmt.setString(1, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getGrpProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getPrtNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getRiskVersion());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getSignCom());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getAgentType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getSaleChnl());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getPassword());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getGrpNo());
			}
			if(this.getPassword2() == null || this.getPassword2().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getPassword2());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getGrpName());
			}
			if(this.getGrpAddressCode() == null || this.getGrpAddressCode().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getGrpAddressCode());
			}
			if(this.getGrpAddress() == null || this.getGrpAddress().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpAddress());
			}
			if(this.getGrpZipCode() == null || this.getGrpZipCode().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getGrpZipCode());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getGrpNature());
			}
			pstmt.setInt(22, this.getPeoples2());
			pstmt.setDouble(23, this.getRgtMoney());
			pstmt.setDouble(24, this.getAsset());
			pstmt.setDouble(25, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getComAera());
			}
			if(this.getLinkMan1() == null || this.getLinkMan1().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getLinkMan1());
			}
			if(this.getDepartment1() == null || this.getDepartment1().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getDepartment1());
			}
			if(this.getHeadShip1() == null || this.getHeadShip1().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getHeadShip1());
			}
			if(this.getPhone1() == null || this.getPhone1().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getPhone1());
			}
			if(this.getE_Mail1() == null || this.getE_Mail1().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getE_Mail1());
			}
			if(this.getFax1() == null || this.getFax1().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getFax1());
			}
			if(this.getLinkMan2() == null || this.getLinkMan2().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getLinkMan2());
			}
			if(this.getDepartment2() == null || this.getDepartment2().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getDepartment2());
			}
			if(this.getHeadShip2() == null || this.getHeadShip2().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getHeadShip2());
			}
			if(this.getPhone2() == null || this.getPhone2().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getPhone2());
			}
			if(this.getE_Mail2() == null || this.getE_Mail2().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getE_Mail2());
			}
			if(this.getFax2() == null || this.getFax2().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getFax2());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(45, 1);
			} else {
				pstmt.setString(45, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getBankAccNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getBankCode());
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getGrpGroupNo());
			}
			pstmt.setInt(50, this.getPayIntv());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getPayMode());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getCValiDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(54, 91);
			} else {
				pstmt.setDate(54, Date.valueOf(this.getSignDate()));
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(55, 91);
			} else {
				pstmt.setDate(55, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(56, 91);
			} else {
				pstmt.setDate(56, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getRegetDate()));
			}
			pstmt.setInt(59, this.getPeoples());
			pstmt.setDouble(60, this.getMult());
			pstmt.setDouble(61, this.getPrem());
			pstmt.setDouble(62, this.getAmnt());
			pstmt.setDouble(63, this.getSumPrem());
			pstmt.setDouble(64, this.getSumPay());
			pstmt.setDouble(65, this.getDif());
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getSSFlag());
			}
			pstmt.setDouble(67, this.getPeakLine());
			pstmt.setDouble(68, this.getGetLimit());
			pstmt.setDouble(69, this.getGetRate());
			pstmt.setDouble(70, this.getMaxMedFee());
			pstmt.setInt(71, this.getExpPeoples());
			pstmt.setDouble(72, this.getExpPremium());
			pstmt.setDouble(73, this.getExpAmnt());
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(74, 1);
			} else {
				pstmt.setString(74, this.getDisputedFlag());
			}
			pstmt.setDouble(75, this.getBonusRate());
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(76, 1);
			} else {
				pstmt.setString(76, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(77, 1);
			} else {
				pstmt.setString(77, this.getCurrency());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getState());
			}
			pstmt.setInt(79, this.getLostTimes());
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(80, 1);
			} else {
				pstmt.setString(80, this.getAppFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(81, 1);
			} else {
				pstmt.setString(81, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(82, 91);
			} else {
				pstmt.setDate(82, Date.valueOf(this.getApproveDate()));
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(83, 1);
			} else {
				pstmt.setString(83, this.getUWOperator());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(84, 1);
			} else {
				pstmt.setString(84, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(85, 1);
			} else {
				pstmt.setString(85, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(86, 1);
			} else {
				pstmt.setString(86, this.getAgentCode1());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getRemark());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(88, 1);
			} else {
				pstmt.setString(88, this.getUWFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(89, 1);
			} else {
				pstmt.setString(89, this.getOutPayFlag());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(90, 1);
			} else {
				pstmt.setString(90, this.getApproveFlag());
			}
			pstmt.setDouble(91, this.getEmployeeRate());
			pstmt.setDouble(92, this.getFamilyRate());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(93, 1);
			} else {
				pstmt.setString(93, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(94, 91);
			} else {
				pstmt.setDate(94, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(95, 1);
			} else {
				pstmt.setString(95, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(96, 91);
			} else {
				pstmt.setDate(96, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(97, 1);
			} else {
				pstmt.setString(97, this.getModifyTime());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getAccName());
			}
			pstmt.setInt(99, this.getPrintCount());
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(100, 91);
			} else {
				pstmt.setDate(100, Date.valueOf(this.getLastEdorDate()));
			}
			pstmt.setDouble(101, this.getManageFeeRate());
			if(this.getGrpSpec() == null || this.getGrpSpec().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getGrpSpec());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(103, 1);
			} else {
				pstmt.setString(103, this.getGetPolMode());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(104, 91);
			} else {
				pstmt.setDate(104, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(105, 91);
			} else {
				pstmt.setDate(105, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(106, 1);
			} else {
				pstmt.setString(106, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(107, 1);
			} else {
				pstmt.setString(107, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(108, 1);
			} else {
				pstmt.setString(108, this.getStandbyFlag3());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(109, 1);
			} else {
				pstmt.setString(109, this.getSpecFlag());
			}
			// set where condition
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(110, 1);
			} else {
				pstmt.setString(110, StrTool.space1(this.getGrpPolNo(), 20));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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
		SQLString sqlObj = new SQLString("LCGrpPolBak");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCGrpPolBak(GrpPolNo ,ContNo ,GrpProposalNo ,PrtNo ,KindCode ,RiskCode ,RiskVersion ,SignCom ,ManageCom ,AgentCom ,AgentType ,SaleChnl ,Password ,GrpNo ,Password2 ,GrpName ,GrpAddressCode ,GrpAddress ,GrpZipCode ,BusinessType ,GrpNature ,Peoples2 ,RgtMoney ,Asset ,NetProfitRate ,MainBussiness ,Corporation ,ComAera ,LinkMan1 ,Department1 ,HeadShip1 ,Phone1 ,E_Mail1 ,Fax1 ,LinkMan2 ,Department2 ,HeadShip2 ,Phone2 ,E_Mail2 ,Fax2 ,Fax ,Phone ,GetFlag ,Satrap ,EMail ,FoundDate ,BankAccNo ,BankCode ,GrpGroupNo ,PayIntv ,PayMode ,CValiDate ,GetPolDate ,SignDate ,FirstPayDate ,PayEndDate ,PaytoDate ,RegetDate ,Peoples ,Mult ,Prem ,Amnt ,SumPrem ,SumPay ,Dif ,SSFlag ,PeakLine ,GetLimit ,GetRate ,MaxMedFee ,ExpPeoples ,ExpPremium ,ExpAmnt ,DisputedFlag ,BonusRate ,Lang ,Currency ,State ,LostTimes ,AppFlag ,ApproveCode ,ApproveDate ,UWOperator ,AgentCode ,AgentGroup ,AgentCode1 ,Remark ,UWFlag ,OutPayFlag ,ApproveFlag ,EmployeeRate ,FamilyRate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,AccName ,PrintCount ,LastEdorDate ,ManageFeeRate ,GrpSpec ,GetPolMode ,PolApplyDate ,CustomGetPolDate ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,SpecFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(1, 1);
			} else {
				pstmt.setString(1, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getGrpProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getPrtNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getRiskVersion());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getSignCom());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getAgentType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getSaleChnl());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getPassword());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getGrpNo());
			}
			if(this.getPassword2() == null || this.getPassword2().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getPassword2());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getGrpName());
			}
			if(this.getGrpAddressCode() == null || this.getGrpAddressCode().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getGrpAddressCode());
			}
			if(this.getGrpAddress() == null || this.getGrpAddress().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpAddress());
			}
			if(this.getGrpZipCode() == null || this.getGrpZipCode().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getGrpZipCode());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getGrpNature());
			}
			pstmt.setInt(22, this.getPeoples2());
			pstmt.setDouble(23, this.getRgtMoney());
			pstmt.setDouble(24, this.getAsset());
			pstmt.setDouble(25, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getComAera());
			}
			if(this.getLinkMan1() == null || this.getLinkMan1().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getLinkMan1());
			}
			if(this.getDepartment1() == null || this.getDepartment1().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getDepartment1());
			}
			if(this.getHeadShip1() == null || this.getHeadShip1().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getHeadShip1());
			}
			if(this.getPhone1() == null || this.getPhone1().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getPhone1());
			}
			if(this.getE_Mail1() == null || this.getE_Mail1().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getE_Mail1());
			}
			if(this.getFax1() == null || this.getFax1().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getFax1());
			}
			if(this.getLinkMan2() == null || this.getLinkMan2().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getLinkMan2());
			}
			if(this.getDepartment2() == null || this.getDepartment2().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getDepartment2());
			}
			if(this.getHeadShip2() == null || this.getHeadShip2().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getHeadShip2());
			}
			if(this.getPhone2() == null || this.getPhone2().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getPhone2());
			}
			if(this.getE_Mail2() == null || this.getE_Mail2().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getE_Mail2());
			}
			if(this.getFax2() == null || this.getFax2().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getFax2());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(45, 1);
			} else {
				pstmt.setString(45, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getBankAccNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getBankCode());
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getGrpGroupNo());
			}
			pstmt.setInt(50, this.getPayIntv());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getPayMode());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getCValiDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(54, 91);
			} else {
				pstmt.setDate(54, Date.valueOf(this.getSignDate()));
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(55, 91);
			} else {
				pstmt.setDate(55, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(56, 91);
			} else {
				pstmt.setDate(56, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getRegetDate()));
			}
			pstmt.setInt(59, this.getPeoples());
			pstmt.setDouble(60, this.getMult());
			pstmt.setDouble(61, this.getPrem());
			pstmt.setDouble(62, this.getAmnt());
			pstmt.setDouble(63, this.getSumPrem());
			pstmt.setDouble(64, this.getSumPay());
			pstmt.setDouble(65, this.getDif());
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getSSFlag());
			}
			pstmt.setDouble(67, this.getPeakLine());
			pstmt.setDouble(68, this.getGetLimit());
			pstmt.setDouble(69, this.getGetRate());
			pstmt.setDouble(70, this.getMaxMedFee());
			pstmt.setInt(71, this.getExpPeoples());
			pstmt.setDouble(72, this.getExpPremium());
			pstmt.setDouble(73, this.getExpAmnt());
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(74, 1);
			} else {
				pstmt.setString(74, this.getDisputedFlag());
			}
			pstmt.setDouble(75, this.getBonusRate());
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(76, 1);
			} else {
				pstmt.setString(76, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(77, 1);
			} else {
				pstmt.setString(77, this.getCurrency());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getState());
			}
			pstmt.setInt(79, this.getLostTimes());
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(80, 1);
			} else {
				pstmt.setString(80, this.getAppFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(81, 1);
			} else {
				pstmt.setString(81, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(82, 91);
			} else {
				pstmt.setDate(82, Date.valueOf(this.getApproveDate()));
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(83, 1);
			} else {
				pstmt.setString(83, this.getUWOperator());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(84, 1);
			} else {
				pstmt.setString(84, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(85, 1);
			} else {
				pstmt.setString(85, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(86, 1);
			} else {
				pstmt.setString(86, this.getAgentCode1());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getRemark());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(88, 1);
			} else {
				pstmt.setString(88, this.getUWFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(89, 1);
			} else {
				pstmt.setString(89, this.getOutPayFlag());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(90, 1);
			} else {
				pstmt.setString(90, this.getApproveFlag());
			}
			pstmt.setDouble(91, this.getEmployeeRate());
			pstmt.setDouble(92, this.getFamilyRate());
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(93, 1);
			} else {
				pstmt.setString(93, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(94, 91);
			} else {
				pstmt.setDate(94, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(95, 1);
			} else {
				pstmt.setString(95, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(96, 91);
			} else {
				pstmt.setDate(96, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(97, 1);
			} else {
				pstmt.setString(97, this.getModifyTime());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getAccName());
			}
			pstmt.setInt(99, this.getPrintCount());
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(100, 91);
			} else {
				pstmt.setDate(100, Date.valueOf(this.getLastEdorDate()));
			}
			pstmt.setDouble(101, this.getManageFeeRate());
			if(this.getGrpSpec() == null || this.getGrpSpec().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getGrpSpec());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(103, 1);
			} else {
				pstmt.setString(103, this.getGetPolMode());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(104, 91);
			} else {
				pstmt.setDate(104, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(105, 91);
			} else {
				pstmt.setDate(105, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(106, 1);
			} else {
				pstmt.setString(106, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(107, 1);
			} else {
				pstmt.setString(107, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(108, 1);
			} else {
				pstmt.setString(108, this.getStandbyFlag3());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(109, 1);
			} else {
				pstmt.setString(109, this.getSpecFlag());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCGrpPolBak WHERE  GrpPolNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(1, 1);
			} else {
				pstmt.setString(1, StrTool.space1(this.getGrpPolNo(), 20));
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
					tError.moduleName = "LCGrpPolBakDB";
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
			tError.moduleName = "LCGrpPolBakDB";
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

	public LCGrpPolBakSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCGrpPolBakSet aLCGrpPolBakSet = new LCGrpPolBakSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGrpPolBak");
			LCGrpPolBakSchema aSchema = this.getSchema();
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
				LCGrpPolBakSchema s1 = new LCGrpPolBakSchema();
				s1.setSchema(rs,i);
				aLCGrpPolBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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

		return aLCGrpPolBakSet;

	}

	public LCGrpPolBakSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGrpPolBakSet aLCGrpPolBakSet = new LCGrpPolBakSet();

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
				LCGrpPolBakSchema s1 = new LCGrpPolBakSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpPolBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpPolBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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

		return aLCGrpPolBakSet;
	}

	public LCGrpPolBakSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCGrpPolBakSet aLCGrpPolBakSet = new LCGrpPolBakSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGrpPolBak");
			LCGrpPolBakSchema aSchema = this.getSchema();
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

				LCGrpPolBakSchema s1 = new LCGrpPolBakSchema();
				s1.setSchema(rs,i);
				aLCGrpPolBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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

		return aLCGrpPolBakSet;

	}

	public LCGrpPolBakSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGrpPolBakSet aLCGrpPolBakSet = new LCGrpPolBakSet();

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

				LCGrpPolBakSchema s1 = new LCGrpPolBakSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpPolBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpPolBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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

		return aLCGrpPolBakSet;
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
			SQLString sqlObj = new SQLString("LCGrpPolBak");
			LCGrpPolBakSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCGrpPolBak " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpPolBakDB";
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
			tError.moduleName = "LCGrpPolBakDB";
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
        tError.moduleName = "LCGrpPolBakDB";
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
        tError.moduleName = "LCGrpPolBakDB";
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
        tError.moduleName = "LCGrpPolBakDB";
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
        tError.moduleName = "LCGrpPolBakDB";
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
 * @return LCGrpPolBakSet
 */
public LCGrpPolBakSet getData()
{
    int tCount = 0;
    LCGrpPolBakSet tLCGrpPolBakSet = new LCGrpPolBakSet();
    LCGrpPolBakSchema tLCGrpPolBakSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCGrpPolBakDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCGrpPolBakSchema = new LCGrpPolBakSchema();
        tLCGrpPolBakSchema.setSchema(mResultSet, 1);
        tLCGrpPolBakSet.add(tLCGrpPolBakSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCGrpPolBakSchema = new LCGrpPolBakSchema();
                tLCGrpPolBakSchema.setSchema(mResultSet, 1);
                tLCGrpPolBakSet.add(tLCGrpPolBakSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCGrpPolBakDB";
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
    return tLCGrpPolBakSet;
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
            tError.moduleName = "LCGrpPolBakDB";
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
        tError.moduleName = "LCGrpPolBakDB";
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
            tError.moduleName = "LCGrpPolBakDB";
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
        tError.moduleName = "LCGrpPolBakDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCGrpPolBakSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpPolBakSet aLCGrpPolBakSet = new LCGrpPolBakSet();

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
				LCGrpPolBakSchema s1 = new LCGrpPolBakSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpPolBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpPolBakSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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

		return aLCGrpPolBakSet;
	}

	public LCGrpPolBakSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpPolBakSet aLCGrpPolBakSet = new LCGrpPolBakSet();

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

				LCGrpPolBakSchema s1 = new LCGrpPolBakSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpPolBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpPolBakSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDB";
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

		return aLCGrpPolBakSet; 
	}

}
