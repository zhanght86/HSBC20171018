/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLBCaseSchema;
import com.sinosoft.lis.vschema.LLBCaseSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLBCaseDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLBCaseDB extends LLBCaseSchema
{
private static Logger logger = Logger.getLogger(LLBCaseDB.class);

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
	public LLBCaseDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLBCase" );
		mflag = true;
	}

	public LLBCaseDB()
	{
		con = null;
		db = new DBOper( "LLBCase" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLBCaseSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLBCaseSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLBCase WHERE  EditNo = ? AND CaseNo = ? AND CustomerNo = ?");
			if(this.getEditNo() == null || this.getEditNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEditNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBCase");
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
		SQLString sqlObj = new SQLString("LLBCase");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLBCase SET  EditNo = ? , CaseNo = ? , RgtNo = ? , RgtType = ? , RgtState = ? , CustomerNo = ? , CustomerName = ? , AccidentType = ? , ReceiptFlag = ? , HospitalFlag = ? , RgtDate = ? , ClaimCalDate = ? , AffixGetDate = ? , InHospitalDate = ? , OutHospitalDate = ? , InvaliHosDays = ? , InHospitalDays = ? , DianoseDate = ? , PostalAddress = ? , Phone = ? , AccStartDate = ? , AccidentDate = ? , AccidentSite = ? , DeathDate = ? , DieFlag = ? , CustState = ? , AccdentDesc = ? , CustBirthday = ? , CustomerSex = ? , CustomerAge = ? , IDType = ? , IDNo = ? , Handler = ? , HandleDate = ? , UWState = ? , Dealer = ? , AppealFlag = ? , TogetherGet = ? , GrpDealFlag = ? , GetMode = ? , GetIntv = ? , CalFlag = ? , UWFlag = ? , DeclineFlag = ? , EndCaseFlag = ? , EndCaseDate = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BankCode = ? , BankAccNo = ? , AccName = ? , CaseGetMode = ? , AccModifyReason = ? , CaseNoDate = ? , VIPFlag = ? , AccidentDetail = ? , CureDesc = ? , AffixConclusion = ? , AffixReason = ? , FeeInputFlag = ? , SurveyFlag = ? , SubmitFlag = ? , CondoleFlag = ? , EditFlag = ? , SecondUWFlag = ? , AccDate = ? , AccResult1 = ? , AccResult2 = ? , MasculineFlag = ? , EditReason = ? , BackNo = ? , HospitalCode = ? , HospitalName = ? WHERE  EditNo = ? AND CaseNo = ? AND CustomerNo = ?");
			if(this.getEditNo() == null || this.getEditNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEditNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRgtNo());
			}
			if(this.getRgtType() == null || this.getRgtType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRgtType());
			}
			if(this.getRgtState() == null || this.getRgtState().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRgtState());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo());
			}
			if(this.getCustomerName() == null || this.getCustomerName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getCustomerName());
			}
			if(this.getAccidentType() == null || this.getAccidentType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccidentType());
			}
			if(this.getReceiptFlag() == null || this.getReceiptFlag().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getReceiptFlag());
			}
			if(this.getHospitalFlag() == null || this.getHospitalFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getHospitalFlag());
			}
			if(this.getRgtDate() == null || this.getRgtDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getRgtDate()));
			}
			if(this.getClaimCalDate() == null || this.getClaimCalDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getClaimCalDate()));
			}
			if(this.getAffixGetDate() == null || this.getAffixGetDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getAffixGetDate()));
			}
			if(this.getInHospitalDate() == null || this.getInHospitalDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getInHospitalDate()));
			}
			if(this.getOutHospitalDate() == null || this.getOutHospitalDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getOutHospitalDate()));
			}
			pstmt.setInt(16, this.getInvaliHosDays());
			pstmt.setInt(17, this.getInHospitalDays());
			if(this.getDianoseDate() == null || this.getDianoseDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getDianoseDate()));
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPostalAddress());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPhone());
			}
			if(this.getAccStartDate() == null || this.getAccStartDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getAccStartDate()));
			}
			if(this.getAccidentDate() == null || this.getAccidentDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getAccidentDate()));
			}
			if(this.getAccidentSite() == null || this.getAccidentSite().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAccidentSite());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getDeathDate()));
			}
			if(this.getDieFlag() == null || this.getDieFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDieFlag());
			}
			if(this.getCustState() == null || this.getCustState().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCustState());
			}
			if(this.getAccdentDesc() == null || this.getAccdentDesc().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getAccdentDesc());
			}
			if(this.getCustBirthday() == null || this.getCustBirthday().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getCustBirthday()));
			}
			if(this.getCustomerSex() == null || this.getCustomerSex().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getCustomerSex());
			}
			pstmt.setDouble(30, this.getCustomerAge());
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getIDNo());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getHandler());
			}
			if(this.getHandleDate() == null || this.getHandleDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getHandleDate()));
			}
			if(this.getUWState() == null || this.getUWState().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getUWState());
			}
			if(this.getDealer() == null || this.getDealer().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getDealer());
			}
			if(this.getAppealFlag() == null || this.getAppealFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getAppealFlag());
			}
			if(this.getTogetherGet() == null || this.getTogetherGet().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getTogetherGet());
			}
			if(this.getGrpDealFlag() == null || this.getGrpDealFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getGrpDealFlag());
			}
			if(this.getGetMode() == null || this.getGetMode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getGetMode());
			}
			pstmt.setInt(41, this.getGetIntv());
			if(this.getCalFlag() == null || this.getCalFlag().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getCalFlag());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getUWFlag());
			}
			if(this.getDeclineFlag() == null || this.getDeclineFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getDeclineFlag());
			}
			if(this.getEndCaseFlag() == null || this.getEndCaseFlag().equals("null")) {
				pstmt.setNull(45, 1);
			} else {
				pstmt.setString(45, this.getEndCaseFlag());
			}
			if(this.getEndCaseDate() == null || this.getEndCaseDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getEndCaseDate()));
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(49, 91);
			} else {
				pstmt.setDate(49, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(52, 1);
			} else {
				pstmt.setString(52, this.getModifyTime());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getAccName());
			}
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(56, 1);
			} else {
				pstmt.setString(56, this.getCaseGetMode());
			}
			if(this.getAccModifyReason() == null || this.getAccModifyReason().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getAccModifyReason());
			}
			if(this.getCaseNoDate() == null || this.getCaseNoDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCaseNoDate()));
			}
			if(this.getVIPFlag() == null || this.getVIPFlag().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getVIPFlag());
			}
			if(this.getAccidentDetail() == null || this.getAccidentDetail().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAccidentDetail());
			}
			if(this.getCureDesc() == null || this.getCureDesc().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getCureDesc());
			}
			if(this.getAffixConclusion() == null || this.getAffixConclusion().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAffixConclusion());
			}
			if(this.getAffixReason() == null || this.getAffixReason().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getAffixReason());
			}
			if(this.getFeeInputFlag() == null || this.getFeeInputFlag().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, this.getFeeInputFlag());
			}
			if(this.getSurveyFlag() == null || this.getSurveyFlag().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getSurveyFlag());
			}
			if(this.getSubmitFlag() == null || this.getSubmitFlag().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getSubmitFlag());
			}
			if(this.getCondoleFlag() == null || this.getCondoleFlag().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getCondoleFlag());
			}
			if(this.getEditFlag() == null || this.getEditFlag().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getEditFlag());
			}
			if(this.getSecondUWFlag() == null || this.getSecondUWFlag().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getSecondUWFlag());
			}
			if(this.getAccDate() == null || this.getAccDate().equals("null")) {
				pstmt.setNull(70, 91);
			} else {
				pstmt.setDate(70, Date.valueOf(this.getAccDate()));
			}
			if(this.getAccResult1() == null || this.getAccResult1().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getAccResult1());
			}
			if(this.getAccResult2() == null || this.getAccResult2().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getAccResult2());
			}
			if(this.getMasculineFlag() == null || this.getMasculineFlag().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getMasculineFlag());
			}
			if(this.getEditReason() == null || this.getEditReason().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getEditReason());
			}
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getBackNo());
			}
			if(this.getHospitalCode() == null || this.getHospitalCode().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getHospitalCode());
			}
			if(this.getHospitalName() == null || this.getHospitalName().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getHospitalName());
			}
			// set where condition
			if(this.getEditNo() == null || this.getEditNo().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getEditNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getCaseNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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
		SQLString sqlObj = new SQLString("LLBCase");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLBCase(EditNo ,CaseNo ,RgtNo ,RgtType ,RgtState ,CustomerNo ,CustomerName ,AccidentType ,ReceiptFlag ,HospitalFlag ,RgtDate ,ClaimCalDate ,AffixGetDate ,InHospitalDate ,OutHospitalDate ,InvaliHosDays ,InHospitalDays ,DianoseDate ,PostalAddress ,Phone ,AccStartDate ,AccidentDate ,AccidentSite ,DeathDate ,DieFlag ,CustState ,AccdentDesc ,CustBirthday ,CustomerSex ,CustomerAge ,IDType ,IDNo ,Handler ,HandleDate ,UWState ,Dealer ,AppealFlag ,TogetherGet ,GrpDealFlag ,GetMode ,GetIntv ,CalFlag ,UWFlag ,DeclineFlag ,EndCaseFlag ,EndCaseDate ,MngCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BankCode ,BankAccNo ,AccName ,CaseGetMode ,AccModifyReason ,CaseNoDate ,VIPFlag ,AccidentDetail ,CureDesc ,AffixConclusion ,AffixReason ,FeeInputFlag ,SurveyFlag ,SubmitFlag ,CondoleFlag ,EditFlag ,SecondUWFlag ,AccDate ,AccResult1 ,AccResult2 ,MasculineFlag ,EditReason ,BackNo ,HospitalCode ,HospitalName) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEditNo() == null || this.getEditNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEditNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRgtNo());
			}
			if(this.getRgtType() == null || this.getRgtType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRgtType());
			}
			if(this.getRgtState() == null || this.getRgtState().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRgtState());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo());
			}
			if(this.getCustomerName() == null || this.getCustomerName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getCustomerName());
			}
			if(this.getAccidentType() == null || this.getAccidentType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccidentType());
			}
			if(this.getReceiptFlag() == null || this.getReceiptFlag().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getReceiptFlag());
			}
			if(this.getHospitalFlag() == null || this.getHospitalFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getHospitalFlag());
			}
			if(this.getRgtDate() == null || this.getRgtDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getRgtDate()));
			}
			if(this.getClaimCalDate() == null || this.getClaimCalDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getClaimCalDate()));
			}
			if(this.getAffixGetDate() == null || this.getAffixGetDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getAffixGetDate()));
			}
			if(this.getInHospitalDate() == null || this.getInHospitalDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getInHospitalDate()));
			}
			if(this.getOutHospitalDate() == null || this.getOutHospitalDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getOutHospitalDate()));
			}
			pstmt.setInt(16, this.getInvaliHosDays());
			pstmt.setInt(17, this.getInHospitalDays());
			if(this.getDianoseDate() == null || this.getDianoseDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getDianoseDate()));
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPostalAddress());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPhone());
			}
			if(this.getAccStartDate() == null || this.getAccStartDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getAccStartDate()));
			}
			if(this.getAccidentDate() == null || this.getAccidentDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getAccidentDate()));
			}
			if(this.getAccidentSite() == null || this.getAccidentSite().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAccidentSite());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getDeathDate()));
			}
			if(this.getDieFlag() == null || this.getDieFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDieFlag());
			}
			if(this.getCustState() == null || this.getCustState().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCustState());
			}
			if(this.getAccdentDesc() == null || this.getAccdentDesc().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getAccdentDesc());
			}
			if(this.getCustBirthday() == null || this.getCustBirthday().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getCustBirthday()));
			}
			if(this.getCustomerSex() == null || this.getCustomerSex().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getCustomerSex());
			}
			pstmt.setDouble(30, this.getCustomerAge());
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getIDNo());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getHandler());
			}
			if(this.getHandleDate() == null || this.getHandleDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getHandleDate()));
			}
			if(this.getUWState() == null || this.getUWState().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getUWState());
			}
			if(this.getDealer() == null || this.getDealer().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getDealer());
			}
			if(this.getAppealFlag() == null || this.getAppealFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getAppealFlag());
			}
			if(this.getTogetherGet() == null || this.getTogetherGet().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getTogetherGet());
			}
			if(this.getGrpDealFlag() == null || this.getGrpDealFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getGrpDealFlag());
			}
			if(this.getGetMode() == null || this.getGetMode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getGetMode());
			}
			pstmt.setInt(41, this.getGetIntv());
			if(this.getCalFlag() == null || this.getCalFlag().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getCalFlag());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getUWFlag());
			}
			if(this.getDeclineFlag() == null || this.getDeclineFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getDeclineFlag());
			}
			if(this.getEndCaseFlag() == null || this.getEndCaseFlag().equals("null")) {
				pstmt.setNull(45, 1);
			} else {
				pstmt.setString(45, this.getEndCaseFlag());
			}
			if(this.getEndCaseDate() == null || this.getEndCaseDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getEndCaseDate()));
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(49, 91);
			} else {
				pstmt.setDate(49, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(52, 1);
			} else {
				pstmt.setString(52, this.getModifyTime());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getAccName());
			}
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(56, 1);
			} else {
				pstmt.setString(56, this.getCaseGetMode());
			}
			if(this.getAccModifyReason() == null || this.getAccModifyReason().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getAccModifyReason());
			}
			if(this.getCaseNoDate() == null || this.getCaseNoDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCaseNoDate()));
			}
			if(this.getVIPFlag() == null || this.getVIPFlag().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getVIPFlag());
			}
			if(this.getAccidentDetail() == null || this.getAccidentDetail().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAccidentDetail());
			}
			if(this.getCureDesc() == null || this.getCureDesc().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getCureDesc());
			}
			if(this.getAffixConclusion() == null || this.getAffixConclusion().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAffixConclusion());
			}
			if(this.getAffixReason() == null || this.getAffixReason().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getAffixReason());
			}
			if(this.getFeeInputFlag() == null || this.getFeeInputFlag().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, this.getFeeInputFlag());
			}
			if(this.getSurveyFlag() == null || this.getSurveyFlag().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getSurveyFlag());
			}
			if(this.getSubmitFlag() == null || this.getSubmitFlag().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getSubmitFlag());
			}
			if(this.getCondoleFlag() == null || this.getCondoleFlag().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getCondoleFlag());
			}
			if(this.getEditFlag() == null || this.getEditFlag().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getEditFlag());
			}
			if(this.getSecondUWFlag() == null || this.getSecondUWFlag().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getSecondUWFlag());
			}
			if(this.getAccDate() == null || this.getAccDate().equals("null")) {
				pstmt.setNull(70, 91);
			} else {
				pstmt.setDate(70, Date.valueOf(this.getAccDate()));
			}
			if(this.getAccResult1() == null || this.getAccResult1().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getAccResult1());
			}
			if(this.getAccResult2() == null || this.getAccResult2().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getAccResult2());
			}
			if(this.getMasculineFlag() == null || this.getMasculineFlag().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getMasculineFlag());
			}
			if(this.getEditReason() == null || this.getEditReason().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getEditReason());
			}
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getBackNo());
			}
			if(this.getHospitalCode() == null || this.getHospitalCode().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getHospitalCode());
			}
			if(this.getHospitalName() == null || this.getHospitalName().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getHospitalName());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLBCase WHERE  EditNo = ? AND CaseNo = ? AND CustomerNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEditNo() == null || this.getEditNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEditNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCustomerNo());
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
					tError.moduleName = "LLBCaseDB";
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
			tError.moduleName = "LLBCaseDB";
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

	public LLBCaseSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLBCaseSet aLLBCaseSet = new LLBCaseSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLBCase");
			LLBCaseSchema aSchema = this.getSchema();
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
				LLBCaseSchema s1 = new LLBCaseSchema();
				s1.setSchema(rs,i);
				aLLBCaseSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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

		return aLLBCaseSet;

	}

	public LLBCaseSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLBCaseSet aLLBCaseSet = new LLBCaseSet();

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
				LLBCaseSchema s1 = new LLBCaseSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBCaseDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBCaseSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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

		return aLLBCaseSet;
	}

	public LLBCaseSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLBCaseSet aLLBCaseSet = new LLBCaseSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLBCase");
			LLBCaseSchema aSchema = this.getSchema();
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

				LLBCaseSchema s1 = new LLBCaseSchema();
				s1.setSchema(rs,i);
				aLLBCaseSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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

		return aLLBCaseSet;

	}

	public LLBCaseSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLBCaseSet aLLBCaseSet = new LLBCaseSet();

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

				LLBCaseSchema s1 = new LLBCaseSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBCaseDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBCaseSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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

		return aLLBCaseSet;
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
			SQLString sqlObj = new SQLString("LLBCase");
			LLBCaseSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLBCase " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLBCaseDB";
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
			tError.moduleName = "LLBCaseDB";
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
        tError.moduleName = "LLBCaseDB";
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
        tError.moduleName = "LLBCaseDB";
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
        tError.moduleName = "LLBCaseDB";
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
        tError.moduleName = "LLBCaseDB";
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
 * @return LLBCaseSet
 */
public LLBCaseSet getData()
{
    int tCount = 0;
    LLBCaseSet tLLBCaseSet = new LLBCaseSet();
    LLBCaseSchema tLLBCaseSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLBCaseDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLBCaseSchema = new LLBCaseSchema();
        tLLBCaseSchema.setSchema(mResultSet, 1);
        tLLBCaseSet.add(tLLBCaseSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLBCaseSchema = new LLBCaseSchema();
                tLLBCaseSchema.setSchema(mResultSet, 1);
                tLLBCaseSet.add(tLLBCaseSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLBCaseDB";
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
    return tLLBCaseSet;
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
            tError.moduleName = "LLBCaseDB";
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
        tError.moduleName = "LLBCaseDB";
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
            tError.moduleName = "LLBCaseDB";
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
        tError.moduleName = "LLBCaseDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLBCaseSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLBCaseSet aLLBCaseSet = new LLBCaseSet();

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
				LLBCaseSchema s1 = new LLBCaseSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBCaseDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBCaseSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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

		return aLLBCaseSet;
	}

	public LLBCaseSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLBCaseSet aLLBCaseSet = new LLBCaseSet();

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

				LLBCaseSchema s1 = new LLBCaseSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBCaseDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBCaseSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBCaseDB";
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

		return aLLBCaseSet; 
	}

}
