

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIFeeRateTableSchema;
import com.sinosoft.lis.vschema.RIFeeRateTableSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIFeeRateTableDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIFeeRateTableDBSet extends RIFeeRateTableSet
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
	public RIFeeRateTableDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIFeeRateTable");
		mflag = true;
	}

	public RIFeeRateTableDBSet()
	{
		db = new DBOper( "RIFeeRateTable" );
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
			tError.moduleName = "RIFeeRateTableDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIFeeRateTable WHERE ");
            for (int i = 1; i <= tCount; i++)
            {

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIFeeRateTable");
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
			tError.moduleName = "RIFeeRateTableDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIFeeRateTable SET  FeeTableNo = ? , FeeTableName = ? , BatchNo = ? , NumOne = ? , NumTwo = ? , NumThree = ? , NumFour = ? , NumFive = ? , NumSix = ? , NumSeven = ? , NumEight = ? , NumNine = ? , NumTen = ? , StrOne = ? , StrTwo = ? , StrThree = ? , StrFour = ? , StrFive = ? , FeeRate = ? WHERE ");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeTableNo() == null || this.get(i).getFeeTableNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeTableNo());
			}
			if(this.get(i).getFeeTableName() == null || this.get(i).getFeeTableName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeTableName());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBatchNo());
			}
			pstmt.setDouble(4, this.get(i).getNumOne());
			pstmt.setDouble(5, this.get(i).getNumTwo());
			pstmt.setDouble(6, this.get(i).getNumThree());
			pstmt.setDouble(7, this.get(i).getNumFour());
			pstmt.setDouble(8, this.get(i).getNumFive());
			pstmt.setDouble(9, this.get(i).getNumSix());
			pstmt.setDouble(10, this.get(i).getNumSeven());
			pstmt.setDouble(11, this.get(i).getNumEight());
			pstmt.setDouble(12, this.get(i).getNumNine());
			pstmt.setDouble(13, this.get(i).getNumTen());
			if(this.get(i).getStrOne() == null || this.get(i).getStrOne().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getStrOne());
			}
			if(this.get(i).getStrTwo() == null || this.get(i).getStrTwo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getStrTwo());
			}
			if(this.get(i).getStrThree() == null || this.get(i).getStrThree().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStrThree());
			}
			if(this.get(i).getStrFour() == null || this.get(i).getStrFour().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStrFour());
			}
			if(this.get(i).getStrFive() == null || this.get(i).getStrFive().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStrFive());
			}
			pstmt.setDouble(19, this.get(i).getFeeRate());
			// set where condition

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIFeeRateTable");
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
			tError.moduleName = "RIFeeRateTableDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIFeeRateTable(FeeTableNo ,FeeTableName ,BatchNo ,NumOne ,NumTwo ,NumThree ,NumFour ,NumFive ,NumSix ,NumSeven ,NumEight ,NumNine ,NumTen ,StrOne ,StrTwo ,StrThree ,StrFour ,StrFive ,FeeRate) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeTableNo() == null || this.get(i).getFeeTableNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeTableNo());
			}
			if(this.get(i).getFeeTableName() == null || this.get(i).getFeeTableName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeTableName());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBatchNo());
			}
			pstmt.setDouble(4, this.get(i).getNumOne());
			pstmt.setDouble(5, this.get(i).getNumTwo());
			pstmt.setDouble(6, this.get(i).getNumThree());
			pstmt.setDouble(7, this.get(i).getNumFour());
			pstmt.setDouble(8, this.get(i).getNumFive());
			pstmt.setDouble(9, this.get(i).getNumSix());
			pstmt.setDouble(10, this.get(i).getNumSeven());
			pstmt.setDouble(11, this.get(i).getNumEight());
			pstmt.setDouble(12, this.get(i).getNumNine());
			pstmt.setDouble(13, this.get(i).getNumTen());
			if(this.get(i).getStrOne() == null || this.get(i).getStrOne().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getStrOne());
			}
			if(this.get(i).getStrTwo() == null || this.get(i).getStrTwo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getStrTwo());
			}
			if(this.get(i).getStrThree() == null || this.get(i).getStrThree().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStrThree());
			}
			if(this.get(i).getStrFour() == null || this.get(i).getStrFour().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStrFour());
			}
			if(this.get(i).getStrFive() == null || this.get(i).getStrFive().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStrFive());
			}
			pstmt.setDouble(19, this.get(i).getFeeRate());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIFeeRateTable");
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
			tError.moduleName = "RIFeeRateTableDBSet";
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
