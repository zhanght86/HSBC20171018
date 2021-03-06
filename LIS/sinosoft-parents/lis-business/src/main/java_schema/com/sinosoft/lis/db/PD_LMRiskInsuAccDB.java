

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMRiskInsuAccSchema;
import com.sinosoft.lis.vschema.PD_LMRiskInsuAccSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMRiskInsuAccDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class PD_LMRiskInsuAccDB extends PD_LMRiskInsuAccSchema
{
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
	public PD_LMRiskInsuAccDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMRiskInsuAcc" );
		mflag = true;
	}

	public PD_LMRiskInsuAccDB()
	{
		con = null;
		db = new DBOper( "PD_LMRiskInsuAcc" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMRiskInsuAccSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMRiskInsuAccSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMRiskInsuAcc WHERE  InsuAccNo = ?");
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getInsuAccNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMRiskInsuAcc");
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
		SQLString sqlObj = new SQLString("PD_LMRiskInsuAcc");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMRiskInsuAcc SET  AccKind = ? , InsuAccNo = ? , AccType = ? , InsuAccName = ? , AccCreatePos = ? , AccCreateType = ? , AccRate = ? , AccRateTable = ? , AccCancelCode = ? , AccComputeFlag = ? , InvestType = ? , FundCompanyCode = ? , Owner = ? , AccBonusFlag = ? , AccBonusMode = ? , BonusToInsuAccNo = ? , InsuAccCalBalaFlag = ? , BonusMode = ? , InvestFlag = ? , CalValueFreq = ? , CalValueRule = ? , UnitDecimal = ? , RoundMethod = ? , BonusFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , Currency = ? , TNFlag = ? , TNFlag1 = ? , FundRiskLevel = ? , FundCompanyName = ? , FundEnglishName = ? , SwitchType = ? , FundState = ? , FundOpenDate = ? , FundSuspendDate = ? , FundCloseDate = ? , FundMaturityDate = ? , FundMoney = ? , FundClass = ? , DueCurreny = ? WHERE  InsuAccNo = ?");
			if(this.getAccKind() == null || this.getAccKind().equals("null")) {
				pstmt.setNull(1, 1);
			} else {
				pstmt.setString(1, this.getAccKind());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getInsuAccNo());
			}
			if(this.getAccType() == null || this.getAccType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAccType());
			}
			if(this.getInsuAccName() == null || this.getInsuAccName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuAccName());
			}
			if(this.getAccCreatePos() == null || this.getAccCreatePos().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAccCreatePos());
			}
			if(this.getAccCreateType() == null || this.getAccCreateType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getAccCreateType());
			}
			pstmt.setDouble(7, this.getAccRate());
			if(this.getAccRateTable() == null || this.getAccRateTable().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccRateTable());
			}
			if(this.getAccCancelCode() == null || this.getAccCancelCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAccCancelCode());
			}
			if(this.getAccComputeFlag() == null || this.getAccComputeFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAccComputeFlag());
			}
			if(this.getInvestType() == null || this.getInvestType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getInvestType());
			}
			if(this.getFundCompanyCode() == null || this.getFundCompanyCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getFundCompanyCode());
			}
			if(this.getOwner() == null || this.getOwner().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getOwner());
			}
			if(this.getAccBonusFlag() == null || this.getAccBonusFlag().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAccBonusFlag());
			}
			if(this.getAccBonusMode() == null || this.getAccBonusMode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAccBonusMode());
			}
			if(this.getBonusToInsuAccNo() == null || this.getBonusToInsuAccNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getBonusToInsuAccNo());
			}
			if(this.getInsuAccCalBalaFlag() == null || this.getInsuAccCalBalaFlag().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getInsuAccCalBalaFlag());
			}
			if(this.getBonusMode() == null || this.getBonusMode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getBonusMode());
			}
			if(this.getInvestFlag() == null || this.getInvestFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getInvestFlag());
			}
			if(this.getCalValueFreq() == null || this.getCalValueFreq().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getCalValueFreq());
			}
			if(this.getCalValueRule() == null || this.getCalValueRule().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getCalValueRule());
			}
			if(this.getUnitDecimal() == null || this.getUnitDecimal().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getUnitDecimal());
			}
			if(this.getRoundMethod() == null || this.getRoundMethod().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRoundMethod());
			}
			if(this.getBonusFlag() == null || this.getBonusFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getBonusFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getStandbyflag2());
			}
			pstmt.setInt(32, this.getStandbyflag3());
			pstmt.setInt(33, this.getStandbyflag4());
			pstmt.setDouble(34, this.getStandbyflag5());
			pstmt.setDouble(35, this.getStandbyflag6());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getCurrency());
			}
			pstmt.setInt(37, this.getTNFlag());
			pstmt.setInt(38, this.getTNFlag1());
			if(this.getFundRiskLevel() == null || this.getFundRiskLevel().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getFundRiskLevel());
			}
			if(this.getFundCompanyName() == null || this.getFundCompanyName().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getFundCompanyName());
			}
			if(this.getFundEnglishName() == null || this.getFundEnglishName().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getFundEnglishName());
			}
			if(this.getSwitchType() == null || this.getSwitchType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getSwitchType());
			}
			if(this.getFundState() == null || this.getFundState().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getFundState());
			}
			if(this.getFundOpenDate() == null || this.getFundOpenDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getFundOpenDate()));
			}
			if(this.getFundSuspendDate() == null || this.getFundSuspendDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getFundSuspendDate()));
			}
			if(this.getFundCloseDate() == null || this.getFundCloseDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getFundCloseDate()));
			}
			if(this.getFundMaturityDate() == null || this.getFundMaturityDate().equals("null")) {
				pstmt.setNull(47, 91);
			} else {
				pstmt.setDate(47, Date.valueOf(this.getFundMaturityDate()));
			}
			pstmt.setDouble(48, this.getFundMoney());
			if(this.getFundClass() == null || this.getFundClass().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getFundClass());
			}
			if(this.getDueCurreny() == null || this.getDueCurreny().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getDueCurreny());
			}
			// set where condition
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getInsuAccNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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
		SQLString sqlObj = new SQLString("PD_LMRiskInsuAcc");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMRiskInsuAcc(AccKind ,InsuAccNo ,AccType ,InsuAccName ,AccCreatePos ,AccCreateType ,AccRate ,AccRateTable ,AccCancelCode ,AccComputeFlag ,InvestType ,FundCompanyCode ,Owner ,AccBonusFlag ,AccBonusMode ,BonusToInsuAccNo ,InsuAccCalBalaFlag ,BonusMode ,InvestFlag ,CalValueFreq ,CalValueRule ,UnitDecimal ,RoundMethod ,BonusFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6 ,Currency ,TNFlag ,TNFlag1 ,FundRiskLevel ,FundCompanyName ,FundEnglishName ,SwitchType ,FundState ,FundOpenDate ,FundSuspendDate ,FundCloseDate ,FundMaturityDate ,FundMoney ,FundClass ,DueCurreny) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getAccKind() == null || this.getAccKind().equals("null")) {
				pstmt.setNull(1, 1);
			} else {
				pstmt.setString(1, this.getAccKind());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getInsuAccNo());
			}
			if(this.getAccType() == null || this.getAccType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAccType());
			}
			if(this.getInsuAccName() == null || this.getInsuAccName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuAccName());
			}
			if(this.getAccCreatePos() == null || this.getAccCreatePos().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAccCreatePos());
			}
			if(this.getAccCreateType() == null || this.getAccCreateType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getAccCreateType());
			}
			pstmt.setDouble(7, this.getAccRate());
			if(this.getAccRateTable() == null || this.getAccRateTable().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccRateTable());
			}
			if(this.getAccCancelCode() == null || this.getAccCancelCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAccCancelCode());
			}
			if(this.getAccComputeFlag() == null || this.getAccComputeFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAccComputeFlag());
			}
			if(this.getInvestType() == null || this.getInvestType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getInvestType());
			}
			if(this.getFundCompanyCode() == null || this.getFundCompanyCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getFundCompanyCode());
			}
			if(this.getOwner() == null || this.getOwner().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getOwner());
			}
			if(this.getAccBonusFlag() == null || this.getAccBonusFlag().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAccBonusFlag());
			}
			if(this.getAccBonusMode() == null || this.getAccBonusMode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAccBonusMode());
			}
			if(this.getBonusToInsuAccNo() == null || this.getBonusToInsuAccNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getBonusToInsuAccNo());
			}
			if(this.getInsuAccCalBalaFlag() == null || this.getInsuAccCalBalaFlag().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getInsuAccCalBalaFlag());
			}
			if(this.getBonusMode() == null || this.getBonusMode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getBonusMode());
			}
			if(this.getInvestFlag() == null || this.getInvestFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getInvestFlag());
			}
			if(this.getCalValueFreq() == null || this.getCalValueFreq().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getCalValueFreq());
			}
			if(this.getCalValueRule() == null || this.getCalValueRule().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getCalValueRule());
			}
			if(this.getUnitDecimal() == null || this.getUnitDecimal().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getUnitDecimal());
			}
			if(this.getRoundMethod() == null || this.getRoundMethod().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRoundMethod());
			}
			if(this.getBonusFlag() == null || this.getBonusFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getBonusFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getStandbyflag2());
			}
			pstmt.setInt(32, this.getStandbyflag3());
			pstmt.setInt(33, this.getStandbyflag4());
			pstmt.setDouble(34, this.getStandbyflag5());
			pstmt.setDouble(35, this.getStandbyflag6());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getCurrency());
			}
			pstmt.setInt(37, this.getTNFlag());
			pstmt.setInt(38, this.getTNFlag1());
			if(this.getFundRiskLevel() == null || this.getFundRiskLevel().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getFundRiskLevel());
			}
			if(this.getFundCompanyName() == null || this.getFundCompanyName().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getFundCompanyName());
			}
			if(this.getFundEnglishName() == null || this.getFundEnglishName().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getFundEnglishName());
			}
			if(this.getSwitchType() == null || this.getSwitchType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getSwitchType());
			}
			if(this.getFundState() == null || this.getFundState().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getFundState());
			}
			if(this.getFundOpenDate() == null || this.getFundOpenDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getFundOpenDate()));
			}
			if(this.getFundSuspendDate() == null || this.getFundSuspendDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getFundSuspendDate()));
			}
			if(this.getFundCloseDate() == null || this.getFundCloseDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getFundCloseDate()));
			}
			if(this.getFundMaturityDate() == null || this.getFundMaturityDate().equals("null")) {
				pstmt.setNull(47, 91);
			} else {
				pstmt.setDate(47, Date.valueOf(this.getFundMaturityDate()));
			}
			pstmt.setDouble(48, this.getFundMoney());
			if(this.getFundClass() == null || this.getFundClass().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getFundClass());
			}
			if(this.getDueCurreny() == null || this.getDueCurreny().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getDueCurreny());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMRiskInsuAcc WHERE  InsuAccNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getInsuAccNo());
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
					tError.moduleName = "PD_LMRiskInsuAccDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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

	public PD_LMRiskInsuAccSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskInsuAccSet aPD_LMRiskInsuAccSet = new PD_LMRiskInsuAccSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMRiskInsuAcc");
			PD_LMRiskInsuAccSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				PD_LMRiskInsuAccSchema s1 = new PD_LMRiskInsuAccSchema();
				s1.setSchema(rs,i);
				aPD_LMRiskInsuAccSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
			tError.functionName = "query";
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

		return aPD_LMRiskInsuAccSet;
	}

	public PD_LMRiskInsuAccSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskInsuAccSet aPD_LMRiskInsuAccSet = new PD_LMRiskInsuAccSet();

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
				PD_LMRiskInsuAccSchema s1 = new PD_LMRiskInsuAccSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMRiskInsuAccDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMRiskInsuAccSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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

		return aPD_LMRiskInsuAccSet;
	}

	public PD_LMRiskInsuAccSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskInsuAccSet aPD_LMRiskInsuAccSet = new PD_LMRiskInsuAccSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMRiskInsuAcc");
			PD_LMRiskInsuAccSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
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

				PD_LMRiskInsuAccSchema s1 = new PD_LMRiskInsuAccSchema();
				s1.setSchema(rs,i);
				aPD_LMRiskInsuAccSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
			tError.functionName = "query";
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

		return aPD_LMRiskInsuAccSet;
	}

	public PD_LMRiskInsuAccSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskInsuAccSet aPD_LMRiskInsuAccSet = new PD_LMRiskInsuAccSet();

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

				PD_LMRiskInsuAccSchema s1 = new PD_LMRiskInsuAccSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMRiskInsuAccDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMRiskInsuAccSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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

		return aPD_LMRiskInsuAccSet;
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
			SQLString sqlObj = new SQLString("PD_LMRiskInsuAcc");
			PD_LMRiskInsuAccSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMRiskInsuAcc " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMRiskInsuAccDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskInsuAccDB";
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
        tError.moduleName = "PD_LMRiskInsuAccDB";
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
        e.printStackTrace();
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "PD_LMRiskInsuAccDB";
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
        tError.moduleName = "PD_LMRiskInsuAccDB";
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
        tError.moduleName = "PD_LMRiskInsuAccDB";
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
 * @return PD_LMRiskInsuAccSet
 */
public PD_LMRiskInsuAccSet getData()
{
    int tCount = 0;
    PD_LMRiskInsuAccSet tPD_LMRiskInsuAccSet = new PD_LMRiskInsuAccSet();
    PD_LMRiskInsuAccSchema tPD_LMRiskInsuAccSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMRiskInsuAccDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMRiskInsuAccSchema = new PD_LMRiskInsuAccSchema();
        tPD_LMRiskInsuAccSchema.setSchema(mResultSet, 1);
        tPD_LMRiskInsuAccSet.add(tPD_LMRiskInsuAccSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMRiskInsuAccSchema = new PD_LMRiskInsuAccSchema();
                tPD_LMRiskInsuAccSchema.setSchema(mResultSet, 1);
                tPD_LMRiskInsuAccSet.add(tPD_LMRiskInsuAccSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMRiskInsuAccDB";
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
    return tPD_LMRiskInsuAccSet;
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
            tError.moduleName = "PD_LMRiskInsuAccDB";
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
        tError.moduleName = "PD_LMRiskInsuAccDB";
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
            tError.moduleName = "PD_LMRiskInsuAccDB";
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
        tError.moduleName = "PD_LMRiskInsuAccDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
