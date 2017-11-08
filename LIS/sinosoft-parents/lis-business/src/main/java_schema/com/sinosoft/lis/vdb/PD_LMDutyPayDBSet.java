

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.vschema.PD_LMDutyPaySet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyPayDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_3
 */
public class PD_LMDutyPayDBSet extends PD_LMDutyPaySet
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
	public PD_LMDutyPayDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_LMDutyPay");
		mflag = true;
	}

	public PD_LMDutyPayDBSet()
	{
		db = new DBOper( "PD_LMDutyPay" );
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
			tError.moduleName = "PD_LMDutyPayDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMDutyPay WHERE  PayPlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPayPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyPay");
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
			tError.moduleName = "PD_LMDutyPayDBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_LMDutyPay SET  PayPlanCode = ? , DutyCode = ? , CalCode = ? , PayPlanName = ? , Type = ? , PayIntv = ? , PayEndYearFlag = ? , PayEndYear = ? , PayEndDateCalRef = ? , PayEndDateCalMode = ? , DefaultVal = ? , CnterCalCode = ? , OthCalCode = ? , Rate = ? , MinPay = ? , AssuYield = ? , FeeRate = ? , PayToDateCalMode = ? , UrgePayFlag = ? , PayLackFlag = ? , PayOverFlag = ? , PayOverDeal = ? , AvoidPayFlag = ? , GracePeriod = ? , PubFlag = ? , ZeroFlag = ? , NeedAcc = ? , PayAimClass = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , PayStartYear = ? , PayStartYearFlag = ? , PayStartDateCalRef = ? , PayStartDateCalMode = ? , InvestType = ? , PCalCode = ? , RCalPremFlag = ? , RCalPremCode = ? WHERE  PayPlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCalCode());
			}
			if(this.get(i).getPayPlanName() == null || this.get(i).getPayPlanName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayPlanName());
			}
			if(this.get(i).getType() == null || this.get(i).getType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getType());
			}
			pstmt.setInt(6, this.get(i).getPayIntv());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(8, this.get(i).getPayEndYear());
			if(this.get(i).getPayEndDateCalRef() == null || this.get(i).getPayEndDateCalRef().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPayEndDateCalRef());
			}
			if(this.get(i).getPayEndDateCalMode() == null || this.get(i).getPayEndDateCalMode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPayEndDateCalMode());
			}
			pstmt.setDouble(11, this.get(i).getDefaultVal());
			if(this.get(i).getCnterCalCode() == null || this.get(i).getCnterCalCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCnterCalCode());
			}
			if(this.get(i).getOthCalCode() == null || this.get(i).getOthCalCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOthCalCode());
			}
			pstmt.setDouble(14, this.get(i).getRate());
			pstmt.setDouble(15, this.get(i).getMinPay());
			pstmt.setDouble(16, this.get(i).getAssuYield());
			pstmt.setDouble(17, this.get(i).getFeeRate());
			if(this.get(i).getPayToDateCalMode() == null || this.get(i).getPayToDateCalMode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayToDateCalMode());
			}
			if(this.get(i).getUrgePayFlag() == null || this.get(i).getUrgePayFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getUrgePayFlag());
			}
			if(this.get(i).getPayLackFlag() == null || this.get(i).getPayLackFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPayLackFlag());
			}
			if(this.get(i).getPayOverFlag() == null || this.get(i).getPayOverFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPayOverFlag());
			}
			if(this.get(i).getPayOverDeal() == null || this.get(i).getPayOverDeal().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPayOverDeal());
			}
			if(this.get(i).getAvoidPayFlag() == null || this.get(i).getAvoidPayFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAvoidPayFlag());
			}
			pstmt.setInt(24, this.get(i).getGracePeriod());
			if(this.get(i).getPubFlag() == null || this.get(i).getPubFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getPubFlag());
			}
			if(this.get(i).getZeroFlag() == null || this.get(i).getZeroFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getZeroFlag());
			}
			if(this.get(i).getNeedAcc() == null || this.get(i).getNeedAcc().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getNeedAcc());
			}
			if(this.get(i).getPayAimClass() == null || this.get(i).getPayAimClass().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPayAimClass());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(36, this.get(i).getStandbyflag3());
			pstmt.setInt(37, this.get(i).getStandbyflag4());
			pstmt.setDouble(38, this.get(i).getStandbyflag5());
			pstmt.setDouble(39, this.get(i).getStandbyflag6());
			pstmt.setInt(40, this.get(i).getPayStartYear());
			if(this.get(i).getPayStartYearFlag() == null || this.get(i).getPayStartYearFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPayStartYearFlag());
			}
			if(this.get(i).getPayStartDateCalRef() == null || this.get(i).getPayStartDateCalRef().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPayStartDateCalRef());
			}
			if(this.get(i).getPayStartDateCalMode() == null || this.get(i).getPayStartDateCalMode().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPayStartDateCalMode());
			}
			if(this.get(i).getInvestType() == null || this.get(i).getInvestType().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getInvestType());
			}
			if(this.get(i).getPCalCode() == null || this.get(i).getPCalCode().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getPCalCode());
			}
			if(this.get(i).getRCalPremFlag() == null || this.get(i).getRCalPremFlag().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getRCalPremFlag());
			}
			if(this.get(i).getRCalPremCode() == null || this.get(i).getRCalPremCode().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getRCalPremCode());
			}
			// set where condition
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getPayPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyPay");
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
			tError.moduleName = "PD_LMDutyPayDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_LMDutyPay(PayPlanCode ,DutyCode ,CalCode ,PayPlanName ,Type ,PayIntv ,PayEndYearFlag ,PayEndYear ,PayEndDateCalRef ,PayEndDateCalMode ,DefaultVal ,CnterCalCode ,OthCalCode ,Rate ,MinPay ,AssuYield ,FeeRate ,PayToDateCalMode ,UrgePayFlag ,PayLackFlag ,PayOverFlag ,PayOverDeal ,AvoidPayFlag ,GracePeriod ,PubFlag ,ZeroFlag ,NeedAcc ,PayAimClass ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6 ,PayStartYear ,PayStartYearFlag ,PayStartDateCalRef ,PayStartDateCalMode ,InvestType ,PCalCode ,RCalPremFlag ,RCalPremCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCalCode());
			}
			if(this.get(i).getPayPlanName() == null || this.get(i).getPayPlanName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayPlanName());
			}
			if(this.get(i).getType() == null || this.get(i).getType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getType());
			}
			pstmt.setInt(6, this.get(i).getPayIntv());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(8, this.get(i).getPayEndYear());
			if(this.get(i).getPayEndDateCalRef() == null || this.get(i).getPayEndDateCalRef().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPayEndDateCalRef());
			}
			if(this.get(i).getPayEndDateCalMode() == null || this.get(i).getPayEndDateCalMode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPayEndDateCalMode());
			}
			pstmt.setDouble(11, this.get(i).getDefaultVal());
			if(this.get(i).getCnterCalCode() == null || this.get(i).getCnterCalCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCnterCalCode());
			}
			if(this.get(i).getOthCalCode() == null || this.get(i).getOthCalCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOthCalCode());
			}
			pstmt.setDouble(14, this.get(i).getRate());
			pstmt.setDouble(15, this.get(i).getMinPay());
			pstmt.setDouble(16, this.get(i).getAssuYield());
			pstmt.setDouble(17, this.get(i).getFeeRate());
			if(this.get(i).getPayToDateCalMode() == null || this.get(i).getPayToDateCalMode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayToDateCalMode());
			}
			if(this.get(i).getUrgePayFlag() == null || this.get(i).getUrgePayFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getUrgePayFlag());
			}
			if(this.get(i).getPayLackFlag() == null || this.get(i).getPayLackFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPayLackFlag());
			}
			if(this.get(i).getPayOverFlag() == null || this.get(i).getPayOverFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPayOverFlag());
			}
			if(this.get(i).getPayOverDeal() == null || this.get(i).getPayOverDeal().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPayOverDeal());
			}
			if(this.get(i).getAvoidPayFlag() == null || this.get(i).getAvoidPayFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAvoidPayFlag());
			}
			pstmt.setInt(24, this.get(i).getGracePeriod());
			if(this.get(i).getPubFlag() == null || this.get(i).getPubFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getPubFlag());
			}
			if(this.get(i).getZeroFlag() == null || this.get(i).getZeroFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getZeroFlag());
			}
			if(this.get(i).getNeedAcc() == null || this.get(i).getNeedAcc().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getNeedAcc());
			}
			if(this.get(i).getPayAimClass() == null || this.get(i).getPayAimClass().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPayAimClass());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(36, this.get(i).getStandbyflag3());
			pstmt.setInt(37, this.get(i).getStandbyflag4());
			pstmt.setDouble(38, this.get(i).getStandbyflag5());
			pstmt.setDouble(39, this.get(i).getStandbyflag6());
			pstmt.setInt(40, this.get(i).getPayStartYear());
			if(this.get(i).getPayStartYearFlag() == null || this.get(i).getPayStartYearFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPayStartYearFlag());
			}
			if(this.get(i).getPayStartDateCalRef() == null || this.get(i).getPayStartDateCalRef().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPayStartDateCalRef());
			}
			if(this.get(i).getPayStartDateCalMode() == null || this.get(i).getPayStartDateCalMode().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPayStartDateCalMode());
			}
			if(this.get(i).getInvestType() == null || this.get(i).getInvestType().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getInvestType());
			}
			if(this.get(i).getPCalCode() == null || this.get(i).getPCalCode().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getPCalCode());
			}
			if(this.get(i).getRCalPremFlag() == null || this.get(i).getRCalPremFlag().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getRCalPremFlag());
			}
			if(this.get(i).getRCalPremCode() == null || this.get(i).getRCalPremCode().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getRCalPremCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyPay");
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
			tError.moduleName = "PD_LMDutyPayDBSet";
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
