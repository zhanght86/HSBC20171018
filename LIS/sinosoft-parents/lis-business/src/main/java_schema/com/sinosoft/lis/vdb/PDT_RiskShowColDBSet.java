/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PDT_RiskShowColSchema;
import com.sinosoft.lis.vschema.PDT_RiskShowColSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PDT_RiskShowColDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 险种测试功能
 */
public class PDT_RiskShowColDBSet extends PDT_RiskShowColSet
{
private static Logger logger = Logger.getLogger(PDT_RiskShowColDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public PDT_RiskShowColDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PDT_RiskShowCol");
		mflag = true;
	}

	public PDT_RiskShowColDBSet()
	{
		db = new DBOper( "PDT_RiskShowCol" );
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
			tError.moduleName = "PDT_RiskShowColDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PDT_RiskShowCol WHERE  TemplateID = ? AND ShowType = ? AND ColCode = ? AND ColName = ? AND ColOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTemplateID() == null || this.get(i).getTemplateID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTemplateID());
			}
			if(this.get(i).getShowType() == null || this.get(i).getShowType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getShowType());
			}
			if(this.get(i).getColCode() == null || this.get(i).getColCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getColCode());
			}
			if(this.get(i).getColName() == null || this.get(i).getColName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getColName());
			}
			pstmt.setInt(5, this.get(i).getColOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PDT_RiskShowCol");
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
			tError.moduleName = "PDT_RiskShowColDBSet";
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
			pstmt = con.prepareStatement("UPDATE PDT_RiskShowCol SET  TemplateID = ? , ShowType = ? , ColCode = ? , ColName = ? , ColOrder = ? , OptionFlag = ? , ColProperties = ? , FilterSql = ? WHERE  TemplateID = ? AND ShowType = ? AND ColCode = ? AND ColName = ? AND ColOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTemplateID() == null || this.get(i).getTemplateID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTemplateID());
			}
			if(this.get(i).getShowType() == null || this.get(i).getShowType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getShowType());
			}
			if(this.get(i).getColCode() == null || this.get(i).getColCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getColCode());
			}
			if(this.get(i).getColName() == null || this.get(i).getColName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getColName());
			}
			pstmt.setInt(5, this.get(i).getColOrder());
			if(this.get(i).getOptionFlag() == null || this.get(i).getOptionFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOptionFlag());
			}
			if(this.get(i).getColProperties() == null || this.get(i).getColProperties().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getColProperties());
			}
			if(this.get(i).getFilterSql() == null || this.get(i).getFilterSql().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFilterSql());
			}
			// set where condition
			if(this.get(i).getTemplateID() == null || this.get(i).getTemplateID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getTemplateID());
			}
			if(this.get(i).getShowType() == null || this.get(i).getShowType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getShowType());
			}
			if(this.get(i).getColCode() == null || this.get(i).getColCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getColCode());
			}
			if(this.get(i).getColName() == null || this.get(i).getColName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getColName());
			}
			pstmt.setInt(13, this.get(i).getColOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PDT_RiskShowCol");
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
			tError.moduleName = "PDT_RiskShowColDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PDT_RiskShowCol VALUES( ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTemplateID() == null || this.get(i).getTemplateID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTemplateID());
			}
			if(this.get(i).getShowType() == null || this.get(i).getShowType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getShowType());
			}
			if(this.get(i).getColCode() == null || this.get(i).getColCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getColCode());
			}
			if(this.get(i).getColName() == null || this.get(i).getColName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getColName());
			}
			pstmt.setInt(5, this.get(i).getColOrder());
			if(this.get(i).getOptionFlag() == null || this.get(i).getOptionFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOptionFlag());
			}
			if(this.get(i).getColProperties() == null || this.get(i).getColProperties().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getColProperties());
			}
			if(this.get(i).getFilterSql() == null || this.get(i).getFilterSql().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFilterSql());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PDT_RiskShowCol");
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
			tError.moduleName = "PDT_RiskShowColDBSet";
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
