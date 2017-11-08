/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LWFieldMapSchema;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LWFieldMapDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWFieldMapDBSet extends LWFieldMapSet
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
	public LWFieldMapDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LWFieldMap");
		mflag = true;
	}

	public LWFieldMapDBSet()
	{
		db = new DBOper( "LWFieldMap" );
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
			tError.moduleName = "LWFieldMapDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LWFieldMap WHERE  ActivityID = ? AND FieldOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActivityID());
			}
			pstmt.setInt(2, this.get(i).getFieldOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWFieldMap");
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
			tError.moduleName = "LWFieldMapDBSet";
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
			pstmt = con.prepareStatement("UPDATE LWFieldMap SET  ActivityID = ? , FieldOrder = ? , SourTableName = ? , SourFieldName = ? , SourFieldCName = ? , DestTableName = ? , DestFieldName = ? , DestFieldCName = ? , GetValue = ? , GetValueType = ? , CanShow = ? WHERE  ActivityID = ? AND FieldOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActivityID());
			}
			pstmt.setInt(2, this.get(i).getFieldOrder());
			if(this.get(i).getSourTableName() == null || this.get(i).getSourTableName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSourTableName());
			}
			if(this.get(i).getSourFieldName() == null || this.get(i).getSourFieldName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSourFieldName());
			}
			if(this.get(i).getSourFieldCName() == null || this.get(i).getSourFieldCName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSourFieldCName());
			}
			if(this.get(i).getDestTableName() == null || this.get(i).getDestTableName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDestTableName());
			}
			if(this.get(i).getDestFieldName() == null || this.get(i).getDestFieldName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDestFieldName());
			}
			if(this.get(i).getDestFieldCName() == null || this.get(i).getDestFieldCName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDestFieldCName());
			}
			if(this.get(i).getGetValue() == null || this.get(i).getGetValue().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getGetValue());
			}
			if(this.get(i).getGetValueType() == null || this.get(i).getGetValueType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGetValueType());
			}
			if(this.get(i).getCanShow() == null || this.get(i).getCanShow().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCanShow());
			}
			// set where condition
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getActivityID());
			}
			pstmt.setInt(13, this.get(i).getFieldOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWFieldMap");
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
			tError.moduleName = "LWFieldMapDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LWFieldMap VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActivityID());
			}
			pstmt.setInt(2, this.get(i).getFieldOrder());
			if(this.get(i).getSourTableName() == null || this.get(i).getSourTableName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSourTableName());
			}
			if(this.get(i).getSourFieldName() == null || this.get(i).getSourFieldName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSourFieldName());
			}
			if(this.get(i).getSourFieldCName() == null || this.get(i).getSourFieldCName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSourFieldCName());
			}
			if(this.get(i).getDestTableName() == null || this.get(i).getDestTableName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDestTableName());
			}
			if(this.get(i).getDestFieldName() == null || this.get(i).getDestFieldName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDestFieldName());
			}
			if(this.get(i).getDestFieldCName() == null || this.get(i).getDestFieldCName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDestFieldCName());
			}
			if(this.get(i).getGetValue() == null || this.get(i).getGetValue().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getGetValue());
			}
			if(this.get(i).getGetValueType() == null || this.get(i).getGetValueType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGetValueType());
			}
			if(this.get(i).getCanShow() == null || this.get(i).getCanShow().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCanShow());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWFieldMap");
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
			tError.moduleName = "LWFieldMapDBSet";
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
