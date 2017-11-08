/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LWActivitySchema;
import com.sinosoft.lis.vschema.LWActivitySet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LWActivityDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWActivityDBSet extends LWActivitySet
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
	public LWActivityDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LWActivity");
		mflag = true;
	}

	public LWActivityDBSet()
	{
		db = new DBOper( "LWActivity" );
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
			tError.moduleName = "LWActivityDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LWActivity WHERE  ActivityID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActivityID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWActivity");
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
			tError.moduleName = "LWActivityDBSet";
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
			pstmt = con.prepareStatement("UPDATE LWActivity SET  ActivityID = ? , ActivityName = ? , ActivityType = ? , ActivityDesc = ? , BeforeInit = ? , BeforeInitType = ? , AfterInit = ? , AfterInitType = ? , BeforeEnd = ? , BeforeEndType = ? , AfterEnd = ? , AfterEndType = ? , WatingTime = ? , WorkingTime = ? , TimeOut = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BusiType = ? , IsNeed = ? , ActivityFlag = ? , ImpDegree = ? , FunctionID = ? , CreateAction = ? , CreateActionType = ? , ApplyAction = ? , ApplyActionType = ? , DeleteAction = ? , DeleteActionType = ? , Together = ? , MenuNodeCode = ? WHERE  ActivityID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActivityID());
			}
			if(this.get(i).getActivityName() == null || this.get(i).getActivityName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getActivityName());
			}
			if(this.get(i).getActivityType() == null || this.get(i).getActivityType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getActivityType());
			}
			if(this.get(i).getActivityDesc() == null || this.get(i).getActivityDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getActivityDesc());
			}
			if(this.get(i).getBeforeInit() == null || this.get(i).getBeforeInit().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBeforeInit());
			}
			if(this.get(i).getBeforeInitType() == null || this.get(i).getBeforeInitType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBeforeInitType());
			}
			if(this.get(i).getAfterInit() == null || this.get(i).getAfterInit().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAfterInit());
			}
			if(this.get(i).getAfterInitType() == null || this.get(i).getAfterInitType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAfterInitType());
			}
			if(this.get(i).getBeforeEnd() == null || this.get(i).getBeforeEnd().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBeforeEnd());
			}
			if(this.get(i).getBeforeEndType() == null || this.get(i).getBeforeEndType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBeforeEndType());
			}
			if(this.get(i).getAfterEnd() == null || this.get(i).getAfterEnd().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAfterEnd());
			}
			if(this.get(i).getAfterEndType() == null || this.get(i).getAfterEndType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAfterEndType());
			}
			pstmt.setInt(13, this.get(i).getWatingTime());
			pstmt.setInt(14, this.get(i).getWorkingTime());
			pstmt.setInt(15, this.get(i).getTimeOut());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getBusiType() == null || this.get(i).getBusiType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getBusiType());
			}
			if(this.get(i).getIsNeed() == null || this.get(i).getIsNeed().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getIsNeed());
			}
			if(this.get(i).getActivityFlag() == null || this.get(i).getActivityFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getActivityFlag());
			}
			pstmt.setInt(24, this.get(i).getImpDegree());
			if(this.get(i).getFunctionID() == null || this.get(i).getFunctionID().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getFunctionID());
			}
			if(this.get(i).getCreateAction() == null || this.get(i).getCreateAction().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCreateAction());
			}
			if(this.get(i).getCreateActionType() == null || this.get(i).getCreateActionType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCreateActionType());
			}
			if(this.get(i).getApplyAction() == null || this.get(i).getApplyAction().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getApplyAction());
			}
			if(this.get(i).getApplyActionType() == null || this.get(i).getApplyActionType().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getApplyActionType());
			}
			if(this.get(i).getDeleteAction() == null || this.get(i).getDeleteAction().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getDeleteAction());
			}
			if(this.get(i).getDeleteActionType() == null || this.get(i).getDeleteActionType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDeleteActionType());
			}
			if(this.get(i).getTogether() == null || this.get(i).getTogether().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getTogether());
			}
			if(this.get(i).getMenuNodeCode() == null || this.get(i).getMenuNodeCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getMenuNodeCode());
			}
			// set where condition
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getActivityID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWActivity");
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
			tError.moduleName = "LWActivityDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LWActivity VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActivityID());
			}
			if(this.get(i).getActivityName() == null || this.get(i).getActivityName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getActivityName());
			}
			if(this.get(i).getActivityType() == null || this.get(i).getActivityType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getActivityType());
			}
			if(this.get(i).getActivityDesc() == null || this.get(i).getActivityDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getActivityDesc());
			}
			if(this.get(i).getBeforeInit() == null || this.get(i).getBeforeInit().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBeforeInit());
			}
			if(this.get(i).getBeforeInitType() == null || this.get(i).getBeforeInitType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBeforeInitType());
			}
			if(this.get(i).getAfterInit() == null || this.get(i).getAfterInit().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAfterInit());
			}
			if(this.get(i).getAfterInitType() == null || this.get(i).getAfterInitType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAfterInitType());
			}
			if(this.get(i).getBeforeEnd() == null || this.get(i).getBeforeEnd().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBeforeEnd());
			}
			if(this.get(i).getBeforeEndType() == null || this.get(i).getBeforeEndType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBeforeEndType());
			}
			if(this.get(i).getAfterEnd() == null || this.get(i).getAfterEnd().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAfterEnd());
			}
			if(this.get(i).getAfterEndType() == null || this.get(i).getAfterEndType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAfterEndType());
			}
			pstmt.setInt(13, this.get(i).getWatingTime());
			pstmt.setInt(14, this.get(i).getWorkingTime());
			pstmt.setInt(15, this.get(i).getTimeOut());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getBusiType() == null || this.get(i).getBusiType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getBusiType());
			}
			if(this.get(i).getIsNeed() == null || this.get(i).getIsNeed().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getIsNeed());
			}
			if(this.get(i).getActivityFlag() == null || this.get(i).getActivityFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getActivityFlag());
			}
			pstmt.setInt(24, this.get(i).getImpDegree());
			if(this.get(i).getFunctionID() == null || this.get(i).getFunctionID().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getFunctionID());
			}
			if(this.get(i).getCreateAction() == null || this.get(i).getCreateAction().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCreateAction());
			}
			if(this.get(i).getCreateActionType() == null || this.get(i).getCreateActionType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCreateActionType());
			}
			if(this.get(i).getApplyAction() == null || this.get(i).getApplyAction().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getApplyAction());
			}
			if(this.get(i).getApplyActionType() == null || this.get(i).getApplyActionType().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getApplyActionType());
			}
			if(this.get(i).getDeleteAction() == null || this.get(i).getDeleteAction().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getDeleteAction());
			}
			if(this.get(i).getDeleteActionType() == null || this.get(i).getDeleteActionType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDeleteActionType());
			}
			if(this.get(i).getTogether() == null || this.get(i).getTogether().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getTogether());
			}
			if(this.get(i).getMenuNodeCode() == null || this.get(i).getMenuNodeCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getMenuNodeCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWActivity");
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
			tError.moduleName = "LWActivityDBSet";
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
