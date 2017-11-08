/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCGrpPolBakSchema;
import com.sinosoft.lis.vschema.LCGrpPolBakSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpPolBakDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCGrpPolBakDBSet extends LCGrpPolBakSet
{
private static Logger logger = Logger.getLogger(LCGrpPolBakDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCGrpPolBakDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCGrpPolBak");
		mflag = true;
	}

	public LCGrpPolBakDBSet()
	{
		db = new DBOper( "LCGrpPolBak" );
	}
	// @Method
	public boolean deleteSQL()
	{
		if (db.deleteSQL(this))
		{
		        return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

    /**
     * 删除操作
     * 删除条件：主键
     * @return boolean
     */
	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM LCGrpPolBak WHERE  GrpPolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, StrTool.space1(this.get(i).getGrpPolNo(), 20));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPolBak");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 更新操作
     * 更新条件：主键
     * @return boolean
     */
	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE LCGrpPolBak SET  GrpPolNo = ? , ContNo = ? , GrpProposalNo = ? , PrtNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , SignCom = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , SaleChnl = ? , Password = ? , GrpNo = ? , Password2 = ? , GrpName = ? , GrpAddressCode = ? , GrpAddress = ? , GrpZipCode = ? , BusinessType = ? , GrpNature = ? , Peoples2 = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , LinkMan1 = ? , Department1 = ? , HeadShip1 = ? , Phone1 = ? , E_Mail1 = ? , Fax1 = ? , LinkMan2 = ? , Department2 = ? , HeadShip2 = ? , Phone2 = ? , E_Mail2 = ? , Fax2 = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , BankAccNo = ? , BankCode = ? , GrpGroupNo = ? , PayIntv = ? , PayMode = ? , CValiDate = ? , GetPolDate = ? , SignDate = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , RegetDate = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , MaxMedFee = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , DisputedFlag = ? , BonusRate = ? , Lang = ? , Currency = ? , State = ? , LostTimes = ? , AppFlag = ? , ApproveCode = ? , ApproveDate = ? , UWOperator = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , Remark = ? , UWFlag = ? , OutPayFlag = ? , ApproveFlag = ? , EmployeeRate = ? , FamilyRate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , AccName = ? , PrintCount = ? , LastEdorDate = ? , ManageFeeRate = ? , GrpSpec = ? , GetPolMode = ? , PolApplyDate = ? , CustomGetPolDate = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , SpecFlag = ? WHERE  GrpPolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskVersion());
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSignCom());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSaleChnl());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPassword());
			}
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGrpNo());
			}
			if(this.get(i).getPassword2() == null || this.get(i).getPassword2().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPassword2());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpAddressCode() == null || this.get(i).getGrpAddressCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpAddressCode());
			}
			if(this.get(i).getGrpAddress() == null || this.get(i).getGrpAddress().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGrpAddress());
			}
			if(this.get(i).getGrpZipCode() == null || this.get(i).getGrpZipCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getGrpZipCode());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGrpNature());
			}
			pstmt.setInt(22, this.get(i).getPeoples2());
			pstmt.setDouble(23, this.get(i).getRgtMoney());
			pstmt.setDouble(24, this.get(i).getAsset());
			pstmt.setDouble(25, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getComAera());
			}
			if(this.get(i).getLinkMan1() == null || this.get(i).getLinkMan1().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getLinkMan1());
			}
			if(this.get(i).getDepartment1() == null || this.get(i).getDepartment1().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getDepartment1());
			}
			if(this.get(i).getHeadShip1() == null || this.get(i).getHeadShip1().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getHeadShip1());
			}
			if(this.get(i).getPhone1() == null || this.get(i).getPhone1().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPhone1());
			}
			if(this.get(i).getE_Mail1() == null || this.get(i).getE_Mail1().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getE_Mail1());
			}
			if(this.get(i).getFax1() == null || this.get(i).getFax1().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getFax1());
			}
			if(this.get(i).getLinkMan2() == null || this.get(i).getLinkMan2().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getLinkMan2());
			}
			if(this.get(i).getDepartment2() == null || this.get(i).getDepartment2().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDepartment2());
			}
			if(this.get(i).getHeadShip2() == null || this.get(i).getHeadShip2().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getHeadShip2());
			}
			if(this.get(i).getPhone2() == null || this.get(i).getPhone2().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getPhone2());
			}
			if(this.get(i).getE_Mail2() == null || this.get(i).getE_Mail2().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getE_Mail2());
			}
			if(this.get(i).getFax2() == null || this.get(i).getFax2().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getFax2());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getBankCode());
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getGrpGroupNo());
			}
			pstmt.setInt(50, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPayMode());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(54,null);
			} else {
				pstmt.setDate(54, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(55,null);
			} else {
				pstmt.setDate(55, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(56,null);
			} else {
				pstmt.setDate(56, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getRegetDate()));
			}
			pstmt.setInt(59, this.get(i).getPeoples());
			pstmt.setDouble(60, this.get(i).getMult());
			pstmt.setDouble(61, this.get(i).getPrem());
			pstmt.setDouble(62, this.get(i).getAmnt());
			pstmt.setDouble(63, this.get(i).getSumPrem());
			pstmt.setDouble(64, this.get(i).getSumPay());
			pstmt.setDouble(65, this.get(i).getDif());
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getSSFlag());
			}
			pstmt.setDouble(67, this.get(i).getPeakLine());
			pstmt.setDouble(68, this.get(i).getGetLimit());
			pstmt.setDouble(69, this.get(i).getGetRate());
			pstmt.setDouble(70, this.get(i).getMaxMedFee());
			pstmt.setInt(71, this.get(i).getExpPeoples());
			pstmt.setDouble(72, this.get(i).getExpPremium());
			pstmt.setDouble(73, this.get(i).getExpAmnt());
			if(this.get(i).getDisputedFlag() == null || this.get(i).getDisputedFlag().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getDisputedFlag());
			}
			pstmt.setDouble(75, this.get(i).getBonusRate());
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getLang());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getCurrency());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getState());
			}
			pstmt.setInt(79, this.get(i).getLostTimes());
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getAppFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(82,null);
			} else {
				pstmt.setDate(82, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getUWOperator());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getAgentCode1());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getRemark());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getUWFlag());
			}
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getOutPayFlag());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getApproveFlag());
			}
			pstmt.setDouble(91, this.get(i).getEmployeeRate());
			pstmt.setDouble(92, this.get(i).getFamilyRate());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(94,null);
			} else {
				pstmt.setDate(94, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(96,null);
			} else {
				pstmt.setDate(96, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getModifyTime());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getAccName());
			}
			pstmt.setInt(99, this.get(i).getPrintCount());
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(100,null);
			} else {
				pstmt.setDate(100, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			pstmt.setDouble(101, this.get(i).getManageFeeRate());
			if(this.get(i).getGrpSpec() == null || this.get(i).getGrpSpec().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getGrpSpec());
			}
			if(this.get(i).getGetPolMode() == null || this.get(i).getGetPolMode().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getGetPolMode());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(104,null);
			} else {
				pstmt.setDate(104, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(105,null);
			} else {
				pstmt.setDate(105, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getSpecFlag());
			}
			// set where condition
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, StrTool.space1(this.get(i).getGrpPolNo(), 20));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPolBak");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 新增操作
     * @return boolean
     */
	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO LCGrpPolBak(GrpPolNo ,ContNo ,GrpProposalNo ,PrtNo ,KindCode ,RiskCode ,RiskVersion ,SignCom ,ManageCom ,AgentCom ,AgentType ,SaleChnl ,Password ,GrpNo ,Password2 ,GrpName ,GrpAddressCode ,GrpAddress ,GrpZipCode ,BusinessType ,GrpNature ,Peoples2 ,RgtMoney ,Asset ,NetProfitRate ,MainBussiness ,Corporation ,ComAera ,LinkMan1 ,Department1 ,HeadShip1 ,Phone1 ,E_Mail1 ,Fax1 ,LinkMan2 ,Department2 ,HeadShip2 ,Phone2 ,E_Mail2 ,Fax2 ,Fax ,Phone ,GetFlag ,Satrap ,EMail ,FoundDate ,BankAccNo ,BankCode ,GrpGroupNo ,PayIntv ,PayMode ,CValiDate ,GetPolDate ,SignDate ,FirstPayDate ,PayEndDate ,PaytoDate ,RegetDate ,Peoples ,Mult ,Prem ,Amnt ,SumPrem ,SumPay ,Dif ,SSFlag ,PeakLine ,GetLimit ,GetRate ,MaxMedFee ,ExpPeoples ,ExpPremium ,ExpAmnt ,DisputedFlag ,BonusRate ,Lang ,Currency ,State ,LostTimes ,AppFlag ,ApproveCode ,ApproveDate ,UWOperator ,AgentCode ,AgentGroup ,AgentCode1 ,Remark ,UWFlag ,OutPayFlag ,ApproveFlag ,EmployeeRate ,FamilyRate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,AccName ,PrintCount ,LastEdorDate ,ManageFeeRate ,GrpSpec ,GetPolMode ,PolApplyDate ,CustomGetPolDate ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,SpecFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskVersion());
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSignCom());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSaleChnl());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPassword());
			}
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGrpNo());
			}
			if(this.get(i).getPassword2() == null || this.get(i).getPassword2().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPassword2());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpAddressCode() == null || this.get(i).getGrpAddressCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpAddressCode());
			}
			if(this.get(i).getGrpAddress() == null || this.get(i).getGrpAddress().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGrpAddress());
			}
			if(this.get(i).getGrpZipCode() == null || this.get(i).getGrpZipCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getGrpZipCode());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGrpNature());
			}
			pstmt.setInt(22, this.get(i).getPeoples2());
			pstmt.setDouble(23, this.get(i).getRgtMoney());
			pstmt.setDouble(24, this.get(i).getAsset());
			pstmt.setDouble(25, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getComAera());
			}
			if(this.get(i).getLinkMan1() == null || this.get(i).getLinkMan1().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getLinkMan1());
			}
			if(this.get(i).getDepartment1() == null || this.get(i).getDepartment1().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getDepartment1());
			}
			if(this.get(i).getHeadShip1() == null || this.get(i).getHeadShip1().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getHeadShip1());
			}
			if(this.get(i).getPhone1() == null || this.get(i).getPhone1().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPhone1());
			}
			if(this.get(i).getE_Mail1() == null || this.get(i).getE_Mail1().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getE_Mail1());
			}
			if(this.get(i).getFax1() == null || this.get(i).getFax1().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getFax1());
			}
			if(this.get(i).getLinkMan2() == null || this.get(i).getLinkMan2().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getLinkMan2());
			}
			if(this.get(i).getDepartment2() == null || this.get(i).getDepartment2().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDepartment2());
			}
			if(this.get(i).getHeadShip2() == null || this.get(i).getHeadShip2().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getHeadShip2());
			}
			if(this.get(i).getPhone2() == null || this.get(i).getPhone2().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getPhone2());
			}
			if(this.get(i).getE_Mail2() == null || this.get(i).getE_Mail2().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getE_Mail2());
			}
			if(this.get(i).getFax2() == null || this.get(i).getFax2().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getFax2());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getBankCode());
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getGrpGroupNo());
			}
			pstmt.setInt(50, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPayMode());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(54,null);
			} else {
				pstmt.setDate(54, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(55,null);
			} else {
				pstmt.setDate(55, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(56,null);
			} else {
				pstmt.setDate(56, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getRegetDate()));
			}
			pstmt.setInt(59, this.get(i).getPeoples());
			pstmt.setDouble(60, this.get(i).getMult());
			pstmt.setDouble(61, this.get(i).getPrem());
			pstmt.setDouble(62, this.get(i).getAmnt());
			pstmt.setDouble(63, this.get(i).getSumPrem());
			pstmt.setDouble(64, this.get(i).getSumPay());
			pstmt.setDouble(65, this.get(i).getDif());
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getSSFlag());
			}
			pstmt.setDouble(67, this.get(i).getPeakLine());
			pstmt.setDouble(68, this.get(i).getGetLimit());
			pstmt.setDouble(69, this.get(i).getGetRate());
			pstmt.setDouble(70, this.get(i).getMaxMedFee());
			pstmt.setInt(71, this.get(i).getExpPeoples());
			pstmt.setDouble(72, this.get(i).getExpPremium());
			pstmt.setDouble(73, this.get(i).getExpAmnt());
			if(this.get(i).getDisputedFlag() == null || this.get(i).getDisputedFlag().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getDisputedFlag());
			}
			pstmt.setDouble(75, this.get(i).getBonusRate());
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getLang());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getCurrency());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getState());
			}
			pstmt.setInt(79, this.get(i).getLostTimes());
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getAppFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(82,null);
			} else {
				pstmt.setDate(82, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getUWOperator());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getAgentCode1());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getRemark());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getUWFlag());
			}
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getOutPayFlag());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getApproveFlag());
			}
			pstmt.setDouble(91, this.get(i).getEmployeeRate());
			pstmt.setDouble(92, this.get(i).getFamilyRate());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(94,null);
			} else {
				pstmt.setDate(94, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(96,null);
			} else {
				pstmt.setDate(96, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getModifyTime());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getAccName());
			}
			pstmt.setInt(99, this.get(i).getPrintCount());
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(100,null);
			} else {
				pstmt.setDate(100, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			pstmt.setDouble(101, this.get(i).getManageFeeRate());
			if(this.get(i).getGrpSpec() == null || this.get(i).getGrpSpec().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getGrpSpec());
			}
			if(this.get(i).getGetPolMode() == null || this.get(i).getGetPolMode().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getGetPolMode());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(104,null);
			} else {
				pstmt.setDate(104, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(105,null);
			} else {
				pstmt.setDate(105, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getSpecFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPolBak");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

}
