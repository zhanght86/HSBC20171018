

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RICalParamBSchema;
import com.sinosoft.lis.vschema.RICalParamBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RICalParamBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RICalParamBDBSet extends RICalParamBSet
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
	public RICalParamBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RICalParamB");
		mflag = true;
	}

	public RICalParamBDBSet()
	{
		db = new DBOper( "RICalParamB" );
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
			tError.moduleName = "RICalParamBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RICalParamB WHERE  BakeSerialNo = ? AND SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBakeSerialNo() == null || this.get(i).getBakeSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBakeSerialNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RICalParamB");
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
			tError.moduleName = "RICalParamBDBSet";
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
			pstmt = con.prepareStatement("UPDATE RICalParamB SET  BakeSerialNo = ? , SerialNo = ? , EventNo = ? , BatchNo = ? , OtherNo = ? , ContNo = ? , RiskCode = ? , DutyCode = ? , AreaID = ? , AccumulateDefNO = ? , RIPreceptNo = ? , ParamDouble1 = ? , ParamDouble2 = ? , ParamDouble3 = ? , ParamDouble4 = ? , ParamDouble5 = ? , ParamDouble6 = ? , ParamDouble7 = ? , ParamDouble8 = ? , ParamDouble9 = ? , ParamDouble10 = ? , ParamString1 = ? , ParamString2 = ? , ParamString3 = ? , ParamString4 = ? , ParamString5 = ? , ParamString6 = ? , ParamDate1 = ? , ParamDate2 = ? , ParamDate3 = ? WHERE  BakeSerialNo = ? AND SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBakeSerialNo() == null || this.get(i).getBakeSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBakeSerialNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSerialNo());
			}
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEventNo());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatchNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOtherNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDutyCode());
			}
			if(this.get(i).getAreaID() == null || this.get(i).getAreaID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAreaID());
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRIPreceptNo());
			}
			pstmt.setDouble(12, this.get(i).getParamDouble1());
			pstmt.setDouble(13, this.get(i).getParamDouble2());
			pstmt.setDouble(14, this.get(i).getParamDouble3());
			pstmt.setDouble(15, this.get(i).getParamDouble4());
			pstmt.setDouble(16, this.get(i).getParamDouble5());
			pstmt.setDouble(17, this.get(i).getParamDouble6());
			pstmt.setDouble(18, this.get(i).getParamDouble7());
			pstmt.setDouble(19, this.get(i).getParamDouble8());
			pstmt.setDouble(20, this.get(i).getParamDouble9());
			pstmt.setDouble(21, this.get(i).getParamDouble10());
			if(this.get(i).getParamString1() == null || this.get(i).getParamString1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getParamString1());
			}
			if(this.get(i).getParamString2() == null || this.get(i).getParamString2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getParamString2());
			}
			if(this.get(i).getParamString3() == null || this.get(i).getParamString3().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getParamString3());
			}
			if(this.get(i).getParamString4() == null || this.get(i).getParamString4().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getParamString4());
			}
			if(this.get(i).getParamString5() == null || this.get(i).getParamString5().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getParamString5());
			}
			if(this.get(i).getParamString6() == null || this.get(i).getParamString6().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getParamString6());
			}
			if(this.get(i).getParamDate1() == null || this.get(i).getParamDate1().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getParamDate1()));
			}
			if(this.get(i).getParamDate2() == null || this.get(i).getParamDate2().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getParamDate2()));
			}
			if(this.get(i).getParamDate3() == null || this.get(i).getParamDate3().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getParamDate3()));
			}
			// set where condition
			if(this.get(i).getBakeSerialNo() == null || this.get(i).getBakeSerialNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBakeSerialNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RICalParamB");
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
			tError.moduleName = "RICalParamBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RICalParamB(BakeSerialNo ,SerialNo ,EventNo ,BatchNo ,OtherNo ,ContNo ,RiskCode ,DutyCode ,AreaID ,AccumulateDefNO ,RIPreceptNo ,ParamDouble1 ,ParamDouble2 ,ParamDouble3 ,ParamDouble4 ,ParamDouble5 ,ParamDouble6 ,ParamDouble7 ,ParamDouble8 ,ParamDouble9 ,ParamDouble10 ,ParamString1 ,ParamString2 ,ParamString3 ,ParamString4 ,ParamString5 ,ParamString6 ,ParamDate1 ,ParamDate2 ,ParamDate3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBakeSerialNo() == null || this.get(i).getBakeSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBakeSerialNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSerialNo());
			}
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEventNo());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatchNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOtherNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDutyCode());
			}
			if(this.get(i).getAreaID() == null || this.get(i).getAreaID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAreaID());
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRIPreceptNo());
			}
			pstmt.setDouble(12, this.get(i).getParamDouble1());
			pstmt.setDouble(13, this.get(i).getParamDouble2());
			pstmt.setDouble(14, this.get(i).getParamDouble3());
			pstmt.setDouble(15, this.get(i).getParamDouble4());
			pstmt.setDouble(16, this.get(i).getParamDouble5());
			pstmt.setDouble(17, this.get(i).getParamDouble6());
			pstmt.setDouble(18, this.get(i).getParamDouble7());
			pstmt.setDouble(19, this.get(i).getParamDouble8());
			pstmt.setDouble(20, this.get(i).getParamDouble9());
			pstmt.setDouble(21, this.get(i).getParamDouble10());
			if(this.get(i).getParamString1() == null || this.get(i).getParamString1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getParamString1());
			}
			if(this.get(i).getParamString2() == null || this.get(i).getParamString2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getParamString2());
			}
			if(this.get(i).getParamString3() == null || this.get(i).getParamString3().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getParamString3());
			}
			if(this.get(i).getParamString4() == null || this.get(i).getParamString4().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getParamString4());
			}
			if(this.get(i).getParamString5() == null || this.get(i).getParamString5().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getParamString5());
			}
			if(this.get(i).getParamString6() == null || this.get(i).getParamString6().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getParamString6());
			}
			if(this.get(i).getParamDate1() == null || this.get(i).getParamDate1().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getParamDate1()));
			}
			if(this.get(i).getParamDate2() == null || this.get(i).getParamDate2().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getParamDate2()));
			}
			if(this.get(i).getParamDate3() == null || this.get(i).getParamDate3().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getParamDate3()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RICalParamB");
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
			tError.moduleName = "RICalParamBDBSet";
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
