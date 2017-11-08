

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIPreceptSchema;
import com.sinosoft.lis.vschema.RIPreceptSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIPreceptDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIPreceptDBSet extends RIPreceptSet
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
	public RIPreceptDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIPrecept");
		mflag = true;
	}

	public RIPreceptDBSet()
	{
		db = new DBOper( "RIPrecept" );
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
			tError.moduleName = "RIPreceptDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIPrecept WHERE  RIPreceptNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRIPreceptNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPrecept");
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
			tError.moduleName = "RIPreceptDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIPrecept SET  RIContNo = ? , RIPreceptNo = ? , RIPreceptName = ? , RIMainPreceptNo = ? , PreceptType = ? , ReinsuranceType = ? , ReinsuranceType1 = ? , AccumulateDefNO = ? , CompanyNum = ? , HierarchyNumType = ? , DivisionNum = ? , ArithmeticID = ? , State = ? , Currency = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? , AttachFalg = ? , Bonus = ? WHERE  RIPreceptNo = ?");
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
			if(this.get(i).getRIPreceptName() == null || this.get(i).getRIPreceptName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRIPreceptName());
			}
			if(this.get(i).getRIMainPreceptNo() == null || this.get(i).getRIMainPreceptNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRIMainPreceptNo());
			}
			if(this.get(i).getPreceptType() == null || this.get(i).getPreceptType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPreceptType());
			}
			if(this.get(i).getReinsuranceType() == null || this.get(i).getReinsuranceType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getReinsuranceType());
			}
			if(this.get(i).getReinsuranceType1() == null || this.get(i).getReinsuranceType1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getReinsuranceType1());
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccumulateDefNO());
			}
			pstmt.setInt(9, this.get(i).getCompanyNum());
			if(this.get(i).getHierarchyNumType() == null || this.get(i).getHierarchyNumType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHierarchyNumType());
			}
			pstmt.setInt(11, this.get(i).getDivisionNum());
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getArithmeticID());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCurrency());
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyString3());
			}
			if(this.get(i).getAttachFalg() == null || this.get(i).getAttachFalg().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAttachFalg());
			}
			if(this.get(i).getBonus() == null || this.get(i).getBonus().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBonus());
			}
			// set where condition
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRIPreceptNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPrecept");
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
			tError.moduleName = "RIPreceptDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIPrecept(RIContNo ,RIPreceptNo ,RIPreceptName ,RIMainPreceptNo ,PreceptType ,ReinsuranceType ,ReinsuranceType1 ,AccumulateDefNO ,CompanyNum ,HierarchyNumType ,DivisionNum ,ArithmeticID ,State ,Currency ,StandbyString1 ,StandbyString2 ,StandbyString3 ,AttachFalg ,Bonus) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.get(i).getRIPreceptName() == null || this.get(i).getRIPreceptName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRIPreceptName());
			}
			if(this.get(i).getRIMainPreceptNo() == null || this.get(i).getRIMainPreceptNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRIMainPreceptNo());
			}
			if(this.get(i).getPreceptType() == null || this.get(i).getPreceptType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPreceptType());
			}
			if(this.get(i).getReinsuranceType() == null || this.get(i).getReinsuranceType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getReinsuranceType());
			}
			if(this.get(i).getReinsuranceType1() == null || this.get(i).getReinsuranceType1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getReinsuranceType1());
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccumulateDefNO());
			}
			pstmt.setInt(9, this.get(i).getCompanyNum());
			if(this.get(i).getHierarchyNumType() == null || this.get(i).getHierarchyNumType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHierarchyNumType());
			}
			pstmt.setInt(11, this.get(i).getDivisionNum());
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getArithmeticID());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCurrency());
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyString3());
			}
			if(this.get(i).getAttachFalg() == null || this.get(i).getAttachFalg().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAttachFalg());
			}
			if(this.get(i).getBonus() == null || this.get(i).getBonus().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBonus());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPrecept");
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
			tError.moduleName = "RIPreceptDBSet";
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
