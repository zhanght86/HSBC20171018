

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMRiskInsuAccSchema;
import com.sinosoft.lis.vschema.LMRiskInsuAccSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMRiskInsuAccDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LMRiskInsuAccDBSet extends LMRiskInsuAccSet
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
	public LMRiskInsuAccDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMRiskInsuAcc");
		mflag = true;
	}

	public LMRiskInsuAccDBSet()
	{
		db = new DBOper( "LMRiskInsuAcc" );
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
			tError.moduleName = "LMRiskInsuAccDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMRiskInsuAcc WHERE  InsuAccNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getInsuAccNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskInsuAcc");
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
			tError.moduleName = "LMRiskInsuAccDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMRiskInsuAcc SET  InsuAccNo = ? , AccType = ? , AccKind = ? , InsuAccName = ? , AccCreatePos = ? , AccCreateType = ? , AccRate = ? , AccRateTable = ? , AccCancelCode = ? , AccComputeFlag = ? , InvestType = ? , FundCompanyCode = ? , Owner = ? , BonusFlag = ? , AccBonusMode = ? , BonusToInsuAccNo = ? , InsuAccCalBalaFlag = ? , BonusMode = ? , InvestFlag = ? , CalValueFreq = ? , CalValueRule = ? , UnitDecimal = ? , RoundMethod = ? , AccBonusFlag = ? , Currency = ? , TNFlag = ? , FundRiskLevel = ? , FundCompanyName = ? , FundEnglishName = ? , SwitchType = ? , FundState = ? , FundOpenDate = ? , FundSuspendDate = ? , FundCloseDate = ? , FundMaturityDate = ? , TNFlag1 = ? , FundMoney = ? , FundClass = ? , DueCurreny = ? WHERE  InsuAccNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getAccType() == null || this.get(i).getAccType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAccType());
			}
			if(this.get(i).getAccKind() == null || this.get(i).getAccKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccKind());
			}
			if(this.get(i).getInsuAccName() == null || this.get(i).getInsuAccName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInsuAccName());
			}
			if(this.get(i).getAccCreatePos() == null || this.get(i).getAccCreatePos().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccCreatePos());
			}
			if(this.get(i).getAccCreateType() == null || this.get(i).getAccCreateType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAccCreateType());
			}
			pstmt.setDouble(7, this.get(i).getAccRate());
			if(this.get(i).getAccRateTable() == null || this.get(i).getAccRateTable().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccRateTable());
			}
			if(this.get(i).getAccCancelCode() == null || this.get(i).getAccCancelCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAccCancelCode());
			}
			if(this.get(i).getAccComputeFlag() == null || this.get(i).getAccComputeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAccComputeFlag());
			}
			if(this.get(i).getInvestType() == null || this.get(i).getInvestType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInvestType());
			}
			if(this.get(i).getFundCompanyCode() == null || this.get(i).getFundCompanyCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getFundCompanyCode());
			}
			if(this.get(i).getOwner() == null || this.get(i).getOwner().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOwner());
			}
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBonusFlag());
			}
			if(this.get(i).getAccBonusMode() == null || this.get(i).getAccBonusMode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAccBonusMode());
			}
			if(this.get(i).getBonusToInsuAccNo() == null || this.get(i).getBonusToInsuAccNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBonusToInsuAccNo());
			}
			if(this.get(i).getInsuAccCalBalaFlag() == null || this.get(i).getInsuAccCalBalaFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getInsuAccCalBalaFlag());
			}
			if(this.get(i).getBonusMode() == null || this.get(i).getBonusMode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBonusMode());
			}
			if(this.get(i).getInvestFlag() == null || this.get(i).getInvestFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getInvestFlag());
			}
			if(this.get(i).getCalValueFreq() == null || this.get(i).getCalValueFreq().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCalValueFreq());
			}
			if(this.get(i).getCalValueRule() == null || this.get(i).getCalValueRule().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getCalValueRule());
			}
			if(this.get(i).getUnitDecimal() == null || this.get(i).getUnitDecimal().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUnitDecimal());
			}
			if(this.get(i).getRoundMethod() == null || this.get(i).getRoundMethod().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRoundMethod());
			}
			if(this.get(i).getAccBonusFlag() == null || this.get(i).getAccBonusFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAccBonusFlag());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getCurrency());
			}
			pstmt.setInt(26, this.get(i).getTNFlag());
			if(this.get(i).getFundRiskLevel() == null || this.get(i).getFundRiskLevel().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getFundRiskLevel());
			}
			if(this.get(i).getFundCompanyName() == null || this.get(i).getFundCompanyName().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFundCompanyName());
			}
			if(this.get(i).getFundEnglishName() == null || this.get(i).getFundEnglishName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFundEnglishName());
			}
			if(this.get(i).getSwitchType() == null || this.get(i).getSwitchType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getSwitchType());
			}
			if(this.get(i).getFundState() == null || this.get(i).getFundState().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getFundState());
			}
			if(this.get(i).getFundOpenDate() == null || this.get(i).getFundOpenDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getFundOpenDate()));
			}
			if(this.get(i).getFundSuspendDate() == null || this.get(i).getFundSuspendDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getFundSuspendDate()));
			}
			if(this.get(i).getFundCloseDate() == null || this.get(i).getFundCloseDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getFundCloseDate()));
			}
			if(this.get(i).getFundMaturityDate() == null || this.get(i).getFundMaturityDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getFundMaturityDate()));
			}
			pstmt.setInt(36, this.get(i).getTNFlag1());
			pstmt.setDouble(37, this.get(i).getFundMoney());
			if(this.get(i).getFundClass() == null || this.get(i).getFundClass().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getFundClass());
			}
			if(this.get(i).getDueCurreny() == null || this.get(i).getDueCurreny().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getDueCurreny());
			}
			// set where condition
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getInsuAccNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskInsuAcc");
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
			tError.moduleName = "LMRiskInsuAccDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMRiskInsuAcc(InsuAccNo ,AccType ,AccKind ,InsuAccName ,AccCreatePos ,AccCreateType ,AccRate ,AccRateTable ,AccCancelCode ,AccComputeFlag ,InvestType ,FundCompanyCode ,Owner ,BonusFlag ,AccBonusMode ,BonusToInsuAccNo ,InsuAccCalBalaFlag ,BonusMode ,InvestFlag ,CalValueFreq ,CalValueRule ,UnitDecimal ,RoundMethod ,AccBonusFlag ,Currency ,TNFlag ,FundRiskLevel ,FundCompanyName ,FundEnglishName ,SwitchType ,FundState ,FundOpenDate ,FundSuspendDate ,FundCloseDate ,FundMaturityDate ,TNFlag1 ,FundMoney ,FundClass ,DueCurreny) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getAccType() == null || this.get(i).getAccType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAccType());
			}
			if(this.get(i).getAccKind() == null || this.get(i).getAccKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccKind());
			}
			if(this.get(i).getInsuAccName() == null || this.get(i).getInsuAccName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInsuAccName());
			}
			if(this.get(i).getAccCreatePos() == null || this.get(i).getAccCreatePos().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccCreatePos());
			}
			if(this.get(i).getAccCreateType() == null || this.get(i).getAccCreateType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAccCreateType());
			}
			pstmt.setDouble(7, this.get(i).getAccRate());
			if(this.get(i).getAccRateTable() == null || this.get(i).getAccRateTable().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccRateTable());
			}
			if(this.get(i).getAccCancelCode() == null || this.get(i).getAccCancelCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAccCancelCode());
			}
			if(this.get(i).getAccComputeFlag() == null || this.get(i).getAccComputeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAccComputeFlag());
			}
			if(this.get(i).getInvestType() == null || this.get(i).getInvestType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInvestType());
			}
			if(this.get(i).getFundCompanyCode() == null || this.get(i).getFundCompanyCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getFundCompanyCode());
			}
			if(this.get(i).getOwner() == null || this.get(i).getOwner().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOwner());
			}
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBonusFlag());
			}
			if(this.get(i).getAccBonusMode() == null || this.get(i).getAccBonusMode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAccBonusMode());
			}
			if(this.get(i).getBonusToInsuAccNo() == null || this.get(i).getBonusToInsuAccNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBonusToInsuAccNo());
			}
			if(this.get(i).getInsuAccCalBalaFlag() == null || this.get(i).getInsuAccCalBalaFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getInsuAccCalBalaFlag());
			}
			if(this.get(i).getBonusMode() == null || this.get(i).getBonusMode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBonusMode());
			}
			if(this.get(i).getInvestFlag() == null || this.get(i).getInvestFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getInvestFlag());
			}
			if(this.get(i).getCalValueFreq() == null || this.get(i).getCalValueFreq().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCalValueFreq());
			}
			if(this.get(i).getCalValueRule() == null || this.get(i).getCalValueRule().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getCalValueRule());
			}
			if(this.get(i).getUnitDecimal() == null || this.get(i).getUnitDecimal().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUnitDecimal());
			}
			if(this.get(i).getRoundMethod() == null || this.get(i).getRoundMethod().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRoundMethod());
			}
			if(this.get(i).getAccBonusFlag() == null || this.get(i).getAccBonusFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAccBonusFlag());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getCurrency());
			}
			pstmt.setInt(26, this.get(i).getTNFlag());
			if(this.get(i).getFundRiskLevel() == null || this.get(i).getFundRiskLevel().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getFundRiskLevel());
			}
			if(this.get(i).getFundCompanyName() == null || this.get(i).getFundCompanyName().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFundCompanyName());
			}
			if(this.get(i).getFundEnglishName() == null || this.get(i).getFundEnglishName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFundEnglishName());
			}
			if(this.get(i).getSwitchType() == null || this.get(i).getSwitchType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getSwitchType());
			}
			if(this.get(i).getFundState() == null || this.get(i).getFundState().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getFundState());
			}
			if(this.get(i).getFundOpenDate() == null || this.get(i).getFundOpenDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getFundOpenDate()));
			}
			if(this.get(i).getFundSuspendDate() == null || this.get(i).getFundSuspendDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getFundSuspendDate()));
			}
			if(this.get(i).getFundCloseDate() == null || this.get(i).getFundCloseDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getFundCloseDate()));
			}
			if(this.get(i).getFundMaturityDate() == null || this.get(i).getFundMaturityDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getFundMaturityDate()));
			}
			pstmt.setInt(36, this.get(i).getTNFlag1());
			pstmt.setDouble(37, this.get(i).getFundMoney());
			if(this.get(i).getFundClass() == null || this.get(i).getFundClass().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getFundClass());
			}
			if(this.get(i).getDueCurreny() == null || this.get(i).getDueCurreny().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getDueCurreny());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskInsuAcc");
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
			tError.moduleName = "LMRiskInsuAccDBSet";
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
