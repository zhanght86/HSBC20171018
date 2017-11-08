

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.vschema.RIAccumulateDefSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIAccumulateDefDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIAccumulateDefDBSet extends RIAccumulateDefSet
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
	public RIAccumulateDefDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIAccumulateDef");
		mflag = true;
	}

	public RIAccumulateDefDBSet()
	{
		db = new DBOper( "RIAccumulateDef" );
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
			tError.moduleName = "RIAccumulateDefDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIAccumulateDef WHERE  AccumulateDefNO = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAccumulateDefNO());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIAccumulateDef");
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
			tError.moduleName = "RIAccumulateDefDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIAccumulateDef SET  AccumulateDefNO = ? , AccumulateDefName = ? , DeTailFlag = ? , AccumulateMode = ? , GetDutyType = ? , RiskAmntFlag = ? , ArithmeticID = ? , GIType = ? , StandbyFlag = ? , State = ? , Currency = ? , DIntv = ? , BFFlag = ? WHERE  AccumulateDefNO = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getAccumulateDefName() == null || this.get(i).getAccumulateDefName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAccumulateDefName());
			}
			if(this.get(i).getDeTailFlag() == null || this.get(i).getDeTailFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDeTailFlag());
			}
			if(this.get(i).getAccumulateMode() == null || this.get(i).getAccumulateMode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccumulateMode());
			}
			if(this.get(i).getGetDutyType() == null || this.get(i).getGetDutyType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getGetDutyType());
			}
			if(this.get(i).getRiskAmntFlag() == null || this.get(i).getRiskAmntFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskAmntFlag());
			}
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getArithmeticID());
			}
			if(this.get(i).getGIType() == null || this.get(i).getGIType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGIType());
			}
			if(this.get(i).getStandbyFlag() == null || this.get(i).getStandbyFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getStandbyFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCurrency());
			}
			if(this.get(i).getDIntv() == null || this.get(i).getDIntv().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getDIntv());
			}
			if(this.get(i).getBFFlag() == null || this.get(i).getBFFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getBFFlag());
			}
			// set where condition
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAccumulateDefNO());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIAccumulateDef");
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
			tError.moduleName = "RIAccumulateDefDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIAccumulateDef(AccumulateDefNO ,AccumulateDefName ,DeTailFlag ,AccumulateMode ,GetDutyType ,RiskAmntFlag ,ArithmeticID ,GIType ,StandbyFlag ,State ,Currency ,DIntv ,BFFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getAccumulateDefName() == null || this.get(i).getAccumulateDefName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAccumulateDefName());
			}
			if(this.get(i).getDeTailFlag() == null || this.get(i).getDeTailFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDeTailFlag());
			}
			if(this.get(i).getAccumulateMode() == null || this.get(i).getAccumulateMode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccumulateMode());
			}
			if(this.get(i).getGetDutyType() == null || this.get(i).getGetDutyType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getGetDutyType());
			}
			if(this.get(i).getRiskAmntFlag() == null || this.get(i).getRiskAmntFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskAmntFlag());
			}
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getArithmeticID());
			}
			if(this.get(i).getGIType() == null || this.get(i).getGIType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGIType());
			}
			if(this.get(i).getStandbyFlag() == null || this.get(i).getStandbyFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getStandbyFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCurrency());
			}
			if(this.get(i).getDIntv() == null || this.get(i).getDIntv().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getDIntv());
			}
			if(this.get(i).getBFFlag() == null || this.get(i).getBFFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getBFFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIAccumulateDef");
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
			tError.moduleName = "RIAccumulateDefDBSet";
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
