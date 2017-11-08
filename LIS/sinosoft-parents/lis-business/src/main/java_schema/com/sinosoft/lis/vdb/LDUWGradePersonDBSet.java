/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDUWGradePersonSchema;
import com.sinosoft.lis.vschema.LDUWGradePersonSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDUWGradePersonDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWGradePersonDBSet extends LDUWGradePersonSet
{
private static Logger logger = Logger.getLogger(LDUWGradePersonDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDUWGradePersonDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDUWGradePerson");
		mflag = true;
	}

	public LDUWGradePersonDBSet()
	{
		db = new DBOper( "LDUWGradePerson" );
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
			tError.moduleName = "LDUWGradePersonDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDUWGradePerson WHERE  UWPopedom = ? AND RiskCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getUWPopedom() == null || this.get(i).getUWPopedom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getUWPopedom());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDUWGradePerson");
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
			tError.moduleName = "LDUWGradePersonDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDUWGradePerson SET  UWPopedom = ? , UWCom = ? , UWKind = ? , StandFlag = ? , HealthFlag = ? , DelayFlag = ? , BusiRange = ? , MaxAmnt = ? , MaxIllAmnt = ? , MaxDieAmnt = ? , OthMaxAmnt = ? , MaxPrem = ? , Rate = ? , OthRate = ? , Flag = ? , Flag1 = ? , AddPoint = ? , UWType = ? , UWPopedomName = ? , RiskCode = ? , Mult = ? WHERE  UWPopedom = ? AND RiskCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getUWPopedom() == null || this.get(i).getUWPopedom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getUWPopedom());
			}
			if(this.get(i).getUWCom() == null || this.get(i).getUWCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getUWCom());
			}
			if(this.get(i).getUWKind() == null || this.get(i).getUWKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getUWKind());
			}
			if(this.get(i).getStandFlag() == null || this.get(i).getStandFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getStandFlag());
			}
			if(this.get(i).getHealthFlag() == null || this.get(i).getHealthFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getHealthFlag());
			}
			if(this.get(i).getDelayFlag() == null || this.get(i).getDelayFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDelayFlag());
			}
			if(this.get(i).getBusiRange() == null || this.get(i).getBusiRange().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBusiRange());
			}
			pstmt.setDouble(8, this.get(i).getMaxAmnt());
			pstmt.setDouble(9, this.get(i).getMaxIllAmnt());
			pstmt.setDouble(10, this.get(i).getMaxDieAmnt());
			pstmt.setDouble(11, this.get(i).getOthMaxAmnt());
			pstmt.setDouble(12, this.get(i).getMaxPrem());
			pstmt.setDouble(13, this.get(i).getRate());
			pstmt.setDouble(14, this.get(i).getOthRate());
			if(this.get(i).getFlag() == null || this.get(i).getFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getFlag());
			}
			if(this.get(i).getFlag1() == null || this.get(i).getFlag1().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getFlag1());
			}
			pstmt.setDouble(17, this.get(i).getAddPoint());
			if(this.get(i).getUWType() == null || this.get(i).getUWType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getUWType());
			}
			if(this.get(i).getUWPopedomName() == null || this.get(i).getUWPopedomName().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getUWPopedomName());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRiskCode());
			}
			pstmt.setDouble(21, this.get(i).getMult());
			// set where condition
			if(this.get(i).getUWPopedom() == null || this.get(i).getUWPopedom().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUWPopedom());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRiskCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDUWGradePerson");
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
			tError.moduleName = "LDUWGradePersonDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDUWGradePerson(UWPopedom ,UWCom ,UWKind ,StandFlag ,HealthFlag ,DelayFlag ,BusiRange ,MaxAmnt ,MaxIllAmnt ,MaxDieAmnt ,OthMaxAmnt ,MaxPrem ,Rate ,OthRate ,Flag ,Flag1 ,AddPoint ,UWType ,UWPopedomName ,RiskCode ,Mult) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getUWPopedom() == null || this.get(i).getUWPopedom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getUWPopedom());
			}
			if(this.get(i).getUWCom() == null || this.get(i).getUWCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getUWCom());
			}
			if(this.get(i).getUWKind() == null || this.get(i).getUWKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getUWKind());
			}
			if(this.get(i).getStandFlag() == null || this.get(i).getStandFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getStandFlag());
			}
			if(this.get(i).getHealthFlag() == null || this.get(i).getHealthFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getHealthFlag());
			}
			if(this.get(i).getDelayFlag() == null || this.get(i).getDelayFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDelayFlag());
			}
			if(this.get(i).getBusiRange() == null || this.get(i).getBusiRange().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBusiRange());
			}
			pstmt.setDouble(8, this.get(i).getMaxAmnt());
			pstmt.setDouble(9, this.get(i).getMaxIllAmnt());
			pstmt.setDouble(10, this.get(i).getMaxDieAmnt());
			pstmt.setDouble(11, this.get(i).getOthMaxAmnt());
			pstmt.setDouble(12, this.get(i).getMaxPrem());
			pstmt.setDouble(13, this.get(i).getRate());
			pstmt.setDouble(14, this.get(i).getOthRate());
			if(this.get(i).getFlag() == null || this.get(i).getFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getFlag());
			}
			if(this.get(i).getFlag1() == null || this.get(i).getFlag1().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getFlag1());
			}
			pstmt.setDouble(17, this.get(i).getAddPoint());
			if(this.get(i).getUWType() == null || this.get(i).getUWType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getUWType());
			}
			if(this.get(i).getUWPopedomName() == null || this.get(i).getUWPopedomName().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getUWPopedomName());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRiskCode());
			}
			pstmt.setDouble(21, this.get(i).getMult());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDUWGradePerson");
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
			tError.moduleName = "LDUWGradePersonDBSet";
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
