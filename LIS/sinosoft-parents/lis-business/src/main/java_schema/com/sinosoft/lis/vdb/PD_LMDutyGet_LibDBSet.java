/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMDutyGet_LibSchema;
import com.sinosoft.lis.vschema.PD_LMDutyGet_LibSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyGet_LibDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyGet_LibDBSet extends PD_LMDutyGet_LibSet
{
private static Logger logger = Logger.getLogger(PD_LMDutyGet_LibDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public PD_LMDutyGet_LibDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_LMDutyGet_Lib");
		mflag = true;
	}

	public PD_LMDutyGet_LibDBSet()
	{
		db = new DBOper( "PD_LMDutyGet_Lib" );
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
			tError.moduleName = "PD_LMDutyGet_LibDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMDutyGet_Lib WHERE  GetDutyCode2 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode2() == null || this.get(i).getGetDutyCode2().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
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
			tError.moduleName = "PD_LMDutyGet_LibDBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_LMDutyGet_Lib SET  GetDutyCode2 = ? , CalCode = ? , GetDutyName = ? , Type = ? , GetIntv = ? , DefaultVal = ? , CnterCalCode = ? , OthCalCode = ? , GetYear = ? , GetYearFlag = ? , StartDateCalRef = ? , StartDateCaLmode = ? , MinGetStartPeriod = ? , GetEndPeriod = ? , GetEndUnit = ? , EndDateCalRef = ? , EndDateCalMode = ? , MaxGetEndPeriod = ? , AddFlag = ? , SexRelaFlag = ? , UnitAppRelaFlag = ? , AddAmntFlag = ? , DiscntFlag = ? , InterestFlag = ? , ShareFlag = ? , InpFlag = ? , BnfFlag = ? , UrgeGetFlag = ? , DeadValiFlag = ? , GetInitFlag = ? , GetLimit = ? , MaxGet = ? , GetRate = ? , NeedAcc = ? , CanGet = ? , NeedCancelAcc = ? , GetType1 = ? , ZeroFlag = ? , GetType2 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  GetDutyCode2 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode2() == null || this.get(i).getGetDutyCode2().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode2());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCalCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyName());
			}
			if(this.get(i).getType() == null || this.get(i).getType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getType());
			}
			pstmt.setInt(5, this.get(i).getGetIntv());
			pstmt.setDouble(6, this.get(i).getDefaultVal());
			if(this.get(i).getCnterCalCode() == null || this.get(i).getCnterCalCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCnterCalCode());
			}
			if(this.get(i).getOthCalCode() == null || this.get(i).getOthCalCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getOthCalCode());
			}
			pstmt.setInt(9, this.get(i).getGetYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGetYearFlag());
			}
			if(this.get(i).getStartDateCalRef() == null || this.get(i).getStartDateCalRef().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStartDateCalRef());
			}
			if(this.get(i).getStartDateCaLmode() == null || this.get(i).getStartDateCaLmode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStartDateCaLmode());
			}
			pstmt.setInt(13, this.get(i).getMinGetStartPeriod());
			pstmt.setInt(14, this.get(i).getGetEndPeriod());
			if(this.get(i).getGetEndUnit() == null || this.get(i).getGetEndUnit().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGetEndUnit());
			}
			if(this.get(i).getEndDateCalRef() == null || this.get(i).getEndDateCalRef().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEndDateCalRef());
			}
			if(this.get(i).getEndDateCalMode() == null || this.get(i).getEndDateCalMode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getEndDateCalMode());
			}
			pstmt.setInt(18, this.get(i).getMaxGetEndPeriod());
			if(this.get(i).getAddFlag() == null || this.get(i).getAddFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAddFlag());
			}
			if(this.get(i).getSexRelaFlag() == null || this.get(i).getSexRelaFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSexRelaFlag());
			}
			if(this.get(i).getUnitAppRelaFlag() == null || this.get(i).getUnitAppRelaFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getUnitAppRelaFlag());
			}
			if(this.get(i).getAddAmntFlag() == null || this.get(i).getAddAmntFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAddAmntFlag());
			}
			if(this.get(i).getDiscntFlag() == null || this.get(i).getDiscntFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getDiscntFlag());
			}
			if(this.get(i).getInterestFlag() == null || this.get(i).getInterestFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getInterestFlag());
			}
			if(this.get(i).getShareFlag() == null || this.get(i).getShareFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getShareFlag());
			}
			if(this.get(i).getInpFlag() == null || this.get(i).getInpFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getInpFlag());
			}
			if(this.get(i).getBnfFlag() == null || this.get(i).getBnfFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getBnfFlag());
			}
			if(this.get(i).getUrgeGetFlag() == null || this.get(i).getUrgeGetFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getUrgeGetFlag());
			}
			if(this.get(i).getDeadValiFlag() == null || this.get(i).getDeadValiFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getDeadValiFlag());
			}
			if(this.get(i).getGetInitFlag() == null || this.get(i).getGetInitFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getGetInitFlag());
			}
			pstmt.setDouble(31, this.get(i).getGetLimit());
			pstmt.setDouble(32, this.get(i).getMaxGet());
			pstmt.setDouble(33, this.get(i).getGetRate());
			if(this.get(i).getNeedAcc() == null || this.get(i).getNeedAcc().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getNeedAcc());
			}
			if(this.get(i).getCanGet() == null || this.get(i).getCanGet().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCanGet());
			}
			if(this.get(i).getNeedCancelAcc() == null || this.get(i).getNeedCancelAcc().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getNeedCancelAcc());
			}
			if(this.get(i).getGetType1() == null || this.get(i).getGetType1().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getGetType1());
			}
			if(this.get(i).getZeroFlag() == null || this.get(i).getZeroFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getZeroFlag());
			}
			if(this.get(i).getGetType2() == null || this.get(i).getGetType2().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetType2());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(47, this.get(i).getStandbyflag3());
			pstmt.setInt(48, this.get(i).getStandbyflag4());
			pstmt.setDouble(49, this.get(i).getStandbyflag5());
			pstmt.setDouble(50, this.get(i).getStandbyflag6());
			// set where condition
			if(this.get(i).getGetDutyCode2() == null || this.get(i).getGetDutyCode2().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getGetDutyCode2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
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
			tError.moduleName = "PD_LMDutyGet_LibDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_LMDutyGet_Lib(GetDutyCode2 ,CalCode ,GetDutyName ,Type ,GetIntv ,DefaultVal ,CnterCalCode ,OthCalCode ,GetYear ,GetYearFlag ,StartDateCalRef ,StartDateCaLmode ,MinGetStartPeriod ,GetEndPeriod ,GetEndUnit ,EndDateCalRef ,EndDateCalMode ,MaxGetEndPeriod ,AddFlag ,SexRelaFlag ,UnitAppRelaFlag ,AddAmntFlag ,DiscntFlag ,InterestFlag ,ShareFlag ,InpFlag ,BnfFlag ,UrgeGetFlag ,DeadValiFlag ,GetInitFlag ,GetLimit ,MaxGet ,GetRate ,NeedAcc ,CanGet ,NeedCancelAcc ,GetType1 ,ZeroFlag ,GetType2 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode2() == null || this.get(i).getGetDutyCode2().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode2());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCalCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyName());
			}
			if(this.get(i).getType() == null || this.get(i).getType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getType());
			}
			pstmt.setInt(5, this.get(i).getGetIntv());
			pstmt.setDouble(6, this.get(i).getDefaultVal());
			if(this.get(i).getCnterCalCode() == null || this.get(i).getCnterCalCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCnterCalCode());
			}
			if(this.get(i).getOthCalCode() == null || this.get(i).getOthCalCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getOthCalCode());
			}
			pstmt.setInt(9, this.get(i).getGetYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGetYearFlag());
			}
			if(this.get(i).getStartDateCalRef() == null || this.get(i).getStartDateCalRef().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStartDateCalRef());
			}
			if(this.get(i).getStartDateCaLmode() == null || this.get(i).getStartDateCaLmode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStartDateCaLmode());
			}
			pstmt.setInt(13, this.get(i).getMinGetStartPeriod());
			pstmt.setInt(14, this.get(i).getGetEndPeriod());
			if(this.get(i).getGetEndUnit() == null || this.get(i).getGetEndUnit().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGetEndUnit());
			}
			if(this.get(i).getEndDateCalRef() == null || this.get(i).getEndDateCalRef().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEndDateCalRef());
			}
			if(this.get(i).getEndDateCalMode() == null || this.get(i).getEndDateCalMode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getEndDateCalMode());
			}
			pstmt.setInt(18, this.get(i).getMaxGetEndPeriod());
			if(this.get(i).getAddFlag() == null || this.get(i).getAddFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAddFlag());
			}
			if(this.get(i).getSexRelaFlag() == null || this.get(i).getSexRelaFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSexRelaFlag());
			}
			if(this.get(i).getUnitAppRelaFlag() == null || this.get(i).getUnitAppRelaFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getUnitAppRelaFlag());
			}
			if(this.get(i).getAddAmntFlag() == null || this.get(i).getAddAmntFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAddAmntFlag());
			}
			if(this.get(i).getDiscntFlag() == null || this.get(i).getDiscntFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getDiscntFlag());
			}
			if(this.get(i).getInterestFlag() == null || this.get(i).getInterestFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getInterestFlag());
			}
			if(this.get(i).getShareFlag() == null || this.get(i).getShareFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getShareFlag());
			}
			if(this.get(i).getInpFlag() == null || this.get(i).getInpFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getInpFlag());
			}
			if(this.get(i).getBnfFlag() == null || this.get(i).getBnfFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getBnfFlag());
			}
			if(this.get(i).getUrgeGetFlag() == null || this.get(i).getUrgeGetFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getUrgeGetFlag());
			}
			if(this.get(i).getDeadValiFlag() == null || this.get(i).getDeadValiFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getDeadValiFlag());
			}
			if(this.get(i).getGetInitFlag() == null || this.get(i).getGetInitFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getGetInitFlag());
			}
			pstmt.setDouble(31, this.get(i).getGetLimit());
			pstmt.setDouble(32, this.get(i).getMaxGet());
			pstmt.setDouble(33, this.get(i).getGetRate());
			if(this.get(i).getNeedAcc() == null || this.get(i).getNeedAcc().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getNeedAcc());
			}
			if(this.get(i).getCanGet() == null || this.get(i).getCanGet().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCanGet());
			}
			if(this.get(i).getNeedCancelAcc() == null || this.get(i).getNeedCancelAcc().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getNeedCancelAcc());
			}
			if(this.get(i).getGetType1() == null || this.get(i).getGetType1().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getGetType1());
			}
			if(this.get(i).getZeroFlag() == null || this.get(i).getZeroFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getZeroFlag());
			}
			if(this.get(i).getGetType2() == null || this.get(i).getGetType2().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetType2());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(47, this.get(i).getStandbyflag3());
			pstmt.setInt(48, this.get(i).getStandbyflag4());
			pstmt.setDouble(49, this.get(i).getStandbyflag5());
			pstmt.setDouble(50, this.get(i).getStandbyflag6());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
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
			tError.moduleName = "PD_LMDutyGet_LibDBSet";
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
