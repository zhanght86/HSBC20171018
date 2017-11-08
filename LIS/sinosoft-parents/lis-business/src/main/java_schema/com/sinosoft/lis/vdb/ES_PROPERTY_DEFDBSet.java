/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.ES_PROPERTY_DEFSchema;
import com.sinosoft.lis.vschema.ES_PROPERTY_DEFSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: ES_PROPERTY_DEFDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug
 */
public class ES_PROPERTY_DEFDBSet extends ES_PROPERTY_DEFSet
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
	public ES_PROPERTY_DEFDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"ES_PROPERTY_DEF");
		mflag = true;
	}

	public ES_PROPERTY_DEFDBSet()
	{
		db = new DBOper( "ES_PROPERTY_DEF" );
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
			tError.moduleName = "ES_PROPERTY_DEFDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM ES_PROPERTY_DEF WHERE  DefCode = ? AND PropField = ? AND BussType = ? AND SubType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDefCode() == null || this.get(i).getDefCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDefCode());
			}
			if(this.get(i).getPropField() == null || this.get(i).getPropField().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPropField());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBussType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSubType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_PROPERTY_DEF");
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
			tError.moduleName = "ES_PROPERTY_DEFDBSet";
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
			pstmt = con.prepareStatement("UPDATE ES_PROPERTY_DEF SET  DefCode = ? , PropField = ? , PropCode = ? , PropName = ? , FieldOrder = ? , BussType = ? , SubType = ? , Version = ? , CtrlType = ? , CtrlTop = ? , CtrlLeft = ? , CtrlHeight = ? , CtrlWidth = ? , CtrlVisible = ? , CtrlTitle = ? , CtrlForm = ? , ListCodeType = ? , CtrlDefaultValue = ? , Remark = ? , ValidFlag = ? WHERE  DefCode = ? AND PropField = ? AND BussType = ? AND SubType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDefCode() == null || this.get(i).getDefCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDefCode());
			}
			if(this.get(i).getPropField() == null || this.get(i).getPropField().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPropField());
			}
			if(this.get(i).getPropCode() == null || this.get(i).getPropCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPropCode());
			}
			if(this.get(i).getPropName() == null || this.get(i).getPropName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPropName());
			}
			pstmt.setInt(5, this.get(i).getFieldOrder());
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBussType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSubType());
			}
			if(this.get(i).getVersion() == null || this.get(i).getVersion().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getVersion());
			}
			if(this.get(i).getCtrlType() == null || this.get(i).getCtrlType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCtrlType());
			}
			pstmt.setInt(10, this.get(i).getCtrlTop());
			pstmt.setInt(11, this.get(i).getCtrlLeft());
			pstmt.setInt(12, this.get(i).getCtrlHeight());
			pstmt.setInt(13, this.get(i).getCtrlWidth());
			if(this.get(i).getCtrlVisible() == null || this.get(i).getCtrlVisible().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCtrlVisible());
			}
			if(this.get(i).getCtrlTitle() == null || this.get(i).getCtrlTitle().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCtrlTitle());
			}
			if(this.get(i).getCtrlForm() == null || this.get(i).getCtrlForm().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCtrlForm());
			}
			if(this.get(i).getListCodeType() == null || this.get(i).getListCodeType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getListCodeType());
			}
			if(this.get(i).getCtrlDefaultValue() == null || this.get(i).getCtrlDefaultValue().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCtrlDefaultValue());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRemark());
			}
			if(this.get(i).getValidFlag() == null || this.get(i).getValidFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getValidFlag());
			}
			// set where condition
			if(this.get(i).getDefCode() == null || this.get(i).getDefCode().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getDefCode());
			}
			if(this.get(i).getPropField() == null || this.get(i).getPropField().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPropField());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getBussType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSubType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_PROPERTY_DEF");
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
			tError.moduleName = "ES_PROPERTY_DEFDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO ES_PROPERTY_DEF VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getDefCode() == null || this.get(i).getDefCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getDefCode());
			}
			if(this.get(i).getPropField() == null || this.get(i).getPropField().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPropField());
			}
			if(this.get(i).getPropCode() == null || this.get(i).getPropCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPropCode());
			}
			if(this.get(i).getPropName() == null || this.get(i).getPropName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPropName());
			}
			pstmt.setInt(5, this.get(i).getFieldOrder());
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBussType());
			}
			if(this.get(i).getSubType() == null || this.get(i).getSubType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSubType());
			}
			if(this.get(i).getVersion() == null || this.get(i).getVersion().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getVersion());
			}
			if(this.get(i).getCtrlType() == null || this.get(i).getCtrlType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCtrlType());
			}
			pstmt.setInt(10, this.get(i).getCtrlTop());
			pstmt.setInt(11, this.get(i).getCtrlLeft());
			pstmt.setInt(12, this.get(i).getCtrlHeight());
			pstmt.setInt(13, this.get(i).getCtrlWidth());
			if(this.get(i).getCtrlVisible() == null || this.get(i).getCtrlVisible().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCtrlVisible());
			}
			if(this.get(i).getCtrlTitle() == null || this.get(i).getCtrlTitle().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCtrlTitle());
			}
			if(this.get(i).getCtrlForm() == null || this.get(i).getCtrlForm().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCtrlForm());
			}
			if(this.get(i).getListCodeType() == null || this.get(i).getListCodeType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getListCodeType());
			}
			if(this.get(i).getCtrlDefaultValue() == null || this.get(i).getCtrlDefaultValue().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCtrlDefaultValue());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRemark());
			}
			if(this.get(i).getValidFlag() == null || this.get(i).getValidFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getValidFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_PROPERTY_DEF");
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
			tError.moduleName = "ES_PROPERTY_DEFDBSet";
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
