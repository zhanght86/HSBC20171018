/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLBCaseSchema;
import com.sinosoft.lis.vschema.LLBCaseSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLBCaseDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLBCaseDBSet extends LLBCaseSet
{
private static Logger logger = Logger.getLogger(LLBCaseDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLBCaseDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLBCase");
		mflag = true;
	}

	public LLBCaseDBSet()
	{
		db = new DBOper( "LLBCase" );
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
			tError.moduleName = "LLBCaseDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLBCase WHERE  EditNo = ? AND CaseNo = ? AND CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEditNo() == null || this.get(i).getEditNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEditNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBCase");
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
			tError.moduleName = "LLBCaseDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLBCase SET  EditNo = ? , CaseNo = ? , RgtNo = ? , RgtType = ? , RgtState = ? , CustomerNo = ? , CustomerName = ? , AccidentType = ? , ReceiptFlag = ? , HospitalFlag = ? , RgtDate = ? , ClaimCalDate = ? , AffixGetDate = ? , InHospitalDate = ? , OutHospitalDate = ? , InvaliHosDays = ? , InHospitalDays = ? , DianoseDate = ? , PostalAddress = ? , Phone = ? , AccStartDate = ? , AccidentDate = ? , AccidentSite = ? , DeathDate = ? , DieFlag = ? , CustState = ? , AccdentDesc = ? , CustBirthday = ? , CustomerSex = ? , CustomerAge = ? , IDType = ? , IDNo = ? , Handler = ? , HandleDate = ? , UWState = ? , Dealer = ? , AppealFlag = ? , TogetherGet = ? , GrpDealFlag = ? , GetMode = ? , GetIntv = ? , CalFlag = ? , UWFlag = ? , DeclineFlag = ? , EndCaseFlag = ? , EndCaseDate = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BankCode = ? , BankAccNo = ? , AccName = ? , CaseGetMode = ? , AccModifyReason = ? , CaseNoDate = ? , VIPFlag = ? , AccidentDetail = ? , CureDesc = ? , AffixConclusion = ? , AffixReason = ? , FeeInputFlag = ? , SurveyFlag = ? , SubmitFlag = ? , CondoleFlag = ? , EditFlag = ? , SecondUWFlag = ? , AccDate = ? , AccResult1 = ? , AccResult2 = ? , MasculineFlag = ? , EditReason = ? , BackNo = ? , HospitalCode = ? , HospitalName = ? WHERE  EditNo = ? AND CaseNo = ? AND CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEditNo() == null || this.get(i).getEditNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEditNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtNo());
			}
			if(this.get(i).getRgtType() == null || this.get(i).getRgtType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtType());
			}
			if(this.get(i).getRgtState() == null || this.get(i).getRgtState().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRgtState());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerName() == null || this.get(i).getCustomerName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCustomerName());
			}
			if(this.get(i).getAccidentType() == null || this.get(i).getAccidentType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccidentType());
			}
			if(this.get(i).getReceiptFlag() == null || this.get(i).getReceiptFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getReceiptFlag());
			}
			if(this.get(i).getHospitalFlag() == null || this.get(i).getHospitalFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHospitalFlag());
			}
			if(this.get(i).getRgtDate() == null || this.get(i).getRgtDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getRgtDate()));
			}
			if(this.get(i).getClaimCalDate() == null || this.get(i).getClaimCalDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getClaimCalDate()));
			}
			if(this.get(i).getAffixGetDate() == null || this.get(i).getAffixGetDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getAffixGetDate()));
			}
			if(this.get(i).getInHospitalDate() == null || this.get(i).getInHospitalDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getInHospitalDate()));
			}
			if(this.get(i).getOutHospitalDate() == null || this.get(i).getOutHospitalDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getOutHospitalDate()));
			}
			pstmt.setInt(16, this.get(i).getInvaliHosDays());
			pstmt.setInt(17, this.get(i).getInHospitalDays());
			if(this.get(i).getDianoseDate() == null || this.get(i).getDianoseDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getDianoseDate()));
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPostalAddress());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPhone());
			}
			if(this.get(i).getAccStartDate() == null || this.get(i).getAccStartDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getAccStartDate()));
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAccidentSite());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getDieFlag() == null || this.get(i).getDieFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getDieFlag());
			}
			if(this.get(i).getCustState() == null || this.get(i).getCustState().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCustState());
			}
			if(this.get(i).getAccdentDesc() == null || this.get(i).getAccdentDesc().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAccdentDesc());
			}
			if(this.get(i).getCustBirthday() == null || this.get(i).getCustBirthday().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getCustBirthday()));
			}
			if(this.get(i).getCustomerSex() == null || this.get(i).getCustomerSex().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCustomerSex());
			}
			pstmt.setDouble(30, this.get(i).getCustomerAge());
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getIDNo());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getHandler());
			}
			if(this.get(i).getHandleDate() == null || this.get(i).getHandleDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getHandleDate()));
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getUWState());
			}
			if(this.get(i).getDealer() == null || this.get(i).getDealer().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDealer());
			}
			if(this.get(i).getAppealFlag() == null || this.get(i).getAppealFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAppealFlag());
			}
			if(this.get(i).getTogetherGet() == null || this.get(i).getTogetherGet().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getTogetherGet());
			}
			if(this.get(i).getGrpDealFlag() == null || this.get(i).getGrpDealFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGrpDealFlag());
			}
			if(this.get(i).getGetMode() == null || this.get(i).getGetMode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGetMode());
			}
			pstmt.setInt(41, this.get(i).getGetIntv());
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCalFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getUWFlag());
			}
			if(this.get(i).getDeclineFlag() == null || this.get(i).getDeclineFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getDeclineFlag());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getModifyTime());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getAccName());
			}
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getAccModifyReason() == null || this.get(i).getAccModifyReason().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAccModifyReason());
			}
			if(this.get(i).getCaseNoDate() == null || this.get(i).getCaseNoDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getCaseNoDate()));
			}
			if(this.get(i).getVIPFlag() == null || this.get(i).getVIPFlag().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getVIPFlag());
			}
			if(this.get(i).getAccidentDetail() == null || this.get(i).getAccidentDetail().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getAccidentDetail());
			}
			if(this.get(i).getCureDesc() == null || this.get(i).getCureDesc().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getCureDesc());
			}
			if(this.get(i).getAffixConclusion() == null || this.get(i).getAffixConclusion().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getAffixConclusion());
			}
			if(this.get(i).getAffixReason() == null || this.get(i).getAffixReason().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAffixReason());
			}
			if(this.get(i).getFeeInputFlag() == null || this.get(i).getFeeInputFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getFeeInputFlag());
			}
			if(this.get(i).getSurveyFlag() == null || this.get(i).getSurveyFlag().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getSurveyFlag());
			}
			if(this.get(i).getSubmitFlag() == null || this.get(i).getSubmitFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getSubmitFlag());
			}
			if(this.get(i).getCondoleFlag() == null || this.get(i).getCondoleFlag().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getCondoleFlag());
			}
			if(this.get(i).getEditFlag() == null || this.get(i).getEditFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getEditFlag());
			}
			if(this.get(i).getSecondUWFlag() == null || this.get(i).getSecondUWFlag().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getSecondUWFlag());
			}
			if(this.get(i).getAccDate() == null || this.get(i).getAccDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getAccDate()));
			}
			if(this.get(i).getAccResult1() == null || this.get(i).getAccResult1().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getAccResult1());
			}
			if(this.get(i).getAccResult2() == null || this.get(i).getAccResult2().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getAccResult2());
			}
			if(this.get(i).getMasculineFlag() == null || this.get(i).getMasculineFlag().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getMasculineFlag());
			}
			if(this.get(i).getEditReason() == null || this.get(i).getEditReason().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getEditReason());
			}
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getBackNo());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getHospitalCode());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getHospitalName());
			}
			// set where condition
			if(this.get(i).getEditNo() == null || this.get(i).getEditNo().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getEditNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getCaseNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBCase");
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
			tError.moduleName = "LLBCaseDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLBCase(EditNo ,CaseNo ,RgtNo ,RgtType ,RgtState ,CustomerNo ,CustomerName ,AccidentType ,ReceiptFlag ,HospitalFlag ,RgtDate ,ClaimCalDate ,AffixGetDate ,InHospitalDate ,OutHospitalDate ,InvaliHosDays ,InHospitalDays ,DianoseDate ,PostalAddress ,Phone ,AccStartDate ,AccidentDate ,AccidentSite ,DeathDate ,DieFlag ,CustState ,AccdentDesc ,CustBirthday ,CustomerSex ,CustomerAge ,IDType ,IDNo ,Handler ,HandleDate ,UWState ,Dealer ,AppealFlag ,TogetherGet ,GrpDealFlag ,GetMode ,GetIntv ,CalFlag ,UWFlag ,DeclineFlag ,EndCaseFlag ,EndCaseDate ,MngCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BankCode ,BankAccNo ,AccName ,CaseGetMode ,AccModifyReason ,CaseNoDate ,VIPFlag ,AccidentDetail ,CureDesc ,AffixConclusion ,AffixReason ,FeeInputFlag ,SurveyFlag ,SubmitFlag ,CondoleFlag ,EditFlag ,SecondUWFlag ,AccDate ,AccResult1 ,AccResult2 ,MasculineFlag ,EditReason ,BackNo ,HospitalCode ,HospitalName) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEditNo() == null || this.get(i).getEditNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEditNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtNo());
			}
			if(this.get(i).getRgtType() == null || this.get(i).getRgtType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtType());
			}
			if(this.get(i).getRgtState() == null || this.get(i).getRgtState().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRgtState());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerName() == null || this.get(i).getCustomerName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCustomerName());
			}
			if(this.get(i).getAccidentType() == null || this.get(i).getAccidentType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccidentType());
			}
			if(this.get(i).getReceiptFlag() == null || this.get(i).getReceiptFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getReceiptFlag());
			}
			if(this.get(i).getHospitalFlag() == null || this.get(i).getHospitalFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHospitalFlag());
			}
			if(this.get(i).getRgtDate() == null || this.get(i).getRgtDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getRgtDate()));
			}
			if(this.get(i).getClaimCalDate() == null || this.get(i).getClaimCalDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getClaimCalDate()));
			}
			if(this.get(i).getAffixGetDate() == null || this.get(i).getAffixGetDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getAffixGetDate()));
			}
			if(this.get(i).getInHospitalDate() == null || this.get(i).getInHospitalDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getInHospitalDate()));
			}
			if(this.get(i).getOutHospitalDate() == null || this.get(i).getOutHospitalDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getOutHospitalDate()));
			}
			pstmt.setInt(16, this.get(i).getInvaliHosDays());
			pstmt.setInt(17, this.get(i).getInHospitalDays());
			if(this.get(i).getDianoseDate() == null || this.get(i).getDianoseDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getDianoseDate()));
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPostalAddress());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPhone());
			}
			if(this.get(i).getAccStartDate() == null || this.get(i).getAccStartDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getAccStartDate()));
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAccidentSite());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getDieFlag() == null || this.get(i).getDieFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getDieFlag());
			}
			if(this.get(i).getCustState() == null || this.get(i).getCustState().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCustState());
			}
			if(this.get(i).getAccdentDesc() == null || this.get(i).getAccdentDesc().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAccdentDesc());
			}
			if(this.get(i).getCustBirthday() == null || this.get(i).getCustBirthday().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getCustBirthday()));
			}
			if(this.get(i).getCustomerSex() == null || this.get(i).getCustomerSex().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCustomerSex());
			}
			pstmt.setDouble(30, this.get(i).getCustomerAge());
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getIDNo());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getHandler());
			}
			if(this.get(i).getHandleDate() == null || this.get(i).getHandleDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getHandleDate()));
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getUWState());
			}
			if(this.get(i).getDealer() == null || this.get(i).getDealer().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDealer());
			}
			if(this.get(i).getAppealFlag() == null || this.get(i).getAppealFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAppealFlag());
			}
			if(this.get(i).getTogetherGet() == null || this.get(i).getTogetherGet().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getTogetherGet());
			}
			if(this.get(i).getGrpDealFlag() == null || this.get(i).getGrpDealFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGrpDealFlag());
			}
			if(this.get(i).getGetMode() == null || this.get(i).getGetMode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGetMode());
			}
			pstmt.setInt(41, this.get(i).getGetIntv());
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCalFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getUWFlag());
			}
			if(this.get(i).getDeclineFlag() == null || this.get(i).getDeclineFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getDeclineFlag());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getModifyTime());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getAccName());
			}
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getAccModifyReason() == null || this.get(i).getAccModifyReason().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAccModifyReason());
			}
			if(this.get(i).getCaseNoDate() == null || this.get(i).getCaseNoDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getCaseNoDate()));
			}
			if(this.get(i).getVIPFlag() == null || this.get(i).getVIPFlag().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getVIPFlag());
			}
			if(this.get(i).getAccidentDetail() == null || this.get(i).getAccidentDetail().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getAccidentDetail());
			}
			if(this.get(i).getCureDesc() == null || this.get(i).getCureDesc().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getCureDesc());
			}
			if(this.get(i).getAffixConclusion() == null || this.get(i).getAffixConclusion().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getAffixConclusion());
			}
			if(this.get(i).getAffixReason() == null || this.get(i).getAffixReason().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAffixReason());
			}
			if(this.get(i).getFeeInputFlag() == null || this.get(i).getFeeInputFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getFeeInputFlag());
			}
			if(this.get(i).getSurveyFlag() == null || this.get(i).getSurveyFlag().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getSurveyFlag());
			}
			if(this.get(i).getSubmitFlag() == null || this.get(i).getSubmitFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getSubmitFlag());
			}
			if(this.get(i).getCondoleFlag() == null || this.get(i).getCondoleFlag().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getCondoleFlag());
			}
			if(this.get(i).getEditFlag() == null || this.get(i).getEditFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getEditFlag());
			}
			if(this.get(i).getSecondUWFlag() == null || this.get(i).getSecondUWFlag().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getSecondUWFlag());
			}
			if(this.get(i).getAccDate() == null || this.get(i).getAccDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getAccDate()));
			}
			if(this.get(i).getAccResult1() == null || this.get(i).getAccResult1().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getAccResult1());
			}
			if(this.get(i).getAccResult2() == null || this.get(i).getAccResult2().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getAccResult2());
			}
			if(this.get(i).getMasculineFlag() == null || this.get(i).getMasculineFlag().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getMasculineFlag());
			}
			if(this.get(i).getEditReason() == null || this.get(i).getEditReason().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getEditReason());
			}
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getBackNo());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getHospitalCode());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getHospitalName());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBCase");
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
			tError.moduleName = "LLBCaseDBSet";
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
