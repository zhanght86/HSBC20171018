/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDTaskParamSchema;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDTaskParamDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 后台任务服务
 */
public class LDTaskParamDBSet extends LDTaskParamSet
{
private static Logger logger = Logger.getLogger(LDTaskParamDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDTaskParamDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDTaskParam");
		mflag = true;
	}

	public LDTaskParamDBSet()
	{
		db = new DBOper( "LDTaskParam" );
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
			tError.moduleName = "LDTaskParamDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDTaskParam WHERE  TaskCode = ? AND TaskPlanCode = ? AND ParamName = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTaskCode() == null || this.get(i).getTaskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTaskCode());
			}
			if(this.get(i).getTaskPlanCode() == null || this.get(i).getTaskPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTaskPlanCode());
			}
			if(this.get(i).getParamName() == null || this.get(i).getParamName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getParamName());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDTaskParam");
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
			tError.moduleName = "LDTaskParamDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDTaskParam SET  TaskCode = ? , TaskPlanCode = ? , ParamName = ? , ParamValue = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  TaskCode = ? AND TaskPlanCode = ? AND ParamName = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTaskCode() == null || this.get(i).getTaskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTaskCode());
			}
			if(this.get(i).getTaskPlanCode() == null || this.get(i).getTaskPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTaskPlanCode());
			}
			if(this.get(i).getParamName() == null || this.get(i).getParamName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getParamName());
			}
			if(this.get(i).getParamValue() == null || this.get(i).getParamValue().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getParamValue());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getTaskCode() == null || this.get(i).getTaskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getTaskCode());
			}
			if(this.get(i).getTaskPlanCode() == null || this.get(i).getTaskPlanCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getTaskPlanCode());
			}
			if(this.get(i).getParamName() == null || this.get(i).getParamName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getParamName());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDTaskParam");
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
			tError.moduleName = "LDTaskParamDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDTaskParam(TaskCode ,TaskPlanCode ,ParamName ,ParamValue ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTaskCode() == null || this.get(i).getTaskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTaskCode());
			}
			if(this.get(i).getTaskPlanCode() == null || this.get(i).getTaskPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTaskPlanCode());
			}
			if(this.get(i).getParamName() == null || this.get(i).getParamName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getParamName());
			}
			if(this.get(i).getParamValue() == null || this.get(i).getParamValue().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getParamValue());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDTaskParam");
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
			tError.moduleName = "LDTaskParamDBSet";
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
