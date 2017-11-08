

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMDutyDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LMDutyDBSet extends LMDutySet
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
	public LMDutyDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMDuty");
		mflag = true;
	}

	public LMDutyDBSet()
	{
		db = new DBOper( "LMDuty" );
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
			tError.moduleName = "LMDutyDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMDuty WHERE  DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDuty");
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
			tError.moduleName = "LMDutyDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMDuty SET  DutyCode = ? , DutyName = ? , PayEndYear = ? , PayEndYearFlag = ? , PayEndDateCalRef = ? , PayEndDateCalMode = ? , GetYear = ? , GetYearFlag = ? , InsuYear = ? , InsuYearFlag = ? , AcciYear = ? , AcciYearFlag = ? , CalMode = ? , PayEndYearRela = ? , GetYearRela = ? , InsuYearRela = ? , BasicCalCode = ? , RiskCalCode = ? , AmntFlag = ? , VPU = ? , AddFeeFlag = ? , EndDateCalMode = ? , AddAmntFlag = ? , PCalMode = ? WHERE  DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDutyCode());
			}
			if(this.get(i).getDutyName() == null || this.get(i).getDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyName());
			}
			pstmt.setInt(3, this.get(i).getPayEndYear());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayEndYearFlag());
			}
			if(this.get(i).getPayEndDateCalRef() == null || this.get(i).getPayEndDateCalRef().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPayEndDateCalRef());
			}
			if(this.get(i).getPayEndDateCalMode() == null || this.get(i).getPayEndDateCalMode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayEndDateCalMode());
			}
			pstmt.setInt(7, this.get(i).getGetYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(9, this.get(i).getInsuYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(11, this.get(i).getAcciYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAcciYearFlag());
			}
			if(this.get(i).getCalMode() == null || this.get(i).getCalMode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalMode());
			}
			if(this.get(i).getPayEndYearRela() == null || this.get(i).getPayEndYearRela().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getPayEndYearRela());
			}
			if(this.get(i).getGetYearRela() == null || this.get(i).getGetYearRela().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGetYearRela());
			}
			if(this.get(i).getInsuYearRela() == null || this.get(i).getInsuYearRela().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getInsuYearRela());
			}
			if(this.get(i).getBasicCalCode() == null || this.get(i).getBasicCalCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getBasicCalCode());
			}
			if(this.get(i).getRiskCalCode() == null || this.get(i).getRiskCalCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskCalCode());
			}
			if(this.get(i).getAmntFlag() == null || this.get(i).getAmntFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAmntFlag());
			}
			pstmt.setDouble(20, this.get(i).getVPU());
			if(this.get(i).getAddFeeFlag() == null || this.get(i).getAddFeeFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAddFeeFlag());
			}
			if(this.get(i).getEndDateCalMode() == null || this.get(i).getEndDateCalMode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getEndDateCalMode());
			}
			if(this.get(i).getAddAmntFlag() == null || this.get(i).getAddAmntFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAddAmntFlag());
			}
			if(this.get(i).getPCalMode() == null || this.get(i).getPCalMode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPCalMode());
			}
			// set where condition
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDuty");
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
			tError.moduleName = "LMDutyDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMDuty(DutyCode ,DutyName ,PayEndYear ,PayEndYearFlag ,PayEndDateCalRef ,PayEndDateCalMode ,GetYear ,GetYearFlag ,InsuYear ,InsuYearFlag ,AcciYear ,AcciYearFlag ,CalMode ,PayEndYearRela ,GetYearRela ,InsuYearRela ,BasicCalCode ,RiskCalCode ,AmntFlag ,VPU ,AddFeeFlag ,EndDateCalMode ,AddAmntFlag ,PCalMode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDutyCode());
			}
			if(this.get(i).getDutyName() == null || this.get(i).getDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyName());
			}
			pstmt.setInt(3, this.get(i).getPayEndYear());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayEndYearFlag());
			}
			if(this.get(i).getPayEndDateCalRef() == null || this.get(i).getPayEndDateCalRef().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPayEndDateCalRef());
			}
			if(this.get(i).getPayEndDateCalMode() == null || this.get(i).getPayEndDateCalMode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayEndDateCalMode());
			}
			pstmt.setInt(7, this.get(i).getGetYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(9, this.get(i).getInsuYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(11, this.get(i).getAcciYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAcciYearFlag());
			}
			if(this.get(i).getCalMode() == null || this.get(i).getCalMode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalMode());
			}
			if(this.get(i).getPayEndYearRela() == null || this.get(i).getPayEndYearRela().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getPayEndYearRela());
			}
			if(this.get(i).getGetYearRela() == null || this.get(i).getGetYearRela().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGetYearRela());
			}
			if(this.get(i).getInsuYearRela() == null || this.get(i).getInsuYearRela().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getInsuYearRela());
			}
			if(this.get(i).getBasicCalCode() == null || this.get(i).getBasicCalCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getBasicCalCode());
			}
			if(this.get(i).getRiskCalCode() == null || this.get(i).getRiskCalCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskCalCode());
			}
			if(this.get(i).getAmntFlag() == null || this.get(i).getAmntFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAmntFlag());
			}
			pstmt.setDouble(20, this.get(i).getVPU());
			if(this.get(i).getAddFeeFlag() == null || this.get(i).getAddFeeFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAddFeeFlag());
			}
			if(this.get(i).getEndDateCalMode() == null || this.get(i).getEndDateCalMode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getEndDateCalMode());
			}
			if(this.get(i).getAddAmntFlag() == null || this.get(i).getAddAmntFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAddAmntFlag());
			}
			if(this.get(i).getPCalMode() == null || this.get(i).getPCalMode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPCalMode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDuty");
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
			tError.moduleName = "LMDutyDBSet";
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
