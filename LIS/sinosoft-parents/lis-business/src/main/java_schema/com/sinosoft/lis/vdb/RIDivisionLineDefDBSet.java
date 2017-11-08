

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIDivisionLineDefSchema;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIDivisionLineDefDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIDivisionLineDefDBSet extends RIDivisionLineDefSet
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
	public RIDivisionLineDefDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIDivisionLineDef");
		mflag = true;
	}

	public RIDivisionLineDefDBSet()
	{
		db = new DBOper( "RIDivisionLineDef" );
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
			tError.moduleName = "RIDivisionLineDefDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIDivisionLineDef WHERE  RIPreceptNo = ? AND DivisionLineOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRIPreceptNo());
			}
			pstmt.setInt(2, this.get(i).getDivisionLineOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIDivisionLineDef");
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
			tError.moduleName = "RIDivisionLineDefDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIDivisionLineDef SET  RIContNo = ? , RIPreceptNo = ? , DivisionLineType = ? , DivisionLineOrder = ? , DivisionLineValue = ? , FactorCode = ? , DivisionFactor = ? , State = ? , Currency = ? , StandByOne = ? , StandByTwo = ? WHERE  RIPreceptNo = ? AND DivisionLineOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRIContNo() == null || this.get(i).getRIContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRIContNo());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRIPreceptNo());
			}
			if(this.get(i).getDivisionLineType() == null || this.get(i).getDivisionLineType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDivisionLineType());
			}
			pstmt.setInt(4, this.get(i).getDivisionLineOrder());
			pstmt.setDouble(5, this.get(i).getDivisionLineValue());
			if(this.get(i).getFactorCode() == null || this.get(i).getFactorCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFactorCode());
			}
			if(this.get(i).getDivisionFactor() == null || this.get(i).getDivisionFactor().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDivisionFactor());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCurrency());
			}
			if(this.get(i).getStandByOne() == null || this.get(i).getStandByOne().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getStandByOne());
			}
			if(this.get(i).getStandByTwo() == null || this.get(i).getStandByTwo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStandByTwo());
			}
			// set where condition
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRIPreceptNo());
			}
			pstmt.setInt(13, this.get(i).getDivisionLineOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIDivisionLineDef");
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
			tError.moduleName = "RIDivisionLineDefDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIDivisionLineDef(RIContNo ,RIPreceptNo ,DivisionLineType ,DivisionLineOrder ,DivisionLineValue ,FactorCode ,DivisionFactor ,State ,Currency ,StandByOne ,StandByTwo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRIContNo() == null || this.get(i).getRIContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRIContNo());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRIPreceptNo());
			}
			if(this.get(i).getDivisionLineType() == null || this.get(i).getDivisionLineType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDivisionLineType());
			}
			pstmt.setInt(4, this.get(i).getDivisionLineOrder());
			pstmt.setDouble(5, this.get(i).getDivisionLineValue());
			if(this.get(i).getFactorCode() == null || this.get(i).getFactorCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFactorCode());
			}
			if(this.get(i).getDivisionFactor() == null || this.get(i).getDivisionFactor().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDivisionFactor());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCurrency());
			}
			if(this.get(i).getStandByOne() == null || this.get(i).getStandByOne().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getStandByOne());
			}
			if(this.get(i).getStandByTwo() == null || this.get(i).getStandByTwo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStandByTwo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIDivisionLineDef");
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
			tError.moduleName = "RIDivisionLineDefDBSet";
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
