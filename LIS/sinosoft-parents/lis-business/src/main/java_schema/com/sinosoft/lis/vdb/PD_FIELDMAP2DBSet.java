/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.PD_FIELDMAP2Schema;
import com.sinosoft.lis.vschema.PD_FIELDMAP2Set;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_FIELDMAP2DBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台
 */
public class PD_FIELDMAP2DBSet extends PD_FIELDMAP2Set
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
	public PD_FIELDMAP2DBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_FIELDMAP2");
		mflag = true;
	}

	public PD_FIELDMAP2DBSet()
	{
		db = new DBOper( "PD_FIELDMAP2" );
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
			tError.moduleName = "PD_FIELDMAP2DBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_FIELDMAP2 WHERE  TABLECODE1 = ? AND TABLECODE2 = ? AND FIELDCODE1 = ? AND FIELDCODE2 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTABLECODE1() == null || this.get(i).getTABLECODE1().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTABLECODE1());
			}
			if(this.get(i).getTABLECODE2() == null || this.get(i).getTABLECODE2().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTABLECODE2());
			}
			if(this.get(i).getFIELDCODE1() == null || this.get(i).getFIELDCODE1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getFIELDCODE1());
			}
			if(this.get(i).getFIELDCODE2() == null || this.get(i).getFIELDCODE2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFIELDCODE2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_FIELDMAP2");
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
			tError.moduleName = "PD_FIELDMAP2DBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_FIELDMAP2 SET  TABLECODE1 = ? , TABLECODE2 = ? , FIELDCODE1 = ? , FIELDCODE2 = ? , FIELDTYPE1 = ? , ISPDTABLE1PRIMARY = ? , FIELDTYPE2 = ? , ISTABLE2PRIMARY = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? , STANDBYFLAG1 = ? , STANDBYFLAG2 = ? , STANDBYFLAG3 = ? , STANDBYFLAG4 = ? , STANDBYFLAG5 = ? , STANDBYFLAG6 = ? WHERE  TABLECODE1 = ? AND TABLECODE2 = ? AND FIELDCODE1 = ? AND FIELDCODE2 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTABLECODE1() == null || this.get(i).getTABLECODE1().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTABLECODE1());
			}
			if(this.get(i).getTABLECODE2() == null || this.get(i).getTABLECODE2().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTABLECODE2());
			}
			if(this.get(i).getFIELDCODE1() == null || this.get(i).getFIELDCODE1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getFIELDCODE1());
			}
			if(this.get(i).getFIELDCODE2() == null || this.get(i).getFIELDCODE2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFIELDCODE2());
			}
			if(this.get(i).getFIELDTYPE1() == null || this.get(i).getFIELDTYPE1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFIELDTYPE1());
			}
			if(this.get(i).getISPDTABLE1PRIMARY() == null || this.get(i).getISPDTABLE1PRIMARY().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getISPDTABLE1PRIMARY());
			}
			if(this.get(i).getFIELDTYPE2() == null || this.get(i).getFIELDTYPE2().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFIELDTYPE2());
			}
			if(this.get(i).getISTABLE2PRIMARY() == null || this.get(i).getISTABLE2PRIMARY().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getISTABLE2PRIMARY());
			}
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getOPERATOR());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMAKETIME());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getMODIFYTIME());
			}
			if(this.get(i).getSTANDBYFLAG1() == null || this.get(i).getSTANDBYFLAG1().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSTANDBYFLAG1());
			}
			if(this.get(i).getSTANDBYFLAG2() == null || this.get(i).getSTANDBYFLAG2().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getSTANDBYFLAG2());
			}
			pstmt.setInt(16, this.get(i).getSTANDBYFLAG3());
			pstmt.setInt(17, this.get(i).getSTANDBYFLAG4());
			pstmt.setDouble(18, this.get(i).getSTANDBYFLAG5());
			pstmt.setDouble(19, this.get(i).getSTANDBYFLAG6());
			// set where condition
			if(this.get(i).getTABLECODE1() == null || this.get(i).getTABLECODE1().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getTABLECODE1());
			}
			if(this.get(i).getTABLECODE2() == null || this.get(i).getTABLECODE2().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getTABLECODE2());
			}
			if(this.get(i).getFIELDCODE1() == null || this.get(i).getFIELDCODE1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getFIELDCODE1());
			}
			if(this.get(i).getFIELDCODE2() == null || this.get(i).getFIELDCODE2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getFIELDCODE2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_FIELDMAP2");
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
			tError.moduleName = "PD_FIELDMAP2DBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_FIELDMAP2 VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTABLECODE1() == null || this.get(i).getTABLECODE1().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTABLECODE1());
			}
			if(this.get(i).getTABLECODE2() == null || this.get(i).getTABLECODE2().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTABLECODE2());
			}
			if(this.get(i).getFIELDCODE1() == null || this.get(i).getFIELDCODE1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getFIELDCODE1());
			}
			if(this.get(i).getFIELDCODE2() == null || this.get(i).getFIELDCODE2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFIELDCODE2());
			}
			if(this.get(i).getFIELDTYPE1() == null || this.get(i).getFIELDTYPE1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFIELDTYPE1());
			}
			if(this.get(i).getISPDTABLE1PRIMARY() == null || this.get(i).getISPDTABLE1PRIMARY().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getISPDTABLE1PRIMARY());
			}
			if(this.get(i).getFIELDTYPE2() == null || this.get(i).getFIELDTYPE2().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFIELDTYPE2());
			}
			if(this.get(i).getISTABLE2PRIMARY() == null || this.get(i).getISTABLE2PRIMARY().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getISTABLE2PRIMARY());
			}
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getOPERATOR());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMAKETIME());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getMODIFYTIME());
			}
			if(this.get(i).getSTANDBYFLAG1() == null || this.get(i).getSTANDBYFLAG1().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSTANDBYFLAG1());
			}
			if(this.get(i).getSTANDBYFLAG2() == null || this.get(i).getSTANDBYFLAG2().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getSTANDBYFLAG2());
			}
			pstmt.setInt(16, this.get(i).getSTANDBYFLAG3());
			pstmt.setInt(17, this.get(i).getSTANDBYFLAG4());
			pstmt.setDouble(18, this.get(i).getSTANDBYFLAG5());
			pstmt.setDouble(19, this.get(i).getSTANDBYFLAG6());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_FIELDMAP2");
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
			tError.moduleName = "PD_FIELDMAP2DBSet";
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
