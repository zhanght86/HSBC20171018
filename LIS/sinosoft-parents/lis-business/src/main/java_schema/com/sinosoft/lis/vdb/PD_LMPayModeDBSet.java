/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMPayModeSchema;
import com.sinosoft.lis.vschema.PD_LMPayModeSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMPayModeDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMPayModeDBSet extends PD_LMPayModeSet
{
private static Logger logger = Logger.getLogger(PD_LMPayModeDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public PD_LMPayModeDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_LMPayMode");
		mflag = true;
	}

	public PD_LMPayModeDBSet()
	{
		db = new DBOper( "PD_LMPayMode" );
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
			tError.moduleName = "PD_LMPayModeDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMPayMode WHERE  RiskCode = ? AND PayEndYearFlag = ? AND PayEndYear = ? AND PayIntv = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space1(this.get(i).getPayEndYearFlag(), 1));
			}
			pstmt.setInt(3, this.get(i).getPayEndYear());
			pstmt.setInt(4, this.get(i).getPayIntv());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMPayMode");
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
			tError.moduleName = "PD_LMPayModeDBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_LMPayMode SET  RiskCode = ? , PayEndYearFlag = ? , PayEndYear = ? , PayIntv = ? , RiskVer = ? , PayToExpiryFlag = ? , ShowInfo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  RiskCode = ? AND PayEndYearFlag = ? AND PayEndYear = ? AND PayIntv = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(3, this.get(i).getPayEndYear());
			pstmt.setInt(4, this.get(i).getPayIntv());
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskVer());
			}
			if(this.get(i).getPayToExpiryFlag() == null || this.get(i).getPayToExpiryFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayToExpiryFlag());
			}
			if(this.get(i).getShowInfo() == null || this.get(i).getShowInfo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getShowInfo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(15, this.get(i).getStandbyflag3());
			pstmt.setInt(16, this.get(i).getStandbyflag4());
			pstmt.setDouble(17, this.get(i).getStandbyflag5());
			pstmt.setDouble(18, this.get(i).getStandbyflag6());
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskCode());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, StrTool.space1(this.get(i).getPayEndYearFlag(), 1));
			}
			pstmt.setInt(21, this.get(i).getPayEndYear());
			pstmt.setInt(22, this.get(i).getPayIntv());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMPayMode");
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
			tError.moduleName = "PD_LMPayModeDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_LMPayMode(RiskCode ,PayEndYearFlag ,PayEndYear ,PayIntv ,RiskVer ,PayToExpiryFlag ,ShowInfo ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(3, this.get(i).getPayEndYear());
			pstmt.setInt(4, this.get(i).getPayIntv());
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskVer());
			}
			if(this.get(i).getPayToExpiryFlag() == null || this.get(i).getPayToExpiryFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayToExpiryFlag());
			}
			if(this.get(i).getShowInfo() == null || this.get(i).getShowInfo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getShowInfo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(15, this.get(i).getStandbyflag3());
			pstmt.setInt(16, this.get(i).getStandbyflag4());
			pstmt.setDouble(17, this.get(i).getStandbyflag5());
			pstmt.setDouble(18, this.get(i).getStandbyflag6());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMPayMode");
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
			tError.moduleName = "PD_LMPayModeDBSet";
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
