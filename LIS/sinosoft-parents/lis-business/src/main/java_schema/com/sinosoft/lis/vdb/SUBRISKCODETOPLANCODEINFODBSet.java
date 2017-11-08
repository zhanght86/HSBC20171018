

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.SUBRISKCODETOPLANCODEINFOSchema;
import com.sinosoft.lis.vschema.SUBRISKCODETOPLANCODEINFOSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: SUBRISKCODETOPLANCODEINFODBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class SUBRISKCODETOPLANCODEINFODBSet extends SUBRISKCODETOPLANCODEINFOSet
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
	public SUBRISKCODETOPLANCODEINFODBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"SUBRISKCODETOPLANCODEINFO");
		mflag = true;
	}

	public SUBRISKCODETOPLANCODEINFODBSet()
	{
		db = new DBOper( "SUBRISKCODETOPLANCODEINFO" );
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
			tError.moduleName = "SUBRISKCODETOPLANCODEINFODBSet";
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
			pstmt = con.prepareStatement("DELETE FROM SUBRISKCODETOPLANCODEINFO WHERE  RISKCODE = ? AND PLANCODE = ? AND SUBRISKCODE = ? AND SUBPLANCODE = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRISKCODE() == null || this.get(i).getRISKCODE().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRISKCODE());
			}
			if(this.get(i).getPLANCODE() == null || this.get(i).getPLANCODE().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPLANCODE());
			}
			if(this.get(i).getSUBRISKCODE() == null || this.get(i).getSUBRISKCODE().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSUBRISKCODE());
			}
			if(this.get(i).getSUBPLANCODE() == null || this.get(i).getSUBPLANCODE().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSUBPLANCODE());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("SUBRISKCODETOPLANCODEINFO");
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
			tError.moduleName = "SUBRISKCODETOPLANCODEINFODBSet";
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
			pstmt = con.prepareStatement("UPDATE SUBRISKCODETOPLANCODEINFO SET  RISKCODE = ? , PLANCODE = ? , SUBRISKCODE = ? , SUBPLANCODE = ? , BATCHNO = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? , ISBENEFITOPTION = ? , BENEFITOPTIONTYPE = ? , BENEFITOPTION = ? WHERE  RISKCODE = ? AND PLANCODE = ? AND SUBRISKCODE = ? AND SUBPLANCODE = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRISKCODE() == null || this.get(i).getRISKCODE().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRISKCODE());
			}
			if(this.get(i).getPLANCODE() == null || this.get(i).getPLANCODE().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPLANCODE());
			}
			if(this.get(i).getSUBRISKCODE() == null || this.get(i).getSUBRISKCODE().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSUBRISKCODE());
			}
			if(this.get(i).getSUBPLANCODE() == null || this.get(i).getSUBPLANCODE().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSUBPLANCODE());
			}
			pstmt.setInt(5, this.get(i).getBATCHNO());
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOPERATOR());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMAKETIME());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMODIFYTIME());
			}
			if(this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStandByFlag1());
			}
			if(this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStandByFlag2());
			}
			if(this.get(i).getStandByFlag3() == null || this.get(i).getStandByFlag3().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStandByFlag3());
			}
			if(this.get(i).getISBENEFITOPTION() == null || this.get(i).getISBENEFITOPTION().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getISBENEFITOPTION());
			}
			if(this.get(i).getBENEFITOPTIONTYPE() == null || this.get(i).getBENEFITOPTIONTYPE().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBENEFITOPTIONTYPE());
			}
			if(this.get(i).getBENEFITOPTION() == null || this.get(i).getBENEFITOPTION().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBENEFITOPTION());
			}
			// set where condition
			if(this.get(i).getRISKCODE() == null || this.get(i).getRISKCODE().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRISKCODE());
			}
			if(this.get(i).getPLANCODE() == null || this.get(i).getPLANCODE().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPLANCODE());
			}
			if(this.get(i).getSUBRISKCODE() == null || this.get(i).getSUBRISKCODE().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getSUBRISKCODE());
			}
			if(this.get(i).getSUBPLANCODE() == null || this.get(i).getSUBPLANCODE().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSUBPLANCODE());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("SUBRISKCODETOPLANCODEINFO");
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
			tError.moduleName = "SUBRISKCODETOPLANCODEINFODBSet";
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
			pstmt = con.prepareStatement("INSERT INTO SUBRISKCODETOPLANCODEINFO(RISKCODE ,PLANCODE ,SUBRISKCODE ,SUBPLANCODE ,BATCHNO ,OPERATOR ,MAKEDATE ,MAKETIME ,MODIFYDATE ,MODIFYTIME ,StandByFlag1 ,StandByFlag2 ,StandByFlag3 ,ISBENEFITOPTION ,BENEFITOPTIONTYPE ,BENEFITOPTION) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRISKCODE() == null || this.get(i).getRISKCODE().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRISKCODE());
			}
			if(this.get(i).getPLANCODE() == null || this.get(i).getPLANCODE().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPLANCODE());
			}
			if(this.get(i).getSUBRISKCODE() == null || this.get(i).getSUBRISKCODE().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSUBRISKCODE());
			}
			if(this.get(i).getSUBPLANCODE() == null || this.get(i).getSUBPLANCODE().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSUBPLANCODE());
			}
			pstmt.setInt(5, this.get(i).getBATCHNO());
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOPERATOR());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMAKETIME());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMODIFYTIME());
			}
			if(this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStandByFlag1());
			}
			if(this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStandByFlag2());
			}
			if(this.get(i).getStandByFlag3() == null || this.get(i).getStandByFlag3().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStandByFlag3());
			}
			if(this.get(i).getISBENEFITOPTION() == null || this.get(i).getISBENEFITOPTION().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getISBENEFITOPTION());
			}
			if(this.get(i).getBENEFITOPTIONTYPE() == null || this.get(i).getBENEFITOPTIONTYPE().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBENEFITOPTIONTYPE());
			}
			if(this.get(i).getBENEFITOPTION() == null || this.get(i).getBENEFITOPTION().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBENEFITOPTION());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("SUBRISKCODETOPLANCODEINFO");
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
			tError.moduleName = "SUBRISKCODETOPLANCODEINFODBSet";
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

