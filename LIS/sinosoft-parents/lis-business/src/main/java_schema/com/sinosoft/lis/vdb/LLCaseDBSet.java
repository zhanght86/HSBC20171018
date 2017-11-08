/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLCaseDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 */
public class LLCaseDBSet extends LLCaseSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLCaseDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLCase");
		mflag = true;
	}

	public LLCaseDBSet()
	{
		db = new DBOper( "LLCase" );
		con = db.getConnection();
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
			tError.moduleName = "LLCaseDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLCase WHERE  CaseNo = ? AND CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCaseNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCase");
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
			tError.moduleName = "LLCaseDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLCase SET  CaseNo = ? , RgtNo = ? , RgtType = ? , RgtState = ? , CustomerNo = ? , CustomerName = ? , AccidentType = ? , ReceiptFlag = ? , HospitalFlag = ? , RgtDate = ? , ClaimCalDate = ? , AffixGetDate = ? , InHospitalDate = ? , OutHospitalDate = ? , InvaliHosDays = ? , InHospitalDays = ? , DianoseDate = ? , PostalAddress = ? , Phone = ? , AccStartDate = ? , AccidentDate = ? , AccidentSite = ? , DeathDate = ? , DieFlag = ? , CustState = ? , AccdentDesc = ? , CustBirthday = ? , CustomerSex = ? , CustomerAge = ? , IDType = ? , IDNo = ? , Handler = ? , HandleDate = ? , UWState = ? , Dealer = ? , AppealFlag = ? , TogetherGet = ? , GrpDealFlag = ? , GetMode = ? , GetIntv = ? , CalFlag = ? , UWFlag = ? , DeclineFlag = ? , EndCaseFlag = ? , EndCaseDate = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BankCode = ? , BankAccNo = ? , AccName = ? , CaseGetMode = ? , AccModifyReason = ? , CaseNoDate = ? , VIPFlag = ? , AccidentDetail = ? , CureDesc = ? , AffixConclusion = ? , AffixReason = ? , FeeInputFlag = ? , SurveyFlag = ? , SubmitFlag = ? , CondoleFlag = ? , EditFlag = ? , SecondUWFlag = ? , AccDate = ? , AccResult1 = ? , AccResult2 = ? , MasculineFlag = ? , HospitalCode = ? , HospitalName = ? , Remark = ? , SeqNo = ? , Standpay = ? , MedAccDate = ? , BnfName = ? , BnfIDNo = ? , RelationToInsured = ? , BnfIDType = ? , Birthday = ? , BnfSex = ? , BnfAccName = ? , RealPay = ? , HospitalClass = ? , AccProvince = ? , AccCity = ? , AccCounty = ? , AccSite = ? , CaseSource = ? , CaseState = ? , CloseCaseReason = ? , CloseCaseRemark = ? , LRCaseNo = ? , ComCode = ? , ModifyOperator = ? , ContNo = ? WHERE  CaseNo = ? AND CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCaseNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtNo());
			}
			if(this.get(i).getRgtType() == null || this.get(i).getRgtType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtType());
			}
			if(this.get(i).getRgtState() == null || this.get(i).getRgtState().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtState());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerName() == null || this.get(i).getCustomerName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustomerName());
			}
			if(this.get(i).getAccidentType() == null || this.get(i).getAccidentType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAccidentType());
			}
			if(this.get(i).getReceiptFlag() == null || this.get(i).getReceiptFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReceiptFlag());
			}
			if(this.get(i).getHospitalFlag() == null || this.get(i).getHospitalFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getHospitalFlag());
			}
			if(this.get(i).getRgtDate() == null || this.get(i).getRgtDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getRgtDate()));
			}
			if(this.get(i).getClaimCalDate() == null || this.get(i).getClaimCalDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getClaimCalDate()));
			}
			if(this.get(i).getAffixGetDate() == null || this.get(i).getAffixGetDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getAffixGetDate()));
			}
			if(this.get(i).getInHospitalDate() == null || this.get(i).getInHospitalDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getInHospitalDate()));
			}
			if(this.get(i).getOutHospitalDate() == null || this.get(i).getOutHospitalDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getOutHospitalDate()));
			}
			pstmt.setInt(15, this.get(i).getInvaliHosDays());
			pstmt.setInt(16, this.get(i).getInHospitalDays());
			if(this.get(i).getDianoseDate() == null || this.get(i).getDianoseDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getDianoseDate()));
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPostalAddress());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPhone());
			}
			if(this.get(i).getAccStartDate() == null || this.get(i).getAccStartDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getAccStartDate()));
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAccidentSite());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getDieFlag() == null || this.get(i).getDieFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getDieFlag());
			}
			if(this.get(i).getCustState() == null || this.get(i).getCustState().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getCustState());
			}
			if(this.get(i).getAccdentDesc() == null || this.get(i).getAccdentDesc().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAccdentDesc());
			}
			if(this.get(i).getCustBirthday() == null || this.get(i).getCustBirthday().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getCustBirthday()));
			}
			if(this.get(i).getCustomerSex() == null || this.get(i).getCustomerSex().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getCustomerSex());
			}
			pstmt.setDouble(29, this.get(i).getCustomerAge());
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getIDNo());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getHandler());
			}
			if(this.get(i).getHandleDate() == null || this.get(i).getHandleDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getHandleDate()));
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getUWState());
			}
			if(this.get(i).getDealer() == null || this.get(i).getDealer().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getDealer());
			}
			if(this.get(i).getAppealFlag() == null || this.get(i).getAppealFlag().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAppealFlag());
			}
			if(this.get(i).getTogetherGet() == null || this.get(i).getTogetherGet().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getTogetherGet());
			}
			if(this.get(i).getGrpDealFlag() == null || this.get(i).getGrpDealFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getGrpDealFlag());
			}
			if(this.get(i).getGetMode() == null || this.get(i).getGetMode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetMode());
			}
			pstmt.setInt(40, this.get(i).getGetIntv());
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getCalFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getUWFlag());
			}
			if(this.get(i).getDeclineFlag() == null || this.get(i).getDeclineFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getDeclineFlag());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getModifyTime());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getAccName());
			}
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getAccModifyReason() == null || this.get(i).getAccModifyReason().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getAccModifyReason());
			}
			if(this.get(i).getCaseNoDate() == null || this.get(i).getCaseNoDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getCaseNoDate()));
			}
			if(this.get(i).getVIPFlag() == null || this.get(i).getVIPFlag().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getVIPFlag());
			}
			if(this.get(i).getAccidentDetail() == null || this.get(i).getAccidentDetail().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAccidentDetail());
			}
			if(this.get(i).getCureDesc() == null || this.get(i).getCureDesc().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getCureDesc());
			}
			if(this.get(i).getAffixConclusion() == null || this.get(i).getAffixConclusion().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getAffixConclusion());
			}
			if(this.get(i).getAffixReason() == null || this.get(i).getAffixReason().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getAffixReason());
			}
			if(this.get(i).getFeeInputFlag() == null || this.get(i).getFeeInputFlag().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getFeeInputFlag());
			}
			if(this.get(i).getSurveyFlag() == null || this.get(i).getSurveyFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getSurveyFlag());
			}
			if(this.get(i).getSubmitFlag() == null || this.get(i).getSubmitFlag().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getSubmitFlag());
			}
			if(this.get(i).getCondoleFlag() == null || this.get(i).getCondoleFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getCondoleFlag());
			}
			if(this.get(i).getEditFlag() == null || this.get(i).getEditFlag().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getEditFlag());
			}
			if(this.get(i).getSecondUWFlag() == null || this.get(i).getSecondUWFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getSecondUWFlag());
			}
			if(this.get(i).getAccDate() == null || this.get(i).getAccDate().equals("null")) {
				pstmt.setDate(69,null);
			} else {
				pstmt.setDate(69, Date.valueOf(this.get(i).getAccDate()));
			}
			if(this.get(i).getAccResult1() == null || this.get(i).getAccResult1().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getAccResult1());
			}
			if(this.get(i).getAccResult2() == null || this.get(i).getAccResult2().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getAccResult2());
			}
			if(this.get(i).getMasculineFlag() == null || this.get(i).getMasculineFlag().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getMasculineFlag());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getHospitalCode());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getHospitalName());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getRemark());
			}
			pstmt.setInt(76, this.get(i).getSeqNo());
			pstmt.setDouble(77, this.get(i).getStandpay());
			if(this.get(i).getMedAccDate() == null || this.get(i).getMedAccDate().equals("null")) {
				pstmt.setDate(78,null);
			} else {
				pstmt.setDate(78, Date.valueOf(this.get(i).getMedAccDate()));
			}
			if(this.get(i).getBnfName() == null || this.get(i).getBnfName().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getBnfName());
			}
			if(this.get(i).getBnfIDNo() == null || this.get(i).getBnfIDNo().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getBnfIDNo());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getBnfIDType() == null || this.get(i).getBnfIDType().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getBnfIDType());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(83,null);
			} else {
				pstmt.setDate(83, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getBnfSex() == null || this.get(i).getBnfSex().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getBnfSex());
			}
			if(this.get(i).getBnfAccName() == null || this.get(i).getBnfAccName().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getBnfAccName());
			}
			pstmt.setDouble(86, this.get(i).getRealPay());
			if(this.get(i).getHospitalClass() == null || this.get(i).getHospitalClass().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getHospitalClass());
			}
			if(this.get(i).getAccProvince() == null || this.get(i).getAccProvince().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getAccProvince());
			}
			if(this.get(i).getAccCity() == null || this.get(i).getAccCity().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getAccCity());
			}
			if(this.get(i).getAccCounty() == null || this.get(i).getAccCounty().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getAccCounty());
			}
			if(this.get(i).getAccSite() == null || this.get(i).getAccSite().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getAccSite());
			}
			if(this.get(i).getCaseSource() == null || this.get(i).getCaseSource().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getCaseSource());
			}
			if(this.get(i).getCaseState() == null || this.get(i).getCaseState().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getCaseState());
			}
			if(this.get(i).getCloseCaseReason() == null || this.get(i).getCloseCaseReason().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getCloseCaseReason());
			}
			if(this.get(i).getCloseCaseRemark() == null || this.get(i).getCloseCaseRemark().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getCloseCaseRemark());
			}
			if(this.get(i).getLRCaseNo() == null || this.get(i).getLRCaseNo().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getLRCaseNo());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getModifyOperator());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getContNo());
			}
			// set where condition
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getCaseNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCase");
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
			tError.moduleName = "LLCaseDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLCase VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCaseNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtNo());
			}
			if(this.get(i).getRgtType() == null || this.get(i).getRgtType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtType());
			}
			if(this.get(i).getRgtState() == null || this.get(i).getRgtState().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtState());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerName() == null || this.get(i).getCustomerName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustomerName());
			}
			if(this.get(i).getAccidentType() == null || this.get(i).getAccidentType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAccidentType());
			}
			if(this.get(i).getReceiptFlag() == null || this.get(i).getReceiptFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReceiptFlag());
			}
			if(this.get(i).getHospitalFlag() == null || this.get(i).getHospitalFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getHospitalFlag());
			}
			if(this.get(i).getRgtDate() == null || this.get(i).getRgtDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getRgtDate()));
			}
			if(this.get(i).getClaimCalDate() == null || this.get(i).getClaimCalDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getClaimCalDate()));
			}
			if(this.get(i).getAffixGetDate() == null || this.get(i).getAffixGetDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getAffixGetDate()));
			}
			if(this.get(i).getInHospitalDate() == null || this.get(i).getInHospitalDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getInHospitalDate()));
			}
			if(this.get(i).getOutHospitalDate() == null || this.get(i).getOutHospitalDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getOutHospitalDate()));
			}
			pstmt.setInt(15, this.get(i).getInvaliHosDays());
			pstmt.setInt(16, this.get(i).getInHospitalDays());
			if(this.get(i).getDianoseDate() == null || this.get(i).getDianoseDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getDianoseDate()));
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPostalAddress());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPhone());
			}
			if(this.get(i).getAccStartDate() == null || this.get(i).getAccStartDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getAccStartDate()));
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAccidentSite());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getDieFlag() == null || this.get(i).getDieFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getDieFlag());
			}
			if(this.get(i).getCustState() == null || this.get(i).getCustState().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getCustState());
			}
			if(this.get(i).getAccdentDesc() == null || this.get(i).getAccdentDesc().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAccdentDesc());
			}
			if(this.get(i).getCustBirthday() == null || this.get(i).getCustBirthday().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getCustBirthday()));
			}
			if(this.get(i).getCustomerSex() == null || this.get(i).getCustomerSex().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getCustomerSex());
			}
			pstmt.setDouble(29, this.get(i).getCustomerAge());
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getIDNo());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getHandler());
			}
			if(this.get(i).getHandleDate() == null || this.get(i).getHandleDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getHandleDate()));
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getUWState());
			}
			if(this.get(i).getDealer() == null || this.get(i).getDealer().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getDealer());
			}
			if(this.get(i).getAppealFlag() == null || this.get(i).getAppealFlag().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAppealFlag());
			}
			if(this.get(i).getTogetherGet() == null || this.get(i).getTogetherGet().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getTogetherGet());
			}
			if(this.get(i).getGrpDealFlag() == null || this.get(i).getGrpDealFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getGrpDealFlag());
			}
			if(this.get(i).getGetMode() == null || this.get(i).getGetMode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetMode());
			}
			pstmt.setInt(40, this.get(i).getGetIntv());
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getCalFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getUWFlag());
			}
			if(this.get(i).getDeclineFlag() == null || this.get(i).getDeclineFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getDeclineFlag());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getModifyTime());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getAccName());
			}
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getAccModifyReason() == null || this.get(i).getAccModifyReason().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getAccModifyReason());
			}
			if(this.get(i).getCaseNoDate() == null || this.get(i).getCaseNoDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getCaseNoDate()));
			}
			if(this.get(i).getVIPFlag() == null || this.get(i).getVIPFlag().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getVIPFlag());
			}
			if(this.get(i).getAccidentDetail() == null || this.get(i).getAccidentDetail().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAccidentDetail());
			}
			if(this.get(i).getCureDesc() == null || this.get(i).getCureDesc().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getCureDesc());
			}
			if(this.get(i).getAffixConclusion() == null || this.get(i).getAffixConclusion().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getAffixConclusion());
			}
			if(this.get(i).getAffixReason() == null || this.get(i).getAffixReason().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getAffixReason());
			}
			if(this.get(i).getFeeInputFlag() == null || this.get(i).getFeeInputFlag().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getFeeInputFlag());
			}
			if(this.get(i).getSurveyFlag() == null || this.get(i).getSurveyFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getSurveyFlag());
			}
			if(this.get(i).getSubmitFlag() == null || this.get(i).getSubmitFlag().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getSubmitFlag());
			}
			if(this.get(i).getCondoleFlag() == null || this.get(i).getCondoleFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getCondoleFlag());
			}
			if(this.get(i).getEditFlag() == null || this.get(i).getEditFlag().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getEditFlag());
			}
			if(this.get(i).getSecondUWFlag() == null || this.get(i).getSecondUWFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getSecondUWFlag());
			}
			if(this.get(i).getAccDate() == null || this.get(i).getAccDate().equals("null")) {
				pstmt.setDate(69,null);
			} else {
				pstmt.setDate(69, Date.valueOf(this.get(i).getAccDate()));
			}
			if(this.get(i).getAccResult1() == null || this.get(i).getAccResult1().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getAccResult1());
			}
			if(this.get(i).getAccResult2() == null || this.get(i).getAccResult2().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getAccResult2());
			}
			if(this.get(i).getMasculineFlag() == null || this.get(i).getMasculineFlag().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getMasculineFlag());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getHospitalCode());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getHospitalName());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getRemark());
			}
			pstmt.setInt(76, this.get(i).getSeqNo());
			pstmt.setDouble(77, this.get(i).getStandpay());
			if(this.get(i).getMedAccDate() == null || this.get(i).getMedAccDate().equals("null")) {
				pstmt.setDate(78,null);
			} else {
				pstmt.setDate(78, Date.valueOf(this.get(i).getMedAccDate()));
			}
			if(this.get(i).getBnfName() == null || this.get(i).getBnfName().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getBnfName());
			}
			if(this.get(i).getBnfIDNo() == null || this.get(i).getBnfIDNo().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getBnfIDNo());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getBnfIDType() == null || this.get(i).getBnfIDType().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getBnfIDType());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(83,null);
			} else {
				pstmt.setDate(83, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getBnfSex() == null || this.get(i).getBnfSex().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getBnfSex());
			}
			if(this.get(i).getBnfAccName() == null || this.get(i).getBnfAccName().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getBnfAccName());
			}
			pstmt.setDouble(86, this.get(i).getRealPay());
			if(this.get(i).getHospitalClass() == null || this.get(i).getHospitalClass().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getHospitalClass());
			}
			if(this.get(i).getAccProvince() == null || this.get(i).getAccProvince().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getAccProvince());
			}
			if(this.get(i).getAccCity() == null || this.get(i).getAccCity().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getAccCity());
			}
			if(this.get(i).getAccCounty() == null || this.get(i).getAccCounty().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getAccCounty());
			}
			if(this.get(i).getAccSite() == null || this.get(i).getAccSite().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getAccSite());
			}
			if(this.get(i).getCaseSource() == null || this.get(i).getCaseSource().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getCaseSource());
			}
			if(this.get(i).getCaseState() == null || this.get(i).getCaseState().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getCaseState());
			}
			if(this.get(i).getCloseCaseReason() == null || this.get(i).getCloseCaseReason().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getCloseCaseReason());
			}
			if(this.get(i).getCloseCaseRemark() == null || this.get(i).getCloseCaseRemark().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getCloseCaseRemark());
			}
			if(this.get(i).getLRCaseNo() == null || this.get(i).getLRCaseNo().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getLRCaseNo());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getModifyOperator());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCase");
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
			tError.moduleName = "LLCaseDBSet";
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
