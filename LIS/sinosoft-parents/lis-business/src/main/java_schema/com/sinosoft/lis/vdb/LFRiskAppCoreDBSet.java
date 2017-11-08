/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LFRiskAppCoreSchema;
import com.sinosoft.lis.vschema.LFRiskAppCoreSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LFRiskAppCoreDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LFRiskAppCoreDBSet extends LFRiskAppCoreSet
{
private static Logger logger = Logger.getLogger(LFRiskAppCoreDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LFRiskAppCoreDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LFRiskAppCore");
		mflag = true;
	}

	public LFRiskAppCoreDBSet()
	{
		db = new DBOper( "LFRiskAppCore" );
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
			tError.moduleName = "LFRiskAppCoreDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LFRiskAppCore WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ? AND MeasurementType = ? AND SickType = ? AND FeeType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, StrTool.space1(this.get(i).getRiskCode(), 6));
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space1(this.get(i).getManageCom(), 10));
			}
			pstmt.setInt(3, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, StrTool.space1(this.get(i).getSaleChnl(), 2));
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, StrTool.space1(this.get(i).getFirstPayFlag(), 1));
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, StrTool.space1(this.get(i).getPersonPolFlag(), 1));
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getReportDate()));
			}
			if(this.get(i).getMeasurementType() == null || this.get(i).getMeasurementType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, StrTool.space1(this.get(i).getMeasurementType(), 2));
			}
			if(this.get(i).getSickType() == null || this.get(i).getSickType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, StrTool.space1(this.get(i).getSickType(), 2));
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, StrTool.space1(this.get(i).getFeeType(), 2));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskAppCore");
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
			tError.moduleName = "LFRiskAppCoreDBSet";
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
			pstmt = con.prepareStatement("UPDATE LFRiskAppCore SET  RiskCode = ? , ManageCom = ? , PayIntv = ? , SaleChnl = ? , FirstPayFlag = ? , PersonPolFlag = ? , ReportDate = ? , MeasurementType = ? , CurYearValue = ? , MakeDate = ? , MakeTime = ? , SickType = ? , FeeType = ? WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ? AND MeasurementType = ? AND SickType = ? AND FeeType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getManageCom());
			}
			pstmt.setInt(3, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSaleChnl());
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFirstPayFlag());
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPersonPolFlag());
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getReportDate()));
			}
			if(this.get(i).getMeasurementType() == null || this.get(i).getMeasurementType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMeasurementType());
			}
			pstmt.setDouble(9, this.get(i).getCurYearValue());
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMakeTime());
			}
			if(this.get(i).getSickType() == null || this.get(i).getSickType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSickType());
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getFeeType());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, StrTool.space1(this.get(i).getRiskCode(), 6));
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, StrTool.space1(this.get(i).getManageCom(), 10));
			}
			pstmt.setInt(16, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, StrTool.space1(this.get(i).getSaleChnl(), 2));
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, StrTool.space1(this.get(i).getFirstPayFlag(), 1));
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, StrTool.space1(this.get(i).getPersonPolFlag(), 1));
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getReportDate()));
			}
			if(this.get(i).getMeasurementType() == null || this.get(i).getMeasurementType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, StrTool.space1(this.get(i).getMeasurementType(), 2));
			}
			if(this.get(i).getSickType() == null || this.get(i).getSickType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, StrTool.space1(this.get(i).getSickType(), 2));
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, StrTool.space1(this.get(i).getFeeType(), 2));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskAppCore");
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
			tError.moduleName = "LFRiskAppCoreDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LFRiskAppCore(RiskCode ,ManageCom ,PayIntv ,SaleChnl ,FirstPayFlag ,PersonPolFlag ,ReportDate ,MeasurementType ,CurYearValue ,MakeDate ,MakeTime ,SickType ,FeeType) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getManageCom());
			}
			pstmt.setInt(3, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSaleChnl());
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFirstPayFlag());
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPersonPolFlag());
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getReportDate()));
			}
			if(this.get(i).getMeasurementType() == null || this.get(i).getMeasurementType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMeasurementType());
			}
			pstmt.setDouble(9, this.get(i).getCurYearValue());
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMakeTime());
			}
			if(this.get(i).getSickType() == null || this.get(i).getSickType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSickType());
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getFeeType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskAppCore");
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
			tError.moduleName = "LFRiskAppCoreDBSet";
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
