/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMDutyGetAliveSchema;
import com.sinosoft.lis.vschema.LMDutyGetAliveSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMDutyGetAliveDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyGetAliveDBSet extends LMDutyGetAliveSet
{
private static Logger logger = Logger.getLogger(LMDutyGetAliveDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMDutyGetAliveDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMDutyGetAlive");
		mflag = true;
	}

	public LMDutyGetAliveDBSet()
	{
		db = new DBOper( "LMDutyGetAlive" );
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
			tError.moduleName = "LMDutyGetAliveDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMDutyGetAlive WHERE  GetDutyCode = ? AND GetDutyKind = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetAlive");
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
			tError.moduleName = "LMDutyGetAliveDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMDutyGetAlive SET  GetDutyCode = ? , GetDutyName = ? , GetDutyKind = ? , GetIntv = ? , DefaultVal = ? , CalCode = ? , CnterCalCode = ? , OthCalCode = ? , GetStartPeriod = ? , GetStartUnit = ? , StartDateCalRef = ? , StartDateCalMode = ? , MinGetStartPeriod = ? , GetEndPeriod = ? , GetEndUnit = ? , EndDateCalRef = ? , EndDateCalMode = ? , MaxGetEndPeriod = ? , AddFlag = ? , AddIntv = ? , AddStartPeriod = ? , AddStartUnit = ? , AddEndPeriod = ? , AddEndUnit = ? , AddType = ? , AddValue = ? , MaxGetCount = ? , AfterGet = ? , GetActionType = ? , UrgeGetFlag = ? , DiscntFlag = ? , GetCond = ? , MaxGetCountType = ? , NeedReCompute = ? WHERE  GetDutyCode = ? AND GetDutyKind = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyName());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyKind());
			}
			pstmt.setInt(4, this.get(i).getGetIntv());
			pstmt.setDouble(5, this.get(i).getDefaultVal());
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode());
			}
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
			pstmt.setInt(9, this.get(i).getGetStartPeriod());
			if(this.get(i).getGetStartUnit() == null || this.get(i).getGetStartUnit().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGetStartUnit());
			}
			if(this.get(i).getStartDateCalRef() == null || this.get(i).getStartDateCalRef().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStartDateCalRef());
			}
			if(this.get(i).getStartDateCalMode() == null || this.get(i).getStartDateCalMode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStartDateCalMode());
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
			pstmt.setInt(20, this.get(i).getAddIntv());
			pstmt.setInt(21, this.get(i).getAddStartPeriod());
			if(this.get(i).getAddStartUnit() == null || this.get(i).getAddStartUnit().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAddStartUnit());
			}
			pstmt.setInt(23, this.get(i).getAddEndPeriod());
			if(this.get(i).getAddEndUnit() == null || this.get(i).getAddEndUnit().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAddEndUnit());
			}
			if(this.get(i).getAddType() == null || this.get(i).getAddType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAddType());
			}
			pstmt.setDouble(26, this.get(i).getAddValue());
			pstmt.setInt(27, this.get(i).getMaxGetCount());
			if(this.get(i).getAfterGet() == null || this.get(i).getAfterGet().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAfterGet());
			}
			if(this.get(i).getGetActionType() == null || this.get(i).getGetActionType().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getGetActionType());
			}
			if(this.get(i).getUrgeGetFlag() == null || this.get(i).getUrgeGetFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getUrgeGetFlag());
			}
			if(this.get(i).getDiscntFlag() == null || this.get(i).getDiscntFlag().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDiscntFlag());
			}
			if(this.get(i).getGetCond() == null || this.get(i).getGetCond().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getGetCond());
			}
			if(this.get(i).getMaxGetCountType() == null || this.get(i).getMaxGetCountType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getMaxGetCountType());
			}
			if(this.get(i).getNeedReCompute() == null || this.get(i).getNeedReCompute().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getNeedReCompute());
			}
			// set where condition
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetAlive");
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
			tError.moduleName = "LMDutyGetAliveDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMDutyGetAlive(GetDutyCode ,GetDutyName ,GetDutyKind ,GetIntv ,DefaultVal ,CalCode ,CnterCalCode ,OthCalCode ,GetStartPeriod ,GetStartUnit ,StartDateCalRef ,StartDateCalMode ,MinGetStartPeriod ,GetEndPeriod ,GetEndUnit ,EndDateCalRef ,EndDateCalMode ,MaxGetEndPeriod ,AddFlag ,AddIntv ,AddStartPeriod ,AddStartUnit ,AddEndPeriod ,AddEndUnit ,AddType ,AddValue ,MaxGetCount ,AfterGet ,GetActionType ,UrgeGetFlag ,DiscntFlag ,GetCond ,MaxGetCountType ,NeedReCompute) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyName());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyKind());
			}
			pstmt.setInt(4, this.get(i).getGetIntv());
			pstmt.setDouble(5, this.get(i).getDefaultVal());
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode());
			}
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
			pstmt.setInt(9, this.get(i).getGetStartPeriod());
			if(this.get(i).getGetStartUnit() == null || this.get(i).getGetStartUnit().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGetStartUnit());
			}
			if(this.get(i).getStartDateCalRef() == null || this.get(i).getStartDateCalRef().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStartDateCalRef());
			}
			if(this.get(i).getStartDateCalMode() == null || this.get(i).getStartDateCalMode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStartDateCalMode());
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
			pstmt.setInt(20, this.get(i).getAddIntv());
			pstmt.setInt(21, this.get(i).getAddStartPeriod());
			if(this.get(i).getAddStartUnit() == null || this.get(i).getAddStartUnit().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAddStartUnit());
			}
			pstmt.setInt(23, this.get(i).getAddEndPeriod());
			if(this.get(i).getAddEndUnit() == null || this.get(i).getAddEndUnit().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAddEndUnit());
			}
			if(this.get(i).getAddType() == null || this.get(i).getAddType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAddType());
			}
			pstmt.setDouble(26, this.get(i).getAddValue());
			pstmt.setInt(27, this.get(i).getMaxGetCount());
			if(this.get(i).getAfterGet() == null || this.get(i).getAfterGet().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAfterGet());
			}
			if(this.get(i).getGetActionType() == null || this.get(i).getGetActionType().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getGetActionType());
			}
			if(this.get(i).getUrgeGetFlag() == null || this.get(i).getUrgeGetFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getUrgeGetFlag());
			}
			if(this.get(i).getDiscntFlag() == null || this.get(i).getDiscntFlag().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDiscntFlag());
			}
			if(this.get(i).getGetCond() == null || this.get(i).getGetCond().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getGetCond());
			}
			if(this.get(i).getMaxGetCountType() == null || this.get(i).getMaxGetCountType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getMaxGetCountType());
			}
			if(this.get(i).getNeedReCompute() == null || this.get(i).getNeedReCompute().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getNeedReCompute());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetAlive");
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
			tError.moduleName = "LMDutyGetAliveDBSet";
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
